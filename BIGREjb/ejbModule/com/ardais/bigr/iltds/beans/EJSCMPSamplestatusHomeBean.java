package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPSamplestatusHomeBean
 */
public class EJSCMPSamplestatusHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPSamplestatusHomeBean_fcc4f8e2 {
	/**
	 * EJSCMPSamplestatusHomeBean
	 */
	public EJSCMPSamplestatusHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Samplestatus postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Samplestatus) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
