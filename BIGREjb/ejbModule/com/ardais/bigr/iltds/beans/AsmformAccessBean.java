package com.ardais.bigr.iltds.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * AsmformAccessBean
 * @generated
 */
public class AsmformAccessBean
	extends AbstractEntityAccessBean
	implements com.ardais.bigr.iltds.beans.AsmformAccessBeanData {
  /**
   * @generated
   */
  private Asmform __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_argAsm_form_id;
  /**
   * @generated
   */
  private com.ardais.bigr.iltds.beans.ConsentKey init_argConsent;
  /**
   * @generated
   */
  private java.lang.String init_argArdais_id;
  /**
   * getArdais_id
   * @generated
   */
  public java.lang.String getArdais_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_id")));
  }
  /**
   * setArdais_id
   * @generated
   */
  public void setArdais_id(java.lang.String newValue) {
    __setCache("ardais_id", newValue);
  }
  /**
   * getArdais_staff_id
   * @generated
   */
  public java.lang.String getArdais_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_staff_id")));
  }
  /**
   * setArdais_staff_id
   * @generated
   */
  public void setArdais_staff_id(java.lang.String newValue) {
    __setCache("ardais_staff_id", newValue);
  }
  /**
   * getConsentKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ConsentKey getConsentKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.ConsentKey) __getCache("consentKey")));
  }
  /**
   * getCreation_time
   * @generated
   */
  public java.sql.Timestamp getCreation_time()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("creation_time")));
  }
  /**
   * setCreation_time
   * @generated
   */
  public void setCreation_time(java.sql.Timestamp newValue) {
    __setCache("creation_time", newValue);
  }
  /**
   * getLink_staff_id
   * @generated
   */
  public java.lang.String getLink_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("link_staff_id")));
  }
  /**
   * setLink_staff_id
   * @generated
   */
  public void setLink_staff_id(java.lang.String newValue) {
    __setCache("link_staff_id", newValue);
  }
  /**
   * getSurgical_specimen_id
   * @generated
   */
  public java.lang.String getSurgical_specimen_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("surgical_specimen_id")));
  }
  /**
   * setSurgical_specimen_id
   * @generated
   */
  public void setSurgical_specimen_id(java.lang.String newValue) {
    __setCache("surgical_specimen_id", newValue);
  }
  /**
   * getGrossing_date
   * @generated
   */
  public java.sql.Timestamp getGrossing_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("grossing_date")));
  }
  /**
   * setGrossing_date
   * @generated
   */
  public void setGrossing_date(java.sql.Timestamp newValue) {
    __setCache("grossing_date", newValue);
  }
  /**
   * getArdaisstaffKey
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ArdaisstaffKey getArdaisstaffKey()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((com.ardais.bigr.iltds.beans.ArdaisstaffKey) __getCache("ardaisstaffKey")));
  }
  /**
   * getRemoval_date
   * @generated
   */
  public java.sql.Timestamp getRemoval_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("removal_date")));
  }
  /**
   * setRemoval_date
   * @generated
   */
  public void setRemoval_date(java.sql.Timestamp newValue) {
    __setCache("removal_date", newValue);
  }
  /**
   * getSurgical_specimen_id_other
   * @generated
   */
  public java.lang.String getSurgical_specimen_id_other()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("surgical_specimen_id_other")));
  }
  /**
   * setSurgical_specimen_id_other
   * @generated
   */
  public void setSurgical_specimen_id_other(java.lang.String newValue) {
    __setCache("surgical_specimen_id_other", newValue);
  }
  /**
   * getAcc_surgical_removal_time
   * @generated
   */
  public java.lang.String getAcc_surgical_removal_time()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("acc_surgical_removal_time")));
  }
  /**
   * setAcc_surgical_removal_time
   * @generated
   */
  public void setAcc_surgical_removal_time(java.lang.String newValue) {
    __setCache("acc_surgical_removal_time", newValue);
  }
  /**
   * setInit_argAsm_form_id
   * @generated
   */
  public void setInit_argAsm_form_id(java.lang.String newValue) {
    this.init_argAsm_form_id = (newValue);
  }
  /**
   * setInit_argConsent
   * @generated
   */
  public void setInit_argConsent(com.ardais.bigr.iltds.beans.ConsentKey newValue) {
    this.init_argConsent = (newValue);
  }
  /**
   * setInit_argArdais_id
   * @generated
   */
  public void setInit_argArdais_id(java.lang.String newValue) {
    this.init_argArdais_id = (newValue);
  }
  /**
   * AsmformAccessBean
   * @generated
   */
  public AsmformAccessBean() {
    super();
  }
  /**
   * AsmformAccessBean
   * @generated
   */
  public AsmformAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Asmform";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.AsmformHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.AsmformHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.AsmformHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Asmform ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Asmform) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Asmform.class);

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

    ejbRef = ejbHome().create(init_argAsm_form_id, init_argConsent, init_argArdais_id);
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
      com.ardais.bigr.iltds.beans.AsmformKey pKey = (com.ardais.bigr.iltds.beans.AsmformKey) this
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
   * AsmformAccessBean
   * @generated
   */
  public AsmformAccessBean(com.ardais.bigr.iltds.beans.AsmformKey primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * AsmformAccessBean
   * @generated
   */
  public AsmformAccessBean(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    com.ardais.bigr.iltds.beans.ArdaisstaffKey argArdaisstaff) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(argAsm_form_id, argConsent, argArdaisstaff);
  }
  /**
   * AsmformAccessBean
   * @generated
   */
  public AsmformAccessBean(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(argAsm_form_id, argConsent);
  }
  /**
   * AsmformAccessBean
   * @generated
   */
  public AsmformAccessBean(
    java.lang.String asm_form_id,
    com.ardais.bigr.iltds.beans.Consent argConsent) throws javax.naming.NamingException,
    javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().create(asm_form_id, argConsent);
  }
  /**
   * AsmformAccessBean
   * @generated
   */
  public AsmformAccessBean(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    com.ardais.bigr.iltds.beans.ArdaisstaffKey argArdaisstaff,
    java.lang.String argArdais_id) throws javax.naming.NamingException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().create(argAsm_form_id, argConsent, argArdaisstaff, argArdais_id);
  }
  /**
   * findByASMFormID
   * @generated
   */
  public java.util.Enumeration findByASMFormID(java.lang.String asmFormID)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.AsmformHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByASMFormID(asmFormID);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findAsmformByConsent
   * @generated
   */
  public java.util.Enumeration findAsmformByConsent(com.ardais.bigr.iltds.beans.ConsentKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.AsmformHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findAsmformByConsent(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * privateSetArdaisstaffKey
   * @generated
   */
  public void privateSetArdaisstaffKey(com.ardais.bigr.iltds.beans.ArdaisstaffKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetArdaisstaffKey(inKey);
  }
  /**
   * secondarySetConsent
   * @generated
   */
  public void secondarySetConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondarySetConsent(aConsent);
  }
  /**
   * privateSetConsentKey
   * @generated
   */
  public void privateSetConsentKey(com.ardais.bigr.iltds.beans.ConsentKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetConsentKey(inKey);
  }
  /**
   * setConsent
   * @generated
   */
  public void setConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setConsent(aConsent);
  }
  /**
   * getConsent
   * @generated
   */
  public com.ardais.bigr.iltds.beans.ConsentAccessBean getConsent()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    com.ardais.bigr.iltds.beans.Consent localEJBRef = ejbRef().getConsent();
    if (localEJBRef != null)
      return new com.ardais.bigr.iltds.beans.ConsentAccessBean(localEJBRef);
    else
      return null;
  }
  /**
   * getProcedure_date
   * @generated
   */
  public java.sql.Timestamp getProcedure_date()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("procedure_date")));
  }
  /**
   * setProcedure_date
   * @generated
   */
  public void setProcedure_date(java.sql.Timestamp newValue) {
    __setCache("procedure_date", newValue);
  }
}
