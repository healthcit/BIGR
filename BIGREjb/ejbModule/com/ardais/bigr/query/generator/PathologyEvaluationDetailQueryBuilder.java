package com.ardais.bigr.query.generator;

import java.sql.Connection;
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
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;

/**
 */
public class PathologyEvaluationDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>PathologyEvaluationQueryBuilder</code>.
   */
  public PathologyEvaluationDetailQueryBuilder() {
    super();
  }

  /**
   * Specifies that the acellular stroma composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionAcellularStroma() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_TAS);
  }

  /**
   * Specifies that the cellular stroma composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionCellularStroma() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_TCS);
  }

  /**
   * Specifies that the lesion composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionLesion() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_LSN);
  }

  /**
   * Specifies that the necrosis composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionNecrosis() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_NEC);
  }

  /**
   * Specifies that the normal composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionNormal() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_NRM);
  }

  /**
   * Specifies that the tumor composition column be returned in this 
   * detail query.
   */
  public void addColumnCompositionTumor() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_TMR);
  }

  /**
   * Specifies that the LIMS diagnosis column be returned in this 
   * detail query.
   */
  public void addColumnDiagnosis() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_DX);
  }

  /**
   * Specifies that the LIMS diagnosis other column be returned in this 
   * detail query.
   */
  public void addColumnDiagnosisOther() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_OTHER_DIAGNOSIS);
  }

  /**
   * Specifies that the microscopic appearance column be returned in this 
   * detail query.
   */
  public void addColumnMicroscopicAppearance() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_APPEARANCE);
  }

  /**
   * Specifies that the LIMS finding tissue column be returned in this 
   * detail query.
   */
  public void addColumnTissueFinding() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_TISSUE_FINDING);
  }

  /**
   * Specifies that the LIMS finding tissue other column be returned in this 
   * detail query.
   */
  public void addColumnTissueFindingOther() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_OTHER_TISSUE_FINDING);
  }

  /**
   * Specifies that the LIMS origin tissue column be returned in this 
   * detail query.
   */
  public void addColumnTissueOrigin() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_TISSUE_ORIGIN);
  }

  /**
   * Specifies that the LIMS origin tissue other column be returned in this 
   * detail query.
   */
  public void addColumnTissueOriginOther() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_OTHER_TISSUE_ORIGIN);
  }

  /**
   * Specifies that the LIMS pv notes column be returned in this 
   * detail query.
   */
  public void addColumnPvNotes() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_EXTERNAL_COMMENTS);
  }

  /**
   * Specifies that the internal LIMS pv notes column be returned in this 
   * detail query.
   */
  public void addColumnPvNotesInternal() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_INTERNAL_COMMENTS);
  }

  /**
   * Specifies that the LIMS pv create_user column be returned in this 
   * detail query.
   */
  public void addColumnPvCreateUser() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_CREATE_USER);
  }

  /**
   * Specifies that the LIMS pv slide id column be returned in this 
   * detail query.
   */
  public void addColumnSlideId() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_SLIDE_ID);
  }

  /**
   * Specifies that the LIMS pv create date column be returned in this 
   * detail query.
   */
  public void addColumnPvCreateDate() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_CREATE_DATE);
  }

  /**
   * Specifies that the LIMS pv reported date column be returned in this 
   * detail query.
   */
  public void addColumnPvReportedDate() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_REPORTED_DATE);
  }

  /**
   * Specifies that the LIMS pv id column be returned in this 
   * detail query.
   */
  public void addColumnPvId() {
    addColumnInLimsPe(ColumnMetaData.KEY_PE_ID);
  }

  /**
   * Specifies that the sample id column be returned in this detail query.
   */
  public void addColumnSampleId() {
    addColumnInLimsPe(ColumnMetaData.KEY_LIMS_PE_SAMPLE_ID);
  }

  /**
   * Add a column from the LIMS pathology evaluation table to this detail query.
   */
  private void addColumnInLimsPe(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_LIMS_PE);
  }

  /**
   * Indicates that the query should only return reported pathology evaluations.
   */
  public void addFilterReportedOnly() {
    _queryBuilder.addFilter(FilterMetaData.KEY_LIMS_PE_REPORTED);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_LIMS_PE);
  }

  /**
   * Creates and populates {@link com.ardais.bigr.lims.javabeans.PathologyEvaluation}
   * data beans for the samples in the specified map, with the columns specified 
   * by the addColumn methods in this class.  The PathologyEvaluation data beans
   * are set onto their associated samples.
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.SampleData}
   * 				  beans, keyed by their sample id.
   */
  public void getDetailsForSamples(Map sampleDataBeans) {

    addColumnSampleId();

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
        _queryBuilder.addFilter(FilterMetaData.KEY_LIMS_PE_SAMPLE_ID, chunk);
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
          PathologyEvaluationData peData = new PathologyEvaluationData(columns, brs);
          String sampleId = brs.getString(DbAliases.LIMS_PE_SAMPLE_ID);
          SampleData sampleData = (SampleData) sampleDataBeans.get(sampleId);
          sampleData.setPathologyEvaluationData(peData);
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
