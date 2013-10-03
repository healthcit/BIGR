package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPWorkorderHomeBean
 */
public class EJSCMPWorkorderHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPWorkorderHomeBean_514544f7 {
	/**
	 * EJSCMPWorkorderHomeBean
	 */
	public EJSCMPWorkorderHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Workorder postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Workorder) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
