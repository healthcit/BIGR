package com.gulfstreambio.gboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GbossValues {
  
  private List _valueList = new ArrayList();
  private Map _valueMap = new HashMap();
  private boolean _immutable = false;

  /**
   * Create a new GbossValues object.
   */
  public GbossValues() {
    super();
  }
  
  /**
   * Add a new GbossValue
   * @param value  the GbossValue to add
   */
  public void addValue(GbossValue value) {    
    checkImmutable();
    if (value != null) {
      _valueList.add(value);
      _valueMap.put(value.getCui(), value);
    }
  }
  
  /**
   * Retrieve a specific GbossValue
   * @param cui  the cui of the GbossValue to be retrieved
   * @return  A GbossValue with the specified cui, or null if no such GbossValue exists
   */
  public GbossValue getValue(String cui) {
    GbossValue returnValue = (GbossValue)_valueMap.get(cui);
    return returnValue;
  }
  
  /**
   * Retrieve a list of the GbossValues contained by this GbossValues
   * @return a list of the GbossValues contained by this GbossValues
   */
  public List getValues() {
    return _valueList;
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    //mark each GbossValue immutable
    Iterator values = _valueList.iterator();
    while (values.hasNext()) {
      ((GbossValue)values.next()).setImmutable();
    }
    //make the list and map unmodifiable
    _valueList = Collections.unmodifiableList(_valueList);
    _valueMap = Collections.unmodifiableMap(_valueMap);
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
      throw new IllegalStateException("Attempted to modify an immutable GbossValues: " + this);
    }
  }

}
