package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterUserAccountInfo;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 *  Filter the RNA so that samples retrieved are the restricted 
 *  samples for use by a particular Donor Institution.
 * 
 * Operates by checking all Samples the RNA was derived from (usually just
 * one) and considers the RNA restircted if any samples were.
 */
public class FilterRnaRestrictedForDi extends FilterUserAccountInfo {

    /**
     * Constructor for FilterRnaRestrictedForDi.
     * @param accountName  The name of the donor account to filter by
     */
    public FilterRnaRestrictedForDi(String accountName) {
        super(FilterConstants.KEY_RESTRICTED_RNA_FOR_DI, null, accountName);
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     * This method causes this filter to be added to a query builder.
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        RnaSummaryQueryBuilder rnaSQB = (RnaSummaryQueryBuilder) queryBuilder;
        rnaSQB.addFilterRnaRestricted(getAccount());
    }
}
