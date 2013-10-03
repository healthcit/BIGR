package com.gulfstreambio.gboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.collections.ArrayStack;

import com.ardais.bigr.api.ApiFunctions;

public class GbossValueSet extends GbossConcept {
  
  private List _valueList = new ArrayList();
  private Map _valueMap = new HashMap();
  
  private Map _allValuesMap = new HashMap();
  
  /**
   * Create a new GbossValueSet object.
   */
  public GbossValueSet() {
    super();
  }
  
  /**
   * Create a new GbossValueSet object.
   */
  public GbossValueSet(GbossValueSet source) {
    super();
    populate(source);
    Iterator childIterator = source.getValues().iterator();
    while (childIterator.hasNext()) {
      GbossValue child = (GbossValue)childIterator.next();
      addValue(new GbossValue(child));
    }
  }
  
  private void populate(GbossValueSet source) {
    setCui(source.getCui());
    setDescription(source.getDescription());
    setVIntro(source.getVIntro());
    setVRevised(source.getVRevised());
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
      value.setParentValueSet(this);
    }
  }
  
  protected void registerValue(GbossValue value) {
    checkImmutable();
    if (value != null) {
      _allValuesMap.put(value.getCui(), value);
    }
  }
  
  protected void unregisterValue(GbossValue value) {
    checkImmutable();
    if (value != null) {
      _allValuesMap.remove(value.getCui());
    }
  }
  
  /**
   * Remove values from this GbossValueSet
   * @param cuis  the cuis corresponding to the GbossValue(s) to remove
   */
  public void removeValues(Set cuis) {
    checkImmutable();
    Iterator cuiIterator = cuis.iterator();
    while (cuiIterator.hasNext()) {
      String cui = (String)cuiIterator.next();
      GbossValue value = getValue(cui);
      GbossValue parent = value.getParentValue();
      parent.removeValue(value);
      //if the parent of the removed GbossValue is now childless (i.e. we just removed the
      //last child GbossValue), remove it and all childless ancestors as well
      while (parent != null && ApiFunctions.isEmpty(parent.getValues())) {
        value = parent;
        parent = value.getParentValue();
        if (parent != null) {
          parent.removeValue(value);
        }
        else {
          //we've reached the topmost node, so remove it from this value set
          this.removeValue(value);
        }
      }
    }
  }
  
  /**
   * Remove a GbossValue
   * @param value  the GbossValue to remove
   */
  public void removeValue(GbossValue value) {    
    checkImmutable();
    if (value != null) {
      _valueList.remove(value);
      _valueMap.remove(value.getCui());
      value.setParentValueSet(null);
    }
  }
  
  /**
   * Retrieve a specific GbossValue
   * @param cui  the cui of the GbossValue to be retrieved
   * @return  A GbossValue with the specified cui, or null if no such GbossValue exists
   */
  public GbossValue getValue(String cui) {
    GbossValue returnValue = null;
    if (!ApiFunctions.isEmpty(cui)) {
      returnValue = (GbossValue) _allValuesMap.get(cui);
    }
    return returnValue;
  }
  
  /**
   * Retrieve a list of the GbossValues contained by this GbossValueSet
   * @return a list of the GbossValues contained by this GbossValueSet
   */
  public List getValues() {
    return _valueList;
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    super.setImmutable();
    //mark each GbossValue immutable
    Iterator values = _valueList.iterator();
    while (values.hasNext()) {
      ((GbossValue)values.next()).setImmutable();
    }
    //make the lists and maps unmodifiable
    _valueList = Collections.unmodifiableList(_valueList);
    _valueMap = Collections.unmodifiableMap(_valueMap);
    _allValuesMap = Collections.unmodifiableMap(_allValuesMap);
  }
  
  public boolean containsValue(String cui) {
    return (getValue(cui) != null);
  }

  /**
   * This iterator supplies each value in the value set exactly once, in
   * depth-first order.
   */
  public Iterator depthFirstIterator() {
    return new DepthFirstIterator();
  }

  /**
   * This iterator supplies each value in the GbossValueSet once, in
   * depth-first order.
   */
  private class DepthFirstIterator implements Iterator {
    private ArrayStack _valueStack = new ArrayStack();
    private Set _markedValues = new HashSet(2 * getValues().size() + 1);

    DepthFirstIterator() {
      Iterator valueIterator = getValues().iterator();
      while (valueIterator.hasNext()) {
        Object value = valueIterator.next();
        if (!_markedValues.contains(value)) {
          _valueStack.push(value);
          _markedValues.add(value);
        }
      }
    }

    public boolean hasNext() {
      return (!_valueStack.empty());
    }

    public Object next() {
      if (_valueStack.empty()) {
        throw new NoSuchElementException();
      }

      Object nextObject = _valueStack.pop();

      Iterator children = ((GbossValue)nextObject).getValues().iterator();
      while (children.hasNext()) {
        Object childValue = children.next();
        if (!_markedValues.contains(childValue)) {
          _valueStack.push(childValue);
          _markedValues.add(childValue);
        }
      }

      return nextObject;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
