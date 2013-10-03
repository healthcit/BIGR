package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterBoolean;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter for samples that have been received at Ardais.
 */
public class FilterReceivedAtArdais extends FilterBoolean {

    public FilterReceivedAtArdais(Boolean bool) {
      this(bool.booleanValue());
    }
    
    public FilterReceivedAtArdais(boolean bool) {
        super(FilterConstants.KEY_RECEIVED_AT_ARDAIS, bool);
    }

    public FilterReceivedAtArdais(String boolString) {
        super(FilterConstants.KEY_RECEIVED_AT_ARDAIS, boolString);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Is Received at Ardais";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        qb.addFilterSampleReceivedAtArdais();
    }

}
