package com.ardais.bigr.validation;

import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Validates that a required data element has a value. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The value is empty.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorRequired extends AbstractValidator {

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.noValue";
  
  private String _value;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorRequired v1 = (ValidatorRequired) v;
      v1.addErrorMessage(ValidatorRequired.ERROR_KEY1, v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorRequired</code> validator.
   */
  public ValidatorRequired() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    if (ApiFunctions.isEmpty(getValue())) {
      notifyValidatorErrorListener(ERROR_KEY1);
    }
    return getActionErrors();
  }

  /**
   * Returns the value to be validated.
   * 
   * @return  The value.
   */
  public String getValue() {
    return _value;
  }

  /**
   * Sets the value to be validated.
   * 
   * @param value the value
   */
  public void setValue(String value) {
    _value = value;
  }

}
