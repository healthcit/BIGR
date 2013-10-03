package com.ardais.bigr.query.generator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.StringCanonicalizer;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.DiagnosticTestFilterDto;
import com.ardais.bigr.lims.javabeans.DiagnosticTestResultData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;

public class DiagnosticTestDetailQueryBuilder {

  DiagnosticTestFilterDto _diagnosticDto = null;
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
  public DiagnosticTestDetailQueryBuilder(DiagnosticTestFilterDto dto) {
    super();
    _diagnosticDto = dto;
  }

  /**
   * Creates and populates {@link com.ardais.bigr.lims.javabeans.ConsentData}
   * data beans for the cases in the specified map, with the columns specified 
   * by the addColumn methods in this class.  The ConsentData data beans
   * are set onto their associated samples.
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.ConsentData}
   *          beans, keyed by their consent id.
   */
  public void getDetailsForConsents(Map consentDataBeans) {

    addColumnConsentId();
    addColumnDiagnosticConceptId();
    addColumnDiagnosticResultCid();

    Connection con = null;
    BigrPreparedStatement pstmt = null;
    BigrResultSet brs = new BigrResultSet(new StringCanonicalizer());
    Map columns = null;

    try {
      con = ApiFunctions.getDbConnection();

      int idBatchSize = getIdBatchSize();

      List idChunks =
        ApiFunctions.chunkStrings(
          (String[]) consentDataBeans.keySet().toArray(new String[0]),
          idBatchSize);
      Iterator chunks = idChunks.iterator();
      boolean first = true;
      while (chunks.hasNext()) {
        String[] chunk = (String[]) chunks.next();
        _queryBuilder.addFilter(FilterMetaData.KEY_PATH_CONSENT_ID, chunk);

        applyDiagnosticFilter();

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
          ConsentData consentData =
            (ConsentData) consentDataBeans.get(brs.getString(DbAliases.PATH_CONSENT_ID));

          DiagnosticTestResultData data = new DiagnosticTestResultData();
          data.populate(columns, brs);
          consentData.addDiagnosticTestResultData(data);
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

  private void applyDiagnosticFilter() {
    // This ignores any filters in _diagnosticDto that constrain on particular test results.
    // For the purposes of the detail query, if "Show Results" is selected for a test,
    // we want to show all results for that test regardless of what value constraints
    // may be present -- those constraints only apply to selecting which rows are present
    // in the result, not to what test details are displayed.
    
    String[] testShow = _diagnosticDto.getTestShow();
    String[] diagnosticConceptId = _diagnosticDto.getDiagnosticConceptId();
    List concepts = new ArrayList();
    if (!ApiFunctions.isEmpty(testShow)) {
      for (int i=0; i<testShow.length; i++) {
        if (("true".equals(testShow[i]))) {
          concepts.add(diagnosticConceptId[i]);  
        }
      }
    }
    
    if (!concepts.isEmpty()) {
      _queryBuilder.addFilterOr("diagnostictest", FilterMetaData.KEY_DIAGNOSTIC_TEST, (String[]) concepts.toArray(new String[0]));
    }
  }

  /**
   * Specifies that the consent id column be returned in this detail query.
   */
  public void addColumnConsentId() {
    addColumnInPath(ColumnMetaData.KEY_PATH_CONSENT_ID);
  }

  /**
   * Add a column from the pathology report table to this detail query.
   */
  private void addColumnInPath(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_PATH);
  }

  /**
   * Specifies that the concept id column be returned in this detail query.
   */
  public void addColumnDiagnosticConceptId() {
    addColumnInDiagnostic(ColumnMetaData.KEY_DIAGNOSTICS_CONCEPT_ID);
  }

  /**
   * Specifies that the result cid column be returned in this detail query.
   */
  public void addColumnDiagnosticResultCid() {
    addColumnInDiagnostic(ColumnMetaData.KEY_DIAGNOSTIC_RESULTS_CID);
  }

  /**
   * Add a column from path report diagnostic table to this detail query.
   */
  private void addColumnInDiagnostic(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_PATH_REPORT_DIAGNOSTICS);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_PATH_DIAGNOSTICS);
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
