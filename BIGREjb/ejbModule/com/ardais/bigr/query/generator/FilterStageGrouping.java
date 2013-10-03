package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Filters for samples with one of a list of Stage Grouping codes.
 */
public class FilterStageGrouping extends FilterStringsEqual {

  /**
   * Constructor 
   * @param filterValues  stages to filter for (internal codes from ORMMasterData)
   */
  public FilterStageGrouping(String[] filterValues) {
    super(FilterConstants.KEY_STAGE, filterValues);
  }

  protected String remap(String s) {
    return GbossFactory.getInstance().getDescription(s);
  }

  protected String displayName() {
    return "Minimum Stage Grouping";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterStageGrouping(getFilterValues());
  }
}
