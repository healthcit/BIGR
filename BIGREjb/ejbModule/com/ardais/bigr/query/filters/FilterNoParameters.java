package com.ardais.bigr.query.filters;

/**
 * @author dfeldman
 *
 * This is the superclass of non-parameterized filters, such as FilterInArdaisInventory
 * which does not need any strings, integers or other values to configure it.
 */
public abstract class FilterNoParameters extends Filter {

  /**
   * Constructor for FilterNoParameters.
   * @param key
   */
  public FilterNoParameters(String key) {
    super(key);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#appendVals(StringBuffer)
   */
  protected void appendVals(StringBuffer buf) {
    buf.append(""); // no params
  }

}
