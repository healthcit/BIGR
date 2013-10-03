package com.gulfstreambio.kc.form;

import java.util.Collection;
import java.util.Iterator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.util.SystemNameUtils;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetAdeElement;

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
public class ValidatorAdeElementForAde extends AbstractValidator {

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "error.valueNotInList";

  private String _value;
  private DetAdeElement[] _valueSet;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorAdeElementForAde v1 = (ValidatorAdeElementForAde) v;
      StringBuffer sb = new StringBuffer();
      DetAdeElement[] valueSet = v1.getValueSet();
      for (int j = 0; j < valueSet.length; j++) {
        if (j > 0) {
          sb.append(", ");          
        }
        sb.append(SystemNameUtils.convertToCanonicalForm(valueSet[j].getSystemName()));
      }
            
      v1.addErrorMessage(
        ValidatorAdeElementForAde.ERROR_KEY1,
        v1.getPropertyDisplayName(),
        v1.getValue(),
        sb.toString());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorAdeElementForAde</code> validator.
   */
  public ValidatorAdeElementForAde() {
    super();
    setPropertyDisplayName("Value collection"); //default
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);    
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    DetAdeElement[] valueSet = getValueSet();
    if (valueSet == null) {
      throw new ApiException("ValidatorAdeElementForAde: value set not specified.");
    }
    String value = getValue();
    value = SystemNameUtils.convertToCanonicalForm(value);
    if (!ApiFunctions.isEmpty(value)) {
      boolean foundLegal = false;
      for (int j = 0; j < valueSet.length; j++) {
        DetAdeElement adeElement = valueSet[j];
        if (value.equals(adeElement.getSystemName())) {
          foundLegal = true;
          break;
        }
      }
      if (!foundLegal) {
        notifyValidatorErrorListener(ERROR_KEY1);
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

  public DetAdeElement[] getValueSet() {
    return _valueSet;
  }

  public void setValueSet(DetAdeElement[] valueSet) {
    _valueSet = valueSet;
  }

}
