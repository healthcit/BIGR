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
import com.ardais.bigr.javabeans.RnaData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;

/**
 */
public class RnaProjectDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>RnaProjectDetailQueryBuilder</code>.
   */
  public RnaProjectDetailQueryBuilder() {
    super();
  }

  /**
   * Specifies that the project name column be returned in this 
   * detail query.
   */
  public void addColumnProjectName() {
    addColumnInRnaProject(ColumnMetaData.KEY_RNA_PROJECT);
  }

  /**
   * Specifies that the project name column be returned in this 
   * detail query.
   */
  public void addColumnParentVial() {
    addColumnInRnaList(ColumnMetaData.KEY_RNA_LIST_VIALTOUSE);
  }

  /**
   * Specifies that the project name column be returned in this 
   * detail query.
   */
  public void addColumnChildVial() {
    addColumnInRnaList(ColumnMetaData.KEY_RNA_LIST_VIALTOSEND);
  }

  /**
   * Add a column from the Rna Project table to this detail query.
   */
  private void addColumnInRnaProject(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_PROJECT);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_LIST);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_RNA_PROJECT_LIST);
  }

  /**
   * Add a column from the Rna list table to this detail query.
   */
  private void addColumnInRnaList(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_RNA_LIST);
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
  public void getDetailsForRna(Map rnaDataBeans) {

    addColumnParentVial();
    addColumnChildVial();

    Connection con = null;
    BigrPreparedStatement pstmt = null;
    BigrResultSet brs = new BigrResultSet(new StringCanonicalizer());
    Map columns = null;

    try {
      con = ApiFunctions.getDbConnection();

      int idBatchSize = getIdBatchSize();

      List idChunks =
        ApiFunctions.chunkStrings(
          (String[]) rnaDataBeans.keySet().toArray(new String[0]),
          idBatchSize);
      Iterator chunks = idChunks.iterator();
      boolean first = true;
      while (chunks.hasNext()) {
        String[] chunk = (String[]) chunks.next();
        _queryBuilder.addFilter(FilterMetaData.KEY_RNA_LIST_VIALTOUSE, chunk);
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
          String parentId = brs.getString(DbAliases.RNA_LIST_VIALTOUSE);
          String childId = brs.getString(DbAliases.RNA_LIST_VIALTOSEND);
          String projectName = brs.getString(DbAliases.RNA_PROJECT);
          RnaData rna = (RnaData) rnaDataBeans.get(parentId);

          // On hold
          if (childId == null) {
            String rnaHoldProjects = rna.getRnaHoldProject();
            if (rnaHoldProjects == null) {
              rnaHoldProjects = projectName;
            }
            else {
              rnaHoldProjects = rnaHoldProjects.concat(", " + projectName);
            }
            rna.setRnaHoldProject(rnaHoldProjects);
          }

          // Sold
          else {
            String rnaProjects = rna.getRnaProject();
            if (rnaProjects == null) {
              rnaProjects = projectName;
            }
            else {
              rnaProjects = rnaProjects.concat(", " + projectName);
            }
            rna.setRnaProject(rnaProjects);
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
