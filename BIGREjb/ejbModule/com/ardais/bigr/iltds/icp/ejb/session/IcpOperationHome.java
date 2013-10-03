package com.ardais.bigr.iltds.icp.ejb.session;

/**
 * This is a Home interface for the Session Bean
 */
public interface IcpOperationHome extends javax.ejb.EJBHome {

/**
 * create method for a session bean
 * @return com.ardais.bigr.iltds.icp.ejb.session.IcpOperation
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.icp.ejb.session.IcpOperation create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
