package com.gulfstreambio.kc.det;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gulfstreambio.gboss.GbossCategory;
import com.gulfstreambio.gboss.GbossDataElement;

public class DetCategory implements DetConcept, Serializable {

  private String _cui;
  private String _systemName;
  private String _description;
  private List _categories;
  private List _dataElements;
  
  DetCategory(DataElementTaxonomy det, GbossCategory category) {
    
    super();
    setCui(category.getCui());
    setDescription(category.getDescription());
    setSystemName(category.getSystemName());

    _categories = new ArrayList();
    List cats = category.getCategories();
    for (int i = 0; i < cats.size(); i++) {
      GbossCategory subCategory = (GbossCategory) cats.get(i);
      _categories.add(new DetCategory(det, subCategory));
    }

    _dataElements = new ArrayList();
    List elements = category.getDataElements();
    for (int j = 0; j < elements.size(); j++) {
      GbossDataElement dataElement = (GbossDataElement) elements.get(j);
      _dataElements.add(new DetDataElement(det, dataElement));
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
  
  public String getSystemName() {
    return _systemName;
  }
  
  private void setSystemName(String systemName) {
    _systemName = systemName;
  }
  
  public DetCategory[] getCategories() {
    return (DetCategory[]) _categories.toArray(new DetCategory[0]);
  }

  public DetDataElement[] getDataElements() {
    return (DetDataElement[]) _dataElements.toArray(new DetDataElement[0]);
  }
}
