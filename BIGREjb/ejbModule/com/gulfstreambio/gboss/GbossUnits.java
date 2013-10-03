package com.gulfstreambio.gboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GbossUnits {
  
  private List _unitList = new ArrayList();
  private Map _unitMap = new HashMap();
  private boolean _immutable = false;

  /**
   * Create a new GbossUnits object.
   */
  public GbossUnits() {
    super();
  }

  /**
   * Add a new GbossUnit
   * @param unit  the GbossUnit to add
   */
  public void addUnit(GbossUnit unit) {    
    checkImmutable();
    if (unit != null) {
      _unitList.add(unit);
      _unitMap.put(unit.getCui(), unit);
    }
  }
  
  /**
   * Retrieve a specific GbossUnit
   * @param cui  the cui of the GbossUnit to be retrieved
   * @return  A GbossUnit with the specified cui, or null if no such GbossUnit exists
   */
  public GbossUnit getUnit(String cui) {
    GbossUnit returnValue = (GbossUnit)_unitMap.get(cui);
    return returnValue;
  }
  
  /**
   * Retrieve a list of the GbossUnits contained by this GbossUnits
   * @return a list of the GbossUnits contained by this GbossUnits
   */
  public List getUnits() {
    return _unitList;
  }

  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    _immutable = true;
    //mark each GbossUnit immutable
    Iterator units = _unitList.iterator();
    while (units.hasNext()) {
      ((GbossUnit)units.next()).setImmutable();
    }
    //make the list and map unmodifiable
    _unitList = Collections.unmodifiableList(_unitList);
    _unitMap = Collections.unmodifiableMap(_unitMap);
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
      throw new IllegalStateException("Attempted to modify an immutable GbossUnits: " + this);
    }
  }

}
