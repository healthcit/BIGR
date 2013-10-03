package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter the RNA results to show only samples with one of a list of status codes
 */
public class FilterRnaStatusOrBmsYNYes extends FilterStringsEqual {

  /**
   * Constructor for FilterRnaStatusOrBmsYNYes.
  */
  public FilterRnaStatusOrBmsYNYes(String[] statuses) {
    super(FilterConstants.KEY_RNA_STATUS_OR_BMS_YN_YES, statuses);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * This method causes this filter to be added to a query builder.
   */
  public void addToQueryBuilder(InitializableFromFilter initFromFilt) {
    RnaSummaryQueryBuilder qb = (RnaSummaryQueryBuilder) initFromFilt;
    qb.addFilterRnaStatusOrBmsYNYes(getFilterValues());
  }
}
