package com.gulfstreambio.kc.det;

public interface DatabaseElement {

  /**
   * Returns the metadata of this element.
   * 
   * @return  The DetElement.
   */
  public DetElement getDetElement();

  /**
   * Returns an indication of whether this element's value is stored in a conventional database 
   * table.
   * 
   * @return <code>true</code> if this element's value is stored in a conventional table; 
   * <code>false</code> otherwise.  Note that this will always return <code>false</code> if this 
   * element is multivalued.
   */
  public boolean isTableConventional();

  /**
   * Returns an indication of whether this element's value is stored in an EAV database table.
   * 
   * @return <code>true</code> if this element's value is stored in an EAV table; 
   * <code>false</code> otherwise.  Note that this will always return <code>true</code> if this 
   * element is multivalued.
   */
  public boolean isTableEav();

  /**
   * Returns the name of the database table that holds the value of the element.
   * 
   * @return  The database table name.
   */
  public String getTableName();

  /**
   * Returns the name of the primary key column in the table returned by {@link #getTableName()}.
   * 
   * @return  The primary key column name.
   */
  public String getColumnPrimaryKey();

  /**
   * Returns the name of the foreign key column to the form instance table in the table returned 
   * by {@link #getTableName()}.  This should be joined with the column returned by 
   * {@link DatabaseFormInstance.getColumnPrimaryKey()}.
   * 
   * @return  The foreign key column name.
   */
  public String getColumnForeignKeyFormInstance();

  /**
   * Returns the name of the foreign key column to the parent table in the table returned 
   * by {@link #getTableName()}.  This should be joined with the column returned by 
   * {@link #getColumnPrimaryKey()} of the parent data element table.  This is only applicable
   * to ADE tables.
   * 
   * @return  The foreign key column name.
   */
  public String getColumnForeignKeyParent();

  /**
   * Returns the name of the column that holds the CUI of this element in the table returned by 
   * {@link #getTableName()}.  This only applies to EAV tables, and therefore if 
   * {@link #isTableConventional()} returns <code>true</code>, this returns <code>null</code>.
   * 
   * @return  The element column name, or <code>null</code> if the table is conventional.
   */
  public String getColumnDataElement();

  /**
   * Returns the name of the column that holds the data value of this element in the table returned 
   * by {@link #getTableName()}.  
   * 
   * @return  The data value column name.
   */
  public String getColumnDataValue();

  /**
   * Returns the name of the column that holds the value of the date precision component (DPC) of 
   * this element in the table returned by {@link #getTableName()}.  This only applies to elements 
   * whose datatype is Variable Precision Date (VPD), and thus returns <code>null</code> unless 
   * {@link DetElement#isDatatypeVpd() isDatatypeVpd} returns true.
   * 
   * @return  The DPC value column name, or <code>null</code> if this element does not have 
   * datatype VPD.
   */
  public String getColumnDataValueDpc();

  /**
   * Returns the name of the column that holds the "other" data value of this element in the table 
   * returned by {@link #getTableName()}.  This only applies to elements whose datatype is 
   * Controlled Vocabulary (CV) and can take an "other" value, and thus returns 
   * <code>null</code> unless {@link DetElement#isDatatypeCv() isDatatypeCv} returns true and
   * {@link DetElement#isHasOtherValue() isHasOtherValue} returns true.
   * 
   * @return  The "other" value column name, or <code>null</code> if this element does not have 
   * an "other" value.
   */
  public String getColumnDataValueOther();

  /**
   * Returns the name of the column that holds the data value of this element if the value is
   * one of the system standard values, in the table returned by {@link #getTableName()}.
   * If the element's datatype is Controlled Vocabulary (CV) 
   * ({@link DetElement#isDatatypeCv() isDatatypeCv} returns true), then this returns the same 
   * column name as {@link #getColumnDataValue()}
   * 
   * @return  The system standard value data value column name.
   */
  public String getColumnDataValueSystemStandard();

}
