package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ManifestAccessBean
 * @generated
 */
public class ManifestAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.ManifestAccessBeanData {
  /**
   * @generated
   */
  private Manifest __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_manifest_number;
  /**
   * getShip_staff_id
   * @generated
   */
  public java.lang.String getShip_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ship_staff_id")));
  }
  /**
   * setShip_staff_id
   * @generated
   */
  public void setShip_staff_id(java.lang.String newValue) {
    __setCache("ship_staff_id", newValue);
  }
  /**
   * getShip_verify_datetime
   * @generated
   */
  public java.sql.Timestamp getShip_verify_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("ship_verify_datetime")));
  }
  /**
   * setShip_verify_datetime
   * @generated
   */
  public void setShip_verify_datetime(java.sql.Timestamp newValue) {
    __setCache("ship_verify_datetime", newValue);
  }
  /**
   * getReceipt_datetime
   * @generated
   */
  public java.sql.Timestamp getReceipt_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("receipt_datetime")));
  }
  /**
   * setReceipt_datetime
   * @generated
   */
  public void setReceipt_datetime(java.sql.Timestamp newValue) {
    __setCache("receipt_datetime", newValue);
  }
  /**
   * getReceipt_verify_datetime
   * @generated
   */
  public java.sql.Timestamp getReceipt_verify_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("receipt_verify_datetime")));
  }
  /**
   * setReceipt_verify_datetime
   * @generated
   */
  public void setReceipt_verify_datetime(java.sql.Timestamp newValue) {
    __setCache("receipt_verify_datetime", newValue);
  }
  /**
   * getShipment_status
   * @generated
   */
  public java.lang.String getShipment_status()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("shipment_status")));
  }
  /**
   * setShipment_status
   * @generated
   */
  public void setShipment_status(java.lang.String newValue) {
    __setCache("shipment_status", newValue);
  }
  /**
   * getShip_datetime
   * @generated
   */
  public java.sql.Timestamp getShip_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("ship_datetime")));
  }
  /**
   * setShip_datetime
   * @generated
   */
  public void setShip_datetime(java.sql.Timestamp newValue) {
    __setCache("ship_datetime", newValue);
  }
  /**
   * getAirbill_tracking_number
   * @generated
   */
  public java.lang.String getAirbill_tracking_number()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("airbill_tracking_number")));
  }
  /**
   * setAirbill_tracking_number
   * @generated
   */
  public void setAirbill_tracking_number(java.lang.String newValue) {
    __setCache("airbill_tracking_number", newValue);
  }
  /**
   * getMnft_create_staff_id
   * @generated
   */
  public java.lang.String getMnft_create_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("mnft_create_staff_id")));
  }
  /**
   * setMnft_create_staff_id
   * @generated
   */
  public void setMnft_create_staff_id(java.lang.String newValue) {
    __setCache("mnft_create_staff_id", newValue);
  }
  /**
   * getReceipt_verify_staff_id
   * @generated
   */
  public java.lang.String getReceipt_verify_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("receipt_verify_staff_id")));
  }
  /**
   * setReceipt_verify_staff_id
   * @generated
   */
  public void setReceipt_verify_staff_id(java.lang.String newValue) {
    __setCache("receipt_verify_staff_id", newValue);
  }
  /**
   * getReceipt_by_staff_id
   * @generated
   */
  public java.lang.String getReceipt_by_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("receipt_by_staff_id")));
  }
  /**
   * setReceipt_by_staff_id
   * @generated
   */
  public void setReceipt_by_staff_id(java.lang.String newValue) {
    __setCache("receipt_by_staff_id", newValue);
  }
  /**
   * getShip_verify_staff_id
   * @generated
   */
  public java.lang.String getShip_verify_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ship_verify_staff_id")));
  }
  /**
   * setShip_verify_staff_id
   * @generated
   */
  public void setShip_verify_staff_id(java.lang.String newValue) {
    __setCache("ship_verify_staff_id", newValue);
  }
  /**
   * getMnft_create_datetime
   * @generated
   */
  public java.sql.Timestamp getMnft_create_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("mnft_create_datetime")));
  }
  /**
   * setMnft_create_datetime
   * @generated
   */
  public void setMnft_create_datetime(java.sql.Timestamp newValue) {
    __setCache("mnft_create_datetime", newValue);
  }
  /**
   * setInit_manifest_number
   * @generated
   */
  public void setInit_manifest_number(java.lang.String newValue) {
    this.init_manifest_number = (newValue);
  }
  /**
   * ManifestAccessBean
   * @generated
   */
  public ManifestAccessBean() {
    super();
  }
  /**
   * ManifestAccessBean
   * @generated
   */
  public ManifestAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Manifest";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.ManifestHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.ManifestHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.ManifestHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Manifest ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Manifest) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Manifest.class);

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

    ejbRef = ejbHome().create(init_manifest_number);
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
      com.ardais.bigr.iltds.beans.ManifestKey pKey = (com.ardais.bigr.iltds.beans.ManifestKey) this
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
   * findByBoxID
   * @generated
   */
  public java.util.Enumeration findByBoxID(java.lang.String boxID)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.ManifestHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByBoxID(boxID);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * ManifestAccessBean
   * @generated
   */
  public ManifestAccessBean(com.ardais.bigr.iltds.beans.ManifestKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * findByWaybill
   * @generated
   */
  public java.util.Enumeration findByWaybill(java.lang.String waybill)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.ManifestHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByWaybill(waybill);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * addSamplebox
   * @generated
   */
  public void addSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addSamplebox(aSamplebox);
  }
  /**
   * secondaryRemoveSamplebox
   * @generated
   */
  public void secondaryRemoveSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveSamplebox(aSamplebox);
  }
  /**
   * removeSamplebox
   * @generated
   */
  public void removeSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().removeSamplebox(aSamplebox);
  }
  /**
   * getSamplebox
   * @generated
   */
  public java.util.Enumeration getSamplebox()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getSamplebox();
  }
  /**
   * secondaryAddSamplebox
   * @generated
   */
  public void secondaryAddSamplebox(com.ardais.bigr.iltds.beans.Samplebox aSamplebox)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddSamplebox(aSamplebox);
  }
}
