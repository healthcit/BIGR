package com.ardais.bigr.validation;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Validate that an "other" value has or has not been specified, as appropriate, depending upon the
 * value of the CUI for the associated controlled-vocabulary value.
 * <p>
 * This validator may return one of two errors as follows, with insertion strings listed below 
 * the errors:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The "other" CUI was specified, but no "other" text was entered.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY2} - "other" text was entered, but the "other" CUI was not specified.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 * 
 */
public class ValidatorSeeNote extends AbstractValidator {

  /**
   * The key of the error that is returned if the "other" CUI was specified, but no "other" text 
   * was entered.
   */
  public final static String ERROR_KEY1 = "error.noNoteText";
  
  private String _cui;
  private String _seeNoteCui;
  private String _valueNote;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorSeeNote v1 = (ValidatorSeeNote) v;
      v1.addErrorMessage(errorKey, v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorSeeNote</code> validator.
   */
  public ValidatorSeeNote() {
    super();
    setPropertyDisplayName("Note");
    addErrorKey(ERROR_KEY1);
    ValidatorErrorListener listener = new DefaultErrorListener();
    addValidatorErrorListener(listener, ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    String cui = getCui();
    String other = ApiFunctions.safeTrim(getValueNote());
    
    if (cui != null) { 
      if (cui.equals(getSeeNoteCui())) {
        if (ApiFunctions.isEmpty(other)) {
          notifyValidatorErrorListener(ERROR_KEY1);
        }
      }
    }
    return getActionErrors();
  }
  
  public String getSeeNoteCui() {
    return _seeNoteCui;
  }

  public String getValueNote() {
    return _valueNote;
  }

  public String getCui() {
    return _cui;
  }

  public void setSeeNoteCui(String cui) {
    if (ApiFunctions.isEmpty(cui)) {
      throw new ApiException("ValidatorSeeNote: No seeNoteCui specified.");
    }
    _seeNoteCui = cui;
  }

  public void setValueNote(String value) {
    _valueNote = value;
  }

  public void setCui(String cui) {
    _cui = cui;
  }

}
