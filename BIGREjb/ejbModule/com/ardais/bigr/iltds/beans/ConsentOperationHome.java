package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Session Bean
 */
public interface ConsentOperationHome extends javax.ejb.EJBHome {

/**
 * create method for a session bean
 * @return com.ardais.bigr.iltds.ejb.ConsentOperation
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.ConsentOperation create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
