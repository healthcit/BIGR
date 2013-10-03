package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ManifestHome extends javax.ejb.EJBHome {

	/**
	 * Creates an instance from a key for Entity Bean: Manifest
	 */
	public com.ardais.bigr.iltds.beans.Manifest create(
		java.lang.String manifest_number)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * Insert the method's description here.
 * Creation date: (2/7/01 10:14:45 AM)
 * @return java.util.Enumeration
 * @param boxID java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findByBoxID(String boxID) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.iltds.beans.Manifest
 * @param key com.ardais.bigr.iltds.beans.ManifestKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.iltds.beans.Manifest findByPrimaryKey(com.ardais.bigr.iltds.beans.ManifestKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Insert the method's description here.
 * Creation date: (2/7/01 3:48:47 PM)
 * @return java.util.Enumeration
 * @param waybill java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findByWaybill(String waybill) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
