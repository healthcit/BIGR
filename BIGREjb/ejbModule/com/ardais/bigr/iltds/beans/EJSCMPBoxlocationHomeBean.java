package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPBoxlocationHomeBean
 */
public class EJSCMPBoxlocationHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPBoxlocationHomeBean_bc5f2c16 {
	/**
	 * EJSCMPBoxlocationHomeBean
	 */
	public EJSCMPBoxlocationHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Boxlocation postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Boxlocation) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
