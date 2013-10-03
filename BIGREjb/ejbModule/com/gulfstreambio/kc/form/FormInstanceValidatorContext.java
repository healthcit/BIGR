package com.gulfstreambio.kc.form;

import com.ardais.bigr.validation.ValidatorContext;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * A <code>ValidatorContext</code> for use with the validators that perform KC-specific 
 * validation on form instances.
 */
public class FormInstanceValidatorContext implements ValidatorContext {

  private FormInstance _formInst;
  private DataFormDefinition _formDef;

  public FormInstanceValidatorContext() {
    super();
  }

  public FormInstance getFormInstance() {
    return _formInst;
  }

  public DataFormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormInstance(FormInstance instance) {
    _formInst = instance;
  }

  public void setFormDefinition(DataFormDefinition formDef) {
    _formDef = formDef;
  }

}
