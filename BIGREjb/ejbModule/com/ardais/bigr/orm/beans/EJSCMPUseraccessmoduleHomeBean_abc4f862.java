package com.ardais.bigr.orm.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPUseraccessmoduleHomeBean_abc4f862
 */
public class EJSCMPUseraccessmoduleHomeBean_abc4f862 extends EJSHome {
	/**
	 * EJSCMPUseraccessmoduleHomeBean_abc4f862
	 */
	public EJSCMPUseraccessmoduleHomeBean_abc4f862() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.orm.beans.Useraccessmodule postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.orm.beans.Useraccessmodule) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.orm.beans.Useraccessmodule findByPrimaryKey(com.ardais.bigr.orm.beans.UseraccessmoduleKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.orm.beans.EJSJDBCPersisterCMPUseraccessmoduleBean_abc4f862) persister).findByPrimaryKey(key);
	}
	/**
	 * findUseraccessmoduleByObjects
	 */
	public java.util.Enumeration findUseraccessmoduleByObjects(com.ardais.bigr.orm.beans.ObjectsKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.orm.beans.EJSFinderUseraccessmoduleBean)persister).findUseraccessmoduleByObjects(inKey));	}
	/**
	 * create
	 */
	public com.ardais.bigr.orm.beans.Useraccessmodule create(java.lang.String argArdais_acct_key, java.lang.String argArdais_user_id, com.ardais.bigr.orm.beans.ObjectsKey argObjects, java.lang.String argCreated_by, java.sql.Timestamp argCreate_date, java.lang.String argUpdated_by, java.sql.Timestamp argUpdate_date) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.orm.beans.Useraccessmodule _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.orm.beans.UseraccessmoduleBean bean = (com.ardais.bigr.orm.beans.UseraccessmoduleBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argArdais_acct_key, argArdais_user_id, argObjects, argCreated_by, argCreate_date, argUpdated_by, argUpdate_date);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argArdais_acct_key, argArdais_user_id, argObjects, argCreated_by, argCreate_date, argUpdated_by, argUpdate_date);
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
		com.ardais.bigr.orm.beans.UseraccessmoduleBean tmpEJB = (com.ardais.bigr.orm.beans.UseraccessmoduleBean) generalEJB;
		com.ardais.bigr.orm.beans.UseraccessmoduleKey keyClass = new com.ardais.bigr.orm.beans.UseraccessmoduleKey();
		keyClass.ardais_acct_key = tmpEJB.ardais_acct_key;
		keyClass.ardais_user_id = tmpEJB.ardais_user_id;
		keyClass.objects_object_id = tmpEJB.objects_object_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.orm.beans.UseraccessmoduleKey keyFromFields(java.lang.String f0, java.lang.String f1, java.lang.String f2) {
		com.ardais.bigr.orm.beans.UseraccessmoduleKey keyClass = new com.ardais.bigr.orm.beans.UseraccessmoduleKey();
		keyClass.ardais_acct_key = f0;
		keyClass.ardais_user_id = f1;
		keyClass.objects_object_id = f2;
		return keyClass;
	}
}
