package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Filters for samples with one of a list of Hisologic Nuclear Grade (HNG) codes.
 */
public class FilterHng extends FilterStringsEqual {

  /**
   * Constructor 
   * @param filterValues  hng codes to filter for (internal codes from ORMMasterData)
   */
  public FilterHng(String[] filterValues) {
    super(FilterConstants.KEY_HNG, filterValues);
  }

  protected String remap(String s) {
    return GbossFactory.getInstance().getDescription(s);
  }

  protected String displayName() {
    return "Histologic / Nuclear Grade";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterHistologicNuclearGrade(getFilterValues());
  }
}
