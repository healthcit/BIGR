package com.ardais.bigr.kc.form.def;

import java.util.Collection;
import java.util.Iterator;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.ValidatorValueInCollection;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionHostElement;

/**
 * Validates all of the data elements that comprise a results form definition. 
 */
public class ValidatorResultsFormDefinitionHostElements extends AbstractValidator {  
  
  private Collection _valueSet; 
  private ResultsFormDefinition _formDefinition;

  /**
   * Creates a new <code>ValidatorDataFormDefinitionDataElements</code> validator.
   */
  public ValidatorResultsFormDefinitionHostElements() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();

    ResultsFormDefinition formDef = getFormDefinition();
    ResultsFormDefinitionHostElement[] defs = formDef.getResultsHostElements();
    for (int i = 0; i < defs.length; i++) {
      ResultsFormDefinitionHostElement def = defs[i];
      ValidatorValueInCollection v = new ValidatorValueInCollection();
      v.setValue(def.getHostId());
      v.setValueSet(getValueSet());
      errors.addErrors(v.validate());
    }
    return errors;
  }

  public ResultsFormDefinition getFormDefinition() {
    return _formDefinition;
  }

  public void setFormDefinition(ResultsFormDefinition formDefinition) {
    _formDefinition = formDefinition;
  }
  
  private Collection getValueSet() {
    if (_valueSet == null) {
      _valueSet = ColumnConstants.getResultsViewColumns();
    }
    return _valueSet;
  }
  
  /**
   * @param valueSet The valueSet to set.
   */
  public void setValueSet(Collection valueSet) {
    _valueSet = valueSet;
  }
}
