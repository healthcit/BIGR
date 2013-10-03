package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to exclude samples whose consent has been pulled.
 */
public class FilterSalesStatusExists extends FilterNoParameters {

  /**
   * Constructor for FilterSalesStatusExists.
   */
  public FilterSalesStatusExists() {
      super(FilterConstants.KEY_SALES_STATUS_EXISTS);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * This method causes this filter to be added to a query builder.
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
      ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
      qb.addFilterSampleSalesStatusExists();
  }
  
  /**
   * @see com.ardais.bigr.query.filters.Filter#getDataDescription()
   */
  public String getDataDescription() {
      return "Sales Status is not null";
  }

}
