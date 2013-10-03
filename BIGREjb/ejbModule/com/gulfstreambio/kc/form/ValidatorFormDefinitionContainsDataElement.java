package com.gulfstreambio.kc.form;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * Validates that a data element is present within a specified form definition. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The data element is not present in the form definition.
 *   <ol>
 *   <li>The CUI of the data element, as set by {@link #setValue(java.lang.String) setValue}.
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorFormDefinitionContainsDataElement extends AbstractValidator {

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.elementNotInFormDefinition";

  private String _value;
  private DataFormDefinition _formDefinition; 

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorFormDefinitionContainsDataElement v1 = (ValidatorFormDefinitionContainsDataElement) v;
      v1.addErrorMessage(ValidatorFormDefinitionContainsDataElement.ERROR_KEY1, v1.getValue());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorFormDefinitionContainsDataElement</code> validator.
   */
  public ValidatorFormDefinitionContainsDataElement() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    DataFormDefinition formDefinition = getFormDefinition();
    if (formDefinition == null) {
      throw new ApiException("ValidatorFormDefinitionContainsDataElement: form definition was not specified");
    }
    
    if (formDefinition.getDataDataElement(getValue()) == null) {
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

  public DataFormDefinition getFormDefinition() {
    return _formDefinition;
  }

  public void setFormDefinition(DataFormDefinition formDefinition) {
    _formDefinition = formDefinition;
  }

}
