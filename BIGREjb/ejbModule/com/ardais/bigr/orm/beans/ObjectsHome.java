package com.ardais.bigr.orm.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ObjectsHome extends javax.ejb.EJBHome {

  /**
   * Creates an instance from a key for Entity Bean: Objects
   */
  public com.ardais.bigr.orm.beans.Objects create(java.lang.String object_id)
    throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.orm.beans.Objects
 * @param key com.ardais.bigr.orm.beans.ObjectsKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.orm.beans.Objects findByPrimaryKey(com.ardais.bigr.orm.beans.ObjectsKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;
}
