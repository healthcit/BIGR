package com.ardais.bigr.query.filters;

import java.io.Serializable;

import com.ardais.bigr.userprofile.UserProfileTopicSerializer;

/**
 *  A filter to check that a string value equals any one of a set of values.
 */
public abstract class FilterBoolean extends Filter {

  private boolean _bool;

  /**
   * Creates a new <code>FilterBoolean</code> object that constrains the key to be
     * the boolean value <code> bool </code>.
   */
  public FilterBoolean(String key, boolean bool) {
    super(key);
    _bool = bool;
  }

  /**
   * Constructor to create a boolean filter from a string.
   * @param boolString - one of "true" "false" "Y" or "N"
   */
  public FilterBoolean(String key, String boolString) {
    super(key);
    if ("true".equals(boolString) || "Y".equals(boolString))
      setFilterValue(true);
    else if ("false".equals(boolString) || "N".equals(boolString))
      setFilterValue(false);
    else
      throw new IllegalArgumentException(displayName() + " Filter must be set with true or false");
  }

  //	/* (non-Javadoc)
  //	 * @see com.ardais.bigr.query.filters.QueryBuilderFilter#addToQueryBuilder(InitializableFromFilter)
  //	 */
  //	public void addToQueryBuilder(InitializableFromFilter queryBuilder) {
  //		queryBuilder.addFilter(getKey(), getFilterValue());
  //	}

  /**
   * Returns the array of filter values.
   * 
   * @return  the filter values
   */
  public boolean getFilterValue() {
    return _bool;
  }

  protected void setFilterValue(boolean bool) {
    _bool = bool;
  }

  // @todo:  make these abstract
  protected String getPositiveDescription() {
    return displayName() + " is true";
  }
  protected String getNegativeDescription() {
    return displayName() + " is false";
  }

  public void appendVals(StringBuffer buf) {
    buf.append(_bool ? "true" : "false");
  }
}
