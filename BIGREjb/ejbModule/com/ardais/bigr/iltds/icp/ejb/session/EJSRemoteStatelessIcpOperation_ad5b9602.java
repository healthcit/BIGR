package com.ardais.bigr.iltds.icp.ejb.session;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessIcpOperation_ad5b9602
 */
public class EJSRemoteStatelessIcpOperation_ad5b9602 extends EJSWrapper implements IcpOperation {
	/**
	 * EJSRemoteStatelessIcpOperation_ad5b9602
	 */
	public EJSRemoteStatelessIcpOperation_ad5b9602() throws java.rmi.RemoteException {
		super();	}
	/**
	 * getAsmData
	 */
	public com.ardais.bigr.iltds.databeans.AsmData getAsmData(com.ardais.bigr.iltds.databeans.AsmData asmData, com.ardais.bigr.security.SecurityInfo securityInfo, boolean getSub, boolean getParents) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.iltds.databeans.AsmData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[4];
				_jacc_parms[0] = asmData;
				_jacc_parms[1] = securityInfo;
				_jacc_parms[2] = new java.lang.Boolean(getSub);
				_jacc_parms[3] = new java.lang.Boolean(getParents);
			}
	com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean beanRef = (com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getAsmData(asmData, securityInfo, getSub, getParents);
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
	 * getCaseData
	 */
	public com.ardais.bigr.iltds.databeans.CaseData getCaseData(com.ardais.bigr.iltds.databeans.CaseData caseData, com.ardais.bigr.security.SecurityInfo securityInfo, boolean getParents) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.iltds.databeans.CaseData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = caseData;
				_jacc_parms[1] = securityInfo;
				_jacc_parms[2] = new java.lang.Boolean(getParents);
			}
	com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean beanRef = (com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getCaseData(caseData, securityInfo, getParents);
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
	 * getLogicalRepositoryData
	 */
	public com.ardais.bigr.iltds.databeans.LogicalRepositoryExtendedData getLogicalRepositoryData(java.lang.String repositoryId, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.iltds.databeans.LogicalRepositoryExtendedData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = repositoryId;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean beanRef = (com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getLogicalRepositoryData(repositoryId, securityInfo);
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
	 * getSampleData
	 */
	public com.ardais.bigr.iltds.databeans.SampleData getSampleData(com.ardais.bigr.iltds.databeans.SampleData sampleData, com.ardais.bigr.security.SecurityInfo securityInfo, boolean getSub, boolean getParents) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.iltds.databeans.SampleData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[4];
				_jacc_parms[0] = sampleData;
				_jacc_parms[1] = securityInfo;
				_jacc_parms[2] = new java.lang.Boolean(getSub);
				_jacc_parms[3] = new java.lang.Boolean(getParents);
			}
	com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean beanRef = (com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getSampleData(sampleData, securityInfo, getSub, getParents);
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
	 * getBoxData
	 */
	public com.ardais.bigr.javabeans.BoxDto getBoxData(com.ardais.bigr.javabeans.BoxDto boxData, com.ardais.bigr.security.SecurityInfo securityInfo, boolean getContents) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.javabeans.BoxDto _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = boxData;
				_jacc_parms[1] = securityInfo;
				_jacc_parms[2] = new java.lang.Boolean(getContents);
			}
	com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean beanRef = (com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getBoxData(boxData, securityInfo, getContents);
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
	 * getSessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		javax.ejb.SessionContext _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean beanRef = (com.ardais.bigr.iltds.icp.ejb.session.IcpOperationBean)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getSessionContext();
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
}
