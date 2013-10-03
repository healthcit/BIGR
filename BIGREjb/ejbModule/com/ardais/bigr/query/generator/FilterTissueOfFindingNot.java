package com.ardais.bigr.query.generator;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.FilterStringsEqualWithDescriptions;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filters for samples found in a Tissue that is not one of a list of codes.
 */
public class FilterTissueOfFindingNot extends FilterStringsEqualWithDescriptions {

  /**
   * Constructor 
   * @param filterValues  tissue codes to filter for (internal codes from ORMMasterData)
   */
  public FilterTissueOfFindingNot(String[] filterValues, String[] labels) {
    super(FilterConstants.KEY_TISSUEFINDINGNOT, filterValues, labels);
  }

  protected String remap(String s) {
    return BigrGbossData.getTissueDescription(s);
  }

  protected String displayName() {
    return "Tissue of Finding";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterTissueFindingNot(getFilterValues());
  }
  
  /**
  * Return a human-readable snippet, suitable for inclusion in a summary.
  * @see ProductFilters.toString()
  */
  public String toString() {
    return FilterStringsEqual.valsAsDisplayString(displayName(), remap(getFilterValues()), false);
  }
}
