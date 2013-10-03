package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to exclude samples whose consent has been pulled.
 */
public class FilterConsentNotPulled extends FilterNoParameters {

  /**
   * Constructor for FilterConsentNotPulled.
   */
  public FilterConsentNotPulled() {
    super(FilterConstants.KEY_CONSENT_NOT_PULLED);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * This method causes this filter to be added to a query builder.
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterConsentNotPulled();
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#getDataDescription()
   */
  public String getDataDescription() {
    return "not pulled";
  }
}
