package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPShoppingcartHomeBean_4bdce51d
 */
public class EJSCMPShoppingcartHomeBean_4bdce51d extends EJSHome {
	/**
	 * EJSCMPShoppingcartHomeBean_4bdce51d
	 */
	public EJSCMPShoppingcartHomeBean_4bdce51d() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Shoppingcart postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Shoppingcart) super.postCreate(beanO, ejsKey, true);
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
	public com.ardais.bigr.es.beans.Shoppingcart create(java.math.BigDecimal shopping_cart_id, com.ardais.bigr.es.beans.Ardaisuser argArdaisuser) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Shoppingcart _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ShoppingcartBean bean = (com.ardais.bigr.es.beans.ShoppingcartBean) beanO.getEnterpriseBean();
			bean.ejbCreate(shopping_cart_id, argArdaisuser);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(shopping_cart_id, argArdaisuser);
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
	public com.ardais.bigr.es.beans.Shoppingcart create(java.math.BigDecimal shopping_cart_id, java.lang.String userId, java.lang.String acctId) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Shoppingcart _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ShoppingcartBean bean = (com.ardais.bigr.es.beans.ShoppingcartBean) beanO.getEnterpriseBean();
			bean.ejbCreate(shopping_cart_id, userId, acctId);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(shopping_cart_id, userId, acctId);
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
	 * findByUserAccount
	 */
	public java.util.Enumeration findByUserAccount(java.lang.String user, java.lang.String account) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderShoppingcartBean)persister).findByUserAccount(user, account));	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Shoppingcart findByPrimaryKey(com.ardais.bigr.es.beans.ShoppingcartKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.es.beans.EJSJDBCPersisterCMPShoppingcartBean_4bdce51d) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * findShoppingcartByArdaisuser
	 */
	public java.util.Enumeration findShoppingcartByArdaisuser(com.ardais.bigr.es.beans.ArdaisuserKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderShoppingcartBean)persister).findShoppingcartByArdaisuser(inKey));	}
	/**
	 * keyFromBean
	 */
	public Object keyFromBean(javax.ejb.EntityBean generalEJB) {
		com.ardais.bigr.es.beans.ShoppingcartBean tmpEJB = (com.ardais.bigr.es.beans.ShoppingcartBean) generalEJB;
		com.ardais.bigr.es.beans.ShoppingcartKey keyClass = new com.ardais.bigr.es.beans.ShoppingcartKey();
		keyClass.shopping_cart_id = tmpEJB.shopping_cart_id;
		keyClass.ardaisuser_ardais_user_id = tmpEJB.ardaisuser_ardais_user_id;
		keyClass.ardaisuser_ardaisaccount_ardais_acct_key = tmpEJB.ardaisuser_ardaisaccount_ardais_acct_key;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.es.beans.ShoppingcartKey keyFromFields(java.math.BigDecimal f0, java.lang.String f1, java.lang.String f2) {
		com.ardais.bigr.es.beans.ShoppingcartKey keyClass = new com.ardais.bigr.es.beans.ShoppingcartKey();
		keyClass.shopping_cart_id = f0;
		keyClass.ardaisuser_ardais_user_id = f1;
		keyClass.ardaisuser_ardaisaccount_ardais_acct_key = f2;
		return keyClass;
	}
}
