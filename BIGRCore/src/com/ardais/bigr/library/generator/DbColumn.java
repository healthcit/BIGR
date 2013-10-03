/*
 * Created on Oct 22, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;

/**
 * @author jesielionis
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DbColumn {
  
  private String _columnName = null;
  private String _javaConstant = null;
  private List _dbAliases = new ArrayList();

  /**
   * Get the column name
   * @return String  the column name
   */
  public String getColumnName() {
    return _columnName;
  }

  /**
   * Get the java constant
   * @return String  the java constant
   */
  public String getJavaConstant() {
    return _javaConstant;
  }

  /**
   * Set the column name
   * @param string  the column name
   */
  public void setColumnName(String string) {
    _columnName = ApiFunctions.safeTrim(string);
  }

  /**
   * Set the java constant
   * @param string  the java constant
   */
  public void setJavaConstant(String string) {
    _javaConstant = ApiFunctions.safeTrim(string);
  }
  
  /**
   * Add a DbAlias for this column
   * @param alias
   */
  public void addDbAlias(DbAlias alias) {
    alias.setParent(this);
    _dbAliases.add(alias);
  }
  
  /**
   * Get the list of DbAliases for this column
   * @return List  list of DbAliases
   */
  public List getDbAliases() {
    return _dbAliases;
  }
  
  /**
   * Get a DbAlias for this column
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

}
