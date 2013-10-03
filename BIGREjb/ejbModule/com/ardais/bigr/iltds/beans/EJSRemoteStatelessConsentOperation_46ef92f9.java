package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessConsentOperation_46ef92f9
 */
public class EJSRemoteStatelessConsentOperation_46ef92f9 extends EJSWrapper implements ConsentOperation {
	/**
	 * EJSRemoteStatelessConsentOperation_46ef92f9
	 */
	public EJSRemoteStatelessConsentOperation_46ef92f9() throws java.rmi.RemoteException {
		super();	}
	/**
	 * insertCNTAttachment
	 */
	public com.ardais.bigr.pdc.javabeans.AttachmentData insertCNTAttachment(com.ardais.bigr.pdc.javabeans.AttachmentData attachData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
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
	com.ardais.bigr.iltds.beans.ConsentOperationBean beanRef = (com.ardais.bigr.iltds.beans.ConsentOperationBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.insertCNTAttachment(attachData, securityInfo);
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
	 * getCaseOrigin
	 */
	public java.lang.String getCaseOrigin(java.lang.String caseID) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.lang.String _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = caseID;
			}
	com.ardais.bigr.iltds.beans.ConsentOperationBean beanRef = (com.ardais.bigr.iltds.beans.ConsentOperationBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getCaseOrigin(caseID);
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
	 * pullFromESHold
	 */
	public void pullFromESHold(java.lang.String sampleID) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = sampleID;
			}
	com.ardais.bigr.iltds.beans.ConsentOperationBean beanRef = (com.ardais.bigr.iltds.beans.ConsentOperationBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			beanRef.pullFromESHold(sampleID);
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
	 * revokeConsent
	 */
	public void revokeConsent(java.lang.String consent_id, java.lang.String ardais_id, java.lang.String ardaisstaff_id, java.lang.String reason) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[4];
				_jacc_parms[0] = consent_id;
				_jacc_parms[1] = ardais_id;
				_jacc_parms[2] = ardaisstaff_id;
				_jacc_parms[3] = reason;
			}
	com.ardais.bigr.iltds.beans.ConsentOperationBean beanRef = (com.ardais.bigr.iltds.beans.ConsentOperationBean)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			beanRef.revokeConsent(consent_id, ardais_id, ardaisstaff_id, reason);
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
}
