package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * SamplestatusFactory
 * @generated
 */
public class SamplestatusFactory extends AbstractEJBFactory {
  /**
   * SamplestatusFactory
   * @generated
   */
  public SamplestatusFactory() {
    super();
  }
  /**
   * _acquireSamplestatusHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.SamplestatusHome _acquireSamplestatusHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.SamplestatusHome) _acquireEJBHome();
  }
  /**
   * acquireSamplestatusHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.SamplestatusHome acquireSamplestatusHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.SamplestatusHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Samplestatus";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.SamplestatusHome.class;
  }
  /**
   * resetSamplestatusHome
   * @generated
   */
  public void resetSamplestatusHome() {
    resetEJBHome();
  }
  /**
   * setSamplestatusHome
   * @generated
   */
  public void setSamplestatusHome(com.ardais.bigr.iltds.beans.SamplestatusHome home) {
    setEJBHome(home);
  }
  /**
   * findSamplestatusBySample
   * @generated
   */
  public java.util.Enumeration findSamplestatusBySample(com.ardais.bigr.iltds.beans.SampleKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireSamplestatusHome().findSamplestatusBySample(inKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Samplestatus create(
    java.math.BigDecimal id,
    com.ardais.bigr.iltds.beans.Sample argSample)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireSamplestatusHome().create(id, argSample);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Samplestatus findByPrimaryKey(
    com.ardais.bigr.iltds.beans.SamplestatusKey primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireSamplestatusHome().findByPrimaryKey(primaryKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Samplestatus create(
    java.lang.String argStatus_type_code,
    com.ardais.bigr.iltds.beans.SampleKey argSample,
    java.sql.Timestamp argSample_status_datetime,
    java.math.BigDecimal argId) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireSamplestatusHome().create(
      argStatus_type_code,
      argSample,
      argSample_status_datetime,
      argId);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Samplestatus create(
    java.lang.String argStatus_type_code,
    com.ardais.bigr.iltds.beans.SampleKey argSample,
    java.sql.Timestamp argSample_status_datetime)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireSamplestatusHome().create(
      argStatus_type_code,
      argSample,
      argSample_status_datetime);
  }
  /**
   * findBySampleIDStatus
   * @generated
   */
  public java.util.Enumeration findBySampleIDStatus(
    java.lang.String sampleID,
    java.lang.String statusID) throws java.rmi.RemoteException, javax.ejb.FinderException {
    return _acquireSamplestatusHome().findBySampleIDStatus(sampleID, statusID);
  }
}
