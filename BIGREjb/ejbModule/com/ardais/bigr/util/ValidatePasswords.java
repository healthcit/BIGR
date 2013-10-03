package com.ardais.bigr.util;

import javax.ejb.ObjectNotFoundException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.es.beans.ArdaisuserAccessBean;
import com.ardais.bigr.es.beans.ArdaisuserKey;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;

public class ValidatePasswords {
  
  private String _newPasswordText;
  private String _confirmPasswordText;
  private String _userId;
  private String _accountId;

  private static final int MINIMUM_PASSWORD_LENGTH = 6;
  private static final int MAXIMUM_PASSWORD_LENGTH = 100;
  
  public ValidatePasswords() {
  }

  public ValidatePasswords(
    String newPasswordText,
    String confirmPasswordText,
    String userId,
    String accountId)
  {
    setNewPasswordText(ApiFunctions.safeTrim(newPasswordText));
    setConfirmPasswordText(ApiFunctions.safeTrim(confirmPasswordText));
    setUserId(userId);
    setAccountId(accountId);
  }

  public String checkAllPasswordRules( ) {

    if (ApiFunctions.isEmpty(_newPasswordText)) {
      return "Please specify a non-null value for the new password";
    }
    if (!(_newPasswordText.equals(_confirmPasswordText))) {
      return "The two password values do not match";
    }
    if (!checkForValidLength()) {
      return "All passwords must be at least six, but not more than 100, characters in length";
    }
    if (!checkForValidSyntax()) {
      return "All passwords must have at least one digit and one letter";
    }
    if (checkForSameAsUserId()) {    
      return "The password cannot be the same as the userid"; 
    }
    if (checkForOldPassword()) {
      return "The password cannot be the same as the old password.";
    }

     return null; // indicates that password validation succeeded 
  }

  public void checkAllPasswordRules(BTXDetails btxDetails) {
    
    if (ApiFunctions.isEmpty(_newPasswordText)) {
      btxDetails.addActionError(new BtxActionError("orm.error.password.nullPassword"));
      return;
    }
    if (!(_newPasswordText.equals(_confirmPasswordText))) {
      btxDetails.addActionError(new BtxActionError("orm.error.password.mismatchPasswords"));
    }
    if (!checkForValidLength()) {
      btxDetails.addActionError(new BtxActionError("orm.error.password.invalidPasswordLength"));
    }
    if (!checkForValidSyntax()) {
      btxDetails.addActionError(new BtxActionError("orm.error.password.invalidPassword"));
    }
    if (checkForSameAsUserId()) {    
      btxDetails.addActionError(new BtxActionError("orm.error.password.userIdViolation"));
    }
    if (checkForOldPassword()) {
      btxDetails.addActionError(new BtxActionError("orm.error.password.samePasswordUsed"));
    }
  }

  private boolean checkForValidLength() {
    int length = _newPasswordText.length();
    return ((length >= MINIMUM_PASSWORD_LENGTH) &&
            (length <= MAXIMUM_PASSWORD_LENGTH));
  }

  private boolean checkForValidSyntax() { 
    String newPassword = new String(_newPasswordText);

    // look for a digit...
    char isChar;
    boolean foundDigit = false;
    boolean foundLetter = false;
    for (int i=0; i < newPassword.length(); i++) {
      isChar = newPassword.charAt(i);
      if ( ((isChar) >= '0') && ((isChar) <= '9'))
        foundDigit = true;
    }   
    // now look for a letter...
    for (int i=0; i < newPassword.length(); i++) {
      isChar = newPassword.charAt(i);
      if ((((isChar) >= 'A') && ((isChar) <= 'Z'))  || ( ((isChar) >= 'a') && ((isChar) <= 'z'))) {
        foundLetter = true;
      }
    }     
    
    if (foundDigit && foundLetter) {
      return true;
    }
    else {
      return false;
    }
  }

  private boolean checkForSameAsUserId() {
    return ((_newPasswordText.toLowerCase()).equals(_userId.toLowerCase()));
  }

  private boolean checkForOldPassword() {
    String oldPassword = null;

    try {
      ArdaisuserAccessBean ardaisUserEB =
        new ArdaisuserAccessBean(new ArdaisuserKey(_userId, new ArdaisaccountKey(_accountId)));
      oldPassword = ardaisUserEB.getPassword();
    }
    catch (ObjectNotFoundException onfe) {
      return false;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    if (!ApiFunctions.isEmpty(oldPassword)) {
      return oldPassword.equals(ApiFunctions.encryptPassword(_newPasswordText));
    }
    else {
      return false;
    }
  }

  /**
   * @return
   */
  public String getAccountId() {
    return _accountId;
  }

  /**
   * @return
   */
  public String getConfirmPasswordText() {
    return _confirmPasswordText;
  }

  /**
   * @return
   */
  public String getNewPasswordText() {
    return _newPasswordText;
  }

  /**
   * @return
   */
  public String getUserId() {
    return _userId;
  }

  /**
   * @param string
   */
  public void setAccountId(String string) {
    _accountId = string;
  }

  /**
   * @param string
   */
  public void setConfirmPasswordText(String string) {
    _confirmPasswordText = string;
  }

  /**
   * @param string
   */
  public void setNewPasswordText(String string) {
    _newPasswordText = string;
  }

  /**
   * @param string
   */
  public void setUserId(String string) {
    _userId = string;
  }
}
