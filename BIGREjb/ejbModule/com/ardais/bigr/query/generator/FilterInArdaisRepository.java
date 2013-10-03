package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit results to those samples that are not currently involved in a project.
 */
public class FilterInArdaisRepository extends FilterNoParameters {

  /**
   * Constructor for FilterInArdaisRepository.
   */
  public FilterInArdaisRepository() {
    super(FilterConstants.KEY_IN_ARDAIS_REPOSITORY);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * This method causes this filter to be added to a query builder.
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterInArdaisRepository();
  }
}
