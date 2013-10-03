package com.ardais.bigr.orm.beans;

/**
 * This is a Home interface for the Session Bean
 */
public interface AccountSrchSBHome extends javax.ejb.EJBHome {

/**
 * create method for a session bean
 * @return com.ardais.bigr.orm.beans.AccountSrchSB
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.orm.beans.AccountSrchSB create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
