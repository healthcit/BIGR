package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionTypes;

/**
 * A <code>Validator</code> that wraps the KC-specific delete form definition validation in
 * the KC form definition service.
 */
public class ValidatorKcDeleteFormDefinitionWrapper extends AbstractValidator {

  private BigrFormDefinition _form;

  /**
   *  Creates a new <code>ValidatorKcDeleteFormDefinitionWrapper</code>.
   */
  public ValidatorKcDeleteFormDefinitionWrapper() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BigrFormDefinition form = getFormDefinition();
    String formType = form.getFormType();
    String id = form.getFormDefinitionId();
    if (FormDefinitionTypes.DATA.equals(formType)) {
      return FormDefinitionService.SINGLETON.validateDeleteDataFormDefinition(id);
    }
    else if (FormDefinitionTypes.QUERY.equals(formType)) {
      return FormDefinitionService.SINGLETON.validateDeleteQueryFormDefinition(id);
    }
    else if (FormDefinitionTypes.RESULTS.equals(formType)) {
      return FormDefinitionService.SINGLETON.validateDeleteResultsFormDefinition(id);
    }
    else {
      throw new ApiException("In ValidatorKcDeleteFormDefinitionWrapper, attempt to validate unknown form type: " + formType);
    }
  }

  public BigrFormDefinition getFormDefinition() {
    return _form;
  }

  public void setFormDefinition(BigrFormDefinition form) {
    _form = form;
  }

}
