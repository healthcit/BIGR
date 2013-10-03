package com.ardais.bigr.validation;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.concept.BigrConceptList;
import com.ardais.bigr.configuration.SystemConfiguration;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Validates that a concept is a member of a concept list.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The specified CUI is not in the specified concept list.
 *   <ol>
 *   <li>The name of the property containing the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorConceptInConceptList extends AbstractValidator {
  
  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.valueNotInListNoSuggestions";

  private String _cui;
  private String _conceptListName;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorConceptInConceptList v1 = (ValidatorConceptInConceptList) v;
      v1.addErrorMessage(ValidatorConceptInConceptList.ERROR_KEY1, v1.getPropertyDisplayName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorConceptInConceptList</code> validator.
   */
  public ValidatorConceptInConceptList() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.AbstractValidator#validate()
   */
  public BtxActionErrors validate() {
    String cui = getCui();
    if (cui != null) {
      BigrConceptList bcl = SystemConfiguration.getConceptList(getConceptListName());
      if (!bcl.containsConcept(cui)) {
        notifyValidatorErrorListener(ERROR_KEY1);
      }
    }
    return getActionErrors();
  }

  public String getConceptListName() {
    return _conceptListName;
  }

  public String getCui() {
    return _cui;
  }

  public void setConceptListName(String listName) {
    if (ApiFunctions.isEmpty(listName)) {
      throw new ApiException("ValidatorConceptInConceptList: No concept list name specified.");
    }
    _conceptListName = listName;
  }

  public void setCui(String cui) {
    _cui = cui;
  }

}
