package com.gulfstreambio.kc.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;

/**
 * Represents a data element with one or more values.
 */
public class DataElement implements Element, Serializable {
  
  /**
   * The CUI or system name of this data element.
   */
  private String _cuiOrSystemName;

  /**
   * A list of the values of this data element.
   */
  private List _dataElementValues;

  /**
   * The value of this data element's note.
   */
  private String _valueNote;

  /**
   * Creates a new <code>DataElement</code> for the data element with the specified CUI or
   * system name. 
   * 
   * @param  cuiOrSystemName  the CUI or system name that identifies the data element
   */
  public DataElement(String cuiOrSystemName) {
    super();
    setCuiOrSystemName(cuiOrSystemName);
  }

  public DataElement(DataElement element) {
    this(element.getCuiOrSystemName());
    setValueNote(element.getValueNote());
    DataElementValue[] values = element.getDataElementValues();
    for (int i = 0; i < values.length; i++) {
      createElementValue(values[i]);
    }    
  }
  
  /**
   * Returns the CUI or system name of this data element.
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
   * Creates and returns a new value for this data element.
   * 
   * @return  The new <code>DataElementValue</code>.
   */
  public ElementValue createElementValue() {
    return createDataElementValue();
  }

  public DataElementValue createDataElementValue() {
    DataElementValue value = new DataElementValue();
    addElementValue(value);
    return value;
  }

  private ElementValue createElementValue(DataElementValue value) {
    DataElementValue newValue = new DataElementValue(value);
    addElementValue(newValue);
    return newValue;
  }
  
  private void addElementValue(DataElementValue value) {
    if (_dataElementValues == null) {
      _dataElementValues = new ArrayList();
    }
    _dataElementValues.add(value);
  }

  /**
   * Returns the values of this data element.
   * 
   * @return  An array containing all of the data element values.
   *          If a value was not added via {@link #createElementValue()}, then an empty array
   *          is returned.   
   */
  public ElementValue[] getElementValues() {
    return getDataElementValues();
  }

  /**
   * Returns the values of this data element.
   * 
   * @return  An array containing all of the data element values.
   *          If a value was not added via {@link #createElementValue()}, then an empty array
   *          is returned.   
   */
  public DataElementValue[] getDataElementValues() {
    if (_dataElementValues == null) {
      return new DataElementValue[0];
    }
    else {
      return (DataElementValue[]) _dataElementValues.toArray(new DataElementValue[0]);
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
    if ((_dataElementValues == null) || (_dataElementValues.size() <= index)) {
      throw new ApiException("Attempt to get data element value at index " + String.valueOf(index) + " for data element without that many values.");
    }
    else {
      return (DataElementValue) _dataElementValues.get(index);
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
    if ((_dataElementValues == null) || (_dataElementValues.size() <= index)) {
      return false;
    }
    else {
      return true;
    }
  }

  /**
   * Returns the value of this data element's note.
   * 
   * @return  The note value, or null if this data element has no note.
   */
  public String getValueNote() {
    return _valueNote;
  }

  /**
   * Sets the value of this data element's note.
   * 
   * @param  note  the note
   */
  public void setValueNote(String note) {
    _valueNote = ApiFunctions.safeTrim(note);
  }
}
