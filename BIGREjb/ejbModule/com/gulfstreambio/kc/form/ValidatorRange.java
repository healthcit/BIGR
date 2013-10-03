package com.gulfstreambio.kc.form;

import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;

/**
 * Validates that a value is confined to a range.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below:
 * <ul>
 * <li>{@link #ERROR_KEY1} - {0} must be a value smaller than {1}.</li>
 * <li>{@link #ERROR_KEY2} - {0} must be a value smaller than or equal to {1}.</li>
 * <li>{@link #ERROR_KEY3} - {0} must be a value greater than {1}.</li>
 * <li>{@link #ERROR_KEY4} - {0} must be a value greater than or equal to {1}.</li>
 * </ul> 
 */
public class ValidatorRange extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "errors.max";
  public final static String ERROR_KEY2 = "errors.maxInclusive";
  public final static String ERROR_KEY3 = "errors.min";
  public final static String ERROR_KEY4 = "errors.minInclusive";
  
  private Comparable _value;
  private Comparable _minValue;
  private Comparable _maxValue;
  private boolean _minInclusive;
  private boolean _maxInclusive;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorRange v1 = (ValidatorRange) v;
      if (errorKey.equals(ERROR_KEY1)) {
        v1.addErrorMessage(ValidatorRange.ERROR_KEY1, v1.getPropertyDisplayName(), v1.getValue().toString(), v1.getMaxValue().toString());
      } else if (errorKey.equals(ERROR_KEY2)) {
        v1.addErrorMessage(ValidatorRange.ERROR_KEY2, v1.getPropertyDisplayName(), v1.getValue().toString(), v1.getMaxValue().toString());
      } else if (errorKey.equals(ERROR_KEY3)) {
        v1.addErrorMessage(ValidatorRange.ERROR_KEY3, v1.getPropertyDisplayName(), v1.getValue().toString(), v1.getMinValue().toString());
      } else if (errorKey.equals(ERROR_KEY4)) {
        v1.addErrorMessage(ValidatorRange.ERROR_KEY4, v1.getPropertyDisplayName(), v1.getValue().toString(), v1.getMinValue().toString());
      } else {
        return false;
      }
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorRange</code> validator.
   */
  public ValidatorRange() {
    super();
    addErrorKey(ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    addErrorKey(ERROR_KEY3);
    addErrorKey(ERROR_KEY4);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY2);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY3);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY4);
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
   public BtxActionErrors validate() {
     if (getValue() != null) {
       if (getMaxValue() != null) {
         if (isMaxInclusive() ) {
           if (!(getValue().compareTo(getMaxValue()) <= 0) ) {
             notifyValidatorErrorListener(ERROR_KEY2);
           }         
         } else {
           if (!(getValue().compareTo(getMaxValue()) < 0) ) {
             notifyValidatorErrorListener(ERROR_KEY1);
           }         
         }
       }
       if (getMinValue() != null) {
         if (isMinInclusive() ) {
           if (!(getValue().compareTo(getMinValue()) >= 0) ) {
             notifyValidatorErrorListener(ERROR_KEY4);
           }         
         } else {
           if (!(getValue().compareTo(getMinValue()) > 0) ) {
             notifyValidatorErrorListener(ERROR_KEY3);
           }         
         }
       }    
     }
     return getActionErrors();
   }

   public Comparable getMaxValue() {
     return _maxValue;
   }
   public void setMaxValue(Comparable max) {
     _maxValue = max;
   }
   public boolean isMaxInclusive() {
     return _maxInclusive;
   }
   public void setMaxInclusive(boolean maxInclusive) {
     _maxInclusive = maxInclusive;
   }
   public Comparable getMinValue() {
     return _minValue;
   }
   public void setMinValue(Comparable min) {
     _minValue = min;
   }
   public boolean isMinInclusive() {
     return _minInclusive;
   }
   public void setMinInclusive(boolean minInclusive) {
     _minInclusive = minInclusive;
   }
   public void setValue(Comparable value) {
     _value = value;
   }   
   public Comparable getValue() {
    return _value;
  }
}
