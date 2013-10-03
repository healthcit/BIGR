package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPConsentHomeBean_12d3e012
 */
public class EJSCMPConsentHomeBean_12d3e012 extends EJSHome {
	/**
	 * EJSCMPConsentHomeBean_12d3e012
	 */
	public EJSCMPConsentHomeBean_12d3e012() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Consent postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Consent) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.iltds.beans.Consent findByPrimaryKey(com.ardais.bigr.iltds.beans.ConsentKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPConsentBean_12d3e012) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * findConsentByGeolocation
	 */
	public java.util.Enumeration findConsentByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderConsentBean)persister).findConsentByGeolocation(inKey));	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Consent create(java.lang.String consent_id, java.math.BigDecimal policy_id, java.lang.String ardais_id, java.lang.String imported_yn, java.lang.String ardais_acct_key, java.lang.String case_registration_form) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Consent _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.ConsentBean bean = (com.ardais.bigr.iltds.beans.ConsentBean) beanO.getEnterpriseBean();
			bean.ejbCreate(consent_id, policy_id, ardais_id, imported_yn, ardais_acct_key, case_registration_form);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(consent_id, policy_id, ardais_id, imported_yn, ardais_acct_key, case_registration_form);
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
	 * findConsentByArdaisID
	 */
	public java.util.Enumeration findConsentByArdaisID(java.lang.String consentID) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderConsentBean)persister).findConsentByArdaisID(consentID));	}
	/**
	 * findByConsentID
	 */
	public java.util.Enumeration findByConsentID(java.lang.String consentID) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderConsentBean)persister).findByConsentID(consentID));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.ConsentBean tmpEJB = (com.ardais.bigr.iltds.beans.ConsentBean) generalEJB;
		com.ardais.bigr.iltds.beans.ConsentKey keyClass = new com.ardais.bigr.iltds.beans.ConsentKey();
		keyClass.consent_id = tmpEJB.consent_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.ConsentKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.ConsentKey keyClass = new com.ardais.bigr.iltds.beans.ConsentKey();
		keyClass.consent_id = f0;
		return keyClass;
	}
}
