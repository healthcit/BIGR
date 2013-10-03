package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 */
public class FilterQcInProcessNot extends FilterNoParameters {

    /**
     * Constructor for FilterQcInProcessNot.
     */
    public FilterQcInProcessNot() {
        super(FilterConstants.KEY_QC_INPROCESS_NOT);
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     * This method causes this filter to be added to a query builder.
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) queryBuilder;
        qb.addFilterSampleQcInProcessNot();
    }
    
    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    public String toString() {
      return "NOT on order for PV";
    }

}
