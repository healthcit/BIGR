package com.ardais.bigr.es.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ArdaisorderstatusFactory
 * @generated
 */
public class ArdaisorderstatusFactory extends AbstractEJBFactory {
  /**
   * ArdaisorderstatusFactory
   * @generated
   */
  public ArdaisorderstatusFactory() {
    super();
  }
  /**
   * _acquireArdaisorderstatusHome
   * @generated
   */
  protected com.ardais.bigr.es.beans.ArdaisorderstatusHome _acquireArdaisorderstatusHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.es.beans.ArdaisorderstatusHome) _acquireEJBHome();
  }
  /**
   * acquireArdaisorderstatusHome
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisorderstatusHome acquireArdaisorderstatusHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ArdaisorderstatusHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/es/beans/Ardaisorderstatus";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.es.beans.ArdaisorderstatusHome.class;
  }
  /**
   * resetArdaisorderstatusHome
   * @generated
   */
  public void resetArdaisorderstatusHome() {
    resetEJBHome();
  }
  /**
   * setArdaisorderstatusHome
   * @generated
   */
  public void setArdaisorderstatusHome(com.ardais.bigr.es.beans.ArdaisorderstatusHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisorderstatus findByPrimaryKey(
    com.ardais.bigr.es.beans.ArdaisorderstatusKey primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireArdaisorderstatusHome().findByPrimaryKey(primaryKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisorderstatus create(
    java.sql.Timestamp order_status_date,
    java.lang.String order_status_id,
    java.lang.String ardais_acct_key,
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireArdaisorderstatusHome().create(
      order_status_date,
      order_status_id,
      ardais_acct_key,
      ardais_user_id,
      argArdaisorder);
  }
  /**
   * findArdaisorderstatusByArdaisorder
   * @generated
   */
  public java.util.Enumeration findArdaisorderstatusByArdaisorder(
    com.ardais.bigr.es.beans.ArdaisorderKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisorderstatusHome().findArdaisorderstatusByArdaisorder(inKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisorderstatus create(
    java.sql.Timestamp argOrder_status_date,
    java.lang.String argOrder_status_id,
    com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder,
    java.lang.String argArdais_acct_key,
    java.lang.String argArdais_user_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireArdaisorderstatusHome().create(
      argOrder_status_date,
      argOrder_status_id,
      argArdaisorder,
      argArdais_acct_key,
      argArdais_user_id);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisorderstatus create(
    java.lang.String ardais_acct_key,
    java.lang.String order_status_id,
    java.sql.Timestamp order_status_date,
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireArdaisorderstatusHome().create(
      ardais_acct_key,
      order_status_id,
      order_status_date,
      ardais_user_id,
      argArdaisorder);
  }
}
