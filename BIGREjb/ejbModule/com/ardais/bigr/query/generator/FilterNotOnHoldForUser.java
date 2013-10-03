package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterUserAccountInfo;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * @author dfeldman
 *
 * Only allow items that are not already on hold for this user.  Useful for RNA, which is not
 * excluded altogether when it is on hold, since only a certain quantity is put on hold.
 */
public class FilterNotOnHoldForUser extends FilterUserAccountInfo {

    /**
     * Constructor for FilterNotOnHoldForUser.
     */
    public FilterNotOnHoldForUser(String userId) {
        super(FilterConstants.KEY_NOT_ON_HOLD_FOR_USER, userId, null);
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     * This method causes this filter to be added to a query builder.
     */
    // note this uses a direct callback, rather than going into a generic case statement on the query builder
    // because this is not a generic filter who's logic depends on the key
    // this also demands (has a parameter type of) an RnaSummaryQueryBuilder
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        RnaSummaryQueryBuilder rnaSQB = (RnaSummaryQueryBuilder) queryBuilder;
        rnaSQB.addFilterNotOnHoldForUser(getUserId());
    }
}
