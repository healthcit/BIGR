package com.gulfstreambio.kc.det;

import java.io.Serializable;

public abstract class DetElementBase implements DetElement, Serializable {

  private String _cui;
  private String _description; 
  private String _datatype;
  private boolean _isMaxInclusive;
  private boolean _isMinInclusive;
  private boolean _isMultilevelValueSet;
  private boolean _isMultivalued;
  private boolean _isHasOtherValue;
  private boolean _isHasNoValue;
  private boolean _isHasUnit;
  private String _maxValue;
  private String _minValue;
  private String _noValueCui;
  private String _noValueDescription;
  private String _otherValueCui;
  private String _otherValueDescription;
  private String _systemName;
  private String _unitCui;
  private String _unitDescription;
  private DetValueSet _broadValueSet;

  protected DetElementBase() {
    super();
  }

  public String getCui() {
    return _cui;
  }

  protected void setCui(String cui) {
    _cui = cui;
  }

  public String getDatatype() {
    return _datatype;
  }
  
  protected void setDatatype(String datatype) {
    _datatype = datatype;
  }
  
  public boolean isDatatypeCv() {
    return DetElementDatatypes.CV.equals(getDatatype());
  }
  
  public boolean isDatatypeInt() {
    return DetElementDatatypes.INT.equals(getDatatype());
  }
  
  public boolean isDatatypeFloat() {
    return DetElementDatatypes.FLOAT.equals(getDatatype());
  }
  
  public boolean isDatatypeDate() {
    return DetElementDatatypes.DATE.equals(getDatatype());
  }
  
  public boolean isDatatypeVpd() {
    return DetElementDatatypes.VPD.equals(getDatatype());        
  }
  
  public boolean isDatatypeReport() {
    return DetElementDatatypes.REPORT.equals(getDatatype());        
  }
  
  public boolean isDatatypeText() {
    return DetElementDatatypes.TEXT.equals(getDatatype());        
  }

  public String getDescription() {
    return _description;
  }
  
  protected void setDescription(String description) {
    _description = description;
  }

  public boolean isMaxInclusive() {
    return _isMaxInclusive;
  }
  
  protected void setMaxInclusive(boolean maxInclusive) {
    _isMaxInclusive = maxInclusive;
  }
  
  public boolean isMinInclusive() {
    return _isMinInclusive;
  }
  
  protected void setMinInclusive(boolean minInclusive) {
    _isMinInclusive = minInclusive;
  }
  
  public boolean isMultilevelValueSet() {
    return _isMultilevelValueSet;
  }
  
  protected void setMultilevelValueSet(boolean multilevelValueSet) {
    _isMultilevelValueSet = multilevelValueSet;
  }
  
  public boolean isMultivalued() {
    return _isMultivalued;
  }
  
  protected void setMultivalued(boolean multivalued) {
    _isMultivalued = multivalued;
  }
  
  public boolean isHasOtherValue() {
    return _isHasOtherValue;
  }
  
  protected void setHasOtherValue(boolean hasOtherValue) {
    _isHasOtherValue = hasOtherValue;
  }
  
  public boolean isHasNoValue() {
    return _isHasNoValue;
  }
  
  protected void setHasNoValue(boolean hasNoValue) {
    _isHasNoValue = hasNoValue;
  }
  
  public boolean isHasUnit() {
    return _isHasUnit;
  }
  
  protected void setHasUnit(boolean hasUnit) {
    _isHasUnit = hasUnit;
  }
  
  public String getMaxValue() {
    return _maxValue;
  }
  
  protected void setMaxValue(String maxValue) {
    _maxValue = maxValue;
  }
  
  public String getMinValue() {
    return _minValue;
  }
  
  protected void setMinValue(String minValue) {
    _minValue = minValue;
  }
  
  public String getNoValueCui() {
    return _noValueCui;
  }
  
  protected void setNoValueCui(String noValueCui) {
    _noValueCui = noValueCui;
  }
  
  public String getNoValueDescription() {
    return _noValueDescription;
  }
  
  protected void setNoValueDescription(String description) {
    _noValueDescription = description;
  }

  public String getOtherValueCui() {
    return _otherValueCui;
  }
  
  protected void setOtherValueCui(String otherValueCui) {
    _otherValueCui = otherValueCui;
  }
  
  public String getOtherValueDescription() {
    return _otherValueDescription;
  }
  
  protected void setOtherValueDescription(String description) {
    _otherValueDescription = description;
  }

  public String getSystemName() {
    return _systemName;
  }
  
  protected void setSystemName(String systemName) {
    _systemName = systemName;
  }
  
  public String getUnitCui() {
    return _unitCui;
  }
  
  protected void setUnitCui(String unitCui) {
    _unitCui = unitCui;
  }
  
  public String getUnitDescription() {
    return _unitDescription;
  }
  
  protected void setUnitDescription(String description) {
    _unitDescription = description;
  }
  
  public DetValueSet getBroadValueSet() {
    return _broadValueSet;
  }
  
  protected void setBroadValueSet(DetValueSet broadValueSet) {
    _broadValueSet = broadValueSet;
  }

}
