package com.ardais.bigr.orm.web.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.javabeans.LocationData;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;


/**
 * @author jesielionis
 *
 */
public class ManageRolesForm extends BigrActionForm {
  private UserDto _userData;
  //the list of roles to which the user had access at the start of this transaction
  //(used to initialize the form)
  private List _assignedRoles = null;
  //the list of all available roles (used to initialize the form)
  private List _allRoles = null;
  //the ids of the roles to which the user should have access at the completion
  //of this transaction.
  //(Set when the form is submitted)
  private String[] _selectedRoles = null;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _userData = null;
    _assignedRoles = null;
    _allRoles = null;
    _selectedRoles = null;
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
   * @return a String containing information about the user
   */
  public String getObjectInformation() {
    StringBuffer buff = new StringBuffer(50);
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
    return buff.toString();
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
  public String[] getSelectedRoles() {
    return _selectedRoles;
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
   * @param strings
   */
  public void setSelectedRoles(String[] strings) {
    _selectedRoles = strings;
  }

}
