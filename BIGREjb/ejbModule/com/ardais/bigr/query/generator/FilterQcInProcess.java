package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 */
public class FilterQcInProcess extends FilterNoParameters {

    /**
     * Constructor for FilterQcInProcess.
     */
    public FilterQcInProcess() {
        super(FilterConstants.KEY_QC_INPROCESS);
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     * This method causes this filter to be added to a query builder.
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) queryBuilder;
        qb.addFilterSampleQcInProcess();
    }
    
    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    public String toString() {
      return "Currently on order for PV";
    }

}
