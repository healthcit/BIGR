package com.ardais.bigr.query.generator;

import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringEquals;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.Constants;

public class FilterPathVerifiedStatus extends FilterStringEquals {

  static Map displayCodes = new HashMap();
  static {
    displayCodes.put(Constants.PV_STATUS_PVED, "Path Verified");
    displayCodes.put(Constants.PV_STATUS_UNPVED, "Non Path Verified");
  }

  public FilterPathVerifiedStatus(String filterValue) {
    super(FilterConstants.KEY_PATHVERIFIED_STATUS, filterValue);
  }

  /**
   * @see com.ardais.bigr.query.filters.FilterStringEquals#codeDisplayMap()
   */
  protected Map codeDisplayMap() {
    return displayCodes;
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "Pathology Verified";
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterSamplePathVerifiedStatus(getFilterValue());
  }
}
