package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPArdaisaccountHomeBean_7f00ab2e
 */
public class EJSCMPArdaisaccountHomeBean_7f00ab2e extends EJSHome {
	/**
	 * EJSCMPArdaisaccountHomeBean_7f00ab2e
	 */
	public EJSCMPArdaisaccountHomeBean_7f00ab2e() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Ardaisaccount postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Ardaisaccount) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisaccount create(java.lang.String ardais_acct_key) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisaccount _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisaccountBean bean = (com.ardais.bigr.es.beans.ArdaisaccountBean) beanO.getEnterpriseBean();
			bean.ejbCreate(ardais_acct_key);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(ardais_acct_key);
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
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisaccount create(java.lang.String ardais_acct_key, java.lang.String linked_cases_only_yn, java.lang.String argPasswordPolicyCid, int argPasswordLifeSpan) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisaccount _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisaccountBean bean = (com.ardais.bigr.es.beans.ArdaisaccountBean) beanO.getEnterpriseBean();
			bean.ejbCreate(ardais_acct_key, linked_cases_only_yn, argPasswordPolicyCid, argPasswordLifeSpan);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(ardais_acct_key, linked_cases_only_yn, argPasswordPolicyCid, argPasswordLifeSpan);
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
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Ardaisaccount findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisaccountKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.es.beans.EJSJDBCPersisterCMPArdaisaccountBean_7f00ab2e) persister).findByPrimaryKey(key);
	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.es.beans.ArdaisaccountBean tmpEJB = (com.ardais.bigr.es.beans.ArdaisaccountBean) generalEJB;
		com.ardais.bigr.es.beans.ArdaisaccountKey keyClass = new com.ardais.bigr.es.beans.ArdaisaccountKey();
		keyClass.ardais_acct_key = tmpEJB.ardais_acct_key;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.es.beans.ArdaisaccountKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.es.beans.ArdaisaccountKey keyClass = new com.ardais.bigr.es.beans.ArdaisaccountKey();
		keyClass.ardais_acct_key = f0;
		return keyClass;
	}
}
