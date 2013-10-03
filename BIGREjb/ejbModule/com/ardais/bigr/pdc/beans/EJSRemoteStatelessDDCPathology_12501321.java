package com.ardais.bigr.pdc.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessDDCPathology_12501321
 */
public class EJSRemoteStatelessDDCPathology_12501321 extends EJSWrapper implements DDCPathology {
	/**
	 * EJSRemoteStatelessDDCPathology_12501321
	 */
	public EJSRemoteStatelessDDCPathology_12501321() throws java.rmi.RemoteException {
		super();	}
	/**
	 * isExistsPathReportSection
	 */
	public boolean isExistsPathReportSection(com.ardais.bigr.pdc.javabeans.PathReportSectionData dataBean) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		boolean _EJS_result = false;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = dataBean;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.isExistsPathReportSection(dataBean);
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
	 * isExistsPathReportSectionFinding
	 */
	public boolean isExistsPathReportSectionFinding(com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData dataBean) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		boolean _EJS_result = false;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = dataBean;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.isExistsPathReportSectionFinding(dataBean);
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
	 * getSectionIdentifierList
	 */
	public com.ardais.bigr.iltds.assistants.LegalValueSet getSectionIdentifierList(java.lang.String pathReportId, java.lang.String pathReportSecId) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.iltds.assistants.LegalValueSet _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = pathReportId;
				_jacc_parms[1] = pathReportSecId;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getSectionIdentifierList(pathReportId, pathReportSecId);
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
	 * buildPathReport
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportData buildPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = pathReport;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.buildPathReport(pathReport);
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
	 * createPathReport
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportData createPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = pathReport;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.createPathReport(pathReport, securityInfo);
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
	 * createRawPathReport
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportData createRawPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = pathReport;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.createRawPathReport(pathReport, securityInfo);
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
	 * getPathReport
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportData getPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = pathReport;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPathReport(pathReport);
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
	 * getPathReportSummary
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportData getPathReportSummary(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = pathReport;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 7, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPathReportSummary(pathReport);
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
	 * getRawPathReport
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportData getRawPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = pathReport;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 8, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getRawPathReport(pathReport);
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
	 * getRawPathReport
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportData getRawPathReport(java.lang.String lineId) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = lineId;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 9, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getRawPathReport(lineId);
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
	 * updatePathReport
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportData updatePathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = pathReport;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 10, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.updatePathReport(pathReport, securityInfo);
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
	 * updateRawPathReport
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportData updateRawPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = pathReport;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 11, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.updateRawPathReport(pathReport, securityInfo);
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
	 * createPathReportDiagnostic
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData createPathReportDiagnostic(com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData pathReportDiagnostic, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = pathReportDiagnostic;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 12, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.createPathReportDiagnostic(pathReportDiagnostic, securityInfo);
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
	 * getPathReportDiagnostic
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData getPathReportDiagnostic(com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData pathReportDiagnostic) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = pathReportDiagnostic;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 13, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPathReportDiagnostic(pathReportDiagnostic);
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
	 * updatePathReportDiagnostic
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData updatePathReportDiagnostic(com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData pathReportDiagnostic, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = pathReportDiagnostic;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 14, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.updatePathReportDiagnostic(pathReportDiagnostic, securityInfo);
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
	 * createPathReportSection
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportSectionData createPathReportSection(com.ardais.bigr.pdc.javabeans.PathReportSectionData pathReportSection, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportSectionData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = pathReportSection;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 15, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.createPathReportSection(pathReportSection, securityInfo);
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
	 * getPathReportSection
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportSectionData getPathReportSection(com.ardais.bigr.pdc.javabeans.PathReportSectionData pathReportSection) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportSectionData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = pathReportSection;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 16, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPathReportSection(pathReportSection);
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
	/**
	 * updatePathReportSection
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportSectionData updatePathReportSection(com.ardais.bigr.pdc.javabeans.PathReportSectionData pathReportSection, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportSectionData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = pathReportSection;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 17, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.updatePathReportSection(pathReportSection, securityInfo);
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
				container.postInvoke(this, 17, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * createPathReportSectionFinding
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData createPathReportSectionFinding(com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData dataBean, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = dataBean;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 18, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.createPathReportSectionFinding(dataBean, securityInfo);
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
				container.postInvoke(this, 18, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getPathReportSectionFinding
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData getPathReportSectionFinding(com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData dataBean) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = dataBean;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 19, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPathReportSectionFinding(dataBean);
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
				container.postInvoke(this, 19, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * updatePathReportSectionFinding
	 */
	public com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData updatePathReportSectionFinding(com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData dataBean, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[2];
				_jacc_parms[0] = dataBean;
				_jacc_parms[1] = securityInfo;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 20, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.updatePathReportSectionFinding(dataBean, securityInfo);
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
				container.postInvoke(this, 20, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getPathReportDiagnostics
	 */
	public java.util.List getPathReportDiagnostics(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = pathReport;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 21, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPathReportDiagnostics(pathReport);
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
				container.postInvoke(this, 21, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getPathReportSectionFindings
	 */
	public java.util.List getPathReportSectionFindings(com.ardais.bigr.pdc.javabeans.PathReportSectionData dataBean) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = dataBean;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 22, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPathReportSectionFindings(dataBean);
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
				container.postInvoke(this, 22, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * getPathReportSections
	 */
	public java.util.List getPathReportSections(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.List _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = pathReport;
			}
	com.ardais.bigr.pdc.beans.DDCPathologyBean beanRef = (com.ardais.bigr.pdc.beans.DDCPathologyBean)container.preInvoke(this, 23, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.getPathReportSections(pathReport);
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
				container.postInvoke(this, 23, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
}
