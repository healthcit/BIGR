package com.gulfstreambio.kc.form.def;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory;

/**
 * Validates all of the categories that comprise a form definition.  This validator may directly 
 * return one error as follows, with insertion strings listed below 
 * the error.  This validator also delegates much of its validation to
 * {@link KcCategoryDefinitionValidationService}.
 * <ul>
 * <li>{@link #ERROR_KEY_DUPLICATE_DISPLAY_NAMES} - One or more categories with type "page"
 * have the same display name.
 *   <ol>
 *   <li>A list of the duplicated display names, as returned by
 *       {@link #getDuplicatedDisplayNames() getDuplicatedDisplayNames}. 
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorFormDefinitionCategories extends AbstractValidator {

  /**
   * The key of the error that is returned from this validator if there are duplicated display
   * names.
   */
  public final static String ERROR_KEY_DUPLICATE_DISPLAY_NAMES 
    = "kc.error.formdef.categoryDisplayNameDuplicated";

  private FormDefinition _formDef; 
  private List _duplicatedDisplayNames; 

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorFormDefinitionCategories v1 = (ValidatorFormDefinitionCategories) v;
      String dups =
        ApiFunctions.toCommaSeparatedList(ApiFunctions.toStringArray(v1.getDuplicatedDisplayNames()));
      v1.addErrorMessage(ValidatorFormDefinitionCategories.ERROR_KEY_DUPLICATE_DISPLAY_NAMES, dups);
      return true;
    }
  }

  public ValidatorFormDefinitionCategories() {
    super();
    addErrorKey(ERROR_KEY_DUPLICATE_DISPLAY_NAMES);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY_DUPLICATE_DISPLAY_NAMES);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();

    Set uniqueDisplayNames = new HashSet();
    List duplicatedDisplayNames = new ArrayList();
    
    KcCategoryDefinitionValidationService catService = new KcCategoryDefinitionValidationService();
    catService.setCheckDisplayName(true);
    catService.setCheckCategoryType(true);

    FormDefinition formDef = getFormDefinition();
    FormDefinitionCategory[] categories = formDef.getCategories();
    for (int i = 0; i < categories.length; i++) {
      FormDefinitionCategory category = categories[i];
      catService.setCategoryDefinition(category); 
      errors.addErrors(catService.validate());
      if (category.isPage()) {
        String displayName = category.getDisplayName();
        if (displayName != null) {
          if (uniqueDisplayNames.contains(displayName)) {
            duplicatedDisplayNames.add(displayName);
          }
          else {
            uniqueDisplayNames.add(displayName);
          }          
        }          
      }
    }
    
    if (!duplicatedDisplayNames.isEmpty()) {
      setDuplicatedDisplayNames(duplicatedDisplayNames);
      notifyValidatorErrorListener(ERROR_KEY_DUPLICATE_DISPLAY_NAMES);
    }
    return errors;
  }

  public FormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(FormDefinition definition) {
    _formDef = definition;
  }

  public List getDuplicatedDisplayNames() {
    return _duplicatedDisplayNames;
  }

  private void setDuplicatedDisplayNames(List values) {
    _duplicatedDisplayNames = values;
  }
}
