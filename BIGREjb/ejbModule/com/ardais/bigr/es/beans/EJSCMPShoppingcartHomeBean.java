package com.ardais.bigr.es.beans;

/**
 * EJSCMPShoppingcartHomeBean
 */
public class EJSCMPShoppingcartHomeBean extends com.ardais.bigr.es.beans.EJSCMPShoppingcartHomeBean_4bdce51d {
	/**
	 * EJSCMPShoppingcartHomeBean
	 */
	public EJSCMPShoppingcartHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Shoppingcart postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Shoppingcart) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
