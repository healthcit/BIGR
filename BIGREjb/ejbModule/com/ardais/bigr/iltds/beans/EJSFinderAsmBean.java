package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderAsmBean
 */
public interface EJSFinderAsmBean {
	/**
	 * findByConsentID
	 */
	public com.ibm.ejs.persistence.EJSFinder findByConsentID(java.lang.String argConsentID) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
