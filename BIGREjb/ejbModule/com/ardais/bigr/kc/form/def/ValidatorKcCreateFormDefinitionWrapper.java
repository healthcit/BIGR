package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;

/**
 * A <code>Validator</code> that wraps the KC-specific create form definition validation in
 * the KC form definition service.
 */
public class ValidatorKcCreateFormDefinitionWrapper extends AbstractValidator {

  private FormDefinition _form;

  /**
   *  Creates a new <code>ValidatorKcCreateFormDefinitionWrapper</code>.
   */
  public ValidatorKcCreateFormDefinitionWrapper() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    FormDefinition form = getFormDefinition();
    String formType = form.getType();
    if (FormDefinitionTypes.DATA.equals(formType)) {
      return FormDefinitionService.SINGLETON.validateCreateDataFormDefinition((DataFormDefinition) form);
    }
    else if (FormDefinitionTypes.QUERY.equals(formType)) {
      return FormDefinitionService.SINGLETON.validateCreateQueryFormDefinition((QueryFormDefinition) form);
    }
    else if (FormDefinitionTypes.RESULTS.equals(formType)) {
      return FormDefinitionService.SINGLETON.validateCreateResultsFormDefinition((ResultsFormDefinition) form);
    }
    else {
      throw new ApiException("In ValidatorKcCreateFormDefinitionWrapper, attempt to validate unknown form type: " + formType);
    }
    
  }

  public FormDefinition getFormDefinition() {
    return _form;
  }

  public void setFormDefinition(FormDefinition form) {
    _form = form;
  }

}
