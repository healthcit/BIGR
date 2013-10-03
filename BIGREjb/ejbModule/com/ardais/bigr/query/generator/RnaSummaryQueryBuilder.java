package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.ProductFilters;
import com.ardais.bigr.query.filters.RepeatingFilterData;
import com.ardais.bigr.query.generator.gen.ColumnMetaData;
import com.ardais.bigr.query.generator.gen.FilterMetaData;
import com.ardais.bigr.query.generator.gen.TableMetaData;
import com.ardais.bigr.util.IdValidator;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 */
public class RnaSummaryQueryBuilder extends ProductSummaryQueryBuilder {

  private static final String BMS_YN_OR_GROUP = "orBmsYn";

  /**
   * Creates a new <code>RnaSummaryQueryBuilder</code>.
   *
   * @param  securityInfo  the security info of the requesting user
   */
  public RnaSummaryQueryBuilder() {
    super();
  }

  /**
   * Creates a new <code>RnaSummaryQueryBuilder</code>.
   * 
   * @param  filters
   * @param  securityInfo  the security info of the requesting user
   */
  public RnaSummaryQueryBuilder(ProductFilters filters) {
    super(filters);

  }

  /**
   * Returns the SQL for the RNA query specified by this class.
   *
   * @return  The SQL statement for the RNA query.
   */
  protected String doGetSummarySingleQuerySql() {
    ProductQueryBuilder query = getQueryBuilder();

    // to be an rna query at all, we must filter for RNA only
    addFilterRnaProductType("RNA");

    // Add the columns used for summary information.        
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_CONSENT_ID);
    addColumnInSample(ColumnMetaData.KEY_SAMPLE_ARDAIS_ID);
    addColumnInRnaBatchDetail(ColumnMetaData.KEY_RNA_RNAVIALID);

    // Append all of the clauses to form the query.
    StringBuffer sql = new StringBuffer(1024);
    query.getQuery(sql);
    sql.append("\n");
    sql.append("GROUP BY ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.SAMPLE_ARDAIS_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_SAMPLE);
    sql.append(".");
    sql.append(DbConstants.SAMPLE_CONSENT_ID);
    sql.append(", ");
    sql.append(DbAliases.TABLE_RNA_BATCH_DETAIL);
    sql.append(".");
    sql.append(DbConstants.RNA_RNAVIALID);

    return sql.toString();
  }

  public void addFilterCompositionAcellularStroma(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_PE_TAS, new String[] { from, to });
  }

  public void addFilterCompositionCellularStroma(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_PE_TCS, new String[] { from, to });
  }

  public void addFilterCompositionLesion(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_PE_LSN, new String[] { from, to });
  }

  public void addFilterCompositionNecrosis(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_PE_NEC, new String[] { from, to });
  }

  public void addFilterCompositionNormal(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_PE_NRM, new String[] { from, to });
  }

  public void addFilterCompositionTumor(String from, String to) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_PE_TMR, new String[] { from, to });
  }

  public void addFilterQualityGreater(String quality) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_QUALITY_GREATER, quality);
    addJoinRnaGrossExtraction();
  }

  public void addFilterRnaAmountAvailable() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_AMOUNT_AVAILABLE);
    addOuterJoinRnaHold();
  }

  public void addFilterNotOnHoldForUser(String userId) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_NO_EXISTING_SHOPPING_CART_ENTRY, userId);
  }

  public void addFilterNotOnHoldForUserOrBmsYNYes(String userId) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_NO_EXISTING_SHOPPING_CART_ENTRY_OR_BMS_RNA_YN_YES, userId);
  }

  public void addFilterIDsEqual(String[] rnaIds, String orGroupCode) {
    addFilterRnaEqual(rnaIds, orGroupCode);
  }

  public void addFilterRnaEqual(String[] ids, String orGroupCode) {
    String[] rnaIds = IdValidator.validRnaIds(ids);
    if (rnaIds.length > 0) {
      getQueryBuilder().addFilterOr(orGroupCode, FilterMetaData.KEY_RNA_RNAVIALID, rnaIds);
      //addJoinRnaPoolSample();
      addJoinRnaSample();
    }
  }

  public void addFilterRnaStatusOrBmsYNYes(String[] codes) {
    getQueryBuilder().addFilterOr(BMS_YN_OR_GROUP, FilterMetaData.KEY_RNA_STATUS, codes);
    getQueryBuilder().addFilterOr(BMS_YN_OR_GROUP, FilterMetaData.KEY_RNA_BMS_YN_YES);

    addJoinRnaSample();
  }

  private void addFilterRnaProductType(String type) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_PRODUCT_TYPE, type);
    //addJoinRnaPoolBatchDetail();
    addJoinRnaSample();
  }

  public void addFilterRnaNotRestricted() {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_NOT_RESTRICTED);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_POOL_TISSUE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterRnaRestricted(String account) {
    getQueryBuilder().addFilter(FilterMetaData.KEY_RNA_RESTRICTED_DI, account);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_POOL_TISSUE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  public void addFilterLogicalRepository(RepeatingFilterData logicalRepositories) {
    //if no logical repository ids were passed in, the user doesn't have
    //access to any logical repositories and therefore no results should 
    //be returned.
    if (logicalRepositories.isEmpty()) {
      addFilterReturnNoResults();
    }
    else {
      getQueryBuilder().addFilter(
        FilterMetaData.KEY_RNA_IN_LOGICAL_REPOSITORY,
        logicalRepositories);
    }
  }

  /**
   * Adds a column in the RNA batch detail table to the query, adding the 
   * necessary joins as well.
   */
  private void addColumnInRnaBatchDetail(String key) {
    getQueryBuilder().addColumn(key);
    //addJoinRnaPoolBatchDetail();
    addJoinRnaSample();
  }

  /**
   * Adds the RNA batch detail to RNA join to the query, adding the necessary 
   * tables as well.
   */
  private void addJoinRnaPoolBatchDetail() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_RNA_POOL_BATCH_DETAIL);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_POOL_TISSUE);
    addJoinRnaPoolSample();
  }

  /**
   * Adds the RNA gross extraction to RNA pooled tissue join to the query, 
   * adding the necessary tables as well.
   */
  private void addJoinRnaGrossExtraction() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_RNA_GROSS_EXTRACTION);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_GROSS_EXTRACTION);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
    addJoinRnaPoolBatchDetail();
  }

  /**
   * Adds the RNA to sample join to the query, adding the necessary tables
   * as well.
   */
  private void addJoinRnaPoolSample() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_RNA_POOL_SAMPLE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_POOL_TISSUE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  /**
   * Adds joins RNA to Sample through rep sample, adding the necessary tables
   * as well.
   */
  private void addJoinRnaSample() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_RNA_REP_SAMPLE);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_SAMPLE);
  }

  /**
   * Adds the sample to ard_logical_repos_item join to the query, adding the necessary tables
   * as well.
   */
  protected void addJoinRnaLogicalReposItem() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_JOIN_RNA_LOGICAL_REPOS_ITEM);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_LOGICAL_REPOS_ITEM);
  }

  /**
   * Adds the hold list to RNA outer join to the query, adding the necessary 
   * tables as well.
   */
  private void addOuterJoinRnaHold() {
    getQueryBuilder().addJoin(FilterMetaData.KEY_OUTER_JOIN_RNA_RNA_HOLD);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_HOLD_AMOUNT);
    getQueryBuilder().addTable(TableMetaData.KEY_TABLE_RNA_BATCH_DETAIL);
    addJoinRnaPoolBatchDetail();
  }

  /**
   * @see com.ardais.bigr.query.generator.ProductSummaryQueryBuilder#newQueryBuilder()
   */
  protected ProductSummaryQueryBuilder newQueryBuilder() {
    return new RnaSummaryQueryBuilder();
  }
  /**
  * Returns overall order by clause
  *
  * @return  The order by clause for the sample query.
  */
  protected String doGetSummaryOrderBy() {
    String sql = ") ORDER BY ardais_id, consent_id, rnavialid";
    return sql;
  }

  /**
  * Returns overall select clause
  *
  * @return  The select clause for the sample query.
  */
  protected String doGetSummarySelectClause() {
    String sql = "Select \"sample_ardais_id\" as ardais_id, \"sample_consent_id\" as consent_id, rnavialid FROM (";
    return sql;
  }

}
