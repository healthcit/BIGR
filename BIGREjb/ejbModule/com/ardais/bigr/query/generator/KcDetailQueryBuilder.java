package com.ardais.bigr.query.generator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiDateUtil;
import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.StringCanonicalizer;
import com.ardais.bigr.kc.form.BigrFormInstance;
import com.ardais.bigr.kc.form.BigrFormInstanceEnabled;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.VariablePrecisionDate;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.det.DatabaseElement;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.DataElementValue;
import com.gulfstreambio.kc.form.ElementValue;

/**
 */
public class KcDetailQueryBuilder {

  /*
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);
  
  /*
   * Domain object type upon which this query builder is being executed
   */
  private String _domainObjectType;
  
  /*
   * Kc data is retrieved from a single kc table at a time. A helper class is used to contain
   * and manage information about each kc table, and this map is used to get the helper
   * for a given kc data table
   */
  private Map _tableHelpers = new HashMap();
  
  /*
   * Map of DetDataElements processed keyed by cui, used for adding data elements to the data forms
   * returned via getDetails()
   */
  private Map _detDataElements = new HashMap();
  
  /*
   * A Map of maps to keep track of which data form was used to populate a given data item on the
   * data form created for a given donor.  This information is needed for handling
   * multi-valued attributes, to make sure that we return only the most recent value(s) 
   * (not all values across all form instances).  Outermost map is keyed by domain object id, and
   * inner map is keyed by data element cui
   */
  private Map _dataElementPopulationSource = new HashMap();

  /**
   * Creates a new <code>KcDetailQueryBuilder</code>.
   */
  public KcDetailQueryBuilder(String domainObjectType) {
    super();
    _domainObjectType = domainObjectType;
  }

  /**
   * Specifies that the data for the element corresponding to cui be returned in this 
   * detail query.
   */
  public void addColumn(String cui) {
    DetDataElement element = DetService.SINGLETON.getDataElementTaxonomy().getDataElement(cui);
    if (element == null) {
      throw new ApiException("Unable to find data element for cui: " + cui);
    }
    else {
      //get the helper for the kc table to which the column belongs. If one doesn't exist, 
      //create and initialize it
      DatabaseElement tableInfo = element.getDatabaseElement();
      KcDetailQueryBuilderHelper helper = (KcDetailQueryBuilderHelper) _tableHelpers.get(tableInfo.getTableName());
      if (helper == null) {
        helper = createAndInitializeHelper(tableInfo, element);
      }
      processElement(helper, element);
    }
  }
  
  /*
   * Method to create and initialize a helper for a kc data table
   */
  private KcDetailQueryBuilderHelper createAndInitializeHelper(DatabaseElement tableInfo, DetDataElement element) {
    //create the helper and add it to the map
    KcDetailQueryBuilderHelper returnValue = new KcDetailQueryBuilderHelper();
    _tableHelpers.put(tableInfo.getTableName(), returnValue);
    
    String elementTableKey = ProductQueryMetaDataKc.getTableKey(element);
    String formTableKey = ProductQueryMetaDataKc.getTableKeyForm(getDomainObjectType());
    
    //create a product query builder for the helper
    ProductQueryBuilder pqb = new ProductQueryBuilder();
    //add the table holding the column we want
    pqb.addTable(elementTableKey);
    //add the form instance table
    pqb.addTable(formTableKey);
    //join the form instance table to the table holding the column we want
    pqb.addFilter(ProductQueryMetaDataKc.getFilterKeyJoinForm(element, getDomainObjectType()));
    
    returnValue.setProductQueryBuilder(pqb);
    //set the table info on the helper
    returnValue.setTableInfo(tableInfo);
    // set the query metadata element table key on the helper
    returnValue.setQueryTableKeyForElement(elementTableKey);
    
    return returnValue;
  }
  
  /*
   * Method to process a DetDataElement.  This includes adding the element to the helper,
   * adding the appropriate columns for the element to the helper's query builder, etc.
   */
  private void processElement(KcDetailQueryBuilderHelper helper, DetDataElement element) {
    //add the element to the helper so we know the element was retrieved by
    //the query builder for the helper
    helper.getElements().add(element);
    //add the appropriate columns to the query builder
    ProductQueryBuilder queryBuilder = helper.getProductQueryBuilder();
    //add the column to retrieve
    queryBuilder.addColumn(ProductQueryMetaDataKc.getColumnKey(element));
    //if the element is cv and is other-enabled, add the column that hold's it's other value
    if (element.isDatatypeCv() && element.isHasOtherValue()) {
      queryBuilder.addColumn(ProductQueryMetaDataKc.getColumnKeyOtherValue(element));
    }
    //if the column is not cv, add the column that holds it's "standard" value (i.e. not
    //reported, etc)
    if (!element.isDatatypeCv()) {
      queryBuilder.addColumn(ProductQueryMetaDataKc.getColumnKeySystemStandardValue(element));
    }
    //if the element is vpd, add the column that holds the date precision
    if (element.isDatatypeVpd()) {
      queryBuilder.addColumn(ProductQueryMetaDataKc.getColumnKeyDatePrecision(element));
    }
    //if the table for this element is non-conventional, add it's data_element_cui column to the 
    //query.
    if (!helper.getTableInfo().isTableConventional()) {
      queryBuilder.addColumn(ProductQueryMetaDataKc.getColumnKeyElementCui(element));
      //we will need to filter on this cui column, but because we might be looking for multiple 
      //columns from the same table we can't build that filter until all of the columns we're 
      //looking for have been added.  So add the element cui value to the helper, and 
      //getDetails will add the filter
      helper.getElementCuis().add(element.getCui());
    }
    //add this element to the list of elements we've processed
    _detDataElements.put(element.getCui(), element);
  }

  /**
   * Populates the donor data beans in the specified map with the kc data
   * specified via the addColumn method in this class.
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.DonorData}
   * 				  beans, keyed by their donor id.
   */
  public void getDetails(Map map) {
    //initialize the domain objects with a blank form instance
    initializeDomainObjectsWithFormInstances(map);
    
    //for each query builder we've created, retrieve the data
    Set helperKeys = _tableHelpers.keySet();
    Iterator helperIterator = helperKeys.iterator();
    while (helperIterator.hasNext()) {
      KcDetailQueryBuilderHelper helper = (KcDetailQueryBuilderHelper) _tableHelpers.get(helperIterator.next());
      ProductQueryBuilder queryBuilder = helper.getProductQueryBuilder();
      
      String elementTableKey = helper.getQueryTableKeyForElement();
      String formTableKey = ProductQueryMetaDataKc.getTableKeyForm(getDomainObjectType());
      
      //Add the form id, domain object id, and modification date from the form instance table to 
      //the query so we can order and process the rows appropriately
      String domainObjectIdColumnKey = ProductQueryMetaDataKc.getColumnKeyFormDomainObjectId(getDomainObjectType());
      queryBuilder.addColumn(domainObjectIdColumnKey);
      String modDateColumnKey = ProductQueryMetaDataKc.getColumnKeyFormModificationDate(getDomainObjectType());
      queryBuilder.addColumn(modDateColumnKey);
      String formIdColumnKey = ProductQueryMetaDataKc.getColumnKeyFormId(getDomainObjectType());
      queryBuilder.addColumn(formIdColumnKey);
      
      //if we need to add a filter on the data element cui column for this table, do so now
      List elementCuis = helper.getElementCuis();
      if (!ApiFunctions.isEmpty(elementCuis)) {
        String cui = (String) elementCuis.get(0);
        DetDataElement element = DetService.SINGLETON.getDataElementTaxonomy().getDataElement(cui);
        if (element == null) {
          throw new ApiException("Unable to find data element for cui: " + cui);
        }
        else {
          queryBuilder.addFilter(ProductQueryMetaDataKc.getFilterKeyElementCui(element),
              ApiFunctions.toStringArray(elementCuis));
        }
      }

      //execute the query
      Connection con = null;
      BigrPreparedStatement pstmt = null;
      BigrResultSet brs = new BigrResultSet(new StringCanonicalizer());
      Map columns = null;

      try {
        con = ApiFunctions.getDbConnection();
        int idBatchSize = getIdBatchSize();
        List idChunks = ApiFunctions.chunkStrings((String[]) map.keySet().toArray(new String[0]), idBatchSize);
        Iterator chunks = idChunks.iterator();
        boolean first = true;
        while (chunks.hasNext()) {
          String[] chunk = (String[]) chunks.next();
          queryBuilder.addFilter(ProductQueryMetaDataKc.getFilterKeyFormDomainObjectId(getDomainObjectType()), chunk);
          if (first || (chunk.length != idBatchSize)) {
            StringBuffer sqlBuf = new StringBuffer(1024);
            queryBuilder.setOptimizerHint(getQueryHint(elementTableKey, formTableKey));
            // don't let this hint be superceded by a filter hint
            queryBuilder.setHintPriority(ProductQueryBuilder.MAX_HINT_PRIORITY); 
            queryBuilder.getQuery(sqlBuf);
            if (pstmt != null) {
              pstmt.close();
              pstmt = null;
            }
            //append an order by clause to the sql statement
            List orderByList = new ArrayList();
            orderByList.add(domainObjectIdColumnKey);
            orderByList.add(modDateColumnKey);
            orderByList.add(formIdColumnKey);
            appendOrderBy(sqlBuf, orderByList);
            pstmt = new BigrPreparedStatement(con, sqlBuf.toString());
          }
          queryBuilder.bindAllParameters(pstmt);
          if (first && _queryLog.isDebugEnabled()) {
            _queryLog.debug("Detail query for " + ApiFunctions.shortClassName(getClass().getName()));
            _queryLog.debug(pstmt.toString());
          }
          
          brs.setResultSet(pstmt.executeQuery());
          
          processResultSet(map, helper, brs);
          
          brs.close();
          brs.setResultSet(null);
          first = false;
        }
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(con, pstmt, brs);
      }
    }
  }
  
  private void processResultSet(Map map, KcDetailQueryBuilderHelper helper, BigrResultSet brs) {
    String domainObjectIdColumnKey = ProductQueryMetaDataKc.getColumnKeyFormDomainObjectId(getDomainObjectType());
    ColumnMetaData columnMetaData = ProductQueryMetaData.getColumnMetaData(domainObjectIdColumnKey);
    String domainObjectIdColumnName = columnMetaData.getColumnAlias();
    try {
      while (brs.next()) {
        //get the form instance to populate
        String domainObjectId = brs.getString(domainObjectIdColumnName);
        BigrFormInstanceEnabled domainObject = (BigrFormInstanceEnabled)map.get(domainObjectId);
        BigrFormInstance bigrFormInstance = domainObject.getBigrFormInstance();
        //populate the form instance from the current row
        processRow(helper, bigrFormInstance, brs);
      }
    }
    catch (SQLException e) {
      throw new ApiException(e);
    }
    
  }
  
  //method to populate a BigrFormInstance object from the current row of a result set
  private void processRow(KcDetailQueryBuilderHelper helper, BigrFormInstance bigrFormInstance, BigrResultSet brs) {
    //for every element we retrieved in the result set, set the value on the form instance 
    //as appropriate.  For non-multi-valued elements this means set it if it hasn't already been
    //set.  For multiValued data elements, this means add the value from the current row if either
    //there is no value or the row comes from the same form as the previous value(s) 
    Iterator elementIterator = helper.getElements().iterator();
    while (elementIterator.hasNext()) {
      //get the element we're going to retrieve from the result set
      DetDataElement detDataElement = (DetDataElement)elementIterator.next();
      //if the table is not conventional, the result set will contain one row per element so only
      //proceed if the element in the current row is the one we're working on
      if (!helper.getTableInfo().isTableConventional()) {
        ColumnMetaData cmd = ProductQueryMetaData.getColumnMetaData(ProductQueryMetaDataKc.getColumnKeyElementCui(detDataElement));
        String elementCuiColumnName = cmd.getColumnAlias();
        String elementCuiValue = null;
        try {
          elementCuiValue = brs.getString(elementCuiColumnName);
        }
        catch (SQLException e) {
          throw new ApiException(e);
        }
        if (!detDataElement.getCui().equalsIgnoreCase(elementCuiValue)) {
          continue;
        }
      }
      //get the corresponding data element on the form instance
      DataElement dataElement = null;
      boolean dataElementFound = false;
      Iterator dataElementIterator = bigrFormInstance.getDataElements();
      while (!dataElementFound && dataElementIterator.hasNext()) {
        dataElement = (DataElement)dataElementIterator.next();
        if (detDataElement.getCui().equalsIgnoreCase(dataElement.getCuiOrSystemName())) {
          dataElementFound = true;
        }
      }
      String domainObjectId = bigrFormInstance.getDomainObjectId();
      //get the existing value(s) for the data element on the form instance
      DataElementValue[] currentValues = dataElement.getDataElementValues();
      //if there isn't already a value for the data element, add the value from the current row
      if (currentValues.length <= 0) {
        addDataValue(helper, detDataElement, dataElement, brs, domainObjectId);
      }
      else {
        //if there is already a value for the data element, add the value from the current row only
        //if the element is multivalued and the current row came from the same form instance as the 
        //previous value(s)
        if (detDataElement.isMultivalued()) {
          ColumnMetaData cmd = ProductQueryMetaData.getColumnMetaData(ProductQueryMetaDataKc.getColumnKeyFormId(getDomainObjectType()));
          String formIdColumnName = cmd.getColumnAlias();
          String formId = null;
          try {
            formId = brs.getString(formIdColumnName);
          }
          catch (SQLException e) {
            throw new ApiException(e);
          }
          //get the map for this domain object
          Map domainObjectMap = (Map)_dataElementPopulationSource.get(domainObjectId);
          //get the form id used to populate previous values for this data element
          String previouslyUsedFormId = (String)domainObjectMap.get(detDataElement.getCui());
          if (formId.equalsIgnoreCase(previouslyUsedFormId)) {
            addDataValue(helper, detDataElement, dataElement, brs, domainObjectId);
          }
        }
      }
    }
  }
  
  //method to populate a BigrFormInstance data element from the current row of a result set
  private void addDataValue(KcDetailQueryBuilderHelper helper, DetDataElement detDataElement, 
                            DataElement dataElement, BigrResultSet brs, String domainObjectId) {
    try {
      String value = null;
      String otherValue = null;
      ColumnMetaData cmd = ProductQueryMetaData.getColumnMetaData(ProductQueryMetaDataKc.getColumnKey(detDataElement));
      String valueColumnName = cmd.getColumnAlias();
      //call the appropriate method on the result set to get the value
      if (detDataElement.isDatatypeCv()) {
        value = brs.getString(valueColumnName);
        if (!ApiFunctions.isEmpty(value)) {
          value = GbossFactory.getInstance().getDescription(value);
        }
      }
      else if (detDataElement.isDatatypeInt()) {
        Integer i = ApiFunctions.safeInteger(brs.getString(valueColumnName));
        if (i != null) {
          value = i.toString();
        }
      }
      else if (detDataElement.isDatatypeFloat()) {
        BigDecimal bd = ApiFunctions.safeBigDecimal(brs.getString(valueColumnName));
        if (bd != null) {
          value = bd.toString();
        }
      }
      else if (detDataElement.isDatatypeDate()) {
        Date d = brs.getDate(valueColumnName);
        if (d != null) {
          value = ApiDateUtil.convertDateToString(d);
        }
      }
      else if (detDataElement.isDatatypeVpd()) {
        Date d = brs.getDate(valueColumnName);
        cmd = ProductQueryMetaData.getColumnMetaData(ProductQueryMetaDataKc.getColumnKeyDatePrecision(detDataElement));
        String precisionColumnName = cmd.getColumnAlias();
        String precision = brs.getString(precisionColumnName);
        VariablePrecisionDate vpd = new VariablePrecisionDate(d, precision);
        value = vpd.displayVpd();
      }
      else if (detDataElement.isDatatypeReport()) {
        value = ApiFunctions.getStringFromClob(brs.getClob(valueColumnName));
      }
      else if (detDataElement.isDatatypeText()) {
        value = brs.getString(valueColumnName);
      }
      //if there is no value for the data element in the row, see if there is a system 
      //standard value for it
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(value)) && !detDataElement.isDatatypeCv()) {
        cmd = ProductQueryMetaData.getColumnMetaData(ProductQueryMetaDataKc.getColumnKeySystemStandardValue(detDataElement));
        String standardValueColumnName = cmd.getColumnAlias();
        value = brs.getString(standardValueColumnName);
        if (!ApiFunctions.isEmpty(value)) {
          value = GbossFactory.getInstance().getDescription(value);
        }
      }
      //if there is still no value for the data element, return
      if (ApiFunctions.isEmpty(ApiFunctions.safeTrim(value))) {
        return;
      }
      //otherwise, grab the other value if appropriate 
      if (detDataElement.isHasOtherValue()) {
        cmd = ProductQueryMetaData.getColumnMetaData(ProductQueryMetaDataKc.getColumnKeyOtherValue(detDataElement));
        String otherValueColumnName = cmd.getColumnAlias();
        otherValue = brs.getString(otherValueColumnName);
      }
      //now that we have the data, create an element value on the data element
      ElementValue elementValue = dataElement.createElementValue();
      elementValue.setValue(value);
      elementValue.setValueOther(otherValue);
      
      //store the id of the form instance from which the value came, as we'll need it for handling
      //multivalued attributes
      cmd = ProductQueryMetaData.getColumnMetaData(ProductQueryMetaDataKc.getColumnKeyFormId(getDomainObjectType()));
      String formIdColumnName = cmd.getColumnAlias();
      Map domainObjectMap = (Map)_dataElementPopulationSource.get(domainObjectId);
      if (domainObjectMap == null) {
        domainObjectMap = new HashMap();
        _dataElementPopulationSource.put(domainObjectId, domainObjectMap);
      }
      domainObjectMap.put(detDataElement.getCui(), brs.getString(formIdColumnName));
    }
    catch (SQLException e) {
      throw new ApiException(e);
    }
    
  }

  /**
  * Method to append an order by clause to the query contained in a string buffer
  *
  */
  protected void appendOrderBy(StringBuffer sqlBuf, List columnKeys) {
    Iterator columnKeyIterator = columnKeys.iterator();
    boolean firstEntry = true;
    while (columnKeyIterator.hasNext()) {
      if (firstEntry) {
        sqlBuf.append("order by ");
      }
      else {
        sqlBuf.append(", ");
      }
      firstEntry = false;
      String columnKey = (String)columnKeyIterator.next();
      ColumnMetaData column = ProductQueryMetaData.getColumnMetaData(columnKey);
      sqlBuf.append(column.getTableAlias());
      sqlBuf.append(".");
      sqlBuf.append(column.getColumnAlias());
      sqlBuf.append(" desc");
    }
  }
  
  private void initializeDomainObjectsWithFormInstances(Map map) {
    Iterator keyIterator = map.keySet().iterator();
    while (keyIterator.hasNext()) {
      String key = (String)keyIterator.next();
      BigrFormInstanceEnabled domainObject = (BigrFormInstanceEnabled)map.get(key);
      BigrFormInstance bigrFormInstance = new BigrFormInstance();
      bigrFormInstance.setDomainObjectId(key);
      bigrFormInstance.setDomainObjectType(getDomainObjectType());
      domainObject.setBigrFormInstance(bigrFormInstance);
      //for every element we retrieved, create a data element on the form instance
      Iterator cuiIterator = _detDataElements.keySet().iterator();
      while (cuiIterator.hasNext()) {
        DataElement dataElement = new DataElement((String)cuiIterator.next());
        bigrFormInstance.addDataElement(dataElement);
      }
    }
  }

  /**
   * Return the number of ids to query against in a single query when
   * fetching detail rows.
   * 
   * @return the number of ids
   */
  private int getIdBatchSize() {
    return ApiProperties.getPropertyAsInt(
      "api.bigr.library." + ApiFunctions.shortClassName(getClass().getName()) + ".batch.size",
      500);
  }

  /**
   * Return the Oracle query optimizer hint to use when fetching detail rows,
   * or null if there is no hint defined.
   *
   * @return the Oracle query optimizer hint
   */
  private String getQueryHint(String elementTableKey, String formTableKey) {
    // For most query builders we read the hint from the Api.properties file, but that
    // doesn't work for the KC query builder since the tables involved in the query
    // aren't static: they depend on which KC data element was selected and what kind of domain
    // object the data is associated with.  So we generate the hint dynamically here.
    
    TableMetaData elementTable = ProductQueryMetaData.getTableMetaData(elementTableKey);
    TableMetaData formTable = ProductQueryMetaData.getTableMetaData(formTableKey);
    String elementTableAlias = elementTable.getTableAlias();
    String formTableAlias = formTable.getTableAlias();
    
    StringBuffer sb = new StringBuffer(100);
    
    sb.append("INDEX(");
    sb.append(formTableAlias);
    sb.append(" CIR_FORM_IN2) LEADING(");
    sb.append(formTableAlias);
    sb.append(") USE_NL(");
    sb.append(elementTableAlias);
    sb.append(')');

    return sb.toString();
  }

  public String getDomainObjectType() {
    return _domainObjectType;
  }
  
}
