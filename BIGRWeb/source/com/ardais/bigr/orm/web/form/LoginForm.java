package com.ardais.bigr.orm.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LoginForm extends BigrActionForm {
    private String _userId;
    private String _accountId;
    private String _password;
    private String _newPassword;
    private String _confirmPassword;

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
     */
    protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
        _userId = null;
        _accountId = null;
        _password = null;
        _newPassword = null;
        _confirmPassword = null;
    }

    /**
     * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
     */
    protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
       return null; 
    }

    /**
     * Returns the accountId.
     * @return String
     */
    public String getAccountId() {
        return _accountId;
    }

    /**
     * Returns the password.
     * @return String
     */
    public String getPassword() {
        return _password;
    }

    /**
     * Returns the userId.
     * @return String
     */
    public String getUserId() {
        return _userId;
    }

    /**
     * Sets the accountId.
     * @param accountId The accountId to set
     */
    public void setAccountId(String accountId) {
        _accountId = accountId;
    }

    /**
     * Sets the password.
     * @param password The password to set
     */
    public void setPassword(String password) {
        _password = password;
    }

    /**
     * Sets the userId.
     * @param userId The userId to set
     */
    public void setUserId(String userId) {
        _userId = userId;
    }

    /**
     * @return
     */
    public String getConfirmPassword() {
      return _confirmPassword;
    }

    /**
     * @return
     */
    public String getNewPassword() {
      return _newPassword;
    }

    /**
     * @param string
     */
    public void setConfirmPassword(String string) {
      _confirmPassword = string;
    }

    /**
     * @param string
     */
    public void setNewPassword(String string) {
      _newPassword = string;
    }
}
