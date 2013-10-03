package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;

/**
 * A <code>Validator</code> that wraps the KC-specific update form definition validation in
 * the KC form definition service.
 */
public class ValidatorKcUpdateFormDefinitionWrapper extends AbstractValidator {

  private FormDefinition _form;

  /**
   *  Creates a new <code>ValidatorKcDataFormDefinitionWrapper</code>.
   */
  public ValidatorKcUpdateFormDefinitionWrapper() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    FormDefinition form = getFormDefinition();
    String formType = form.getType();
    if (FormDefinitionTypes.DATA.equals(formType)) {
      return FormDefinitionService.SINGLETON.validateUpdateDataFormDefinition((DataFormDefinition) form);
    }
    else if (FormDefinitionTypes.QUERY.equals(formType)) {
      return FormDefinitionService.SINGLETON.validateUpdateQueryFormDefinition((QueryFormDefinition) form);
    }
    else if (FormDefinitionTypes.RESULTS.equals(formType)) {
      //return FormDefinitionService.SINGLETON.validateUpdateResultsFormDefinition((ResultsFormDefinition) form);
      return null;
    }
    else {
      throw new ApiException("In ValidatorKcUpdateFormDefinitionWrapper, attempt to validate unknown form type: " + formType);
    }
  }

  public FormDefinition getFormDefinition() {
    return _form;
  }

  public void setFormDefinition(FormDefinition form) {
    _form = form;
  }
}
