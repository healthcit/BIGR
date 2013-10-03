/*
 * Created on Jan 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
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
import com.ardais.bigr.javabeans.StringList;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.LogicalRepositoryUtils;

/**
 * Represent the details of a manage inventory groups transaction.
 * 
 * <p>The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserData(UserDto)}: A UserDto containing the id of the user.  OR</li>
 * <li>{@link #setAccountData(AccountDto)}: An AccountDto containing the id of the account.</li>
 * <li>{@link #setSelectedInventoryGroups(String[])}: A String array containing the inventory group ids
 *      to which the user or account should be given access.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setAssignedInventoryGroups(List)}: The inventory groups to which the 
 * user or account currently has access.</li>
 * <li>{@link #setAllInventoryGroups(List)}: A list of all inventory groups.</li>
 * <li>{@link #setRemovedInventoryGroups(List)}: Any inventory groups to which the user or account
 *      just had access removed.</li>
 * <li>{@link #setAddedInventoryGroups(List)}: Any inventory groups to which the user or account 
 *      just had access added.</li>
 * </ul>
 * 
 */
public class BtxDetailsManageInventoryGroups extends BTXDetails {
  private UserDto _userData = null;
  private AccountDto _accountData = null;
  private String[] _selectedInventoryGroups = null;
  private List _assignedInventoryGroups = null;
  private List _allInventoryGroups = null;
  private List _removedInventoryGroups = null;
  private List _addedInventoryGroups = null;
  private String _objectType = null;
  
  /** 
   * Default constructor.
   */
  public BtxDetailsManageInventoryGroups() {
    super();
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_MANAGE_INVENTORY_GROUPS;
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
    if (getAddedInventoryGroups() != null) {
      Iterator iterator = getAddedInventoryGroups().iterator();
      while (iterator.hasNext()) {
        LogicalRepository lr = (LogicalRepository)iterator.next();
        set.add(FormLogic.makePrefixedLogicalRepositoryId(lr.getId()));
      }
    }
    if (getRemovedInventoryGroups() != null) {
      Iterator iterator = getRemovedInventoryGroups().iterator();
      while (iterator.hasNext()) {
        LogicalRepository lr = (LogicalRepository)iterator.next();
        set.add(FormLogic.makePrefixedLogicalRepositoryId(lr.getId()));
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
    // setObjectType
    // one of the following, depending upon object type
    //   getUserData().setUserId
    //   getAccountData.setId
    // setAddedInventoryGroups
    // setRemovedInventoryGroups
    // setAssignedInventoryGroups - note this will be null
    // setAllInventoryGroups - note this will be null
    // setSelectedInventoryGroups - note this will be null

    // The result has this form:
    //    User <userId> had access to the following inventory groups revoked: <inventory group names>.
    //    They were granted access to the following inventory groups: <inventory group names>.
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
    if (getRemovedInventoryGroups().size() == 0 && 
        getAddedInventoryGroups().size() == 0) {
      sb.append(" had no changes made to");
      if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(getObjectType())) {
        sb.append(" its");
      }
      else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(getObjectType())) {
        sb.append(" their");
      }
      sb.append(" inventory group access.");
    }
    else {
      if (getRemovedInventoryGroups().size() > 0) {
        sb.append(" had access to the following inventory groups revoked: ");
        Iterator iterator = getRemovedInventoryGroups().iterator();
        boolean addComma = false;
        while (iterator.hasNext()) {
          if (addComma) {
            sb.append(", ");
          }
          addComma = true;
          sb.append(((LogicalRepository)iterator.next()).getFullName());
        }
        sb.append(".");
      }
      if (getAddedInventoryGroups().size() > 0) {
        //if we said something about removed inventory groups, start a new sentence.
        if (getRemovedInventoryGroups().size() > 0) {
          if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(getObjectType())) {
            sb.append(" It was ");
          }
          else if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(getObjectType())) {
            sb.append(" They were ");
          }
        }
        else {
          sb.append(" was ");
        }
        sb.append("granted access to the following inventory groups: ");
        Iterator iterator = getAddedInventoryGroups().iterator();
        boolean addComma = false;
        while (iterator.hasNext()) {
          if (addComma) {
            sb.append(", ");
          }
          addComma = true;
          sb.append(((LogicalRepository)iterator.next()).getFullName());
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
    IdList addedIGIds = new IdList();
    StringList addedIGNames = new StringList();
    Iterator iterator = getAddedInventoryGroups().iterator();
    while (iterator.hasNext()) {
      LogicalRepository lr = (LogicalRepository)iterator.next();
      addedIGIds.add(lr.getId());
      addedIGNames.add(lr.getFullName());
    }
    IdList removedIGIds = new IdList();
    StringList removedIGNames = new StringList();
    iterator = getRemovedInventoryGroups().iterator();
    while (iterator.hasNext()) {
      LogicalRepository lr = (LogicalRepository)iterator.next();
      removedIGIds.add(lr.getId());
      removedIGNames.add(lr.getFullName());
    }
    history.setAttrib1(getObjectType());
    history.setAttrib2(getUserData().getUserId());
    history.setAttrib3(getUserData().getAccountId());
    history.setAttrib4(getAccountData().getId());
    history.setIdList1(addedIGIds);
    history.setClob1(addedIGNames.pack());
    history.setIdList2(removedIGIds);
    history.setClob2(removedIGNames.pack());
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#populateFromHistoryRecord(com.ardais.bigr.iltds.btx.BTXHistoryRecord)
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);
    IdList addedIGIds = history.getIdList1();
    StringList addedIGNames = new StringList(history.getClob1());
    Iterator idIterator = addedIGIds.iterator();
    Iterator nameIterator = addedIGNames.iterator();
    List addedIGs = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      LogicalRepository lr = new LogicalRepository();
      lr.setFullName((String)nameIterator.next());
      lr.setId((String)idIterator.next());
      addedIGs.add(lr);
    }
    IdList removedIGIds = history.getIdList2();
    StringList removedIGNames = new StringList(history.getClob2());
    idIterator = removedIGIds.iterator();
    nameIterator = removedIGNames.iterator();
    List removedIGs = new ArrayList();
    while (idIterator.hasNext() && nameIterator.hasNext()) {
      LogicalRepository lr = new LogicalRepository();
      lr.setFullName((String)nameIterator.next());
      lr.setId((String)idIterator.next());
      removedIGs.add(lr);
    }
    setObjectType(history.getAttrib1());
    getUserData().setUserId(history.getAttrib2());
    getUserData().setAccountId(history.getAttrib3());
    getAccountData().setId(history.getAttrib4());
    setAddedInventoryGroups(addedIGs);
    setRemovedInventoryGroups(removedIGs);
    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    setAssignedInventoryGroups(null);
    setAllInventoryGroups(null);
    setSelectedInventoryGroups(null);
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
  public AccountDto getAccountData() {
    if (_accountData == null) {
      _accountData = new AccountDto();
    }
    return _accountData;
  }

  /**
   * @param dto
   */
  public void setAccountData(AccountDto dto) {
    _accountData = dto;
  }

  private void addIcpLink(LogicalRepository lr, StringBuffer sb) {
    IdList ilist = new IdList();
    ilist.add(lr.getId());
    ilist = LogicalRepositoryUtils.makePrefixedLogicalRepositoryIds(ilist);
    StringList sList = new StringList();
    sList.add(lr.getFullName());
    ilist.appendICPHTML(sb, sList.getList(), getLoggedInUserSecurityInfo());    
  }
  /**
   * @return
   */
  public List getAddedInventoryGroups() {
    return _addedInventoryGroups;
  }

  /**
   * @return
   */
  public List getAllInventoryGroups() {
    return _allInventoryGroups;
  }

  /**
   * @return
   */
  public List getAssignedInventoryGroups() {
    return _assignedInventoryGroups;
  }

  /**
   * @return
   */
  public String getObjectType() {
    return _objectType;
  }

  /**
   * @return
   */
  public List getRemovedInventoryGroups() {
    return _removedInventoryGroups;
  }

  /**
   * @return
   */
  public String[] getSelectedInventoryGroups() {
    return _selectedInventoryGroups;
  }

  /**
   * @param list
   */
  public void setAddedInventoryGroups(List list) {
    _addedInventoryGroups = list;
  }

  /**
   * @param list
   */
  public void setAllInventoryGroups(List list) {
    _allInventoryGroups = list;
  }

  /**
   * @param list
   */
  public void setAssignedInventoryGroups(List list) {
    _assignedInventoryGroups = list;
  }

  /**
   * @param string
   */
  public void setObjectType(String string) {
    _objectType = string;
  }

  /**
   * @param list
   */
  public void setRemovedInventoryGroups(List list) {
    _removedInventoryGroups = list;
  }

  /**
   * @param strings
   */
  public void setSelectedInventoryGroups(String[] strings) {
    _selectedInventoryGroups = strings;
  }

}
