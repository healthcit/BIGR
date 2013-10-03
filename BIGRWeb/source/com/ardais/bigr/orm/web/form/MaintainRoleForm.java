package com.ardais.bigr.orm.web.form;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.PrivilegeDto;
import com.ardais.bigr.javabeans.RoleDto;
import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.orm.btx.BtxDetailsMaintainRoleStart;
import com.ardais.bigr.orm.helpers.FormLogic;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for maintaining roles.
 */
public class MaintainRoleForm extends BigrActionForm {
  private boolean _resetForm;
  private boolean _retry;
  
  //"Create", "Update", or "Delete"
  private String _operation;
  //a list of the existing roles
  private List _roles;  
  //the role currently being created, updated, or deleted
  private RoleDto _role;
  //a map of all available users (used to initialize the form)
  private Map _allUsers = null;
  //the ids of the users to whom the role should be granted at the completion
  //of this transaction. (Set when the form is submitted)
  private String[] _selectedUsers = null;
  //a map of all available privileges (used to initialize the form)
  private Map _allPrivileges = null;
  //the ids of the privileges to which the role should have access at the completion
  //of this transaction. (Set when the form is submitted)
  private String[] _selectedPrivileges = null;
  //the privilege filter in use
  private String _privilegeFilter = null;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _resetForm = false;
    _retry = false;
    _operation = Constants.OPERATION_CREATE;
    _roles = null;
    _role = null;
    _allUsers = null;
    _selectedUsers = null;
    _allPrivileges = null;
    _selectedPrivileges = null;
    _privilegeFilter = FormLogic.ALL;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Note: only primitive validations occur here. Any business rule or
    // business validation should occur in the BTX performer class.

    ActionErrors errors = new ActionErrors();
    ActionError error = null;

    boolean doNotValidate = false;

    // If retry is true, it means we're coming back to this form to display validation errors.
    // In most cases we don't have to do anything special, but the "Delete" operation is a
    // special case since the form fields aren't used to gather input for Delete operations.
    // So, when the operation is "Delete" and we're retrying, we reset the form.  The user
    // will still see the validation errors, but the form will otherwise look like a fresh
    // "Create" form.
    //
    // If we're retrying we don't do any further validation, since we can assume in this
    // case that there are problems and we're just trying to report them.
    //
    if (isRetry()) {
      if (Constants.OPERATION_DELETE.equals(getOperation())) {
        setResetForm(true);
      }
    }

    // The isResetForm() "field" is an instruction that the form fields are to be reset to their
    // default values before proceeding.
    //
    // When we reset the form, we don't do any further validation.
    //
    if (isResetForm()) {
      doNotValidate = true;
      doReset(mapping, request);
    }

    if (doNotValidate) {
      return null;
    }
    else {
      // No further validations here.  All validations are done in the BTX business logic bean.
      return errors;
    }
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {

    super.describeIntoBtxDetails(btxDetails0, mapping, request);
    
    //populate the RoleDto on the btxDetails with any selected users and privileges
    if (!ApiFunctions.isEmpty(getSelectedUsers())) {
      for (int i = 0; i < getSelectedUsers().length; i++) {
        UserDto user = new UserDto();
        user.setUserId(getSelectedUsers()[i]);
        getRole().getUsers().add(user);
      }
    }
    if (!ApiFunctions.isEmpty(getSelectedPrivileges())) {
      for (int i = 0; i < getSelectedPrivileges().length; i++) {
        PrivilegeDto privilege = new PrivilegeDto();
        privilege.setId(getSelectedPrivileges()[i]);
        getRole().getPrivileges().add(privilege);
      }
    }
    
    // If we're retrying and the btxDetails object is the "Start" version, set an extra
    // flag on the btxDetails object telling it not to populate some of its usual output
    // fields.  This is useful when the user attempted an update but got a validation
    // error, and we want to keep their data fields as-is for them to correct instead of
    // resetting them to the values in the database, which is what would happen by default.
    if (isRetry() && btxDetails0 instanceof BtxDetailsMaintainRoleStart) {
      BtxDetailsMaintainRoleStart btxDetails =
        (BtxDetailsMaintainRoleStart) btxDetails0;
      btxDetails.setPopulateRoleOutputFields(false);
    }
  }
  
  /**
   * Return the legal value set of ardais accounts.
   * 
   * @return LegalValueSet
   */
  public LegalValueSet getAccountChoices() {
    return IltdsUtils.getAccountList(true);
  }
  
  public String getAccountName() {
    String accountName= null;
    if (getRole() != null) {
      accountName = getRole().getAccountName();
    }
    else {
      accountName = "";
    }
    return accountName;
  }

  /**
   * @return
   */
  public String getOperation() {
    return _operation;
  }

  /**
   * @return
   */
  public List getRoles() {
    return _roles;
  }
  
  /**
   * @return
   */
  public boolean isResetForm() {
    return _resetForm;
  }

  /**
   * @return
   */
  public boolean isRetry() {
    return _retry;
  }

  /**
   * @param string
   */
  public void setOperation(String string) {
    _operation = string;
  }

  /**
   * @param list
   */
  public void setRoles(List list) {
    _roles = list;
  }

  /**
   * @param b
   */
  public void setResetForm(boolean b) {
    _resetForm = b;
  }

  /**
   * @param b
   */
  public void setRetry(boolean b) {
    _retry = b;
  }
  
  public RoleDto getRole() {
    if (_role == null) {
      _role = new RoleDto();
    }
    return _role;
  }
  
  public void setRole(RoleDto role) {
    _role = role;
  }
  
  /**
   * @return Returns the allPrivileges.
   */
  public Map getAllPrivileges() {
    return _allPrivileges;
  }
  
  /**
   * @return Returns the privilegeFilter.
   */
  public String getPrivilegeFilter() {
    return _privilegeFilter;
  }
  
  /**
   * @return Returns the selectedPrivileges.
   */
  public String[] getSelectedPrivileges() {
    return _selectedPrivileges;
  }
  
  /**
   * @return Returns the allUsers.
   */
  public Map getAllUsers() {
    return _allUsers;
  }
  
  /**
   * @return Returns the selectedUsers.
   */
  public String[] getSelectedUsers() {
    return _selectedUsers;
  }
  
  /**
   * @param allPrivileges The allPrivileges to set.
   */
  public void setAllPrivileges(Map allPrivileges) {
    _allPrivileges = allPrivileges;
  }
  
  /**
   * @param privilegeFilter The privilegeFilter to set.
   */
  public void setPrivilegeFilter(String privilegeFilter) {
    _privilegeFilter = privilegeFilter;
  }
  
  /**
   * @param selectedPrivileges The selectedPrivileges to set.
   */
  public void setSelectedPrivileges(String[] selectedPrivileges) {
    _selectedPrivileges = selectedPrivileges;
  }
  
  /**
   * @param allUsers The allUsers to set.
   */
  public void setAllUsers(Map allUsers) {
    _allUsers = allUsers;
  }
  
  /**
   * @param selectedUsers The selectedUsers to set.
   */
  public void setSelectedUsers(String[] selectedUsers) {
    _selectedUsers = selectedUsers;
  }
}
