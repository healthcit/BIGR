package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Samplestatus
 */
public interface Samplestatus extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {














/**
 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.Sample
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public com.ardais.bigr.iltds.beans.Sample getSample() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Getter method for sample_status_datetime
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getSample_status_datetime() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.SampleKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public com.ardais.bigr.iltds.beans.SampleKey getSampleKey() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getStatus_type_code() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
 * 	It will be deleted/edited when the association is deleted/edited.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void privateSetSampleKey(com.ardais.bigr.iltds.beans.SampleKey inKey) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
 * 	It will be deleted/edited when the association is deleted/edited.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondarySetSample(com.ardais.bigr.iltds.beans.Sample aSample) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
 * 	It will be deleted/edited when the association is deleted/edited.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void setSample(com.ardais.bigr.iltds.beans.Sample aSample) throws java.rmi.RemoteException;
/**
 * Setter method for sample_status_datetime
 * @param newValue java.sql.Timestamp
 */
void setSample_status_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setStatus_type_code(java.lang.String newValue) throws java.rmi.RemoteException;
}
