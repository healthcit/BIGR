package com.ardais.bigr.kc.form.def;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;

/**
 * Validates that a form definition exists. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The specified form definition does not exist.
 *   <ol>
 *   <li>The form definition id, as obtained by calling 
 *       {@link #getValue() getValue}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorFormDefinitionExists extends AbstractValidator {

  /**
   * The key of the error that is returned when the form definition does not exist. 
   */
  public final static String ERROR_KEY1 = "orm.error.formcreator.formdefinitionnotfound";
  
  private String _value;
  private BigrFormDefinition _form;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorFormDefinitionExists v1 = (ValidatorFormDefinitionExists) v;
      v1.addErrorMessage(errorKey, v1.getValue());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorFormDefinitionExists</code> validator.
   */
  public ValidatorFormDefinitionExists() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    String id = getValue();

    // Find the form definitions with the specified id.  If it does not exist, then notify 
    // listeners of the error.
    if (!ApiFunctions.isEmpty(id)) {
      FormDefinitionServiceResponse response =
        FormDefinitionService.SINGLETON.findFormDefinitionById(id);
      BtxActionErrors errors = response.getErrors();
      if (!errors.empty()) {
        getActionErrors().addErrors(errors);
      }
      else { 
        FormDefinition form = response.getFormDefinition();
        if (form == null) {
          notifyValidatorErrorListener(ERROR_KEY1);
        }
        else {
          setFormDefinition(new BigrFormDefinition(form));
        }
      }
    }    
    return getActionErrors();
  }

  public String getValue() {
    return _value;
  }

  public void setValue(String value) {
    _value = value;
  }

  public BigrFormDefinition getFormDefinition() {
    return _form;
  }

  public void setFormDefinition(BigrFormDefinition form) {
    _form = form;
  }

}
