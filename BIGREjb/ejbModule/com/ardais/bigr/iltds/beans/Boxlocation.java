package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Boxlocation
 */
public interface Boxlocation extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {














/**
 * Getter method for available_ind
 * @return java.lang.String
 */
java.lang.String getAvailable_ind() throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named geolocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.iltds.beans.Geolocation getGeolocation()
		throws java.rmi.RemoteException, javax.ejb.FinderException;

public java.lang.String getGeolocation_location_address_id() throws java.rmi.RemoteException;

	/**
	 * This method was generated for supporting the relationship role named geolocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.iltds.beans.GeolocationKey getGeolocationKey()
		throws java.rmi.RemoteException;
/**
 * Getter method for location_status
 * @return java.lang.String
 */
java.lang.String getLocation_status() throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named samplebox.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.iltds.beans.Samplebox getSamplebox()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getSamplebox_box_barcode_id() throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named samplebox.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.iltds.beans.SampleboxKey getSampleboxKey()
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named samplebox.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void privateSetSampleboxKey(
		com.ardais.bigr.iltds.beans.SampleboxKey inKey)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named samplebox.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondarySetSamplebox(
		com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
		throws java.rmi.RemoteException;
/**
 * Setter method for available_ind
 * @param newValue java.lang.String
 */
void setAvailable_ind(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for location_status
 * @param newValue java.lang.String
 */
void setLocation_status(java.lang.String newValue) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named samplebox.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void setSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
		throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newSamplebox_box_barcode_id java.lang.String
 */
void setSamplebox_box_barcode_id(java.lang.String newSamplebox_box_barcode_id) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: storageTypeCid
   */
  public java.lang.String getStorageTypeCid() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: storageTypeCid
   */
  public void setStorageTypeCid(java.lang.String newStorageTypeCid)
    throws java.rmi.RemoteException;
}
