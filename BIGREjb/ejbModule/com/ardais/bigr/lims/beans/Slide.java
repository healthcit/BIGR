package com.ardais.bigr.lims.beans;
/**
 * Remote interface for Enterprise Bean: Slide
 */
public interface Slide extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {





	/**
	 * Get accessor for persistent attribute: createDate
	 */
	public java.sql.Timestamp getCreateDate() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: createDate
	 */
	public void setCreateDate(java.sql.Timestamp newCreateDate)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: destroyDate
	 */
	public java.sql.Timestamp getDestroyDate() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: destroyDate
	 */
	public void setDestroyDate(java.sql.Timestamp newDestroyDate)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: sectionNumber
	 */
	public int getSectionNumber() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: sectionNumber
	 */
	public void setSectionNumber(int newSectionNumber)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: sectionLevel
	 */
	public int getSectionLevel() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: sectionLevel
	 */
	public void setSectionLevel(int newSectionLevel)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: currentLocation
	 */
	public java.lang.String getCurrentLocation()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: currentLocation
	 */
	public void setCurrentLocation(java.lang.String newCurrentLocation)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: sampleBarcodeId
	 */
	public java.lang.String getSampleBarcodeId()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: sampleBarcodeId
	 */
	public void setSampleBarcodeId(java.lang.String newSampleBarcodeId)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: createUser
	 */
	public java.lang.String getCreateUser() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: createUser
	 */
	public void setCreateUser(java.lang.String newCreateUser)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: sectionProc
	 */
	public java.lang.String getSectionProc() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: sectionProc
	 */
	public void setSectionProc(java.lang.String newSectionProc)
		throws java.rmi.RemoteException;
}
