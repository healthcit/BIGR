/*
 * Created on Nov 28, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * @author sislam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableColumn {

  private String _tableAlias;
  private String _columnName;

  public TableColumn() {
    _tableAlias = null;
  }
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
    if (!ApiFunctions.isEmpty(getTableAlias()) && !ApiFunctions.isEmpty(getColumnName())) {
      String alias = getTableAlias();
      String columnName = getColumnName();
      DbTable table = data.getDbTableFromAlias(alias);
      // check to see if table exist for the alias
      if (table == null) {
        verified = false;
        StringBuffer buff =
          new StringBuffer(
            "tableColumn: Alias " + alias + " is used, " + "but has no corresponding dbTable element");
        System.out.println("Warning: " + buff.toString());
      }

      // check to see if the column exist for the table
      if (!data.columnExists(alias, columnName)) {
        verified = false;
        StringBuffer buff =
          new StringBuffer(table.getTableName() + " does not have the column " + columnName);
        System.out.println("Warning: " +buff.toString());
      }
    }
    return verified;
  }
}
