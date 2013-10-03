package com.ardais.bigr.es.beans;

/**
 * EJSFinderArdaisorderstatusBean
 */
public interface EJSFinderArdaisorderstatusBean {
	/**
	 * findArdaisorderstatusByArdaisorder
	 */
	public com.ibm.ejs.persistence.EJSFinder findArdaisorderstatusByArdaisorder(com.ardais.bigr.es.beans.ArdaisorderKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
