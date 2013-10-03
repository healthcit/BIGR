package com.ardais.bigr.btx.framework;
/**
 * Home interface for Enterprise Bean: BtxTransaction
 */
public interface BtxTransactionHome extends javax.ejb.EJBHome {

  /**
   * Creates a default instance of Session Bean: BtxTransaction
   */
  public com.ardais.bigr.btx.framework.BtxTransaction create()
    throws javax.ejb.CreateException, java.rmi.RemoteException;
}
