package com.ardais.bigr.userprofile;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessUserProf_4714acdf
 */
public class EJSRemoteStatelessUserProf_4714acdf extends EJSWrapper implements UserProf {
	/**
	 * EJSRemoteStatelessUserProf_4714acdf
	 */
	public EJSRemoteStatelessUserProf_4714acdf() throws java.rmi.RemoteException {
		super();	}
	/**
	 * validateUser
	 */
	public boolean validateUser(java.lang.String userid, java.lang.String account, java.lang.String password) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		boolean _EJS_result = false;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = userid;
				_jacc_parms[1] = account;
				_jacc_parms[2] = password;
			}
	com.ardais.bigr.userprofile.UserProfBean beanRef = (com.ardais.bigr.userprofile.UserProfBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.validateUser(userid, account, password);
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
	 * getDefaultViewProfile
	 */
	public com.ardais.bigr.userprofile.ViewProfile getDefaultViewProfile(com.ardais.bigr.security.SecurityInfo secInfo, com.ardais.bigr.query.ViewParams vp) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.userprofile.ViewProfile _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = secInfo;
				_jacc_parms[1] = vp;
			}
	com.ardais.bigr.userprofile.UserProfBean beanRef = (com.ardais.bigr.userprofile.UserProfBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getDefaultViewProfile(secInfo, vp);
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
	 * getViewProfile
	 */
	public com.ardais.bigr.userprofile.ViewProfile getViewProfile(com.ardais.bigr.security.SecurityInfo secInfo, com.ardais.bigr.query.ViewParams vp) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.userprofile.ViewProfile _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = secInfo;
				_jacc_parms[1] = vp;
			}
	com.ardais.bigr.userprofile.UserProfBean beanRef = (com.ardais.bigr.userprofile.UserProfBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getViewProfile(secInfo, vp);
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
	 * getMenuUrls
	 */
	public java.util.List getMenuUrls(java.lang.String accountId) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = accountId;
			}
	com.ardais.bigr.userprofile.UserProfBean beanRef = (com.ardais.bigr.userprofile.UserProfBean)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getMenuUrls(accountId);
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
	 * getLogicalRepositories
	 */
	public java.util.Set getLogicalRepositories(java.lang.String userid) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Set _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = userid;
			}
	com.ardais.bigr.userprofile.UserProfBean beanRef = (com.ardais.bigr.userprofile.UserProfBean)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getLogicalRepositories(userid);
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
	 * getPrivileges
	 */
	public java.util.Set getPrivileges(java.lang.String userid, java.lang.String account) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Set _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = userid;
				_jacc_parms[1] = account;
			}
	com.ardais.bigr.userprofile.UserProfBean beanRef = (com.ardais.bigr.userprofile.UserProfBean)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPrivileges(userid, account);
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
	 * getProfile
	 */
	public java.util.Vector getProfile(java.lang.String userid, java.lang.String account) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Vector _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = userid;
				_jacc_parms[1] = account;
			}
	com.ardais.bigr.userprofile.UserProfBean beanRef = (com.ardais.bigr.userprofile.UserProfBean)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getProfile(userid, account);
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
	 * setViewProfile
	 */
	public void setViewProfile(com.ardais.bigr.security.SecurityInfo secInfo, com.ardais.bigr.query.ViewParams vp, com.ardais.bigr.userprofile.ViewProfile viewProfile) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = secInfo;
				_jacc_parms[1] = vp;
				_jacc_parms[2] = viewProfile;
			}
	com.ardais.bigr.userprofile.UserProfBean beanRef = (com.ardais.bigr.userprofile.UserProfBean)container.preInvoke(this, 7, _EJS_s, _jacc_parms);
			beanRef.setViewProfile(secInfo, vp, viewProfile);
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
}
