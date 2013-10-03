package com.gulfstreambio.gboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GbossAdes {
  
  private List _ancillaryDataElementList = new ArrayList();
  private Map _ancillaryDataElementMap = new HashMap();
  private boolean _immutable = false;

  /**
   * Create a new GbossAdes object.
   */
  public GbossAdes() {
    super();
  }

  /**
   * Add a new GbossAde
   * @param ancillaryDataElement  the GbossAde to add
   */
  public void addAncillaryDataElement(GbossAde ancillaryDataElement) {    
    checkImmutable();
    if (ancillaryDataElement != null) {
      _ancillaryDataElementList.add(ancillaryDataElement);
      _ancillaryDataElementMap.put(ancillaryDataElement.getCui(), ancillaryDataElement);
    }
  }
  
  /**
   * Retrieve a specific GbossAde
   * @param cui  the cui of the GbossAde to be retrieved
   * @return  An GbossAde with the specified cui, or null if no such 
   * GbossAde exists
   */
  public GbossAde getAncillaryDataElement(String cui) {
    GbossAde returnValue = (GbossAde)_ancillaryDataElementMap.get(cui);
    return returnValue;
  }
  
  /**
   * Retrieve a list of the GbossAdes contained by this GbossAdes
   * @return a list of the GbossAdes contained by this GbossAdes
   */
  public List getAncillaryDataElements() {
    return _ancillaryDataElementList;
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    //mark each GbossAde immutable
    Iterator ancillaryDataElements = _ancillaryDataElementList.iterator();
    while (ancillaryDataElements.hasNext()) {
      ((GbossAde)ancillaryDataElements.next()).setImmutable();
    }
    //make the list and map unmodifiable
    _ancillaryDataElementList = Collections.unmodifiableList(_ancillaryDataElementList);
    _ancillaryDataElementMap = Collections.unmodifiableMap(_ancillaryDataElementMap);
  }

  /**
   * Is this instance immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkImmutable() {
    if (_immutable) {
      throw new IllegalStateException("Attempted to modify an immutable GbossAdes: " + this);
    }
  }

}
