package com.gulfstreambio.kc.form.def;

import java.util.Arrays;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorCollection;
import com.ardais.bigr.validation.ValidatorCollectionNonProceeding;
import com.ardais.bigr.validation.ValidatorCollectionProceeding;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.ardais.bigr.validation.ValidatorFactory;
import com.ardais.bigr.validation.ValidatorRequired;
import com.ardais.bigr.validation.ValidatorValueInCollection;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory;

/**
 * KnowledgeCapture's category definition validation service.  This service performs
 * KnowledgeCapture-specific validation related to a single category definition within a 
 * form definition.  To use this service, set the category definition to be validated, call one 
 * or more setCheck* methods to indicate which validations are to be performed, and then call the 
 * validate method to perform validation. 
 */
class KcCategoryDefinitionValidationService extends AbstractValidationService {

  private FormDefinitionCategory _catDef;

  private boolean _checkDisplayName;
  private boolean _checkCategoryType;

  /**
   * Creates a new <code>KcCategoryDefinitionValidationService</code>.
   */
  public KcCategoryDefinitionValidationService() {
    super();
  }

  /**
   * Creates a new <code>KcCategoryDefinitionValidationService</code> to validate the specified
   * category definition.
   * 
   * @param  definition  the <code>DataFormDefinitionCategory</code>
   */
  public KcCategoryDefinitionValidationService(DataFormDefinitionCategory definition) {
    this();
    setCategoryDefinition(definition);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    FormDefinitionCategory catDef = getCategoryDefinition();
    if (catDef == null) {
      throw new ApiException("KcCategoryDefinitionValidationService.validate: a category definition was not specified.");
    }
    
    BtxActionErrors errors = getActionErrors();
    ValidatorCollection collection = new ValidatorCollectionProceeding();

    if (isCheckDisplayName()) {
      collection.addValidator(createDisplayNameSpecifiedValidator());
    }

    if (isCheckCategoryType()) {
      ValidatorCollection c = new ValidatorCollectionNonProceeding();      
      c.addValidator(createCategoryTypeSpecifiedValidator());
      c.addValidator(createCategoryTypeValidValidator());
      collection.addValidator(c);
    }

    // Perform all requested validations. 
    if (!collection.isEmpty()) {
      errors.addErrors(collection.validate());
    }

    // If no validations were requested, then throw an exception. 
    else {
      throw new ApiException("KcCategoryDefinitionValidationService: No validation checks requested.");
    }

    return errors;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the display name of
   * the category was specified.  The returned <code>Validator</code> must have an input with 
   * property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the display name
   * of the category.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createDisplayNameSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorRequired v1 = (ValidatorRequired) v;
        v1.addErrorMessage("kc.error.formdef.categorynameempty");
        return true;
      }
    }, ValidatorRequired.ERROR_KEY1);
    v.setValue(getCategoryDefinition().getDisplayName());
    return v;
  }
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the category type of
   * the category was specified.  The returned <code>Validator</code> must have an input with 
   * property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the category type
   * of the category.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createCategoryTypeSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorRequired v1 = (ValidatorRequired) v;
        v1.addErrorMessage("kc.error.formdef.categorytypeempty");
        return true;
      }
    }, ValidatorRequired.ERROR_KEY1);
    v.setValue(getCategoryDefinition().getCategoryType());
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the category type of
   * the category is in the list of valid values.  The returned <code>Validator</code> must have  
   * an input with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes 
   * the category type of the category.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createCategoryTypeValidValidator() {
    ValidatorValueInCollection v = new ValidatorValueInCollection();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorValueInCollection v1 = (ValidatorValueInCollection) v;
        String csvalues =
          ApiFunctions.toCommaSeparatedList(
            ApiFunctions.toStringArray(v1.getValueCollection()));
        v1.addErrorMessage("kc.error.formdef.badcategorytype", v1.getValue(), csvalues);
        return true;
      }
    }, ValidatorValueInCollection.ERROR_KEY1);
    v.setValue(getCategoryDefinition().getCategoryType());
    v.setValueSet(Arrays.asList(FormDefinitionCategoryTypes.getCategoryTypes()));
    return v;
  }

  private FormDefinitionCategory getCategoryDefinition() {
    return _catDef;
  }

  /**
   * Sets the category definition to be validated.
   * 
   * @param  definition  the <code>DataFormDefinitionCategory</code>
   */
  public void setCategoryDefinition(FormDefinitionCategory definition) {
    _catDef = definition;
  }

  private boolean isCheckDisplayName() {
    return _checkDisplayName;
  }

  private boolean isCheckCategoryType() {
    return _checkCategoryType;
  }

  /**
   * Sets whether the category definition display name should be validated.
   * 
   * @param  check  <code>true</code> if the category definition display name should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckDisplayName(boolean check) {
    _checkDisplayName = check;
  }

  /**
   * Sets whether the category definition category type should be validated.
   * 
   * @param  check  <code>true</code> if the category definition display category type should be 
   *                validated; <code>false</code> otherwise. 
   */
  public void setCheckCategoryType(boolean check) {
    _checkCategoryType = check;
  }
}
