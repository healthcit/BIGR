package com.ardais.bigr.es.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class ArdaisaccountBean implements EntityBean {
  public static final String DEFAULT_ardais_acct_key = null;
  public static final String DEFAULT_ardais_acct_type = null;
  public static final String DEFAULT_ardais_acct_status = null;
  public static final String DEFAULT_ardais_acct_company_desc = null;
  public static final String DEFAULT_ardais_parent_acct_comp_desc = null;
  public static final java.sql.Timestamp DEFAULT_ardais_acct_open_date = null;
  public static final java.sql.Timestamp DEFAULT_ardais_acct_active_date = null;
  public static final java.sql.Timestamp DEFAULT_ardais_acct_close_date = null;
  public static final java.sql.Timestamp DEFAULT_ardais_acct_deactivate_date = null;
  public static final String DEFAULT_ardais_acct_deactivate_reason = null;
  public static final java.sql.Timestamp DEFAULT_ardais_acct_reactivate_date = null;
  public static final String DEFAULT_request_mgr_email_address = null;
  public static final String DEFAULT_linked_cases_only_yn = null;
  public static final String DEFAULT_passwordPolicyCid = null;
  public static final int DEFAULT_passwordLifeSpan = 0;  
  public static final String DEFAULT_defaultBoxLayoutId = null;
  public static final String DEFAULT_primaryLocation = null;
  public static final String DEFAULT_donorRegistrationForm = null;

  public String ardais_acct_key;
  public String ardais_acct_type;
  public String ardais_acct_status;
  public String ardais_acct_company_desc;
  public String ardais_parent_acct_comp_desc;
  public java.sql.Timestamp ardais_acct_open_date;
  public java.sql.Timestamp ardais_acct_active_date;
  public java.sql.Timestamp ardais_acct_close_date;
  public java.sql.Timestamp ardais_acct_deactivate_date;
  public String ardais_acct_deactivate_reason;
  public java.sql.Timestamp ardais_acct_reactivate_date;
  public String request_mgr_email_address;
  private javax.ejb.EntityContext entityContext = null;
  final static long serialVersionUID = 3206093459760846163L;
  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.es.beans.ArdaisaccountBean.class);
  private transient com.ibm.ivj.ejb.associations.interfaces.ManyLink ardaisuserLink;
  public java.lang.String linked_cases_only_yn;
  
  /**
   * Implementation field for persistent attribute: passwordPolicyCid
   */
  public java.lang.String passwordPolicyCid;
  /**
   * Implementation field for persistent attribute: passwordLifeSpan
   */
  public int passwordLifeSpan;
  /**
   * Implementation field for persistent attribute: defaultBoxLayoutId
   */
  public java.lang.String defaultBoxLayoutId;
  /**
   * Implementation field for persistent attribute: primaryLocation
   */
  public java.lang.String primaryLocation;
  /**
   * Implementation field for persistent attribute: donorRegistrationForm
   */
  public java.lang.String donorRegistrationForm;
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    links.add(getArdaisuserLink());
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
    ardaisuserLink = null;
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _removeLinks() throws java.rmi.RemoteException, javax.ejb.RemoveException {
    java.util.List links = _getLinks();
    for (int i = 0; i < links.size(); i++) {
      try {
        ((com.ibm.ivj.ejb.associations.interfaces.Link) links.get(i)).remove();
      } catch (javax.ejb.FinderException e) {
      } //Consume Finder error since I am going away
    }
  }
  /**
   * ejbActivate method comment
   */
  public void ejbActivate() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.es.beans.ArdaisaccountKey ejbCreate(
    java.lang.String ardais_acct_key,
    java.lang.String linked_cases_only_yn,
    java.lang.String argPasswordPolicyCid,
    int argPasswordLifeSpan)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.ardais_acct_key = ardais_acct_key;
    this.linked_cases_only_yn = linked_cases_only_yn;
    setPasswordPolicyCid(argPasswordPolicyCid);
    setPasswordLifeSpan(argPasswordLifeSpan);
    return null;
  }
  /**
   * ejbLoad method comment
   */
  public void ejbLoad() throws java.rmi.RemoteException {
    _initLinks();
  }
  /**
   * ejbPassivate method comment
   */
  public void ejbPassivate() throws java.rmi.RemoteException {
  }
  /**
   * ejbPostCreate
   */
  public void ejbPostCreate(
    java.lang.String ardais_acct_key,
    java.lang.String linked_cases_only_yn,
    java.lang.String argPasswordPolicyCid,
    int argPasswordLifeSpan)
    throws javax.ejb.CreateException, EJBException {
  }
  /**
   * ejbRemove method comment
   */
  public void ejbRemove() throws java.rmi.RemoteException, javax.ejb.RemoveException {
    _removeLinks();
  }
  /**
   * ejbStore method comment
   */
  public void ejbStore() throws java.rmi.RemoteException {
  }
  /**
   * Getter method for ardais_acct_active_date
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getArdais_acct_active_date() {
    return ardais_acct_active_date;
  }
  /**
   * Getter method for ardais_acct_close_date
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getArdais_acct_close_date() {
    return ardais_acct_close_date;
  }
  /**
   * Getter method for ardais_acct_company_desc
   * @return java.lang.String
   */
  public java.lang.String getArdais_acct_company_desc() {
    return ardais_acct_company_desc;
  }
  /**
   * Getter method for ardais_acct_deactivate_date
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getArdais_acct_deactivate_date() {
    return ardais_acct_deactivate_date;
  }
  /**
   * Getter method for ardais_acct_deactivate_reason
   * @return java.lang.String
   */
  public java.lang.String getArdais_acct_deactivate_reason() {
    return ardais_acct_deactivate_reason;
  }
  /**
   * Getter method for ardais_acct_open_date
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getArdais_acct_open_date() {
    return ardais_acct_open_date;
  }
  /**
   * Getter method for ardais_acct_reactivate_date
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getArdais_acct_reactivate_date() {
    return ardais_acct_reactivate_date;
  }
  /**
   * Getter method for ardais_acct_status
   * @return java.lang.String
   */
  public java.lang.String getArdais_acct_status() {
    return ardais_acct_status;
  }
  /**
   * Getter method for ardais_acct_type
   * @return java.lang.String
   */
  public java.lang.String getArdais_acct_type() {
    return ardais_acct_type;
  }
  /**
   * Getter method for ardais_parent_acct_comp_desc
   * @return java.lang.String
   */
  public java.lang.String getArdais_parent_acct_comp_desc() {
    return ardais_parent_acct_comp_desc;
  }
  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }
  /**
   * Setter method for ardais_acct_active_date
   * @param newValue java.sql.Timestamp
   */
  public void setArdais_acct_active_date(java.sql.Timestamp newValue) {
    this.ardais_acct_active_date = newValue;
  }
  /**
   * Setter method for ardais_acct_close_date
   * @param newValue java.sql.Timestamp
   */
  public void setArdais_acct_close_date(java.sql.Timestamp newValue) {
    this.ardais_acct_close_date = newValue;
  }
  /**
   * Setter method for ardais_acct_company_desc
   * @param newValue java.lang.String
   */
  public void setArdais_acct_company_desc(java.lang.String newValue) {
    this.ardais_acct_company_desc = newValue;
  }
  /**
   * Setter method for ardais_acct_deactivate_date
   * @param newValue java.sql.Timestamp
   */
  public void setArdais_acct_deactivate_date(java.sql.Timestamp newValue) {
    this.ardais_acct_deactivate_date = newValue;
  }
  /**
   * Setter method for ardais_acct_deactivate_reason
   * @param newValue java.lang.String
   */
  public void setArdais_acct_deactivate_reason(java.lang.String newValue) {
    this.ardais_acct_deactivate_reason = newValue;
  }
  /**
   * Setter method for ardais_acct_open_date
   * @param newValue java.sql.Timestamp
   */
  public void setArdais_acct_open_date(java.sql.Timestamp newValue) {
    this.ardais_acct_open_date = newValue;
  }
  /**
   * Setter method for ardais_acct_reactivate_date
   * @param newValue java.sql.Timestamp
   */
  public void setArdais_acct_reactivate_date(java.sql.Timestamp newValue) {
    this.ardais_acct_reactivate_date = newValue;
  }
  /**
   * Setter method for ardais_acct_status
   * @param newValue java.lang.String
   */
  public void setArdais_acct_status(java.lang.String newValue) {
    this.ardais_acct_status = newValue;
  }
  /**
   * Setter method for ardais_acct_type
   * @param newValue java.lang.String
   */
  public void setArdais_acct_type(java.lang.String newValue) {
    this.ardais_acct_type = newValue;
  }
  /**
   * Setter method for ardais_parent_acct_comp_desc
   * @param newValue java.lang.String
   */
  public void setArdais_parent_acct_comp_desc(java.lang.String newValue) {
    this.ardais_parent_acct_comp_desc = newValue;
  }
  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws javax.ejb.EJBException {
    entityContext = ctx;
  }
  /**
   * unsetEntityContext method comment
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    entityContext = null;
  }

  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryAddArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
    throws java.rmi.RemoteException {
    this.getArdaisuserLink().secondaryAddElement(anArdaisuser);
  }
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void secondaryRemoveArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
    throws java.rmi.RemoteException {
    this.getArdaisuserLink().secondaryRemoveElement(anArdaisuser);
  }
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  protected com.ibm.ivj.ejb.associations.interfaces.ManyLink getArdaisuserLink() {
    if (ardaisuserLink == null)
      ardaisuserLink = new ArdaisaccountToArdaisuserLink(this);
    return ardaisuserLink;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisuser.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public java.util.Enumeration getArdaisuser()
    throws java.rmi.RemoteException, javax.ejb.FinderException {
    return this.getArdaisuserLink().enumerationValue();
  }
  /**
   * Get accessor for persistent attribute: request_mgr_email_address
   */
  public java.lang.String getRequest_mgr_email_address() {
    return request_mgr_email_address;
  }
  /**
   * Set accessor for persistent attribute: request_mgr_email_address
   */
  public void setRequest_mgr_email_address(java.lang.String newRequest_mgr_email_address) {
    request_mgr_email_address = newRequest_mgr_email_address;
  }
  /**
   * Get accessor for persistent attribute: linked_cases_only_yn
   */
  public java.lang.String getLinked_cases_only_yn() {
    return linked_cases_only_yn;
  }
  /**
   * Set accessor for persistent attribute: linked_cases_only_yn
   */
  public void setLinked_cases_only_yn(java.lang.String newLinked_cases_only_yn) {
    linked_cases_only_yn = newLinked_cases_only_yn;
  }
  /**
   * Get accessor for persistent attribute: passwordPolicyCid
   */
  public java.lang.String getPasswordPolicyCid() {
    return passwordPolicyCid;
  }
  /**
   * Set accessor for persistent attribute: passwordPolicyCid
   */
  public void setPasswordPolicyCid(java.lang.String newPasswordPolicyCid) {
    passwordPolicyCid = newPasswordPolicyCid;
  }
  /**
   * Get accessor for persistent attribute: passwordLifeSpan
   */
  public int getPasswordLifeSpan() {
    return passwordLifeSpan;
  }
  /**
   * Set accessor for persistent attribute: passwordLifeSpan
   */
  public void setPasswordLifeSpan(int newPasswordLifeSpan) {
    passwordLifeSpan = newPasswordLifeSpan;
  }
  /**
   * Get accessor for persistent attribute: defaultBoxLayoutId
   */
  public java.lang.String getDefaultBoxLayoutId() {
    return defaultBoxLayoutId;
  }
  /**
   * Set accessor for persistent attribute: defaultBoxLayoutId
   */
  public void setDefaultBoxLayoutId(java.lang.String newDefaultBoxLayoutId) {
    defaultBoxLayoutId = newDefaultBoxLayoutId;
  }
  /**
   * Get accessor for persistent attribute: primaryLocation
   */
  public java.lang.String getPrimaryLocation() {
    return primaryLocation;
  }
  /**
   * Set accessor for persistent attribute: primaryLocation
   */
  public void setPrimaryLocation(java.lang.String newPrimaryLocation) {
    primaryLocation = newPrimaryLocation;
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("ardais_acct_open_date", getArdais_acct_open_date());
    h.put("defaultBoxLayoutId", getDefaultBoxLayoutId());
    h.put("ardais_acct_reactivate_date", getArdais_acct_reactivate_date());
    h.put("ardais_acct_type", getArdais_acct_type());
    h.put("ardais_parent_acct_comp_desc", getArdais_parent_acct_comp_desc());
    h.put("ardais_acct_deactivate_date", getArdais_acct_deactivate_date());
    h.put("ardais_acct_status", getArdais_acct_status());
    h.put("request_mgr_email_address", getRequest_mgr_email_address());
    h.put("ardais_acct_close_date", getArdais_acct_close_date());
    h.put("passwordPolicyCid", getPasswordPolicyCid());
    h.put("primaryLocation", getPrimaryLocation());
    h.put("linked_cases_only_yn", getLinked_cases_only_yn());
    h.put("ardais_acct_active_date", getArdais_acct_active_date());
    h.put("ardais_acct_company_desc", getArdais_acct_company_desc());
    h.put("passwordLifeSpan", new Integer(getPasswordLifeSpan()));
    h.put("ardais_acct_deactivate_reason", getArdais_acct_deactivate_reason());
    h.put("donorRegistrationForm", getDonorRegistrationForm());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.sql.Timestamp localArdais_acct_open_date = (java.sql.Timestamp) h
      .get("ardais_acct_open_date");
    java.lang.String localDefaultBoxLayoutId = (java.lang.String) h.get("defaultBoxLayoutId");
    java.sql.Timestamp localArdais_acct_reactivate_date = (java.sql.Timestamp) h
      .get("ardais_acct_reactivate_date");
    java.lang.String localArdais_acct_type = (java.lang.String) h.get("ardais_acct_type");
    java.lang.String localArdais_parent_acct_comp_desc = (java.lang.String) h
      .get("ardais_parent_acct_comp_desc");
    java.sql.Timestamp localArdais_acct_deactivate_date = (java.sql.Timestamp) h
      .get("ardais_acct_deactivate_date");
    java.lang.String localArdais_acct_status = (java.lang.String) h.get("ardais_acct_status");
    java.lang.String localRequest_mgr_email_address = (java.lang.String) h
      .get("request_mgr_email_address");
    java.sql.Timestamp localArdais_acct_close_date = (java.sql.Timestamp) h
      .get("ardais_acct_close_date");
    java.lang.String localPasswordPolicyCid = (java.lang.String) h.get("passwordPolicyCid");
    java.lang.String localPrimaryLocation = (java.lang.String) h.get("primaryLocation");
    java.lang.String localLinked_cases_only_yn = (java.lang.String) h.get("linked_cases_only_yn");
    java.sql.Timestamp localArdais_acct_active_date = (java.sql.Timestamp) h
      .get("ardais_acct_active_date");
    java.lang.String localArdais_acct_company_desc = (java.lang.String) h
      .get("ardais_acct_company_desc");
    Integer localPasswordLifeSpan = (Integer) h.get("passwordLifeSpan");
    java.lang.String localArdais_acct_deactivate_reason = (java.lang.String) h
      .get("ardais_acct_deactivate_reason");
    java.lang.String localDonorRegistrationForm = (java.lang.String) h.get("donorRegistrationForm");

    if (h.containsKey("ardais_acct_open_date"))
      setArdais_acct_open_date((localArdais_acct_open_date));
    if (h.containsKey("defaultBoxLayoutId"))
      setDefaultBoxLayoutId((localDefaultBoxLayoutId));
    if (h.containsKey("ardais_acct_reactivate_date"))
      setArdais_acct_reactivate_date((localArdais_acct_reactivate_date));
    if (h.containsKey("ardais_acct_type"))
      setArdais_acct_type((localArdais_acct_type));
    if (h.containsKey("ardais_parent_acct_comp_desc"))
      setArdais_parent_acct_comp_desc((localArdais_parent_acct_comp_desc));
    if (h.containsKey("ardais_acct_deactivate_date"))
      setArdais_acct_deactivate_date((localArdais_acct_deactivate_date));
    if (h.containsKey("ardais_acct_status"))
      setArdais_acct_status((localArdais_acct_status));
    if (h.containsKey("request_mgr_email_address"))
      setRequest_mgr_email_address((localRequest_mgr_email_address));
    if (h.containsKey("ardais_acct_close_date"))
      setArdais_acct_close_date((localArdais_acct_close_date));
    if (h.containsKey("passwordPolicyCid"))
      setPasswordPolicyCid((localPasswordPolicyCid));
    if (h.containsKey("primaryLocation"))
      setPrimaryLocation((localPrimaryLocation));
    if (h.containsKey("linked_cases_only_yn"))
      setLinked_cases_only_yn((localLinked_cases_only_yn));
    if (h.containsKey("ardais_acct_active_date"))
      setArdais_acct_active_date((localArdais_acct_active_date));
    if (h.containsKey("ardais_acct_company_desc"))
      setArdais_acct_company_desc((localArdais_acct_company_desc));
    if (h.containsKey("passwordLifeSpan"))
      setPasswordLifeSpan((localPasswordLifeSpan).intValue());
    if (h.containsKey("ardais_acct_deactivate_reason"))
      setArdais_acct_deactivate_reason((localArdais_acct_deactivate_reason));
    if (h.containsKey("donorRegistrationForm"))
      setDonorRegistrationForm((localDonorRegistrationForm));
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.es.beans.ArdaisaccountKey ejbCreate(java.lang.String ardais_acct_key)
    throws javax.ejb.CreateException {
    _initLinks();
    this.ardais_acct_key = ardais_acct_key;
    return null;
  }
  /**
   * ejbPostCreate
   */
  public void ejbPostCreate(java.lang.String ardais_acct_key) throws javax.ejb.CreateException {
  }
  /**
   * Get accessor for persistent attribute: donorRegistrationForm
   */
  public java.lang.String getDonorRegistrationForm() {
    return donorRegistrationForm;
  }
  /**
   * Set accessor for persistent attribute: donorRegistrationForm
   */
  public void setDonorRegistrationForm(java.lang.String newDonorRegistrationForm) {
    donorRegistrationForm = newDonorRegistrationForm;
  }
}