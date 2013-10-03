package com.ardais.bigr.query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummary;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.btx.BTXDetailsGetSummarySql;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.security.SecurityInfo;
/**
 * Represents the results of a single tissue sample query.
 */
public class SampleQuery extends ProductQuery {

  private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

  //    public SampleQuery(ProductFilters filters) {
  //        this(0, securityInfo, filters);
  //    }

  public SampleQuery(int chunkSize, SecurityInfo securityInfo) {
    super(chunkSize, securityInfo);
  }

  /**
   * Returns partial query information for all samples matching the filters.
   * 
   * @return  The <code>SampleSelectionSummary</code> containing the 
   * 						partial query information.
   */
  protected BTXDetails doGetSummary(BTXDetails details) throws Exception {

    details.setTransactionType("library_get_sample_summary");
    BTXDetails newBtx =
      (BTXDetailsGetSampleSummary) Btx.perform(details);
    return newBtx;
  }

  /**
   * Returns the SQL statement for the partial query for all samples matching
   * the filters.
   * 
   * @return  The SQL statement.
   */
  protected BTXDetails doGetSummarySql(BTXDetails btx) throws Exception {
    //        BTXDetailsGetSummarySql btx = new BTXDetailsGetSummarySql();
    //        btx.setFilters(getFilters());
    //        btx.setLoggedInUserSecurityInfo(request);
    btx.setTransactionType("library_get_sample_summary_sql");
    btx = (BTXDetailsGetSummarySql) Btx.perform(btx);
    //        String sql = btx.getSqlResult();
    return btx;
  }

  /**
   * Returns the list of details for all samples matching the filters.
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
    return true;
  }
  /**
   * @see com.ardais.bigr.query.SampleResults#getDisplay()
   */
  public String getDisplay() {
    return "Samples";
  }

  /**
   * @see com.ardais.bigr.query.SampleResults#getProductType()
   */
  public String getProductType() {
    return ResultsHelper.PRODUCT_TYPE_SAMPLE;
  }

  public BTXDetailsGetSamples getSampleData(BTXDetailsGetSamples btx) {
    try {
      // Get the sample types for all the samples in the query (not just the page view).
      btx.setSampleTypes(getSummary().getSampleTypes());

      return retrieveDetailsChunk(btx);
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return null;
    }
  }
}
