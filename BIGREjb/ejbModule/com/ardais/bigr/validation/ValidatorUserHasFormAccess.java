package com.ardais.bigr.validation;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Validates that a user has access to a form.
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
public class ValidatorUserHasFormAccess extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.noPrivilege";

  private SecurityInfo _securityInfo;
  private BigrFormDefinition _formDefinition;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorUserHasFormAccess v1 = (ValidatorUserHasFormAccess) v;
      v1.addErrorMessage(ValidatorUserHasFormAccess.ERROR_KEY1, "to delete " + (v1.getFormDefinition().getName()));
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorUserHasAccountAccess</code> validator.
   */
  public ValidatorUserHasFormAccess() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    String account = getSecurityInfo().getAccount();
    String userName = getSecurityInfo().getUsername();
    if (!account.equalsIgnoreCase(getFormDefinition().getAccount()) ||
        !userName.equalsIgnoreCase(getFormDefinition().getUserName()))
        notifyValidatorErrorListener(ERROR_KEY1);
    return getActionErrors();
  }

  /**
   * @return Returns the formDefinition.
   */
  public BigrFormDefinition getFormDefinition() {
    return _formDefinition;
  }
  
  /**
   * @param formDefinition The formDefinition to set.
   */
  public void setFormDefinition(BigrFormDefinition formDefinition) {
    _formDefinition = formDefinition;
  }
  
  public SecurityInfo getSecurityInfo() {
    return _securityInfo;
  }
  
  public void setSecurityInfo(SecurityInfo securityInfo) {
    _securityInfo = securityInfo;
  }
}
