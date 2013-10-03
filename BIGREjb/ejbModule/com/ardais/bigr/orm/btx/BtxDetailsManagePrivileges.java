package com.ardais.bigr.orm.btx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.PrivilegeDto;
import com.ardais.bigr.javabeans.StringList;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.util.Constants;

/**
 * Represent the details of a manage privileges transaction.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserData(UserDto)}: A UserDto containing the id of the user.  OR</li>
 * <li>{@link #setAccountData(AccountDto)}: An AccountDto containing the id of the account.</li>
 * <li>{@link #setSelectedPrivileges(String[])}: A String array containing the privilege ids
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
 * <li>{@link #setAssignedPrivileges(List)}: The privileges to which the 
 * user currently has access.</li>
 * <li>{@link #setAllPrivileges(Map)}: A map of all privileges, keyed by functional area.</li>
 * <li>{@link #setRoleBasedPrivileges(List)}: A list of privileges to which the user has access
 * via their membership in account roles.</li>
 * <li>{@link #setRemovedPrivileges(List)}: Any privileges to which the user just 
 *      had access removed.</li>
 * <li>{@link #setAddedPrivileges(List)}: Any privileges to which the user just 
 *      had access added.</li>
 * </ul>
 * 
 */
public class BtxDetailsManagePrivileges extends BTXDetails {
  private UserDto _userData = null;
  private AccountDto _accountData = null;
  private String[] _selectedPrivileges = null;
  private List _assignedPrivileges = null;
  private List _roleBasedPrivileges = null;
  private Map _allPrivileges = null;
  private List _removedPrivileges = null;
  private List _addedPrivileges = null;
  private String _privilegeFilter = null;
  private String _objectType = null;
  
  /** 
   * Default constructor.
   */
  public BtxDetailsManagePrivileges() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MANAGE_PRIVILEGES;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    Set set = new HashSet();
    if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(getObjectType())) {
      set.add(getAccountData().getId());
    }
    else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(getObjectType())) {
      set.add(getUserData().getUserId());
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
    // setObjectType
    // one of the following, depending upon object type
    //   getUserData().setUserId
    //   getAccountData.setId
    // setAddedPrivileges
    // setRemovedPrivileges
    // setAssignedPrivileges - note this will be null
    // setAllPrivileges - note this will be null
    // setSelectedPrivileges - note this will be null
    // setRoleBasedPrivileges - note this will be null

    // The result has this form:
    //    User <userId> had access to the following privilege revoked: <privilege names>.
    //    They were granted access to the following privileges: <privilege names>.
    StringBuffer sb = new StringBuffer(512);
    if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(getObjectType())) {
      sb.append("Account ");
      Escaper.htmlEscape(getAccountData().getId(), sb);
    }
    else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(getObjectType())) {
      sb.append("User ");
      Escaper.htmlEscape(getUserData().getUserId(), sb);
    }
    //if no changes were made, say that
    if (getRemovedPrivileges().size() == 0 && 
        getAddedPrivileges().size() == 0) {
      sb.append(" had no changes made to");
      if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(getObjectType())) {
        sb.append(" its");
      }
      else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(getObjectType())) {
        sb.append(" their");
      }
      sb.append(" privilege access.");
    }
    else {
      if (getRemovedPrivileges().size() > 0) {
        sb.append(" had access to the following privileges revoked: ");
        Iterator iterator = getRemovedPrivileges().iterator();
        boolean addComma = false;
        while (iterator.hasNext()) {
          if (addComma) {
            sb.append(", ");
          }
          addComma = true;
          sb.append(((PrivilegeDto)iterator.next()).getDescription());
        }
        sb.append(".");
      }
      if (getAddedPrivileges().size() > 0) {
        //if we said something about removed privileges, start a new sentence.
        if (getRemovedPrivileges().size() > 0) {
          if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(getObjectType())) {
            sb.append(" It was");
          }
          else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(getObjectType())) {
            sb.append(" They were ");
          }
        }
        else {
          sb.append(" was ");
        }
        sb.append("granted access to the following privileges: ");
        Iterator iterator = getAddedPrivileges().iterator();
        boolean addComma = false;
        while (iterator.hasNext()) {
          if (addComma) {
            sb.append(", ");
          }
          addComma = true;
          sb.append(((PrivilegeDto)iterator.next()).getDescription());
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
    IdList addedPrivilegeIds = new IdList();
    StringList addedPrivilegeNames = new StringList();
    Iterator iterator = getAddedPrivileges().iterator();
    while (iterator.hasNext()) {
      PrivilegeDto privilege = (PrivilegeDto)iterator.next();
      addedPrivilegeIds.add(privilege.getId());
      addedPrivilegeNames.add(privilege.getDescription());
    }
    IdList removedPrivilegeIds = new IdList();
    StringList removedPrivilegeNames = new StringList();
    iterator = getRemovedPrivileges().iterator();
    while (iterator.hasNext()) {
      PrivilegeDto privilege = (PrivilegeDto)iterator.next();
      removedPrivilegeIds.add(privilege.getId());
      removedPrivilegeNames.add(privilege.getDescription());
    }
    history.setAttrib1(getObjectType());
    history.setAttrib2(getUserData().getUserId());
    history.setAttrib3(getUserData().getAccountId());
    history.setAttrib4(getAccountData().getId());
    history.setIdList1(addedPrivilegeIds);
    history.setClob1(addedPrivilegeNames.pack());
    history.setIdList2(removedPrivilegeIds);
    history.setClob2(removedPrivilegeNames.pack());
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    IdList addedPrivilegeIds = history.getIdList1();
    StringList addedPrivilegeNames = new StringList(history.getClob1());
    Iterator idIterator = addedPrivilegeIds.iterator();
    Iterator nameIterator = addedPrivilegeNames.iterator();
    List addedPrivileges = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      PrivilegeDto privilege = new PrivilegeDto();
      privilege.setDescription((String)nameIterator.next());
      privilege.setId((String)idIterator.next());
      addedPrivileges.add(privilege);
    }
    IdList removedPrivilegeIds = history.getIdList2();
    StringList removedPrivilegeNames = new StringList(history.getClob2());
    idIterator = removedPrivilegeIds.iterator();
    nameIterator = removedPrivilegeNames.iterator();
    List removedPrivileges = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      PrivilegeDto privilege = new PrivilegeDto();
      privilege.setDescription((String)nameIterator.next());
      privilege.setId((String)idIterator.next());
      removedPrivileges.add(privilege);
    }
    setObjectType(history.getAttrib1());
    getUserData().setUserId(history.getAttrib2());
    getUserData().setAccountId(history.getAttrib3());
    getAccountData().setId(history.getAttrib4());
    setAddedPrivileges(addedPrivileges);
    setRemovedPrivileges(removedPrivileges);
    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    setAssignedPrivileges(null);
    setAllPrivileges(null);
    setRoleBasedPrivileges(null);
    setSelectedPrivileges(null);
    setPrivilegeFilter(null);
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
   * @return
   */
  public List getAddedPrivileges() {
    return _addedPrivileges;
  }

  /**
   * @return
   */
  public Map getAllPrivileges() {
    return _allPrivileges;
  }

  /**
   * @return Returns the roleBasedPrivileges.
   */
  public List getRoleBasedPrivileges() {
    return _roleBasedPrivileges;
  }
  
  /**
   * @return
   */
  public List getAssignedPrivileges() {
    return _assignedPrivileges;
  }

  /**
   * @return
   */
  public List getRemovedPrivileges() {
    return _removedPrivileges;
  }

  /**
   * @return
   */
  public String[] getSelectedPrivileges() {
    return _selectedPrivileges;
  }

  /**
   * @param dto
   */
  public void setUserData(UserDto dto) {
    _userData = dto;
  }

  /**
   * @param list
   */
  public void setAddedPrivileges(List list) {
    _addedPrivileges = list;
  }

  /**
   * @param map
   */
  public void setAllPrivileges(Map map) {
    _allPrivileges = map;
  }
  
  /**
   * @param roleBasedPrivileges The roleBasedPrivileges to set.
   */
  public void setRoleBasedPrivileges(List roleBasedPrivileges) {
    _roleBasedPrivileges = roleBasedPrivileges;
  }

  /**
   * @param list
   */
  public void setAssignedPrivileges(List list) {
    _assignedPrivileges = list;
  }

  /**
   * @param list
   */
  public void setRemovedPrivileges(List list) {
    _removedPrivileges = list;
  }

  /**
   * @param strings
   */
  public void setSelectedPrivileges(String[] strings) {
    _selectedPrivileges = strings;
  }

  /**
   * @return
   */
  public String getPrivilegeFilter() {
    return _privilegeFilter;
  }

  /**
   * @param string
   */
  public void setPrivilegeFilter(String string) {
    _privilegeFilter = string;
  }

  /**
   * @return
   */
  public AccountDto getAccountData() {
    if (_accountData == null) {
      _accountData = new AccountDto();
    }
    return _accountData;
  }

  /**
   * @return
   */
  public String getObjectType() {
    return _objectType;
  }

  /**
   * @param dto
   */
  public void setAccountData(AccountDto dto) {
    _accountData = dto;
  }

  /**
   * @param string
   */
  public void setObjectType(String string) {
    _objectType = string;
  }

}
