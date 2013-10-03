package com.ardais.bigr.validation;

import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Validates that two values are equal as determined by their <code>equals</code> method.  In
 * addition, two values that are both <code>null</code> are considered equal.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The values are not equal.
 *   <ol>
 *   <li>The first value.  This must be supplied by the caller
 *       by calling {@link #setValue1(java.lang.Object) setValue1}. 
 *   </li>
 *   <li>The second value.  This must be supplied by the caller
 *       by calling {@link #setValue2(java.lang.Object) setValue2}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorValuesEqual extends AbstractValidator {

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.valuesNotEqual";

  private Object _value1;
  private Object _value2;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorValuesEqual v1 = (ValidatorValuesEqual) v;
      Object value1 = v1.getValue1();
      String value1String = (value1 == null) ? "null" : value1.toString();
      Object value2 = v1.getValue2();
      String value2String = (value2 == null) ? "null" : value2.toString();
      v1.addErrorMessage(ValidatorValuesEqual.ERROR_KEY1, value1String, value2String);
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorValuesEqual</code> validator.
   */
  public ValidatorValuesEqual() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    Object value1 = getValue1();
    Object value2 = getValue2();
    
    if (value1 == null) {
      if (value2 != null) {
        notifyValidatorErrorListener(ERROR_KEY1);
      }
    }
    else if (!value1.equals(value2)) {
      notifyValidatorErrorListener(ERROR_KEY1);
    }
    
    return getActionErrors();
  }

  public Object getValue1() {
    return _value1;
  }

  public void setValue1(Object value) {
    _value1 = value;
  }

  public Object getValue2() {
    return _value2;
  }

  public void setValue2(Object value) {
    _value2 = value;
  }

}
