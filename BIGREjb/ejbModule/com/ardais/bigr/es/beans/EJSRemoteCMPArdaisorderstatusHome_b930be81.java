package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteCMPArdaisorderstatusHome_b930be81
 */
public class EJSRemoteCMPArdaisorderstatusHome_b930be81 extends EJSWrapper implements com.ardais.bigr.es.beans.ArdaisorderstatusHome {
	/**
	 * EJSRemoteCMPArdaisorderstatusHome_b930be81
	 */
	public EJSRemoteCMPArdaisorderstatusHome_b930be81() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisorderstatus create(java.lang.String ardais_acct_key, java.lang.String order_status_id, java.sql.Timestamp order_status_date, java.lang.String ardais_user_id, com.ardais.bigr.es.beans.Ardaisorder argArdaisorder) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Ardaisorderstatus _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[5];
				_jacc_parms[0] = ardais_acct_key;
				_jacc_parms[1] = order_status_id;
				_jacc_parms[2] = order_status_date;
				_jacc_parms[3] = ardais_user_id;
				_jacc_parms[4] = argArdaisorder;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81 beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(ardais_acct_key, order_status_id, order_status_date, ardais_user_id, argArdaisorder);
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
	public com.ardais.bigr.es.beans.Ardaisorderstatus create(java.sql.Timestamp argOrder_status_date, java.lang.String argOrder_status_id, com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder, java.lang.String argArdais_acct_key, java.lang.String argArdais_user_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Ardaisorderstatus _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[5];
				_jacc_parms[0] = argOrder_status_date;
				_jacc_parms[1] = argOrder_status_id;
				_jacc_parms[2] = argArdaisorder;
				_jacc_parms[3] = argArdais_acct_key;
				_jacc_parms[4] = argArdais_user_id;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81 beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(argOrder_status_date, argOrder_status_id, argArdaisorder, argArdais_acct_key, argArdais_user_id);
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
	public com.ardais.bigr.es.beans.Ardaisorderstatus create(java.sql.Timestamp order_status_date, java.lang.String order_status_id, java.lang.String ardais_acct_key, java.lang.String ardais_user_id, com.ardais.bigr.es.beans.Ardaisorder argArdaisorder) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Ardaisorderstatus _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[5];
				_jacc_parms[0] = order_status_date;
				_jacc_parms[1] = order_status_id;
				_jacc_parms[2] = ardais_acct_key;
				_jacc_parms[3] = ardais_user_id;
				_jacc_parms[4] = argArdaisorder;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81 beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(order_status_date, order_status_id, ardais_acct_key, ardais_user_id, argArdaisorder);
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
	public com.ardais.bigr.es.beans.Ardaisorderstatus findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisorderstatusKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.es.beans.Ardaisorderstatus _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = primaryKey;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81 beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
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
	 * findArdaisorderstatusByArdaisorder
	 */
	public java.util.Enumeration findArdaisorderstatusByArdaisorder(com.ardais.bigr.es.beans.ArdaisorderKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = inKey;
			}
	com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81 beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findArdaisorderstatusByArdaisorder(inKey);
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
				container.postInvoke(this, 4, _EJS_s);
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
	com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81 beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
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
				container.postInvoke(this, 5, _EJS_s);
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
	com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81 beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
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
				container.postInvoke(this, 6, _EJS_s);
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
	com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81 beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81)container.preInvoke(this, 7, _EJS_s, _jacc_parms);
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
				container.postInvoke(this, 7, _EJS_s);
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
	com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81 beanRef = (com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81)container.preInvoke(this, 8, _EJS_s, _jacc_parms);
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
}
