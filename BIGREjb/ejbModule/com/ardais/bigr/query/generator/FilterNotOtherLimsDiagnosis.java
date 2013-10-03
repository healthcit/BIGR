package com.ardais.bigr.query.generator;

import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNoParameters;
import com.ardais.bigr.query.filters.InitializableFromFilter;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FilterNotOtherLimsDiagnosis extends FilterNoParameters {

    /**
     * Constructor for FilterNotOtherDdcDiagnosis.
     * @param key
     */
    public FilterNotOtherLimsDiagnosis() {
        super(FilterConstants.KEY_NOT_OTHER_LIMS_DIAGNOSIS);
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
        qb.addFilterLimsDiagnosisNot(FormLogic.OTHER_DX);
    }

}
