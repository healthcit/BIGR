package com.gulfstreambio.kc.form;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.util.SystemNameUtils;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;

/**
 * Represents an ADE containing a number of ADE elements.
 */
public class Ade implements Serializable {

  /**
   * Holds all of the ADE elements that comprise this ADE.
   */
  private Map _adeElements;
  private Map _adeElementsCache;

  /**
   * Creates a new <code>Ade</code>. 
   */
  Ade() {
    super();
    _adeElements = new HashMap();
    _adeElementsCache = new HashMap();
  }
  
  Ade(Ade ade) {
    this();
    AdeElement[] adeElements = ade.getAdeElements();
    for (int i = 0; i < adeElements.length; i++) {
      createAdeElement(adeElements[i]);
    }
  }
  

  /**
   * Creates a new ADE element with the specified CUI or system name for this ADE and returns it.
   * 
   * @return  The newly created ADE element.
   * @throws ApiException if the ADE element with the specified system name or CUI already exists
   */
  public AdeElement createAdeElement(String cuiOrSystemName) {
    AdeElement adeElement = new AdeElement(cuiOrSystemName);
    addAdeElement(adeElement);
    return adeElement;
  }

  private AdeElement createAdeElement(AdeElement adeElement) {
    AdeElement newAdeElement = new AdeElement(adeElement);
    addAdeElement(newAdeElement);
    return newAdeElement;
  }

  private void addAdeElement(AdeElement adeElement) {
    String cuiOrSystemName = adeElement.getCuiOrSystemName();
    AdeElement existingAdeElement = getAdeElement(cuiOrSystemName);
    if (existingAdeElement != null) {
      throw new ApiException("Attempt to create ADE element that already exists (" + cuiOrSystemName + ")");
    }
    _adeElements.put(cuiOrSystemName, adeElement);

    DetAdeElement metadata = 
      DetService.SINGLETON.getDataElementTaxonomy().getAdeElement(cuiOrSystemName);
    _adeElementsCache.put(metadata.getCui(), adeElement);
    _adeElementsCache.put(metadata.getSystemName(), adeElement);
  }

  /**
   * Returns the ADE element with the specified CUI or system name.
   * 
   * @return  The ADE element, or <code>null</code> if such an ADE element does not exist.
   */
  public AdeElement getAdeElement(String cuiOrSystemName) {
    AdeElement adeElement = (AdeElement) _adeElementsCache.get(cuiOrSystemName);
    if (adeElement == null) {
      String canonical = SystemNameUtils.convertToCanonicalForm(cuiOrSystemName);      
      adeElement = (AdeElement) _adeElementsCache.get(canonical);
    }
    return adeElement;
  }

  /**
   * Returns a collection of all ADE elements of this ADE.  
   * 
   * @return  a collection of <code>AdeElement</code> objects.
   */
  public AdeElement[] getAdeElements() {
    if (_adeElements == null) {
      return new AdeElement[0];
    }
    else {
      return (AdeElement[]) _adeElements.values().toArray(new AdeElement[0]);
    }
  }

}
