package com.ardais.bigr.es.beans;

/**
 * EJSFinderOrderlineBean
 */
public interface EJSFinderOrderlineBean {
	/**
	 * findOrderlineByArdaisorder
	 */
	public com.ibm.ejs.persistence.EJSFinder findOrderlineByArdaisorder(com.ardais.bigr.es.beans.ArdaisorderKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
