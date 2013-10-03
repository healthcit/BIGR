package com.ardais.bigr.es.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ArdaisorderHome extends javax.ejb.EJBHome {

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.es.beans.Ardaisorder
 * @param argOrder_number java.math.BigDecimal
 * @param argArdaisuser com.ardais.bigr.es.beans.ArdaisuserKey
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.es.beans.Ardaisorder create(java.math.BigDecimal argOrder_number, com.ardais.bigr.es.beans.ArdaisuserKey argArdaisuser) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * Insert the method's description here.
 * Creation date: (3/14/2001 8:20:48 AM)
 * @return java.util.Enumeration
 * @param accountID java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findAccountOrderHistory(String accountID) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Insert the method's description here.
 * Creation date: (3/29/2001 11:08:36 AM)
 * @return java.util.Enumeration
 * @param arg_account java.lang.String
 * @param arg_status java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findAccountOrderswithStatus(String arg_account, String arg_status) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Insert the method's description here.
 * Creation date: (3/14/2001 1:30:50 PM)
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findAllOrders() throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the association named ardaisuser.
	 * It will be deleted/edited when the association is deleted/edited.
	 */
	public java.util.Enumeration findArdaisorderByArdaisuser(com.ardais.bigr.es.beans.ArdaisuserKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Insert the method's description here.
 * Creation date: (3/30/2001 1:36:02 PM)
 * @return java.util.Enumeration
 * @param arg_accountID java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findByAccount(String arg_accountID) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Insert the method's description here.
 * Creation date: (3/8/01 3:17:11 PM)
 * @return java.util.Enumeration
 * @param orderID int
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findByOrderNumber(String orderID) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Insert the method's description here.
 * Creation date: (3/27/2001 10:56:15 AM)
 * @return java.util.Enumeration
 * @param ardOrder_status java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findByOrderStatus(String ardOrder_status) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Finds an instance using a key for Entity Bean: Ardaisorder
	 */
	public com.ardais.bigr.es.beans.Ardaisorder findByPrimaryKey(
		com.ardais.bigr.es.beans.ArdaisorderKey primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
/**
 * Insert the method's description here.
 * Creation date: (3/14/2001 8:21:59 AM)
 * @return java.util.Enumeration
 * @param user java.lang.String
 * @param account java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findUserOpenOrders(String user, String account) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Insert the method's description here.
 * Creation date: (3/14/2001 8:19:57 AM)
 * @return java.util.Enumeration
 * @param user java.lang.String
 * @param account java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findUserOrderHistory(String user, String account) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Creates an instance from a key for Entity Bean: Ardaisorder
	 */
	public com.ardais.bigr.es.beans.Ardaisorder create(java.math.BigDecimal order_number, com.ardais.bigr.es.beans.Ardaisuser argArdaisuser) throws javax.ejb.CreateException, java.rmi.RemoteException;
}
