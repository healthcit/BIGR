package com.gulfstreambio.kc.form.def.data.calculation;


public interface Operand {
  
  public void toXml(StringBuffer buf);
  
  public void setXmlIndentLevel(int indentLevel);
  
  public String getDataType();
  
}
