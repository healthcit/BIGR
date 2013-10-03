package com.ardais.bigr.query.generator;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterDateRange;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filters for gender (M, F)
 */
public class FilterDateOfCollection extends FilterDateRange {

  public FilterDateOfCollection(String start, String end) {
    super(FilterConstants.KEY_SAMPLE_DATE_COLLECTED, start, end);
  }

  protected String displayName() {
    return "First Arrived at Supplier Biorepository";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    String s = getStartForQuery();
    String e = getEndForQuery();
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterSampleDateOfCollection(new String[] {s, e});
  }
}
