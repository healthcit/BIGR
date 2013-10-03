package com.ardais.bigr.query.generator;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterUserAccountInfo;
import com.ardais.bigr.query.filters.InitializableFromFilter;

public class FilterNotOnHoldForUserOrBmsRnaYnYes extends FilterUserAccountInfo {

  public FilterNotOnHoldForUserOrBmsRnaYnYes(String userId) {
    super(FilterConstants.KEY_NOT_ON_HOLD_FOR_USER_OR_BMS_RNA_YN_YES, userId, null);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.Filter#addToQueryBuilder(com.ardais.bigr.query.filters.InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    RnaSummaryQueryBuilder rnaSQB = (RnaSummaryQueryBuilder) queryBuilder;
    rnaSQB.addFilterNotOnHoldForUserOrBmsYNYes(getUserId());
  }

}
