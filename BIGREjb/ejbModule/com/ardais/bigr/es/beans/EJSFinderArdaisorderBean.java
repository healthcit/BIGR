package com.ardais.bigr.es.beans;

/**
 * EJSFinderArdaisorderBean
 */
public interface EJSFinderArdaisorderBean {
	/**
	 * findByOrderStatus
	 */
	public com.ibm.ejs.persistence.EJSFinder findByOrderStatus(java.lang.String ardOrder_status) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findUserOpenOrders
	 */
	public com.ibm.ejs.persistence.EJSFinder findUserOpenOrders(java.lang.String user, java.lang.String account) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findAllOrders
	 */
	public com.ibm.ejs.persistence.EJSFinder findAllOrders() throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findByOrderNumber
	 */
	public com.ibm.ejs.persistence.EJSFinder findByOrderNumber(java.lang.String orderID) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findAccountOrderswithStatus
	 */
	public com.ibm.ejs.persistence.EJSFinder findAccountOrderswithStatus(java.lang.String arg_account, java.lang.String arg_status) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findByAccount
	 */
	public com.ibm.ejs.persistence.EJSFinder findByAccount(java.lang.String arg_accountID) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findUserOrderHistory
	 */
	public com.ibm.ejs.persistence.EJSFinder findUserOrderHistory(java.lang.String user, java.lang.String account) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findAccountOrderHistory
	 */
	public com.ibm.ejs.persistence.EJSFinder findAccountOrderHistory(java.lang.String accountID) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findArdaisorderByArdaisuser
	 */
	public com.ibm.ejs.persistence.EJSFinder findArdaisorderByArdaisuser(com.ardais.bigr.es.beans.ArdaisuserKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
