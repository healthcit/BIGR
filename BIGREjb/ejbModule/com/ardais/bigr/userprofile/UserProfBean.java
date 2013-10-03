package com.ardais.bigr.userprofile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.util.UrlUtils;
import com.gulfstreambio.kc.form.def.shared.SharedViewService;
import org.apache.commons.lang.StringUtils;

public class UserProfBean implements SessionBean {

  private javax.ejb.SessionContext mySessionCtx = null;
  final static long serialVersionUID = 3206093459760846163L;

  /**
   * Return true if the specified hashed password is the correct password
   * for the specified user.
   * 
   * @param userid the user id
   * @param account the user account
   * @param hashedPassword the hashed password
   * 
   * @return true if the password is correct
   */
  public boolean validateUser(String userid, String account, String hashedPassword) {

    boolean validUser = false;

    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    try {
      con = ApiFunctions.getDbConnection();
      String query =
        "SELECT U.ARDAIS_USER_ID FROM ES_ARDAIS_USER U,ES_ARDAIS_ACCOUNT A WHERE "
          + "U.ARDAIS_USER_ID = ? AND U.PASSWORD = ? AND U.ARDAIS_ACCT_KEY = ? "
          + "AND U.ARDAIS_ACCT_KEY =  A.ARDAIS_ACCT_KEY "
          + "AND U.USER_ACTIVE_IND = 'Y' "
          + "AND A.ARDAIS_ACCT_STATUS = 'A'";
      pstmt = con.prepareStatement(query);
      pstmt.setString(1, userid);
      pstmt.setString(2, hashedPassword);
      pstmt.setString(3, account);
      rs = ApiFunctions.queryDb(pstmt, con);
      if (rs.next()) {
        validUser = true;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return validUser;
  }

  /**
   * Get the user's privileges.  This includes all items from ARD_OBJECT
   * that are assigned to the specified user, not just the items with
   * OBJECT_TYPE = 'P'.
   * 
   * @param userid the user id
   * @param account the user account
   * 
   * @return a set of the privileges, each privilege in the set is a
   *     string that appears in the ARD_OBJECT.OBJECT_ID column.
   */
  public Set getPrivileges(String userid, String account) {
    Set result = new HashSet();

    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    try {
      con = ApiFunctions.getDbConnection();
      String query = "SELECT OB.OBJECT_ID FROM "
          + "ARD_USER_ACCESS_MODULE UO, ARD_OBJECTS OB "
          + "WHERE OB.OBJECT_ID = UO.OBJECT_ID "
          + "AND UO.ARDAIS_ACCT_KEY = ? "
          + "AND UO.ARDAIS_USER_ID = ? ";

      pstmt = con.prepareStatement(query);
      pstmt.setString(1, account);
      pstmt.setString(2, userid);
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
   * Get the logical repositories to which the user has visiblity.
   * 
   * @param userid the user id
   * 
   * @return a set of the visible logical repositories.
   */
  public Set getLogicalRepositories(String userId) {
    return LogicalRepositoryUtils.getLogicalRepositoriesForUser(userId);
  }

  /**
   * Get the left hand menu urls defined for the account to which the user belongs.
   * 
   * @param accountId the account id
   * 
   * @return a List of the left-hand menu URLs defined for this account.
   */
  public List getMenuUrls(String accountId) {
    final List<String> objectTypes = new ArrayList<String>();
    objectTypes.add(UrlUtils.OBJECT_TYPE_MENU);
    return UrlUtils.getUrlsByAccountId(accountId, objectTypes);
  }

  /**
   * Get the user profile vector.
   * 
   * @param userid the user id
   * @param account the user account
   * 
   * @return the list of profile features
   */
  public Vector getProfile(String userid, String account) {
    Vector result = new Vector();

    ResultSet rs = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    try {
      con = ApiFunctions.getDbConnection();
      final String query =
		  		"SELECT UO.OBJECT_ID, UO.NEW_ORDER_CREATION, UO.ORDER_MAINTAIN, UO.ORDER_VIEW, "
			  + "OB.OBJECT_DESCRIPTION, OB.LONG_DESCRIPTION, OB.URL, OB.TOP_MENU, OB.SUB_MENU FROM "
			  + "ARD_USER_ACCESS_MODULE UO, ARD_OBJECTS OB "
			  + "WHERE OB.OBJECT_TYPE = 'M' "
			  + "AND OB.OBJECT_ID = UO.OBJECT_ID "
			  + "AND UO.ARDAIS_ACCT_KEY = ? "
			  + "AND UO.ARDAIS_USER_ID = ? "
          + "UNION "
			  + "SELECT OB.OBJECT_ID, '', '', '', "
			  + "OB.OBJECT_DESCRIPTION, OB.LONG_DESCRIPTION, OB.URL, OB.TOP_MENU, OB.SUB_MENU FROM "
			  + "ARD_OBJECTS OB "
			  + "WHERE OB.OBJECT_TYPE = 'M' "
			  + "AND OB.OBJECT_ID IN ("
					+ "(SELECT PRIVILEGE_ID FROM BIGR_ROLE_PRIVILEGE P WHERE "
			  			+ "P.ROLE_ID IN (SELECT ROLE_ID FROM BIGR_ROLE_USER WHERE USER_ID = ?)) "
					+ " UNION " + getDependedPrivilegeQueryForUser()
					+ " UNION " + getDependedPrivilegeQueryForRole()+ ") "
		  + "ORDER BY 1";

      pstmt = con.prepareStatement(query);
      pstmt.setString(1, account);
      pstmt.setString(2, userid);
      pstmt.setString(3, userid);
	  pstmt.setString(4, account);
	  pstmt.setString(5, userid);
	  pstmt.setString(6, userid);
      rs = ApiFunctions.queryDb(pstmt, con);
      result = parseResultSet(rs);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }
    return result;
  }

	protected String getDependedPrivilegeQueryForUser()
	{
		return "(SELECT DISTINCT D.PRIVILEGE_ID " +
			"FROM ARD_USER_ACCESS_MODULE UO " +
			"INNER JOIN DEPENDED_PRIVILEGES D ON D.DEPENDED_PRIVILEGE_ID = UO.OBJECT_ID " +
			"WHERE UO.ARDAIS_ACCT_KEY = ? AND UO.ARDAIS_USER_ID = ?)";
	}

	protected String getDependedPrivilegeQueryForRole()
	{
		return "(SELECT D.PRIVILEGE_ID " +
			"FROM BIGR_ROLE_PRIVILEGE P " +
			"INNER JOIN DEPENDED_PRIVILEGES D ON D.DEPENDED_PRIVILEGE_ID = P.PRIVILEGE_ID " +
			"WHERE P.ROLE_ID IN (SELECT ROLE_ID FROM BIGR_ROLE_USER WHERE USER_ID = ?))";
	}

  private Vector<Vector> parseResultSet(ResultSet rs) throws Exception {

    Vector<Vector> results = new Vector<Vector>();
    Vector<Vector> topLevel = new Vector<Vector>();
    Vector<Vector> subLevel = new Vector<Vector>();
    Vector<String> optionLevel = new Vector<String>();

    String currentTop = null;
    String currentSub = null;
    String topMenu = null;
    String subMenu = null;
    String option = null;
    String url = null;

    while (rs.next()) {
      topMenu = rs.getString(8);
      subMenu = rs.getString(9);
      option = rs.getString(5);
      url = rs.getString(7);
      optionLevel.add(option);
      optionLevel.add(url);

      if (!subMenu.equals(currentSub) || subMenu.equals("SUB_MENU") || !topMenu.equals(currentTop)) {
        if (currentSub == null) {
          currentSub = subMenu;
          subLevel.add(optionLevel);
          optionLevel = new Vector<String>();

        }
        else {

          Vector temp = new Vector();
          temp.add(currentSub);
          temp.add(subLevel);
          topLevel.add(temp);
          currentSub = subMenu;
          subLevel = new Vector<Vector>();
          subLevel.add(optionLevel);
          optionLevel = new Vector<String>();
        }
      }
      else if (subMenu.equals(currentSub)) {
        subLevel.add(optionLevel);
        optionLevel = new Vector<String>();
      }

      if (!topMenu.equals(currentTop) || topMenu.equals("TOP_MENU")) {
        if (currentTop == null) {
          currentTop = topMenu;
        }
        else {
          Vector temp = new Vector();
          temp.add(currentTop);
          temp.add(topLevel);
          results.add(temp);
          currentTop = topMenu;
          topLevel = new Vector<Vector>();
        }

      }

    }

    // MR 7573 changed code to not fill results Vector when
    // there are no privileges for the user...
    Vector temp = new Vector();
    if ((currentSub != null) && (subLevel != null)) {
      temp.add(currentSub);
      temp.add(subLevel);
      topLevel.add(temp);
    }
    temp = new Vector();
    if ((currentTop != null) && (topLevel != null)) {
      temp.add(currentTop);
      temp.add(topLevel);
    }
    if (temp != null)
      results.add(temp);

    return results;
  }

  /**
   * @see com.ardais.bigr.userprofile.UserProf
   */
  public void setViewProfile(SecurityInfo secInfo, ViewParams vp, ViewProfile viewProfile) {
    UserProfileTopics tpcs = UserProfileTopics.load(secInfo);
    tpcs.addViewProfile(vp.getKey(), viewProfile);
    tpcs.persist(secInfo);
  }

  public ViewProfile getViewProfile(SecurityInfo secInfo, ViewParams vp) {
    UserProfileTopics topics = UserProfileTopics.load(secInfo);
    ViewProfile profile = null;
    //if there is no viewparams or it has an empty key, use the default
    if (vp == null || ApiFunctions.isEmpty(vp.getKey())) {
      profile = getDefaultViewProfile(secInfo, vp);
    }
    //otherwise try to get the profile for the key. If there is no profile, use 
    //the default.  If there is a profile, check that it contains a valid results
    //form id (it might not, as the user could have deleted the form).
    //If the id is valid then return the profile, otherwise clear the bogus value
    //and use the default.  (ViewProfile.getResultsFormDefinition returning null indicates
    //that the resultsFormDefinitionId in the view profile is invalid.)
    else {
      profile = topics.getViewProfile(vp.getKey());
      if (profile == null) {
        profile = getDefaultViewProfile(secInfo, vp);
      }
      else {
        if (profile.getResultsFormDefinition() == null) {
          profile = getDefaultViewProfile(secInfo, vp);
          try {
            setViewProfile(secInfo, vp, profile);
          }
          catch (Exception e) {
            throw new ApiException("Could not clear view for user", e);
          }
        }
      }
    }
    return profile;
  }

  /**
   * @return   the profile for the system default view
   */
  public ViewProfile getDefaultViewProfile(SecurityInfo secInfo, ViewParams vp) {
    ViewProfile viewProfile = new ViewProfile(secInfo);
    //hack to handle RNA based screens and the order confirmation screen
    if (vp != null && (vp.isProduct(ColumnPermissions.PROD_RNA) || vp.isScreen(ColumnPermissions.SCRN_CONFIRM_REQ))) {
      //if the screen is the RNA tab, use the RNA columns
      if (vp.isRnaTab()) {
        viewProfile.setResultsFormDefinitionId(Integer.toString(ColumnPermissions.PROD_RNA));
      }
      //otherwise use the generic columns
      else {
        viewProfile.setResultsFormDefinitionId(Integer.toString(ColumnPermissions.PROD_GENERIC));
      }
    }
    //end of hack to handle RNA based screens
    else {
		final String defaultFormDefinitionIdForRole = SharedViewService.SINGLETON.getDefaultForRoles(secInfo.getRoleIds());
		if (StringUtils.isNotBlank(defaultFormDefinitionIdForRole)) // default view for role
		{
			viewProfile.setResultsFormDefinitionId(defaultFormDefinitionIdForRole);
		}
		else
		{
			viewProfile.setResultsFormDefinitionId(Constants.DEFAULT_RESULTS_VIEW_ID);
		}
    }
    return viewProfile;
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