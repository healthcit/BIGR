package com.ardais.bigr.query.generator;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.gulfstreambio.kc.det.DatabaseAdeElement;
import com.gulfstreambio.kc.det.DatabaseElement;
import com.gulfstreambio.kc.det.DatabaseFormInstance;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetService;

public class ProductQueryMetaDataKc {

  private static final Map _comparisonOperators;
  private static final Map _orOperators;
  private static final Map _andOperators;
  static {
    _comparisonOperators = new HashMap();
    _comparisonOperators.put(KcQueryOperators.EQ, "=");
    _comparisonOperators.put(KcQueryOperators.NE, "<>");
    _comparisonOperators.put(KcQueryOperators.LT, "<");
    _comparisonOperators.put(KcQueryOperators.LE, "<=");
    _comparisonOperators.put(KcQueryOperators.GT, ">");
    _comparisonOperators.put(KcQueryOperators.GE, ">=");   
    
    _orOperators = new HashMap();
    _orOperators.put(KcQueryOperators.ORLTGT, new String[] {"<", ">"});
    _orOperators.put(KcQueryOperators.ORLTGE, new String[] {"<", ">="});
    _orOperators.put(KcQueryOperators.ORLEGT, new String[] {"<=", ">"});
    _orOperators.put(KcQueryOperators.ORLEGE, new String[] {"<=", ">="});
    _orOperators.put(KcQueryOperators.ORGTLT, new String[] {">", "<"});
    _orOperators.put(KcQueryOperators.ORGELT, new String[] {">=", "<"});
    _orOperators.put(KcQueryOperators.ORGTLE, new String[] {">", "<="});
    _orOperators.put(KcQueryOperators.ORGELE, new String[] {">=", "<="});

    _andOperators = new HashMap();
    _andOperators.put(KcQueryOperators.ANDLTGT, new String[] {"<", ">"});
    _andOperators.put(KcQueryOperators.ANDLTGE, new String[] {"<", ">="});
    _andOperators.put(KcQueryOperators.ANDLEGT, new String[] {"<=", ">"});
    _andOperators.put(KcQueryOperators.ANDLEGE, new String[] {"<=", ">="});
    _andOperators.put(KcQueryOperators.ANDGTLT, new String[] {">", "<"});
    _andOperators.put(KcQueryOperators.ANDGELT, new String[] {">=", "<"});
    _andOperators.put(KcQueryOperators.ANDGTLE, new String[] {">", "<="});
    _andOperators.put(KcQueryOperators.ANDGELE, new String[] {">=", "<="});
  }

  private static final Map _multiOperators;
  static {
    _multiOperators = new HashMap();
    _multiOperators.put(KcQueryOperators.EQ, "OR");
    _multiOperators.put(KcQueryOperators.NE, "AND");
    _multiOperators.put(KcQueryOperators.LT, "OR");
    _multiOperators.put(KcQueryOperators.LE, "OR");
    _multiOperators.put(KcQueryOperators.GT, "OR");
    _multiOperators.put(KcQueryOperators.GE, "OR");   
    _multiOperators.put(KcQueryOperators.ORLTGT, "OR");
    _multiOperators.put(KcQueryOperators.ORLTGE, "OR");
    _multiOperators.put(KcQueryOperators.ORLEGT, "OR");
    _multiOperators.put(KcQueryOperators.ORLEGE, "OR");
    _multiOperators.put(KcQueryOperators.ORGTLT, "OR");
    _multiOperators.put(KcQueryOperators.ORGELT, "OR");
    _multiOperators.put(KcQueryOperators.ORGTLE, "OR");
    _multiOperators.put(KcQueryOperators.ORGELE, "OR");
    _multiOperators.put(KcQueryOperators.ANDLTGT, "OR");
    _multiOperators.put(KcQueryOperators.ANDLTGE, "OR");
    _multiOperators.put(KcQueryOperators.ANDLEGT, "OR");
    _multiOperators.put(KcQueryOperators.ANDLEGE, "OR");
    _multiOperators.put(KcQueryOperators.ANDGTLT, "OR");
    _multiOperators.put(KcQueryOperators.ANDGELT, "OR");
    _multiOperators.put(KcQueryOperators.ANDGTLE, "OR");
    _multiOperators.put(KcQueryOperators.ANDGELE, "OR");
  }
  
  
  /**
   * Returns the key that can be used to get the {@link TableMetaData} instance from 
   * {@link ProductQueryMetaData} of the table that holds the specified element .  In addition, 
   * creates the <code>TableMetaData</code> instance and adds it to the collection of all
   * <code>TableMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used for getting the appropriate table when adding filters or columns to a query
   * for a particular element.
   * Note that for conventional attributes this method will return a key based on the table name,
   * and for eav or multi-valued attributes it will return a key based on the element's system name.
   *  
   * @param element the element
   * @return The key.
   */
  public static String getTableKeyUnique(DetElement element) {
    // Create the table key to be returned.
    DatabaseElement dbElement = element.getDatabaseElement();      
    String tableKey = 
      dbElement.isTableEav() ? "kc." + element.getSystemName() : "kc." + dbElement.getTableName();

    // If the table corresponding to the table key does not exist, then create it and
    // add it to the map of all tables.
    if (!ProductQueryMetaData.isHasTableMetaData(tableKey)) {
      String tableName = dbElement.getTableName();
      String tableAlias = dbElement.isTableEav() ? 
          (element.isMultivalued() ? element.getSystemName() + "_multi" : element.getSystemName()) 
          : tableName;
      ProductQueryMetaData.addTableMetaData(tableKey, new TableMetaData(tableName, tableAlias));
    }

    return tableKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link TableMetaData} instance from 
   * {@link ProductQueryMetaData} of the table that holds the specified element.  In addition, 
   * creates the <code>TableMetaData</code> instance and adds it to the collection of all
   * <code>TableMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * Note that this method always returns a key based on the table name.
   *  
   * @param element the element
   * @return The key.
   */
  public static String getTableKey(DetElement element) {
    // Create the table key to be returned.
    DatabaseElement dbElement = element.getDatabaseElement();      
    String tableKey = "kc." + dbElement.getTableName();

    // If the table corresponding to the table key does not exist, then create it and
    // add it to the map of all tables.
    if (!ProductQueryMetaData.isHasTableMetaData(tableKey)) {
      String tableName = dbElement.getTableName();
      String tableAlias = tableName;
      ProductQueryMetaData.addTableMetaData(tableKey, new TableMetaData(tableName, tableAlias));
    }

    return tableKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link TableMetaData} instance from 
   * {@link ProductQueryMetaData} of the table that holds the specified ADE element.  In addition, 
   * creates the <code>TableMetaData</code> instance and adds it to the collection of all
   * <code>TableMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   *  
   * @param dataElement the data element
   * @param adeElement the ADE element
   * @return The key.
   */
  public static String getTableKeyAde(DetElement dataElement, DetElement adeElement) {
    // Create the table key to be returned.
    String tableKey = "kc." + dataElement.getSystemName() + ".ade";

    // If the table corresponding to the table key does not exist, then create it and
    // add it to the map of all tables.
    if (!ProductQueryMetaData.isHasTableMetaData(tableKey)) {
      DatabaseElement dbAdeElement = adeElement.getDatabaseElement();
      String tableName = dbAdeElement.getTableName();
      String tableAlias = dataElement.getSystemName() + "_ade";
      ProductQueryMetaData.addTableMetaData(tableKey, new TableMetaData(tableName, tableAlias));
    }

    return tableKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link TableMetaData} instance from 
   * {@link ProductQueryMetaData} of the table that holds the specified multivalued ADE element.
   * In addition, creates the <code>TableMetaData</code> instance and adds it to the collection of 
   * all <code>TableMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   *  
   * @param dataElement the data element
   * @param adeElement the ADE element
   * @return The key.
   */
  public static String getTableKeyAdeMulti(DetElement dataElement, DetElement adeElement) {
    if (!adeElement.isMultivalued()) {
      throw new ApiException("Attempt to get the multivalued ADE table key for a non-multivalued ADE element (" + adeElement.getCui() + ").");                  
    }
    
    // Create the table key to be returned.
    String tableKey = "kc." + dataElement.getSystemName() + ".ade.multi";

    // If the table corresponding to the table key does not exist, then create it and
    // add it to the map of all tables.
    if (!ProductQueryMetaData.isHasTableMetaData(tableKey)) {
      DatabaseElement dbAdeElement = adeElement.getDatabaseElement();
      String tableName = dbAdeElement.getTableName();
      String tableAlias = dataElement.getSystemName() + "_ade_multi";
      ProductQueryMetaData.addTableMetaData(tableKey, new TableMetaData(tableName, tableAlias));
    }

    return tableKey;
  }

  private static String getTableKeyAdeBase(DetElement dataElement, DetElement adeElement) {
    // Create the table key to be returned.
    String tableKey = "kc." + dataElement.getSystemName() + ".ade";

    // If the table corresponding to the table key does not exist, then create it and
    // add it to the map of all tables.
    if (!ProductQueryMetaData.isHasTableMetaData(tableKey)) {
      DatabaseAdeElement dbAdeElement = (DatabaseAdeElement) adeElement.getDatabaseElement();
      String tableName = dbAdeElement.getTableNameBase();
      String tableAlias = dataElement.getSystemName() + "_ade";
      ProductQueryMetaData.addTableMetaData(tableKey, new TableMetaData(tableName, tableAlias));
    }

    return tableKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link TableMetaData} instance from 
   * {@link ProductQueryMetaData} of the form instance table for the specified domain object type.
   * In addition, creates the <code>TableMetaData</code> instance and adds it to the collection of 
   * all <code>TableMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used for getting the form instance table, aliased properly for the
   * type of domain object, when adding a join to the form instance table in the course of 
   * building a query.  
   *  
   * @param domainObjectType the type of BIGR domain object 
   * @return The key.
   */
  public static String getTableKeyForm(String domainObjectType) {
    // Create the table key to be returned.
    String tableKey = "kc.form." + domainObjectType;

    // If the table corresponding to the table key does not exist, then create it and
    // add it to the map of all tables.
    if (!ProductQueryMetaData.isHasTableMetaData(tableKey)) {
      DatabaseFormInstance formInstance = 
        DetService.SINGLETON.getDataElementTaxonomy().getDatabaseFormInstance();  
      String tableName = formInstance.getTableName();
      String tableAlias = domainObjectType + "_form";
      ProductQueryMetaData.addTableMetaData(tableKey, new TableMetaData(tableName, tableAlias));
    }

    return tableKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link ColumnMetaData} instance from 
   * {@link ProductQueryMetaData} of the column that holds the specified element.  In addition, 
   * creates the <code>ColumnMetaData</code> instance and adds it to the collection of all
   * <code>ColumnMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used for getting the appropriate column when adding columns to a query
   * for a particular element.  
   *  
   * @param element the element
   * @return The key.
   */
  public static String getColumnKey(DetElement element) {
    // Create the column key to be returned.
    DatabaseElement dbElement = element.getDatabaseElement();
    TableMetaData tableMetaData = ProductQueryMetaData.getTableMetaData(ProductQueryMetaDataKc.getTableKey(element));
    String columnKey = "kc." + tableMetaData.getTableName() + "." + dbElement.getColumnDataValue();

    // If the column corresponding to the column key does not exist, then create it and
    // add it to the map of all columns.
    if (!ProductQueryMetaData.isHasColumnMetaData(columnKey)) {
      String columnName = dbElement.getColumnDataValue();
      String columnAlias = columnName;
      String tableAlias = tableMetaData.getTableAlias();
      ProductQueryMetaData.addColumnMetaData(columnKey, new ColumnMetaData(tableAlias, columnName, columnAlias));
    }

    return columnKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link ColumnMetaData} instance from 
   * {@link ProductQueryMetaData} for the "other value" of the column that holds the specified 
   * element.  In addition, creates the <code>ColumnMetaData</code> instance and adds it to the 
   * collection of all <code>ColumnMetaData</code> instances in <code>ProductQueryMetaData</code> 
   * if it does not already exist.
   * <p>
   * This is used for getting the appropriate column when adding columns to a query
   * for a particular element.  
   *  
   * @param element the element
   * @return The key.
   */
  public static String getColumnKeyOtherValue(DetElement element) {
    // Create the column key to be returned.
    DatabaseElement dbElement = element.getDatabaseElement();
    TableMetaData tableMetaData = ProductQueryMetaData.getTableMetaData(ProductQueryMetaDataKc.getTableKey(element));
    String columnKey = "kc." + tableMetaData.getTableName() + "." + dbElement.getColumnDataValue() + ".otherValue";

    // If the column corresponding to the column key does not exist, then create it and
    // add it to the map of all columns.
    if (!ProductQueryMetaData.isHasColumnMetaData(columnKey)) {
      String columnName = dbElement.getColumnDataValueOther();
      String columnAlias = columnName;
      String tableAlias = tableMetaData.getTableAlias();
      ProductQueryMetaData.addColumnMetaData(columnKey, new ColumnMetaData(tableAlias, columnName, columnAlias));
    }

    return columnKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link ColumnMetaData} instance from 
   * {@link ProductQueryMetaData} for the date precision of the column that holds the specified 
   * element.  In addition, creates the <code>ColumnMetaData</code> instance and adds it to the 
   * collection of all <code>ColumnMetaData</code> instances in <code>ProductQueryMetaData</code> 
   * if it does not already exist.
   * <p>
   * This is used for getting the appropriate column when adding columns to a query
   * for a particular element.  
   *  
   * @param element the element
   * @return The key.
   */
  public static String getColumnKeyDatePrecision(DetElement element) {
    // Create the column key to be returned.
    DatabaseElement dbElement = element.getDatabaseElement();
    TableMetaData tableMetaData = ProductQueryMetaData.getTableMetaData(ProductQueryMetaDataKc.getTableKey(element));
    String columnKey = "kc." + tableMetaData.getTableName() + "." + dbElement.getColumnDataValue() + ".datePrecision";

    // If the column corresponding to the column key does not exist, then create it and
    // add it to the map of all columns.
    if (!ProductQueryMetaData.isHasColumnMetaData(columnKey)) {
      String columnName = dbElement.getColumnDataValueDpc();
      String columnAlias = columnName;
      String tableAlias = tableMetaData.getTableAlias();
      ProductQueryMetaData.addColumnMetaData(columnKey, new ColumnMetaData(tableAlias, columnName, columnAlias));
    }

    return columnKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link ColumnMetaData} instance from 
   * {@link ProductQueryMetaData} for the "standard value" of the column that holds the 
   * specified element (i.e. not sought, not reported, etc).  In addition, 
   * creates the <code>ColumnMetaData</code> instance and adds it to the collection of all
   * <code>ColumnMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used for getting the appropriate column when adding columns to a query
   * for a particular element.  
   *  
   * @param element the element
   * @return The key.
   */
  public static String getColumnKeySystemStandardValue(DetElement element) {
    // Create the column key to be returned.
    DatabaseElement dbElement = element.getDatabaseElement();
    TableMetaData tableMetaData = ProductQueryMetaData.getTableMetaData(ProductQueryMetaDataKc.getTableKey(element));
    String columnKey = "kc." + tableMetaData.getTableName() + "." + dbElement.getColumnDataValue() + ".standardValue";

    // If the column corresponding to the column key does not exist, then create it and
    // add it to the map of all columns.
    if (!ProductQueryMetaData.isHasColumnMetaData(columnKey)) {
      String columnName = dbElement.getColumnDataValueSystemStandard();
      String columnAlias = columnName;
      String tableAlias = tableMetaData.getTableAlias();
      ProductQueryMetaData.addColumnMetaData(columnKey, new ColumnMetaData(tableAlias, columnName, columnAlias));
    }

    return columnKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link ColumnMetaData} instance from 
   * {@link ProductQueryMetaData} for the element cui of the column that holds the specified element .  In addition, 
   * creates the <code>ColumnMetaData</code> instance and adds it to the collection of all
   * <code>ColumnMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used for getting the appropriate column when adding columns to a query
   * for a particular element.  
   *  
   * @param element the element
   * @return The key.
   */
  public static String getColumnKeyElementCui(DetElement element) {
    // Create the column key to be returned.
    DatabaseElement dbElement = element.getDatabaseElement();
    TableMetaData tableMetaData = ProductQueryMetaData.getTableMetaData(ProductQueryMetaDataKc.getTableKey(element));
    String columnKey = "kc." + tableMetaData.getTableName() + "." + dbElement.getColumnDataValue() + ".elementCui";

    // If the column corresponding to the column key does not exist, then create it and
    // add it to the map of all columns.
    if (!ProductQueryMetaData.isHasColumnMetaData(columnKey)) {
      String columnName = dbElement.getColumnDataElement();
      String columnAlias = columnName;
      String tableAlias = tableMetaData.getTableAlias();
      ProductQueryMetaData.addColumnMetaData(columnKey, new ColumnMetaData(tableAlias, columnName, columnAlias));
    }

    return columnKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link ColumnMetaData} instance from 
   * {@link ProductQueryMetaData} of the column that holds the domain object id.  In addition, 
   * creates the <code>ColumnMetaData</code> instance and adds it to the collection of all
   * <code>ColumnMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used for getting the appropriate column when adding columns to a query
   * for a particular element.  
   *  
   * @param element the element
   * @return The key.
   */
  public static String getColumnKeyFormDomainObjectId(String domainObjectType) {
    String columnKey = "kc.form." + domainObjectType + ".domainObjectId";

    // If the column corresponding to the column key does not exist, then create it and
    // add it to the map of all columns.
    if (!ProductQueryMetaData.isHasColumnMetaData(columnKey)) {
      DatabaseFormInstance formInstance = 
        DetService.SINGLETON.getDataElementTaxonomy().getDatabaseFormInstance();  
      String columnName = formInstance.getColumnDomainObjectId();
      String columnAlias = columnName;
      String formTableKey = getTableKeyForm(domainObjectType);
      TableMetaData formTable = ProductQueryMetaData.getTableMetaData(formTableKey);
      String tableAlias = formTable.getTableAlias();
      ProductQueryMetaData.addColumnMetaData(columnKey, new ColumnMetaData(tableAlias, columnName, columnAlias));
    }

    return columnKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link ColumnMetaData} instance from 
   * {@link ProductQueryMetaData} of the column that holds the domain object id.  In addition, 
   * creates the <code>ColumnMetaData</code> instance and adds it to the collection of all
   * <code>ColumnMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used for getting the appropriate column when adding columns to a query
   * for a particular element.  
   *  
   * @param element the element
   * @return The key.
   */
  public static String getColumnKeyFormId(String domainObjectType) {
    String columnKey = "kc.form." + domainObjectType + ".formId";

    // If the column corresponding to the column key does not exist, then create it and
    // add it to the map of all columns.
    if (!ProductQueryMetaData.isHasColumnMetaData(columnKey)) {
      DatabaseFormInstance formInstance = 
        DetService.SINGLETON.getDataElementTaxonomy().getDatabaseFormInstance();  
      String columnName = formInstance.getColumnPrimaryKey();
      String columnAlias = columnName;
      String formTableKey = getTableKeyForm(domainObjectType);
      TableMetaData formTable = ProductQueryMetaData.getTableMetaData(formTableKey);
      String tableAlias = formTable.getTableAlias();
      ProductQueryMetaData.addColumnMetaData(columnKey, new ColumnMetaData(tableAlias, columnName, columnAlias));
    }

    return columnKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link ColumnMetaData} instance from 
   * {@link ProductQueryMetaData} of the column that holds the specified element .  In addition, 
   * creates the <code>ColumnMetaData</code> instance and adds it to the collection of all
   * <code>ColumnMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used for getting the appropriate column when adding columns to a query
   * for a particular element.  
   *  
   * @param element the element
   * @return The key.
   */
  public static String getColumnKeyFormModificationDate(String domainObjectType) {
    String columnKey = "kc.form." + domainObjectType + ".modificationDate";

    // If the column corresponding to the column key does not exist, then create it and
    // add it to the map of all columns.
    if (!ProductQueryMetaData.isHasColumnMetaData(columnKey)) {
      DatabaseFormInstance formInstance = 
        DetService.SINGLETON.getDataElementTaxonomy().getDatabaseFormInstance();  
      String columnName = formInstance.getColumnModificationDate();
      String columnAlias = columnName;
      String formTableKey = getTableKeyForm(domainObjectType);
      TableMetaData formTable = ProductQueryMetaData.getTableMetaData(formTableKey);
      String tableAlias = formTable.getTableAlias();
      ProductQueryMetaData.addColumnMetaData(columnKey, new ColumnMetaData(tableAlias, columnName, columnAlias));
    }

    return columnKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter on the object_id column of the form instance 
   * table.  In addition, creates the <code>FilterMetaData</code> instance and adds it to the 
   * collection of all <code>FilterMetaData</code> instances in 
   * <code>ProductQueryMetaData</code> if it does not already exist.
   *  
   * @param domainObjectType the type of BIGR domain object with which the element is associated.
   * This is necessary to properly alias the form instance table.  
   * @return The key.
   */
  public static String getFilterKeyFormDomainObjectId(String domainObjectType) {
    String filterKey = "kc.form." + domainObjectType + ".domainObjectId";

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      String idColumnKey = getColumnKeyFormDomainObjectId(domainObjectType);
      ColumnMetaData columnMetaData = ProductQueryMetaData.getColumnMetaData(idColumnKey);
      StringBuffer fragment = new StringBuffer(50);
      fragment.append(columnMetaData.getTableAlias());
      fragment.append(".");
      fragment.append(columnMetaData.getColumnAlias());
      fragment.append(" = ?");
      FilterMetaData filterMetaData = new FilterMetaData(fragment.toString());
      ProductQueryMetaData.addFilterMetaData(filterKey, filterMetaData);
    }

    return filterKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter on the element cui column of a kc 
   * table.  In addition, creates the <code>FilterMetaData</code> instance and adds it to the 
   * collection of all <code>FilterMetaData</code> instances in 
   * <code>ProductQueryMetaData</code> if it does not already exist.
   *  
   * @param domainObjectType the type of BIGR domain object with which the element is associated.
   * This is necessary to properly alias the form instance table.  
   * @return The key.
   */
  public static String getFilterKeyElementCui(DetElement element) {
    // Create the filter key to be returned.
    DatabaseElement dbElement = element.getDatabaseElement();
    TableMetaData tableMetaData = ProductQueryMetaData.getTableMetaData(ProductQueryMetaDataKc.getTableKey(element));
    String filterKey = "kc." + tableMetaData.getTableName() + "." + dbElement.getColumnDataValue() + ".standardValue";

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      String elementCuiColumnKey = getColumnKeyElementCui(element);
      ColumnMetaData columnMetaData = ProductQueryMetaData.getColumnMetaData(elementCuiColumnKey);
      StringBuffer fragment = new StringBuffer(50);
      fragment.append(columnMetaData.getTableAlias());
      fragment.append(".");
      fragment.append(columnMetaData.getColumnAlias());
      fragment.append(" = ?");
      FilterMetaData filterMetaData = new FilterMetaData(fragment.toString());
      ProductQueryMetaData.addFilterMetaData(filterKey, filterMetaData);
    }

    return filterKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter for the specified element and operator.
   * In addition, creates the <code>FilterMetaData</code> instance and adds it to the collection of 
   * all <code>FilterMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used to add a filter to a query for the specified element using the 
   * specified operator.  The filters created by this method are of the following form: 
   * <ul>
   * <li>For operator {@link KcQueryOperators#ANY ANY}: "table.column is not null"</li> 
   * <li>For operator {@link KcQueryOperators#EQ EQ}: "table.column = ?"</li> 
   * <li>For operator {@link KcQueryOperators#NE NE}: "table.column <> ?"</li> 
   * <li>For operator {@link KcQueryOperators#GT GT}: "table.column > ?"</li> 
   * <li>For operator {@link KcQueryOperators#GE GE}: "table.column >= ?"</li> 
   * <li>For operator {@link KcQueryOperators#LT LT}: "table.column < ?"</li> 
   * <li>For operator {@link KcQueryOperators#LE LE}: "table.column <= ?"</li> 
   * <li>For operator {@link KcQueryOperators#ORLTGT ORLTGT}: 
   * "(table.column < ? OR table.column > ?"</li> 
   * <li>For operator {@link KcQueryOperators#ORLEGT ORLEGT}: 
   * "(table.column <= ? OR table.column > ?"</li> 
   * <li>For operator {@link KcQueryOperators#ORLTGE ORLTGE}: 
   * "(table.column < ? OR table.column >= ?"</li> 
   * <li>For operator {@link KcQueryOperators#ORLEGE ORLEGE}: 
   * "(table.column <= ? OR table.column >= ?"</li> 
   * </ul>
   *  
   * @param element the element
   * @param operator the comparison or range operator.  Must be on of the operators specified in
   * {@link KcQueryOperators}.  
   * @return The key.
   * @throws ApiException if the specified operator is not one of the <code>KcQueryOperators</code>
   * operators. 
   */
  public static String getFilterKey(DetElement element, String operator) {
    // Make sure a legal operator was specified.
    if (!KcQueryOperators.isValidOperator(operator)) {
      throw new ApiException("Illegal operator (" + operator + ") for a KC filter.");      
    }

    // Create the filter key to be returned.
    String filterKey = "kc." + element.getSystemName() + "." + operator;

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      TableMetaData table = ProductQueryMetaData.getTableMetaData(getTableKeyUnique(element));
      ProductQueryMetaData.addFilterMetaData(filterKey, makeFilter(table, element, operator));
    }

    return filterKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter for the specified ADE element of the specified 
   * data element for the specified operator.  In addition, creates the <code>FilterMetaData</code> 
   * instance and adds it to the collection of all <code>FilterMetaData</code> instances in 
   * <code>ProductQueryMetaData</code> if it does not already exist.
   * <p>
   * This is used to add a filter to a query for the specified ADE element of the specified data
   * element using the specified operator.  The filters created by this method are of the following 
   * form: 
   * <ul>
   * <li>For operator {@link KcQueryOperators#ANY ANY}: "table.column is not null"</li> 
   * <li>For operator {@link KcQueryOperators#EQ EQ}: "table.column = ?"</li> 
   * <li>For operator {@link KcQueryOperators#NE NE}: "table.column <> ?"</li> 
   * <li>For operator {@link KcQueryOperators#GT GT}: "table.column > ?"</li> 
   * <li>For operator {@link KcQueryOperators#GE GE}: "table.column >= ?"</li> 
   * <li>For operator {@link KcQueryOperators#LT LT}: "table.column < ?"</li> 
   * <li>For operator {@link KcQueryOperators#LE LE}: "table.column <= ?"</li> 
   * <li>For operator {@link KcQueryOperators#ORLTGT ORLTGT}: 
   * "(table.column < ? OR table.column > ?"</li> 
   * <li>For operator {@link KcQueryOperators#ORLEGT ORLEGT}: 
   * "(table.column <= ? OR table.column > ?"</li> 
   * <li>For operator {@link KcQueryOperators#ORLTGE ORLTGE}: 
   * "(table.column < ? OR table.column >= ?"</li> 
   * <li>For operator {@link KcQueryOperators#ORLEGE ORLEGE}: 
   * "(table.column <= ? OR table.column >= ?"</li> 
   * </ul>
   *  
   * @param dataElement the data element
   * @param adeElement the ADE element
   * @param operator the comparison or range operator.  Must be on of the operators specified in
   * {@link KcQueryOperators}.  
   * @return The key.
   * @throws ApiException if the specified operator is not one of the <code>KcQueryOperators</code>
   * operators. 
   */
  public static String getFilterKeyAde(DetElement dataElement, 
                                       DetElement adeElement,
                                       String operator) {
    // Make sure a legal operator was specified.
    if (!KcQueryOperators.isValidOperator(operator)) {
      throw new ApiException("Illegal operator (" + operator + ") for a KC filter.");      
    }

    // Create the filter key to be returned.
    String filterKey = "kc." + dataElement.getSystemName() + "." 
                       + adeElement.getSystemName() + "." + operator;

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      TableMetaData table = adeElement.isMultivalued()
        ? ProductQueryMetaData.getTableMetaData(getTableKeyAdeMulti(dataElement, adeElement))
        : ProductQueryMetaData.getTableMetaData(getTableKeyAde(dataElement, adeElement));
      ProductQueryMetaData.addFilterMetaData(filterKey, makeFilter(table, adeElement, operator));
    }

    return filterKey;
  }
  
  private static FilterMetaData makeFilter(TableMetaData table, 
                                           DetElement element,
                                           String operator) {
    DatabaseElement dbElement = element.getDatabaseElement();      
    StringBuffer sql = new StringBuffer(64);
    if (KcQueryOperators.ANY.equals(operator)) {
      sql.append(table.getTableAlias());
      sql.append(".");
      sql.append(dbElement.getColumnDataValue());
      sql.append(" is not null");                
    }
    else {
      boolean isDate = (element.isDatatypeDate() || element.isDatatypeVpd());
      String sqlOperator = (String) _comparisonOperators.get(operator);
      if (sqlOperator != null) {
        makeFilterComparison(sql, table, dbElement, sqlOperator, isDate);
      }
      else {
        String[] sqlOperators = (String[]) _orOperators.get(operator);
        if (sqlOperators != null) {
          sql.append("(");
          makeFilterComparison(sql, table, dbElement, sqlOperators[0], isDate);
          sql.append(" or ");
          makeFilterComparison(sql, table, dbElement, sqlOperators[1], isDate);
          sql.append(")");
        }
        else {
          sqlOperators = (String[]) _andOperators.get(operator);
          if (sqlOperators != null) {
            sql.append("(");
            makeFilterComparison(sql, table, dbElement, sqlOperators[0], isDate);
            sql.append(" and ");
            makeFilterComparison(sql, table, dbElement, sqlOperators[1], isDate);
            sql.append(")");
          }
          else {
            throw new ApiException("Could not form KC filter with operator '" + operator + "'.");            
          }
        }        
      }        
    }
    return new FilterMetaData(sql.toString(), Types.VARCHAR, (String)_multiOperators.get(operator));    
  }
  
  private static void makeFilterComparison(StringBuffer sql, 
                                    TableMetaData table, 
                                    DatabaseElement dbElement, 
                                    String sqlOperator, 
                                    boolean isDate) {
    sql.append(table.getTableAlias());
    sql.append(".");
    sql.append(dbElement.getColumnDataValue());
    sql.append(" ");
    sql.append(sqlOperator);
    if (isDate) {
      sql.append(" to_date(?, 'MM/DD/YY')");
    }
    else {
      sql.append(" ?");
    }    
  }
  
  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter on the data element column of the specified 
   * element that is stored in an EAV table.  In addition, creates the <code>FilterMetaData</code> 
   * instance and adds it to the collection of all <code>FilterMetaData</code> instances in 
   * <code>ProductQueryMetaData</code> if it does not already exist.
   * <p>
   * This is used when the specified element is stored in an EAV table, to add a filter for the
   * data element column, which will ultimately be bound to the element's CUI. 
   *  
   * @param element the element
   * @return The key.
   * @throws ApiException if the specified element is not stored in an EAV table.
   */
  public static String getFilterKeyEavElement(DetElement element) {
    // Make sure that the element is implemented in an EAV table.
    if (!element.getDatabaseElement().isTableEav()) {
      throw new ApiException("Attempt to get the EAV element filter key for a non-EAV element (" + element.getCui() + ").");            
    }
    
    // Create the filter key to be returned.
    String filterKey = "kc." + element.getSystemName() + ".data_element";

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      DatabaseElement dbElement = element.getDatabaseElement();      
      TableMetaData table = ProductQueryMetaData.getTableMetaData(getTableKeyUnique(element));
      StringBuffer sql = new StringBuffer(64);
      sql.append(table.getTableAlias());
      sql.append(".");
      sql.append(dbElement.getColumnDataElement());
      sql.append(" = ?");
      ProductQueryMetaData.addFilterMetaData(filterKey, new FilterMetaData(sql.toString()));
    }

    return filterKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter on the data element column of the specified 
   * ADE element.  In addition, creates the <code>FilterMetaData</code> instance and adds it to 
   * the collection of all <code>FilterMetaData</code> instances in 
   * <code>ProductQueryMetaData</code> if it does not already exist.
   * <p>
   * This is used to add a filter for the ADE's data element column, which will ultimately be 
   * bound to either the data element's CUI (for singlevalued ADE elements) or the ADE element's 
   * CUI (for multivalued ADE elements).
   *  
   * @param dataElement the data element
   * @param adeElement the ADE element
   * @return The key.
   */
  public static String getFilterKeyAdeDataElement(DetElement dataElement, DetElement adeElement) {
    // Create the filter key to be returned.
    String filterKey = (adeElement.isMultivalued()) 
      ? "kc." + dataElement.getSystemName() + ".ade.multi.data_element" 
      : "kc." + dataElement.getSystemName() + ".ade.data_element";

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      String tableKey = (adeElement.isMultivalued()) 
        ? getTableKeyAdeMulti(dataElement, adeElement) : getTableKeyAde(dataElement, adeElement);
      ProductQueryMetaData.addFilterMetaData(filterKey, makeFilterAdeDataElement(adeElement, tableKey));
    }

    return filterKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter on the data element column of the base ADE table
   * associated with the specified data element and ADE element.  In addition, creates the 
   * <code>FilterMetaData</code> instance and adds it to the collection of all 
   * <code>FilterMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used to add a filter for the ADE's base table data element column, which will 
   * ultimately be bound to the data element's CUI, when the filter is for a multivalued data
   * element and we have to join through the base table.
   *  
   * @param dataElement the data element
   * @param adeElement the ADE element
   * @return The key.
   */
  public static String getFilterKeyAdeDataElementBase(DetElement dataElement, DetElement adeElement) {
    // Create the filter key to be returned.
    String filterKey = "kc." + dataElement.getSystemName() + ".ade.data_element"; 

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      String tableKey = getTableKeyAdeBase(dataElement, adeElement);
      ProductQueryMetaData.addFilterMetaData(filterKey, makeFilterAdeDataElement(adeElement, tableKey));
    }

    return filterKey;
  }
  
  private static FilterMetaData makeFilterAdeDataElement(DetElement adeElement, String tableKey) {
    DatabaseElement dbElement = adeElement.getDatabaseElement();
    TableMetaData table = ProductQueryMetaData.getTableMetaData(tableKey);
    StringBuffer sql = new StringBuffer(64);
    sql.append(table.getTableAlias());
    sql.append(".");
    sql.append(dbElement.getColumnDataElement());
    sql.append(" = ?");
    return new FilterMetaData(sql.toString());    
  }

  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter that represents a join from the table that
   * stores the specified element to the form instance table.  In addition, creates the 
   * <code>FilterMetaData</code> instance and adds it to the collection of all 
   * <code>FilterMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used to form the join from the table containing the element to the form instance
   * table. 
   *  
   * @param element the element
   * @param domainObjectType the type of BIGR domain object with which the element is associated.
   * This is necessary to properly alias the form instance table.  
   * @return The key.
   */
  public static String getFilterKeyJoinFormUnique(DetElement element, String domainObjectType) {
    // Create the filter key to be returned.
    String filterKey = "kc." + element.getSystemName() + "." + domainObjectType + ".form";

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      DatabaseFormInstance formInstance = 
        DetService.SINGLETON.getDataElementTaxonomy().getDatabaseFormInstance();  
      DatabaseElement dbElement = element.getDatabaseElement();      
      TableMetaData elementTable = ProductQueryMetaData.getTableMetaData(getTableKeyUnique(element));
      String formTableKey = getTableKeyForm(domainObjectType);
      TableMetaData formTable = ProductQueryMetaData.getTableMetaData(formTableKey);
      StringBuffer sql = new StringBuffer(64);
      sql.append(elementTable.getTableAlias());
      sql.append(".");
      sql.append(dbElement.getColumnForeignKeyFormInstance());
      sql.append(" = ");
      sql.append(formTable.getTableAlias());
      sql.append(".");
      sql.append(formInstance.getColumnPrimaryKey());
      ProductQueryMetaData.addFilterMetaData(filterKey, new FilterMetaData(sql.toString()));
    }

    return filterKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter that represents a join from the table that
   * stores the specified element to the form instance table.  In addition, creates the 
   * <code>FilterMetaData</code> instance and adds it to the collection of all 
   * <code>FilterMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used to form the join from the table containing the element to the form instance
   * table. 
   *  
   * @param element the element
   * @param domainObjectType the type of BIGR domain object with which the element is associated.
   * This is necessary to properly alias the form instance table.  
   * @return The key.
   */
  public static String getFilterKeyJoinForm(DetElement element, String domainObjectType) {
    // Create the filter key to be returned.
    String filterKey = "kc." + element.getDatabaseElement().getTableName() + "." + domainObjectType + ".form";

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      DatabaseFormInstance formInstance = 
        DetService.SINGLETON.getDataElementTaxonomy().getDatabaseFormInstance();  
      DatabaseElement dbElement = element.getDatabaseElement();      
      TableMetaData elementTable = ProductQueryMetaData.getTableMetaData(getTableKey(element));
      String formTableKey = getTableKeyForm(domainObjectType);
      TableMetaData formTable = ProductQueryMetaData.getTableMetaData(formTableKey);
      StringBuffer sql = new StringBuffer(64);
      sql.append(elementTable.getTableAlias());
      sql.append(".");
      sql.append(dbElement.getColumnForeignKeyFormInstance());
      sql.append(" = ");
      sql.append(formTable.getTableAlias());
      sql.append(".");
      sql.append(formInstance.getColumnPrimaryKey());
      ProductQueryMetaData.addFilterMetaData(filterKey, new FilterMetaData(sql.toString()));
    }

    return filterKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter that represents a join from the table that
   * stores the specified ADE element to its parent data element table.  In addition, creates the 
   * <code>FilterMetaData</code> instance and adds it to the collection of all 
   * <code>FilterMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used to form the join from the table containing the ADE element value to the table
   * containing the parent data element's value.
   *  
   * @param dataElement the parent data element
   * @param adeElement the ADE Element
   * @return The key.
   */
  public static String getFilterKeyJoinAdeParent(DetElement dataElement, DetElement adeElement) {
    // Create the filter key to be returned.
    String filterKey = "kc." + dataElement.getSystemName() + ".ade.join";

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      DatabaseElement dbDataElement = dataElement.getDatabaseElement();      
      DatabaseElement dbAdeElement = adeElement.getDatabaseElement();      
      TableMetaData dataElementTable = 
        ProductQueryMetaData.getTableMetaData(getTableKeyUnique(dataElement));
      TableMetaData adeTable = 
        ProductQueryMetaData.getTableMetaData(getTableKeyAde(dataElement, adeElement));
      StringBuffer sql = new StringBuffer(64);
      sql.append(dataElementTable.getTableAlias());
      sql.append(".");
      sql.append(dbDataElement.getColumnPrimaryKey());
      sql.append(" = ");
      sql.append(adeTable.getTableAlias());
      sql.append(".");
      sql.append(dbAdeElement.getColumnForeignKeyParent());
      ProductQueryMetaData.addFilterMetaData(filterKey, new FilterMetaData(sql.toString()));
    }

    return filterKey;
  }
  
  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter that represents a join from the table that
   * stores the specified multivalued ADE element to its parent ADE element table.  In addition,  
   * creates the <code>FilterMetaData</code> instance and adds it to the collection of all 
   * <code>FilterMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used to form the join from the table containing a multivalued ADE element value to the 
   * base ADE table containing the singlevalued ADE elements values.
   *  
   * @param dataElement the parent data element
   * @param adeElement the ADE Element
   * @return The key.
   */
  public static String getFilterKeyJoinAdeParentMulti(DetElement dataElement, 
                                                      DetElement adeElement) {
    // Create the filter key to be returned.
    String filterKey = "kc." + dataElement.getSystemName() + ".ade.multi.join";

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      DatabaseElement dbAdeElement = adeElement.getDatabaseElement();      
      TableMetaData adeTable = 
        ProductQueryMetaData.getTableMetaData(getTableKeyAdeBase(dataElement, adeElement));
      TableMetaData adeMultiTable = 
        ProductQueryMetaData.getTableMetaData(getTableKeyAdeMulti(dataElement, adeElement));
      StringBuffer sql = new StringBuffer(64);
      sql.append(adeTable.getTableAlias());
      sql.append(".");
      sql.append(dbAdeElement.getColumnPrimaryKey());
      sql.append(" = ");
      sql.append(adeMultiTable.getTableAlias());
      sql.append(".");
      sql.append(dbAdeElement.getColumnForeignKeyParent());
      ProductQueryMetaData.addFilterMetaData(filterKey, new FilterMetaData(sql.toString()));
    }

    return filterKey;
  }

  /**
   * Returns the key that can be used to get the {@link FilterMetaData} instance from 
   * {@link ProductQueryMetaData} of the filter that represents a join from the form instance 
   * table to the BIGR table that represents the specified type of domain object.  In addition, 
   * creates the <code>FilterMetaData</code> instance and adds it to the collection of all 
   * <code>FilterMetaData</code> instances in <code>ProductQueryMetaData</code> if it does not 
   * already exist.
   * <p>
   * This is used to form the join from the BIGR table containing the type of domain object to
   * the form instance table. 
   *  
   * @param element the element
   * @param domainObjectType the type of BIGR domain object.  This is necessary to properly alias 
   * the form instance table.  
   * @return The key.
   */
  public static String getFilterKeyJoinFormBigrDomainObject(String domainObjectType) {
    // Create the filter key to be returned.
    String filterKey = "kc.form." + domainObjectType;

    // If the filter corresponding to the filter key does not exist, then create it and
    // add it to the map of all filters.
    if (!ProductQueryMetaData.isHasFilterMetaData(filterKey)) {
      DatabaseFormInstance formInstance = 
        DetService.SINGLETON.getDataElementTaxonomy().getDatabaseFormInstance();  
      String formTableKey = getTableKeyForm(domainObjectType);
      TableMetaData formTable = ProductQueryMetaData.getTableMetaData(formTableKey);
      StringBuffer sql = new StringBuffer(64);
      String sampleColumn = null;
      if (TagTypes.DOMAIN_OBJECT_VALUE_DONOR.equals(domainObjectType)) {
        sampleColumn = DbConstants.DONOR_ID;
      }
      else if (TagTypes.DOMAIN_OBJECT_VALUE_CASE.equals(domainObjectType)) {
        sampleColumn = DbConstants.CONSENT_ID;
      }
      else if (TagTypes.DOMAIN_OBJECT_VALUE_SAMPLE.equals(domainObjectType)) {
        sampleColumn = DbConstants.SAMPLE_ID;
      }
      sql.append(DbAliases.TABLE_SAMPLE);
      sql.append(".");
      sql.append(sampleColumn);
      sql.append(" = ");
      sql.append(formTable.getTableAlias());
      sql.append(".");
      sql.append(formInstance.getColumnDomainObjectId());
      ProductQueryMetaData.addFilterMetaData(filterKey, new FilterMetaData(sql.toString()));
    }

    return filterKey;
  }
  
  private ProductQueryMetaDataKc() {
    super();
  }
}
