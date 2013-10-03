package com.ardais.bigr.lims.beans;
/**
 * Home interface for Enterprise Bean: LIMSDataValidator
 */
public interface LimsDataValidatorHome extends javax.ejb.EJBHome {
	/**
	 * Creates a default instance of Session Bean: LIMSDataValidator
	 */
	public com.ardais.bigr.lims.beans.LimsDataValidator create()
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
