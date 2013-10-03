package com.ardais.bigr.orm.beans;

/**
 * This is a Home interface for the Session Bean
 */
public interface OrmUserManagementHome extends javax.ejb.EJBHome {

/**
 * create method for a session bean
 * @return com.ardais.bigr.orm.beans.OrmUserManagement
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.orm.beans.OrmUserManagement create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
