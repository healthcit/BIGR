package com.ardais.bigr.orm.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteCMPUseraccessmoduleHome_abc4f862
 */
public class EJSRemoteCMPUseraccessmoduleHome_abc4f862 extends EJSWrapper implements com.ardais.bigr.orm.beans.UseraccessmoduleHome {
	/**
	 * EJSRemoteCMPUseraccessmoduleHome_abc4f862
	 */
	public EJSRemoteCMPUseraccessmoduleHome_abc4f862() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.orm.beans.Useraccessmodule create(java.lang.String argArdais_acct_key, java.lang.String argArdais_user_id, com.ardais.bigr.orm.beans.ObjectsKey argObjects, java.lang.String argCreated_by, java.sql.Timestamp argCreate_date, java.lang.String argUpdated_by, java.sql.Timestamp argUpdate_date) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.orm.beans.Useraccessmodule _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[7];
				_jacc_parms[0] = argArdais_acct_key;
				_jacc_parms[1] = argArdais_user_id;
				_jacc_parms[2] = argObjects;
				_jacc_parms[3] = argCreated_by;
				_jacc_parms[4] = argCreate_date;
				_jacc_parms[5] = argUpdated_by;
				_jacc_parms[6] = argUpdate_date;
			}
	com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862 beanRef = (com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(argArdais_acct_key, argArdais_user_id, argObjects, argCreated_by, argCreate_date, argUpdated_by, argUpdate_date);
		}
		catch (javax.ejb.CreateException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.orm.beans.Useraccessmodule findByPrimaryKey(com.ardais.bigr.orm.beans.UseraccessmoduleKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.orm.beans.Useraccessmodule _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = key;
			}
	com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862 beanRef = (com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findByPrimaryKey(key);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (javax.ejb.FinderException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
	 * findUseraccessmoduleByObjects
	 */
	public java.util.Enumeration findUseraccessmoduleByObjects(com.ardais.bigr.orm.beans.ObjectsKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = inKey;
			}
	com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862 beanRef = (com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findUseraccessmoduleByObjects(inKey);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (javax.ejb.FinderException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
	 * getEJBMetaData
	 */
	public javax.ejb.EJBMetaData getEJBMetaData() throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		javax.ejb.EJBMetaData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862 beanRef = (com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getEJBMetaData();
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
	 * getHomeHandle
	 */
	public javax.ejb.HomeHandle getHomeHandle() throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		javax.ejb.HomeHandle _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862 beanRef = (com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getHomeHandle();
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
	 * remove
	 */
	public void remove(java.lang.Object arg0) throws java.rmi.RemoteException, javax.ejb.RemoveException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = arg0;
			}
	com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862 beanRef = (com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			beanRef.remove(arg0);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (javax.ejb.RemoveException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
		return ;
	}
	/**
	 * remove
	 */
	public void remove(javax.ejb.Handle arg0) throws java.rmi.RemoteException, javax.ejb.RemoveException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = arg0;
			}
	com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862 beanRef = (com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
			beanRef.remove(arg0);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (javax.ejb.RemoveException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
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
		return ;
	}
}
