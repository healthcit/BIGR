package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Manifest
 */
public interface Manifest extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {


/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void addSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox) throws java.rmi.RemoteException;
/**
 * Getter method for airbill_tracking_number
 * @return java.lang.String
 */
java.lang.String getAirbill_tracking_number() throws java.rmi.RemoteException;
/**
 * Getter method for mnft_create_datetime
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getMnft_create_datetime() throws java.rmi.RemoteException;
/**
 * Getter method for mnft_create_staff_id
 * @return java.lang.String
 */
java.lang.String getMnft_create_staff_id() throws java.rmi.RemoteException;
/**
 * Getter method for receipt_by_staff_id
 * @return java.lang.String
 */
java.lang.String getReceipt_by_staff_id() throws java.rmi.RemoteException;
/**
 * Getter method for receipt_datetime
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getReceipt_datetime() throws java.rmi.RemoteException;
/**
 * 
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getReceipt_verify_datetime() throws java.rmi.RemoteException;
/**
 * Getter method for receipt_verify_staff_id
 * @return java.lang.String
 */
java.lang.String getReceipt_verify_staff_id() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration getSamplebox() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Getter method for ship_datetime
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getShip_datetime() throws java.rmi.RemoteException;
/**
 * Getter method for ship_staff_id
 * @return java.lang.String
 */
java.lang.String getShip_staff_id() throws java.rmi.RemoteException;
/**
 * Getter method for ship_verify_datetime
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getShip_verify_datetime() throws java.rmi.RemoteException;
/**
 * Getter method for ship_verify_staff_id
 * @return java.lang.String
 */
java.lang.String getShip_verify_staff_id() throws java.rmi.RemoteException;
/**
 * Getter method for shipment_status
 * @return java.lang.String
 */
java.lang.String getShipment_status() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void removeSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryAddSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSamplebox com.ardais.bigr.iltds.beans.Samplebox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryRemoveSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox) throws java.rmi.RemoteException;
/**
 * Setter method for airbill_tracking_number
 * @param newValue java.lang.String
 */
void setAirbill_tracking_number(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for mnft_create_datetime
 * @param newValue java.sql.Timestamp
 */
void setMnft_create_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for mnft_create_staff_id
 * @param newValue java.lang.String
 */
void setMnft_create_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for receipt_by_staff_id
 * @param newValue java.lang.String
 */
void setReceipt_by_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for receipt_datetime
 * @param newValue java.sql.Timestamp
 */
void setReceipt_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.sql.Timestamp
 */
void setReceipt_verify_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for receipt_verify_staff_id
 * @param newValue java.lang.String
 */
void setReceipt_verify_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ship_datetime
 * @param newValue java.sql.Timestamp
 */
void setShip_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ship_staff_id
 * @param newValue java.lang.String
 */
void setShip_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ship_verify_datetime
 * @param newValue java.sql.Timestamp
 */
void setShip_verify_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ship_verify_staff_id
 * @param newValue java.lang.String
 */
void setShip_verify_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for shipment_status
 * @param newValue java.lang.String
 */
void setShipment_status(java.lang.String newValue) throws java.rmi.RemoteException;
}
