package com.gulfstreambio.kc.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.util.BigrXmlUtils;

/**
 * Represents an ADE element with one or more values.
 */
public class AdeElement implements Element, Serializable {

  /**
   * The CUI or system name of this ADE element.
   */
  private String _cuiOrSystemName;

  /**
   * A list of the values of this ADE element.
   */
  private List _adeElementValues;

  /**
   * Creates a new <code>AdeElement</code> for the ADE element with the specified CUI or
   * system name. 
   * 
   * @param  cuiOrSystemName  the CUI or system name that identifies the ADE element
   */
  AdeElement(String cuiOrSystemName) {
    super();
    setCuiOrSystemName(cuiOrSystemName);
  }

  AdeElement(AdeElement element) {
    this(element.getCuiOrSystemName());
    AdeElementValue[] values = element.getAdeElementValues();
    for (int i = 0; i < values.length; i++) {
      createElementValue(values[i]);
    }        
  }

  /**
   * Returns the CUI or system name of this ADE element.
   * 
   * @return The CUI or system name.
   */
  public String getCuiOrSystemName() {
    return _cuiOrSystemName;
  }
  
  private void setCuiOrSystemName(String cuiOrSystemName) {
    _cuiOrSystemName = cuiOrSystemName;
  }

  /**
   * Creates and returns a new value for this ADE element.
   * 
   * @return  The new <code>AdeElementValue</code>.
   */
  public ElementValue createElementValue() {
    return createAdeElementValue();
  }

  public AdeElementValue createAdeElementValue() {
    AdeElementValue value = new AdeElementValue();
    addElementValue(value);
    return value;
  }

  private ElementValue createElementValue(AdeElementValue value) {
    AdeElementValue newValue = new AdeElementValue(value);
    addElementValue(newValue);
    return newValue;
  }
  
  private void addElementValue(AdeElementValue value) {
    if (_adeElementValues == null) {
      _adeElementValues = new ArrayList();
    }
    _adeElementValues.add(value);    
  }

  /**
   * Returns the values of this ADE element.
   * 
   * @return  An array containing all of the ADE element values.
   *          If a value was not added via {@link #createElementValue()}, then
   *          an empty array is returned.   
   */
  public ElementValue[] getElementValues() {
    return getAdeElementValues();
  }

  /**
   * Returns the values of this ADE element.
   * 
   * @return  An array containing all of the ADE element values.
   *          If a value was not added via {@link #createElementValue()}, then
   *          an empty array is returned.   
   */
  public AdeElementValue[] getAdeElementValues() {
    if (_adeElementValues == null) {
      return new AdeElementValue[0];
    }
    else {
      return (AdeElementValue[]) _adeElementValues.toArray(new AdeElementValue[0]);
    }
  }

  /**
   * Returns the value of this data element at the specified index.
   * 
   * @param  the index
   * @return  The data element value at the specified index.
   * @throws ApiException if there are no data element values or the index specifies a data 
   * element value that does not exist. 
   */
  public ElementValue getElementValue(int index) {
    if ((_adeElementValues == null) || (_adeElementValues.size() <= index)) {
      throw new ApiException("Attempt to get ADE element value at index " + String.valueOf(index) + " for ADE element without that many values.");
    }
    else {
      return (AdeElementValue) _adeElementValues.get(index);
    }
  }

  /**
   * Returns an indication of whether a value for the specified index exists.
   * 
   * @param  the index
   * @return  <code>true</code> if the value at the specified index exists; <code>false</code> 
   *          otherwise  
   */
  public boolean isElementValueExists(int index) {
    if ((_adeElementValues == null) || (_adeElementValues.size() <= index)) {
      return false;
    }
    else {
      return true;
    }
  }  

}
