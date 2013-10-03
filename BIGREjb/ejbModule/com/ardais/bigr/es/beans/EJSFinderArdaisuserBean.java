package com.ardais.bigr.es.beans;

/**
 * EJSFinderArdaisuserBean
 */
public interface EJSFinderArdaisuserBean {
	/**
	 * findByUserId
	 */
	public com.ardais.bigr.es.beans.Ardaisuser findByUserId(java.lang.String userId) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findArdaisuserByArdaisaccount
	 */
	public com.ibm.ejs.persistence.EJSFinder findArdaisuserByArdaisaccount(com.ardais.bigr.es.beans.ArdaisaccountKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
