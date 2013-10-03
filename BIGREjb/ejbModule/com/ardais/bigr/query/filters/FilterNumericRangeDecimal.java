package com.ardais.bigr.query.filters;

import java.math.BigDecimal;

/**
 * A filter to check that a value falls within a particular decimal range.  This is an abstract 
 * class that is expected to be extended by more specific decimal range filters. 
 */
public abstract class FilterNumericRangeDecimal extends FilterNumericRange {

  private BigDecimal _min;
  private BigDecimal _max;

  public FilterNumericRangeDecimal(String key) {
    super(key);
  }

  public FilterNumericRangeDecimal(
    String key,
    BigDecimal min,
    BigDecimal max,
    double minVal,
    double maxVal) {
    this(key);
    _min = (min == null) ? new BigDecimal(minVal) : min;
    _max = (max == null) ? new BigDecimal(maxVal) : max;
  }

  public BigDecimal getMin() {
    return _min;
  }

  public void setMin(BigDecimal min) {
    _min = min;
  }

  public BigDecimal getMax() {
    return _max;
  }

  public void setMax(BigDecimal max) {
    _max = max;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.FilterNumericRange#getMinAsString()
   */
  protected String getMinAsString() {
    BigDecimal min = getMin();
    return (min == null) ? null : min.toString();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.query.filters.FilterNumericRange#getMaxAsString()
   */
  protected String getMaxAsString() {
    BigDecimal max = getMax();
    return (max == null) ? null : max.toString();
  }

}
