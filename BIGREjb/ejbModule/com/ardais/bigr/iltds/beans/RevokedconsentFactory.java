package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * RevokedconsentFactory
 * @generated
 */
public class RevokedconsentFactory extends AbstractEJBFactory {
  /**
   * RevokedconsentFactory
   * @generated
   */
  public RevokedconsentFactory() {
    super();
  }
  /**
   * _acquireRevokedconsentHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.RevokedconsentHome _acquireRevokedconsentHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.RevokedconsentHome) _acquireEJBHome();
  }
  /**
   * acquireRevokedconsentHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.RevokedconsentHome acquireRevokedconsentHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.RevokedconsentHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Revokedconsent";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.RevokedconsentHome.class;
  }
  /**
   * resetRevokedconsentHome
   * @generated
   */
  public void resetRevokedconsentHome() {
    resetEJBHome();
  }
  /**
   * setRevokedconsentHome
   * @generated
   */
  public void setRevokedconsentHome(com.ardais.bigr.iltds.beans.RevokedconsentHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Revokedconsent findByPrimaryKey(
    com.ardais.bigr.iltds.beans.RevokedconsentKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireRevokedconsentHome().findByPrimaryKey(key);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Revokedconsent create(
    java.lang.String argConsent_id,
    java.lang.String argArdais_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireRevokedconsentHome().create(argConsent_id, argArdais_id);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Revokedconsent create(java.lang.String argConsent_id)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireRevokedconsentHome().create(argConsent_id);
  }
}
