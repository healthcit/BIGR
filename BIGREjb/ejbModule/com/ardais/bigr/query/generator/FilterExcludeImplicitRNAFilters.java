package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 */
public class FilterExcludeImplicitRNAFilters extends FilterNoParameters {

  /**
   */
  public FilterExcludeImplicitRNAFilters() {
    super(FilterConstants.KEY_EXCLUDE_IMPLICIT_RNA_FILTERS);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "Show RNA of all quality";
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#toString()
   */
  public String toString() {
    return displayName();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   * Because this filter is used to prevent the regularly included implicit RNA filters, it
   * doesn't actually add anything to the query builder so just return
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    return;
  }

}
