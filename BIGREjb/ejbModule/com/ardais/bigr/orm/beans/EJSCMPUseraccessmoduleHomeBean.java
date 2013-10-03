package com.ardais.bigr.orm.beans;

/**
 * EJSCMPUseraccessmoduleHomeBean
 */
public class EJSCMPUseraccessmoduleHomeBean extends com.ardais.bigr.orm.beans.EJSCMPUseraccessmoduleHomeBean_abc4f862 {
	/**
	 * EJSCMPUseraccessmoduleHomeBean
	 */
	public EJSCMPUseraccessmoduleHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.orm.beans.Useraccessmodule postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.orm.beans.Useraccessmodule) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
