package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPLineitemHomeBean
 */
public class EJSCMPLineitemHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPLineitemHomeBean_7206cc70 {
	/**
	 * EJSCMPLineitemHomeBean
	 */
	public EJSCMPLineitemHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Lineitem postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Lineitem) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
