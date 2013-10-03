package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPProjectHomeBean_979479c7
 */
public class EJSCMPProjectHomeBean_979479c7 extends EJSHome {
	/**
	 * EJSCMPProjectHomeBean_979479c7
	 */
	public EJSCMPProjectHomeBean_979479c7() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Project postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Project) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.iltds.beans.Project create(java.lang.String argProjectid) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Project _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.ProjectBean bean = (com.ardais.bigr.iltds.beans.ProjectBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argProjectid);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argProjectid);
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
	public com.ardais.bigr.iltds.beans.Project findByPrimaryKey(com.ardais.bigr.iltds.beans.ProjectKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPProjectBean_979479c7) persister).findByPrimaryKey(key);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Project create(java.lang.String argProjectid, java.lang.String projectName, java.lang.String client, java.lang.String requestedBy, java.sql.Timestamp requestDate) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Project _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.ProjectBean bean = (com.ardais.bigr.iltds.beans.ProjectBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argProjectid, projectName, client, requestedBy, requestDate);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argProjectid, projectName, client, requestedBy, requestDate);
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
		com.ardais.bigr.iltds.beans.ProjectBean tmpEJB = (com.ardais.bigr.iltds.beans.ProjectBean) generalEJB;
		com.ardais.bigr.iltds.beans.ProjectKey keyClass = new com.ardais.bigr.iltds.beans.ProjectKey();
		keyClass.projectid = tmpEJB.projectid;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.ProjectKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.ProjectKey keyClass = new com.ardais.bigr.iltds.beans.ProjectKey();
		keyClass.projectid = f0;
		return keyClass;
	}
}
