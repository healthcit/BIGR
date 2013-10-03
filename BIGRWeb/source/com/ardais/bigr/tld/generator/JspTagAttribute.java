package com.ardais.bigr.tld.generator;

/**
 * Represents an attribute element in a TLD file. 
 */
public class JspTagAttribute implements Comparable {

  private String _name;
  private String _required = "false";
  private String _rtexprvalue = "false";

  /**
   * Creates a new JspTagAttribute.
   */
  public JspTagAttribute() {
    super();
  }

  public String getName() {
    return _name;
  }

  public String getRequired() {
    return _required;
  }

  public String getRtexprvalue() {
    return _rtexprvalue;
  }

  public void setName(String string) {
    _name = string;
  }

  public void setRequired(String string) {
    _required = string;
  }

  public void setRtexprvalue(String string) {
    _rtexprvalue = string;
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object o) {
    JspTagAttribute attr = (JspTagAttribute)o;
    return (getName().compareTo(attr.getName()));
  }

}
