package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Consent
 */
public interface Consent extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {















  /**
   * 
   * @return java.lang.String
   */
  java.lang.String getArdais_staff_id() throws java.rmi.RemoteException;
  /**
   * This method was generated for supporting the association named Consent REFILTDS_ARDAIS_STAFF3 Ardaisstaff.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.ArdaisstaffKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  com.ardais.bigr.iltds.beans.ArdaisstaffKey getArdaisstaffKey() throws java.rmi.RemoteException;
  /**
   * This method was generated for supporting the relationship role named asmform.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public java.util.Enumeration getAsmform()
    throws java.rmi.RemoteException, javax.ejb.FinderException;
  /**
   * Getter method for bankable_ind
   * @return java.lang.String
   */
  java.lang.String getBankable_ind() throws java.rmi.RemoteException;
  /**
   * Getter method for consent_datetime
   * @return java.sql.Timestamp
   */
  java.sql.Timestamp getConsent_datetime() throws java.rmi.RemoteException;
  /**
   * Getter method for consent_pull_comment
   * @return java.lang.String
   */
  java.lang.String getConsent_pull_comment() throws java.rmi.RemoteException;
  /**
   * Getter method for consent_pull_datetime
   * @return java.sql.Timestamp
   */
  java.sql.Timestamp getConsent_pull_datetime() throws java.rmi.RemoteException;
  /**
   * Getter method for consent_pull_reason_cd
   * @return java.lang.String
   */
  java.lang.String getConsent_pull_reason_cd() throws java.rmi.RemoteException;
  /**
   * Getter method for consent_pull_request_by
   * @return java.lang.String
   */
  java.lang.String getConsent_pull_request_by() throws java.rmi.RemoteException;
  /**
   * Getter method for consent_pull_staff_id
   * @return java.lang.String
   */
  java.lang.String getConsent_pull_staff_id() throws java.rmi.RemoteException;
  /**
   * Getter method for consent_release_datetime
   * @return java.sql.Timestamp
   */
  java.sql.Timestamp getConsent_release_datetime() throws java.rmi.RemoteException;
  /**
   * Getter method for consent_release_staff_id
   * @return java.lang.String
   */
  java.lang.String getConsent_release_staff_id() throws java.rmi.RemoteException;
  /**
   * Getter method for consent_version_num
   * @return java.lang.String
   */
  java.lang.String getConsent_version_num() throws java.rmi.RemoteException;
  /**
   * Getter method for disease_concept_id
   * @return java.lang.String
   */
  java.lang.String getDisease_concept_id() throws java.rmi.RemoteException;
  /**
   * 
   * @return java.lang.String
   */
  java.lang.String getDisease_concept_id_other() throws java.rmi.RemoteException;
  /**
   * Getter method for form_entry_datetime
   * @return java.sql.Timestamp
   */
  java.sql.Timestamp getForm_entry_datetime() throws java.rmi.RemoteException;
  /**
   * Getter method for form_entry_staff_id
   * @return java.lang.String
   */
  java.lang.String getForm_entry_staff_id() throws java.rmi.RemoteException;
  /**
   * Getter method for form_signed_ind
   * @return java.lang.String
   */
  java.lang.String getForm_signed_ind() throws java.rmi.RemoteException;
  /**
   * Getter method for form_verified_by_staff_id
   * @return java.lang.String
   */
  java.lang.String getForm_verified_by_staff_id() throws java.rmi.RemoteException;
  /**
   * Getter method for form_verified_datetime
   * @return java.sql.Timestamp
   */
  java.sql.Timestamp getForm_verified_datetime() throws java.rmi.RemoteException;
  /**
   * Getter method for form_verified_sign_ind
   * @return java.lang.String
   */
  java.lang.String getForm_verified_sign_ind() throws java.rmi.RemoteException;
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.Geolocation
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  com.ardais.bigr.iltds.beans.Geolocation getGeolocation()
    throws java.rmi.RemoteException, javax.ejb.FinderException;
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return com.ardais.bigr.iltds.beans.GeolocationKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  com.ardais.bigr.iltds.beans.GeolocationKey getGeolocationKey() throws java.rmi.RemoteException;
  /**
   * Getter method for linked
   * @return java.lang.String
   */
  java.lang.String getLinked() throws java.rmi.RemoteException;
  /**
   * This method was generated for supporting the association named Consent REFILTDS_ARDAIS_STAFF3 Ardaisstaff.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param inKey com.ardais.bigr.iltds.beans.ArdaisstaffKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  void privateSetArdaisstaffKey(com.ardais.bigr.iltds.beans.ArdaisstaffKey inKey)
    throws java.rmi.RemoteException;
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param inKey com.ardais.bigr.iltds.beans.GeolocationKey
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  void privateSetGeolocationKey(com.ardais.bigr.iltds.beans.GeolocationKey inKey)
    throws java.rmi.RemoteException;
  /**
   * This method was generated for supporting the relationship role named asmform.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryAddAsmform(com.ardais.bigr.iltds.beans.Asmform anAsmform)
    throws java.rmi.RemoteException;
  /**
   * This method was generated for supporting the relationship role named asmform.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryRemoveAsmform(com.ardais.bigr.iltds.beans.Asmform anAsmform)
    throws java.rmi.RemoteException;
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aGeolocation com.ardais.bigr.iltds.beans.Geolocation
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  void secondarySetGeolocation(com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
    throws java.rmi.RemoteException;
  /**
   * 
   * @return void
   * @param newValue java.lang.String
   */
  void setArdais_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for bankable_ind
   * @param newValue java.lang.String
   */
  void setBankable_ind(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for consent_datetime
   * @param newValue java.sql.Timestamp
   */
  void setConsent_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for consent_pull_comment
   * @param newValue java.lang.String
   */
  void setConsent_pull_comment(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for consent_pull_datetime
   * @param newValue java.sql.Timestamp
   */
  void setConsent_pull_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for consent_pull_reason_cd
   * @param newValue java.lang.String
   */
  void setConsent_pull_reason_cd(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for consent_pull_request_by
   * @param newValue java.lang.String
   */
  void setConsent_pull_request_by(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for consent_pull_staff_id
   * @param newValue java.lang.String
   */
  void setConsent_pull_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for consent_release_datetime
   * @param newValue java.sql.Timestamp
   */
  void setConsent_release_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for consent_release_staff_id
   * @param newValue java.lang.String
   */
  void setConsent_release_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for consent_version_num
   * @param newValue java.lang.String
   */
  void setConsent_version_num(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for disease_concept_id
   * @param newValue java.lang.String
   */
  void setDisease_concept_id(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * 
   * @return void
   * @param newDisease_concept_id_other java.lang.String
   */
  void setDisease_concept_id_other(java.lang.String newDisease_concept_id_other)
    throws java.rmi.RemoteException;
  /**
   * Setter method for form_entry_datetime
   * @param newValue java.sql.Timestamp
   */
  void setForm_entry_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for form_entry_staff_id
   * @param newValue java.lang.String
   */
  void setForm_entry_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for form_signed_ind
   * @param newValue java.lang.String
   */
  void setForm_signed_ind(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for form_verified_by_staff_id
   * @param newValue java.lang.String
   */
  void setForm_verified_by_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for form_verified_datetime
   * @param newValue java.sql.Timestamp
   */
  void setForm_verified_datetime(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
  /**
   * Setter method for form_verified_sign_ind
   * @param newValue java.lang.String
   */
  void setForm_verified_sign_ind(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @param aGeolocation com.ardais.bigr.iltds.beans.Geolocation
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  void setGeolocation(com.ardais.bigr.iltds.beans.Geolocation aGeolocation)
    throws java.rmi.RemoteException;
  /**
   * Setter method for linked
   * @param newValue java.lang.String
   */
  void setLinked(java.lang.String newValue) throws java.rmi.RemoteException;
  /**
   * This method was generated for supporting the relationship role named asmform.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void addAsmform(com.ardais.bigr.iltds.beans.Asmform anAsmform)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: ardais_id
   */
  public java.lang.String getArdais_id() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: ardais_id
   */
  public void setArdais_id(java.lang.String newArdais_id) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: policy_id
   */
  public java.math.BigDecimal getPolicy_id() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: policy_id
   */
  public void setPolicy_id(java.math.BigDecimal newPolicy_id) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: consent_version_id
   */
  public java.math.BigDecimal getConsent_version_id() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: consent_version_id
   */
  public void setConsent_version_id(java.math.BigDecimal newConsent_version_id)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: comments
   */
  public java.lang.String getComments() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: comments
   */
  public void setComments(java.lang.String newComments) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: blood_sample_yn
   */
  public java.lang.String getBlood_sample_yn() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: blood_sample_yn
   */
  public void setBlood_sample_yn(java.lang.String newBlood_sample_yn)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: additional_needle_stick_yn
   */
  public java.lang.String getAdditional_needle_stick_yn() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: additional_needle_stick_yn
   */
  public void setAdditional_needle_stick_yn(java.lang.String newAdditional_needle_stick_yn)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: future_contact_yn
   */
  public java.lang.String getFuture_contact_yn() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: future_contact_yn
   */
  public void setFuture_contact_yn(java.lang.String newFuture_contact_yn)
    throws java.rmi.RemoteException;
  /**
   * 
   * @return java.lang.String
   */
  java.lang.String getBest_diagnosis_cui() throws java.rmi.RemoteException;
  /**
   * 
   * @return java.lang.String
   */
  java.lang.String getBest_diagnosis_other() throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: imported_yn
   */
  public java.lang.String getImported_yn() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: imported_yn
   */
  public void setImported_yn(java.lang.String newImported_yn) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: customer_id
   */
  public java.lang.String getCustomer_id() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: customer_id
   */
  public void setCustomer_id(java.lang.String newCustomer_id) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: ardais_acct_key
   */
  public java.lang.String getArdais_acct_key() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: ardais_acct_key
   */
  public void setArdais_acct_key(java.lang.String newArdais_acct_key)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: psa
   */
  public java.math.BigDecimal getPsa() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: psa
   */
  public void setPsa(java.math.BigDecimal newPsa) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: dre_cid
   */
  public java.lang.String getDre_cid() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: dre_cid
   */
  public void setDre_cid(java.lang.String newDre_cid) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: clinical_finding_notes
   */
  public java.lang.String getClinical_finding_notes() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: clinical_finding_notes
   */
  public void setClinical_finding_notes(java.lang.String newClinical_finding_notes)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: case_registration_form
   */
  public java.lang.String getCase_registration_form() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: case_registration_form
   */
  public void setCase_registration_form(java.lang.String newCase_registration_form)
    throws java.rmi.RemoteException;
}
