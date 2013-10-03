package com.ardais.bigr.es.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ArdaisorderFactory
 * @generated
 */
public class ArdaisorderFactory extends AbstractEJBFactory {
  /**
   * ArdaisorderFactory
   * @generated
   */
  public ArdaisorderFactory() {
    super();
  }
  /**
   * _acquireArdaisorderHome
   * @generated
   */
  protected com.ardais.bigr.es.beans.ArdaisorderHome _acquireArdaisorderHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.es.beans.ArdaisorderHome) _acquireEJBHome();
  }
  /**
   * acquireArdaisorderHome
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisorderHome acquireArdaisorderHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ArdaisorderHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/es/beans/Ardaisorder";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.es.beans.ArdaisorderHome.class;
  }
  /**
   * resetArdaisorderHome
   * @generated
   */
  public void resetArdaisorderHome() {
    resetEJBHome();
  }
  /**
   * setArdaisorderHome
   * @generated
   */
  public void setArdaisorderHome(com.ardais.bigr.es.beans.ArdaisorderHome home) {
    setEJBHome(home);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisorder create(
    java.math.BigDecimal order_number,
    com.ardais.bigr.es.beans.Ardaisuser argArdaisuser)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireArdaisorderHome().create(order_number, argArdaisuser);
  }
  /**
   * findByAccount
   * @generated
   */
  public java.util.Enumeration findByAccount(java.lang.String arg_accountID)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisorderHome().findByAccount(arg_accountID);
  }
  /**
   * findUserOrderHistory
   * @generated
   */
  public java.util.Enumeration findUserOrderHistory(java.lang.String user, java.lang.String account)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisorderHome().findUserOrderHistory(user, account);
  }
  /**
   * findByOrderStatus
   * @generated
   */
  public java.util.Enumeration findByOrderStatus(java.lang.String ardOrder_status)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisorderHome().findByOrderStatus(ardOrder_status);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisorder create(
    java.math.BigDecimal argOrder_number,
    com.ardais.bigr.es.beans.ArdaisuserKey argArdaisuser)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireArdaisorderHome().create(argOrder_number, argArdaisuser);
  }
  /**
   * findUserOpenOrders
   * @generated
   */
  public java.util.Enumeration findUserOpenOrders(java.lang.String user, java.lang.String account)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisorderHome().findUserOpenOrders(user, account);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisorder findByPrimaryKey(
    com.ardais.bigr.es.beans.ArdaisorderKey primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireArdaisorderHome().findByPrimaryKey(primaryKey);
  }
  /**
   * findAllOrders
   * @generated
   */
  public java.util.Enumeration findAllOrders()
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisorderHome().findAllOrders();
  }
  /**
   * findAccountOrderHistory
   * @generated
   */
  public java.util.Enumeration findAccountOrderHistory(java.lang.String accountID)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisorderHome().findAccountOrderHistory(accountID);
  }
  /**
   * findByOrderNumber
   * @generated
   */
  public java.util.Enumeration findByOrderNumber(java.lang.String orderID)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisorderHome().findByOrderNumber(orderID);
  }
  /**
   * findAccountOrderswithStatus
   * @generated
   */
  public java.util.Enumeration findAccountOrderswithStatus(
    java.lang.String arg_account,
    java.lang.String arg_status) throws java.rmi.RemoteException, javax.ejb.FinderException {
    return _acquireArdaisorderHome().findAccountOrderswithStatus(arg_account, arg_status);
  }
  /**
   * findArdaisorderByArdaisuser
   * @generated
   */
  public java.util.Enumeration findArdaisorderByArdaisuser(
    com.ardais.bigr.es.beans.ArdaisuserKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisorderHome().findArdaisorderByArdaisuser(inKey);
  }
}
