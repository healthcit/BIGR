package com.ardais.bigr.iltds.beans;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface ListGenerator extends javax.ejb.EJBObject {

  /**
   * 
   * @return Vector
   * @param geoLoc String
   */
  Vector findAvailableBoxLocationAll(String geoLoc) throws java.rmi.RemoteException;
  /**
   * 
   * @return Vector
   * @param manifestID String
   */
  Vector findBoxesByManifestID(String manifestID) throws java.rmi.RemoteException;
  
  /**
   * Returns a list of IrbVersionData objects containing all active IRB versions for the
   * specified account.
   * 
   * @param accountId the account
   * @return the list of active IRB consent versions for the account
   */
  List findActiveConsentVersions(String accountId) throws java.rmi.RemoteException;
  
  /**
   * Returns a list of IrbVersionData objects containing all active IRB versions for the
   * specified account, with the option to filter out those based on a policy that has
   * either case release required and/or consent verify required = Y.
   * 
   * @param accountId the account
   * @return the list of active IRB consent versions for the account
   */
  public List findActiveConsentVersions(String accountId, boolean allowCaseReleaseRequired, 
                                        boolean allowConsentVerifyRequired) throws java.rmi.RemoteException;
  
  /**
   * Returns a list of logical repository objects containing all repositories.
   * Each item in the list is a {@link com.ardais.bigr.iltds.assistants.LogicalRepository} object.
   * The list is returned sorted by ascending full name (case insensitive).
   * 
   * @return the list of logical repositories
   */
  List getLogicalRepositories() throws java.rmi.RemoteException;
  
  /**
   * 
   * @return Vector
   * @param location String
   * @param room String
   * @param freezer String
   * @param drawer String
   * @param storageType String
   */
  Vector findNextLocationDropDown(
    String location,
    String room,
    String freezer,
    String drawer,
    String storageType)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   * @param category String
   */
  com.ardais.bigr.iltds.assistants.LegalValueSet getArdLookup(String category)
    throws java.rmi.RemoteException;
  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   */
  com.ardais.bigr.iltds.assistants.LegalValueSet getDonorLocations()
    throws java.rmi.RemoteException;
  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   */
  com.ardais.bigr.iltds.assistants.LegalValueSet getFinishedProductFormats()
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   */
  com.ardais.bigr.iltds.assistants.LegalValueSet getPtsClients() throws java.rmi.RemoteException;
  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   * @param account String
   */
  com.ardais.bigr.iltds.assistants.LegalValueSet getPtsOrders(String account)
    throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   * @param account String
   */
  com.ardais.bigr.iltds.assistants.LegalValueSet getPtsShoppingCarts(String account)
    throws java.rmi.RemoteException;
  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   * @param account String
   */
  com.ardais.bigr.iltds.assistants.LegalValueSet getPtsUsersByAccount(String account)
    throws java.rmi.RemoteException;
  /**
   * 
   * @return String
   * @param slideId String
   */
  String getSampleFromSlide(String slideId) throws java.rmi.RemoteException;

  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   */
  com.ardais.bigr.iltds.assistants.LegalValueSet getSpecimenTypes()
    throws java.rmi.RemoteException;
  /**
   * 
   * @return String
   * @param staffId String
   * @param accountId String
   */
  String getUserRealName(String staffId, String accountId) throws java.rmi.RemoteException;
  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   * @param type String
   */
  com.ardais.bigr.iltds.assistants.LegalValueSet getWorkOrderStatusesForType(String type)
    throws java.rmi.RemoteException;
  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSet
   */
  com.ardais.bigr.iltds.assistants.LegalValueSet getWorkOrderStatusTypeMapping()
    throws java.rmi.RemoteException;
  /**
   * 
   * @return com.ardais.bigr.iltds.assistants.LegalValueSetHierarchy
   */
  com.ardais.bigr.iltds.assistants.LegalValueSetHierarchy getWorkOrderTypesAndStatuses()
    throws java.rmi.RemoteException;

  /**
   * Return a vector of the active users for the specified account (only ACTIVE users are
   * returned).  The returned vector alternates pairs of values: the user's full name (first/last)
   * followed by their ARDAIS_STAFF_ID value from ILTDS_ARDAIS_STAFF.  The values returned are
   * sorted by increasing last name, and by increasing first name within a last name.
   * 
   * @return the vector of user information
   */
  Vector lookupArdaisStaffByAccountId(String accountID) throws java.rmi.RemoteException;

  /**
   * 
   * @return String
   * @param category String
   * @param code String
   */
  String lookupArdLookupDescription(String category, String code) throws java.rmi.RemoteException;
  /**
   * 
   * @return String
   * @param accountKey String
   */
  String lookupDIAccountName(String accountKey) throws java.rmi.RemoteException;

  /**
   * 
   * @return String
   * @param diseaseName String
   */
  String lookupDiseaseCode(String diseaseName) throws java.rmi.RemoteException;
  /**
   * 
   * @return String
   * @param diseaseCode String
   */
  String lookupDiseaseName(String diseaseCode) throws java.rmi.RemoteException;

  /**
   * 
   * @return boolean
   * @param trackingNumber String
   */
  boolean trackingNumberExists(String trackingNumber) throws java.rmi.RemoteException;
  /**
       * Returns all donor institution locations.
       * @return  A <code>LegalValueSet</code> containing all donor institution locations.
       */
  public Collection getDonorAccounts() throws java.rmi.RemoteException;

  /**
   * Find the manifest that have been created or shipped but not yet received and that are
   * visible to the specified user (the visible manifests are those created by a user with the
   * same account as the specified user).
   * 
   * @param userID The id of the user performing the transaction.
   * @return A vector of hashtables, see the implementation for details.
   */
  Vector findPrintableManifests(String userID) throws java.rmi.RemoteException;
}
