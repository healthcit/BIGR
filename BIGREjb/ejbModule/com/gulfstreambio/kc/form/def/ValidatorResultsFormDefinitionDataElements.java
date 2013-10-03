package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;

/**
 * Validates all of the data elements that comprise a results form definition. 
 */
public class ValidatorResultsFormDefinitionDataElements extends AbstractValidator {  
  
  private DataElementTaxonomy _det; 
  private ResultsFormDefinition _formDef;

  /**
   * Creates a new <code>ValidatorDataFormDefinitionDataElements</code> validator.
   */
  public ValidatorResultsFormDefinitionDataElements() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();

    ResultsFormDefinition formDef = getFormDefinition();
    ResultsFormDefinitionDataElement[] dataElements = formDef.getResultsDataElements();
    for (int i = 0; i < dataElements.length; i++) {
      ResultsFormDefinitionDataElement def = dataElements[i];
      ValidatorDetContainsDataElement v = new ValidatorDetContainsDataElement();
      v.setValue(def.getCui());
      v.setDet(getDet());
      errors.addErrors(v.validate());
    }
    return errors;
  }

  public ResultsFormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(ResultsFormDefinition definition) {
    _formDef = definition;
  }

  public DataElementTaxonomy getDet() {
    return _det;
  }

  public void setDet(DataElementTaxonomy det) {
    _det = det;
  }
}
