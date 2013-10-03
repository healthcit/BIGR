package com.ardais.bigr.iltds.beans;
import com.ibm.etools.ejb.client.runtime.*;
/**
 * BoxlocationFactory
 * @generated
 */
public class BoxlocationFactory extends AbstractEJBFactory {
  /**
   * BoxlocationFactory
   * @generated
   */
  public BoxlocationFactory() {
    super();
  }
  /**
   * _acquireBoxlocationHome
   * @generated
   */
  protected com.ardais.bigr.iltds.beans.BoxlocationHome _acquireBoxlocationHome()
    throws java.rmi.RemoteException {
    return (com.ardais.bigr.iltds.beans.BoxlocationHome) _acquireEJBHome();
  }
  /**
   * acquireBoxlocationHome
   * @generated
   */
  public com.ardais.bigr.iltds.beans.BoxlocationHome acquireBoxlocationHome()
    throws javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.BoxlocationHome) acquireEJBHome();
  }
  /**
   * getDefaultJNDIName
   * @generated
   */
  public String getDefaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Boxlocation";
  }
  /**
   * getHomeInterface
   * @generated
   */
  protected Class getHomeInterface() {
    return com.ardais.bigr.iltds.beans.BoxlocationHome.class;
  }
  /**
   * resetBoxlocationHome
   * @generated
   */
  public void resetBoxlocationHome() {
    resetEJBHome();
  }
  /**
   * setBoxlocationHome
   * @generated
   */
  public void setBoxlocationHome(com.ardais.bigr.iltds.beans.BoxlocationHome home) {
    setEJBHome(home);
  }
  /**
   * findBoxlocationBySamplebox
   * @generated
   */
  public java.util.Enumeration findBoxlocationBySamplebox(
    com.ardais.bigr.iltds.beans.SampleboxKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireBoxlocationHome().findBoxlocationBySamplebox(inKey);
  }
  /**
   * findBoxlocationByGeolocation
   * @generated
   */
  public java.util.Enumeration findBoxlocationByGeolocation(
    com.ardais.bigr.iltds.beans.GeolocationKey inKey)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireBoxlocationHome().findBoxlocationByGeolocation(inKey);
  }
  /**
   * findBoxLocationByStorageTypeCid
   * @generated
   */
  public java.util.Enumeration findBoxLocationByStorageTypeCid(
    java.lang.String locationAddressId,
    java.lang.String storageTypeCid,
    java.lang.String availableInd) throws javax.ejb.FinderException, java.rmi.RemoteException {
    return _acquireBoxlocationHome().findBoxLocationByStorageTypeCid(
      locationAddressId,
      storageTypeCid,
      availableInd);
  }
  /**
   * findByPrimaryKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Boxlocation findByPrimaryKey(
    com.ardais.bigr.iltds.beans.BoxlocationKey primaryKey)
    throws javax.ejb.FinderException,
    java.rmi.RemoteException {
    return _acquireBoxlocationHome().findByPrimaryKey(primaryKey);
  }
  /**
   * findBoxLocationByBoxId
   * @generated
   */
  public java.util.Enumeration findBoxLocationByBoxId(java.lang.String box)
    throws java.rmi.RemoteException,
    javax.ejb.FinderException {
    return _acquireBoxlocationHome().findBoxLocationByBoxId(box);
  }
  /**
   * create
   * @generated
   */
  public com.ardais.bigr.iltds.beans.Boxlocation create(
    com.ardais.bigr.iltds.assistants.StorageLocAsst asst)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException {
    return _acquireBoxlocationHome().create(asst);
  }
}
