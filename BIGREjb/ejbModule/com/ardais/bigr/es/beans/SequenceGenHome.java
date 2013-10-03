package com.ardais.bigr.es.beans;

/**
 * This is a Home interface for the Session Bean
 */
public interface SequenceGenHome extends javax.ejb.EJBHome {

/**
 * create method for a session bean
 * @return com.ardais.bigr.es.beans.SequenceGen
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.es.beans.SequenceGen create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
