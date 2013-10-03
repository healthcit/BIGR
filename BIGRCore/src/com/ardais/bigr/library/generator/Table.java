/*
 * Created on Oct 27, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

/**
 * @author jesielionis
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Table {
  private String _tableAlias;
  private String _ordering;
  private BigrLibraryMetaData _parent;
  private final static String KEYCONSTANT = "KEY_";

  /**
   * Get the key name
   * @return String  the key name
   */
  public String getKeyName() {
    DbAlias dbAlias = getParent().getDbTableFromAlias(getTableAlias()).getDbAlias(getTableAlias());
    String javaConstant = dbAlias.getJavaConstant().toUpperCase();
    String keyName = Table.KEYCONSTANT + javaConstant;
    return keyName;
  }

  /**
   * Get the ordering
   * @return String  the ordering
   */
  public String getOrdering() {
    return _ordering;
  }

  /**
   * Get the table alias
   * @return String  the table alias
   */
  public String getTableAlias() {
    return _tableAlias;
  }

  /**
   * Set the ordering
   * @param string  the ordering
   */
  public void setOrdering(String string) {
    _ordering = string;
  }

  /**
   * Set the table alias
   * @param string  the table alias
   */
  public void setTableAlias(String string) {
    _tableAlias = string;
  }

  /**
   * @return
   */
  public BigrLibraryMetaData getParent() {
    return _parent;
  }

  /**
   * @param data
   */
  public void setParent(BigrLibraryMetaData data) {
    _parent = data;
  }

}
