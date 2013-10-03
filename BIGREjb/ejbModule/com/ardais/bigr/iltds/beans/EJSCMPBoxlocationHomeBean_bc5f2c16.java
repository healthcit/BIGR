package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPBoxlocationHomeBean_bc5f2c16
 */
public class EJSCMPBoxlocationHomeBean_bc5f2c16 extends EJSHome {
	/**
	 * EJSCMPBoxlocationHomeBean_bc5f2c16
	 */
	public EJSCMPBoxlocationHomeBean_bc5f2c16() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Boxlocation postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Boxlocation) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * findBoxlocationBySamplebox
	 */
	public java.util.Enumeration findBoxlocationBySamplebox(com.ardais.bigr.iltds.beans.SampleboxKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderBoxlocationBean)persister).findBoxlocationBySamplebox(inKey));	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Boxlocation findByPrimaryKey(com.ardais.bigr.iltds.beans.BoxlocationKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPBoxlocationBean_bc5f2c16) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * findBoxlocationByGeolocation
	 */
	public java.util.Enumeration findBoxlocationByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderBoxlocationBean)persister).findBoxlocationByGeolocation(inKey));	}
	/**
	 * findBoxLocationByBoxId
	 */
	public java.util.Enumeration findBoxLocationByBoxId(java.lang.String box) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderBoxlocationBean)persister).findBoxLocationByBoxId(box));	}
	/**
	 * findBoxLocationByStorageTypeCid
	 */
	public java.util.Enumeration findBoxLocationByStorageTypeCid(java.lang.String locationAddressId, java.lang.String storageTypeCid, java.lang.String availableInd) throws javax.ejb.FinderException, java.rmi.RemoteException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderBoxlocationBean)persister).findBoxLocationByStorageTypeCid(locationAddressId, storageTypeCid, availableInd));	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Boxlocation create(com.ardais.bigr.iltds.assistants.StorageLocAsst asst) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Boxlocation _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.BoxlocationBean bean = (com.ardais.bigr.iltds.beans.BoxlocationBean) beanO.getEnterpriseBean();
			bean.ejbCreate(asst);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(asst);
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
		com.ardais.bigr.iltds.beans.BoxlocationBean tmpEJB = (com.ardais.bigr.iltds.beans.BoxlocationBean) generalEJB;
		com.ardais.bigr.iltds.beans.BoxlocationKey keyClass = new com.ardais.bigr.iltds.beans.BoxlocationKey();
		keyClass.drawer_id = tmpEJB.drawer_id;
		keyClass.geolocation_location_address_id = tmpEJB.geolocation_location_address_id;
		keyClass.room_id = tmpEJB.room_id;
		keyClass.slot_id = tmpEJB.slot_id;
		keyClass.unitName = tmpEJB.unitName;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.BoxlocationKey keyFromFields(java.lang.String f0, java.lang.String f1, java.lang.String f2, java.lang.String f3, java.lang.String f4) {
		com.ardais.bigr.iltds.beans.BoxlocationKey keyClass = new com.ardais.bigr.iltds.beans.BoxlocationKey();
		keyClass.drawer_id = f0;
		keyClass.geolocation_location_address_id = f1;
		keyClass.room_id = f2;
		keyClass.slot_id = f3;
		keyClass.unitName = f4;
		return keyClass;
	}
}
