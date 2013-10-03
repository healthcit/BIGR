package com.gulfstreambio.kc.det;

import java.io.Serializable;

import com.ardais.bigr.api.ApiException;
import com.gulfstreambio.gboss.GbossCategory;
import com.gulfstreambio.gboss.GbossDataElement;
import com.gulfstreambio.gboss.Gboss;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.util.KcNamingUtils;

public class DatabaseDataElement implements DatabaseElement, Serializable {

  public final static String COLUMN_DATA_ELEMENT = "DATA_ELEMENT_CUI"; 
  
  private DetDataElement _dataElement;
  private boolean _isEav;
  private String _tableName;
  private String _columnName;
  private String _columnNameDpc;
  private String _columnNameOther;
  private String _columnNameSystemStandard;
  
  DatabaseDataElement(DetDataElement detElement) {
    super();
    _dataElement = detElement;
    
    Gboss gboss = GbossFactory.getInstance();
    GbossDataElement gbossDataElement = gboss.getDataElement(detElement.getCui());
    GbossCategory databaseCategory = gbossDataElement.getDatabaseCategory();
    
    boolean multivalued = detElement.isMultivalued();
    boolean eav = databaseCategory.isDatabaseTypeEav();

    // The database table is EAV if the data element is multivalued or the pattern of the
    // database category is EAV.
    _isEav = multivalued || eav;
    
    // Determine the name of the table based on whether the data element is multivalued or
    // whether the database pattern is EAV.
    if (multivalued) {
      _tableName = KcNamingUtils.getDatabaseTableNameMulti(databaseCategory);
    }
    else if (eav) {
      _tableName = KcNamingUtils.getDatabaseTableNameEav(databaseCategory);
    }
    else {
      _tableName = KcNamingUtils.getDatabaseTableNameConventional(databaseCategory);
    }
    
    // Get the name of the data value main column and the DPC column.
    if (_isEav) {
      if (detElement.isDatatypeCv()) {
        _columnName = "DATA_VALUE_CUI";
      }
      else if (detElement.isDatatypeDate() || detElement.isDatatypeVpd()) {
        _columnName = "DATA_VALUE_DATE";
      }
      else if (detElement.isDatatypeFloat() || detElement.isDatatypeInt()) {
        _columnName = "DATA_VALUE";
      }
      else if (detElement.isDatatypeText()) {
        _columnName = "DATA_VALUE_TEXT";
      }
      else if (detElement.isDatatypeReport()) {
        _columnName = "DATA_VALUE_CLOB";
      }
      else {
        throw new ApiException("Could not determine data value column for element :" + detElement.getCui());
      }
    }
    else {
      _columnName = KcNamingUtils.getDatabaseColumnName(detElement);
    }

    // Get the name of the DPC column if applicable.
    if (detElement.isDatatypeVpd()) {
      _columnNameDpc = 
        (_isEav) ? "DATA_VALUE_DATE_DPC" : KcNamingUtils.getDatabaseColumnNameDpc(detElement);
    }

    // Get the name of the other column if applicable.
    if (detElement.isDatatypeCv() && detElement.isHasOtherValue()) {
      _columnNameOther = 
        (_isEav) ? "OTHER_DATA_VALUE" : KcNamingUtils.getDatabaseColumnNameOther(detElement);
    }
    
    // Get the name of the system standard value column.
    _columnNameSystemStandard = 
      (_isEav) ? "DATA_VALUE_CUI" : KcNamingUtils.getDatabaseColumnNameStandardValue(detElement);
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getElementMetadata()
   */
  public DetElement getDetElement() {
    return _dataElement;
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
    return null;
  }

  /* (non-Javadoc)
   * @see com.gulfstreambio.kc.det.DatabaseElement#getColumnDataElement()
   */
  public String getColumnDataElement() {
    return (isTableEav()) ? COLUMN_DATA_ELEMENT : null;
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
    return _columnNameSystemStandard;
  }
}
