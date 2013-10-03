package com.ardais.bigr.validation;

import java.sql.Date;

import org.apache.commons.beanutils.ConversionException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrConvertUtilsBean;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Validates that a date has the proper syntax of a full date, with month, day and year.
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
public class ValidatorDate extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.invalidDate";
  
  private String _date;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorDate v1 = (ValidatorDate) v;
      v1.addErrorMessage(ValidatorDate.ERROR_KEY1, v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDate</code> validator.
   */
  public ValidatorDate() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    String date = getDate();  
    if (!ApiFunctions.isEmpty(date)) {
      try {
        BigrConvertUtilsBean.SINGLETON.convert(date, Date.class);      
      }
      catch (ConversionException e) {
        notifyValidatorErrorListener(ERROR_KEY1);
      }
    }
    return getActionErrors();
  }

  public String getDate() {
    return _date;
  }

  public void setDate(String date) {
    _date = date;
  }

}
