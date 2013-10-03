package com.ardais.bigr.es.beans;

import com.ibm.ejs.container.*;

/**
 * EJSCMPShoppingcartdetailHomeBean_a81a2bea
 */
public class EJSCMPShoppingcartdetailHomeBean_a81a2bea extends EJSHome {
	/**
	 * EJSCMPShoppingcartdetailHomeBean_a81a2bea
	 */
	public EJSCMPShoppingcartdetailHomeBean_a81a2bea() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Shoppingcartdetail postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Shoppingcartdetail) super.postCreate(beanO, ejsKey, true);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		super.afterPostCreate(beanO, ejsKey);
	}
	/**
	 * findShoppingcartdetailByShoppingcart
	 */
	public java.util.Enumeration findShoppingcartdetailByShoppingcart(com.ardais.bigr.es.beans.ShoppingcartKey inKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderShoppingcartdetailBean)persister).findShoppingcartdetailByShoppingcart(inKey));	}
	/**
	 * findByBarcodeUserAccount
	 */
	public java.util.Enumeration findByBarcodeUserAccount(java.lang.String barcode_arg, java.lang.String user_arg, java.lang.String account_arg) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderShoppingcartdetailBean)persister).findByBarcodeUserAccount(barcode_arg, user_arg, account_arg));	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Shoppingcartdetail create(java.math.BigDecimal shopping_cart_line_number, com.ardais.bigr.es.beans.Shoppingcart argShoppingcart) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Shoppingcartdetail _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ShoppingcartdetailBean bean = (com.ardais.bigr.es.beans.ShoppingcartdetailBean) beanO.getEnterpriseBean();
			bean.ejbCreate(shopping_cart_line_number, argShoppingcart);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(shopping_cart_line_number, argShoppingcart);
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
	 * findByUserAccountOrderByLineNumber
	 */
	public java.util.Enumeration findByUserAccountOrderByLineNumber(java.lang.String user, java.lang.String account) throws java.rmi.RemoteException, javax.ejb.FinderException {
return super.getEnumeration(((com.ardais.bigr.es.beans.EJSFinderShoppingcartdetailBean)persister).findByUserAccountOrderByLineNumber(user, account));	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Shoppingcartdetail findByPrimaryKey(com.ardais.bigr.es.beans.ShoppingcartdetailKey primaryKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		return ((com.ardais.bigr.es.beans.EJSJDBCPersisterCMPShoppingcartdetailBean_a81a2bea) persister).findByPrimaryKey(primaryKey);
	}
	/**
	 * create
	 */
	public com.ardais.bigr.es.beans.Shoppingcartdetail create(java.lang.String barcode_id, java.lang.String productType, java.math.BigDecimal shopping_cart_line_number, java.lang.String userId, java.lang.String acctId, java.math.BigDecimal cartNo) throws javax.ejb.CreateException, java.rmi.RemoteException {
		BeanO beanO = null;
		com.ardais.bigr.es.beans.Shoppingcartdetail _EJS_result = null;
		boolean createFailed = false;
		try {
			beanO = super.createBeanO();
			com.ardais.bigr.es.beans.ShoppingcartdetailBean bean = (com.ardais.bigr.es.beans.ShoppingcartdetailBean) beanO.getEnterpriseBean();
			bean.ejbCreate(barcode_id, productType, shopping_cart_line_number, userId, acctId, cartNo);
			Object _primaryKey = keyFromBean(bean);
_EJS_result = postCreateWrapper(beanO, _primaryKey);
			bean.ejbPostCreate(barcode_id, productType, shopping_cart_line_number, userId, acctId, cartNo);
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
		com.ardais.bigr.es.beans.ShoppingcartdetailBean tmpEJB = (com.ardais.bigr.es.beans.ShoppingcartdetailBean) generalEJB;
		com.ardais.bigr.es.beans.ShoppingcartdetailKey keyClass = new com.ardais.bigr.es.beans.ShoppingcartdetailKey();
		keyClass.shopping_cart_line_number = tmpEJB.shopping_cart_line_number;
		keyClass.shoppingcart_shopping_cart_id = tmpEJB.shoppingcart_shopping_cart_id;
		keyClass.shoppingcart_ardaisuser_ardais_user_id = tmpEJB.shoppingcart_ardaisuser_ardais_user_id;
		keyClass.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key = tmpEJB.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key;
		return keyClass;
	}
	/**
	 * keyFromFields
	 */
	public com.ardais.bigr.es.beans.ShoppingcartdetailKey keyFromFields(java.math.BigDecimal f0, java.math.BigDecimal f1, java.lang.String f2, java.lang.String f3) {
		com.ardais.bigr.es.beans.ShoppingcartdetailKey keyClass = new com.ardais.bigr.es.beans.ShoppingcartdetailKey();
		keyClass.shopping_cart_line_number = f0;
		keyClass.shoppingcart_shopping_cart_id = f1;
		keyClass.shoppingcart_ardaisuser_ardais_user_id = f2;
		keyClass.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key = f3;
		return keyClass;
	}
}
