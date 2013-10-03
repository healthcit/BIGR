package com.gulfstreambio.gboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;

public class GbossAde extends GbossConcept {
  
  private List _ancillaryDataElementElementList = new ArrayList();
  private Map _ancillaryDataElementElementMap = new HashMap();
  private String _systemName = null;

  /**
   * Create a new GbossAde object.
   */
  public GbossAde() {
    super();
  }
  
  public String toHtml() {
    StringBuffer buff = new StringBuffer(100);
    buff.append(super.toHtml());
    if (!ApiFunctions.isEmpty(getSystemName())) {
      buff.append(", ");
      buff.append("system name = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getSystemName()));
    }
    return buff.toString();
  }
  
  /**
   * Add a new GbossAdeElement
   * @param ancillaryDataElementElement  the GbossAdeElement to add
   */
  public void addAncillaryDataElementElement(GbossAdeElement ancillaryDataElementElement) {    
    checkImmutable();
    if (ancillaryDataElementElement != null) {
      _ancillaryDataElementElementList.add(ancillaryDataElementElement);
      _ancillaryDataElementElementMap.put(ancillaryDataElementElement.getCui(), ancillaryDataElementElement);
    }
  }
  
  /**
   * Retrieve a specific GbossAdeElement
   * @param cui  the cui of the GbossAdeElement to be retrieved
   * @return  An GbossAdeElement with the specified cui, or null if no such 
   * GbossAdeElement exists
   */
  public GbossAdeElement getAncillaryDataElementElement(String cui) {
    GbossAdeElement returnValue = (GbossAdeElement)_ancillaryDataElementElementMap.get(cui);
    return returnValue;
  }
  
  /**
   * Retrieve a list of the AncillaryDataElementElements contained by this 
   * GbossAde
   * @return a list of the GbossAdeElement contained by this 
   * GbossAde
   */
  public List getAncillaryDataElementElements() {
    return _ancillaryDataElementElementList;
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    super.setImmutable();
    //mark each GbossAdeElement immutable
    Iterator ancillaryDataElementElements = _ancillaryDataElementElementList.iterator();
    while (ancillaryDataElementElements.hasNext()) {
      ((GbossAdeElement)ancillaryDataElementElements.next()).setImmutable();
    }
    //make the list and map unmodifiable
    _ancillaryDataElementElementList = Collections.unmodifiableList(_ancillaryDataElementElementList);
    _ancillaryDataElementElementMap = Collections.unmodifiableMap(_ancillaryDataElementElementMap);
  }
  
  /**
   * @return Returns the systemName.
   */
  public String getSystemName() {
    return _systemName;
  }
  
  /**
   * @param systemName The systemName to set.
   */
  public void setSystemName(String systemName) {
    checkImmutable();
    _systemName = systemName;
  }
}
