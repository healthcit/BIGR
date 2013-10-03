package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Geolocation
 */
public interface Geolocation extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {


	/**
	 * This method was generated for supporting the relationship role named ardaisstaff.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void addArdaisstaff(
		com.ardais.bigr.iltds.beans.Ardaisstaff anArdaisstaff)
		throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aConsent com.ardais.bigr.iltds.beans.Consent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void addConsent(com.ardais.bigr.iltds.beans.Consent aConsent) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisstaff.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getArdaisstaff()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the relationship role named boxlocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getBoxlocation()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration getConsent() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Getter method for location_address_1
 * @return java.lang.String
 */
java.lang.String getLocation_address_1() throws java.rmi.RemoteException;
/**
 * Getter method for location_address_2
 * @return java.lang.String
 */
java.lang.String getLocation_address_2() throws java.rmi.RemoteException;
/**
 * Getter method for location_city
 * @return java.lang.String
 */
java.lang.String getLocation_city() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getLocation_name() throws java.rmi.RemoteException;
/**
 * Getter method for location_phone
 * @return java.lang.String
 */
java.lang.String getLocation_phone() throws java.rmi.RemoteException;
/**
 * Getter method for location_state
 * @return java.lang.String
 */
java.lang.String getLocation_state() throws java.rmi.RemoteException;
/**
 * Getter method for location_zip
 * @return java.lang.String
 */
java.lang.String getLocation_zip() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aConsent com.ardais.bigr.iltds.beans.Consent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void removeConsent(com.ardais.bigr.iltds.beans.Consent aConsent) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisstaff.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddArdaisstaff(
		com.ardais.bigr.iltds.beans.Ardaisstaff anArdaisstaff)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named boxlocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddBoxlocation(
		com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
		throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aConsent com.ardais.bigr.iltds.beans.Consent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryAddConsent(com.ardais.bigr.iltds.beans.Consent aConsent) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisstaff.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveArdaisstaff(
		com.ardais.bigr.iltds.beans.Ardaisstaff anArdaisstaff)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named boxlocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveBoxlocation(
		com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
		throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aConsent com.ardais.bigr.iltds.beans.Consent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryRemoveConsent(com.ardais.bigr.iltds.beans.Consent aConsent) throws java.rmi.RemoteException;
/**
 * Setter method for location_address_1
 * @param newValue java.lang.String
 */
void setLocation_address_1(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for location_address_2
 * @param newValue java.lang.String
 */
void setLocation_address_2(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for location_city
 * @param newValue java.lang.String
 */
void setLocation_city(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setLocation_name(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for location_phone
 * @param newValue java.lang.String
 */
void setLocation_phone(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for location_state
 * @param newValue java.lang.String
 */
void setLocation_state(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for location_zip
 * @param newValue java.lang.String
 */
void setLocation_zip(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param locAsst com.ardais.bigr.iltds.assistants.LocationManagementAsst
 */
void updateGeoLoc(com.ardais.bigr.iltds.assistants.LocationManagementAsst locAsst) throws java.rmi.RemoteException, com.ardais.bigr.api.ApiException;
  /**
   * Get accessor for persistent attribute: location_country
   */
  public java.lang.String getLocation_country() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: location_country
   */
  public void setLocation_country(java.lang.String newLocation_country)
    throws java.rmi.RemoteException;
}
