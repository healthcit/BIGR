package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 */
public class FilterGenreleasedOrNotNullAndBmsYNYes extends FilterNoParameters {

	/**
	 */
	public FilterGenreleasedOrNotNullAndBmsYNYes() {
		super(FilterConstants.KEY_GENRELEASED_OR_NOT_NULL_AND_BMS_YN_YES);
	}

	/* (non-Javadoc)
	 * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
	 */
	public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterSampleGenreleasedOrNotNullAndBmsYNYes();
	}
}
