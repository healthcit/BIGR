package com.ardais.bigr.lims.beans;
import javax.ejb.*;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * SlideFactory
 * @generated
 */
public class SlideFactory extends AbstractEJBFactory {
  /**
   * SlideFactory
   * @generated
   */
  public SlideFactory() {
    super();
  }
  /**
   * _acquireSlideHome
   * @generated
   */
  protected com.ardais.bigr.lims.beans.SlideHome _acquireSlideHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.lims.beans.SlideHome) _acquireEJBHome();
  }
  /**
   * acquireSlideHome
   * @generated
   */
  public com.ardais.bigr.lims.beans.SlideHome acquireSlideHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.lims.beans.SlideHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "ejb/com/ardais/bigr/lims/beans/SlideHome";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.lims.beans.SlideHome.class;
  }
  /**
   * resetSlideHome
   * @generated
   */
  public void resetSlideHome() {
    resetEJBHome();
  }
  /**
   * setSlideHome
   * @generated
   */
  public void setSlideHome(com.ardais.bigr.lims.beans.SlideHome home) {
    setEJBHome(home);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.lims.beans.Slide findByPrimaryKey(java.lang.String primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireSlideHome().findByPrimaryKey(primaryKey);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.lims.beans.Slide create(
    java.lang.String slideId,
    java.sql.Timestamp createDate,
    java.lang.String sampleBarcodeId,
    java.lang.String createUser) throws javax.ejb.CreateException, java.rmi.RemoteException {
    return _acquireSlideHome().create(slideId, createDate, sampleBarcodeId, createUser);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.lims.beans.Slide create(java.lang.String slideId)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireSlideHome().create(slideId);
  }
}
