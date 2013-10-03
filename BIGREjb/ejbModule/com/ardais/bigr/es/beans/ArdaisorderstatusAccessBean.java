package com.ardais.bigr.es.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ArdaisorderstatusAccessBean
 * @generated
 */
public class ArdaisorderstatusAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.es.beans.ArdaisorderstatusAccessBeanData {
  /**
   * @generated
   */
  private Ardaisorderstatus __ejbRef;
  /**
   * @generated
   */
  private java.sql.Timestamp init_argOrder_status_date;
  /**
   * @generated
   */
  private java.lang.String init_argOrder_status_id;
  /**
   * @generated
   */
  private com.ardais.bigr.es.beans.ArdaisorderKey init_argArdaisorder;
  /**
   * @generated
   */
  private java.lang.String init_argArdais_acct_key;
  /**
   * @generated
   */
  private java.lang.String init_argArdais_user_id;
  /**
   * getOrder_status_comment
   * @generated
   */
  public java.lang.String getOrder_status_comment()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("order_status_comment")));
  }
  /**
   * setOrder_status_comment
   * @generated
   */
  public void setOrder_status_comment(java.lang.String newValue) {
    __setCache("order_status_comment", newValue);
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
   * setInit_argOrder_status_date
   * @generated
   */
  public void setInit_argOrder_status_date(java.sql.Timestamp newValue) {
    this.init_argOrder_status_date = (newValue);
  }
  /**
   * setInit_argOrder_status_id
   * @generated
   */
  public void setInit_argOrder_status_id(java.lang.String newValue) {
    this.init_argOrder_status_id = (newValue);
  }
  /**
   * setInit_argArdaisorder
   * @generated
   */
  public void setInit_argArdaisorder(com.ardais.bigr.es.beans.ArdaisorderKey newValue) {
    this.init_argArdaisorder = (newValue);
  }
  /**
   * setInit_argArdais_acct_key
   * @generated
   */
  public void setInit_argArdais_acct_key(java.lang.String newValue) {
    this.init_argArdais_acct_key = (newValue);
  }
  /**
   * setInit_argArdais_user_id
   * @generated
   */
  public void setInit_argArdais_user_id(java.lang.String newValue) {
    this.init_argArdais_user_id = (newValue);
  }
  /**
   * ArdaisorderstatusAccessBean
   * @generated
   */
  public ArdaisorderstatusAccessBean() {
    super();
  }
  /**
   * ArdaisorderstatusAccessBean
   * @generated
   */
  public ArdaisorderstatusAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/es/beans/Ardaisorderstatus";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.es.beans.ArdaisorderstatusHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ArdaisorderstatusHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.es.beans.ArdaisorderstatusHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.es.beans.Ardaisorderstatus ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.es.beans.Ardaisorderstatus) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.es.beans.Ardaisorderstatus.class);

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
      init_argOrder_status_date,
      init_argOrder_status_id,
      init_argArdaisorder,
      init_argArdais_acct_key,
      init_argArdais_user_id);
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
      com.ardais.bigr.es.beans.ArdaisorderstatusKey pKey = (com.ardais.bigr.es.beans.ArdaisorderstatusKey) this
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
   * ArdaisorderstatusAccessBean
   * @generated
   */
  public ArdaisorderstatusAccessBean(com.ardais.bigr.es.beans.ArdaisorderstatusKey primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * ArdaisorderstatusAccessBean
   * @generated
   */
  public ArdaisorderstatusAccessBean(
    java.sql.Timestamp order_status_date,
    java.lang.String order_status_id,
    java.lang.String ardais_acct_key,
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.Ardaisorder argArdaisorder) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(
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
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisorderstatusHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findArdaisorderstatusByArdaisorder(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
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
   * ArdaisorderstatusAccessBean
   * @generated
   */
  public ArdaisorderstatusAccessBean(
    java.lang.String ardais_acct_key,
    java.lang.String order_status_id,
    java.sql.Timestamp order_status_date,
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.Ardaisorder argArdaisorder) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(
      ardais_acct_key,
      order_status_id,
      order_status_date,
      ardais_user_id,
      argArdaisorder);
  }
}
