package com.ardais.bigr.orm.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPObjectsHomeBean_0056eaf8
 */
public class EJSCMPObjectsHomeBean_0056eaf8 extends EJSHome {
	/**
	 * EJSCMPObjectsHomeBean_0056eaf8
	 */
	public EJSCMPObjectsHomeBean_0056eaf8() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.orm.beans.Objects postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.orm.beans.Objects) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.orm.beans.Objects create(java.lang.String object_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.orm.beans.Objects _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.orm.beans.ObjectsBean bean = (com.ardais.bigr.orm.beans.ObjectsBean) beanO.getEnterpriseBean();
			bean.ejbCreate(object_id);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(object_id);
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
	public com.ardais.bigr.orm.beans.Objects findByPrimaryKey(com.ardais.bigr.orm.beans.ObjectsKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.orm.beans.EJSJDBCPersisterCMPObjectsBean_0056eaf8) persister).findByPrimaryKey(key);
	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.orm.beans.ObjectsBean tmpEJB = (com.ardais.bigr.orm.beans.ObjectsBean) generalEJB;
		com.ardais.bigr.orm.beans.ObjectsKey keyClass = new com.ardais.bigr.orm.beans.ObjectsKey();
		keyClass.object_id = tmpEJB.object_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.orm.beans.ObjectsKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.orm.beans.ObjectsKey keyClass = new com.ardais.bigr.orm.beans.ObjectsKey();
		keyClass.object_id = f0;
		return keyClass;
	}
}
