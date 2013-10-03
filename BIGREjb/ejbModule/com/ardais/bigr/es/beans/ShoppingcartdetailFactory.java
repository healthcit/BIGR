package com.ardais.bigr.es.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ShoppingcartdetailFactory
 * @generated
 */
public class ShoppingcartdetailFactory extends AbstractEJBFactory {
  /**
   * ShoppingcartdetailFactory
   * @generated
   */
  public ShoppingcartdetailFactory() {
    super();
  }
  /**
   * _acquireShoppingcartdetailHome
   * @generated
   */
  protected com.ardais.bigr.es.beans.ShoppingcartdetailHome _acquireShoppingcartdetailHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.es.beans.ShoppingcartdetailHome) _acquireEJBHome();
  }
  /**
   * acquireShoppingcartdetailHome
   * @generated
   */
  public com.ardais.bigr.es.beans.ShoppingcartdetailHome acquireShoppingcartdetailHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ShoppingcartdetailHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "ejb/com/ardais/bigr/es/beans/ShoppingcartdetailHome";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.es.beans.ShoppingcartdetailHome.class;
  }
  /**
   * resetShoppingcartdetailHome
   * @generated
   */
  public void resetShoppingcartdetailHome() {
    resetEJBHome();
  }
  /**
   * setShoppingcartdetailHome
   * @generated
   */
  public void setShoppingcartdetailHome(com.ardais.bigr.es.beans.ShoppingcartdetailHome home) {
    setEJBHome(home);
  }
  /**
   * findByUserAccountOrderByLineNumber
   * @generated
   */
  public java.util.Enumeration findByUserAccountOrderByLineNumber(
    java.lang.String user,
    java.lang.String account) throws java.rmi.RemoteException, javax.ejb.FinderException {
    return _acquireShoppingcartdetailHome().findByUserAccountOrderByLineNumber(user, account);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Shoppingcartdetail create(
    java.math.BigDecimal shopping_cart_line_number,
    com.ardais.bigr.es.beans.Shoppingcart argShoppingcart)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireShoppingcartdetailHome().create(shopping_cart_line_number, argShoppingcart);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Shoppingcartdetail create(
    java.lang.String barcode_id,
    java.lang.String productType,
    java.math.BigDecimal shopping_cart_line_number,
    java.lang.String userId,
    java.lang.String acctId,
    java.math.BigDecimal cartNo) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireShoppingcartdetailHome().create(
      barcode_id,
      productType,
      shopping_cart_line_number,
      userId,
      acctId,
      cartNo);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.es.beans.Shoppingcartdetail findByPrimaryKey(
    com.ardais.bigr.es.beans.ShoppingcartdetailKey primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireShoppingcartdetailHome().findByPrimaryKey(primaryKey);
  }
  /**
   * findShoppingcartdetailByShoppingcart
   * @generated
   */
  public java.util.Enumeration findShoppingcartdetailByShoppingcart(
    com.ardais.bigr.es.beans.ShoppingcartKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireShoppingcartdetailHome().findShoppingcartdetailByShoppingcart(inKey);
  }
  /**
   * findByBarcodeUserAccount
   * @generated
   */
  public java.util.Enumeration findByBarcodeUserAccount(
    java.lang.String barcode_arg,
    java.lang.String user_arg,
    java.lang.String account_arg) throws java.rmi.RemoteException, javax.ejb.FinderException {
    return _acquireShoppingcartdetailHome().findByBarcodeUserAccount(
      barcode_arg,
      user_arg,
      account_arg);
  }
}
