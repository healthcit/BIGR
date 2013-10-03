package com.gulfstreambio.kc.form;


/**
 * Contains the core methods of the KnowledgeCapture (KC) Data Access Object (DAO).  These methods
 * allow the data elements of a KC database category to be stored and retrieved in the database
 * schema generated from a Data Element Taxonomy (DET).
 */
interface KcDataAccessObject {

  /**
   * Persists this database category to the database by inserting a new row(s) in the table(s) 
   * associated with this database category.
   */
  public void create();

  /**
   * Persists this database category to the database by updating existing row(s) in the table(s) 
   * associated with this database category.
   */
  public void update();

  /**
   * Deletes this database category from the database by deleting existing row(s) in the table(s) 
   * associated with this database category.
   * 
   * @return  The <code>FormInstance</code>.
   */
  public FormInstance delete();

 
  /**
   * Returns the form instance associated with this database category, given the form instance id.
   * 
   * @return  The <code>FormInstance</code>.
   */
  public FormInstance findByFormInstanceId();

}
