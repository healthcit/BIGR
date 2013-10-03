package com.ardais.bigr.es.beans;

/**
 * EJSFinderArdaisaddressBean
 */
public interface EJSFinderArdaisaddressBean {
	/**
	 * findByAccountandType
	 */
	public com.ibm.ejs.persistence.EJSFinder findByAccountandType(java.lang.String arg_Account, java.lang.String arg_Type) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
