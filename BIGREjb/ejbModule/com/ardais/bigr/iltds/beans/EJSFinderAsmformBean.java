package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderAsmformBean
 */
public interface EJSFinderAsmformBean {
	/**
	 * findAsmformByConsent
	 */
	public com.ibm.ejs.persistence.EJSFinder findAsmformByConsent(com.ardais.bigr.iltds.beans.ConsentKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findByASMFormID
	 */
	public com.ibm.ejs.persistence.EJSFinder findByASMFormID(java.lang.String asmFormID) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
