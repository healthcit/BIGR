package com.gulfstreambio.kc.form;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidationService;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorCollection;
import com.ardais.bigr.validation.ValidatorCollectionProceeding;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.ardais.bigr.validation.ValidatorInput;
import com.ardais.bigr.validation.ValidatorInputOutputProperties;
import com.ardais.bigr.validation.ValidatorSeeNote;
import com.ardais.bigr.validation.ValidatorStringLength;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.util.KcUtils;

/**
 * KnowledgeCapture's data element validator.  This validator performs
 * KnowledgeCapture-specific validation related to a single data element within a 
 * form instance, including its value sets.  To use this service, set the data element 
 * to be validated and the DataFormDefinition, and then call the validate method 
 * to perform validation. 
 */
public class ValidatorDataElement extends AbstractValidationService {

  private DataElement _dataElem;
  private DataFormDefinition _formDefinition; 

  /**
   * Creates a new <code>ValidatorDataElement</code>.
   */
  public ValidatorDataElement() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
        
    DataElement deDef = getDataElement();
    if (deDef == null) {
      throw new ApiException("ValidatorDataElement.validate: a data element definition was not specified.");
    }
    DataFormDefinition formDefinition = getFormDefinition();
    if (formDefinition == null) {
      throw new ApiException("ValidatorDataElement.validate: a form definition was not specified.");
    }    
    
    BtxActionErrors errors = getActionErrors();
    ValidatorCollection collection = new ValidatorCollectionProceeding();

    collection.addValidator(createDataElementInFormDefinitionValidator());
    collection.addValidator(createDataElementValuesValidator());
    
    
    // Perform all requested validations. 
    errors.addErrors(collection.validate());
    return errors;
  }

  protected Validator createDataElementInFormDefinitionValidator() {
    ValidatorFormDefinitionContainsDataElement v = new ValidatorFormDefinitionContainsDataElement();
    String elemCui = 
      DetService.SINGLETON.getDataElementTaxonomy().getDataElement(getDataElement().getCuiOrSystemName()).getCui();
    v.setValue(elemCui);
    v.setFormDefinition(getFormDefinition());
    return v;
  }

  protected Validator createDataElementValuesValidator() {
    DetValueSet standardValues = 
      DetService.SINGLETON.getDataElementTaxonomy().getSystemStandardValues();
    ValidatorCollectionProceeding validator = new ValidatorCollectionProceeding(); 
    
    DetDataElement deMetadata = 
      DetService.SINGLETON.getDataElementTaxonomy().getDataElement(getDataElement().getCuiOrSystemName());

    ValidatorElementValues multiValuedValidator = new ValidatorElementValues();
    //extract the PropertyDisplayName from the Form Def or DET
    multiValuedValidator.setPropertyDisplayName(
        KcUtils.getDescription(getDataElement(), getFormDefinition()));
    multiValuedValidator.setElement(getDataElement());
    multiValuedValidator.setElementMetadata(deMetadata);
    validator.addValidator(multiValuedValidator);
    
    //for each value of the DataElem we need to check that value provided is usable
    DataElementValue[] devs = getDataElement().getDataElementValues(); 
    for (int i = 0; i < devs.length; i++) {
      DataElementValue dev = devs[i];
      ValidatorCollectionProceeding v = new ValidatorCollectionProceeding();
      ElementValidatorContext context = new ElementValidatorContext();
      //extract the PropertyDisplayName from the Form Def or DET
      context.setPropertyDisplayName(KcUtils.getDescription(getDataElement(), getFormDefinition()));      
      context.setDataElement(getDataElement());
      context.setElementValue(dev);
      context.setElementMetadata(deMetadata);
      v.setValidatorContext(context);
        
      //validate value conforms to the format of its datatype and can be converted 
      v.addValidator(new ValidatorElementValue(), new ValidatorInput[] {
                            new ValidatorInput("elementValue", "elementValue"),
                            new ValidatorInput("elementMetadata", "elementMetadata"),
                            new ValidatorInput("propertyDisplayName", "propertyDisplayName")
                          });
          
      // Validate note length.
      ValidatorStringLength vNote = new ValidatorStringLength();
      vNote.setMaxLength(4000);
      vNote.addValidatorErrorListener(new ValidatorErrorListener() {
        public boolean validatorError(Validator v, String errorKey) {
          ValidatorStringLength v1 = (ValidatorStringLength) v;
          v1.addErrorMessage("errors.cirNoteLength", "4000");
          return true;
        }
      }, ValidatorStringLength.ERROR_KEY_TOO_LONG);
      v.addValidator(vNote, new ValidatorInput[] {
          new ValidatorInput("value", "dataElement.valueNote"),
          new ValidatorInput("propertyDisplayName", "propertyDisplayName")
      });

      
      //validate use of "See Note"
      if (standardValues.containsValue(dev.getValue())) {
        v.addValidator(new ValidatorSeeNote(), new ValidatorInput[] {
            new ValidatorInput("cui", "elementValue.value"),
            new ValidatorInput("seeNoteCui", "seeNoteCui"),
            new ValidatorInput("valueNote", "dataElement.valueNote"),
            new ValidatorInput("propertyDisplayName", "propertyDisplayName")
        });
      }
      
      //validate use of ADE 
      if(dev.getAde() != null) {
        v.addValidator(new ValidatorAde(), new ValidatorInput[] {
            new ValidatorInput("dataElementValue", "elementValue"),
            new ValidatorInput("dataElementMetadata", "elementMetadata")
        });
      }

      validator.addValidator(v);
    }
    return validator;
  }  
  
  private DataElement getDataElement() {
    return _dataElem;
  }

  /**
   * Sets the data element definition to be validated.
   * 
   * @param  definition  the <code>DataElement</code>
   */
  public void setDataElement(DataElement dElem) {
    _dataElem = dElem;
  }

  private DataFormDefinition getFormDefinition() {
    return _formDefinition;
  }

  /**
   * Sets the DataFormDefinition associated with the form definition that is associated with the
   * data element to be validated. 
   * 
   * @param  formDefinition  the DataFormDefinition
   */
  public void setFormDefinition(DataFormDefinition formDefinition) { 
    _formDefinition = formDefinition;
  }
}
