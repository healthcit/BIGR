package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPAsmformHomeBean_05e60265
 */
public class EJSCMPAsmformHomeBean_05e60265 extends EJSHome {
	/**
	 * EJSCMPAsmformHomeBean_05e60265
	 */
	public EJSCMPAsmformHomeBean_05e60265() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Asmform postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Asmform) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.iltds.beans.Asmform create(java.lang.String asm_form_id, com.ardais.bigr.iltds.beans.Consent argConsent) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Asmform _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.AsmformBean bean = (com.ardais.bigr.iltds.beans.AsmformBean) beanO.getEnterpriseBean();
			bean.ejbCreate(asm_form_id, argConsent);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(asm_form_id, argConsent);
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
	public com.ardais.bigr.iltds.beans.Asmform create(java.lang.String argAsm_form_id, com.ardais.bigr.iltds.beans.ConsentKey argConsent) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Asmform _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.AsmformBean bean = (com.ardais.bigr.iltds.beans.AsmformBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argAsm_form_id, argConsent);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argAsm_form_id, argConsent);
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
	public com.ardais.bigr.iltds.beans.Asmform findByPrimaryKey(com.ardais.bigr.iltds.beans.AsmformKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPAsmformBean_05e60265) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * findAsmformByConsent
	 */
	public java.util.Enumeration findAsmformByConsent(com.ardais.bigr.iltds.beans.ConsentKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderAsmformBean)persister).findAsmformByConsent(inKey));	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Asmform create(java.lang.String argAsm_form_id, com.ardais.bigr.iltds.beans.ConsentKey argConsent, com.ardais.bigr.iltds.beans.ArdaisstaffKey argArdaisstaff, java.lang.String argArdais_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Asmform _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.AsmformBean bean = (com.ardais.bigr.iltds.beans.AsmformBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argAsm_form_id, argConsent, argArdaisstaff, argArdais_id);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argAsm_form_id, argConsent, argArdaisstaff, argArdais_id);
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
	public com.ardais.bigr.iltds.beans.Asmform create(java.lang.String argAsm_form_id, com.ardais.bigr.iltds.beans.ConsentKey argConsent, com.ardais.bigr.iltds.beans.ArdaisstaffKey argArdaisstaff) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Asmform _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.AsmformBean bean = (com.ardais.bigr.iltds.beans.AsmformBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argAsm_form_id, argConsent, argArdaisstaff);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argAsm_form_id, argConsent, argArdaisstaff);
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
	public com.ardais.bigr.iltds.beans.Asmform create(java.lang.String argAsm_form_id, com.ardais.bigr.iltds.beans.ConsentKey argConsent, java.lang.String argArdais_id) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Asmform _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.AsmformBean bean = (com.ardais.bigr.iltds.beans.AsmformBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argAsm_form_id, argConsent, argArdais_id);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argAsm_form_id, argConsent, argArdais_id);
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
	 * findByASMFormID
	 */
	public java.util.Enumeration findByASMFormID(java.lang.String asmFormID) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderAsmformBean)persister).findByASMFormID(asmFormID));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.AsmformBean tmpEJB = (com.ardais.bigr.iltds.beans.AsmformBean) generalEJB;
		com.ardais.bigr.iltds.beans.AsmformKey keyClass = new com.ardais.bigr.iltds.beans.AsmformKey();
		keyClass.asm_form_id = tmpEJB.asm_form_id;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.AsmformKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.AsmformKey keyClass = new com.ardais.bigr.iltds.beans.AsmformKey();
		keyClass.asm_form_id = f0;
		return keyClass;
	}
}
