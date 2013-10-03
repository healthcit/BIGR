package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter the tissue results to show only samples with one of a list of sales status codes.
 */
public class FilterSalesStatus extends FilterStringsEqual {

  public FilterSalesStatus(String[] statuses) {
    super(FilterConstants.KEY_SALES_STATUS, statuses);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * This method causes this filter to be added to a query builder.
   */
  public void addToQueryBuilder(InitializableFromFilter initFromFilt) {
    TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) initFromFilt;
    qb.addFilterSampleSalesStatus(getFilterValues());
  }
}
