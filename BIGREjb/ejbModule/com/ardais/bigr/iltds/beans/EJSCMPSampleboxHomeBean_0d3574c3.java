package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPSampleboxHomeBean_0d3574c3
 */
public class EJSCMPSampleboxHomeBean_0d3574c3 extends EJSHome {
	/**
	 * EJSCMPSampleboxHomeBean_0d3574c3
	 */
	public EJSCMPSampleboxHomeBean_0d3574c3() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Samplebox postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Samplebox) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.iltds.beans.Samplebox findByPrimaryKey(com.ardais.bigr.iltds.beans.SampleboxKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPSampleboxBean_0d3574c3) persister).findByPrimaryKey(key);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Samplebox create(java.lang.String box_barcode_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Samplebox _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.SampleboxBean bean = (com.ardais.bigr.iltds.beans.SampleboxBean) beanO.getEnterpriseBean();
			bean.ejbCreate(box_barcode_id);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(box_barcode_id);
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
	 * findSampleboxByManifest
	 */
	public java.util.Enumeration findSampleboxByManifest(com.ardais.bigr.iltds.beans.ManifestKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderSampleboxBean)persister).findSampleboxByManifest(inKey));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.SampleboxBean tmpEJB = (com.ardais.bigr.iltds.beans.SampleboxBean) generalEJB;
		com.ardais.bigr.iltds.beans.SampleboxKey keyClass = new com.ardais.bigr.iltds.beans.SampleboxKey();
		keyClass.box_barcode_id = tmpEJB.box_barcode_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.SampleboxKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.SampleboxKey keyClass = new com.ardais.bigr.iltds.beans.SampleboxKey();
		keyClass.box_barcode_id = f0;
		return keyClass;
	}
}
