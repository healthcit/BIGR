package com.gulfstreambio.kc.detviewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gulfstreambio.kc.det.DetAde;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetCategory;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetUnit;
import com.gulfstreambio.kc.det.DetValue;
import com.gulfstreambio.kc.det.DetValueSet;

public class DetViewerQueryResults {

  private List _ades;
  private List _adeElements;
  private List _categories;
  private List _dataElements;
  private List _units; 
  private List _valueSets;
  private List _values;
  private int _count = 0;
  
  private List _associatedDataElements;
  private List _associatedAdeElements;
  private List _associatedValueSets;
  
  DetViewerQueryResults() {
    super();
  }
  
  void addAde(DetAde ade) {
    if (_ades == null) {
      _ades = new ArrayList();
    }
    _ades.add(ade);
    _count++;
  }

  public DetAde[] getAdes() {
    if (_ades == null) {
      return new DetAde[0];
    }
    else {
      return (DetAde[]) _ades.toArray(new DetAde[0]);
    }
  }

  void addAdeElement(DetAdeElement adeElement) {
    if (_adeElements == null) {
      _adeElements = new ArrayList();
    }
    _adeElements.add(adeElement);
    _count++;
  }

  public DetAdeElement[] getAdeElements() {
    if (_adeElements == null) {
      return new DetAdeElement[0];
    }
    else {
      return (DetAdeElement[]) _adeElements.toArray(new DetAdeElement[0]);
    }
  }

  void addAssociatedAdeElement(DetAdeElement adeElement) {
    if (_associatedAdeElements == null) {
      _associatedAdeElements = new ArrayList();
    }
    _associatedAdeElements.add(adeElement);
  }

  public DetAdeElement[] getAssociatedAdeElements() {
    if (_associatedAdeElements == null) {
      return new DetAdeElement[0];
    }
    else {
      return (DetAdeElement[]) _associatedAdeElements.toArray(new DetAdeElement[0]);
    }
  }

  void addCategory(DetCategory category) {
    if (_categories == null) {
      _categories = new ArrayList();
    }
    _categories.add(category);
    _count++;
  }

  public DetCategory[] getCategories() {
    if (_categories == null) {
      return new DetCategory[0];
    }
    else {
      return (DetCategory[]) _categories.toArray(new DetCategory[0]);
    }
  }

  void addDataElement(DetDataElement dataElement) {
    if (_dataElements == null) {
      _dataElements = new ArrayList();
    }
    _dataElements.add(dataElement);
    _count++;
  }

  public DetDataElement[] getDataElements() {
    if (_dataElements == null) {
      return new DetDataElement[0];
    }
    else {
      return (DetDataElement[]) _dataElements.toArray(new DetDataElement[0]);
    }
  }

  void addAssociatedDataElement(DetDataElement dataElement) {
    if (_associatedDataElements == null) {
      _associatedDataElements = new ArrayList();
    }
    _associatedDataElements.add(dataElement);
  }

  public DetDataElement[] getAssociatedDataElements() {
    if (_associatedDataElements == null) {
      return new DetDataElement[0];
    }
    else {
      return (DetDataElement[]) _associatedDataElements.toArray(new DetDataElement[0]);
    }
  }

  public DetUnit[] getUnits() {
    return (_units == null) ? new DetUnit[0] : (DetUnit[]) _units.toArray(new DetUnit[0]);
  }
  
  void addUnit(DetUnit unit) {
    if (_units == null) {
      _units = new ArrayList();
    }
    _units.add(unit);
    _count++;
  }

  public DetValueSet[] getValueSets() {
    return (_valueSets == null) 
      ? new DetValueSet[0] : (DetValueSet[]) _valueSets.toArray(new DetValueSet[0]);
  }
  
  void addValueSet(DetValueSet valueSet) {
    if (_valueSets == null) {
      _valueSets = new ArrayList();
    }
    _valueSets.add(valueSet);
    _count++;
  }

  void addAssociatedValueSet(DetValueSet valueSet) {
    if (_associatedValueSets == null) {
      _associatedValueSets = new ArrayList();
    }
    _associatedValueSets.add(valueSet);
  }

  public DetValueSet[] getAssociatedValueSets() {
    if (_associatedValueSets == null) {
      return new DetValueSet[0];
    }
    else {
      return (DetValueSet[]) _associatedValueSets.toArray(new DetValueSet[0]);
    }
  }

  public DetValue[] getValues() {
    return (_values == null) ? new DetValue[0] : (DetValue[]) _values.toArray(new DetValue[0]);
  }
  
  void addValue(DetValue value) {
    if (_values == null) {
      _values = new ArrayList();
    }
    _values.add(value);
    _count++;
  }

  public int getResultsCount() {
    return _count;
  }  
}
