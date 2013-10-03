package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPSampleHomeBean
 */
public class EJSCMPSampleHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPSampleHomeBean_46ba81fe {
	/**
	 * EJSCMPSampleHomeBean
	 */
	public EJSCMPSampleHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Sample postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Sample) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
