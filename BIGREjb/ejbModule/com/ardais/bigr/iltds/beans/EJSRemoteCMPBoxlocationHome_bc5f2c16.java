package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteCMPBoxlocationHome_bc5f2c16
 */
public class EJSRemoteCMPBoxlocationHome_bc5f2c16 extends EJSWrapper implements com.ardais.bigr.iltds.beans.BoxlocationHome {
	/**
	 * EJSRemoteCMPBoxlocationHome_bc5f2c16
	 */
	public EJSRemoteCMPBoxlocationHome_bc5f2c16() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Boxlocation create(com.ardais.bigr.iltds.assistants.StorageLocAsst asst) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.iltds.beans.Boxlocation _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = asst;
			}
	com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 beanRef = (com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(asst);
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
	public com.ardais.bigr.iltds.beans.Boxlocation findByPrimaryKey(com.ardais.bigr.iltds.beans.BoxlocationKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.iltds.beans.Boxlocation _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = primaryKey;
			}
	com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 beanRef = (com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
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
				container.postInvoke(this, 1, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * findBoxLocationByBoxId
	 */
	public java.util.Enumeration findBoxLocationByBoxId(java.lang.String box) throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = box;
			}
	com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 beanRef = (com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findBoxLocationByBoxId(box);
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
	 * findBoxLocationByStorageTypeCid
	 */
	public java.util.Enumeration findBoxLocationByStorageTypeCid(java.lang.String locationAddressId, java.lang.String storageTypeCid, java.lang.String availableInd) throws javax.ejb.FinderException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = locationAddressId;
				_jacc_parms[1] = storageTypeCid;
				_jacc_parms[2] = availableInd;
			}
	com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 beanRef = (com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findBoxLocationByStorageTypeCid(locationAddressId, storageTypeCid, availableInd);
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
	 * findBoxlocationByGeolocation
	 */
	public java.util.Enumeration findBoxlocationByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = inKey;
			}
	com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 beanRef = (com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findBoxlocationByGeolocation(inKey);
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
	 * findBoxlocationBySamplebox
	 */
	public java.util.Enumeration findBoxlocationBySamplebox(com.ardais.bigr.iltds.beans.SampleboxKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = inKey;
			}
	com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 beanRef = (com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findBoxlocationBySamplebox(inKey);
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
	com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 beanRef = (com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
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
	com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 beanRef = (com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16)container.preInvoke(this, 7, _EJS_s, _jacc_parms);
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
	com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 beanRef = (com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16)container.preInvoke(this, 8, _EJS_s, _jacc_parms);
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
	com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 beanRef = (com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16)container.preInvoke(this, 9, _EJS_s, _jacc_parms);
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
