package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit to those samples that are not pulled.
 */
public class FilterNotPulled extends FilterNoParameters {

    /**
     * Constructor for FilterNotPulled.
     */
    public FilterNotPulled() {
        super(FilterConstants.KEY_NOT_PULLED);
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     * This method causes this filter to be added to a query builder.
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) queryBuilder;
        qb.addFilterSampleNotPulled();
    }
}
