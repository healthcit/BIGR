package com.gulfstreambio.kc.web.support;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.DataElementValue;
import com.gulfstreambio.kc.form.Element;
import com.gulfstreambio.kc.form.ElementValue;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet;
import com.gulfstreambio.kc.form.def.data.calculation.Calculation;

public class DataFormDataElementContext extends DataFormElementContextBase implements DataFormElementContext {

  private DataFormDefinitionDataElement _formDefinitionDataElement;
  private DataElement _dataElement;
  private DetDataElement _detDataElement;

  DataFormDataElementContext(DataFormDefinitionDataElement definition, DataElement instance) {
    super();
    _formDefinitionDataElement = definition;
    _dataElement = instance;
  }

  public DataElement getDataElement() {
    return _dataElement;
  }

  DataFormDefinitionDataElement getDataFormDefinitionDataElement() {
    return _formDefinitionDataElement;
  }
  
  public DetDataElement getDetDataElement() {
    if (_detDataElement == null) {
      DataFormDefinitionDataElement def = getDataFormDefinitionDataElement();
      if (def != null) {
        _detDataElement = DetService.SINGLETON.getDataElementTaxonomy().getDataElement(def.getCui());
      }
      else {
        DataElement instance = getDataElement();
        if (instance != null) {
          _detDataElement = 
            DetService.SINGLETON.getDataElementTaxonomy().getDataElement(instance.getCuiOrSystemName());
        }
      }      
    }
    return _detDataElement;
  }

  protected DetElement getDetElement() {
    return getDetDataElement();
  }
  
  protected Element getElement() {
    return getDataElement();
  }
  
  /**
   * Returns the display name of the data element. 
   * 
   * @return  The display name, of the empty string if the display name could not be determined.
   */
  public String getLabel() {
    String displayName = "";
    DataFormDefinitionDataElement def = getDataFormDefinitionDataElement();
    DetDataElement metadata = getDetDataElement();

    // Get the display name from the data element definition if it was specified there.
    if ((def != null) && !ApiFunctions.isEmpty(def.getDisplayName())) {
      displayName = def.getDisplayName();
    }
    
    // Otherwise get the display name from the data element metadata, which is its description
    // in the DET.
    else if (metadata != null) {
      displayName = metadata.getDescription();
    }
    
    return displayName;
  }

  public boolean isRequired() {
    DataFormDefinitionDataElement def = getDataFormDefinitionDataElement();
    return (def == null) ? false : def.isRequired();
  } 
  
  public boolean isHasAde() {
    DetDataElement metadata = getDetDataElement();
    return (metadata == null) ? false : metadata.isHasAde();
  } 
  
  public DetValueSet getValueSetNonAtv() {
    DetDataElement metadata = getDetDataElement();
    return (metadata == null) ? null : metadata.getNonAdeTriggerValueSet();    
  }
  
  public DetValueSet getValueSetBroad() {
    DetValueSet broadValueSet = super.getValueSetBroad();
    DetValueSet standardValueSet = getValueSetStandard();
    if ((broadValueSet != null) && (standardValueSet != null)) {
      DetValueSetValue[] standardValues = standardValueSet.getValues();
      for (int i = 0; i < standardValues.length; i++) {
        DetValueSetValue standardValue = standardValues[i];
        DetValueSetValue broadValue = broadValueSet.getValue(standardValue.getCui());
        broadValue.setDescription(standardValue.getDescription());
      }
    }
    return broadValueSet;
  }

  public DetValueSet getValueSetStandard() {
    DataFormDefinitionDataElement def = getDataFormDefinitionDataElement();
    if (def == null) {
      return null;
    }
    DataFormDefinitionValueSet valueSetDef = def.getStandardValueSet();
    if (valueSetDef == null) {
      return null;
    }
    else {
      return new DetValueSet(valueSetDef);
    }
  }
  
  public DetValueSet getValueSetNarrow() {
    DataFormDefinitionDataElement def = getDataFormDefinitionDataElement();
    if (def == null) {
      return null;
    }
    DataFormDefinitionValueSet valueSetDef = def.getNarrowValueSet();
    if (valueSetDef == null) {
      return null;
    }
    else {
      return new DetValueSet(valueSetDef);
    }
  }

  public String getValueDescription(int index) {
    DataElement instance = getDataElement();
    if (instance == null) {
      return "";
    }
    else {
      DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
      DetDataElement metadata = getDetDataElement();
      String otherCui = metadata.getOtherValueCui();
      DetValueSet standardValues = det.getSystemStandardValues();
      DetValueSet broadValueSet = getValueSetBroad();

      DataElementValue valueObj = (DataElementValue) instance.getElementValue(index);
      String value = valueObj.getValue();

      // If this is a system standard value, then translate its CUI to its description.
      if (standardValues.containsValue(value)) {
        value = det.getCuiDescription(value);          
      }
      
      // If this is a CV value, then get its description and return it.
      else if (metadata.isDatatypeCv()) {
        if (!ApiFunctions.isEmpty(value)) {
          if (value.equals(otherCui)) {
            value = valueObj.getValueOther();
          }
          else {
            value = broadValueSet.getValue(value).getDescription();
          }
        }
      }
      return (value == null) ? "" : value; 
    }    
  }

  /**
   * Returns the description of this data element's value(s), suitable for display as
   * a summary of the element's value(s).  CUI values will be translated to their description, and
   * multiple values will be returned as a comma-separated list, with other values appended.
   * 
   * @return  The description.
   */
  public String getValueDescription() {
    DataElement instance = getDataElement();
    if (instance == null) {
      return "";
    }
    else {
      DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
      DetDataElement metadata = getDetDataElement();
      StringBuffer buf = new StringBuffer(128);      
      boolean first = true;
      String otherCui = metadata.getOtherValueCui();
      DetValueSet standardValues = det.getSystemStandardValues();
      DetValueSet broadValueSet = getValueSetBroad();

      // Iterate over all of this data element's values, add the description of each to the overall 
      // description, separated by commas. 
      ElementValue[] values = instance.getElementValues();
      for (int i = 0; i < values.length; i++) {
        ElementValue valueObj = values[i];
        String value = valueObj.getValue();

        // If this is a system standard value, then translate its CUI to its description.
        if (standardValues.containsValue(value)) {
          value = det.getCuiDescription(value);          
        }

        // If this is a CV element, then translate its CUI to its description, but only if this
        // is not the other CUI, since we'll add the other texts below.
        else if (metadata.isDatatypeCv()
          && !ApiFunctions.isEmpty(value)
          && !value.equals(otherCui)) {
          value = broadValueSet.getValue(value).getDescription();
        }

        if (!ApiFunctions.isEmpty(value) && !value.equals(otherCui)) {
          if (!first) {
            buf.append(", ");
          }
          buf.append(value);
          first = false;
        }

        value = valueObj.getValueOther();
        if (!ApiFunctions.isEmpty(value)) {
          if (!first) {
            buf.append(", ");
          }
          buf.append(value);
          first = false;
        }
      }
      return buf.toString();
    }
  }
  
  public String getValueNote() {
    DataElement instance = getDataElement();
    String value = null;
    if (instance != null) {
      value = instance.getValueNote();
    }
    return value;
  }

  public boolean isValueAtvValue() {
    String[] values = getValues();
    if ((values == null) || (values.length == 0)) {
      return false;
    }
    if (isValueStandardValue()) {
      return false;
    }
    return !checkAllInValueSet(values, getValueSetNonAtv());    
  }
  
  public boolean isCalculated() {
    return getCalculation() != null;
  }
  
  public Calculation getCalculation() {
    return getDataFormDefinitionDataElement().getCalculation();
  }
  
}
