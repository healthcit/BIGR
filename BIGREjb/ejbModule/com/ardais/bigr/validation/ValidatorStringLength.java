package com.ardais.bigr.validation;

import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Validates that the length of a string is less than or equal to its specified maximum length. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY_TOO_LONG} - The length of the string is greater than its maximum length.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorStringLength extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator when the string is too long. 
   */
  public final static String ERROR_KEY_TOO_LONG = "error.valueTooLong";
  
  private String _value;
  private int _maxLength;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorStringLength v1 = (ValidatorStringLength) v;
      v1.addErrorMessage(ValidatorStringLength.ERROR_KEY_TOO_LONG, v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorRequired</code> validator.
   */
  public ValidatorStringLength() {
    super();
    addErrorKey(ERROR_KEY_TOO_LONG);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY_TOO_LONG);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    String value = getValue();
    if (!ApiFunctions.isEmpty(value) && (value.length() > getMaxLength())) {
      notifyValidatorErrorListener(ERROR_KEY_TOO_LONG);
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

  /**
   * Returns the maximim length of the value.
   * 
   * @return  The maximum length.
   */
  public int getMaxLength() {
    return _maxLength;
  }

  /**
   * Sets the maximum length of the value.
   * 
   * @param maxLength the maximum length
   */
  public void setMaxLength(int maxLength) {
    _maxLength = maxLength;
  }
}
