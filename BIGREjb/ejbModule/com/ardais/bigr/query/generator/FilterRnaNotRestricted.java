package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit RNA to those samples that are not restricted.
 * This checks the sample(s) the RNA was derived from to see if it (or any 
 * of them) were restricted.
 */
public class FilterRnaNotRestricted extends FilterNoParameters {

    /**
     * Constructor for FilterRnaNotRestricted.
     */
    public FilterRnaNotRestricted() {
        super(FilterConstants.KEY_RNA_NOT_RESTRICTED);
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     * This method causes this filter to be added to a query builder.
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        RnaSummaryQueryBuilder rnaSQB = (RnaSummaryQueryBuilder) queryBuilder;
        rnaSQB.addFilterRnaNotRestricted();
    }
}
