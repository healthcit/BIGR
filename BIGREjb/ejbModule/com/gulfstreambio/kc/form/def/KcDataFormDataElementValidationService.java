package com.gulfstreambio.kc.form.def;

import java.util.Iterator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorCollection;
import com.ardais.bigr.validation.ValidatorCollectionNonProceeding;
import com.ardais.bigr.validation.ValidatorCollectionProceeding;
import com.ardais.bigr.validation.ValidatorInputOutputProperties;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement;

/**
 * KnowledgeCapture's data element definition validation service.  This service performs
 * KnowledgeCapture-specific validation related to a single data element definition within a 
 * form definition, inlcuding its value sets.  To use this service, set the data element 
 * definition to be validated and the id of the associated DET, call one or more setCheck* 
 * methods to indicate which validations are to be performed, and then call the validate method 
 * to perform validation. 
 */
class KcDataFormDataElementValidationService extends AbstractValidationService {

  private DataFormDefinitionDataElement _deDef;
  private DataElementTaxonomy _det; 

  private boolean _checkDataElementInDet;
  private boolean _checkNarrowValueSet;
  private boolean _checkStandardValueSet;
  private boolean _checkStandardIfNarrowValueSet;
  private boolean _checkNoValueSetWithNonCV;
  private boolean _checkValueSetNoOthersNarrowStandard;
  private boolean _checkAdeElements;
  private boolean _checkCalculation; 

  /**
   * @return Returns the checkAdeElements.
   */
  public boolean isCheckAdeElements() {
    return _checkAdeElements;
  }
  /**
   * @param checkAdeElements The checkAdeElements to set.
   */
  public void setCheckAdeElements(boolean checkAdeElements) {
    _checkAdeElements = checkAdeElements;
  }
  /**
   * Creates a new <code>KcDataFormDataElementValidationService</code>.
   */
  public KcDataFormDataElementValidationService() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    DataFormDefinitionDataElement deDef = getDataElementDefinition();
    if (deDef == null) {
      throw new ApiException("KcDataFormDataElementValidationService.validate: a data element definition was not specified.");
    }
    DataElementTaxonomy det = getDet();
    if (det == null) {
      throw new ApiException("KcDataFormDataElementValidationService.validate: a DET was not specified.");
    }    
    
    BtxActionErrors errors = getActionErrors();

    ValidatorCollection masterCollection = null;
    ValidatorCollection collection = null;

    // The collections are a bit tricky.  If we are to check if the data element is in the DET
    // and that validation fails, then we do not want to proceed so we need the master collection 
    // to be non-proceeding.  We always want to perform all other checks though, so all
    // subsequent validators need to be added to a proceeding validator, whether we are doing
    // the DET check or not.
    if (isCheckDataElementInDet()) {
      masterCollection = new ValidatorCollectionNonProceeding();
      masterCollection.addValidator(createDataElementDefinitionInDetValidator());
      collection = new ValidatorCollectionProceeding();
      masterCollection.addValidator(collection);
    }
    else {
      masterCollection = new ValidatorCollectionProceeding();
      collection = masterCollection;
    }

    if (isCheckStandardIfNarrowValueSet()) {
      collection.addValidator(createStandardValueSetExistsValidator());
    }

    if (isCheckNarrowValueSet()) {
      collection.addValidator(createNarrowSubsetStandardValidator());
    }

    if (isCheckStandardValueSet()) {
      collection.addValidator(createStandardSubsetBroadValidator());
    }
    
   
    if (isCheckNoValueSetWithNonCV()){
      collection.addValidator(createNoValueSetWithNonCVValidator());
    }
    
    if (isCheckValueSetNoOthersNarrowStandard()) {
      collection.addValidator(createNoOthersStandardNarrow());
    }
    
    if (isCheckAdeElements()) {
      collection.addValidator(createAdeElementsValidator());
    }
    
    if (isCheckCalculation()) {
      collection.addValidator(createCalculationValidator());
    }

    // Perform all requested validations. 
    if (!collection.isEmpty()) {
      errors.addErrors(masterCollection.validate());
    }

    // If no validations were requested, then throw an exception. 
    else {
      throw new ApiException("KcDataFormDataElementValidationService: No validation checks requested.");
    }

    return errors;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the data element is 
   * present in the DET.  The returned <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the CUI of the data element
   * and an input with property name {@link ValidatorInputOutputProperties#INPUT_DET} that
   * takes the DET to check against.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createDataElementDefinitionInDetValidator() {
    ValidatorDetContainsDataElement v = new ValidatorDetContainsDataElement();
    v.setValue(getDataElementDefinition().getCui());
    v.setDet(getDet());
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the standard value set
   * of the data element exists.  The returned <code>Validator</code> must have an input with 
   * property name {@link ValidatorInputOutputProperties#INPUT_DATA_ELEMENT} that takes the 
   * data element.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createStandardValueSetExistsValidator() {
    ValidatorStandardIfNarrow v = new ValidatorStandardIfNarrow();
    v.setDataElement(getDataElementDefinition());
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the standard value set
   * of the data element exists.  The returned <code>Validator</code> must have an input with 
   * property name {@link ValidatorInputOutputProperties#INPUT_DATA_ELEMENT} that takes the 
   * data element.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createAdeElementsValidator() {
    ValidatorFormDefinitionAdeElements v = new ValidatorFormDefinitionAdeElements();
    v.setDataElement(getDataElementDefinition());
    return v;
  } 
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the narrow value set is 
   * a subset of the standard value set.  The returned <code>Validator</code> must have an input 
   * with property name {@link ValidatorInputOutputProperties#INPUT_DATA_ELEMENT} that takes the 
   * data element.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createNarrowSubsetStandardValidator() {
    ValidatorNarrowSubsetStandard v = new ValidatorNarrowSubsetStandard();
    v.setDataElement(getDataElementDefinition());
    return v;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the standard value set is 
   * a subset of the broad value set.  The returned <code>Validator</code> must have an input 
   * with property name {@link ValidatorInputOutputProperties#INPUT_DATA_ELEMENT} that takes the 
   * data element.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createStandardSubsetBroadValidator() {
    ValidatorStandardSubsetBroad v = new ValidatorStandardSubsetBroad();
    v.setDataElement(getDataElementDefinition());
    return v;
  }
  
    
  /**
   * Creates and returns a <code>Validator</code> that checks whether the data element is
   * is a of CV datatype; if not, then it should not have any value sets.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createNoValueSetWithNonCVValidator() {
    ValidatorNoValueSetWithNonCV v = new ValidatorNoValueSetWithNonCV();
    v.setDataElement(getDataElementDefinition());
    return v;    
  } 
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the value set
   * has used Others in standard or narrow value sets.  
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createNoOthersStandardNarrow() {
    ValidatorNoOthersStandardNarrow v = new ValidatorNoOthersStandardNarrow();
    v.setDataElement(getDataElementDefinition());
    return v;
  }
  

  /**
   * Creates and returns a <code>Validator</code> that checks whether the calculation of the data 
   * element (if any) is valid.  The returned <code>Validator</code> must have an input with 
   * property name {@link ValidatorInputOutputProperties#INPUT_DATA_ELEMENT} that takes the 
   * data element.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createCalculationValidator() {
    ValidatorFormDefinitionCalculation v = new ValidatorFormDefinitionCalculation();
    v.setDet(getDet());
    v.setDataElement(getDataElementDefinition());
    return v;
  } 
  
  
  private DataFormDefinitionDataElement getDataElementDefinition() {
    return _deDef;
  }

  /**
   * Sets the data element definition to be validated.
   * 
   * @param  definition  the <code>DataFormDefinitionDataElement</code>
   */
  public void setDataElementDefinition(DataFormDefinitionDataElement definition) {
    _deDef = definition;
  }

  private DataElementTaxonomy getDet() {
    return _det;
  }

  /**
   * Sets the DET associated with the form definition that is associated with the
   * data element to be validated. 
   * 
   * @param  det  the DET
   */
  public void setDet(DataElementTaxonomy det) { 
    _det = det;
  }

  private boolean isCheckDataElementInDet() {
    return _checkDataElementInDet;
  }

  private boolean isCheckNarrowValueSet() {
    return _checkNarrowValueSet;
  }

  private boolean isCheckStandardValueSet() {
    return _checkStandardValueSet;
  }
  
  private boolean isCheckStandardIfNarrowValueSet() {
    return _checkStandardIfNarrowValueSet;
  }
  
  private boolean isCheckNoValueSetWithNonCV() {
    return _checkNoValueSetWithNonCV;
  }


  /**
   * Sets whether it should be validated that the data element definition refers to a data element
   * that is present in the DET. 
   * 
   * @param  check  <code>true</code> if the check of whether the data element is present in the
   *                DET should be validated; <code>false</code> otherwise. 
   */
  public void setCheckDataElementInDet(boolean check) {
    _checkDataElementInDet = check;
  }

  /**
   * Sets whether it should be validated that the narrow value set is a subset of the standard
   * value set. 
   * 
   * @param  check  <code>true</code> if the narrow value set check should be performed;
   *                <code>false</code> otherwise. 
   */
  public void setCheckNarrowValueSet(boolean check) {
    _checkNarrowValueSet = check;
  }

  /**
   * Sets whether it should be validated that the standard value set is a subset of the broad
   * value set. 
   * 
   * @param  check  <code>true</code> if the standard value set check should be performed;
   *                <code>false</code> otherwise. 
   */
  public void setCheckStandardValueSet(boolean check) {
    _checkStandardValueSet = check;
  }

  /**
   * Sets whether it should be validated that a standard value set is specified if a narrow
   * value set is specified.
   * 
   * @param  check  <code>true</code> if the standard value set present check should be performed;
   *                <code>false</code> otherwise. 
   */
  public void setCheckStandardIfNarrowValueSet(boolean check) {
    _checkStandardIfNarrowValueSet = check;
  }

  /**
   * Sets whether it should be validated that non-CV fields cannot have value sets
   * 
   * @param  check  <code>true</code> non-CV fields cannot have value sets check should be performed;
   *                <code>false</code> otherwise. 
   */
  public void setCheckNoValueSetWithNonCV(boolean check) {
    _checkNoValueSetWithNonCV = check;
  }  
  
  public void setCheckValueSetNoOthersNarrowStandard (boolean checkValueSetNoOthersNarrowStandard) {
    _checkValueSetNoOthersNarrowStandard = checkValueSetNoOthersNarrowStandard;
  }
   
  private boolean isCheckValueSetNoOthersNarrowStandard () {
    return _checkValueSetNoOthersNarrowStandard;
  }
  
  /**
   * @return Returns the checkCalculation.
   */
  public boolean isCheckCalculation() {
    return _checkCalculation;
  }
  /**
   * @param checkCalculation The checkCalculation to set.
   */
  public void setCheckCalculation(boolean checkCalculation) {
    _checkCalculation = checkCalculation;
  }
  
  
  private DataFormDefinitionCategory getFirstPageCategory() {
    DataFormDefinition form = getDataElementDefinition().getDataForm();
    DataFormDefinitionElement[] elements = form.getDataFormElements().getDataFormElements();
    for (int i = 0; i < elements.length; i++) {
      DataFormDefinitionElement formElement = elements[i];
      if (formElement.isCategory()) {
        DataFormDefinitionCategory category = formElement.getDataCategory();
        if (category.isPage()) {
          return getLowestLevelPageCategory(category);
        }
      }
    }
    return null;
  }

  private DataFormDefinitionCategory getLowestLevelPageCategory(DataFormDefinitionCategory category) {
    DataFormDefinitionCategory lowestLevelCategory = category;
    DataFormDefinitionElement[] elements = category.getDataFormElements();
    for (int i = 0; i < elements.length; i++) {
      DataFormDefinitionElement formElementDef = elements[i];
      if (formElementDef.isCategory()) {
        DataFormDefinitionCategory subCategory = formElementDef.getDataCategory();
        if (subCategory.isPage()) {
          return getLowestLevelPageCategory(subCategory);
        }
      }
    }
    return lowestLevelCategory;
  }
}
