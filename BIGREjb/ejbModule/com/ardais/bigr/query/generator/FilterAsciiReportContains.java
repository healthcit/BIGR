package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringContains;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.DbUtils;

/**
 * @author sislam
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FilterAsciiReportContains extends FilterStringContains {

  /**
   * Constructor for FilterAsciiReportContains.
   * @param key
   * @param phrase
   */
  public FilterAsciiReportContains(String pattern) {
    super(FilterConstants.KEY_ASCIIREPORTCONTAINS, pattern);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
      return "Ascii Report";
  }

  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterAsciiReportContains(DbUtils.prepareIntermediaQueryString(getPhrase()));
  }

}
