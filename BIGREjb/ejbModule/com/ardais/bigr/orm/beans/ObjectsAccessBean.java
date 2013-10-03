package com.ardais.bigr.orm.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ObjectsAccessBean
 * @generated
 */
public class ObjectsAccessBean extends AbstractEntityAccessBean implements com.ardais.bigr.orm.beans.ObjectsAccessBeanData {
  /**
   * @generated
   */
  private Objects __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_argObject_id;
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
   * getUrl
   * @generated
   */
  public java.lang.String getUrl()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("url")));
  }
  /**
   * setUrl
   * @generated
   */
  public void setUrl(java.lang.String newValue) {
    __setCache("url", newValue);
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
   * getObject_description
   * @generated
   */
  public java.lang.String getObject_description()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("object_description")));
  }
  /**
   * setObject_description
   * @generated
   */
  public void setObject_description(java.lang.String newValue) {
    __setCache("object_description", newValue);
  }
  /**
   * getLong_description
   * @generated
   */
  public java.lang.String getLong_description()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("long_description")));
  }
  /**
   * setLong_description
   * @generated
   */
  public void setLong_description(java.lang.String newValue) {
    __setCache("long_description", newValue);
  }
  /**
   * setInit_argObject_id
   * @generated
   */
  public void setInit_argObject_id(java.lang.String newValue) {
    this.init_argObject_id = (newValue);
  }
  /**
   * ObjectsAccessBean
   * @generated
   */
  public ObjectsAccessBean() {
    super();
  }
  /**
   * ObjectsAccessBean
   * @generated
   */
  public ObjectsAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/orm/beans/Objects";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.orm.beans.ObjectsHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.orm.beans.ObjectsHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.orm.beans.ObjectsHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.orm.beans.Objects ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.orm.beans.Objects) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.orm.beans.Objects.class);

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

    ejbRef = ejbHome().create(init_argObject_id);
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
      com.ardais.bigr.orm.beans.ObjectsKey pKey = (com.ardais.bigr.orm.beans.ObjectsKey) this
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
   * ObjectsAccessBean
   * @generated
   */
  public ObjectsAccessBean(com.ardais.bigr.orm.beans.ObjectsKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * secondaryAddUseraccessmodule
   * @generated
   */
  public void secondaryAddUseraccessmodule(
    com.ardais.bigr.orm.beans.Useraccessmodule anUseraccessmodule)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddUseraccessmodule(anUseraccessmodule);
  }
  /**
   * getUseraccessmodule
   * @generated
   */
  public java.util.Enumeration getUseraccessmodule()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getUseraccessmodule();
  }
  /**
   * secondaryRemoveUseraccessmodule
   * @generated
   */
  public void secondaryRemoveUseraccessmodule(
    com.ardais.bigr.orm.beans.Useraccessmodule anUseraccessmodule)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveUseraccessmodule(anUseraccessmodule);
  }
}
