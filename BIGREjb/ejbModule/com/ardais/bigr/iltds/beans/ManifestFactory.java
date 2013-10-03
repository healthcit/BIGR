package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ManifestFactory
 * @generated
 */
public class ManifestFactory extends AbstractEJBFactory {
  /**
   * ManifestFactory
   * @generated
   */
  public ManifestFactory() {
    super();
  }
  /**
   * _acquireManifestHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.ManifestHome _acquireManifestHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.ManifestHome) _acquireEJBHome();
  }
  /**
   * acquireManifestHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ManifestHome acquireManifestHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.ManifestHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Manifest";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.ManifestHome.class;
  }
  /**
   * resetManifestHome
   * @generated
   */
  public void resetManifestHome() {
    resetEJBHome();
  }
  /**
   * setManifestHome
   * @generated
   */
  public void setManifestHome(com.ardais.bigr.iltds.beans.ManifestHome home) {
    setEJBHome(home);
  }
  /**
   * findByBoxID
   * @generated
   */
  public java.util.Enumeration findByBoxID(java.lang.String boxID)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireManifestHome().findByBoxID(boxID);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Manifest findByPrimaryKey(
    com.ardais.bigr.iltds.beans.ManifestKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireManifestHome().findByPrimaryKey(key);
  }
  /**
   * findByWaybill
   * @generated
   */
  public java.util.Enumeration findByWaybill(java.lang.String waybill)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireManifestHome().findByWaybill(waybill);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Manifest create(java.lang.String manifest_number)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireManifestHome().create(manifest_number);
  }
}
