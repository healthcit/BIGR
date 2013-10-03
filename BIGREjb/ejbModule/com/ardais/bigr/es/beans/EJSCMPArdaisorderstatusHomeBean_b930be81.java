package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPArdaisorderstatusHomeBean_b930be81
 */
public class EJSCMPArdaisorderstatusHomeBean_b930be81 extends EJSHome {
	/**
	 * EJSCMPArdaisorderstatusHomeBean_b930be81
	 */
	public EJSCMPArdaisorderstatusHomeBean_b930be81() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Ardaisorderstatus postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Ardaisorderstatus) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.es.beans.Ardaisorderstatus create(java.sql.Timestamp argOrder_status_date, java.lang.String argOrder_status_id, com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder, java.lang.String argArdais_acct_key, java.lang.String argArdais_user_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisorderstatus _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisorderstatusBean bean = (com.ardais.bigr.es.beans.ArdaisorderstatusBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argOrder_status_date, argOrder_status_id, argArdaisorder, argArdais_acct_key, argArdais_user_id);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argOrder_status_date, argOrder_status_id, argArdaisorder, argArdais_acct_key, argArdais_user_id);
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
	public com.ardais.bigr.es.beans.Ardaisorderstatus findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisorderstatusKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.es.beans.EJSJDBCPersisterCMPArdaisorderstatusBean_b930be81) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisorderstatus create(java.lang.String ardais_acct_key, java.lang.String order_status_id, java.sql.Timestamp order_status_date, java.lang.String ardais_user_id, com.ardais.bigr.es.beans.Ardaisorder argArdaisorder) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisorderstatus _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisorderstatusBean bean = (com.ardais.bigr.es.beans.ArdaisorderstatusBean) beanO.getEnterpriseBean();
			bean.ejbCreate(ardais_acct_key, order_status_id, order_status_date, ardais_user_id, argArdaisorder);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(ardais_acct_key, order_status_id, order_status_date, ardais_user_id, argArdaisorder);
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
	 * findArdaisorderstatusByArdaisorder
	 */
	public java.util.Enumeration findArdaisorderstatusByArdaisorder(com.ardais.bigr.es.beans.ArdaisorderKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisorderstatusBean)persister).findArdaisorderstatusByArdaisorder(inKey));	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisorderstatus create(java.sql.Timestamp order_status_date, java.lang.String order_status_id, java.lang.String ardais_acct_key, java.lang.String ardais_user_id, com.ardais.bigr.es.beans.Ardaisorder argArdaisorder) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisorderstatus _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisorderstatusBean bean = (com.ardais.bigr.es.beans.ArdaisorderstatusBean) beanO.getEnterpriseBean();
			bean.ejbCreate(order_status_date, order_status_id, ardais_acct_key, ardais_user_id, argArdaisorder);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(order_status_date, order_status_id, ardais_acct_key, ardais_user_id, argArdaisorder);
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
		com.ardais.bigr.es.beans.ArdaisorderstatusBean tmpEJB = (com.ardais.bigr.es.beans.ArdaisorderstatusBean) generalEJB;
		com.ardais.bigr.es.beans.ArdaisorderstatusKey keyClass = new com.ardais.bigr.es.beans.ArdaisorderstatusKey();
		keyClass.order_status_date = tmpEJB.order_status_date;
		keyClass.order_status_id = tmpEJB.order_status_id;
		keyClass.ardaisorder_order_number = tmpEJB.ardaisorder_order_number;
		keyClass.ardais_acct_key = tmpEJB.ardais_acct_key;
		keyClass.ardais_user_id = tmpEJB.ardais_user_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.es.beans.ArdaisorderstatusKey keyFromFields(java.sql.Timestamp f0, java.lang.String f1, java.math.BigDecimal f2, java.lang.String f3, java.lang.String f4) {
		com.ardais.bigr.es.beans.ArdaisorderstatusKey keyClass = new com.ardais.bigr.es.beans.ArdaisorderstatusKey();
		keyClass.order_status_date = f0;
		keyClass.order_status_id = f1;
		keyClass.ardaisorder_order_number = f2;
		keyClass.ardais_acct_key = f3;
		keyClass.ardais_user_id = f4;
		return keyClass;
	}
}
