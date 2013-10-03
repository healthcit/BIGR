package com.ardais.bigr.orm.web.form;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.javabeans.AccountDto;
import com.ardais.bigr.javabeans.LocationData;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.orm.helpers.FormLogic;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author jesielionis
 *
 */
public class ManagePrivilegesForm extends BigrActionForm implements AccountBasedForm {
  private UserDto _userData;
  private AccountDto _accountData;
  //the list of privileges to which the user had access at the start of this transaction
  //(used to initialize the form)
  private List _assignedPrivileges = null;
  //a list of privileges to which the user has access via their membership in account roles
  //(used to initialize the form)
  private List _roleBasedPrivileges = null;
  //a map of all available privileges (used to initialize the form)
  private Map _allPrivileges = null;
  //the ids of the privileges to which the user should have access at the completion
  //of this transaction. (Set when the form is submitted)
  private String[] _selectedPrivileges = null;
  //the privilege filter in use
  private String _privilegeFilter = null;
  //the type of object for which we are setting privileges
  private String _objectType = null;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _userData = null;
    _accountData = null;
    _assignedPrivileges = null;
    _roleBasedPrivileges = null;
    _allPrivileges = null;
    _selectedPrivileges = null;
    _privilegeFilter = FormLogic.ALL;
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
  public String[] getSelectedPrivileges() {
    return _selectedPrivileges;
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
  public String getObjectType() {
    return _objectType;
  }

  /**
   * @param string
   */
  public void setObjectType(String string) {
    _objectType = string;
  }

}
