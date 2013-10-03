package com.ardais.bigr.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.ObjectNotFoundException;

import org.apache.commons.collections.CollectionUtils;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.es.beans.ArdaisaccountAccessBean;
import com.ardais.bigr.es.beans.ArdaisuserAccessBean;
import com.ardais.bigr.es.beans.ArdaisuserKey;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.javabeans.PrivilegeDto;
import com.ardais.bigr.javabeans.RoleDto;
import com.ardais.bigr.userprofile.UserProf;
import com.ardais.bigr.userprofile.UserProfHome;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.util.RoleUtils;

/**
 * Represents the user's role and privileges in the system based on their 
 * account.
 */
public class SecurityInfo implements Serializable {

  //-- Begin Privilege Definitions -----------------------------------------------------

  /**
   * BIGR Library Privileges.
   */
  public final static String PRIV_HOLDLIST_ADDRMV = "15_ES_HOLDLIST_ADDRMV";
  public final static String PRIV_SHOW_SQL_TAB = "15_ES_SQL_TAB";
  public final static String PRIV_VIEW_HOLD = "15_ES_010_VIEWITEM";
  public final static String PRIV_SAMPLE_SELECT = "15_ES_006_SS";
  public final static String PRIV_HOLDSOLD_USER = "15_ES_HOLDSOLD_USER";
  public final static String PRIV_HOLDSOLD_ACCOUNT = "15_ES_HOLDSOLD_ACCNT";
  public final static String PRIV_HOLD_RNA_ADDRMV = "15_ES_HOLD_RNA_ADDRMV";
  public final static String PRIV_HOLD_RNA_CUST_AMT = "15_ES_HOLD_RNA_CUSTM";
  public final static String PRIV_VIEW_CONSENT = "15_ES_CONSENT_VIEW";
  public final static String PRIV_LINKED_CASES_ONLY = "15_ES_LINKED_CASES_ONLY";
  public final static String PRIV_CHANGE_PROFILE = "15_ES_050_PROFILE";
  //not in ARD_OBJECTS
  public final static String PRIV_PSA_DRE = "15_ES_PSA_DRE";

  /**
   * LIMS Privileges.
   */
  public final static String PRIV_LIMS_QCPOST_UNPOST = "20_LIMS_QCPOST_UNPOST";
  public final static String PRIV_LIMS_INC_FOR_REPV = "20_LIMS_INC_FOR_REPV";

  /**
   * ILTDS Privileges.  Some privileges aren't stored in ARD_OBJECTS, they're inferred from
   * other information instead of being explicitly configured for a user.
   */
  public final static String PRIV_ILTDS_BOX_SCAN_DI = "10_ILTDS_045_NBOXSCAN";
  public final static String PRIV_ILTDS_CHECK_OUT_BOX_DI = "10_ILTDS_050_CKOUTINV";
  public final static String PRIV_ILTDS_UPDATE_BOX_LOC_DI = "10_ILTDS_055_UPDBXLOC";
  public final static String PRIV_ILTDS_BOX_SCAN_ARDAIS = "10_ILTDS_085_ABOXSCAN";
  public final static String PRIV_ILTDS_CHECK_OUT_BOX_ARDAIS = "10_ILTDS_090_ACOINV";
  public final static String PRIV_ILTDS_MOVE_TO_PATHOLOGY = "10_ILTDS_095_MVTOPATH";
  public final static String PRIV_ILTDS_UPDATE_BOX_LOC_ARDAIS = "10_ILTDS_100_UPDBXLOC";
  public final static String PRIV_ILTDS_BOXES_AVAIL_FOR_SHIPMENT = "10_ILTDS_103_BOXFSHIP";
  public final static String PRIV_ILTDS_CREATE_MANIFEST = "10_ILTDS_104_CRTMAN";
  public final static String PRIV_ILTDS_SCAN_AND_PACKAGE = "10_ILTDS_107_SCANPACK";
  public final static String PRIV_ILTDS_RECEIPT_VERIFICATION = "10_ILTDS_120_RCPTVERF";
  public final static String PRIV_ILTDS_SCAN_AND_STORE = "10_ILTDS_122_SCANSTOR";
  public final static String PRIV_ICP = "10_ILTDS_145_ICP";
  public final static String PRIV_ICP_SUPERUSER = "10_ILTDS_147_ICP_SUSR";
  public final static String PRIV_ILTDS_MANAGE_REQUESTS = "12_ILTDS_10_MNGREQS";
  public final static String PRIV_ILTDS_REQUEST_SAMPLES = "12_ILTDS_10_REQSAMP";
  public final static String PRIV_ILTDS_VIEW_REQUESTS = "12_ILTDS_10_VIEWREQS";
  public final static String PRIV_ILTDS_CREATE_PATH_REQUESTS = "12_ILTDS_21_PATH_REQ";
  public final static String PRIV_ILTDS_CREATE_RAND_REQUESTS = "12_ILTDS_22_RAND_REQ";
  
  /**
   * DDC Privileges
   */
  public final static String PRIV_CLINICAL_DATA_EXTRACTION = "25_PDC_040_CDE";
  public final static String PRIV_DONOR_PROFILE = "25_PDC_020_DNRPRF";
  public final static String PRIV_CASE_PROFILE_ENTRY = "25_PDC_025_CASPRF";
  public final static String PRIV_PATH_REPORT_FULL = "25_PDC_030_PTHFULL";
  public final static String PRIV_PATH_REPORT_ABSTRACT = "25_PDC_035_PTHAB";

  /**
   * LOGICAL RESPOSITORY Privileges
   */
  public final static String PRIV_VIEW_ALL_LOGICAL_REPOS = "40_ORM_VIEW_ALL_LRS";
  public final static String PRIV_ORM_MAINTAIN_LOGICAL_REPOS = "40_ORM_035_MAINTLR";
  public final static String PRIV_ORM_MAINTAIN_POLICIES = "40_ORM_030_PLCYMGR";

  /**
   * DATA IMPORT Privileges
   */
  public final static String PRIV_DATA_IMPORT_CREATE_DONOR = "50_IMP_10_CREATEDONOR";
  public final static String PRIV_DATA_IMPORT_MODIFY_DONOR = "50_IMP_15_MODIFYDONOR";
  public final static String PRIV_DATA_IMPORT_CREATE_CASE = "50_IMP_20_CREATECASE";
  public final static String PRIV_DATA_IMPORT_MODIFY_CASE = "50_IMP_25_MODIFYCASE";
  public final static String PRIV_DATA_IMPORT_PULL_CASE = "50_IMP_30_CASEPULL";
  public final static String PRIV_DATA_IMPORT_CREATE_SAMPLE = "50_IMP_35_CREATESAMP";
  public final static String PRIV_DATA_IMPORT_MODIFY_SAMPLE = "50_IMP_40_MODIFYSAMP";
  public final static String PRIV_DATA_IMPORT_CASE_PROFILE_ENTRY = "50_IMP_45_CASEPRF";
  public final static String PRIV_DATA_IMPORT_PATH_REPORT_FULL = "50_IMP_50_PATHREPFULL";
  public final static String PRIV_DATA_IMPORT_PATH_REPORT_ABS = "50_IMP_55_PATHREPABS";
  public final static String PRIV_DATA_IMPORT_CLINICAL_DATA_EXT = "50_IMP_60_CLINDATAEXT";
  public final static String PRIV_DATA_IMPORT_FIND_BY_ID = "50_IMP_65_FINDBYID";
  
  /**
   * ORM privileges
   */
  public final static String PRIV_ORM_USER_MANAGEMENT = "40_ORM_010_USERMNG";
  public final static String PRIV_ORM_ACCOUNT_MANAGEMENT = "40_ORM_005_ACCTMNG";
  public final static String PRIV_ORM_ROLE_MANAGEMENT = "40_ORM_015_MAINTROLES";

	/**
	 * Shared View privileges
	 */
	public static final String PRIV_ORM_SHARE_VIEW = "40_ORM_SHARE_VIEW";

	/**
	 * View Access privileges
	 */
	public static final String PRIV_ORM_ACCESS_SAMPLE_VIEW = "40_ORM_20_SAMPLEVIEW";
	public static final String PRIV_ORM_ACCESS_DONOR_VIEW = "40_ORM_20_DONORVIEW";
	public static final String PRIV_ORM_ACCESS_CASE_VIEW = "40_ORM_20_CASEVIEW";
	public static final String PRIV_ORM_ACCESS_VIEW_MANAGEMENT = "40_ORM_20_VIEWMNG";

  //-- End Privilege Definitions -----------------------------------------------------

  //-- Begin ROLE Definitions -----------------------------------------------------

  /**
   * The System Owner role.  The value of this constant must be the value used to represent
   * System Owner in ES_ARDAIS_ACCOUNT.ARDAIS_ACCT_TYPE.  A validly logged-in user will always
   * have exactly one of ROLE_SYSTEM_OWNER, ROLE_CUSTOMER, or ROLE_DI.
   */
  public final static String ROLE_SYSTEM_OWNER = Constants.ACCOUNT_TYPE_SYSTEM_OWNER;

  /**
   * The CUSTOMER role.  The value of this constant must be the value used to represent
   * customers in ES_ARDAIS_ACCOUNT.ARDAIS_ACCT_TYPE.  A validly logged-in user will always
   * have exactly one of ROLE_SYSTEM_OWNER, ROLE_CUSTOMER, or ROLE_DI.
   */
  public final static String ROLE_CUSTOMER = Constants.ACCOUNT_TYPE_CONSUMER;

  /**
   * The DI role.  The value of this constant must be the value used to represent
   * donor institutions in ES_ARDAIS_ACCOUNT.ARDAIS_ACCT_TYPE.  A validly logged-in user will
   * always have exactly one of ROLE_SYSTEM_OWNER, ROLE_CUSTOMER, or ROLE_DI.
   */
  public final static String ROLE_DI = Constants.ACCOUNT_TYPE_DONOR_AND_CONSUMER;

  /**
   * The Repository Manager role.
   */
  public final static String ROLE_REPOSITORY_MANAGER = "ReposMgr";

  //-- End ROLE Definitions -----------------------------------------------------

  //-- Begin Privilege Set Definitions -----------------------------------------------------

  /**
   * This set is used to define which users are considered to have the ROLE_REPOSITORY_MANAGER
   * role.  Any user that has one or more of the privileges in this set gets that role.
   */
  private final static Set PRIVSET_REPOSITORY_MANAGER =
    Collections.unmodifiableSet(
      new HashSet(
        Arrays.asList(
          new String[] {
            PRIV_ILTDS_BOX_SCAN_DI,
            PRIV_ILTDS_CHECK_OUT_BOX_DI,
            PRIV_ILTDS_UPDATE_BOX_LOC_DI,
            PRIV_ILTDS_BOX_SCAN_ARDAIS,
            PRIV_ILTDS_CHECK_OUT_BOX_ARDAIS,
            PRIV_ILTDS_MOVE_TO_PATHOLOGY,
            PRIV_ILTDS_UPDATE_BOX_LOC_ARDAIS,
            PRIV_ILTDS_BOXES_AVAIL_FOR_SHIPMENT,
            PRIV_ILTDS_CREATE_MANIFEST,
            PRIV_ILTDS_SCAN_AND_PACKAGE,
            PRIV_ILTDS_RECEIPT_VERIFICATION,
            PRIV_ILTDS_SCAN_AND_STORE,
            PRIV_ILTDS_MANAGE_REQUESTS })));

  //-- End Privilege Set Definitions -----------------------------------------------------

  /**
   * The key under which the <code>SecurityInfo</code> object is saved in the 
   * user's session.
   */
  public final static String SECURITY_KEY = "securityKey";

  // Initialize everything so that _roleType and _privileges are null, _roleNone is true,
  // and all of the other role variables are false.
  //
  private String _username = null;
  private String _account = null;
  private String _userLocationId = null;
  private Set _privileges = Collections.EMPTY_SET;
  private Set _roles = Collections.EMPTY_SET;
  private Set _logicalRepositories = Collections.EMPTY_SET;
  private boolean _hasBmsAccess = false;
  private Set _accountRoles = Collections.EMPTY_SET;
  //the set of ids of the privs this user has via membership in roles defined for their account.  
  private Set _privilegeIdsViaAccountRoles = Collections.EMPTY_SET;

  /**
   * Creates a <code>SecurityInfo</code> that defaults to no role or privileges.
   */
  public SecurityInfo() {
    // Don't set anything here, use the defaults specified in the variable declarations
    // above.
  }

  /**
   * Creates a <code>SecurityInfo</code> for the specified user in the specified 
   * account.  Only the LoginAction should ever create a SecurityInfo object.
   * 
   * @param  username  the username of the user
   */
  public SecurityInfo(String username) {
    this();

    // First set _username, other methods rely on it.
    _username = username;

    // Next set the basic user properties including the user's account.  Some of the
    // other methods called later depend on this (e.g. some of the determine* methods).
    ArdaisuserAccessBean userBean = determineBasicUserProperties();

    determinePrivileges(userBean);
    
    determineAccountRolesAndPrivileges(userBean);

    // This must be called after determinePrivileges, since some roles are inferred from
    // privileges that the user has.
    determineUserRoles(userBean);

    // determineLogicalRepositories must be called after determining basic user
    // properties and privileges.
    determineLogicalRepositories(userBean);
  }

  /**
   * Returns the user's account.
   * 
   * @return  The user's account.
   */
  public String getAccount() {
    return _account;
  }

  /**
   * Returns the user's geolocation id.
   * 
   * @return  The user's geolocation id.
   */
  public String getUserLocationId() {
    return _userLocationId;
  }

  /**
   * Returns the user's username.
   * 
   * @return  The user's username.
   */
  public String getUsername() {
    return _username;
  }

  /**
   * @return the set of logical repositories to which the user has access.  Each item
   *   in the set is a {@link LogicalRepository} object.  For users that have the 
   *   PRIV_VIEW_ALL_LOGICAL_REPOS privilege, the set returned cannot be relied on to
   *   contain a list of all logical repositories.  It's value is undefined in this case.
   *   This method never returns null, though it may return an empty set.
   */
  public Set getLogicalRepositories() {
    // TECHNICAL NOTE: One reason for the decision to make this undefined for users that
    // have PRIV_VIEW_ALL_LOGICAL_REPOS is that we determine the value of this property and
    // other SecurityInfo properties at login time, and don't change it afterwards.  If we
    // set this to a list of all logical repositories at login time, there's no guarantee that
    // would continue to be right, since new logical repositories could be added (or removed)
    // while the user is logged in.

    return ((_logicalRepositories == null) ? Collections.EMPTY_SET : _logicalRepositories);
  }

  /**
   * @return a List of logical repositories to which the user has access sorted by full name.
   *   Each item in the set is a {@link LogicalRepository} object.  For users that have the 
   *   PRIV_VIEW_ALL_LOGICAL_REPOS privilege, the List returned cannot be relied on to
   *   contain a list of all logical repositories.  Its value is undefined in this case.
   *   This method never returns null, though it may return an empty list.
   */
  public List getLogicalRepositoriesByFullName() {
    // TECHNICAL NOTE: One reason for the decision to make this undefined for users that
    // have PRIV_VIEW_ALL_LOGICAL_REPOS is that we determine the value of this property and
    // other SecurityInfo properties at login time, and don't change it afterwards.  If we
    // set this to a list of all logical repositories at login time, there's no guarantee that
    // would continue to be right, since new logical repositories could be added (or removed)
    // while the user is logged in.

    Set repositories = getLogicalRepositories();
    if (repositories == null) {
      return Collections.EMPTY_LIST;
    }
    LogicalRepository[] items =
      (LogicalRepository[]) repositories.toArray(new LogicalRepository[0]);
    Arrays.sort(items, LogicalRepositoryUtils.FULL_NAME_ORDER);
    List itemList = Arrays.asList(items);
    return Collections.unmodifiableList(itemList);
  }

  /**
   * @return an array of the logical repository ids to which the user has access,
   *   sorted by logical repository full name.  For users that have the 
   *   PRIV_VIEW_ALL_LOGICAL_REPOS privilege, the array returned cannot be relied on to
   *   contain a list of all logical repository ids.  Its value is undefined in this case.
   *   This method never returns null, though it may return an empty array.
   */
  public String[] getLogicalRepositoriesIdByFullName() {
    // TECHNICAL NOTE: One reason for the decision to make this undefined for users that
    // have PRIV_VIEW_ALL_LOGICAL_REPOS is that we determine the value of this property and
    // other SecurityInfo properties at login time, and don't change it afterwards.  If we
    // set this to a list of all logical repositories at login time, there's no guarantee that
    // would continue to be right, since new logical repositories could be added (or removed)
    // while the user is logged in.

    List repositories = getLogicalRepositoriesByFullName();
    Iterator iterator = repositories.iterator();
    ArrayList lrList = new ArrayList();
    while (iterator.hasNext()) {
      LogicalRepository lr = (LogicalRepository) iterator.next();
      lrList.add(lr.getId());
    }
    return (
      lrList.isEmpty()
        ? ApiFunctions.EMPTY_STRING_ARRAY
        : (String[]) lrList.toArray(new String[0]));
  }

  /**
   * Returns true if the user is in the specified role, false otherwise.
   * 
   * @param  roleName  a role name, which must be one of the
   *                    <code>ROLE_*</code> constants defined in this class
   */
  public boolean isInRole(String roleName) {
    return _roles.contains(roleName);
  }

  /**
   * Returns true if the user is in any of the specified role, false otherwise.
   * 
   * @param roleNames a set of role names, each of which must be one of the
   *     <code>ROLE_*</code> constants defined in this class.
   * @return true if the user is in any of the specified roles.  If the set is null or empty
   *     this returns false.
   */
  public boolean isInAnyRole(Set roleNames) {
    if (roleNames == null)
      return false;

    return CollectionUtils.containsAny(_roles, roleNames);
  }

  /**
   * Returns true if the user is in either the System Owner or DI role.
   * 
   * @return  true if the user is in either the System Owner or DI role
   */
  public boolean isInRoleSystemOwnerOrDi() {
    return (_roles.contains(ROLE_SYSTEM_OWNER) || _roles.contains(ROLE_DI));
  }

  /**
   * Returns true if the user is in either the System Owner or CUSTOMER role.
   * 
   * @return  true if the user is in either the System Owner or CUSTOMER role.
   */
  public boolean isInRoleSystemOwnerOrCustomer() {
    return (_roles.contains(ROLE_SYSTEM_OWNER) || _roles.contains(ROLE_CUSTOMER));
  }

  /**
   * Returns true if the user is in either the CUSTOMER or DI role.
   * 
   * @return  true if the user is in either the CUSTOMER or DI role.
   */
  public boolean isInRoleCustomerOrDi() {
    return (_roles.contains(ROLE_DI) || _roles.contains(ROLE_CUSTOMER));
  }

  /**
   * Returns true if the user is in the System Owner role.
   * 
   * @return  true if the user is in the System Owner role.
   */
  public boolean isInRoleSystemOwner() {
    return (_roles.contains(ROLE_SYSTEM_OWNER));
  }

  /**
   * Returns true if the user is in the CUSTOMER role.
   * 
   * @return  true if the user is in the CUSTOMER role.
   */
  public boolean isInRoleCustomer() {
    return (_roles.contains(ROLE_CUSTOMER));
  }

  /**
   * Returns true if the user is in the DI role.
   * 
   * @return  true if the user is in the DI role.
   */
  public boolean isInRoleDi() {
    return (_roles.contains(ROLE_DI));
  }

  /**
   * Returns true if the user is in the NONE role.
   * 
   * @return  true if the user is in the NONE role.
   */
  public boolean isInRoleNone() {
    return (_roles.isEmpty());
  }

  /**
   * Returns true if the user can access the specified logical repository, false otherwise.
   * 
   * @param repositoryId A logical repository id.
   * @see #isLogicalRepositoryAccessible(LogicalRepository)
   */
  public boolean isLogicalRepositoryAccessible(String repositoryId) {
    if (isHasPrivilege(PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      return true;
    }
    else if (ApiFunctions.isEmpty(repositoryId) || ApiFunctions.isEmpty(_logicalRepositories)) {
      return false;
    }
    else {
      LogicalRepository repository = new LogicalRepository();
      repository.setId(repositoryId);
      return _logicalRepositories.contains(repository);
    }
  }

  /**
   * Returns true if the user can access the specified logical repository, false otherwise.
   * 
   * @param repository A logical repository.
   * @see #isLogicalRepositoryAccessible(String)
   */
  public boolean isLogicalRepositoryAccessible(LogicalRepository repository) {
    if (isHasPrivilege(PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      return true;
    }
    else if (repository == null) {
      return false;
    }
    else {
      return _logicalRepositories.contains(repository);
    }
  }

  /**
   * Returns true if the user can access one or more of the specified logical repositories,
   * false otherwise.
   * 
   * @param repositories A collection of logical repositories.  Each item in the collection may be
   *     either a repository id string or a {@link LogicalRepository} object whose
   *     id property contains a repository id.  If the collection is null or empty
   *     this returns false (even if the user has the PRIV_VIEW_ALL_LOGICAL_REPOS
   *     privilege).
   */
  public boolean isAnyLogicalRepositoryAccessible(Collection repositoryIds) {
    if (repositoryIds == null) {
      return false;
    }
    Iterator iter = repositoryIds.iterator();
    while (iter.hasNext()) {
      Object nextObject = iter.next();
      if (nextObject != null) {
        if (nextObject instanceof String) {
          // nextObject is a logical repository id
          if (isLogicalRepositoryAccessible((String) nextObject)) {
            return true;
          }
        }
        else if (nextObject instanceof LogicalRepository) {
          if (isLogicalRepositoryAccessible((LogicalRepository) nextObject)) {
            return true;
          }
        }
        else {
          throw new ApiException("Invalid repository specification.");
        }
      }
    }
    return false;
  }

  /**
   * @return True if the current user has access to at least one BMS inventory group.
   *    If the user can view all inventory groups, we assume that there's at least one
   *    BMS inventory group in the system and return true.
   */
  public boolean isHasBmsAccess() {
    return _hasBmsAccess;
  }

  /**
   * Returns true if the user has the specified privilege, false otherwise.
   * 
   * @param  privilegeName  a privilege name, which must be one of the
   *     <code>PRIV_*</code> constants defined in this class.
   */
  public boolean isHasPrivilege(String privilegeName) {
    return (_privileges.contains(privilegeName) || 
            _privilegeIdsViaAccountRoles.contains(privilegeName));
  }

  /**
   * Returns true if the user has at least one the specified privileges, false otherwise.
   * 
   * @param  privilegeNames  a set of privilege names, each of which must be one of the
   *     <code>PRIV_*</code> constants defined in this class.
   */
  public boolean isHasAnyPrivilege(Set privilegeNames) {
    if (privilegeNames == null)
      return false;

    return (CollectionUtils.containsAny(_privileges, privilegeNames) ||
            CollectionUtils.containsAny(_privilegeIdsViaAccountRoles, privilegeNames));
  }
  
  /**
   * Get and store basic information about the user.  This assumes that the username
   * property has already been set.  It sets the account and userLocationId properties.
   */
  private ArdaisuserAccessBean determineBasicUserProperties() {
    _account = null;
    _userLocationId = null;

    ArdaisuserAccessBean userBean = null;

    String username = getUsername();

    String account = null;
    String userLocationId = null;
    try {
      // We could get the account key from the Ardaisstaff bean that we get the location id
      // from, but the account key on that table is denormalized.  Since getting it right
      // here for security purposes is so critical, we do an extra query to get the user's
      // account from its primary source on the Ardaisuser bean.

      userBean = (new ArdaisuserAccessBean()).findByUserId(username);
      account = userBean.getArdaisaccountKey().ardais_acct_key;

      ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(username));
      userLocationId = staff.getGeolocation_location_address_id();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    _account = account;
    _userLocationId = userLocationId;

    return userBean;
  }

  /**
   * Gets the user's privileges.  The username and account properties must already have
   * been set when this is called.
   */
  private void determinePrivileges(ArdaisuserAccessBean userBean) {
    try {
      _privileges = Collections.EMPTY_SET; // make sure no privileges if there's an exception.

      if (userBean == null)
        return; // no user implies no privileges.

      // First get the privileges that are explicitly configured for a user, than add any
      // privileges that are inferred from other characteristics.
      String account = getAccount();
      String username = getUsername();
      UserProfHome home = (UserProfHome) EjbHomes.getHome(UserProfHome.class);
      UserProf usrProf = home.create();
      Set privileges = usrProf.getPrivileges(username, account);

      // Now add inferred privileges.
      //If the user belongs to an account limited to viewing linked cases only, they get 
      //that privilege as well
      try {
        ArdaisaccountAccessBean accountBean = userBean.getArdaisaccount();
        String accountLinkedCasesOnly = accountBean.getLinked_cases_only_yn();
        if ("Y".equalsIgnoreCase(accountLinkedCasesOnly)) {
          privileges.add(PRIV_LINKED_CASES_ONLY);
        }
      }
      catch (ObjectNotFoundException e) {
        // User/Account not found, so user has no role in the system.
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }

      _privileges = Collections.unmodifiableSet(privileges);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Gets the account-defined roles to which the user belongs, and the privileges to which they
   * have access via membership in those roles.  The username and account properties must already 
   * have been set when this is called.
   */
  private void determineAccountRolesAndPrivileges(ArdaisuserAccessBean userBean) {
    try {
      _accountRoles = Collections.EMPTY_SET; 
      _privilegeIdsViaAccountRoles = Collections.EMPTY_SET;

      if (userBean == null)
        return; // no user implies no account roles/privileges.

      // First get the roles to which the user belongs.
      List roles = RoleUtils.getRolesByUserId(((ArdaisuserKey) userBean.__getKey()).ardais_user_id);
      //Iterate over each role to which the user belongs, and iterate over its privileges.  For 
      //each privilege, add it to the set of privileges and set of privilege ids to which the user 
      //has access via their membership in roles.
      if (!ApiFunctions.isEmpty(roles)) {
        _accountRoles = Collections.unmodifiableSet(new HashSet(roles));
        Iterator roleIterator = _accountRoles.iterator();
        Set rolePrivilegeIds = new HashSet();
        while (roleIterator.hasNext()) {
          RoleDto role = (RoleDto)roleIterator.next();
          Iterator privilegeIterator = role.getPrivileges().iterator();
          while (privilegeIterator.hasNext()) {
            PrivilegeDto privilege = (PrivilegeDto)privilegeIterator.next();
            rolePrivilegeIds.add(privilege.getId());
          }
        }
        if (!ApiFunctions.isEmpty(rolePrivilegeIds)) {
          _privilegeIdsViaAccountRoles = Collections.unmodifiableSet(rolePrivilegeIds);
        }
      }
    }
    catch (Exception e) {
      _accountRoles = Collections.EMPTY_SET; // make sure no roles if there's an exception.
      _privilegeIdsViaAccountRoles = Collections.EMPTY_SET; // make sure no privilege ids if there's an exception.
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Gets the logical repositories the user is allowed to access.
   * The username property must already have been set when this is called, and the user's
   * privileges must already have been determined when this is called.
   * This also defines the {@link #isBmsUser()} property.
   */
  private void determineLogicalRepositories(ArdaisuserAccessBean userBean) {
    try {
      _logicalRepositories = null; // make sure no visiblity if there's an exception.
      _hasBmsAccess = false; // make sure no visiblity if there's an exception.

      if (userBean == null)
        return; // no user implies no inventory groups.

      String username = getUsername();
      UserProfHome home = (UserProfHome) EjbHomes.getHome(UserProfHome.class);
      UserProf usrProf = home.create();
      Set lrs = usrProf.getLogicalRepositories(username);

      // Now decide if the user has BMS access.  A user has BMS access if they have access to
      // to at least on BMS inventory group.  If the user can view all inventory groups,
      // we assume that there's at least one BMS inventory group, so that users that can
      // view all inventory groups will always be considered to have BMS access.
      //
      boolean hasBmsAccess = false;
      {
        if (isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
          hasBmsAccess = true;
        }
        else {
          Iterator iter = lrs.iterator();
          while (iter.hasNext()) {
            LogicalRepository invGroup = (LogicalRepository) iter.next();
            if (invGroup.isBms()) {
              hasBmsAccess = true;
              break;
            }
          }
        }
      }

      _logicalRepositories = Collections.unmodifiableSet(lrs);
      _hasBmsAccess = hasBmsAccess;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Gets the user's roles.  If there is an error or an unknown
   * account type, then the user is assumed to have no roles.
   * The username and account properties must already have
   * been set when this is called, and {@link #determinePrivileges()}
   * must have been called already.
   */
  private void determineUserRoles(ArdaisuserAccessBean userBean) {
    // Default to no roles (_roles is an empty set)
    _roles = Collections.EMPTY_SET;

    if (userBean == null)
      return; // no user implies no roles.

    Set roles = new HashSet();

    // Every user gets a role corresponding to their account type (Ardais, DI, or Customer),
    // so look up their account type and assign that role first (by adding it to the "roles"
    // set variable).  After that, we add any other roles that the user might have.

    String accountType = null;
    try {
      ArdaisaccountAccessBean accountBean = userBean.getArdaisaccount();
      accountType = accountBean.getArdais_acct_type();
    }
    catch (ObjectNotFoundException e) {
      // User/Account not found, so user has no role in the system.
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    if (ROLE_SYSTEM_OWNER.equals(accountType)
      || ROLE_DI.equals(accountType)
      || ROLE_CUSTOMER.equals(accountType)) {
      roles.add(accountType);
    }
    else {
      // Unknown or empty account type.  Don't change any of the role variables,
      // they'll remain set to an empty role set.

      if (!ApiFunctions.isEmpty(accountType)) {
        throw new ApiException(
          "Unknown account type for user '"
            + getUsername()
            + "' in account '"
            + getAccount()
            + "': "
            + accountType);
      }
    }

    // Now we've determined the user role that corresponds to their account type.  Next we add
    // any other roles that the user may have.

    // The user gets the "Repository Manager" role id they have at least one of a certain
    // set of privileges.
    //
    if (isHasAnyPrivilege(PRIVSET_REPOSITORY_MANAGER)) {
      roles.add(ROLE_REPOSITORY_MANAGER);
    }

    // Ok, we made it all the way through without errors, so we can now safely move the
    // user's roles into the _roles class variable.
    // 
    _roles = Collections.unmodifiableSet(roles);
  }

	public Set<String> getRoleIds()
	{
		final Set<String> result = new HashSet<String>();
		for (Object o : _accountRoles)
		{
			final RoleDto role = (RoleDto) o;
			result.add(role.getId());
		}
		return result;
	}
}
