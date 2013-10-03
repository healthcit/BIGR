package com.gulfstreambio.kc.form.def;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;

public class Tag implements Serializable {
  
  private String _type;
  private String _value;
  private int _xmlIndentLevel = 0;
  
  public Tag() {
    super();
  }
  
  public Tag(Tag tag) {
    this(tag.getType(), tag.getValue());
  }

  public Tag(String type, String value) {
    this();
    setType(type);
    setValue(value);
  }

  /**
   * @return Returns the type.
   */
  public String getType() {
    return _type;
  }
  /**
   * @return Returns the value.
   */
  public String getValue() {
    return _value;
  }
  /**
   * @param type The type to set.
   */
  public void setType(String type) {
    _type = type;
  }
  /**
   * @param value The value to set.
   */
  public void setValue(String value) {
    _value = value;
  }
  
  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    BigrXmlUtils.writeElementStartTag(buf, "tag", getXmlIndentLevel());
    BigrXmlUtils.writeAttribute(buf, "type", getType());
    BigrXmlUtils.writeAttribute(buf, "value", getValue());
    buf.append("/>");
  }
  
  public int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  public void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Tag)) {
      return false;
    }
    
    Tag tag = (Tag) obj;
        
    boolean result = true;
    result = result && ((_type == null) ? tag._type == null : _type.equals(tag._type));  
    result = result && ((_value == null) ? tag._value == null : _value.equals(tag._value));  
    return result;
    
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    result = 37*result + ((_type == null) ? 0 : _type.hashCode());
    result = 37*result + ((_value == null) ? 0 : _value.hashCode());
    
    return result;
  }
  
}
