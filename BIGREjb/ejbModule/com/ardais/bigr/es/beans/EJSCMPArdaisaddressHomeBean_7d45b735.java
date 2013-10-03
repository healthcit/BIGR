package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPArdaisaddressHomeBean_7d45b735
 */
public class EJSCMPArdaisaddressHomeBean_7d45b735 extends EJSHome {
	/**
	 * EJSCMPArdaisaddressHomeBean_7d45b735
	 */
	public EJSCMPArdaisaddressHomeBean_7d45b735() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Ardaisaddress postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Ardaisaddress) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.es.beans.Ardaisaddress findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisaddressKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.es.beans.EJSJDBCPersisterCMPArdaisaddressBean_7d45b735) persister).findByPrimaryKey(key);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisaddress create(java.math.BigDecimal argAddress_id, java.lang.String argArdais_acct_key) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisaddress _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisaddressBean bean = (com.ardais.bigr.es.beans.ArdaisaddressBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argAddress_id, argArdais_acct_key);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argAddress_id, argArdais_acct_key);
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
	public com.ardais.bigr.es.beans.Ardaisaddress create(java.math.BigDecimal argAddress_id, java.lang.String argArdais_acct_key, java.lang.String argAddress1) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisaddress _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisaddressBean bean = (com.ardais.bigr.es.beans.ArdaisaddressBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argAddress_id, argArdais_acct_key, argAddress1);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argAddress_id, argArdais_acct_key, argAddress1);
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
	 * findByAccountandType
	 */
	public java.util.Enumeration findByAccountandType(java.lang.String arg_Account, java.lang.String arg_Type) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisaddressBean)persister).findByAccountandType(arg_Account, arg_Type));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.es.beans.ArdaisaddressBean tmpEJB = (com.ardais.bigr.es.beans.ArdaisaddressBean) generalEJB;
		com.ardais.bigr.es.beans.ArdaisaddressKey keyClass = new com.ardais.bigr.es.beans.ArdaisaddressKey();
		keyClass.address_id = tmpEJB.address_id;
		keyClass.ardais_acct_key = tmpEJB.ardais_acct_key;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.es.beans.ArdaisaddressKey keyFromFields(java.math.BigDecimal f0, java.lang.String f1) {
		com.ardais.bigr.es.beans.ArdaisaddressKey keyClass = new com.ardais.bigr.es.beans.ArdaisaddressKey();
		keyClass.address_id = f0;
		keyClass.ardais_acct_key = f1;
		return keyClass;
	}
}
