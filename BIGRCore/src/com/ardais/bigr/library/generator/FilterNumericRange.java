package com.ardais.bigr.library.generator;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * Represents a numeric range filter from BigrLibraryMetaData.xml, and generates the SQL fragment
 * for a numeric range filter based on its metadata.  
 */
public class FilterNumericRange extends ColumnFilter {

  private String _lowerLimit;
  private String _lowerInclusive;
  private String _upperLimit;
  private String _upperInclusive;

  public FilterNumericRange() {
    setMultiOperator("OR");
    setLowerInclusive("true");
    setUpperInclusive("true");
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.library.generator.ColumnFilter#getSqlFragment()
   */
  public String getSqlFragment() {
    StringBuffer buf = new StringBuffer();
    
    // Add the opening paren.
    buf.append('(');

    // Add the lower bound of the range.
    buf.append('(');
    buf.append(getTableAlias());
    buf.append('.');
    buf.append(getColumnName());
    if ("true".equals(getLowerInclusive())) {
      buf.append(" >= ");
    }
    else {
      buf.append(" > ");
    }
    String lower = getLowerLimit();
    if (ApiFunctions.isEmpty(lower)) {
      buf.append("? )");
    }
    else {
      buf.append(lower);
      buf.append(')');
    }

    // Add the AND.
    buf.append(" AND ");

    // Add the upper bound of the range.
    buf.append('(');
    buf.append(getTableAlias());
    buf.append('.');
    buf.append(getColumnName());
    if ("true".equals(getUpperInclusive())) {
      buf.append(" <= ");
    }
    else {
      buf.append(" < ");
    }
    String upper = getUpperLimit();
    if (ApiFunctions.isEmpty(upper)) {
      buf.append("? )");
    }
    else {
      buf.append(upper);
      buf.append(')');
    }

    // Add the closing paren.
    buf.append(')');

    return buf.toString();
  }

  public String getLowerLimit() {
    return _lowerLimit;
  }

  public String getLowerInclusive() {
    return _lowerInclusive;
  }

  public String getUpperLimit() {
    return _upperLimit;
  }

  public String getUpperInclusive() {
    return _upperInclusive;
  }

  public void setLowerLimit(String string) {
    _lowerLimit = string;
  }

  public void setLowerInclusive(String string) {
    _lowerInclusive = string;
  }

  public void setUpperLimit(String string) {
    _upperLimit = string;
  }

  public void setUpperInclusive(String string) {
    _upperInclusive = string;
  }

}
