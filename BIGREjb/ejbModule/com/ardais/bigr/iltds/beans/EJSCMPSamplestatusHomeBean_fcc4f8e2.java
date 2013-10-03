package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPSamplestatusHomeBean_fcc4f8e2
 */
public class EJSCMPSamplestatusHomeBean_fcc4f8e2 extends EJSHome {
	/**
	 * EJSCMPSamplestatusHomeBean_fcc4f8e2
	 */
	public EJSCMPSamplestatusHomeBean_fcc4f8e2() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Samplestatus postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Samplestatus) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * findSamplestatusBySample
	 */
	public java.util.Enumeration findSamplestatusBySample(com.ardais.bigr.iltds.beans.SampleKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderSamplestatusBean)persister).findSamplestatusBySample(inKey));	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Samplestatus create(java.math.BigDecimal id, com.ardais.bigr.iltds.beans.Sample argSample) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Samplestatus _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.SamplestatusBean bean = (com.ardais.bigr.iltds.beans.SamplestatusBean) beanO.getEnterpriseBean();
			bean.ejbCreate(id, argSample);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(id, argSample);
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
	public com.ardais.bigr.iltds.beans.Samplestatus findByPrimaryKey(com.ardais.bigr.iltds.beans.SamplestatusKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPSamplestatusBean_fcc4f8e2) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Samplestatus create(java.lang.String argStatus_type_code, com.ardais.bigr.iltds.beans.SampleKey argSample, java.sql.Timestamp argSample_status_datetime, java.math.BigDecimal argId) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Samplestatus _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.SamplestatusBean bean = (com.ardais.bigr.iltds.beans.SamplestatusBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argStatus_type_code, argSample, argSample_status_datetime, argId);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argStatus_type_code, argSample, argSample_status_datetime, argId);
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
	public com.ardais.bigr.iltds.beans.Samplestatus create(java.lang.String argStatus_type_code, com.ardais.bigr.iltds.beans.SampleKey argSample, java.sql.Timestamp argSample_status_datetime) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Samplestatus _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.SamplestatusBean bean = (com.ardais.bigr.iltds.beans.SamplestatusBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argStatus_type_code, argSample, argSample_status_datetime);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argStatus_type_code, argSample, argSample_status_datetime);
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
	 * findBySampleIDStatus
	 */
	public java.util.Enumeration findBySampleIDStatus(java.lang.String sampleID, java.lang.String statusID) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderSamplestatusBean)persister).findBySampleIDStatus(sampleID, statusID));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.SamplestatusBean tmpEJB = (com.ardais.bigr.iltds.beans.SamplestatusBean) generalEJB;
		com.ardais.bigr.iltds.beans.SamplestatusKey keyClass = new com.ardais.bigr.iltds.beans.SamplestatusKey();
		keyClass.id = tmpEJB.id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.SamplestatusKey keyFromFields(java.math.BigDecimal f0) {
		com.ardais.bigr.iltds.beans.SamplestatusKey keyClass = new com.ardais.bigr.iltds.beans.SamplestatusKey();
		keyClass.id = f0;
		return keyClass;
	}
}
