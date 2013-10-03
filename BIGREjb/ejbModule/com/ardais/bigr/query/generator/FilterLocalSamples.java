/*
 * Created on Feb 25, 2004
 */
package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringEquals;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to determine that a sample is stored at the specified location.
 */
public class FilterLocalSamples extends FilterStringEquals {
  /**
   * Constructor for FilterLogicalRepository.
   * @param location a LOCATION_ADDRESS_ID.
   */
  public FilterLocalSamples(String location) {
    super(FilterConstants.KEY_LOCAL_SAMPLES_ONLY, location);
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * This method causes this filter to be added to a query builder.
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) queryBuilder;
    qb.addFilterLocalSamples(getFilterValue());
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "Local";
  }

  // Override superclass implement to use " to " instead of " is ".
  // E.g. "Local to DUKE" not "Local is DUKE".
  public String toString() {
    StringBuffer sb = new StringBuffer(32);
    sb.append(displayName());
    sb.append(" to ");
    String displayVal = (String) codeDisplayMap().get(getFilterValue());
    if (displayVal == null) {
      displayVal = getFilterValue();
    }
    sb.append(displayVal);
    return sb.toString();
  }
}
