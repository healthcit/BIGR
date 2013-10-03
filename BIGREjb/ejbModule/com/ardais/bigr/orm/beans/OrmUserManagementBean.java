package com.ardais.bigr.orm.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.es.beans.ArdaisuserAccessBean;
import com.ardais.bigr.es.beans.ArdaisuserKey;
import com.ardais.bigr.es.beans.SequenceGenAccessBean;
import com.ardais.bigr.javabeans.PrivilegeDto;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.orm.helpers.FormLogic;
import com.ardais.bigr.security.SecurityInfo;

/**
 * This is a Session Bean Class
 */
public class OrmUserManagementBean implements SessionBean {
  private javax.ejb.SessionContext mySessionCtx = null;
  final static long serialVersionUID = 3206093459760846163L;

  public void createNewUser(UserDto userData, String createdBy) {

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      SequenceGenAccessBean addressSeq = new SequenceGenAccessBean();
      int addressID = addressSeq.getSeqNextVal(com.ardais.bigr.es.helpers.FormLogic.SEQ_ADDRESS_ID);

      con = ApiFunctions.getDbConnection();

      String qStmt1 =
        " INSERT INTO ARDAIS_ADDRESS ("
          + " ADDRESS_ID, ARDAIS_ACCT_KEY, ADDRESS_TYPE, ADDRESS_1, ADDRESS_2, "
          + " ADDR_CITY, ADDR_STATE, ADDR_ZIP_CODE, "
          + " ADDR_COUNTRY) VALUES (?,?,?,?,?,?,?,?,?)";

      {
        pstmt = con.prepareStatement(qStmt1);
        pstmt.setInt(1, addressID);
        pstmt.setString(2, userData.getAccountId().trim());
        pstmt.setString(3, "User");
        pstmt.setString(4, userData.getAddress().getAddress1().trim());
        pstmt.setString(5, userData.getAddress().getAddress2().trim());
        pstmt.setString(6, userData.getAddress().getCity().trim());
        pstmt.setString(7, userData.getAddress().getState().trim());
        pstmt.setString(8, userData.getAddress().getZipCode().trim());
        pstmt.setString(9, userData.getAddress().getCountry().trim());

        pstmt.executeUpdate();

        ApiFunctions.close(pstmt);
        pstmt = null;
      }

      String qStmt =
        " INSERT INTO ES_ARDAIS_USER ( "
          + " ARDAIS_USER_ID, ARDAIS_ACCT_KEY, PASSWORD, "
          + " USER_LASTNAME, USER_FIRSTNAME, USER_TITLE, USER_FUNCTIONAL_TITLE, USER_AFFILIATION, USER_PHONE_NUMBER, "
          + " USER_PHONE_EXT, USER_FAX_NUMBER, USER_MOBILE_NUMBER, USER_EMAIL_ADDRESS, CREATED_BY, "
          + " CREATE_DATE, USER_ADDRESS_ID, USER_ACTIVE_IND, PASSWORD_POLICY_CID, USER_DEPARTMENT) "
          + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


      {
        pstmt = con.prepareStatement(qStmt);
        pstmt.setString(1, userData.getUserId().trim());
        pstmt.setString(2, userData.getAccountId().trim());
        pstmt.setString(3, ApiFunctions.encryptPassword(userData.getPassword1().trim()));
        pstmt.setString(4, userData.getLastName().trim());
        pstmt.setString(5, userData.getFirstName().trim());
        pstmt.setString(6, userData.getTitle().trim());
        pstmt.setString(7, userData.getFunctionalTitle().trim());
        pstmt.setString(8, userData.getAffiliation().trim());
        pstmt.setString(9, userData.getPhoneNumber().trim());
        pstmt.setString(10, userData.getExtension().trim());
        pstmt.setString(11, userData.getFaxNumber().trim());
        pstmt.setString(12, userData.getMobileNumber().trim());
        pstmt.setString(13, userData.getEmail().trim());
        pstmt.setString(14, createdBy.trim());
        pstmt.setTimestamp(15, new java.sql.Timestamp(new java.util.Date().getTime()));
        pstmt.setInt(16, addressID);
        pstmt.setString(17, userData.getActiveYN().trim());
        pstmt.setString(18, userData.getPasswordPolicy().trim());
        pstmt.setString(19, userData.getDepartment().trim());

        pstmt.executeQuery();

        ApiFunctions.close(pstmt);
        pstmt = null;
      }

      {
        UseraccessmoduleAccessBean userObjOrmEB = new UseraccessmoduleAccessBean();
        userObjOrmEB = new UseraccessmoduleAccessBean();
        userObjOrmEB.setInit_argArdais_acct_key(userData.getAccountId().trim());
        userObjOrmEB.setInit_argArdais_user_id(userData.getUserId().trim());
        userObjOrmEB.setInit_argObjects(
          new ObjectsKey(SecurityInfo.PRIV_CHANGE_PROFILE));
        userObjOrmEB.setInit_argCreated_by(createdBy);
        userObjOrmEB.setInit_argUpdated_by(createdBy);
        userObjOrmEB.setInit_argUpdate_date(
          new java.sql.Timestamp((new java.util.Date()).getTime()));
        userObjOrmEB.setInit_argCreate_date(
          new java.sql.Timestamp((new java.util.Date()).getTime()));
        userObjOrmEB.commitCopyHelper();
      }
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
   * Method to find all privileges
   * @return a Map of Lists of PrivilegeDtos.  The Map is keyed by the various
   * functional areas of the system
   */
  public Map getAllPrivileges() {
      
    Map privilegeMap = new HashMap();
    privilegeMap.put(FormLogic.ALL, new ArrayList());
    privilegeMap.put(FormLogic.ESTORE, new ArrayList());
    privilegeMap.put(FormLogic.ILTDS, new ArrayList());
    privilegeMap.put(FormLogic.ORM, new ArrayList());
    privilegeMap.put(FormLogic.PDC, new ArrayList());
    privilegeMap.put(FormLogic.LIMS, new ArrayList());
    privilegeMap.put(FormLogic.IMPORT, new ArrayList());

    //retrieve all privileges, and put them into the "All" list
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement("select distinct ao.object_id, ao.object_description from ard_objects ao order by ao.object_description");
      rs = ApiFunctions.queryDb(pstmt, con);
      List allPrivilegeList = (List)privilegeMap.get(FormLogic.ALL);
      while (rs.next()) {
        PrivilegeDto privilege = new PrivilegeDto();
        privilege.setId(rs.getString("object_id"));
        privilege.setDescription(rs.getString("object_description"));
        allPrivilegeList.add(privilege);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    //now, create lists of privs that correspond to each functional area of the system
    Iterator iterator = ((List)privilegeMap.get(FormLogic.ALL)).iterator();
    while (iterator.hasNext()) {
      PrivilegeDto privilege = (PrivilegeDto)iterator.next();
      String id = privilege.getId();
      int firstUnderBar = id.indexOf("_");
      int secondUnderBar = id.indexOf("_", firstUnderBar + 1);
      String group = id.substring(firstUnderBar + 1, secondUnderBar);
      if (privilegeMap.containsKey(group)) {
        ((List)privilegeMap.get(group)).add(privilege);
      }
    }
    return privilegeMap;
  }
  
  /**
   * Method to find a list of privileges
   * @param privilegeIds List - a list of privilege ids to be retrieved
   * @return a List of PrivilegeDtos.
   */
  public List getPrivilegesById(List privilegeIds) {

    List returnValue = new ArrayList();
    if (!ApiFunctions.isEmpty(privilegeIds)) {
      StringBuffer query = new StringBuffer(200);
      query.append("select distinct object_id, object_description from ard_objects ");
      query.append(" where object_id in ");
      query.append(ApiFunctions.makeBindParameterList(privilegeIds.size()));
      query.append(" order by object_description");
      Connection con = null;
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      try {
        con = ApiFunctions.getDbConnection();
        pstmt = con.prepareStatement(query.toString());
        ApiFunctions.bindBindParameterList(pstmt, 1, privilegeIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          PrivilegeDto privilege = new PrivilegeDto();
          privilege.setId(rs.getString("object_id"));
          privilege.setDescription(rs.getString("object_description"));
          returnValue.add(privilege);
        }
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
    return returnValue;
  }

/**
 * Method to find the privileges assigned to a user
 * @param userID
 * @param accountID
 * @return a List of PrivilegeDtos representing the privileges assigned to the specified user
 */
  public List getPrivilegesAssignedToUser(String userID, String accountID) {

    List privilegeList = new ArrayList();

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      //retrieve the privileges that have been granted to this user
      StringBuffer assignedPrivilegeQuery = new StringBuffer(300);
      assignedPrivilegeQuery.append("select distinct ao.object_id, ao.object_description");
      assignedPrivilegeQuery.append(" from ard_objects ao, ard_user_access_module au");
      assignedPrivilegeQuery.append(" where ao.object_id = au.object_id");
      assignedPrivilegeQuery.append(" and lower(au.ardais_acct_key) = lower(?)");
      assignedPrivilegeQuery.append(" and lower(au.ardais_user_id) = lower(?)");
      assignedPrivilegeQuery.append(" order by ao.object_description");
      pstmt = con.prepareStatement(assignedPrivilegeQuery.toString());
      pstmt.setString(1, accountID);
      pstmt.setString(2, userID);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        if (rs.getString("object_description") != null) {
          PrivilegeDto privilege = new PrivilegeDto();
          privilege.setId(rs.getString("object_id"));
          privilege.setDescription(rs.getString("object_description"));
          privilegeList.add(privilege);
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return privilegeList;
  }

  /**
   * Returns <code>true</code> if user ID is a valid user in BIGR. Else 
   * returns <code>false</code>.
   * 
   * @param userID the user id
   */
  public boolean isUserExisting(String userID) {

    boolean existing = false;

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    try {
      con = ApiFunctions.getDbConnection();

      String qStmt =
        " SELECT  ARDAIS_USER_ID FROM ES_ARDAIS_USER WHERE " + " UPPER(ARDAIS_USER_ID) = UPPER(?)";

      pstmt = con.prepareStatement(qStmt);
      pstmt.setString(1, userID.trim());
      rs = ApiFunctions.queryDb(pstmt, con);

      if ((rs.next())) {
        existing = true;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return existing;
  }

  /**
   * Returns <code>List</code> of user IDs in BIGR.
   * @return <code>List</code> userIDs
   */
  public List getAllUserIds() {

    List list = new ArrayList();

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    try {
      con = ApiFunctions.getDbConnection();
      String qStmt =
        " SELECT ARDAIS_USER_ID FROM ES_ARDAIS_USER " + " ORDER BY UPPER(ARDAIS_USER_ID) ASC";

      pstmt = con.prepareStatement(qStmt);
      rs = ApiFunctions.queryDb(pstmt, con);

      while ((rs.next())) {
        list.add(rs.getString("ARDAIS_USER_ID"));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return list;
  }

  /**
   * Returns <code>List</code> of <code>UserDto</code> beans
   * containing information about the users who have the specified privilege in BIGR.
   * @return <code>List</code> users
   */
  public List findUsersWithPrivilege(String privilege) {

    List result = new ArrayList();
    
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      StringBuffer sb = new StringBuffer();
      //gets users who have privilege directly
      sb.append("select eau.ardais_user_id, eau.user_firstname, eau.user_lastname, eau.ardais_acct_key, ");
      sb.append("  upper(eau.user_firstname) upper_firstname, upper(eau.user_lastname) upper_lastname, upper(eau.ardais_acct_key) upper_account ");
      sb.append("from es_ardais_user eau, ard_user_access_module uamodule ");
      sb.append("where uamodule.ardais_user_id = eau.ardais_user_id and ");
      sb.append("uamodule.object_id = ? ");
      String queryString = sb.toString();
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, privilege);
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next()) {
        UserDto user = new UserDto();
        user.setUserId(rs.getString("ardais_user_id"));
        user.setAccountId(rs.getString("ardais_acct_key"));
        user.setFirstName(rs.getString("user_firstname"));
        user.setLastName(rs.getString("user_lastname"));
        result.add(user);
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
   * Method to remove privileges from a user
   * @param userId String - the id of the user to whom the privileges are being revoked
   * @param accountId String - the id of the account of the user to whom the privileges are being revoked
   * @param privilegeIds List - a list of privilege ids being revoked
   */
  public void removePrivilegesFromUser(String userId, String accountId, List privilegeIds) {

    //if there are no privileges being removed, just return
    if (privilegeIds.size() == 0) {
      return;
    }

    //remove the privileges
    StringBuffer query = new StringBuffer(200);
    query.append("DELETE FROM ARD_USER_ACCESS_MODULE WHERE ARDAIS_ACCT_KEY = ? AND ARDAIS_USER_ID = ? ");
    query.append(" AND OBJECT_ID IN ");
    query.append(ApiFunctions.makeBindParameterList(privilegeIds.size()));

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountId);
      pstmt.setString(2, userId);
      ApiFunctions.bindBindParameterList(pstmt, 3, privilegeIds);
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
   * Method to grant privileges to a user
   * @param userId String - the id of the user to whom the privileges are being granted
   * @param accountId String - the id of the account of the user to whom the privileges are being granted
   * @param privilegeIds List - a list of privilege ids to be granted to the user
   * @param updateUserId String - the id of the user granting the privileges
   */
  public void addPrivilegesToUser(String userId, String accountId, List privilegeIds, String updateUserId) {
    
    //if there are no privileges to add, just return
    if (privilegeIds.size() == 0) {
      return;
    }
    
    //get the currently assigned privs, so that we don't assign a privilege to a user
    //if they already have it
    List currentPrivileges = getPrivilegesAssignedToUser(userId, accountId);
    List currentPrivilegeIds = new ArrayList();
    Iterator iterator = currentPrivileges.iterator();
    while (iterator.hasNext()) {
      currentPrivilegeIds.add(((PrivilegeDto)iterator.next()).getId());
    }
    
    //add the privileges
    try {
      for (int i=0; i < privilegeIds.size(); i++) {
        String privilegeId = (String)privilegeIds.get(i);
        if (!currentPrivilegeIds.contains(privilegeId)) {
          UseraccessmoduleAccessBean userObjOrmEB = new UseraccessmoduleAccessBean();
          userObjOrmEB.setInit_argArdais_acct_key(accountId.trim());
          userObjOrmEB.setInit_argArdais_user_id(userId.trim());
          userObjOrmEB.setInit_argObjects(new ObjectsKey(privilegeId));
          userObjOrmEB.setInit_argCreated_by(updateUserId);
          userObjOrmEB.setInit_argUpdated_by(updateUserId);
          userObjOrmEB.setInit_argUpdate_date(
            new java.sql.Timestamp((new java.util.Date()).getTime()));
          userObjOrmEB.setInit_argCreate_date(
            new java.sql.Timestamp((new java.util.Date()).getTime()));
          userObjOrmEB.commitCopyHelper();
          userObjOrmEB.refreshCopyHelper();
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  public String verifyAnswer(
    String userID,
    String accountID,
    String verificationAnswer,
    String password) {

    String emailYN = null;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      boolean answer = false;

      StringBuffer query = new StringBuffer(100);
      query.append("select USER_EMAIL_ADDRESS,USER_VERIF_ANSWER from es_ardais_user ");
      query.append(" where USER_ACTIVE_IND = 'Y' and ARDAIS_ACCT_KEY = ? and ARDAIS_USER_ID = ?");
      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountID.trim());
      pstmt.setString(2, userID.trim());
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        if (rs.getString("USER_VERIF_ANSWER").trim().equalsIgnoreCase(verificationAnswer)) {
          answer = true;
          emailYN = rs.getString("USER_EMAIL_ADDRESS");
        }
      }
      rs.close();
      rs = null;
      pstmt.close();
      pstmt = null;

      if (answer) {
        query = new StringBuffer(100);
        query.append("update es_ardais_user set PASSWORD = ?");
        query.append(" where ARDAIS_ACCT_KEY = ? and ARDAIS_USER_ID = ?");
        pstmt = con.prepareStatement(query.toString());
        pstmt.setString(1, password.trim());
        pstmt.setString(2, accountID.trim());
        pstmt.setString(3, userID.trim());
        rs = ApiFunctions.queryDb(pstmt, con);

        rs.close();
        rs = null;
        pstmt.close();
        pstmt = null;
      }
      else {
        //update the number of failed security question answer attempts made for this user
        try {
          ArdaisuserAccessBean ardaisUserEB = new ArdaisuserAccessBean(new ArdaisuserKey(userID, new ArdaisaccountKey(accountID)));
          int failedAttempts = ardaisUserEB.getConsecutive_failed_answers().intValue() + 1;
          ardaisUserEB.setConsecutive_failed_answers(new Integer(failedAttempts));
          ardaisUserEB.commitCopyHelper();
        }
        catch (Exception e){
          //do nothing here, as perhaps the attempt user and/or account is invalid so no update
          //to make
        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return emailYN;
  }

  public String getVerificationQuestion(String userID, String accountID) {

    String verifyQuestion = null;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      con = ApiFunctions.getDbConnection();

      StringBuffer query = new StringBuffer(100);
      query.append("select USER_VERIF_QUESTION from es_ardais_user");
      query.append(" where ARDAIS_ACCT_KEY = ? and ARDAIS_USER_ID = ?");

      pstmt = con.prepareStatement(query.toString());
      pstmt.setString(1, accountID.trim());
      pstmt.setString(2, userID.trim());
      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        verifyQuestion = rs.getString("USER_VERIF_QUESTION");
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return verifyQuestion;
  }

  public void ejbActivate() throws java.rmi.RemoteException {
  }

  public void ejbCreate() throws javax.ejb.CreateException, javax.ejb.EJBException {
  }

  public void ejbPassivate() throws java.rmi.RemoteException {
  }

  public void ejbRemove() throws java.rmi.RemoteException {
  }

  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }

  public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException {
    mySessionCtx = ctx;
  }
}