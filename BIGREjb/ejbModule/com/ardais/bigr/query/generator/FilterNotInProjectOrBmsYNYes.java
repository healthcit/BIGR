package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit results to those samples that are not currently involved in a project.
 */
public class FilterNotInProjectOrBmsYNYes extends FilterNoParameters {

  /**
   * Constructor for FilterNotInProjectOrBmsYNYes.
   */
  public FilterNotInProjectOrBmsYNYes() {
    super(FilterConstants.KEY_NOT_IN_PROJECT_OR_BMS_YN_YES);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * This method causes this filter to be added to a query builder.
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) queryBuilder;
    qb.addFilterSampleNotInProjectOrBmsYNYes();
  }
}