package com.gulfstreambio.kc.form.def;

import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;

/**
 * Validates that the category types specified in the categories of a form definition do not 
 * conflict.  The following rules are validated:
 * <p>
 * <ul>
 * <li>Sibling categories must all have the same category type.</li>
 * <li>Only the first two levels of category may have category type "page".</li>
 * <li>If a category has category type "page", it must be a first level category, or its parent 
 * category must have category type "page".</li>
 * </ul>
 * <p>
 * This validator may return three possible errors as follows.  None have insertion strings.
 * <ul>
 * <li>{@link #ERROR_KEY1} - The category type of two or more sibling categories is not the same.
 * </li>
 * <li>{@link #ERROR_KEY2} - The category type of a category beyond a level 2 category is
 *     "page", which is not allowed beyond level 2 categories.</li>
 * <li>{@link #ERROR_KEY3} - The category type of a category that is set to "page" has
 *     a parent category whose category type is not set to "page".</li>
 * </ul> 
 */
public class ValidatorFormDefinitionCategoryTypes extends AbstractValidator {

  /**
   * This is the error key that is returned when the category type of two or more sibling 
   * categories is not the same.
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.categorytypenotsameassibling";

  /**
   * This is the error key that is returned when the category type of a category beyond a level 2 
   * category is "page". 
   */
  public final static String ERROR_KEY2 = "kc.error.formdef.categorytypecantbetab";

  /**
   * This is the error key that is returned when the category type of a category that is set to 
   * "page" has a parent category whose category type is not set to "page".
   */
  public final static String ERROR_KEY3 = "kc.error.formdef.categorytypeparentnottab";
  
  /**
   * This is the error key that is returned when the category type of a category that is set to 
   * "heading" has an ancestor category whose category type is set to "heading" exists.
   */
  public final static String ERROR_KEY4 = "kc.error.formdef.categorytypeancestorheading";
  

  private FormDefinition _formDef; 

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorFormDefinitionCategoryTypes v1 = (ValidatorFormDefinitionCategoryTypes) v;
      v1.addErrorMessage(errorKey);
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorFormDefinitionCategoryTypes</code> validator.
   */
  public ValidatorFormDefinitionCategoryTypes() {
    super();
    addErrorKey(ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    addErrorKey(ERROR_KEY3);
    addErrorKey(ERROR_KEY4);
    ValidatorErrorListener listener = new DefaultErrorListener();
    addValidatorErrorListener(listener, ERROR_KEY1);
    addValidatorErrorListener(listener, ERROR_KEY2);
    addValidatorErrorListener(listener, ERROR_KEY3);
    addValidatorErrorListener(listener, ERROR_KEY4);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    validateCategoryTypes(
      getFormDefinition().getFormElements().getFormElements(),
      null,
      1);
    return getActionErrors();
  }

  private void validateCategoryTypes(FormDefinitionElement[] formElementDefs, String parentCategoryType, int level) {
    String commonCategoryType = null;
    for (int i = 0; i < formElementDefs.length; i++) {
      FormDefinitionElement def = (FormDefinitionElement) formElementDefs[i];
      if (def.isCategory()) {
        FormDefinitionCategory category = def.getCategory();
        String categoryType = category.getCategoryType();

        // Ensure that sibling categories have the same category type.
        if (commonCategoryType == null) {
          commonCategoryType = categoryType;
        }
        else if (!commonCategoryType.equals(categoryType)) {
          notifyValidatorErrorListener(ERROR_KEY1);
        }
        
        if (FormDefinitionCategoryTypes.PAGE.equals(categoryType)) {
          // Ensure that categories with category type "page" are level 1 or 2.
          if (level > 2) {
            notifyValidatorErrorListener(ERROR_KEY2);
          }

          // Ensure that categories with category type "page" have a parent with category 
          // type "page".
          if ((parentCategoryType != null)
              && (!parentCategoryType.equals(FormDefinitionCategoryTypes.PAGE))) {
            notifyValidatorErrorListener(ERROR_KEY3);
          }        
          
        } else if (FormDefinitionCategoryTypes.HEADING.equals(categoryType)) {
          //There must only be a single level of category with category type "heading".
          if (FormDefinitionCategoryTypes.HEADING.equals(parentCategoryType)) {                
            notifyValidatorErrorListener(ERROR_KEY4);
          }
        }
        
        // Validate all children categories.
        validateCategoryTypes(category.getFormElements(), categoryType, level + 1);
      }
    }
    
  }

  public FormDefinition getFormDefinition() {
    return _formDef;
  }

  public void setFormDefinition(FormDefinition definition) {
    _formDef = definition;
  }

}
