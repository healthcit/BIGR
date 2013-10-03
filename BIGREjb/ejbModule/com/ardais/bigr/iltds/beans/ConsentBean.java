package com.ardais.bigr.iltds.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.EntityBean;

import com.ardais.bigr.api.ApiFunctions;
/**
 * This is an Entity Bean class with CMP fields
 */
public class ConsentBean implements EntityBean {
  public static final String DEFAULT_consent_id = null;
  public static final String DEFAULT_ardais_id = null;
  public static final java.lang.String DEFAULT_geolocation_location_address_id = null;
  public static final String DEFAULT_ardais_staff_id = null;
  public static final String DEFAULT_disease_concept_id = null;
  public static final String DEFAULT_linked = null;
  public static final String DEFAULT_form_entry_staff_id = null;
  public static final java.sql.Timestamp DEFAULT_form_entry_datetime = null;
  public static final String DEFAULT_form_signed_ind = null;
  public static final String DEFAULT_consent_release_staff_id = null;
  public static final java.sql.Timestamp DEFAULT_consent_release_datetime = null;
  public static final String DEFAULT_consent_pull_staff_id = null;
  public static final String DEFAULT_consent_pull_request_by = null;
  public static final java.sql.Timestamp DEFAULT_consent_pull_datetime = null;
  public static final String DEFAULT_consent_pull_reason_cd = null;
  public static final String DEFAULT_consent_pull_comment = null;
  public static final String DEFAULT_form_verified_by_staff_id = null;
  public static final java.sql.Timestamp DEFAULT_form_verified_datetime = null;
  public static final String DEFAULT_form_verified_sign_ind = null;
  public static final String DEFAULT_bankable_ind = null;
  public static final String DEFAULT_consent_version_num = null;
  public static final java.sql.Timestamp DEFAULT_consent_datetime = null;
  public static final String DEFAULT_disease_concept_id_other = null;
  public static final String DEFAULT_donor_ardaisId = null;
  public static final java.math.BigDecimal DEFAULT_policy_id = null;
  public static final java.math.BigDecimal DEFAULT_consent_version_id = null;
  public static final String DEFAULT_comments = null;
  public static final String DEFAULT_blood_sample_yn = null;
  public static final String DEFAULT_additional_needle_stick_yn = null;
  public static final String DEFAULT_future_contact_yn = null;
  public static final String DEFAULT_imported_yn = null;
  public static final String DEFAULT_customer_id = null;
  public static final String DEFAULT_ardais_acct_key = null;
  public static final java.math.BigDecimal DEFAULT_psa = null;
  public static final String DEFAULT_dre_cid = null;
  public static final String DEFAULT_clinical_finding_notes = null;
  public static final String DEFAULT_case_registration_form = null;

  public String consent_id;
  public java.lang.String ardais_id;
  public java.lang.String geolocation_location_address_id;
  public String ardais_staff_id;
  public String disease_concept_id;
  public String linked;
  public String form_entry_staff_id;
  public java.sql.Timestamp form_entry_datetime;
  public String form_signed_ind;
  public String consent_release_staff_id;
  public java.sql.Timestamp consent_release_datetime;
  public String consent_pull_staff_id;
  public String consent_pull_request_by;
  public java.sql.Timestamp consent_pull_datetime;
  public String consent_pull_reason_cd;
  public String consent_pull_comment;
  public String form_verified_by_staff_id;
  public java.sql.Timestamp form_verified_datetime;
  public String form_verified_sign_ind;
  public String bankable_ind;
  public String consent_version_num;
  public java.sql.Timestamp consent_datetime;
  public String disease_concept_id_other;
  public java.math.BigDecimal policy_id;
  public java.math.BigDecimal consent_version_id;
  public java.lang.String comments;
  public java.lang.String blood_sample_yn;
  public java.lang.String additional_needle_stick_yn;
  public java.lang.String future_contact_yn;
  public java.lang.String imported_yn;
  public java.lang.String customer_id;
  public java.lang.String ardais_acct_key;

  //fields that are read-only for this bean (computed fields in the database)
  private boolean jdbcFields = false;
  private String best_diagnosis_cui;
  private String best_diagnosis_other;

  private javax.ejb.EntityContext entityContext = null;
  final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink ardaisstaffLink = null;
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink asmformLink;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink geolocationLink = null;

  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.ConsentBean.class);

  private javax.ejb.EntityContext myEntityCtx;

  /**
   * Implementation field for persistent attribute: psa
   */
  public java.math.BigDecimal psa;
  /**
   * Implementation field for persistent attribute: dre_cid
   */
  public java.lang.String dre_cid;
  /**
   * Implementation field for persistent attribute: clinical_finding_notes
   */
  public java.lang.String clinical_finding_notes;
  /**
   * Implementation field for persistent attribute: case_registration_form
   */
  public java.lang.String case_registration_form;
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getGeolocationLink());
    links.add(getAsmformLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    geolocationLink = null;
    asmformLink = null;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _removeLinks() throws java.rmi.RemoteException, javax.ejb.RemoveException {
    java.util.List links = _getLinks();
    for (int i = 0; i < links.size(); i++) {
      try {
        ((com.ibm.ivj.ejb.associations.interfaces.Link) links.get(i)).remove();
      }
      catch (javax.ejb.FinderException e) {
      } //Consume Finder error since I am going away
    }
  }
  /**
   * ejbActivate
   */
  public void ejbActivate() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.iltds.beans.ConsentKey ejbCreate(
    String consent_id,
    java.math.BigDecimal policy_id,
    String ardais_id,
    String imported_yn,
    String ardais_acct_key,
    String case_registration_form)
    throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.consent_id = consent_id;
    this.policy_id = policy_id;
    this.ardais_id = ardais_id;
    this.imported_yn = imported_yn;
    this.ardais_acct_key = ardais_acct_key;
    this.case_registration_form = case_registration_form;
    return null;
  }
  /**
   * ejbLoad
   */
  public void ejbLoad() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbPassivate
   */
  public void ejbPassivate() throws java.rmi.RemoteException {
  }
  /**
   * ejbPostCreate
   */
  public void ejbPostCreate(String consent_id, java.math.BigDecimal policy_id, String ardais_id,
  String imported_yn,
  String ardais_acct_key,
  String case_registration_form)
    throws javax.ejb.CreateException {
  }
  /**
   * ejbRemove
   */
  public void ejbRemove() throws javax.ejb.RemoveException, java.rmi.RemoteException {
    try {
      _removeLinks();
    }
    catch (java.rmi.RemoteException e) {
      throw new javax.ejb.RemoveException(e.getMessage());
    }
  }
  /**
   * ejbStore
   */
  public void ejbStore() throws java.rmi.RemoteException {
  }
  /**
   * Getter method for ardais_staff_id
   */
  public java.lang.String getArdais_staff_id() {
    return ardais_staff_id;
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_ARDAIS_STAFF3 Ardaisstaff.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.ArdaisstaffKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.ArdaisstaffKey getArdaisstaffKey() {
    com.ardais.bigr.iltds.beans.ArdaisstaffKey temp = null;
    temp = new com.ardais.bigr.iltds.beans.ArdaisstaffKey();
    boolean ardaisstaff_NULLTEST = true;
    ardaisstaff_NULLTEST &= (ardais_staff_id == null);
    temp.ardais_staff_id = ardais_staff_id;
    if (ardaisstaff_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the relationship role named asmform.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public java.util.Enumeration getAsmform()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getAsmformLink().enumerationValue();
  }
  /**
   * This method was generated for supporting the relationship role named asmform.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getAsmformLink() {
    if (asmformLink == null)
      asmformLink = new ConsentToAsmformLink(this);
    return asmformLink;
  }
  /**
   * Getter method for bankable_ind
   * @return java.lang.String
   */
  public java.lang.String getBankable_ind() {
    return bankable_ind;
  }
  /**
   * Getter method for consent_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getConsent_datetime() {
    return consent_datetime;
  }
  /**
   * Getter method for consent_pull_comment
   * @return java.lang.String
   */
  public java.lang.String getConsent_pull_comment() {
    return consent_pull_comment;
  }
  /**
   * Getter method for consent_pull_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getConsent_pull_datetime() {
    return consent_pull_datetime;
  }
  /**
   * Getter method for consent_pull_reason_cd
   * @return java.lang.String
   */
  public java.lang.String getConsent_pull_reason_cd() {
    return consent_pull_reason_cd;
  }
  /**
   * Getter method for consent_pull_request_by
   * @return java.lang.String
   */
  public java.lang.String getConsent_pull_request_by() {
    return consent_pull_request_by;
  }
  /**
   * Getter method for consent_pull_staff_id
   * @return java.lang.String
   */
  public java.lang.String getConsent_pull_staff_id() {
    return consent_pull_staff_id;
  }
  /**
   * Getter method for consent_release_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getConsent_release_datetime() {
    return consent_release_datetime;
  }
  /**
   * Getter method for consent_release_staff_id
   * @return java.lang.String
   */
  public java.lang.String getConsent_release_staff_id() {
    return consent_release_staff_id;
  }
  /**
   * Getter method for consent_version_num
   * @return java.lang.String
   */
  public java.lang.String getConsent_version_num() {
    return consent_version_num;
  }
  /**
   * Getter method for disease_concept_id
   * @return java.lang.String
   */
  public java.lang.String getDisease_concept_id() {
    return disease_concept_id;
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/16/2001 1:09:01 PM)
   * @return java.lang.String
   */
  public java.lang.String getDisease_concept_id_other() {
    return disease_concept_id_other;
  }
  /**
   * getEntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return myEntityCtx;
  }
  /**
   * Getter method for form_entry_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getForm_entry_datetime() {
    return form_entry_datetime;
  }
  /**
   * Getter method for form_entry_staff_id
   * @return java.lang.String
   */
  public java.lang.String getForm_entry_staff_id() {
    return form_entry_staff_id;
  }
  /**
   * Getter method for form_signed_ind
   * @return java.lang.String
   */
  public java.lang.String getForm_signed_ind() {
    return form_signed_ind;
  }
  /**
   * Getter method for form_verified_by_staff_id
   * @return java.lang.String
   */
  public java.lang.String getForm_verified_by_staff_id() {
    return form_verified_by_staff_id;
  }
  /**
   * Getter method for form_verified_datetime
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getForm_verified_datetime() {
    return form_verified_datetime;
  }
  /**
   * Getter method for form_verified_sign_ind
   * @return java.lang.String
   */
  public java.lang.String getForm_verified_sign_ind() {
    return form_verified_sign_ind;
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.Geolocation
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.Geolocation getGeolocation()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.iltds.beans.Geolocation) this.getGeolocationLink().value();
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.GeolocationKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public com.ardais.bigr.iltds.beans.GeolocationKey getGeolocationKey() {
    com.ardais.bigr.iltds.beans.GeolocationKey temp = null;
    temp = new com.ardais.bigr.iltds.beans.GeolocationKey();
    boolean geolocation_NULLTEST = true;
    geolocation_NULLTEST &= (geolocation_location_address_id == null);
    temp.location_address_id = geolocation_location_address_id;
    if (geolocation_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ibm.ivj.ejb.associations.interfaces.SingleLink
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getGeolocationLink() {
    if (geolocationLink == null)
      geolocationLink = new ConsentToGeolocationLink(this);
    return geolocationLink;
  }
  /**
   * Getter method for linked
   * @return java.lang.String
   */
  public java.lang.String getLinked() {
    return linked;
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_ARDAIS_STAFF3 Ardaisstaff.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param inKey com.ardais.bigr.iltds.beans.ArdaisstaffKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void privateSetArdaisstaffKey(com.ardais.bigr.iltds.beans.ArdaisstaffKey inKey) {
    boolean ardaisstaff_NULLTEST = (inKey == null);
    if (ardaisstaff_NULLTEST)
      ardais_staff_id = null;
    else
      ardais_staff_id = inKey.ardais_staff_id;
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param inKey com.ardais.bigr.iltds.beans.GeolocationKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void privateSetGeolocationKey(com.ardais.bigr.iltds.beans.GeolocationKey inKey) {
    boolean geolocation_NULLTEST = (inKey == null);
    if (geolocation_NULLTEST)
      geolocation_location_address_id = null;
    else
      geolocation_location_address_id = inKey.location_address_id;
  }
  /**
   * This method was generated for supporting the relationship role named asmform.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryAddAsmform(com.ardais.bigr.iltds.beans.Asmform anAsmform)
    throws java.rmi.RemoteException {
    this.getAsmformLink().secondaryAddElement(anAsmform);
  }
  /**
   * This method was generated for supporting the relationship role named asmform.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryRemoveAsmform(com.ardais.bigr.iltds.beans.Asmform anAsmform)
    throws java.rmi.RemoteException {
    this.getAsmformLink().secondaryRemoveElement(anAsmform);
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aGeolocation com.ardais.bigr.iltds.beans.Geolocation
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void secondarySetGeolocation(com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
    throws java.rmi.RemoteException {
    this.getGeolocationLink().secondarySet(aGeolocation);
  }
  /**
   * Setter method for ardais_staff_id
   */
  public void setArdais_staff_id(java.lang.String newValue) {
    this.ardais_staff_id = newValue;
  }
  /**
   * Setter method for bankable_ind
   * @param newValue java.lang.String
   */
  public void setBankable_ind(java.lang.String newValue) {
    this.bankable_ind = newValue;
  }
  /**
   * Setter method for consent_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setConsent_datetime(java.sql.Timestamp newValue) {
    this.consent_datetime = newValue;
  }
  /**
   * Setter method for consent_pull_comment
   * @param newValue java.lang.String
   */
  public void setConsent_pull_comment(java.lang.String newValue) {
    this.consent_pull_comment = newValue;
  }
  /**
   * Setter method for consent_pull_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setConsent_pull_datetime(java.sql.Timestamp newValue) {
    this.consent_pull_datetime = newValue;
  }
  /**
   * Setter method for consent_pull_reason_cd
   * @param newValue java.lang.String
   */
  public void setConsent_pull_reason_cd(java.lang.String newValue) {
    this.consent_pull_reason_cd = newValue;
  }
  /**
   * Setter method for consent_pull_request_by
   * @param newValue java.lang.String
   */
  public void setConsent_pull_request_by(java.lang.String newValue) {
    this.consent_pull_request_by = newValue;
  }
  /**
   * Setter method for consent_pull_staff_id
   * @param newValue java.lang.String
   */
  public void setConsent_pull_staff_id(java.lang.String newValue) {
    this.consent_pull_staff_id = newValue;
  }
  /**
   * Setter method for consent_release_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setConsent_release_datetime(java.sql.Timestamp newValue) {
    this.consent_release_datetime = newValue;
  }
  /**
   * Setter method for consent_release_staff_id
   * @param newValue java.lang.String
   */
  public void setConsent_release_staff_id(java.lang.String newValue) {
    this.consent_release_staff_id = newValue;
  }
  /**
   * Setter method for consent_version_num
   * @param newValue java.lang.String
   */
  public void setConsent_version_num(java.lang.String newValue) {
    this.consent_version_num = newValue;
  }
  /**
   * Setter method for disease_concept_id
   * @param newValue java.lang.String
   */
  public void setDisease_concept_id(java.lang.String newValue) {
    this.disease_concept_id = newValue;
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/16/2001 1:09:01 PM)
   * @param newDisease_concept_id_other java.lang.String
   */
  public void setDisease_concept_id_other(java.lang.String newDisease_concept_id_other) {
    disease_concept_id_other = newDisease_concept_id_other;
  }
  /**
   * setEntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    myEntityCtx = ctx;
  }
  /**
   * Setter method for form_entry_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setForm_entry_datetime(java.sql.Timestamp newValue) {
    this.form_entry_datetime = newValue;
  }
  /**
   * Setter method for form_entry_staff_id
   * @param newValue java.lang.String
   */
  public void setForm_entry_staff_id(java.lang.String newValue) {
    this.form_entry_staff_id = newValue;
  }
  /**
   * Setter method for form_signed_ind
   * @param newValue java.lang.String
   */
  public void setForm_signed_ind(java.lang.String newValue) {
    this.form_signed_ind = newValue;
  }
  /**
   * Setter method for form_verified_by_staff_id
   * @param newValue java.lang.String
   */
  public void setForm_verified_by_staff_id(java.lang.String newValue) {
    this.form_verified_by_staff_id = newValue;
  }
  /**
   * Setter method for form_verified_datetime
   * @param newValue java.sql.Timestamp
   */
  public void setForm_verified_datetime(java.sql.Timestamp newValue) {
    this.form_verified_datetime = newValue;
  }
  /**
   * Setter method for form_verified_sign_ind
   * @param newValue java.lang.String
   */
  public void setForm_verified_sign_ind(java.lang.String newValue) {
    this.form_verified_sign_ind = newValue;
  }
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aGeolocation com.ardais.bigr.iltds.beans.Geolocation
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  public void setGeolocation(com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
    throws java.rmi.RemoteException {
    this.getGeolocationLink().set(aGeolocation);
  }
  /**
   * Setter method for linked
   * @param newValue java.lang.String
   */
  public void setLinked(java.lang.String newValue) {
    this.linked = newValue;
  }
  /**
   * unsetEntityContext
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    myEntityCtx = null;
  }
  /**
   * This method was generated for supporting the relationship role named asmform.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void addAsmform(com.ardais.bigr.iltds.beans.Asmform anAsmform)
    throws java.rmi.RemoteException {
    this.getAsmformLink().addElement(anAsmform);
  }
  /**
   * Get accessor for persistent attribute: ardais_id
   */
  public java.lang.String getArdais_id() {
    return ardais_id;
  }
  /**
   * Set accessor for persistent attribute: ardais_id
   */
  public void setArdais_id(java.lang.String newArdais_id) {
    ardais_id = newArdais_id;
  }
  /**
   * Get accessor for persistent attribute: policy_id
   */
  public java.math.BigDecimal getPolicy_id() {
    return policy_id;
  }
  /**
   * Set accessor for persistent attribute: policy_id
   */
  public void setPolicy_id(java.math.BigDecimal newPolicy_id) {
    policy_id = newPolicy_id;
  }
  /**
   * Get accessor for persistent attribute: consent_version_id
   */
  public java.math.BigDecimal getConsent_version_id() {
    return consent_version_id;
  }
  /**
   * Set accessor for persistent attribute: consent_version_id
   */
  public void setConsent_version_id(java.math.BigDecimal newConsent_version_id) {
    consent_version_id = newConsent_version_id;
  }
  /**
   * Get accessor for persistent attribute: comments
   */
  public java.lang.String getComments() {
    return comments;
  }
  /**
   * Set accessor for persistent attribute: comments
   */
  public void setComments(java.lang.String newComments) {
    comments = newComments;
  }
  /**
   * Get accessor for persistent attribute: blood_sample_yn
   */
  public java.lang.String getBlood_sample_yn() {
    return blood_sample_yn;
  }
  /**
   * Set accessor for persistent attribute: blood_sample_yn
   */
  public void setBlood_sample_yn(java.lang.String newBlood_sample_yn) {
    blood_sample_yn = newBlood_sample_yn;
  }
  /**
   * Get accessor for persistent attribute: additional_needle_stick_yn
   */
  public java.lang.String getAdditional_needle_stick_yn() {
    return additional_needle_stick_yn;
  }
  /**
   * Set accessor for persistent attribute: additional_needle_stick_yn
   */
  public void setAdditional_needle_stick_yn(java.lang.String newAdditional_needle_stick_yn) {
    additional_needle_stick_yn = newAdditional_needle_stick_yn;
  }
  /**
   * Get accessor for persistent attribute: future_contact_yn
   */
  public java.lang.String getFuture_contact_yn() {
    return future_contact_yn;
  }
  /**
   * Set accessor for persistent attribute: future_contact_yn
   */
  public void setFuture_contact_yn(java.lang.String newFuture_contact_yn) {
    future_contact_yn = newFuture_contact_yn;
  }
  /**
   * Getter method for best_diagnosis_cui
   */
  public java.lang.String getBest_diagnosis_cui() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return best_diagnosis_cui;
  }
  
  /**
   * Getter method for best_diagnosis_other
   */
  public java.lang.String getBest_diagnosis_other() {
    if (!jdbcFields) {
      populateJDBCFields();
    }
    return best_diagnosis_other;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/14/2002 11:52:06 AM)
   */
  public void populateJDBCFields() {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = com.ardais.bigr.api.ApiFunctions.getDbConnection();
      String query =
        "SELECT BEST_DIAGNOSIS_CUI, BEST_DIAGNOSIS_OTHER FROM ILTDS_INFORMED_CONSENT WHERE CONSENT_ID = ?";

      ps = con.prepareStatement(query);
      ps.setString(1, this.consent_id);

      rs = ps.executeQuery();
      rs.next();

      this.best_diagnosis_cui = rs.getString(1);
      this.best_diagnosis_other = rs.getString(2);

      jdbcFields = true;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con,ps,rs);
    }

  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("future_contact_yn", getFuture_contact_yn());
    h.put("consent_release_datetime", getConsent_release_datetime());
    h.put("consent_release_staff_id", getConsent_release_staff_id());
    h.put("consent_pull_request_by", getConsent_pull_request_by());
    h.put("best_diagnosis_cui", getBest_diagnosis_cui());
    h.put("form_entry_staff_id", getForm_entry_staff_id());
    h.put("form_entry_datetime", getForm_entry_datetime());
    h.put("bankable_ind", getBankable_ind());
    h.put("comments", getComments());
    h.put("consent_pull_reason_cd", getConsent_pull_reason_cd());
    h.put("geolocationKey", getGeolocationKey());
    h.put("consent_version_num", getConsent_version_num());
    h.put("form_verified_sign_ind", getForm_verified_sign_ind());
    h.put("consent_pull_datetime", getConsent_pull_datetime());
    h.put("consent_pull_staff_id", getConsent_pull_staff_id());
    h.put("consent_datetime", getConsent_datetime());
    h.put("ardais_id", getArdais_id());
    h.put("consent_pull_comment", getConsent_pull_comment());
    h.put("best_diagnosis_other", getBest_diagnosis_other());
    h.put("form_verified_by_staff_id", getForm_verified_by_staff_id());
    h.put("disease_concept_id_other", getDisease_concept_id_other());
    h.put("disease_concept_id", getDisease_concept_id());
    h.put("blood_sample_yn", getBlood_sample_yn());
    h.put("ardaisstaffKey", getArdaisstaffKey());
    h.put("linked", getLinked());
    h.put("ardais_staff_id", getArdais_staff_id());
    h.put("form_verified_datetime", getForm_verified_datetime());
    h.put("consent_version_id", getConsent_version_id());
    h.put("form_signed_ind", getForm_signed_ind());
    h.put("policy_id", getPolicy_id());
    h.put("additional_needle_stick_yn", getAdditional_needle_stick_yn());
    h.put("imported_yn", getImported_yn());
    h.put("ardais_acct_key", getArdais_acct_key());
    h.put("customer_id", getCustomer_id());
    h.put("psa", getPsa());
    h.put("clinical_finding_notes", getClinical_finding_notes());
    h.put("dre_cid", getDre_cid());
    h.put("case_registration_form", getCase_registration_form());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localFuture_contact_yn = (java.lang.String) h.get("future_contact_yn");
    java.sql.Timestamp localConsent_release_datetime = (java.sql.Timestamp) h
      .get("consent_release_datetime");
    java.lang.String localConsent_release_staff_id = (java.lang.String) h
      .get("consent_release_staff_id");
    java.lang.String localConsent_pull_request_by = (java.lang.String) h
      .get("consent_pull_request_by");
    java.lang.String localForm_entry_staff_id = (java.lang.String) h.get("form_entry_staff_id");
    java.sql.Timestamp localForm_entry_datetime = (java.sql.Timestamp) h.get("form_entry_datetime");
    java.lang.String localBankable_ind = (java.lang.String) h.get("bankable_ind");
    java.lang.String localComments = (java.lang.String) h.get("comments");
    java.lang.String localConsent_pull_reason_cd = (java.lang.String) h
      .get("consent_pull_reason_cd");
    java.lang.String localConsent_version_num = (java.lang.String) h.get("consent_version_num");
    java.lang.String localForm_verified_sign_ind = (java.lang.String) h
      .get("form_verified_sign_ind");
    java.sql.Timestamp localConsent_pull_datetime = (java.sql.Timestamp) h
      .get("consent_pull_datetime");
    java.lang.String localConsent_pull_staff_id = (java.lang.String) h.get("consent_pull_staff_id");
    java.sql.Timestamp localConsent_datetime = (java.sql.Timestamp) h.get("consent_datetime");
    java.lang.String localArdais_id = (java.lang.String) h.get("ardais_id");
    java.lang.String localConsent_pull_comment = (java.lang.String) h.get("consent_pull_comment");
    java.lang.String localForm_verified_by_staff_id = (java.lang.String) h
      .get("form_verified_by_staff_id");
    java.lang.String localDisease_concept_id_other = (java.lang.String) h
      .get("disease_concept_id_other");
    java.lang.String localDisease_concept_id = (java.lang.String) h.get("disease_concept_id");
    java.lang.String localBlood_sample_yn = (java.lang.String) h.get("blood_sample_yn");
    java.lang.String localLinked = (java.lang.String) h.get("linked");
    java.lang.String localArdais_staff_id = (java.lang.String) h.get("ardais_staff_id");
    java.sql.Timestamp localForm_verified_datetime = (java.sql.Timestamp) h
      .get("form_verified_datetime");
    java.math.BigDecimal localConsent_version_id = (java.math.BigDecimal) h
      .get("consent_version_id");
    java.lang.String localForm_signed_ind = (java.lang.String) h.get("form_signed_ind");
    java.math.BigDecimal localPolicy_id = (java.math.BigDecimal) h.get("policy_id");
    java.lang.String localAdditional_needle_stick_yn = (java.lang.String) h
      .get("additional_needle_stick_yn");
    java.lang.String localImported_yn = (java.lang.String) h.get("imported_yn");
    java.lang.String localArdais_acct_key = (java.lang.String) h.get("ardais_acct_key");
    java.lang.String localCustomer_id = (java.lang.String) h.get("customer_id");
    java.math.BigDecimal localPsa = (java.math.BigDecimal) h.get("psa");
    java.lang.String localClinical_finding_notes = (java.lang.String) h
      .get("clinical_finding_notes");
    java.lang.String localDre_cid = (java.lang.String) h.get("dre_cid");
    java.lang.String localCase_registration_form = (java.lang.String) h
      .get("case_registration_form");

    if (h.containsKey("future_contact_yn"))
      setFuture_contact_yn((localFuture_contact_yn));
    if (h.containsKey("consent_release_datetime"))
      setConsent_release_datetime((localConsent_release_datetime));
    if (h.containsKey("consent_release_staff_id"))
      setConsent_release_staff_id((localConsent_release_staff_id));
    if (h.containsKey("consent_pull_request_by"))
      setConsent_pull_request_by((localConsent_pull_request_by));
    if (h.containsKey("form_entry_staff_id"))
      setForm_entry_staff_id((localForm_entry_staff_id));
    if (h.containsKey("form_entry_datetime"))
      setForm_entry_datetime((localForm_entry_datetime));
    if (h.containsKey("bankable_ind"))
      setBankable_ind((localBankable_ind));
    if (h.containsKey("comments"))
      setComments((localComments));
    if (h.containsKey("consent_pull_reason_cd"))
      setConsent_pull_reason_cd((localConsent_pull_reason_cd));
    if (h.containsKey("consent_version_num"))
      setConsent_version_num((localConsent_version_num));
    if (h.containsKey("form_verified_sign_ind"))
      setForm_verified_sign_ind((localForm_verified_sign_ind));
    if (h.containsKey("consent_pull_datetime"))
      setConsent_pull_datetime((localConsent_pull_datetime));
    if (h.containsKey("consent_pull_staff_id"))
      setConsent_pull_staff_id((localConsent_pull_staff_id));
    if (h.containsKey("consent_datetime"))
      setConsent_datetime((localConsent_datetime));
    if (h.containsKey("ardais_id"))
      setArdais_id((localArdais_id));
    if (h.containsKey("consent_pull_comment"))
      setConsent_pull_comment((localConsent_pull_comment));
    if (h.containsKey("form_verified_by_staff_id"))
      setForm_verified_by_staff_id((localForm_verified_by_staff_id));
    if (h.containsKey("disease_concept_id_other"))
      setDisease_concept_id_other((localDisease_concept_id_other));
    if (h.containsKey("disease_concept_id"))
      setDisease_concept_id((localDisease_concept_id));
    if (h.containsKey("blood_sample_yn"))
      setBlood_sample_yn((localBlood_sample_yn));
    if (h.containsKey("linked"))
      setLinked((localLinked));
    if (h.containsKey("ardais_staff_id"))
      setArdais_staff_id((localArdais_staff_id));
    if (h.containsKey("form_verified_datetime"))
      setForm_verified_datetime((localForm_verified_datetime));
    if (h.containsKey("consent_version_id"))
      setConsent_version_id((localConsent_version_id));
    if (h.containsKey("form_signed_ind"))
      setForm_signed_ind((localForm_signed_ind));
    if (h.containsKey("policy_id"))
      setPolicy_id((localPolicy_id));
    if (h.containsKey("additional_needle_stick_yn"))
      setAdditional_needle_stick_yn((localAdditional_needle_stick_yn));
    if (h.containsKey("imported_yn"))
      setImported_yn((localImported_yn));
    if (h.containsKey("ardais_acct_key"))
      setArdais_acct_key((localArdais_acct_key));
    if (h.containsKey("customer_id"))
      setCustomer_id((localCustomer_id));
    if (h.containsKey("psa"))
      setPsa((localPsa));
    if (h.containsKey("clinical_finding_notes"))
      setClinical_finding_notes((localClinical_finding_notes));
    if (h.containsKey("dre_cid"))
      setDre_cid((localDre_cid));
    if (h.containsKey("case_registration_form"))
      setCase_registration_form((localCase_registration_form));
  }
  /**
   * Get accessor for persistent attribute: imported_yn
   */
  public java.lang.String getImported_yn() {
    return imported_yn;
  }
  /**
   * Set accessor for persistent attribute: imported_yn
   */
  public void setImported_yn(java.lang.String newImported_yn) {
    imported_yn = newImported_yn;
  }
  /**
   * Get accessor for persistent attribute: customer_id
   */
  public java.lang.String getCustomer_id() {
    return customer_id;
  }
  /**
   * Set accessor for persistent attribute: customer_id
   */
  public void setCustomer_id(java.lang.String newCustomer_id) {
    customer_id = newCustomer_id;
  }
  /**
   * Get accessor for persistent attribute: ardais_acct_key
   */
  public java.lang.String getArdais_acct_key() {
    return ardais_acct_key;
  }
  /**
   * Set accessor for persistent attribute: ardais_acct_key
   */
  public void setArdais_acct_key(java.lang.String newArdais_acct_key) {
    ardais_acct_key = newArdais_acct_key;
  }
  /**
   * Get accessor for persistent attribute: psa
   */
  public java.math.BigDecimal getPsa() {
    return psa;
  }
  /**
   * Set accessor for persistent attribute: psa
   */
  public void setPsa(java.math.BigDecimal newPsa) {
    psa = newPsa;
  }
  /**
   * Get accessor for persistent attribute: dre_cid
   */
  public java.lang.String getDre_cid() {
    return dre_cid;
  }
  /**
   * Set accessor for persistent attribute: dre_cid
   */
  public void setDre_cid(java.lang.String newDre_cid) {
    dre_cid = newDre_cid;
  }
  /**
   * Get accessor for persistent attribute: clinical_finding_notes
   */
  public java.lang.String getClinical_finding_notes() {
    return clinical_finding_notes;
  }
  /**
   * Set accessor for persistent attribute: clinical_finding_notes
   */
  public void setClinical_finding_notes(java.lang.String newClinical_finding_notes) {
    clinical_finding_notes = newClinical_finding_notes;
  }
  /**
   * Get accessor for persistent attribute: case_registration_form
   */
  public java.lang.String getCase_registration_form() {
    return case_registration_form;
  }
  /**
   * Set accessor for persistent attribute: case_registration_form
   */
  public void setCase_registration_form(java.lang.String newCase_registration_form) {
    case_registration_form = newCase_registration_form;
  }
}
