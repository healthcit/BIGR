package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface SampleboxHome extends javax.ejb.EJBHome {

	/**
	 * Creates an instance from a key for Entity Bean: Samplebox
	 */
	public com.ardais.bigr.iltds.beans.Samplebox create(
		java.lang.String box_barcode_id)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.iltds.beans.Samplebox
 * @param key com.ardais.bigr.iltds.beans.SampleboxKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.iltds.beans.Samplebox findByPrimaryKey(com.ardais.bigr.iltds.beans.SampleboxKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Samplebox REFILTDS_MANIFEST15 Manifest.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @param inKey com.ardais.bigr.iltds.beans.ManifestKey
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration findSampleboxByManifest(com.ardais.bigr.iltds.beans.ManifestKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
