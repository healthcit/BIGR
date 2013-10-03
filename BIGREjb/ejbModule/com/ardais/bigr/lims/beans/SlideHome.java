package com.ardais.bigr.lims.beans;
/**
 * Home interface for Enterprise Bean: Slide
 */
public interface SlideHome extends javax.ejb.EJBHome {
	/**
	 * Creates an instance from a key for Entity Bean: Slide
	 */
	public com.ardais.bigr.lims.beans.Slide create(java.lang.String slideId)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * Creates an instance from a key for Entity Bean: Slide
	 */
	public com.ardais.bigr.lims.beans.Slide create(java.lang.String slideId, java.sql.Timestamp createDate,
													java.lang.String sampleBarcodeId, java.lang.String createUser)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * Finds an instance using a key for Entity Bean: Slide
	 */
	public com.ardais.bigr.lims.beans.Slide findByPrimaryKey(
		java.lang.String primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
}
