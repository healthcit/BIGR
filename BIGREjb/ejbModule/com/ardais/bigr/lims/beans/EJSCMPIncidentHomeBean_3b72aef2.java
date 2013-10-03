package com.ardais.bigr.lims.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPIncidentHomeBean_3b72aef2
 */
public class EJSCMPIncidentHomeBean_3b72aef2 extends EJSHome {
	/**
	 * EJSCMPIncidentHomeBean_3b72aef2
	 */
	public EJSCMPIncidentHomeBean_3b72aef2() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.lims.beans.Incident postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.lims.beans.Incident) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.lims.beans.Incident findByPrimaryKey(java.lang.String primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.lims.beans.EJSJDBCPersisterCMPIncidentBean_3b72aef2) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.lims.beans.Incident create(java.lang.String incidentId, java.lang.String createUser, java.sql.Timestamp createDate, java.lang.String sampleId, java.lang.String consentId, java.lang.String action, java.lang.String reason) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.lims.beans.Incident _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.lims.beans.IncidentBean bean = (com.ardais.bigr.lims.beans.IncidentBean) beanO.getEnterpriseBean();
			bean.ejbCreate(incidentId, createUser, createDate, sampleId, consentId, action, reason);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(incidentId, createUser, createDate, sampleId, consentId, action, reason);
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
		com.ardais.bigr.lims.beans.IncidentBean tmpEJB = (com.ardais.bigr.lims.beans.IncidentBean) generalEJB;
		return tmpEJB.incidentId;
	}
	/**
	 * keyFromFields
	 */
	public java.lang.String keyFromFields(java.lang.String f0) {
		return f0;
	}
}
