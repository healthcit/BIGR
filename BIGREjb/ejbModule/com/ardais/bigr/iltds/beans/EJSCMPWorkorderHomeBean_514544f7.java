package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPWorkorderHomeBean_514544f7
 */
public class EJSCMPWorkorderHomeBean_514544f7 extends EJSHome {
	/**
	 * EJSCMPWorkorderHomeBean_514544f7
	 */
	public EJSCMPWorkorderHomeBean_514544f7() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Workorder postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Workorder) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * findWorkorderByProject
	 */
	public java.util.Enumeration findWorkorderByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderWorkorderBean)persister).findWorkorderByProject(inKey));	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Workorder create(java.lang.String argWorkorderid) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Workorder _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.WorkorderBean bean = (com.ardais.bigr.iltds.beans.WorkorderBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argWorkorderid);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argWorkorderid);
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
	public com.ardais.bigr.iltds.beans.Workorder findByPrimaryKey(com.ardais.bigr.iltds.beans.WorkorderKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPWorkorderBean_514544f7) persister).findByPrimaryKey(key);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.iltds.beans.Workorder create(java.lang.String argWorkorderid, java.lang.String argProjectid, java.lang.String argWorkordertype, java.lang.String argWorkordername, java.math.BigDecimal argListorder) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Workorder _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.WorkorderBean bean = (com.ardais.bigr.iltds.beans.WorkorderBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argWorkorderid, argProjectid, argWorkordertype, argWorkordername, argListorder);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argWorkorderid, argProjectid, argWorkordertype, argWorkordername, argListorder);
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
		com.ardais.bigr.iltds.beans.WorkorderBean tmpEJB = (com.ardais.bigr.iltds.beans.WorkorderBean) generalEJB;
		com.ardais.bigr.iltds.beans.WorkorderKey keyClass = new com.ardais.bigr.iltds.beans.WorkorderKey();
		keyClass.workorderid = tmpEJB.workorderid;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.WorkorderKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.WorkorderKey keyClass = new com.ardais.bigr.iltds.beans.WorkorderKey();
		keyClass.workorderid = f0;
		return keyClass;
	}
}
