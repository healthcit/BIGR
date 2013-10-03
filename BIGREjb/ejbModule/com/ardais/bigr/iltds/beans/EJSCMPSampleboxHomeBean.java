package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPSampleboxHomeBean
 */
public class EJSCMPSampleboxHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPSampleboxHomeBean_0d3574c3 {
	/**
	 * EJSCMPSampleboxHomeBean
	 */
	public EJSCMPSampleboxHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Samplebox postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Samplebox) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
