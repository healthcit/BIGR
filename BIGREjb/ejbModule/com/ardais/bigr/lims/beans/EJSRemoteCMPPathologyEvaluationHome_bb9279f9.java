package com.ardais.bigr.lims.beans;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteCMPPathologyEvaluationHome_bb9279f9
 */
public class EJSRemoteCMPPathologyEvaluationHome_bb9279f9 extends EJSWrapper implements com.ardais.bigr.lims.beans.PathologyEvaluationHome {
	/**
	 * EJSRemoteCMPPathologyEvaluationHome_bb9279f9
	 */
	public EJSRemoteCMPPathologyEvaluationHome_bb9279f9() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation create(java.lang.String evaluationId) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.lims.beans.PathologyEvaluation _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = evaluationId;
			}
	com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 beanRef = (com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(evaluationId);
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
	public com.ardais.bigr.lims.beans.PathologyEvaluation create(java.lang.String evaluationId, java.lang.String slideID, java.lang.String sampleId, java.lang.String microscopicAppearance, java.lang.String reportedYN, java.lang.String createMethod, java.lang.String diagnosis, java.lang.String tissueOfOrigin, java.lang.String tissueOfFinding, java.lang.Integer tumorCells, java.lang.Integer normalCells, java.lang.Integer hypoacellularstromaCells, java.lang.Integer necrosisCells, java.lang.Integer lesionCells, java.lang.Integer cellularstromaCells) throws javax.ejb.CreateException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.lims.beans.PathologyEvaluation _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[15];
				_jacc_parms[0] = evaluationId;
				_jacc_parms[1] = slideID;
				_jacc_parms[2] = sampleId;
				_jacc_parms[3] = microscopicAppearance;
				_jacc_parms[4] = reportedYN;
				_jacc_parms[5] = createMethod;
				_jacc_parms[6] = diagnosis;
				_jacc_parms[7] = tissueOfOrigin;
				_jacc_parms[8] = tissueOfFinding;
				_jacc_parms[9] = tumorCells;
				_jacc_parms[10] = normalCells;
				_jacc_parms[11] = hypoacellularstromaCells;
				_jacc_parms[12] = necrosisCells;
				_jacc_parms[13] = lesionCells;
				_jacc_parms[14] = cellularstromaCells;
			}
	com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 beanRef = (com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.create(evaluationId, slideID, sampleId, microscopicAppearance, reportedYN, createMethod, diagnosis, tissueOfOrigin, tissueOfFinding, tumorCells, normalCells, hypoacellularstromaCells, necrosisCells, lesionCells, cellularstromaCells);
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
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation findByPrimaryKey(java.lang.String primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		com.ardais.bigr.lims.beans.PathologyEvaluation _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = primaryKey;
			}
	com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 beanRef = (com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9)container.preInvoke(this, 2, _EJS_s, _jacc_parms);
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
				container.postInvoke(this, 2, _EJS_s);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * findAll
	 */
	public java.util.Enumeration findAll() throws javax.ejb.FinderException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 beanRef = (com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9)container.preInvoke(this, 3, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findAll();
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
	 * findAllNonMigrated
	 */
	public java.util.Enumeration findAllNonMigrated() throws javax.ejb.FinderException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 beanRef = (com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9)container.preInvoke(this, 4, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findAllNonMigrated();
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
	 * findBySampleId
	 */
	public java.util.Enumeration findBySampleId(java.lang.String sampleId) throws javax.ejb.FinderException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		java.util.Enumeration _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = sampleId;
			}
	com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 beanRef = (com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9)container.preInvoke(this, 5, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.findBySampleId(sampleId);
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
	com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 beanRef = (com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9)container.preInvoke(this, 6, _EJS_s, _jacc_parms);
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
	com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 beanRef = (com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9)container.preInvoke(this, 7, _EJS_s, _jacc_parms);
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
	com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 beanRef = (com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9)container.preInvoke(this, 8, _EJS_s, _jacc_parms);
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
	com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9 beanRef = (com.ardais.bigr.lims.beans.EJSCMPPathologyEvaluationHomeBean_bb9279f9)container.preInvoke(this, 9, _EJS_s, _jacc_parms);
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
