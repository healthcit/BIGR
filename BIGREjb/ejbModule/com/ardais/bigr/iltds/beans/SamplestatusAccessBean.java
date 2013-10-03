package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * SamplestatusAccessBean
 * @generated
 */
public class SamplestatusAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.SamplestatusAccessBeanData {
  /**
   * @generated
   */
  private Samplestatus __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_argStatus_type_code;
  /**
   * @generated
   */
  private com.ardais.bigr.iltds.beans.SampleKey init_argSample;
  /**
   * @generated
   */
  private java.sql.Timestamp init_argSample_status_datetime;
  /**
   * getStatus_type_code
   * @generated
   */
  public java.lang.String getStatus_type_code()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("status_type_code")));
  }
  /**
   * setStatus_type_code
   * @generated
   */
  public void setStatus_type_code(java.lang.String newValue) {
    __setCache("status_type_code", newValue);
  }
  /**
   * getSampleKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.SampleKey getSampleKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.SampleKey) __getCache("sampleKey")));
  }
  /**
   * getSample_status_datetime
   * @generated
   */
  public java.sql.Timestamp getSample_status_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("sample_status_datetime")));
  }
  /**
   * setSample_status_datetime
   * @generated
   */
  public void setSample_status_datetime(java.sql.Timestamp newValue) {
    __setCache("sample_status_datetime", newValue);
  }
  /**
   * setInit_argStatus_type_code
   * @generated
   */
  public void setInit_argStatus_type_code(java.lang.String newValue) {
    this.init_argStatus_type_code = (newValue);
  }
  /**
   * setInit_argSample
   * @generated
   */
  public void setInit_argSample(com.ardais.bigr.iltds.beans.SampleKey newValue) {
    this.init_argSample = (newValue);
  }
  /**
   * setInit_argSample_status_datetime
   * @generated
   */
  public void setInit_argSample_status_datetime(java.sql.Timestamp newValue) {
    this.init_argSample_status_datetime = (newValue);
  }
  /**
   * SamplestatusAccessBean
   * @generated
   */
  public SamplestatusAccessBean() {
    super();
  }
  /**
   * SamplestatusAccessBean
   * @generated
   */
  public SamplestatusAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Samplestatus";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.SamplestatusHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.SamplestatusHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.SamplestatusHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Samplestatus ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Samplestatus) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Samplestatus.class);

    return __ejbRef;
  }
  /**
   * instantiateEJB
   * @generated
   */
  protected void instantiateEJB()
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    if (ejbRef() != null)
      return;

    ejbRef = ejbHome().create(
      init_argStatus_type_code,
      init_argSample,
      init_argSample_status_datetime);
  }
  /**
   * instantiateEJBByPrimaryKey
   * @generated
   */
  protected boolean instantiateEJBByPrimaryKey()
    throws javax.ejb.CreateException,
    java.rmi.RemoteException,
    javax.naming.NamingException {
    boolean result = false;

    if (ejbRef() != null)
      return true;

    try {
      com.ardais.bigr.iltds.beans.SamplestatusKey pKey = (com.ardais.bigr.iltds.beans.SamplestatusKey) this
        .__getKey();
      if (pKey != null) {
        ejbRef = ejbHome().findByPrimaryKey(pKey);
        result = true;
      }
    } catch (javax.ejb.FinderException e) {
    }
    return result;
  }
  /**
   * refreshCopyHelper
   * @generated
   */
  public void refreshCopyHelper()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    refreshCopyHelper(ejbRef());
  }
  /**
   * commitCopyHelper
   * @generated
   */
  public void commitCopyHelper()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    commitCopyHelper(ejbRef());
  }
  /**
   * findSamplestatusBySample
   * @generated
   */
  public java.util.Enumeration findSamplestatusBySample(com.ardais.bigr.iltds.beans.SampleKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.SamplestatusHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findSamplestatusBySample(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * SamplestatusAccessBean
   * @generated
   */
  public SamplestatusAccessBean(
    java.math.BigDecimal id,
    com.ardais.bigr.iltds.beans.Sample argSample) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(id, argSample);
  }
  /**
   * SamplestatusAccessBean
   * @generated
   */
  public SamplestatusAccessBean(com.ardais.bigr.iltds.beans.SamplestatusKey primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * SamplestatusAccessBean
   * @generated
   */
  public SamplestatusAccessBean(
    java.lang.String argStatus_type_code,
    com.ardais.bigr.iltds.beans.SampleKey argSample,
    java.sql.Timestamp argSample_status_datetime,
    java.math.BigDecimal argId) throws javax.naming.NamingException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().create(argStatus_type_code, argSample, argSample_status_datetime, argId);
  }
  /**
   * findBySampleIDStatus
   * @generated
   */
  public java.util.Enumeration findBySampleIDStatus(
    java.lang.String sampleID,
    java.lang.String statusID)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.SamplestatusHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findBySampleIDStatus(sampleID, statusID);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * setSample
   * @generated
   */
  public void setSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setSample(aSample);
  }
  /**
   * getSample
   * @generated
   */
  public com.ardais.bigr.iltds.beans.SampleAccessBean getSample()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.iltds.beans.Sample localEJBRef = ejbRef().getSample();
    if (localEJBRef != null)
      return new com.ardais.bigr.iltds.beans.SampleAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * privateSetSampleKey
   * @generated
   */
  public void privateSetSampleKey(com.ardais.bigr.iltds.beans.SampleKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetSampleKey(inKey);
  }
  /**
   * secondarySetSample
   * @generated
   */
  public void secondarySetSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondarySetSample(aSample);
  }
}
