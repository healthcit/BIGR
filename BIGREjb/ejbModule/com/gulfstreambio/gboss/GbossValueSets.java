package com.gulfstreambio.gboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GbossValueSets {
  
  private List _valueSetList = new ArrayList();
  private Map _valueSetMap = new HashMap();
  private boolean _immutable = false;

  /**
   * Create a new GbossValueSets object.
   */
  public GbossValueSets() {
    super();
  }
  
  /**
   * Add a new GbossValueSet
   * @param valueSet  the GbossValueSet to add
   */
  public void addValueSet(GbossValueSet valueSet) {    
    checkImmutable();
    if (valueSet != null) {
      _valueSetList.add(valueSet);
      _valueSetMap.put(valueSet.getCui(), valueSet);
    }
  }
  
  /**
   * Retrieve a specific GbossValueSet
   * @param cui  the cui of the GbossValueSet to be retrieved
   * @return  A GbossValueSet with the specified cui, or null if no such GbossValueSet exists
   */
  public GbossValueSet getValueSet(String cui) {
    GbossValueSet returnValue = (GbossValueSet)_valueSetMap.get(cui);
    return returnValue;
  }
  
  /**
   * Retrieve a list of the GbossValueSets contained by this GbossValueSets
   * @return a list of the GbossValueSets contained by this GbossValueSets
   */
  public List getValueSets() {
    return _valueSetList;
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    //mark each GbossValueSet immutable
    Iterator valueSets = _valueSetList.iterator();
    while (valueSets.hasNext()) {
      ((GbossValueSet)valueSets.next()).setImmutable();
    }
    //make the list and map unmodifiable
    _valueSetList = Collections.unmodifiableList(_valueSetList);
    _valueSetMap = Collections.unmodifiableMap(_valueSetMap);
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
      throw new IllegalStateException("Attempted to modify an immutable GbossValueSets: " + this);
    }
  }

}
