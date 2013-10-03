package com.ardais.bigr.pdc.beans;

/**
 * This is a Home interface for the Session Bean
 */
public interface DDCDonorHome extends javax.ejb.EJBHome {

/**
 * create method for a session bean
 * @return com.ardais.bigr.pdc.beans.DDCDonor
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.pdc.beans.DDCDonor create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
