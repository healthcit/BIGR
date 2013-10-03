package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPArdaisorderHomeBean_3e397772
 */
public class EJSCMPArdaisorderHomeBean_3e397772 extends EJSHome {
	/**
	 * EJSCMPArdaisorderHomeBean_3e397772
	 */
	public EJSCMPArdaisorderHomeBean_3e397772() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Ardaisorder postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Ardaisorder) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * findByOrderStatus
	 */
	public java.util.Enumeration findByOrderStatus(java.lang.String ardOrder_status) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisorderBean)persister).findByOrderStatus(ardOrder_status));	}
	/**
	 * findUserOpenOrders
	 */
	public java.util.Enumeration findUserOpenOrders(java.lang.String user, java.lang.String account) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisorderBean)persister).findUserOpenOrders(user, account));	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Ardaisorder findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisorderKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.es.beans.EJSJDBCPersisterCMPArdaisorderBean_3e397772) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * findAllOrders
	 */
	public java.util.Enumeration findAllOrders() throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisorderBean)persister).findAllOrders());	}
	/**
	 * findByOrderNumber
	 */
	public java.util.Enumeration findByOrderNumber(java.lang.String orderID) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisorderBean)persister).findByOrderNumber(orderID));	}
	/**
	 * findAccountOrderswithStatus
	 */
	public java.util.Enumeration findAccountOrderswithStatus(java.lang.String arg_account, java.lang.String arg_status) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisorderBean)persister).findAccountOrderswithStatus(arg_account, arg_status));	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisorder create(java.math.BigDecimal order_number, com.ardais.bigr.es.beans.Ardaisuser argArdaisuser) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisorder _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisorderBean bean = (com.ardais.bigr.es.beans.ArdaisorderBean) beanO.getEnterpriseBean();
			bean.ejbCreate(order_number, argArdaisuser);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(order_number, argArdaisuser);
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
	 * findByAccount
	 */
	public java.util.Enumeration findByAccount(java.lang.String arg_accountID) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisorderBean)persister).findByAccount(arg_accountID));	}
	/**
	 * findUserOrderHistory
	 */
	public java.util.Enumeration findUserOrderHistory(java.lang.String user, java.lang.String account) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisorderBean)persister).findUserOrderHistory(user, account));	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Ardaisorder create(java.math.BigDecimal argOrder_number, com.ardais.bigr.es.beans.ArdaisuserKey argArdaisuser) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Ardaisorder _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ArdaisorderBean bean = (com.ardais.bigr.es.beans.ArdaisorderBean) beanO.getEnterpriseBean();
			bean.ejbCreate(argOrder_number, argArdaisuser);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(argOrder_number, argArdaisuser);
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
	 * findAccountOrderHistory
	 */
	public java.util.Enumeration findAccountOrderHistory(java.lang.String accountID) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisorderBean)persister).findAccountOrderHistory(accountID));	}
	/**
	 * findArdaisorderByArdaisuser
	 */
	public java.util.Enumeration findArdaisorderByArdaisuser(com.ardais.bigr.es.beans.ArdaisuserKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderArdaisorderBean)persister).findArdaisorderByArdaisuser(inKey));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.es.beans.ArdaisorderBean tmpEJB = (com.ardais.bigr.es.beans.ArdaisorderBean) generalEJB;
		com.ardais.bigr.es.beans.ArdaisorderKey keyClass = new com.ardais.bigr.es.beans.ArdaisorderKey();
		keyClass.order_number = tmpEJB.order_number;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.es.beans.ArdaisorderKey keyFromFields(java.math.BigDecimal f0) {
		com.ardais.bigr.es.beans.ArdaisorderKey keyClass = new com.ardais.bigr.es.beans.ArdaisorderKey();
		keyClass.order_number = f0;
		return keyClass;
	}
}
