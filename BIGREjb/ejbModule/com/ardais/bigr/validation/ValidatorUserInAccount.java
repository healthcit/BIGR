package com.ardais.bigr.validation;

import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValue;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.ArdaisStaff;

/**
 * Validates that a user belongs to a given account.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The specified user is not in the specified account.
 *   <ol>
 *   <li>The user's full name, based on the specified user id. 
 *   </li>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorUserInAccount extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.badvalue";

  private String _userId;
  private String _account;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorUserInAccount v1 = (ValidatorUserInAccount) v;
      v1.addErrorMessage(ValidatorRequired.ERROR_KEY1, v1.getUserFullName(), v1.getPropertyDisplayName(), "a member of your account");
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorUserInAccount</code> validator.
   */
  public ValidatorUserInAccount() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    String userId = getUserId();
    if (!ApiFunctions.isEmpty(userId)) {
      boolean found = IltdsUtils.isUserInAccount(userId, getAccount());
      if (!found) {
        notifyValidatorErrorListener(ERROR_KEY1);
      }
    }
    return getActionErrors();
  }
  
  /**
   * @return  the specified user id
   */
  public String getUserId() {
    return _userId;
  }
  
  public String getUserFullName() {
    String userFullName = null;
    if (!ApiFunctions.isEmpty(getUserId())) {
      try {
        ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(getUserId()));
        userFullName = (staff.getArdais_staff_fname() + " " + staff.getArdais_staff_lname());
      }
      catch (Exception e) {
        userFullName = "";
      }
    }
    return userFullName;
  }

  public String getAccount() {
    return _account;
  }

  public void setUserId(String string) {
    _userId = string;
  }

  public void setAccount(String account) {
    if (ApiFunctions.isEmpty(account)) {
      throw new ApiException("ValidatorUserInAccount: No account specified.");
    }
    _account = account;
  }

}
