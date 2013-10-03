package com.ardais.bigr.query.generator;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.library.generator.BigrLibraryMetaData;
import com.ardais.bigr.library.generator.BigrLibraryMetaDataParser;
import com.ardais.bigr.library.generator.Column;
import com.ardais.bigr.library.generator.ColumnCount;
import com.ardais.bigr.library.generator.DbColumn;
import com.ardais.bigr.library.generator.DbTable;
import com.ardais.bigr.library.generator.Filter;
import com.ardais.bigr.library.generator.Table;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;

/**
 * Holds all metadata necessary for creating Sample Selection queries.  Parses 
 * BigrLibraryMetaData.xml and creates all of the {@link ColumnMetaData}, {@link TableMetaData} 
 * and {@link FilterMetaData} instances that are used to form queries using standard BIGR columns, 
 * tables and filters.  For KnowledgeCapture, such metadata instances are created lazily as
 * needed, using {@link ProductQueryMetaDataKc}.  The package-level methods in this class 
 * support cooperation with <code>ProductQueryMetaDataKc</code>.
 */
public class ProductQueryMetaData {

  // A logger to log all of the metadata.
  private static Log _logMeta = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_META);

  // Parse BigrLibraryMetaData.xml.
  private static BigrLibraryMetaData metaData;
  static {
    BigrLibraryMetaDataParser digester = new BigrLibraryMetaDataParser();
    metaData = digester.performParse();
  }

  /**
   * Holds the column metadata. 
   */
  private static Map _columns = new HashMap();

  /**
   * Holds the table metadata. 
   */
  private static Map _tables = new HashMap();

  /**
   * Holds the filter metadata. 
   */
  private static Map _filters = new HashMap();

  // Initializes all of the metadata objects from the metadata that was found in
  // BigrLibraryMetaData.xml.  
  static {

    // Create the column metadata instances.
    Iterator i = metaData.getColumns().iterator();
    while (i.hasNext()) {
      Column col = (Column) i.next();
      String columnAlias = col.getColumnAlias();
      DbColumn dbColumn = metaData.getDbColumnFromAlias(columnAlias);
      ColumnMetaData columnMeta = 
        new ColumnMetaData(col.getTableAlias(), dbColumn.getColumnName(), columnAlias);
      _columns.put(col.getOrdering(), columnMeta);
    }

    // Create the column count metadata instances.
    i = metaData.getColumnCounts().iterator();
    while (i.hasNext()) {
      ColumnCount col = (ColumnCount) i.next();
      String columnAlias = col.getColumnAlias();
      DbColumn dbColumn = metaData.getDbColumnFromAlias(columnAlias);
      ColumnCountMetaData columnMeta = 
        new ColumnCountMetaData(col.getTableAlias(), dbColumn.getColumnName(), columnAlias);
      _columns.put(col.getOrdering(), columnMeta);
    }

    // Create the table metadata instances.
    Iterator iTable = metaData.getTables().iterator();
    while (iTable.hasNext()) {
      Table table = (Table) iTable.next();
      String tableAlias = table.getTableAlias();
      DbTable dbTable = metaData.getDbTableFromAlias(tableAlias);
      TableMetaData tableMeta = new TableMetaData(dbTable.getTableName(), tableAlias);
      _tables.put(table.getOrdering(), tableMeta);
    }

    // Create the filter metadata instances.
    FilterMetaData filterMeta = null;
    i = metaData.getFilters().iterator();
    while (i.hasNext()) {
      Filter filter = (Filter) i.next();
      String filterSpec = filter.getSqlFragment();
      if (filter.getMultiOperator().equals("OR")) {
        filterMeta = new FilterMetaData(filterSpec);
      }
      else if (filter.getMultiOperator().equals("AND")) {
        filterMeta = new FilterMetaData(filterSpec, Types.VARCHAR, "AND");
      }
      else {
        throw new ApiException("In ProductQueryMetaData static initializer: invalid multi operator for filter " + filter.getOrdering());
      }
      if (filterMeta != null) {
        filterMeta.setHintPriority(filter.getHintPriority());
        filterMeta.setHintText(filter.getHintText());
      }
      _filters.put(filter.getOrdering(), filterMeta);
    }

    logMetaData();

  } // End of static initializer

  /**
   * Returns the ColumnMetaData for the specified key.
   * 
   * @param  columnKey  the key of the desired column metadata.  Must be one of the KEY_* constants
   *                    from ColumnMetaData.
   * @return  The ColumnMetaData instance.
   * @throws  ApiException if there is no ColumnMetaData associated with the specified key. 
   */
  public static ColumnMetaData getColumnMetaData(String columnKey) {
    ColumnMetaData meta = (ColumnMetaData) _columns.get(columnKey);
    if (meta == null) {
      throw new ApiException("ColumnMetaData for key '" + columnKey + "' not found.");
    }
    return meta;
  }

  /**
   * Returns an indication of whether the <code>ColumnMetaData</code> instance for the specified 
   * key exists.
   * 
   * @param  key  the key of the desired column metadata
   * @return  <code>true</code> if the ColumnMetaData instance exists; <code>false</code> otherwise.
   */
  static boolean isHasColumnMetaData(String key) {
    return (_columns.get(key) != null);
  }

  /**
   * Adds the specified <code>ColumnMetaData</code> to the collection of all 
   * <code>ColumnMetaData</code> instances under the specified key.
   * 
   * @param  key  the key of the column metadata
   * @param  metadata the <code>ColumnMetaData</code> instance
   */
  static void addColumnMetaData(String key, ColumnMetaData metadata) {
    _columns.put(key, metadata);
  }

  /**
   * Returns the TableMetaData for the specified key.
   * 
   * @param  tableKey  the key of the desired table metadata.  Must be one of the KEY_* constants
   *                   from TableMetaData.
   * @return  The TableMetaData instance.
   * @throws  ApiException if there is no TableMetaData associated with the specified key. 
   */
  public static TableMetaData getTableMetaData(String tableKey) {
    TableMetaData meta = (TableMetaData) _tables.get(tableKey);
    if (meta == null) {
      throw new ApiException("TableMetaData for key '" + tableKey + "' not found.");
    }
    return meta;
  }

  /**
   * Returns an indication of whether the <code>TableMetaData</code> instance for the specified 
   * key exists.
   * 
   * @param  tableKey  the key of the desired table metadata
   * @return  <code>true</code> if the TableMetaData instance exists; <code>false</code> otherwise.
   */
  static boolean isHasTableMetaData(String tableKey) {
    return (_tables.get(tableKey) != null);
  }

  /**
   * Adds the specified <code>TableMetaData</code> to the collection of all 
   * <code>TableMetaData</code> instances under the specified key.
   * 
   * @param  key  the key of the table metadata
   * @param  metadata the <code>TableMetaData</code> instance
   */
  static void addTableMetaData(String key, TableMetaData metadata) {
    _tables.put(key, metadata);
  }

  /**
   * Returns the FilterMetaData for the specified key.
   * 
   * @param  filerKey  the key of the desired filter metadata.  Must be one of the KEY_* constants
   *                    from FilterMetaData.
   * @return  The FilterMetaData instance.
   * @throws  ApiException if there is no FilterMetaData associated with the specified key. 
   */
  public static FilterMetaData getFilterMetaData(String filterKey) {
    FilterMetaData meta = (FilterMetaData) _filters.get(filterKey);
    if (meta == null) {
      throw new ApiException("FilterMetaData for key '" + filterKey + "' not found.");
    }
    return meta;
  }

  /**
   * Returns an indication of whether the <code>FilterMetaData</code> instance for the specified 
   * key exists.
   * 
   * @param  filterKey  the key of the desired filter metadata
   * @return  <code>true</code> if the FilterMetaData instance exists; <code>false</code> otherwise.
   */
  static boolean isHasFilterMetaData(String filterKey) {
    return (_filters.get(filterKey) != null);
  }

  /**
   * Adds the specified <code>FilterMetaData</code> to the collection of all 
   * <code>FilterMetaData</code> instances under the specified key.
   * 
   * @param  key  the key of the filter metadata
   * @param  metadata the <code>FilterMetaData</code> instance
   */
  static void addFilterMetaData(String key, FilterMetaData metadata) {
    _filters.put(key, metadata);
  }

  /**
   * Logs all of the metadata if the associated logger is debug-enabled.  The XML file must have
   * been parsed and the metadata instances must have been created.   
   */
  private static void logMetaData() {
    Log log = _logMeta; 
    if (!log.isDebugEnabled()) {
      return;
    }

    log.debug("---------- Start Bigr Library metadata at " + new Date().toString());
    log.debug("--------------- Column metadata");
    SortedSet columns = new TreeSet(_columns.keySet());
    Iterator i = columns.iterator();
    while (i.hasNext()) {
      String key = (String) i.next();
      ColumnMetaData meta = getColumnMetaData(key);
      log.debug(key + "=" + meta.getSelectFragment()); 
    }    

    log.debug("--------------- Table metadata");
    SortedSet tables = new TreeSet(_tables.keySet());
    i = tables.iterator();
    while (i.hasNext()) {
      String key = (String) i.next();
      TableMetaData meta = getTableMetaData(key);
      log.debug(key + "=" + meta.getFromFragment()); 
    }    

    log.debug("--------------- Filter metadata");
    SortedSet filters = new TreeSet(_filters.keySet());
    i = filters.iterator();
    while (i.hasNext()) {
      String key = (String) i.next();
      FilterMetaData meta = getFilterMetaData(key);
      log.debug(key + "=" + meta.getWhereFragment());

      String additionalClause = meta.getAdditionalClause();
      if (additionalClause != null) {
        log.debug("  ADDITIONAL CLAUSE: " + additionalClause);         
      }

      int hintPriority = meta.getHintPriority();
      String hintText = meta.getHintText();
      if (hintText != null) {
        log.debug("  HINT: " + hintText);         
        log.debug("  HINT PRIORITY: " + String.valueOf(hintPriority));         
      }
    }    

    log.debug("---------- End Bigr Library metadata at " + new Date().toString());
  }
}