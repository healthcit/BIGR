package com.ardais.bigr.query.generator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.StringCanonicalizer;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.ardais.bigr.javabeans.SampleData;

/**
 */
public class  SampleLocationDetailQueryBuilder  {

  private SecurityInfo _securityInfo;

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>ConsentDetailQueryBuilder</code>.
   */
  public  SampleLocationDetailQueryBuilder (SecurityInfo securityInfo) {
    super();
    _securityInfo = securityInfo;
  }

  /**
  * Add a column from the box location table
  */
  private void addColumnInBoxLocation(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_SAMPLE_BOX_LOCATION);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_SAMPLE);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_BOX_LOCATION);
  }


  /**
  * Add a column from the sample table
  */
  private void addColumnInSample(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  
  /**
   * Specifies that the item id column be returned in this 
   * detail query.
   */
  public void addSampleLocationAddressId() {
    addColumnInBoxLocation(ColumnMetaData.KEY_BOX_LOCATION_ADDRESS_ID);
  }

  
 // spira #IN:000621
  public void addSampleLocationFields()
  {
    addColumnInBoxLocation(ColumnMetaData.KEY_BOX_LOCATION_ADDRESS_ID);
    addColumnInBoxLocation(ColumnMetaData.KEY_BOX_LOCATION_ROOM_ID);
    addColumnInBoxLocation(ColumnMetaData.KEY_BOX_LOCATION_UNIT_NAME);
    addColumnInBoxLocation(ColumnMetaData.KEY_BOX_LOCATION_DRAWER_ID);
    addColumnInBoxLocation(ColumnMetaData.KEY_BOX_LOCATION_SLOT_ID);
    addColumnInBoxLocation(ColumnMetaData.KEY_BOX_LOCATION_BOX_ID);
  }
  /**
   * Specifies that the sample id column be returned in this 
   * detail query.
   */
  public void addSampleId() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_ID);
  }




  /**
   * Populates the box location information on the box location beans in the specified map
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.SampleData}
   *          beans, keyed by their sample id.
   */
  public void getDetails(Map sampleDataBeans) {
//    addSampleLocationAddressId();
    addSampleLocationFields();
    addSampleId();

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
        _queryBuilder.addFilter(FilterMetaData.KEY_SAMPLE_ID, chunk);
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
          // get the data bean to fill...
          SampleData sample =
            (SampleData) sampleDataBeans.get(brs.getString(DbAliases.SAMPLE_ID));
 
          // instantiate a new object
          BTXBoxLocation boxloc = new BTXBoxLocation();
 
          // get the value from this row in the result set...
          String locationAddressId = brs.getString(DbAliases.BOX_LOCATION_ADDRESS_ID);
          
          // fill the value from the result set into the object...
          boxloc.setLocationAddressID(locationAddressId);
          
          // spira #IN:000621
          boxloc.setDrawerID( brs.getString(DbAliases.BOX_LOCATION_DRAWER_ID));
          boxloc.setRoomID( brs.getString(DbAliases.BOX_LOCATION_ROOM_ID));
          boxloc.setUnitName( brs.getString(DbAliases.BOX_LOCATION_UNIT_NAME));
          boxloc.setSlotID( brs.getString(DbAliases.BOX_LOCATION_SLOT_ID));
          boxloc.setBoxBarcodeID( brs.getString(DbAliases.BOX_LOCATION_BOX_BARCODE_ID));
          
          // fill the data bean with the object
          sample.setStorageLocation(boxloc);
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

