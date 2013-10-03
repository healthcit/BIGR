/*
 * Created on Dec 1, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * @author sislam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SelectClause {

  private List _columns = new ArrayList();
  private List _tables = new ArrayList();
  private BigrLibraryMetaData _metaData;
  private WhereClause _whereClause;

  public String getSqlFragment() {
    String sqlFragment = null;
    boolean start = true;
    String selectSql = null;
    String fromSql = null;

    Iterator i = _columns.iterator();
    while (i.hasNext()) {
      ColumnOrValue column = (ColumnOrValue) i.next();

      if (start) {
        start = false;
        if (ApiFunctions.isEmpty(column.getValue())) {
          selectSql = column.getTableAlias() + "." + column.getColumnName();
        }
        else {
          selectSql = column.getValue();
        }
      }
      else {
        if (ApiFunctions.isEmpty(column.getValue())) {
          selectSql = selectSql + ", " + column.getTableAlias() + "." + column.getColumnName();
        }
        else {
          selectSql = selectSql + ", " + column.getValue();
        }
      }
    }

    start = true;
    Iterator j = _tables.iterator();
    while (j.hasNext()) {
      DbAlias table = (DbAlias) j.next();
      DbTable dbTable = _metaData.getDbTableFromAlias(table.getAliasValue());
      if (start) {
        start = false;
        fromSql = dbTable.getTableName() + " " + table.getAliasValue();
      }
      else {
        fromSql = fromSql + ", " + dbTable.getTableName() + " " + table.getAliasValue();
      }
    }

    sqlFragment = "(SELECT " + selectSql + " FROM " + fromSql + " WHERE " + getWhereClauseSql() + ")";
    return sqlFragment;
  }

  public boolean validate(BigrLibraryMetaData data) {
    boolean verified = true;
    _metaData = data;
    Iterator i = _columns.iterator();

      while (i.hasNext()) {
        ColumnOrValue column = (ColumnOrValue) i.next();
        column.validate(data);
      }

      i = _tables.iterator();
      while (i.hasNext()) {
        DbAlias alias = (DbAlias) i.next();
        DbTable table = data.getDbTableFromAlias(alias.getAliasValue());
        // check to see if table exist for the alias
        if (table == null) {
          verified = false;
          StringBuffer buff =
            new StringBuffer(
              "fromTable : Alias " + alias.getAliasValue() + " is used, " + "but has no corresponding dbTable element");
          System.out.println("Warning: " + buff.toString());
        }
      }

    if (_whereClause.validate(data) == false) {
      verified = false;
    }
    return verified;
    }

  public void addColumn(ColumnOrValue column) {
    _columns.add(column);
  }
  /**
   * @return
   */
  public List getColumns() {
    return _columns;
  }

  public void addTable(DbAlias table) {
    _tables.add(table);
  }
  /**
   * @return
   */
  public List getTables() {
    return _tables;
  }


  private String getWhereClauseSql() {
    return getWhereClause().getSqlFragment();
  }

  /**
   * @param clause
   */
  public void setWhereClause(WhereClause clause) {
    _whereClause = clause;
  }

  /**
   * @return
   */
  public WhereClause getWhereClause() {
    return _whereClause;
  }

}
