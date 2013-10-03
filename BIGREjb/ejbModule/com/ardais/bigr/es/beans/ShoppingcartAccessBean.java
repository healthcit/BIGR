package com.ardais.bigr.es.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ShoppingcartAccessBean
 * @generated
 */
public class ShoppingcartAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.es.beans.ShoppingcartAccessBeanData {
  /**
   * @generated
   */
  private Shoppingcart __ejbRef;
  /**
   * @generated
   */
  private java.math.BigDecimal init_shopping_cart_id;
  /**
   * @generated
   */
  private java.lang.String init_userId;
  /**
   * @generated
   */
  private java.lang.String init_acctId;
  /**
   * getCart_create_date
   * @generated
   */
  public java.sql.Timestamp getCart_create_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("cart_create_date")));
  }
  /**
   * setCart_create_date
   * @generated
   */
  public void setCart_create_date(java.sql.Timestamp newValue) {
    __setCache("cart_create_date", newValue);
  }
  /**
   * getLast_update_dt
   * @generated
   */
  public java.sql.Timestamp getLast_update_dt()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("last_update_dt")));
  }
  /**
   * setLast_update_dt
   * @generated
   */
  public void setLast_update_dt(java.sql.Timestamp newValue) {
    __setCache("last_update_dt", newValue);
  }
  /**
   * getArdaisuserKey
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisuserKey getArdaisuserKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.es.beans.ArdaisuserKey) __getCache("ardaisuserKey")));
  }
  /**
   * setInit_shopping_cart_id
   * @generated
   */
  public void setInit_shopping_cart_id(java.math.BigDecimal newValue) {
    this.init_shopping_cart_id = (newValue);
  }
  /**
   * ShoppingcartAccessBean
   * @generated
   */
  public ShoppingcartAccessBean() {
    super();
  }
  /**
   * ShoppingcartAccessBean
   * @generated
   */
  public ShoppingcartAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "ejb/com/ardais/bigr/es/beans/ShoppingcartHome";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.es.beans.ShoppingcartHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ShoppingcartHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.es.beans.ShoppingcartHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.es.beans.Shoppingcart ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.es.beans.Shoppingcart) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.es.beans.Shoppingcart.class);

    return __ejbRef;
  }
  /**
   * instantiateEJB
   * @generated
   */
  protected void instantiateEJB()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    if (ejbRef() != null)
      return;

    ejbRef = ejbHome().create(init_shopping_cart_id, init_userId, init_acctId);
  }
  /**
   * instantiateEJBByPrimaryKey
   * @generated
   */
  protected boolean instantiateEJBByPrimaryKey()
    throws javax.ejb.CreateException,
    java.rmi.RemoteException,
    javax.naming.NamingException {
    boolean result = false;

    if (ejbRef() != null)
      return true;

    try {
      com.ardais.bigr.es.beans.ShoppingcartKey pKey = (com.ardais.bigr.es.beans.ShoppingcartKey) this
        .__getKey();
      if (pKey != null) {
        ejbRef = ejbHome().findByPrimaryKey(pKey);
        result = true;
      }
    } catch (javax.ejb.FinderException e) {
    }
    return result;
  }
  /**
   * refreshCopyHelper
   * @generated
   */
  public void refreshCopyHelper()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    refreshCopyHelper(ejbRef());
  }
  /**
   * commitCopyHelper
   * @generated
   */
  public void commitCopyHelper()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    commitCopyHelper(ejbRef());
  }
  /**
   * ShoppingcartAccessBean
   * @generated
   */
  public ShoppingcartAccessBean(com.ardais.bigr.es.beans.ShoppingcartKey primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * findShoppingcartByArdaisuser
   * @generated
   */
  public java.util.Enumeration findShoppingcartByArdaisuser(
    com.ardais.bigr.es.beans.ArdaisuserKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ShoppingcartHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findShoppingcartByArdaisuser(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * getShoppingcartdetail
   * @generated
   */
  public java.util.Enumeration getShoppingcartdetail()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getShoppingcartdetail();
  }
  /**
   * secondaryAddShoppingcartdetail
   * @generated
   */
  public void secondaryAddShoppingcartdetail(
    com.ardais.bigr.es.beans.Shoppingcartdetail aShoppingcartdetail)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddShoppingcartdetail(aShoppingcartdetail);
  }
  /**
   * getArdaisuser
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisuserAccessBean getArdaisuser()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.es.beans.Ardaisuser localEJBRef = ejbRef().getArdaisuser();
    if (localEJBRef != null)
      return new com.ardais.bigr.es.beans.ArdaisuserAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * secondaryRemoveShoppingcartdetail
   * @generated
   */
  public void secondaryRemoveShoppingcartdetail(
    com.ardais.bigr.es.beans.Shoppingcartdetail aShoppingcartdetail)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveShoppingcartdetail(aShoppingcartdetail);
  }
  /**
   * findByUserAccount
   * @generated
   */
  public java.util.Enumeration findByUserAccount(java.lang.String user, java.lang.String account)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ShoppingcartHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByUserAccount(user, account);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * setInit_userId
   * @generated
   */
  public void setInit_userId(java.lang.String newValue) {
    this.init_userId = (newValue);
  }
  /**
   * setInit_acctId
   * @generated
   */
  public void setInit_acctId(java.lang.String newValue) {
    this.init_acctId = (newValue);
  }
  /**
   * ShoppingcartAccessBean
   * @generated
   */
  public ShoppingcartAccessBean(
    java.math.BigDecimal shopping_cart_id,
    com.ardais.bigr.es.beans.Ardaisuser argArdaisuser) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(shopping_cart_id, argArdaisuser);
  }
}
