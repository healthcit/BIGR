package com.ardais.bigr.btx.framework;

/**
 * Represents a single BTX history attribute in a {@link BtxHistoryAttributes} object.  This
 * class exists primarily to assist in parsing XML representations of BtxHistoryAttributes
 * objects.  It makes it simpler to write the XML Digester rules in {@link BtxHistoryObjectParser}.
 */
public class BtxHistoryAttribute {
  private String _name = null;
  private Object _value = null;

  public BtxHistoryAttribute() {
    super();
  }

  /**
   * @return the attribute name.
   */
  public String getName() {
    return _name;
  }

  /**
   * @return the attribute value.
   */
  public Object getValue() {
    return _value;
  }

  /**
   * @param string the attribute name.
   */
  public void setName(String string) {
    _name = string;
  }

  /**
   * @param object the attribute value.
   */
  public void setValue(Object object) {
    _value = object;
  }

}
