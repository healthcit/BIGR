package com.ardais.bigr.kc.form.def;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionCategory;
import com.gulfstreambio.kc.form.def.ValidatorFormDefinitionCategories;

/**
 * @author sthomashow
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ValidatorRegistrationFormNoCategories extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if there are duplicated display
   * names.
   */
  public final static String ERROR_KEY1 
    = "orm.error.formcreator.nocategoriesregforms";

  private BigrFormDefinition _formDef; 


  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorRegistrationFormNoCategories v1 = (ValidatorRegistrationFormNoCategories) v;
      v1.addErrorMessage(ValidatorRegistrationFormNoCategories.ERROR_KEY1);
      return true;
    }
  }

  public ValidatorRegistrationFormNoCategories() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();

    FormDefinition formDef = getFormDefinition().getKcFormDefinition();
    FormDefinitionCategory[] categories = formDef.getCategories();
    if (categories.length > 0) {
      notifyValidatorErrorListener(ERROR_KEY1);
    }
    return errors;
  }

  public BigrFormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(BigrFormDefinition definition) {
    _formDef = definition;
  }


}