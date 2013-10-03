package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringNotLike;

/**
 * Filter for Tissue of Origin not like a pattern.
 */
public class FilterTissueOriginNotLike extends FilterStringNotLike {

    /**
     * Constructor 
     * @param pattern   A pattern to match, using * and ? as wildcards.
     */
    public FilterTissueOriginNotLike(String pattern) {
        super(FilterConstants.KEY_TISSUEORIGINLIKENOT, pattern);
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
        qb.addFilterTissueOriginNotLike(getPatternForDisplay());
    }

}
