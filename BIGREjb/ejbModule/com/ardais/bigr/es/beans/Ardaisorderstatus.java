package com.ardais.bigr.es.beans;

/**
 * Remote interface for Enterprise Bean: Ardaisorderstatus
 */
public interface Ardaisorderstatus extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {






/**
 * This method was generated for supporting the association named Ardaisorderstatus-Ardaisorder.  
 * 	It will be deleted/edited when the association is deleted/edited.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public com.ardais.bigr.es.beans.Ardaisorder getArdaisorder() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Ardaisorderstatus-Ardaisorder.  
 * 	It will be deleted/edited when the association is deleted/edited.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public com.ardais.bigr.es.beans.ArdaisorderKey getArdaisorderKey() throws java.rmi.RemoteException;
/**
 * Getter method for order_status_comment
 * @return java.lang.String
 */
java.lang.String getOrder_status_comment() throws java.rmi.RemoteException;
/**
 * Setter method for order_status_comment
 * @param newValue java.lang.String
 */
void setOrder_status_comment(java.lang.String newValue) throws java.rmi.RemoteException;
}
