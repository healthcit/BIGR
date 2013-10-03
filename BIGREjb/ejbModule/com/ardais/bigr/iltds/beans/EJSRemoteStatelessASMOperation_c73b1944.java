package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessASMOperation_c73b1944
 */
public class EJSRemoteStatelessASMOperation_c73b1944 extends EJSWrapper implements ASMOperation {
	/**
	 * EJSRemoteStatelessASMOperation_c73b1944
	 */
	public EJSRemoteStatelessASMOperation_c73b1944() throws java.rmi.RemoteException {
		super();	}
	/**
	 * asmFormExistInRange
	 */
	public boolean asmFormExistInRange(java.lang.String asmFormID, java.lang.String consentID) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		boolean _EJS_result = false;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = asmFormID;
				_jacc_parms[1] = consentID;
			}
	com.ardais.bigr.iltds.beans.ASMOperationBean beanRef = (com.ardais.bigr.iltds.beans.ASMOperationBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.asmFormExistInRange(asmFormID, consentID);
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
	 * nonAsmFormSamples
	 */
	public java.util.Vector nonAsmFormSamples(java.lang.String asmID, java.util.Vector samples) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Vector _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = asmID;
				_jacc_parms[1] = samples;
			}
	com.ardais.bigr.iltds.beans.ASMOperationBean beanRef = (com.ardais.bigr.iltds.beans.ASMOperationBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.nonAsmFormSamples(asmID, samples);
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
	 * associateASMForm
	 */
	public void associateASMForm(java.lang.String consentID, java.lang.String asmFormID, com.ardais.bigr.security.SecurityInfo secInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = consentID;
				_jacc_parms[1] = asmFormID;
				_jacc_parms[2] = secInfo;
			}
	com.ardais.bigr.iltds.beans.ASMOperationBean beanRef = (com.ardais.bigr.iltds.beans.ASMOperationBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			beanRef.associateASMForm(consentID, asmFormID, secInfo);
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
	/**
	 * updateASMFormInfo
	 */
	public void updateASMFormInfo(java.lang.String asmFormID, java.lang.String surgicalProcedure, java.sql.Date timeOfRemoval, java.sql.Date timeOfGrossing, java.lang.String employeeName) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[5];
				_jacc_parms[0] = asmFormID;
				_jacc_parms[1] = surgicalProcedure;
				_jacc_parms[2] = timeOfRemoval;
				_jacc_parms[3] = timeOfGrossing;
				_jacc_parms[4] = employeeName;
			}
	com.ardais.bigr.iltds.beans.ASMOperationBean beanRef = (com.ardais.bigr.iltds.beans.ASMOperationBean)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			beanRef.updateASMFormInfo(asmFormID, surgicalProcedure, timeOfRemoval, timeOfGrossing, employeeName);
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
		return ;
	}
	/**
	 * updateASMModuleInfo
	 */
	public void updateASMModuleInfo(com.ardais.bigr.iltds.databeans.AsmData asmData, com.ardais.bigr.security.SecurityInfo secInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = asmData;
				_jacc_parms[1] = secInfo;
			}
	com.ardais.bigr.iltds.beans.ASMOperationBean beanRef = (com.ardais.bigr.iltds.beans.ASMOperationBean)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			beanRef.updateASMModuleInfo(asmData, secInfo);
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
		return ;
	}
}
