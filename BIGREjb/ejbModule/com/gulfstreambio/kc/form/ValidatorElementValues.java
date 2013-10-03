package com.gulfstreambio.kc.form;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;

/**
 * Validates that a data element is mutivalued only if allowed by DET. 
 * <p>
 * This validator may return one error as follows, with insertion strings listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The data element {0} should not have multiple values.
 *   <ol>
 *   <li>The data element, as set by {@link #setDataElement(DataElement) setDataElement}.
 *   </li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorElementValues extends AbstractValidator {

  /**
   * The key of the error that may be returned from this validator. 
   */
  public final static String ERROR_KEY1 = "kc.error.forminst.elementCannotBeMutiValued";

  private Element _element;
  private DetElement _elementMetadata;

  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorElementValues v1 = (ValidatorElementValues) v;
      v1.addErrorMessage(ValidatorElementValues.ERROR_KEY1, 
                          v1.getElement().getCuiOrSystemName());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorElementValues</code> validator.
   */
  public ValidatorElementValues() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    if (getElement() == null) {
      throw new ApiException("ValidatorElementValues: data element was not specified");
    }
    
    //Get iterator only to examine if second value exists 
    ElementValue[] values = getElement().getElementValues();
    if (values.length > 1) {
      
      // If the element is not multivalued and there is more than one value, that is an error.
      if (!getElementMetadata().isMultivalued()) {
        notifyValidatorErrorListener(ERROR_KEY1);
      }
      
      // If the element is multivalued, make sure that if one of the values is the system standard
      // value or no value, then there are not any other values.
      else {
        DetValueSet standardValues = 
          DetService.SINGLETON.getDataElementTaxonomy().getSystemStandardValues();
        for (int i = 0; i < values.length; i++) {
          ElementValue value = values[i];
          if (!ApiFunctions.isEmpty(value.getValue())) {
            if (standardValues.containsValue(value.getValue()) || 
                value.getValue().equals(getElementMetadata().getNoValueCui())) {
              notifyValidatorErrorListener(ERROR_KEY1); 
            }
          }
          //validate correct use of otherValue
          ValidatorMultiValuedElementOther v = new ValidatorMultiValuedElementOther();
          v.setPropertyDisplayName(getPropertyDisplayName());
          v.setCui(value.getValue());
          v.setOtherCui(getElementMetadata().getOtherValueCui());
          v.setOtherValue(value.getValueOther());
          getActionErrors().addErrors(v.validate());                
        }
      }       
    }
    
    return getActionErrors();
  }
  
  public Element getElement() {
    return _element;
  }

  public void setElement(Element element) {
    _element = element;
  }
  public DetElement getElementMetadata() {
    return _elementMetadata;
  }
  public void setElementMetadata(DetElement elementMetadata) {
    _elementMetadata = elementMetadata;
  }
}
