package com.ardais.bigr.validation;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Validates that a security info matches to a given account or a Sys Owner.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The specified user is not in the specified account or system owner.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorUserHasAccountAccess extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.noPrivilege";

  private SecurityInfo _securityInfo;
  private String _account;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorUserHasAccountAccess v1 = (ValidatorUserHasAccountAccess) v;
      v1.addErrorMessage(ValidatorUserHasAccountAccess.ERROR_KEY1, IltdsUtils.getAccountName(v1.getAccount()));
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorUserHasAccountAccess</code> validator.
   */
  public ValidatorUserHasAccountAccess() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    //- Valid only for users in the system owner account or if account matches securityInfo
    String account = getSecurityInfo().getAccount();
    if (getAccount() != null) 
      if (! (getSecurityInfo().isInRoleSystemOwner() || account.equals(getAccount()))) 
        notifyValidatorErrorListener(ERROR_KEY1);
    return getActionErrors();
  }
  
  public String getAccount() {
    return _account;
  }

  public void setAccount(String account) {
    _account = account;
  }

  public SecurityInfo getSecurityInfo() {
    return _securityInfo;
  }
  
  public void setSecurityInfo(SecurityInfo securityInfo) {
    _securityInfo = securityInfo;
  }
}
