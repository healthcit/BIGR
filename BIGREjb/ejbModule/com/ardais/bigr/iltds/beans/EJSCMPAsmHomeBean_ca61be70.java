package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPAsmHomeBean_ca61be70
 */
public class EJSCMPAsmHomeBean_ca61be70 extends EJSHome {
	/**
	 * EJSCMPAsmHomeBean_ca61be70
	 */
	public EJSCMPAsmHomeBean_ca61be70() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Asm postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Asm) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.iltds.beans.Asm create(java.lang.String asm_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Asm _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.AsmBean bean = (com.ardais.bigr.iltds.beans.AsmBean) beanO.getEnterpriseBean();
			bean.ejbCreate(asm_id);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(asm_id);
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
	public com.ardais.bigr.iltds.beans.Asm findByPrimaryKey(com.ardais.bigr.iltds.beans.AsmKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPAsmBean_ca61be70) persister).findByPrimaryKey(key);
	}
	/**
	 * findByConsentID
	 */
	public java.util.Enumeration findByConsentID(java.lang.String argConsentID) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderAsmBean)persister).findByConsentID(argConsentID));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.AsmBean tmpEJB = (com.ardais.bigr.iltds.beans.AsmBean) generalEJB;
		com.ardais.bigr.iltds.beans.AsmKey keyClass = new com.ardais.bigr.iltds.beans.AsmKey();
		keyClass.asm_id = tmpEJB.asm_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.AsmKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.AsmKey keyClass = new com.ardais.bigr.iltds.beans.AsmKey();
		keyClass.asm_id = f0;
		return keyClass;
	}
}
