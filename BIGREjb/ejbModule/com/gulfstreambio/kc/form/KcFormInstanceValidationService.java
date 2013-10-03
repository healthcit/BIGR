package com.gulfstreambio.kc.form;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.ConversionException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrConvertUtilsBean;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.util.VariablePrecisionDate;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorCollection;
import com.ardais.bigr.validation.ValidatorCollectionNonProceeding;
import com.ardais.bigr.validation.ValidatorCollectionProceeding;
import com.ardais.bigr.validation.ValidatorInput;
import com.ardais.bigr.validation.ValidatorInputOutputProperties;
import com.ardais.bigr.validation.ValidatorOutput;
import com.ardais.bigr.validation.ValidatorRequired;
import com.gulfstreambio.kc.det.DetElementDatatypes;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.form.def.ValidatorFormDefinitionExists;

/**
 * KnowledgeCapture's form instance validation service.  This service performs
 * KnowledgeCapture-specific validation related to form instances.  To use this service, 
 * set the form instance to be validated, call one or more setCheck* methods to indicate which 
 * validations are to be performed, and then call the validate method to perform validation. 
 */
class KcFormInstanceValidationService 
extends AbstractValidationService 
implements ValidatorInputOutputProperties {

  private FormInstance _formInstance;

  private boolean _checkFormDefinitionIdSpecified;
  private boolean _checkCreateable;
  private boolean _checkUpdateable;
  private boolean _checkDeleteable;
  private boolean _checkIdSpecified;

  /**
   * Creates a new <code>KcFormInstanceValidationService</code>.
   */
  public KcFormInstanceValidationService() {
    super();
  }

  /**
   * Creates a new <code>KcFormInstanceValidationService</code> to validate the specified
   * form instance.
   * 
   * @param  form  the <code>FormInstance</code>
   */
  public KcFormInstanceValidationService(FormInstance form) {
    super();
    setFormInstance(form);
  }

  private FormInstance getFormInstance() {
    return _formInstance;
  }

  /**
   * Sets the form instance to be validated.
   * 
   * @param  form  the <code>FormInstance</code>
   */
  public void setFormInstance(FormInstance formInstance) {
    if (formInstance == null) {
      throw new ApiException("KcFormInstanceValidationService.setFormInstance: a form was not specified.");
    }
    _formInstance = formInstance;
  }

  
  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    BtxActionErrors errors = getActionErrors();

    // Create the shared context and set the input form definition. 
    FormInstanceValidatorContext context = new FormInstanceValidatorContext();
    context.setFormInstance(getFormInstance());

    // Create the validator collection and add the shared context.
    ValidatorCollection collection = new ValidatorCollectionProceeding();
    collection.setValidatorContext(context);

    if (isCheckFormDefinitionIdSpecified()) {      
      collection.addValidator(createFormDefinitionIdSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formInstance.formDefinitionId"));
    }
    
    if (isCheckCreateable()) {
      collection.addValidator(createCreateableValidator());
    }

    if (isCheckUpdateable()) {
      collection.addValidator(createUpdateableValidator());
    }

    if (isCheckDeleteable()) {
      collection.addValidator(createIdSpecifiedValidator(),
          new ValidatorInput(INPUT_VALUE, "formInstance.formInstanceId"));
    }

    if (isCheckIdSpecified()) {
      collection.addValidator(createIdSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formInstance.formInstanceId"));
    }

    // Perform all requested validations. 
    if (!collection.isEmpty()) {
      errors.addErrors(collection.validate());
    }

    // If no validations were requested, then throw an exception. 
    else {
      throw new ApiException("KcFormInstanceValidationService: No validation checks requested.");
    }

    return errors;
  }

  protected ValidatorRequired createFormDefinitionIdSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.setPropertyDisplayName("form definition id");
    return v;
  }
  
  protected Validator createFormDefinitionExistsValidator() {
    return new ValidatorFormDefinitionExists();
  }

  private ValidatorCollection createCreateableValidator() {
    // Create the validator collection.
    ValidatorCollection collection = new ValidatorCollectionProceeding();

    collection.addValidator(
        createFormDefinitionIdSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formInstance.formDefinitionId"));
    collection.addValidator(
        createFormDefinitionExistsValidator(),
        new ValidatorInput(INPUT_VALUE, "formInstance.formDefinitionId"),
        new ValidatorOutput(OUTPUT_FORM_DEFINITION, "formDefinition"));

    {//If form Def is available we want a proceeding collection to get most error messages out
      ValidatorCollection collection2 = new ValidatorCollectionProceeding();
     
      // Create validators to check that instance has all the 
      // data elements required in the form definition 
      collection2.addValidator(
          new ValidatorRequiredDataElements(),
          new ValidatorInput[] {
            new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
            new ValidatorInput(INPUT_FORM_INSTANCE, "formInstance")});
      
      collection2.addValidator(
          new ValidatorDataElements(),
        new ValidatorInput[] {
          new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
          new ValidatorInput(INPUT_FORM_INSTANCE, "formInstance")});
      collection.addValidator(collection2);
    }      
    
    
    return collection;
  }

  private ValidatorCollection createUpdateableValidator() {
    // Create the validator collection.
    ValidatorCollection collection = new ValidatorCollectionNonProceeding();

    // Create validators to check that the form definition id was specified and that the 
    // form definition with the specified id exists.
    collection.addValidator(
      createIdSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formInstance.formInstanceId"));
    collection.addValidator(
        new ValidatorFormInstanceExists(),
        new ValidatorInput(INPUT_VALUE, "formInstance.formInstanceId"));

    collection.addValidator(
        createFormDefinitionIdSpecifiedValidator(),
      new ValidatorInput(INPUT_VALUE, "formInstance.formDefinitionId"));
    collection.addValidator(
        createFormDefinitionExistsValidator(),
        new ValidatorInput(INPUT_VALUE, "formInstance.formDefinitionId"),
        new ValidatorOutput(OUTPUT_FORM_DEFINITION, "formDefinition"));

      
    {//If form Def is available we want a proceeding collection to get most error messages out
      ValidatorCollection collection2 = new ValidatorCollectionProceeding();
      //Ensure that any elements being updated are not being cleared if they are required
      //this Validation is slightly different from the ValidatorRequiredDataElements 
      //which is run only during create
      collection2.addValidator(
          new ValidatorRequiredDataElementsNotCleared(),
          new ValidatorInput[] {
            new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
            new ValidatorInput(INPUT_FORM_INSTANCE, "formInstance")});
      
      collection2.addValidator(
          new ValidatorDataElements(),
        new ValidatorInput[] {
          new ValidatorInput(INPUT_FORM_DEFINITION, "formDefinition"),
          new ValidatorInput(INPUT_FORM_INSTANCE, "formInstance")});
      collection.addValidator(collection2);
    }      
    
   
    return collection;
  }
    
  /**
   * Creates and returns a <code>Validator</code> that checks whether the form instance id was 
   * specified.  The returned <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the form instance id to be 
   * validated. 
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createIdSpecifiedValidator() {
    ValidatorRequired v = new ValidatorRequired();
    v.setPropertyDisplayName("form instance id");    
    return v;
  }  
  
  private boolean isCheckFormDefinitionIdSpecified() {
    return _checkFormDefinitionIdSpecified;
  }

  /**
   * Sets whether validation should be performed to determine whether a form definition id was
   * specified.
   * 
   * @param  check  <code>true</code> if specification of a form definition id should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckFormDefinitionIdSpecified(boolean check) {
    _checkFormDefinitionIdSpecified = check;
  }

  private boolean isCheckCreateable() {
    return _checkCreateable;
  }

  /**
   * Sets whether validation should be performed to determine whether the form instance is
   * createable.
   * 
   * @param  check  <code>true</code> if createability should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckCreateable(boolean check) {
    _checkCreateable = check;
  }

  private boolean isCheckUpdateable() {
    return _checkUpdateable;
  }

  /**
   * Sets whether validation should be performed to determine whether the form instance is
   * updateable.
   * 
   * @param  check  <code>true</code> if updateability should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckUpdateable(boolean check) {
    _checkUpdateable = check;
  }

  private boolean isCheckDeleteable() {
    return _checkDeleteable;
  }

  /**
   * Sets whether validation should be performed to determine whether the form instance is
   * deleteable.
   * 
   * @param  check  <code>true</code> if deleteability should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckDeleteable(boolean check) {
    _checkDeleteable = check;
  }

  private boolean isCheckIdSpecified() {
    return _checkIdSpecified;
  }

  /**
   * Sets whether validation should be performed to determine whether the form instance id
   * was specified.
   * 
   * @param  check  <code>true</code> if specification of form definition id should be validated;
   *                <code>false</code> otherwise. 
   */
  public void setCheckIdSpecified(boolean check) {
    _checkIdSpecified = check;
  }

  static Comparable CastToComparableObj(String value, String datatype)
    throws ConversionException {
    DetValueSet standardValues = 
      DetService.SINGLETON.getDataElementTaxonomy().getSystemStandardValues();
    if (!ApiFunctions.isEmpty(value) && !standardValues.containsValue(value)) {
      if (DetElementDatatypes.CV.equals(datatype) || DetElementDatatypes.REPORT.equals(datatype)
          || DetElementDatatypes.TEXT.equals(datatype)) {
        //nothing to do the string itself is our comparable result
        return value;
      }
      else {
        try {//try casting the value into the Typed Object based on datatype 
          if (DetElementDatatypes.DATE.equals(datatype)) {
            if (value.equalsIgnoreCase("today")) {
              //we only want the date today NOT the time now
              java.util.Date today = new java.util.Date();
              //NOTE case is important!!
              SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
              String todayStr = fmt.format(today);
              return java.sql.Date.valueOf(todayStr);
            }
            else {
              return (Date) BigrConvertUtilsBean.SINGLETON.convert(value, Date.class);
            }
          }
          else if (DetElementDatatypes.VPD.equals(datatype)) {
            return new VariablePrecisionDate(value);
          }
          else if (DetElementDatatypes.FLOAT.equals(datatype)) {
            return (BigDecimal) BigrConvertUtilsBean.SINGLETON.convert(value, BigDecimal.class);
          }
          else if (DetElementDatatypes.INT.equals(datatype)) {
            return (Integer) BigrConvertUtilsBean.SINGLETON.convert(value, Integer.class);
          }
        }
        catch (Exception e) {
          throw new ConversionException("'" + value + "' couldn't be converted to datatype: "
              + datatype);
        }
      }
    }
    return null;
  }
  
}
