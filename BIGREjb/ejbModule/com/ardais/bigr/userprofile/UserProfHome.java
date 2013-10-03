package com.ardais.bigr.userprofile;

/**
 * This is a Home interface for the Session Bean
 */
public interface UserProfHome extends javax.ejb.EJBHome {

/**
 * create method for a session bean
 * @return com.ardais.bigr.userprofile.UserProf
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.userprofile.UserProf create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
