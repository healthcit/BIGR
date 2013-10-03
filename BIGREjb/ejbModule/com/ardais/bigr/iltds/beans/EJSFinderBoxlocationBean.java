package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderBoxlocationBean
 */
public interface EJSFinderBoxlocationBean {
	/**
	 * findBoxlocationBySamplebox
	 */
	public com.ibm.ejs.persistence.EJSFinder findBoxlocationBySamplebox(com.ardais.bigr.iltds.beans.SampleboxKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findBoxlocationByGeolocation
	 */
	public com.ibm.ejs.persistence.EJSFinder findBoxlocationByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findBoxLocationByBoxId
	 */
	public com.ibm.ejs.persistence.EJSFinder findBoxLocationByBoxId(java.lang.String box) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findBoxLocationByStorageTypeCid
	 */
	public com.ibm.ejs.persistence.EJSFinder findBoxLocationByStorageTypeCid(java.lang.String locationAddressId, java.lang.String storageTypeCid, java.lang.String availableInd) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
