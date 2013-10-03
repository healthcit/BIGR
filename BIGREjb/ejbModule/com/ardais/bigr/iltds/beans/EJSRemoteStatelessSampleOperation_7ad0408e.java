package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessSampleOperation_7ad0408e
 */
public class EJSRemoteStatelessSampleOperation_7ad0408e extends EJSWrapper implements SampleOperation {
	/**
	 * EJSRemoteStatelessSampleOperation_7ad0408e
	 */
	public EJSRemoteStatelessSampleOperation_7ad0408e() throws java.rmi.RemoteException {
		super();	}
	/**
	 * insertSampleAttachment
	 */
	public com.ardais.bigr.pdc.javabeans.AttachmentData insertSampleAttachment(com.ardais.bigr.pdc.javabeans.AttachmentData attachData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.AttachmentData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = attachData;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.iltds.beans.SampleOperationBean beanRef = (com.ardais.bigr.iltds.beans.SampleOperationBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.insertSampleAttachment(attachData, securityInfo);
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
	 * validSamplesForBoxScan
	 */
	public java.lang.String validSamplesForBoxScan(java.util.List sampleIds, boolean samplesMustExist, boolean enforceRequestedSampleRestrictions, boolean enforceStorageTypes, com.ardais.bigr.security.SecurityInfo securityInfo, com.ardais.bigr.javabeans.BoxLayoutDto boxLayoutDto) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.lang.String _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[6];
				_jacc_parms[0] = sampleIds;
				_jacc_parms[1] = new java.lang.Boolean(samplesMustExist);
				_jacc_parms[2] = new java.lang.Boolean(enforceRequestedSampleRestrictions);
				_jacc_parms[3] = new java.lang.Boolean(enforceStorageTypes);
				_jacc_parms[4] = securityInfo;
				_jacc_parms[5] = boxLayoutDto;
			}
	com.ardais.bigr.iltds.beans.SampleOperationBean beanRef = (com.ardais.bigr.iltds.beans.SampleOperationBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.validSamplesForBoxScan(sampleIds, samplesMustExist, enforceRequestedSampleRestrictions, enforceStorageTypes, securityInfo, boxLayoutDto);
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
	 * identifyNonLocalSamples
	 */
	public java.util.List identifyNonLocalSamples(java.util.List sampleIds, com.ardais.bigr.security.SecurityInfo securityInfo, boolean ignoreSamplesWithNoLocation, boolean samplesMustBeInRepository) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[4];
				_jacc_parms[0] = sampleIds;
				_jacc_parms[1] = securityInfo;
				_jacc_parms[2] = new java.lang.Boolean(ignoreSamplesWithNoLocation);
				_jacc_parms[3] = new java.lang.Boolean(samplesMustBeInRepository);
			}
	com.ardais.bigr.iltds.beans.SampleOperationBean beanRef = (com.ardais.bigr.iltds.beans.SampleOperationBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.identifyNonLocalSamples(sampleIds, securityInfo, ignoreSamplesWithNoLocation, samplesMustBeInRepository);
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
}
