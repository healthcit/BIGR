package com.ardais.bigr.userprofile;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;

/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface UserProf extends javax.ejb.EJBObject {

    /**
     * Return true if the specified hashed password is the correct password
     * for the specified user.
     * 
     * @param userid the user id
     * @param account the user account
     * @param hashedPassword the hashed password
     * 
     * @return true if the password is correct
     */
    boolean validateUser(String userid, String account, String password)
        throws RemoteException;

    /**
     * Get the user's privileges
     * 
     * @param userid the user id
     * @param account the user account
     * 
     * @return a set of the privileges, each privilege in the set is a
     *     string that appears in the ARD_OBJECT.OBJECT_ID column.
     */
    Set getPrivileges(String userid, String account)
        throws RemoteException;
    /**
     * Get the logical repositories to which the user has visiblity.
     * 
     * @param userid the user id
     * 
     * @return a set of the visible logical repositories.
     */
    Set getLogicalRepositories(String userid) 
      throws RemoteException;
      
    /**
     * Get the left hand menu urls defined for the account to which the user belongs.
     * 
     * @param accountId the account id
     * 
     * @return a List of the left-hand menu URLs defined for this account.
     */
    public List getMenuUrls(String accountId) 
      throws RemoteException;

    /**
     * Get the user profile vector.
     * 
     * @param userid the user id
     * @param account the user account
     * 
     * @return the list of profile features
     */
    Vector getProfile(String userid, String account)
        throws RemoteException;
    
    /**
     * @param secInfo   the user information
     * @param vp   the view information
     * @return  the profile for the user and view.
     */
    public ViewProfile getViewProfile(SecurityInfo secInfo, ViewParams vp)
      throws RemoteException ;
      
    /**
     * @param vp   the view to get the default for
     * @return   the default columns for that view, ignoring user customization
     */
    public ViewProfile getDefaultViewProfile(SecurityInfo secInfo, ViewParams vp) 
      throws RemoteException;
      
    /**
     * @param secInfo   the user information
     * @param vp   the view information
     * @param cols  the profile for the user and view.
     */
    public void setViewProfile(SecurityInfo secInfo, ViewParams vp, ViewProfile viewProfile)
      throws RemoteException ;
          
}
