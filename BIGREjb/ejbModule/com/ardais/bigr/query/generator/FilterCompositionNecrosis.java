package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterPercentRange;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit to samples with a Necrosis composition in a percentage range.
 */
public class FilterCompositionNecrosis extends FilterPercentRange {

  public FilterCompositionNecrosis(Integer min, Integer max) {
    super(FilterConstants.KEY_COMP_NECROSIS, min, max);
  }

  public FilterCompositionNecrosis(String min, String max) {
    super(FilterConstants.KEY_COMP_NECROSIS, min, max);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "% Necrosis (NEC) Composition";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterCompositionNecrosis(getMin().toString(), getMax().toString());
  }
}
