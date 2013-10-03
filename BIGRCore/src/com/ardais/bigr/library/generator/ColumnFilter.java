/*
 * Created on Nov 24, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import com.ardais.bigr.api.ApiException;

/**
 * @author sislam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class ColumnFilter extends Filter {

  private String _tableAlias;
  private String _columnName;

  /**
   * @return
   */
  public String getColumnName() {
    return _columnName;
  }

  /**
   * @return
   */
  public String getTableAlias() {
    return _tableAlias;
  }

  /**
   * @param string
   */
  public void setColumnName(String string) {
    _columnName = string;
  }

  /**
   * @param string
   */
  public void setTableAlias(String string) {
    _tableAlias = string;
  }

  public boolean validate(BigrLibraryMetaData data) {
    boolean verified = true;
    String alias = getTableAlias();
    String columnName = getColumnName();
    DbTable table = data.getDbTableFromAlias(alias);
    // check to see if table exist for the alias
    if (table == null) {
      verified = false;
      StringBuffer buff =
        new StringBuffer(
          "Warning: "
            + this.getClass().getName()
            + " Alias "
            + alias
            + " is used, "
            + "but has no corresponding dbTable element");
      System.out.println(buff.toString());
    }

    // check to see if the column exist for the table
    if (!data.columnExists(alias, columnName)) {
      verified = false;
      StringBuffer buff =
        new StringBuffer(
          "Warning: "
            + this.getClass().getName()
            + table.getTableName()
            + " does not have the column "
            + columnName);
      System.out.println(buff.toString());
    }
    return verified;
  }
}
