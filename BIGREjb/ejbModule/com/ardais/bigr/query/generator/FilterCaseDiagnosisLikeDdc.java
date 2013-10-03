package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringLike;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter for case diagnosis matching a pattern.
 */
public class FilterCaseDiagnosisLikeDdc extends FilterStringLike {

    /**
     * Constructor for FilterCaseDiagnosisLikeDdc.
     * @param pattern   A pattern to match, using * and ? as wildcards.
     */
    public FilterCaseDiagnosisLikeDdc(String pattern) {
        super(FilterConstants.KEY_DDCCASEDIAGNOSISLIKE, pattern);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Case Diagnosis (DDC)";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void privateAddToProductSummaryQueryBuilder(ProductSummaryQueryBuilder qb) {
        qb.addFilterDdcDiagnosisLike(getPatternForDisplay(), getOrGroupCode());        
    }

}
