package com.gulfstreambio.kc.form.def;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorCollection;
import com.ardais.bigr.validation.ValidatorCollectionNonProceeding;
import com.ardais.bigr.validation.ValidatorCollectionProceeding;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.ardais.bigr.validation.ValidatorInput;
import com.ardais.bigr.validation.ValidatorInputOutputProperties;
import com.ardais.bigr.validation.ValidatorOutput;
import com.ardais.bigr.validation.ValidatorRequired;
import com.ardais.bigr.validation.ValidatorRequiredOneOrMore;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.FormInstanceQueryCriteria;
import com.gulfstreambio.kc.form.FormInstanceService;
import com.gulfstreambio.kc.form.FormInstanceServiceResponse;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.ardais.bigr.validation.ValidatorStringLength;

/**
 * KnowledgeCapture's form definition validation service.  This service performs
 * KnowledgeCapture-specific validation related to entire form definitions.  To use this service, 
 * set the form definition to be validated, call one or more setCheck* methods to indicate which 
 * validations are to be performed, and then call the validate method to perform validation. 
 */
class KcFormDefinitionValidationService
  extends AbstractValidationService
  implements ValidatorInputOutputProperties {

  private FormDefinition _formDef;
  private String _formDefinitionId;

  private boolean _checkCreateable;
  private boolean _checkDeleteable;
  private boolean _checkUpdateable;
  private boolean _checkIdSpecified;
  private boolean _checkNameSpecified;
  private boolean _checkTagsSpecified;

  /**
   * Creates a new <code>KcFormDefinitionValidationService</code>.
   */
  public KcFormDefinitionValidationService() {
    super();
  }

  /**
   * Creates a new <code>KcFormDefinitionValidationService</code> to validate the specified
   * form definition.
   * 
   * @param  definition  the <code>DataFormDefinition</code>
   */
  public KcFormDefinitionValidationService(FormDefinition definition) {
    this();
    setFormDefinition(definition);
  }

  /**
   * Creates a new <code>KcFormDefinitionValidationService</code> to validate the specified
   * form definition id.
   * 
   * @param  formDefinitionId  the <code>id of a FormDefinition</code>
   */
  public KcFormDefinitionValidationService(String formDefinitionId) {
    this();
    setFormDefinitionId(formDefinitionId);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();

    // Create the shared context and set the input form definition and/or id. 
    FormDefinitionValidatorContext context = new FormDefinitionValidatorContext();
    context.setFormDefinition(getFormDefinition());
    context.setFormDefinitionId(getFormDefinitionId());

    // Create the validator collection and add the shared context.
    ValidatorCollection collection = new ValidatorCollectionProceeding();
    collection.setValidatorContext(context);

    if (isCheckCreateable()) {
      context.setDet(DetService.SINGLETON.getDataElementTaxonomy());
      collection.addValidator(createCreateableValidator());
    }

    if (isCheckUpdateable()) {
      collection.addValidator(createUpdateableValidator());
    }

    if (isCheckDeleteable()) {
      collection.addValidator(createDeleteableValidator());
    }

    if (isCheckIdSpecified()) {
      collection.addValidator(
        createIdSpecifiedValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinitionId"));
    }

    if (isCheckTagsSpecified()) {
      ValidatorRequiredOneOrMore v = new ValidatorRequiredOneOrMore();
      v.setPropertyDisplayName("form definition tags");
      List tags = new ArrayList(Arrays.asList(getFormDefinition().getTags()));
      v.setValues(tags);
      collection.addValidator(v);
    }

    // Perform all requested validations. 
    if (!collection.isEmpty()) {
      errors.addErrors(collection.validate());
    }

    // If no validations were requested, then throw an exception. 
    else {
      throw new ApiException("KcFormDefinitionValidationService: No validation checks requested.");
    }

    return errors;
  }

  private ValidatorCollection createCreateableValidator() {
    ValidatorCollection returnValue = null;
    if (FormDefinitionTypes.DATA.equalsIgnoreCase(getFormDefinition().getType())) {
      returnValue = createCreateableDataFormValidator();
    }
    else if (FormDefinitionTypes.RESULTS.equalsIgnoreCase(getFormDefinition().getType())) {
      returnValue = createCreateableResultsFormValidator();
    }
    else if (FormDefinitionTypes.QUERY.equalsIgnoreCase(getFormDefinition().getType())) {
      returnValue = createCreateableQueryFormValidator();
    }
    return returnValue;
  }
  
  private ValidatorCollection createCreateableDataFormValidator() {
    // Create the validator collection.
    ValidatorCollection collection = new ValidatorCollectionProceeding();

    collection.addValidator(
      createNameSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
    collection.addValidator(
      createAllCategoriesValidator(),
      new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
    collection.addValidator(
      createCategoryTypesValidator(),
      new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
    collection.addValidator(
      createDataFormDataElementsValidator(),
      new ValidatorInput[] {
        new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
        new ValidatorInput(INPUT_DET, "det")});
    collection.addValidator(
        createValueSetsValidator(),
        new ValidatorInput[] {
          new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
          new ValidatorInput(INPUT_DET, "det")});    
    collection.addValidator(createNameLengthValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
 
    return collection;
  }
  
  private ValidatorCollection createCreateableResultsFormValidator() {
    // Create the validator collection.
    ValidatorCollection collection = new ValidatorCollectionProceeding();

    collection.addValidator(
      createNameSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
    collection.addValidator(
      createResultsFormDataElementsValidator(),
      new ValidatorInput[] {
        new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
        new ValidatorInput(INPUT_DET, "det")});
    collection.addValidator(createNameLengthValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
 
    return collection;
  }

  private ValidatorCollection createCreateableQueryFormValidator() {
    // Create the validator collection.
    ValidatorCollection collection = new ValidatorCollectionProceeding();

    collection.addValidator(
      createNameSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
    collection.addValidator(
      createAllCategoriesValidator(),
      new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
    collection.addValidator(
      createCategoryTypesValidator(),
      new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
    collection.addValidator(
      createQueryFormDataElementsValidator(),
      new ValidatorInput[] {
        new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
        new ValidatorInput(INPUT_DET, "det")});
    collection.addValidator(
        createQueryFormRollupsValidator(),
        new ValidatorInput[] {
          new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
          new ValidatorInput(INPUT_DET, "det")}); 
    collection.addValidator(createNameLengthValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
 
    return collection;
  }  

  private ValidatorCollection createUpdateableValidator() {
    ValidatorCollection returnValue = null;
    if (FormDefinitionTypes.DATA.equalsIgnoreCase(getFormDefinition().getType())) {
      returnValue = createUpdateableDataFormValidator();
    }
    else if (FormDefinitionTypes.RESULTS.equalsIgnoreCase(getFormDefinition().getType())) {
      returnValue = createUpdateableResultsFormValidator();
    }
    else if (FormDefinitionTypes.QUERY.equalsIgnoreCase(getFormDefinition().getType())) {
      returnValue = createUpdateableQueryFormValidator();
    }
    return returnValue;
  }  
  
  private ValidatorCollection createUpdateableResultsFormValidator() {
    // Create the validator collection.
    ValidatorCollection collection = new ValidatorCollectionNonProceeding();

    // Create validators to check that the form definition id was specified and that the 
    // form definition with the specified id exists.
    collection.addValidator(
      createIdSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionId"));
    collection.addValidator(
      createDefinitionExistsValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionId"),
      new ValidatorOutput[] {
        new ValidatorOutput(OUTPUT_FORM_DEFINITION, "persistedFormDefinition"),
        new ValidatorOutput(OUTPUT_DET, "det"),
        });

    // At this point we will have validated that the form definition exists, so if that passes  
    // we'll perform the remainder of the validations in a proceeding collection.
    ValidatorCollection collection1 = new ValidatorCollectionProceeding();
    collection1.addValidator(
        createNameSpecifiedValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
    collection1.addValidator(createNameLengthValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
    collection1.addValidator(
        createResultsFormDataElementsValidator(),
        new ValidatorInput[] {
          new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
          new ValidatorInput(INPUT_DET, "det")});
    
    collection.addValidator(collection1);
    return collection;
  }
  
  private ValidatorCollection createUpdateableQueryFormValidator() {

    // Create the validator collection.
    ValidatorCollection collection = new ValidatorCollectionProceeding();

    collection.addValidator(
        createIdSpecifiedValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionId"));
      collection.addValidator(
        createDefinitionExistsValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionId"),
        new ValidatorOutput[] {
          new ValidatorOutput(OUTPUT_FORM_DEFINITION, "persistedFormDefinition"),
          new ValidatorOutput(OUTPUT_DET, "det"),
          });
    collection.addValidator(
      createNameSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
      collection.addValidator(createNameLengthValidator(),
          new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
    collection.addValidator(
      createAllCategoriesValidator(),
      new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
    collection.addValidator(
      createCategoryTypesValidator(),
      new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
    collection.addValidator(
      createQueryFormDataElementsValidator(),
      new ValidatorInput[] {
        new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
        new ValidatorInput(INPUT_DET, "det")});
    collection.addValidator(
        createQueryFormRollupsValidator(),
        new ValidatorInput[] {
          new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
          new ValidatorInput(INPUT_DET, "det")});     
    collection.addValidator(createNameLengthValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
 
    return collection;
  }
  
  private ValidatorCollection createUpdateableDataFormValidator() {
    // Create the validator collection.
    ValidatorCollection collection = new ValidatorCollectionNonProceeding();

    // Create validators to check that the form definition id was specified and that the 
    // form definition with the specified id exists.
    collection.addValidator(
      createIdSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionId"));
    collection.addValidator(
      createDefinitionExistsValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinition.formDefinitionId"),
      new ValidatorOutput[] {
        new ValidatorOutput(OUTPUT_FORM_DEFINITION, "persistedFormDefinition"),
        new ValidatorOutput(OUTPUT_DET, "det"),
        });
    collection.addValidator(createNameLengthValidator(),
        new ValidatorInput(INPUT_VALUE, "formDefinition.name"));


    // At this point we will have validated that the form definition exists, so if that passes  
    // we'll perform the remainder of the validations in a proceeding collection.
    ValidatorCollection collection1 = new ValidatorCollectionProceeding();

    collection.addValidator(
      createNameSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinition.name"));
    collection.addValidator(
      createAllCategoriesValidator(),
      new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
    collection.addValidator(
      createCategoryTypesValidator(),
      new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"));
    collection.addValidator(
      createDataFormDataElementsValidator(),
      new ValidatorInput[] {
        new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
        new ValidatorInput(INPUT_DET, "det")});
    collection.addValidator(
        createValueSetsValidator(),
        new ValidatorInput[] {
          new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
          new ValidatorInput(INPUT_DET, "det")});       
    
    collection.addValidator(collection1);
    return collection;
  }

  private ValidatorCollection createDeleteableValidator() {
    // Create the validator collection.
    ValidatorCollection collection = new ValidatorCollectionNonProceeding();

    // Create validators to check that the form definition id was specified and that the 
    // form definition with the specified id exists.
    collection.addValidator(
      createIdSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinitionId"));
    collection.addValidator(
      createDefinitionExistsValidator(),
      new ValidatorInput(INPUT_VALUE, "formDefinitionId"),
      new ValidatorOutput(OUTPUT_FORM_DEFINITION, "persistedFormDefinition"));
    
    // Create a validator to check that no form instances were created from this form definition.
    collection.addValidator(
      createNoInstancesValidator(),
      new ValidatorInput(INPUT_FORM_DEFINITION, "persistedFormDefinition"));

    return collection;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the form defintion name
   * was specified.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the name. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createNameSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorRequired v1 = (ValidatorRequired) v;
        v1.addErrorMessage("kc.error.formdef.noname");
        return true;
      }
    }, ValidatorRequired.ERROR_KEY1);
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks all aspects of each inidividual 
   * category in the form defintion.  The returned <code>Validator</code> must have an input
   * with property name {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes 
   * the form definition. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createAllCategoriesValidator() {
    return new ValidatorFormDefinitionCategories();
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether there are any conflicts 
   * in the ui types of the categories in the specified form definition.  The returned 
   * <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the form definition. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createCategoryTypesValidator() {
    return new ValidatorFormDefinitionCategoryTypes();
  }

  /**
   * Creates and returns a <code>Validator</code> that checks checks all aspects of each 
   * value set in the specified form definition.  The returned 
   * <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the form definition
    * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createValueSetsValidator() {
    return new ValidatorFormDefinitionValueSets();
  }

  /**
   * Creates and returns a <code>Validator</code> that checks checks all aspects of each 
   * individual data element in the specified data form definition.  The returned 
   * <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the form definition
   * and an input with property name
   * {@link ValidatorInputOutputProperties#INPUT_DET} that takes the DET.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createDataFormDataElementsValidator() {
    return new ValidatorDataFormDefinitionDataElements();
  }  

  /**
   * Creates and returns a <code>Validator</code> that checks checks all aspects of each 
   * individual data element in the specified query form definition.  The returned 
   * <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the form definition
   * and an input with property name
   * {@link ValidatorInputOutputProperties#INPUT_DET} that takes the DET.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createQueryFormDataElementsValidator() {
    return new ValidatorQueryFormDefinitionDataElements();
  }   

  /**
   * Creates and returns a <code>Validator</code> that checks checks all aspects of each 
   * rollup in the specified query form definition.  The returned 
   * <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the form definition
   * and an input with property name
   * {@link ValidatorInputOutputProperties#INPUT_DET} that takes the DET.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createQueryFormRollupsValidator() {
    return new ValidatorQueryFormDefinitionRollups();
  }  
  
  
  
  /**
   * Creates and returns a <code>Validator</code> that checks checks all aspects of each 
   * individual data element in the specified results form definition.  The returned 
   * <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that takes the form definition
   * and an input with property name
   * {@link ValidatorInputOutputProperties#INPUT_DET} that takes the DET.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createResultsFormDataElementsValidator() {
    return new ValidatorResultsFormDefinitionDataElements();
  }  
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the form definition id was 
   * specified.  The returned <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the form definition id to be 
   * validated. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createIdSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.setPropertyDisplayName("form definition id");
    return v;
  }
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the form definition exists. 
   * The returned <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the id of the form definition
   * and have an output with property name
   * {@link ValidatorInputOutputProperties#OUTPUT_FORM_DEFINITION} that returns the form
   * definition if it exists.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createDefinitionExistsValidator() {
    return new ValidatorFormDefinitionExists();
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the specified form definition 
   * has changed from its current persisted state.  The returned <code>Validator</code> must have 
   * an input with property name {@link ValidatorInputOutputProperties#INPUT_FORM_DEFINITION} that 
   * takes the form definition.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createNoInstancesValidator() {
    ValidatorNoFormInstancesForFormDefinition v =
      new ValidatorNoFormInstancesForFormDefinition();
    v.setFormDefinition(getFormDefinition());
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks that the length of the form 
   * definition name is less than its maximum length.  The returned <code>Validator</code> must 
   * have an input with property name {@link ValidatorInputOutputProperties#INPUT_VALUE} that 
   * takes the name. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createNameLengthValidator() {
    ValidatorStringLength v = new ValidatorStringLength();
    v.setMaxLength(256);
    v.addValidatorErrorListener(new ValidatorErrorListener() {
      public boolean validatorError(Validator v, String errorKey) {
        ValidatorStringLength v1 = (ValidatorStringLength) v;
        v1.addErrorMessage("kc.error.formdef.nametoolong");
        return true;
      }
    }, ValidatorStringLength.ERROR_KEY_TOO_LONG);
    return v;
  }
  
  
  
  public FormDefinition getFormDefinition() {
    return _formDef;
  }

  /**
   * Sets the form definition to be validated.
   * 
   * @param  definition  the <code>FormDefinition</code>
   */
  public void setFormDefinition(FormDefinition definition) {
    if (definition == null) {
      throw new ApiException("KcFormDefinitionValidationService.setFormDefinition: a form definition was not specified.");
    }
    _formDef = definition;
  }

  private boolean isCheckCreateable() {
    return _checkCreateable;
  }

  /**
   * Sets whether validation should be performed to determine whether the form definition is
   * createable.
   * 
   * @param  check  <code>true</code> if createability should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckCreateable(boolean check) {
    _checkCreateable = check;
  }

  private boolean isCheckDeleteable() {
    return _checkDeleteable;
  }

  /**
   * Sets whether validation should be performed to determine whether the form definition is
   * deletable.
   * 
   * @param  check  <code>true</code> if deleteability should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckDeleteable(boolean check) {
    _checkDeleteable = check;
  }

  private boolean isCheckUpdateable() {
    return _checkUpdateable;
  }

  /**
   * Sets whether validation should be performed to determine whether the form definition is
   * updateable.
   * 
   * @param  check  <code>true</code> if updateability should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckUpdateable(boolean check) {
    _checkUpdateable = check;
  }

  private boolean isCheckIdSpecified() {
    return _checkIdSpecified;
  }

  /**
   * Sets whether validation should be performed to determine whether the form definition id
   * was specified.
   * 
   * @param  check  <code>true</code> if specification of form definition id should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckIdSpecified(boolean check) {
    _checkIdSpecified = check;
  }

  private boolean isCheckTagsSpecified() {
    return _checkTagsSpecified;
  }

  /**
   * Sets whether validation should be performed to determine whether at least one tag was
   * specified.
   * 
   * @param  check  <code>true</code> if specification of form definition tags should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckTagsSpecified(boolean check) {
    _checkTagsSpecified = check;
  }

  /**
   * @return Returns the formDefinitionId.
   */
  public String getFormDefinitionId() {
    return _formDefinitionId;
  }
  
  /**
   * @param formDefinitionId The formDefinitionId to set.
   */
  public void setFormDefinitionId(String formDefinitionId) {
    _formDefinitionId = formDefinitionId;
  }
}
