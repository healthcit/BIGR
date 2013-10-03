
package com.gulfstreambio.kc.form.def;

import java.util.Iterator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorCollection;
import com.ardais.bigr.validation.ValidatorCollectionNonProceeding;
import com.ardais.bigr.validation.ValidatorCollectionProceeding;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionElement;

/**
 * @author sthomashow
 *
 */
public class KCQueryFormDataElementValidationService extends AbstractValidationService {

  private QueryFormDefinitionDataElement _deDef;
  private DataElementTaxonomy _det; 

  private boolean _checkDataElementInDet;
  private boolean _checkSupportedDatatypes;
  /**
   * Creates a new <code>KcQueryFormDataElementValidationService</code>.
   */
  public KCQueryFormDataElementValidationService() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    QueryFormDefinitionDataElement deDef = getDataElementDefinition();
    if (deDef == null) {
      throw new ApiException("KcQueryFormDataElementValidationService.validate: a data element definition was not specified.");
    }
    DataElementTaxonomy det = getDet();
    if (det == null) {
      throw new ApiException("KcQueryFormDataElementValidationService.validate: a DET was not specified.");
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

    if (isCheckSupportedDatatypes()) {
      collection.addValidator(createSupportedDatatypes());
    }
    

    // Perform all requested validations. 
    if (!collection.isEmpty()) {
      errors.addErrors(masterCollection.validate());
    }

    // If no validations were requested, then throw an exception. 
    else {
      throw new ApiException("KcQueryFormDataElementValidationService: No validation checks requested.");
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
   * Creates and returns a <code>Validator</code> that checks whether the data element has
   * a datatype that is supported for query forms.  The returned <code>Validator</code> 
   * must have an input with property name {@link ValidatorInputOutputProperties#INPUT_DATA_ELEMENT}
   * that takes the data element.
   * 
   * @return  The <code>Validator</code>.
   */
  protected Validator createSupportedDatatypes() {
    ValidatorQueryFormSupportedDataElements v = new ValidatorQueryFormSupportedDataElements();
    v.setCui(getDataElementDefinition().getCui());
    v.setDet(getDet());
    return v;    
  }
  
  
  private QueryFormDefinitionDataElement getDataElementDefinition() {
    return _deDef;
  }

  /**
   * Sets the data element definition to be validated.
   * 
   * @param  definition  the <code>QueryFormDefinitionDataElement</code>
   */
  public void setDataElementDefinition(QueryFormDefinitionDataElement definition) {
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
   * @return Returns the checkSupportedDatatypes.
   */
  public boolean isCheckSupportedDatatypes() {
    return _checkSupportedDatatypes;
  }
  /**
   * @param checkSupportedDatatypes The checkSupportedDatatypes to set.
   */
  public void setCheckSupportedDatatypes(boolean checkSupportedDatatypes) {
    _checkSupportedDatatypes = checkSupportedDatatypes;
  }
}
