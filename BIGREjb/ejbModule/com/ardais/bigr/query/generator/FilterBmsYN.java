package com.ardais.bigr.query.generator;

import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringEquals;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.Constants;

/**
 * Filters for BMS (Y, N)
 */
public class FilterBmsYN extends FilterStringEquals {

  static Map displayCodes = new HashMap();
  static {
    displayCodes.put(FormLogic.DB_YES, FormLogic.DB_YES_TEXT);
    displayCodes.put(FormLogic.DB_NO, FormLogic.DB_NO_TEXT);
  }

  /**
   * Constructor for FilterBmsYN.
   * @param bms   Constants values for yes, no
   */
  public FilterBmsYN(String bms) {
    super(FilterConstants.KEY_BMS_YN, bms);
  }

  protected String displayName() {
    return "BMS";
  }

  protected Map codeDisplayMap() {
    return displayCodes;
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    TissueSummaryQueryBuilder qb = (TissueSummaryQueryBuilder) queryBuilder;
    qb.addFilterBmsYN(getFilterValue());
  }

}
