package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsLike;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to get donors with particular alias ids
 */
public class FilterDonorAliasId extends FilterStringsLike {

  public FilterDonorAliasId(String[] patterns) {
    super(FilterConstants.KEY_DONOR_ALIAS_ID, patterns);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "Donor Alias ID";
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterDonorAliasLike(getPatternsForDB(), getOrGroupCode());
  }

  public boolean isEmpty() {
    return getPatterns().length == 0;
  }
}
