package com.gulfstreambio.kc.form.def.data.calculation;

import java.io.Serializable;

import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.gboss.GbossFactory;

public class DataElementDefinitionReference implements Serializable, Operand {
  
  private String _cui;
  private int _xmlIndentLevel = 0;
  
  public DataElementDefinitionReference() {
    super();
  }
  
  /**
   * @return Returns the cui.
   */
  public String getCui() {
    return _cui;
  }
  
  /**
   * @param cui The cui to set.
   */
  public void setCui(String cui) {
    _cui = cui;
  }
  
  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    BigrXmlUtils.writeElementStartTag(buf, "dataElementDefinitionReference", getXmlIndentLevel());
    BigrXmlUtils.writeAttribute(buf, "cui", getCui());
    buf.append("/>");
  }
  
  public int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  public void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
  
  public String getDataType() {
    return GbossFactory.getInstance().getDataElement(getCui()).getDatatype();
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Operand)) {
      return false;
    }
    
    DataElementDefinitionReference dataElementDefinitionReference = (DataElementDefinitionReference) obj;
        
    boolean result = true;
    result = result && ((_cui == null) ? dataElementDefinitionReference._cui == null : _cui.equals(dataElementDefinitionReference._cui));  
    return result;
    
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    result = 37*result + ((_cui == null) ? 0 : _cui.hashCode());
    
    return result;
  }

}
