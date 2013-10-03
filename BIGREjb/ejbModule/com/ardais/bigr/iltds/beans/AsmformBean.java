package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class AsmformBean implements EntityBean {
  public static final String DEFAULT_consent_consent_id = null;
  public static final String DEFAULT_consent_ardais_id = null;
  public static final String DEFAULT_asm_form_id = null;
  public static final String DEFAULT_ardais_staff_id = null;
  public static final String DEFAULT_surgical_specimen_id_other = null;
  public static final String DEFAULT_link_staff_id = null;
  public static final String DEFAULT_ardais_id = null;
  public static final java.sql.Timestamp DEFAULT_grossing_date = null;
  public static final java.sql.Timestamp DEFAULT_removal_date = null;
  public static final java.sql.Timestamp DEFAULT_creation_time = null;
  public static final String DEFAULT_surgical_specimen_id = null;
  public static final String DEFAULT_acc_surgical_removal_time = null;
  public static final java.sql.Timestamp DEFAULT_procedure_date = null;

  /**
   * Implementation field for persistent attribute: consent_consent_id
   */
  public java.lang.String consent_consent_id;
  public String asm_form_id;
  public String ardais_staff_id;
  public String surgical_specimen_id_other;
  public String link_staff_id;
  public java.sql.Timestamp grossing_date;
  public java.sql.Timestamp removal_date;
  public java.sql.Timestamp creation_time;
  public String surgical_specimen_id;

  private javax.ejb.EntityContext entityContext = null;
  final static long serialVersionUID = 3206093459760846163L;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink ardaisstaffLink = null;
  private transient com.ibm.ivj.ejb.associations.interfaces.SingleLink consentLink;
  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.AsmformBean.class);
  private javax.ejb.EntityContext myEntityCtx;
  /**
   * Implementation field for persistent attribute: ardais_id
   */
  public java.lang.String ardais_id;
  /**
   * Implementation field for persistent attribute: acc_surgical_removal_time
   */
  public java.lang.String acc_surgical_removal_time;
  /**
   * Implementation field for persistent attribute: procedure_date
   */
  public java.sql.Timestamp procedure_date;
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getConsentLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    consentLink = null;
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
   * ejbCreate method for a CMP entity bean
   * @param argAsm_form_id java.lang.String
   * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
   * @exception javax.ejb.CreateException The exception description.
   */
  public AsmformKey ejbCreate(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    asm_form_id = argAsm_form_id;
    boolean consent_NULLTEST = (argConsent == null);
    if (consent_NULLTEST)
      consent_consent_id = null;
    else
      consent_consent_id = argConsent.consent_id;
    return null;
  }

  /**
   * ejbCreate method for a CMP entity bean
   * @param argAsm_form_id java.lang.String
   * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
   * @param argArdais_id java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public AsmformKey ejbCreate(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    java.lang.String argArdais_id)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    asm_form_id = argAsm_form_id;
    ardais_id = argArdais_id;
    boolean consent_NULLTEST = (argConsent == null);
    if (consent_NULLTEST)
      consent_consent_id = null;
    else
      consent_consent_id = argConsent.consent_id;
    return null;
  }

  /**
   * ejbCreate method for a CMP entity bean
   * @param argAsm_form_id java.lang.String
   * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
   * @exception javax.ejb.CreateException The exception description.
   */
  public AsmformKey ejbCreate(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    ArdaisstaffKey argArdaisstaff)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    asm_form_id = argAsm_form_id;
    boolean consent_NULLTEST = (argConsent == null);
    if (consent_NULLTEST)
      consent_consent_id = null;
    else
      consent_consent_id = argConsent.consent_id;

    boolean ardaisstaff_NULLTEST = (argArdaisstaff == null);
    if (ardaisstaff_NULLTEST)
      ardais_staff_id = null;
    else
      ardais_staff_id = argArdaisstaff.ardais_staff_id;
    return null;

  }
  /**
   * ejbCreate method for a CMP entity bean
   * @param argAsm_form_id java.lang.String
   * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
   * @param argArdaisstaff com.ardais.bigr.iltds.beans.ArdaisstaffKey
   * @param argArdais_id java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public AsmformKey ejbCreate(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    ArdaisstaffKey argArdaisstaff,
    java.lang.String argArdais_id)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    asm_form_id = argAsm_form_id;
    ardais_id = argArdais_id;
    boolean consent_NULLTEST = (argConsent == null);
    if (consent_NULLTEST)
      consent_consent_id = null;
    else
      consent_consent_id = argConsent.consent_id;

    boolean ardaisstaff_NULLTEST = (argArdaisstaff == null);
    if (ardaisstaff_NULLTEST)
      ardais_staff_id = null;
    else
      ardais_staff_id = argArdaisstaff.ardais_staff_id;
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
   * ejbPostCreate method for a CMP entity bean
   * @param argAsm_form_id java.lang.String
   * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
   */
  public void ejbPostCreate(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent)
    throws EJBException {
  }

  /**
   * ejbPostCreate method for a CMP entity bean
   * @param argAsm_form_id java.lang.String
   * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
   * @param argArdais_id java.lang.String
   */
  public void ejbPostCreate(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    java.lang.String argArdais_id)
    throws EJBException {
  }

  /**
   * ejbRemove method comment
   */
  public void ejbPostCreate(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    ArdaisstaffKey argArdaisstaff)
    throws EJBException {
  }
  /**
   * ejbPostCreate method for a CMP entity bean
   * @param argAsm_form_id java.lang.String
   * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
   * @param argArdaisstaff com.ardais.bigr.iltds.beans.ArdaisstaffKey
   * @param argArdais_id java.lang.String
   */
  public void ejbPostCreate(
    java.lang.String argAsm_form_id,
    com.ardais.bigr.iltds.beans.ConsentKey argConsent,
    ArdaisstaffKey argArdaisstaff,
    java.lang.String argArdais_id)
    throws EJBException {
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
   * This method was generated for supporting the association named Asmform REFILTDS_ARDAIS_STAFF23 Ardaisstaff.  
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
   * This method was generated for supporting the relationship role named consent.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.iltds.beans.Consent getConsent()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return (com.ardais.bigr.iltds.beans.Consent) this.getConsentLink().value();
  }
  /**
   * This method was generated for supporting the relationship role named consent.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.iltds.beans.ConsentKey getConsentKey() {
    com.ardais.bigr.iltds.beans.ConsentKey temp = new com.ardais.bigr.iltds.beans.ConsentKey();
    boolean consent_NULLTEST = true;
    consent_NULLTEST &= (consent_consent_id == null);
    temp.consent_id = consent_consent_id;
    if (consent_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the relationship role named consent.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.SingleLink getConsentLink() {
    if (consentLink == null)
      consentLink = new AsmformToConsentLink(this);
    return consentLink;
  }
  /**
   * Getter method for creation_time
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getCreation_time() {
    return creation_time;
  }
  /**
   * getEntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return myEntityCtx;
  }
  /**
   * Getter method for grossing_date
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getGrossing_date() {
    return grossing_date;
  }
  /**
   * Getter method for link_staff_id
   * @return java.lang.String
   */
  public java.lang.String getLink_staff_id() {
    return link_staff_id;
  }
  /**
   * Getter method for removal_date
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getRemoval_date() {
    return removal_date;
  }
  /**
   * Getter method for surgical_specimen_id
   * @return java.lang.String
   */
  public java.lang.String getSurgical_specimen_id() {
    return surgical_specimen_id;
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/16/2001 1:05:54 PM)
   * @return java.lang.String
   */
  public java.lang.String getSurgical_specimen_id_other() {
    return surgical_specimen_id_other;
  }
  /**
   * This method was generated for supporting the association named Asmform REFILTDS_ARDAIS_STAFF23 Ardaisstaff.  
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
   * This method was generated for supporting the relationship role named consent.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetConsentKey(com.ardais.bigr.iltds.beans.ConsentKey inKey) {
    boolean consent_NULLTEST = (inKey == null);
    consent_consent_id = (consent_NULLTEST) ? null : inKey.consent_id;
  }
  /**
   * Setter method for ardais_staff_id
   */
  public void setArdais_staff_id(java.lang.String newValue) {
    this.ardais_staff_id = newValue;
  }
  /**
   * This method was generated for supporting the relationship role named consent.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void setConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
    throws java.rmi.RemoteException {
    this.getConsentLink().set(aConsent);
  }
  /**
   * Setter method for creation_time
   * @param newValue java.sql.Timestamp
   */
  public void setCreation_time(java.sql.Timestamp newValue) {
    this.creation_time = newValue;
  }
  /**
   * setEntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    myEntityCtx = ctx;
  }
  /**
   * Setter method for grossing_date
   * @param newValue java.sql.Timestamp
   */
  public void setGrossing_date(java.sql.Timestamp newValue) {
    this.grossing_date = newValue;
  }
  /**
   * Setter method for link_staff_id
   * @param newValue java.lang.String
   */
  public void setLink_staff_id(java.lang.String newValue) {
    this.link_staff_id = newValue;
  }
  /**
   * Setter method for removal_date
   * @param newValue java.sql.Timestamp
   */
  public void setRemoval_date(java.sql.Timestamp newValue) {
    this.removal_date = newValue;
  }
  /**
   * Setter method for surgical_specimen_id
   * @param newValue java.lang.String
   */
  public void setSurgical_specimen_id(java.lang.String newValue) {
    this.surgical_specimen_id = newValue;
  }
  /**
   * Insert the method's description here.
   * Creation date: (10/16/2001 1:05:54 PM)
   * @param newSurgical_specimen_id_other java.lang.String
   */
  public void setSurgical_specimen_id_other(java.lang.String newSurgical_specimen_id_other) {
    surgical_specimen_id_other = newSurgical_specimen_id_other;
  }
  /**
   * unsetEntityContext
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    myEntityCtx = null;
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.iltds.beans.AsmformKey ejbCreate(
    java.lang.String asm_form_id,
    com.ardais.bigr.iltds.beans.Consent argConsent)
    throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.asm_form_id = asm_form_id;
    try {
      setConsent(argConsent);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
    return null;
  }
  /**
   * ejbPostCreate
   */
  public void ejbPostCreate(
    java.lang.String asm_form_id,
    com.ardais.bigr.iltds.beans.Consent argConsent)
    throws javax.ejb.CreateException {
    try {
      setConsent(argConsent);
    }
    catch (java.rmi.RemoteException remoteEx) {
      throw new javax.ejb.CreateException(remoteEx.getMessage());
    }
  }
  /**
   * This method was generated for supporting the relationship role named consent.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondarySetConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
    throws java.rmi.RemoteException {
    this.getConsentLink().secondarySet(aConsent);
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
   * Get accessor for persistent attribute: acc_surgical_removal_time
   */
  public java.lang.String getAcc_surgical_removal_time() {
    return acc_surgical_removal_time;
  }
  /**
   * Set accessor for persistent attribute: acc_surgical_removal_time
   */
  public void setAcc_surgical_removal_time(java.lang.String newAcc_surgical_removal_time) {
    acc_surgical_removal_time = newAcc_surgical_removal_time;
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("ardais_id", getArdais_id());
    h.put("ardais_staff_id", getArdais_staff_id());
    h.put("consentKey", getConsentKey());
    h.put("creation_time", getCreation_time());
    h.put("link_staff_id", getLink_staff_id());
    h.put("surgical_specimen_id", getSurgical_specimen_id());
    h.put("grossing_date", getGrossing_date());
    h.put("ardaisstaffKey", getArdaisstaffKey());
    h.put("removal_date", getRemoval_date());
    h.put("surgical_specimen_id_other", getSurgical_specimen_id_other());
    h.put("acc_surgical_removal_time", getAcc_surgical_removal_time());
    h.put("procedure_date", getProcedure_date());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localArdais_id = (java.lang.String) h.get("ardais_id");
    java.lang.String localArdais_staff_id = (java.lang.String) h.get("ardais_staff_id");
    java.sql.Timestamp localCreation_time = (java.sql.Timestamp) h.get("creation_time");
    java.lang.String localLink_staff_id = (java.lang.String) h.get("link_staff_id");
    java.lang.String localSurgical_specimen_id = (java.lang.String) h.get("surgical_specimen_id");
    java.sql.Timestamp localGrossing_date = (java.sql.Timestamp) h.get("grossing_date");
    java.sql.Timestamp localRemoval_date = (java.sql.Timestamp) h.get("removal_date");
    java.lang.String localSurgical_specimen_id_other = (java.lang.String) h
      .get("surgical_specimen_id_other");
    java.lang.String localAcc_surgical_removal_time = (java.lang.String) h
      .get("acc_surgical_removal_time");
    java.sql.Timestamp localProcedure_date = (java.sql.Timestamp) h.get("procedure_date");

    if (h.containsKey("ardais_id"))
      setArdais_id((localArdais_id));
    if (h.containsKey("ardais_staff_id"))
      setArdais_staff_id((localArdais_staff_id));
    if (h.containsKey("creation_time"))
      setCreation_time((localCreation_time));
    if (h.containsKey("link_staff_id"))
      setLink_staff_id((localLink_staff_id));
    if (h.containsKey("surgical_specimen_id"))
      setSurgical_specimen_id((localSurgical_specimen_id));
    if (h.containsKey("grossing_date"))
      setGrossing_date((localGrossing_date));
    if (h.containsKey("removal_date"))
      setRemoval_date((localRemoval_date));
    if (h.containsKey("surgical_specimen_id_other"))
      setSurgical_specimen_id_other((localSurgical_specimen_id_other));
    if (h.containsKey("acc_surgical_removal_time"))
      setAcc_surgical_removal_time((localAcc_surgical_removal_time));
    if (h.containsKey("procedure_date"))
      setProcedure_date((localProcedure_date));
  }
  /**
   * Get accessor for persistent attribute: procedure_date
   */
  public java.sql.Timestamp getProcedure_date() {
    return procedure_date;
  }
  /**
   * Set accessor for persistent attribute: procedure_date
   */
  public void setProcedure_date(java.sql.Timestamp newProcedure_date) {
    procedure_date = newProcedure_date;
  }
}
