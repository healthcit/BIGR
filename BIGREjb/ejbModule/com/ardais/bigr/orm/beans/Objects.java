package com.ardais.bigr.orm.beans;

/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface Objects extends com.ibm.ivj.ejb.runtime.CopyHelper, javax.ejb.EJBObject {
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
 * Getter method for long_description
 * @return java.lang.String
 */
java.lang.String getLong_description() throws java.rmi.RemoteException;
/**
 * Getter method for object_description
 * @return java.lang.String
 */
java.lang.String getObject_description() throws java.rmi.RemoteException;
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
 * Getter method for url
 * @return java.lang.String
 */
java.lang.String getUrl() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration getUseraccessmodule() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param anUseraccessmodule com.ardais.bigr.orm.beans.Useraccessmodule
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryAddUseraccessmodule(com.ardais.bigr.orm.beans.Useraccessmodule anUseraccessmodule) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param anUseraccessmodule com.ardais.bigr.orm.beans.Useraccessmodule
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryRemoveUseraccessmodule(com.ardais.bigr.orm.beans.Useraccessmodule anUseraccessmodule) throws java.rmi.RemoteException;
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
 * Setter method for long_description
 * @param newValue java.lang.String
 */
void setLong_description(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for object_description
 * @param newValue java.lang.String
 */
void setObject_description(java.lang.String newValue) throws java.rmi.RemoteException;
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
/**
 * Setter method for url
 * @param newValue java.lang.String
 */
void setUrl(java.lang.String newValue) throws java.rmi.RemoteException;
}
