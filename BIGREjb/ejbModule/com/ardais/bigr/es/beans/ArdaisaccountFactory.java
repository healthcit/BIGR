package com.ardais.bigr.es.beans;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ArdaisaccountFactory
 * @generated
 */
public class ArdaisaccountFactory extends AbstractEJBFactory {
  /**
   * ArdaisaccountFactory
   * @generated
   */
  public ArdaisaccountFactory() {
    super();
  }
  /**
   * _acquireArdaisaccountHome
   * @generated
   */
  protected com.ardais.bigr.es.beans.ArdaisaccountHome _acquireArdaisaccountHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.es.beans.ArdaisaccountHome) _acquireEJBHome();
  }
  /**
   * acquireArdaisaccountHome
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisaccountHome acquireArdaisaccountHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ArdaisaccountHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/es/beans/Ardaisaccount";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.es.beans.ArdaisaccountHome.class;
  }
  /**
   * resetArdaisaccountHome
   * @generated
   */
  public void resetArdaisaccountHome() {
    resetEJBHome();
  }
  /**
   * setArdaisaccountHome
   * @generated
   */
  public void setArdaisaccountHome(com.ardais.bigr.es.beans.ArdaisaccountHome home) {
    setEJBHome(home);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisaccount create(
    java.lang.String ardais_acct_key,
    java.lang.String linked_cases_only_yn,
    java.lang.String argPasswordPolicyCid,
    int argPasswordLifeSpan) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireArdaisaccountHome().create(
      ardais_acct_key,
      linked_cases_only_yn,
      argPasswordPolicyCid,
      argPasswordLifeSpan);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisaccount findByPrimaryKey(
    com.ardais.bigr.es.beans.ArdaisaccountKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisaccountHome().findByPrimaryKey(key);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisaccount create(java.lang.String ardais_acct_key)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireArdaisaccountHome().create(ardais_acct_key);
  }
}
