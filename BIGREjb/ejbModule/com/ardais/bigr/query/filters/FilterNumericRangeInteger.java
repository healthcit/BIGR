package com.ardais.bigr.query.filters;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.util.BigrValidator;
import com.ardais.bigr.util.Constants;

/**
 * A filter to check that a value falls within a particular integer range.  This is an abstract 
 * class that is expected to be extended by more specific integer range filters. 
 */
public abstract class FilterNumericRangeInteger extends FilterNumericRange {

  private Integer _min;
  private Integer _max;

  protected FilterNumericRangeInteger(String key) {
    super(key);
  }

  public FilterNumericRangeInteger(String key, Integer min, Integer max, int minVal, int maxVal) {
    this(key);
    _min = (min == null) ? new Integer(minVal) : min;
    _max = (max == null) ? new Integer(maxVal) : max;
  }

  public FilterNumericRangeInteger(String key, String min, String max, int minVal, int maxVal) {
    this(key);

    if ((min == null) || (min.equalsIgnoreCase(Constants.ANY))) {
      _min = new Integer(minVal);
    }
    else if (BigrValidator.isInteger(min)) {
      _min = new Integer(min);
    }
    else {
      throw new ApiException("Attempted to create a FilterNumericRangeInteger with a non-Integer min value.");
    }

    if ((max == null) || (max.equalsIgnoreCase(Constants.ANY))) {
      _max = new Integer(maxVal);
    }
    else if (BigrValidator.isInteger(max)) {
      _max = new Integer(max);
    }
    else {
      throw new ApiException("Attempted to create a FilterNumericRangeInteger with a non-Integer max value.");
    }
  }

  public Integer getMin() {
    return _min;
  }

  public void setMin(Integer min) {
    _min = min;
  }

  public Integer getMax() {
    return _max;
  }

  public void setMax(Integer max) {
    _max = max;
  }

  protected String getMinAsString() {
    Integer min = getMin();
    return (min == null) ? null : min.toString();
  }

  protected String getMaxAsString() {
    Integer max = getMax();
    return (max == null) ? null : max.toString();
  }
}
