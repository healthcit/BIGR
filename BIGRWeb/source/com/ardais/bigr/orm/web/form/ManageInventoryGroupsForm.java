/*
 * Created on Jan 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.orm.web.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.LocationData;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;


/**
 * @author jesielionis
 *
 */
public class ManageInventoryGroupsForm extends BigrActionForm implements AccountBasedForm {
  private UserDto _userData;
  private AccountDto _accountData;
  //the list of inventory groups to which the user had access at the start of this transaction
  //(used to initialize the form)
  private List _assignedInventoryGroups = null;
  //the list of all available inventory groups (used to initialize the form)
  private List _allInventoryGroups = null;
  //the ids of the inventory groups to which the user should have access at the completion
  //of this transaction.
  //(Set when the form is submitted)
  private String[] _selectedInventoryGroups = null;
  //the type of object for which we are setting inventory groups
  private String _objectType = null;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _userData = null;
    _accountData = null;
    _assignedInventoryGroups = null;
    _allInventoryGroups = null;
    _selectedInventoryGroups = null;
    _objectType = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
     return null; 
  }
  
  /**
   * @return
   */
  public UserDto getUserData() {
    if (_userData == null) {
      _userData = new UserDto();
      _userData.setAddress(new LocationData());
    }
    return _userData;
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
   * @return a String containing information about the user
   */
  public String getObjectInformation() {
    StringBuffer buff = new StringBuffer(50);
    if (Constants.OBJECT_TYPE_USER.equalsIgnoreCase(getObjectType())) {
      UserDto userData = getUserData();
      String firstname = userData.getFirstName();
      String lastname = userData.getLastName();
      if (firstname != null && lastname != null) {
        buff.append(firstname);
        buff.append(" ");
        buff.append(lastname);
      }
      String account = userData.getAccountId();
      if (account != null) {
        buff.append(" (");
        buff.append(account);
        buff.append(")");
      }
    }
    else if (Constants.OBJECT_TYPE_ACCOUNT.equalsIgnoreCase(getObjectType())) {
      AccountDto accountData = getAccountData();
      String name = accountData.getName();
      String id = accountData.getId();
      if (!ApiFunctions.isEmpty(id)) {
        buff.append(id);
        if (!ApiFunctions.isEmpty(name)) {
          buff.append(" (");
          buff.append(name);
          buff.append(")");
        }
      }
    }
    return buff.toString();
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
  public String[] getSelectedInventoryGroups() {
    return _selectedInventoryGroups;
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
   * @param strings
   */
  public void setSelectedInventoryGroups(String[] strings) {
    _selectedInventoryGroups = strings;
  }

}
