package com.ardais.bigr.orm.beans;

import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.javabeans.UserDto;

/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface OrmUserManagement extends javax.ejb.EJBObject {

  public void createNewUser(UserDto userData, String createdBy) throws java.rmi.RemoteException;
  
    Map getAllPrivileges() throws java.rmi.RemoteException;
    
  /**
   * Method to find a list of privileges
   * @param privilegeIds List - a list of privilege ids to be retrieved
   * @return a List of PrivilegeDtos.
   */
  public List getPrivilegesById(List privilegeIds) throws java.rmi.RemoteException;

    List getPrivilegesAssignedToUser(String userID, String accountID)
        throws java.rmi.RemoteException;

    boolean isUserExisting(String userID) throws java.rmi.RemoteException;

    List getAllUserIds() throws ApiException, java.rmi.RemoteException;

    List findUsersWithPrivilege(String privilege) throws ApiException, java.rmi.RemoteException;
  
  /**
   * Method to remove privileges from a user
   * @param userId String - the id of the user to whom the privileges are being revoked
   * @param accountId String - the id of the account of the user to whom the privileges are being revoked
   * @param privilegeIds List - a list of privilege ids being revoked
   */
  public void removePrivilegesFromUser(String userId, String accountId, 
                                       List privilegeIds) throws java.rmi.RemoteException;

  /**
   * Method to grant privileges to a user
   * @param userId String - the id of the user to whom the privileges are being granted
   * @param accountId String - the id of the account of the user to whom the privileges are being granted
   * @param privilegeIds List - a list of privilege ids to be granted to the user
   * @param updateUserId String - the id of the user granting the privileges
   */
  public void addPrivilegesToUser(String userId, String accountId, 
                                  List privilegeIds, String updateUserId) throws java.rmi.RemoteException;

    String verifyAnswer(
        String userID,
        String accountID,
        String verificationAnswer,
        String password)
        throws java.rmi.RemoteException;

    String getVerificationQuestion(String userID, String accountID)
        throws java.rmi.RemoteException;
}
