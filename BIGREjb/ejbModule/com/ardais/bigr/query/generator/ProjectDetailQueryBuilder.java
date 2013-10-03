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
import com.ardais.bigr.iltds.assistants.ProjectDataBean;
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
public class ProjectDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * A map of ProjectDataBean beans that is created when the query is run.
   */
  private Map _projectData;

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>ProjectDetailQueryBuilder</code>.
   */
  public ProjectDetailQueryBuilder() {
    super();
  }

  /**
   * Specifies that the project id column be returned in this 
   * detail query.
   */
  public void addColumnProjectId() {
    addColumnInProject(ColumnMetaData.KEY_PROJECT_ID);
  }

  /**
   * Specifies that the project name column be returned in this 
   * detail query.
   */
  public void addColumnProjectName() {
    addColumnInProject(ColumnMetaData.KEY_PROJECT_NAME);
  }

  /**
   * Specifies that the project date requested be returned in this 
   * detail query.
   */
  public void addColumnProjectDateRequested() {
    addColumnInProject(ColumnMetaData.KEY_PROJECT_DATE_REQUESTED);
  }

  /**
   * Specifies that the sample id column be returned in this 
   * detail query.
   */
  public void addColumnSampleId() {
    addColumnInProjectSample(ColumnMetaData.KEY_PTS_SAMPLE_ID);
  }

  /**
   * Add a column from the PTS Project table to this detail query.
   */
  private void addColumnInProject(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_PTS_PROJECT);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_PTS_SAMPLE);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_PTS_SAMPLE_PROJECT);
  }

  /**
   * Add a column from the PTS Project sample table to this detail query.
   */
  private void addColumnInProjectSample(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_PTS_SAMPLE);
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

    addColumnProjectId();
    addColumnSampleId();

    _projectData = new HashMap();

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
        _queryBuilder.addFilter(FilterMetaData.KEY_PTS_SAMPLE_ID, chunk);
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
          String projectId = brs.getString(DbAliases.PROJECT_ID);
          ProjectDataBean project = (ProjectDataBean) _projectData.get(projectId);
          if (project == null) {
            project = new ProjectDataBean(columns, brs);
            _projectData.put(projectId, project);
          }

          String sampleId = brs.getString(DbAliases.PTS_SAMPLE_ID);
          SampleData sampleData = (SampleData) sampleDataBeans.get(sampleId);
          sampleData.setProjectData(project);
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
   * Returns a map of all of the projects for all of the samples that were
   * specified in the previous call to getDetails().
   * 
   * @return  A <code>Map</code> of {@link com.ardais.bigr.iltds.assistants.ProjectDataBean}
   * 				  beans, keyed by their project id.  
   */
  public Map getProjectData() {
    return _projectData;
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
