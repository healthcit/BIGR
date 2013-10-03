package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterPercentRange;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit to samples with a Cellular Stroma composition in a percentage range.
 */
public class FilterCompositionCellularStroma extends FilterPercentRange {

  public FilterCompositionCellularStroma(Integer min, Integer max) {
    super(FilterConstants.KEY_COMP_CS, min, max);
  }

  public FilterCompositionCellularStroma(String min, String max) {
    super(FilterConstants.KEY_COMP_CS, min, max);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "% Cellular Stroma (TCS) Composition";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterCompositionCellularStroma(getMin().toString(), getMax().toString());
  }
}
