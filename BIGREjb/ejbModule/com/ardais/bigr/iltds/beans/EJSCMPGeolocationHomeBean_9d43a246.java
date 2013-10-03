package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPGeolocationHomeBean_9d43a246
 */
public class EJSCMPGeolocationHomeBean_9d43a246 extends EJSHome {
	/**
	 * EJSCMPGeolocationHomeBean_9d43a246
	 */
	public EJSCMPGeolocationHomeBean_9d43a246() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Geolocation postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Geolocation) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.iltds.beans.Geolocation create(com.ardais.bigr.iltds.assistants.LocationManagementAsst locAsst) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Geolocation _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.GeolocationBean bean = (com.ardais.bigr.iltds.beans.GeolocationBean) beanO.getEnterpriseBean();
			bean.ejbCreate(locAsst);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(locAsst);
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
	public com.ardais.bigr.iltds.beans.Geolocation findByPrimaryKey(com.ardais.bigr.iltds.beans.GeolocationKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPGeolocationBean_9d43a246) persister).findByPrimaryKey(key);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Geolocation create(java.lang.String location_address_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Geolocation _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.GeolocationBean bean = (com.ardais.bigr.iltds.beans.GeolocationBean) beanO.getEnterpriseBean();
			bean.ejbCreate(location_address_id);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(location_address_id);
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
		com.ardais.bigr.iltds.beans.GeolocationBean tmpEJB = (com.ardais.bigr.iltds.beans.GeolocationBean) generalEJB;
		com.ardais.bigr.iltds.beans.GeolocationKey keyClass = new com.ardais.bigr.iltds.beans.GeolocationKey();
		keyClass.location_address_id = tmpEJB.location_address_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.GeolocationKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.GeolocationKey keyClass = new com.ardais.bigr.iltds.beans.GeolocationKey();
		keyClass.location_address_id = f0;
		return keyClass;
	}
}
