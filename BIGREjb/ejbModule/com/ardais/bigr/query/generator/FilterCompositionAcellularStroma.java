package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterPercentRange;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit to samples with a Hypocellular or Acellular Stroma composition in a percentage range.
 */
public class FilterCompositionAcellularStroma extends FilterPercentRange {

  public FilterCompositionAcellularStroma(Integer min, Integer max) {
    super(FilterConstants.KEY_COMP_ACS, min, max);
  }

  public FilterCompositionAcellularStroma(String min, String max) {
    super(FilterConstants.KEY_COMP_ACS, min, max);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "% Hypo/Acellular Stroma (TAS) Composition";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterCompositionAcellularStroma(getMin().toString(), getMax().toString());
  }

}
