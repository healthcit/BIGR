package com.ardais.bigr.orm.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.javabeans.UserDto;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class PasswordForm extends BigrActionForm {
  private UserDto _userData;
  private String _oldPassword;
  private String _reasonForChange;
  private boolean _requireLogin = true;
  private boolean _requireOldPassword = true;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _userData = null;
    _oldPassword = null;
    _reasonForChange = null;
    _requireLogin = true;
    _requireOldPassword = true;
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
  public String getOldPassword() {
    return _oldPassword;
  }

  /**
   * @return
   */
  public String getReasonForChange() {
    return _reasonForChange;
  }

  /**
   * @return
   */
  public boolean isRequireLogin() {
    return _requireLogin;
  }

  /**
   * @return
   */
  public boolean isRequireOldPassword() {
    return _requireOldPassword;
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
   * @param string
   */
  public void setOldPassword(String string) {
    _oldPassword = string;
  }

  /**
   * @param string
   */
  public void setReasonForChange(String string) {
    _reasonForChange = string;
  }

  /**
   * @param b
   */
  public void setRequireLogin(boolean b) {
    _requireLogin = b;
  }

  /**
   * @param b
   */
  public void setRequireOldPassword(boolean b) {
    _requireOldPassword = b;
  }

  /**
   * @param dto
   */
  public void setUserData(UserDto dto) {
    _userData = dto;
  }

}
