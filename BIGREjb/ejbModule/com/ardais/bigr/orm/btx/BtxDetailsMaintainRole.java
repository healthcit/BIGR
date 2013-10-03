package com.ardais.bigr.orm.btx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.PrivilegeDto;
import com.ardais.bigr.javabeans.RoleDto;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;

/**
 * Represent the details of maintaining a role (create/edit/delete).
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setOperation(String) Operation}: The maintenance operation that is being
 *     performed.  This must be one of "Create", "Update", or "Delete" (case-sensitive).</li>
 * <li>{@link #Role.setId(String) id}: The role id that we're
 *     operating on.  This is required for "Update" and "Delete" operations and must be
 *     null on input for "Create" operations (it is an output parameter for "Create").</li>
 * <li>{@link #Role.setName(String) name}: The name of the role. This is required for
 *     "Create" and "Update" operations and is ignored on input for the "Delete" operation (it is
 *     an output parameter for "Delete"). Any whitespace at the beginning or end of the name
 *     will be trimmed.</li>
 * <li>{@link #Role.setAccountId(String) Account id}: The id of the account to which the role that 
 *     we're operating on belongs.  This is required for "Create" operations and is ignored for
 *     "Update" and "Delete" operations (it is an output parameter for "Delete").  It is ignored
 *     for "Update" since the account to which a role belongs cannot be modified.</li>
 * <li>{@link #Role.setPrivileges(List)}: A List of PrivilegeDtos representing the privileges
 *      to which users in the role should be given access. This is required for
 *     "Create" and "Update" operations and is ignored for the "Delete" operation.  Note that
 *     it is acceptable for the list to be empty, meaning that users in the role will not
 *     gain access to any additional privileges by virtue of belonging to the rol.</li>
 * <li>{@link #Role.setUsers(List)}: A List of UserDtos representing the users to be
 *      assigned the role.  This is required for "Create" and "Update" operations and is ignored for 
 *      the "Delete" operation.  Note that it is acceptable for the list to be empty, meaning no
 *      users will belong to the role.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None. </li>
 * </ul>
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #Role.setId(String)}: This is sometimes an output parameter, as
 *     described above.</li>
 * <li>{@link #Role.setName(String)}: This is sometimes an output parameter, as
 *     described above.</li>
 * <li>{@link #Role.setAccountId(String)}: This is sometimes an output parameter, as
 *     described above.</li>
 * <li>{@link #Role.setPrivileges(Collection)}: A collection of PrivilegeDtos representing the
 *     privileges to which users in the role have access.  This will be the same list as on
 *     input, but with additional information populated.</li>
 * <li>{@link #Role.setUsers(Collection)}: A collection of UserDtos representing the users
 *     in the role.  This will be the same list as on input, but with additional information populated.</li>
 * <li>{@link #setAllPrivileges(Map)}: A map of PrivilegeDtos representing the privileges to
 *     which the user has the ability to control access, keyed by functional area.  Will be all 
 *     privileges if the suer is in the system owner account, otherwise will be the privileges
 *     designated as assignable by users in the current users account.</li>
 * <li>{@link #setRemovedPrivileges(Collection)}: A collection of PrivilegeDtos representing the
 *     privileges to which users in the role just had access removed.</li>
 * <li>{@link #setAddedPrivileges(Collection)}: A collection of PrivilegeDtos representing the 
 *     privileges to which users in the role just had access added.</li>
 * <li>{@link #setAllUsers(Map)}: A map of all users, keyed by account.  Will either be all users
 *     if the user is in the system owner account or just the users in the account to which the user
 *     belongs.</li>
 * <li>{@link #setRemovedUsers(Collection)}: A collection of UserDtos representing the
 *     users just removed from the role.</li>
 * <li>{@link #setAddedUsers(Collection)}: A collection of UserDtos representing the 
 *     users just added to the role.</li>
 </ul>
 */
public class BtxDetailsMaintainRole extends BTXDetails {
  //static final long serialVersionUID = -1852947526051608131L;

  private String _operation = null;
  private RoleDto _role = null;
  private String _privilegeFilter = null;
  private Map _allPrivileges = null;
  private Collection _removedPrivileges = null;
  private Collection _addedPrivileges = null;
  private Map _allUsers = null;
  private Collection _removedUsers = null;
  private Collection _addedUsers = null;

  public BtxDetailsMaintainRole() {
    super();
  }

  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MAINTAIN_ROLE;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  public Set getDirectlyInvolvedObjects() {

    Set set = new HashSet();
    set.add(getRole().getId());

    return set;
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will have its fields set to
   *    the transaction information.
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    history.setAttrib1(getOperation());
    history.setAttrib2(getRole().getId());
    history.setAttrib3(getRole().getName());
    history.setAttrib4(getRole().getAccountId());
    //because there are only two idlists available in the history object but we require four
    //(one for the removed privileges, one for the added privileges, one for the removed users,
    //and one for the added users), we'll have to make use of the historyObject.  Construct a
    //list containing the various lists, and pass that as the history object.  The lists will be
    //reconstructed in the populateFromHistoryObject method
    IdList removedPrivilegeIds = new IdList();
    List removedPrivilegeNames = new ArrayList();
    Iterator iterator = getRemovedPrivileges().iterator();
    while (iterator.hasNext()) {
      PrivilegeDto privilege = (PrivilegeDto)iterator.next();
      removedPrivilegeIds.add(privilege.getId());
      removedPrivilegeNames.add(privilege.getDescription());
    }
    IdList addedPrivilegeIds = new IdList();
    List addedPrivilegeNames = new ArrayList();
    iterator = getAddedPrivileges().iterator();
    while (iterator.hasNext()) {
      PrivilegeDto privilege = (PrivilegeDto)iterator.next();
      addedPrivilegeIds.add(privilege.getId());
      addedPrivilegeNames.add(privilege.getDescription());
    }
    IdList removedUserIds = new IdList();
    List removedUserNames = new ArrayList();
    iterator = getRemovedUsers().iterator();
    while (iterator.hasNext()) {
      UserDto user = (UserDto)iterator.next();
      removedUserIds.add(user.getUserId());
      removedUserNames.add(user.getFirstName() + " " + user.getLastName());
    }
    IdList addedUserIds = new IdList();
    List addedUserNames = new ArrayList();
    iterator = getAddedUsers().iterator();
    while (iterator.hasNext()) {
      UserDto user = (UserDto)iterator.next();
      addedUserIds.add(user.getUserId());
      addedUserNames.add(user.getFirstName() + " " + user.getLastName());
    }
    //note - do not alter the order of these lists, as the populateFromHistoryRecord method
    //relies on the ordering to determine which list is which.
    List userAndPrivilegeLists = new ArrayList();
    userAndPrivilegeLists.add(0, removedPrivilegeIds);
    userAndPrivilegeLists.add(1, removedPrivilegeNames);
    userAndPrivilegeLists.add(2, addedPrivilegeIds);
    userAndPrivilegeLists.add(3, addedPrivilegeNames);
    userAndPrivilegeLists.add(4, removedUserIds);
    userAndPrivilegeLists.add(5, removedUserNames);
    userAndPrivilegeLists.add(6, addedUserIds);
    userAndPrivilegeLists.add(7, addedUserNames);
    history.setHistoryObject(userAndPrivilegeLists);
  }

  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set <b>all</b>
   * fields on this object, as if it had been newly created immediately before
   * this method was called.  A runtime exception is thrown if the transaction type
   * represented by the history record doesn't match the transaction type represented
   * by this object.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the
   *    information source.  A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);

    setOperation(history.getAttrib1());

    getRole().setId(history.getAttrib2());
    getRole().setName(history.getAttrib3());
    getRole().setAccountId(history.getAttrib4());
    
    //get the history object (a list of lists) and populate the added/removed
    //users and priveleges
    List userAndPrivilegeLists = (List)history.getHistoryObject();
    IdList removedPrivilegeIds = (IdList) userAndPrivilegeLists.get(0);
    List removedPrivilegeNames = (List) userAndPrivilegeLists.get(1);
    IdList addedPrivilegeIds = (IdList) userAndPrivilegeLists.get(2);
    List addedPrivilegeNames = (List) userAndPrivilegeLists.get(3);
    IdList removedUserIds = (IdList) userAndPrivilegeLists.get(4);
    List removedUserNames = (List) userAndPrivilegeLists.get(5);
    IdList addedUserIds = (IdList) userAndPrivilegeLists.get(6);
    List addedUserNames = (List) userAndPrivilegeLists.get(7);
    
    Iterator idIterator = addedPrivilegeIds.iterator();
    Iterator nameIterator = addedPrivilegeNames.iterator();
    List addedPrivileges = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      PrivilegeDto privilege = new PrivilegeDto();
      privilege.setDescription((String)nameIterator.next());
      privilege.setId((String)idIterator.next());
      addedPrivileges.add(privilege);
    }
    setAddedPrivileges(addedPrivileges);
    
    idIterator = removedPrivilegeIds.iterator();
    nameIterator = removedPrivilegeNames.iterator();
    List removedPrivileges = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      PrivilegeDto privilege = new PrivilegeDto();
      privilege.setDescription((String)nameIterator.next());
      privilege.setId((String)idIterator.next());
      removedPrivileges.add(privilege);
    }
    setRemovedPrivileges(removedPrivileges);
    
    idIterator = addedUserIds.iterator();
    nameIterator = addedUserNames.iterator();
    List addedUsers = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      UserDto user = new UserDto();
      String fullName = (String)nameIterator.next();
      int spaceLocation = fullName.indexOf(" ");
      String firstName = fullName.substring(0, spaceLocation);
      String lastName = fullName.substring(spaceLocation+1);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setUserId((String)idIterator.next());
      addedUsers.add(user);
    }
    setAddedUsers(addedUsers);
    
    idIterator = removedUserIds.iterator();
    nameIterator = removedUserNames.iterator();
    List removedUsers = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      UserDto user = new UserDto();
      String fullName = (String)nameIterator.next();
      int spaceLocation = fullName.indexOf(" ");
      String firstName = fullName.substring(0, spaceLocation);
      String lastName = fullName.substring(spaceLocation+1);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setUserId((String)idIterator.next());
      removedUsers.add(user);
    }
    setRemovedUsers(removedUsers);

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    setPrivilegeFilter(null);
    setAllPrivileges(null);
    setAllUsers(null);
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    //   getOperation
    //   getRole().getId
    //   getRole().getName
    //   getRole().getAccountId
    //   getAddedPrivileges();
    //   getRemovedPrivileges();
    //   getAddedUsers();
    //   getRemovedUsers();

    StringBuffer sb = new StringBuffer(256);

    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    // The form of the result depends on the operation.
    // Operation "Create":
    //    Created role <roleName> (<roleId>) [for account (accountId)], 
    //    with the following users and privileges: [roles, privileges]
    // Operation "Update":
    //    Updated role <roleName> (<roleId>) [for account (accountId)], 
    //    with the following users and privileges: [added users, removed users, 
    //    added privs, removed privs]
    // Operation "Delete":
    //    Removed role <roleName> (<roleId>) [for account (accountId)]

    String operation = getOperation();

    if (Constants.OPERATION_CREATE.equals(operation)) {
      sb.append("Created role ");
    }
    else if (Constants.OPERATION_UPDATE.equals(operation)) {
      sb.append("Updated role ");
    }
    else if (Constants.OPERATION_DELETE.equals(operation)) {
      sb.append("Removed role ");
    }
    Escaper.htmlEscape(getRole().getName(), sb);
    sb.append(" (");
    sb.append(IcpUtils.prepareLink(getRole().getId(), securityInfo));
    
    String accountId = getRole().getAccountId();
    if (!ApiFunctions.isEmpty(accountId)) {
      sb.append(") for account (");
      sb.append(IcpUtils.prepareLink(accountId, securityInfo));
    }
    sb.append(").");
    
    if (Constants.OPERATION_CREATE.equals(operation) || Constants.OPERATION_UPDATE.equals(operation)) {
      if (!ApiFunctions.isEmpty(getAddedPrivileges())) {
        sb.append("  The following privileges were granted to users in this role: ");
        Iterator iterator = getAddedPrivileges().iterator();
        boolean includeComma = false;
        while (iterator.hasNext()) {
          PrivilegeDto privilege = (PrivilegeDto)iterator.next();
          if (includeComma) {
            sb.append(", ");
          }
          includeComma = true;
          sb.append(privilege.getDescription());
        }
        sb.append("."); 
      }
      if (!ApiFunctions.isEmpty(getRemovedPrivileges())) {
        sb.append("  The following privileges were revoked from users in this role: ");
        Iterator iterator = getRemovedPrivileges().iterator();
        boolean includeComma = false;
        while (iterator.hasNext()) {
          PrivilegeDto privilege = (PrivilegeDto)iterator.next();
          if (includeComma) {
            sb.append(", ");
          }
          includeComma = true;
          sb.append(privilege.getDescription());
        }
        sb.append("."); 
      }
      if (!ApiFunctions.isEmpty(getAddedUsers())) {
        sb.append("  The following users were included in this role: ");
        Iterator iterator = getAddedUsers().iterator();
        boolean includeComma = false;
        while (iterator.hasNext()) {
          UserDto user = (UserDto)iterator.next();
          if (includeComma) {
            sb.append(", ");
          }
          includeComma = true;
          sb.append(user.getUserId());
        }
        sb.append("."); 
      }
      if (!ApiFunctions.isEmpty(getRemovedUsers())) {
        sb.append("  The following users were removed from this role: ");
        Iterator iterator = getRemovedUsers().iterator();
        boolean includeComma = false;
        while (iterator.hasNext()) {
          UserDto user = (UserDto)iterator.next();
          if (includeComma) {
            sb.append(", ");
          }
          includeComma = true;
          sb.append(user.getUserId());
        }
        sb.append("."); 
      }
    }

    return sb.toString();
  }

  /**
   * @return
   */
  public String getOperation() {
    return _operation;
  }
  
  public RoleDto getRole() {
    if (_role == null) {
      _role = new RoleDto();
    }
    return _role;
  }

  /**
   * @return Returns the addedPrivileges.
   */
  public Collection getAddedPrivileges() {
    return _addedPrivileges;
  }
  /**
   * @return Returns the addedUsers.
   */
  public Collection getAddedUsers() {
    return _addedUsers;
  }
  /**
   * @return Returns the allPrivileges.
   */
  public Map getAllPrivileges() {
    return _allPrivileges;
  }
  
  /**
   * @return Returns the allUsers.
   */
  public Map getAllUsers() {
    return _allUsers;
  }
  
  /**
   * @return Returns the selectedPrivileges.
   */
  //public String[] getSelectedPrivileges() {
  //  return _selectedPrivileges;
  //}
  
  /**
   * @return Returns the removedPrivileges.
   */
  public Collection getRemovedPrivileges() {
    return _removedPrivileges;
  }
  
  /**
   * @return Returns the removedUsers.
   */
  public Collection getRemovedUsers() {
    return _removedUsers;
  }
  
  /**
   * @return Returns the privilegeFilter.
   */
  public String getPrivilegeFilter() {
    return _privilegeFilter;
  }

  /**
   * @param string
   */
  public void setOperation(String string) {
    _operation = string;
  }
  
  public void setRole(RoleDto role) {
    _role = role;
  }
  
  /**
   * @param addedPrivileges The addedPrivileges to set.
   */
  public void setAddedPrivileges(Collection addedPrivileges) {
    _addedPrivileges = addedPrivileges;
  }
  
  /**
   * @param addedUsers The addedUsers to set.
   */
  public void setAddedUsers(Collection addedUsers) {
    _addedUsers = addedUsers;
  }
  
  /**
   * @param allPrivileges The allPrivileges to set.
   */
  public void setAllPrivileges(Map allPrivileges) {
    _allPrivileges = allPrivileges;
  }
  
  /**
   * @param allUsers The allUsers to set.
   */
  public void setAllUsers(Map allUsers) {
    _allUsers = allUsers;
  }
  
  /**
   * @param removedPrivileges The removedPrivileges to set.
   */
  public void setRemovedPrivileges(Collection removedPrivileges) {
    _removedPrivileges = removedPrivileges;
  }
  
  /**
   * @param removedUsers The removedUsers to set.
   */
  public void setRemovedUsers(Collection removedUsers) {
    _removedUsers = removedUsers;
  }

  /**
   * @param privilegeFilter The privilegeFilter to set.
   */
  public void setPrivilegeFilter(String privilegeFilter) {
    _privilegeFilter = privilegeFilter;
  }
}