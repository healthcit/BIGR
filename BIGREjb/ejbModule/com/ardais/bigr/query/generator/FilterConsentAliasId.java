package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsLike;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to get cases with particular alias ids
 */
public class FilterConsentAliasId extends FilterStringsLike {

  public FilterConsentAliasId(String[] patterns) {
    super(FilterConstants.KEY_CONSENT_ALIAS_ID, patterns);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "Case Alias ID";
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterConsentAliasLike(getPatternsForDB(), getOrGroupCode());
  }

  public boolean isEmpty() {
    return getPatterns().length == 0;
  }
}
