package com.ardais.bigr.iltds.beans;
/**
 * Home interface for Enterprise Bean: SampleOperation
 */
public interface SampleOperationHome extends javax.ejb.EJBHome {
    /**
     * Creates a default instance of Session Bean: SampleOperation
     */
    public com.ardais.bigr.iltds.beans.SampleOperation create()
        throws javax.ejb.CreateException, java.rmi.RemoteException;
}
