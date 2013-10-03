package com.ardais.bigr.lims.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * SlideAccessBean
 * @generated
 */
public class SlideAccessBean
  extends AbstractEntityAccessBean
  implements com.ardais.bigr.lims.beans.SlideAccessBeanData {
  /**
   * @generated
   */
  private Slide __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_slideId;
  /**
   * @generated
   */
  private java.sql.Timestamp init_createDate;
  /**
   * @generated
   */
  private java.lang.String init_sampleBarcodeId;
  /**
   * @generated
   */
  private java.lang.String init_createUser;
  /**
   * getSectionLevel
   * @generated
   */
  public int getSectionLevel()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((Integer) __getCache("sectionLevel")).intValue());
  }
  /**
   * setSectionLevel
   * @generated
   */
  public void setSectionLevel(int newValue) {
    __setCache("sectionLevel", new Integer(newValue));
  }
  /**
   * getDestroyDate
   * @generated
   */
  public java.sql.Timestamp getDestroyDate()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("destroyDate")));
  }
  /**
   * setDestroyDate
   * @generated
   */
  public void setDestroyDate(java.sql.Timestamp newValue) {
    __setCache("destroyDate", newValue);
  }
  /**
   * getSampleBarcodeId
   * @generated
   */
  public java.lang.String getSampleBarcodeId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("sampleBarcodeId")));
  }
  /**
   * setSampleBarcodeId
   * @generated
   */
  public void setSampleBarcodeId(java.lang.String newValue) {
    __setCache("sampleBarcodeId", newValue);
  }
  /**
   * getCurrentLocation
   * @generated
   */
  public java.lang.String getCurrentLocation()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("currentLocation")));
  }
  /**
   * setCurrentLocation
   * @generated
   */
  public void setCurrentLocation(java.lang.String newValue) {
    __setCache("currentLocation", newValue);
  }
  /**
   * getSectionNumber
   * @generated
   */
  public int getSectionNumber()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((Integer) __getCache("sectionNumber")).intValue());
  }
  /**
   * setSectionNumber
   * @generated
   */
  public void setSectionNumber(int newValue) {
    __setCache("sectionNumber", new Integer(newValue));
  }
  /**
   * getCreateDate
   * @generated
   */
  public java.sql.Timestamp getCreateDate()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("createDate")));
  }
  /**
   * setCreateDate
   * @generated
   */
  public void setCreateDate(java.sql.Timestamp newValue) {
    __setCache("createDate", newValue);
  }
  /**
   * getSectionProc
   * @generated
   */
  public java.lang.String getSectionProc()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("sectionProc")));
  }
  /**
   * setSectionProc
   * @generated
   */
  public void setSectionProc(java.lang.String newValue) {
    __setCache("sectionProc", newValue);
  }
  /**
   * getCreateUser
   * @generated
   */
  public java.lang.String getCreateUser()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("createUser")));
  }
  /**
   * setCreateUser
   * @generated
   */
  public void setCreateUser(java.lang.String newValue) {
    __setCache("createUser", newValue);
  }
  /**
   * setInit_slideId
   * @generated
   */
  public void setInit_slideId(java.lang.String newValue) {
    this.init_slideId = (newValue);
  }
  /**
   * setInit_createDate
   * @generated
   */
  public void setInit_createDate(java.sql.Timestamp newValue) {
    this.init_createDate = (newValue);
  }
  /**
   * setInit_sampleBarcodeId
   * @generated
   */
  public void setInit_sampleBarcodeId(java.lang.String newValue) {
    this.init_sampleBarcodeId = (newValue);
  }
  /**
   * setInit_createUser
   * @generated
   */
  public void setInit_createUser(java.lang.String newValue) {
    this.init_createUser = (newValue);
  }
  /**
   * SlideAccessBean
   * @generated
   */
  public SlideAccessBean() {
    super();
  }
  /**
   * SlideAccessBean
   * @generated
   */
  public SlideAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "ejb/com/ardais/bigr/lims/beans/SlideHome";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.lims.beans.SlideHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.lims.beans.SlideHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.lims.beans.SlideHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.lims.beans.Slide ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.lims.beans.Slide) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.lims.beans.Slide.class);

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

    ejbRef = ejbHome().create(init_slideId, init_createDate, init_sampleBarcodeId, init_createUser);
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
      java.lang.String pKey = (java.lang.String) this.__getKey();
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
   * SlideAccessBean
   * @generated
   */
  public SlideAccessBean(java.lang.String primaryKey) throws javax.naming.NamingException,
    javax.ejb.FinderException, javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
}
