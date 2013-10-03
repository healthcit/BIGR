package com.ardais.bigr.orm.beans;

/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface Useraccessmodule extends com.ibm.ivj.ejb.runtime.CopyHelper, javax.ejb.EJBObject {
/**
 * Getter method for create_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getCreate_date() throws java.rmi.RemoteException;
/**
 * Getter method for created_by
 * @return java.lang.String
 */
java.lang.String getCreated_by() throws java.rmi.RemoteException;
/**
 * Getter method for description
 * @return java.lang.String
 */
java.lang.String getDescription() throws java.rmi.RemoteException;
/**
 * Getter method for new_order_creation
 * @return java.lang.String
 */
java.lang.String getNew_order_creation() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.orm.beans.Objects
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.orm.beans.Objects getObjects() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.orm.beans.ObjectsKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.orm.beans.ObjectsKey getObjectsKey() throws java.rmi.RemoteException;
/**
 * Getter method for order_maintain
 * @return java.lang.String
 */
java.lang.String getOrder_maintain() throws java.rmi.RemoteException;
/**
 * Getter method for order_view
 * @return java.lang.String
 */
java.lang.String getOrder_view() throws java.rmi.RemoteException;
/**
 * Getter method for update_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getUpdate_date() throws java.rmi.RemoteException;
/**
 * Getter method for updated_by
 * @return java.lang.String
 */
java.lang.String getUpdated_by() throws java.rmi.RemoteException;
/**
 * Setter method for create_date
 * @param newValue java.sql.Timestamp
 */
void setCreate_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for created_by
 * @param newValue java.lang.String
 */
void setCreated_by(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for description
 * @param newValue java.lang.String
 */
void setDescription(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for new_order_creation
 * @param newValue java.lang.String
 */
void setNew_order_creation(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for order_maintain
 * @param newValue java.lang.String
 */
void setOrder_maintain(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for order_view
 * @param newValue java.lang.String
 */
void setOrder_view(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for update_date
 * @param newValue java.sql.Timestamp
 */
void setUpdate_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for updated_by
 * @param newValue java.lang.String
 */
void setUpdated_by(java.lang.String newValue) throws java.rmi.RemoteException;
}
