package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Session Bean
 */
public interface ListGeneratorHome extends javax.ejb.EJBHome {

/**
 * create method for a session bean
 * @return com.ardais.bigr.iltds.beans.ListGenerator
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.ListGenerator create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
