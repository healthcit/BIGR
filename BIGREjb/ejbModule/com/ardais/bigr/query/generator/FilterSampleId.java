package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to get samples with particular ids
 */
public class FilterSampleId extends FilterStringsEqual {
    
    /**
     * Constructor for FilterSampleId.
     * @param filterValues   Sample ids
     */
    public FilterSampleId(String[] filterValues) {
        super(FilterConstants.KEY_TISSUEID, filterValues);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Sample ID";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        qb.addFilterIDsEqual(getFilterValues(), getOrGroupCode());        
    }

    public boolean isEmpty() {
      return getFilterValues().length == 0;
    }
}
