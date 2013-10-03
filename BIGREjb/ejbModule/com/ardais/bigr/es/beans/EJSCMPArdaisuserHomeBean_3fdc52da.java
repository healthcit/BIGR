package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPArdaisuserHomeBean_3fdc52da
 */
public class EJSCMPArdaisuserHomeBean_3fdc52da extends EJSHome {
	/**
	 * EJSCMPArdaisuserHomeBean_3fdc52da
	 */
	public EJSCMPArdaisuserHomeBean_3fdc52da() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Ardaisuser postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Ardaisuser) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * findByUserId
	 */
	public com.ardais.bigr.es.beans.Ardaisuser findByUserId(java.lang.String userId) throws javax.ejb.FinderException, java.rmi.RemoteException {
return ((com.ardais.bigr.es.beans.EJSJDBCPersisterCMPArdaisuserBean_3fdc52da)persister).findByUserId(userId);	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisuser create(java.lang.String argArdais_user_id, com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount, java.lang.String argPassword, java.lang.String argCreated_by, java.sql.Timestamp argCreate_date, java.lang.String argPasswordPolicyCid) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisuser _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisuserBean bean = (com.ardais.bigr.es.beans.ArdaisuserBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argArdais_user_id, argArdaisaccount, argPassword, argCreated_by, argCreate_date, argPasswordPolicyCid);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argArdais_user_id, argArdaisaccount, argPassword, argCreated_by, argCreate_date, argPasswordPolicyCid);
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
	public com.ardais.bigr.es.beans.Ardaisuser create(java.lang.String ardais_user_id, com.ardais.bigr.es.beans.Ardaisaccount argArdaisaccount, java.lang.String argPasswordPolicyCid) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisuser _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisuserBean bean = (com.ardais.bigr.es.beans.ArdaisuserBean) beanO.getEnterpriseBean();
			bean.ejbCreate(ardais_user_id, argArdaisaccount, argPasswordPolicyCid);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(ardais_user_id, argArdaisaccount, argPasswordPolicyCid);
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
	public com.ardais.bigr.es.beans.Ardaisuser findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisuserKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.es.beans.EJSJDBCPersisterCMPArdaisuserBean_3fdc52da) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * findArdaisuserByArdaisaccount
	 */
	public java.util.Enumeration findArdaisuserByArdaisaccount(com.ardais.bigr.es.beans.ArdaisaccountKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisuserBean)persister).findArdaisuserByArdaisaccount(inKey));	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisuser create(java.lang.String argArdais_user_id, com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount, java.lang.String argPasswordPolicyCid) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisuser _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisuserBean bean = (com.ardais.bigr.es.beans.ArdaisuserBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argArdais_user_id, argArdaisaccount, argPasswordPolicyCid);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argArdais_user_id, argArdaisaccount, argPasswordPolicyCid);
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
		com.ardais.bigr.es.beans.ArdaisuserBean tmpEJB = (com.ardais.bigr.es.beans.ArdaisuserBean) generalEJB;
		com.ardais.bigr.es.beans.ArdaisuserKey keyClass = new com.ardais.bigr.es.beans.ArdaisuserKey();
		keyClass.ardais_user_id = tmpEJB.ardais_user_id;
		keyClass.ardaisaccount_ardais_acct_key = tmpEJB.ardaisaccount_ardais_acct_key;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.es.beans.ArdaisuserKey keyFromFields(java.lang.String f0, java.lang.String f1) {
		com.ardais.bigr.es.beans.ArdaisuserKey keyClass = new com.ardais.bigr.es.beans.ArdaisuserKey();
		keyClass.ardais_user_id = f0;
		keyClass.ardaisaccount_ardais_acct_key = f1;
		return keyClass;
	}
}
