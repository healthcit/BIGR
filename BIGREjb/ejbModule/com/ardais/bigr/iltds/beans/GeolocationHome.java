package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface GeolocationHome extends javax.ejb.EJBHome {

/**
 * 
 * @return com.ardais.bigr.iltds.beans.Geolocation
 * @param locAsst com.ardais.bigr.iltds.assistants.LocationManagementAsst
 */
com.ardais.bigr.iltds.beans.Geolocation create(com.ardais.bigr.iltds.assistants.LocationManagementAsst locAsst) throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * Creates an instance from a key for Entity Bean: Geolocation
	 */
	public com.ardais.bigr.iltds.beans.Geolocation create(
		java.lang.String location_address_id)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.iltds.beans.Geolocation
 * @param key com.ardais.bigr.iltds.beans.GeolocationKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.iltds.beans.Geolocation findByPrimaryKey(com.ardais.bigr.iltds.beans.GeolocationKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
