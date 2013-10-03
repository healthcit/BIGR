package com.ardais.bigr.pdc.oce;
/**
 * Home interface for Enterprise Bean: Oce
 */
public interface OceHome extends javax.ejb.EJBHome {
	/**
	 * Creates a default instance of Session Bean: Oce
	 */
	public com.ardais.bigr.pdc.oce.Oce create()
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
