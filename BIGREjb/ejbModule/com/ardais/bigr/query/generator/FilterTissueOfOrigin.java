package com.ardais.bigr.query.generator;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqualWithDescriptions;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filters for samples Originating from a list of Tissue codes.
 */
public class FilterTissueOfOrigin extends FilterStringsEqualWithDescriptions {

    /**
     * Constructor 
     * @param filterValues  tissue codes to filter for (internal codes from ORMMasterData)
     */
    public FilterTissueOfOrigin(String[] filterValues, String[] labels) {
        super(FilterConstants.KEY_TISSUEORIGIN, filterValues, labels);
    }

    protected String remap(String s) {
        return BigrGbossData.getTissueDescription(s);
    }

    protected String displayName() {
        return "Tissue of Origin of Diagnosis";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        qb.addFilterTissueOrigin(getFilterValues(), getOrGroupCode());
    }
}
