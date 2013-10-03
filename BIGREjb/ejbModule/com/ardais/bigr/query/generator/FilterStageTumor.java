package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Filters for samples with one of a list of Tumor Stage codes.
 */
public class FilterStageTumor extends FilterStringsEqual {

  /**
   * Constructor for FilterStageLymphNode.
   * @param filterValues  stages to filter for (internal codes from ORMMasterData)
   */
  public FilterStageTumor(String[] filterValues) {
    super(FilterConstants.KEY_TUMORSTAGE, filterValues);
  }

  protected String remap(String s) {
    return GbossFactory.getInstance().getDescription(s);
  }

  protected String displayName() {
    return "Tumor Stage";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterTumorStage(getFilterValues());
  }
}
