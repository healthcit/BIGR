package com.ardais.bigr.lims.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * IncidentFactory
 * @generated
 */
public class IncidentFactory extends AbstractEJBFactory {
  /**
   * IncidentFactory
   * @generated
   */
  public IncidentFactory() {
    super();
  }
  /**
   * _acquireIncidentHome
   * @generated
   */
  protected com.ardais.bigr.lims.beans.IncidentHome _acquireIncidentHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.lims.beans.IncidentHome) _acquireEJBHome();
  }
  /**
   * acquireIncidentHome
   * @generated
   */
  public com.ardais.bigr.lims.beans.IncidentHome acquireIncidentHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.lims.beans.IncidentHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "ejb/com/ardais/bigr/lims/beans/IncidentHome";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.lims.beans.IncidentHome.class;
  }
  /**
   * resetIncidentHome
   * @generated
   */
  public void resetIncidentHome() {
    resetEJBHome();
  }
  /**
   * setIncidentHome
   * @generated
   */
  public void setIncidentHome(com.ardais.bigr.lims.beans.IncidentHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.lims.beans.Incident findByPrimaryKey(java.lang.String primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireIncidentHome().findByPrimaryKey(primaryKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.lims.beans.Incident create(
    java.lang.String incidentId,
    java.lang.String createUser,
    java.sql.Timestamp createDate,
    java.lang.String sampleId,
    java.lang.String consentId,
    java.lang.String action,
    java.lang.String reason) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireIncidentHome().create(
      incidentId,
      createUser,
      createDate,
      sampleId,
      consentId,
      action,
      reason);
  }
}
