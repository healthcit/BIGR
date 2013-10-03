package com.gulfstreambio.kc.det;

import java.io.Serializable;

public class DatabaseFormInstance implements Serializable {

  DatabaseFormInstance() {
    super();
  }

  /**
   * Returns the name of the database table that holds the form instance. 
   * 
   * @return  The database table name.
   */
  public String getTableName() {
    return "CIR_FORM";
  }

  /**
   * Returns the name of the primary key column in the table that holds the form instance.
   * 
   * @return  The primary key column name.
   */
  public String getColumnPrimaryKey() {
    return "FORM_ID";
  }

  /**
   * Returns the name of the column that holds the domain object id, in the table that holds the 
   * form instance.  This is intended to be used to join with tables from the host database that 
   * hold domain objects.
   * 
   * @return  The domain object id column name.
   */
  public String getColumnDomainObjectId() {
    return "DOMAIN_OBJECT_ID";
  }

  /**
   * Returns the name of the column that holds the domain object type, in the table that holds the 
   * form instance.  This is intended to be used to help the host form queries for appropriate 
   * types of objects.
   * 
   * @return  The domain object type column name.
   */
  public String getColumnDomainObjectType() {
    return "DOMAIN_OBJECT_TYPE";
  }

  /**
   * Returns the name of the column that holds the creation date of the form instance, in the table 
   * that holds the form instance.
   * 
   * @return  The creation date column name.
   */
  public String getColumnCreationDate() {
    return "CREATION_DATETIME";    
  }

  /**
   * Returns the name of the column that holds the modification date of the form instance, in the table 
   * that holds the form instance.
   * 
   * @return  The modification date column name.
   */
  public String getColumnModificationDate() {
    return "MODIFICATION_DATETIME";    
  }
}
