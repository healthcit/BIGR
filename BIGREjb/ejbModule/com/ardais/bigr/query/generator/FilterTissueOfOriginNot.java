package com.ardais.bigr.query.generator;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.FilterStringsEqualWithDescriptions;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filters for samples Originating not from a list of Tissue codes.
 */
public class FilterTissueOfOriginNot extends FilterStringsEqualWithDescriptions {

  /**
   * Constructor 
   * @param filterValues  tissue codes to filter for (internal codes from ORMMasterData)
   */
  public FilterTissueOfOriginNot(String[] filterValues, String[] labels) {
    super(FilterConstants.KEY_TISSUEORIGINNOT, filterValues, labels);
  }

  protected String remap(String s) {
    return BigrGbossData.getTissueDescription(s);
  }

  protected String displayName() {
    return "Tissue of Origin of Diagnosis";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterTissueOriginNot(getFilterValues());
  }

  /**
  * Return a human-readable snippet, suitable for inclusion in a summary.
  * @see ProductFilters.toString()
  */
  public String toString() {
    return FilterStringsEqual.valsAsDisplayString(displayName(), remap(getFilterValues()), false);
  }
}
