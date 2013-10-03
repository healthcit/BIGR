package com.ardais.bigr.es.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ArdaisaddressFactory
 * @generated
 */
public class ArdaisaddressFactory extends AbstractEJBFactory {
  /**
   * ArdaisaddressFactory
   * @generated
   */
  public ArdaisaddressFactory() {
    super();
  }
  /**
   * _acquireArdaisaddressHome
   * @generated
   */
  protected com.ardais.bigr.es.beans.ArdaisaddressHome _acquireArdaisaddressHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.es.beans.ArdaisaddressHome) _acquireEJBHome();
  }
  /**
   * acquireArdaisaddressHome
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisaddressHome acquireArdaisaddressHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ArdaisaddressHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/es/beans/Ardaisaddress";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.es.beans.ArdaisaddressHome.class;
  }
  /**
   * resetArdaisaddressHome
   * @generated
   */
  public void resetArdaisaddressHome() {
    resetEJBHome();
  }
  /**
   * setArdaisaddressHome
   * @generated
   */
  public void setArdaisaddressHome(com.ardais.bigr.es.beans.ArdaisaddressHome home) {
    setEJBHome(home);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisaddress create(
    java.math.BigDecimal argAddress_id,
    java.lang.String argArdais_acct_key,
    java.lang.String argAddress1) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireArdaisaddressHome().create(argAddress_id, argArdais_acct_key, argAddress1);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisaddress findByPrimaryKey(
    com.ardais.bigr.es.beans.ArdaisaddressKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisaddressHome().findByPrimaryKey(key);
  }
  /**
   * findByAccountandType
   * @generated
   */
  public java.util.Enumeration findByAccountandType(
    java.lang.String arg_Account,
    java.lang.String arg_Type) throws java.rmi.RemoteException, javax.ejb.FinderException {
    return _acquireArdaisaddressHome().findByAccountandType(arg_Account, arg_Type);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisaddress create(
    java.math.BigDecimal argAddress_id,
    java.lang.String argArdais_acct_key) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireArdaisaddressHome().create(argAddress_id, argArdais_acct_key);
  }
}
