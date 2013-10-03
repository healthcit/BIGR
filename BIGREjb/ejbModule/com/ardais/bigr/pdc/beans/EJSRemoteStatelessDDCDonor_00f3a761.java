package com.ardais.bigr.pdc.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessDDCDonor_00f3a761
 */
public class EJSRemoteStatelessDDCDonor_00f3a761 extends EJSWrapper implements DDCDonor {
	/**
	 * EJSRemoteStatelessDDCDonor_00f3a761
	 */
	public EJSRemoteStatelessDDCDonor_00f3a761() throws java.rmi.RemoteException {
		super();	}
	/**
	 * deleteAttachment
	 */
	public boolean deleteAttachment(java.lang.String deleteAttachId) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		boolean _EJS_result = false;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = deleteAttachId;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.deleteAttachment(deleteAttachId);
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
	 * isPresent
	 */
	public boolean isPresent(com.ardais.bigr.pdc.javabeans.DonorData donorData) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		boolean _EJS_result = false;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = donorData;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.isPresent(donorData);
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
	 * insertDonorAttachment
	 */
	public com.ardais.bigr.pdc.javabeans.AttachmentData insertDonorAttachment(com.ardais.bigr.pdc.javabeans.AttachmentData attachData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
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
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.insertDonorAttachment(attachData, securityInfo);
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
	 * createClinicalData
	 */
	public com.ardais.bigr.pdc.javabeans.ClinicalDataData createClinicalData(com.ardais.bigr.pdc.javabeans.ClinicalDataData clinicalData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.ClinicalDataData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = clinicalData;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.createClinicalData(clinicalData, securityInfo);
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
	 * getClinicalData
	 */
	public com.ardais.bigr.pdc.javabeans.ClinicalDataData getClinicalData(com.ardais.bigr.pdc.javabeans.ClinicalDataData clinicalData) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.ClinicalDataData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = clinicalData;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getClinicalData(clinicalData);
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
	 * updateClinicalData
	 */
	public com.ardais.bigr.pdc.javabeans.ClinicalDataData updateClinicalData(com.ardais.bigr.pdc.javabeans.ClinicalDataData clinicalData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.ClinicalDataData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = clinicalData;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.updateClinicalData(clinicalData, securityInfo);
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
	 * getConsentDetail
	 */
	public com.ardais.bigr.pdc.javabeans.ConsentData getConsentDetail(com.ardais.bigr.pdc.javabeans.ConsentData consentData) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.ConsentData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = consentData;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getConsentDetail(consentData);
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
	 * updateCaseProfileNotes
	 */
	public com.ardais.bigr.pdc.javabeans.ConsentData updateCaseProfileNotes(com.ardais.bigr.pdc.javabeans.ConsentData consentData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.ConsentData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = consentData;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 7, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.updateCaseProfileNotes(consentData, securityInfo);
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
	 * buildDonorData
	 */
	public com.ardais.bigr.pdc.javabeans.DonorData buildDonorData(com.ardais.bigr.pdc.javabeans.DonorData donorData) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.DonorData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = donorData;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 8, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.buildDonorData(donorData);
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
				container.postInvoke(this, 8, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * createDonorProfile
	 */
	public com.ardais.bigr.pdc.javabeans.DonorData createDonorProfile(com.ardais.bigr.pdc.javabeans.DonorData donorData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.DonorData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = donorData;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 9, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.createDonorProfile(donorData, securityInfo);
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
				container.postInvoke(this, 9, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getDonorCaseSummary
	 */
	public com.ardais.bigr.pdc.javabeans.DonorData getDonorCaseSummary(java.lang.String ardaisId, boolean linkedCasesOnly) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.DonorData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = ardaisId;
				_jacc_parms[1] = new java.lang.Boolean(linkedCasesOnly);
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 10, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getDonorCaseSummary(ardaisId, linkedCasesOnly);
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
				container.postInvoke(this, 10, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getDonorProfile
	 */
	public com.ardais.bigr.pdc.javabeans.DonorData getDonorProfile(com.ardais.bigr.pdc.javabeans.DonorData donorData) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.DonorData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = donorData;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 11, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getDonorProfile(donorData);
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
				container.postInvoke(this, 11, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * updateDonorProfile
	 */
	public com.ardais.bigr.pdc.javabeans.DonorData updateDonorProfile(com.ardais.bigr.pdc.javabeans.DonorData donorData, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.DonorData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = donorData;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 12, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.updateDonorProfile(donorData, securityInfo);
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
				container.postInvoke(this, 12, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getDonorAccount
	 */
	public java.lang.String getDonorAccount(java.lang.String ardaisID) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.lang.String _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = ardaisID;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 13, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getDonorAccount(ardaisID);
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
				container.postInvoke(this, 13, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getAttachments
	 */
	public java.util.List getAttachments(com.ardais.bigr.pdc.javabeans.DonorData dData) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = dData;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 14, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getAttachments(dData);
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
				container.postInvoke(this, 14, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getClinicalDataList
	 */
	public java.util.List getClinicalDataList(com.ardais.bigr.pdc.javabeans.ClinicalDataData clinicalData) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = clinicalData;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 15, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getClinicalDataList(clinicalData);
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
				container.postInvoke(this, 15, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getConsents
	 */
	public java.util.List getConsents(com.ardais.bigr.pdc.javabeans.DonorData donorData) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = donorData;
			}
	com.ardais.bigr.pdc.beans.DDCDonorBean beanRef = (com.ardais.bigr.pdc.beans.DDCDonorBean)container.preInvoke(this, 16, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getConsents(donorData);
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
				container.postInvoke(this, 16, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
}
