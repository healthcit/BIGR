package com.ardais.bigr.es.beans;

/**
 * EJSCMPArdaisorderstatusHomeBean
 */
public class EJSCMPArdaisorderstatusHomeBean extends com.ardais.bigr.es.beans.EJSCMPArdaisorderstatusHomeBean_b930be81 {
	/**
	 * EJSCMPArdaisorderstatusHomeBean
	 */
	public EJSCMPArdaisorderstatusHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Ardaisorderstatus postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Ardaisorderstatus) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
