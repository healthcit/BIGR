package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Revokedconsent
 */
public interface Revokedconsent extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {




/**
 * 
 * @return java.lang.String
 */
java.lang.String getArdais_id() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getConsent_id() throws java.rmi.RemoteException;
/**
 * Getter method for revoked_by_staff_id
 * @return java.lang.String
 */
java.lang.String getRevoked_by_staff_id() throws java.rmi.RemoteException;
/**
 * Getter method for revoked_reason
 * @return java.lang.String
 */
java.lang.String getRevoked_reason() throws java.rmi.RemoteException;
/**
 * Getter method for revoked_timestamp
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getRevoked_timestamp() throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newArdais_id java.lang.String
 */
void setArdais_id(java.lang.String newArdais_id) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newConsent_id java.lang.String
 */
void setConsent_id(java.lang.String newConsent_id) throws java.rmi.RemoteException;
/**
 * Setter method for revoked_by_staff_id
 * @param newValue java.lang.String
 */
void setRevoked_by_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for revoked_reason
 * @param newValue java.lang.String
 */
void setRevoked_reason(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for revoked_timestamp
 * @param newValue java.sql.Timestamp
 */
void setRevoked_timestamp(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
}
