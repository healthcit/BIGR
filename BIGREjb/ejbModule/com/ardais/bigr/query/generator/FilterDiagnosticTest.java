package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.Constants;
import com.gulfstreambio.gboss.GbossFactory;

public class FilterDiagnosticTest extends FilterStringsEqual {

  public FilterDiagnosticTest(String[] tests) {
    super(FilterConstants.KEY_DIAGNOSTIC_TEST, tests);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterDiagnosticTest(getFilterValues(), getOrGroupCode());
  }

  /**
   * Return a human-readable snippet, suitable for inclusion in a summary.
   * @see ProductFilters.toString()
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();
    String[] values = getFilterValues();
    
    boolean first = true;
    for (int i=0; i<values.length; i++) {
      if (!first) {
        buf.append("\n"); 
        buf.append("OR ");
      }
      buf.append(GbossFactory.getInstance().getDescription(values[i]));
      buf.append(" is ");
      buf.append(Constants.PERFORMED);
      first = false;
      
    }
    return buf.toString();
  }

}
