package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringLike;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter for Tissue of Origin like a pattern.
 */
public class FilterTissueOriginLike extends FilterStringLike {

    /**
     * Constructor 
     * @param pattern   A pattern to match, using * and ? as wildcards.
     */
    public FilterTissueOriginLike(String pattern) {
        super(FilterConstants.KEY_TISSUEORIGINLIKE, pattern);
    }

    
    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Tissue of Origin of Diagnosis";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void privateAddToProductSummaryQueryBuilder(ProductSummaryQueryBuilder qb) {
        qb.addFilterTissueOriginLike(getPatternForDisplay(), getOrGroupCode());
    }

}
