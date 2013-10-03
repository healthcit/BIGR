package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsNotEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to exclude items that have a list of inventory statuses
 */
public class FilterExcludeInventoryStatusOrBmsYNYes extends FilterStringsNotEqual {

  /**
   * Constructor for FilterExcludeInventoryStatus.
   * @param filterValues
   */
  public FilterExcludeInventoryStatusOrBmsYNYes(String[] excludedValues) {
    super(FilterConstants.KEY_INVENTORY_STATUS_OR_BMS_YN_YES, excludedValues);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterSampleInvStatusNotOrBmsYNYes(getFilterValues());
  }
}
