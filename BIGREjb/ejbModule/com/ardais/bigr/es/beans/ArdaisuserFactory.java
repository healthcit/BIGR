package com.ardais.bigr.es.beans;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * ArdaisuserFactory
 * @generated
 */
public class ArdaisuserFactory extends AbstractEJBFactory {
  /**
   * ArdaisuserFactory
   * @generated
   */
  public ArdaisuserFactory() {
    super();
  }
  /**
   * _acquireArdaisuserHome
   * @generated
   */
  protected com.ardais.bigr.es.beans.ArdaisuserHome _acquireArdaisuserHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.es.beans.ArdaisuserHome) _acquireEJBHome();
  }
  /**
   * acquireArdaisuserHome
   * @generated
   */
  public com.ardais.bigr.es.beans.ArdaisuserHome acquireArdaisuserHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.es.beans.ArdaisuserHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/es/beans/Ardaisuser";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.es.beans.ArdaisuserHome.class;
  }
  /**
   * resetArdaisuserHome
   * @generated
   */
  public void resetArdaisuserHome() {
    resetEJBHome();
  }
  /**
   * setArdaisuserHome
   * @generated
   */
  public void setArdaisuserHome(com.ardais.bigr.es.beans.ArdaisuserHome home) {
    setEJBHome(home);
  }
  /**
   * findByUserId
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisuser findByUserId(java.lang.String userId)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireArdaisuserHome().findByUserId(userId);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisuser create(
    java.lang.String argArdais_user_id,
    com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount,
    java.lang.String argPassword,
    java.lang.String argCreated_by,
    java.sql.Timestamp argCreate_date,
    java.lang.String argPasswordPolicyCid)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireArdaisuserHome().create(
      argArdais_user_id,
      argArdaisaccount,
      argPassword,
      argCreated_by,
      argCreate_date,
      argPasswordPolicyCid);
  }
  /**
   * findArdaisuserByArdaisaccount
   * @generated
   */
  public java.util.Enumeration findArdaisuserByArdaisaccount(
    com.ardais.bigr.es.beans.ArdaisaccountKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireArdaisuserHome().findArdaisuserByArdaisaccount(inKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisuser create(
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.Ardaisaccount argArdaisaccount,
    java.lang.String argPasswordPolicyCid)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireArdaisuserHome().create(ardais_user_id, argArdaisaccount, argPasswordPolicyCid);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisuser findByPrimaryKey(
    com.ardais.bigr.es.beans.ArdaisuserKey primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireArdaisuserHome().findByPrimaryKey(primaryKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.es.beans.Ardaisuser create(
    java.lang.String argArdais_user_id,
    com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount,
    java.lang.String argPasswordPolicyCid)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireArdaisuserHome().create(
      argArdais_user_id,
      argArdaisaccount,
      argPasswordPolicyCid);
  }
}
