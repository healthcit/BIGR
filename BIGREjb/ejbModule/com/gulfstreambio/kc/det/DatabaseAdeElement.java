package com.gulfstreambio.kc.det;

import java.io.Serializable;

import com.gulfstreambio.kc.util.KcNamingUtils;

public class DatabaseAdeElement implements DatabaseElement, Serializable {

  private DetAdeElement _adeElement;
  private boolean _isEav;
  private String _baseTableName;
  private String _tableName;
  private String _columnName;
  private String _columnNameDpc;
  private String _columnNameOther;
  
  DatabaseAdeElement(DetAdeElement detElement) {
    super();
    _adeElement = detElement;
    boolean multivalued = detElement.isMultivalued();
    
    // The database table is EAV if the ADE element is multivalued.
    _isEav = multivalued;

    // Determine the name of the table based on whether the data element is multivalued or
    // whether the database pattern is EAV.
    DetAde ade = detElement.getAde();
    if (multivalued) {
      _tableName = KcNamingUtils.getDatabaseTableNameMulti(ade);
    }
    else {
      _tableName = KcNamingUtils.getDatabaseTableNameConventional(ade);
    }
    _baseTableName = KcNamingUtils.getDatabaseTableNameConventional(ade);

    // Get the name of the data value main column and the DPC column.
    _columnName = 
      (multivalued) ? "DATA_VALUE_CUI" : KcNamingUtils.getDatabaseColumnName(detElement);

    // Get the name of the DPC column if applicable.
    if (detElement.isDatatypeVpd()) {
      _columnNameDpc = KcNamingUtils.getDatabaseColumnNameDpc(detElement);
    }

    // Get the name of the other column if applicable.
    if (detElement.isDatatypeCv() && detElement.isHasOtherValue()) {
      _columnNameOther = 
        (multivalued) ? "OTHER_DATA_VALUE" : KcNamingUtils.getDatabaseColumnNameOther(detElement);
    }
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getDetElement()
   */
  public DetElement getDetElement() {
    return _adeElement;
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#isTableConventional()
   */
  public boolean isTableConventional() {
    return !isTableEav();
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#isTableEav()
   */
  public boolean isTableEav() {
    return _isEav;
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getTableName()
   */
  public String getTableName() {
    return _tableName;
  }

  /**
   * Returns the name of the "base" ADE table, i.e. the ADE table that holds the singlevalued
   * ADE element values.  This will return the same as {@link #getTableName()} if the ADE element
   * is singlevalued (which will also implies that {@link #isTableConventional()} returns
   * <code>true</code>).  But, if the ADE element is multivalued, the "base" table can be used
   * to form an appropriate join from the table that holds the multivalued ADE element values
   * of this ADE element to the table that holds the corresponding data element value.   
   *    
   * @return  The "base" ADE table name.
   */
  public String getTableNameBase() {
    return _baseTableName;
  }
  
  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getColumnPrimaryKey()
   */
  public String getColumnPrimaryKey() {
    return "ID";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getColumnForeignKeyFormInstance()
   */
  public String getColumnForeignKeyFormInstance() {
    return "FORM_ID";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getColumnForeignKeyParent()
   */
  public String getColumnForeignKeyParent() {
    return "PARENT_ID";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getColumnDataElement()
   */
  public String getColumnDataElement() {
    return "DATA_ELEMENT_CUI";
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getColumnDataValue()
   */
  public String getColumnDataValue() {
    return _columnName;
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getColumnDataValueDpc()
   */
  public String getColumnDataValueDpc() {
    return _columnNameDpc;
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getColumnDataValueOther()
   */
  public String getColumnDataValueOther() {
    return _columnNameOther;
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getColumnDataValueSystemStandard()
   */
  public String getColumnDataValueSystemStandard() {
    return null;
  }
}
