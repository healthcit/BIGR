package com.ardais.bigr.query.filters;

import org.apache.commons.lang.StringUtils;

/**
 * @author dfeldman
 *
 * This is the superclass of filters that filter for a list of string values and also
 * hold onto user-readable descriptions of the values to support rendering in a GUI.
 * 
 */
public abstract class FilterStringsEqualWithDescriptions extends FilterStringsEqual {

  private String[] _descriptions; // display-only labels for GUI support

  /**
   * Constructor for FilterStringsEqualWithDescriptions.
   * @param key
   * @param filterValues
   */
  public FilterStringsEqualWithDescriptions(
    String key,
    String[] filterValues,
    String[] descriptions) {
    super(key, filterValues);
    _descriptions = descriptions;
  }

  public String[] getDescriptions() {
    return _descriptions;
  }

  public void setDescriptions(String[] descriptions) {
    _descriptions = descriptions;
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#appendVals(StringBuffer)
   * write out the codes, and also write out descriptions (separated from codes via a semicolon)
   */
  protected void appendVals(StringBuffer buf) {
    super.appendVals(buf);
    buf.append(';');
    buf.append(StringUtils.join(getDescriptions(),","));
  }

}
