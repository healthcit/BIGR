package com.gulfstreambio.gboss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;

public class GbossValue extends GbossConcept {
  
  private String _longDescription = null;
  private String _comment = null;
  private boolean _otherValue = false;
  private boolean _noValue = false;
  private List _valueList = new ArrayList();
  private Map _valueMap = new HashMap();
  private GbossValue _parentValue = null;
  private GbossValueSet _parentValueSet = null;

  /**
   * Create a new GbossValue object.
   */
  public GbossValue() {
    super();
  }
  
  public GbossValue(GbossValue source) {
    super();
    populate(source);
    Iterator childIterator = source.getValues().iterator();
    while (childIterator.hasNext()) {
      GbossValue child = (GbossValue)childIterator.next();
      addValue(new GbossValue(child));
    }
  }
  
  public void populate(GbossValue source) {
    setComment(source.getComment());
    setCui(source.getCui());
    setDescription(source.getDescription());
    setLongDescription(source.getLongDescription());
    setVIntro(source.getVIntro());
    setVRevised(source.getVRevised());
    setOtherValue(source.isOtherValue());
    setNoValue(source.isNoValue());
  }
  
  public String toHtml() {
    StringBuffer buff = new StringBuffer(100);
    buff.append(super.toHtml());
    if (isOtherValue()) {
      buff.append(", ");
      buff.append("otherValue = ");
      buff.append(isOtherValue());
    }
    if (isNoValue()) {
      buff.append(", ");
      buff.append("noValue = ");
      buff.append(isNoValue());
    }
    if (!ApiFunctions.isEmpty(getLongDescription())) {
      buff.append(", ");
      buff.append("long description = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getLongDescription()));
    }
    if (!ApiFunctions.isEmpty(getComment())) {
      buff.append(", ");
      buff.append("comment = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getComment()));
    }
    return buff.toString();
  }

  /**
   * @return Returns the comment.
   */
  public String getComment() {
    return _comment;
  }
  
  /**
   * @param comment The comment to set.
   */
  public void setComment(String comment) {
    checkImmutable();
    _comment = comment;
  }
  
  /**
   * @return Returns the longDescription.
   */
  public String getLongDescription() {
    return _longDescription;
  }
  
  /**
   * @param longDescription The longDescription to set.
   */
  public void setLongDescription(String longDescription) {
    checkImmutable();
    _longDescription = longDescription;
  }
  
  /**
   * @return Returns the noValue.
   */
  public boolean isNoValue() {
    return _noValue;
  }
  
  /**
   * @param noValue The noValue to set.
   */
  public void setNoValue(boolean noValue) {
    checkImmutable();
    _noValue = noValue;
  }
  
  /**
   * @return Returns the otherValue.
   */
  public boolean isOtherValue() {
    return _otherValue;
  }
  
  /**
   * @param otherValue The otherValue to set.
   */
  public void setOtherValue(boolean otherValue) {
    checkImmutable();
    _otherValue = otherValue;
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
      value.setParentValue(this);
      value.setParentValueSet(this.getParentValueSet());
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
      value.setParentValue(null);
      value.setParentValueSet(null);
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
   * @return Returns the parentValue.
   */
  public GbossValue getParentValue() {
    return _parentValue;
  }
  
  /**
   * @param parentValue The parentValue to set.
   */
  public void setParentValue(GbossValue parentValue) {
    checkImmutable();
    _parentValue = parentValue;
  }

  /**
   * @return Returns the parentValueSet.
   */
  public GbossValueSet getParentValueSet() {
    return _parentValueSet;
  }
  
  /**
   * @param parentValueSet The parentValueSet to set.
   */
  public void setParentValueSet(GbossValueSet parentValueSet) {
    checkImmutable();
    //unregister this value (and its children) from its current value set, if any
    unregisterValueFromValueSet();
    //register this value and its children with the new value set
    registerValueWithValueSet(parentValueSet);
  }
  
  private void unregisterValueFromValueSet() {
    //unregister this value and its children from its existing parent value set, if any
    if (_parentValueSet != null) {
      _parentValueSet.unregisterValue(this);
    }
    _parentValueSet = null;
    Iterator iterator = getValues().iterator();
    while (iterator.hasNext()) {
      ((GbossValue)iterator.next()).unregisterValueFromValueSet();
    }
  }
  
  private void registerValueWithValueSet(GbossValueSet valueSet) {
    if (valueSet != null) {
      valueSet.registerValue(this);
    }
    _parentValueSet = valueSet;
    Iterator iterator = getValues().iterator();
    while (iterator.hasNext()) {
      ((GbossValue)iterator.next()).registerValueWithValueSet(valueSet);
    }
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
    //make the list and map unmodifiable
    _valueList = Collections.unmodifiableList(_valueList);
    _valueMap = Collections.unmodifiableMap(_valueMap);
  }
}
