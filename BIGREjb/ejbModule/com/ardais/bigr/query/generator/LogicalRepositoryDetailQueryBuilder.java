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
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.javabeans.RnaData;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.LogicalRepositoryUtils;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * The LogicalRepositoryDetailQueryBuilder adds implicit filters to the query that cause
 * it to only return information on logical repositories that the current user is permitted
 * to see.  So, for example, if you use this to get the list of logical repositories that
 * a sample is in, it will include only the ones that the current user can see, which may not
 * be all of the repositories that the sample is actually in.  There are places that currently
 * depend on LogicalRepositoryDetailQueryBuilder behaving this way.  For example:
 * <ul>
 * <li>When we display sample details in sample selection and related places (hold lists,
 *     order details, request samples, ...), the Inventory Group column only should only
 *     show inventory groups that the user has access to, even if the sample is in other
 *     groups as well.</li>
 * </ul>
 */
public class LogicalRepositoryDetailQueryBuilder {

  private SecurityInfo _securityInfo;

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  public LogicalRepositoryDetailQueryBuilder(SecurityInfo securityInfo) {
    super();
    _securityInfo = securityInfo;

    addMandatoryColumns();

    // Restrict the list of logical repositories to only those to which the user has been
    // given access, if they do not have the "View All Logical Repositories" privilege
    if (!_securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      String[] ids;
      Set logicalRepositories = _securityInfo.getLogicalRepositories();
      if (logicalRepositories == null || logicalRepositories.isEmpty()) {
        // The user doesn't have access to any inventory groups.  Inserting a criteria
        // that matches on a a repository id of "" forces the query to not return data
        // for any repositories, which is what we want in this case.
        ids = new String[] { "" };
      }
      else {
        ids = new String[logicalRepositories.size()];
        Iterator iterator = logicalRepositories.iterator();
        int count = 0;
        while (iterator.hasNext()) {
          ids[count] = ((LogicalRepository) iterator.next()).getId();
          count = count + 1;
        }
      }
      _queryBuilder.addFilter(FilterMetaData.KEY_LOGICAL_REPOS_ITEM_REPOSITORY_ID, ids);
    }
  }

  /**
  * Add a column from the logical repository table to this detail query.
  */
  private void addColumnInLogicalRepos(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_LOGICAL_REPOS_ITEM_LOGICAL_REPOS);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_LOGICAL_REPOS);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_LOGICAL_REPOS_ITEM);
  }

  /**
  * Add a column from the logical repository product table to this detail query.
  */
  private void addColumnInLogicalReposItem(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_LOGICAL_REPOS_ITEM);
  }

  /**
   * Add any columns that are mandatory for proper system function.  These columns must be
   * included in the result details, for example, even if the information in them isn't being
   * shown to the user.  This is public so you can call it explicitly, but this class's
   * constructor also calls this.
   */
  public void addMandatoryColumns() {
    // We always need to get the repository id and BMS Y/N indicator.  These are essential for
    // basic functions such as determining the equality of two LogicalRepository objects and
    // doing basic inventory item autorization and categorization checks.  For example
    // the isAccessibleToUser and isBmsFromUsersPerspective methods in
    // ProductDto/SampleData/RnaData expect these fields to be defined.
    // 
    addColumnRepositoryId();
    addColumnRepositoryBmsYn();
  }

  /**
   * Specifies that the repository id column be returned in this 
   * detail query.
   */
  public void addColumnRepositoryId() {
    addColumnInLogicalRepos(ColumnMetaData.KEY_LOGICAL_REPOS_ID);
  }

  /**
   * Specifies that the indicator of whether this is a BMS repository be returned in this 
   * detail query.
   */
  public void addColumnRepositoryBmsYn() {
    addColumnInLogicalRepos(ColumnMetaData.KEY_LOGICAL_REPOS_BMS_YN);
  }

  /**
   * Specifies that the repository full-name column be returned in this 
   * detail query.
   */
  public void addColumnRepositoryFullName() {
    addColumnInLogicalRepos(ColumnMetaData.KEY_LOGICAL_REPOS_FULL_NAME);
  }

  /**
   * Specifies that the repository short-name column be returned in this 
   * detail query.
   */
  public void addColumnRepositoryShortName() {
    addColumnInLogicalRepos(ColumnMetaData.KEY_LOGICAL_REPOS_SHORT_NAME);
  }

  /**
   * Specifies that the item id column be returned in this 
   * detail query.
   */
  public void addColumnItemId() {
    addColumnInLogicalReposItem(ColumnMetaData.KEY_LOGICAL_REPOS_ITEM_ITEM_ID);
  }

  /**
   * Specifies that the item type column be returned in this 
   * detail query.
   */
  public void addColumnItemType() {
    addColumnInLogicalReposItem(ColumnMetaData.KEY_LOGICAL_REPOS_ITEM_ITEM_TYPE);
  }

  /**
   * Populates the logical repository information on the sample data beans in the specified map
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.SampleData}
   *          beans, keyed by their sample id.
   */
  public void getDetails(Map sampleDataBeans) {
    addColumnItemId();

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
        _queryBuilder.addFilter(FilterMetaData.KEY_LOGICAL_REPOS_ITEM_ITEM_ID, chunk);
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
          SampleData sample =
            (SampleData) sampleDataBeans.get(brs.getString(DbAliases.LOGICAL_REPOS_ITEM_ITEM_ID));
          List logicalRepositories = sample.getLogicalRepositories();
          if (logicalRepositories == null) {
            logicalRepositories = new ArrayList();
            sample.setLogicalRepositories(logicalRepositories);
          }

          LogicalRepository logicalRepository = new LogicalRepository(columns, brs);
          logicalRepositories.add(logicalRepository);
        }

        //now that the SampleData beans have their logical repositories, sort each logical
        //repository set so that they are shown in alpabetical order
        Iterator iterator = sampleDataBeans.values().iterator();
        Comparator comparator = LogicalRepositoryUtils.SHORT_NAME_ORDER;
        while (iterator.hasNext()) {
          List logicalRepositories = ((SampleData) iterator.next()).getLogicalRepositories();
          if (logicalRepositories != null && logicalRepositories.size() > 1) {
            Collections.sort(logicalRepositories, comparator);
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
   * Populates the logical repository information on the rna data beans in the specified map
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.RnaData}
   *          beans, keyed by their rna vial id.
   */
  public void getDetailsForRna(Map rnaDataBeans) {
    addColumnItemId();

    Connection con = null;
    BigrPreparedStatement pstmt = null;
    BigrResultSet brs = new BigrResultSet(new StringCanonicalizer());
    Map columns = null;

    try {
      con = ApiFunctions.getDbConnection();
      int idBatchSize = getIdBatchSize();

      List idChunks =
        ApiFunctions.chunkStrings((String[]) rnaDataBeans.keySet().toArray(new String[] {
      }), idBatchSize);
      Iterator chunks = idChunks.iterator();
      boolean first = true;
      while (chunks.hasNext()) {
        String[] chunk = (String[]) chunks.next();
        _queryBuilder.addFilter(FilterMetaData.KEY_LOGICAL_REPOS_ITEM_ITEM_ID, chunk);
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
          RnaData rna =
            (RnaData) rnaDataBeans.get(brs.getString(DbAliases.LOGICAL_REPOS_ITEM_ITEM_ID));
          List logicalRepositories = rna.getLogicalRepositories();
          if (logicalRepositories == null) {
            logicalRepositories = new ArrayList();
            rna.setLogicalRepositories(logicalRepositories);
          }

          LogicalRepository logicalRepository = new LogicalRepository(columns, brs);
          logicalRepositories.add(logicalRepository);
        }

        //now that the SampleData beans have their logical repositories, sort each logical
        //repository set so that they are shown in alpabetical order
        Iterator iterator = rnaDataBeans.values().iterator();
        Comparator comparator = LogicalRepositoryUtils.SHORT_NAME_ORDER;
        while (iterator.hasNext()) {
          List logicalRepositories = ((RnaData) iterator.next()).getLogicalRepositories();
          if (logicalRepositories != null && logicalRepositories.size() > 1) {
            Collections.sort(logicalRepositories, comparator);
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
