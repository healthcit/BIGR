package com.gulfstreambio.gboss;

import com.ardais.bigr.api.Escaper;
import com.gulfstreambio.kc.det.DetElementDatatypes;

public class GbossDataElementInt extends GbossDataElement {

  private Integer _min;
  private Boolean _minInclusive;
  private Integer _max;
  private Boolean _maxInclusive;
  private String _unitRef;
  private GbossUnit _unit;
  
  /**
   * Create a new GbossDataElementInt object.
   */
  public GbossDataElementInt() {
    super();
  }
  
  public String getDatatype() {
    return DetElementDatatypes.INT; 
   }
  
  public String toHtml() {
    StringBuffer buff = new StringBuffer(100);
    buff.append(super.toHtml());
    if (getMin() != null) {
      buff.append(", ");
      buff.append("min");
      if (getMinInclusive() != null && getMinInclusive().booleanValue()) {
        buff.append (" (inclusive)");
      }
      buff.append(" = ");
      buff.append(getMin().toString());
    }
    if (getMax() != null) {
      buff.append(", ");
      buff.append("max");
      if (getMaxInclusive() != null && getMaxInclusive().booleanValue()) {
        buff.append (" (inclusive)");
      }
      buff.append(" = ");
      buff.append(getMax().toString());
    }
    if (getUnit() != null) {
      buff.append(", ");
      buff.append("unit = ");
      buff.append(Escaper.htmlEscapeAndPreserveWhitespace(getUnit().getDescription()));
      buff.append(" (");
      buff.append(getUnit().getCui());
      buff.append(")");
    }
    return buff.toString();
  }
  
  /**
   * @return Returns the max.
   */
  public Integer getMax() {
    return _max;
  }
  
  /**
   * @return Returns the max value as String.
   */
  public String getMaxValue() {
    if (getMax() != null)
    	return getMax().toString();
    else
      return null;
  }
  
  /**
   * @param max The max to set.
   */
  public void setMax(Integer max) {
    checkImmutable();
    _max = max;
  }
  
  /**
   * @return Returns the maxInclusive.
   */
  public Boolean getMaxInclusive() {
    return _maxInclusive;
  }
  
  /**
   * @return Returns the maxInclusive as boolean
   */
  public boolean isMaximumInclusive() {
    if (getMaxInclusive() != null)
    	return getMaxInclusive().booleanValue();
    else
      return false;
  }
  
  /**
   * @param maxInclusive The maxInclusive to set.
   */
  public void setMaxInclusive(Boolean maxInclusive) {
    checkImmutable();
    _maxInclusive = maxInclusive;
  }
  
  /**
   * @return Returns the min.
   */
  public Integer getMin() {
    return _min;
  }
  
  /**
   * @return Returns the min value as String.
   */
  public String getMinValue() {
    if (getMin() != null)
    	return getMin().toString();
    else
      return null;
  }
  
  /**
   * @param min The min to set.
   */
  public void setMin(Integer min) {
    checkImmutable();
    _min = min;
  }
  
  /**
   * @return Returns the minInclusive.
   */
  public Boolean getMinInclusive() {
    return _minInclusive;
  }
  
  /**
   * @return Returns the minInclusive as boolean
   */
  public boolean isMinimumInclusive() {
    if (getMinInclusive() != null)
    	return getMinInclusive().booleanValue();
    else
      return false;
   }
  
  /**
   * @param minInclusive The minInclusive to set.
   */
  public void setMinInclusive(Boolean minInclusive) {
    checkImmutable();
    _minInclusive = minInclusive;
  }
  
  /**
   * @return Returns the unit.
   */
  public GbossUnit getUnit() {
    return _unit;
  }

  /**
   * @param unit The unit to set.
   */
  public void setUnit(GbossUnit unit) {
    checkImmutable();
    _unit = unit;
  }
  
  /**
   * @return Returns the unit cui.
   */
  public String getUnitCui() {
    if (getUnit() != null)
    	return getUnit().getCui();
    else
      return null;
  }
  
  /**
   * @return Returns the unitRef.
   */
  public String getUnitRef() {
    return _unitRef;
  }
  
  /**
   * @param cui The cui of the unitRef to set.
   */
  public void setUnitRef(String cui) {
    checkImmutable();
    _unitRef = cui;
  }
  
  /**
   * Set this instance to be immutable.  Any attempts to modify immutable
   * instances will throw an exception.
   */
  public void setImmutable() {
    super.setImmutable();
    if (_unit != null) {
      _unit.setImmutable();
    }
  }
}
