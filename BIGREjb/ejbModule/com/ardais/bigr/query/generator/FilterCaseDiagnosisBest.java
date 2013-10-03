package com.ardais.bigr.query.generator;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringsEqualWithDescriptions;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * Filter to restrict samples to those with certain "Best" Case Diagnoses.
 * Best is ILTDS if it exists, and DDC if it does not.
 */
public class FilterCaseDiagnosisBest extends FilterStringsEqualWithDescriptions {
    
    /**
     * Constructor for FilterCaseDiagnosisBestOf
     * @param diagnosisCodes   the internal codes of the diagnoses to filter for
     */
    public FilterCaseDiagnosisBest(String[] diagnosisCodes, String[] labels) {
        super(FilterConstants.KEY_BESTCASEDIAGNOSIS, diagnosisCodes, labels);
    }

    protected String remap(String s) {
        return BigrGbossData.getDiagnosisDescription(s);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Case Diagnosis";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void addToQueryBuilder(InitializableFromFilter initFF) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) initFF;
        qb.addFilterBestCaseDiagnosis(getFilterValues(), getOrGroupCode());
    }

}
