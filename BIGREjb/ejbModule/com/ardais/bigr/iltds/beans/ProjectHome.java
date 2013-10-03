package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ProjectHome extends javax.ejb.EJBHome {

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Project
 * @param argProjectid java.lang.String
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Project create(java.lang.String argProjectid) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Project
 * @param argProjectid java.lang.String
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Project create(java.lang.String argProjectid, java.lang.String projectName, java.lang.String client, java.lang.String requestedBy, java.sql.Timestamp requestDate) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.iltds.beans.Project
 * @param key com.ardais.bigr.iltds.beans.ProjectKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.iltds.beans.Project findByPrimaryKey(com.ardais.bigr.iltds.beans.ProjectKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
