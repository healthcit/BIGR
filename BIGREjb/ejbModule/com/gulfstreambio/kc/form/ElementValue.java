package com.gulfstreambio.kc.form;

/**
 * Holds a single value of a KnowledgeCapture data element.  If the associated element is 
 * multivalued, then multiple instances of this class are required to hold all of the values.  
 * This <code>ElementValue</code> can hold both the value and the "other" value of a data element, 
 * both as strings.
 */
public interface ElementValue {

  /**
   * Returns the value of the element. 
   *  
   * @return  The value.
   */
  public String getValue();

  /**
   * Sets the value of the element.  
   *  
   * @param  value  the value
   */
  public void setValue(String value);

  /**
   * Returns the other value of the element.
   *  
   * @return  The other value.
   */
  public String getValueOther();

  /**
   * Sets the other value of the element. 
   * 
   * @param  value  the other value
   */
  public void setValueOther(String value);

  /**
   * Returns an indication of whether this element has an empty value.
   * 
   * @return <code>true</code> if this element has both an empty value and other value;
   *         <code>false</code> otherwise. 
   */
  public boolean isEmpty();
}
