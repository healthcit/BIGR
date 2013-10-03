package com.ardais.bigr.library.web.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.DerivationBatchDto;
import com.ardais.bigr.javabeans.DiagnosticTestFilterDto;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.library.DerivationBatchDetailsSampleResults;
import com.ardais.bigr.library.HoldListSampleSet;
import com.ardais.bigr.library.IcpSampleSet;
import com.ardais.bigr.library.OrderDetailSampleResults;
import com.ardais.bigr.library.PickListSampleSet;
import com.ardais.bigr.library.RequestDetailsSampleResults;
import com.ardais.bigr.library.SampleSet;
import com.ardais.bigr.library.SampleViewData;
import com.ardais.bigr.library.SimpleSampleSet;
import com.ardais.bigr.library.btx.BTXDetailsForSampleSummary;
import com.ardais.bigr.library.btx.BTXDetailsGetSampleSummary;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.column.SampleColumn;
import com.ardais.bigr.library.web.form.QueryKcForm;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ProductQuery;
import com.ardais.bigr.query.RnaQuery;
import com.ardais.bigr.query.SampleQuery;
import com.ardais.bigr.query.SampleResults;
import com.ardais.bigr.query.SampleSelectionSummary;
import com.ardais.bigr.query.SampleSetResultsWrapper;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterSet;
import com.ardais.bigr.query.filters.RnaFilters;
import com.ardais.bigr.query.filters.SampleFilters;
import com.ardais.bigr.query.kc.filters.FilterKc;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.userprofile.QueryProfile;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.IdValidator;
import com.ardais.bigr.util.WebUtils;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;

/**
 * Holds the results of a Sample Selection query.  <code>ResultsHelper</code>
 * primarily holds a map of SampleResults objects, which in turn hold state
 * about the data queried and returned (see {@link SampleResults}).
 * 
 * Together, all the data represents the state or "model" for all queries.
 * It also holds some view information, such as the currentProductType.
 */
public class ResultsHelper {

  /**
   * The key under which an instance of this object is stored in the request.
   * Note that a transaction_type is appended to this key so different types
   * of transactions don't overwrite each other's session data.
   */
  public static final String KEY = "library.ss.resultshelper";

  /**
   * There are four different contexts that sample selection is used in.
   * <p/>
   * The first is true sample selection where the user is viewing query results
   * for samples and RNA to order them or put them on hold (samp sel.)
   * <p/>
   * The second is viewing samples to generate a pick list (request samples)
   * <p/>
   * The third is viewing sample data to review order details. (Order Detail).
   * <p/>
   * The fourth is viewing sample data for the samples in a case for ICP. (ICP).
   * <p/>
   * All four have their own ResultsHelper and their own session data, to avoid
   * having one task step on the global variables of another.  These keys help
   * keep session data separate -- they are used to build keys for session lookup.
   */
  public static final String TX_TYPE_SAMP_SEL = "TxSampSel";
  public static final String TX_TYPE_SAMP_REQUEST = "TxRequestSamples";
  public static final String TX_TYPE_ORDER_DETAIL = "TxOrderDetail";
  public static final String TX_TYPE_ICP = "TxIcp";
  public static final String TX_TYPE_DERIV_OPS = "TxDerivOps";

  public static final String PRODUCT_QUERY_NA = "Not Applicable";

  /**
   * This object is in one of four states - it holds data for all four, but one is current.
   * When the list of IDs, summary information, and so on is shown, it is always shown for 
   * the current state.  
   * 
   * The states are:  viewing tissue query, viewing RNA query, viewing Hold list,
   * viewing order details.
   * 
   * Sample and RNA types are for viewing queries for tissue and rna, respectively.
   * Hold_list type is for viewing the hold list
   * Oder_detail type is for viewing orders (from the OrderDetail bean, not samp sel.)
   * Selections type is for viewing selected, but not transactionally submitted items
   *   in their own window -- currently display as blue rows.  This is not the 
   *   items that have already been transactionally submitted to the hold list.
   */
  public static final String PRODUCT_TYPE_SAMPLE = BTXDetailsForSampleSummary.REQUEST_TYPE_TISSUE;
  public static final String PRODUCT_TYPE_RNA = BTXDetailsForSampleSummary.REQUEST_TYPE_RNA;
  public static final String PRODUCT_TYPE_HOLD_LIST = "HoldListData";
  public static final String PRODUCT_TYPE_ORDER_DETAIL = "OrderDetailData";
  // selections is to show the selected items (across chunks) in their own view
  public static final String PRODUCT_TYPE_RNA_SELECTIONS = "SelectedRnaData";

  private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

  /**
   * The security info of the user.
   */
  private SecurityInfo _securityInfo;

  /**
   * The type of product currently being displayed.  The value of this is
   * one of the PRODUCT_TYPE_* contstants defined in this class.
   */
  private String _currentProductType;

  private String _lastTissueRnaProductType;

  private String _txType;

  // which order to show, if this object is supporting order details
  private String _orderNumberStr;

  // which request to show, if this object is supporting request details (which
  // is basically a modified order details)
  private String _requestNumberStr;

  //the id of the ICP item whose sample we're showing: only used for TX_TYPE_ICP.
  private String _icpItemId;
  
  //a derivation batch dto for which we're showing child samples: only used for TX_TYPE_DERIV_OPS.
  private DerivationBatchDto _derivationBatchDto;

  private Map _productQueries = new HashMap(); // holds a ProductQuery for Samples and one for RNA

  private FilterSet _filters;

  private SampleViewData _sampleViewData;

  private QueryFormDefinition _queryForm; 
  
  /**
   * Get the ResultsHelper for this txType from session state.
   * 
   * After this is called all updates/gets to/from the helper MUST be synchronized, because
   * this object is shared across web request threads.
   * 
   */
  public static synchronized ResultsHelper get(String txType, HttpServletRequest request) {
    // don't allow two threads to create helpers by having one sneak in between the check and create
    HttpSession session = request.getSession(false);
    String key = KEY + txType;
    ResultsHelper helper = (ResultsHelper) session.getAttribute(key);

    if (helper == null) {
      helper = new ResultsHelper(txType, WebUtils.getSecurityInfo(request));
      session.setAttribute(key, helper);
    }

    // Diagnostic test filter is special in that it is sometimes not shown even if its
    // permissions/screens/etc tests pass.  In particular, it won't be show if the test filter
    // DTO says not to show it, which happens when the user doesn't select any tests for display.
    // 
    DiagnosticTestFilterDto dto =
      (DiagnosticTestFilterDto) session.getAttribute(
        txType + Constants.DIAGNOSTIC_TEST_RESULT_FILTER);
    if (dto == null
      || (!dto.isShowResultDiag()
      && !dto.isShowResultClinLab()
      && !dto.isShowResultClinFindings())) {
      if (helper.getHelpers() != null) {
        Iterator iter = helper.getHelpers().getColumns().getColumns().iterator();
        while (iter.hasNext()) {
          SampleColumn column = (SampleColumn) iter.next();
          if (column.getKey().equalsIgnoreCase(ColumnConstants._DIAGNOSTIC_RESULT)) {
            ViewParams vp = null;
            column.setShown(vp);
          }
        }
      }
    }

    return helper;
  }

  /**
   * @param txString the string code representing the txType for a view
   * @return the int bit code corresponding to the string transaction type used in URLs
   * and in internal ResultsHelper state
   */
  public static int getTxBits(String txString) {
    if (TX_TYPE_SAMP_SEL.equals(txString))
      return ColumnPermissions.TX_SAMP_SELECTION;
    else if (TX_TYPE_SAMP_REQUEST.equals(txString))
      return ColumnPermissions.TX_REQUEST_SAMP;
    else if (TX_TYPE_ORDER_DETAIL.equals(txString))
      return ColumnPermissions.TX_ORDER_DETAIL;
    //icp uses the same columns as sample selection
    else if (TX_TYPE_ICP.equals(txString))
      return ColumnPermissions.TX_TYPE_ICP;
    else if (TX_TYPE_DERIV_OPS.equals(txString)) {
      return ColumnPermissions.TX_TYPE_DERIV_OPS;
    }
    else
      throw new ApiException("No view bit code for trans type: " + txString);
  }

  /**
   * @param prodString the string code representing the productType for a view
   * @return the int bit code corresponding to the string product type used in URLs
   * and in internal ResultsHelper state
   */
  public static int getProductBits(String prodString) {
    if (ResultsHelper.PRODUCT_TYPE_SAMPLE.equals(prodString)) {
      return ColumnPermissions.PROD_TISSUE;
    }
    else if (ResultsHelper.PRODUCT_TYPE_RNA.equals(prodString)) {
      return ColumnPermissions.PROD_RNA;
    }
    else {
      return ColumnPermissions.PROD_GENERIC;
    }
  }
  /**
   * Creates a new <code>SampleSelectionResultsHelper</code> for the specified
   * product type and adds it to the request as a request attribute.
   * 
   */
  private ResultsHelper(String txType, SecurityInfo secInfo) {
    _securityInfo = secInfo;
    if (txType == null)
      throw new ApiException("Attempt to get query context data without specified transaction type");
    _txType = txType;
  }

  /**
   * set up the hold list or order list -- if we are viewing orders, all
   * we need is the order list.  If we are doing queries (sample selection
   * or request samples) we need to also have a hold list, as well as the
   * main queries for tissue and rna, which will have been set up by the
   * setting of filters by the caller.
   */
  private void initLists(String productType, SecurityInfo secInfo) {
    SampleResults sampRes;
    if (TX_TYPE_ORDER_DETAIL.equals(_txType)) {
      //if both an order number and a request number are specified,
      //throw an exception (only one or the other should be specified)
      if (!ApiFunctions.isEmpty(_orderNumberStr) && !ApiFunctions.isEmpty(_requestNumberStr)) {
        throw new ApiException("Both an order and request number are specified - only one or the other must be specified.");
      }
      else if (!ApiFunctions.isEmpty(_orderNumberStr)) {
        sampRes = new OrderDetailSampleResults(secInfo, _orderNumberStr);
        _productQueries.put(PRODUCT_TYPE_ORDER_DETAIL, sampRes);
      }
      else if (!ApiFunctions.isEmpty(_requestNumberStr)) {
        sampRes = new RequestDetailsSampleResults(secInfo, _requestNumberStr);
        _productQueries.put(PRODUCT_TYPE_ORDER_DETAIL, sampRes);
      }
      //if neither an order number nor a request number are specified,
      //throw an exception (one or the other must be specified)
      else {
        throw new ApiException("No order or request number is specified - one or the other must be specified.");
      }
    }
    else if (TX_TYPE_SAMP_REQUEST.equals(_txType)) {
      SampleResults existingPickListSet =
        (SampleResults) _productQueries.get(PRODUCT_TYPE_HOLD_LIST);
      if (existingPickListSet == null) {
        sampRes = new SampleSetResultsWrapper(new PickListSampleSet(secInfo), "Tissue Samples");
        _productQueries.put(PRODUCT_TYPE_HOLD_LIST, sampRes);
      }
    }
    else if (TX_TYPE_SAMP_SEL.equals(_txType)) {
      sampRes = new SampleSetResultsWrapper(new HoldListSampleSet(secInfo), "Hold List");
      _productQueries.put(PRODUCT_TYPE_HOLD_LIST, sampRes);
    }
    else if (TX_TYPE_ICP.equals(_txType)) {
      if (!ApiFunctions.isEmpty(_icpItemId)) {
        sampRes = new IcpSampleSet(secInfo, _icpItemId);
        _productQueries.put(PRODUCT_TYPE_SAMPLE, sampRes);
      }
      //if a consent id hasn't been specified throw an exception
      else {
        throw new ApiException("No consent id has been specified.");
      }
    }
    else if (TX_TYPE_DERIV_OPS.equals(_txType)) {
      if (_derivationBatchDto != null) {
        sampRes = new DerivationBatchDetailsSampleResults(secInfo, _derivationBatchDto);
        _productQueries.put(PRODUCT_TYPE_SAMPLE, sampRes);
      }
    }
  }

  /**
   *  set the filters and contruct and call the summary query
   * @param  btx  the BTXDetails instance that holds the filters map
   */
  public BTXDetails setSampleFiltersAndQuery(BTXDetailsGetSampleSummary btx, int chunkSize)
    throws Exception {
    return createProductQueryAndLoadSummary(PRODUCT_TYPE_SAMPLE, btx, chunkSize);
  }

  /**
   *  set the filters and contruct and calls the summary query
   * @param  btx  the BTXDetails instance that holds the filters map
   */
  public BTXDetails setRnaFiltersAndQuery(BTXDetailsGetSampleSummary btx, int chunkSize)
    throws Exception {
    return createProductQueryAndLoadSummary(PRODUCT_TYPE_RNA, btx, chunkSize);
  }

  public void setFilters(Map m) {
    _filters = new QueryProfile(m);
  }

  /**
   *  set the filters for Tissue samples but do not execute the related query
   * @param  btx  the BTXDetails instance that holds the filters map
   */
  public BTXDetails setSampleFilters(BTXDetailsGetSampleSummary btx, int chunkSize)
    throws Exception {
    _filters = btx.getFilterSet();
    ProductQuery query = new SampleQuery(chunkSize, btx.getLoggedInUserSecurityInfo());
    _productQueries.put(PRODUCT_TYPE_SAMPLE, query);
    return btx;
  }

  /**
   *  set the filters for RNA, but do not run the query
   * @param  btx  the BTXDetails instance that holds the filters map
   */
  public BTXDetails setRnaFilters(BTXDetailsGetSampleSummary btx) throws Exception {
    _filters = btx.getFilterSet();
    ProductQuery query = new RnaQuery(0, btx.getLoggedInUserSecurityInfo());
    _productQueries.put(PRODUCT_TYPE_RNA, query);
    return btx;
  }

  /** remove the RNA query from the results.  Use if the RNA query is empty or not run **/
  public void removeRnaQuery() {
    _productQueries.remove(PRODUCT_TYPE_RNA);
  }

  /** remove the Tissue query from the results.  Use if the Tissue query is empty or not run **/
  public void removeSampleQuery() {
    _productQueries.remove(PRODUCT_TYPE_SAMPLE);
  }

  /**
   *  The object saved in the session is a <code>Map</code> of 
   * <code>ProductQuery</code> objects, keyed by the product type.
   * 
   * @param productType  the product type, which must be one of the 
   *                      PRODUCT_TYPE_* contstants defined in this class
   * @param btxSumm   the BTXDetails request object with filter conditions
   * @param chunkSize   how many records per page to display.  0 means all records.
   * @return  a BTXDetails object with the Summary information on it.
   */
  private BTXDetails createProductQueryAndLoadSummary(
    String productType,
    BTXDetailsGetSampleSummary btxSumm,
    int chunkSize)
    throws Exception {
    // log start, create Query object, load summary, log end
    long tStart = 0;
    if (_perfLog.isDebugEnabled()) {
      _perfLog.debug("    A: START ProductQuery " + productType);
      tStart = System.currentTimeMillis();
    }

    ProductQuery query = null;
    _filters = btxSumm.getFilterSet();
    if (productType.equals(PRODUCT_TYPE_SAMPLE)) {
      query = new SampleQuery(chunkSize, btxSumm.getLoggedInUserSecurityInfo());
      btxSumm = query.loadSummary(btxSumm);
    }
    else if (productType.equals(PRODUCT_TYPE_RNA)) {
      query = new RnaQuery(chunkSize, btxSumm.getLoggedInUserSecurityInfo());
      btxSumm = query.loadSummary(btxSumm);
    }
    _productQueries.put(productType, query);

    if (_perfLog.isDebugEnabled()) {
      long elapsedTime = System.currentTimeMillis() - tStart;
      _perfLog.debug("    A: END   ProductQuery " + productType + " (" + elapsedTime + " ms)");
    }

    return btxSumm;
  }

  /**
   *  Set the input data from the Form.  Includes next chunk, selected ids, etc.
   */
  //    private void setForm(ResultsForm form) {
  //        getCurrentSampleResults().setForm(form);
  //    }

  /**
   * Return a String with the query summary for the RNA query.
   * @return the summary text
   */
  public String getRnaQuerySummary() {
    if (getProductQuery(PRODUCT_TYPE_RNA) != null) {
      RnaFilters rf = new RnaFilters(_filters);
      return rf.toString();
    }
    else {
      return PRODUCT_QUERY_NA;
    }
  }

  /**
   * Return a String with the query summary for the Tissue (a.k.a. sample) query.
   * @return the summary text
   */
  public String getTissueQuerySummary() {
    if (getProductQuery(PRODUCT_TYPE_SAMPLE) != null) {
      SampleFilters sf = new SampleFilters(_filters);
      return sf.toString();
    }
    else {
      return PRODUCT_QUERY_NA;
    }
  }

  public void setOrderNumber(String ordNo) {
    _orderNumberStr = ordNo;
    //if we're setting the order number, we need to blank out the request number
    //(since the same functionality is used for both requests and orders)
    _requestNumberStr = null;
  }

  public void setRequestNumber(String reqNo) {
    _requestNumberStr = reqNo;
    //if we're setting the request number, we need to blank out the order number
    //(since the same functionality is used for both requests and orders)
    _orderNumberStr = null;
  }

  /**
   * Se the id of the ICP item whose sample we're showing: only used for TX_TYPE_ICP.
   */
  public void setIcpItemId(String icpItemId) {
    _icpItemId = icpItemId;
  }
  
  public void setDerivationBatchDto(DerivationBatchDto derivationBatchDto) {
    _derivationBatchDto = derivationBatchDto;
  }

  /**
   * Returns the product type to be displayed.
   * 
   * @return  One of the PRODUCT_TYPE_* contstants defined in this class.
   */
  public String getProductType() {
    return _currentProductType;
  }

  /**
   * Between tissue and RNA only, what was the last type we were viewing?
   */
  public String getRememberedType() {
    return _lastTissueRnaProductType;
  }

  /**
   * Set a product type to retreive later and return to.  Allows the user to do something like
   * add to hold, then be returned to where they left off.
   */
  public void setRememberedProductType(String type) {
    if (PRODUCT_TYPE_RNA.equals(type) || PRODUCT_TYPE_SAMPLE.equals(type)) {
      _lastTissueRnaProductType = type; // remember which tab we were on
    }
  }

  public void setProductType(String type) {
    _currentProductType = type;
    initLists(type, _securityInfo);

    if (_currentProductType.equals(PRODUCT_TYPE_RNA_SELECTIONS)) {
      SampleSet sampSet = new SimpleSampleSet(_securityInfo);
      SampleResults rnaResults = getSampleResults(PRODUCT_TYPE_RNA);
      sampSet.addSamplesById(rnaResults.getSelectedIds());
      SampleResults selectedRna = new SampleSetResultsWrapper(sampSet, "RNA Selections");
      _productQueries.put(PRODUCT_TYPE_RNA_SELECTIONS, selectedRna);
    }
  }

  public void setChunk(int chunkNo) {
    getCurrentSampleResults().setChunk(chunkNo);
  }

  /**
   * Get the SampleSelectionSummary for the specified productType.
   * @param productType   The string representing which query object to get the summary for.
   */
  public SampleSelectionSummary getQuerySummary(String productType) {
    ProductQuery productQuery = getProductQuery(productType);
    return (productQuery == null) ? null : productQuery.getSummary();
  }

  /**
  * Get the SampleSelectionSummary for the hold list.
  * This could be extended to support multiple products on hold list, but 
  * assumes Tissue for now
  */
  public SampleSelectionSummary getOnHoldSummary() {
    SampleResults res = getSampleResults(PRODUCT_TYPE_HOLD_LIST);
    if (res == null) {
      SampleSet ss = new HoldListSampleSet(_securityInfo);
      res = new SampleSetResultsWrapper(ss, "Hold List");
      _productQueries.put(PRODUCT_TYPE_HOLD_LIST, res);
    }
    return res.getSummary();
  }

  /**
   * Returns the number of th current chunk that was being viewed by the user.  
   * 
   * @return  The current chunk number, or 0 if the next chunk cannot be 
   * 						determined, or the last chunk if the next chunk specified 
   * 						is greater than the number of chunks for the product type.
   */
  public int getCurrentChunkAsInt() {
    return getCurrentProductQuery().getCurrentChunkNumber();
  }

  /**
   * Returns a list of all product types that can be displayed to the current
   * user.  Each entry in the list is a string that is equal to one of the
   * <code>PRODUCT_TYPE_*</code> contstants defined in this class.
   * 
   * @return  The product types, in the order in which they should be displayed.
   */
  public List getAllProductTypes() {
    List types = new ArrayList();
    types.add(PRODUCT_TYPE_SAMPLE);
    if (_productQueries.containsKey(PRODUCT_TYPE_RNA)) {
      types.add(PRODUCT_TYPE_RNA);
    }
    return types;
  }

  /**
   * @return a Map containing all the Filter objects in any product query, keyed
   * by the FilterConstant that represents the Filter subclass.
   */
  public Map getFilterMap() {
    if (_filters == null)
      return Collections.EMPTY_MAP;

    return _filters.getFiltersAsFlatMap();

    //        Map m = new HashMap();
    //        Iterator it = _productQueries.values().iterator();
    //        while (it.hasNext()) {
    //            SampleResults sr = (SampleResults) it.next();
    //            if (sr instanceof ProductQuery) {
    //              ProductQuery pq = (ProductQuery) sr; // maybe put empty getFilters on SampReslts?
    //              m.putAll(pq.getFilters().getAllInputFilters()); // was pq.getFilt().getAllInptFilt()
    //            }
    //        }
    //        return m;
  }

  // return the query for the type.  Null is OK if there are no results for that
  private ProductQuery getProductQuery(String prodType) {
    Object pq = _productQueries.get(prodType);
    if (pq == null || pq instanceof ProductQuery)
      return (ProductQuery) pq;
    else
      throw new ApiException(
        "Attempt to get a product query, but found a "
          + ApiFunctions.shortClassName(pq.getClass().getName()));
  }

  /**
   * Return a List of result sets.
   */
  public List getAllSampleResults() {
    List results = new ArrayList();
    SampleResults sr;
    // retreive SampleResult objects in order and add each to the list
    sr = (SampleResults) _productQueries.get(PRODUCT_TYPE_SAMPLE);
    if (sr != null)
      results.add(sr);
    sr = (SampleResults) _productQueries.get(PRODUCT_TYPE_RNA);
    if (sr != null)
      results.add(sr);
    return results;
  }

  public SampleResults getSampleResults(String prodType) {
    SampleResults sr = (SampleResults) _productQueries.get(prodType);
    return sr;
  }

  /*
   * Return the ProductQuery that handles the currently selected product type.
   */
  private ProductQuery getCurrentProductQuery() {
    ProductQuery pq = getProductQuery(getProductType());
    return pq;
  }

  /**
   * @return a SampleResults object representing the currently selected sample set.
   */
  public SampleResults getCurrentSampleResults() {
    SampleResults res = getSampleResults(getProductType());
    return res;
  }

  /**
   * @return a SampleResults object representing the samples on hold.
   */
  public SampleResults getHoldResults() {
    return getSampleResults(PRODUCT_TYPE_HOLD_LIST);
  }

  /**
   * Return the number of chunks in the query for the current product type.
   * @return the number of chunks.
   */
  public int getNumberOfChunks() {
    return getProductQuery(getProductType()).getNumberOfChunks();
  }

  /**
   * Returns the number of the current chunk (which block of ids we are on)
   * @return the numeric index of the current chunk for the current product type.
   */
  public int getCurrentChunkNumber() {
    return getProductQuery(getProductType()).getCurrentChunkNumber();
  }

  /**
   * @return the index of the start of the current chunk in the overall list of ids for the current product.
   */
  public int getCurrentChunkIndex() {
    SampleResults query = getCurrentSampleResults();
    return query.getCurrentChunkIndex();
  }

  // build a map with no amounts
  private Map getSelectedIdsAndAmountsMap() {
    // convert array of IDs to a map with null as each "amount" -- currently no amounts specified
    Map m = new HashMap();
    String[] ids = getSelectedIds();
    for (int i = 0; i < ids.length; i++) {
      m.put(ids[i], null);
    }
    return m;
  }

  /**
   * Reset the selected items for the current product query type.
   */
  public void clearSelectedIds() {
    SampleResults res = getCurrentSampleResults();
    if (res != null)
      res.clearSelectedIds();
  }

  public void setSelectedIdsForCurrentChunk(String[] ids) {
    getCurrentSampleResults().clearSelectedIdsForCurrentChunk();
    getCurrentSampleResults().addSelectedIds(ids);
  }

  /**
   * Returns the ids of the selected products for the specified chunk.
   * 
   * @param  chunkNumber  the chunk number that contains the ids
   * @return  An array of ids.  If no ids are selected, an empty array is returned.
   */
  public String[] getSelectedIds(int chunkNumber) {
    return getCurrentProductQuery().getSelectedIds(chunkNumber);
  }

  /**
   * Returns the ids of the selected products for the specified chunk as a
   * String, where each id is separated by a space
   * 
   * @param  chunkNumber  the chunk number that contains the ids
   * @return  The concatenation of all of the ids, with each separated by a
   * 					 space.  If there are no ids in the chunk, an empty string is 
   * 					 returned.
   */
  public String getSelectedIdsAsString(int chunkNumber) {
    String[] selectedIds = getSelectedIds(chunkNumber);
    if (ApiFunctions.isEmpty(selectedIds)) {
      return "";
    }
    else {
      StringBuffer buf = new StringBuffer();
      for (int i = 0; i < selectedIds.length; i++) {
        if (i > 0) {
          buf.append(' ');
        }
        buf.append(selectedIds[i]);
      }
      return buf.toString();
    }
  }

  /**
   * @return all the selected ids, per chunk, with spaces between ids,
   */
  public String[] getSelectedIdsAsStrings() {
    int numChunks = getNumberOfChunks();
    String[] result = new String[numChunks];
    for (int i = 0; i < numChunks; i++) {
      result[i] = getSelectedIdsAsString(i);
    }
    return result;
  }

  /**
   * Returns the ids of all selected products across all chunks.
   * 
   * @return  An array of ids.  If no ids are selected, an empty array is
   * 					 returned.
   */
  public String[] getSelectedIds() {
    return getCurrentSampleResults().getSelectedIds();
  }

  /**
   * Returns the ids of the on hold products.
   * 
   * @return  An array of ids.  If no ids are selected, an empty array is returned.
   */
  public String[] getOnHoldIds() {
    // return the hold list's RNA ids or Sample ID's, depending on what's current
    String currProdType = getProductType();
    if (PRODUCT_TYPE_SAMPLE.equals(currProdType)) {
      SampleResults holdRes = getSampleResults(PRODUCT_TYPE_HOLD_LIST);
      String[] ids = holdRes.getSampleIds();
      return IdValidator.validSampleIds(ids);
    }
    else if (PRODUCT_TYPE_RNA.equals(currProdType)) {
      SampleResults holdRes = getSampleResults(PRODUCT_TYPE_HOLD_LIST);
      String[] ids = holdRes.getSampleIds();
      return IdValidator.validRnaIds(ids);
    }
    else {
      return new String[0];
    }
  }

  /**
   * Adds the specified array of ids to the list of ids that are no longer
   * available to be put on hold for the current product type. 
   * 
   * @param  ids  the ids
   */
  public void addRemovedIds(String[] ids) {
    getCurrentSampleResults().addRemovedIds(ids);
  }

  /**
   * Returns the ids of the removed products.
   * 
   * @return  An array of ids.  If no ids are in the unavailable list, an 
   * 					 empty array is returned.
   */
  public String[] getRemovedIds() {
    return getCurrentSampleResults().getRemovedIds();
  }

  /**
   * Returns an indication of whether any results were returned by any of the
   * product queries that were run.
   * 
   * @return  <code>true</code> if at least one item was returned in one query;
   * 					 <code>false</code> otherwise.
   */
  public boolean isAnyResults() {
    List prodTypes = getAllProductTypes();
    for (int i = 0; i < prodTypes.size(); i++) {
      if (isAnyResults((String) prodTypes.get(i)))
        return true;
    }
    return false;
  }

  /**
   * Returns an indication of whether any results were returned by the
   * product query that was run for the specified product type.
   * 
   * @param  the product type
   * @return  <code>true</code> if at least one item was returned in the 
   * 					 query for the product type; <code>false</code> otherwise.
   */
  public boolean isAnyResults(String productType) {
    SampleSelectionSummary summary = getQuerySummary(productType);
    return (summary != null) && (summary.getTotalSampleCount() > 0);
  }

  /**
   * Returns an indication of whether BIGR supports placing the current product 
   * type on hold.
   * 
   * @return  <code>true</code> if the current product type can be placed
   * 					 on hold; <code>false</code> otherwise.
   */
  public boolean isCanBePlacedOnHold() {
    return (
      getProductType().equals(PRODUCT_TYPE_SAMPLE) || getProductType().equals(PRODUCT_TYPE_RNA));
  }

  /**
   * Returns an indication of whether BIGR supports placing the current product 
   * type on hold.
   * 
   * @return  The string "true" if the current product type can be placed
   * 					 on hold; "false" otherwise.
   */
  public String getCanBePlacedOnHold() {
    return (isCanBePlacedOnHold()) ? "true" : "false";
  }

  /**
   * @return a helper holding data about counts of samples with the specified sample type.
   */
  public SampleCountHelper getSampleCountHelper(String sampleType) {
    ProductQuery pq = getCurrentProductQuery();
    return pq.getSampleCount(sampleType);
  }

  /**
   * Returns the count of the number of on hold products.
   * 
   * @return  The number of on hold ids, as a String.
   */
  public String getOnHoldIdsCount() {
    return String.valueOf(getOnHoldIds().length);
  }

  /**
   * Returns the count of the number of removed products.
   * 
   * @return  The number of on hold ids, as a String.
   */
  public String getRemovedIdsCount() {
    return String.valueOf(getRemovedIds().length);
  }

  /**
   * Returns the count of the number of selected product ids across all chunks.
   * 
   * @return  The number of selected ids, as a String.
   */
  public String getSelectedIdsCount() {
    return Integer.toString(getCurrentProductQuery().getSelectedIdsCount());
  }

  public SampleViewData getHelpers() {
    return _sampleViewData;
  }

  /**
   * Return the helpers representing the detailed data for the current view.
   */
  public void updateHelpers(BTXDetailsGetSamples btx) {
    if (btx == null) {
      throw new ApiException("No btx passed into ResultsHelper.updateHelpers()");
    }

    SampleResults currRes = getCurrentSampleResults();

    // explicit BTX chunk means set chunk from BTX.  No BTX chunk means get chunk from session
    if (btx.getCurrentChunk() == -1) { // change chunks?  (explicit chunk number)
      btx.setCurrentChunk(currRes.getCurrentChunkNumber());
    }

    try {
      BTXDetailsGetSamples newBtx = currRes.getSampleData(btx);
      //copy the view profile used back to the original btxdetails
      btx.setViewProfile(newBtx.getViewProfile());
           
      // now manipulate the results (add hold amounts, re-sort) and copy results back to btx
      _sampleViewData = new SampleViewData(newBtx);

      // get the amounts on hold for every ID on hold (if there is a valid hold list)
      SampleResults holdRes = getSampleResults(PRODUCT_TYPE_HOLD_LIST);
      Map m = (holdRes == null) ? Collections.EMPTY_MAP : holdRes.getSampleAmounts();
      _sampleViewData.addHoldAmounts(m);

      // sort the data for the current chunk if appropriate
      // multi-page sample/RNA views should maintain DB sort.  Others are sorted.
      if (!_currentProductType.equals(ResultsHelper.PRODUCT_TYPE_RNA)
        && !_currentProductType.equals(ResultsHelper.PRODUCT_TYPE_SAMPLE)) {
        _sampleViewData.sortByDonorCaseAsm();
      }

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Return true if the current view has any data elements.  
   */
  public boolean hasData() {
    return getCurrentSampleResults().getSampleIds().length > 0;
  }

  // Wrap each SampleData object in a SampleSelectionHelper GUI helper wrapper.
  // To do this, "knit together" db query info and hold list info to provide an 
  // integrated display record with fundamental data plus hold amounts for RNA
  // If there is no hold information, there are no hold amounts, so use an empty map.
  private List sampleHelpersForSampleData(List data) {
    // get the amounts on hold for every ID on hold (if there is a valid hold list)
    SampleResults holdRes = getSampleResults(PRODUCT_TYPE_HOLD_LIST);
    Map m = (holdRes == null) ? Collections.EMPTY_MAP : holdRes.getSampleAmounts();

    // set the hold values on any RNA objects that have an entry in the hold map
    int len = data.size();
    List helpers = new ArrayList(len);
    for (int i = 0; i < len; i++) {
      ProductDto sd = (ProductDto) data.get(i);
      SampleSelectionHelper helper = new SampleSelectionHelper(sd);
      // if the item is RNA with a hold entry, set the value
      if (helper.isRna()) {
        Integer amt = (Integer) m.get(helper.getRnaVialId());
        helper.setAmountOnHold(amt);
      }
      helpers.add(helper);
    }
    return helpers;
  }

  /**
   * Returns the currentTransactionType.
   * @return String
   */
  public String getTransactionType() {
    return _txType;
  }

  /**
   * Returns the SampleSet that manages selections (The Hold List, for instance).  
   * 
   * @return SampleSet
   */
  public SampleSet getSelectedSamples() {
    return (SampleSet) _productQueries.get(PRODUCT_TYPE_HOLD_LIST);
  }
  
  public QueryFormDefinition getQueryForm() {
    return _queryForm; 
  }

  public void setQueryForm(QueryFormDefinition queryForm) {
    _queryForm = queryForm;

    // Whenever we set a new query form into the session we want to remove all existing KC 
    // filters, since any filters that are saved here are for the previous KC form.  We do
    // this even if the KC form is set to null, which happens, for example, when the user
    // clears the KC form.
    if (_filters != null) {
      Map m = _filters.getFiltersAsFlatMap();
      if (m != null) {
        Iterator i = m.keySet().iterator();
        while (i.hasNext()) {
          String key = (String) i.next();
          if (FilterKc.isKcFilterKey(key)) {
            _filters.removeFilter(key);
          }
        }
      }      

      // Create the appropriate filters from the query form and add them to the filters collection.
      if (queryForm != null) {
        QueryKcForm kcForm = new QueryKcForm();
        kcForm.setQueryFormDefinition(queryForm);
        Map kcFilters = kcForm.getFiltersFromQueryForm();
        Iterator i = kcFilters.values().iterator();
        while (i.hasNext()) {
          _filters.addFilter((Filter) i.next());
        }
      }
    }
    
  }
}
