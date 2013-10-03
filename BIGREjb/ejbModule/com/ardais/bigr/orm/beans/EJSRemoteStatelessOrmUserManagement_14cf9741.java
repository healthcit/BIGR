package com.ardais.bigr.orm.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessOrmUserManagement_14cf9741
 */
public class EJSRemoteStatelessOrmUserManagement_14cf9741 extends EJSWrapper implements OrmUserManagement {
	/**
	 * EJSRemoteStatelessOrmUserManagement_14cf9741
	 */
	public EJSRemoteStatelessOrmUserManagement_14cf9741() throws java.rmi.RemoteException {
		super();	}
	/**
	 * isUserExisting
	 */
	public boolean isUserExisting(java.lang.String userID) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		boolean _EJS_result = false;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = userID;
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.isUserExisting(userID);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 0, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getVerificationQuestion
	 */
	public java.lang.String getVerificationQuestion(java.lang.String userID, java.lang.String accountID) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.lang.String _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = userID;
				_jacc_parms[1] = accountID;
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getVerificationQuestion(userID, accountID);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 1, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * verifyAnswer
	 */
	public java.lang.String verifyAnswer(java.lang.String userID, java.lang.String accountID, java.lang.String verificationAnswer, java.lang.String password) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.lang.String _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[4];
				_jacc_parms[0] = userID;
				_jacc_parms[1] = accountID;
				_jacc_parms[2] = verificationAnswer;
				_jacc_parms[3] = password;
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.verifyAnswer(userID, accountID, verificationAnswer, password);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 2, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * findUsersWithPrivilege
	 */
	public java.util.List findUsersWithPrivilege(java.lang.String privilege) throws com.ardais.bigr.api.ApiException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = privilege;
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findUsersWithPrivilege(privilege);
		}
		catch (com.ardais.bigr.api.ApiException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 3, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getAllUserIds
	 */
	public java.util.List getAllUserIds() throws com.ardais.bigr.api.ApiException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getAllUserIds();
		}
		catch (com.ardais.bigr.api.ApiException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 4, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getPrivilegesAssignedToUser
	 */
	public java.util.List getPrivilegesAssignedToUser(java.lang.String userID, java.lang.String accountID) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = userID;
				_jacc_parms[1] = accountID;
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPrivilegesAssignedToUser(userID, accountID);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 5, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getPrivilegesById
	 */
	public java.util.List getPrivilegesById(java.util.List privilegeIds) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = privilegeIds;
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPrivilegesById(privilegeIds);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 6, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getAllPrivileges
	 */
	public java.util.Map getAllPrivileges() throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Map _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 7, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getAllPrivileges();
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 7, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * addPrivilegesToUser
	 */
	public void addPrivilegesToUser(java.lang.String userId, java.lang.String accountId, java.util.List privilegeIds, java.lang.String updateUserId) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[4];
				_jacc_parms[0] = userId;
				_jacc_parms[1] = accountId;
				_jacc_parms[2] = privilegeIds;
				_jacc_parms[3] = updateUserId;
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 8, _EJS_s, _jacc_parms);
			beanRef.addPrivilegesToUser(userId, accountId, privilegeIds, updateUserId);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 8, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
	/**
	 * createNewUser
	 */
	public void createNewUser(com.ardais.bigr.javabeans.UserDto userData, java.lang.String createdBy) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = userData;
				_jacc_parms[1] = createdBy;
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 9, _EJS_s, _jacc_parms);
			beanRef.createNewUser(userData, createdBy);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 9, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
	/**
	 * removePrivilegesFromUser
	 */
	public void removePrivilegesFromUser(java.lang.String userId, java.lang.String accountId, java.util.List privilegeIds) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = userId;
				_jacc_parms[1] = accountId;
				_jacc_parms[2] = privilegeIds;
			}
	com.ardais.bigr.orm.beans.OrmUserManagementBean beanRef = (com.ardais.bigr.orm.beans.OrmUserManagementBean)container.preInvoke(this, 10, _EJS_s, _jacc_parms);
			beanRef.removePrivilegesFromUser(userId, accountId, privilegeIds);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 10, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
}
