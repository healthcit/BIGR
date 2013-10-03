package com.ardais.bigr.query.filters;

import com.ardais.bigr.api.ApiFunctions;

/**
 * A filter to check that a date is within a range.
 */
public abstract class FilterDateRange extends Filter {

  private String _start = null;
  private String _end = null;

  /**
   * Creates a new <code>FilterStringEqual</code>.
   */
  public FilterDateRange(String key, String start, String end) {
    super(key);
    setValues(start, end);
  }

  private void setValues(String start, String end) {
    _start = (ApiFunctions.isEmpty(start) ? null : start);
    _end = (ApiFunctions.isEmpty(end) ? null : end);
  }

  //  private boolean validDate(String s) {
  //    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
  //    try {
  //      formatter.parse(s);
  //      return true;
  //    }
  //    catch (ParseException e) {
  //      return false;
  //    }
  //  }

  /**
   * returns true if this filter does not impose any restriction.
   */
  public boolean isEmpty() {
    return (ApiFunctions.isEmpty(_start) && ApiFunctions.isEmpty(_end));
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(displayName());
    if (!isEmpty()) {
      sb.append(" is between ");
      sb.append(_start == null ? "any" : _start);
      sb.append(" and ");
      sb.append(_end == null ? "any" : _end);
    }
    sb.append('.');
    return sb.toString();
  }

  /**
   * Returns the end.
   * @return String
   */
  public String getEndForDisplay() {
    return _end;
  }

  /**
   * Returns the start.
   * @return String
   */
  public String getStartForDisplay() {
    return _start;
  }

  public String getStartForQuery() {
    // the database WHERE template needs both dates, so fill in null start or end
    String s = getStartForDisplay();
    if (s == null)
      s = "01/01/1900";
    return s + " 00:00";
  }

  public String getEndForQuery() {
    // the database WHERE template needs both dates, so fill in null start or end
    String e = getEndForDisplay();
    if (e == null)
      e = "01/01/2100";
    return e + " 23:59";
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#appendVals(StringBuffer)
   */
  protected void appendVals(StringBuffer buf) {
    buf.append(_start == null ? "-" : _start.toString());
    buf.append(','); // date format must not contain commas
    buf.append(_end == null ? "-" : _end.toString());
  }

}
