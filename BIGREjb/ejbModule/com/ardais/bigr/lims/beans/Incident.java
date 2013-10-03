package com.ardais.bigr.lims.beans;
/**
 * Remote interface for Enterprise Bean: Incident
 */
public interface Incident extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {









  /**
   * Get accessor for persistent attribute: createUser
   */
  public java.lang.String getCreateUser() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: createUser
   */
  public void setCreateUser(java.lang.String newCreateUser) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: createDate
   */
  public java.sql.Timestamp getCreateDate() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: createDate
   */
  public void setCreateDate(java.sql.Timestamp newCreateDate) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: consentId
   */
  public java.lang.String getConsentId() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: consentId
   */
  public void setConsentId(java.lang.String newConsentId) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: action
   */
  public java.lang.String getAction() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: action
   */
  public void setAction(java.lang.String newAction) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: reason
   */
  public java.lang.String getReason() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: reason
   */
  public void setReason(java.lang.String newReason) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: slideId
   */
  public java.lang.String getSlideId() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: slideId
   */
  public void setSlideId(java.lang.String newSlideId) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: pathologist
   */
  public java.lang.String getPathologist() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: pathologist
   */
  public void setPathologist(java.lang.String newPathologist) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: rePVRequestorCode
   */
  public java.lang.String getRePVRequestorCode() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: rePVRequestorCode
   */
  public void setRePVRequestorCode(java.lang.String newRePVRequestorCode)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: actionOther
   */
  public java.lang.String getActionOther() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: actionOther
   */
  public void setActionOther(java.lang.String newActionOther) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: comments
   */
  public java.lang.String getComments() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: comments
   */
  public void setComments(java.lang.String newComments) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: sampleId
   */
  public java.lang.String getSampleId() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: sampleId
   */
  public void setSampleId(java.lang.String newSampleId) throws java.rmi.RemoteException;
}
