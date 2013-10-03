package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterPercentRange;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit to samples with a Lesion composition in a percentage range.
 */
public class FilterCompositionLesion extends FilterPercentRange {

  public FilterCompositionLesion(Integer min, Integer max) {
    super(FilterConstants.KEY_COMP_LESION, min, max);
  }

  public FilterCompositionLesion(String min, String max) {
    super(FilterConstants.KEY_COMP_LESION, min, max);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "% Lesion (LSN) Composition";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterCompositionLesion(getMin().toString(), getMax().toString());
  }
}
