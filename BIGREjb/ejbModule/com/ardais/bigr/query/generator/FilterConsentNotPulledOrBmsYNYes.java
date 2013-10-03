package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to exclude samples whose consent has been pulled.
 */
public class FilterConsentNotPulledOrBmsYNYes extends FilterNoParameters {

  /**
   * Constructor for FilterConsentNotPulledOrBmsYNYes.
   */
  public FilterConsentNotPulledOrBmsYNYes() {
    super(FilterConstants.KEY_CONSENT_NOT_PULLED_OR_BMS_YN_YES);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * This method causes this filter to be added to a query builder.
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterConsentNotPulledOrBmsYNYes();
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#getDataDescription()
   */
  public String getDataDescription() {
    return "not pulled or BMS is yes";
  }
}
