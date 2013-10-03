package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteCMPArdaisuserHome_3fdc52da
 */
public class EJSRemoteCMPArdaisuserHome_3fdc52da extends EJSWrapper implements com.ardais.bigr.es.beans.ArdaisuserHome {
	/**
	 * EJSRemoteCMPArdaisuserHome_3fdc52da
	 */
	public EJSRemoteCMPArdaisuserHome_3fdc52da() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisuser create(java.lang.String ardais_user_id, com.ardais.bigr.es.beans.Ardaisaccount argArdaisaccount, java.lang.String argPasswordPolicyCid) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Ardaisuser _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = ardais_user_id;
				_jacc_parms[1] = argArdaisaccount;
				_jacc_parms[2] = argPasswordPolicyCid;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(ardais_user_id, argArdaisaccount, argPasswordPolicyCid);
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
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisuser create(java.lang.String argArdais_user_id, com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount, java.lang.String argPasswordPolicyCid) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Ardaisuser _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = argArdais_user_id;
				_jacc_parms[1] = argArdaisaccount;
				_jacc_parms[2] = argPasswordPolicyCid;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(argArdais_user_id, argArdaisaccount, argPasswordPolicyCid);
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
				container.postInvoke(this, 1, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisuser create(java.lang.String argArdais_user_id, com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount, java.lang.String argPassword, java.lang.String argCreated_by, java.sql.Timestamp argCreate_date, java.lang.String argPasswordPolicyCid) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Ardaisuser _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[6];
				_jacc_parms[0] = argArdais_user_id;
				_jacc_parms[1] = argArdaisaccount;
				_jacc_parms[2] = argPassword;
				_jacc_parms[3] = argCreated_by;
				_jacc_parms[4] = argCreate_date;
				_jacc_parms[5] = argPasswordPolicyCid;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(argArdais_user_id, argArdaisaccount, argPassword, argCreated_by, argCreate_date, argPasswordPolicyCid);
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
				container.postInvoke(this, 2, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Ardaisuser findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisuserKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Ardaisuser _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = primaryKey;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findByPrimaryKey(primaryKey);
		}
		catch (javax.ejb.FinderException ex) {
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
				container.postInvoke(this, 3, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * findByUserId
	 */
	public com.ardais.bigr.es.beans.Ardaisuser findByUserId(java.lang.String userId) throws javax.ejb.FinderException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Ardaisuser _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = userId;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findByUserId(userId);
		}
		catch (javax.ejb.FinderException ex) {
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
				container.postInvoke(this, 4, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * findArdaisuserByArdaisaccount
	 */
	public java.util.Enumeration findArdaisuserByArdaisaccount(com.ardais.bigr.es.beans.ArdaisaccountKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = inKey;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findArdaisuserByArdaisaccount(inKey);
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
				container.postInvoke(this, 5, _EJS_s);
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
	com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
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
				container.postInvoke(this, 6, _EJS_s);
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
	com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da)container.preInvoke(this, 7, _EJS_s, _jacc_parms);
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
				container.postInvoke(this, 7, _EJS_s);
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
	com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da)container.preInvoke(this, 8, _EJS_s, _jacc_parms);
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
				container.postInvoke(this, 8, _EJS_s);
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
	com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da)container.preInvoke(this, 9, _EJS_s, _jacc_parms);
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
				container.postInvoke(this, 9, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
}
