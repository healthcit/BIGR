package com.ardais.bigr.es.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * OrderlineFactory
 * @generated
 */
public class OrderlineFactory extends AbstractEJBFactory {
  /**
   * OrderlineFactory
   * @generated
   */
  public OrderlineFactory() {
    super();
  }
  /**
   * _acquireOrderlineHome
   * @generated
   */
  protected com.ardais.bigr.es.beans.OrderlineHome _acquireOrderlineHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.es.beans.OrderlineHome) _acquireEJBHome();
  }
  /**
   * acquireOrderlineHome
   * @generated
   */
  public com.ardais.bigr.es.beans.OrderlineHome acquireOrderlineHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.OrderlineHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/es/beans/Orderline";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.es.beans.OrderlineHome.class;
  }
  /**
   * resetOrderlineHome
   * @generated
   */
  public void resetOrderlineHome() {
    resetEJBHome();
  }
  /**
   * setOrderlineHome
   * @generated
   */
  public void setOrderlineHome(com.ardais.bigr.es.beans.OrderlineHome home) {
    setEJBHome(home);
  }
  /**
   * findOrderlineByArdaisorder
   * @generated
   */
  public java.util.Enumeration findOrderlineByArdaisorder(
    com.ardais.bigr.es.beans.ArdaisorderKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireOrderlineHome().findOrderlineByArdaisorder(inKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Orderline create(
    java.math.BigDecimal argOrder_line_number,
    com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireOrderlineHome().create(argOrder_line_number, argArdaisorder);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.es.beans.Orderline findByPrimaryKey(
    com.ardais.bigr.es.beans.OrderlineKey primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireOrderlineHome().findByPrimaryKey(primaryKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Orderline create(
    java.math.BigDecimal order_line_number,
    com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireOrderlineHome().create(order_line_number, argArdaisorder);
  }
}
