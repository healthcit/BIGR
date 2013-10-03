package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPLineitemHomeBean_7206cc70
 */
public class EJSCMPLineitemHomeBean_7206cc70 extends EJSHome {
	/**
	 * EJSCMPLineitemHomeBean_7206cc70
	 */
	public EJSCMPLineitemHomeBean_7206cc70() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Lineitem postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Lineitem) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.iltds.beans.Lineitem create(java.lang.String argLineitemid, java.lang.String argProjectid, java.math.BigDecimal argLineitemnumber, java.math.BigDecimal argQuantity) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Lineitem _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.LineitemBean bean = (com.ardais.bigr.iltds.beans.LineitemBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argLineitemid, argProjectid, argLineitemnumber, argQuantity);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argLineitemid, argProjectid, argLineitemnumber, argQuantity);
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
	public com.ardais.bigr.iltds.beans.Lineitem create(java.lang.String argLineitemid) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.iltds.beans.Lineitem _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.iltds.beans.LineitemBean bean = (com.ardais.bigr.iltds.beans.LineitemBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argLineitemid);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argLineitemid);
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
	public com.ardais.bigr.iltds.beans.Lineitem findByPrimaryKey(com.ardais.bigr.iltds.beans.LineitemKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return ((com.ardais.bigr.iltds.beans.EJSJDBCPersisterCMPLineitemBean_7206cc70) persister).findByPrimaryKey(key);
	}
	/**
	 * findLineitemByProject
	 */
	public java.util.Enumeration findLineitemByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.iltds.beans.EJSFinderLineitemBean)persister).findLineitemByProject(inKey));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.iltds.beans.LineitemBean tmpEJB = (com.ardais.bigr.iltds.beans.LineitemBean) generalEJB;
		com.ardais.bigr.iltds.beans.LineitemKey keyClass = new com.ardais.bigr.iltds.beans.LineitemKey();
		keyClass.lineitemid = tmpEJB.lineitemid;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.iltds.beans.LineitemKey keyFromFields(java.lang.String f0) {
		com.ardais.bigr.iltds.beans.LineitemKey keyClass = new com.ardais.bigr.iltds.beans.LineitemKey();
		keyClass.lineitemid = f0;
		return keyClass;
	}
}
