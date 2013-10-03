package com.ardais.bigr.iltds.beans;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * SampleboxFactory
 * @generated
 */
public class SampleboxFactory extends AbstractEJBFactory {
  /**
   * SampleboxFactory
   * @generated
   */
  public SampleboxFactory() {
    super();
  }
  /**
   * _acquireSampleboxHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.SampleboxHome _acquireSampleboxHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.SampleboxHome) _acquireEJBHome();
  }
  /**
   * acquireSampleboxHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.SampleboxHome acquireSampleboxHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.SampleboxHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Samplebox";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.SampleboxHome.class;
  }
  /**
   * resetSampleboxHome
   * @generated
   */
  public void resetSampleboxHome() {
    resetEJBHome();
  }
  /**
   * setSampleboxHome
   * @generated
   */
  public void setSampleboxHome(com.ardais.bigr.iltds.beans.SampleboxHome home) {
    setEJBHome(home);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Samplebox create(java.lang.String box_barcode_id)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireSampleboxHome().create(box_barcode_id);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Samplebox findByPrimaryKey(
    com.ardais.bigr.iltds.beans.SampleboxKey key)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireSampleboxHome().findByPrimaryKey(key);
  }
  /**
   * findSampleboxByManifest
   * @generated
   */
  public java.util.Enumeration findSampleboxByManifest(com.ardais.bigr.iltds.beans.ManifestKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireSampleboxHome().findSampleboxByManifest(inKey);
  }
}
