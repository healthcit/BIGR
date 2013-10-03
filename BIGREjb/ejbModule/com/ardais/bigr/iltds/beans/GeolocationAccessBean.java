package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * GeolocationAccessBean
 * @generated
 */
public class GeolocationAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.GeolocationAccessBeanData {
  /**
   * @generated
   */
  private Geolocation __ejbRef;
  /**
   * @generated
   */
  private com.ardais.bigr.iltds.assistants.LocationManagementAsst init_locAsst;
  /**
   * getLocation_zip
   * @generated
   */
  public java.lang.String getLocation_zip()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("location_zip")));
  }
  /**
   * setLocation_zip
   * @generated
   */
  public void setLocation_zip(java.lang.String newValue) {
    __setCache("location_zip", newValue);
  }
  /**
   * getLocation_name
   * @generated
   */
  public java.lang.String getLocation_name()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("location_name")));
  }
  /**
   * setLocation_name
   * @generated
   */
  public void setLocation_name(java.lang.String newValue) {
    __setCache("location_name", newValue);
  }
  /**
   * getLocation_state
   * @generated
   */
  public java.lang.String getLocation_state()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("location_state")));
  }
  /**
   * setLocation_state
   * @generated
   */
  public void setLocation_state(java.lang.String newValue) {
    __setCache("location_state", newValue);
  }
  /**
   * getLocation_city
   * @generated
   */
  public java.lang.String getLocation_city()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("location_city")));
  }
  /**
   * setLocation_city
   * @generated
   */
  public void setLocation_city(java.lang.String newValue) {
    __setCache("location_city", newValue);
  }
  /**
   * getLocation_address_2
   * @generated
   */
  public java.lang.String getLocation_address_2()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("location_address_2")));
  }
  /**
   * setLocation_address_2
   * @generated
   */
  public void setLocation_address_2(java.lang.String newValue) {
    __setCache("location_address_2", newValue);
  }
  /**
   * getLocation_address_1
   * @generated
   */
  public java.lang.String getLocation_address_1()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("location_address_1")));
  }
  /**
   * setLocation_address_1
   * @generated
   */
  public void setLocation_address_1(java.lang.String newValue) {
    __setCache("location_address_1", newValue);
  }
  /**
   * getLocation_phone
   * @generated
   */
  public java.lang.String getLocation_phone()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("location_phone")));
  }
  /**
   * setLocation_phone
   * @generated
   */
  public void setLocation_phone(java.lang.String newValue) {
    __setCache("location_phone", newValue);
  }
  /**
   * setInit_locAsst
   * @generated
   */
  public void setInit_locAsst(com.ardais.bigr.iltds.assistants.LocationManagementAsst newValue) {
    this.init_locAsst = (newValue);
  }
  /**
   * GeolocationAccessBean
   * @generated
   */
  public GeolocationAccessBean() {
    super();
  }
  /**
   * GeolocationAccessBean
   * @generated
   */
  public GeolocationAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Geolocation";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.GeolocationHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.GeolocationHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.GeolocationHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Geolocation ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Geolocation) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Geolocation.class);

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

    ejbRef = ejbHome().create(init_locAsst);
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
      com.ardais.bigr.iltds.beans.GeolocationKey pKey = (com.ardais.bigr.iltds.beans.GeolocationKey) this
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
   * GeolocationAccessBean
   * @generated
   */
  public GeolocationAccessBean(com.ardais.bigr.iltds.beans.GeolocationKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * GeolocationAccessBean
   * @generated
   */
  public GeolocationAccessBean(java.lang.String location_address_id)
    throws javax.naming.NamingException, javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(location_address_id);
  }
  /**
   * secondaryAddConsent
   * @generated
   */
  public void secondaryAddConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddConsent(aConsent);
  }
  /**
   * secondaryRemoveBoxlocation
   * @generated
   */
  public void secondaryRemoveBoxlocation(com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveBoxlocation(aBoxlocation);
  }
  /**
   * removeConsent
   * @generated
   */
  public void removeConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().removeConsent(aConsent);
  }
  /**
   * secondaryRemoveConsent
   * @generated
   */
  public void secondaryRemoveConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveConsent(aConsent);
  }
  /**
   * secondaryAddArdaisstaff
   * @generated
   */
  public void secondaryAddArdaisstaff(com.ardais.bigr.iltds.beans.Ardaisstaff anArdaisstaff)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddArdaisstaff(anArdaisstaff);
  }
  /**
   * secondaryRemoveArdaisstaff
   * @generated
   */
  public void secondaryRemoveArdaisstaff(com.ardais.bigr.iltds.beans.Ardaisstaff anArdaisstaff)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveArdaisstaff(anArdaisstaff);
  }
  /**
   * secondaryAddBoxlocation
   * @generated
   */
  public void secondaryAddBoxlocation(com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddBoxlocation(aBoxlocation);
  }
  /**
   * getArdaisstaff
   * @generated
   */
  public java.util.Enumeration getArdaisstaff()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getArdaisstaff();
  }
  /**
   * getBoxlocation
   * @generated
   */
  public java.util.Enumeration getBoxlocation()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getBoxlocation();
  }
  /**
   * updateGeoLoc
   * @generated
   */
  public void updateGeoLoc(com.ardais.bigr.iltds.assistants.LocationManagementAsst locAsst)
    throws javax.naming.NamingException,
    com.ardais.bigr.api.ApiException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().updateGeoLoc(locAsst);
  }
  /**
   * addArdaisstaff
   * @generated
   */
  public void addArdaisstaff(com.ardais.bigr.iltds.beans.Ardaisstaff anArdaisstaff)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addArdaisstaff(anArdaisstaff);
  }
  /**
   * addConsent
   * @generated
   */
  public void addConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addConsent(aConsent);
  }
  /**
   * getConsent
   * @generated
   */
  public java.util.Enumeration getConsent()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getConsent();
  }
  /**
   * getLocation_country
   * @generated
   */
  public java.lang.String getLocation_country()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("location_country")));
  }
  /**
   * setLocation_country
   * @generated
   */
  public void setLocation_country(java.lang.String newValue) {
    __setCache("location_country", newValue);
  }
}
