package com.ardais.bigr.query.filters;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * A filter to check that one string equals one particular value.
 */
public abstract class FilterStringEquals extends Filter {

  private String _filterValue;

  /**
   * Creates a new <code>FilterStringEqual</code>.
   */
  public FilterStringEquals(String key, String filterValue) {
    super(key);
    _filterValue = filterValue;
  }

  /**
   * Returns the array of filter values.
   * 
   * @return  the filter values
   */
  public String getFilterValue() {
    return _filterValue;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer(32);
    sb.append(displayName());
    sb.append(" is ");
    String displayVal = (String) codeDisplayMap().get(getFilterValue());
    if (displayVal == null) {
      displayVal = getFilterValue();
    }
    sb.append(displayVal);
    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#appendVals(StringBuffer)
   */
  protected void appendVals(StringBuffer buf) {
    buf.append(_filterValue);
  }

}
