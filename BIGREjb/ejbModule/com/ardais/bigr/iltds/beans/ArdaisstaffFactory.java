package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ArdaisstaffFactory
 * @generated
 */
public class ArdaisstaffFactory extends AbstractEJBFactory {
  /**
   * ArdaisstaffFactory
   * @generated
   */
  public ArdaisstaffFactory() {
    super();
  }
  /**
   * _acquireArdaisstaffHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.ArdaisstaffHome _acquireArdaisstaffHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.ArdaisstaffHome) _acquireEJBHome();
  }
  /**
   * acquireArdaisstaffHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ArdaisstaffHome acquireArdaisstaffHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.ArdaisstaffHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Ardaisstaff";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.ArdaisstaffHome.class;
  }
  /**
   * resetArdaisstaffHome
   * @generated
   */
  public void resetArdaisstaffHome() {
    resetEJBHome();
  }
  /**
   * setArdaisstaffHome
   * @generated
   */
  public void setArdaisstaffHome(com.ardais.bigr.iltds.beans.ArdaisstaffHome home) {
    setEJBHome(home);
  }
  /**
   * findAllStaffMembers
   * @generated
   */
  public java.util.Enumeration findAllStaffMembers()
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisstaffHome().findAllStaffMembers();
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Ardaisstaff create(
    java.lang.String ardais_staff_id,
    com.ardais.bigr.iltds.beans.Geolocation argGeolocation)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireArdaisstaffHome().create(ardais_staff_id, argGeolocation);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Ardaisstaff findByPrimaryKey(
    com.ardais.bigr.iltds.beans.ArdaisstaffKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisstaffHome().findByPrimaryKey(key);
  }
  /**
   * findLocByUserProf
   * @generated
   */
  public java.util.Enumeration findLocByUserProf(
    java.lang.String ardais_user_id,
    java.lang.String ardais_acct_key) throws java.rmi.RemoteException, javax.ejb.FinderException {
    return _acquireArdaisstaffHome().findLocByUserProf(ardais_user_id, ardais_acct_key);
  }
  /**
   * findArdaisstaffByGeolocation
   * @generated
   */
  public java.util.Enumeration findArdaisstaffByGeolocation(
    com.ardais.bigr.iltds.beans.GeolocationKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisstaffHome().findArdaisstaffByGeolocation(inKey);
  }
}
