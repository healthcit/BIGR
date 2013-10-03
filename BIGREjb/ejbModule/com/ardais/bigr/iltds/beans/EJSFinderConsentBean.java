package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderConsentBean
 */
public interface EJSFinderConsentBean {
	/**
	 * findConsentByGeolocation
	 */
	public com.ibm.ejs.persistence.EJSFinder findConsentByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findConsentByArdaisID
	 */
	public com.ibm.ejs.persistence.EJSFinder findConsentByArdaisID(java.lang.String consentID) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findByConsentID
	 */
	public com.ibm.ejs.persistence.EJSFinder findByConsentID(java.lang.String consentID) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
