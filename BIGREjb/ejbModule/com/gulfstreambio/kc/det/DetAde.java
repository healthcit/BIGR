package com.gulfstreambio.kc.det;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gulfstreambio.gboss.GbossAde;
import com.gulfstreambio.gboss.GbossAdeElement;

public class DetAde implements DetConcept, Serializable {

  private String _cui;
  private String _systemName;
  private String _description;
  private List _adeElements;
  
  DetAde(DataElementTaxonomy det, GbossAde ade) {
    super();
    setCui(ade.getCui());
    setDescription(ade.getDescription());
    setSystemName(ade.getSystemName());
    
    _adeElements = new ArrayList();
    // get all the data elements
    Iterator i = ade.getAncillaryDataElementElements().iterator();
    while (i.hasNext()) {
      GbossAdeElement adeElement = (GbossAdeElement) i.next();
      _adeElements.add(new DetAdeElement(det, adeElement));
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

  public DetAdeElement[] getAdeElements() {
    if (_adeElements == null) {
      return new DetAdeElement[0];
    }
    else {
      return (DetAdeElement[]) _adeElements.toArray(new DetAdeElement[0]);
    }
  }
}
