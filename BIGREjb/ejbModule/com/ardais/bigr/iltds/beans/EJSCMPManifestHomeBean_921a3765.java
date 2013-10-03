package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPManifestHomeBean_921a3765
 */
public class EJSCMPManifestHomeBean_921a3765 extends EJSHome {
	/**
	 * EJSCMPManifestHomeBean_921a3765
	 */
	public EJSCMPManifestHomeBean_921a3765() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Manifest postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Manifest) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.iltds.beans.Manifest create(java.lang.String manifest_number) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Manifest _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.ManifestBean bean = (com.ardais.bigr.iltds.beans.ManifestBean) beanO.getEnterpriseBean();
			bean.ejbCreate(manifest_number);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(manifest_number);
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
	 * findByWaybill
	 */
	public java.util.Enumeration findByWaybill(java.lang.String waybill) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderManifestBean)persister).findByWaybill(waybill));	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Manifest findByPrimaryKey(com.ardais.bigr.iltds.beans.ManifestKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPManifestBean_921a3765) persister).findByPrimaryKey(key);
	}
	/**
	 * findByBoxID
	 */
	public java.util.Enumeration findByBoxID(java.lang.String boxID) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderManifestBean)persister).findByBoxID(boxID));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.ManifestBean tmpEJB = (com.ardais.bigr.iltds.beans.ManifestBean) generalEJB;
		com.ardais.bigr.iltds.beans.ManifestKey keyClass = new com.ardais.bigr.iltds.beans.ManifestKey();
		keyClass.manifest_number = tmpEJB.manifest_number;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.ManifestKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.ManifestKey keyClass = new com.ardais.bigr.iltds.beans.ManifestKey();
		keyClass.manifest_number = f0;
		return keyClass;
	}
}
