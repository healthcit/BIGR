package com.ardais.bigr.lims.beans;

/**
 * EJSCMPSlideHomeBean
 */
public class EJSCMPSlideHomeBean extends com.ardais.bigr.lims.beans.EJSCMPSlideHomeBean_cc534205 {
	/**
	 * EJSCMPSlideHomeBean
	 */
	public EJSCMPSlideHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.lims.beans.Slide postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.lims.beans.Slide) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
