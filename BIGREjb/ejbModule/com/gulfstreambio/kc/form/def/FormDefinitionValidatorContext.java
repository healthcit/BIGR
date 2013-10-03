package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.validation.ValidatorContext;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * A <code>ValidatorContext</code> for use with the validators that perform KC-specific 
 * validation on form definitions.
 */
public class FormDefinitionValidatorContext implements ValidatorContext {

  private FormDefinition _formDef;
  private FormDefinition _persistedFormDef;
  private DataElementTaxonomy _det;
  private String _formDefinitionId;

  public FormDefinitionValidatorContext() {
    super();
  }

  public FormDefinition getFormDefinition() {
    return _formDef;
  }

  public FormDefinition getPersistedFormDefinition() {
    return _persistedFormDef;
  }

  public void setFormDefinition(FormDefinition definition) {
    _formDef = definition;
  }

  public void setPersistedFormDefinition(FormDefinition definition) {
    _persistedFormDef = definition;
  }

  public DataElementTaxonomy getDet() {
    return _det;
  }

  public void setDet(DataElementTaxonomy taxonomy) {
    _det = taxonomy;
  }

  /**
   * @return Returns the formDefinitionId.
   */
  public String getFormDefinitionId() {
    return _formDefinitionId;
  }
  
  /**
   * @param formDefinitionId The formDefinitionId to set.
   */
  public void setFormDefinitionId(String formDefinitionId) {
    _formDefinitionId = formDefinitionId;
  }
}
