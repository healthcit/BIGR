package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterPercentRange;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit to samples with a Normal composition in a percentage range.
 */
public class FilterCompositionNormal extends FilterPercentRange {

    public FilterCompositionNormal(Integer min, Integer max) {
        super(FilterConstants.KEY_COMP_NORMAL, min, max);
    }

    public FilterCompositionNormal(String min, String max) {
        super(FilterConstants.KEY_COMP_NORMAL, min, max);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "% Normal (NRM) Composition";
    }

    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
      ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
      qb.addFilterCompositionNormal(getMin().toString(), getMax().toString());
    }
}
