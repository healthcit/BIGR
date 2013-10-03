/*
 * Created on Oct 22, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;

/**
 * @author jesielionis
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DbTable {
  
  private String _tableName = null;
  private String _javaConstant = null;
  private List _dbAliases = new ArrayList();
  private List _dbColumns = new ArrayList();
  
  /**
   * Add a DbAlias for this table
   * @param alias
   */
  public void addDbAlias(DbAlias alias) {
    alias.setParent(this);
    _dbAliases.add(alias);
  }
  
  /**
   * Get the list of DbAliases for this table
   * @return List  list of DbAliases
   */
  public List getDbAliases() {
    return _dbAliases;
  }
  
  /**
   * Get a DbAlias for this table
   * @param aliasName  the name of the alias to retrieve
   * @return DbAlias the DbAlias matching the given alias name, if any
   */
  public DbAlias getDbAlias(String aliasName) {
    DbAlias dbAlias = null;
    DbAlias returnValue = null;
    Iterator iterator = _dbAliases.iterator();
    while (iterator.hasNext()) {
      dbAlias = (DbAlias)iterator.next();
      if (dbAlias.getAliasValue().equalsIgnoreCase(aliasName)){
        returnValue = dbAlias;
        break;
      }
    }
    return returnValue;
  }
  
  /**
   * Add a DbColumn for this table
   * @param column
   */
  public void addDbColumn(DbColumn column) {
    _dbColumns.add(column);
  }

  /**
   * Get the list of DbColumns for this table
   * @return List  list of DbColumns
   */
  public List getDbColumns() {
    return _dbColumns;
  }

  /**
   * Get the java constant
   * @return String  the java constant
   */
  public String getJavaConstant() {
    return _javaConstant;
  }

  /**
   * Get the table name
   * @return String  the table name
   */
  public String getTableName() {
    return _tableName;
  }

  /**
   * Set the java constant
   * @param string  the java constant
   */
  public void setJavaConstant(String string) {
    _javaConstant = ApiFunctions.safeTrim(string);
  }

  /**
   * Set the table name
   * @param string  the table name
   */
  public void setTableName(String string) {
    _tableName = ApiFunctions.safeTrim(string);
  }
  
  /**
   * Method to determine if this DbTable has DbColumns associated with it
   * @return true if this DbTable has 1 or more DbColumns, false otherwise
   */
  public boolean hasDbColumns() {
    return (getDbColumns().size() > 0);
  }

}
