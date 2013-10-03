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
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.DonorData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.BigrPreparedStatement;
import com.ardais.bigr.util.BigrResultSet;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;

/**
 */
public class ConsentDetailQueryBuilder {

  /**
   * The query builder used to build this query.
   */
  private ProductQueryBuilder _queryBuilder = new ProductQueryBuilder();

  /**
   * A map of DonorData beans that is created when the query is run.
   */
  private Map _donorData;

  /**
   * Indicates whether DonorData beans associated with the samples should be 
   * created when the query is run.
   */
  private boolean _createDonorData = false;

  /**
   * Logger for logging info about database queries (e.g. a query's SQL).
   */
  private static Log _queryLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_QUERY);

  /**
   * Creates a new <code>ConsentDetailQueryBuilder</code>.
   */
  public ConsentDetailQueryBuilder() {
    super();
  }

  /**
   * Specifies that the consent id column be returned in this 
   * detail query.
   */
  public void addColumnConsentId() {
    addColumnInConsent(ColumnMetaData.KEY_CONSENT_ID);
  }

  /**
   * Specifies that the consent id column be returned in this 
   * detail query.
   */
  public void addColumnConsentLocation() {
    addColumnInConsent(ColumnMetaData.KEY_CONSENT_LOCATION);
  }

  /**
   * Specifies that the case's customer_id column be returned in this 
   * detail query.
   */
  public void addColumnCustomerId() {
    addColumnInConsent(ColumnMetaData.KEY_CONSENT_CUSTOMER_ID);
  }

  /**
   * Specifies that the donor id column be returned in this 
   * detail query.
   */
  public void addColumnDonorId() {
    addColumnInConsent(ColumnMetaData.KEY_DONOR_ID);
  }

  /**
   * Specifies that the case's customer_id column be returned in this 
   * detail query.
   */
  public void addColumnDonorCustomerId() {
    addColumnInDonor(ColumnMetaData.KEY_DONOR_CUSTOMER_ID);
  }

  /**
   * Specifies that the gender column be returned in this 
   * detail query.
   */
  public void addColumnGender() {
    addColumnInDonor(ColumnMetaData.KEY_DONOR_GENDER);
  }

  
  /**
   * Specifies that the race column be returned in this 
   * detail query.
   */
  public void addColumnRace() {
    addColumnInDonor(ColumnMetaData.KEY_DONOR_RACE);
  }
  
  public void addColumnDonorComments() {
    addColumnInDonor(ColumnMetaData.KEY_DONOR_PROFILE_NOTES);
  }

  /**
   * Specifies that the ILTDS diagnosis column be returned in this 
   * detail query.
   */
  public void addColumnIltdsDiagnosis() {
    addColumnInConsent(ColumnMetaData.KEY_CONSENT_DX);
  }

  /**
   * Specifies that the other ILTDS diagnosis column be returned in this 
   * detail query.
   */
  public void addColumnIltdsDiagnosisOther() {
    addColumnInConsent(ColumnMetaData.KEY_CONSENT_DX_OTHER);
  }

  /**
   * Specifies that the PSA column be returned in this detail query.
   */
  public void addColumnPsa() {
    addColumnInConsent(ColumnMetaData.KEY_CONSENT_PSA);
  }

  /**
   * Specifies that the DRE column be returned in this detail query.
   */
  public void addColumnDre() {
    addColumnInConsent(ColumnMetaData.KEY_CONSENT_DRE_CID);
  }

  /**
   * Add a column from the consent table to this detail query.
   */
  private void addColumnInConsent(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_CONSENT);
  }

  /**
   * Add a column from the donor table to this detail query.
   */
  private void addColumnInDonor(String key) {
    _queryBuilder.addColumn(key);
    _queryBuilder.addJoin(FilterMetaData.KEY_OUTER_JOIN_CONSENT_DONOR);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_DONOR);
    _queryBuilder.addTable(TableMetaData.KEY_TABLE_CONSENT);
  }

  /**
   * Populates the consent data beans in the specified map, with the columns
   * specified by the addColumn methods in this class.
   * 
   * @param  a <code>Map</code> of {@link com.ardais.bigr.javabeans.ConsentData}
   * 				  beans, keyed by their consent id.
   */
  public void getDetails(Map consentDataBeans) {

    addColumnConsentId();

    if (isCreateDonorData()) {
      _donorData = new HashMap();
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
          (String[]) consentDataBeans.keySet().toArray(new String[0]),
          idBatchSize);
      Iterator chunks = idChunks.iterator();
      boolean first = true;
      while (chunks.hasNext()) {
        String[] chunk = (String[]) chunks.next();
        _queryBuilder.addFilter(FilterMetaData.KEY_CONSENT_ID, chunk);
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
          String consentId = brs.getString(DbAliases.CONSENT_ID);
          ConsentData consentData = (ConsentData) consentDataBeans.get(consentId);
          consentData.populate(columns, brs);

          if (isCreateDonorData()) {
            String donorId = brs.getString(DbAliases.DONOR_ID);
            DonorData donor = (DonorData) _donorData.get(donorId);
            if (donor == null) {
              donor = new DonorData(columns, brs);
              _donorData.put(donorId, donor);
            }
            consentData.setDonorData(donor);
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
   * Returns the donorData.
   * @return Map
   */
  public Map getDonorData() {
    return _donorData;
  }

  /**
   * Returns whether {@link com.ardais.bigr.javabeans.DonorData} beans associated 
   * with the samples should be created when the query is run.
   * 
   * @return  <code>true</code> if associated DonorData beans should
   *                   be created and added to each sample data bean
   */
  public boolean isCreateDonorData() {
    return _createDonorData;
  }

  /**
   * Sets whether {@link com.ardais.bigr.javabeans.DonorData} beans associated 
   * with the samples should be created when the query is run.
   * 
   * @param  create  <code>true</code> if associated DonorData beans should
   *                                  be created and added to each sample data bean
   */
  public void setCreateDonorData(boolean create) {
    _createDonorData = create;
    if (create) {
      addColumnConsentDonorId();
    }
  }

  /**
   * Specifies that the ASM id column from sample be returned 
   * in this detail query.
   */
  public void addColumnConsentDonorId() {
    addColumnInConsent(ColumnMetaData.KEY_DONOR_ID);
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
