package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface RevokedconsentHome extends javax.ejb.EJBHome {

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Revokedconsent
 * @param argConsent_id java.lang.String
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Revokedconsent create(java.lang.String argConsent_id) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.iltds.beans.Revokedconsent
 * @param argConsent_id java.lang.String
 * @param argArdais_id java.lang.String
 */
com.ardais.bigr.iltds.beans.Revokedconsent create(java.lang.String argConsent_id, java.lang.String argArdais_id) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.iltds.beans.Revokedconsent
 * @param key com.ardais.bigr.iltds.beans.RevokedconsentKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.iltds.beans.Revokedconsent findByPrimaryKey(com.ardais.bigr.iltds.beans.RevokedconsentKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
