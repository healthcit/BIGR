package com.gulfstreambio.kc.form;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;

/**
 * Abstract base class that implements the <code>ElementValue</code> interface.
 */
public abstract class ElementValueBase implements ElementValue, Serializable {

  /**
   * The element's value as a string. 
   */
  private String _value;

  /**
   * The element's other value.  
   */
  private String _valueOther;

  protected ElementValueBase() {
    super();
  }

  protected ElementValueBase(ElementValueBase value) {
    this();
    setValue(value.getValue());
    setValueOther(value.getValueOther());
  }

  /**
   * Returns the value of the element. 
   *  
   * @return  The value.
   */
  public String getValue() {
    return _value;
  }

  /**
   * Sets the value of the element.  
   *  
   * @param  value  the value
   */
  public void setValue(String value) {
    _value = value;
  }

  /**
   * Returns the other value of the element.
   *  
   * @return  The other value.
   */
  public String getValueOther() {
    return _valueOther;
  }

  /**
   * Sets the other value of the element. 
   * 
   * @param  value  the other value
   */
  public void setValueOther(String value) {
    _valueOther = value;
  }

  /**
   * Returns an indication of whether this element has an empty value.
   * 
   * @return <code>true</code> if this element has an empty value or other value;
   *         <code>false</code> otherwise. 
   */
  public boolean isEmpty() {
    return (ApiFunctions.isEmpty(getValue()) && ApiFunctions.isEmpty(getValueOther()));
  }
}
