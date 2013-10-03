package com.ardais.bigr.orm.beans;

/**
 * EJSCMPObjectsHomeBean
 */
public class EJSCMPObjectsHomeBean extends com.ardais.bigr.orm.beans.EJSCMPObjectsHomeBean_0056eaf8 {
	/**
	 * EJSCMPObjectsHomeBean
	 */
	public EJSCMPObjectsHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.orm.beans.Objects postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.orm.beans.Objects) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
