package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ArdaisstaffHome extends javax.ejb.EJBHome {

	/**
	 * Creates an instance from a key for Entity Bean: Ardaisstaff
	 */
	public com.ardais.bigr.iltds.beans.Ardaisstaff create(
		java.lang.String ardais_staff_id,
		com.ardais.bigr.iltds.beans.Geolocation argGeolocation)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * Insert the method's description here.
 * Creation date: (2/6/01 2:55:03 PM)
 * @return java.util.Enumeration
 */
java.util.Enumeration findAllStaffMembers() throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the relationship role named geolocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration findArdaisstaffByGeolocation(
		com.ardais.bigr.iltds.beans.GeolocationKey inKey)
		throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.iltds.beans.Ardaisstaff
 * @param key com.ardais.bigr.iltds.beans.ArdaisstaffKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.iltds.beans.Ardaisstaff findByPrimaryKey(com.ardais.bigr.iltds.beans.ArdaisstaffKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Insert the method's description here.
 * Creation date: (2/20/01 1:29:56 PM)
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findLocByUserProf(String ardais_user_id, String ardais_acct_key) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
