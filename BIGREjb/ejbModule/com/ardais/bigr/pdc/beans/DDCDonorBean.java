package com.ardais.bigr.pdc.beans;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;


import javax.ejb.EJBException;
import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.api.TemporaryBlob;
import com.ardais.bigr.ddc.donor.BTXDetailsCreateDonor;
import com.ardais.bigr.ddc.donor.BTXDetailsUpdateDonor;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.performers.BtxPerformerPlaceHolder;
import com.ardais.bigr.pdc.btx.BTXDetailsCreateCaseProfileNotes;
import com.ardais.bigr.pdc.btx.BTXDetailsCreateClinicalData;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdateCaseProfileNotes;
import com.ardais.bigr.pdc.btx.BTXDetailsUpdateClinicalData;
import com.ardais.bigr.pdc.javabeans.ClinicalDataData;
import com.ardais.bigr.pdc.javabeans.ConsentData;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.pdc.javabeans.AttachmentData;
import com.ardais.bigr.security.SecurityInfo;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.FormInstanceQueryCriteria;
import com.gulfstreambio.kc.form.FormInstanceService;
import com.gulfstreambio.kc.form.FormInstanceServiceResponse;

/**
 * This is a Session Bean Class
 */
public class DDCDonorBean implements SessionBean {
  private javax.ejb.SessionContext mySessionCtx = null;
  private final static long serialVersionUID = 3206093459760846163L;

  public DonorData buildDonorData(DonorData donorData) {
    boolean donorExists = false;

    {
      DonorData newDonorData = getDonorProfile(donorData);
      if (newDonorData.getArdaisId() != null) {
        donorExists = true;
        donorData = newDonorData;
      }
    }

    {
      List consents = getConsents(donorData);
      if (!consents.isEmpty()) {
        donorExists = true;
      }
      donorData.setConsents(consents);
    }

    donorData.setExists(donorExists);

    return donorData;
  }
  public ClinicalDataData createClinicalData(
    ClinicalDataData clinicalData,
    SecurityInfo securityInfo) {
    StringBuffer insertSql = new StringBuffer(256);
    insertSql.append("{ CALL INSERT INTO pdc_clinical_data");
    insertSql.append(" (clinical_data_id,");
    insertSql.append(" ardais_id,");
    insertSql.append(" consent_id,");
    insertSql.append(" category,");
    insertSql.append(" create_user, create_date, last_update_user, last_update_date,");
    insertSql.append(" clinical_data)");
    insertSql.append(" VALUES (pdc_clinical_data_id_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)");
    insertSql.append(" RETURNING clinical_data_id INTO ? }");

    Connection con = ApiFunctions.getDbConnection();
    CallableStatement cstmt = null;
    TemporaryClob clob = null;
    boolean success = false;
    try {
      // Insert the clinical data, saving the returned clinical data
      // id in the data bean.
      cstmt = con.prepareCall(insertSql.toString());
      java.sql.Timestamp time = new java.sql.Timestamp((new java.util.Date()).getTime());
      cstmt.setString(1, clinicalData.getArdaisId());
      cstmt.setString(2, clinicalData.getConsentId());
      cstmt.setString(3, clinicalData.getCategory());
      cstmt.setString(4, clinicalData.getCreateUser());
      cstmt.setTimestamp(5, time);
      cstmt.setString(6, clinicalData.getLastUpdateUser());
      cstmt.setTimestamp(7, time);
      clob = new TemporaryClob(con, clinicalData.getClinicalData());
      cstmt.setClob(8, clob.getSQLClob());
      cstmt.registerOutParameter(9, Types.VARCHAR);
      cstmt.execute();
      success = true;
      clinicalData.setClinicalDataId(cstmt.getString(9));
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(clob, con);
      ApiFunctions.close(cstmt);
      ApiFunctions.close(con);
    }

    //create an ICP history record if the clinical data was successfully created
    if (success) {
      //get the alias values for the donor and case
      ConsentData cd = new ConsentData();
      cd.setConsentId(clinicalData.getConsentId());
      String caseAlias = getConsentDetail(cd).getCustomerId();
      DonorData dd = new DonorData();
      dd.setArdaisId(clinicalData.getArdaisId());
      String donorAlias = getDonorProfile(dd).getCustomerId();
      
      BTXDetailsCreateClinicalData btxDetails = new BTXDetailsCreateClinicalData();
      btxDetails.setLoggedInUserSecurityInfo(securityInfo);
      btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btxDetails.setTransactionType("pdc_placeholder");
      btxDetails.setClinicalDataData(clinicalData);
      btxDetails.setCaseAlias(caseAlias);
      btxDetails.setDonorAlias(donorAlias);
      BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
      btx.perform(btxDetails);
    }
    return clinicalData;
  }

  public DonorData createDonorProfile(DonorData donorData, SecurityInfo securityInfo) {
    FormInstance form = donorData.getFormInstance();

    //if the donor data is imported and has no ardais id, get one
    if (ApiFunctions.isEmpty(donorData.getArdaisId())
      && "Y".equalsIgnoreCase(donorData.getImportedYN())) {
      donorData.setArdaisId(getIdForNewImportedDonor());
      if (form != null) {
        form.setDomainObjectId(donorData.getArdaisId());
      }
    }
    StringBuffer sql = new StringBuffer(256);
    sql.append("INSERT INTO pdc_ardais_donor");
    sql.append(" (ardais_id,");
    sql.append(" yyyy_dob,");
    sql.append(" gender,");
    sql.append(" ethnic_category,");
    sql.append(" race,");
    sql.append(" zip_code,");
    sql.append(" country_of_birth,");
    sql.append(" create_date,");
    sql.append(" donor_profile_notes,");
    sql.append(" create_user,");
    sql.append(" imported_yn,");
    sql.append(" ardais_acct_key,");
    sql.append(" customer_id,");
    sql.append(" donor_registration_form");
    sql.append(")");
    sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

    Connection con = ApiFunctions.getDbConnection();
    TemporaryClob clob = null;
    PreparedStatement pstmt = null;
    boolean success = false;
    try {
      // Insert the new path report diagnostic.
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, donorData.getArdaisId());
      pstmt.setString(2, donorData.getYyyyDob());
      pstmt.setString(3, donorData.getGender());
      pstmt.setString(4, donorData.getEthnicCategory());
      pstmt.setString(5, donorData.getRace());
      pstmt.setString(6, donorData.getZipCode());
      pstmt.setString(7, donorData.getCountryOfBirth());
      pstmt.setTimestamp(8, new java.sql.Timestamp((new java.util.Date()).getTime()));
      clob = new TemporaryClob(con, donorData.getDonorProfileNotes());
      pstmt.setClob(9, clob.getSQLClob());
      pstmt.setString(10, donorData.getCreateUser());
      pstmt.setString(11, donorData.getImportedYN());
      pstmt.setString(12, donorData.getArdaisAccountKey());
      pstmt.setString(13, donorData.getCustomerId());
      pstmt.setString(14, donorData.getRegistrationFormId());
      pstmt.executeUpdate();
      success = true;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(clob, con);
      ApiFunctions.close(pstmt);
      ApiFunctions.closeDbConnection(con);
    }

    // If we have KC data element values, then create a new KC form instance.
    if ((form != null) && (form.getDataElements().length > 0)) {
      FormInstanceServiceResponse response = 
        FormInstanceService.SINGLETON.createFormInstance(form);
    }

    //create an ICP history record if the donor was successfully created
    if (success) {
      BTXDetailsCreateDonor btxDetails = new BTXDetailsCreateDonor();
      btxDetails.setLoggedInUserSecurityInfo(securityInfo);
      btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btxDetails.setTransactionType("pdc_placeholder");
      btxDetails.setDonorData(donorData);
      BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
      btx.perform(btxDetails);
    }

    return donorData;
  }
  
  
  public List getAttachments(DonorData dData){
      StringBuffer query = new StringBuffer();
      
      query.append("SELECT ID, ARDAIS_ACCT_KEY, ARDAIS_ID, CONSENT_ID, SAMPLE_BARCODE_ID, IS_PHI_YN, COMMENTS,NAME, CREATED_BY, CREATED_DATE, CONTENTTYPE ");
      query.append("FROM iltds_attachments ");
      query.append("WHERE ARDAIS_ACCT_KEY = ? AND ARDAIS_ID = ? order by CREATED_DATE desc");
       
      String[] params = { dData.getArdaisAccountKey(), dData.getArdaisId()};
    
      return ApiFunctions.runQuery(query.toString(), params, AttachmentData.class); 
    

  }
  
  
  public AttachmentData insertDonorAttachment(AttachmentData attachData, SecurityInfo securityInfo) {
   
  
    StringBuffer sql = new StringBuffer(256);
    sql.append("INSERT INTO iltds_attachments");
    sql.append(" (ardais_acct_key,");
    sql.append(" ardais_id,");
   
    sql.append(" is_phi_yn,");
    sql.append(" comments,");
    sql.append(" name,");
    sql.append(" attachment,");
    sql.append(" created_by,");
    sql.append(" created_date,");
    sql.append(" contenttype");
   
    sql.append(")");
    sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

    Connection con = ApiFunctions.getDbConnection();
    TemporaryBlob blob = null;
    PreparedStatement pstmt = null;
    boolean success = false;
    try {
      // Insert the new attachment.
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, attachData.getArdaisAccountKey());
      pstmt.setString(2, attachData.getArdaisId());
      pstmt.setString(3, attachData.getIsPHIYN());
      pstmt.setString(4, attachData.getComments());
      pstmt.setString(5, attachData.getName());

      blob = new TemporaryBlob(con, attachData.getAttachment());
      pstmt.setBlob(6, blob.getSQLBlob() );
      pstmt.setString(7, attachData.getCreatedBy());
      pstmt.setTimestamp(8, new java.sql.Timestamp((new java.util.Date()).getTime()));
      pstmt.setString(9, attachData.getContentType());
   
      pstmt.executeUpdate();
      success = true;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(blob, con);
      ApiFunctions.close(pstmt);
      ApiFunctions.closeDbConnection(con);
    }

  /***

    //create an ICP history record if the donor was successfully created
    if (success) {
      BTXDetailsCreateDonor btxDetails = new BTXDetailsCreateDonor();
      btxDetails.setLoggedInUserSecurityInfo(securityInfo);
      btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btxDetails.setTransactionType("pdc_placeholder");
      btxDetails.setDonorData(donorData);
      BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
      btx.perform(btxDetails);
    } ***/

    return attachData;
  }
  
  
  public boolean deleteAttachment(String attachId){
    StringBuffer sql = new StringBuffer();
    
    sql.append("DELETE ");
    sql.append("FROM iltds_attachments ");
    sql.append("WHERE ID = ? ");
     
    
    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    boolean success = false;
    try {
      // delete the attachment
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, attachId);
  
      pstmt.executeUpdate();
      success = true;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.closeDbConnection(con);
    }
  
    return success;
  

  }
  
  
  /**
   * ejbActivate method comment
   */
  public void ejbActivate() throws java.rmi.RemoteException {
  }
  /**
   * ejbCreate method comment
   * @exception javax.ejb.CreateException The exception description.
   */
  public void ejbCreate() throws javax.ejb.CreateException, EJBException {
  }
  /**
   * ejbPassivate method comment
   */
  public void ejbPassivate() throws java.rmi.RemoteException {
  }
  /**
   * ejbRemove method comment
   */
  public void ejbRemove() throws java.rmi.RemoteException {
  }
  public ClinicalDataData getClinicalData(ClinicalDataData clinicalData) {
    String sql = "SELECT * FROM pdc_clinical_data WHERE clinical_data_id = ?";
    String[] params = { clinicalData.getClinicalDataId()};
    List results = ApiFunctions.runQuery(sql, params, ClinicalDataData.class);
    return (results.size() > 0) ? (ClinicalDataData) results.get(0) : new ClinicalDataData();
  }
  public List getClinicalDataList(ClinicalDataData clinicalData) {
    String sql = "SELECT * FROM pdc_clinical_data WHERE consent_id = ?";
    String[] params = new String[] { clinicalData.getConsentId()};
    return ApiFunctions.runQuery(sql, params, ClinicalDataData.class);
  }
  public ConsentData getConsentDetail(ConsentData consentData) {
    String query = "SELECT * FROM iltds_informed_consent WHERE consent_id = ?";
    String[] params = { consentData.getConsentId()};
    List results = ApiFunctions.runQuery(query, params, ConsentData.class);
    return (results.size() > 0) ? (ConsentData) results.get(0) : new ConsentData();
  }
  public List getConsents(DonorData donorData) {

    StringBuffer query = new StringBuffer();
    query.append("SELECT c.ardais_id, 												\n");
    query.append("       c.consent_id, 												\n");
    query.append("       c.customer_id,                        \n");
    query.append("       c.ddc_check_flag, 											\n");
    query.append("       c.di_case_profile_notes,                                   \n");
    query.append("       r.path_report_id, 											\n");
    query.append("       r.diagnosis_concept_id, 									\n");
    query.append("       nvl2(r.pathology_ascii_report, 'Y', 'N') AS path_report	\n");
    query.append("FROM   iltds_informed_consent c,									\n");
    query.append("       pdc_pathology_report   r									\n");
    query.append("WHERE      c.ardais_id  = ?										\n");
    query.append("       AND c.consent_id = r.consent_id(+)							\n");
    query.append("ORDER BY c.consent_id												\n");

    String[] params = { donorData.getArdaisId()};
    return ApiFunctions.runQuery(query.toString(), params, ConsentData.class);
  }
  /**
   */
  public DonorData getDonorCaseSummary(String ardaisId, boolean linkedCasesOnly) {
    StringBuffer sql = new StringBuffer(256);
    sql.append(
      "SELECT c.ardais_id, c.consent_id, c.ddc_check_flag, c.customer_id, r.path_report_id, r.diagnosis_concept_id, nvl2(r.pathology_ascii_report, 'Y', 'N') AS path_report");
    sql.append(" FROM iltds_informed_consent c,");
    sql.append(" pdc_pathology_report r");
    sql.append(" WHERE c.ardais_id = ?");
    if (linkedCasesOnly) {
      sql.append(" AND c.linked = 'Y'");
    }
    sql.append(" AND c.consent_id = r.consent_id(+)");
    sql.append(" ORDER BY c.consent_id");

    DonorData donorData = new DonorData();
    donorData.setArdaisId(ardaisId);
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, ardaisId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        ConsentData consentData = new ConsentData();
        consentData.setConsentId(rs.getString("consent_id"));
        consentData.setPathReportId(rs.getString("path_report_id"));
        consentData.setDiagnosis(rs.getString("diagnosis_concept_id"));
        String ddcCheck = rs.getString("ddc_check_flag");
        if ((ddcCheck != null) && (ddcCheck.equals("Y")))
          consentData.setAbstractionComplete(new Boolean(true));
        else
          consentData.setAbstractionComplete(new Boolean(false));
        String pathReport = rs.getString("path_report");
        if ((pathReport != null) && (pathReport.equals("Y")))
          consentData.setRawPathReportEntered(new Boolean(true));
        else
          consentData.setRawPathReportEntered(new Boolean(false));
        consentData.setCustomerId(rs.getString("customer_id"));
        donorData.addConsent(consentData);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return donorData;
  }

  public DonorData getDonorProfile(DonorData donorData) {
    StringBuffer query = new StringBuffer();
    query.append("SELECT donor.*            	\n");
    query.append("FROM   PDC_ARDAIS_DONOR donor	\n");
    query.append("WHERE  donor.ARDAIS_ID = ?	\n");

    String[] params = { donorData.getArdaisId()};
    List results = ApiFunctions.runQuery(query.toString(), params, DonorData.class);

    if (results.size() > 0) {
      DonorData returnDonorData = (DonorData) results.get(0);
      
      // Get the KC form instance, if one exists for the donor.  The find using the criteria
      // returns only basic form information, and does not include the values of the data elements
      // that comprise the form.  Thus, call find by id to get the data element values. 
      FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
      criteria.addDomainObjectId(returnDonorData.getArdaisId());
      criteria.addFormDefinitionId(returnDonorData.getRegistrationFormId());
      FormInstanceServiceResponse response = 
        FormInstanceService.SINGLETON.findFormInstances(criteria);
      FormInstance form = response.getFormInstance();
      if (form != null) {
        response = FormInstanceService.SINGLETON.findFormInstanceById(form.getFormInstanceId());
        form = response.getFormInstance();
        if (form != null) {
          returnDonorData.setFormInstance(form);
        }
      }
      return returnDonorData;
    }
    else {      
      return new DonorData();
    }
  }

  public String getDonorAccount(String ardaisID)
      throws com.ardais.bigr.api.ApiException {
      Connection con = null;
      PreparedStatement prepStmt = null;
      ResultSet rs = null;
      try {
          String queryString =
              "select account.ARDAIS_ACCT_KEY "
                  + "from pdc_ardais_donor donor, iltds_informed_consent consent, "
                  + "  es_ardais_account account "
                  + "where donor.ardais_id = consent.ardais_id And consent.CONSENT_LOCATION_ADDRESS_ID = account.PRIMARY_LOCATION "
                  + "and donor.ardais_id = ? ";

          // Prepare the query.  Try the source that was passed in first.
          con = ApiFunctions.getDbConnection();
          prepStmt = con.prepareStatement(queryString);
          prepStmt.setString(1, ardaisID);

          // Execute the query.  If it did not return any results,
          // then perform the query again using Ardais as the source.
          rs = prepStmt.executeQuery();
          while (rs.next()) {
              return rs.getString(1);
          }
      }
      catch (Exception ex) {
          ApiFunctions.throwAsRuntimeException(ex);
      }
      finally {
          ApiFunctions.close(con, prepStmt, rs);
      }
      return null;
  }

    /**
   * getSessionContext method comment
   * @return javax.ejb.SessionContext
   */
  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }

  /**
   * Insert the method's description here.
   * Creation date: (7/30/2002 2:51:15 PM)
   * @return boolean
   * @param donorData com.ardais.bigr.pdc.javabeans.DonorData
   */
  public boolean isPresent(DonorData donorData) {
    PreparedStatement pstmt = null;
    Connection con = null;
    ResultSet rs = null;
    String query = null;
    String query2 = null;
    boolean result = false;
    
    // We need to look in both the case and donor tables to determine whether a donor exists.
    // For imported donors, we can have a record in PDC_ARDAIS_DONOR but not have any cases
    // for that donor.  In other situations, we can have records in ILTDS_INFORMED_CONSENT for
    // a donor but no corresponding record in PDC_ARDAIS_DONOR (for example, a non-imported
    // donor/case that hasn't had DDC abstraction done yet).

    query = "SELECT 1 FROM ILTDS_INFORMED_CONSENT consent WHERE consent.ARDAIS_ID = ?";
    query2 = "SELECT 1 FROM PDC_ARDAIS_DONOR donor WHERE donor.ARDAIS_ID = ?";

    String donorId = donorData.getArdaisId();

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, donorId);

      rs = ApiFunctions.queryDb(pstmt, con);

      result = (rs.next()) ? true : false;

      //if we found no cases, see if this is an imported donor (which might not have any cases)
      if (!result) {
        ApiFunctions.close(rs);
        rs = null;
        ApiFunctions.close(pstmt);
        pstmt = null;
        pstmt = con.prepareStatement(query2);
        pstmt.setString(1, donorId);

        rs = ApiFunctions.queryDb(pstmt, con);

        result = (rs.next()) ? true : false;
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
   * setSessionContext method comment
   * @param ctx javax.ejb.SessionContext
   */
  public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException {
    mySessionCtx = ctx;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/24/2002 4:29:13 PM)
   * @return com.ardais.bigr.pdc.javabeans.ConsentData
   * @param consentData com.ardais.bigr.pdc.javabeans.ConsentData
   */
  public ConsentData updateCaseProfileNotes(ConsentData consentData, SecurityInfo securityInfo) {
    StringBuffer query = new StringBuffer(256);
    query.append("SELECT consent_id FROM iltds_informed_consent");
    query.append(" WHERE consent_id = ? and di_case_profile_notes is null");

    StringBuffer sql = new StringBuffer(256);
    sql.append("UPDATE iltds_informed_consent");
    sql.append(" SET di_case_profile_notes = ?");
    sql.append(" WHERE consent_id = ?");

    Connection con = ApiFunctions.getDbConnection();
    TemporaryClob clob = null;
    PreparedStatement pstmt = null;
    boolean success = false;
    boolean isNew = true;
    try {
      //first figure out if we're creating or updating the case profile notes
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, consentData.getConsentId());
      ResultSet rs = pstmt.executeQuery();
      isNew = rs.next();
      ApiFunctions.close(pstmt);
      // Update donor profile.
      pstmt = con.prepareStatement(sql.toString());
      clob = new TemporaryClob(con, consentData.getDiCaseProfileNotes());
      pstmt.setClob(1, clob.getSQLClob());
      pstmt.setString(2, consentData.getConsentId());
      pstmt.executeUpdate();
      success = true;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(clob, con);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
    //if we successfully created/edited the notes, create an ICP history record to show that
    if (success) {
      //get the alias values for the donor and case

      ConsentData cd = new ConsentData();
      cd.setConsentId(consentData.getConsentId());
      String caseAlias = getConsentDetail(cd).getCustomerId();
      DonorData dd = new DonorData();
      dd.setArdaisId(consentData.getArdaisId());
      String donorAlias = getDonorProfile(dd).getCustomerId();

      BTXDetails btxDetails;
      if (isNew) {
        btxDetails = new BTXDetailsCreateCaseProfileNotes();
        ((BTXDetailsCreateCaseProfileNotes) btxDetails).setConsentData(consentData);
        ((BTXDetailsCreateCaseProfileNotes) btxDetails).setCaseAlias(caseAlias);
        ((BTXDetailsCreateCaseProfileNotes) btxDetails).setDonorAlias(donorAlias);
      }
      else {
        btxDetails = new BTXDetailsUpdateCaseProfileNotes();
        ((BTXDetailsUpdateCaseProfileNotes) btxDetails).setConsentData(consentData);
        ((BTXDetailsUpdateCaseProfileNotes) btxDetails).setCaseAlias(caseAlias);
        ((BTXDetailsUpdateCaseProfileNotes) btxDetails).setDonorAlias(donorAlias);
      }
      
      btxDetails.setLoggedInUserSecurityInfo(securityInfo);
      btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btxDetails.setTransactionType("pdc_placeholder");
      BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
      btx.perform(btxDetails);
    }
    return consentData;
  }
  public ClinicalDataData updateClinicalData(
    ClinicalDataData clinicalData,
    SecurityInfo securityInfo) {
    StringBuffer sql = new StringBuffer(256);
    sql.append("UPDATE pdc_clinical_data");
    sql.append(" SET last_update_user = ?,");
    sql.append(" last_update_date = ?,");
    sql.append(" clinical_data = ?");
    sql.append(" WHERE clinical_data_id = ?");

    Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt = null;
    TemporaryClob clob = null;
    boolean success = false;
    try {
      // Update the clinical data.
      pstmt = con.prepareStatement(sql.toString());
      java.sql.Timestamp time = new java.sql.Timestamp((new java.util.Date()).getTime());
      pstmt.setString(1, clinicalData.getLastUpdateUser());
      pstmt.setTimestamp(2, time);
      clob = new TemporaryClob(con, clinicalData.getClinicalData());
      pstmt.setClob(3, clob.getSQLClob());
      pstmt.setString(4, clinicalData.getClinicalDataId());
      pstmt.executeUpdate();
      success = true;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(clob, con);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    //create an ICP history record if the clinical data was successfully created
    if (success) {
      //get the alias values for the donor and case
      ConsentData cd = new ConsentData();
      cd.setConsentId(clinicalData.getConsentId());
      String caseAlias = getConsentDetail(cd).getCustomerId();
      DonorData dd = new DonorData();
      dd.setArdaisId(clinicalData.getArdaisId());
      String donorAlias = getDonorProfile(dd).getCustomerId();
      
      BTXDetailsUpdateClinicalData btxDetails = new BTXDetailsUpdateClinicalData();
      btxDetails.setLoggedInUserSecurityInfo(securityInfo);
      btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btxDetails.setTransactionType("pdc_placeholder");
      btxDetails.setClinicalDataData(clinicalData);
      btxDetails.setCaseAlias(caseAlias);
      btxDetails.setDonorAlias(donorAlias);
      BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
      btx.perform(btxDetails);
    }
    return clinicalData;
  }

  public DonorData updateDonorProfile(DonorData donorData, SecurityInfo securityInfo) {
    StringBuffer sql = new StringBuffer(256);
    sql.append("UPDATE pdc_ardais_donor");
    sql.append(" SET yyyy_dob = ?,");
    sql.append(" gender = ?,");
    sql.append(" ethnic_category = ?,");
    sql.append(" race = ?,");
    sql.append(" zip_code = ?,");
    sql.append(" country_of_birth = ?,");
    sql.append(" last_update_date = ?,");
    sql.append(" last_update_user = ?,");
    sql.append(" donor_profile_notes = ?");
    if ("Y".equalsIgnoreCase(donorData.getImportedYN())) {
      sql.append(", customer_id = ?");
    }
    sql.append(" WHERE ardais_id = ?");

    Connection con = ApiFunctions.getDbConnection();
    TemporaryClob clob = null;
    PreparedStatement pstmt = null;
    boolean success = false;
    try {
      // Update donor profile.
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, donorData.getYyyyDob());
      pstmt.setString(2, donorData.getGender());
      pstmt.setString(3, donorData.getEthnicCategory());
      pstmt.setString(4, donorData.getRace());
      pstmt.setString(5, donorData.getZipCode());
      pstmt.setString(6, donorData.getCountryOfBirth());
      pstmt.setTimestamp(7, new java.sql.Timestamp((new java.util.Date()).getTime()));
      pstmt.setString(8, donorData.getLastUpdateUser());
      clob = new TemporaryClob(con, donorData.getDonorProfileNotes());
      pstmt.setClob(9, clob.getSQLClob());
      if ("Y".equalsIgnoreCase(donorData.getImportedYN())) {
        pstmt.setString(10, donorData.getCustomerId());
        pstmt.setString(11, donorData.getArdaisId());
      }
      else {
        pstmt.setString(10, donorData.getArdaisId());
      }
      pstmt.executeUpdate();
      success = true;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(clob, con);
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }

    FormInstance form = donorData.getFormInstance();
    if (form != null) {
      FormInstanceServiceResponse response = 
        FormInstanceService.SINGLETON.updateFormInstance(form);
    }
    
    //create an ICP history record if the donor was successfully created
    if (success) {
      BTXDetailsUpdateDonor btxDetails = new BTXDetailsUpdateDonor();
      btxDetails.setLoggedInUserSecurityInfo(securityInfo);
      btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
      btxDetails.setTransactionType("pdc_placeholder");
      btxDetails.setDonorData(donorData);
      BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
      btx.perform(btxDetails);
    }
    return donorData;
  }
  

  //private method used to get an id for a new imported donor
  private String getIdForNewImportedDonor() {
    String donorId = null;
    Connection connection = null;
    CallableStatement cstmt = null;
    ResultSet results = null;
    try {
      connection = ApiFunctions.getDbConnection();
      cstmt = connection.prepareCall("begin DATA_IMPORT_GET_DONOR_ID(?,?); end;");
      cstmt.registerOutParameter(1, Types.VARCHAR);
      cstmt.registerOutParameter(2, Types.VARCHAR);
      cstmt.executeQuery();
      Object obj = cstmt.getObject(2);
      if (obj != null) {
        String emsg = obj.toString();
        //throw an exception
        throw new ApiException(
          "DATA_IMPORT_GET_DONOR_ID failed at DDCDonorBean.getIdForNewImportedDonor(): " + emsg);
      }
      else {
        donorId = cstmt.getString(1);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(connection, cstmt, results);
    }
    return donorId;
  }
}
