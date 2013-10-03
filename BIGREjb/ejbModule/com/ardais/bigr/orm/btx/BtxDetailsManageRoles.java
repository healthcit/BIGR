package com.ardais.bigr.orm.btx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.RoleDto;
import com.ardais.bigr.javabeans.StringList;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.util.LogicalRepositoryUtils;

/**
 * Represent the details of a manage roles transaction.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserData(UserDto)}: A UserDto containing the id of the user.</li>
 * <li>{@link #setSelectedRoles(String[])}: A String array containing the role ids
 *      to which the user should be given access.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setAssignedRoles(List)}: The roles to which the user currently has access.</li>
 * <li>{@link #setAllRoles(List)}: A list of all roles.</li>
 * <li>{@link #setRemovedRoles(List)}: Any roles to which the user just had access removed.</li>
 * <li>{@link #setAddedRoles(List)}: Any roles to which the user just had access added.</li>
 * </ul>
 * 
 */
public class BtxDetailsManageRoles extends BTXDetails {
  private UserDto _userData = null;
  private String[] _selectedRoles = null;
  private List _assignedRoles = null;
  private List _allRoles = null;
  private List _removedRoles = null;
  private List _addedRoles = null;
  
  /** 
   * Default constructor.
   */
  public BtxDetailsManageRoles() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MANAGE_ROLES;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    set.add(getUserData().getUserId());
    if (getAddedRoles() != null) {
      Iterator iterator = getAddedRoles().iterator();
      while (iterator.hasNext()) {
        RoleDto role = (RoleDto)iterator.next();
        set.add(role.getId());
      }
    }
    if (getRemovedRoles() != null) {
      Iterator iterator = getRemovedRoles().iterator();
      while (iterator.hasNext()) {
        RoleDto role = (RoleDto)iterator.next();
        set.add(role.getId());
      }
    }
    return set;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    // NOTE: This method must not make use of any fields that aren't
    // set by the populateFromHistoryRecord method.
    //
    // For this object type, the fields we can use here are:
    // getUserData().setUserId
    // setAddedRoles
    // setRemovedRoles
    // setAssignedRoles - note this will be null
    // setAllRoles - note this will be null
    // setSelectedRoles - note this will be null

    // The result has this form:
    //    User <userId> had access to the following roles revoked: <role names>.
    //    They were granted access to the following roles: <role names>.
    StringBuffer sb = new StringBuffer(512);
    SecurityInfo securityInfo = getLoggedInUserSecurityInfo();

    sb.append("User ");
    sb.append(getUserData().getUserId());
    //if no changes were made, say that
    if (getRemovedRoles().size() == 0 && 
        getAddedRoles().size() == 0) {
      sb.append(" had no changes made to their role assignments.");
    }
    else {
      if (getRemovedRoles().size() > 0) {
        sb.append(" had membership in the following roles revoked: ");
        Iterator iterator = getRemovedRoles().iterator();
        boolean addComma = false;
        while (iterator.hasNext()) {
          if (addComma) {
            sb.append(", ");
          }
          addComma = true;
          RoleDto role = (RoleDto)iterator.next();
          sb.append(role.getName());
          sb.append(" (");
          sb.append(IcpUtils.prepareLink(role.getId(), securityInfo));
          sb.append(")");
        }
        sb.append(".");
      }
      if (getAddedRoles().size() > 0) {
        //if we said something about removed roles, start a new sentence.
        if (getRemovedRoles().size() > 0) {
          sb.append(" They were ");
        }
        else {
          sb.append(" was ");
        }
        sb.append("granted membership in the following roles: ");
        Iterator iterator = getAddedRoles().iterator();
        boolean addComma = false;
        while (iterator.hasNext()) {
          if (addComma) {
            sb.append(", ");
          }
          addComma = true;
          RoleDto role = (RoleDto)iterator.next();
          sb.append(role.getName());
          sb.append(" (");
          sb.append(IcpUtils.prepareLink(role.getId(), securityInfo));
          sb.append(")");
        }
        sb.append(".");
      }
    }
    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#describeIntoHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);
    IdList addedRoleIds = new IdList();
    StringList addedRoleNames = new StringList();
    Iterator iterator = getAddedRoles().iterator();
    while (iterator.hasNext()) {
      RoleDto role = (RoleDto)iterator.next();
      addedRoleIds.add(role.getId());
      addedRoleNames.add(role.getName());
    }
    IdList removedRoleIds = new IdList();
    StringList removedRoleNames = new StringList();
    iterator = getRemovedRoles().iterator();
    while (iterator.hasNext()) {
      RoleDto role = (RoleDto)iterator.next();
      removedRoleIds.add(role.getId());
      removedRoleNames.add(role.getName());
    }
    history.setAttrib1(getUserData().getUserId());
    history.setAttrib2(getUserData().getAccountId());
    history.setIdList1(addedRoleIds);
    history.setClob1(addedRoleNames.pack());
    history.setIdList2(removedRoleIds);
    history.setClob2(removedRoleNames.pack());
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    IdList addedRoleIds = history.getIdList1();
    StringList addedRoleNames = new StringList(history.getClob1());
    Iterator idIterator = addedRoleIds.iterator();
    Iterator nameIterator = addedRoleNames.iterator();
    List addedRoles = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      RoleDto role = new RoleDto();
      role.setName((String)nameIterator.next());
      role.setId((String)idIterator.next());
      addedRoles.add(role);
    }
    IdList removedRoleIds = history.getIdList2();
    StringList removedRoleNames = new StringList(history.getClob2());
    idIterator = removedRoleIds.iterator();
    nameIterator = removedRoleNames.iterator();
    List removedRoles = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      RoleDto role = new RoleDto();
      role.setName((String)nameIterator.next());
      role.setId((String)idIterator.next());
      removedRoles.add(role);
    }
    getUserData().setUserId(history.getAttrib1());
    getUserData().setAccountId(history.getAttrib2());
    setAddedRoles(addedRoles);
    setRemovedRoles(removedRoles);
    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    setAssignedRoles(null);
    setAllRoles(null);
    setSelectedRoles(null);
  }

  /**
   * @return
   */
  public UserDto getUserData() {
    if (_userData == null) {
      _userData = new UserDto();
    }
    return _userData;
  }

  /**
   * @param dto
   */
  public void setUserData(UserDto dto) {
    _userData = dto;
  }

  /**
   * @return
   */
  public List getAddedRoles() {
    return _addedRoles;
  }

  /**
   * @return
   */
  public List getAllRoles() {
    return _allRoles;
  }

  /**
   * @return
   */
  public List getAssignedRoles() {
    return _assignedRoles;
  }

  /**
   * @return
   */
  public List getRemovedRoles() {
    return _removedRoles;
  }

  /**
   * @return
   */
  public String[] getSelectedRoles() {
    return _selectedRoles;
  }

  /**
   * @param list
   */
  public void setAddedRoles(List list) {
    _addedRoles = list;
  }

  /**
   * @param list
   */
  public void setAllRoles(List list) {
    _allRoles = list;
  }

  /**
   * @param list
   */
  public void setAssignedRoles(List list) {
    _assignedRoles = list;
  }

  /**
   * @param list
   */
  public void setRemovedRoles(List list) {
    _removedRoles = list;
  }

  /**
   * @param strings
   */
  public void setSelectedRoles(String[] strings) {
    _selectedRoles = strings;
  }

}
