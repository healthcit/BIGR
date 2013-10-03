package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Implicit filter used by BtxPerformerSampleSelection to restrict samples to only those that
 * are not pulled or are BMS.  See MR6723 for details
 */
public class FilterNotPulledOrBmsYNYes extends FilterNoParameters {

  /**
   * Constructor for FilterNotPulledOrBmsYNYes.
   */
  public FilterNotPulledOrBmsYNYes() {
    super(FilterConstants.KEY_NOT_PULLED_OR_BMS_YN_YES);
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) queryBuilder;
    qb.addFilterNotPulledOrBmsYNYes();
  }
}
