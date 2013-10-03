package com.gulfstreambio.kc.form;

import java.util.Iterator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.ValidatorCollectionNonProceeding;
import com.ardais.bigr.validation.ValidatorCollectionProceeding;
import com.ardais.bigr.validation.ValidatorInput;
import com.ardais.bigr.validation.ValidatorValueInCollection;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetAde;
import com.gulfstreambio.kc.det.DetService;

/**
 * KnowledgeCapture's ade element validator.  This validator performs
 * KnowledgeCapture-specific validation related to a single ade element within a 
 * form instance, including its value sets.  To use this service, set the data element 
 * to be validated and the DataFormDefinition, and then call the validate method 
 * to perform validation. 
 */
public class ValidatorAdeElement extends AbstractValidationService {

  private AdeElement _adeElement;
  private DetAde _adeMetadata;
  
  /**
   * Creates a new <code>ValidatorAdeElement</code>.
   */
  public ValidatorAdeElement() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    AdeElement deDef = getAdeElement();
    if (deDef == null) {
      throw new ApiException("ValidatorAdeElement.validate: a data element definition was not specified.");
    }
    
    BtxActionErrors errors = getActionErrors();
    ValidatorCollectionProceeding validator = new ValidatorCollectionProceeding(); 
    
    //validate that the adeElement is allowed in this Ade
    ValidatorAdeElementForAde adeElemAllowedForAde = new ValidatorAdeElementForAde();
    validator.addValidator(adeElemAllowedForAde);
    adeElemAllowedForAde.setValue(getAdeElement().getCuiOrSystemName());
    adeElemAllowedForAde.setValueSet(getAdeMetadata().getAdeElements());
    adeElemAllowedForAde.setPropertyDisplayName("Ade Element '" +  
        getAdeElement().getCuiOrSystemName() + "' can't belong to this Ade based on the DET");
    
    DetAdeElement adeElemMetadata = 
      DetService.SINGLETON.getDataElementTaxonomy().getAdeElement(getAdeElement().getCuiOrSystemName());
    
    ValidatorElementValues v1 = new ValidatorElementValues();
    v1.setPropertyDisplayName(adeElemMetadata.getDescription());
    v1.setElement(getAdeElement());
    v1.setElementMetadata(adeElemMetadata);
    validator.addValidator(v1);
    
    //for each value of the DataElem we need to check that value provided is usable
    ElementValue[] adeVals = getAdeElement().getElementValues(); 
    for (int i = 0; i < adeVals.length; i++) {
      ElementValue adeVal = adeVals[i];
      ValidatorCollectionNonProceeding v = new ValidatorCollectionNonProceeding();
      ElementValidatorContext context = new ElementValidatorContext();
      context.setAdeElement(getAdeElement());
      //ADE description comes from DET, can be overiden from the form def (unlike Data Elems)
      context.setPropertyDisplayName(adeElemMetadata.getDescription());
      context.setElementValue(adeVal);
      context.setElementMetadata(adeElemMetadata);
      v.setValidatorContext(context);
        
      //validate value conforms to the format of its datatype and can be converted 
      v.addValidator(new ValidatorElementValue(), new ValidatorInput[] {
                            new ValidatorInput("elementValue", "elementValue"),
                            new ValidatorInput("elementMetadata", "elementMetadata"),
                            new ValidatorInput("propertyDisplayName", "propertyDisplayName")
                          });
      validator.addValidator(v);
    }    // Perform all requested validations. 
    errors.addErrors(validator.validate());
    return errors;
  }

  public AdeElement getAdeElement() {
    return _adeElement;
  }
  public void setAdeElement(AdeElement adeElem) {
    _adeElement = adeElem;
  }
  public DetAde getAdeMetadata() {
    return _adeMetadata;
  }
  public void setAdeMetadata(DetAde adeMetadata) {
    _adeMetadata = adeMetadata;
  }
}
