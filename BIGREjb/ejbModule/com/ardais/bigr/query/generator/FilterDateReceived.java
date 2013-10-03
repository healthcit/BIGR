package com.ardais.bigr.query.generator;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterDateRange;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filters for gender (M, F)
 */
public class FilterDateReceived extends FilterDateRange {

  public FilterDateReceived(String start, String end) {
    super(FilterConstants.KEY_SAMPLE_DATE_RECEIVED, start, end);
  }

  protected String displayName() {
    return "First Arrived at Supplier Biorepository";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    String s = getStartForQuery();
    String e = getEndForQuery();
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterSampleDateReceived(new String[] {s, e});
  }
}
