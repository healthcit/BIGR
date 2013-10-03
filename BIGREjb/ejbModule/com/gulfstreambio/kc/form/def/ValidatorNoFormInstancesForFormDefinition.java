package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.FormInstanceQueryCriteria;
import com.gulfstreambio.kc.form.FormInstanceService;
import com.gulfstreambio.kc.form.FormInstanceServiceResponse;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * Validates that the form definition with the specified id has no form instances created from it
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - One or more form instances exist.
 *   <ol>
 *   <li>The name of the form definition, as obtained by calling the
 *       {@link com.gulfstreambio.kc.form.def.data.DataFormDefinition#getName() getName} method on the
 *       form definition returned by calling {@link #getFormDefinition() getFormDefinition}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorNoFormInstancesForFormDefinition extends AbstractValidator {

  /**
   * The key of the error that is returned when one or more form instances exist. 
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.instancesexists";

  private FormDefinition _formDef;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorNoFormInstancesForFormDefinition v1 = (ValidatorNoFormInstancesForFormDefinition) v;
      FormDefinition formDef = v1.getFormDefinition();
      String name = (formDef == null) ? "UNKNOWN" : formDef.getName();
      v1.addErrorMessage(errorKey, name);
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorNoFormInstancesForFormDefinition</code> validator.
   */
  public ValidatorNoFormInstancesForFormDefinition() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    String id = getFormDefinition().getFormDefinitionId();

    FormInstanceQueryCriteria criteria = new FormInstanceQueryCriteria();
    criteria.addFormDefinitionId(id);
    FormInstanceServiceResponse response = 
      FormInstanceService.SINGLETON.findFormInstances(criteria);
    BtxActionErrors errors = response.getErrors();
    if (!errors.empty()) {
      getActionErrors().addErrors(errors);
    }
    else if (response.getFormInstances().length > 0) { 
      notifyValidatorErrorListener(ERROR_KEY1);      
    }
    return getActionErrors();
  }

  public void setFormDefinition(FormDefinition definition) {
    _formDef = definition;
  }

  public FormDefinition getFormDefinition() {
    return _formDef;
  }

}
