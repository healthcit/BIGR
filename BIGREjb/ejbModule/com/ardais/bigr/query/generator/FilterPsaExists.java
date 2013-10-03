package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to include samples for which a PSA test was performed.
 */
public class FilterPsaExists extends FilterNoParameters {

  public FilterPsaExists() {
    super(FilterConstants.KEY_PSA_EXISTS);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterPsaExists();
  }

  public String toString() {
    return "Prostate Specific Antigen, Free Serum was performed";
  }

}
