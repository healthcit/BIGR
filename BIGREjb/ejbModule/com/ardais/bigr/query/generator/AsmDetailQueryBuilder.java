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
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;

/**
 */
public class AsmDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * Indicates whether ConsentData beans associated with the ASMs should be 
   * created when the query is run.
   */
  private boolean _createConsentData = false;

  /**
   * A map of ConsentData beans that is created when the query is run.
   */
  private Map _consentData;

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>AsmDetailQueryBuilder</code>.
   */
  public AsmDetailQueryBuilder() {
    super();
  }

  /**
   * Specifies that the consent id column from ASM be returned in this 
   * detail query.
   */
  public void addColumnAsmConsentId() {
    addColumnInAsm(ColumnMetaData.KEY_ASM_CONSENT_ID);
  }

  /**
   * Specifies that the ASM id column be returned in this detail query.
   */
  public void addColumnAsmId() {
    addColumnInAsm(ColumnMetaData.KEY_ASM_ID);
  }

  /**
   * Specifies that the ASM tissue column be returned in this detail query.
   */
  public void addColumnAsmTissue() {
    addColumnInAsm(ColumnMetaData.KEY_ASM_TISSUE);
  }

  /**
   * Specifies that the ASM module comment column be returned in this 
   * detail query.
   */
  public void addColumnAsmModuleComment() {
    addColumnInAsm(ColumnMetaData.KEY_ASM_MODULE_COMMENTS);
  }

  /**
   * Specifies that the column for free text describing other tissue (if exists)
   * be returned in this detail query.
   */
  public void addColumnAsmTissueOther() {
    addColumnInAsm(ColumnMetaData.KEY_ASM_TISSUE_OTHER);
  }

  /**
   * Specifies that the gross appearance column be returned in this 
   * detail query.
   */
  public void addColumnGrossAppearance() {
    addColumnInAsm(ColumnMetaData.KEY_ASM_APPEARANCE);
  }

  /**
   * Specifies that the ASM procedure column be returned in this detail query.
   */
  public void addColumnAsmProcedure() {
    addColumnInAsmForm(ColumnMetaData.KEY_ASM_FORM_PROCEDURE);
  }

  /**
   * Specifies that the ASM procedure other column be returned in this detail query.
   */
  public void addColumnAsmProcedureOther() {
    addColumnInAsmForm(ColumnMetaData.KEY_ASM_FORM_PROCEDURE_OTHER);
  }

  /**
   * Specifies that the ASM prepared by column be returned in this detail query.
   */
  public void addColumnAsmPreparedBy() {
    addColumnInAsmForm(ColumnMetaData.KEY_ASM_FORM_PREPARED_BY);
  }

  /**
   * Add a column from the ASM table to this detail query.
   */
  private void addColumnInAsm(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_ASM);
  }

  /**
   * Add a column from the ASM_FORM table to this detail query.
   */
  private void addColumnInAsmForm(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_ASM);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_ASM_FORM);
    _queryBuilder.addJoin(FilterMetaData.KEY_JOIN_ASM_ASM_FORM);
  }

  /**
   * Sets whether {@link com.ardais.bigr.javabeans.ConsentData} beans 
   * associated with the ASMs should be created when the query is run.
   * 
   * @param  create  <code>true</code> if associated ConsentData beans should
   * 									be created and added to each ASM data bean
   */
  public void setCreateConsentData(boolean create) {
    _createConsentData = create;
    if (create) {
      addColumnAsmConsentId();
    }
  }

  /**
   * Returns whether {@link com.ardais.bigr.javabeans.ConsentData} beans 
   * associated with the ASMs should be created when the query is run.
   * 
   * @param  <code>true</code> if associated ConsentData beans should
   * 					be created and added to each ASM data bean
   */
  public boolean isCreateConsentData() {
    return _createConsentData;
  }

  /**
   * Populates the ASM data beans in the specified map, with the columns
   * specified by the addColumn methods in this class.  In addition, creates
   * and the ASM's associated consent data bean and adds it to the ASM data
   * bean if addColumnAsmConsentId() was called.
   * 
   * @param  A <code>Map</code> of {@link com.ardais.bigr.javabeans.AsmData}
   * 				  beans, keyed by their ASM id.
   */
  public void getDetails(Map asmData) {

    addColumnAsmId();

    if (isCreateConsentData()) {
      _consentData = new HashMap();
    }

    Connection con = null;
    BigrPreparedStatement pstmt = null;
    BigrResultSet brs = new BigrResultSet(new StringCanonicalizer());
    Map columns = null;

    try {
      con = ApiFunctions.getDbConnection();

      int idBatchSize = getIdBatchSize();

      List idChunks =
        ApiFunctions.chunkStrings((String[]) asmData.keySet().toArray(new String[0]), idBatchSize);
      Iterator chunks = idChunks.iterator();
      boolean first = true;
      while (chunks.hasNext()) {
        String[] chunk = (String[]) chunks.next();
        _queryBuilder.addFilter(FilterMetaData.KEY_ASM_ID, chunk);
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
          String asmId = brs.getString(DbAliases.ASM_ID);
          AsmData asm = (AsmData) asmData.get(asmId);
          asm.populate(columns, brs);

          if (isCreateConsentData()) {
            String consentId = brs.getString(DbAliases.ASM_CONSENT_ID);
            ConsentData consent = (ConsentData) _consentData.get(consentId);
            if (consent == null) {
              consent = new ConsentData(columns, brs);
              _consentData.put(consentId, consent);
            }
            asm.setConsentData(consent);
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
   * Returns a map of all of the consents for all of the ASMs that were
   * returned in the previous call to getDetails().  If 
   * setCreateConsentData(true) was not called before calling getDetails, 
   * this will return <code>null</code>.
   * 
   * @return  A <code>Map</code> of {@link com.ardais.bigr.javabeans.ConsentData}
   * 				  beans, keyed by their consent id.  
   */
  public Map getConsentData() {
    return _consentData;
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
