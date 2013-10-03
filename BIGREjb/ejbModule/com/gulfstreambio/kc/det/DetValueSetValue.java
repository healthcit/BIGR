package com.gulfstreambio.kc.det;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.gboss.GbossValue;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValue;

public class DetValueSetValue implements DetConcept, Serializable{

  private String _cui;
  private String _description;
  private boolean _noValue = false;
  private boolean _otherValue = false;
  private List _values;
  private Map _valuesCache;
  
  DetValueSetValue(GbossValue value) {
    super();
    setCui(value.getCui());
    setDescription(value.getDescription());

    if (value.isNoValue()) {
    	setNoValue(true);
    }
    if (value.isOtherValue()) {
      setOtherValue(true);
    }
    
    List subValues = value.getValues();
    for (int i = 0; i < subValues.size(); i++) {
      GbossValue subValue = (GbossValue) subValues.get(i);
      addValue(new DetValueSetValue(subValue));
    }
  }

  DetValueSetValue(DataFormDefinitionValue valueDefinition) {
    super();
    String cui = valueDefinition.getCui();
    setCui(valueDefinition.getCui());
    
    DetValue metadata = DetService.SINGLETON.getDataElementTaxonomy().getValue(cui);
    String description = valueDefinition.getDisplayName();
    if (ApiFunctions.isEmpty(description)) {
      setDescription(metadata.getDescription());
    }
    else {
      setDescription(description);
    }
    setNoValue(metadata.isNoValue());
    setOtherValue(metadata.isOtherValue());
    
    DataFormDefinitionValue[] subValues = valueDefinition.getValues();
    for (int i = 0; i < subValues.length; i++) {
      addValue(new DetValueSetValue(subValues[i]));
    }
  }
  
  DetValueSetValue(QueryFormDefinitionRollupValue value) {
    super();
    setCui(value.getDisplayName());
    setDescription(value.getDisplayName());
  }
  
  public String getCui() {
    return _cui;
  }
  
  private void setCui(String cui) {
    _cui = cui;
  }
  
  public String getDescription() {
    return _description;
  }
  
  public void setDescription(String description) {
    _description = description;
  }
  
  public boolean isNoValue() {
    return _noValue;
  }
  
  private void setNoValue(boolean noValue) {
    _noValue = noValue;
  }
  
  public boolean isOtherValue() {
    return _otherValue;
  }
  
  private void setOtherValue(boolean otherValue) {
    _otherValue = otherValue;
  }

  private void addValue(DetValueSetValue value) {
    if (_values == null) {
      _values = new ArrayList();
      _valuesCache = new HashMap();
    }
    _values.add(value);
    _valuesCache.put(value.getCui(), value);
  }
  
  public DetValueSetValue getValue(String cui) {
    return (_valuesCache == null) ? null : (DetValueSetValue) _valuesCache.get(cui);
  }

  public DetValueSetValue[] getValues() {
    if (_values == null) { 
      return new DetValueSetValue[0];
    }
    else {
      return (DetValueSetValue[]) _values.toArray(new DetValueSetValue[0]);
    }
  }
  
  public boolean containsValue(String cui) {
    return (getValue(cui) != null);
  }  
  
  public boolean equals(Object obj) {

    if (!(obj instanceof DetValueSetValue)) {
      return false;
    }

    DetValueSetValue vSetVal = (DetValueSetValue) obj;  
    
    // do not process empty lists...
    if ((_values == null) && (vSetVal._values == null)) {
      return true;
    }
    
    // otherwise, make sure the sizes of the value sets are the same...if not,
    // then they are not the same
    if (_values.size() != vSetVal._values.size()) {
        return false;
    }
     
    // compare all the CUI values in the value sets
    for (int i = 0; i < _values.size(); i++) {
       DetValueSetValue vElementVal = (DetValueSetValue) _values.get(i);      
       if (!vSetVal._values.contains(vElementVal)) {
         return false;
       }
       // recursively check for values...
       if (!vElementVal.equals(vSetVal._values.get(vSetVal._values.indexOf(vElementVal)))) {
           return false;
         }
       } 
  
     return true;
   }
}
