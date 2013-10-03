package com.ardais.bigr.lims.beans;
import javax.ejb.*;
import javax.rmi.*;
import com.ibm.ivj.ejb.runtime.*;
/**
 * IncidentAccessBean
 * @generated
 */
public class IncidentAccessBean
  extends AbstractEntityAccessBean
  implements com.ardais.bigr.lims.beans.IncidentAccessBeanData {
  /**
   * @generated
   */
  private Incident __ejbRef;
  /**
   * @generated
   */
  private java.lang.String init_incidentId;
  /**
   * @generated
   */
  private java.lang.String init_createUser;
  /**
   * @generated
   */
  private java.sql.Timestamp init_createDate;
  /**
   * @generated
   */
  private java.lang.String init_sampleId;
  /**
   * @generated
   */
  private java.lang.String init_consentId;
  /**
   * @generated
   */
  private java.lang.String init_action;
  /**
   * @generated
   */
  private java.lang.String init_reason;
  /**
   * getRePVRequestorCode
   * @generated
   */
  public java.lang.String getRePVRequestorCode()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("rePVRequestorCode")));
  }
  /**
   * setRePVRequestorCode
   * @generated
   */
  public void setRePVRequestorCode(java.lang.String newValue) {
    __setCache("rePVRequestorCode", newValue);
  }
  /**
   * getAction
   * @generated
   */
  public java.lang.String getAction()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("action")));
  }
  /**
   * setAction
   * @generated
   */
  public void setAction(java.lang.String newValue) {
    __setCache("action", newValue);
  }
  /**
   * getReason
   * @generated
   */
  public java.lang.String getReason()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("reason")));
  }
  /**
   * setReason
   * @generated
   */
  public void setReason(java.lang.String newValue) {
    __setCache("reason", newValue);
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
   * getSampleId
   * @generated
   */
  public java.lang.String getSampleId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("sampleId")));
  }
  /**
   * setSampleId
   * @generated
   */
  public void setSampleId(java.lang.String newValue) {
    __setCache("sampleId", newValue);
  }
  /**
   * getPathologist
   * @generated
   */
  public java.lang.String getPathologist()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("pathologist")));
  }
  /**
   * setPathologist
   * @generated
   */
  public void setPathologist(java.lang.String newValue) {
    __setCache("pathologist", newValue);
  }
  /**
   * getSlideId
   * @generated
   */
  public java.lang.String getSlideId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("slideId")));
  }
  /**
   * setSlideId
   * @generated
   */
  public void setSlideId(java.lang.String newValue) {
    __setCache("slideId", newValue);
  }
  /**
   * getConsentId
   * @generated
   */
  public java.lang.String getConsentId()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("consentId")));
  }
  /**
   * setConsentId
   * @generated
   */
  public void setConsentId(java.lang.String newValue) {
    __setCache("consentId", newValue);
  }
  /**
   * getCreateDate
   * @generated
   */
  public java.sql.Timestamp getCreateDate()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.sql.Timestamp) __getCache("createDate")));
  }
  /**
   * setCreateDate
   * @generated
   */
  public void setCreateDate(java.sql.Timestamp newValue) {
    __setCache("createDate", newValue);
  }
  /**
   * getActionOther
   * @generated
   */
  public java.lang.String getActionOther()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("actionOther")));
  }
  /**
   * setActionOther
   * @generated
   */
  public void setActionOther(java.lang.String newValue) {
    __setCache("actionOther", newValue);
  }
  /**
   * getCreateUser
   * @generated
   */
  public java.lang.String getCreateUser()
    throws java.rmi.RemoteException,
    javax.ejb.CreateException,
    javax.ejb.FinderException,
    javax.naming.NamingException {
    return (((java.lang.String) __getCache("createUser")));
  }
  /**
   * setCreateUser
   * @generated
   */
  public void setCreateUser(java.lang.String newValue) {
    __setCache("createUser", newValue);
  }
  /**
   * setInit_incidentId
   * @generated
   */
  public void setInit_incidentId(java.lang.String newValue) {
    this.init_incidentId = (newValue);
  }
  /**
   * setInit_createUser
   * @generated
   */
  public void setInit_createUser(java.lang.String newValue) {
    this.init_createUser = (newValue);
  }
  /**
   * setInit_createDate
   * @generated
   */
  public void setInit_createDate(java.sql.Timestamp newValue) {
    this.init_createDate = (newValue);
  }
  /**
   * setInit_sampleId
   * @generated
   */
  public void setInit_sampleId(java.lang.String newValue) {
    this.init_sampleId = (newValue);
  }
  /**
   * setInit_consentId
   * @generated
   */
  public void setInit_consentId(java.lang.String newValue) {
    this.init_consentId = (newValue);
  }
  /**
   * setInit_action
   * @generated
   */
  public void setInit_action(java.lang.String newValue) {
    this.init_action = (newValue);
  }
  /**
   * setInit_reason
   * @generated
   */
  public void setInit_reason(java.lang.String newValue) {
    this.init_reason = (newValue);
  }
  /**
   * IncidentAccessBean
   * @generated
   */
  public IncidentAccessBean() {
    super();
  }
  /**
   * IncidentAccessBean
   * @generated
   */
  public IncidentAccessBean(javax.ejb.EJBObject o) throws java.rmi.RemoteException {
    super(o);
  }
  /**
   * defaultJNDIName
   * @generated
   */
  public String defaultJNDIName() {
    return "ejb/com/ardais/bigr/lims/beans/IncidentHome";
  }
  /**
   * ejbHome
   * @generated
   */
  private com.ardais.bigr.lims.beans.IncidentHome ejbHome()
    throws java.rmi.RemoteException,
    javax.naming.NamingException {
    return (com.ardais.bigr.lims.beans.IncidentHome) PortableRemoteObject.narrow(
      getHome(),
      com.ardais.bigr.lims.beans.IncidentHome.class);
  }
  /**
   * ejbRef
   * @generated
   */
  private com.ardais.bigr.lims.beans.Incident ejbRef() throws java.rmi.RemoteException {
    if (ejbRef == null)
      return null;
    if (__ejbRef == null)
      __ejbRef = (com.ardais.bigr.lims.beans.Incident) PortableRemoteObject.narrow(
        ejbRef,
        com.ardais.bigr.lims.beans.Incident.class);

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
      init_incidentId,
      init_createUser,
      init_createDate,
      init_sampleId,
      init_consentId,
      init_action,
      init_reason);
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
      java.lang.String pKey = (java.lang.String) this.__getKey();
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
   * IncidentAccessBean
   * @generated
   */
  public IncidentAccessBean(java.lang.String primaryKey) throws javax.naming.NamingException,
    javax.ejb.FinderException, javax.ejb.CreateException, java.rmi.RemoteException {
    ejbRef = ejbHome().findByPrimaryKey(primaryKey);
  }
}
