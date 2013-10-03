package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FilterRnaAmountAvailable extends FilterNoParameters {

    /**
     * Constructor for FilterRnaAmountAvailable.
     * @param key
     * @param filterValue
     */
    public FilterRnaAmountAvailable() {
        super(FilterConstants.KEY_RNA_AMOUNT_AVAILABLE);
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
        rnaSQB.addFilterRnaAmountAvailable();
    }
}
