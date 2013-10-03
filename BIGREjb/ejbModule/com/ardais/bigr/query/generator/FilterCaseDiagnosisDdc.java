package com.ardais.bigr.query.generator;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqualWithDescriptions;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to restrict samples to those with certain DDC Case Diagnoses.
 */
public class FilterCaseDiagnosisDdc extends FilterStringsEqualWithDescriptions {

    /**
     * Constructor for FilterDdcCaseDiagnosis.
     * @param diagnosisCodes   the internal codes of the diagnoses to filter for
     */
    public FilterCaseDiagnosisDdc(String[] diagnosisCodes, String[] dxLabels) {
        super(FilterConstants.KEY_DDCCASEDIAGNOSIS, diagnosisCodes, dxLabels);
    }

    protected String remap(String s) {
        return BigrGbossData.getDiagnosisDescription(s);
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
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        qb.addFilterDdcDiagnosis(getFilterValues(), getOrGroupCode());
    }

}
