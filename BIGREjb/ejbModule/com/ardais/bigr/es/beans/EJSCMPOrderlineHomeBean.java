package com.ardais.bigr.es.beans;

/**
 * EJSCMPOrderlineHomeBean
 */
public class EJSCMPOrderlineHomeBean extends com.ardais.bigr.es.beans.EJSCMPOrderlineHomeBean_3a6e7917 {
	/**
	 * EJSCMPOrderlineHomeBean
	 */
	public EJSCMPOrderlineHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Orderline postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Orderline) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
