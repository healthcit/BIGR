package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Session Bean
 */
public interface ASMOperationHome extends javax.ejb.EJBHome {

/**
 * create method for a session bean
 * @return com.ardais.bigr.iltds.ejb.ASMOperation
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.ASMOperation create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
