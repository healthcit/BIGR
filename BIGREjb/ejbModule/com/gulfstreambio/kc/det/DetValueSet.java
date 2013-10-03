package com.gulfstreambio.kc.det;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gulfstreambio.gboss.GbossValue;
import com.gulfstreambio.gboss.GbossValueSet;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValue;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet;

public class DetValueSet implements DetConcept, Serializable {

  private String _cui;
  private String _description;
  private List _values;
  private Map _valuesCache;
  private DetValueSetValue _otherValue;
  private DetValueSetValue _noValue;
  
  public DetValueSet(GbossValueSet valueSet) {
    super();
    
    setCui(valueSet.getCui());
    setDescription(valueSet.getDescription());
    List values = valueSet.getValues();
    for (int i = 0; i < values.size(); i++) {
      GbossValue value = (GbossValue) values.get(i);
      addValue(new DetValueSetValue(value));
    }
  }
  
  public DetValueSet(DataFormDefinitionValueSet valueSet) {
    super();
    
    DataFormDefinitionValue[] values = valueSet.getValues();
    for (int i = 0; i < values.length; i++) {
      addValue(new DetValueSetValue(values[i]));
    }
  }
  
  public DetValueSet(QueryFormDefinitionRollupValueSet valueSet) {
    super();
    QueryFormDefinitionRollupValue[] values = valueSet.getValues();
    for (int i = 0; i < values.length; i++) {
      addValue(new DetValueSetValue(values[i]));
    }
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
  
  private void setDescription(String description) {
    _description = description;
  }
  
  private void addValue(DetValueSetValue value) {
    if (_values == null) {
      _values = new ArrayList();
    }
    _values.add(value);
  }
  
  public DetValueSetValue getValue(String cui) {
    cacheValues();    
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
  
  public DetValueSetValue getNoValue() {
    cacheValues();
    return _noValue;
  }
  
  private void setNoValue(DetValueSetValue noValue) {
    _noValue = noValue;
  }
  
  public DetValueSetValue getOtherValue() {
    cacheValues();
    return _otherValue;
  }
  
  private void setOtherValue(DetValueSetValue otherValue) {
    _otherValue = otherValue;
  }

  private void cacheValues() {
    if (_valuesCache == null) {
      _valuesCache = new HashMap();
      DetValueSetValue[] values = getValues();
      for (int i = 0; i < values.length; i++) {
        DetValueSetValue value = values[i];
        _valuesCache.put(value.getCui(), value);
        if (value.isNoValue()) {
          setNoValue(value);
        }
        if (value.isOtherValue()) {
          setOtherValue(value);
        }        
        cacheValueChildren(value);
      }
    }
  }

  private void cacheValueChildren(DetValueSetValue value) {
    DetValueSetValue[] values = value.getValues();
    for (int i = 0; i < values.length; i++) {
      DetValueSetValue subValue = values[i];
      _valuesCache.put(subValue.getCui(), subValue);
      if (value.isNoValue()) {
        setNoValue(value);
      }
      if (value.isOtherValue()) {
        setOtherValue(value);
      }        
      cacheValueChildren(subValue);
    }    
  }
  
  public boolean equals(Object obj) {

    if (!(obj instanceof DetValueSet)) {
      return false;
    }

    DetValueSet vSet = (DetValueSet) obj;  
      
    // first, make sure the sizes of the value sets are the same...
    if (_values.size() != vSet._values.size()) {
       return false;
    }

    // compare all the CUI values in the value sets
    for (int i = 0; i < _values.size(); i++) {
      DetValueSetValue vElementVal = (DetValueSetValue) _values.get(i);      
      if (!vSet._values.contains(vElementVal)) {
        return false;
      }
      // note that there may be a 2nd level value set, so we must
      // compare that, too...
      if (!vElementVal.equals(vSet._values.get(vSet._values.indexOf(vElementVal)))) {
          return false;
        }
      } 
 
    return true;
  }
  
}
