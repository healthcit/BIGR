package com.ardais.bigr.lims.beans;
/**
 * Bean implementation class for Enterprise Bean: Incident
 */
public class IncidentBean implements javax.ejb.EntityBean {
  
  public static final String DEFAULT_incidentId  = null;
  public static final String DEFAULT_createUser  = null;
  public static final java.sql.Timestamp DEFAULT_createDate  = null;
  public static final String DEFAULT_sampleId  = null;  
  public static final String DEFAULT_consentId  = null; 
  public static final String DEFAULT_action  = null;  
  public static final String DEFAULT_reason  = null;  
  public static final String DEFAULT_slideId  = null;
  public static final String DEFAULT_pathologist  = null;
  public static final String DEFAULT_rePVRequestorCode  = null;
  public static final String DEFAULT_actionOther  = null;
  public static final String DEFAULT_comments  = null;

  private javax.ejb.EntityContext myEntityCtx;
  /**
   * Implementation field for persistent attribute: incidentId
   */
  public java.lang.String incidentId;
  /**
   * Implementation field for persistent attribute: createUser
   */
  public java.lang.String createUser;
  /**
   * Implementation field for persistent attribute: createDate
   */
  public java.sql.Timestamp createDate;
  /**
   * Implementation field for persistent attribute: consentId
   */
  public java.lang.String consentId;
  /**
   * Implementation field for persistent attribute: action
   */
  public java.lang.String action;
  /**
   * Implementation field for persistent attribute: reason
   */
  public java.lang.String reason;
  /**
   * Implementation field for persistent attribute: slideId
   */
  public java.lang.String slideId;
  /**
   * Implementation field for persistent attribute: pathologist
   */
  public java.lang.String pathologist;
  /**
   * Implementation field for persistent attribute: rePVRequestorCode
   */
  public java.lang.String rePVRequestorCode;
  /**
   * Implementation field for persistent attribute: actionOther
   */
  public java.lang.String actionOther;
  /**
   * Implementation field for persistent attribute: comments
   */
  public java.lang.String comments;
  /**
   * Implementation field for persistent attribute: sampleId
   */
  public java.lang.String sampleId;
  
  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(
      com.ardais.bigr.lims.beans.IncidentBean.class);
      
  /**
   * getEntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return myEntityCtx;
  }
  /**
   * setEntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) {
    myEntityCtx = ctx;
  }
  /**
   * unsetEntityContext
   */
  public void unsetEntityContext() {
    myEntityCtx = null;
  }
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public java.lang.String ejbCreate(java.lang.String incidentId, 
                                     java.lang.String createUser,
                                     java.sql.Timestamp createDate,
                                     java.lang.String sampleId,
                                     java.lang.String consentId,
                                     java.lang.String action,
                                     java.lang.String reason) throws javax.ejb.CreateException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    this.incidentId = incidentId;
    this.createUser = createUser;
    this.createDate = createDate;
    this.sampleId = sampleId;
    this.consentId = consentId;
    this.action = action;
    this.reason = reason;
    return null;
  }
  /**
   * ejbPostCreate
   */
  public void ejbPostCreate(java.lang.String incidentId, 
                             java.lang.String createUser,
                             java.sql.Timestamp createDate,
                             java.lang.String sampleId,
                             java.lang.String consentId,
                             java.lang.String action,
                             java.lang.String reason) throws javax.ejb.CreateException {
  }
  /**
   * ejbActivate
   */
  public void ejbActivate() {
    _initLinks();
  }
  /**
   * ejbLoad
   */
  public void ejbLoad() {
    _initLinks();
  }
  /**
   * ejbPassivate
   */
  public void ejbPassivate() {
  }
  /**
   * ejbRemove
   */
  public void ejbRemove() throws javax.ejb.RemoveException {
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
  public void ejbStore() {
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected void _initLinks() {
  }
  /**
   * This method was generated for supporting the associations.
   */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    return links;
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
   * Get accessor for persistent attribute: createUser
   */
  public java.lang.String getCreateUser() {
    return createUser;
  }
  /**
   * Set accessor for persistent attribute: createUser
   */
  public void setCreateUser(java.lang.String newCreateUser) {
    createUser = newCreateUser;
  }
  /**
   * Get accessor for persistent attribute: createDate
   */
  public java.sql.Timestamp getCreateDate() {
    return createDate;
  }
  /**
   * Set accessor for persistent attribute: createDate
   */
  public void setCreateDate(java.sql.Timestamp newCreateDate) {
    createDate = newCreateDate;
  }
  /**
   * Get accessor for persistent attribute: consentId
   */
  public java.lang.String getConsentId() {
    return consentId;
  }
  /**
   * Set accessor for persistent attribute: consentId
   */
  public void setConsentId(java.lang.String newConsentId) {
    consentId = newConsentId;
  }
  /**
   * Get accessor for persistent attribute: action
   */
  public java.lang.String getAction() {
    return action;
  }
  /**
   * Set accessor for persistent attribute: action
   */
  public void setAction(java.lang.String newAction) {
    action = newAction;
  }
  /**
   * Get accessor for persistent attribute: reason
   */
  public java.lang.String getReason() {
    return reason;
  }
  /**
   * Set accessor for persistent attribute: reason
   */
  public void setReason(java.lang.String newReason) {
    reason = newReason;
  }
  /**
   * Get accessor for persistent attribute: slideId
   */
  public java.lang.String getSlideId() {
    return slideId;
  }
  /**
   * Set accessor for persistent attribute: slideId
   */
  public void setSlideId(java.lang.String newSlideId) {
    slideId = newSlideId;
  }
  /**
   * Get accessor for persistent attribute: pathologist
   */
  public java.lang.String getPathologist() {
    return pathologist;
  }
  /**
   * Set accessor for persistent attribute: pathologist
   */
  public void setPathologist(java.lang.String newPathologist) {
    pathologist = newPathologist;
  }
  /**
   * Get accessor for persistent attribute: rePVRequestorCode
   */
  public java.lang.String getRePVRequestorCode() {
    return rePVRequestorCode;
  }
  /**
   * Set accessor for persistent attribute: rePVRequestorCode
   */
  public void setRePVRequestorCode(java.lang.String newRePVRequestorCode) {
    rePVRequestorCode = newRePVRequestorCode;
  }
  /**
   * Get accessor for persistent attribute: actionOther
   */
  public java.lang.String getActionOther() {
    return actionOther;
  }
  /**
   * Set accessor for persistent attribute: actionOther
   */
  public void setActionOther(java.lang.String newActionOther) {
    actionOther = newActionOther;
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
   * Get accessor for persistent attribute: sampleId
   */
  public java.lang.String getSampleId() {
    return sampleId;
  }
  /**
   * Set accessor for persistent attribute: sampleId
   */
  public void setSampleId(java.lang.String newSampleId) {
    sampleId = newSampleId;
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("rePVRequestorCode", getRePVRequestorCode());
    h.put("action", getAction());
    h.put("reason", getReason());
    h.put("comments", getComments());
    h.put("sampleId", getSampleId());
    h.put("pathologist", getPathologist());
    h.put("slideId", getSlideId());
    h.put("consentId", getConsentId());
    h.put("createDate", getCreateDate());
    h.put("actionOther", getActionOther());
    h.put("createUser", getCreateUser());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localRePVRequestorCode = (java.lang.String) h.get("rePVRequestorCode");
    java.lang.String localAction = (java.lang.String) h.get("action");
    java.lang.String localReason = (java.lang.String) h.get("reason");
    java.lang.String localComments = (java.lang.String) h.get("comments");
    java.lang.String localSampleId = (java.lang.String) h.get("sampleId");
    java.lang.String localPathologist = (java.lang.String) h.get("pathologist");
    java.lang.String localSlideId = (java.lang.String) h.get("slideId");
    java.lang.String localConsentId = (java.lang.String) h.get("consentId");
    java.sql.Timestamp localCreateDate = (java.sql.Timestamp) h.get("createDate");
    java.lang.String localActionOther = (java.lang.String) h.get("actionOther");
    java.lang.String localCreateUser = (java.lang.String) h.get("createUser");

    if (h.containsKey("rePVRequestorCode"))
      setRePVRequestorCode((localRePVRequestorCode));
    if (h.containsKey("action"))
      setAction((localAction));
    if (h.containsKey("reason"))
      setReason((localReason));
    if (h.containsKey("comments"))
      setComments((localComments));
    if (h.containsKey("sampleId"))
      setSampleId((localSampleId));
    if (h.containsKey("pathologist"))
      setPathologist((localPathologist));
    if (h.containsKey("slideId"))
      setSlideId((localSlideId));
    if (h.containsKey("consentId"))
      setConsentId((localConsentId));
    if (h.containsKey("createDate"))
      setCreateDate((localCreateDate));
    if (h.containsKey("actionOther"))
      setActionOther((localActionOther));
    if (h.containsKey("createUser"))
      setCreateUser((localCreateUser));
  }
}
