package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ArdaisstaffAccessBean
 * @generated
 */
public class ArdaisstaffAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.ArdaisstaffAccessBeanData {
  /**
   * @generated
   */
  private Ardaisstaff __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_ardais_staff_id;
  /**
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Geolocation init_argGeolocation;
  /**
   * getArdais_user_id
   * @generated
   */
  public java.lang.String getArdais_user_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_user_id")));
  }
  /**
   * setArdais_user_id
   * @generated
   */
  public void setArdais_user_id(java.lang.String newValue) {
    __setCache("ardais_user_id", newValue);
  }
  /**
   * getArdais_staff_fname
   * @generated
   */
  public java.lang.String getArdais_staff_fname()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_staff_fname")));
  }
  /**
   * setArdais_staff_fname
   * @generated
   */
  public void setArdais_staff_fname(java.lang.String newValue) {
    __setCache("ardais_staff_fname", newValue);
  }
  /**
   * getGeolocationKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.GeolocationKey getGeolocationKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.GeolocationKey) __getCache("geolocationKey")));
  }
  /**
   * getArdais_staff_lname
   * @generated
   */
  public java.lang.String getArdais_staff_lname()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_staff_lname")));
  }
  /**
   * setArdais_staff_lname
   * @generated
   */
  public void setArdais_staff_lname(java.lang.String newValue) {
    __setCache("ardais_staff_lname", newValue);
  }
  /**
   * getArdais_acct_key
   * @generated
   */
  public java.lang.String getArdais_acct_key()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_acct_key")));
  }
  /**
   * setArdais_acct_key
   * @generated
   */
  public void setArdais_acct_key(java.lang.String newValue) {
    __setCache("ardais_acct_key", newValue);
  }
  /**
   * getGeolocation_location_address_id
   * @generated
   */
  public java.lang.String getGeolocation_location_address_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("geolocation_location_address_id")));
  }
  /**
   * setGeolocation_location_address_id
   * @generated
   */
  public void setGeolocation_location_address_id(java.lang.String newValue) {
    __setCache("geolocation_location_address_id", newValue);
  }
  /**
   * setInit_ardais_staff_id
   * @generated
   */
  public void setInit_ardais_staff_id(java.lang.String newValue) {
    this.init_ardais_staff_id = (newValue);
  }
  /**
   * setInit_argGeolocation
   * @generated
   */
  public void setInit_argGeolocation(com.ardais.bigr.iltds.beans.Geolocation newValue) {
    this.init_argGeolocation = (newValue);
  }
  /**
   * ArdaisstaffAccessBean
   * @generated
   */
  public ArdaisstaffAccessBean() {
    super();
  }
  /**
   * ArdaisstaffAccessBean
   * @generated
   */
  public ArdaisstaffAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Ardaisstaff";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.ArdaisstaffHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.ArdaisstaffHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.ArdaisstaffHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Ardaisstaff ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Ardaisstaff) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Ardaisstaff.class);

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

    ejbRef = ejbHome().create(init_ardais_staff_id, init_argGeolocation);
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
      com.ardais.bigr.iltds.beans.ArdaisstaffKey pKey = (com.ardais.bigr.iltds.beans.ArdaisstaffKey) this
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
   * findAllStaffMembers
   * @generated
   */
  public java.util.Enumeration findAllStaffMembers()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.ArdaisstaffHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findAllStaffMembers();
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * ArdaisstaffAccessBean
   * @generated
   */
  public ArdaisstaffAccessBean(com.ardais.bigr.iltds.beans.ArdaisstaffKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * findLocByUserProf
   * @generated
   */
  public java.util.Enumeration findLocByUserProf(
    java.lang.String ardais_user_id,
    java.lang.String ardais_acct_key)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.ArdaisstaffHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findLocByUserProf(ardais_user_id, ardais_acct_key);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findArdaisstaffByGeolocation
   * @generated
   */
  public java.util.Enumeration findArdaisstaffByGeolocation(
    com.ardais.bigr.iltds.beans.GeolocationKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.ArdaisstaffHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findArdaisstaffByGeolocation(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * secondarySetGeolocation
   * @generated
   */
  public void secondarySetGeolocation(com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondarySetGeolocation(aGeolocation);
  }
  /**
   * privateSetGeolocationKey
   * @generated
   */
  public void privateSetGeolocationKey(com.ardais.bigr.iltds.beans.GeolocationKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetGeolocationKey(inKey);
  }
  /**
   * getGeolocation
   * @generated
   */
  public com.ardais.bigr.iltds.beans.GeolocationAccessBean getGeolocation()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.iltds.beans.Geolocation localEJBRef = ejbRef().getGeolocation();
    if (localEJBRef != null)
      return new com.ardais.bigr.iltds.beans.GeolocationAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * setGeolocation
   * @generated
   */
  public void setGeolocation(com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setGeolocation(aGeolocation);
  }
}
