package com.ardais.bigr.es.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ShoppingcartdetailAccessBean
 * @generated
 */
public class ShoppingcartdetailAccessBean
  extends AbstractEntityAccessBean
  implements com.ardais.bigr.es.beans.ShoppingcartdetailAccessBeanData {
  /**
   * @generated
   */
  private Shoppingcartdetail __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_barcode_id;
  /**
   * @generated
   */
  private java.lang.String init_productType;
  /**
   * @generated
   */
  private java.math.BigDecimal init_shopping_cart_line_number;
  /**
   * @generated
   */
  private java.lang.String init_userId;
  /**
   * @generated
   */
  private java.lang.String init_acctId;
  /**
   * @generated
   */
  private java.math.BigDecimal init_cartNo;
  /**
   * getBarcode_id
   * @generated
   */
  public java.lang.String getBarcode_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("barcode_id")));
  }
  /**
   * setBarcode_id
   * @generated
   */
  public void setBarcode_id(java.lang.String newValue) {
    __setCache("barcode_id", newValue);
  }
  /**
   * getQuantity
   * @generated
   */
  public java.lang.Integer getQuantity()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.Integer) __getCache("quantity")));
  }
  /**
   * setQuantity
   * @generated
   */
  public void setQuantity(java.lang.Integer newValue) {
    __setCache("quantity", newValue);
  }
  /**
   * getShoppingcartKey
   * @generated
   */
  public com.ardais.bigr.es.beans.ShoppingcartKey getShoppingcartKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.es.beans.ShoppingcartKey) __getCache("shoppingcartKey")));
  }
  /**
   * getProductType
   * @generated
   */
  public java.lang.String getProductType()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("productType")));
  }
  /**
   * setProductType
   * @generated
   */
  public void setProductType(java.lang.String newValue) {
    __setCache("productType", newValue);
  }
  /**
   * getShopping_cart_line_amount
   * @generated
   */
  public java.math.BigDecimal getShopping_cart_line_amount()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("shopping_cart_line_amount")));
  }
  /**
   * setShopping_cart_line_amount
   * @generated
   */
  public void setShopping_cart_line_amount(java.math.BigDecimal newValue) {
    __setCache("shopping_cart_line_amount", newValue);
  }
  /**
   * getCreation_dt
   * @generated
   */
  public java.sql.Timestamp getCreation_dt()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("creation_dt")));
  }
  /**
   * setCreation_dt
   * @generated
   */
  public void setCreation_dt(java.sql.Timestamp newValue) {
    __setCache("creation_dt", newValue);
  }
  /**
   * getSearch_desc
   * @generated
   */
  public java.lang.String getSearch_desc()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("search_desc")));
  }
  /**
   * setSearch_desc
   * @generated
   */
  public void setSearch_desc(java.lang.String newValue) {
    __setCache("search_desc", newValue);
  }
  /**
   * setInit_barcode_id
   * @generated
   */
  public void setInit_barcode_id(java.lang.String newValue) {
    this.init_barcode_id = (newValue);
  }
  /**
   * setInit_productType
   * @generated
   */
  public void setInit_productType(java.lang.String newValue) {
    this.init_productType = (newValue);
  }
  /**
   * setInit_shopping_cart_line_number
   * @generated
   */
  public void setInit_shopping_cart_line_number(java.math.BigDecimal newValue) {
    this.init_shopping_cart_line_number = (newValue);
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
   * setInit_cartNo
   * @generated
   */
  public void setInit_cartNo(java.math.BigDecimal newValue) {
    this.init_cartNo = (newValue);
  }
  /**
   * ShoppingcartdetailAccessBean
   * @generated
   */
  public ShoppingcartdetailAccessBean() {
    super();
  }
  /**
   * ShoppingcartdetailAccessBean
   * @generated
   */
  public ShoppingcartdetailAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "ejb/com/ardais/bigr/es/beans/ShoppingcartdetailHome";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.es.beans.ShoppingcartdetailHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ShoppingcartdetailHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.es.beans.ShoppingcartdetailHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.es.beans.Shoppingcartdetail ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.es.beans.Shoppingcartdetail) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.es.beans.Shoppingcartdetail.class);

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

    ejbRef = ejbHome().create(
      init_barcode_id,
      init_productType,
      init_shopping_cart_line_number,
      init_userId,
      init_acctId,
      init_cartNo);
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
      com.ardais.bigr.es.beans.ShoppingcartdetailKey pKey = (com.ardais.bigr.es.beans.ShoppingcartdetailKey) this
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
   * findByUserAccountOrderByLineNumber
   * @generated
   */
  public java.util.Enumeration findByUserAccountOrderByLineNumber(
    java.lang.String user,
    java.lang.String account)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ShoppingcartdetailHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByUserAccountOrderByLineNumber(user, account);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * ShoppingcartdetailAccessBean
   * @generated
   */
  public ShoppingcartdetailAccessBean(
    java.math.BigDecimal shopping_cart_line_number,
    com.ardais.bigr.es.beans.Shoppingcart argShoppingcart) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(shopping_cart_line_number, argShoppingcart);
  }
  /**
   * ShoppingcartdetailAccessBean
   * @generated
   */
  public ShoppingcartdetailAccessBean(com.ardais.bigr.es.beans.ShoppingcartdetailKey primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * findShoppingcartdetailByShoppingcart
   * @generated
   */
  public java.util.Enumeration findShoppingcartdetailByShoppingcart(
    com.ardais.bigr.es.beans.ShoppingcartKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ShoppingcartdetailHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findShoppingcartdetailByShoppingcart(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findByBarcodeUserAccount
   * @generated
   */
  public java.util.Enumeration findByBarcodeUserAccount(
    java.lang.String barcode_arg,
    java.lang.String user_arg,
    java.lang.String account_arg)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ShoppingcartdetailHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByBarcodeUserAccount(
      barcode_arg,
      user_arg,
      account_arg);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * getShoppingcart
   * @generated
   */
  public com.ardais.bigr.es.beans.ShoppingcartAccessBean getShoppingcart()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.es.beans.Shoppingcart localEJBRef = ejbRef().getShoppingcart();
    if (localEJBRef != null)
      return new com.ardais.bigr.es.beans.ShoppingcartAccessBean(localEJBRef);
    else
      return null;
  }
}
