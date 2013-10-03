package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Filters for samples with one of a list of Lymph Node Stage codes.
 */
public class FilterStageLymphNode extends FilterStringsEqual {

  /**
   * Constructor for FilterStageLymphNode.
   * @param filterValues  stages to filter for (internal codes from ORMMasterData)
   */
  public FilterStageLymphNode(String[] filterValues) {
    super(FilterConstants.KEY_LYMPHNODESTAGE, filterValues);
  }

  protected String remap(String s) {
    return GbossFactory.getInstance().getDescription(s);
  }

  protected String displayName() {
    return "Lymph Node Stage";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterLymphNodeStage(getFilterValues());
  }
}
