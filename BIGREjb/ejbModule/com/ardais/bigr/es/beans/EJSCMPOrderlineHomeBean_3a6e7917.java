package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPOrderlineHomeBean_3a6e7917
 */
public class EJSCMPOrderlineHomeBean_3a6e7917 extends EJSHome {
	/**
	 * EJSCMPOrderlineHomeBean_3a6e7917
	 */
	public EJSCMPOrderlineHomeBean_3a6e7917() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Orderline postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Orderline) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.es.beans.Orderline create(java.math.BigDecimal order_line_number, com.ardais.bigr.es.beans.Ardaisorder argArdaisorder) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Orderline _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.OrderlineBean bean = (com.ardais.bigr.es.beans.OrderlineBean) beanO.getEnterpriseBean();
			bean.ejbCreate(order_line_number, argArdaisorder);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(order_line_number, argArdaisorder);
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
	public com.ardais.bigr.es.beans.Orderline create(java.math.BigDecimal argOrder_line_number, com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Orderline _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.OrderlineBean bean = (com.ardais.bigr.es.beans.OrderlineBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argOrder_line_number, argArdaisorder);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argOrder_line_number, argArdaisorder);
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
	 * findOrderlineByArdaisorder
	 */
	public java.util.Enumeration findOrderlineByArdaisorder(com.ardais.bigr.es.beans.ArdaisorderKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderOrderlineBean)persister).findOrderlineByArdaisorder(inKey));	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Orderline findByPrimaryKey(com.ardais.bigr.es.beans.OrderlineKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.es.beans.EJSJDBCPersisterCMPOrderlineBean_3a6e7917) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.es.beans.OrderlineBean tmpEJB = (com.ardais.bigr.es.beans.OrderlineBean) generalEJB;
		com.ardais.bigr.es.beans.OrderlineKey keyClass = new com.ardais.bigr.es.beans.OrderlineKey();
		keyClass.ardaisorder_order_number = tmpEJB.ardaisorder_order_number;
		keyClass.order_line_number = tmpEJB.order_line_number;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.es.beans.OrderlineKey keyFromFields(java.math.BigDecimal f0, java.math.BigDecimal f1) {
		com.ardais.bigr.es.beans.OrderlineKey keyClass = new com.ardais.bigr.es.beans.OrderlineKey();
		keyClass.ardaisorder_order_number = f0;
		keyClass.order_line_number = f1;
		return keyClass;
	}
}
