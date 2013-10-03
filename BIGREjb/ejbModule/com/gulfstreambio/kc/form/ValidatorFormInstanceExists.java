package com.gulfstreambio.kc.form;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;

/**
 * Validates that a form instance exists. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The specified form instance does not exist.
 *   <ol>
 *   <li>The form instance id, as obtained by calling 
 *       {@link #getValue() getValue}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorFormInstanceExists extends AbstractValidator {

  /**
   * The key of the error that is returned when the form instance does not exist. 
   */
  public final static String ERROR_KEY1 = "kc.error.forminst.forminstancenotfound";
  
  private String _value;
  private FormInstance _formInst;
    
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorFormInstanceExists v1 = (ValidatorFormInstanceExists) v;
      v1.addErrorMessage(errorKey, v1.getValue());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorFormInstanceExists</code> validator.
   */
  public ValidatorFormInstanceExists() {
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
      FormInstanceServiceResponse response =
        FormInstanceService.SINGLETON.findFormInstanceById(id);
      BtxActionErrors errors = response.getErrors();
      if (!errors.empty()) {
        getActionErrors().addErrors(errors);
      }
      else { 
        FormInstance formInst = response.getFormInstance();
        if (formInst == null) {
          notifyValidatorErrorListener(ERROR_KEY1);
        }
        else {
          setFormInstance(formInst);
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
  public FormInstance getFormInstance() {
    return _formInst;
  }

  public void setFormInstance(FormInstance instance) {
    _formInst = instance;
  }

}
