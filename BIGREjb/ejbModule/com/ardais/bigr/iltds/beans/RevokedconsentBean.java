package com.ardais.bigr.iltds.beans;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
/**
 * This is an Entity Bean class with CMP fields
 */
public class RevokedconsentBean implements EntityBean {
  public static final String DEFAULT_consent_id = null;
  public static final String DEFAULT_ardais_id = null;
  public static final String DEFAULT_revoked_by_staff_id = null;
  public static final java.sql.Timestamp DEFAULT_revoked_timestamp = null;
  public static final String DEFAULT_revoked_reason = null;

  public String consent_id;
  public String ardais_id;
  public String revoked_by_staff_id;
  public java.sql.Timestamp revoked_timestamp;
  public String revoked_reason;

  private javax.ejb.EntityContext entityContext = null;
  final static long serialVersionUID = 3206093459760846163L;

  private static final com.ardais.bigr.api.CMPDefaultValues _fieldDefaultValues =
    new com.ardais.bigr.api.CMPDefaultValues(com.ardais.bigr.iltds.beans.RevokedconsentBean.class);
  /**
   * This method was generated for supporting the associations.
   * @return java.util.Vector
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected java.util.Vector _getLinks() {
    java.util.Vector links = new java.util.Vector();
    return links;
  }
  /**
   * This method was generated for supporting the associations.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected void _initLinks() {
  }
  /**
   * This method was generated for supporting the associations.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  protected void _removeLinks() throws java.rmi.RemoteException, javax.ejb.RemoveException {
    java.util.Enumeration links = _getLinks().elements();
    while (links.hasMoreElements()) {
      try {
        ((com.ibm.ivj.ejb.associations.interfaces.Link) (links.nextElement())).remove();
      }
      catch (javax.ejb.FinderException e) {
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
   * ejbCreate method for a CMP entity bean
   * @param argConsent_id java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public RevokedconsentKey ejbCreate(java.lang.String argConsent_id)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    consent_id = argConsent_id;

    return null;
  }
  /**
   * ejbCreate method for a CMP entity bean
   * @param argConsent_id java.lang.String
   * @param argArdais_id java.lang.String
   * @exception javax.ejb.CreateException The exception description.
   */
  public RevokedconsentKey ejbCreate(java.lang.String argConsent_id, java.lang.String argArdais_id)
    throws javax.ejb.CreateException, EJBException {
    _fieldDefaultValues.assignTo(this);
    _initLinks();
    // All CMP fields should be initialized here.
    consent_id = argConsent_id;
    ardais_id = argArdais_id;
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
   * ejbPostCreate method for a CMP entity bean
   * @param argConsent_id java.lang.String
   */
  public void ejbPostCreate(java.lang.String argConsent_id) throws EJBException {
  }
  /**
   * ejbPostCreate method for a CMP entity bean
   * @param argConsent_id java.lang.String
   * @param argArdais_id java.lang.String
   */
  public void ejbPostCreate(java.lang.String argConsent_id, java.lang.String argArdais_id)
    throws EJBException {
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
   * Insert the method's description here.
   * Creation date: (7/2/01 6:14:18 PM)
   * @return java.lang.String
   */
  public java.lang.String getArdais_id() {
    return ardais_id;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/2/01 6:14:18 PM)
   * @return java.lang.String
   */
  public java.lang.String getConsent_id() {
    return consent_id;
  }
  /**
   * getEntityContext method comment
   * @return javax.ejb.EntityContext
   */
  public javax.ejb.EntityContext getEntityContext() {
    return entityContext;
  }
  /**
   * Getter method for revoked_by_staff_id
   * @return java.lang.String
   */
  public java.lang.String getRevoked_by_staff_id() {
    return revoked_by_staff_id;
  }
  /**
   * Getter method for revoked_reason
   * @return java.lang.String
   */
  public java.lang.String getRevoked_reason() {
    return revoked_reason;
  }
  /**
   * Getter method for revoked_timestamp
   * @return java.sql.Timestamp
   */
  public java.sql.Timestamp getRevoked_timestamp() {
    return revoked_timestamp;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/2/01 6:14:18 PM)
   * @param newArdais_id java.lang.String
   */
  public void setArdais_id(java.lang.String newArdais_id) {
    ardais_id = newArdais_id;
  }
  /**
   * Insert the method's description here.
   * Creation date: (7/2/01 6:14:18 PM)
   * @param newConsent_id java.lang.String
   */
  public void setConsent_id(java.lang.String newConsent_id) {
    consent_id = newConsent_id;
  }
  /**
   * setEntityContext method comment
   * @param ctx javax.ejb.EntityContext
   */
  public void setEntityContext(javax.ejb.EntityContext ctx) throws java.rmi.RemoteException {
    entityContext = ctx;
  }
  /**
   * Setter method for revoked_by_staff_id
   * @param newValue java.lang.String
   */
  public void setRevoked_by_staff_id(java.lang.String newValue) {
    this.revoked_by_staff_id = newValue;
  }
  /**
   * Setter method for revoked_reason
   * @param newValue java.lang.String
   */
  public void setRevoked_reason(java.lang.String newValue) {
    this.revoked_reason = newValue;
  }
  /**
   * Setter method for revoked_timestamp
   * @param newValue java.sql.Timestamp
   */
  public void setRevoked_timestamp(java.sql.Timestamp newValue) {
    this.revoked_timestamp = newValue;
  }
  /**
   * unsetEntityContext method comment
   */
  public void unsetEntityContext() throws java.rmi.RemoteException {
    entityContext = null;
  }
  /**
   * _copyFromEJB
   */
  public java.util.Hashtable _copyFromEJB() {
    com.ibm.ivj.ejb.runtime.AccessBeanHashtable h = new com.ibm.ivj.ejb.runtime.AccessBeanHashtable();

    h.put("revoked_reason", getRevoked_reason());
    h.put("revoked_by_staff_id", getRevoked_by_staff_id());
    h.put("revoked_timestamp", getRevoked_timestamp());
    h.put("ardais_id", getArdais_id());
    h.put("consent_id", getConsent_id());
    h.put("__Key", getEntityContext().getPrimaryKey());

    return h;
  }
  /**
   * _copyToEJB
   */
  public void _copyToEJB(java.util.Hashtable h) {
    java.lang.String localRevoked_reason = (java.lang.String) h.get("revoked_reason");
    java.lang.String localRevoked_by_staff_id = (java.lang.String) h.get("revoked_by_staff_id");
    java.sql.Timestamp localRevoked_timestamp = (java.sql.Timestamp) h.get("revoked_timestamp");
    java.lang.String localArdais_id = (java.lang.String) h.get("ardais_id");
    java.lang.String localConsent_id = (java.lang.String) h.get("consent_id");

    if (h.containsKey("revoked_reason"))
      setRevoked_reason((localRevoked_reason));
    if (h.containsKey("revoked_by_staff_id"))
      setRevoked_by_staff_id((localRevoked_by_staff_id));
    if (h.containsKey("revoked_timestamp"))
      setRevoked_timestamp((localRevoked_timestamp));
    if (h.containsKey("ardais_id"))
      setArdais_id((localArdais_id));
    if (h.containsKey("consent_id"))
      setConsent_id((localConsent_id));
  }
}
