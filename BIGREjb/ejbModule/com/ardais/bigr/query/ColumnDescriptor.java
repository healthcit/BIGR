package com.ardais.bigr.query;

import com.ardais.bigr.api.ApiException;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Class to hold information describing a column to be displayed in a result set
 */
public class ColumnDescriptor {
  
  //booleans to indicate if the column holds KC data or not
  private boolean _kcDataColumn;
  private boolean _bigrDataColumn;
  
  //for BIGR columns, the field to hold the column constant.  All necessary information
  //can be derived from this value
  private String _bigrColumnConstant;
  
  //for KC columns, various data needed to describe the column
  private String _dataType;
  private String _columnDescription;
  private String _domainObjectType;
  private String _cui;
  private int _width;
  private String _unitCui;

  public ColumnDescriptor() {
    super();
  }
  
  public ColumnDescriptor(String bigrColumnConstant) {
    super();
    setBigrColumnConstant(bigrColumnConstant);
  }

  public ColumnDescriptor(String cui, String domainObjectType, String dataType, String columnDescription, int width) {
    super();
    setCui(cui);
    setDomainObjectType(domainObjectType);
    setDataType(dataType);
    setColumnDescription(columnDescription);
    setWidth(width);
  }

  public boolean isBigrDataColumn() {
    return _bigrDataColumn;
  }
  
  public boolean isKcDataColumn() {
    return _kcDataColumn;
  }
  
  public String getBigrColumnConstant() {
    return _bigrColumnConstant;
  }
  
  public String getColumnDescription() {
    return _columnDescription;
  }
  
  public String getDataType() {
    return _dataType;
  }
  
  public String getDomainObjectType() {
    return _domainObjectType;
  }
  
  public int getWidth() {
    return _width;
  }
  
  public String getCui() {
    return _cui;
  }
  
  public void setBigrColumnConstant(String bigrColumnConstant) {
    if (isKcDataColumn()) {
      throw new ApiException("Cannot set BigrColumnConstant on kc data column");
    }
    _bigrColumnConstant = bigrColumnConstant;
    _bigrDataColumn = true;
  }
  
  public void setColumnDescription(String columnDescription) {
    if (isBigrDataColumn()) {
      throw new ApiException("Cannot set columnDescription on BIGR data column");
    }
    _columnDescription = columnDescription;
    _kcDataColumn = true;
  }
  
  public void setDataType(String dataType) {
    if (isBigrDataColumn()) {
      throw new ApiException("Cannot set dataType on BIGR data column");
    }
    _dataType = dataType;
    _kcDataColumn = true;
  }
  
  public void setDomainObjectType(String domainObjectType) {
    if (isBigrDataColumn()) {
      throw new ApiException("Cannot set domainObjectType on BIGR data column");
    }
    _domainObjectType = domainObjectType;
    _kcDataColumn = true;
  }

  public void setWidth(int width) {
    if (isBigrDataColumn()) {
      throw new ApiException("Cannot set width on BIGR data column");
    }
    _width = width;
  }

  public void setCui(String cui) {
    if (isBigrDataColumn()) {
      throw new ApiException("Cannot set cui on BIGR data column");
    }
    _cui = cui;
    //determine the unit cui, if any
    _unitCui = GbossFactory.getInstance().getDataElement(cui).getUnitCui();
  }
  
  /**
   * @return Returns the unitCui.
   */
  public String getUnitCui() {
    return _unitCui;
  }
}
