package com.ardais.bigr.lims.beans;
/**
 * Home interface for Enterprise Bean: LimsOperation
 */
public interface LimsOperationHome extends javax.ejb.EJBHome {
	/**
	 * Creates a default instance of Session Bean: LimsOperation
	 */
	public com.ardais.bigr.lims.beans.LimsOperation create()
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
