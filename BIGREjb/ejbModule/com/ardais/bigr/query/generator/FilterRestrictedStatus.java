package com.ardais.bigr.query.generator;

import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.Filter;
import com.ardais.bigr.query.filters.FilterStringEquals;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.Constants;

/**
 * Filter to return samples with only certain restricted codes 
 * for a particular Donor Institution.
 */
public class FilterRestrictedStatus extends FilterStringEquals {
 
    static Map displayCodes = new HashMap();
    static {
        displayCodes.put(Constants.RESTRICTED_MIA, "All samples");
        displayCodes.put(Constants.RESTRICTED_MIR, "Restricted Only");
        displayCodes.put(Constants.RESTRICTED_MIU, "Unrestricted Only");
        displayCodes.put(Constants.RESTRICTED_MIUR, "MI Only");
        displayCodes.put(Constants.RESTRICTED_R, "Restricted Only");
        displayCodes.put(Constants.RESTRICTED_U, "Unrestricted Only");
    }


        private String _accountName;  // the name of the account to show restricted info for
    
    /**
     * Constructor for FilterRestrictedStatus.
     * @param key
     * @param statuses   the restricted status values to retreive samples for
     * @param accountName  the account that the restricted samples are for 
     */
    public FilterRestrictedStatus(String status, String accountName) {
        super(FilterConstants.KEY_RESTRICTED_FOR_DI, status);
        _accountName = accountName;
//        _status = status;
    }
    
    /**
     * Create a filter limiting results to a particular Restricted Status.
     * More information, about the user account, MUST be filled in later.
     * 
     * @param status   The restricted status to filter for.
     */
    public FilterRestrictedStatus(String status) {
        this(status, null);
    }

    public boolean hasAccount() {
        return _accountName != null;
    }
    
    public void setAccount(String accountName) {
        _accountName = accountName;
    }
    
    /**
     * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
     * This method causes this filter to be added to a query builder.
     */
    public void addToQueryBuilder(InitializableFromFilter initFromFilt) {
        if (!hasAccount())
            throw new ApiException("Cannot add Restricted Status without setting account information");
        ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) initFromFilt;
        qb.addRestrictedStatusFilter(getFilterValue(), _accountName, getOrGroupCode());
    }
    
    protected String displayName() {
        return "Restricted Status";
    }
    
    protected Map codeDisplayMap() {
        return displayCodes;
    }
    
}
