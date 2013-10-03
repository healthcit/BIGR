package com.ardais.bigr.es.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ArdaisaddressAccessBean
 * @generated
 */
public class ArdaisaddressAccessBean extends AbstractEntityAccessBean implements com.ardais.bigr.es.beans.ArdaisaddressAccessBeanData {
  /**
   * @generated
   */
  private Ardaisaddress __ejbRef;
  /**
   * @generated
   */
  private java.math.BigDecimal init_argAddress_id;
  /**
   * @generated
   */
  private java.lang.String init_argArdais_acct_key;
  /**
   * @generated
   */
  private java.lang.String init_argAddress1;
  /**
   * getAddress_type
   * @generated
   */
  public java.lang.String getAddress_type()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("address_type")));
  }
  /**
   * setAddress_type
   * @generated
   */
  public void setAddress_type(java.lang.String newValue) {
    __setCache("address_type", newValue);
  }
  /**
   * getAddr_country
   * @generated
   */
  public java.lang.String getAddr_country()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("addr_country")));
  }
  /**
   * setAddr_country
   * @generated
   */
  public void setAddr_country(java.lang.String newValue) {
    __setCache("addr_country", newValue);
  }
  /**
   * getAddr_state
   * @generated
   */
  public java.lang.String getAddr_state()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("addr_state")));
  }
  /**
   * setAddr_state
   * @generated
   */
  public void setAddr_state(java.lang.String newValue) {
    __setCache("addr_state", newValue);
  }
  /**
   * getAddress_2
   * @generated
   */
  public java.lang.String getAddress_2()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("address_2")));
  }
  /**
   * setAddress_2
   * @generated
   */
  public void setAddress_2(java.lang.String newValue) {
    __setCache("address_2", newValue);
  }
  /**
   * getAddress_1
   * @generated
   */
  public java.lang.String getAddress_1()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("address_1")));
  }
  /**
   * setAddress_1
   * @generated
   */
  public void setAddress_1(java.lang.String newValue) {
    __setCache("address_1", newValue);
  }
  /**
   * getAddr_city
   * @generated
   */
  public java.lang.String getAddr_city()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("addr_city")));
  }
  /**
   * setAddr_city
   * @generated
   */
  public void setAddr_city(java.lang.String newValue) {
    __setCache("addr_city", newValue);
  }
  /**
   * getAddr_zip_code
   * @generated
   */
  public java.lang.String getAddr_zip_code()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("addr_zip_code")));
  }
  /**
   * setAddr_zip_code
   * @generated
   */
  public void setAddr_zip_code(java.lang.String newValue) {
    __setCache("addr_zip_code", newValue);
  }
  /**
   * setInit_argAddress_id
   * @generated
   */
  public void setInit_argAddress_id(java.math.BigDecimal newValue) {
    this.init_argAddress_id = (newValue);
  }
  /**
   * setInit_argArdais_acct_key
   * @generated
   */
  public void setInit_argArdais_acct_key(java.lang.String newValue) {
    this.init_argArdais_acct_key = (newValue);
  }
  /**
   * setInit_argAddress1
   * @generated
   */
  public void setInit_argAddress1(java.lang.String newValue) {
    this.init_argAddress1 = (newValue);
  }
  /**
   * ArdaisaddressAccessBean
   * @generated
   */
  public ArdaisaddressAccessBean() {
    super();
  }
  /**
   * ArdaisaddressAccessBean
   * @generated
   */
  public ArdaisaddressAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/es/beans/Ardaisaddress";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.es.beans.ArdaisaddressHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ArdaisaddressHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.es.beans.ArdaisaddressHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.es.beans.Ardaisaddress ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.es.beans.Ardaisaddress) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.es.beans.Ardaisaddress.class);

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

    ejbRef = ejbHome().create(init_argAddress_id, init_argArdais_acct_key, init_argAddress1);
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
      com.ardais.bigr.es.beans.ArdaisaddressKey pKey = (com.ardais.bigr.es.beans.ArdaisaddressKey) this
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
   * ArdaisaddressAccessBean
   * @generated
   */
  public ArdaisaddressAccessBean(com.ardais.bigr.es.beans.ArdaisaddressKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * findByAccountandType
   * @generated
   */
  public java.util.Enumeration findByAccountandType(
    java.lang.String arg_Account,
    java.lang.String arg_Type)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.es.beans.ArdaisaddressHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByAccountandType(arg_Account, arg_Type);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * ArdaisaddressAccessBean
   * @generated
   */
  public ArdaisaddressAccessBean(
    java.math.BigDecimal argAddress_id,
    java.lang.String argArdais_acct_key) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(argAddress_id, argArdais_acct_key);
  }
}
