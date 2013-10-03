package com.gulfstreambio.kc.web.support;

import java.util.ArrayList;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetElementDatatypes;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.form.Element;
import com.gulfstreambio.kc.form.ElementValue;

public abstract class DataFormElementContextBase implements DataFormElementContext {

  private boolean _treatAsSingle;
  private String[] _excludedValues;
  private String _jsObjectId;
  private String _htmlIdContainer;
  private String _htmlIdPrimary;
  private String _htmlIdStandard;
  private String _htmlIdOther;
  private String _htmlIdOtherText;
  private String _htmlIdOtherContainer;
  private String _htmlIdOtherAdd;
  private String _htmlIdOtherRemove;
  private String _htmlIdNote;
  private String _htmlIdNoteContainer;
  private String _htmlIdNoteButton;
  private String _htmlIdAdeTable;
  private String _htmlIdAdeContainer;
  private String _htmlIdAdeLink;
  private String _htmlIdValueSet;
  private String _htmlIdNarrowValueSetContainer;
  private String _htmlIdStandardValueSetContainer;
  private String _htmlIdBroadValueSetContainer;
  private String _htmlIdNarrowValueSet;
  private String _htmlIdStandardValueSet;
  private String _htmlIdBroadValueSet;
  private String _htmlIdStandardLink;
  private List _htmlIdBroadLink;
  private String _htmlNameValueSet;
  
  DataFormElementContextBase() {
    super();
  }
  
  protected abstract DetElement getDetElement();

  protected abstract Element getElement();

  public String getCui() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? null : metadata.getCui();
  }

  public String getSystemName() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? null : metadata.getSystemName();
  }

  public String getDatatype() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? null : metadata.getDatatype();
  }

  public boolean isDatatypeDate() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.DATE.equals(datatype);
  }

  public boolean isDatatypeInt() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.INT.equals(datatype);
  }

  public boolean isDatatypeFloat() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.FLOAT.equals(datatype);
  }

  public boolean isDatatypeReport() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.REPORT.equals(datatype);
  }

  public boolean isDatatypeText() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.TEXT.equals(datatype);
  }

  public boolean isDatatypeVpd() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.VPD.equals(datatype);
  }

  public boolean isDatatypeCv() {
    String datatype = getDatatype();
    return (datatype == null) ? false : DetElementDatatypes.CV.equals(datatype);
  }

  public boolean isMultivalued() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? false : metadata.isMultivalued();
  }

  public boolean isHasOtherValue() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? false : !ApiFunctions.isEmpty(metadata.getOtherValueCui());
  }

  public String getOtherValueCui() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? null : metadata.getOtherValueCui();
  }

  public String getOtherValueDescription() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? null : metadata.getOtherValueDescription();
  }

  public boolean isHasNoValue() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? false : !ApiFunctions.isEmpty(metadata.getNoValueCui());
  }

  public String getNoValueCui() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? null : metadata.getNoValueCui();
  }

  public boolean isMlvs() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? false : metadata.isMultilevelValueSet();
  }

  public String getUnitCui() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? null : metadata.getUnitCui();
  }

  public String getUnitDescription() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? null : metadata.getUnitDescription();
  }

  public String getLabelWithUnits() {
    StringBuffer buf = new StringBuffer(getLabel());

    // If we have a display name, then append units to it, if there are any.
    if (buf.length() > 0) {
      String units = getUnitDescription();
      if (!ApiFunctions.isEmpty(units)) {
        buf.append(" (");
        buf.append(units);
        buf.append(')');
      }
    }
    return buf.toString();
  }

  public DetValueSet getValueSetBroad() {
    DetElement metadata = getDetElement();
    return (metadata == null) ? null : metadata.getBroadValueSet();    
  }

  public String getValue() {
    String value = null;
    Element instance = getElement();
    if (instance != null) {
      if (instance.isElementValueExists(0)) {
        ElementValue valueObj = instance.getElementValue(0);
        value = valueObj.getValue();        
      }
    }
    return value;
  }

  public String[] getValues() {
    Element instance = getElement();
    if (instance != null) {
      ElementValue[] values = instance.getElementValues();
      String[] returnValues = new String[values.length];
      for (int i = 0; i < values.length; i++) {
        returnValues[i] = values[i].getValue();
      }
      return returnValues;
    }
    else {
      return new String[0];
    }
  }

  public String getValueOther() {
    String value = null;
    Element instance = getElement();
    if (instance != null) {
      if (instance.isElementValueExists(0)) {
        ElementValue valueObj = instance.getElementValue(0);
        value = valueObj.getValueOther();        
      }
    }
    return value;
  }

  public String[] getValueOthers() {
    Element instance = getElement();
    if (instance != null) {
      ElementValue[] values = instance.getElementValues();
      String[] returnValues = new String[values.length];
      for (int i = 0; i < values.length; i++) {
        returnValues[i] = values[i].getValueOther();
      }
      return returnValues;
    }
    else {
      return new String[0];
    }
  }

  public boolean isValueStandardValue() {
    String value = getValue();
    if (value == null) {
      return false;
    }
    else {
      DetValueSet standardValues = 
        DetService.SINGLETON.getDataElementTaxonomy().getSystemStandardValues();
      return standardValues.containsValue(value);
    }
  }

  public boolean isValueNoValue() {
    String value = getValue();
    if (value == null) {
      return false;
    }
    else {
      DetElement metadata = getDetElement();
      return (metadata == null) ? false : value.equals(metadata.getNoValueCui());
    }
  }

  public boolean isAllValuesInNarrowValueSet() {
    return checkAllInValueSet(getValues(), getValueSetNarrow());
  }

  public boolean isAllValuesInStandardValueSet() {
    return checkAllInValueSet(getValues(), getValueSetStandard());
  }

  public boolean isAllValuesInBroadValueSet() {
    return checkAllInValueSet(getValues(), getValueSetBroad());
  }

  protected boolean checkAllInValueSet(String[] values, DetValueSet valueSet) {
    if (valueSet == null) {
      return false;
    }
    else if (ApiFunctions.isAllEmpty(values)) {
      return true;
    }
    else {
      boolean allValues = true;
      for (int i = 0; i < values.length; i++) {
        if (valueSet.getValue(values[i]) == null) {
          allValues = false;
          break;
        }      
      }
      return allValues;
    }    
  }
  
  public boolean isTreatAsSinglevalued() {
    return _treatAsSingle;
  }
  
  public void setTreatAsSinglevalued(boolean single) {
    _treatAsSingle = single;
  }

  public String[] getExcludedValues() {
    return _excludedValues;
  }
  
  public void setExcludedValues(String[] values) {
    _excludedValues = values;
  }

  public String getJavascriptObjectId() {
    return _jsObjectId;
  }
  
  public void setJavascriptObjectId(String id) {
    _jsObjectId = id;
  }

  public String getHtmlIdContainer() {
    return _htmlIdContainer;
  }
  
  public void setHtmlIdContainer(String id) {
    _htmlIdContainer = id;
  }

  public String getHtmlIdPrimary() {
    return _htmlIdPrimary;
  }
  
  public void setHtmlIdPrimary(String id) {
    _htmlIdPrimary = id;
  }

  public String getHtmlIdStandard() {
    return _htmlIdStandard;
  }
  
  public void setHtmlIdStandard(String id) {
    _htmlIdStandard = id;
  }

  public String getHtmlIdOther() {
    return _htmlIdOther;
  }
  
  public void setHtmlIdOther(String id) {
    _htmlIdOther = id;
  }

  public String getHtmlIdOtherText() {
    return _htmlIdOtherText;    
  }
  
  public void setHtmlIdOtherText(String id) {
    _htmlIdOtherText = id;
  }
  
  public String getHtmlIdOtherContainer() {
    return _htmlIdOtherContainer;    
  }
  
  public void setHtmlIdOtherContainer(String id) {
    _htmlIdOtherContainer = id;
  }

  public String getHtmlIdOtherAdd() {
    return _htmlIdOtherAdd;
  }
  
  public void setHtmlIdOtherAdd(String id) {
    _htmlIdOtherAdd = id;
  }
  
  public String getHtmlIdOtherRemove() {
    return _htmlIdOtherRemove;
  }
  
  public void setHtmlIdOtherRemove(String id) {
    _htmlIdOtherRemove = id;
  }

  public String getHtmlIdNote() {
    return _htmlIdNote;
  }
  
  public void setHtmlIdNote(String id) {
    _htmlIdNote = id;
  }
  
  public String getHtmlIdNoteContainer() {
    return _htmlIdNoteContainer;    
  }
  
  public void setHtmlIdNoteContainer(String id) {
    _htmlIdNoteContainer = id;
  }

  public String getHtmlIdNoteButton() {
    return _htmlIdNoteButton;
  }
  
  public void setHtmlIdNoteButton(String id) {
    _htmlIdNoteButton = id;
  }

  public String getHtmlIdAdeTable() {
    return _htmlIdAdeTable;
  }
  
  public void setHtmlIdAdeTable(String id) {
    _htmlIdAdeTable = id;
  }

  public String getHtmlIdAdeContainer() {
    return _htmlIdAdeContainer;
  }
  
  public void setHtmlIdAdeContainer(String id) {
    _htmlIdAdeContainer = id;
  }

  public String getHtmlIdAdeLink() {
    return _htmlIdAdeLink;
  }
  
  public void setHtmlIdAdeLink(String id) {
    _htmlIdAdeLink = id;
  }

  public String getHtmlIdValueSet() {
    return _htmlIdValueSet;
  }
  
  public void setHtmlIdValueSet(String id) {
    _htmlIdValueSet = id;
  }

  public String getHtmlNameValueSet() {
    return _htmlNameValueSet;
  }
  
  public void setHtmlNameValueSet(String name) {
    _htmlNameValueSet = name;
  }

  public String getHtmlIdNarrowValueSetContainer() {
    return _htmlIdNarrowValueSetContainer;
  }
  
  public void setHtmlIdNarrowValueSetContainer(String id) {
    _htmlIdNarrowValueSetContainer = id;
  }
  
  public String getHtmlIdStandardValueSetContainer() {    
    return _htmlIdStandardValueSetContainer;
  }
  
  public void setHtmlIdStandardValueSetContainer(String id) {
    _htmlIdStandardValueSetContainer = id;
  }
  
  public String getHtmlIdBroadValueSetContainer() {
    return _htmlIdBroadValueSetContainer;
  }

  public void setHtmlIdBroadValueSetContainer(String id) {
    _htmlIdBroadValueSetContainer = id;
  }
  
  public String getHtmlIdNarrowValueSet() {
    return _htmlIdNarrowValueSet;
  }
  
  public void setHtmlIdNarrowValueSet(String id) {
    _htmlIdNarrowValueSet = id;
  }
  
  public String getHtmlIdStandardValueSet() {    
    return _htmlIdStandardValueSet;
  }
  
  public void setHtmlIdStandardValueSet(String id) {
    _htmlIdStandardValueSet = id;
  }
  
  public String getHtmlIdBroadValueSet() {
    return _htmlIdBroadValueSet;
  }

  public void setHtmlIdBroadValueSet(String id) {
    _htmlIdBroadValueSet = id;
  }
  
  public String getHtmlIdStandardLink() {
    return _htmlIdStandardLink;
  }
  
  public void setHtmlIdStandardLink(String id) {
    _htmlIdStandardLink = id;
  }

  public String[] getHtmlIdBroadLinks() {
    return (_htmlIdBroadLink == null) ? null : (String[]) _htmlIdBroadLink.toArray(new String[0]);
  }

  public void addHtmlIdBroadLink(String id) {
    if (_htmlIdBroadLink == null) {
      _htmlIdBroadLink = new ArrayList();
    }
    _htmlIdBroadLink.add(id);
  }
  
}
