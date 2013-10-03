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
public class Column {
  
  private String _tableAlias;
  private String _columnAlias;
  private String _ordering;
  private BigrLibraryMetaData _parent;
  private final static String KEYCONSTANT = "KEY_";

  /**
   * Get the key name
   * @return String  the key name
   */
  public String getKeyName() {
    DbAlias dbAlias = getParent().getDbColumnFromAlias(getColumnAlias()).getDbAlias(getColumnAlias());
    String javaConstant = dbAlias.getJavaConstant().toUpperCase();
    String keyName = Column.KEYCONSTANT + javaConstant;
    return keyName;
  }

  /**
   * Get the column alias
   * @return String  the column alias
   */
  public String getColumnAlias() {
    return _columnAlias;
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
   * Set the column alias
   * @param string  the column alias
   */
  public void setColumnAlias(String string) {
    _columnAlias = string;
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
