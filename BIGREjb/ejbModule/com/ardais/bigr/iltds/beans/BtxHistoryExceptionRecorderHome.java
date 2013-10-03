package com.ardais.bigr.iltds.beans;
/**
 * Home interface for Enterprise Bean: BtxHistoryExceptionRecorder
 */
public interface BtxHistoryExceptionRecorderHome extends javax.ejb.EJBHome {
    /**
     * Creates a default instance of Session Bean: BtxHistoryExceptionRecorder
     */
    public com.ardais.bigr.iltds.beans.BtxHistoryExceptionRecorder create()
        throws javax.ejb.CreateException, java.rmi.RemoteException;
}
