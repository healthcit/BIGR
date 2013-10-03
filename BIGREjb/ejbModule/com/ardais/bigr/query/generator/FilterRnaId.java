package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to get samples with particular ids
 */
public class FilterRnaId extends FilterStringsEqual {
    
    /**
     * Constructor for FilterSampleId.
     * @param filterValues   Sample ids
     */
    public FilterRnaId(String[] filterValues) {
        super(FilterConstants.KEY_RNAID, filterValues);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "RNA IDs";
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
