package com.gulfstreambio.kc.form;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.ValidatorCollectionNonProceeding;
import com.ardais.bigr.validation.ValidatorInput;
import com.ardais.bigr.validation.ValidatorOtherValue;
import com.ardais.bigr.validation.ValidatorOutput;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;

/**
 * KnowledgeCapture's element value validator.  This validator performs
 * KnowledgeCapture-specific validation related to a single data element value within a 
 * form instance.  
 */
public class ValidatorElementValue extends AbstractValidator {

  private ElementValue _elementValue;
  private DetElement _elementMetadata; 

  /**
   * Creates a new <code>ValidatorElementValue</code>.
   */
  public ValidatorElementValue() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    if (getElementValue() == null) {
      throw new ApiException("ValidatorElementValue.validate: a element value was not specified.");
    }
    
    if (getElementMetadata() == null) {
      throw new ApiException("ValidatorElementValue.validate: Element Metadata was not specified.");
    }    
    
    DetValueSet standardValues = 
      DetService.SINGLETON.getDataElementTaxonomy().getSystemStandardValues();
    BtxActionErrors errors = getActionErrors();

    //setup the validator collection 
    ValidatorCollectionNonProceeding v = new ValidatorCollectionNonProceeding();
    ElementValidatorContext context = new ElementValidatorContext();
    context.setPropertyDisplayName(getPropertyDisplayName());
    context.setElementValue(getElementValue());
    context.setElementMetadata(getElementMetadata());
    v.setValidatorContext(context);
   
    if (getElementMetadata().isDatatypeCv()) {
      //currentValidator.setPropertyDisplayName(getElementMetadata().getDescription());
      //validate value conforms to the format of its datatype and can be converted
      if (!standardValues.containsValue(getElementValue().getValue())) {
        v.addValidator(new ValidatorElementValueInBroadValueSet(), new ValidatorInput[] {
                              new ValidatorInput("value", "elementValue.value"),
                              new ValidatorInput("broadValueSet", "elementMetadata.broadValueSet"),
                              new ValidatorInput("propertyDisplayName", "propertyDisplayName")                                  
                            });
      }      
      
      //validate use of "other value"
      if (!ApiFunctions.isEmpty(getElementValue().getValue())) {
        v.addValidator(new ValidatorOtherValue(), new ValidatorInput[] {
                              new ValidatorInput("cui", "elementValue.value"),
                              new ValidatorInput("otherCui", "elementMetadata.otherValueCui"),
                              new ValidatorInput("otherValue", "elementValue.valueOther"),
                              new ValidatorInput("propertyDisplayName", "propertyDisplayName")                                
                     });
      }
    }
    else {
      //validate value conforms to the format of its datatype and can be converted 
      v.addValidator(new ValidatorTypeSafe(), new ValidatorInput[] {
                            new ValidatorInput("value", "elementValue.value"),
                            new ValidatorInput("datatype", "elementMetadata.datatype"),
                            new ValidatorInput("propertyDisplayName", "propertyDisplayName")                            
                          },
                          new ValidatorOutput("typeSafeValue", "typeSafeValue"));
  
      if ((getElementMetadata().getMaxValue() != null) || (getElementMetadata().getMinValue() != null) ) {
        //validate range- min/max (inclusive/exclusive)            
        v.addValidator(new ValidatorRange(), new ValidatorInput [] {
                                new ValidatorInput("value", "typeSafeValue"),
                                //context has derived properties maxValue & minValue
                                //NB: these are not the same as elementMetadata.maxValue and
                                //elementMetadata.minvalue
                                new ValidatorInput("maxValue", "maxValue"),
                                new ValidatorInput("minValue", "minValue"),                                  
                                new ValidatorInput("maxInclusive", "elementMetadata.maxInclusive"),
                                new ValidatorInput("minInclusive", "elementMetadata.minInclusive"),
                                new ValidatorInput("propertyDisplayName", "propertyDisplayName")                              
                           });
      }
    }
        
    // Perform validations now 
    errors.addErrors(v.validate());    
    return errors;
  }
  
  public DetElement getElementMetadata() {
    return _elementMetadata;
  }
  public void setElementMetadata(DetElement elementMetadata) {
    _elementMetadata = elementMetadata;
  }
  public ElementValue getElementValue() {
    return _elementValue;
  }
  public void setElementValue(ElementValue elementValue) {
    _elementValue = elementValue;
  }
}
