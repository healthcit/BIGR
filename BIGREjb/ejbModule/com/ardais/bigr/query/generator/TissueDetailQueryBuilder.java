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
import com.ardais.bigr.javabeans.AsmData;
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
public class TissueDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * Indicates whether AsmData beans associated with the samples should be 
   * created when the query is run.
   */
  private boolean _createAsmData = false;

  /**
   * A map of AsmData beans that is created when the query is run.
   */
  private Map _asmData;

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>TissueDetailQueryBuilder</code>.
   */
  public TissueDetailQueryBuilder() {
    super();
  }

  /**
   * Specifies that the appearance best from sample be returned in this 
   * detail query.
   */
  public void addColumnSampleAppearanceBest() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_APPEARANCE_BEST);
  }

  /**
   * Specifies that the age at collection column be returned in this 
   * detail query.
   */
  public void addColumnAgeAtCollection() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_AGE_AT_COLLECTION);
  }

  /**
   * Specifies that the sample customer_id column be returned in this 
   * detail query.
   */
  public void addColumnCustomerId() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_CUSTOMER_ID);
  }

  /**
   * Specifies that the ASM position column be returned in this 
   * detail query.
   */
  public void addColumnAsmPosition() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_ASM_POSITION);
  }

  /**
   * Specifies that the discordant column be returned in this 
   * detail query.
   */
  public void addColumnDiscordant() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_DISCORDANT);
  }

  /**
   * Specifies that the format detail column be returned in this 
   * detail query.
   */
  public void addColumnFormatDetail() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_FORMAT_DETAIL);
  }

  /**
   * Specifies that the fixative type column be returned in this 
   * detail query.
   */
  public void addColumnFixativeType() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_FIXATIVE_TYPE);
  }
  
  /**
   * Specifies that the sample type column be returned in this 
   * detail query.
   */
  public void addColumnSampleType() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_SAMPLE_TYPE_CID);
  }
  
  /**
   * Specifies that the sample source column be returned in this 
   * detail query.
   */
  public void addColumnSampleSource() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_SOURCE);
  }

  /**
   * Specifies that the sample's inventory status column be returned in this 
   * detail query.
   */
  public void addColumnInventoryStatus() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_INV_STATUS);
  }

  /**
   * Specifies that the sample's inventory status date column be returned in 
   * this detail query.
   */
  public void addColumnInventoryStatusDate() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_INV_STATUS_DATE);
  }

  /**
   * Specifies that the origin tissue from the sample column be returned 
   * in this detail query.
   */
  public void addColumnSampleTissueOrigin() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_TISSUE_ORIGIN_CUI);
  }

  /**
   * Specifies that the other origin tissue from the sample column be returned 
   * in this detail query.
   */
  public void addColumnSampleTissueOriginOther() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_TISSUE_ORIGIN_OTHER);
  }

  /**
   * Specifies that the sample's project status column be returned in this 
   * detail query.
   */
  public void addColumnProjectStatus() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_PROJECT_STATUS);
  }

  /**
   * Specifies that the sample's project status date column be returned in 
   * this detail query.
   */
  public void addColumnProjectStatusDate() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_PROJECT_STATUS_DATE);
  }

  /**
   * Specifies that the pull yn column be returned in this 
   * detail query.
   */
  public void addColumnPull() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_PULLED);
  }

  /**
   * Specifies that the pull date column be returned in this 
   * detail query.
   */
  public void addColumnPullDate() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_PULL_DATE);
  }

  /**
   * Specifies that the pulled reason column be returned in this 
   * detail query.
   */
  public void addColumnPulledReason() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_PULLED_REASON);
  }

  /**
   * Specifies that the qcposted column be returned in this 
   * detail query.
   */
  public void addColumnQcPosted() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_QCPOSTED);
  }

  /**
   * Specifies that the sample's QC status column be returned in this 
   * detail query.
   */
  public void addColumnHistoNotes() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_HISTO_NOTES);
  }

  /**
   * Specifies that the sample's Histo Notes column be returned in this 
   * detail query.
   */
  public void addColumnQcStatus() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_QCSTATUS);
  }

  /**
   * Specifies that the sample's QC status date column be returned in 
   * this detail query.
   */
  public void addColumnQcStatusDate() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_QCSTATUS_DATE);
  }

  /**
   * Specifies that the qc verified column be returned in this 
   * detail query.
   */
  public void addColumnQcVerified() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_QCVERIFIED);
  }

  /**
   * Specifies that the released column be returned in this 
   * detail query.
   */
  public void addColumnReleased() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_RELEASED);
  }

  /**
   * Specifies that the restricted column be returned in this 
   * detail query.
   */
  public void addColumnRestricted() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_RESTRICTED);
  }

  /**
   * Specifies that the sample's sales status column be returned in this 
   * detail query.
   */
  public void addColumnSalesStatus() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_SALES_STATUS);
  }

  /**
   * Specifies that the sample's sales status date column be returned in 
   * this detail query.
   */
  public void addColumnSalesStatusDate() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_SALES_STATUS_DATE);
  }

  /**
   * Specifies that the sample's sales status date column be returned in 
   * this detail query.
   */
  public void addColumnSampleSubdivisionDate() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_SUBDIVISION_DATE);
  }

  /**
   * Specifies that the sample'sbox barcode id column be returned in 
   * this detail query.
   */
  public void addColumnSampleBoxBarcodeId() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_BOX_BARCODE_ID);
  }

  /**
   * Specifies that the ASM id column from sample be returned 
   * in this detail query.
   */
  public void addColumnSampleAsmId() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_ASM_ID);
  }

  /**
   * Specifies that the ASM note column from sample be returned 
   * in this detail query.
   */
  public void addColumnSampleAsmNote() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_ASM_NOTE);
  }

  /**
   * Specifies that the sample id column from be returned in this 
   * detail query.
   */
  public void addColumnSampleId() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_ID);
  }

  /**
   * Specifies that the sample section count column from be returned in this 
   * detail query.
   */
  public void addColumnSampleSectionCount() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_SECTION_COUNT);
  }

  public void addColumnRnaId() {
    addColumnInSample(ColumnMetaData.KEY_RNA_RNAVIALID);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_POOL_TISSUE);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_RNA_POOL_BATCH_DETAIL_OUTER);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_SAMPLE_RNA_POOL_OUTER);
  }

  public void addColumnTmaId() {
    addColumnInSample(ColumnMetaData.KEY_TMA_BLOCKSETID);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_TMA_BLOCK_SAMPLES);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_SAMPLE_TMA_BLOCK_SAMPLES_OUTER);
  }

  /**
   * Specifies that the bms yn column be returned in this 
   * detail query.
   */
  public void addColumnBmsYN() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_BMS_YN);
  }

  /**
   * Specifies that the cell_ref_location column be returned in this 
   * detail query.
   */
  public void addColumnCellRefLocation() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_LOCATION);
  }
  
  /**
   * 
   */
  public void addColumnSampleVolume() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_VOLUME);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_VOLUME_UNIT_CUI);
  }
  /**
   * 
   */
  public void addColumnSampleWeight() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_WEIGHT);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_WEIGHT_UNIT_CUI);
  }
  /**
   * 
   */
  public void addColumnSampleConcentration() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_CONCENTRATION);
  }

  /**
   * 
   */
  public void addColumnSampleYield() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_YIELD);
  }
  
  /**
   * 
   */
  public void addColumnSampleBufferType() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_BUFFER_TYPE_CUI);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_BUFFER_TYPE_OTHER);
  }

  /**
   * 
   */
  public void addColumnSampleTotalNumOfCells() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_TOTAL_NUM_OF_CELLS);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_TOTAL_NUM_OF_CELLS_EX_REP_CUI);
  }

  /**
   * 
   */
  public void addColumnSampleCellsPerMl() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_CELLS_PER_ML);
  }

  /**
   * 
   */
  public void addColumnSamplePercentViability() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_PERCENT_VIABILITY);
  }

  /**
   * 
   */
  public void addColumnSampleDateOfCollection() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_COLLECTION_DATETIME);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_COLLECTION_DATETIME_DPC);
  }
  /**
   * 
   */
  public void addColumnSampleDateOfPreservation() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_PRESERVATION_DATETIME);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_PRESERVE_DATETIME_DPC);
  }

  /**
   * 
   */
  public void addColumnSampleElapsedTime() {
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_COLLECTION_DATETIME);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_COLLECTION_DATETIME_DPC);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_PRESERVATION_DATETIME);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_PRESERVE_DATETIME_DPC);
  }

  /**
   * Add a column from the sample table to this detail query.
   */
  private void addColumnInSample(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  /**
   * Sets whether {@link com.ardais.bigr.javabeans.AsmData} beans associated 
   * with the samples should be created when the query is run.
   * 
   * @param  create  <code>true</code> if associated AsmData beans should
   * 									be created and added to each sample data bean
   */
  public void setCreateAsmData(boolean create) {
    _createAsmData = create;
    if (create) {
      addColumnSampleAsmId();
    }
  }

  /**
   * Returns whether {@link com.ardais.bigr.javabeans.AsmData} beans associated 
   * with the samples should be created when the query is run.
   * 
   * @return  <code>true</code> if associated AsmData beans should
   * 					 be created and added to each sample data bean
   */
  public boolean isCreateAsmData() {
    return _createAsmData;
  }

  /**
   * Populates the sample data beans in the specified map, with the columns
   * specified by the addColumn methods in this class.  In addition, creates
   * the sample's associated ASM data bean and adds it to the sample data
   * bean if setCreateAsmDate(true) was called.
   * 
   * @param  A <code>Map</code> of {@link com.ardais.bigr.javabeans.SampleData}
   * 				  beans, keyed by their sample id.
   */
  public void getDetails(Map sampleData) {

    addColumnSampleId();

    if (isCreateAsmData()) {
      _asmData = new HashMap();
    }

    Connection con = null;
    BigrPreparedStatement pstmt = null;
    BigrResultSet brs = new BigrResultSet(new StringCanonicalizer());
    Map columns = null;

    try {
      con = ApiFunctions.getDbConnection();

      int idBatchSize = getIdBatchSize();

      List idChunks =
        ApiFunctions.chunkStrings(
          (String[]) sampleData.keySet().toArray(new String[0]),
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
          String sampleId = brs.getString(DbAliases.SAMPLE_ID);
          SampleData sample = (SampleData) sampleData.get(sampleId);
          sample.populate(columns, brs);

          if (isCreateAsmData()) {
            String asmId = brs.getString(DbAliases.SAMPLE_ASM_ID);
            AsmData asm = (AsmData) _asmData.get(asmId);
            if (asm == null) {
              asm = new AsmData(columns, brs);
              _asmData.put(asmId, asm);
            }
            sample.setAsmData(asm);
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

  /**
   * Returns a map of all of the ASMs for all of the samples that were
   * returned in the previous call to getDetails().  If 
   * setCreateAsmData(true) was not called before calling getDetails, 
   * this will return <code>null</code>.
   * 
   * @return  A <code>Map</code> of {@link com.ardais.bigr.javabeans.AsmData}
   * 				  beans, keyed by their ASM id.  
   */
  public Map getAsmData() {
    return _asmData;
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
