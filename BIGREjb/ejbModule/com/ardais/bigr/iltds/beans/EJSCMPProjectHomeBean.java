package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPProjectHomeBean
 */
public class EJSCMPProjectHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPProjectHomeBean_979479c7 {
	/**
	 * EJSCMPProjectHomeBean
	 */
	public EJSCMPProjectHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Project postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Project) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
