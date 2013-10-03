package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 */
public class FilterGenreleased extends FilterNoParameters {

	/**
	 */
	public FilterGenreleased() {
		super(FilterConstants.KEY_GENRELEASED);
	}

	/* (non-Javadoc)
	 * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
	 */
	public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterSampleGenreleased();
	}
}
