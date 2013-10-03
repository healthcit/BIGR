package com.ardais.bigr.query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.btx.BTXDetailsGetSummarySql;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Represents the results of a single RNA query.
 */
public class RnaQuery extends ProductQuery {

  private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

  public RnaQuery(SecurityInfo securityInfo) {
    this(0, securityInfo);
  }

  public RnaQuery(int chunkSize, SecurityInfo securityInfo) {
    super(chunkSize, securityInfo);
  }

  /**
   * Returns partial query information for all RNA matching the filters.
   * 
   * @return  The <code>SampleSelectionSummary</code> containing the 
   * 						partial query information.
   */
  protected BTXDetails doGetSummary(BTXDetails details) throws Exception {
    long tStart = 0;
    if (_perfLog.isDebugEnabled()) {
      _perfLog.debug("  4-6: START RNA summary query");
      tStart = System.currentTimeMillis();
    }

    details.setTransactionType("library_get_rna_summary");
    BTXDetails newDetails = Btx.perform(details);

    if (_perfLog.isDebugEnabled()) {
      long elapsedTime = System.currentTimeMillis() - tStart;
      _perfLog.debug("  4-6: END   RNA summary query (" + elapsedTime + " ms)");
    }

    return newDetails;
  }

  /**
   * Returns the SQL statement for the partial query for all RNA matching
   * the filters.
   * 
   * @return  The SQL statement.
   */
  protected BTXDetails doGetSummarySql(BTXDetails btx) throws Exception {
    btx.setTransactionType("library_get_rna_summary_sql");
    btx = (BTXDetailsGetSummarySql) Btx.perform(btx);
    return btx;
  }

  /**
   * Returns the list of details for all RNA matching the filters.
   * 
   * @return  The list of {@link com.ardais.bigr.javabeans.ProductDto}
   * 						data beans containing the full query results.
   */
  protected BTXDetailsGetSamples doGetDetails(BTXDetailsGetSamples btx) throws Exception {
    btx.setTransactionType("library_get_details");
    btx = (BTXDetailsGetSamples) Btx.perform(btx);
    return btx;
  }

  public boolean isCanBePlacedOnHold() {
    return false;
  }

  /**
   * The results of this query will be displayed as "RNA"
   */
  public String getDisplay() {
    return "RNA";
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getProductType()
   */
  public String getProductType() {
    return ResultsHelper.PRODUCT_TYPE_RNA;
  }

  public BTXDetailsGetSamples getSampleData(BTXDetailsGetSamples btx) {
    try {
      return retrieveDetailsChunk(btx);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return null;
    }
  }
}
