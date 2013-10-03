package com.gulfstreambio.kc.form;

import org.apache.commons.beanutils.ConversionException;

import com.ardais.bigr.validation.ValidatorContext;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetElement;

/**
 * A <code>ValidatorContext</code> for use with the validators that perform KC-specific 
 * validation on form instances.
 */
public class ElementValidatorContext implements ValidatorContext {

  //Input properties
  private DataElement _dataElement;
  private AdeElement _adeElement;
  private String _propertyDisplayName;
  
  private DetElement _elementMetadata;
  
  private ElementValue _elementValue;
  
  
  //output properties
  private Comparable _typeSafeValue;
  //following are derived properties (derived from DetElement) see getters
  //private Comparable maxValue;
  //private Comparable minValue;
  
  public ElementValidatorContext() {
    super();
  }
  public Comparable getTypeSafeValue() {
    return _typeSafeValue;
  }
  public void setTypeSafeValue(Comparable value) {
    _typeSafeValue = value;
  }
  public DetElement getElementMetadata() {
    return _elementMetadata;
  }
  public void setElementMetadata(DetElement elementMetadata) {
    _elementMetadata = elementMetadata;
  }
 
  //derived property
  public Comparable getMaxValue() {
    try {
      return KcFormInstanceValidationService.CastToComparableObj(_elementMetadata.getMaxValue(),
                                          _elementMetadata.getDatatype());
    }
    catch (ConversionException e) {//No support for older constants like "Age" etc.
      return null;
    }
  }

//derived property
  public Comparable getMinValue() {
      try {
        return KcFormInstanceValidationService.CastToComparableObj(_elementMetadata.getMinValue(), 
                                            _elementMetadata.getDatatype());
      }
      catch (ConversionException e) {//No support for older constants like "Age" etc.        
        return null;
      }
  }

  //fixed property 
  public String getSeeNoteCui(){
    return DataElementTaxonomy.SYSTEM_STANDARD_VALUE_SEE_NOTE;
  }

  //fixed property 
  public String getCui(){
    return DataElementTaxonomy.SYSTEM_STANDARD_VALUE_SEE_NOTE;
  }
  

  public DataElement getDataElement() {
    return _dataElement;
  }
  public void setDataElement(DataElement dataElement) {
    _dataElement = dataElement;
  }

  public ElementValue getElementValue() {
    return _elementValue;
  }
  public void setElementValue(ElementValue elementValue) {
    _elementValue = elementValue;
  }
  public AdeElement getAdeElement() {
    return _adeElement;
  }
  public void setAdeElement(AdeElement adeElement) {
    _adeElement = adeElement;
  }
  public String getPropertyDisplayName() {
    return _propertyDisplayName;
  }
  public void setPropertyDisplayName(String description) {
    _propertyDisplayName = description;
  }
}
