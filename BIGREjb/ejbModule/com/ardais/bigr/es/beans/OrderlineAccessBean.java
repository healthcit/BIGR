package com.ardais.bigr.es.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * OrderlineAccessBean
 * @generated
 */
public class OrderlineAccessBean extends AbstractEntityAccessBean implements com.ardais.bigr.es.beans.OrderlineAccessBeanData {
  /**
   * @generated
   */
  private Orderline __ejbRef;
  /**
   * @generated
   */
  private java.math.BigDecimal init_argOrder_line_number;
  /**
   * @generated
   */
  private com.ardais.bigr.es.beans.ArdaisorderKey init_argArdaisorder;
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
   * getOrder_line_amount
   * @generated
   */
  public java.math.BigDecimal getOrder_line_amount()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("order_line_amount")));
  }
  /**
   * setOrder_line_amount
   * @generated
   */
  public void setOrder_line_amount(java.math.BigDecimal newValue) {
    __setCache("order_line_amount", newValue);
  }
  /**
   * getConsortium_ind
   * @generated
   */
  public java.lang.String getConsortium_ind()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consortium_ind")));
  }
  /**
   * setConsortium_ind
   * @generated
   */
  public void setConsortium_ind(java.lang.String newValue) {
    __setCache("consortium_ind", newValue);
  }
  /**
   * getArdaisorderKey
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisorderKey getArdaisorderKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.es.beans.ArdaisorderKey) __getCache("ardaisorderKey")));
  }
  /**
   * setInit_argOrder_line_number
   * @generated
   */
  public void setInit_argOrder_line_number(java.math.BigDecimal newValue) {
    this.init_argOrder_line_number = (newValue);
  }
  /**
   * setInit_argArdaisorder
   * @generated
   */
  public void setInit_argArdaisorder(com.ardais.bigr.es.beans.ArdaisorderKey newValue) {
    this.init_argArdaisorder = (newValue);
  }
  /**
   * OrderlineAccessBean
   * @generated
   */
  public OrderlineAccessBean() {
    super();
  }
  /**
   * OrderlineAccessBean
   * @generated
   */
  public OrderlineAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/es/beans/Orderline";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.es.beans.OrderlineHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.OrderlineHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.es.beans.OrderlineHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.es.beans.Orderline ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.es.beans.Orderline) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.es.beans.Orderline.class);

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

    ejbRef = ejbHome().create(init_argOrder_line_number, init_argArdaisorder);
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
      com.ardais.bigr.es.beans.OrderlineKey pKey = (com.ardais.bigr.es.beans.OrderlineKey) this
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
   * findOrderlineByArdaisorder
   * @generated
   */
  public java.util.Enumeration findOrderlineByArdaisorder(
    com.ardais.bigr.es.beans.ArdaisorderKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.OrderlineHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findOrderlineByArdaisorder(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * OrderlineAccessBean
   * @generated
   */
  public OrderlineAccessBean(com.ardais.bigr.es.beans.OrderlineKey primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * getArdaisorder
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisorderAccessBean getArdaisorder()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.es.beans.Ardaisorder localEJBRef = ejbRef().getArdaisorder();
    if (localEJBRef != null)
      return new com.ardais.bigr.es.beans.ArdaisorderAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * OrderlineAccessBean
   * @generated
   */
  public OrderlineAccessBean(
    java.math.BigDecimal order_line_number,
    com.ardais.bigr.es.beans.Ardaisorder argArdaisorder) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(order_line_number, argArdaisorder);
  }
}
