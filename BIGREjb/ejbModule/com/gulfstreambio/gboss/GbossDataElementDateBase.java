package com.gulfstreambio.gboss;

public abstract class GbossDataElementDateBase extends GbossDataElement {

  //note that the date values come in as strings, to support the use of terms like "today"
  private String _minString;
  private Boolean _minInclusive;
  private String _maxString;
  private Boolean _maxInclusive;
  
  static final String TODAY = "today";
  
  public boolean isMaximumInclusive() {
    return (getMaxInclusive() != null) ? getMaxInclusive().booleanValue() : false;
  }

  /**
   * @return Returns the maxInclusive.
   */
  public Boolean getMaxInclusive() {
    return _maxInclusive;
  }
  
  /**
   * @param maxInclusive The maxInclusive to set.
   */
  public void setMaxInclusive(Boolean maxInclusive) {
    checkImmutable();
    _maxInclusive = maxInclusive;
  }
  
  public boolean isMinimumInclusive() {
    return (getMinInclusive() != null) ? getMinInclusive().booleanValue() : false;
  }

  /**
   * @return Returns the minInclusive.
   */
  public Boolean getMinInclusive() {
    return _minInclusive;
  }
  
  /**
   * @param minInclusive The minInclusive to set.
   */
  public void setMinInclusive(Boolean minInclusive) {
    checkImmutable();
    _minInclusive = minInclusive;
  }
  
  /**
   * @return Returns the maxString.
   */
  public String getMaxString() {
    return _maxString;
  }
  
  /**
   * @param maxString The maxString to set.
   */
  public void setMaxString(String maxString) {
    checkImmutable();
    _maxString = maxString;
  }
  
  /**
   * @return Returns the minString.
   */
  public String getMinString() {
    return _minString;
  }
  
  /**
   * @param minString The minString to set.
   */
  public void setMinString(String minString) {
    checkImmutable();
    _minString = minString;
  }
}
