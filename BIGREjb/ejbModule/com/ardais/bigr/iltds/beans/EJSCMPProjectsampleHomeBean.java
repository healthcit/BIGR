package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPProjectsampleHomeBean
 */
public class EJSCMPProjectsampleHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPProjectsampleHomeBean_1fc02fe2 {
	/**
	 * EJSCMPProjectsampleHomeBean
	 */
	public EJSCMPProjectsampleHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Projectsample postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Projectsample) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
