package com.ardais.bigr.es.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ArdaisorderAccessBean
 * @generated
 */
public class ArdaisorderAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.es.beans.ArdaisorderAccessBeanData {
  /**
   * @generated
   */
  private Ardaisorder __ejbRef;
  /**
   * @generated
   */
  private java.math.BigDecimal init_argOrder_number;
  /**
   * @generated
   */
  private com.ardais.bigr.es.beans.ArdaisuserKey init_argArdaisuser;
  /**
   * getOrder_po_number
   * @generated
   */
  public java.lang.String getOrder_po_number()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("order_po_number")));
  }
  /**
   * setOrder_po_number
   * @generated
   */
  public void setOrder_po_number(java.lang.String newValue) {
    __setCache("order_po_number", newValue);
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
   * getBill_to_addr_id
   * @generated
   */
  public java.math.BigDecimal getBill_to_addr_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("bill_to_addr_id")));
  }
  /**
   * setBill_to_addr_id
   * @generated
   */
  public void setBill_to_addr_id(java.math.BigDecimal newValue) {
    __setCache("bill_to_addr_id", newValue);
  }
  /**
   * getOrder_date
   * @generated
   */
  public java.sql.Timestamp getOrder_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("order_date")));
  }
  /**
   * setOrder_date
   * @generated
   */
  public void setOrder_date(java.sql.Timestamp newValue) {
    __setCache("order_date", newValue);
  }
  /**
   * getApproved_date
   * @generated
   */
  public java.sql.Timestamp getApproved_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("approved_date")));
  }
  /**
   * setApproved_date
   * @generated
   */
  public void setApproved_date(java.sql.Timestamp newValue) {
    __setCache("approved_date", newValue);
  }
  /**
   * getOrder_status
   * @generated
   */
  public java.lang.String getOrder_status()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("order_status")));
  }
  /**
   * setOrder_status
   * @generated
   */
  public void setOrder_status(java.lang.String newValue) {
    __setCache("order_status", newValue);
  }
  /**
   * getOrder_amount
   * @generated
   */
  public java.math.BigDecimal getOrder_amount()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("order_amount")));
  }
  /**
   * setOrder_amount
   * @generated
   */
  public void setOrder_amount(java.math.BigDecimal newValue) {
    __setCache("order_amount", newValue);
  }
  /**
   * getShip_instruction
   * @generated
   */
  public java.lang.String getShip_instruction()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ship_instruction")));
  }
  /**
   * setShip_instruction
   * @generated
   */
  public void setShip_instruction(java.lang.String newValue) {
    __setCache("ship_instruction", newValue);
  }
  /**
   * getApproval_user_id
   * @generated
   */
  public java.lang.String getApproval_user_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("approval_user_id")));
  }
  /**
   * setApproval_user_id
   * @generated
   */
  public void setApproval_user_id(java.lang.String newValue) {
    __setCache("approval_user_id", newValue);
  }
  /**
   * setInit_argOrder_number
   * @generated
   */
  public void setInit_argOrder_number(java.math.BigDecimal newValue) {
    this.init_argOrder_number = (newValue);
  }
  /**
   * setInit_argArdaisuser
   * @generated
   */
  public void setInit_argArdaisuser(com.ardais.bigr.es.beans.ArdaisuserKey newValue) {
    this.init_argArdaisuser = (newValue);
  }
  /**
   * ArdaisorderAccessBean
   * @generated
   */
  public ArdaisorderAccessBean() {
    super();
  }
  /**
   * ArdaisorderAccessBean
   * @generated
   */
  public ArdaisorderAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/es/beans/Ardaisorder";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.es.beans.ArdaisorderHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ArdaisorderHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.es.beans.ArdaisorderHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.es.beans.Ardaisorder ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.es.beans.Ardaisorder) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.es.beans.Ardaisorder.class);

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

    ejbRef = ejbHome().create(init_argOrder_number, init_argArdaisuser);
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
      com.ardais.bigr.es.beans.ArdaisorderKey pKey = (com.ardais.bigr.es.beans.ArdaisorderKey) this
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
   * ArdaisorderAccessBean
   * @generated
   */
  public ArdaisorderAccessBean(
    java.math.BigDecimal order_number,
    com.ardais.bigr.es.beans.Ardaisuser argArdaisuser) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(order_number, argArdaisuser);
  }
  /**
   * findByAccount
   * @generated
   */
  public java.util.Enumeration findByAccount(java.lang.String arg_accountID)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisorderHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByAccount(arg_accountID);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findUserOrderHistory
   * @generated
   */
  public java.util.Enumeration findUserOrderHistory(java.lang.String user, java.lang.String account)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisorderHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findUserOrderHistory(user, account);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findByOrderStatus
   * @generated
   */
  public java.util.Enumeration findByOrderStatus(java.lang.String ardOrder_status)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisorderHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByOrderStatus(ardOrder_status);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findUserOpenOrders
   * @generated
   */
  public java.util.Enumeration findUserOpenOrders(java.lang.String user, java.lang.String account)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisorderHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findUserOpenOrders(user, account);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * ArdaisorderAccessBean
   * @generated
   */
  public ArdaisorderAccessBean(com.ardais.bigr.es.beans.ArdaisorderKey primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * findAllOrders
   * @generated
   */
  public java.util.Enumeration findAllOrders()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisorderHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findAllOrders();
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findAccountOrderHistory
   * @generated
   */
  public java.util.Enumeration findAccountOrderHistory(java.lang.String accountID)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisorderHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findAccountOrderHistory(accountID);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findByOrderNumber
   * @generated
   */
  public java.util.Enumeration findByOrderNumber(java.lang.String orderID)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisorderHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByOrderNumber(orderID);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findAccountOrderswithStatus
   * @generated
   */
  public java.util.Enumeration findAccountOrderswithStatus(
    java.lang.String arg_account,
    java.lang.String arg_status)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisorderHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findAccountOrderswithStatus(arg_account, arg_status);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findArdaisorderByArdaisuser
   * @generated
   */
  public java.util.Enumeration findArdaisorderByArdaisuser(
    com.ardais.bigr.es.beans.ArdaisuserKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisorderHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findArdaisorderByArdaisuser(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * secondaryRemoveOrderline
   * @generated
   */
  public void secondaryRemoveOrderline(com.ardais.bigr.es.beans.Orderline anOrderline)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveOrderline(anOrderline);
  }
  /**
   * getOrderline
   * @generated
   */
  public java.util.Enumeration getOrderline()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getOrderline();
  }
  /**
   * secondaryAddArdaisorderstatus
   * @generated
   */
  public void secondaryAddArdaisorderstatus(
    com.ardais.bigr.es.beans.Ardaisorderstatus anArdaisorderstatus)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddArdaisorderstatus(anArdaisorderstatus);
  }
  /**
   * getArdaisorderstatus
   * @generated
   */
  public java.util.Enumeration getArdaisorderstatus()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getArdaisorderstatus();
  }
  /**
   * secondaryAddOrderline
   * @generated
   */
  public void secondaryAddOrderline(com.ardais.bigr.es.beans.Orderline anOrderline)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddOrderline(anOrderline);
  }
  /**
   * secondaryRemoveArdaisorderstatus
   * @generated
   */
  public void secondaryRemoveArdaisorderstatus(
    com.ardais.bigr.es.beans.Ardaisorderstatus anArdaisorderstatus)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveArdaisorderstatus(anArdaisorderstatus);
  }
  /**
   * privateSetArdaisuserKey
   * @generated
   */
  public void privateSetArdaisuserKey(com.ardais.bigr.es.beans.ArdaisuserKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetArdaisuserKey(inKey);
  }
  /**
   * setArdaisuser
   * @generated
   */
  public void setArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setArdaisuser(anArdaisuser);
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
   * secondarySetArdaisuser
   * @generated
   */
  public void secondarySetArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondarySetArdaisuser(anArdaisuser);
  }
}
