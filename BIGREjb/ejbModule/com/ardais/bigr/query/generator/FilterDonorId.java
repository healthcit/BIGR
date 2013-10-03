package com.ardais.bigr.query.generator;

import java.util.Collections;
import java.util.Map;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to get samples from a set of donors.
 */
public class FilterDonorId extends FilterStringsEqual {
    
    /**
     * Constructor for FilterSampleId.
     * @param filterValues
     */
    public FilterDonorId(String[] filterValues) {
        super(FilterConstants.KEY_DONORID, filterValues);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Donor ID";
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#codeDisplayMap()
     */
    protected Map codeDisplayMap() {
        return Collections.EMPTY_MAP; // print raw id values, without remapping
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        qb.addFilterDonorEqual(getFilterValues(), getOrGroupCode());
    }

}
