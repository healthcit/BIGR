package com.ardais.bigr.validation;

import org.apache.commons.beanutils.ConversionException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrConvertUtilsBean;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.util.VariablePrecisionDate;
import com.ardais.bigr.validation.ValidatorVpd.DefaultErrorListener;

/**
 * Validates that an VPD has the proper syntax.
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
public class ValidatorVpd extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "errors.partialDate";
  
  private String _VPD;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorVpd v1 = (ValidatorVpd) v;
      v1.addErrorMessage(ValidatorVpd.ERROR_KEY1, v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorVpd</code> validator.
   */
  public ValidatorVpd() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
   public BtxActionErrors validate() {
     String vpd = getVpd();  
     if (!ApiFunctions.isEmpty(vpd)) {
       try {
         VariablePrecisionDate date = new VariablePrecisionDate(vpd);
         if (!checkYear(vpd)) {
           notifyValidatorErrorListener(ERROR_KEY1);           
         }
       }
       catch (ConversionException e) {
         notifyValidatorErrorListener(ERROR_KEY1);
       }
     }  
     return getActionErrors();
   }

  public String getVpd() {
    return _VPD;
  }

  public void setVpd(String i) {
    _VPD = i;
  }

  private boolean checkYear(String s) {
    String[] dateValues = ApiFunctions.splitAndTrim(s.toString(), "/");
    if ((dateValues.length < 1) || (dateValues.length > 3)) {
      return false;
    }
    else {
      String dateValue = dateValues[dateValues.length - 1];
      return (dateValue.length() == 4);
    }
  }
}
