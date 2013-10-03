package com.ardais.bigr.iltds.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.LegalValueSetHierarchy;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.databeans.IrbVersionData;
import com.ardais.bigr.iltds.databeans.LabelValueBean;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.DbUtils;

/**
 * This is a Session Bean Class
 */
public class ListGeneratorBean implements SessionBean {
  private javax.ejb.SessionContext mySessionCtx = null;
  private LegalValueSet _specimenTypes = null;
  private LegalValueSet _finishedProductFormats = null;
  final static long serialVersionUID = 3206093459760846163L;

  /**
   * Return the display value corresponding to a code.  It is assumed
   * that the query is formed such that one row with one column is
   * returned, and this is that value that is returned.
   * @param query  the query
   * @return  The display value.
   * @exception com.ardais.bigr.api.ApiException
   */
  private String doLookup(String query) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String result = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        result = rs.getString(1);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
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

    _finishedProductFormats = new LegalValueSet();
    _finishedProductFormats.addLegalValue(FormLogic.FORMAT_DNA);
    _finishedProductFormats.addLegalValue(FormLogic.FORMAT_FROZEN);
    _finishedProductFormats.addLegalValue(FormLogic.FORMAT_PARAFFIN);
    _finishedProductFormats.addLegalValue(FormLogic.FORMAT_RNA);
    _finishedProductFormats.addLegalValue(FormLogic.FORMAT_SLIDES);
    _finishedProductFormats.addLegalValue(FormLogic.FORMAT_TMA);

    _specimenTypes = new LegalValueSet();
    _specimenTypes.addLegalValue(FormLogic.SPEC_DISEASED_IND, FormLogic.SPEC_DISEASED);
    _specimenTypes.addLegalValue(FormLogic.SPEC_MIXED_IND, FormLogic.SPEC_MIXED);
    _specimenTypes.addLegalValue(FormLogic.SPEC_GROSS_NORMAL_IND, FormLogic.SPEC_GROSS_NORMAL);
    _specimenTypes.addLegalValue(FormLogic.SPEC_UNKNOWN_IND, FormLogic.SPEC_UNKNOWN);
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
  /**
   * Insert the method's description here.
   * Creation date: (5/15/2001 2:46:36 PM)
   * @return java.util.Vector
   * @param geoLoc java.lang.String
   * @exception com.ardais.bigr.api.ApiException The exception description.
   */
  public Vector findAvailableBoxLocationAll(String geoLoc) {
    String queryString =
      "select unit_name, drawer_id, slot_id, room_id "
        + "from iltds_box_location "
        + "where location_address_id = ? "
        + "AND box_barcode_id is null "
        + "AND available_ind = 'Y'";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Vector result = new Vector();
    Vector freezerList = new Vector();
    Vector drawerList = new Vector();
    Vector slotList = new Vector();
    Vector roomList = new Vector();

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, geoLoc);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        if (!freezerList.contains(rs.getString(1)))
          freezerList.addElement(rs.getString(1));
        if (!drawerList.contains(rs.getString(2)))
          drawerList.addElement(rs.getString(2));
        if (!slotList.contains(rs.getString(3)))
          slotList.addElement(rs.getString(3));
        if (!roomList.contains(rs.getString(4)))
          roomList.addElement(rs.getString(4));
      }
      result.add(freezerList);
      result.add(drawerList);
      result.add(slotList);
      result.add(roomList);
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  public Vector findBoxesByManifestID(String manifestID) {
    String queryString = "SELECT box_barcode_id FROM ILTDS_SAMPLE_BOX where manifest_number = ?";

    Vector result = new Vector();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, manifestID);
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
   * Returns a list of IrbVersionData objects containing all active IRB versions for the
   * specified account.
   * 
   * @param accountId the account
   * @return the list of active IRB consent versions for the account
   */
  public List findActiveConsentVersions(String accountId) {
    return findActiveConsentVersions(accountId, true, true);
  }

  /**
   * Returns a list of IrbVersionData objects containing all active IRB versions for the
   * specified account, with the option to filter out those based on a policy that has
   * either case release required and/or consent verify required = Y.
   * 
   * @param accountId the account
   * @return the list of active IRB consent versions for the account
   */
  public List findActiveConsentVersions(String accountId, boolean allowCaseReleaseRequired, 
                                        boolean allowConsentVerifyRequired) {
    StringBuffer query = new StringBuffer(200);
    query.append("select con.CONSENT_VERSION_ID, con.CONSENT_VERSION, ");
    query.append("irb.IRBPROTOCOL_ID, irb.IRBPROTOCOL, irb.POLICY_ID ");
    query.append("from es_ardais_consentver con, es_ardais_irb irb ");
    if (!allowCaseReleaseRequired || !allowConsentVerifyRequired) {
      query.append(", ard_policy pol ");
    }
    query.append("where irb.IRBPROTOCOL_ID = con.IRBPROTOCOL_ID ");
    query.append("and con.active = 'Y' ");
    query.append("and irb.ARDAIS_ACCT_KEY = ? ");
    if (!allowCaseReleaseRequired || !allowConsentVerifyRequired) {
      query.append("and irb.policy_id = pol.id ");
    }
    if (!allowCaseReleaseRequired) {
      query.append("and pol.case_release_required_yn = 'N' ");
    }
    if (!allowConsentVerifyRequired) {
      query.append("and pol.consent_verify_required_yn = 'N' ");
    }
    query.append("order by lower(irb.irbprotocol), lower(con.consent_version)");

    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());

      pstmt.setString(1, accountId);

      rs = ApiFunctions.queryDb(pstmt, con);
      Map columns = DbUtils.getColumnNames(rs);

      while (rs.next()) {
        IrbVersionData irb = new IrbVersionData();
        irb.populate(columns, rs);
        result.add(irb);
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
   * Returns a list of logical repository objects containing all repositories.
   * Each item in the list is a {@link LogicalRepository} object.  The list is returned
   * sorted by ascending full name (case insensitive).
   * 
   * @return the list of logical repositories
   */
  public List getLogicalRepositories() {
    String queryString =
      "select r.id, r.full_name, r.short_name, r.bms_yn "
        + "from ard_logical_repos r "
        + "order by lower(r.full_name)";

    List result = new ArrayList();

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);

      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        LogicalRepository repos = new LogicalRepository();

        repos.setId(rs.getString(1));
        repos.setFullName(rs.getString(2));
        repos.setShortName(rs.getString(3));
        repos.setBmsYN(rs.getString(4));

        result.add(repos);
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
   * Insert the method's description here.
   * Creation date: (5/15/2001 2:46:36 PM)
   * @return java.util.Vector
   * @param geoLoc java.lang.String
   * @exception com.ardais.bigr.api.ApiException The exception description.
   */
  public Vector findNextLocationDropDown(
    String location,
    String room,
    String freezer,
    String drawer,
    String storageTypeCid) {

    String findRoomIDs =
      "select distinct(room_id)  "
        + "from iltds_box_location "
        + "where location_address_id = ? "
        + "and available_ind = 'Y' "
        + "and storage_type_cid = ? "
        + "order by room_id ";

    String findStorageUnits =
      "select distinct(unit_name) "
        + "from iltds_box_location "
        + "where location_address_id = ? "
        + "and room_id = ? "
        + "and available_ind = 'Y' "
        + "and storage_type_cid = ? "
        + "order by unit_name ";

    String findDrawerIDs =
      "select distinct(drawer_id) "
        + "from iltds_box_location "
        + "where location_address_id = ? "
        + "and room_id = ? "
        + "and unit_name = ? "
        + "and available_ind = 'Y' "
        + "and storage_type_cid = ? "
        + "order by to_number(drawer_id) ";

    String findSlotIDs =
      "select distinct(slot_id) "
        + "from iltds_box_location "
        + "where location_address_id = ? "
        + "and room_id = ? "
        + "and unit_name = ? "
        + "and drawer_id = ? "
        + "and available_ind = 'Y' "
        + "and storage_type_cid = ? "
        + "order by slot_id ";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Vector nextDropDown = new Vector();

    try {
      con = ApiFunctions.getDbConnection();

      if (drawer != null
        && !drawer.equals("")
        && freezer != null
        && !freezer.equals("")
        && room != null
        && !room.equals("")
        && location != null
        && !location.equals("")) {

        pstmt = con.prepareStatement(findSlotIDs);
        pstmt.setString(1, location);
        pstmt.setString(2, room);
        pstmt.setString(3, freezer);
        pstmt.setString(4, drawer);
        pstmt.setString(5, storageTypeCid);
      }
      else if (
        freezer != null
          && !freezer.equals("")
          && room != null
          && !room.equals("")
          && location != null
          && !location.equals("")) {

        pstmt = con.prepareStatement(findDrawerIDs);
        pstmt.setString(1, location);
        pstmt.setString(2, room);
        pstmt.setString(3, freezer);
        pstmt.setString(4, storageTypeCid);
      }
      else if (room != null && !room.equals("") && location != null && !location.equals("")) {
        pstmt = con.prepareStatement(findStorageUnits);
        pstmt.setString(1, location);
        pstmt.setString(2, room);
        pstmt.setString(3, storageTypeCid);
      }
      else {
        pstmt = con.prepareStatement(findRoomIDs);
        pstmt.setString(1, location);
        pstmt.setString(2, storageTypeCid);
      }

      //pstmt.setString(1, geoLoc);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        nextDropDown.add(rs.getString(1));
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return nextDropDown;
  }

  /**
   * Retrieves a list from the ARD_LOOKUP_ALL table for the
   * specified category.  The return is a <code>LegalValueSet</code>
   * where the value is from the LOOKUP_CD column and the
   * display value is from the LOOKUP_CD_DESC column.
   *
   * @return  A <code>LegalValueSet</code> representing the
   *		values in the specified category.
   */
  public LegalValueSet getArdLookup(String category) {
    StringBuffer sql = new StringBuffer(64);
    sql.append("SELECT lookup_cd, lookup_cd_desc");
    sql.append(" FROM ard_lookup_all");
    sql.append(" WHERE category = '");
    sql.append(category);
    sql.append("' ORDER BY lookup_cd_list_order");
    return getList(sql.toString(), false);
  }
  /**
   * Returns all donor institution locations.
   * @return  A <code>LegalValueSet</code> containing all donor institution locations.
   */
  public LegalValueSet getDonorLocations() {
    StringBuffer queryString = new StringBuffer(100);
    queryString.append("SELECT primary_location, Ardais_Acct_Company_Desc FROM Es_Ardais_Account WHERE Ardais_Acct_Type IN ('");
    queryString.append(Constants.ACCOUNT_TYPE_DONOR_AND_CONSUMER);
    queryString.append("')");
    return getList(queryString.toString(), false);
  }

  /**
   * Returns all donor institution locations.
   * @return  A <code>Map</code> containing all donor institution 
   *           locations.
   */
  public Collection getDonorAccounts() {
    StringBuffer queryString = new StringBuffer(100);
    queryString.append("SELECT ardais_acct_key, Ardais_Acct_Company_Desc FROM Es_Ardais_Account ");
    queryString.append("WHERE Ardais_Acct_Type IN ('");
    queryString.append(Constants.ACCOUNT_TYPE_DONOR_AND_CONSUMER);
    queryString.append("')");
    ArrayList results = new ArrayList();
    PreparedStatement pstmt = null;
    Connection con = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString.toString());
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        results.add(new LabelValueBean(rs.getString(2), rs.getString(1)));
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    catch (ApiException e) {
      e.printStackTrace();
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return results;
  }
  /**
   * Returns all finished product formats.
   *
   * @return  A <code>LegalValueSet</code> with all finished product formats.
   */
  public LegalValueSet getFinishedProductFormats() {
    return _finishedProductFormats;
  }

  /**
   * Retrieves the list of orders for the specified account.  The
   * orders are returned as a <code>LegalValueSet</code>, with the
   * order number as the value and a combination of the order number,
   * the orderer's name and the order status as the value.
   *
   * @return  A <code>LegalValueSet</code> of orders.
   */
  public LegalValueSet getPtsOrders(String account) {
    StringBuffer sql = new StringBuffer(128);
    sql.append("SELECT ord.order_number, lookup.lookup_cd_desc, u.user_firstname, u.user_lastname");
    sql.append(" FROM es_ardais_order ord, es_ardais_user u, ard_lookup_all lookup");
    sql.append(" WHERE ord.ardais_acct_key = ?");
    sql.append(" AND ord.ardais_user_id = u.ardais_user_id");
    sql.append(" AND ord.ardais_acct_key = u.ardais_acct_key");
    sql.append(" AND ord.order_status = lookup.lookup_cd");
    sql.append(" AND lookup.category = 'ESORDER'");
    sql.append(" ORDER BY ord.order_number DESC");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    LegalValueSet results = new LegalValueSet();
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, account);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String value = rs.getString("order_number");
        String display =
          value
            + " - "
            + rs.getString("lookup_cd_desc")
            + " - "
            + rs.getString("user_firstname")
            + " "
            + rs.getString("user_lastname");
        results.addLegalValue(value, display);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return results;
  }

  /**
   * Retrieves the list of shopping carts for the specified account.
   * The shopping carts are returned as a <code>LegalValueSet</code>
   * with the user id as the value and the user's name as the display
   * value.
   *
   * @return  A <code>LegalValueSet</code> of shopping carts.
   */
  public LegalValueSet getPtsShoppingCarts(String account) {
    StringBuffer sql = new StringBuffer(64);
    sql.append("SELECT u.ardais_user_id, u.user_firstname||' '||u.user_lastname AS \"Name\"");
    sql.append(" FROM es_shopping_cart c, es_ardais_user u");
    sql.append(" WHERE c.ardais_acct_key = '");
    sql.append(account);
    sql.append("' AND c.ardais_acct_key = u.ardais_acct_key");
    sql.append(" AND c.ardais_user_id = u.ardais_user_id");
    sql.append(" ORDER BY u.user_firstname, u.user_lastname");
    return getList(sql.toString(), false);
  }
  /**
   * Retrieves the list of users for the specified account.  The users
   * are returned as a <code>LegalValueSet</code> with the user id as 
   * the value and the user's name as the display value.
   *
   * @return  A <code>LegalValueSet</code> of users.
   */
  public LegalValueSet getPtsUsersByAccount(String account) {
    StringBuffer sql = new StringBuffer(64);
    sql.append("SELECT u.ardais_user_id, u.user_firstname||' '||u.user_lastname AS name");
    sql.append(" FROM es_ardais_user u");
    sql.append(" WHERE u.ardais_acct_key = '");
    sql.append(account);
    sql.append("' ORDER BY u.user_firstname, u.user_lastname");
    return getList(sql.toString(), false);
  }
  /**
   * getSessionContext method comment
   * @return javax.ejb.SessionContext
   */
  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }

  /**
   * Returns all specimen types (aka appearances).
   * Creation date: (4/11/01 12:13:13 PM)
   * @return  A <code>LegalValueSet</code> with all specimen types.
   */
  public LegalValueSet getSpecimenTypes() {
    return _specimenTypes;
  }
  /**
   * Return the user's real name, given the user's staff id and account id.
   * @param  staffId  the staff id of the user account
   * @param  accountId  the account id of the user account
   * @return  The user's name in the form "first last"
   */
  public String getUserRealName(String staffId, String accountId) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String name = null;
    String query =
      "SELECT ardais_staff_fname, ardais_staff_lname FROM iltds_ardais_staff WHERE ardais_staff_id = ? AND ardais_acct_key = ?";
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, staffId);
      pstmt.setString(2, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        name = rs.getString(1) + " " + rs.getString(2);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return name;
  }
  /**
   * Retrieves a list of all statuses for a given work order type.
   *
   * @return  A <code>LegalValueSet</code> of statuses.
   */
  public LegalValueSet getWorkOrderStatusesForType(String type) {
    StringBuffer sql = new StringBuffer(128);
    sql.append("SELECT status.lookup_cd,");
    sql.append("status.lookup_cd_desc");
    sql.append(" FROM ard_lookup_all types, ard_lookup_all status");
    sql.append(" WHERE types.category = '");
    sql.append(com.ardais.bigr.iltds.helpers.FormLogic.ARD_LOOKUP_WO_TYPE);
    sql.append("' AND types.ref = status.category");
    sql.append(" AND types.lookup_cd = '");
    sql.append(type);
    sql.append("' ORDER BY status.lookup_cd_list_order");
    return getList(sql.toString(), false);
  }
  /**
   * Retrieves a list of the mapping of work order statuses to
   * work order types.
   *
   * @return  A <code>LegalValueSet</code> of the mapping.
   */
  public LegalValueSet getWorkOrderStatusTypeMapping() {
    StringBuffer sql = new StringBuffer(128);
    sql.append("SELECT lookup_cd, ref");
    sql.append(" FROM ard_lookup_all");
    sql.append(" WHERE category = '");
    sql.append(com.ardais.bigr.iltds.helpers.FormLogic.ARD_LOOKUP_WO_TYPE);
    sql.append("'");
    return getList(sql.toString(), false);
  }
  /**
   * Retrieves a list of all work order types and statuses.
   * The return is a <code>LegalValueSetHierarchy</code>, where 
   * the legal value for each type has a sub-<code>LegalValueSet</code>
   * for the valid statues for each type.
   *
   * @return  A <code>LegalValueSetHierarchy</code> representing all
   *		work order types and their valid statuses.
   */
  public LegalValueSetHierarchy getWorkOrderTypesAndStatuses() {
    StringBuffer sql = new StringBuffer(128);
    sql.append("SELECT types.lookup_cd,");
    sql.append("types.lookup_cd_desc,");
    sql.append("status.lookup_cd,");
    sql.append("status.lookup_cd_desc");
    sql.append(" FROM ard_lookup_all types, ard_lookup_all status");
    sql.append(" WHERE types.category = '");
    sql.append(com.ardais.bigr.iltds.helpers.FormLogic.ARD_LOOKUP_WO_TYPE);
    sql.append("' AND types.ref = status.category");
    sql.append(" ORDER BY types.lookup_cd_list_order, status.lookup_cd_list_order");

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    LegalValueSetHierarchy results = new LegalValueSetHierarchy();
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql.toString());
      rs = ApiFunctions.queryDb(pstmt, con);
      String currentTypeCode = "";
      String currentTypeDesc = "";
      LegalValueSet subLvs = null;
      while (rs.next()) {
        String typeCode = rs.getString(1);
        String typeDesc = rs.getString(2);
        if (!typeCode.equals(currentTypeCode)) {
          if (!currentTypeCode.equals("")) {
            results.addLegalValue(currentTypeCode, currentTypeDesc, subLvs);
          }
          subLvs = new LegalValueSet();
          subLvs.addLegalValue(rs.getString(3), rs.getString(4));
          currentTypeCode = typeCode;
          currentTypeDesc = typeDesc;
        }
        else {
          subLvs.addLegalValue(rs.getString(3), rs.getString(4));
        }
      }
      results.addLegalValue(currentTypeCode, currentTypeDesc, subLvs);
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return results;

  }

  /**
   * Return a vector of the active users for the specified account (only ACTIVE users are
   * returned).  The returned vector alternates pairs of values: the user's full name (first/last)
   * followed by their ARDAIS_STAFF_ID value from ILTDS_ARDAIS_STAFF.  The values returned are
   * sorted by increasing last name, and by increasing first name within a last name.
   * 
   * @return the vector of user information
   */
  public Vector lookupArdaisStaffByAccountId(String accountID) {
    return lookupArdaisStaffByAccountId(accountID, true);
  }

  private Vector lookupArdaisStaffByAccountId(String accountID, boolean active) {

    String activeUser = null;
    if (active) {
      activeUser = FormLogic.DB_YES;
    }
    else {
      activeUser = FormLogic.DB_NO;
    }

    String queryString =
      "SELECT staff.ARDAIS_STAFF_FNAME || ' ' || staff.ARDAIS_STAFF_LNAME, staff.ARDAIS_STAFF_ID "
        + "FROM ILTDS_ARDAIS_STAFF staff, ES_ARDAIS_USER ardaisUser "
        + "WHERE staff.ARDAIS_ACCT_KEY = ? "
        + "AND staff.ARDAIS_USER_ID = ardaisUser.ARDAIS_USER_ID "
        + "AND ardaisUser.USER_ACTIVE_IND = ? "
        + "ORDER BY upper(staff.ARDAIS_STAFF_LNAME), upper(staff.ARDAIS_STAFF_FNAME)";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Vector result = new Vector();
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, accountID);
      pstmt.setString(2, activeUser);

      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        result.add(rs.getString(1));
        result.add(rs.getString(2));
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
   * Returns the description of a code in ARD_LOOKUP_ALL, given 
   * the code and category.  If the description is not found,
   * then <code>null</code> is returned.
   *
   * @param  category  the category
   * @param  code  the code
   * @return  The description.
   */
  public String lookupArdLookupDescription(String category, String code) {
    String query =
      "SELECT lookup_cd_desc FROM ard_lookup_all WHERE category = '"
        + category
        + "' AND lookup_cd = '"
        + code
        + "'";
    return doLookup(query);
  }
  /**
   * Insert the method's description here.
   * Creation date: (5/23/2001 11:23:04 AM)
   * @return java.lang.String
   * @param accountKey java.lang.String
   * @exception com.ardais.bigr.api.ApiException The exception description.
   */
  public String lookupDIAccountName(String accountKey) {
    if (accountKey == null) {
      return null;
    }

    String queryString =
      "select ARDAIS_ACCT_COMPANY_DESC from es_ardais_account WHERE ARDAIS_ACCT_KEY = ? ";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String result = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, accountKey);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        result = rs.getString(1);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  /**
   * Insert the method's description here.
   * Creation date: (4/9/01 7:42:23 PM)
   * @return java.lang.String
   * @param diseaseName java.lang.String
   */
  public String lookupDiseaseCode(String diseaseName) {

    if (diseaseName == null) {
      return null;
    }

    String queryString =
      "SELECT LOOKUP_TYPE_CD "
        + "FROM PDC_LOOKUP "
        + "WHERE LOOKUP_TYPE_CD_DESC = '"
        + diseaseName
        + "' ";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String result = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        result = rs.getString(1);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }
  /**
   * Insert the method's description here.
   * Creation date: (4/9/01 7:41:15 PM)
   * @return java.lang.String
   * @param diseaseCode java.lang.String
   */
  public String lookupDiseaseName(String diseaseCode) {
    if (diseaseCode == null) {
      return null;
    }

    String queryString =
      "SELECT LOOKUP_TYPE_CD_DESC "
        + "FROM PDC_LOOKUP "
        + "WHERE LOOKUP_TYPE_CD = '"
        + diseaseCode
        + "' ";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String result = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        result = rs.getString(1);
      }
    }
    catch (SQLException e) {
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
   * Creation date: (9/24/2001 10:12:13 AM)
   * @return boolean
   * @param trackingNumber java.lang.String
   */
  public boolean trackingNumberExists(String trackingNumber) {
    String queryString =
      "select airbill_tracking_number from iltds_manifest where airbill_tracking_number = ? ";


    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, trackingNumber);
      rs = ApiFunctions.queryDb(pstmt, con);
      if (rs.next()) {
        return true;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return false;
  }

  /**
   * Performs the specified query of a list of values and returns the
   * results as a <code>LegalValueSet</code>.  If the legal values have
   * a code and a display value, the query must be formed such that it
   * returns 2 columns, and the coded value is selected first.  Otherwise
   * the query must be formed such that it returns only 1 column.
   * 
   * Creation date: (10/02/2001 2:30:06 PM)
   * @param query  the query
   * @return  A <code>LegalValueSet</code> containing the results of the query.
   */
  private LegalValueSet getList(String query, boolean defaultToSelect) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    LegalValueSet results = new LegalValueSet();

    if (defaultToSelect) {
      results.addLegalValue("-1", "Select");
    }

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      rs = ApiFunctions.queryDb(pstmt, con);
      ResultSetMetaData meta = rs.getMetaData();
      if (meta.getColumnCount() == 1) {
        while (rs.next()) {
          results.addLegalValue(rs.getString(1));
        }
      }
      else {
        while (rs.next()) {
          results.addLegalValue(rs.getString(1), rs.getString(2));
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return results;
  }

  /**
   * Retrieves the list of potential clients for a project.
   *
   * @return  A <code>LegalValueSet</code> of project clients.
   */
  public LegalValueSet getPtsClients() {
    StringBuffer sql = new StringBuffer(64);
    sql.append("SELECT ardais_acct_key, ardais_acct_company_desc");
    sql.append(" FROM es_ardais_account");
    sql.append(" WHERE ardais_acct_type IN ('");
    sql.append(Constants.ACCOUNT_TYPE_CONSUMER);
    sql.append("', '");
    sql.append(Constants.ACCOUNT_TYPE_DONOR_AND_CONSUMER);
    sql.append("', '");
    sql.append(Constants.ACCOUNT_TYPE_SYSTEM_OWNER);
    sql.append("') ORDER BY ardais_acct_company_desc");
    return getList(sql.toString(), false);
  }

  /**
   * Insert the method's description here.
   * Creation date: (7/26/2002 10:29:42 AM)
   * @return java.lang.String
   * @param slideId java.lang.String
   * @exception com.ardais.bigr.api.ApiException The exception description.
   */
  public String getSampleFromSlide(String slideId) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sampleId = null;
    String query = "SELECT  select sample_barcode_id from lims_slide where slide_id =  ?";
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, slideId);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        sampleId = rs.getString(1);
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return sampleId;
  }

  /**
   * Find the manifest that have been created or shipped but not yet received and that are
   * visible to the specified user (the visible manifests are those created by a user with the
   * same account as the specified user).
   * 
   * @param userID The id of the user performing the transaction.
   * @return A vector of hashtables, see the implementation for details.
   */
  public Vector findPrintableManifests(String userID) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Vector manifests = new Vector();

    try {
      con = ApiFunctions.getDbConnection();

      String query =
        "select distinct m.manifest_number, m.shipment_status, "
          + "decode(m.airbill_tracking_number, null, 'No Tracking Number Specified', m.airbill_tracking_number) "
          + "from iltds_manifest m, es_ardais_user login, es_ardais_user creator "
          + "where m.shipment_status in ('"
          + FormLogic.MNFT_MFCREATED
          + "', '"
          + FormLogic.MNFT_MFPACKAGED
          + "', '"
          + FormLogic.MNFT_MFSHIPPED
          + "') "
          + "  and m.MNFT_CREATE_STAFF_ID = creator.ARDAIS_USER_ID "
          + "  and creator.ardais_acct_key = login.ardais_acct_key   "
          + "  and login.ARDAIS_USER_ID = ? "
          + "order by m.manifest_number ";

      pstmt = con.prepareStatement(query);

      pstmt.setString(1, userID);

      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        Hashtable manifest = new Hashtable();
        manifest.put("manifest", rs.getString(1));
        manifest.put("status", rs.getString(2));
        manifest.put("waybill", rs.getString(3));
        manifests.add(manifest);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return manifests;
  }
}
