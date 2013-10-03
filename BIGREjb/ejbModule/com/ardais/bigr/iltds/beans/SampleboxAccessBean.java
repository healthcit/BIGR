package com.ardais.bigr.iltds.beans;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * SampleboxAccessBean
 * @generated
 */
public class SampleboxAccessBean
  extends AbstractEntityAccessBean
  implements com.ardais.bigr.iltds.beans.SampleboxAccessBeanData {
  /**
   * @generated
   */
  private Samplebox __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_box_barcode_id;
  /**
   * getManifestKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ManifestKey getManifestKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.ManifestKey) __getCache("manifestKey")));
  }
  /**
   * getBoxLayoutId
   * @generated
   */
  public java.lang.String getBoxLayoutId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("boxLayoutId")));
  }
  /**
   * setBoxLayoutId
   * @generated
   */
  public void setBoxLayoutId(java.lang.String newValue) {
    __setCache("boxLayoutId", newValue);
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
   * getBox_check_out_reason
   * @generated
   */
  public java.lang.String getBox_check_out_reason()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("box_check_out_reason")));
  }
  /**
   * setBox_check_out_reason
   * @generated
   */
  public void setBox_check_out_reason(java.lang.String newValue) {
    __setCache("box_check_out_reason", newValue);
  }
  /**
   * getBox_status
   * @generated
   */
  public java.lang.String getBox_status()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("box_status")));
  }
  /**
   * setBox_status
   * @generated
   */
  public void setBox_status(java.lang.String newValue) {
    __setCache("box_status", newValue);
  }
  /**
   * getBox_checkout_request_staff_id
   * @generated
   */
  public java.lang.String getBox_checkout_request_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("box_checkout_request_staff_id")));
  }
  /**
   * setBox_checkout_request_staff_id
   * @generated
   */
  public void setBox_checkout_request_staff_id(java.lang.String newValue) {
    __setCache("box_checkout_request_staff_id", newValue);
  }
  /**
   * getManifest_order
   * @generated
   */
  public java.math.BigDecimal getManifest_order()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("manifest_order")));
  }
  /**
   * setManifest_order
   * @generated
   */
  public void setManifest_order(java.math.BigDecimal newValue) {
    __setCache("manifest_order", newValue);
  }
  /**
   * getBox_check_in_date
   * @generated
   */
  public java.sql.Timestamp getBox_check_in_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("box_check_in_date")));
  }
  /**
   * setBox_check_in_date
   * @generated
   */
  public void setBox_check_in_date(java.sql.Timestamp newValue) {
    __setCache("box_check_in_date", newValue);
  }
  /**
   * getBox_note
   * @generated
   */
  public java.lang.String getBox_note()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("box_note")));
  }
  /**
   * setBox_note
   * @generated
   */
  public void setBox_note(java.lang.String newValue) {
    __setCache("box_note", newValue);
  }
  /**
   * getBox_check_out_date
   * @generated
   */
  public java.sql.Timestamp getBox_check_out_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("box_check_out_date")));
  }
  /**
   * setBox_check_out_date
   * @generated
   */
  public void setBox_check_out_date(java.sql.Timestamp newValue) {
    __setCache("box_check_out_date", newValue);
  }
  /**
   * setInit_box_barcode_id
   * @generated
   */
  public void setInit_box_barcode_id(java.lang.String newValue) {
    this.init_box_barcode_id = (newValue);
  }
  /**
   * SampleboxAccessBean
   * @generated
   */
  public SampleboxAccessBean() {
    super();
  }
  /**
   * SampleboxAccessBean
   * @generated
   */
  public SampleboxAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Samplebox";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.SampleboxHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.SampleboxHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.SampleboxHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Samplebox ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Samplebox) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Samplebox.class);

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

    ejbRef = ejbHome().create(init_box_barcode_id);
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
      com.ardais.bigr.iltds.beans.SampleboxKey pKey = (com.ardais.bigr.iltds.beans.SampleboxKey) this
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
   * SampleboxAccessBean
   * @generated
   */
  public SampleboxAccessBean(com.ardais.bigr.iltds.beans.SampleboxKey key)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(key);
  }
  /**
   * findSampleboxByManifest
   * @generated
   */
  public java.util.Enumeration findSampleboxByManifest(com.ardais.bigr.iltds.beans.ManifestKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.SampleboxHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findSampleboxByManifest(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * getBoxlocation
   * @generated
   */
  public java.util.Enumeration getBoxlocation()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getBoxlocation();
  }
  /**
   * secondarySetManifest
   * @generated
   */
  public void secondarySetManifest(com.ardais.bigr.iltds.beans.Manifest aManifest)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondarySetManifest(aManifest);
  }
  /**
   * privateSetManifestKey
   * @generated
   */
  public void privateSetManifestKey(com.ardais.bigr.iltds.beans.ManifestKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetManifestKey(inKey);
  }
  /**
   * removeBoxlocation
   * @generated
   */
  public void removeBoxlocation(com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().removeBoxlocation(aBoxlocation);
  }
  /**
   * secondaryRemoveSample
   * @generated
   */
  public void secondaryRemoveSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveSample(aSample);
  }
  /**
   * addBoxlocation
   * @generated
   */
  public void addBoxlocation(com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addBoxlocation(aBoxlocation);
  }
  /**
   * secondaryAddBoxlocation
   * @generated
   */
  public void secondaryAddBoxlocation(com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddBoxlocation(aBoxlocation);
  }
  /**
   * setManifest
   * @generated
   */
  public void setManifest(com.ardais.bigr.iltds.beans.Manifest aManifest)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setManifest(aManifest);
  }
  /**
   * addSample
   * @generated
   */
  public void addSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addSample(aSample);
  }
  /**
   * getManifest
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ManifestAccessBean getManifest()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.iltds.beans.Manifest localEJBRef = ejbRef().getManifest();
    if (localEJBRef != null)
      return new com.ardais.bigr.iltds.beans.ManifestAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * secondaryAddSample
   * @generated
   */
  public void secondaryAddSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddSample(aSample);
  }
  /**
   * getSample
   * @generated
   */
  public java.util.Enumeration getSample()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getSample();
  }
  /**
   * secondaryRemoveBoxlocation
   * @generated
   */
  public void secondaryRemoveBoxlocation(com.ardais.bigr.iltds.beans.Boxlocation aBoxlocation)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveBoxlocation(aBoxlocation);
  }
  /**
   * removeSample
   * @generated
   */
  public void removeSample(com.ardais.bigr.iltds.beans.Sample aSample)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().removeSample(aSample);
  }
}
