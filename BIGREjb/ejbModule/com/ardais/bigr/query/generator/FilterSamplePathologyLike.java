package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringLike;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter for case diagnosis matching a pattern.
 */
public class FilterSamplePathologyLike extends FilterStringLike {

    /**
     * Constructor 
     * @param pattern   A pattern to match, using * and ? as wildcards.
     */
    public FilterSamplePathologyLike(String pattern) {
        super(FilterConstants.KEY_SAMPLEPATHOLOGYLIKE, pattern);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Sample Pathology";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void privateAddToProductSummaryQueryBuilder(ProductSummaryQueryBuilder qb) {
        qb.addFilterLimsDiagnosisLike(getPatternForDisplay(), getOrGroupCode());
        
    }

}
