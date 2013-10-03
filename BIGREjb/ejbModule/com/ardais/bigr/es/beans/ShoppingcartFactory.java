package com.ardais.bigr.es.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ShoppingcartFactory
 * @generated
 */
public class ShoppingcartFactory extends AbstractEJBFactory {
  /**
   * ShoppingcartFactory
   * @generated
   */
  public ShoppingcartFactory() {
    super();
  }
  /**
   * _acquireShoppingcartHome
   * @generated
   */
  protected com.ardais.bigr.es.beans.ShoppingcartHome _acquireShoppingcartHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.es.beans.ShoppingcartHome) _acquireEJBHome();
  }
  /**
   * acquireShoppingcartHome
   * @generated
   */
  public com.ardais.bigr.es.beans.ShoppingcartHome acquireShoppingcartHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ShoppingcartHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "ejb/com/ardais/bigr/es/beans/ShoppingcartHome";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.es.beans.ShoppingcartHome.class;
  }
  /**
   * resetShoppingcartHome
   * @generated
   */
  public void resetShoppingcartHome() {
    resetEJBHome();
  }
  /**
   * setShoppingcartHome
   * @generated
   */
  public void setShoppingcartHome(com.ardais.bigr.es.beans.ShoppingcartHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.es.beans.Shoppingcart findByPrimaryKey(
    com.ardais.bigr.es.beans.ShoppingcartKey primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireShoppingcartHome().findByPrimaryKey(primaryKey);
  }
  /**
   * findShoppingcartByArdaisuser
   * @generated
   */
  public java.util.Enumeration findShoppingcartByArdaisuser(
    com.ardais.bigr.es.beans.ArdaisuserKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireShoppingcartHome().findShoppingcartByArdaisuser(inKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Shoppingcart create(
    java.math.BigDecimal shopping_cart_id,
    com.ardais.bigr.es.beans.Ardaisuser argArdaisuser)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireShoppingcartHome().create(shopping_cart_id, argArdaisuser);
  }
  /**
   * findByUserAccount
   * @generated
   */
  public java.util.Enumeration findByUserAccount(java.lang.String user, java.lang.String account)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireShoppingcartHome().findByUserAccount(user, account);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Shoppingcart create(
    java.math.BigDecimal shopping_cart_id,
    java.lang.String userId,
    java.lang.String acctId) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireShoppingcartHome().create(shopping_cart_id, userId, acctId);
  }
}
