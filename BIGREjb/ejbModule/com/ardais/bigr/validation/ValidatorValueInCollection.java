package com.ardais.bigr.validation;

import java.util.Collection;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Validates that a value is within a specified collection of values.  This validation is case-sensitive.
 * In addition, if the value is null it is not considered within the collection of values, unless null 
 * is explicitly part of the collection of values.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The value is not present in the set.
 *   <ol>
 *   <li>The name of the property that holds the value.  This must be supplied by the caller
 *       by calling {@link #setPropertyDisplayName(java.lang.String) setPropertyDisplayName}. 
 *   </li>
 *   <li>The value.  This is the value set in {@link #setValue(java.lang.String) setValue}</li>
 *   <li>The collection of values.  These are the values Collection in 
 *       {@link #setValueCollection(java.util.Collection) setValueCollection}</li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorValueInCollection extends AbstractValidator {

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.valueNotInList";

  private String _value;
  private Collection _valueCollection;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorValueInCollection v1 = (ValidatorValueInCollection) v;
      String csvalues =
        ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getValueCollection()));
      v1.addErrorMessage(
        ValidatorValueInCollection.ERROR_KEY1,
        v1.getPropertyDisplayName(),
        v1.getValue(),
        csvalues);
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorValueInCollection</code> validator.
   */
  public ValidatorValueInCollection() {
    super();
    setPropertyDisplayName("Value collection"); //default
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);    
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    Collection valueSet = getValueCollection();
    if (valueSet == null) {
      throw new ApiException("ValidatorValueInCollection: value set not specified.");
    }
    String value = getValue();
    if (!ApiFunctions.isEmpty(value) && !valueSet.contains(value)) {
      notifyValidatorErrorListener(ERROR_KEY1);
    }    
    return getActionErrors();
  }

  public String getValue() {
    return _value;
  }

  public void setValue(String value) {
    _value = value;
  }

  public Collection getValueCollection() {
    return _valueCollection;
  }

  public void setValueSet(Collection valueSet) {
    _valueCollection = valueSet;
  }

}
