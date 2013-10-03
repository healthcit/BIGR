package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Ardaisstaff
 */
public interface Ardaisstaff extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {




/**
 * 
 * @return java.lang.String
 */
java.lang.String getArdais_acct_key() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_staff_fname
 * @return java.lang.String
 */
java.lang.String getArdais_staff_fname() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_staff_lname
 * @return java.lang.String
 */
java.lang.String getArdais_staff_lname() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getArdais_user_id() throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named geolocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.iltds.beans.Geolocation getGeolocation()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getGeolocation_location_address_id() throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named geolocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.iltds.beans.GeolocationKey getGeolocationKey()
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named geolocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void privateSetGeolocationKey(
		com.ardais.bigr.iltds.beans.GeolocationKey inKey)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named geolocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondarySetGeolocation(
		com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
		throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setArdais_acct_key(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_staff_fname
 * @param newValue java.lang.String
 */
void setArdais_staff_fname(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_staff_lname
 * @param newValue java.lang.String
 */
void setArdais_staff_lname(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setArdais_user_id(java.lang.String newValue) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named geolocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void setGeolocation(
		com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
		throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newGeolocation_location_address_id java.lang.String
 */
void setGeolocation_location_address_id(java.lang.String newGeolocation_location_address_id) throws java.rmi.RemoteException;
}
