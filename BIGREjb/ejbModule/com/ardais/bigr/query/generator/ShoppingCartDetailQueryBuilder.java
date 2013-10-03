package com.ardais.bigr.query.generator;

import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.StringCanonicalizer;
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
public class ShoppingCartDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>ShoppingCartDetailQueryBuilder</code>.
   */
  public ShoppingCartDetailQueryBuilder() {
    super();
  }

  /**
   * Specifies that the shopping cart user id column be returned in this 
   * detail query.
   */
  public void addColumnShoppingCartUser() {
    addColumnInShoppingCart(ColumnMetaData.KEY_SHOPPING_CART_USER_ID);
  }

  /**
   * Specifies that the shopping cart creation date column be returned in this 
   * detail query.
   */
  public void addColumnShoppingCartCreationDate() {
    addColumnInShoppingCart(ColumnMetaData.KEY_SHOPPING_CART_CREATION_DATE);
  }

  /**
   * Specifies that the shopping cart amount (in DB as quantity) column be returned in this 
   * detail query.  This is the amount for each cart record, not total amount on hold.
   */
  public void addColumnShoppingCartAmount() {
    addColumnInShoppingCart(ColumnMetaData.KEY_SHOPPING_CART_AMOUNT);
  }

  /**
   * Specifies that the sample if column be returned in this detail query.
   */
  public void addColumnSampleId() {
    addColumnInShoppingCart(ColumnMetaData.KEY_SHOPPING_CART_SAMPLE_ID);
  }

  /**
   * Add a column from the shopping cart detail table to this detail query.
   */
  private void addColumnInShoppingCart(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_SHOPPING_CART_DETAIL);
  }

  /**
   * Populates the {@link com.ardais.bigr.javabeans.SampleData}
   * data beans for the samples in the specified map, with the columns specified 
   * by the addColumn methods in this class.
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.SampleData}
   * 				  beans, keyed by the id to join to the shopping cart on.
   * 
   * If the keys are RNA IDS the sampleData will be populated with data about RNA on hold.
   * If the keys are Tissue IDS the sampleData will be populated by joining these ids
   * to the shopping cart.  The mechanism is the same -- query for records in the shopping cart
   * and use the map to know how to associate the records returned with the SampleData to store
   * the results on.
   */
  public void getDetailsForSamples(Map sampleDataBeans) {

    addColumnSampleId();
    addColumnShoppingCartAmount(); // always here, no need to add externally

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
        _queryBuilder.addFilter(FilterMetaData.KEY_SHOPPING_CART_SAMPLE_ID, chunk);
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
          String sampleId = brs.getString(DbAliases.SHOPPING_CART_SAMPLE_ID);
          SampleData sampleData = (SampleData) sampleDataBeans.get(sampleId);
          String uid = brs.getString(DbAliases.SHOPPING_CART_USER_ID);
          Date createDt = brs.getDate(DbAliases.SHOPPING_CART_CREATION_DATE);
          int amt = brs.getInt(DbAliases.SHOPPING_CART_AMOUNT);
          Integer amtInt = amt == 0 ? null : new Integer(amt);
          sampleData.addCartEntry(uid, createDt, amtInt);
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
