package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringLike;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter for case diagnosis matching a pattern.
 */
public class FilterCaseDiagnosisLikeIltds extends FilterStringLike {

    /**
     * Constructor for FilterCaseDiagnosisLikeIltds.
     * @param pattern   A pattern to match, using * and ? as wildcards.
     */
    public FilterCaseDiagnosisLikeIltds(String pattern) {
        super(FilterConstants.KEY_ILTDSCASEDIAGNOSISLIKE, pattern);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Case Diagnosis (ILTDS)";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void privateAddToProductSummaryQueryBuilder(ProductSummaryQueryBuilder qb) {
        qb.addFilterIltdsDiagnosisLike(getPatternForDisplay(), getOrGroupCode());
    }

}
