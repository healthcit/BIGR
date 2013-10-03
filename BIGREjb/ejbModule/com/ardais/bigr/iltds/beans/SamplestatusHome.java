package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface SamplestatusHome extends javax.ejb.EJBHome {

/**
 * 
 * @return com.ardais.bigr.iltds.beans.Samplestatus
 * @param argStatus_type_code java.lang.String
 * @param argSample com.ardais.bigr.iltds.beans.SampleKey
 * @param argSample_status_datetime java.sql.Timestamp
 */
com.ardais.bigr.iltds.beans.Samplestatus create(java.lang.String argStatus_type_code, com.ardais.bigr.iltds.beans.SampleKey argSample, java.sql.Timestamp argSample_status_datetime) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.iltds.beans.Samplestatus
 * @param argStatus_type_code java.lang.String
 * @param argSample com.ardais.bigr.iltds.beans.SampleKey
 * @param argSample_status_datetime java.sql.Timestamp
 * @param argId java.lang.Integer
 */
com.ardais.bigr.iltds.beans.Samplestatus create(java.lang.String argStatus_type_code, com.ardais.bigr.iltds.beans.SampleKey argSample, java.sql.Timestamp argSample_status_datetime, java.math.BigDecimal argId) throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * Finds an instance using a key for Entity Bean: Samplestatus
	 */
	public com.ardais.bigr.iltds.beans.Samplestatus findByPrimaryKey(
		com.ardais.bigr.iltds.beans.SamplestatusKey primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
/**
 * Insert the method's description here.
 * Creation date: (3/15/01 4:49:15 PM)
 * @return java.util.Enumeration
 * @param sampleID java.lang.String
 * @param statusID java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findBySampleIDStatus(String sampleID, String statusID) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Samplestatus REFILTDS_SAMPLE25 Sample.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @param inKey com.ardais.bigr.iltds.beans.SampleKey
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public java.util.Enumeration findSamplestatusBySample(com.ardais.bigr.iltds.beans.SampleKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Creates an instance from a key for Entity Bean: Samplestatus
	 */
	public com.ardais.bigr.iltds.beans.Samplestatus create(
		java.math.BigDecimal id,
		com.ardais.bigr.iltds.beans.Sample argSample)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
