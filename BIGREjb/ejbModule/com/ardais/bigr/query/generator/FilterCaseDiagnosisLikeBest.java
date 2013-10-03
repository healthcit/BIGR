package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringLike;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 *  Filter for case diagnosis matching a pattern.
 */
public class FilterCaseDiagnosisLikeBest extends FilterStringLike {

    /**
     * Constructor for FilterCaseDiagnosisLikeBest.
     * @param pattern   A pattern to match, using * and ? as wildcards.
     */
    public FilterCaseDiagnosisLikeBest(String pattern) {
        super(FilterConstants.KEY_BESTCASEDIAGNOSISLIKE, pattern);
    }

    protected String displayName() {
        return "Case Diagnosis";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void privateAddToProductSummaryQueryBuilder(ProductSummaryQueryBuilder qb) {
        qb.addFilterBestCaseDiagnosisLike(getPatternForDisplay(), getOrGroupCode());
    }

}
