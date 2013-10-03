package com.ardais.bigr.iltds.beans;
/**
 * SamplestatusAccessBeanData
 * @generated
 */
public interface SamplestatusAccessBeanData {
	/**
	 * getStatus_type_code
	 * @generated
	 */
	public java.lang.String getStatus_type_code()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * setStatus_type_code
	 * @generated
	 */
	public void setStatus_type_code(java.lang.String newValue);
	/**
	 * getSampleKey
	 * @generated
	 */
	public com.ardais.bigr.iltds.beans.SampleKey getSampleKey()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * getSample_status_datetime
	 * @generated
	 */
	public java.sql.Timestamp getSample_status_datetime()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * setSample_status_datetime
	 * @generated
	 */
	public void setSample_status_datetime(java.sql.Timestamp newValue);
}
