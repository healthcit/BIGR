package com.ardais.bigr.iltds.beans;

import java.rmi.RemoteException;

import com.ardais.bigr.pdc.javabeans.AttachmentData;
import com.ardais.bigr.security.SecurityInfo;

/**
 * Session bean for operations involving Consent objects.
 */
public interface ConsentOperation extends javax.ejb.EJBObject {

  void pullFromESHold(String sampleID) throws java.rmi.RemoteException;

  void revokeConsent(String consent_id, String ardais_id, String ardaisstaff_id, String reason)
    throws java.rmi.RemoteException;

  String getCaseOrigin(String caseID) throws java.rmi.RemoteException;
  
  /**
   * 
   * @param attachData
   * @param securityInfo
   * @return
   */
 AttachmentData insertCNTAttachment(AttachmentData attachData, SecurityInfo securityInfo)throws RemoteException; 
}
