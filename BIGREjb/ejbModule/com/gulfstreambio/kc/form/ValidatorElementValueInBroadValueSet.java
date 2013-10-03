package com.gulfstreambio.kc.form;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetValueSet;

/**
 * Validates that a data element's value is contained within its broad value set. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY_NOT_IN_BROAD_VALUE_SET} - The value is not in the broad value set.
 *   <ol>
 *   <li>The value, as set by {@link #setValue(String) setValue}.</li>
 *   <li>The name of the data element, as set by 
 *       {@link #setPropertyDisplayName(String) setPropertyDisplayName}.</li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorElementValueInBroadValueSet extends AbstractValidator {

  private String _value;
  private DetValueSet _broadValueSet;

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY_NOT_IN_BROAD_VALUE_SET 
    = "kc.error.forminst.valueNotInBroadValueSet";

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorElementValueInBroadValueSet v1 = (ValidatorElementValueInBroadValueSet) v;
      v1.addErrorMessage(ValidatorElementValueInBroadValueSet.ERROR_KEY_NOT_IN_BROAD_VALUE_SET, 
                          v1.getValue(), v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * 
   */
  public ValidatorElementValueInBroadValueSet() {
    super();
    addErrorKey(ERROR_KEY_NOT_IN_BROAD_VALUE_SET);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY_NOT_IN_BROAD_VALUE_SET);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    String value = getValue();
    if (!ApiFunctions.isEmpty(value)) {
      if (!getBroadValueSet().containsValue(value)) {
        notifyValidatorErrorListener(ERROR_KEY_NOT_IN_BROAD_VALUE_SET);       
      }      
    }
    return getActionErrors();
  }

  public String getValue() {
    return _value;
  }

  public void setValue(String value) {
    _value = value;
  }

  public DetValueSet getBroadValueSet() {
    return _broadValueSet;
  }

  public void setBroadValueSet(DetValueSet broadValueSet) {
    _broadValueSet = broadValueSet;
  }
}
