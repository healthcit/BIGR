package com.ardais.bigr.query.filters;


/**
 * A filter to check that a value falls within a particular range.  This is an abstract class
 * that is expected to be extended by more specific range filters. 
 */
public abstract class FilterNumericRange extends Filter {

  protected FilterNumericRange(String key) {
    super(key);
  }

  /**
   * Returns the minimum of the range as a string.
   *  
   * @return  The minimum.
   */
  protected abstract String getMinAsString();

  /**
   * Returns the maximum of the range as a string.
   *  
   * @return  The maximum.
   */
  protected abstract String getMaxAsString();

  /**
   * Returns an indication of whether this filter is empty.
   * 
   * @return  true if this filter is empty (i.e. no min or max); false otherwise
   */
  public boolean isEmpty() {
    return (getMinAsString() == null) && (getMaxAsString() == null);
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#toString()
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append(displayName());

    String min = getMinAsString();
    String max = getMaxAsString();
    
    if ((min != null) && (max != null)) {
      sb.append(" is between ");
      sb.append(min);
      sb.append(" and ");
      sb.append(max);
    }
    else if ((min == null) && (max != null)) {
      sb.append(" is at most ");
      sb.append(max);
    }
    else if ((min != null) && (max == null)) {
      sb.append(" is at least ");
      sb.append(min);
    }
    else {
      sb.append("can be any value");
    }
    sb.append('.');
    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#appendVals(StringBuffer)
   */
  protected void appendVals(StringBuffer buf) {
    String min = getMinAsString();
    String max = getMaxAsString();

    // format:  min,max  (with '-' for null)
    buf.append(min == null ? "-" : min);
    buf.append(',');
    buf.append(max == null ? "-" : max);
  }

}
