package com.gulfstreambio.kc.web.support;

import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionHostElement;

public class DataFormHostElementContext {

  private DataFormDefinitionHostElement _formDefinitionHostElement;
  
  public DataFormHostElementContext(DataFormDefinitionHostElement element) {
    super();
    _formDefinitionHostElement = element;
  }

  DataFormDefinitionHostElement getDataFormDefinitionHostElement() {
    return _formDefinitionHostElement;
  }
  
  public String getDisplayName() {
    DataFormDefinitionHostElement element = getDataFormDefinitionHostElement();
    return (element == null) ? null : element.getDisplayName();
  }
  
  public String getHostId() {
    DataFormDefinitionHostElement element = getDataFormDefinitionHostElement();
    return (element == null) ? null : element.getHostId();
  }
  
  public boolean isRequired() {
    DataFormDefinitionHostElement element = getDataFormDefinitionHostElement();
    return (element == null) ? false : element.isRequired();
  } 

  public Tag[] getTags() {
    DataFormDefinitionHostElement element = getDataFormDefinitionHostElement();
    return (element == null) ? null : element.getTags();
  }
}
