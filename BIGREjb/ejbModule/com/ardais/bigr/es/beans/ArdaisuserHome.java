package com.ardais.bigr.es.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ArdaisuserHome extends javax.ejb.EJBHome {

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.es.beans.Ardaisuser
 * @param argArdais_user_id java.lang.String
 * @param argArdaisaccount com.ardais.bigr.es.beans.ArdaisaccountKey
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.es.beans.Ardaisuser create(
  java.lang.String argArdais_user_id,
  com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount,
  java.lang.String argPasswordPolicyCid) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.es.beans.Ardaisuser
 * @param argArdais_user_id java.lang.String
 * @param argArdaisaccount com.ardais.bigr.es.beans.ArdaisaccountKey
 * @param argPassword java.lang.String
 * @param argCreated_by java.lang.String
 * @param argCreate_date java.sql.Timestamp
 */
com.ardais.bigr.es.beans.Ardaisuser create(
  java.lang.String argArdais_user_id,
  com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount,
  java.lang.String argPassword,
  java.lang.String argCreated_by,
  java.sql.Timestamp argCreate_date,
  java.lang.String argPasswordPolicyCid) throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * Finds an instance using a key for Entity Bean: Ardaisuser
	 */
	public com.ardais.bigr.es.beans.Ardaisuser findByPrimaryKey(
		com.ardais.bigr.es.beans.ArdaisuserKey primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * Creates an instance from a key for Entity Bean: Ardaisuser
	 */
	public com.ardais.bigr.es.beans.Ardaisuser create(
		java.lang.String ardais_user_id,
		com.ardais.bigr.es.beans.Ardaisaccount argArdaisaccount,
    java.lang.String argPasswordPolicyCid)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisaccount.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration findArdaisuserByArdaisaccount(
		com.ardais.bigr.es.beans.ArdaisaccountKey inKey)
		throws java.rmi.RemoteException, javax.ejb.FinderException;
	public com.ardais.bigr.es.beans.Ardaisuser findByUserId(java.lang.String userId) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
