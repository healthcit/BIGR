package com.ardais.bigr.kc.form.def;

import java.util.HashSet;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionQueryCriteria;
import com.gulfstreambio.kc.form.def.FormDefinitionService;
import com.gulfstreambio.kc.form.def.FormDefinitionServiceResponse;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

/**
 * Validates that a form definition name of a specified type is unique within its account.
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - A form definition with the same name exists.
 *   <ol>
 *   <li>The name of the form definition, as obtained by calling the
 *       {@link com.gulfstreambio.kc.form.def.data.DataFormDefinition#getName() getName} method on 
 *       the form definition returned by calling {@link #getFormDefinition() getFormDefinition}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorFormDefinitionNameUnique extends AbstractValidator {

  /**
   * The key of the error that is returned when the form name is already used. 
   */
  public final static String ERROR_KEY1 = "orm.error.formcreator.formnameused";

  private BigrFormDefinition _form;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorFormDefinitionNameUnique v1 = (ValidatorFormDefinitionNameUnique) v;
      v1.addErrorMessage(errorKey, v1.getFormDefinition().getName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorFormDefinitionNameUnique</code> validator.
   */
  public ValidatorFormDefinitionNameUnique() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    // Get the form definition name and the account.
    BigrFormDefinition form = getFormDefinition();
    String name = form.getName();
    String type = form.getFormType();
    String account = form.getAccount();

    // Find form definitions with the same account and name.  If any exist except for the one
    // being validated, then notify listeners of the error.
    if (!ApiFunctions.isEmpty(name) 
        && !ApiFunctions.isEmpty(account) 
        && !ApiFunctions.isEmpty(type)) {
      FormDefinitionQueryCriteria criteria = new FormDefinitionQueryCriteria();
      Tag tag = new Tag(TagTypes.ACCOUNT, account);
      criteria.addTag(tag);
      criteria.addFormName(name);
      criteria.addFormType(type);
      FormDefinitionServiceResponse response =
        FormDefinitionService.SINGLETON.findFormDefinitions(criteria);
      BtxActionErrors errors = response.getErrors();
      if (!errors.empty()) {
        getActionErrors().addErrors(errors);
      }
      else { 
        FormDefinition[] kcForms = response.getFormDefinitions();
        int count = kcForms.length;
        if (count > 0) {
          boolean foundFormWithSameName = false;
          for (int i = 0; i < count; i++) {
            FormDefinition kcForm = kcForms[i];
            if (!kcForm.getFormDefinitionId().equals(form.getFormDefinitionId())) {
              foundFormWithSameName = true;
            }
          }
          if (foundFormWithSameName) {
            notifyValidatorErrorListener(ERROR_KEY1);
          }
        }
      }
    }    
    return getActionErrors();
  }

  public BigrFormDefinition getFormDefinition() {
    return _form;
  }

  public void setFormDefinition(BigrFormDefinition definition) {
    _form = definition;
  }

}
