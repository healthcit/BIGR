package com.gulfstreambio.kc.web.support;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.form.AdeElement;
import com.gulfstreambio.kc.form.DataElementValue;
import com.gulfstreambio.kc.form.Element;
import com.gulfstreambio.kc.form.ElementValue;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionAdeElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;

public class DataFormAdeElementContext extends DataFormElementContextBase implements DataFormElementContext {

  private AdeElement _adeElement;
  private DetAdeElement _detAdeElement;
  private DataFormDataElementContext _dataElementContext;  

  DataFormAdeElementContext(DataFormDataElementContext context, DetAdeElement detAdeElement, AdeElement instance) {
    super();
    _adeElement = instance;
    _dataElementContext = context;
    _detAdeElement = detAdeElement;
  }

  public AdeElement getAdeElement() {
    return _adeElement;
  }

  public DetAdeElement getDetAdeElement() {
    return _detAdeElement;
  }

  private DataFormDataElementContext getDataElementContext() {
    return _dataElementContext;
  }

  protected DetElement getDetElement() {
    return getDetAdeElement();
  }

  protected Element getElement() {
    return getAdeElement();
  }

  public boolean isRequired() {
    return false;
  }

  public boolean isHasAde() {
    return false;
  }

  public String getLabel() {
    
    String displayName = "";
    String cui = new String();

    DetAdeElement adeDetElem = getDetAdeElement();
    
    DataFormDefinitionDataElement dataElem = getDataElementContext().getDataFormDefinitionDataElement();
   
    // get the cui for the ade element
    if (adeDetElem != null) {
      cui = adeDetElem.getCui();
      }
    
    // Get the display name from the ade element if it was specified there.
    if ((cui != null)) {
      DataFormDefinitionAdeElement adeElem = dataElem.getAdeElement(cui);
      if (adeElem != null) {
        displayName = adeElem.getDisplayName();
      }
    }
    
    // Otherwise get the display name from the data element metadata, which is its description
    // in the DET.
    if (ApiFunctions.isEmpty(displayName)) {
      displayName = adeDetElem.getDescription();
    }
    
    return displayName;
  }

  public DetValueSet getValueSetNarrow() {
    return null;
  }

  public DetValueSet getValueSetStandard() {
    return null;
  }

  public DetValueSet getValueSetNonAtv() {
    return null;
  }

  public String getValueDescription(int index) {
    AdeElement instance = getAdeElement();
    if (instance == null) {
      return "";
    }
    else {
      DetAdeElement metadata = getDetAdeElement();
      String otherCui = metadata.getOtherValueCui();

      DataElementValue valueObj = (DataElementValue) instance.getElementValue(index);
      String value = valueObj.getValue();

      // If this is a CV value, then get its description and return it.
      if (metadata.isDatatypeCv()) {
        if (!ApiFunctions.isEmpty(value)) {
          if (value.equals(otherCui)) {
            value = valueObj.getValueOther();
          }
          else {
            value = DetService.SINGLETON.getDataElementTaxonomy().getCuiDescription(value);                      
          }
        }
      }
      return (value == null) ? "" : value; 
    }    
  }

  public String getValueDescription() {
    Element instance = getElement();
    if (instance == null) {
      return "";
    }
    else {
      DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
      DetElement metadata = getDetElement();
      StringBuffer buf = new StringBuffer(128);      
      boolean first = true;
      String otherCui = metadata.getOtherValueCui();
      DetValueSet standardValues = det.getSystemStandardValues();

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
          value = det.getCuiDescription(value);          
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
    return null;
  }

  public boolean isValueAtvValue() {
    return false;    
  }

}
