package com.gulfstreambio.kc.form;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;

/**
 * Validates an Ade of a data elements value 
 */
public class ValidatorAde extends AbstractValidator {
  
  /**
   * The key of the error that is returned from this validator if Ade 
   * can't be attached to a given Data Element value. 
   */
  public final static String ERROR_KEY1 = "kc.error.forminst.adenotlegal";
  
  private DataElementValue _dataElementValue; 
  private DetDataElement _dataElementMetadata;
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorAde v1 = (ValidatorAde) v;
      if (errorKey.equals(ValidatorAde.ERROR_KEY1)) {
        v1.addErrorMessage(ValidatorAde.ERROR_KEY1, getDataElementValue().getValue());
      }
      return true;
    }
  }
  
  /**
   * Creates a new <code>ValidatorAde</code> validator.
   */
  public ValidatorAde() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);    
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    if (_dataElementValue == null) {
      throw new ApiException("ValidatorAde.validate: a data element value was not specified.");
    }
    if (_dataElementMetadata == null) {
      throw new ApiException("ValidatorAde.validate: a data element metadata was not specified.");
    }
    
    BtxActionErrors errors = getActionErrors();
    Ade ade = getDataElementValue().getAde();
    if (ade != null) {
      if (!getDataElementMetadata().isHasAde()) {
        notifyValidatorErrorListener(ERROR_KEY1);
      }      
      else {//okay to have ADE
        //if DataElement was of CV type then we need to verify that ADE use was legal per ATV list
        if (getDataElementMetadata().isDatatypeCv()){
          //if the data element's input value is in its non-ADE trigger value set,
          //then there must not be an associated ADE.
          DetValueSet nonAtvValues = getDataElementMetadata().getNonAdeTriggerValueSet();
          if (nonAtvValues != null){
            DetValueSetValue elem = nonAtvValues.getValue(this.getDataElementValue().getValue());
            // Value is in nonATVs, so if any ADE elements have a non-empty value, then
            // its an error.
            if (elem != null) {
              boolean errorFound = false;
              AdeElement[] elements = ade.getAdeElements();
              for (int i = 0; (i < elements.length) && !errorFound; i++) {
                AdeElement adeElement = elements[i];
                ElementValue[] adeElementValues = adeElement.getElementValues();
                for (int j = 0; (j < adeElementValues.length) && !errorFound; j++) {
                  ElementValue adeElementValue = adeElementValues[j];
                  if (!ApiFunctions.isEmpty(adeElementValue.getValue()) 
                      || !ApiFunctions.isEmpty(adeElementValue.getValueOther())) {
                    errorFound = true;
                    notifyValidatorErrorListener(ERROR_KEY1);
                  }
                }                
              }
            } 
          }
        }
        ValidatorAdeElements adeElementsValidator = new ValidatorAdeElements();
        adeElementsValidator.setAdeElements(ade.getAdeElements());
        adeElementsValidator.setAdeMetadata(getDataElementMetadata().getAde());
        errors.addErrors(adeElementsValidator.validate());
      }
    }   
    return errors;
  }
  
  public DetDataElement getDataElementMetadata() {
    return _dataElementMetadata;
  }
  public void setDataElementMetadata(DetDataElement dataElementMetadata) {
    _dataElementMetadata = dataElementMetadata;
  }
  public DataElementValue getDataElementValue() {
    return _dataElementValue;
  }
  public void setDataElementValue(DataElementValue elementValue) {
    _dataElementValue = elementValue;
  }
}
