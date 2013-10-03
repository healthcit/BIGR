package com.ardais.bigr.query.generator;

import java.util.Map;

import com.ardais.bigr.query.filters.FilterConstants;
import com.ardais.bigr.query.filters.FilterNumericRangeInteger;
import com.ardais.bigr.query.filters.InitializableFromFilter;

public class FilterAgeAtCollection extends FilterNumericRangeInteger {

  public static final int maxAge = 130;
  public static final String maxDisplayString = "89+";
  public static final int minAge = 0;

  public static void addToMap(String min, String max, Map m) {
    FilterNumericRangeInteger filter = new FilterAgeAtCollection(min, max);
    if (!filter.isEmpty())
      m.put(filter.getKey(), filter);
  }

  /**
   * Constructor for FilterAgeAtCollection.
   * @param min   Minimum age
   * @param max   Maxumum age
   */
  public FilterAgeAtCollection(Integer min, Integer max) {
    super(FilterConstants.KEY_AGEATCOLLECTION, min, max, minAge, maxAge);
  }
  /**
   * Constructor for FilterAgeAtCollection.
   * @param min   Minimum age
   * @param max   Maxumum age
   */
  public FilterAgeAtCollection(String min, String max) {
    super(FilterConstants.KEY_AGEATCOLLECTION, min, max, 0, 130);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#displayName()
   */
  protected String displayName() {
    return "Age at Collection";
  }

  public boolean isEmpty() {
    boolean hasMin = getMin() != null;
    boolean hasMax = getMax() != null;
    return !hasMin && !hasMax;
  }

  /**
   * @return the string representing the upper bound for this filter.  Default value is currently "89+"
   */
  public String getMaxAsString() {
    if (getMax() == null)
      return maxDisplayString;
    return getMax().intValue() == maxAge ? maxDisplayString : getMax().toString();
  }

  /**
   * return the string representing the lower bound for this filter.
   */
  public String getMinAsString() {
    if (getMin() == null)
      return "0";
    return getMin().toString();
  }

  public String toString() {
    boolean hasMin = getMin() != null;
    boolean hasMax = getMax() != null;
    String maxString = getMax().intValue() == maxAge ? maxDisplayString : getMax().toString();
    if (hasMin && hasMax)
      return displayName() + " is between " + getMin() + " and " + maxString + '.';
    if (hasMin)
      return displayName() + " is at least " + getMin() + '.';
    if (hasMax)
      return displayName() + " is at most " + maxString + '.';
    return displayName() + " can be any value.";
  }
  /**
   * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
   */
  public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
    ProductSummaryQueryBuilder qb = (ProductSummaryQueryBuilder) queryBuilder;
    qb.addFilterAgeAtCollection(getMin().toString(), getMax().toString());
  }

}
