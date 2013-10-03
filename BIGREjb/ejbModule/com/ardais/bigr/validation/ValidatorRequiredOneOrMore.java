package com.ardais.bigr.validation;

import java.util.Collection;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Validates that at least one value is present in a collection. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The collection of values is empty.
 *   <ol>
 *   <li>A name that describes the collection of values.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorRequiredOneOrMore extends AbstractValidator {

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.noValuesSpecified";

  private Collection _values; 

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorRequiredOneOrMore v1 = (ValidatorRequiredOneOrMore) v;
      v1.addErrorMessage(ValidatorRequiredOneOrMore.ERROR_KEY1, v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorRequiredOneOrMore</code> validator.
   */
  public ValidatorRequiredOneOrMore() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    if (ApiFunctions.isEmpty(getValues())) {
      notifyValidatorErrorListener(ERROR_KEY1);
    }    
    return getActionErrors();
  }

  public Collection getValues() {
    return _values;
  }

  public void setValues(Collection values) {
    _values = values;
  }
}
