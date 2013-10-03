package com.ardais.bigr.lims.beans;
/**
 * Home interface for Enterprise Bean: Incident
 */
public interface IncidentHome extends javax.ejb.EJBHome {

  /**
   * Creates an instance for Entity Bean: Incident
   */
  public com.ardais.bigr.lims.beans.Incident create(java.lang.String incidentId, 
                                                     java.lang.String createUser,
                                                     java.sql.Timestamp createDate,
                                                     java.lang.String sampleId,
                                                     java.lang.String consentId,
                                                     java.lang.String action,
                                                     java.lang.String reason)
    throws javax.ejb.CreateException, java.rmi.RemoteException;
  /**
   * Finds an instance using a key for Entity Bean: Incident
   */
  public com.ardais.bigr.lims.beans.Incident findByPrimaryKey(java.lang.String primaryKey)
    throws javax.ejb.FinderException, java.rmi.RemoteException;
}
