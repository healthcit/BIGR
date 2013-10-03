package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringContains;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.DbUtils;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FilterPathologyVerificationNotesContains extends FilterStringContains {

    /**
     * Constructor for PathologyVerificationNotesContains.
     * @param key
     * @param pattern
     */
    public FilterPathologyVerificationNotesContains(String pattern) {
        super(FilterConstants.KEY_PVNOTESCONTAINS, pattern);
    }

    /**
     * @see com.ardais.bigr.query.filters.Filter#displayName()
     */
    protected String displayName() {
        return "Pathology Verification Notes";
    }

    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     */
    public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
        TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) queryBuilder;
        qb.addFilterPvNotesContains(DbUtils.prepareIntermediaQueryString(getPhrase()));
    }

}
