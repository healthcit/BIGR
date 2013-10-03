package com.ardais.bigr.es.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ArdaisorderstatusHome extends javax.ejb.EJBHome {

/**
 * 
 * @return com.ardais.bigr.es.beans.Ardaisorderstatus
 * @param argOrder_status_date java.sql.Timestamp
 * @param argOrder_status_id java.lang.String
 * @param argArdaisorder com.ardais.bigr.es.beans.ArdaisorderKey
 * @param argArdais_acct_key java.lang.String
 * @param argArdais_user_id java.lang.String
 */
com.ardais.bigr.es.beans.Ardaisorderstatus create(java.sql.Timestamp argOrder_status_date, java.lang.String argOrder_status_id, com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder, java.lang.String argArdais_acct_key, java.lang.String argArdais_user_id) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Ardaisorderstatus-Ardaisorder.  
 * 	It will be deleted/edited when the association is deleted/edited.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public java.util.Enumeration findArdaisorderstatusByArdaisorder(com.ardais.bigr.es.beans.ArdaisorderKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Finds an instance using a key for Entity Bean: Ardaisorderstatus
	 */
	public com.ardais.bigr.es.beans.Ardaisorderstatus findByPrimaryKey(
		com.ardais.bigr.es.beans.ArdaisorderstatusKey primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * Creates an instance from a key for Entity Bean: Ardaisorderstatus
	 */
	public com.ardais.bigr.es.beans.Ardaisorderstatus create(
		java.sql.Timestamp order_status_date,
		java.lang.String order_status_id,
		java.lang.String ardais_acct_key,
		java.lang.String ardais_user_id,
		com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
  /**
   * Creates an instance from a key for Entity Bean: Ardaisorderstatus
   */
  public com.ardais.bigr.es.beans.Ardaisorderstatus create(
    java.lang.String ardais_acct_key,
    java.lang.String order_status_id,
    java.sql.Timestamp order_status_date,
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
    throws javax.ejb.CreateException, java.rmi.RemoteException;
}
