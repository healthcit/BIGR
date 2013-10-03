package com.ardais.bigr.es.beans;

/**
 * EJSCMPShoppingcartdetailHomeBean
 */
public class EJSCMPShoppingcartdetailHomeBean extends com.ardais.bigr.es.beans.EJSCMPShoppingcartdetailHomeBean_a81a2bea {
	/**
	 * EJSCMPShoppingcartdetailHomeBean
	 */
	public EJSCMPShoppingcartdetailHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Shoppingcartdetail postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Shoppingcartdetail) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
