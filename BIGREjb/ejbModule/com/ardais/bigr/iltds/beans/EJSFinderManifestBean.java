package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderManifestBean
 */
public interface EJSFinderManifestBean {
	/**
	 * findByWaybill
	 */
	public com.ibm.ejs.persistence.EJSFinder findByWaybill(java.lang.String waybill) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findByBoxID
	 */
	public com.ibm.ejs.persistence.EJSFinder findByBoxID(java.lang.String boxID) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
