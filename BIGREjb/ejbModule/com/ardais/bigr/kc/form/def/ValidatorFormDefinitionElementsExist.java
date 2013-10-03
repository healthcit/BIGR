package com.ardais.bigr.kc.form.def;

import java.util.Collection;
import java.util.Iterator;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.ardais.bigr.validation.ValidatorValueInCollection;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionElement;
import com.gulfstreambio.kc.form.def.FormDefinitionElements;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement;

/**
 * Validates that a form definition contains one or more elements 
 */
public class ValidatorFormDefinitionElementsExist extends AbstractValidator {  

  /**
   * The key of the error that is returned when the form definition does not exist. 
   */
  public final static String ERROR_KEY1 = "error.noValuesSpecified";
  
  private FormDefinition _formDefinition;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorFormDefinitionElementsExist v1 = (ValidatorFormDefinitionElementsExist) v;
      v1.addErrorMessage(errorKey, "elements");
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorDataFormDefinitionDataElements</code> validator.
   */
  public ValidatorFormDefinitionElementsExist() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    boolean problemFound = false;
    
    FormDefinition formDef = getFormDefinition();
    FormDefinitionElements elements = formDef.getFormElements();
    if (elements == null) {
      problemFound = true;
    }
    else {
      FormDefinitionElement[] elems = elements.getFormElements();
      if (elems == null || elems.length <= 0) {
        problemFound = true;
      }
    }
    if (problemFound) {
      notifyValidatorErrorListener(ERROR_KEY1);      
    }
    return getActionErrors();
  }

  public FormDefinition getFormDefinition() {
    return _formDefinition;
  }

  public void setFormDefinition(FormDefinition formDefinition) {
    _formDefinition = formDefinition;
  }
}
