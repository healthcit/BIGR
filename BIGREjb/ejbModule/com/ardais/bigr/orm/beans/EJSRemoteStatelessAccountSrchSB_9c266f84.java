package com.ardais.bigr.orm.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessAccountSrchSB_9c266f84
 */
public class EJSRemoteStatelessAccountSrchSB_9c266f84 extends EJSWrapper implements AccountSrchSB {
	/**
	 * EJSRemoteStatelessAccountSrchSB_9c266f84
	 */
	public EJSRemoteStatelessAccountSrchSB_9c266f84() throws java.rmi.RemoteException {
		super();	}
	/**
	 * getNewAddress
	 */
	public int getNewAddress(java.lang.String accountId, java.util.Vector addressVec) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		int _EJS_result = 0;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = accountId;
				_jacc_parms[1] = addressVec;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getNewAddress(accountId, addressVec);
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
	 * setConsentText
	 */
	public int setConsentText(java.lang.String irbProtocolID, java.lang.String consentID, java.lang.String consentText, java.lang.String userID) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		int _EJS_result = 0;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[4];
				_jacc_parms[0] = irbProtocolID;
				_jacc_parms[1] = consentID;
				_jacc_parms[2] = consentText;
				_jacc_parms[3] = userID;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.setConsentText(irbProtocolID, consentID, consentText, userID);
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
	 * getConsentText
	 */
	public java.lang.String getConsentText(java.lang.String consentVersionId) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.lang.String _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = consentVersionId;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getConsentText(consentVersionId);
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
	 * getConsentTextbyConsentID
	 */
	public java.lang.String getConsentTextbyConsentID(java.lang.String consentId) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.lang.String _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = consentId;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getConsentTextbyConsentID(consentId);
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
	 * getInventoryGroupsAssignedToAccount
	 */
	public java.util.List getInventoryGroupsAssignedToAccount(java.lang.String accountId) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = accountId;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getInventoryGroupsAssignedToAccount(accountId);
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
	 * getPrivilegesAssignedToAccount
	 */
	public java.util.List getPrivilegesAssignedToAccount(java.lang.String accountId) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = accountId;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPrivilegesAssignedToAccount(accountId);
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
	 * getIrbConsentVer
	 */
	public java.util.Vector getIrbConsentVer(java.lang.String accountID) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Vector _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = accountID;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getIrbConsentVer(accountID);
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
	 * addConsentVertoIrb
	 */
	public void addConsentVertoIrb(java.lang.String irbProtocolId, java.lang.String consentVer, java.lang.String expirationDate) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = irbProtocolId;
				_jacc_parms[1] = consentVer;
				_jacc_parms[2] = expirationDate;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 7, _EJS_s, _jacc_parms);
			beanRef.addConsentVertoIrb(irbProtocolId, consentVer, expirationDate);
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
		return ;
	}
	/**
	 * addIRBandConsentVer
	 */
	public void addIRBandConsentVer(java.lang.String accountID, java.lang.String irbProtocol, java.lang.String policyId, java.lang.String consentVersion, java.lang.String expirationDate) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[5];
				_jacc_parms[0] = accountID;
				_jacc_parms[1] = irbProtocol;
				_jacc_parms[2] = policyId;
				_jacc_parms[3] = consentVersion;
				_jacc_parms[4] = expirationDate;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 8, _EJS_s, _jacc_parms);
			beanRef.addIRBandConsentVer(accountID, irbProtocol, policyId, consentVersion, expirationDate);
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
	 * saveActiveConsentVer
	 */
	public void saveActiveConsentVer(java.lang.String accountID, java.lang.String[] consentVerList, java.lang.String updatedBy) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = accountID;
				_jacc_parms[1] = consentVerList;
				_jacc_parms[2] = updatedBy;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 9, _EJS_s, _jacc_parms);
			beanRef.saveActiveConsentVer(accountID, consentVerList, updatedBy);
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
	 * saveConsentExpirationDate
	 */
	public void saveConsentExpirationDate(java.lang.String accountID, java.lang.String[] consentVerList, java.lang.String[] expirationDates, java.lang.String updatedBy) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[4];
				_jacc_parms[0] = accountID;
				_jacc_parms[1] = consentVerList;
				_jacc_parms[2] = expirationDates;
				_jacc_parms[3] = updatedBy;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 10, _EJS_s, _jacc_parms);
			beanRef.saveConsentExpirationDate(accountID, consentVerList, expirationDates, updatedBy);
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
	/**
	 * setInventoryGroupsAssignedToAccount
	 */
	public void setInventoryGroupsAssignedToAccount(java.lang.String accountId, java.util.List inventoryGroupIds) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = accountId;
				_jacc_parms[1] = inventoryGroupIds;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 11, _EJS_s, _jacc_parms);
			beanRef.setInventoryGroupsAssignedToAccount(accountId, inventoryGroupIds);
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
				container.postInvoke(this, 11, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
	/**
	 * setPrivilegesAssignedToAccount
	 */
	public void setPrivilegesAssignedToAccount(java.lang.String accountId, java.util.List privilegeIds) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = accountId;
				_jacc_parms[1] = privilegeIds;
			}
	com.ardais.bigr.orm.beans.AccountSrchSBBean beanRef = (com.ardais.bigr.orm.beans.AccountSrchSBBean)container.preInvoke(this, 12, _EJS_s, _jacc_parms);
			beanRef.setPrivilegesAssignedToAccount(accountId, privilegeIds);
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
				container.postInvoke(this, 12, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
}
