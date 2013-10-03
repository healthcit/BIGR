package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPRevokedconsentHomeBean_9e86b52d
 */
public class EJSCMPRevokedconsentHomeBean_9e86b52d extends EJSHome {
	/**
	 * EJSCMPRevokedconsentHomeBean_9e86b52d
	 */
	public EJSCMPRevokedconsentHomeBean_9e86b52d() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Revokedconsent postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Revokedconsent) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.iltds.beans.Revokedconsent create(java.lang.String argConsent_id, java.lang.String argArdais_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Revokedconsent _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.RevokedconsentBean bean = (com.ardais.bigr.iltds.beans.RevokedconsentBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argConsent_id, argArdais_id);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argConsent_id, argArdais_id);
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
	public com.ardais.bigr.iltds.beans.Revokedconsent create(java.lang.String argConsent_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Revokedconsent _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.RevokedconsentBean bean = (com.ardais.bigr.iltds.beans.RevokedconsentBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argConsent_id);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argConsent_id);
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
	public com.ardais.bigr.iltds.beans.Revokedconsent findByPrimaryKey(com.ardais.bigr.iltds.beans.RevokedconsentKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPRevokedconsentBean_9e86b52d) persister).findByPrimaryKey(key);
	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.RevokedconsentBean tmpEJB = (com.ardais.bigr.iltds.beans.RevokedconsentBean) generalEJB;
		com.ardais.bigr.iltds.beans.RevokedconsentKey keyClass = new com.ardais.bigr.iltds.beans.RevokedconsentKey();
		keyClass.consent_id = tmpEJB.consent_id;
		keyClass.ardais_id = tmpEJB.ardais_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.RevokedconsentKey keyFromFields(java.lang.String f0, java.lang.String f1) {
		com.ardais.bigr.iltds.beans.RevokedconsentKey keyClass = new com.ardais.bigr.iltds.beans.RevokedconsentKey();
		keyClass.consent_id = f0;
		keyClass.ardais_id = f1;
		return keyClass;
	}
}
