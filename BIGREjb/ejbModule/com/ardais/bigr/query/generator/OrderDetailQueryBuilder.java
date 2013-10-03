package com.ardais.bigr.query.generator;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.StringCanonicalizer;
import com.ardais.bigr.javabeans.OrderData;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;

/**
 */
public class OrderDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * A map of OrderData beans that is created when the query is run.
   */
  private Map _orderData;

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>OrderDetailQueryBuilder</code>.
   */
  public OrderDetailQueryBuilder() {
    super();
  }

  /**
   * Specifies that the order description column be returned in this 
   * detail query.
   */
  public void addColumnOrderDescription() {
    addColumnInOrder(ColumnMetaData.KEY_ORDER_DESCRIPTION);
  }

  /**
   * Specifies that the order description column be returned in this 
   * detail query.
   */
  public void addColumnOrderNumber() {
    addColumnInOrder(ColumnMetaData.KEY_ORDER_NUMBER);
  }

  /**
   * Specifies that the order description column be returned in this 
   * detail query.
   */
  public void addColumnSampleId() {
    addColumnInOrderLine(ColumnMetaData.KEY_LINE_SAMPLE_ID);
  }

  /**
   * Add a column from the Order table to this detail query.
   */
  private void addColumnInOrder(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_ORDER);
  }

  /**
   * Add a column from the Order table to this detail query.
   */
  private void addColumnInOrderLine(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_ORDER);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_ORDER_LINE);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_LINE_ORDER);
  }

  /**
   * Indicates that the query should only return reported pathology evaluations.
   */
  public void addFilterShippedOnly() {
    _queryBuilder.addFilter(FilterMetaData.KEY_ORDER_SHIPPED);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_ORDER);
  }

  /**
   * Creates and populates {@link com.ardais.bigr.iltds.assistants.ProjectDataBean}
   * data beans for the samples in the specified map, with the columns specified 
   * by the addColumn methods in this class.  The ProjectDataBean beans
   * are set onto their associated samples.
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.SampleData}
   * 				  beans, keyed by their sample id.
   */
  public void getDetailsForSamples(Map sampleDataBeans) {

    addColumnOrderNumber();
    addColumnSampleId();

    _orderData = new HashMap();

    Connection con = null;
    BigrPreparedStatement pstmt = null;
    BigrResultSet brs = new BigrResultSet(new StringCanonicalizer());
    Map columns = null;

    try {
      con = ApiFunctions.getDbConnection();

      int idBatchSize = getIdBatchSize();

      List idChunks =
        ApiFunctions.chunkStrings(
          (String[]) sampleDataBeans.keySet().toArray(new String[0]),
          idBatchSize);
      Iterator chunks = idChunks.iterator();
      boolean first = true;
      while (chunks.hasNext()) {
        String[] chunk = (String[]) chunks.next();
        _queryBuilder.addFilter(FilterMetaData.KEY_LINE_SAMPLE_ID, chunk);
        if (first || (chunk.length != idBatchSize)) {
          StringBuffer sqlBuf = new StringBuffer(1024);
          _queryBuilder.setOptimizerHint(getQueryHint());
          // don't let this hint be superceded by a filter hint
          _queryBuilder.setHintPriority(ProductQueryBuilder.MAX_HINT_PRIORITY); 
          _queryBuilder.getQuery(sqlBuf);
          if (pstmt != null) {
            pstmt.close();
            pstmt = null;
          }
          pstmt = new BigrPreparedStatement(con, sqlBuf.toString());
        }

        _queryBuilder.bindAllParameters(pstmt);
        if (first && _queryLog.isDebugEnabled()) {
          _queryLog.debug("Detail query for " + ApiFunctions.shortClassName(getClass().getName()));
          _queryLog.debug(pstmt.toString());
        }
        brs.setResultSet(pstmt.executeQuery());

        if (columns == null) {
          columns = DbUtils.getColumnNames(brs);
        }
        while (brs.next()) {
          String orderId = brs.getString(DbAliases.ORDER_NUMBER);
          OrderData order = (OrderData) _orderData.get(orderId);
          if (order == null) {
            order = new OrderData(columns, brs);
            _orderData.put(orderId, order);
          }

          String sampleId = brs.getString(DbAliases.LINE_SAMPLE_ID);
          SampleData sampleData = (SampleData) sampleDataBeans.get(sampleId);
          sampleData.setOrderData(order);
        }
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

  /**
   * Returns a map of all of the order for all of the samples that were
   * specified in the previous call to getDetails().
   * 
   * @return  A <code>Map</code> of {@link com.ardais.bigr.javabeans.OrderData}
   * 				  beans, keyed by their order number.
   */
  public Map getOrderData() {
    return _orderData;
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
  private String getQueryHint() {
    return ApiProperties.getProperty(
      "api.bigr.library." + ApiFunctions.shortClassName(getClass().getName()) + ".hint");
  }

}
