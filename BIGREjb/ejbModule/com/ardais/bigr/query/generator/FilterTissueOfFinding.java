package com.ardais.bigr.query.generator;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqualWithDescriptions;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filters for samples found in a Tissue with one of a list of codes.
 */
public class FilterTissueOfFinding extends FilterStringsEqualWithDescriptions {

    /**
     * Constructor 
     * @param filterValues  tissue codes to filter for (internal codes from ORMMasterData)
     */
    public FilterTissueOfFinding(String[] filterValues, String[] labels) {
        super(FilterConstants.KEY_TISSUEFINDING, filterValues, labels);
    }

    protected String remap(String s) {
        return BigrGbossData.getTissueDescription(s);
    }

    protected String displayName() {
        return "Tissue of Finding";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        qb.addFilterTissueFinding(getFilterValues(), getOrGroupCode());
    }

}
