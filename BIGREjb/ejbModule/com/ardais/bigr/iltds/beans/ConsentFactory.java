package com.ardais.bigr.iltds.beans;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ConsentFactory
 * @generated
 */
public class ConsentFactory extends AbstractEJBFactory {
  /**
   * ConsentFactory
   * @generated
   */
  public ConsentFactory() {
    super();
  }
  /**
   * _acquireConsentHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.ConsentHome _acquireConsentHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.ConsentHome) _acquireEJBHome();
  }
  /**
   * acquireConsentHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ConsentHome acquireConsentHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.ConsentHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Consent";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.ConsentHome.class;
  }
  /**
   * resetConsentHome
   * @generated
   */
  public void resetConsentHome() {
    resetEJBHome();
  }
  /**
   * setConsentHome
   * @generated
   */
  public void setConsentHome(com.ardais.bigr.iltds.beans.ConsentHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Consent findByPrimaryKey(
    com.ardais.bigr.iltds.beans.ConsentKey primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireConsentHome().findByPrimaryKey(primaryKey);
  }
  /**
   * findConsentByGeolocation
   * @generated
   */
  public java.util.Enumeration findConsentByGeolocation(
    com.ardais.bigr.iltds.beans.GeolocationKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireConsentHome().findConsentByGeolocation(inKey);
  }
  /**
   * findByConsentID
   * @generated
   */
  public java.util.Enumeration findByConsentID(java.lang.String consentID)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireConsentHome().findByConsentID(consentID);
  }
  /**
   * findConsentByArdaisID
   * @generated
   */
  public java.util.Enumeration findConsentByArdaisID(java.lang.String consentID)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireConsentHome().findConsentByArdaisID(consentID);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Consent create(
    java.lang.String consent_id,
    java.math.BigDecimal policy_id,
    java.lang.String ardais_id,
    java.lang.String imported_yn,
    java.lang.String ardais_acct_key,
    java.lang.String case_registration_form)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireConsentHome().create(
      consent_id,
      policy_id,
      ardais_id,
      imported_yn,
      ardais_acct_key,
      case_registration_form);
  }
}
