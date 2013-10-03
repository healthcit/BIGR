package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringLike;
import com.ardais.bigr.query.filters.FilterStringNotLike;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter for Tissue of Finding not like a pattern.
 */
public class FilterTissueFindingNotLike extends FilterStringNotLike {

    /**
     * Constructor 
     * @param pattern   A pattern to match, using * and ? as wildcards.
     */
    public FilterTissueFindingNotLike(String pattern) {
        super(FilterConstants.KEY_TISSUEFINDINGLIKENOT, pattern);
    }

    
    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Site of Finding";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void privateAddToProductSummaryQueryBuilder(ProductSummaryQueryBuilder qb) {
        qb.addFilterTissueFindingNotLike(getPatternForDisplay());
    }

}
