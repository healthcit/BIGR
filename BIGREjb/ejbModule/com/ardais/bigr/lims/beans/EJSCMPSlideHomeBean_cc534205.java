package com.ardais.bigr.lims.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPSlideHomeBean_cc534205
 */
public class EJSCMPSlideHomeBean_cc534205 extends EJSHome {
	/**
	 * EJSCMPSlideHomeBean_cc534205
	 */
	public EJSCMPSlideHomeBean_cc534205() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.lims.beans.Slide postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.lims.beans.Slide) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.lims.beans.Slide create(java.lang.String slideId, java.sql.Timestamp createDate, java.lang.String sampleBarcodeId, java.lang.String createUser) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.lims.beans.Slide _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.lims.beans.SlideBean bean = (com.ardais.bigr.lims.beans.SlideBean) beanO.getEnterpriseBean();
			bean.ejbCreate(slideId, createDate, sampleBarcodeId, createUser);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(slideId, createDate, sampleBarcodeId, createUser);
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
	public com.ardais.bigr.lims.beans.Slide findByPrimaryKey(java.lang.String primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.lims.beans.EJSJDBCPersisterCMPSlideBean_cc534205) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.lims.beans.Slide create(java.lang.String slideId) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.lims.beans.Slide _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.lims.beans.SlideBean bean = (com.ardais.bigr.lims.beans.SlideBean) beanO.getEnterpriseBean();
			bean.ejbCreate(slideId);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(slideId);
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
		com.ardais.bigr.lims.beans.SlideBean tmpEJB = (com.ardais.bigr.lims.beans.SlideBean) generalEJB;
		return tmpEJB.slideId;
	}
	/**
	 * keyFromFields
	 */
	public java.lang.String keyFromFields(java.lang.String f0) {
		return f0;
	}
}
