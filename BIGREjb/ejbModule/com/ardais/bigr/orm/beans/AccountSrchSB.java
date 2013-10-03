package com.ardais.bigr.orm.beans;

import java.util.List;

public interface AccountSrchSB extends javax.ejb.EJBObject {

  void addConsentVertoIrb(String irbProtocolId, String consentVer, String expirationDate) throws java.rmi.RemoteException;

  void addIRBandConsentVer(
    String accountID,
    String irbProtocol,
    String policyId,
    String consentVersion,
    String expirationDate)
    throws java.rmi.RemoteException;

  String getConsentText(String consentVersionId) throws java.rmi.RemoteException;

  String getConsentTextbyConsentID(String consentId) throws java.rmi.RemoteException;

  java.util.Vector getIrbConsentVer(String accountID) throws java.rmi.RemoteException;

  int getNewAddress(String accountId, java.util.Vector addressVec) throws java.rmi.RemoteException;

  void saveActiveConsentVer(String accountID, String[] consentVerList, String updatedBy)
    throws java.rmi.RemoteException;

  void saveConsentExpirationDate(
    String accountID,
    String[] consentVerList,
    String[] expirationDates,
    String updatedBy)
    throws java.rmi.RemoteException;


  int setConsentText(String irbProtocolID, String consentID, String consentText, String userID)
    throws java.rmi.RemoteException;
  
  public List getPrivilegesAssignedToAccount(String accountId) throws java.rmi.RemoteException;
  
  public void setPrivilegesAssignedToAccount(String accountId, List privilegeIds) throws java.rmi.RemoteException;
  
  public List getInventoryGroupsAssignedToAccount(String accountId) throws java.rmi.RemoteException;

  public void setInventoryGroupsAssignedToAccount(String accountId, List inventoryGroupIds) throws java.rmi.RemoteException;
}
