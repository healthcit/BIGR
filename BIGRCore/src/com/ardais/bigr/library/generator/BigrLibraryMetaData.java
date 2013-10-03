/*
 * Created on Oct 22, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import java.io.File;
import java.io.FileWriter;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * @author jesielionis
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BigrLibraryMetaData {

  private String _version = null;
  private List _dbTables = new ArrayList();
  private List _tables = new ArrayList();
  private List _columns = new ArrayList();
  private List _columnCounts = new ArrayList();
  private List _filters = new ArrayList();
  private Map _tableAliasToTable = new HashMap();
  private Map _columnAliasToColumn = new HashMap();

  /**
   * Constructor for BigrLibraryMetaData.
   */
  public BigrLibraryMetaData() {
    super();
  }

  /**
   * Holds the column meta-data. 
   */
  public static Map _columnsMap = new HashMap();

  /**
   * Holds the table meta-data. 
   */
  public static Map _tablesMap = new HashMap();

  /**
   * Holds the filter and  meta-data. 
   */
  public static Map _filtersMap = new HashMap();

  /**
   * Returns the version of the data element taxonomy specification.
   * @return String
   */
  public String getVersion() {
    return _version;
  }

  /**
   * Sets the version of the data element taxonomy specification.
   * @param version The version to set
   */
  public void setVersion(String version) {
    _version = ApiFunctions.safeTrim(version);
  }

  /**
   * Returns the List of DbTables contained in this object
   * @return List  the List of DbTables
   */
  public List getDbTables() {
    return _dbTables;
  }

  /**
   * Returns the List of Tables contained in this object
   * @return List  the List of Tables
   */
  public List getTables() {
    return _tables;
  }

  /**
   * Returns the List of Columns contained in this object
   * @return List  the List of Columns
   */
  public List getColumns() {
    return _columns;
  }

  /**
   * Returns the List of ColumnCounts contained in this object
   * @return List  the List of ColumnCounts
   */
  public List getColumnCounts() {
    return _columnCounts;
  }

  /**
   * Add a DbTable to the BigrLibraryMetaData.
   */
  public void addDbTable(DbTable table) {
    _dbTables.add(table);
    //populate the alias to table map
    Iterator tableAliases = table.getDbAliases().iterator();
    while (tableAliases.hasNext()) {
      String alias = ((DbAlias) tableAliases.next()).getAliasValue();
      _tableAliasToTable.put(alias, table);
    }
    //populate the alias to column map
    Iterator columns = table.getDbColumns().iterator();
    while (columns.hasNext()) {
      DbColumn column = (DbColumn) columns.next();
      Iterator columnAliases = column.getDbAliases().iterator();
      while (columnAliases.hasNext()) {
        String alias = ((DbAlias) columnAliases.next()).getAliasValue();
        _columnAliasToColumn.put(alias, column);
      }
    }
  }

  /**
   * Add a Table to the BigrLibraryMetaData.
   */
  public void addTable(Table table) {
    table.setParent(this);
    _tables.add(table);
  }

  /**
   * Add a Column to the BigrLibraryMetaData.
   */
  public void addColumn(Column column) {
    column.setParent(this);
    _columns.add(column);
  }

  /**
   * Add a ColumnCount to the BigrLibraryMetaData.
   */
  public void addColumnCount(ColumnCount columnCount) {
    columnCount.setParent(this);
    _columnCounts.add(columnCount);
  }

  /**
   * Given a table alias, return the corresponding DbTable.
   */
  public DbTable getDbTableFromAlias(String alias) {
    DbTable table = (DbTable) _tableAliasToTable.get(alias);
    return table;
  }

  /*
   * Given the ordering get column from the list
   */

  public Column getColumn(String ordering) {
    Iterator i = _columns.iterator();

    while (i.hasNext()) {
      Column col = (Column) i.next();
      if (col.getOrdering().equals(ordering)) {
        return col;
      }
    }
    return null;
  }

  /*
   * Given the ordering get table from the list
   */

  public Table getTable(String ordering) {
    Iterator i = _tables.iterator();

    while (i.hasNext()) {
      Table table = (Table) i.next();
      if (table.getOrdering().equals(ordering)) {
        return table;
      }
    }
    return null;
  }

  /*
   * Given the ordering get filter from the list
   */

  public Filter getFilter(String keyName) {


    Iterator i = _filters.iterator();

    while (i.hasNext()) {
      Filter filter = (Filter) i.next();
        if (filter.getKeyName().equals(keyName)) {
          return filter;
        }
    }
    return null;
  }
  /**
   * Given a column alias, return the corresponding DbColumn.
   */
  public DbColumn getDbColumnFromAlias(String alias) {
    DbColumn column = (DbColumn) _columnAliasToColumn.get(alias);
    return column;
  }

  /**
      * Determine whether the BigrLibraryMetaData is valid.  This method doesn't
      * return a value, it throws a runtime exception if the BigrLibraryMetaData 
      * is invalid but otherwise it just returns normally.
      * 
      * @exception RuntimeException if the BigrLibraryMetaData is invalid
      */
  public boolean checkValidity() {
    boolean passedVerification = true;
    if (verifyCommonInfoIsValid() == false) {
      passedVerification = false;
    }
    if (verifyDbTablesAreValid() == false) {
      passedVerification = false;
    }
    if (verifyDbColumnsAreValid() == false) {
      passedVerification = false;
    }
    if (verifyTablesAreValid() == false) {
      passedVerification = false;
    }
    if (verifyColumnsAreValid() == false) {
      passedVerification = false;
    }
    if (verifyFilterKeyNamesAndOrderingsAreUnique() == false) {
      passedVerification = false;
    }
    if (verifyAliasAndColumnInfoForFilters() == false) {
      passedVerification = false;
    }
    //<!--    e) keyName attributes have unique values -->
    //<!--    f) ordering attributes have unique values -->
    //<!--    g) tableAlias attributes reference table aliases -->
    //<!--    h) columnAlias attributes reference column aliases -->
    //<!--    h) columnName attributes reference columns -->
    return passedVerification;
  } /* 
                * Method to verify that common information is valid
                */
  private boolean verifyCommonInfoIsValid() {
    boolean verified = true;
    if (verifyJavaConstantsAreUnique() == false) {
      verified = false;
    }
    if (verifyAliasValuesAreUnique() == false) {
      verified = false;
    }
    return verified;
  } /* 
                * Method to verify that dbTable information is valid
                */
  private boolean verifyDbTablesAreValid() {
    return verifyTableNamesAreUnique();
  } /* 
                * Method to verify that dbColumn information is valid
                */
  private boolean verifyDbColumnsAreValid() {
    boolean verified = true;
    if (verifyColumnNamesAreUniqueWithinTable() == false) {
      verified = false;
    }
    warnIfDbColumnHasMultipleAliases();
    return verified;
  } /* 
                * Method to verify that table information is valid
                */
  private boolean verifyTablesAreValid() {
    boolean verified = true;
    if (verifyTableAliases() == false) {
      verified = false;
    }
    if (verifyTableKeyNamesAndOrderingsAreUnique() == false) {
      verified = false;
    }
    return verified;
  } /* 
                * Method to verify that column information is valid
                */
  private boolean verifyColumnsAreValid() {
    boolean verified = true;
    //for the purposes of validation, Columns and ColumnCounts
    //should be validated together.  So, combine the 2 lists into one and pass it
    //into the validation routines
    List combinedList = new ArrayList(_columns);
    combinedList.addAll(_columnCounts);
    if (verifyColumnAliases(combinedList) == false) {
      verified = false;
    }
    if (verifyColumnKeyNamesAndOrderingsAreUnique(combinedList) == false) {
      verified = false;
    }
    return verified;
  } /*
              * Method to verify that table aliases refer to actual dbTables
              */
  private boolean verifyTableAliases() {
    boolean verified = true;
    Iterator iterator = _tables.iterator();
    ArrayList unknownTables = new ArrayList();
    while (iterator.hasNext()) {
      String tableAlias = ((Table) iterator.next()).getTableAlias();
      //table aliases should never be null or all whitespace, so if that's the case
      //throw an exception
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(tableAlias))) {
        verified = false;
        System.out.println("Warning: Empty table alias found.");
      }
      DbTable table = getDbTableFromAlias(tableAlias);
      if (table == null) {
        unknownTables.add(tableAlias);
      }
    }
    if (!unknownTables.isEmpty()) {
      StringBuffer buff =
        new StringBuffer("The following aliases are used in Tables, but have no corresponding dbTable element: ");
      Iterator aliasIterator = unknownTables.iterator();
      boolean isFirst = true;
      while (aliasIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) aliasIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    return verified;
  } /* 
              * Method to verify that all table name attributes in the XML document
              * are unique across the document.
              */
  private boolean verifyTableKeyNamesAndOrderingsAreUnique() {
    boolean verified = true;
    Iterator iterator = _tables.iterator();
    HashMap keys = new HashMap();
    HashMap orderings = new HashMap();
    ArrayList duplicateKeys = new ArrayList();
    ArrayList duplicateOrderings = new ArrayList();
    while (iterator.hasNext()) {
      Table table = (Table) iterator.next();
      String key = table.getKeyName();
      String ordering = table.getOrdering();
      //keys and orderings should never be null or all whitespace, so if that's the case
      //throw an exception
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(key))) {
        verified = false;
        System.out.println("Warning: Empty table keyname found.");
      }
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(ordering))) {
        verified = false;
        System.out.println("Warning: Empty table ordering found.");
      }
      if (keys.containsKey(key)) {
        duplicateKeys.add(key);
      }
      else {
        keys.put(key, key);
      }
      if (orderings.containsKey(ordering)) {
        duplicateOrderings.add(ordering);
      }
      else {
        orderings.put(ordering, ordering);
      }
    }
    if (!duplicateKeys.isEmpty()) {
      StringBuffer buff = new StringBuffer("The following Table keys are duplicated: ");
      Iterator keyIterator = duplicateKeys.iterator();
      boolean isFirst = true;
      while (keyIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) keyIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    if (!duplicateOrderings.isEmpty()) {
      StringBuffer buff = new StringBuffer("The following Table orderings are duplicated: ");
      Iterator valueIterator = duplicateOrderings.iterator();
      boolean isFirst = true;
      while (valueIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) valueIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    return verified;
  }
  /*
   * Method to verify that column aliases refer to appropriate objects
   */
  private boolean verifyColumnAliases(List list) {
    boolean verified = true;
    Iterator iterator = list.iterator();
    ArrayList unknownTables = new ArrayList();
    ArrayList unknownColumns = new ArrayList();
    while (iterator.hasNext()) {
      Column column = (Column) iterator.next();
      String tableAlias = column.getTableAlias();
      String columnAlias = column.getColumnAlias();
      //table and column aliases should never be null or all whitespace, so if that's the case
      //throw an exception
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(tableAlias))) {
        verified = false;
        System.out.println("Warning: Empty Column/ColumnCount table alias found.");
      }
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(columnAlias))) {
        verified = false;
        System.out.println("Warning: Empty Column/ColumnCount column alias found.");
      } //make sure the table alias refers to a table
      DbTable dbTable = getDbTableFromAlias(tableAlias);
      if (dbTable == null) {
        unknownTables.add(tableAlias);
      } //make sure the column alias refers to a column
      DbColumn dbColumn = getDbColumnFromAlias(columnAlias);
      if (dbColumn == null) {
        unknownColumns.add(columnAlias);
      }
    }
    if (!unknownTables.isEmpty()) {
      StringBuffer buff =
        new StringBuffer("The following table aliases are used in Columns/ColumnCounts, but have no corresponding dbTable element: ");
      Iterator aliasIterator = unknownTables.iterator();
      boolean isFirst = true;
      while (aliasIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) aliasIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    if (!unknownColumns.isEmpty()) {
      StringBuffer buff =
        new StringBuffer("The following column aliases are used in Columns/ColumnCounts, but have no corresponding dbColumn element: ");
      Iterator aliasIterator = unknownColumns.iterator();
      boolean isFirst = true;
      while (aliasIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) aliasIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    return verified;
  }




  /* 
   * Method to verify that all table name attributes in the XML document
   * are unique across the document.
   */
  private boolean verifyColumnKeyNamesAndOrderingsAreUnique(List list) {
    boolean verified = true;
    Iterator iterator = list.iterator();
    HashMap keys = new HashMap();
    HashMap orderings = new HashMap();
    ArrayList duplicateKeys = new ArrayList();
    ArrayList duplicateOrderings = new ArrayList();
    while (iterator.hasNext()) {
      Column column = (Column) iterator.next();
      String key = column.getKeyName();
      String ordering = column.getOrdering();
      //keys and values should never be null or all whitespace, so if that's the case
      //throw an exception
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(key))) {
        verified = false;
        System.out.println("Warning: Empty Column/ColumnCount keyName found.");
      }
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(ordering))) {
        verified = false;
        System.out.println("Warning: Empty Column/ColumnCount ordering found.");
      }
      if (keys.containsKey(key)) {
        duplicateKeys.add(key);
      }
      else {
        keys.put(key, key);
      }
      if (orderings.containsKey(ordering)) {
        duplicateOrderings.add(ordering);
      }
      else {
        orderings.put(ordering, ordering);
      }
    }
    if (!duplicateKeys.isEmpty()) {
      StringBuffer buff =
        new StringBuffer("The following Column/ColumnCount keys are duplicated: ");
      Iterator keyIterator = duplicateKeys.iterator();
      boolean isFirst = true;
      while (keyIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) keyIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    if (!duplicateOrderings.isEmpty()) {
      StringBuffer buff =
        new StringBuffer("The following Column/ColumnCount orderings are duplicated: ");
      Iterator valueIterator = duplicateOrderings.iterator();
      boolean isFirst = true;
      while (valueIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) valueIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    return verified;
  } 
  
  /* 
    * Method to verify that all table name attributes in the XML document
    * are unique across the document.
    */
   private boolean verifyTableNamesAreUnique() {
     boolean verified = true;
    Iterator tableIterator = _dbTables.iterator();
    HashMap names = new HashMap();
    ArrayList duplicateNames = new ArrayList();
    while (tableIterator.hasNext()) {
      DbTable table = (DbTable) tableIterator.next();
      String name = table.getTableName();
      //table names should never be null or all whitespace, so if that's the case
      //throw an exception
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(name))) {
        verified = false;
        System.out.println("Warning: Empty table name found.");
      }
      if (names.containsKey(name)) {
        duplicateNames.add(name);
      }
      else {
        names.put(name, name);
      }
    }
    if (!duplicateNames.isEmpty()) {
      StringBuffer buff = new StringBuffer("The following table names are duplicated: ");
      Iterator nameIterator = duplicateNames.iterator();
      boolean isFirst = true;
      while (nameIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) nameIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    return verified;
  } /* 
              * Method to verify that all column name attributes in the XML document
              * are unique within a table.
              */
  private boolean verifyColumnNamesAreUniqueWithinTable() {
    boolean verified = true;
    Iterator tableIterator = _dbTables.iterator();
    HashMap duplicateNames = new HashMap();
    while (tableIterator.hasNext()) {
      DbTable table = (DbTable) tableIterator.next();
      HashMap names = new HashMap();
      ArrayList tableDuplicateNames = new ArrayList();
      Iterator columnIterator = table.getDbColumns().iterator();
      while (columnIterator.hasNext()) {
        DbColumn column = (DbColumn) columnIterator.next();
        String name = column.getColumnName();
        //column names should never be null or all whitespace, so if that's the case
        //throw an exception
        if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(name))) {
          verified = false;
          System.out.println("Warning: Empty column name found.");
        }
        if (names.containsKey(name)) {
          tableDuplicateNames.add(name);
        }
        else {
          names.put(name, name);
        }
      } //if this table had any duplicate names, add them to the overall list of duplicates
      if (!tableDuplicateNames.isEmpty()) {
        Iterator tableDuplicateNamesIterator = tableDuplicateNames.iterator();
        boolean isFirst = true;
        StringBuffer buff = new StringBuffer(50);
        while (tableDuplicateNamesIterator.hasNext()) {
          String duplicateName = (String) tableDuplicateNamesIterator.next();
          if (!isFirst) {
            buff.append(", ");
          }
          buff.append(duplicateName);
          isFirst = false;
        }
        duplicateNames.put(table.getTableName(), buff.toString());
      }
    }
    if (!duplicateNames.isEmpty()) {
      StringBuffer buff =
        new StringBuffer("The following column names are duplicated within a table: ");
      Iterator iterator = duplicateNames.keySet().iterator();
      boolean isFirst = true;
      while (iterator.hasNext()) {
        if (!isFirst) {
          buff.append("\r\n");
        }
        String key = (String) iterator.next();
        buff.append((String) duplicateNames.get(key));
        buff.append(" in table ");
        buff.append(key);
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    return verified;
  } /* 
              * Method to warn caller if a dbColumn has more than one alias.  Most columns should have
              * one alias, and it's unusual to have more than one.
              */
  private void warnIfDbColumnHasMultipleAliases() {
    Iterator tableIterator = _dbTables.iterator();
    while (tableIterator.hasNext()) {
      DbTable table = (DbTable) tableIterator.next();
      Iterator columnIterator = table.getDbColumns().iterator();
      while (columnIterator.hasNext()) {
        DbColumn column = (DbColumn) columnIterator.next();
        if (column.getDbAliases().size() > 1) {
          StringBuffer buff = new StringBuffer(100);
          buff.append("WARNING: Column ");
          buff.append(column.getColumnName());
          buff.append(" in table ");
          buff.append(table.getTableName());
          buff.append(
            " has multiple aliases.  This is an unusual occurrence, so please verify the data is correct.");
          System.out.println(buff.toString());
        }
      }
    }
  } /* 
              * Method to verify that all java constant attributes in the XML document
              * are unique across the document.
              */
  private boolean verifyJavaConstantsAreUnique() {
    boolean verified = true;
    Iterator tableIterator = _dbTables.iterator();
    HashMap objectConstants = new HashMap();
    HashMap aliasConstants = new HashMap();
    ArrayList duplicateObjectConstants = new ArrayList();
    ArrayList duplicateAliasConstants = new ArrayList();
    while (tableIterator.hasNext()) {
      DbTable table = (DbTable) tableIterator.next();
      String objectConstant = table.getJavaConstant();
      //check the table java constant
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(objectConstant))) {
        verified = false;
        System.out.println(
          "Warning: Empty javaConstant found for table " + table.getTableName() + ".");
      }
      if (objectConstants.containsKey(objectConstant)) {
        duplicateObjectConstants.add(objectConstant);
      }
      else {
        objectConstants.put(objectConstant, objectConstant);
      } //now check the table dbAliases.
      Iterator tableDbAliasIterator = table.getDbAliases().iterator();
      while (tableDbAliasIterator.hasNext()) {
        DbAlias tableAlias = (DbAlias) tableDbAliasIterator.next();
        String aliasConstant = tableAlias.getJavaConstant();
        if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(aliasConstant))) {
          verified = false;
          System.out.println(
            "Warning: Table "
              + table.getTableName()
              + " has an alias with a blank javaConstant value.");
        }
        else {
          if (aliasConstants.containsKey(aliasConstant)) {
            duplicateAliasConstants.add(aliasConstant);
          }
          else {
            aliasConstants.put(aliasConstant, aliasConstant);
          }
        }
      }
      Iterator columnIterator = table.getDbColumns().iterator();
      while (columnIterator.hasNext()) {
        DbColumn column = (DbColumn) columnIterator.next();
        objectConstant = column.getJavaConstant();
        //check the column java constant
        if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(objectConstant))) {
          verified = false;
          System.out.println(
            "Warning: Empty javaConstant found for column "
              + column.getColumnName()
              + " in table "
              + table.getTableName()
              + ".");
        }
        if (objectConstants.containsKey(objectConstant)) {
          duplicateObjectConstants.add(objectConstant);
        }
        else {
          objectConstants.put(objectConstant, objectConstant);
        } //now check the column dbAliases.
        Iterator columnDbAliasIterator = column.getDbAliases().iterator();
        while (columnDbAliasIterator.hasNext()) {
          DbAlias columnAlias = (DbAlias) columnDbAliasIterator.next();
          String aliasConstant = columnAlias.getJavaConstant();
          if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(aliasConstant))) {
            verified = false;
            System.out.println(
              "Warning: Column "
                + column.getColumnName()
                + " in table "
                + table.getTableName()
                + " has an alias with a blank javaConstant value.");
          }
          else {
            if (aliasConstants.containsKey(aliasConstant)) {
              duplicateAliasConstants.add(aliasConstant);
            }
            else {
              aliasConstants.put(aliasConstant, aliasConstant);
            }
          }
        }
      }
    }
    if (!duplicateObjectConstants.isEmpty()) {
      StringBuffer buff =
        new StringBuffer("The following dbTable/dbColumn javaConstants are duplicated: ");
      Iterator constantIterator = duplicateObjectConstants.iterator();
      boolean isFirst = true;
      while (constantIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) constantIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    if (!duplicateAliasConstants.isEmpty()) {
      StringBuffer buff = new StringBuffer("The following dbAlias javaConstants are duplicated: ");
      Iterator constantIterator = duplicateAliasConstants.iterator();
      boolean isFirst = true;
      while (constantIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) constantIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    return verified;
  }

  private boolean verifyFilterKeyNamesAndOrderingsAreUnique() {
    boolean verified = true;
    if (verifyFilterKeyNamesAreUnique() == false) {
      verified = false;
    }
    if (verifyFilterOrderingsAreUnique() == false) {
      verified = false;
    }
    return verified;
  }

  private boolean verifyFilterOrderingsAreUnique() {
    boolean verified = true;
    Iterator filterIterator = _filters.iterator();
    HashMap keyOrderings = new HashMap();
    ArrayList keyOrderingDuplicateValues = new ArrayList();
    while (filterIterator.hasNext()) {
      Filter filter = (Filter) filterIterator.next();
      String keyOrdering = filter.getOrdering();
      if (keyOrderings.containsKey(keyOrdering)) {
        keyOrderingDuplicateValues.add(keyOrdering);
      }
      else {
        keyOrderings.put(keyOrdering, keyOrdering);
      }
    }

    if (!keyOrderingDuplicateValues.isEmpty()) {
      StringBuffer buff = new StringBuffer("The following filter Orderings are duplicated: ");
      Iterator iterator = keyOrderingDuplicateValues.iterator();
      boolean isFirst = true;
      while (iterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) iterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    return verified;
  } /*
              * Method to verify that the filter key names are unique.
              */
  private boolean verifyFilterKeyNamesAreUnique() {
    boolean verified = true;
    Iterator filterIterator = _filters.iterator();
    HashMap keyNames = new HashMap();
    ArrayList keyNameDuplicateValues = new ArrayList();
    while (filterIterator.hasNext()) {
      Filter filter = (Filter) filterIterator.next();
      String keyName = filter.getKeyName();
      if (keyNames.containsKey(keyName)) {
        keyNameDuplicateValues.add(keyName);
      }
      else {
        keyNames.put(keyName, keyName);
      }
    }
    if (!keyNameDuplicateValues.isEmpty()) {
      StringBuffer buff = new StringBuffer("The following filter keyNames are duplicated: ");
      Iterator iterator = keyNameDuplicateValues.iterator();
      boolean isFirst = true;
      while (iterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) iterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    return verified;
  } /*
              * 
              */
  private boolean verifyAliasAndColumnInfoForFilters() {
    boolean verified = true;
    Iterator iterator = _filters.iterator();
    while (iterator.hasNext()) {
      Filter filter = (Filter) iterator.next();
      if (filter.validate(this) == false) {
        verified = false;  
      }
    }
    return verified;
  } 
  
  /* 
              * Method to verify that all aliasValue attributes in the XML document
              * are unique within an element type across the document.  That is, a table dbAlias
              * and a column dbAlias can have the same value, but there cannot be more than
              * one table dbAlias with a given value nor more than one column dbAlias with a
              * given value.
              */
  private boolean verifyAliasValuesAreUnique() {
    boolean verified = true;
    Iterator tableIterator = _dbTables.iterator();
    HashMap tableAliasValues = new HashMap();
    ArrayList tableDuplicateAliasValues = new ArrayList();
    HashMap columnAliasValues = new HashMap();
    ArrayList columnDuplicateAliasValues = new ArrayList();
    while (tableIterator.hasNext()) {
      DbTable table = (DbTable) tableIterator.next();
      Iterator tableDbAliasIterator = table.getDbAliases().iterator();
      while (tableDbAliasIterator.hasNext()) {
        DbAlias tableAlias = (DbAlias) tableDbAliasIterator.next();
        String tableAliasValue = tableAlias.getAliasValue();
        if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(tableAliasValue))) {
          verified = false;
          System.out.println(
            "Warning: Table "
              + table.getTableName()
              + " has dbAlias with a blank aliasValue value.");
        }
        if (tableAliasValues.containsKey(tableAliasValue)) {
          tableDuplicateAliasValues.add(tableAliasValue);
        }
        else {
          tableAliasValues.put(tableAliasValue, tableAliasValue);
        }
      } //
      Iterator columnIterator = table.getDbColumns().iterator();
      while (columnIterator.hasNext()) {
        DbColumn column = (DbColumn) columnIterator.next();
        Iterator columnDbAliasIterator = column.getDbAliases().iterator();
        while (columnDbAliasIterator.hasNext()) {
          DbAlias columnAlias = (DbAlias) columnDbAliasIterator.next();
          String columnAliasValue = columnAlias.getAliasValue();
          if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(columnAliasValue))) {
            verified = false;
            System.out.println(
              "Warning: Column "
                + column.getColumnName()
                + " in table "
                + table.getTableName()
                + " has dbAlias with a blank aliasValue value.");
          }
          if (columnAliasValues.containsKey(columnAliasValue)) {
            columnDuplicateAliasValues.add(columnAliasValue);
          }
          else {
            columnAliasValues.put(columnAliasValue, columnAliasValue);
          }
        }
      }
    }
    if (!tableDuplicateAliasValues.isEmpty()) {
      StringBuffer buff =
        new StringBuffer("The following table dbAlias aliasValues are duplicated: ");
      Iterator aliasValueIterator = tableDuplicateAliasValues.iterator();
      boolean isFirst = true;
      while (aliasValueIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) aliasValueIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }
    if (!columnDuplicateAliasValues.isEmpty()) {
      StringBuffer buff =
        new StringBuffer("The following column dbAlias aliasValues are duplicated: ");
      Iterator aliasValueIterator = columnDuplicateAliasValues.iterator();
      boolean isFirst = true;
      while (aliasValueIterator.hasNext()) {
        if (!isFirst) {
          buff.append(", ");
        }
        buff.append((String) aliasValueIterator.next());
        isFirst = false;
      }
      verified = false;
      System.out.println("Warning: " + buff.toString());
    }

    return verified;

  } /**
              * Method to get the generation message showing which version of the 
              * XML file was used.
              */
  public String getGenerationMessage() {
    StringBuffer buff = new StringBuffer(75);
    buff.append("Generated using version ");
    buff.append(getVersion());
    buff.append(" of the BigrLibraryMetaData xml file.");
    return buff.toString();
  }

  public void addFilter(Filter filter) {
    _filters.add(filter);
  } /**
                * @return
                */
  public List getFilters() {
    return _filters;
  }


  public boolean columnExists(String tableAlias, String columnName) {

    DbTable table = getDbTableFromAlias(tableAlias);
    List columns = table.getDbColumns();
    Iterator i = columns.iterator();
    while (i.hasNext()) {
      DbColumn column = (DbColumn) i.next();
      if (column.getColumnName().equalsIgnoreCase(columnName)) {
        return true;
      }
    }
    return false;
  }
  
  public HashMap getFilterHashMap() {
    HashMap filterMap = new HashMap();
    Iterator i = _filters.iterator();
    
    while (i.hasNext()) {
      Filter filter = (Filter) i.next();
      filterMap.put(filter.getKeyName(), filter.getKeyName());
    }
    
    return filterMap;
  }
}
