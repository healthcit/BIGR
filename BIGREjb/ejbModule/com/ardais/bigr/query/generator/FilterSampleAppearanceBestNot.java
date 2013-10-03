package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsNotEqual;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to limit results to those samples not having specified gross appearance values.
 */
public class FilterSampleAppearanceBestNot extends FilterStringsNotEqual {
    
    /**
     * Constructor for FilterSampleAppearanceBestNot.
     */
    public FilterSampleAppearanceBestNot(String[] excludeAppearances) {
        super(FilterConstants.KEY_SAMPLE_APPEARANCE_BEST_NOT, excludeAppearances);
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     * This method causes this filter to be added to a query builder.
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        qb.addFilterSampleAppearanceBestNot(getFilterValues());
    }
}
