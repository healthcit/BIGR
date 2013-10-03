package com.ardais.bigr.orm.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * UseraccessmoduleAccessBean
 * @generated
 */
public class UseraccessmoduleAccessBean extends AbstractEntityAccessBean implements com.ardais.bigr.orm.beans.UseraccessmoduleAccessBeanData {
  /**
   * @generated
   */
  private Useraccessmodule __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_argArdais_acct_key;
  /**
   * @generated
   */
  private java.lang.String init_argArdais_user_id;
  /**
   * @generated
   */
  private com.ardais.bigr.orm.beans.ObjectsKey init_argObjects;
  /**
   * @generated
   */
  private java.lang.String init_argCreated_by;
  /**
   * @generated
   */
  private java.sql.Timestamp init_argCreate_date;
  /**
   * @generated
   */
  private java.lang.String init_argUpdated_by;
  /**
   * @generated
   */
  private java.sql.Timestamp init_argUpdate_date;
  /**
   * getCreate_date
   * @generated
   */
  public java.sql.Timestamp getCreate_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("create_date")));
  }
  /**
   * setCreate_date
   * @generated
   */
  public void setCreate_date(java.sql.Timestamp newValue) {
    __setCache("create_date", newValue);
  }
  /**
   * getCreated_by
   * @generated
   */
  public java.lang.String getCreated_by()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("created_by")));
  }
  /**
   * setCreated_by
   * @generated
   */
  public void setCreated_by(java.lang.String newValue) {
    __setCache("created_by", newValue);
  }
  /**
   * getDescription
   * @generated
   */
  public java.lang.String getDescription()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("description")));
  }
  /**
   * setDescription
   * @generated
   */
  public void setDescription(java.lang.String newValue) {
    __setCache("description", newValue);
  }
  /**
   * getNew_order_creation
   * @generated
   */
  public java.lang.String getNew_order_creation()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("new_order_creation")));
  }
  /**
   * setNew_order_creation
   * @generated
   */
  public void setNew_order_creation(java.lang.String newValue) {
    __setCache("new_order_creation", newValue);
  }
  /**
   * getObjectsKey
   * @generated
   */
  public com.ardais.bigr.orm.beans.ObjectsKey getObjectsKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.orm.beans.ObjectsKey) __getCache("objectsKey")));
  }
  /**
   * getOrder_maintain
   * @generated
   */
  public java.lang.String getOrder_maintain()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("order_maintain")));
  }
  /**
   * setOrder_maintain
   * @generated
   */
  public void setOrder_maintain(java.lang.String newValue) {
    __setCache("order_maintain", newValue);
  }
  /**
   * getOrder_view
   * @generated
   */
  public java.lang.String getOrder_view()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("order_view")));
  }
  /**
   * setOrder_view
   * @generated
   */
  public void setOrder_view(java.lang.String newValue) {
    __setCache("order_view", newValue);
  }
  /**
   * getUpdate_date
   * @generated
   */
  public java.sql.Timestamp getUpdate_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("update_date")));
  }
  /**
   * setUpdate_date
   * @generated
   */
  public void setUpdate_date(java.sql.Timestamp newValue) {
    __setCache("update_date", newValue);
  }
  /**
   * getUpdated_by
   * @generated
   */
  public java.lang.String getUpdated_by()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("updated_by")));
  }
  /**
   * setUpdated_by
   * @generated
   */
  public void setUpdated_by(java.lang.String newValue) {
    __setCache("updated_by", newValue);
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
   * setInit_argObjects
   * @generated
   */
  public void setInit_argObjects(com.ardais.bigr.orm.beans.ObjectsKey newValue) {
    this.init_argObjects = (newValue);
  }
  /**
   * setInit_argCreated_by
   * @generated
   */
  public void setInit_argCreated_by(java.lang.String newValue) {
    this.init_argCreated_by = (newValue);
  }
  /**
   * setInit_argCreate_date
   * @generated
   */
  public void setInit_argCreate_date(java.sql.Timestamp newValue) {
    this.init_argCreate_date = (newValue);
  }
  /**
   * setInit_argUpdated_by
   * @generated
   */
  public void setInit_argUpdated_by(java.lang.String newValue) {
    this.init_argUpdated_by = (newValue);
  }
  /**
   * setInit_argUpdate_date
   * @generated
   */
  public void setInit_argUpdate_date(java.sql.Timestamp newValue) {
    this.init_argUpdate_date = (newValue);
  }
  /**
   * UseraccessmoduleAccessBean
   * @generated
   */
  public UseraccessmoduleAccessBean() {
    super();
  }
  /**
   * UseraccessmoduleAccessBean
   * @generated
   */
  public UseraccessmoduleAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/orm/beans/Useraccessmodule";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.orm.beans.UseraccessmoduleHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.orm.beans.UseraccessmoduleHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.orm.beans.UseraccessmoduleHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.orm.beans.Useraccessmodule ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.orm.beans.Useraccessmodule) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.orm.beans.Useraccessmodule.class);

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
      init_argArdais_acct_key,
      init_argArdais_user_id,
      init_argObjects,
      init_argCreated_by,
      init_argCreate_date,
      init_argUpdated_by,
      init_argUpdate_date);
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
      com.ardais.bigr.orm.beans.UseraccessmoduleKey pKey = (com.ardais.bigr.orm.beans.UseraccessmoduleKey) this
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
   * UseraccessmoduleAccessBean
   * @generated
   */
  public UseraccessmoduleAccessBean(com.ardais.bigr.orm.beans.UseraccessmoduleKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * findUseraccessmoduleByObjects
   * @generated
   */
  public java.util.Enumeration findUseraccessmoduleByObjects(
    com.ardais.bigr.orm.beans.ObjectsKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.orm.beans.UseraccessmoduleHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findUseraccessmoduleByObjects(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * getObjects
   * @generated
   */
  public com.ardais.bigr.orm.beans.ObjectsAccessBean getObjects()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.orm.beans.Objects localEJBRef = ejbRef().getObjects();
    if (localEJBRef != null)
      return new com.ardais.bigr.orm.beans.ObjectsAccessBean(localEJBRef);
    else
      return null;
  }
}
