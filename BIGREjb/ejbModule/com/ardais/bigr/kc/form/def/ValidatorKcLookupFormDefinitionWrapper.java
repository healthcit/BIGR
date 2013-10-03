package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.gulfstreambio.kc.form.def.FormDefinitionService;

/**
 * A <code>Validator</code> that wraps the KC-specific create form definition validation in
 * the KC form definition service.
 */
public class ValidatorKcLookupFormDefinitionWrapper extends AbstractValidator {

  private String _formId;

  /**
   *  Creates a new <code>ValidatorKcLookupFormDefinitionWrapper</code>.
   */
  public ValidatorKcLookupFormDefinitionWrapper() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    return FormDefinitionService.SINGLETON.validateFindFormDefinitionById(getFormDefinitionId());
  }

  public String getFormDefinitionId() {
    return _formId;
  }

  public void setFormDefinitionId(String id) {
    _formId = id;
  }

}
