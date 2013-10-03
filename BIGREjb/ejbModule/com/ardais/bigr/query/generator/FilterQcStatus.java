package com.ardais.bigr.query.generator;

import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.Constants;

/**
 * Filter to limit results to certain QC statuses.
 */
public class FilterQcStatus extends FilterStringsEqual {
 
    private static Map _codeDisplay;
    static {
      _codeDisplay = new HashMap();
      _codeDisplay.put(Constants.QC_NOTVER, "Not Pathology Verified");
      _codeDisplay.put(Constants.QC_VER_ONLY, "Pathology Verified but not Released");
      _codeDisplay.put(Constants.QC_REL_ONLY, "Released but not Posted");
      _codeDisplay.put(Constants.QC_POST, "Posted");
      _codeDisplay.put(Constants.QC_PULL, "Pulled");
    }
      
    /**
     * Constructor for FilterQcStatus.
     */
    public FilterQcStatus(String[] statuses) {
        super(FilterConstants.KEY_QC_STATUS, statuses);
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     * This method causes this filter to be added to a query builder.
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) queryBuilder;
        qb.addFilterSampleQcStatuses(getFilterValues());
    }
  
  /**
   * @see com.ardais.bigr.query.filters.Filter#codeDisplayMap()
   */
  protected Map codeDisplayMap() {
    return _codeDisplay;
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "QC Status";
  }

}
