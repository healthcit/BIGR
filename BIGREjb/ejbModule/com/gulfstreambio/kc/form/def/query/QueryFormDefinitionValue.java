package com.gulfstreambio.kc.form.def.query;

import com.gulfstreambio.kc.form.def.FormDefinition;

public interface QueryFormDefinitionValue {

  /**
   * Returns the type of this value.
   * 
   * @return  The value's type, which will be one of the {@link QueryFormDefinitionValueTypes}
   * contants.
   */
  public String getValueType();
  
  /**
   * Returns the form definition with which this value is associated.
   * 
   * @return  The form definition.
   */
  public FormDefinition getForm();  

  /**
   * Converts this value to its XML representation, and returns that representation.
   * 
   * @return  The XML, as a string.
   */
  public String toXml();

  /**
   * Converts this value to its XML representation, adding it to the supplied 
   * <code>StringBuffer</code>.
   * 
   * @param  buf  the <code>StringBuffer</code>
   */
  public void toXml(StringBuffer buf);
}
