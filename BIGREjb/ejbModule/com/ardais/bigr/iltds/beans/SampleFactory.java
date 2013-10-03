package com.ardais.bigr.iltds.beans;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * SampleFactory
 * @generated
 */
public class SampleFactory extends AbstractEJBFactory {
  /**
   * SampleFactory
   * @generated
   */
  public SampleFactory() {
    super();
  }
  /**
   * _acquireSampleHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.SampleHome _acquireSampleHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.SampleHome) _acquireEJBHome();
  }
  /**
   * acquireSampleHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.SampleHome acquireSampleHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.SampleHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Sample";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.SampleHome.class;
  }
  /**
   * resetSampleHome
   * @generated
   */
  public void resetSampleHome() {
    resetEJBHome();
  }
  /**
   * setSampleHome
   * @generated
   */
  public void setSampleHome(com.ardais.bigr.iltds.beans.SampleHome home) {
    setEJBHome(home);
  }
  /**
   * findSampleByAsm
   * @generated
   */
  public java.util.Enumeration findSampleByAsm(com.ardais.bigr.iltds.beans.AsmKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireSampleHome().findSampleByAsm(inKey);
  }
  /**
   * findAll
   * @generated
   */
  public java.util.Enumeration findAll() throws java.rmi.RemoteException, javax.ejb.FinderException {
    return _acquireSampleHome().findAll();
  }
  /**
   * findSampleBySamplebox
   * @generated
   */
  public java.util.Enumeration findSampleBySamplebox(com.ardais.bigr.iltds.beans.SampleboxKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireSampleHome().findSampleBySamplebox(inKey);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Sample findByPrimaryKey(
    com.ardais.bigr.iltds.beans.SampleKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireSampleHome().findByPrimaryKey(key);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Sample create(
    java.lang.String sample_barcode_id,
    java.lang.String importedYN,
    java.lang.String sampleTypeCid) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireSampleHome().create(sample_barcode_id, importedYN, sampleTypeCid);
  }
}
