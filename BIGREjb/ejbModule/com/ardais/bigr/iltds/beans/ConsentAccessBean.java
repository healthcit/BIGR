package com.ardais.bigr.iltds.beans;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * ConsentAccessBean
 * @generated
 */
public class ConsentAccessBean
  extends AbstractEntityAccessBean
  implements com.ardais.bigr.iltds.beans.ConsentAccessBeanData {
  /**
   * @generated
   */
  private Consent __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_consent_id;
  /**
   * @generated
   */
  private java.math.BigDecimal init_policy_id;
  /**
   * @generated
   */
  private java.lang.String init_ardais_id;
  /**
   * @generated
   */
  private java.lang.String init_imported_yn;
  /**
   * @generated
   */
  private java.lang.String init_ardais_acct_key;
  /**
   * @generated
   */
  private java.lang.String init_case_registration_form;
  /**
   * getFuture_contact_yn
   * @generated
   */
  public java.lang.String getFuture_contact_yn()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("future_contact_yn")));
  }
  /**
   * setFuture_contact_yn
   * @generated
   */
  public void setFuture_contact_yn(java.lang.String newValue) {
    __setCache("future_contact_yn", newValue);
  }
  /**
   * getConsent_release_datetime
   * @generated
   */
  public java.sql.Timestamp getConsent_release_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("consent_release_datetime")));
  }
  /**
   * setConsent_release_datetime
   * @generated
   */
  public void setConsent_release_datetime(java.sql.Timestamp newValue) {
    __setCache("consent_release_datetime", newValue);
  }
  /**
   * getConsent_release_staff_id
   * @generated
   */
  public java.lang.String getConsent_release_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consent_release_staff_id")));
  }
  /**
   * setConsent_release_staff_id
   * @generated
   */
  public void setConsent_release_staff_id(java.lang.String newValue) {
    __setCache("consent_release_staff_id", newValue);
  }
  /**
   * getConsent_pull_request_by
   * @generated
   */
  public java.lang.String getConsent_pull_request_by()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consent_pull_request_by")));
  }
  /**
   * setConsent_pull_request_by
   * @generated
   */
  public void setConsent_pull_request_by(java.lang.String newValue) {
    __setCache("consent_pull_request_by", newValue);
  }
  /**
   * getBest_diagnosis_cui
   * @generated
   */
  public java.lang.String getBest_diagnosis_cui()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("best_diagnosis_cui")));
  }
  /**
   * getForm_entry_staff_id
   * @generated
   */
  public java.lang.String getForm_entry_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("form_entry_staff_id")));
  }
  /**
   * setForm_entry_staff_id
   * @generated
   */
  public void setForm_entry_staff_id(java.lang.String newValue) {
    __setCache("form_entry_staff_id", newValue);
  }
  /**
   * getForm_entry_datetime
   * @generated
   */
  public java.sql.Timestamp getForm_entry_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("form_entry_datetime")));
  }
  /**
   * setForm_entry_datetime
   * @generated
   */
  public void setForm_entry_datetime(java.sql.Timestamp newValue) {
    __setCache("form_entry_datetime", newValue);
  }
  /**
   * getBankable_ind
   * @generated
   */
  public java.lang.String getBankable_ind()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("bankable_ind")));
  }
  /**
   * setBankable_ind
   * @generated
   */
  public void setBankable_ind(java.lang.String newValue) {
    __setCache("bankable_ind", newValue);
  }
  /**
   * getComments
   * @generated
   */
  public java.lang.String getComments()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("comments")));
  }
  /**
   * setComments
   * @generated
   */
  public void setComments(java.lang.String newValue) {
    __setCache("comments", newValue);
  }
  /**
   * getConsent_pull_reason_cd
   * @generated
   */
  public java.lang.String getConsent_pull_reason_cd()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consent_pull_reason_cd")));
  }
  /**
   * setConsent_pull_reason_cd
   * @generated
   */
  public void setConsent_pull_reason_cd(java.lang.String newValue) {
    __setCache("consent_pull_reason_cd", newValue);
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
   * getConsent_version_num
   * @generated
   */
  public java.lang.String getConsent_version_num()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consent_version_num")));
  }
  /**
   * setConsent_version_num
   * @generated
   */
  public void setConsent_version_num(java.lang.String newValue) {
    __setCache("consent_version_num", newValue);
  }
  /**
   * getForm_verified_sign_ind
   * @generated
   */
  public java.lang.String getForm_verified_sign_ind()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("form_verified_sign_ind")));
  }
  /**
   * setForm_verified_sign_ind
   * @generated
   */
  public void setForm_verified_sign_ind(java.lang.String newValue) {
    __setCache("form_verified_sign_ind", newValue);
  }
  /**
   * getConsent_pull_datetime
   * @generated
   */
  public java.sql.Timestamp getConsent_pull_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("consent_pull_datetime")));
  }
  /**
   * setConsent_pull_datetime
   * @generated
   */
  public void setConsent_pull_datetime(java.sql.Timestamp newValue) {
    __setCache("consent_pull_datetime", newValue);
  }
  /**
   * getConsent_pull_staff_id
   * @generated
   */
  public java.lang.String getConsent_pull_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consent_pull_staff_id")));
  }
  /**
   * setConsent_pull_staff_id
   * @generated
   */
  public void setConsent_pull_staff_id(java.lang.String newValue) {
    __setCache("consent_pull_staff_id", newValue);
  }
  /**
   * getConsent_datetime
   * @generated
   */
  public java.sql.Timestamp getConsent_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("consent_datetime")));
  }
  /**
   * setConsent_datetime
   * @generated
   */
  public void setConsent_datetime(java.sql.Timestamp newValue) {
    __setCache("consent_datetime", newValue);
  }
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
   * getConsent_pull_comment
   * @generated
   */
  public java.lang.String getConsent_pull_comment()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consent_pull_comment")));
  }
  /**
   * setConsent_pull_comment
   * @generated
   */
  public void setConsent_pull_comment(java.lang.String newValue) {
    __setCache("consent_pull_comment", newValue);
  }
  /**
   * getBest_diagnosis_other
   * @generated
   */
  public java.lang.String getBest_diagnosis_other()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("best_diagnosis_other")));
  }
  /**
   * getForm_verified_by_staff_id
   * @generated
   */
  public java.lang.String getForm_verified_by_staff_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("form_verified_by_staff_id")));
  }
  /**
   * setForm_verified_by_staff_id
   * @generated
   */
  public void setForm_verified_by_staff_id(java.lang.String newValue) {
    __setCache("form_verified_by_staff_id", newValue);
  }
  /**
   * getDisease_concept_id_other
   * @generated
   */
  public java.lang.String getDisease_concept_id_other()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("disease_concept_id_other")));
  }
  /**
   * setDisease_concept_id_other
   * @generated
   */
  public void setDisease_concept_id_other(java.lang.String newValue) {
    __setCache("disease_concept_id_other", newValue);
  }
  /**
   * getDisease_concept_id
   * @generated
   */
  public java.lang.String getDisease_concept_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("disease_concept_id")));
  }
  /**
   * setDisease_concept_id
   * @generated
   */
  public void setDisease_concept_id(java.lang.String newValue) {
    __setCache("disease_concept_id", newValue);
  }
  /**
   * getBlood_sample_yn
   * @generated
   */
  public java.lang.String getBlood_sample_yn()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("blood_sample_yn")));
  }
  /**
   * setBlood_sample_yn
   * @generated
   */
  public void setBlood_sample_yn(java.lang.String newValue) {
    __setCache("blood_sample_yn", newValue);
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
   * getLinked
   * @generated
   */
  public java.lang.String getLinked()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("linked")));
  }
  /**
   * setLinked
   * @generated
   */
  public void setLinked(java.lang.String newValue) {
    __setCache("linked", newValue);
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
   * getForm_verified_datetime
   * @generated
   */
  public java.sql.Timestamp getForm_verified_datetime()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("form_verified_datetime")));
  }
  /**
   * setForm_verified_datetime
   * @generated
   */
  public void setForm_verified_datetime(java.sql.Timestamp newValue) {
    __setCache("form_verified_datetime", newValue);
  }
  /**
   * getConsent_version_id
   * @generated
   */
  public java.math.BigDecimal getConsent_version_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("consent_version_id")));
  }
  /**
   * setConsent_version_id
   * @generated
   */
  public void setConsent_version_id(java.math.BigDecimal newValue) {
    __setCache("consent_version_id", newValue);
  }
  /**
   * getForm_signed_ind
   * @generated
   */
  public java.lang.String getForm_signed_ind()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("form_signed_ind")));
  }
  /**
   * setForm_signed_ind
   * @generated
   */
  public void setForm_signed_ind(java.lang.String newValue) {
    __setCache("form_signed_ind", newValue);
  }
  /**
   * getPolicy_id
   * @generated
   */
  public java.math.BigDecimal getPolicy_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("policy_id")));
  }
  /**
   * setPolicy_id
   * @generated
   */
  public void setPolicy_id(java.math.BigDecimal newValue) {
    __setCache("policy_id", newValue);
  }
  /**
   * getAdditional_needle_stick_yn
   * @generated
   */
  public java.lang.String getAdditional_needle_stick_yn()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("additional_needle_stick_yn")));
  }
  /**
   * setAdditional_needle_stick_yn
   * @generated
   */
  public void setAdditional_needle_stick_yn(java.lang.String newValue) {
    __setCache("additional_needle_stick_yn", newValue);
  }
  /**
   * setInit_consent_id
   * @generated
   */
  public void setInit_consent_id(java.lang.String newValue) {
    this.init_consent_id = (newValue);
  }
  /**
   * setInit_policy_id
   * @generated
   */
  public void setInit_policy_id(java.math.BigDecimal newValue) {
    this.init_policy_id = (newValue);
  }
  /**
   * setInit_ardais_id
   * @generated
   */
  public void setInit_ardais_id(java.lang.String newValue) {
    this.init_ardais_id = (newValue);
  }
  /**
   * ConsentAccessBean
   * @generated
   */
  public ConsentAccessBean() {
    super();
  }
  /**
   * ConsentAccessBean
   * @generated
   */
  public ConsentAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "com/ardais/bigr/iltds/beans/Consent";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.iltds.beans.ConsentHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.iltds.beans.ConsentHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.iltds.beans.ConsentHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.iltds.beans.Consent ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.iltds.beans.Consent) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.iltds.beans.Consent.class);

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
      init_consent_id,
      init_policy_id,
      init_ardais_id,
      init_imported_yn,
      init_ardais_acct_key,
      init_case_registration_form);
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
      com.ardais.bigr.iltds.beans.ConsentKey pKey = (com.ardais.bigr.iltds.beans.ConsentKey) this
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
   * ConsentAccessBean
   * @generated
   */
  public ConsentAccessBean(com.ardais.bigr.iltds.beans.ConsentKey primaryKey)
    throws javax.naming.NamingException, javax.ejb.FinderException, javax.ejb.CreateException,
    java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
  /**
   * findConsentByGeolocation
   * @generated
   */
  public java.util.Enumeration findConsentByGeolocation(
    com.ardais.bigr.iltds.beans.GeolocationKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.ConsentHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findConsentByGeolocation(inKey);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findByConsentID
   * @generated
   */
  public java.util.Enumeration findByConsentID(java.lang.String consentID)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.ConsentHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findByConsentID(consentID);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * findConsentByArdaisID
   * @generated
   */
  public java.util.Enumeration findConsentByArdaisID(java.lang.String consentID)
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    java.rmi.RemoteException {
    com.ardais.bigr.iltds.beans.ConsentHome localHome = ejbHome();
    java.util.Enumeration ejbs = localHome.findConsentByArdaisID(consentID);
    return (java.util.Enumeration) createAccessBeans(ejbs);
  }
  /**
   * addAsmform
   * @generated
   */
  public void addAsmform(com.ardais.bigr.iltds.beans.Asmform anAsmform)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().addAsmform(anAsmform);
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
  /**
   * getAsmform
   * @generated
   */
  public java.util.Enumeration getAsmform()
    throws javax.naming.NamingException,
    javax.ejb.FinderException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    return ejbRef().getAsmform();
  }
  /**
   * setGeolocation
   * @generated
   */
  public void setGeolocation(com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().setGeolocation(aGeolocation);
  }
  /**
   * secondaryRemoveAsmform
   * @generated
   */
  public void secondaryRemoveAsmform(com.ardais.bigr.iltds.beans.Asmform anAsmform)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryRemoveAsmform(anAsmform);
  }
  /**
   * secondaryAddAsmform
   * @generated
   */
  public void secondaryAddAsmform(com.ardais.bigr.iltds.beans.Asmform anAsmform)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondaryAddAsmform(anAsmform);
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
   * privateSetGeolocationKey
   * @generated
   */
  public void privateSetGeolocationKey(com.ardais.bigr.iltds.beans.GeolocationKey inKey)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().privateSetGeolocationKey(inKey);
  }
  /**
   * secondarySetGeolocation
   * @generated
   */
  public void secondarySetGeolocation(com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
    throws javax.naming.NamingException,
    javax.ejb.CreateException,
    java.rmi.RemoteException {
    instantiateEJB();
    ejbRef().secondarySetGeolocation(aGeolocation);
  }
  /**
   * getImported_yn
   * @generated
   */
  public java.lang.String getImported_yn()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("imported_yn")));
  }
  /**
   * setImported_yn
   * @generated
   */
  public void setImported_yn(java.lang.String newValue) {
    __setCache("imported_yn", newValue);
  }
  /**
   * getArdais_acct_key
   * @generated
   */
  public java.lang.String getArdais_acct_key()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("ardais_acct_key")));
  }
  /**
   * setArdais_acct_key
   * @generated
   */
  public void setArdais_acct_key(java.lang.String newValue) {
    __setCache("ardais_acct_key", newValue);
  }
  /**
   * getCustomer_id
   * @generated
   */
  public java.lang.String getCustomer_id()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("customer_id")));
  }
  /**
   * setCustomer_id
   * @generated
   */
  public void setCustomer_id(java.lang.String newValue) {
    __setCache("customer_id", newValue);
  }
  /**
   * setInit_imported_yn
   * @generated
   */
  public void setInit_imported_yn(java.lang.String newValue) {
    this.init_imported_yn = (newValue);
  }
  /**
   * setInit_ardais_acct_key
   * @generated
   */
  public void setInit_ardais_acct_key(java.lang.String newValue) {
    this.init_ardais_acct_key = (newValue);
  }
  /**
   * getPsa
   * @generated
   */
  public java.math.BigDecimal getPsa()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.math.BigDecimal) __getCache("psa")));
  }
  /**
   * setPsa
   * @generated
   */
  public void setPsa(java.math.BigDecimal newValue) {
    __setCache("psa", newValue);
  }
  /**
   * getClinical_finding_notes
   * @generated
   */
  public java.lang.String getClinical_finding_notes()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("clinical_finding_notes")));
  }
  /**
   * setClinical_finding_notes
   * @generated
   */
  public void setClinical_finding_notes(java.lang.String newValue) {
    __setCache("clinical_finding_notes", newValue);
  }
  /**
   * getDre_cid
   * @generated
   */
  public java.lang.String getDre_cid()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("dre_cid")));
  }
  /**
   * setDre_cid
   * @generated
   */
  public void setDre_cid(java.lang.String newValue) {
    __setCache("dre_cid", newValue);
  }
  /**
   * getCase_registration_form
   * @generated
   */
  public java.lang.String getCase_registration_form()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("case_registration_form")));
  }
  /**
   * setCase_registration_form
   * @generated
   */
  public void setCase_registration_form(java.lang.String newValue) {
    __setCache("case_registration_form", newValue);
  }
  /**
   * setInit_case_registration_form
   * @generated
   */
  public void setInit_case_registration_form(java.lang.String newValue) {
    this.init_case_registration_form = (newValue);
  }
}
