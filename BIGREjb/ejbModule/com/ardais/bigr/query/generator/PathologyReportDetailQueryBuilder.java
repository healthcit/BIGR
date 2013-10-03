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
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;

public class PathologyReportDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>PathologyReportDetailQueryBuilder</code>.
   */
  public PathologyReportDetailQueryBuilder() {
    super();
  }

  /**
   * Specifies that the consent id column be returned in this 
   * detail query.
   */
  public void addColumnConsentId() {
    addColumnInPath(ColumnMetaData.KEY_PATH_CONSENT_ID);
  }

  /**
   * Specifies that the DDC diagnosis column be returned in this 
   * detail query.
   */
  public void addColumnDdcDiagnosis() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_DX);
  }

  /**
   * Specifies that the other DDC diagnosis column be returned in this 
   * detail query.
   */
  public void addColumnDdcDiagnosisOther() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_DX_OTHER);
  }

  /**
   * Specifies that the DDC finding tissue column be returned in this 
   * detail query.
   */
  public void addColumnDdcTissueFinding() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_TISSUE_FINDING);
  }

  /**
   * Specifies that the other DDC finding tissue column be returned in this 
   * detail query.
   */
  public void addColumnDdcTissueFindingOther() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_TISSUE_FINDING_OTHER);
  }

  /**
   * Specifies that the DDC origin tissue column be returned in this 
   * detail query.
   */
  public void addColumnDdcTissueOrigin() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_TISSUE_ORIGIN);
  }

  /**
   * Specifies that the other DDC origin tissue column be returned in this 
   * detail query.
   */
  public void addColumnDdcTissueOriginOther() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_TISSUE_ORIGIN_OTHER);
  }

  /**
   * Specifies that the distant metastasis column be returned in this 
   * detail query.
   */
  public void addColumnDistantMetastasis() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_METASTASIS);
  }

  /**
   * Specifies that the gleason score columns be returned in this 
   * detail query.  This reuturn the primary, secondary and total gleason score
   * columns.
   */
  public void addColumnGleasonScore() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_GLEASON_PRIMARY);
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_GLEASON_SECONDARY);
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_GLEASON_TOTAL);
  }

  /**
   * Specifies that the histologic/nuclear grade column be returned in this 
   * detail query.
   */
  public void addColumnHistologicNuclearGrade() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_HNG);
  }

  /**
   * Specifies that the lymph node stage column be returned in this 
   * detail query.
   */
  public void addColumnLymphNodeStage() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_LYMPH_STAGE);
  }

  /**
   * Specifies that the pathology report id column be returned in this 
   * detail query.
   */
  public void addColumnPathReportId() {
    addColumnInPath(ColumnMetaData.KEY_PATH_ID);
  }

  /**
   * Specifies that the path report section id column be returned in this 
   * detail query.
   */
  public void addColumnSectionId() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_ID);
  }

  /**
   * Specifies that the tumor size column be returned in this 
   * detail query.
   */
  public void addColumnTumorSize() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_TUMOR_SIZE);
  }

  /**
   * Specifies that the stage grouping column be returned in this 
   * detail query.
   */
  public void addColumnStageGrouping() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_STAGE_GROUPING);
  }

  /**
   * Specifies that the tumor stage column be returned in this 
   * detail query.
   */
  public void addColumnTumorStage() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_TUMOR_STAGE);
  }

  /**
   * Specifies that the tumor stage type column be returned in this 
   * detail query.
   */
  public void addColumnTumorStageType() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_TUMOR_STAGE_TYPE);
  }

  /**
   * Specifies that the tumor weight column be returned in this 
   * detail query.
   */
  public void addColumnTumorWeight() {
    addColumnInPrimarySection(ColumnMetaData.KEY_SECTION_TUMOR_WEIGHT);
  }

  /**
   * Add a column from the pathology report table to this detail query.
   */
  private void addColumnInPath(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_PATH);
  }

  /**
   * Add a column from the pathology report section table to this detail query.
   */
  private void addColumnInPrimarySection(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addJoin(FilterMetaData.KEY_OUTER_JOIN_PATH_SECTION);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_PATH);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_SECTION);
  }

  /**
   * Creates and populates {@link com.ardais.bigr.pdc.javabeans.PathReportData}
   * and {@link com.ardais.bigr.pdc.javabeans.PathReportSectionData}
   * data beans for the consents in the specified map, with the columns 
   * specified by the addColumn methods in this class.  The PathReportData
   * data beans are set onto their associated consents, and the 
   * PathReportSectionData data beans are set onto their associated path
   * reports.
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.ConsentData}
   * 				  beans, keyed by their consent id.
   */
  public void getDetailsForConsents(Map consentDataBeans) {

    addColumnConsentId();

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
          PathReportData path = new PathReportData();
          path.populate(columns, brs);
          consentData.setPathReportData(path);
          PathReportSectionData section = new PathReportSectionData();
          section.populate(columns, brs);
          path.setPrimarySectionData(section);
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
