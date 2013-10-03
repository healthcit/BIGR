package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * GeolocationFactory
 * @generated
 */
public class GeolocationFactory extends AbstractEJBFactory {
  /**
   * GeolocationFactory
   * @generated
   */
  public GeolocationFactory() {
    super();
  }
  /**
   * _acquireGeolocationHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.GeolocationHome _acquireGeolocationHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.GeolocationHome) _acquireEJBHome();
  }
  /**
   * acquireGeolocationHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.GeolocationHome acquireGeolocationHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.GeolocationHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Geolocation";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.GeolocationHome.class;
  }
  /**
   * resetGeolocationHome
   * @generated
   */
  public void resetGeolocationHome() {
    resetEJBHome();
  }
  /**
   * setGeolocationHome
   * @generated
   */
  public void setGeolocationHome(com.ardais.bigr.iltds.beans.GeolocationHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Geolocation findByPrimaryKey(
    com.ardais.bigr.iltds.beans.GeolocationKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireGeolocationHome().findByPrimaryKey(key);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Geolocation create(
    com.ardais.bigr.iltds.assistants.LocationManagementAsst locAsst)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireGeolocationHome().create(locAsst);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Geolocation create(java.lang.String location_address_id)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireGeolocationHome().create(location_address_id);
  }
}
