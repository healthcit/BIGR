package com.ardais.bigr.lims.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPPathologyEvaluationHomeBean_bb9279f9
 */
public class EJSCMPPathologyEvaluationHomeBean_bb9279f9 extends EJSHome {
	/**
	 * EJSCMPPathologyEvaluationHomeBean_bb9279f9
	 */
	public EJSCMPPathologyEvaluationHomeBean_bb9279f9() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.lims.beans.PathologyEvaluation) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation findByPrimaryKey(java.lang.String primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.lims.beans.EJSJDBCPersisterCMPPathologyEvaluationBean_bb9279f9) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation create(java.lang.String evaluationId) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.lims.beans.PathologyEvaluation _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.lims.beans.PathologyEvaluationBean bean = (com.ardais.bigr.lims.beans.PathologyEvaluationBean) beanO.getEnterpriseBean();
			bean.ejbCreate(evaluationId);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(evaluationId);
			afterPostCreateWrapper(beanO, _primaryKey);
		}
		catch (javax.ejb.CreateException ex) {
			createFailed = true;
			throw ex;
		} catch (java.rmi.RemoteException ex) {
			createFailed = true;
			throw ex;
		} catch (Throwable ex) {
			createFailed = true;
			throw new CreateFailureException(ex);
		} finally {
			if (createFailed) {
				super.createFailure(beanO);
			}
		}
		return _EJS_result;
	}
	/**
	 * findAll
	 */
	public java.util.Enumeration findAll() throws javax.ejb.FinderException, java.rmi.RemoteException {
return super.getEnumeration(((com.ardais.bigr.lims.beans.EJSFinderPathologyEvaluationBean)persister).findAll());	}
	/**
	 * findAllNonMigrated
	 */
	public java.util.Enumeration findAllNonMigrated() throws javax.ejb.FinderException, java.rmi.RemoteException {
return super.getEnumeration(((com.ardais.bigr.lims.beans.EJSFinderPathologyEvaluationBean)persister).findAllNonMigrated());	}
	/**
	 * findBySampleId
	 */
	public java.util.Enumeration findBySampleId(java.lang.String sampleId) throws javax.ejb.FinderException, java.rmi.RemoteException {
return super.getEnumeration(((com.ardais.bigr.lims.beans.EJSFinderPathologyEvaluationBean)persister).findBySampleId(sampleId));	}
	/**
	 * create
	 */
	public com.ardais.bigr.lims.beans.PathologyEvaluation create(java.lang.String evaluationId, java.lang.String slideID, java.lang.String sampleId, java.lang.String microscopicAppearance, java.lang.String reportedYN, java.lang.String createMethod, java.lang.String diagnosis, java.lang.String tissueOfOrigin, java.lang.String tissueOfFinding, java.lang.Integer tumorCells, java.lang.Integer normalCells, java.lang.Integer hypoacellularstromaCells, java.lang.Integer necrosisCells, java.lang.Integer lesionCells, java.lang.Integer cellularstromaCells) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.lims.beans.PathologyEvaluation _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.lims.beans.PathologyEvaluationBean bean = (com.ardais.bigr.lims.beans.PathologyEvaluationBean) beanO.getEnterpriseBean();
			bean.ejbCreate(evaluationId, slideID, sampleId, microscopicAppearance, reportedYN, createMethod, diagnosis, tissueOfOrigin, tissueOfFinding, tumorCells, normalCells, hypoacellularstromaCells, necrosisCells, lesionCells, cellularstromaCells);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			afterPostCreateWrapper(beanO, _primaryKey);
		}
		catch (javax.ejb.CreateException ex) {
			createFailed = true;
			throw ex;
		} catch (java.rmi.RemoteException ex) {
			createFailed = true;
			throw ex;
		} catch (Throwable ex) {
			createFailed = true;
			throw new CreateFailureException(ex);
		} finally {
			if (createFailed) {
				super.createFailure(beanO);
			}
		}
		return _EJS_result;
	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.lims.beans.PathologyEvaluationBean tmpEJB = (com.ardais.bigr.lims.beans.PathologyEvaluationBean) generalEJB;
		return tmpEJB.evaluationId;
	}
	/**
	 * keyFromFields
	 */
	public java.lang.String keyFromFields(java.lang.String f0) {
		return f0;
	}
}
