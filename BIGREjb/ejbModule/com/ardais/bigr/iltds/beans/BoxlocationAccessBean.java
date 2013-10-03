package com.ardais.bigr.iltds.beans;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * BoxlocationAccessBean
 * @generated
 */
public class BoxlocationAccessBean
  extends AbstractEntityAccessBean
  implements com.ardais.bigr.iltds.beans.BoxlocationAccessBeanData {
  /**
   * @generated
   */
  private Boxlocation __ejbRef;
  /**
   * @generated
   */
  private com.ardais.bigr.iltds.assistants.StorageLocAsst init_asst;
  /**
   * getGeolocation_location_address_id
   * @generated
   */
  public java.lang.String getGeolocation_location_address_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("geolocation_location_address_id")));
  }
  /**
   * setGeolocation_location_address_id
   * @generated
   */
  public void setGeolocation_location_address_id(java.lang.String newValue) {
    __setCache("geolocation_location_address_id", newValue);
  }
  /**
   * getSampleboxKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.SampleboxKey getSampleboxKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.SampleboxKey) __getCache("sampleboxKey")));
  }
  /**
   * getSamplebox_box_barcode_id
   * @generated
   */
  public java.lang.String getSamplebox_box_barcode_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("samplebox_box_barcode_id")));
  }
  /**
   * setSamplebox_box_barcode_id
   * @generated
   */
  public void setSamplebox_box_barcode_id(java.lang.String newValue) {
    __setCache("samplebox_box_barcode_id", newValue);
  }
  /**
   * getAvailable_ind
   * @generated
   */
  public java.lang.String getAvailable_ind()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("available_ind")));
  }
  /**
   * setAvailable_ind
   * @generated
   */
  public void setAvailable_ind(java.lang.String newValue) {
    __setCache("available_ind", newValue);
  }
  /**
   * getStorageTypeCid
   * @generated
   */
  public java.lang.String getStorageTypeCid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("storageTypeCid")));
  }
  /**
   * setStorageTypeCid
   * @generated
   */
  public void setStorageTypeCid(java.lang.String newValue) {
    __setCache("storageTypeCid", newValue);
  }
  /**
   * getLocation_status
   * @generated
   */
  public java.lang.String getLocation_status()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("location_status")));
  }
  /**
   * setLocation_status
   * @generated
   */
  public void setLocation_status(java.lang.String newValue) {
    __setCache("location_status", newValue);
  }
  /**
   * getGeolocationKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.GeolocationKey getGeolocationKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.GeolocationKey) __getCache("geolocationKey")));
  }
  /**
   * setInit_asst
   * @generated
   */
  public void setInit_asst(com.ardais.bigr.iltds.assistants.StorageLocAsst newValue) {
    this.init_asst = (newValue);
  }
  /**
   * BoxlocationAccessBean
   * @generated
   */
  public BoxlocationAccessBean() {
    super();
  }
  /**
   * BoxlocationAccessBean
   * @generated
   */
  public BoxlocationAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Boxlocation";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.BoxlocationHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.BoxlocationHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.BoxlocationHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Boxlocation ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Boxlocation) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Boxlocation.class);

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

    ejbRef = ejbHome().create(init_asst);
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
      com.ardais.bigr.iltds.beans.BoxlocationKey pKey = (com.ardais.bigr.iltds.beans.BoxlocationKey) this
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
   * findBoxlocationBySamplebox
   * @generated
   */
  public java.util.Enumeration findBoxlocationBySamplebox(
    com.ardais.bigr.iltds.beans.SampleboxKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.BoxlocationHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findBoxlocationBySamplebox(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findBoxlocationByGeolocation
   * @generated
   */
  public java.util.Enumeration findBoxlocationByGeolocation(
    com.ardais.bigr.iltds.beans.GeolocationKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.BoxlocationHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findBoxlocationByGeolocation(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findBoxLocationByStorageTypeCid
   * @generated
   */
  public java.util.Enumeration findBoxLocationByStorageTypeCid(
    java.lang.String locationAddressId,
    java.lang.String storageTypeCid,
    java.lang.String availableInd)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.BoxlocationHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findBoxLocationByStorageTypeCid(
      locationAddressId,
      storageTypeCid,
      availableInd);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * BoxlocationAccessBean
   * @generated
   */
  public BoxlocationAccessBean(com.ardais.bigr.iltds.beans.BoxlocationKey primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * findBoxLocationByBoxId
   * @generated
   */
  public java.util.Enumeration findBoxLocationByBoxId(java.lang.String box)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.BoxlocationHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findBoxLocationByBoxId(box);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * secondarySetSamplebox
   * @generated
   */
  public void secondarySetSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondarySetSamplebox(aSamplebox);
  }
  /**
   * setSamplebox
   * @generated
   */
  public void setSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setSamplebox(aSamplebox);
  }
  /**
   * privateSetSampleboxKey
   * @generated
   */
  public void privateSetSampleboxKey(com.ardais.bigr.iltds.beans.SampleboxKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetSampleboxKey(inKey);
  }
  /**
   * getSamplebox
   * @generated
   */
  public com.ardais.bigr.iltds.beans.SampleboxAccessBean getSamplebox()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.iltds.beans.Samplebox localEJBRef = ejbRef().getSamplebox();
    if (localEJBRef != null)
      return new com.ardais.bigr.iltds.beans.SampleboxAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * getGeolocation
   * @generated
   */
  public com.ardais.bigr.iltds.beans.GeolocationAccessBean getGeolocation()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.iltds.beans.Geolocation localEJBRef = ejbRef().getGeolocation();
    if (localEJBRef != null)
      return new com.ardais.bigr.iltds.beans.GeolocationAccessBean(localEJBRef);
    else
      return null;
  }
}
