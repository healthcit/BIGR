package com.ardais.bigr.query.generator;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqualWithDescriptions;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to restrict samples to those with certain ILTDS Case Diagnoses.
 */
public class FilterCaseDiagnosisIltds extends FilterStringsEqualWithDescriptions {

    /**
     * Constructor for FilterCaseDiagnosisIltds.
     * @param diagnosisCodes   the internal codes of the diagnoses to filter for
     */
    public FilterCaseDiagnosisIltds(String[] diagnosisCodes, String[] labels) {
        super(FilterConstants.KEY_ILTDSCASEDIAGNOSIS, diagnosisCodes, labels);
    }

    protected String remap(String s) {
        return BigrGbossData.getDiagnosisDescription(s);
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
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        qb.addFilterIltdsDiagnosis(getFilterValues(), getOrGroupCode());

    }

}
