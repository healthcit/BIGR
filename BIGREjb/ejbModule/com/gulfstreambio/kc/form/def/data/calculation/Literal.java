package com.gulfstreambio.kc.form.def.data.calculation;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;

public class Literal implements Serializable, Operand {
  
  private String _dataType;
  private String _value;
  private int _xmlIndentLevel = 0;
  
  public Literal() {
    super();
  }

  /**
   * @return Returns the dataType.
   */
  public String getDataType() {
    return _dataType;
  }
  /**
   * @return Returns the value.
   */
  public String getValue() {
    return _value;
  }
  /**
   * @param type The dataType to set.
   */
  public void setDataType(String dataType) {
    _dataType = dataType;
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
    BigrXmlUtils.writeElementStartTag(buf, "literal", getXmlIndentLevel());
    BigrXmlUtils.writeAttribute(buf, "dataType", getDataType());
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
    if (!(obj instanceof Literal)) {
      return false;
    }
    
    Literal literal = (Literal) obj;
        
    boolean result = true;
    result = result && ((_dataType == null) ? literal._dataType == null : _dataType.equals(literal._dataType));  
    result = result && ((_value == null) ? literal._value == null : _value.equals(literal._value));  
    return result;
    
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    result = 37*result + ((_dataType == null) ? 0 : _dataType.hashCode());
    result = 37*result + ((_value == null) ? 0 : _value.hashCode());
    
    return result;
  }
  
}
