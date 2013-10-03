package com.ardais.bigr.query.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.javabeans.RnaData;
import com.ardais.bigr.security.SecurityInfo;

/**
 */
public class RnaDetailQueryBuilder extends ProductDetailQueryBuilder {

  private final static String KEY_RNA = "001"; // rna
  private final static String KEY_RNA_PROJECT = "002"; // rna project hold/sold
  private final static String KEY_RNA_SHOPPING_CART = "004"; // es_shopping_cart
  private final static String KEY_LOGICAL_REPOSITORY = "020"; // logical repository

  /**
   * Logger for logging performance-related items.
   */
  private static Log _perfLog = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_LIBRARY_PERFORMANCE);

  /**
   * Holds ProductQueryBuilder objects representing each query, keyed by the 
   * <code>KEY_Q*</code> constants defined in this class.
   */
  private SortedMap _queries = new TreeMap();

  /**
   * Constructor for RnaDetailQueryBuilder.
   */
  public RnaDetailQueryBuilder(SecurityInfo securityInfo) {
    super(securityInfo);
  }

  /**
   * Specifies that the bms_yn column be returned in this 
   * detail query.
   */
  public void addColumnRnaBmsYN() {
    getQueryRna().addColumnBmsYN();
  }

  /**
   * Specifies that the consent id column from RNA batch detail be returned 
   * in this detail query.
   */
  public void addColumnRnaConsentId() {
    getQueryRna().addColumnConsentId();
  }

  /**
   * Specifies that the amount of RNA on hold, including shopping cart and project entries.  
   */
  public void addColumnRnaHoldAmount() {
    getQueryRna().addColumnHoldAmount();
  }

  /**
   * Specifies that the RNA quality be returned in this detail query.
   */
  public void addColumnRnaQuality() {
    getQueryRna().addColumnQuality();
  }

  /**
   * Specifies that the RNA bio ratio be returned in this detail query.
   */
  public void addColumnRnaRatio() {
    getQueryRna().addColumnRatio();
  }

  /**
   * Specifies that the RNA prep column from be returned in this 
   * detail query.
   */
  public void addColumnRnaPrep() {
    getQueryRna().addColumnPrep();
  }

  /**
   * Specifies that the project name column be returned in this 
   * detail query.
   */
  public void addColumnRnaProject() {
    getQueryRnaProject().addColumnProjectName();
  }

  /**
   * Specifies that the amount of RNA remaining in inventory.  Note that
   * some of it may be on hold, so this is not the amount available to promise.
   */
  public void addColumnRnaRemainingVolume() {
    getQueryRna().addColumnRemainingVolume();
  }

  /**
   * Specifies that the RNA status column from be returned in this 
   * detail query.
   */
  public void addColumnRnaStatus() {
    getQueryRna().addColumnStatus();
  }

  /**
   * Specifies that the RNA Concentration column from be returned in this 
   * detail query.
   */
  public void addColumnRnaConcentration() {
    getQueryRna().addColumnConcentration();
  }

  /**
   * Specifies that the RNA INTERNAL_COMMENTS column from be returned in this 
   * detail query.
   */
  public void addColumnPvNotesInternal() {
    getQueryRna().addColumnInternalComments();
  }

  /**
   * Specifies that the RNA Rep_Sample column from be returned in this 
   * detail query.
   */
  public void addColumnRnaRepSample() {
    getQueryRna().addColumnRepSample();
  }

  /**
   * Specifies that the RNA PooledTissue column from be returned in this 
   * detail query.
   */
  public void addColumnRnaPooledTissue() {
    getQueryRna().addColumnPooledTissue();
  }

  /**
   * Specifies that the RNA PooledTissue column from be returned in this 
   * detail query.
   */
  public void addColumnRnaCaseExhausted() {
    getQueryRna().addColumnCaseExhausted();
  }

  /**
   * Specifies that the RNA vial id column from be returned in this 
   * detail query.
   */
  public void addColumnRnaVialId() {
    getQueryRna().addColumnRnaVialId();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.generator.ProductDetailQueryBuilder#addColumnCompositionAcellularStroma()
   */
  public void addColumnCompositionAcellularStroma() {
    getQueryRna().addColumnCompositionAcellularStroma();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.generator.ProductDetailQueryBuilder#addColumnCompositionCellularStroma()
   */
  public void addColumnCompositionCellularStroma() {
    getQueryRna().addColumnCompositionCellularStroma();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.generator.ProductDetailQueryBuilder#addColumnCompositionLesion()
   */
  public void addColumnCompositionLesion() {
    getQueryRna().addColumnCompositionLesion();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.generator.ProductDetailQueryBuilder#addColumnCompositionNecrosis()
   */
  public void addColumnCompositionNecrosis() {
    getQueryRna().addColumnCompositionNecrosis();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.generator.ProductDetailQueryBuilder#addColumnCompositionNormal()
   */
  public void addColumnCompositionNormal() {
    getQueryRna().addColumnCompositionNormal();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.generator.ProductDetailQueryBuilder#addColumnCompositionTumor()
   */
  public void addColumnCompositionTumor() {
    getQueryRna().addColumnCompositionTumor();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.generator.ProductDetailQueryBuilder#addColumnPvNotes()
   */
  public void addColumnPvNotes() {
    getQueryRna().addColumnPvNotes();
  }

  public void addColumnNotes() {
    getQueryRna().addColumnNotes();
  }

  /**
   * Link shopping cart data about the RNA ID to the sample, rather than linking by the sample id
   */
  public void addColumnShoppingCartCreationDate() {
    getQueryRnaShoppingCart().addColumnShoppingCartCreationDate();
  }

  /**
   * Link shopping cart data about the RNA ID to the sample, rather than linking by the sample id
   */
  public void addColumnShoppingCartUser() {
    getQueryRnaShoppingCart().addColumnShoppingCartUser();
  }

  /**
   * Add any logical repository columns that are mandatory for proper system function.  These
   * columns must be included in the result details, for example, even if the information in them
   * isn't being shown to the user.  This is public so you can call it explicitly, but this class's
   * constructor also calls this.
   * <b>This only returns information on logical repositories that the current user is permitted
   * to see.  So, for example, if you use this to get the list of logical repositories that
   * a sample is in, it will include only the ones that the current user can see, which may not
   * be all of the repositories that the sample is actually in.  There are places that currently
   * depend on LogicalRepositoryDetailQueryBuilder behaving this way, see
   * {@link #getQueryUserLogicalRepository()} for more details.</b>
   */
  public void addMandatoryLogicalRepositoryColumns() {
    getQueryUserLogicalRepository().addMandatoryColumns();
  }

  /**
   * Specifies that the logical repository short name column be returned in this 
   * detail query.
   * <b>This only returns information on logical repositories that the current user is permitted
   * to see.  So, for example, if you use this to get the list of logical repositories that
   * a sample is in, it will include only the ones that the current user can see, which may not
   * be all of the repositories that the sample is actually in.  There are places that currently
   * depend on LogicalRepositoryDetailQueryBuilder behaving this way, see
   * {@link #getQueryUserLogicalRepository()} for more details.</b>
   */
  public void addColumnUserLogicalRepositoryShortNames() {
    getQueryUserLogicalRepository().addColumnRepositoryShortName();
  }

  /**
   * Returns the <code>LogicalRepositoryDetailQueryBuilder</code> for the 
   * logical repository query, creating it if necessary.
   * <b>The LogicalRepositoryDetailQueryBuilder adds implicit filters to the query that cause
   * it to only return information on logical repositories that the current user is permitted
   * to see.  So, for example, if you use this to get the list of logical repositories that
   * a sample is in, it will include only the ones that the current user can see, which may not
   * be all of the repositories that the sample is actually in.  There are places that currently
   * depend on LogicalRepositoryDetailQueryBuilder behaving this way, see
   * {@link LogicalRepositoryDetailQueryBuilder} for more details.</b>
   * 
   * RNA has its own LogicalRepositoryDetailQueryBuilder because it will use it to pass in
   * the list of RNA ids to get data for them.
   */
  private LogicalRepositoryDetailQueryBuilder getQueryUserLogicalRepository() {
    LogicalRepositoryDetailQueryBuilder queryBuilder =
      (LogicalRepositoryDetailQueryBuilder) _queries.get(KEY_LOGICAL_REPOSITORY);
    if (queryBuilder == null) {
      queryBuilder = new LogicalRepositoryDetailQueryBuilder(_securityInfo);
      _queries.put(KEY_LOGICAL_REPOSITORY, queryBuilder);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>ShoppingCartDetailQueryBuilder</code> for the 
   * shopping cart query, creating it if necessary.
   * 
   * RNA has its own ShopCart QB because it will use it to pass in
   * the list of RNA ids to get data for them.
   */
  private ShoppingCartDetailQueryBuilder getQueryRnaShoppingCart() {
    ShoppingCartDetailQueryBuilder queryBuilder =
      (ShoppingCartDetailQueryBuilder) _queries.get(KEY_RNA_SHOPPING_CART);
    if (queryBuilder == null) {
      queryBuilder = new ShoppingCartDetailQueryBuilder();
      _queries.put(KEY_RNA_SHOPPING_CART, queryBuilder);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>RnaBatchDetailQueryBuilder</code> for the RNA query, 
   * creating it if necessary.  The RNA query obtains RNA data.
   */
  private RnaBatchDetailQueryBuilder getQueryRna() {
    RnaBatchDetailQueryBuilder queryBuilder = (RnaBatchDetailQueryBuilder) _queries.get(KEY_RNA);
    if (queryBuilder == null) {
      queryBuilder = new RnaBatchDetailQueryBuilder();
      queryBuilder.setCreateSampleData(true);
      _queries.put(KEY_RNA, queryBuilder);
    }
    return queryBuilder;
  }

  /**
   * Returns the <code>RnaProjectDetailQueryBuilder</code> for RNA Project 
   * query, creating it if necessary.
   */
  private RnaProjectDetailQueryBuilder getQueryRnaProject() {
    RnaProjectDetailQueryBuilder queryBuilder =
      (RnaProjectDetailQueryBuilder) _queries.get(KEY_RNA_PROJECT);
    if (queryBuilder == null) {
      queryBuilder = new RnaProjectDetailQueryBuilder();
      _queries.put(KEY_RNA_PROJECT, queryBuilder);
    }
    return queryBuilder;
  }

  /**
   * Returns the tissue sample detail queries specified by this class.
   * 
   * @param  sampleIds  the array of sample IDs
   * @return  A <code>List</code> of strings, each of which is a SQL statement.
   */
  public List getDetailQueries(String[] sampleIds) {
    //	    return getDetails(sampleIds, false);
    return null;
  }

  /**
   * Returns the full details of the RNA vial IDs.  One or more columns must be 
   * specified before calling this method.  The only filters are the RNA vial 
   * IDs that are passed to this method.
   * 
   * @param  rnaIds  the array of RNA vial IDs
   * @return  A <code>List</code> of 
   * 					 {@link com.ardais.bigr.javabeans.RnaData} beans.
   */
  public List getDetails(String[] rnaIds) {
    List rnaDataBeans = new ArrayList();
    Map rnaMap = new HashMap();
    int numIds = rnaIds.length;
    for (int i = 0; i < numIds; i++) {
      String id = rnaIds[i];
      RnaData rna = new RnaData();
      rna.setRnaVialId(id);
      rnaMap.put(id, rna);
      rnaDataBeans.add(rna);
    }
    getDetails(rnaMap);
    return rnaDataBeans;
  }

  /**
   * Populates the full details of the RNA sample data beans specified
   * in the input <code>Map</code>.  One or more columns must be specified 
   * before calling this method.  The only filters are the RNA vial IDs that
   * are specified in the <code>Map</code>.
   * 
   * @param  rnaDataBeans  a <code>Map</code> of 
   * 						{@link com.ardais.bigr.javabeans.RnaData} data beans, keyed
   * 						by the ids of the RNA vials
   */
  public void getDetails(Map rnaDataBeans) {

    if (rnaDataBeans.isEmpty()) {
      return;
    }

    long tStart = 0;
    String myClassName = null;
    if (_perfLog.isDebugEnabled()) {
      myClassName = ApiFunctions.shortClassName(getClass().getName());
      _perfLog.debug("    C: START " + myClassName + ".getDetails");
      tStart = System.currentTimeMillis();
    }

    // Create the Maps that will hold the parent data beans.
    Map sampleMap = new HashMap();

    long tStartQuery;
    Object queryObj;

    tStartQuery = System.currentTimeMillis();
    queryObj = _queries.get(KEY_RNA);
    if (queryObj != null) {
      RnaBatchDetailQueryBuilder query = (RnaBatchDetailQueryBuilder) queryObj;
      query.getDetailsForRna(rnaDataBeans);
      sampleMap = query.getSampleData();
    }
    if (_perfLog.isDebugEnabled()) {
      long elapsedTime = System.currentTimeMillis() - tStartQuery;
      _perfLog.debug(
        "    D: do detail query RnaBatchDetailQueryBuilder" + " (" + elapsedTime + " ms)");
    }

    tStartQuery = System.currentTimeMillis();
    queryObj = _queries.get(KEY_RNA_PROJECT);
    if (queryObj != null) {
      RnaProjectDetailQueryBuilder query = (RnaProjectDetailQueryBuilder) queryObj;
      query.getDetailsForRna(rnaDataBeans);
    }
    if (_perfLog.isDebugEnabled()) {
      long elapsedTime = System.currentTimeMillis() - tStartQuery;
      _perfLog.debug(
        "    D: do detail query RnaProjectDetailQueryBuilder" + " (" + elapsedTime + " ms)");
    }

    tStartQuery = System.currentTimeMillis();
    queryObj = _queries.get(KEY_LOGICAL_REPOSITORY);
    if (queryObj != null) {
      LogicalRepositoryDetailQueryBuilder query = (LogicalRepositoryDetailQueryBuilder) queryObj;
      query.getDetailsForRna(rnaDataBeans);
    }
    if (_perfLog.isDebugEnabled()) {
      long elapsedTime = System.currentTimeMillis() - tStartQuery;
      _perfLog.debug(
        "    D: do detail query LogicalRepositoryDetailQueryBuilder" + " (" + elapsedTime + " ms)");
    }

    // super is like a Sample Query Detail object
    super.getDetails(sampleMap);

    tStartQuery = System.currentTimeMillis();
    queryObj = _queries.get(KEY_RNA_SHOPPING_CART);
    if (queryObj != null) {
      Map rnaIdsAndSampleData = getRnaIDsAndSampleData(rnaDataBeans);
      ShoppingCartDetailQueryBuilder query = (ShoppingCartDetailQueryBuilder) queryObj;
      // put this data on the samples (that's where they go for now) though it is RNA data
      query.getDetailsForSamples(rnaIdsAndSampleData);
    }
    if (_perfLog.isDebugEnabled()) {
      long elapsedTime = System.currentTimeMillis() - tStartQuery;
      _perfLog.debug(
        "    D: do detail query ShoppingCartDetailQueryBuilder" + " (" + elapsedTime + " ms)");
    }

    if (_perfLog.isDebugEnabled()) {
      long elapsedTime = System.currentTimeMillis() - tStart;
      _perfLog.debug("    C: END   " + myClassName + ".getDetails" + " (" + elapsedTime + " ms)");
    }
  }

  /**
   * Ignore the keys in the map.  For each value (a RnaData) put it in a new map keyed
   * by RNAVialID, and containing the corresponding SampleData rather than the RnaData itself.
   */
  public Map getRnaIDsAndSampleData(Map rnaMap) {
    Map result = new HashMap(rnaMap.size() * 2);
    Iterator it = rnaMap.values().iterator();
    while (it.hasNext()) {
      RnaData rd = (RnaData) it.next();
      result.put(rd.getRnaVialId(), rd.getRepresentativeSample());
    }
    return result;
  }

}
