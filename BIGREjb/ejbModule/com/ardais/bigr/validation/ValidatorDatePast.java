package com.ardais.bigr.validation;

import java.sql.Date;

import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Validates that a date is in the past.  We include today's date as past, thus if the date is
 * today's date or earlier, the validation will pass and not report an error.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The date is not in the past.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorDatePast extends AbstractValidator {

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.dateMustBeBeforeCurrentDate";
  
  private Date _date;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorDatePast v1 = (ValidatorDatePast) v;
      v1.addErrorMessage(ValidatorDatePast.ERROR_KEY1, v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDatePast</code> validator.
   */
  public ValidatorDatePast() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    Date date = getDate();  
    if (date != null) {
      if (date.after(new java.util.Date())) {
        notifyValidatorErrorListener(ERROR_KEY1);
      }
    }
    return getActionErrors();
  }

  public Date getDate() {
    return _date;
  }

  public void setDate(Date date) {
    _date = date;
  }

}
