package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsNotEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to exclude items that have a list of inventory statuses
 */
public class FilterExcludeInventoryStatus extends FilterStringsNotEqual {

  /**
   * Constructor for FilterExcludeInventoryStatus.
   * @param filterValues
   */
  public FilterExcludeInventoryStatus(String[] excludedValues) {
    super(FilterConstants.KEY_INVENTORY_STATUS, excludedValues);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterSampleInvStatusNot(getFilterValues());
  }
}
