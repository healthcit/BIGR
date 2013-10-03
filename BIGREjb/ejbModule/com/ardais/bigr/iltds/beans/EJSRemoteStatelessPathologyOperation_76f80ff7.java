package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessPathologyOperation_76f80ff7
 */
public class EJSRemoteStatelessPathologyOperation_76f80ff7 extends EJSWrapper implements PathologyOperation {
	/**
	 * EJSRemoteStatelessPathologyOperation_76f80ff7
	 */
	public EJSRemoteStatelessPathologyOperation_76f80ff7() throws java.rmi.RemoteException {
		super();	}
	/**
	 * getProjectsAndShoppingCartsForSample
	 */
	public java.util.Vector getProjectsAndShoppingCartsForSample(java.lang.String sampleID) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Vector _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = sampleID;
			}
	com.ardais.bigr.iltds.beans.PathologyOperationBean beanRef = (com.ardais.bigr.iltds.beans.PathologyOperationBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getProjectsAndShoppingCartsForSample(sampleID);
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
	 * getSampleLocations
	 */
	public java.util.Vector getSampleLocations(java.util.Vector samples) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Vector _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = samples;
			}
	com.ardais.bigr.iltds.beans.PathologyOperationBean beanRef = (com.ardais.bigr.iltds.beans.PathologyOperationBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getSampleLocations(samples);
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
	 * updateSampleStatus
	 */
	public void updateSampleStatus(java.util.Vector samples, java.lang.String status) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = samples;
				_jacc_parms[1] = status;
			}
	com.ardais.bigr.iltds.beans.PathologyOperationBean beanRef = (com.ardais.bigr.iltds.beans.PathologyOperationBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			beanRef.updateSampleStatus(samples, status);
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
		return ;
	}
}
