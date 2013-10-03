package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Samplebox
 */
public interface Samplebox extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {




/**
 * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSample com.ardais.bigr.iltds.beans.Sample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void addSample(com.ardais.bigr.iltds.beans.Sample aSample) throws java.rmi.RemoteException;
/**
 * Getter method for box_check_in_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getBox_check_in_date() throws java.rmi.RemoteException;
/**
 * Getter method for box_check_out_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getBox_check_out_date() throws java.rmi.RemoteException;
/**
 * Getter method for box_check_out_reason
 * @return java.lang.String
 */
java.lang.String getBox_check_out_reason() throws java.rmi.RemoteException;
/**
 * Getter method for box_checkout_request_staff_id
 * @return java.lang.String
 */
java.lang.String getBox_checkout_request_staff_id() throws java.rmi.RemoteException;
/**
 * Getter method for box_note
 * @return java.lang.String
 */
java.lang.String getBox_note() throws java.rmi.RemoteException;
/**
 * Getter method for box_status
 * @return java.lang.String
 */
java.lang.String getBox_status() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.Manifest
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.Manifest getManifest() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.ManifestKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.ManifestKey getManifestKey() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration getSample() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param inKey com.ardais.bigr.iltds.beans.ManifestKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void privateSetManifestKey(com.ardais.bigr.iltds.beans.ManifestKey inKey) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSample com.ardais.bigr.iltds.beans.Sample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void removeSample(com.ardais.bigr.iltds.beans.Sample aSample) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSample com.ardais.bigr.iltds.beans.Sample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryAddSample(com.ardais.bigr.iltds.beans.Sample aSample) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSample com.ardais.bigr.iltds.beans.Sample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryRemoveSample(com.ardais.bigr.iltds.beans.Sample aSample) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aManifest com.ardais.bigr.iltds.beans.Manifest
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondarySetManifest(com.ardais.bigr.iltds.beans.Manifest aManifest) throws java.rmi.RemoteException;
/**
 * Setter method for box_check_in_date
 * @param newValue java.sql.Timestamp
 */
void setBox_check_in_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for box_check_out_date
 * @param newValue java.sql.Timestamp
 */
void setBox_check_out_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for box_check_out_reason
 * @param newValue java.lang.String
 */
void setBox_check_out_reason(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for box_checkout_request_staff_id
 * @param newValue java.lang.String
 */
void setBox_checkout_request_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for box_note
 * @param newValue java.lang.String
 */
void setBox_note(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for box_status
 * @param newValue java.lang.String
 */
void setBox_status(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aManifest com.ardais.bigr.iltds.beans.Manifest
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void setManifest(com.ardais.bigr.iltds.beans.Manifest aManifest) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named boxlocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddBoxlocation(
		com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named boxlocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveBoxlocation(
		com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named boxlocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getBoxlocation()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the relationship role named boxlocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void addBoxlocation(
		com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named boxlocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void removeBoxlocation(
		com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: manifest_order
	 */
	public java.math.BigDecimal getManifest_order()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: manifest_order
	 */
	public void setManifest_order(java.math.BigDecimal newManifest_order)
		throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: boxLayoutId
   */
  public java.lang.String getBoxLayoutId() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: boxLayoutId
   */
  public void setBoxLayoutId(java.lang.String newBoxLayoutId) throws java.rmi.RemoteException;
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
