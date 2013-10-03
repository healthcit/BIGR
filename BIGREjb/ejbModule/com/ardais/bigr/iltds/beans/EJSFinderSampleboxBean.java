package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderSampleboxBean
 */
public interface EJSFinderSampleboxBean {
	/**
	 * findSampleboxByManifest
	 */
	public com.ibm.ejs.persistence.EJSFinder findSampleboxByManifest(com.ardais.bigr.iltds.beans.ManifestKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
