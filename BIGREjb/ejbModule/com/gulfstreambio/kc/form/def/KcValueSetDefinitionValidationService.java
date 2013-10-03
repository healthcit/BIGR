package com.gulfstreambio.kc.form.def;

import java.util.Iterator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorCollection;
import com.ardais.bigr.validation.ValidatorCollectionNonProceeding;
import com.ardais.bigr.validation.ValidatorCollectionProceeding;
import com.ardais.bigr.validation.ValidatorFactory;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet;

/**
 * KnowledgeCapture's value set definition validation service.  This service performs
 * KnowledgeCapture-specific validation related to the details of value sets.
 * To use this service, set the value set to be validated and the
 * id of the associated DET, call one or more setCheck* 
 * methods to indicate which validations are to be performed, 
 * and then call the validate method to perform validation. 
 */
class KcValueSetDefinitionValidationService extends AbstractValidationService {

  private DataElementTaxonomy _det; 
  private DataFormDefinitionValueSet _vsDef;

  private boolean _checkValueSetCuis;
  private boolean _checkValueSetInDet;


  
  /**
   * Creates a new <code>KcValueSetDefinitionValidationService</code>.
   */
  public KcValueSetDefinitionValidationService() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {

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
    if (isCheckValueSetInDet()) {
      masterCollection = new ValidatorCollectionNonProceeding();
      masterCollection.addValidator(createValueSetDefinitionInDetValidator());
      collection = new ValidatorCollectionProceeding();
      masterCollection.addValidator(collection);
    }
    else {
      masterCollection = new ValidatorCollectionProceeding();
      collection = masterCollection;
    }    
    
    
     // build up the collection of validations to run...
    if (isCheckValueSetCuis()) {
      collection.addValidator(createValueSetCuis());
    }
    
     // now, perform all requested validations. 
    if (!collection.isEmpty()) {
      errors.addErrors(masterCollection.validate());
    }

    // If no validations were requested, then throw an exception. 
    else {
      throw new ApiException("KcValueSetDefinitionValidationService: No validation checks requested.");
    }

    return errors;
  }

  /**
   * Creates and returns a <code>Validator</code> that checks whether the value set is 
   * present in the DET.  The returned <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the CUI of the data element
   * and an input with property name {@link ValidatorInputOutputProperties#INPUT_DET} that
   * takes the DET to check against.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createValueSetDefinitionInDetValidator() {
    ValidatorDetContainsValueSet v = new ValidatorDetContainsValueSet();
    v.setDet(getDet());
    return v;
  }
  
  
  
  /**
   * Creates and returns a <code>Validator</code> that checks whether the value set
   * has duplicate display names.  The returned <code>Validator</code> must have an input with property name 
   * {@link ValidatorInputOutputProperties#INPUT_VALUE} that takes the CUI of the data element
   * and an input with property name {@link ValidatorInputOutputProperties#INPUT_DET} that
   * takes the DET to check against.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createValueSetCuis() {
    ValidatorValueSetCuis v = new ValidatorValueSetCuis();
    v.setValueSet(getValueSetDefinition());
    return v;
  }    
  
  private DataFormDefinitionValueSet getValueSetDefinition() {
    return _vsDef;
  }

  /**
   * Sets the data element definition to be validated.
   * 
   * @param  definition  the <code>DataFormDefinitionDataElement</code>
   */
  public void setValueSetDefinition(DataFormDefinitionValueSet definition) {
    _vsDef = definition;
  }
 

  /**
   * @return Returns the checkValueSetCuis.
   */
  public boolean isCheckValueSetCuis() {
    return _checkValueSetCuis;
  }
  /**
   * @param checkValueSetCuis The checkValueSetCuis to set.
   */
  public void setCheckValueSetCuis(boolean checkValueSetCuis) {
    _checkValueSetCuis = checkValueSetCuis;
  }
  
  private boolean isCheckValueSetInDet() {
    return _checkValueSetInDet;
  }  
  
  /**
   * @return Returns the det.
   */
  public DataElementTaxonomy getDet() {
    return _det;
  }
  /**
   * @param det The det to set.
   */
  public void setDet(DataElementTaxonomy det) {
    _det = det;
  }
}
