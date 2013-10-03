package com.ardais.bigr.validation;

import org.apache.commons.beanutils.ConversionException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrConvertUtilsBean;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.ValidatorFloat.DefaultErrorListener;

/**
 * Validates that a float has the proper syntax.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The value does not have the proper syntax.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorFloat extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "errors.bigDecimal";
  
  private String _float;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorFloat v1 = (ValidatorFloat) v;
      v1.addErrorMessage(ValidatorFloat.ERROR_KEY1, v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorFloat</code> validator.
   */
  public ValidatorFloat() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
   public BtxActionErrors validate() {
     String floatstr = getFloat();  
     if (!ApiFunctions.isEmpty(floatstr)) {
       try {
         BigrConvertUtilsBean.SINGLETON.convert(floatstr, Float.class);      
       }
       catch (ConversionException e) {
         notifyValidatorErrorListener(ERROR_KEY1);
       }
     }  
     return getActionErrors();
   }

  public String getFloat() {
    return _float;
  }

  public void setFloat(String i) {
    _float = i;
  }
}
