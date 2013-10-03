package com.ardais.bigr.query.generator;

import java.util.Iterator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterRepeating;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.query.filters.RepeatingFilterData;
import com.ardais.bigr.query.filters.RepeatingSingleData;
import com.ardais.bigr.util.Constants;

public class FilterDiagnosticTestResult extends FilterRepeating {

  public FilterDiagnosticTestResult(RepeatingFilterData repeatingValues) {
    super(FilterConstants.KEY_DIAGNOSTIC_TEST_RESULT, repeatingValues);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterDiagnosticTestResult(getRepeatingValues(), getOrGroupCode());
  }

  /**
   * Return a human-readable snippet, suitable for inclusion in a summary.
   * @see ProductFilters.toString()
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();
    
    Iterator iter = getRepeatingValues().getValues().iterator();
    boolean first = true;
    while(iter.hasNext()) {
      RepeatingSingleData singleData = (RepeatingSingleData) iter.next();
      if (!singleData.isEmpty()) {
        if (!first) {
          buf.append("\n"); 
          buf.append("OR ");
        }
        buf.append(singleData.getFilterName());
        buf.append(" is ");
        String[] selectedValues = singleData.getSelectedValues();
        if (selectedValues.length > 1)
            buf.append("one of:\n      "); // break and indent
        
        for (int i=0; i < selectedValues.length; i++) {
          if (i>0)
          buf.append("; ");
          if (Constants.PERFORMED.equalsIgnoreCase(selectedValues[i])) {
            buf.append(selectedValues[i]);
          }
          else {
            buf.append(remap(selectedValues[i]));
          }
        }
        first = false;
      }
    }
    return buf.toString();
  }

}
