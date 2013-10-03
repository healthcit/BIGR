package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * RevokedconsentAccessBean
 * @generated
 */
public class RevokedconsentAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.RevokedconsentAccessBeanData {
  /**
   * @generated
   */
  private Revokedconsent __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_argConsent_id;
  /**
   * @generated
   */
  private java.lang.String init_argArdais_id;
  /**
   * getRevoked_reason
   * @generated
   */
  public java.lang.String getRevoked_reason()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("revoked_reason")));
  }
  /**
   * setRevoked_reason
   * @generated
   */
  public void setRevoked_reason(java.lang.String newValue) {
    __setCache("revoked_reason", newValue);
  }
  /**
   * getRevoked_by_staff_id
   * @generated
   */
  public java.lang.String getRevoked_by_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("revoked_by_staff_id")));
  }
  /**
   * setRevoked_by_staff_id
   * @generated
   */
  public void setRevoked_by_staff_id(java.lang.String newValue) {
    __setCache("revoked_by_staff_id", newValue);
  }
  /**
   * getRevoked_timestamp
   * @generated
   */
  public java.sql.Timestamp getRevoked_timestamp()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("revoked_timestamp")));
  }
  /**
   * setRevoked_timestamp
   * @generated
   */
  public void setRevoked_timestamp(java.sql.Timestamp newValue) {
    __setCache("revoked_timestamp", newValue);
  }
  /**
   * getArdais_id
   * @generated
   */
  public java.lang.String getArdais_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_id")));
  }
  /**
   * setArdais_id
   * @generated
   */
  public void setArdais_id(java.lang.String newValue) {
    __setCache("ardais_id", newValue);
  }
  /**
   * getConsent_id
   * @generated
   */
  public java.lang.String getConsent_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consent_id")));
  }
  /**
   * setConsent_id
   * @generated
   */
  public void setConsent_id(java.lang.String newValue) {
    __setCache("consent_id", newValue);
  }
  /**
   * setInit_argConsent_id
   * @generated
   */
  public void setInit_argConsent_id(java.lang.String newValue) {
    this.init_argConsent_id = (newValue);
  }
  /**
   * setInit_argArdais_id
   * @generated
   */
  public void setInit_argArdais_id(java.lang.String newValue) {
    this.init_argArdais_id = (newValue);
  }
  /**
   * RevokedconsentAccessBean
   * @generated
   */
  public RevokedconsentAccessBean() {
    super();
  }
  /**
   * RevokedconsentAccessBean
   * @generated
   */
  public RevokedconsentAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Revokedconsent";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.RevokedconsentHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.RevokedconsentHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.RevokedconsentHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Revokedconsent ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Revokedconsent) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Revokedconsent.class);

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

    ejbRef = ejbHome().create(init_argConsent_id, init_argArdais_id);
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
      com.ardais.bigr.iltds.beans.RevokedconsentKey pKey = (com.ardais.bigr.iltds.beans.RevokedconsentKey) this
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
   * RevokedconsentAccessBean
   * @generated
   */
  public RevokedconsentAccessBean(com.ardais.bigr.iltds.beans.RevokedconsentKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * RevokedconsentAccessBean
   * @generated
   */
  public RevokedconsentAccessBean(java.lang.String argConsent_id)
    throws javax.naming.NamingException, javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(argConsent_id);
  }
}
