package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Filters for samples with one of a list of Distant Metastasis Stage codes.
 */
public class FilterStageMetastasis extends FilterStringsEqual {

  /**
   * Constructor 
   * @param filterValues  metastasis codes to filter for (internal codes from ORMMasterData)
   */
  public FilterStageMetastasis(String[] filterValues) {
    super(FilterConstants.KEY_DISTANTMETASTASIS, filterValues);
  }

  protected String remap(String s) {
    return GbossFactory.getInstance().getDescription(s);
  }

  protected String displayName() {
    return "Distant Metastasis";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterDistantMetastasis(getFilterValues());
  }
}
