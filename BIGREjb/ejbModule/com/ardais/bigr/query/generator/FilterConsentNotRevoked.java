package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to exclude samples whose consent has been revoked.
 */
public class FilterConsentNotRevoked extends FilterNoParameters {

  /**
   * Constructor for FilterConsentNotRevoked.
   */
  public FilterConsentNotRevoked() {
    super(FilterConstants.KEY_CONSENT_NOT_REVOKED);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * This method causes this filter to be added to a query builder.
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterConsentNotRevoked();
  }
}
