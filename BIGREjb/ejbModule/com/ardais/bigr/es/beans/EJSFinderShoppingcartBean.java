package com.ardais.bigr.es.beans;

/**
 * EJSFinderShoppingcartBean
 */
public interface EJSFinderShoppingcartBean {
	/**
	 * findByUserAccount
	 */
	public com.ibm.ejs.persistence.EJSFinder findByUserAccount(java.lang.String user, java.lang.String account) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findShoppingcartByArdaisuser
	 */
	public com.ibm.ejs.persistence.EJSFinder findShoppingcartByArdaisuser(com.ardais.bigr.es.beans.ArdaisuserKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
