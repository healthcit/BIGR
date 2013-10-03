package com.ardais.bigr.query.generator;

import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterStringEquals;
import com.ardais.bigr.query.filters.InitializableFromFilter;
import com.ardais.bigr.util.Constants;

/**
 * Filters for gender (M, F)
 */
public class FilterGender extends FilterStringEquals {

  static Map displayCodes = new HashMap();
  static {
    displayCodes.put(Constants.GENDER_MALE, "Male");
    displayCodes.put(Constants.GENDER_FEMALE, "Female");
    displayCodes.put(Constants.GENDER_OTHER, "Other");
    displayCodes.put(Constants.GENDER_UNKNOWN, "Unknown");
  }

  /**
   * Constructor for FilterGender.
   * @param gender   Constants values for male, female
   */
  public FilterGender(String gender) {
    super(FilterConstants.KEY_GENDER, gender);
  }

  protected String displayName() {
    return "Gender";
  }

  protected Map codeDisplayMap() {
    return displayCodes;
  }

  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterGender(getFilterValue());
  }

}
