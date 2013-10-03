package com.ardais.bigr.orm.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.ejb.SessionBean;

import org.apache.commons.digester.Digester;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.es.beans.SequenceGenAccessBean;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.javabeans.PrivilegeDto;
import com.ardais.bigr.orm.databeans.ConsentDatabean;
import com.ardais.bigr.orm.databeans.IrbProtocolDatabean;
import com.ardais.bigr.orm.helpers.AccountData;
import com.ardais.bigr.orm.helpers.FormLogic;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BigrXMLParserBase;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.LogicalRepositoryUtils;

public class AccountSrchSBBean implements SessionBean {
  private javax.ejb.SessionContext mySessionCtx = null;
  final static long serialVersionUID = 3206093459760846163L;

  public void addConsentVertoIrb(String irbProtocolId, String consentVer, String expirationDate) {
    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      SequenceGenAccessBean consentVerSeq = new SequenceGenAccessBean();

      String sql =
        " insert into es_ardais_consentver "
          + "(irbprotocol_id, consent_version_id, consent_version, expiration_date, active) "
          + "values (?,?,?,?,'N')";

      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(sql);

      pstmt.setInt(1, Integer.parseInt(irbProtocolId));
      pstmt.setInt(
        2,
        consentVerSeq.getSeqNextVal(FormLogic.SEQ_CONSENT_VERSION_ID));
      pstmt.setString(3, consentVer);
      pstmt.setDate(4, ApiFunctions.safeDate(expirationDate));

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

  public void addIRBandConsentVer(
    String accountID,
    String irbProtocol,
    String policyId,
    String consentVersion,
    String expirationDate) {

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      int irbProtocolID;
      {
        SequenceGenAccessBean consentVerSeq = new SequenceGenAccessBean();
        irbProtocolID =
          consentVerSeq.getSeqNextVal(com.ardais.bigr.orm.helpers.FormLogic.SEQ_IRBPROTOCOL_ID);
      }

      String sql =
        "insert into es_ardais_irb (ardais_acct_key, irbprotocol_id, irbprotocol, policy_id) "
          + "values (?,?,?,?)";

      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, accountID);
      pstmt.setInt(2, irbProtocolID);
      pstmt.setString(3, irbProtocol);
      pstmt.setInt(4, Integer.parseInt(policyId));

      pstmt.executeUpdate();

      addConsentVertoIrb(String.valueOf(irbProtocolID), consentVersion, expirationDate);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  public void ejbActivate() throws java.rmi.RemoteException {
  }
  public void ejbCreate() throws javax.ejb.CreateException, javax.ejb.EJBException {
  }
  public void ejbPassivate() throws java.rmi.RemoteException {
  }
  public void ejbRemove() throws java.rmi.RemoteException {
  }

  public String getConsentText(String consentVersionId) {
    String consentText = "";

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    try {
      String query =
        " select cons.consent_text "
          + " from es_ardais_consentver cons "
          + " where cons.CONSENT_VERSION_ID = ? ";

      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(query);
      pstmt.setInt(1, Integer.parseInt(consentVersionId));

      rs = ApiFunctions.queryDb(pstmt, con);

      if (rs.next()) {
        consentText = ApiFunctions.getStringFromClob(rs.getClob("CONSENT_TEXT"));
      }
      else {
        throw new ApiException(
          "There is no consent version with CONSENT_VERSION_ID = " + consentVersionId);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return consentText;
  }

  public String getConsentTextbyConsentID(String consentId) {
    String consentText = null;

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    try {
      String query =
        " select v.consent_text "
          + " from es_ardais_consentver v, iltds_informed_consent c "
          + " where c.consent_id = ? "
          + " and c.consent_version_id = v.consent_version_id ";

      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(query);
      pstmt.setString(1, consentId);

      rs = ApiFunctions.queryDb(pstmt, con);

      // The result set might be empty.  That's valid, for example it will be empty
      // for unlinked cases since they don't have consents.
      //
      if (rs.next()) {
        consentText = ApiFunctions.getStringFromClob(rs.getClob("CONSENT_TEXT"));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return consentText;
  }

  public Vector getIrbConsentVer(String accountID) {
    Vector irbConsentVector = new Vector();

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    try {
      String query =
        " select irb.IRBPROTOCOL_ID, irb.IRBPROTOCOL, irb.POLICY_ID, p.NAME, cons.EXPIRATION_DATE,"
          + "   cons.CONSENT_VERSION_ID, cons.CONSENT_VERSION, cons.ACTIVE"
          + " from es_ardais_irb irb, es_ardais_consentver cons, ard_policy p "
          + " where irb.IRBPROTOCOL_ID = cons.IRBPROTOCOL_ID "
          + "   and irb.policy_id = p.id "
          + "   and irb.ARDAIS_ACCT_KEY = ? "
          + " order by irb.IRBPROTOCOL_ID, cons.CONSENT_VERSION_ID";

      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(query);
      pstmt.setString(1, accountID);

      rs = ApiFunctions.queryDb(pstmt, con);

      IrbProtocolDatabean irbProtocol1 = new IrbProtocolDatabean();
      int irbTemp = 0;
      List consentList = new ArrayList();

      while (rs.next()) {
        int irbId = rs.getInt("IRBPROTOCOL_ID");
        int consentID = rs.getInt("CONSENT_VERSION_ID");
        String irbProtocol = rs.getString("IRBPROTOCOL");
        String policyId = rs.getString("POLICY_ID");
        String policyName = rs.getString("NAME");
        String consentVersion = rs.getString("CONSENT_VERSION");
        String expirationDate = ApiFunctions.sqlDateToString(rs.getDate("EXPIRATION_DATE"));
        boolean active = false;

        if ("Y".equalsIgnoreCase(ApiFunctions.safeTrim(rs.getString("ACTIVE")))) {
          active = true;
        }

        if (irbTemp == 0) {
          irbProtocol1.setIrbId(irbId);
          irbProtocol1.setIrbName(irbProtocol);
          irbProtocol1.setPolicyId(policyId);
          irbProtocol1.setPolicyName(policyName);
        }
        else if (irbTemp != irbId) {
          irbProtocol1.setConsentCollection(consentList);
          irbConsentVector.addElement(irbProtocol1);

          consentList = new Vector();
          irbProtocol1 = new IrbProtocolDatabean();

          irbProtocol1.setIrbId(irbId);
          irbProtocol1.setIrbName(irbProtocol);
          irbProtocol1.setPolicyId(policyId);
          irbProtocol1.setPolicyName(policyName);
        }

        ConsentDatabean consent1 = new ConsentDatabean();
        consent1.setActive(active);
        consent1.setConsentVersionID(consentID);
        consent1.setConsentVersionName(consentVersion);
        consent1.setExpirationDate(ApiFunctions.safeString(expirationDate));
        consentList.add(consent1);

        irbTemp = irbId;
      } // end while

      if (irbTemp != 0) {
        irbProtocol1.setConsentCollection(consentList);
        irbConsentVector.addElement(irbProtocol1);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return irbConsentVector;
  }

  public int getNewAddress(String accountId, Vector addressVec) {
    Connection conn = ApiFunctions.getDbConnection();
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    SequenceGenAccessBean addressSeq = new SequenceGenAccessBean();
    int val = 0;

    //Added to generate address by sequence.

    try {
      Hashtable addHash = (Hashtable) addressVec.get(0);
      String strLast = (String) addHash.get("strLast");
      String strFirst = (String) addHash.get("strFirst");
      String strMid = (String) addHash.get("strMid");
      String strAdd1 = (String) addHash.get("strAdd1");
      String strAdd2 = (String) addHash.get("strAdd2");
      String strCity = (String) addHash.get("strCity");
      String strState = (String) addHash.get("strState");
      String strZip = (String) addHash.get("strZip");
      String strCount = (String) addHash.get("strCount");
      String strShip = (String) addHash.get("strShip");
      String strBill = (String) addHash.get("strBill");
      String strContact = (String) addHash.get("strContact");

      String strAddContact =
        " INSERT INTO ardais_address(Address_Id, Ardais_Acct_Key, Address_Type, Address_1, Address_2, Addr_City, "
          + " Addr_State, Addr_Zip_Code, Addr_Country, FIRST_NAME, LAST_NAME, MIDDLE_NAME) "
          + " Values(?, ?, ? , ? , ? , ? , ? , ? , ?, ?,?,?) ";
      pstmt = conn.prepareStatement(strAddContact);
      //rs = ApiFunctions.queryDb(pstmt, conn);
      if (!strShip.equals("")) {
        val = addressSeq.getSeqNextVal(com.ardais.bigr.es.helpers.FormLogic.SEQ_ADDRESS_ID);
        pstmt.setInt(1, val);
        pstmt.setString(2, accountId);
        pstmt.setString(3, strShip);
        pstmt.setString(4, strAdd1);
        pstmt.setString(5, strAdd2);
        pstmt.setString(6, strCity);
        pstmt.setString(7, strState);
        pstmt.setString(8, strZip);
        pstmt.setString(9, strCount);
        pstmt.setString(10, strFirst);
        pstmt.setString(11, strLast);
        pstmt.setString(12, strMid);
        rs = ApiFunctions.queryDb(pstmt, conn);
      }

      if (!strBill.equals("")) {
        val = addressSeq.getSeqNextVal(com.ardais.bigr.es.helpers.FormLogic.SEQ_ADDRESS_ID);
        pstmt.setInt(1, val);
        pstmt.setString(2, accountId);
        pstmt.setString(3, strBill);
        pstmt.setString(4, strAdd1);
        pstmt.setString(5, strAdd2);
        pstmt.setString(6, strCity);
        pstmt.setString(7, strState);
        pstmt.setString(8, strZip);
        pstmt.setString(9, strCount);
        pstmt.setString(10, strFirst);
        pstmt.setString(11, strLast);
        pstmt.setString(12, strMid);
        rs = ApiFunctions.queryDb(pstmt, conn);
      }

      if (!strContact.equals("")) {
        val = addressSeq.getSeqNextVal(com.ardais.bigr.es.helpers.FormLogic.SEQ_ADDRESS_ID);
        pstmt.setInt(1, val);
        pstmt.setString(2, accountId);
        pstmt.setString(3, strContact);
        pstmt.setString(4, strAdd1);
        pstmt.setString(5, strAdd2);
        pstmt.setString(6, strCity);
        pstmt.setString(7, strState);
        pstmt.setString(8, strZip);
        pstmt.setString(9, strCount);
        pstmt.setString(10, strFirst);
        pstmt.setString(11, strLast);
        pstmt.setString(12, strMid);
        rs = ApiFunctions.queryDb(pstmt, conn);
        ResultSet rs1;
        String strQuery =
          "SELECT contact_Address_Id from  Es_Ardais_Account where Ardais_Acct_Key = ?";
        pstmt = conn.prepareStatement(strQuery);
        pstmt.setString(1, accountId);
        rs1 = ApiFunctions.queryDb(pstmt, conn);
        if (rs1.next()) {
          String strVal = (String) rs1.getString(1);
          rs1.close();
          if (strVal == null) {
            ResultSet rs2;
            String strAddId =
              "UPDATE Es_Ardais_Account SET contact_Address_Id = ? WHERE Ardais_Acct_Key = ? ";
            pstmt = conn.prepareStatement(strAddId);
            pstmt.setInt(1, val);
            pstmt.setString(2, accountId);
            rs2 = ApiFunctions.queryDb(pstmt, conn);
            rs2.close();
          }
        }
      }
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    catch (Exception e) {
      throw new ApiException(e);
    }
    finally {
      ApiFunctions.close(conn, pstmt, rs);
    }
    return val;
  }

  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }

  public void saveConsentExpirationDate(String accountID, String[] consentVerList, String[] expirationDates, String updatedBy) {
    
    java.sql.Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt;
    String qStmt2 =
      " update es_ardais_consentver set expiration_date = ?, UPDATED_BY = ?, UPDATE_DATE_TIME = ? "
        + " where CONSENT_VERSION_ID = ? ";

    try {

      pstmt = con.prepareStatement(qStmt2);
      if ((consentVerList != null) && (consentVerList.length >= 0)) {
        for (int i = 0; i < consentVerList.length; i++) {
          pstmt.setDate(1, ApiFunctions.safeDate(expirationDates[i]));
          pstmt.setString(2, updatedBy);
          pstmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
          pstmt.setInt(4, new Integer(consentVerList[i].trim()).intValue());

          pstmt.executeUpdate();

        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }  }

  public void saveActiveConsentVer(String accountID, String[] consentVerList, String updatedBy) {

    java.sql.Connection con = ApiFunctions.getDbConnection();
    PreparedStatement pstmt;

    String qStmt1 =
      " update es_ardais_consentver set active = 'N', UPDATED_BY = ?, UPDATE_DATE_TIME = ?  "
        + " where IRBPROTOCOL_ID in(select IRBPROTOCOL_id from es_ardais_irb where ARDAIS_ACCT_KEY = ?)";

    try {
      pstmt = con.prepareStatement(qStmt1);
      pstmt.setString(1, updatedBy);
      pstmt.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
      pstmt.setString(3, accountID);
      pstmt.executeUpdate();

    }
    catch (Exception e) {
      throw new ApiException();
    }

    /*
    
    	String strConsentVerList = "";
    	for (int i =0; i<consentVerList.length; i++)
    	{
    		if(i == (consentVerList.length -1))
    		{
    			strConsentVerList = strConsentVerList + consentVerList[i];
    			}
    		else
    		{
    		strConsentVerList = strConsentVerList + consentVerList[i]+",";
    		}
    	}
    
    */
    String qStmt2 =
      " update es_ardais_consentver set active = 'Y', UPDATED_BY = ?, UPDATE_DATE_TIME = ? "
        + " where CONSENT_VERSION_ID = ? ";

    try {

      pstmt = con.prepareStatement(qStmt2);
      if ((consentVerList != null) && (consentVerList.length >= 0)) {
        for (int i = 0; i < consentVerList.length; i++) {
          pstmt.setString(1, updatedBy);
          pstmt.setTimestamp(2, new java.sql.Timestamp(new java.util.Date().getTime()));
          pstmt.setInt(3, new Integer(consentVerList[i].trim()).intValue());

          pstmt.executeUpdate();

        }
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }


  public int setConsentText(
    String irbProtocolID,
    String consentID,
    String consentText,
    String userID) {
    Connection conn = ApiFunctions.getDbConnection();
    ResultSet rs;
    PreparedStatement pstmt;
    TemporaryClob clob = null;

    try {
      String query =
        " update ES_ARDAIS_CONSENTVER"
          + " set CONSENT_TEXT = ?, UPDATED_BY = ?, UPDATE_DATE_TIME = ?  "
          + " where IRBPROTOCOL_ID =  ? "
          + " and CONSENT_VERSION_ID = ? ";

      pstmt = conn.prepareStatement(query);
      clob = new TemporaryClob(conn, consentText);
      pstmt.setClob(1, clob.getSQLClob());
      pstmt.setString(2, userID);
      pstmt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
      pstmt.setInt(4, Integer.parseInt(irbProtocolID));
      pstmt.setInt(5, Integer.parseInt(consentID));
      rs = ApiFunctions.queryDb(pstmt, conn);
      rs.close();
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(clob, conn);
      ApiFunctions.closeDbConnection(conn);
    }
    return 0;
  }

  public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException {
    mySessionCtx = ctx;
  }

  /**
   * Method to find the privileges that can be managed by an account administrator for an account
   * @param accountID
   * @return a List of PrivilegeDtos representing the privileges managable by an account 
   * administrator for an account
   */
  public List getPrivilegesAssignedToAccount(String accountId) {

    AccountData accountData = getAccountData(accountId);
    List returnValue = accountData.getAssignablePrivileges();

    //because the account_data xml string holds privilege ids and not object ids, find the
    //privileges that have those privilege ids.  This is likely to be a one to one match,
    //but is not guaranteed to be because we may have altered the object_id of a privilege
    //to change it's ordering in the left hand menu.  Also, this provides an opportunity to
    //remove any privileges that may have been deleted from the ard_objects table but have
    //not yet been removed from the xml.
    if (!ApiFunctions.isEmpty(returnValue)) {
      List privilegeIds = new ArrayList();
      Iterator iterator = returnValue.iterator();
      while (iterator.hasNext()) {
        privilegeIds.add(((PrivilegeDto)iterator.next()).getId());
      }
      Connection con = null;
      ResultSet rs = null;
      PreparedStatement pstmt = null;
      try {
        con = ApiFunctions.getDbConnection();
        StringBuffer xmlQuery = new StringBuffer(100);
        xmlQuery.append("select object_id, object_description from ard_objects where privilege_id in ");
        xmlQuery.append(ApiFunctions.makeBindParameterList(privilegeIds.size()));
        xmlQuery.append(" order by object_description");
        pstmt = con.prepareStatement(xmlQuery.toString());
        ApiFunctions.bindBindParameterList(pstmt,1,privilegeIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        returnValue.clear();
        while (rs.next()) {
          PrivilegeDto privilege = new PrivilegeDto();
          privilege.setDescription(rs.getString("object_description"));
          privilege.setId(rs.getString("object_id"));
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
   * Method to set the privileges that can be managed by an account administrator for an account
   * @param accountID  the account being updated
   * @param privilegeIds a List of privilege ids, one for each privilege managable by an account 
   * administrator for an account
   */
  public void setPrivilegesAssignedToAccount(String accountId, List privilegeIds) {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    List privileges = new ArrayList();
    
    //because the account_data xml string holds privilege ids and not object ids, get the privilege 
    //ids for the objectIds passed in.  This is likely to be a one to one match,
    //but is not guaranteed to be because we may have altered the object_id of a privilege
    //to change it's ordering in the left hand menu.
    if (!ApiFunctions.isEmpty(privilegeIds)) {
      try {
        con = ApiFunctions.getDbConnection();
        StringBuffer xmlQuery = new StringBuffer(100);
        xmlQuery.append("select privilege_id, object_description from ard_objects where object_id in ");
        xmlQuery.append(ApiFunctions.makeBindParameterList(privilegeIds.size()));
        pstmt = con.prepareStatement(xmlQuery.toString());
        ApiFunctions.bindBindParameterList(pstmt,1,privilegeIds);
        rs = ApiFunctions.queryDb(pstmt, con);
        while (rs.next()) {
          PrivilegeDto privilege = new PrivilegeDto();
          privilege.setDescription(rs.getString("object_description"));
          privilege.setId(rs.getString("privilege_id"));
          privileges.add(privilege);
        }
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, rs);
      }
    }
    
    //get an AccountData with the existing account information
    AccountData accountData = getAccountData(accountId);
    //replace the "old" privileges with the "new" privileges
    accountData.setAssignablePrivileges(privileges);
    setAccountData(accountId, accountData);
  }
  
  /**
   * Method to find the inventory groups that can be managed by an account administrator 
   * for an account
   * @param accountID
   * @return a List of LogicalRepository objects representing the inventory groups
   * managable by an account administrator for an account
   */
  public List getInventoryGroupsAssignedToAccount(String accountId) {
    AccountData accountData = getAccountData(accountId);
    //remove any inventory groups that are no longer recognized by the system
    List inventoryGroups = accountData.getAssignableInventoryGroups();
    List knownInventoryGroups = LogicalRepositoryUtils.getAllLogicalRepositories();
    inventoryGroups.retainAll(knownInventoryGroups);
    //now, to get the latest data for each inventory group, use the known inventory groups
    knownInventoryGroups.retainAll(inventoryGroups);
    return knownInventoryGroups;
  }

  /**
   * Method to set the inventory groups that can be managed by an account administrator 
   * for an account
   * @param accountID  the account being updated
   * @param inventoryGroupIds a List of inventory group ids, one for each inventory group
   * managable by an account administrator for an account
   */
  public void setInventoryGroupsAssignedToAccount(String accountId, List inventoryGroupIds) {
    List inventoryGroups = LogicalRepositoryUtils.getLogicalRepositoriesById(inventoryGroupIds);
    //get an AccountData with the existing account information
    AccountData accountData = getAccountData(accountId);
    //replace the "old" privileges with the "new" privileges
    accountData.setAssignableInventoryGroups(inventoryGroups);
    setAccountData(accountId, accountData);
  }
  
  private AccountData getAccountData(String accountId) {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    AccountData accountData = new AccountData();
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer xmlQuery = new StringBuffer(100);
      xmlQuery.append("select account_data from es_ardais_account where ardais_acct_key = ?");
      pstmt = con.prepareStatement(xmlQuery.toString());
      pstmt.setString(1, accountId);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        accountData.setAccountData(ApiFunctions.getStringFromClob(rs.getClob("account_data")));
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return accountData;
  }
  
  private void setAccountData(String accountId, AccountData accountData) {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    //update the database
    TemporaryClob clob = null;
    try {
      con = ApiFunctions.getDbConnection();
      StringBuffer updateSql = new StringBuffer(100);
      updateSql.append("update es_ardais_account set account_data = ?, account_data_encoding = ? where ardais_acct_key = ?");
      pstmt = con.prepareStatement(updateSql.toString());
      clob = new TemporaryClob(con, accountData.toXml());
      pstmt.setClob(1, clob.getSQLClob());
      pstmt.setString(2, Constants.ENCODING_SCHEME_XML1);
      pstmt.setString(3, accountId);
      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(clob, con);
      ApiFunctions.close(con, pstmt, rs);
    }
  }
  
  private String getDescriptionForAccountType(String accountType) {
    String description = null;
    if (Constants.ACCOUNT_TYPE_CONSUMER.equalsIgnoreCase(accountType)) {
      description = Constants.ACCOUNT_TYPE_CONSUMER_DESC;
    }
    else if (Constants.ACCOUNT_TYPE_DONOR_AND_CONSUMER.equalsIgnoreCase(accountType)) {
      description = Constants.ACCOUNT_TYPE_DONOR_AND_CONSUMER_DESC;
    }
    else if (Constants.ACCOUNT_TYPE_SYSTEM_OWNER.equalsIgnoreCase(accountType)) {
      description = Constants.ACCOUNT_TYPE_SYSTEM_OWNER_DESC;
    }
    else {
      throw new ApiException("Unknown account type encountered");
    }
    return description;
  }
}