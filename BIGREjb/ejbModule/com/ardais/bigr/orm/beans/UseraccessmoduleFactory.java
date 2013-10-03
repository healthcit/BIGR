package com.ardais.bigr.orm.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * UseraccessmoduleFactory
 * @generated
 */
public class UseraccessmoduleFactory extends AbstractEJBFactory {
  /**
   * UseraccessmoduleFactory
   * @generated
   */
  public UseraccessmoduleFactory() {
    super();
  }
  /**
   * _acquireUseraccessmoduleHome
   * @generated
   */
  protected com.ardais.bigr.orm.beans.UseraccessmoduleHome _acquireUseraccessmoduleHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.orm.beans.UseraccessmoduleHome) _acquireEJBHome();
  }
  /**
   * acquireUseraccessmoduleHome
   * @generated
   */
  public com.ardais.bigr.orm.beans.UseraccessmoduleHome acquireUseraccessmoduleHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.orm.beans.UseraccessmoduleHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/orm/beans/Useraccessmodule";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.orm.beans.UseraccessmoduleHome.class;
  }
  /**
   * resetUseraccessmoduleHome
   * @generated
   */
  public void resetUseraccessmoduleHome() {
    resetEJBHome();
  }
  /**
   * setUseraccessmoduleHome
   * @generated
   */
  public void setUseraccessmoduleHome(com.ardais.bigr.orm.beans.UseraccessmoduleHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.orm.beans.Useraccessmodule findByPrimaryKey(
    com.ardais.bigr.orm.beans.UseraccessmoduleKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireUseraccessmoduleHome().findByPrimaryKey(key);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.orm.beans.Useraccessmodule create(
    java.lang.String argArdais_acct_key,
    java.lang.String argArdais_user_id,
    com.ardais.bigr.orm.beans.ObjectsKey argObjects,
    java.lang.String argCreated_by,
    java.sql.Timestamp argCreate_date,
    java.lang.String argUpdated_by,
    java.sql.Timestamp argUpdate_date) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireUseraccessmoduleHome().create(
      argArdais_acct_key,
      argArdais_user_id,
      argObjects,
      argCreated_by,
      argCreate_date,
      argUpdated_by,
      argUpdate_date);
  }
  /**
   * findUseraccessmoduleByObjects
   * @generated
   */
  public java.util.Enumeration findUseraccessmoduleByObjects(
    com.ardais.bigr.orm.beans.ObjectsKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireUseraccessmoduleHome().findUseraccessmoduleByObjects(inKey);
  }
}
