package com.ardais.bigr.iltds.helpers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.ejb.ObjectNotFoundException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.concept.BigrConcept;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.es.beans.ArdaisaccountAccessBean;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.iltds.assistants.Address;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.beans.AsmAccessBean;
import com.ardais.bigr.iltds.beans.AsmKey;
import com.ardais.bigr.iltds.beans.AsmformAccessBean;
import com.ardais.bigr.iltds.beans.AsmformKey;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.beans.GeolocationKey;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.beans.RevokedconsentAccessBean;
import com.ardais.bigr.iltds.beans.RevokedconsentKey;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.beans.SamplestatusAccessBean;
import com.ardais.bigr.iltds.beans.SequenceGenAccessBean;
import com.ardais.bigr.iltds.bizlogic.RequestFinder;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxDetailsLogCreateSample;
import com.ardais.bigr.iltds.databeans.IrbVersionData;
import com.ardais.bigr.iltds.databeans.PolicyData;
import com.ardais.bigr.iltds.databeans.SampleType;
import com.ardais.bigr.iltds.databeans.StorageType;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.AddressDto;
import com.ardais.bigr.javabeans.ArdaisStaff;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.LocationData;
import com.ardais.bigr.javabeans.ManifestDto;
import com.ardais.bigr.javabeans.RequestBoxDto;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.javabeans.ShippingPartnerDto;
import com.ardais.bigr.javabeans.StorageUnitSummaryDto;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.util.PolicyUtils;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

public class IltdsUtils {

  public static final String BMS = "BMS";
  public static final String NONBMS = "NONBMS";
  
  public static final String OUT_OF_NETWORK_LOCATION = "OUTOFNETWORK";

  public static final String APPLY_POLICY_FOR_SALES_STATUS = "SALES_STATUS";

  public static final String STORABLE_SAMPLE_TYPE_LIST = "STORABLE_SAMPLE_TYPE_LIST";
  public static final String UNSTORABLE_SAMPLE_TYPE_LIST = "UNSTORABLE_SAMPLE_TYPE_LIST";

  public static final String STORABLE_SAMPLE_ID_LIST = "STORABLE_SAMPLE_ID_LIST";
  public static final String UNSTORABLE_SAMPLE_ID_LIST = "UNSTORABLE_SAMPLE_ID_LIST";

  private static final Set VALID_APPLY_POLICY_ACTIONS = new HashSet();

  static {
    VALID_APPLY_POLICY_ACTIONS.add(APPLY_POLICY_FOR_SALES_STATUS);
  }

  /**
   * Return true if the specified user is at the same location that the specified case
   * came from.  If the user's account type is system owner, this always returns
   * true.
   * 
   * @param consentId the case id
   * @param userId the user id
   * 
   * @return true if the case and user are from the same location or the user is in an Ardais-type
   *   account.
   */
  public static boolean checkGeoLocation(String consentId, String userId) {
    try {
      ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(userId));

      ArdaisaccountAccessBean accountBean =
        new ArdaisaccountAccessBean(new ArdaisaccountKey(staff.getArdais_acct_key()));

      if (accountBean.getArdais_acct_type().equals(Constants.ACCOUNT_TYPE_SYSTEM_OWNER)) {
        return true;
      }

      ConsentAccessBean consent = new ConsentAccessBean(new ConsentKey(consentId));
      String consentLocation = consent.getGeolocationKey().location_address_id;

      String userLocation = staff.getGeolocation_location_address_id();

      return consentLocation.equals(userLocation);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // not reached, make compiler happy.
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (2/13/2001 10:24:08 AM)
   * @return boolean
   * @param consentID java.lang.String
   */
  public static boolean consentPulled(String consentID) {
    ConsentAccessBean consent;
    AccessBeanEnumeration consentEnum;
    
    //if the case doesn't exist, then it cannot be pulled so return false
    if (!caseExists(consentID)) {
      return false;
    }

    try {
      consent = new ConsentAccessBean();
      consentEnum = (AccessBeanEnumeration) consent.findByConsentID(consentID);
      if (consentEnum.hasMoreElements()) {
        consent = (ConsentAccessBean) consentEnum.nextElement();
        if (consent.getConsent_pull_datetime() == null) {
          return false;
        }
        else {
          return true;
        }
      }
      else {
        return false;
      }

    }
    catch (Exception onfe) {

      return false;
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/13/2001 10:24:08 AM)
   * @return boolean
   * @param consentID java.lang.String
   */
  public static boolean consentRevoked(String consentID) {
    ConsentAccessBean consent;
    AccessBeanEnumeration consentEnum;
    
    //if the case doesn't exist, then it cannot be revoked so return false
    if (!caseExists(consentID)) {
      return false;
    }

    try {
      consent = new ConsentAccessBean();
      consentEnum = (AccessBeanEnumeration) consent.findByConsentID(consentID);
      consent = (ConsentAccessBean) consentEnum.nextElement();
      ConsentKey key = (ConsentKey) consent.__getKey();

      new RevokedconsentAccessBean(new RevokedconsentKey(key.consent_id, consent.getArdais_id()));

      return true;
    }
    catch (ObjectNotFoundException onfe) {
      return false;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // make compiler happy; won't ever be reached
    }
  }
  
  /**
   * Insert the method's description here.
   * Creation date: (6/22/2001 1:21:01 PM)
   * @return java.lang.String
   * @exception com.ardais.bigr.api.ApiException The exception description.
   */
  public static String getNextPicklistID() throws com.ardais.bigr.api.ApiException {
    try {
      SequenceGenAccessBean seq = new SequenceGenAccessBean();
      String id = seq.getSeqNextVal(FormLogic.ILTDS_PATH_PICKLIST_SEQ);
      return id;
    }
    catch (Exception e) {
      throw new ApiException("Error creating new staff ID.");
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (6/22/2001 1:21:01 PM)
   * @return java.lang.String
   * @exception com.ardais.bigr.api.ApiException The exception description.
   */
  public static String getNextStaffID() throws com.ardais.bigr.api.ApiException {
    try {
      SequenceGenAccessBean seq = new SequenceGenAccessBean();
      String id = seq.getSeqNextVal(FormLogic.ILTDS_ARDAIS_STAFF_SEQ);
      return id;
    }
    catch (Exception e) {
      throw new ApiException("Error creating new staff ID.");
    }
  }

  /**
   * @param id A sample id.
   * @return True if the sample's case has been pulled or revoked.
   */
  public static boolean sampleCasePulledOrRevoked(String id) {
    if (ApiFunctions.isEmpty(id)) {
      return false;
    }

    boolean exists = false;

    String query =
      "select s.sample_barcode_id "
        + "from iltds_sample s, iltds_asm a, iltds_informed_consent c "
        + "where s.asm_id = a.asm_id "
        + "  and a.consent_id = c.consent_id "
        + "  and s.sample_barcode_id = ? "
        + "  and (c.consent_id in (select r.consent_id from iltds_revoked_consent_archive r) "
        + "       or "
        + "     c.consent_pull_datetime is not null) ";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        exists = true;
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return exists;
  }

  /**
   * Validate the specified box id.  Return the canonical form of the id if the
   * id is valid, otherwise return null and add appropriate ActionErrors
   * to btxDetails.  When isRequired is false, a null id is considered valid.  To
   * make it easier for callers to know whether the id was valid in all situations,
   * this method returns an empty string ("") when the input id is null and isRequired
   * is false. 
   * 
   * @param id The box id to validate.
   * @param btxDetails The btxDetails object to add ActionErrors to.
   * @param lenient True to accept non-canonical forms of the id (e.g. "bx12").
   * @param isRequired True if the id is required.
   * @param mustExist True if the id must specify a box that exists in the
   *     database.  If isRequired is false and the id is empty, then the existence check is
   *     not performed, so if you want to be certain that the id is the id of an existing
   *     object, you must pass <code>true</code> to both isRequired and mustExist.
   * @return The box id in canonical form if it is valid, otherwise null.
   */
  public static String checkBoxId(
    String id,
    BTXDetails btxDetails,
    boolean lenient,
    boolean isRequired,
    boolean mustExist) {

    boolean isOk = true;

    if (lenient) {
      id = ApiFunctions.safeString(ApiFunctions.safeTrim(id));
    }

    if (ApiFunctions.isEmpty(id)) {
      if (isRequired) {
        btxDetails.addActionError(new BtxActionError("iltds.error.general.requiredBoxId"));
        return null;
      }
      else {
        return ApiFunctions.EMPTY_STRING;
      }
    }

    {
      String validatedId = ValidateIds.validateId(id, ValidateIds.TYPESET_BOX, lenient);
      if (validatedId == null) {
        btxDetails.addActionError(
          new BtxActionError("iltds.error.general.invalidBoxId", Escaper.htmlEscape(id)));
        return null;
      }
      id = validatedId;
    }

    if (mustExist && !boxExists(id)) {
      isOk = false;
      btxDetails.addActionError(new BtxActionError("iltds.error.general.notFoundBoxId", id));
    }

    return (isOk ? id : null);
  }

  /**
   * Return true if a box with the specified id exists in the database.
   * 
   * @param id The box id.
   * @return True if a box with the specified id exists in the database.
   */
  public static boolean boxExists(String id) {
    if (ApiFunctions.isEmpty(id)) {
      return false;
    }

    boolean exists = false;

    String query = "SELECT 1 FROM iltds_sample_box b WHERE b.box_barcode_id = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        exists = true;
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return exists;
  }

  /**
   * Return true if a box layout with the specified id exists in the database.
   * 
   * @param id The box layout id.
   * @return True if a box layout with the specified id exists in the database.
   */
  public static boolean boxLayoutExists(String id) {
    if (ApiFunctions.isEmpty(id)) {
      return false;
    }

    boolean exists = false;

    String query = "SELECT 1 FROM " + DbConstants.TABLE_BOX_LAYOUT + " WHERE " + DbConstants.LY_BOX_LAYOUT_ID + " = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        exists = true;
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return exists;
  }


  /**
   * Validate the specified sample id.  Return the canonical form of the id if the
   * id is valid, otherwise return null and add appropriate ActionErrors
   * to btxDetails.  When isRequired is false, a null id is considered valid.  To
   * make it easier for callers to know whether the id was valid in all situations,
   * this method returns an empty string ("") when the input id is null and isRequired
   * is false. 
   * 
   * @param id The sample id to validate.
   * @param btxDetails The btxDetails object to add ActionErrors to.
   * @param lenient True to accept non-canonical forms of the id (e.g. "fr12").
   * @param isRequired True if the id is required.
   * @param mustExist True if the id must specify a sample that exists in the database.
   *     If isRequired is false and the id is empty, then the existence check is
   *     not performed, so if you want to be certain that the id is the id of an existing
   *     object, you must pass <code>true</code> to both isRequired and mustExist.
   * @return The sample id in canonical form if it is valid, otherwise null.
   */
  public static String checkSampleId(
    String id,
    BTXDetails btxDetails,
    boolean lenient,
    boolean isRequired,
    boolean mustExist) {

    boolean isOk = true;

    if (lenient) {
      id = ApiFunctions.safeString(ApiFunctions.safeTrim(id));
    }

    if (ApiFunctions.isEmpty(id)) {
      if (isRequired) {
        btxDetails.addActionError(new BtxActionError("iltds.error.general.requiredSampleId"));
        return null;
      }
      else {
        return ApiFunctions.EMPTY_STRING;
      }
    }

    {
      String validatedId = ValidateIds.validateId(id, ValidateIds.TYPESET_SAMPLE, lenient);
      if (validatedId == null) {
        btxDetails.addActionError(
          new BtxActionError("iltds.error.general.invalidSampleId", Escaper.htmlEscape(id)));
        return null;
      }
      id = validatedId;
    }

    if (mustExist && !sampleExists(id)) {
      isOk = false;
      btxDetails.addActionError(new BtxActionError("iltds.error.general.notFoundSampleId", id));
    }

    return (isOk ? id : null);
  }

  /**
   * Return true if a sample with the specified id exists in the database.
   * 
   * @param id The sample id.
   * @return True if a sample with the specified id exists in the database.
   */
  public static boolean sampleExists(String id) {
    if (ApiFunctions.isEmpty(id)) {
      return false;
    }

    boolean exists = false;

    String query = "SELECT 1 FROM iltds_sample s WHERE s.sample_barcode_id = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        exists = true;
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return exists;
  }

  /**
   * Return true if a policy with the specified id exists in the database.
   * 
   * @param id The policy id.
   * @return True if a policy with the specified id exists in the database.
   */
  public static boolean policyExists(String id) {
    if (ApiFunctions.isEmpty(id)) {
      return false;
    }

    boolean exists = false;

    String query = "SELECT 1 FROM " + DbConstants.TABLE_ARD_POLICY + " WHERE " + DbConstants.POLICY_ID + " = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setInt(1, ApiFunctions.safeInteger(id, 0));
      rs = pstmt.executeQuery();
      if (rs.next()) {
        exists = true;
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return exists;
  }

  /**
   * Validate the specified tracking number,  Return the tracking number if the
   * id is valid, otherwise return null and add appropriate ActionErrors
   * to btxDetails.  When isRequired is false, a null id is considered valid.  To
   * make it easier for callers to know whether the id was valid in all situations,
   * this method returns an empty string ("") when the input id is null and isRequired
   * is false. 
   * 
   * @param id The sample id to validate.
   * @param btxDetails The btxDetails object to add ActionErrors to.
   * @param isRequired True if the id is required.
   * @param mustExist True if the id must specify a sample that exists in the database.
   *     If isRequired is false and the id is empty, then the existence check is
   *     not performed, so if you want to be certain that the id is the id of an existing
   *     object, you must pass <code>true</code> to both isRequired and mustExist.
   * @return The tracking number in canonical form if it is valid, otherwise null.
   */
  public static String checkTrackingNumber(
    String id,
    BTXDetails btxDetails,
    boolean isRequired,
    boolean mustExist) {

    boolean isOk = true;

    if (ApiFunctions.isEmpty(id)) {
      if (isRequired) {
        btxDetails.addActionError(new BtxActionError("iltds.error.general.requiredTrackingNumber"));
        return null;
      }
      else {
        return ApiFunctions.EMPTY_STRING;
      }
    }

    {
      String validatedId = parseTrackingNumber(id);
      if (validatedId == null) {
        btxDetails.addActionError(
          new BtxActionError("iltds.error.general.invalidTrackingNumber", Escaper.htmlEscape(id)));
        return null;
      }
      id = validatedId;
    }

    if (mustExist && !trackingNumberExists(id)) {
      isOk = false;
      btxDetails.addActionError(
        new BtxActionError("iltds.error.general.notFoundTrackingNumber", id));
    }

    return (isOk ? id : null);
  }

  /**
   * Return true if a tracking number with the specified id exists in the database.
   * 
   * @param trackingNumber The tracking number.
   * @return True if a tracking number with the specified id exists in the database.
   */
  public static boolean trackingNumberExists(String trackingNumber) {
    try {
      ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
      ListGenerator list = home.create();

      return list.trackingNumberExists(parseTrackingNumber(trackingNumber));
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return false;
  }

  /**
   * FedEx tracking IDs, when scanned, contain extra information beyond what you would
   * enter at the FedEx tracking site.  We accept three different forms here: 
   * 12-char (just the tracking ID), 16-char (trackingID + Form ID), and 18-char variations
   * (prefix char + tracking ID + Form ID + suffix char).  See MR 1552 for complete details.
   * 
   * If the input string is either 16 or 18 characters we assume it is a FedEx id and the
   * return value is just the 12-character tracking id part.  Otherwise we return the input
   * string unchanged.
   * 
   * @param The raw barcode id.
   * @return The modified tracking number as described above.
   */
  private static String parseFedEx(String barcode) {
    int length = barcode.length();

    if (length == 16) {
      return barcode.substring(0, 12);
    }
    else if (length == 18) {
      return barcode.substring(1, 13);
    }

    return barcode;
  }

  /**
   * Currently we assume that the tracking number is a FedEx tracking number, and this may not
   * do the right thing for other kinds of tracking numbers (or if FedEx changes their format).
   * This returns the canonical form of the tracking number if it is valid, otherwise it
   * returns null.
   * 
   * @param barcode The tracking number.
   * @return The canonicalized number or null as described above.
   */
  public static String parseTrackingNumber(String barcode) {
    barcode = ApiFunctions.safeString(ApiFunctions.safeTrim(barcode));
    return parseFedEx(barcode);
  }

  /**
   * Validate the specified manifest number.  Return the canonical form of the id
   * if the id is valid, otherwise return null and add appropriate ActionErrors
   * to btxDetails.  When isRequired is false, a null id is considered valid.  To
   * make it easier for callers to know whether the id was valid in all situations,
   * this method returns an empty string ("") when the input id is null and isRequired
   * is false. If the manifest number is syntactically valid, the number in the
   * btxDetails is replaced by the canonical form of the number (for example, "mnft3001"
   * is replaced by "MNFT0000003001").
   * 
   * @param id The manifest number id to validate.
   * @param btxDetails The btxDetails object to add ActionErrors to.
   * @param lenient True to accept non-canonical forms of the id (e.g. "mnft3001").
   * @param isRequired True if the id is required.
   * @param mustExist True if the id must specify a manifest number that exists in the database.
   *     If isRequired is false and the id is empty, then the existence check is
   *     not performed, so if you want to be certain that the id is the id of an existing
   *     object, you must pass <code>true</code> to both isRequired and mustExist.
   * @return The sample id in canonical form if it is valid, otherwise null.
   */
  public static String checkManifestNumber(
    String id,
    BTXDetails btxDetails,
    boolean lenient,
    boolean isRequired,
    boolean mustExist) {

    boolean isOk = true;

    if (lenient) {
      id = ApiFunctions.safeString(ApiFunctions.safeTrim(id));
    }

    if (ApiFunctions.isEmpty(id)) {
      if (isRequired) {
        btxDetails.addActionError(new BtxActionError("iltds.error.general.requiredManifestNumber"));
        return null;
      }
      else {
        return ApiFunctions.EMPTY_STRING;
      }
    }

    {
      String validatedId = ValidateIds.validateId(id, ValidateIds.TYPESET_MANIFEST, lenient);
      if (validatedId == null) {
        btxDetails.addActionError(
          new BtxActionError("iltds.error.general.invalidManifestNumber", Escaper.htmlEscape(id)));
        return null;
      }
      id = validatedId;
    }

    if (mustExist && !manifestNumberExists(id)) {
      isOk = false;
      btxDetails.addActionError(
        new BtxActionError("iltds.error.general.notFoundManifestNumber", id));
    }

    return (isOk ? id : null);
  }

  /**
   * Return true if a manifest number with the specified id exists in the database.
   * 
   * @param manifestNumber The manifest number.
   * @return True if a manifest number with the specified id exists in the database.
   */
  private static boolean manifestNumberExists(String manifestNumber) {
    if (ApiFunctions.isEmpty(manifestNumber)) {
      return false;
    }

    boolean exists = false;

    String query = "SELECT 1 FROM iltds_manifest m WHERE m.manifest_number = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, manifestNumber);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        exists = true;
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return exists;
  }

  /**
   * Return details on a manifest object given that manifest unique identifier number.
   * This populates basic manifest fields on the returned ManifestDot, and also populates
   * the details of the ship to/from fields (as {@link Address} objects).  To keep this
   * method fast for the common cases where manifest box details aren't needed, this does
   * NOT populate the box information lists on the ManifestDto.
   * 
   * <p>If the specified manifest does not exist, a runtime exception is thrown.
   * See {@link #getManifestById(String, boolean)} for a method that does not necessarily
   * throw an exception when the manifest does not exist. 
   * 
   * @param manifestNumber The manifest number.
   * @return The populated ManifestDto.
   */
  public static ManifestDto getManifestById(String manifestNumber) {
    return getManifestById(manifestNumber, true);
  }

    /**
     * Return details on a manifest object given that manifest unique identifier number.
     * This populates basic manifest fields on the returned ManifestDot, and also populates
     * the details of the ship to/from fields (as {@link Address} objects).  To keep this
     * method fast for the common cases where manifest box details aren't needed, this does
     * NOT populate the box information lists on the ManifestDto.
     * 
     * <p>If the specified manifest does not exist, what happens depends on the 
     * <code>exceptionIfNotExists</code> parameter.  If it is true, a runtime exception is thrown.
     * If it is false, null is returned.
     * 
     * @param manifestNumber The manifest number.
     * @param exceptionIfNotExists If the manifest doesn't exist and this parameter is true,
     *   throw a runtime exception, otherwise return null when the manifest doesn't exist.
     * @return The populated ManifestDto, or null in some cases when the manifest does not
     *   exist as described above.
     */
    public static ManifestDto getManifestById(String manifestNumber, boolean exceptionIfNotExists) {
    // Note to developers:  If you need box details in addition to what this method provides,
    // please don't change this method to retrieve them.  This method is called many places
    // that don't need that level of detail, and we shouldn't force those places to pay
    // the performance price of getting the box information.  Currently, the methods that
    // retrieve that additional information are private methods in BtxPerformerShippingOperations,
    // since that's the only place that needs them.  

    ManifestDto manifestDto = null;
    if (manifestNumber != null) {
      String query =
        "SELECT m.*, "
          + "\n  af.address_type from_address_type, "
          + "\n  af.ardais_acct_key from_ardais_acct_key, af.address_1 from_address_1, "
          + "\n  af.address_2 from_address_2, af.addr_city from_addr_city, "
          + "\n  af.addr_state from_addr_state, af.addr_zip_code from_addr_zip_code, "
          + "\n  af.addr_country from_addr_country, af.first_name from_first_name, "
          + "\n  af.middle_name from_middle_name, af.last_name from_last_name, "
          + "\n  decode(af.address_type, '" 
          + com.ardais.bigr.es.helpers.FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED
          + "', null, cf.ardais_acct_company_desc) from_ardais_acct_company_desc, "
          + "\n  ato.address_type to_address_type, "
          + "\n  ato.ardais_acct_key to_ardais_acct_key, ato.address_1 to_address_1, "
          + "\n  ato.address_2 to_address_2, ato.addr_city to_addr_city, "
          + "\n  ato.addr_state to_addr_state, ato.addr_zip_code to_addr_zip_code, "
          + "\n  ato.addr_country to_addr_country, ato.first_name to_first_name, "
          + "\n  ato.middle_name to_middle_name, ato.last_name to_last_name, "
          + "\n  decode(ato.address_type, '" 
          + com.ardais.bigr.es.helpers.FormLogic.ADDRESS_TYPE_SYSTEM_GENERATED
          + "', null, cto.ardais_acct_company_desc) to_ardais_acct_company_desc "
          + "\nFROM iltds_manifest m, ardais_address af, es_ardais_account cf, "
          + "\n     ardais_address ato, es_ardais_account cto "
          + "\nWHERE m.manifest_number = ? "
          + "\n  AND m.ship_from_addr_id = af.address_id "
          + "\n  AND af.ardais_acct_key = cf.ardais_acct_key "
          + "\n  AND m.ship_to_addr_id = ato.address_id "
          + "\n  AND ato.ardais_acct_key = cto.ardais_acct_key ";

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(query);
        pstmt.setString(1, manifestNumber);
        rs = pstmt.executeQuery();
        if (rs.next()) {
          Map columns = DbUtils.getColumnNames(rs);
          
          manifestDto = new ManifestDto();
          manifestDto.populateFromResultSet(columns, rs);

          Address addressFrom = new Address();
          addressFrom.setAddressID(manifestDto.getShipFromAddressId());
          addressFrom.setAddressType(rs.getString("from_address_type"));
          addressFrom.setAddressAccountId(rs.getString("from_ardais_acct_key"));
          addressFrom.setAddressName(rs.getString("from_ardais_acct_company_desc"));
          addressFrom.setLocationAddress1(rs.getString("from_address_1"));
          addressFrom.setLocationAddress2(rs.getString("from_address_2"));
          addressFrom.setLocationCity(rs.getString("from_addr_city"));
          addressFrom.setLocationState(rs.getString("from_addr_state"));
          addressFrom.setLocationZip(rs.getString("from_addr_zip_code"));
          addressFrom.setCountry(rs.getString("from_addr_country"));
          addressFrom.setFirstName(rs.getString("from_first_name"));
          addressFrom.setMiddleName(rs.getString("from_middle_name"));
          addressFrom.setLastName(rs.getString("from_last_name"));
          manifestDto.setShipFromAddress(addressFrom);

          Address addressTo = new Address();
          addressTo.setAddressID(manifestDto.getShipToAddressId());
          addressTo.setAddressType(rs.getString("to_address_type"));
          addressTo.setAddressAccountId(rs.getString("to_ardais_acct_key"));
          addressTo.setAddressName(rs.getString("to_ardais_acct_company_desc"));
          addressTo.setLocationAddress1(rs.getString("to_address_1"));
          addressTo.setLocationAddress2(rs.getString("to_address_2"));
          addressTo.setLocationCity(rs.getString("to_addr_city"));
          addressTo.setLocationState(rs.getString("to_addr_state"));
          addressTo.setLocationZip(rs.getString("to_addr_zip_code"));
          addressTo.setCountry(rs.getString("to_addr_country"));
          addressTo.setFirstName(rs.getString("to_first_name"));
          addressTo.setMiddleName(rs.getString("to_middle_name"));
          addressTo.setLastName(rs.getString("to_last_name"));
          manifestDto.setShipToAddress(addressTo);
        }
        else if (exceptionIfNotExists) {
          throw new ApiException("Could not find manifest for manifest number" + manifestNumber);
        }
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
    return manifestDto;
  }

  /**
   * Method to determine if a sample id has been assigned to an account for purposes of
   * importing that sample.
   * @return boolean
   * @param sampleId - the sample to evaluate
   * @param accountId - the account to evaluate
   */
  public static String getAccountAssignedToSample(String sampleId) {
    String returnValue;
    if (ApiFunctions.isEmpty(sampleId)) {
      throw new ApiException("Sample id cannot be empty");
    }
    StringBuffer sql = new StringBuffer(100);
    sql.append("SELECT ARDAIS_ACCT_KEY FROM ILTDS_ASSIGNED_SAMPLE_IDS WHERE ?");
    sql.append(" BETWEEN SAMPLE_ID_START AND SAMPLE_ID_END");
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        returnValue = rs.getString(1);
      }
      else  {
        returnValue = "";
      } 
    }
    catch (SQLException e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con,pstmt,rs);
    }

    return returnValue;
  }

  /**
   * Method to determine if a sample id has been assigned to an account for purposes of
   * importing that sample.
   * @return boolean
   * @param sampleId - the sample to evaluate
   * @param accountId - the account to evaluate
   */
  public static boolean isSampleAssignedToAccount(String sampleId, String accountId) {
    boolean returnValue = false;
    if (ApiFunctions.isEmpty(sampleId)) {
      throw new ApiException("Sample id cannot be empty");
    }
    if (ApiFunctions.isEmpty(accountId)) {
      throw new ApiException("Account id cannot be empty");
    }
    StringBuffer sql = new StringBuffer(100);
    sql.append("SELECT * FROM ILTDS_ASSIGNED_SAMPLE_IDS WHERE ?");
    sql.append(" BETWEEN SAMPLE_ID_START AND SAMPLE_ID_END AND ARDAIS_ACCT_KEY = ?");
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      pstmt.setString(2, accountId);
      rs = pstmt.executeQuery();
      returnValue = rs.next();
    }
    catch (SQLException e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con,pstmt,rs);
    }

    return returnValue;
  }

  /**
   * Method to determine if the account that the specified user is in can create the
   * specified sample id (for example, by performing a box scan on the sample id).
   * This does NOT check to see whether the sample already exists or not, it just checks
   * whether it WOULD be creatable if it didn't exist.  It also does NOT check that the specific
   * user passed in can create the sample based on their assigned privileges.  The checking
   * that we currently do here is account-based, not user-based.
   * 
   * @param sampleID the sample id
   * @param securityInfo the user's security information
   * @return true if the sample is creatable by a user in the specified account.
   */
  public static boolean isSampleCreatableByAccount(String sampleId, SecurityInfo securityInfo) {
    // MR 7867 lays out the rules:  Only DI users can create non-imported sample ids.
    // DI users can create imported samples only if the sample ids have been assigned to
    // their account.  Ardais users can create samples for any imported sample id, as long as
    // the sample has been assigned to an account.
     
    if (!ValidateIds.isValid(sampleId, ValidateIds.TYPESET_SAMPLE, false)) {
      return false;
    }
    
    if (securityInfo.isInRoleDi()) {
      boolean isImportedId = ValidateIds.isValid(sampleId, ValidateIds.TYPESET_SAMPLE_IMPORTED, false);
      if (! isImportedId) {
        return true;
      }
      return isSampleAssignedToAccount(sampleId, securityInfo.getAccount());
    }
    else if (securityInfo.isInRoleSystemOwner()) {
      boolean isImportedId = ValidateIds.isValid(sampleId, ValidateIds.TYPESET_SAMPLE_IMPORTED, false);
      if (isImportedId) {
        return !ApiFunctions.isEmpty(getAccountAssignedToSample(sampleId));
      }
      else {
        return false;
      }
    }
    else {
      return false;
    }
  }

  /**
   * Method to determine if a sample id has been imported.  Note that we not only check
   * that imported_yn = 'Y', but we also check to see if the sample has an associated
   * consent.  This is because an imported sample may have been created via a box scan,
   * but it won't have a consent associated with it until create sample has been performed.
   * @return boolean
   * @param sampleId - the sample to evaluate
   */
  public static boolean isSampleImported(String sampleId) {
    boolean returnValue = false;
    if (ApiFunctions.isEmpty(sampleId)) {
      throw new ApiException("Sample id cannot be empty");
    }
    StringBuffer sql = new StringBuffer(100);
    sql.append("SELECT * FROM ILTDS_SAMPLE WHERE SAMPLE_BARCODE_ID = ?");
    sql.append(" AND IMPORTED_YN = 'Y' AND CONSENT_ID IS NOT NULL ");
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, sampleId);
      rs = pstmt.executeQuery();
      returnValue = rs.next();
      rs.close();
    }
    catch (SQLException e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con,pstmt,rs);
    }

    return returnValue;
  }

  /**
   * Method to determine which samples are BMS or non-BMS.
   * @return ArrayList - the samples to be returned 
   * @param filter - look for BMS samples or look for NONBMS samples
   * @param sampleList - the sample list to  evaluate
   */
  public static ArrayList getBMSSamplesFromList(String filter, ArrayList sampleList) {

    ArrayList bms_List = new ArrayList();
    if (sampleList.isEmpty()) {
      return bms_List;
    }

    StringBuffer sql = new StringBuffer();

    // okay, we need to construct the SQL clause...
    sql.append("SELECT SAMPLE_BARCODE_ID FROM ILTDS_SAMPLE WHERE (SAMPLE_BARCODE_ID=? ");
    for (int j = 0; j < (sampleList.size() - 1); j++) {
      // create additional matches to the sample barcode id
      sql.append(" OR SAMPLE_BARCODE_ID=? ");
    }

    if (filter.equals(BMS)) {
      sql.append(" ) AND BMS_YN='Y'");
    }
    else {
      sql.append(" ) AND BMS_YN='N'");
    }
    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(sql.toString());

      // ok, now we have to bind the sample ids to the query
      String sampleId = new String();
      for (int i = 0; i < sampleList.size(); i++) {
        sampleId = (String) sampleList.get(i);
        pstmt.setString(i + 1, sampleId);
      }

      ResultSet rs = pstmt.executeQuery();
      String id = new String();
      while (rs.next()) {
        // ok, build the list of samples that match the query
        id = rs.getString(1);
        bms_List.add(new String(id));
      }
      rs.close();
    }
    catch (SQLException e) {
      throw new ApiException("Error getting details on getBMSSamplesFromList ", e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }

    return bms_List;
  }

  /**
   * Return information about the IRB version with the specified consent version id.
   * 
   * @param consentVersionId The primary key of the consent version 
   * @return the IRB version information
   */
  public static IrbVersionData getIrbVersionData(String consentVersionId) {
    String queryString =
      "select irb.IRBPROTOCOL_ID, irb.IRBPROTOCOL, irb.POLICY_ID, con.CONSENT_VERSION "
        + "from es_ardais_consentver con, es_ardais_irb irb "
        + "where irb.IRBPROTOCOL_ID = con.IRBPROTOCOL_ID "
        + "and con.CONSENT_VERSION_ID = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    IrbVersionData result = new IrbVersionData();

    try {
      result.setConsentVersionId(consentVersionId);

      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setBigDecimal(1, new BigDecimal(consentVersionId));
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        result.setIrbProtocolId(rs.getString(1));
        result.setIrbProtocolName(rs.getString(2));
        result.setIrbPolicyId(rs.getString(3));
        result.setConsentVersionName(rs.getString(4));
      }
      else {
        throw new ApiException(
          "Could not retrieve IRB information for consent_version_id = " + consentVersionId);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  /**
   * Return information about the logical repository with the specified id.
   * 
   * @param repositoryId The primary key of the logical repository.
   * @param exceptionIfNotExists If true, throw an exception if there is no logical
   *     repository with the specified id.  If false, return null if the repository doesn't exist.
   * @param resultClass The class of object to create to hold the result.  This must be
   *     {@link LogicalRepository} or a class that extends it.  This method only populates
   *     fields that exist on the LogicalRepository class itself.
   * @return the logical repository information
   */
  public static LogicalRepository getLogicalRepositoryData(
    String repositoryId,
    boolean exceptionIfNotExists,
    Class resultClass) {

    LogicalRepository result = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String queryString =
        "select r.full_name, r.short_name, r.bms_yn " + "from ard_logical_repos r where r.id = ?";

      result = (LogicalRepository) resultClass.newInstance();

      result.setId(repositoryId);

      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setBigDecimal(1, new BigDecimal(repositoryId));
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        result.setFullName(rs.getString(1));
        result.setShortName(rs.getString(2));
        result.setBmsYN(rs.getString(3));
      }
      else if (exceptionIfNotExists) {
        throw new ApiException(
          "Could not retrieve logical repository information for id = " + repositoryId);
      }
      else {
        result = null;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  /**
   * Return information about the logical repository with the specified id.
   * 
   * @param repositoryId The primary key of the logical repository.
   * @return the logical repository information
   */
  public static LogicalRepository getLogicalRepositoryData(String repositoryId) {
    return getLogicalRepositoryData(repositoryId, true, LogicalRepository.class);
  }

  /**
   * Return true if the box with the specified id is unshipped on an open request
   * 
   * @param boxId The id of the box.
   * @return true if the specified box is unshipped on an open request.
   */
  public static boolean isBoxUnshippedOnOpenRequest(SecurityInfo securityInfo, String boxId) {
    boolean returnValue = false;
    // Set up the filter.
    RequestFilter filter = new RequestFilter();
    filter.setIncludeClosedRequests(false);
    filter.setBoxId(boxId);
    //set up the select object
    RequestSelect selector = new RequestSelect(true, true, RequestSelect.ITEM_INFO_NONE, RequestSelect.BOX_INFO_DETAILS);
    List openRequests = RequestFinder.find(securityInfo, selector, filter);
    //iterate over any returned requests, and see if this box is unshipped on each request.
    //Stop once we find one
    Iterator requestIterator = openRequests.iterator();
    while (requestIterator.hasNext() && !returnValue) {
      RequestDto request = (RequestDto)requestIterator.next();
      Iterator boxIterator = request.getBoxes().iterator();
      while (boxIterator.hasNext()) {
        RequestBoxDto box = (RequestBoxDto)boxIterator.next();
        if (boxId.equalsIgnoreCase(box.getBoxId()) && !box.isShipped()) {
          returnValue = true;
        }
      }
    }
    return returnValue;
  }

  /**
   * Return true if there is a logical repository with the specified id.
   * 
   * @param repositoryId The primary key of the logical repository.
   * @return true if there is a logical repository with the specified id.
   */
  public static boolean isExistingLogicalRepository(String repositoryId) {
    String queryString = "select 1 from ard_logical_repos where id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setBigDecimal(1, new BigDecimal(repositoryId));
      rs = ApiFunctions.queryDb(pstmt, con);

      return rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, keep compiler happy
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /**
   * Given a list of {@link PolicyData} objects, return a string containing the comma-separated
   * names of those policies, or an empty string if the input list is empty.
   * 
   * @param policies The list of {@link PolicyData} objects.
   * @return The comma-separated policy names.
   */
  public static String getPolicyNames(List policies) {
    if (ApiFunctions.isEmpty(policies)) {
      return "";
    }

    StringBuffer policyNames = new StringBuffer(20 * policies.size());
    boolean needSeparator = false;
    Iterator iter = policies.iterator();
    while (iter.hasNext()) {
      PolicyData policy = (PolicyData) iter.next();
      String policyName = policy.getPolicyName();
      if (needSeparator) {
        policyNames.append(", ");
      }
      else {
        needSeparator = true;
      }
      policyNames.append(policyName);
    }
    return policyNames.toString();
  }

  /**
   * Create a BTX history record describing the creation of a new sample.
   * 
   * @param securityInfo the SecurityInfo describing the user who's action resulted
   *     in creating the sample.
   * @param sampleId the sample's barcode id.
   * @param sampleAlias the sample's alias.
   * @param importedYN - Y if the sample is imported, N if not
   * @param asmId the sample's ASM id.
   * @param asmPosition the sample's ASM position.
   * @param donorAccountId the id of the donor institution account that the sample came from.
   */
  public static void logSampleCreation(
    SecurityInfo securityInfo,
    String sampleId,
    String sampleAlias,
    String importedYN,
    String asmId,
    String asmPosition,
    String donorAccountId) {

    Timestamp now = new Timestamp(System.currentTimeMillis());
    BtxDetailsLogCreateSample btxDetails = new BtxDetailsLogCreateSample();
    btxDetails.setBeginTimestamp(now);
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setSampleBarcodeId(sampleId);
    btxDetails.setImportedYN(importedYN);
    btxDetails.setAsmId(asmId);
    btxDetails.setAsmPosition(asmPosition);
    btxDetails.setAccountId(donorAccountId);
    btxDetails.setSampleAlias(sampleAlias);
    Btx.perform(btxDetails, "iltds_sample_logCreateSample");
  }

  /**
   * Return true if the specified user has any local storage units
   * 
   * @param userId The id of the user
   * @return true if the specified user has any local storage units
   */
  public static boolean isUserLocaltoStorageUnit(String userId) {

    String queryString =
      "select 1 from iltds_ardais_staff staff, iltds_box_location box "
        + " where staff.ardais_staff_id = ? and "
        + " staff.location_address_id = box.location_address_id";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, userId);
      rs = ApiFunctions.queryDb(pstmt, con);

      return rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, keep compiler happy
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /**
   * Return true if the specified user is in the specified account
   * 
   * @param userId The id of the user
   * @param accountId The id of the account
   * @return true if the specified user belongs to the specified account
   */
  public static boolean isUserInAccount(String userId, String accountId) {
    
    if (ApiFunctions.isEmpty(userId) || ApiFunctions.isEmpty(accountId)) {
      return false;
    }

    String queryString =
      "select 1 from es_ardais_user where ardais_user_id = ? and ardais_acct_key = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, userId);
      pstmt.setString(2, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);

      return rs.next();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return false; // unreached, keep compiler happy
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
  }

  /**
   * Return true if a case with the specified id exists in the database.
   * 
   * @param id The case id.
   * @return True if a case with the specified id exists in the database.
   */
  public static boolean caseExists(String id) {
    if (ApiFunctions.isEmpty(id)) {
      return false;
    }

    boolean exists = false;

    String query = "SELECT 1 FROM iltds_informed_consent c WHERE c.consent_id = ?";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        exists = true;
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return exists;
  }

  /**
   * Return true if a case with the specified id was imported by the specified account.
   * 
   * @param id The case id.
   * @param id The account id.
   * @return True if a case with the specified id was imported by the specified account.
   */
  public static boolean caseImportedByAccount(String consentId, String accountId) {
    boolean returnValue = false;
    
    if (ApiFunctions.isEmpty(consentId)) {
      return returnValue;
    }

    StringBuffer sql = new StringBuffer(100);
    sql.append("SELECT 1 FROM iltds_informed_consent c WHERE c.consent_id = ?");
    sql.append(" AND c.IMPORTED_YN = 'Y' AND c.ARDAIS_ACCT_KEY = ?");
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, consentId);
      pstmt.setString(2, accountId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        returnValue = true;
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return returnValue;
  }

  /**
   * Given an ASM, return the policy associated with that ASM's case.  An exception will
   * be thrown if the case policy can't be retrieved (for example because the specified
   * ASM's case doesn't exist).
   * 
   * @param asm the ASM.
   * @return the policy.
   */
  public static PolicyData getSamplePolicyFromAsm(AsmAccessBean asm) throws Exception {
    // We need to use an ASM access bean here since in some cases the ASM record won't
    // exist in the database yet when this is called, so we can't just do a database
    // query based on an asm id.  For example, can happen when this is called from
    // ASMOperationBean.createAsmAndUpdateSamples.

    String asmId = ((AsmKey) asm.__getKey()).asm_id;
    String consentId = asm.getConsent_id();

    if (ApiFunctions.isEmpty(consentId)) {
      throw new ApiException("Could not determine case policy for ASM " + asmId);
    }
    PolicyData policy = getPolicyForConsent(consentId);

    return policy;
  }

  /**
   * Given a case id, return the policy associated with that case.  An exception will
   * be thrown if the case policy can't be retrieved.
   * 
   * @param case id the id of the case
   * @return the policy.
   */
  public static PolicyData getPolicyForConsent(String consentId) {

    if (ApiFunctions.isEmpty(consentId)) {
      throw new ApiException("Consent id was not specified");
    }

    PolicyData policy = null;

    String query =
      "select c.policy_id " + "from iltds_informed_consent c " + "where c.consent_id = ? ";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);

      pstmt.setString(1, consentId);

      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        String policyId = rs.getBigDecimal(1).toString();
        policy = PolicyUtils.getPolicyData(policyId);
      }
      else {
        throw new ApiException("Could not determine policy for case " + consentId);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return policy;
  }


  /**
   * Given a protocol name, return the policy associated with that protocol.  An exception will
   * be thrown if the irb policy can't be retrieved.
   * 
   * @param irbProtocol the protocol name
   * @return the policy.
   */
  public static PolicyData getPolicyForIrb(String irbProtocolName) throws Exception {

    if (ApiFunctions.isEmpty(irbProtocolName)) {
      throw new ApiException("IRB Protocol was not specified");
    }

    PolicyData policy = null;

    String query =
      "select " + DbConstants.IRB_POLICY_ID + " from "
       + DbConstants.TABLE_IRB  + " where " + DbConstants.IRB_PROTOCOL + " = ? ";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);

      pstmt.setString(1, irbProtocolName);

      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        String policyId = rs.getBigDecimal(1).toString();
        policy = PolicyUtils.getPolicyData(policyId);
      }
      else {
        throw new ApiException("Could not determine policy for protocol " + irbProtocolName);
      }
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return policy;
  }
  
  public static List getArdaisStaffInAccount(String user, String account) {
    List returnValue = new ArrayList();
    try {
      ArdaisstaffAccessBean myStaff = new ArdaisstaffAccessBean();
      AccessBeanEnumeration enum1 =
        (AccessBeanEnumeration) myStaff.findLocByUserProf(user, account);
      if (enum1.hasMoreElements()) {
        myStaff = (ArdaisstaffAccessBean) enum1.nextElement();
        GeolocationKey key = myStaff.getGeolocationKey();

        AccessBeanEnumeration staffList = null;
        staffList =
          (AccessBeanEnumeration) myStaff.findArdaisstaffByGeolocation(key);

        while (staffList.hasMoreElements()) {
          myStaff = (ArdaisstaffAccessBean) staffList.nextElement();
          ArdaisStaff staff = new ArdaisStaff(myStaff);
          returnValue.add(staff);
        }
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    return returnValue;
  }

  public static LegalValueSet getArdaisStaffInAccountAsLegalValueSet(SecurityInfo securityInfo) {
    LegalValueSet lvs = new LegalValueSet();
    try {
      ArdaisstaffAccessBean staffBean = new ArdaisstaffAccessBean();
      AccessBeanEnumeration staffList =
        (AccessBeanEnumeration) staffBean.findArdaisstaffByGeolocation(
          new GeolocationKey(securityInfo.getUserLocationId()));
      while (staffList.hasMoreElements()) {
        ArdaisStaff staff = new ArdaisStaff((ArdaisstaffAccessBean) staffList.nextElement());
        lvs.addLegalValue(staff.getStaffId(), staff.getFullName());
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    return lvs;
  }

  /**
   * Given a case id, return the account id associated with that case.
   * 
   * @param case id the id of the case
   * @return the account id.
   */
  public static String getAccountForCase(String caseId) {
    String returnValue = null;

    if (ApiFunctions.isEmpty(caseId)) {
      return returnValue;
    }

    StringBuffer query = new StringBuffer();
    query.append("SELECT case.ARDAIS_ACCT_KEY AS CASE_ACCOUNT \n");
    query.append("FROM   ILTDS_INFORMED_CONSENT case             \n");
    query.append("WHERE  case.CONSENT_ID = ?                     \n");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());

      pstmt.setString(1, caseId);

      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        returnValue = rs.getString("CASE_ACCOUNT");
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return returnValue;
  }

  /** 
   * Method to get the samples for a case.
   * 
   * @param consent - the case for which samples should be retrived
   * @return ArrayList - an ArrayList of SampleAccessBeans
   */
  public static ArrayList getSamplesForConsent(ConsentAccessBean consent) {
    ArrayList sampleList = new ArrayList();
    try {
      if (consent != null) {
        AsmformAccessBean asmForm = new AsmformAccessBean();
        //get the asm forms for the case
        AccessBeanEnumeration asmFormEnum =
          (AccessBeanEnumeration) asmForm.findAsmformByConsent((ConsentKey) consent.__getKey());
        ArrayList asmList = new ArrayList();
        //for each asm form, get the asm ids associated with it
        while (asmFormEnum.hasMoreElements()) {
          Vector asmIds =
            FormLogic.genASMEntryIDs(
              (
                (AsmformKey) ((AsmformAccessBean) asmFormEnum.nextElement())
                  .__getKey())
                  .asm_form_id);
          for (int q = 0; q < asmIds.size(); q++) {
            asmList.add(new AsmKey((String) asmIds.get(q)));
          }
        }
        SampleAccessBean sample = new SampleAccessBean();
        //for each asm id, get the samples in it.
        for (int k = 0; k < asmList.size(); k++) {
          Enumeration sampleTempEnum = sample.findSampleByAsm((AsmKey) asmList.get(k));
          while (sampleTempEnum.hasMoreElements()) {
            sampleList.add((SampleAccessBean) sampleTempEnum.nextElement());
          }
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    return sampleList;
  }

  /** 
   * Method to get the samples for a case.
   * 
   * @param consentId - the case id for which samples should be retrived
   * @return List - a List of sample ids
   */
  public static ArrayList getSamplesForConsent(String consentId) {
    ArrayList sampleList = new ArrayList();
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement("SELECT sample_barcode_id FROM iltds_sample WHERE consent_id = ?");
      pstmt.setString(1, consentId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        sampleList.add(rs.getString("sample_barcode_id"));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return sampleList;
  }

  /**
   * Method to apply the policy under which a sample was collected to that sample.
   * @param sample  The SampleAccessBean to which the policy should be applied
   * @param action  The aspect of the policy to apply to the Sample.  See 
   * <code>IltdsUtils.APPLY_POLICY_FOR_SALES_STATUS <code> etc for valid values
   */
  public static void applyPolicyToSample(SampleAccessBean sample, String action) {
    //make sure sample is non-null
    if (sample == null) {
      throw new ApiException("Sample was not specified");
    }
    //make sure action is recognized
    if (!VALID_APPLY_POLICY_ACTIONS.contains(action)) {
      throw new ApiException("Specified action (" + action + ") is unrecognized.");
    }
    AsmAccessBean asm = null;
    ConsentAccessBean consent = null;
    PolicyData policy = null;
    try {
      asm = sample.getAsm();
      if (asm != null) {
        consent = getConsentFromAsm(asm);
        policy = getPolicyForConsent(((ConsentKey) consent.__getKey()).consent_id);
      }
      applyPolicyToSample(sample, asm, consent, policy, action);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Method to apply the policy under which a case was consented to every sample 
   * in that case.
   * @param consent  The ConsentAccessBean representing the case.
   * @param action  The aspect of the policy to apply to the samples.  See 
   * <code>IltdsUtils.APPLY_POLICY_FOR_SALES_STATUS <code> etc for valid values
   */
  public static void applyPolicyToSamplesInCase(ConsentAccessBean consent, String action) {
    //make sure a consent was provided
    if (consent == null) {
      throw new ApiException("Consent cannot be null");
    }
    //make sure action is recognized
    if (!VALID_APPLY_POLICY_ACTIONS.contains(action)) {
      throw new ApiException("Specified action (" + action + ") is unrecognized.");
    }
    try {
      //get the samples in the case
      ArrayList samples = getSamplesForConsent(consent);
      //get the policy for the case
      PolicyData policy = getPolicyForConsent(((ConsentKey) consent.__getKey()).consent_id);
      //for each sample in the case, call the helper method that does all the work
      Iterator iterator = samples.iterator();
      while (iterator.hasNext()) {
        SampleAccessBean sample = (SampleAccessBean) iterator.next();
        applyPolicyToSample(sample, sample.getAsm(), consent, policy, action);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /** Method to retrieve the consent for an ASM
   * 
   * @param asm
   * @return ConsentAccessBean the consent
   */
  public static ConsentAccessBean getConsentFromAsm(AsmAccessBean asm) {
    //make sure asm is non-null
    if (asm == null) {
      throw new ApiException("Asm was not specified");
    }
    ConsentAccessBean consent = null;
    try {
      AccessBeanEnumeration enum1 =
        (AccessBeanEnumeration) new ConsentAccessBean().findByConsentID(asm.getConsent_id());
      while (enum1.hasMoreElements()) {
        consent = (ConsentAccessBean) enum1.nextElement();
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    return consent;
  }

  public static List findConsentIdsFromCustomerId(String customerId) {
    
    if (ApiFunctions.isEmpty(customerId)) {
      throw new ApiException("customerId cannot be null");
    }
    
    List returnValue = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement("select consent_id from iltds_informed_consent c where upper(c.customer_id) = ?");
      pstmt.setString(1, customerId.toUpperCase());
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        returnValue.add(rs.getString("consent_id"));
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return returnValue;
  }

  public static ConsentData findConsentFromCustomerId(String customerId, String accountId) {

      if (ApiFunctions.isEmpty(customerId)) {
        throw new ApiException("customerId cannot be null");
      }
      if (ApiFunctions.isEmpty(accountId)) {
        throw new ApiException("accountId cannot be null");
      }

      ConsentData consent = null;

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement("select * from iltds_informed_consent c where upper(c.customer_id) = ? and ardais_acct_key = ?");
        pstmt.setString(1, customerId.toUpperCase());
        pstmt.setString(2, accountId);
        rs = ApiFunctions.queryDb(pstmt, con);
        if (rs.next()) {
          consent = new ConsentData(DbUtils.getColumnNames(rs), rs);
        }
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
      return consent;
    }
  
  public static List findSampleIdsFromCustomerId(String customerId, boolean annotatedAndImportedSamplesOnly) {
    List returnValue = new ArrayList();
    List samples = findSamplesFromCustomerId(customerId, annotatedAndImportedSamplesOnly);
    Iterator sampleIterator = samples.iterator();
    while (sampleIterator.hasNext()) {
      returnValue.add(((SampleData)sampleIterator.next()).getSampleId());
    }
    return returnValue;
  }
  
  public static List findSamplesFromCustomerId(String customerId, boolean annotatedAndImportedSamplesOnly) {

    if (ApiFunctions.isEmpty(customerId)) {
      throw new ApiException("customerId cannot be null");
    }
  
    List returnValue = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuffer query = new StringBuffer(200);
    if (annotatedAndImportedSamplesOnly) {
      //only return those samples that have been imported and annotated (i.e. have imported_yn = 'Y' and
      //have a non-null consent_id)
      query.append("select s.*, c.customer_id case_alias, d.customer_id donor_alias");
      query.append(" from iltds_sample s, iltds_informed_consent c, pdc_ardais_donor d");
      query.append(" where s.imported_yn = 'Y'");
      query.append(" and s.consent_id is not null");
      query.append(" and upper(s.customer_id) = ? ");
      query.append(" and c.CONSENT_ID = s.CONSENT_ID");
      query.append(" and d.ARDAIS_ID = s.ARDAIS_ID");
      query.append(" order by sample_barcode_id");
    }
    else {
      //return any samples that have a non-null customer_id, regardless of whether they are imported
      //and/or annotated
      query.append("select s.* from iltds_sample s where upper(s.customer_id) = ?");
      query.append(" order by sample_barcode_id");
    }
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, customerId.toUpperCase());
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        SampleData sample = new SampleData(DbUtils.getColumnNames(rs), rs);
        if (annotatedAndImportedSamplesOnly) {
          sample.setConsentAlias(rs.getString("case_alias"));
          sample.setDonorAlias(rs.getString("donor_alias"));
        }
        returnValue.add(sample);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return returnValue;
  }
  
  public static List findBoxScannedSamplesFromCustomerId(String customerId, String accountId) {

    if (ApiFunctions.isEmpty(customerId)) {
      throw new ApiException("customerId cannot be null");
    }
    if (ApiFunctions.isEmpty(accountId)) {
      throw new ApiException("accountId cannot be null");
    }
  
    List returnValue = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuffer query = new StringBuffer(200);
    query.append("select s.* from iltds_sample s where upper(s.customer_id) = ?");
    query.append(" and upper(s.ardais_acct_key) = ?");
    query.append(" and s.asm_id is null");
    query.append(" order by sample_barcode_id");
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, customerId.toUpperCase());
      pstmt.setString(2, accountId.toUpperCase());
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        SampleData sample = new SampleData(DbUtils.getColumnNames(rs), rs);
        returnValue.add(sample);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return returnValue;
  }
  
    public static List findSamplesFromCustomerId(String customerId, String accountId) {

      if (ApiFunctions.isEmpty(customerId)) {
        throw new ApiException("customerId cannot be null");
      }
      if (ApiFunctions.isEmpty(accountId)) {
        throw new ApiException("accountId cannot be null");
      }
    
      List returnValue = new ArrayList();

      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      //only return those samples that have been imported and annotated (i.e. have imported_yn = 'Y' and
      //have a non-null consent_id)
      StringBuffer query = new StringBuffer(200);
      query.append("select s.*, c.customer_id case_alias, d.customer_id donor_alias");
      query.append(" from iltds_sample s, iltds_informed_consent c, pdc_ardais_donor d");
      query.append(" where s.imported_yn = 'Y'");
      query.append(" and s.consent_id is not null");
      query.append(" and upper(s.customer_id) = ? ");
      query.append(" and s.ardais_acct_key = ?");
      query.append(" and c.CONSENT_ID = s.CONSENT_ID");
      query.append(" and d.ARDAIS_ID = s.ARDAIS_ID");
      query.append(" order by sample_barcode_id");
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(query.toString());
        pstmt.setString(1, customerId.toUpperCase());
        pstmt.setString(2, accountId);
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          SampleData sample = new SampleData(DbUtils.getColumnNames(rs), rs);
          sample.setConsentAlias(rs.getString("case_alias"));
          sample.setDonorAlias(rs.getString("donor_alias"));
          returnValue.add(sample);
        }
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
      return returnValue;
    }
  
  /**
   * Finds the list of ConsentData with matching customerId. If the parameter customerId
   * contains * (wild character), the search will be performed with LIKE operator.
   * @param customerId may contain * for wildcard search
   * @param accountId
   * @return a list of objects with type ConsentData
   */
  public static List findConsentsFromLikeCustomerId(String customerId, String accountId) {

    if (ApiFunctions.isEmpty(customerId)) {
      throw new ApiException("customerId cannot be null");
    }
    if (ApiFunctions.isEmpty(accountId)) {
      throw new ApiException("accountId cannot be null");
    }

    ConsentData consent = null;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean hasWildCharacter = false;
    StringBuffer selectSql = new StringBuffer(128);
    List returnValue = new ArrayList();
    
    if (customerId.indexOf('*') >= 0) {
      hasWildCharacter = true;  
    }

    try {
      con = ApiFunctions.getDbConnection();
      selectSql.append("select * from iltds_informed_consent c where upper(c.customer_id)");
      if (hasWildCharacter) {
        selectSql.append(" LIKE ");  
      }
      else {
        selectSql.append(" = ");  
      }
      selectSql.append("?");
      if (hasWildCharacter) {
        selectSql.append(" ESCAPE '/'");  
      }
      selectSql.append(" and ardais_acct_key = ?");
      
      pstmt = con.prepareStatement(selectSql.toString());
      if (hasWildCharacter) {
        pstmt.setString(1, DbUtils.convertLikeSpecialChars(customerId.toUpperCase()));
      }
      else {
        pstmt.setString(1, customerId.toUpperCase());
      }
      pstmt.setString(2, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        consent = new ConsentData(DbUtils.getColumnNames(rs), rs);
        returnValue.add(consent);
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return returnValue;
  }
  
  public static List findSamplesFromLikeCustomerId(String customerId, String accountId) {

    if (ApiFunctions.isEmpty(customerId)) {
      throw new ApiException("customerId cannot be null");
    }
    if (ApiFunctions.isEmpty(accountId)) {
      throw new ApiException("accountId cannot be null");
    }
    
    List returnValue = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean hasWildCharacter = false;
    StringBuffer selectSql = new StringBuffer(128);
    
    if (customerId.indexOf('*') >= 0) {
      hasWildCharacter = true;  
    }
    try {
      con = ApiFunctions.getDbConnection();
      //only return those samples that have been imported and annotated (i.e. have imported_yn = 'Y' and
      //have a non-null consent_id)
      selectSql.append("select * from iltds_sample s where imported_yn = 'Y' and consent_id is not null and upper(customer_id)");
      if (hasWildCharacter) {
        selectSql.append(" LIKE ");  
      }
      else {
        selectSql.append(" = ");  
      }
      selectSql.append("?");
      if (hasWildCharacter) {
        selectSql.append(" ESCAPE '/'");  
      }
      selectSql.append(" and ardais_acct_key = ? order by sample_barcode_id");

      pstmt = con.prepareStatement(selectSql.toString());
      if (hasWildCharacter) {
        pstmt.setString(1, DbUtils.convertLikeSpecialChars(customerId.toUpperCase()));
      }
      else {
        pstmt.setString(1, customerId.toUpperCase());
      }
      pstmt.setString(2, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        returnValue.add(new SampleData(DbUtils.getColumnNames(rs), rs));
      }
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return returnValue;
  }

  //private method invoked by the public methods (applyPolicyToSample, applyPolicyToSamplesInCase,
  //and applyPolicyToSamplesInAsm above.  This method assumes that all error checking has
  //been done and that all required objects are provided via the methods above.
  private static void applyPolicyToSample(
    SampleAccessBean sample,
    AsmAccessBean asm,
    ConsentAccessBean consent,
    PolicyData policy,
    String action) {
    try {
      if (APPLY_POLICY_FOR_SALES_STATUS.equalsIgnoreCase(action)) {
        //only update the sales status if the sample doesn't already have a sales status
        if (ApiFunctions.isEmpty(sample.getSales_status())) {
          //if the consent and/or policy are null, we cannot determine if the sales status
          //should be set so return
          if (consent == null || policy == null) {
            return;
          }
          //sample sales_Status values are used to determine whether a sample should be
          //visible in sample selection result sets, so this code is tightly coupled with
          //that logic - see BtxPerformerSampleSelection.addImplicitTissueFilterDonorInstitution 
          //for details.  WE DON'T WANT TO RETURN ANY NON-IMPORTED SAMPLES THAT BELONG TO A CASE
          //THAT HAS NOT BEEN CASE RELEASED (AND VERIFIED IF LINKED), SO DO NOT CHANGE THE CODE
          //BELOW WITHOUT ENSURING THIS IS STILL THE CASE (PROBABLY BY CHANGING THE CODE IN
          //BtxPerformerSampleSelection.addImplicitTissueFilterDonorInstitution)!!!!!
          boolean setSalesStatus = false;

          if ("Y".equalsIgnoreCase(policy.getVerifyRequired())) {
            if (consent.getForm_verified_datetime() == null) {
              if ("Y".equalsIgnoreCase(consent.getLinked())) {
                return;
              }               
            }
          }

          if ("Y".equalsIgnoreCase(policy.getReleaseRequired())) {
            if (consent.getConsent_release_datetime() == null) {
              return;
            }
          }
                    
          //otherwise set the sales status
          setSalesStatus = true;

          //if we've determined that it's ok to set the sales status, do so
          if (setSalesStatus) {
            SampleKey sampleKey = (SampleKey) sample.__getKey();
            SamplestatusAccessBean status = new SamplestatusAccessBean();
            status.setInit_argSample(sampleKey);
            status.setInit_argStatus_type_code(FormLogic.SMPL_GENRELEASED);
            status.setInit_argSample_status_datetime(new Timestamp(new java.util.Date().getTime()));
            status.commitCopyHelper();
          }
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /*
   * Return a list of the box ids of the boxes that the specified samples
   * are currently in, but don't include newBoxID in the list.  The
   * returned list isn't in any particular order, and has no duplicates.
   * 
   * @param sampleIds the list of sample ids
   * @param newBoxId the box id to exclude form the list
   * @return the list of box ids
   */
  public static List getSampleBoxIds(List sampleIds, String newBoxId) throws Exception {

    List boxIds = new Vector();

    Iterator iterator = sampleIds.iterator();
    while (iterator.hasNext()) {
      String sampleId = (String)iterator.next();
      try {
        SampleAccessBean sample = new SampleAccessBean(new SampleKey(sampleId));
        String oldBoxId = sample.getSamplebox_box_barcode_id();
        if (!ApiFunctions.isEmpty(oldBoxId)) {
          if (!newBoxId.equals(oldBoxId)) {
            if (!boxIds.contains(oldBoxId)) {
              boxIds.add(oldBoxId);
            }
          }
        }
      }
      catch (ObjectNotFoundException e) {
        // The sample doesn't exist, so it isn't in a box, so there's
        // nothing to do here.  What happens if someone tries to check
        // out samples that don't exist?  Does some other part of the
        // code result in a nice informative message to the user?
        // TODO: Figure this out.
      }
    }
    return boxIds;
  }

  public static Vector processEmptyBoxLocations(List boxIds, List sampleIds, String userID) {
    Vector results = new Vector();

    // Do not want to run query if the box id list or the sample id list is empty.
    // This was causing a ORA-00921: unexpected end of SQL command.
    if (ApiFunctions.isEmpty(boxIds) || ApiFunctions.isEmpty(sampleIds)) {
      return results;
    }

    Vector temp = new Vector();

    PreparedStatement pstmt;
    Connection con = null;
    
    // SELECT bl.box_barcode_id, bl.location_address_id, bl.room_id, bl.unit_name, bl.drawer_id, bl.slot_id 
    // FROM iltds_box_location bl 
    // WHERE bl.box_barcode_id IN  ('BXnnn1','BXnnn2',...)
    // AND NOT EXISTS 
    //   (SELECT 1 FROM iltds_sample s
    //    WHERE s.box_barcode_id = bl.box_barcode_id
    //    AND s.sample_barcode_id NOT IN ('FRnnn1','FRnnn2',...))

    String queryString = "SELECT bl.box_barcode_id, bl.location_address_id, bl.room_id, bl.unit_name, bl.drawer_id, bl.slot_id "
      + "FROM iltds_box_location bl "
      + "WHERE bl.box_barcode_id IN " + ApiFunctions.makeBindParameterList(boxIds.size())
      + " AND NOT EXISTS "
      + " (SELECT 1 FROM iltds_sample s"
      + " WHERE s.box_barcode_id = bl.box_barcode_id"
      + " AND s.sample_barcode_id NOT IN " + ApiFunctions.makeBindParameterList(sampleIds.size()) + ")";

    ResultSet rs;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      int index = 1;
      for (int i = 0; i < boxIds.size(); i++) {
        pstmt.setString(index++, (String)boxIds.get(i));
      }
      
      for (int i = 0; i < sampleIds.size(); i++) {
        pstmt.setString(index++, (String)sampleIds.get(i));
      }

      String emptyBoxId;
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        temp = new Vector();
        emptyBoxId = rs.getString(1);

        try {
          SampleboxAccessBean box = new SampleboxAccessBean(new SampleboxKey(emptyBoxId));
          box.setBox_status(FormLogic.BX_CHECKEDOUT);
          box.setBox_check_out_reason(FormLogic.BOX_EMPTY);
          box.setBox_check_out_date(new java.sql.Timestamp(System.currentTimeMillis()));
          box.setBox_checkout_request_staff_id(userID); 
          box.commitCopyHelper();
        }
        catch (ObjectNotFoundException onfe) {
          throw new ApiException(
            "Box " + emptyBoxId + " does not exist in system: " + onfe.getLocalizedMessage());
        }
        catch (Exception e) {
          throw new ApiException(
            "Error checking out box " + emptyBoxId + ": " + e.getLocalizedMessage());
        }
        removeEmptyBoxLocations(emptyBoxId);
        temp.add(emptyBoxId);


        BTXBoxLocation emptyBoxLoc = new BTXBoxLocation();
        emptyBoxLoc.setLocationAddressID(rs.getString(2));
        emptyBoxLoc.setRoomID(rs.getString(3));
        emptyBoxLoc.setUnitName(rs.getString(4));
        emptyBoxLoc.setDrawerID(rs.getString(5));
        emptyBoxLoc.setSlotID(rs.getString(6));
        
        temp.add(emptyBoxLoc);
        results.add(temp);
      }
      rs.close();

    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    catch (ApiException e) {
      e.printStackTrace();
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }

    return results;
  }

  private static void removeEmptyBoxLocations(String boxId) {
    Connection con = null;
    PreparedStatement pstmt = null;

    try {
      con = ApiFunctions.getDbConnection();

      String updateBox =
        "UPDATE iltds_box_location bl SET "
        + "bl.available_ind = 'Y', "
        + "bl.box_barcode_id = NULL "
        + "WHERE bl.box_barcode_id = ? ";

      pstmt = con.prepareStatement(updateBox);
      pstmt.setString(1, boxId);
      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  /**
   * Get a list of all available shipping partners by account.
   * @return the list of all available shipping partners for this account.
   */
  public static List getAllAvailableShippingPartnersByAccount(String accountId) {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT ");
      query.append(DbAliases.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(".");
      query.append(DbConstants.LOCATION_ADDRESS_ID);
      query.append(" AS ");
      query.append(ShippingPartnerDto.SHIPPING_PARTNER_ID);
      query.append(", ");
      query.append(DbAliases.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(".");
      query.append(DbConstants.LOCATION_NAME);
      query.append(" AS ");
      query.append(ShippingPartnerDto.SHIPPING_PARTNER_NAME);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(" ");
      query.append(DbAliases.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(".");
      query.append(DbConstants.LOCATION_ADDRESS_ID);
      query.append(" = 'OUTOFNETWORK'");
      query.append(" UNION");
      query.append(" SELECT ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_PRIMARY_LOCATION);
      query.append(", ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_NAME);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_ARDAIS_ACCOUNT);
      query.append(" ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_TYPE);
      query.append(" IN ('");
      query.append(Constants.ACCOUNT_TYPE_SYSTEM_OWNER);
      query.append("','");
      query.append(Constants.ACCOUNT_TYPE_DONOR_AND_CONSUMER);
      query.append("')");
      query.append(" AND ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_PRIMARY_LOCATION);
      query.append(" IS NOT NULL ");
      query.append(" AND ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_KEY);
      query.append(" <> ? ORDER BY ");
      query.append(ShippingPartnerDto.SHIPPING_PARTNER_NAME);

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        ShippingPartnerDto spdto = new ShippingPartnerDto();
        spdto.populateFromResultSet(columns, rs);
        result.add(spdto);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }

  /**
   * Get a list of all assigned shipping partners by account.
   * @return the list of all assigned shipping partners for this account.
   */
  public static List getAssignedShippingPartnersByAccount(String accountId) {
    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      // SELECT igl.location_address_id AS shipping_partner_id, 
      // decode(account.ardais_acct_company_desc, NULL, igl.location_name, account.ardais_acct_company_desc) AS shipping_partner_name
      // FROM iltds_shipping_partners isp, es_ardais_account account, iltds_geography_location igl
      // WHERE isp.destination_id = igl.location_address_id
      // AND igl.location_address_id = account.primary_location (+)
      // AND isp.ardais_acct_key = 'DI00000002'
      // ORDER BY shipping_partner_name

      StringBuffer query = new StringBuffer(256);
      query.append("SELECT ");
      query.append(DbAliases.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(".");
      query.append(DbConstants.LOCATION_ADDRESS_ID);
      query.append(" AS ");
      query.append(ShippingPartnerDto.SHIPPING_PARTNER_ID);
      query.append(", decode(");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_NAME);
      query.append(", NULL, ");
      query.append(DbAliases.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(".");
      query.append(DbConstants.LOCATION_NAME);
      query.append(", ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_NAME);
      query.append(") AS ");
      query.append(ShippingPartnerDto.SHIPPING_PARTNER_NAME);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_ILTDS_SHIPPING_PARTNERS);
      query.append(" ");
      query.append(DbAliases.TABLE_ILTDS_SHIPPING_PARTNERS);
      query.append(", ");
      query.append(DbConstants.TABLE_ARDAIS_ACCOUNT);
      query.append(" ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(", ");
      query.append(DbConstants.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(" ");
      query.append(DbAliases.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(" WHERE ");
      query.append(DbAliases.TABLE_ILTDS_SHIPPING_PARTNERS);
      query.append(".");
      query.append(DbConstants.SHIPPING_PARTNER_DESTINATION_ID);
      query.append(" = ");
      query.append(DbAliases.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(".");
      query.append(DbConstants.LOCATION_ADDRESS_ID);
      query.append(" AND ");
      query.append(DbAliases.TABLE_ILTDS_GEOGRAPHY_LOCATION);
      query.append(".");
      query.append(DbConstants.LOCATION_ADDRESS_ID);
      query.append(" = ");
      query.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      query.append(".");
      query.append(DbConstants.ACCOUNT_PRIMARY_LOCATION);
      query.append(" (+) AND ");
      query.append(DbAliases.TABLE_ILTDS_SHIPPING_PARTNERS);
      query.append(".");
      query.append(DbConstants.SHIPPING_PARTNER_ARDAIS_ACCT_KEY);
      query.append(" = ? ORDER BY ");
      query.append(ShippingPartnerDto.SHIPPING_PARTNER_NAME);

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        ShippingPartnerDto spdto = new ShippingPartnerDto();
        spdto.populateFromResultSet(columns, rs);
        result.add(spdto);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }

  /**
   * Get a list of unselected shipping partners by by account.
   * @return the list of available shipping partners for this account.
   */
  public static List getAvailableShippingPartnersByAccount(String accountId, List assignedShippingPartners) {

    // Get all shipping partners that are available for this account.
    List allAvailableShippingPartners = IltdsUtils.getAllAvailableShippingPartnersByAccount(accountId);

    // Determine the available shipping partner list by removing from the all available list the ones that
    // are in the assigned list.
    List availableShippingPartners = IltdsUtils.getAvailableShippingPartners(allAvailableShippingPartners, assignedShippingPartners);

    return availableShippingPartners;
  }

  /**
   * Get a list of unselected shipping partners by by account.
   * @return the list of available shipping partners for this account.
   */
  public static List getAvailableShippingPartners(List allAvailableShippingPartners, List assignedShippingPartners) {

    // Determine the available shipping partner list by removing from the all available list the ones that
    // are in the assigned list.
    List availableShippingPartners = new ArrayList();
    for (int i = 0; i < allAvailableShippingPartners.size(); i++) {
      boolean found = false;
      ShippingPartnerDto availableSPDto = (ShippingPartnerDto)allAvailableShippingPartners.get(i);
      for (int j = 0; j < assignedShippingPartners.size(); j++) {
        ShippingPartnerDto assignedSPDto = (ShippingPartnerDto)assignedShippingPartners.get(j);
        if (assignedSPDto.getShippingPartnerId().equals(availableSPDto.getShippingPartnerId())) {
          found = true;
          break;
        }
      }
      if (!found) {
        availableShippingPartners.add(availableSPDto);
      }
    }
    return availableShippingPartners;
  }

  /**
   * 
   * @param allAvailableShippingPartners
   * @param ids
   * @return
   */
  public static List getShippingPartnersByIds(List allAvailableShippingPartners, List ids) {
    List shippingPartners = new ArrayList();
    for (int i = 0; i < allAvailableShippingPartners.size(); i++) {
      ShippingPartnerDto availableSPDto = (ShippingPartnerDto)allAvailableShippingPartners.get(i);
      for (int j = 0; j < ids.size(); j++) {
        String shippingPartnerId = (String)ids.get(j);
        if (shippingPartnerId.equals(availableSPDto.getShippingPartnerId())) {
          shippingPartners.add(availableSPDto);
          break;
        }
      }
    }
    return shippingPartners;
  }
  
  /**
   * Given a geolocation id (key into ILTDS_GEOGRAPHY_LOCATION), return an address id
   * that describes the ship-to address corresponding to that location.  Address ids are
   * keys into ARDAIS_ADDRESS.
   * 
   * <p>Note that while the ILTDS_GEOGRAPHY_LOCATION table does contain some address
   * information in its columns, this is NOT the address to ship to.  Shipping addresses
   * are stored in ARDAIS_ADDRESS.  The relationship between ILTDS_GEOGRAPHY_LOCATION
   * and ARDAIS_ADDRESS is tenuous and depends on the assumption (valid as of this
   * writing in 2/2004) that every geolocation in ILTDS_GEOGRAPHY_LOCATION is the
   * ES_ARDAIS_ACCOUNT.PRIMARY_LOCATION for some account.  We get the ARDAIS_ADDRESS
   * for a given geolocation by finding the account with that primary location and then
   * looking into ARDAIS_ADDRESS to find the address of type 'Ship-To' for that account. 
   * 
   * @param geoLocationId The geolocation key.
   * @return The ship-to address id.
   */
  public static String getShipToAddressIdForGeolocation(String geoLocationId) {
    String query =
      "SELECT addr.address_id"
        + " FROM es_ardais_account acct, ardais_address addr"
        + " WHERE acct.primary_location = ?"
        + "   AND acct.ardais_acct_key = addr.ardais_acct_key"
        + "   AND addr.address_type = 'Ship To'";
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String addressId = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, geoLocationId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        addressId = rs.getString("address_id");
      }
      else {
        throw new ApiException(
          "Could not find account Ship To address corresponding to location " + geoLocationId);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return addressId;
  }

  /**
   * Return the sample type set determined by the sampleId list passed in. Please note that
   * for legacy sample ids, the sample type can be determined by the prefix. This is needed because
   * the samples may not yet exist in the database but we need to know the sample type.
   * 
   * @param sampleIds The sample id list to get the sample types for.
   * 
   * @return The sample type set.
   */
  public static Set getSampleTypesBySampleIds(List sampleIds) {
    Set result = new HashSet();
    
    if (ApiFunctions.isEmpty(sampleIds)) {
      return result;
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      
      // SELECT DISTINCT sample_type_cid FROM iltds_sample
      // WHERE sample_barcode_id IN ('FR000001E8', 'FR000001ED')
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT DISTINCT ");
      query.append(DbConstants.SAMPLE_SAMPLE_TYPE_CID);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_SAMPLE);
      query.append(" WHERE ");
      query.append(DbConstants.SAMPLE_ID);
      query.append(" IN ");
      query.append(ApiFunctions.makeBindParameterList(sampleIds.size()));

      pstmt = con.prepareStatement(query.toString());
      ApiFunctions.bindBindParameterList(pstmt, 1, sampleIds);

      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        result.add(rs.getString(1));
      }
      
      // Iterate through the sampleId list and get the sample types of those samples that
      // where not found in the database and have the prefix of FR or PA found in legacy
      // style sample ids.
      Iterator iterator = sampleIds.iterator();
      while (iterator.hasNext()) {
        String sampleId = (String)iterator.next();

        if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)) {
          result.add(ArtsConstants.SAMPLE_TYPE_FROZEN_TISSUE);
        }
        else if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {
          result.add(ArtsConstants.SAMPLE_TYPE_PARAFFIN_TISSUE);
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }
  
  /**
   * Return the sample type set by getting all the samples in the box provided.
   * 
   * @param boxId The box to get the sample type set from.
   * 
   * @return The sample type set.
   */
  public static Set getSampleTypesByBoxId(String boxId) {
    Set result = new HashSet();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      
      // SELECT DISTINCT sample_type_cid FROM iltds_sample
      // WHERE box_barcode_id = 'BX1000010002'
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT DISTINCT ");
      query.append(DbConstants.SAMPLE_SAMPLE_TYPE_CID);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_SAMPLE);
      query.append(" WHERE ");
      query.append(DbConstants.SAMPLE_BOX_BARCODE_ID);
      query.append(" = ? ");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, boxId);

      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        result.add(rs.getString(1));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }
  
  /**
   * Return the common storage type set by the account and box.  The samples in the box are used
   * to get the sample types, from there the storage types can be derived. The common storage type
   * set looks at the available storage units assigned to the account to determine the common set.
   * This allows the available storage types to be filtered down to what is actually available.
   * 
   * @param securityInfo Used in getting the storage types available for this account.
   * @param boxId The box to get the sample types from.
   * 
   * @return The common storage type set.
   */
  public static Set getCommonStorageTypesByAccountAndBoxId(SecurityInfo securityInfo, String boxId) {
    Set sampleTypes = new HashSet();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      
      // SELECT DISTINCT sample_type_cid FROM iltds_sample
      // WHERE box_barcode_id = 'BX1000010002'
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT DISTINCT ");
      query.append(DbConstants.SAMPLE_SAMPLE_TYPE_CID);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_SAMPLE);
      query.append(" WHERE ");
      query.append(DbConstants.SAMPLE_BOX_BARCODE_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, boxId);

      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        sampleTypes.add(rs.getString(1));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    Set accountStorageTypes = IltdsUtils.getStorageTypesByAccount(securityInfo);
    if (!sampleTypes.isEmpty()) {
      accountStorageTypes.retainAll(IltdsUtils.getCommonStorageTypesBySampleTypes(sampleTypes));
    }
    return accountStorageTypes;
  }
  
  /**
   * Return the common storage type set given the sample type set. This method return the mapping
   * of storage types given the sample types.
   * 
   * @param sampleTypes The sample types to get the storage types from.
   * 
   * @return The common storage types.
   */
  public static Set getCommonStorageTypesBySampleTypes(Set sampleTypes) {
    HashSet result = new HashSet();
    
    // Iterate through all the sample types. Let all of the storage types of the first sample type
    // mapping be set as the initial result set. From this result set, only retain the common
    // storage types. If no common storage type exist, the result set will be empty.
    boolean initialPass = true;
    Iterator iterator = sampleTypes.iterator();
    while (iterator.hasNext()) {
      String sampleTypeCid = (String)iterator.next();
      if (initialPass) {
        result.addAll(getStorageTypesBySampleType(sampleTypeCid));
        initialPass = false;
      }
      else {
        // Retain only the common storage types.
        result.retainAll(getStorageTypesBySampleType(sampleTypeCid));
      }
    }
    return result;
  }
  
  /**
   * Return the storage types applicable to the specified sample type.
   *  
   * @param sampleTypeCid The sample type code used to get the storage type set.
   * 
   * @return A set of cui values representing the storage types applicable to the specified sample
   * type.
   */
  public static Set getStorageTypesBySampleType(String sampleTypeCid) {
    HashSet result = new HashSet();
    
    if (ApiFunctions.isEmpty(sampleTypeCid)) {
      throw new ApiException("Sample type cannot by null.");
    }
    else if (ArtsConstants.SAMPLE_TYPE_UNKNOWN.equals(sampleTypeCid)) {
      BigrConceptList allStorageTypes = SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_STORAGE_TYPES);
      Iterator iterator = allStorageTypes.iterator();
      while (iterator.hasNext()) {
        BigrConcept storageTypeConcept = (BigrConcept) iterator.next();
        result.add(storageTypeConcept.getCode());
      }
    }
    else {
      // Get the sample type corresponding to the specified cui
      SampleType sampleType = SystemConfiguration.getSampleType(sampleTypeCid);
      if (sampleType == null) {
        throw new ApiException("Unknown sample type cui (" + sampleTypeCid + ") encountered.");
      }
      else {
        //iterate over it's list of applicable storage types
        Iterator storageTypeIterator = sampleType.getStorageTypeList().iterator();
        while (storageTypeIterator.hasNext()) {
          result.add(((StorageType)storageTypeIterator.next()).getCui());
        }
      }
    }
    return result;
  }
  
  /**
   * Return the storage type set available by the account passed in. This method looks at the
   * actually storage units configured at the account to determine the storage types available.
   * 
   * @param securityInfo Used to get the account information.
   * 
   * @return The storage type set.
   */
  public static Set getStorageTypesByAccount(SecurityInfo securityInfo) {
    Set result = new HashSet();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      
      // SELECT DISTINCT storage_type_cid FROM iltds_box_location WHERE location_address_id = 'DUKE'
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT DISTINCT ");
      query.append(DbConstants.BOX_LOCATION_STORAGE_TYPE_CID);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_BOX_LOCATION);
      query.append(" WHERE ");
      query.append(DbConstants.BOX_LOCATION_ADDRESS_ID);
      query.append(" = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, securityInfo.getUserLocationId());

      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        result.add(rs.getString(1));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }
  
  /**
   * Return the legal value set given the storage type set. This is used in conjunction with some
   * UI tags (select list).
   * 
   * @param storageTypes The storage type set.
   * 
   * @return The legal value set representation of the storage type set passed in.
   */
  public static LegalValueSet getStorageTypesAsLVS(Set storageTypes) {
    LegalValueSet allStorageTypes = 
       SystemConfiguration.getConceptList(SystemConfiguration.CONCEPT_LIST_STORAGE_TYPES).toLegalValueSet();
    LegalValueSet commonStorageTypes = new LegalValueSet();

    int count = allStorageTypes.getCount();
    for (int i = 0; i < count; i++) {
      String value = allStorageTypes.getValue(i);
      String displayValue = allStorageTypes.getDisplayValue(i);

      Iterator iterator = storageTypes.iterator();
      while (iterator.hasNext()) {
        String storageType = (String)iterator.next();
        if (storageType.equals(value)) {
          commonStorageTypes.addLegalValue(storageType, displayValue);
        }
      }
    }
    return commonStorageTypes;
  }
  
  /**
   * Return the id list of the matching box and sample type list passed in.
   * 
   * @param boxId The box id to match against.
   * @param sampleTypeList The sample type list to match against.
   * 
   * @return The id list.
   */
  public static IdList getSampleIdsByBoxIdAndSampleTypes(String boxId, List sampleTypeList) {
    IdList result = new IdList();
    
    if (ApiFunctions.isEmpty(sampleTypeList)) {
      return result;
    }

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      
      // SELECT DISTINCT sample_barcode_cid FROM iltds_sample
      // WHERE box_barcode_id = 'BX1000010002' AND sample_type_cid IN ('CA00052C', 'CA09400C', 'CA11117C')
      StringBuffer query = new StringBuffer(256);
      query.append("SELECT DISTINCT ");
      query.append(DbConstants.SAMPLE_ID);
      query.append(" FROM ");
      query.append(DbConstants.TABLE_SAMPLE);
      query.append(" WHERE ");
      query.append(DbConstants.SAMPLE_BOX_BARCODE_ID);
      query.append(" = ? AND ");
      query.append(DbConstants.SAMPLE_SAMPLE_TYPE_CID);
      query.append(" IN ");
      query.append(ApiFunctions.makeBindParameterList(sampleTypeList.size()));

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, boxId);
      ApiFunctions.bindBindParameterList(pstmt, 2, sampleTypeList);

      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        result.add(rs.getString(1));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }

  /**
   * The box contains sample types that do not share a common storage type.
   * The list below shows possible solutions as to what the user can do to alleviate the
   * problem:
   * 
   * 1. The box has samples that when divided into compatible storage types, will allow ALL
   * the samples to be processed because the system has determine that all the sample types
   * in the box have a compatable storage type in the system.
   * 
   * 2. The box has samples that when divided into compatible storage types, will allow SOME
   * of the samples to be processed because the system has determine that some of the sample
   * types in the box have a compatable storage type in the system. The samples that cannot
   * be handled need to be addressed offline.
   * 
   * 3. The box has samples that even when divided into compatible storage types, will not
   * be allowed into the system. The system has determined that none of the sample types in
   * the box can be handled. The user needs to address this issue offline.
   * 
   * Please note that there still exist the possibility that once samples have been divided
   * up into "compatable" storage types, there might not be any available slots. In which
   * case the user needs to free up space in the repository or add new storage units.
   * 
   */
  public static void determineStorageTypeWarning(SecurityInfo securityInfo, Map storageMap, boolean isHistoryRecord, StringBuffer sb) {
    IdList storableSampleIdList = (IdList)storageMap.get(IltdsUtils.STORABLE_SAMPLE_ID_LIST);
    IdList unstorableSampleIdList = (IdList)storageMap.get(IltdsUtils.UNSTORABLE_SAMPLE_ID_LIST);
    List storableSampleTypeList = (List)storageMap.get(IltdsUtils.STORABLE_SAMPLE_TYPE_LIST);
    List unstorableSampleTypeList = (List)storageMap.get(IltdsUtils.UNSTORABLE_SAMPLE_TYPE_LIST);

    if (!isIdListEmpty(storableSampleIdList) && isIdListEmpty(unstorableSampleIdList)) {
      // Warn that all of samples are storable if separated.
      sb.append("The samples in this box cannot be stored together.  Please separate the samples " +
        "by storage requirements and try again.");
    }
    else if (!isIdListEmpty(storableSampleIdList) && !isIdListEmpty(unstorableSampleIdList)) {
      // Warn that some of samples are storable if separated.
      sb.append("This box contains samples that cannot be stored at this site.  Please remove " +
        "those samples from the box and contact customer service.  The remaining samples can " +
        "then be stored.");

      determineStorageTypeWarning(securityInfo, unstorableSampleIdList, unstorableSampleTypeList, isHistoryRecord, sb);
    }
    else if (isIdListEmpty(storableSampleIdList) && !isIdListEmpty(unstorableSampleIdList)) {
      // Warn that none of the samples are storable.
      sb.append("The samples in this box cannot be stored, as this site does not have the " +
        "appropriate storage facilities.  Please contact customer service.");

      determineStorageTypeWarning(securityInfo, unstorableSampleIdList, unstorableSampleTypeList, isHistoryRecord, sb);
    }
  }
  
  private static void determineStorageTypeWarning(
    SecurityInfo securityInfo,
    IdList unstorableSampleIdList,
    List unstorableSampleTypeList,
    boolean isHistoryRecord,
    StringBuffer sb) {

    if (isHistoryRecord) {
      sb.append("  Please note that the following samples are not supported at this site: ");
      unstorableSampleIdList.appendICPHTML(sb, securityInfo);
    }
    else {
      sb.append("  Please note that the following sample types are not supported at this site: ");
      Iterator iterator = unstorableSampleTypeList.iterator();
      while (iterator.hasNext()) {
        String sampleTypeCid = (String)iterator.next();
        sb.append(GbossFactory.getInstance().getDescription(sampleTypeCid));
        if (iterator.hasNext()) {
          sb.append(", ");
        }
      }
      sb.append(".");
    }
  }
  
  /**
   * Returns <code>true</code> if the IdList is either null or empty,
   * <code>false</code> otherwise.
   *
   * @param  idList  the <code>IdList</code>
   * @return  <code>true</code> if the idList is empty
   */
  public static boolean isIdListEmpty(IdList idList) {
    return (idList == null || ApiFunctions.isEmpty(idList.getList()));
  }

  /**
   * Method to determine and set the collection and preservation date/time values for
   * samples in all modules in an ASM form
   * @param asmForm
   */
  public static void setSampleCollectionAndPreservationDates(AsmformAccessBean asmForm) {
    try{
      ArrayList asmList = new ArrayList();
      ArrayList sampleList = new ArrayList();
      Vector asmIds = FormLogic.genASMEntryIDs(((AsmformKey)asmForm.__getKey()).asm_form_id);
      for (int i=0; i<asmIds.size(); i++) {
        asmList.add(new AsmKey((String) asmIds.get(i)));
      }
      SampleAccessBean sample = new SampleAccessBean();
      //for each asm id, get the samples in it.
      for (int i=0; i<asmList.size(); i++) {
        Enumeration sampleTempEnum = sample.findSampleByAsm((AsmKey) asmList.get(i));
        while (sampleTempEnum.hasMoreElements()) {
          sampleList.add((SampleAccessBean) sampleTempEnum.nextElement());
        }
      }
      //now that we have the list of samples, update them with the appropriate collection
      //and preservation datetime values
      ConsentAccessBean consent = asmForm.getConsent();
      for (int i=0; i<sampleList.size(); i++) {
        sample = (SampleAccessBean)sampleList.get(i);
        setSampleCollectionAndPreservationDates(asmForm, consent, sample);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
  /**
   * Method to determine and set the collection and preservation date/time values for
   * a sample
   * @param asmForm
   * @param consent
   * @param sample
   */  
  public static void setSampleCollectionAndPreservationDates(AsmformAccessBean asmForm, 
                                                             ConsentAccessBean consent,
                                                             SampleAccessBean sample) {
    //return if the ASM form, consent, or sample is null
    if (asmForm == null || consent == null || sample == null) {
      return;
    }
    
    try {
      //throw an exception if the form, consent, and sample are not related
      String sampleAsmFormId = sample.getAsm().getAsm_form_id();
      String asmAsmFormId = ((AsmformKey)asmForm.__getKey()).asm_form_id;
      String asmConsentId = ((ConsentKey)asmForm.getConsent().__getKey()).consent_id;
      String consentConsentId = ((ConsentKey)consent.__getKey()).consent_id;
      if (!sampleAsmFormId.equalsIgnoreCase(asmAsmFormId) || !asmConsentId.equalsIgnoreCase(consentConsentId)) {
        throw new ApiException("Form, consent, and sample are not related");
      }
      
      //make sure we can determine the collection and preservation datetime values.
      //For the date piece, try to use the form procedure date if possible and if not use the 
      //consent creation date.  If neither is available then give up
      Timestamp theDate = asmForm.getProcedure_date();
      if (theDate == null) {
        if (consent.getForm_entry_datetime() == null) {
          return;
        }
        else {
          theDate = consent.getForm_entry_datetime();
        }
      }
      
      //now that we have the date, determine and set the collection and preservation 
      //time values if possible (ie. the asm form has a removal or grossing time value)
      Timestamp theTime = asmForm.getRemoval_date();
      if (theTime != null) {
        Calendar collectionCal = Calendar.getInstance();
        collectionCal.clear();
        collectionCal.setTimeInMillis(theDate.getTime());
        collectionCal.set(Calendar.HOUR_OF_DAY, theTime.getHours());
        collectionCal.set(Calendar.MINUTE, theTime.getMinutes());
        collectionCal.set(Calendar.SECOND, 0);
        collectionCal.set(Calendar.MILLISECOND, 0);
        if (theTime.getHours() < 12) {
          collectionCal.set(Calendar.AM_PM, Calendar.AM);
        }
        else {
          collectionCal.set(Calendar.AM_PM, Calendar.PM);
        }
        sample.setCollection_datetime(new java.sql.Timestamp(collectionCal.getTime().getTime()));
        sample.setCollection_datetime_dpc(Constants.MINUTE);
        sample.commitCopyHelper();
      } 
      theTime = asmForm.getGrossing_date();
      if (theTime != null) {
        Calendar preservationCal = Calendar.getInstance();
        preservationCal.clear();
        preservationCal.setTimeInMillis(theDate.getTime());
        preservationCal.set(Calendar.HOUR_OF_DAY, theTime.getHours());
        preservationCal.set(Calendar.MINUTE, theTime.getMinutes());
        preservationCal.set(Calendar.SECOND, 0);
        preservationCal.set(Calendar.MILLISECOND, 0);
        if (theTime.getHours() < 12) {
          preservationCal.set(Calendar.AM_PM, Calendar.AM);
        }
        else {
          preservationCal.set(Calendar.AM_PM, Calendar.PM);
        }
        sample.setPreservation_datetime(new java.sql.Timestamp(preservationCal.getTime().getTime()));
        sample.setPreservation_datetime_dpc(Constants.MINUTE);
        sample.commitCopyHelper();
      } 
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  public static String getCaseMinAgeAtCollection(String caseId){

    String ageAtCollection = "";

    String query =
      "select Min(" + DbConstants.SAMPLE_AGE_AT_COLLECTION + ") from " + DbConstants.TABLE_SAMPLE 
        +  " where " + DbConstants.CONSENT_ID +  " =? Group By " + DbConstants.CONSENT_ID;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, caseId);
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        ageAtCollection = ApiFunctions.safeString(rs.getString(1)) + "";
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return ageAtCollection;
  } 
  
  public static String getCaseMaxAgeAtCollection(String caseId) {

    String ageAtCollection = "";

    String query =
      "select Max(" + DbConstants.SAMPLE_AGE_AT_COLLECTION + ") from " + DbConstants.TABLE_SAMPLE 
        +  " where " + DbConstants.CONSENT_ID +  " =? Group By " + DbConstants.CONSENT_ID;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, caseId);
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        ageAtCollection = ApiFunctions.safeString(rs.getString(1)) + "";
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return ageAtCollection;
  }
  
  public static String getDonorMinAgeAtCollection(String donorId){

    String ageAtCollection = "";

    String query =
      "select Min(" + DbConstants.SAMPLE_AGE_AT_COLLECTION + ") from " + DbConstants.TABLE_SAMPLE 
        +  " where " + DbConstants.DONOR_ID +  " =? Group By " + DbConstants.DONOR_ID;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, donorId);
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        ageAtCollection = ApiFunctions.safeString(rs.getString(1)) + "";
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return ageAtCollection;
  } 
  
  public static String getDonorMaxAgeAtCollection(String donorId) {

    String ageAtCollection = "";

    String query =
      "select Max(" + DbConstants.SAMPLE_AGE_AT_COLLECTION + ") from " + DbConstants.TABLE_SAMPLE 
        +  " where " + DbConstants.DONOR_ID +  " =? Group By " + DbConstants.DONOR_ID;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, donorId);
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        ageAtCollection = ApiFunctions.safeString(rs.getString(1)) + "";
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return ageAtCollection;
  }            


  public static String getSampleAgeAtCollection(String sampleId) {

    String ageAtCollection = "";

    String query =
      "select " + DbConstants.SAMPLE_AGE_AT_COLLECTION + " from " + DbConstants.TABLE_SAMPLE 
        +  " where " + DbConstants.SAMPLE_ID +  " =?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, sampleId);
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        ageAtCollection = ApiFunctions.safeString(rs.getString(1)) + "";
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return ageAtCollection;
  } 

  public static LegalValueSet getAccountList() {
    return getAccountList(false);
  }

  public static LegalValueSet getAccountList(boolean accountsWithUsersOnly) {
    LegalValueSet lvs = new LegalValueSet();
    
    // SELECT account.ardais_acct_key, account.ardais_acct_company_desc 
    // FROM es_ardais_account account
    // (optional) WHERE EXISTS (SELECT 1 from es_ardais_user es_ardais_user 
    //                                   where es_ardais_user.ardais_acct_key = 
    //                                   account.ardais_acct_key)
    // ORDER BY upper(ardais_acct_company_desc)
    StringBuffer buff = new StringBuffer(256);
    buff.append("SELECT ");
    buff.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
    buff.append(".");
    buff.append(DbConstants.ACCOUNT_KEY);
    buff.append(", ");
    buff.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
    buff.append(".");
    buff.append(DbConstants.ACCOUNT_NAME);
    buff.append(" FROM ");
    buff.append(DbConstants.TABLE_ARDAIS_ACCOUNT);
    buff.append(" ");
    buff.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
    if (accountsWithUsersOnly) {
      buff.append(" WHERE EXISTS (SELECT 1 FROM ");
      buff.append(DbConstants.TABLE_ARDAIS_USER);
      buff.append(" ");
      buff.append(DbAliases.TABLE_ARDAIS_USER);
      buff.append(" WHERE ");
      buff.append(DbAliases.TABLE_ARDAIS_USER);
      buff.append(".");
      buff.append(DbConstants.USER_ACCT_KEY);
      buff.append(" = ");
      buff.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
      buff.append(".");
      buff.append(DbConstants.ACCOUNT_KEY);
      buff.append(")");
    }
    buff.append(" ORDER BY upper(");
    buff.append(DbAliases.TABLE_ARDAIS_ACCOUNT);
    buff.append(".");
    buff.append(DbConstants.ACCOUNT_NAME);
    buff.append(")");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(buff.toString());
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        String nameAndKey = rs.getString(DbConstants.ACCOUNT_NAME) + " (" + rs.getString(DbConstants.ACCOUNT_KEY) + ")";
        lvs.addLegalValue(rs.getString(DbConstants.ACCOUNT_KEY), nameAndKey);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return lvs;
  }

  //this utility function mirrors the format of List returned by getAccountList() above
  public static String getSingleAccountInList(String accountId) {
    return  getAccountName(accountId) + " (" + accountId + ")";
  }

  public static String getAccountName(String accountId) {
    String accountName = null;
    String query =
      "select " + DbConstants.ACCOUNT_NAME + " from " 
      + DbConstants.TABLE_ARDAIS_ACCOUNT + " Where " + DbConstants.ACCOUNT_KEY + "=?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        accountName = rs.getString(DbConstants.ACCOUNT_NAME);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return accountName;      
  }

  public static String getAccountDonorRegistrationFormId(String accountId) {
    String registrationFormId = null;
    String query =
      "select " + DbConstants.ACCOUNT_DONOR_REGISTRATION_FORM + " from " 
      + DbConstants.TABLE_ARDAIS_ACCOUNT + " Where " + DbConstants.ACCOUNT_KEY + "=?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        registrationFormId = rs.getString(DbConstants.ACCOUNT_DONOR_REGISTRATION_FORM);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return registrationFormId;      
  }

  /**
   * Determines whether an item is present in the specified inventory group.
   * @param groupId 
   * @param itemId
   * @return true if the item belongs to the inventory group, false otherwise.
   */
  public static boolean isInInventoryGroup(String groupId, String itemId) {
    boolean returnValue = false;
    
    String query = "Select " + DbConstants.LOGICAL_REPOS_ITEM_ITEM_ID + " from " 
    + DbConstants.TABLE_LOGICAL_REPOS_ITEM + " where " + DbConstants.LOGICAL_REPOS_ITEM_REPOSITORY_ID
    + " = ? and " + DbConstants.LOGICAL_REPOS_ITEM_ITEM_ID + " = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, groupId);
      pstmt.setString(2, itemId);
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        return true;
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    
    return returnValue;
  }
  
  /**
   * Determines if a list of samples all belong to an identical non-empty list of
   * inventory groups from the users perspective (that is, for each parent take away any
   * groups to which the user does not have access and if the remaining groups are identical
   * and non-empty for each parent the result is true)
   * @param securityInfo 
   * @param samples
   * @return true if the samples belong to identical inventory groups, false otherwise.
   */
  public static boolean isSamplesInIdenticalInventoryGroups(SecurityInfo securityInfo, List samples) {
    Iterator iterator = samples.iterator();
    boolean returnValue = true;
    List commonInventoryGroups = null;
    while (iterator.hasNext()) {
      SampleData parent = (SampleData)iterator.next();
      List inventoryGroups = LogicalRepositoryUtils.getInventoryGroupsVisibleToUser(securityInfo,parent.getLogicalRepositories());
      //if this is the first parent, save the list of inventory groups
      if (commonInventoryGroups == null) {
        commonInventoryGroups = inventoryGroups;
      }
      //otherwise compare this parent's inventory groups to the previously saved groups.  If they
      //are different set the return value to false and break out of the loop
      else {
        if (!commonInventoryGroups.equals(inventoryGroups)) {
          returnValue = false;
          break;
        }
      }
    }
    //if the list of common inventory groups is empty, return false;
    if (ApiFunctions.isEmpty(commonInventoryGroups)) {
      returnValue = false;
    }
    return returnValue;
  }
  
  /**
   * Method to determine if a sample alias value is already in use within an account
   */
  public static boolean isSampleAliasValueInUse(String accountId, String alias, String sampleId) {
    boolean returnValue = false;
    if (ApiFunctions.isEmpty(accountId)) {
      throw new ApiException("Account id cannot be empty");
    }
    if (ApiFunctions.isEmpty(alias)) {
      throw new ApiException("Sample alias cannot be empty");
    }
    if (ApiFunctions.isEmpty(sampleId)) {
      throw new ApiException("Sample id cannot be empty");
    }
    StringBuffer sql = new StringBuffer(100);
    sql.append("SELECT * FROM ILTDS_SAMPLE ");
    sql.append(" WHERE LOWER(ARDAIS_ACCT_KEY) = LOWER(?)");
    sql.append(" AND LOWER(CUSTOMER_ID) = LOWER(?)");
    sql.append(" AND LOWER(SAMPLE_BARCODE_ID) <> LOWER(?)");
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, accountId);
      pstmt.setString(2, alias);
      pstmt.setString(3, sampleId);
      rs = pstmt.executeQuery();
      returnValue = rs.next();
    }
    catch (SQLException e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(con,pstmt,rs);
    }

    return returnValue;
  }
  
  public static String getSampleIdAndAlias(com.ardais.bigr.javabeans.SampleData sample) {
    StringBuffer buff = new StringBuffer(50);
    buff.append(sample.getSampleId());
    if (!ApiFunctions.isEmpty(sample.getSampleAlias())) {
      buff.append(" (");
      buff.append(sample.getSampleAlias());
      buff.append(")");
    }
    return buff.toString();
  }
  
  public static String getSampleIdAndAlias(com.ardais.bigr.iltds.databeans.SampleData sample) {
    StringBuffer buff = new StringBuffer(50);
    buff.append(sample.getSample_id());
    if (!ApiFunctions.isEmpty(sample.getCustomer_id())) {
      buff.append(" (");
      buff.append(sample.getCustomer_id());
      buff.append(")");
    }
    return buff.toString();
  }
  
  public static boolean isSystemDonorId(String donorId) {
    boolean returnValue = false;
    donorId = ApiFunctions.safeTrim(donorId);
    if (!ApiFunctions.isEmpty(donorId) && donorId.length() >= 2) {
      String prefix = donorId.substring(0,2);
      returnValue = ValidateIds.TYPESET_DONOR.contains(prefix);
    }
    return returnValue;
  }
  
  public static boolean isSystemCaseId(String caseId) {
    boolean returnValue = false;
    caseId = ApiFunctions.safeTrim(caseId);
    if (!ApiFunctions.isEmpty(caseId) && caseId.length() >= 2) {
      String prefix = caseId.substring(0,2);
      returnValue = ValidateIds.TYPESET_CASE.contains(prefix);
    }
    return returnValue;
  }
  
  public static boolean isSystemSampleId(String sampleId) {
    boolean returnValue = false;
    sampleId = ApiFunctions.safeTrim(sampleId);
    if (!ApiFunctions.isEmpty(sampleId) && sampleId.length() >= 2) {
      String prefix = sampleId.substring(0,2);
      returnValue = ValidateIds.TYPESET_SAMPLE.contains(prefix);
    }
    return returnValue;
  }

  /*
   * Method to get the data for an account by id
   */
  public static AccountDto getAccountById(String accountId, boolean retrieveContactDetails,
                                    boolean retrieveLocationDetails) {
    String accountQuery = "select * from es_ardais_account where lower(ardais_acct_key) = lower(?)";
    return getAccount(accountQuery, accountId, retrieveContactDetails, retrieveLocationDetails);
  }

  /*
   * Method to get the data for an account by name
   */
  public static AccountDto getAccountByName(String accountName, boolean retrieveContactDetails,
                                      boolean retrieveLocationDetails) {
    String accountQuery = "select * from es_ardais_account where lower(ardais_acct_company_desc) = lower(?)";
    return getAccount(accountQuery, accountName, retrieveContactDetails, retrieveLocationDetails);
  }

  /*
   * Method to get the data for an account
   */
  private static AccountDto getAccount(String accountQuery, String accountIdentifier,
                                boolean retrieveContactDetails, boolean retrieveLocationDetails) {

    AccountDto returnValue = null;
    accountIdentifier = ApiFunctions.safeTrim(accountIdentifier);
    if (ApiFunctions.isEmpty(accountIdentifier)) {
      return returnValue;
    }

    //find the account information
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(accountQuery);
      pstmt.setString(1, accountIdentifier);
      rs = ApiFunctions.queryDb(pstmt, con);
      Map columnNames = DbUtils.getColumnNames(rs);
      while (rs.next()) {
        returnValue = new AccountDto(columnNames, rs);
      }
      //if necessary and possible find the contact information
      if (retrieveContactDetails && returnValue != null
          && !ApiFunctions.isEmpty(returnValue.getContactAddressId())) {
        ApiFunctions.close(rs);
        ApiFunctions.close(pstmt);
        String contactQuery = "select * from ardais_address where address_id = ?";
        pstmt = con.prepareStatement(contactQuery);
        pstmt.setString(1, returnValue.getContactAddressId());
        rs = ApiFunctions.queryDb(pstmt, con);
        columnNames = DbUtils.getColumnNames(rs);
        while (rs.next()) {
          returnValue.populateContactInfo(columnNames, rs);
        }
      }
      //if necessary and possible find the location information
      if (retrieveLocationDetails && returnValue != null
          && !ApiFunctions.isEmpty(returnValue.getPrimaryLocation())) {
        LocationData location = IltdsUtils.getLocationById(returnValue.getPrimaryLocation(), true);
        returnValue.setLocation(location);
      }

      //make sure the donor registration form is valid and if not clear it
      if (returnValue != null) {
        String donorRegistrationFormId = returnValue.getDonorRegistrationFormId();
        if (!ApiFunctions.isEmpty(donorRegistrationFormId)) {
          FormDefinitionServiceResponse response = FormDefinitionService.SINGLETON
              .findFormDefinitionById(donorRegistrationFormId);
          FormDefinition[] kcForms = response.getFormDefinitions();
          //if anything other than one form was found, return false
          if (kcForms.length != 1) {
            returnValue.setDonorRegistrationFormId(null);
          }
          else {
            if (!FormUtils.isRegistrationFormValid(kcForms[0], returnValue.getId(),
                TagTypes.DOMAIN_OBJECT_VALUE_DONOR)) {
              returnValue.setDonorRegistrationFormId(null);
            }
          }
        }
      }

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return returnValue;
  }

  /*
   * Method to get the data for a location, using the location id as a key
   */
  public static LocationData getLocationById(String locationId, boolean retrieveStorageUnits) {
    String locationQuery = "select * from iltds_geography_location where lower(location_address_id) = lower(?)";
    return getLocation(locationQuery, locationId, retrieveStorageUnits);
  }

  /*
   * Method to get the data for a location, using the location id as a key
   */
  public static LocationData getLocationByName(String locationName, boolean retrieveStorageUnits) {
    String locationQuery = "select * from iltds_geography_location where lower(location_name) = lower(?)";
    return getLocation(locationQuery, locationName, retrieveStorageUnits);
  }

  /*
   * Method to get the data for a location
   */
  private static LocationData getLocation(String locationQuery, String locationIdentifier,
                                   boolean retrieveStorageUnits) {
    LocationData returnValue = null;
    locationIdentifier = ApiFunctions.safeTrim(locationIdentifier);
    if (ApiFunctions.isEmpty(locationIdentifier)) {
      return returnValue;
    }

    //find the location information
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(locationQuery);
      pstmt.setString(1, locationIdentifier);
      rs = ApiFunctions.queryDb(pstmt, con);
      if (rs.next()) {
        Map columnNames = DbUtils.getColumnNames(rs);
        returnValue = new LocationData(columnNames, rs);
        ApiFunctions.close(rs);
        ApiFunctions.close(pstmt);
        //get the ship to information
        if (!ApiFunctions.isEmpty(returnValue.getAddressId())) {
          StringBuffer shipToQuery = new StringBuffer(300);
          shipToQuery.append("SELECT * from ardais_address where address_type = '");
          shipToQuery.append(com.ardais.bigr.es.helpers.FormLogic.ADDR_SHIP_TO);
          shipToQuery.append("'");
          shipToQuery
              .append(" and ardais_acct_key = (select ardais_acct_key from es_ardais_account");
          shipToQuery.append(" where primary_location = ?)");
          pstmt = con.prepareStatement(shipToQuery.toString());
          pstmt.setString(1, returnValue.getAddressId());
          rs = ApiFunctions.queryDb(pstmt, con);
          if (rs.next()) {
            columnNames = DbUtils.getColumnNames(rs);
            returnValue.setShipToAddress(new AddressDto(columnNames, rs));
          }
        }

        //find the existing storage unit information if necessary
        if (retrieveStorageUnits && returnValue.getAddressId() != null) {
          StringBuffer storageQuery = new StringBuffer(300);
          storageQuery
              .append("SELECT ibl.STORAGE_TYPE_CID, pl.lookup_type_cd_desc, lower(ibl.room_id),");
          storageQuery.append(" ibl.ROOM_ID, ibl.UNIT_NAME, lower(ibl.unit_name),");
          storageQuery.append(" count(DISTINCT ibl.DRAWER_ID) as NUMBER_OF_DRAWERS,");
          storageQuery.append(" MIN(ibl.SLOT_ID) as MIN_SLOT_ID, MAX(ibl.SLOT_ID) as MAX_SLOT_ID");
          storageQuery.append(" FROM iltds_box_location ibl, pdc_lookup pl");
          storageQuery.append("  WHERE LOCATION_ADDRESS_ID = ?");
          storageQuery.append(" and storage_type_cid = pl.lookup_type_cd");
          storageQuery
              .append(" GROUP BY lookup_type_cd_desc, STORAGE_TYPE_CID, lower(ibl.room_id),");
          storageQuery.append(" ibl.ROOM_ID, lower(ibl.unit_name), ibl.UNIT_NAME");
          storageQuery
              .append(" ORDER BY lookup_type_cd_desc, lower(ibl.room_id),lower(ibl.unit_name)");
          pstmt = con.prepareStatement(storageQuery.toString());
          pstmt.setString(1, returnValue.getAddressId());
          rs = ApiFunctions.queryDb(pstmt, con);
          if (rs != null) {
            while (rs.next()) {
              StorageUnitSummaryDto storageUnitSummary = new StorageUnitSummaryDto();
              storageUnitSummary.setStorageTypeCui(rs.getString("STORAGE_TYPE_CID"));
              storageUnitSummary.setRoomId(rs.getString("ROOM_ID"));
              storageUnitSummary.setUnitName(rs.getString("UNIT_NAME"));
              storageUnitSummary.setNumberOfDrawers(rs.getString("NUMBER_OF_DRAWERS"));
              storageUnitSummary.setMinimumSlotId(rs.getString("MIN_SLOT_ID"));
              storageUnitSummary.setMaximumSlotId(rs.getString("MAX_SLOT_ID"));
              returnValue.addExistingStorageUnit(storageUnitSummary);
            }
          }
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return returnValue;
  }
}
