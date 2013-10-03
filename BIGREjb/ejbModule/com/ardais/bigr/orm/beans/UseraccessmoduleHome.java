package com.ardais.bigr.orm.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface UseraccessmoduleHome extends javax.ejb.EJBHome {

/**
 * 
 * @return com.ardais.bigr.orm.beans.Useraccessmodule
 * @param argArdais_acct_key java.lang.String
 * @param argArdais_user_id java.lang.String
 * @param argObjects com.ardais.bigr.orm.beans.ObjectsKey
 * @param argCreated_by java.lang.String
 * @param argCreate_date java.sql.Timestamp
 * @param argUpdated_by java.lang.String
 * @param argUpdate_date java.sql.Timestamp
 */
com.ardais.bigr.orm.beans.Useraccessmodule create(java.lang.String argArdais_acct_key, java.lang.String argArdais_user_id, com.ardais.bigr.orm.beans.ObjectsKey argObjects, java.lang.String argCreated_by, java.sql.Timestamp argCreate_date, java.lang.String argUpdated_by, java.sql.Timestamp argUpdate_date) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.orm.beans.Useraccessmodule
 * @param key com.ardais.bigr.orm.beans.UseraccessmoduleKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.orm.beans.Useraccessmodule findByPrimaryKey(com.ardais.bigr.orm.beans.UseraccessmoduleKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @param inKey com.ardais.bigr.orm.beans.ObjectsKey
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration findUseraccessmoduleByObjects(com.ardais.bigr.orm.beans.ObjectsKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
