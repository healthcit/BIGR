package com.ardais.bigr.es.beans;

/**
 * EJSFinderShoppingcartdetailBean
 */
public interface EJSFinderShoppingcartdetailBean {
	/**
	 * findShoppingcartdetailByShoppingcart
	 */
	public com.ibm.ejs.persistence.EJSFinder findShoppingcartdetailByShoppingcart(com.ardais.bigr.es.beans.ShoppingcartKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findByBarcodeUserAccount
	 */
	public com.ibm.ejs.persistence.EJSFinder findByBarcodeUserAccount(java.lang.String barcode_arg, java.lang.String user_arg, java.lang.String account_arg) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findByUserAccountOrderByLineNumber
	 */
	public com.ibm.ejs.persistence.EJSFinder findByUserAccountOrderByLineNumber(java.lang.String user, java.lang.String account) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
