package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterPercentRange;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit to samples with a Normal composition in a percentage range.
 */
public class FilterCompositionTumor extends FilterPercentRange {

  public FilterCompositionTumor(Integer min, Integer max) {
    super(FilterConstants.KEY_COMP_TUMOR, min, max);
  }

  public FilterCompositionTumor(String min, String max) {
    super(FilterConstants.KEY_COMP_TUMOR, min, max);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "% Tumor (TMR) Composition";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterCompositionTumor(getMin().toString(), getMax().toString());
  }
}
