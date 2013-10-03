package com.ardais.bigr.orm.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ObjectsFactory
 * @generated
 */
public class ObjectsFactory extends AbstractEJBFactory {
  /**
   * ObjectsFactory
   * @generated
   */
  public ObjectsFactory() {
    super();
  }
  /**
   * _acquireObjectsHome
   * @generated
   */
  protected com.ardais.bigr.orm.beans.ObjectsHome _acquireObjectsHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.orm.beans.ObjectsHome) _acquireEJBHome();
  }
  /**
   * acquireObjectsHome
   * @generated
   */
  public com.ardais.bigr.orm.beans.ObjectsHome acquireObjectsHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.orm.beans.ObjectsHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/orm/beans/Objects";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.orm.beans.ObjectsHome.class;
  }
  /**
   * resetObjectsHome
   * @generated
   */
  public void resetObjectsHome() {
    resetEJBHome();
  }
  /**
   * setObjectsHome
   * @generated
   */
  public void setObjectsHome(com.ardais.bigr.orm.beans.ObjectsHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.orm.beans.Objects findByPrimaryKey(com.ardais.bigr.orm.beans.ObjectsKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireObjectsHome().findByPrimaryKey(key);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.orm.beans.Objects create(java.lang.String object_id)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireObjectsHome().create(object_id);
  }
}
