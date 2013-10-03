package com.ardais.bigr.query.generator;

import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.StringCanonicalizer;
import com.ardais.bigr.javabeans.RnaData;
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
public class RnaBatchDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * Indicates whether SampleData beans associated with the RNA should be 
   * created when the query is run.
   */
  private boolean _createSampleData = false;

  /**
   * A map of SampleData beans that is created when the query is run.
   */
  private Map _sampleData;

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>RnaBatchDetailQueryBuilder</code>.
   */
  public RnaBatchDetailQueryBuilder() {
    super();
  }

  /**
   * Specifies that the consent id column from RNA batch detail be returned 
   * in this detail query.
   */
  public void addColumnConsentId() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_CONSENT_ID);
  }

  /**
   * Specifies that the amount of RNA remaining in inventory.  Note that
   * some of it may be on hold, so this is not the amount available to promise.
   */
  public void addColumnHoldAmount() {
    addColumnInRnaHoldAmount(ColumnMetaData.KEY_RNA_HOLD_AMOUNT);
  }
  //  public void addColumnHoldVolume() {
  //    addColumnInRnaHold(ColumnMetaData.KEY_RNA_HOLD_VOLUME);
  //  }

  /**
   * Specifies that the RNA prep column from be returned in this 
   * detail query.
   */
  public void addColumnPrep() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_PREP);
  }

  /**
   * Specifies that the RNA quality be returned in this detail query.
   */
  public void addColumnQuality() {
    addColumnInRnaGrossExtraction(ColumnMetaData.KEY_RNA_QUALITY);
  }

  /**
   * Specifies that the RNA bio ratio be returned in this detail query.
   */
  public void addColumnRatio() {
    addColumnInRnaBioanalyser(ColumnMetaData.KEY_RNA_BIORATIO);
  }

  /**
   * Specifies that the amount of RNA remaining in inventory.  Note that
   * some of it may be on hold, so this is not the amount available to promise.
   */
  public void addColumnRemainingVolume() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_REMAINING_VOLUME);
  }

  /**
   * Specifies that the RNA vial id column from be returned in this 
   * detail query.
   */
  public void addColumnRnaVialId() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_RNAVIALID);
  }

  /**
   * Specifies that the bms_yn column be returned in this 
   * detail query.
   */
  public void addColumnBmsYN() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_BMS_YN);
  }

  /**
   * Specifies that the RNA status column from be returned in this 
   * detail query.
   */
  public void addColumnStatus() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_STATUS);
  }

  /**
   * Specifies that the RNA Concentration column from be returned in this 
   * detail query.
   */
  public void addColumnConcentration() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_CONCENTRATION);
  }

  /**
   * Specifies that the RNA status column from be returned in this 
   * detail query.
   */
  public void addColumnSampleId() {
    addColumnInRnaPoolTissue(ColumnMetaData.KEY_RNA_POOL_SAMPLE_ID);
  }

  /**
   * Specifies that the RNA PooledTissue column from be returned in this 
   * detail query.
   */
  public void addColumnPooledTissue() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_POOLED_TISSUE);
  }

  /**
   * Specifies that the RNA Rep_Sample column from be returned in this 
   * detail query.
   */
  public void addColumnRepSample() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_REP_SAMPLE);
  }

  /**
   * Specifies that the RNA case_exhausted column from be returned in this 
   * detail query.
   */
  public void addColumnCaseExhausted() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_CASE_EXHAUSTED);
  }

  /**
   * Specifies that the RNA TUMOR_CELLS column from be returned in this 
   * detail query.
   */
  public void addColumnCompositionTumor() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_PE_TMR);
  }

  /**
   * Specifies that the RNA LESION_CELLS column from be returned in this 
   * detail query.
   */
  public void addColumnCompositionLesion() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_PE_LSN);
  }

  /**
   * Specifies that the RNA NORMAL_CELLS column from be returned in this 
   * detail query.
   */
  public void addColumnCompositionNormal() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_PE_NRM);
  }

  /**
   * Specifies that the RNA CELLULARSTROMA_CELLS column from be returned in this 
   * detail query.
   */
  public void addColumnCompositionCellularStroma() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_PE_TCS);
  }

  /**
   * Specifies that the RNA HYPOACELLULARSTROMA_CELLS column from be returned in this 
   * detail query.
   */
  public void addColumnCompositionAcellularStroma() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_PE_TAS);
  }

  /**
   * Specifies that the RNA NECROSIS_CELLS column from be returned in this 
   * detail query.
   */
  public void addColumnCompositionNecrosis() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_PE_NEC);
  }

  /**
   * Specifies that the RNA EXTERNAL_COMMENTS column from be returned in this 
   * detail query.
   */
  public void addColumnPvNotes() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_EXTERNAL_COMMENTS);
  }

  /**
   * Specifies that the RNA INTERNAL_COMMENTS column from be returned in this 
   * detail query.
   */
  public void addColumnInternalComments() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_INTERNAL_COMMENTS);
  }

  /**
   * Specifies that the RNA NOTES column from be returned in this 
   * detail query.
   */
  public void addColumnNotes() {
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_NOTES);
  }

  /**
   * Add a column from the RNA batch detail table to this detail query.
   */
  private void addColumnInRnaBatchDetail(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
  }

  /**
   * Add a column from the RNA hold table to this detail query.
   */
  private void addColumnInRnaHoldAmount(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_HOLD_AMOUNT);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
    _queryBuilder.addJoin(FilterMetaData.KEY_OUTER_JOIN_RNA_RNA_HOLD);
  }

  /**
   * Add a column from the RNA gross extraction table to this detail query.
   */
  private void addColumnInRnaGrossExtraction(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_GROSS_EXTRACTION);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_RNA_GROSS_EXTRACTION);
  }

  /**
   * Add a column from the RNA bioanalyser table to this detail query.
   */
  private void addColumnInRnaBioanalyser(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_BIOANALYSER);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_RNA_BIOANALYSER);
  }

  /**
   * Add a column from the RNA pool tissue table to this detail query.
   */
  private void addColumnInRnaPoolTissue(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_POOL_TISSUE);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_RNA_POOL_BATCH_DETAIL);
  }

  /**
   * Sets whether {@link com.ardais.bigr.javabeans.SampleData} beans 
   * associated with the RNA should be created when the query is run.
   * 
   * @param  create  <code>true</code> if associated SampleData beans should
   * 									be created and added to each RNA data bean
   */
  public void setCreateSampleData(boolean create) {
    _createSampleData = create;
    if (create) {
      addColumnSampleId();
    }
  }

  /**
   * Returns whether {@link com.ardais.bigr.javabeans.SampleData} beans 
   * associated with the RNA should be created when the query is run.
   * 
   * @return  <code>true</code> if associated SampleData beans should
   * 					 be created and added to each RNA data bean
   */
  public boolean isCreateSampleData() {
    return _createSampleData;
  }

  /**
   * Populates the RNA data beans in the specified map, with the columns
   * specified by the addColumn methods in this class.  In addition, creates
   * and the RNA's associated consent data bean and adds it to the ASM data
   * bean if addColumnAsmConsentId() was called.
   * 
   * @param  A <code>Map</code> of {@link com.ardais.bigr.javabeans.AsmData}
   * 				  beans, keyed by their ASM id.
   */
  public void getDetailsForRna(Map rnaData) {

    addColumnRnaVialId();

    if (isCreateSampleData()) {
      _sampleData = new HashMap();
    }

    // We'll keep a separate HashSet of all RNA ids since the query may return
    // duplicate RNA ids if we're joining to the pool tissue to get the sample
    // id.  Since we only need a single representative sample, using the first
    // one returned is sufficient.
    HashSet rnaIds = new HashSet();

    Connection con = null;
    BigrPreparedStatement pstmt = null;
    BigrResultSet brs = new BigrResultSet(new StringCanonicalizer());
    Map columns = null;

    try {
      con = ApiFunctions.getDbConnection();

      int idBatchSize = getIdBatchSize();

      List idChunks =
        ApiFunctions.chunkStrings((String[]) rnaData.keySet().toArray(new String[0]), idBatchSize);
      Iterator chunks = idChunks.iterator();
      boolean first = true;
      while (chunks.hasNext()) {
        String[] chunk = (String[]) chunks.next();
        _queryBuilder.addFilter(FilterMetaData.KEY_RNA_RNAVIALID, chunk);
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
          String rnaId = brs.getString(DbAliases.RNA_RNAVIALID);
          RnaData rna = (RnaData) rnaData.get(rnaId);

          // Because of pooled-tissue RNA, we may encounter the same RNA vial id more than
          // once in this loop.  Some thing we don only the first time, other things
          // we do for tissue in the pool.
          
          if (!rnaIds.contains(rnaId)) {
            rnaIds.add(rnaId);
            rna.populate(columns, brs);

            if (isCreateSampleData()) {
              String repSampleId = brs.getString(DbAliases.RNA_REP_SAMPLE);
              SampleData repSample = getOrCreateSample(repSampleId);
              rna.setRepresentativeSample(repSample);
            }
          }

          if (isCreateSampleData()) {
            String sampleId = brs.getString(DbAliases.RNA_POOL_SAMPLE_ID);
            SampleData sample = getOrCreateSample(sampleId);
            rna.getSamples().add(sample);
          }
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

  private SampleData getOrCreateSample(String sampleId) {
    SampleData sample;
    if (_sampleData.containsKey(sampleId)) {
      sample = (SampleData) _sampleData.get(sampleId);
    }
    else {
      sample = new SampleData();
      sample.setSampleId(sampleId);
      _sampleData.put(sampleId, sample);
    }
    return sample;
  }

  /**
   * Returns a map of all of the samples for all of the RNAs that were
   * returned in the previous call to getDetails().  If 
   * setCreateSampleData(true) was not called before calling getDetails, 
   * this will return <code>null</code>.
   * 
   * @return  A <code>Map</code> of {@link com.ardais.bigr.javabeans.SampleData}
   * 				  beans, keyed by their sample id.  
   */
  public Map getSampleData() {
    return _sampleData;
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
