package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPArdaisstaffHomeBean_9351b184
 */
public class EJSCMPArdaisstaffHomeBean_9351b184 extends EJSHome {
	/**
	 * EJSCMPArdaisstaffHomeBean_9351b184
	 */
	public EJSCMPArdaisstaffHomeBean_9351b184() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Ardaisstaff postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Ardaisstaff) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * findAllStaffMembers
	 */
	public java.util.Enumeration findAllStaffMembers() throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderArdaisstaffBean)persister).findAllStaffMembers());	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Ardaisstaff create(java.lang.String ardais_staff_id, com.ardais.bigr.iltds.beans.Geolocation argGeolocation) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Ardaisstaff _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.ArdaisstaffBean bean = (com.ardais.bigr.iltds.beans.ArdaisstaffBean) beanO.getEnterpriseBean();
			bean.ejbCreate(ardais_staff_id, argGeolocation);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(ardais_staff_id, argGeolocation);
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
	public com.ardais.bigr.iltds.beans.Ardaisstaff findByPrimaryKey(com.ardais.bigr.iltds.beans.ArdaisstaffKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPArdaisstaffBean_9351b184) persister).findByPrimaryKey(key);
	}
	/**
	 * findLocByUserProf
	 */
	public java.util.Enumeration findLocByUserProf(java.lang.String ardais_user_id, java.lang.String ardais_acct_key) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderArdaisstaffBean)persister).findLocByUserProf(ardais_user_id, ardais_acct_key));	}
	/**
	 * findArdaisstaffByGeolocation
	 */
	public java.util.Enumeration findArdaisstaffByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderArdaisstaffBean)persister).findArdaisstaffByGeolocation(inKey));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.ArdaisstaffBean tmpEJB = (com.ardais.bigr.iltds.beans.ArdaisstaffBean) generalEJB;
		com.ardais.bigr.iltds.beans.ArdaisstaffKey keyClass = new com.ardais.bigr.iltds.beans.ArdaisstaffKey();
		keyClass.ardais_staff_id = tmpEJB.ardais_staff_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.ArdaisstaffKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.ArdaisstaffKey keyClass = new com.ardais.bigr.iltds.beans.ArdaisstaffKey();
		keyClass.ardais_staff_id = f0;
		return keyClass;
	}
}
