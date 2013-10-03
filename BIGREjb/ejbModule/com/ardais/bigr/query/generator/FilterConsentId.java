package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to get samples with a set of case ids.
 */
public class FilterConsentId extends FilterStringsEqual {
    
    /**
     * Constructor for FilterSampleId.
     * @param filterValues   case ids to filter for
     */
    public FilterConsentId(String[] filterValues) {
        super(FilterConstants.KEY_CASEID, filterValues);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Case ID";
    }


    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        qb.addFilterConsentEqual(getFilterValues(), getOrGroupCode());
    }
}
