package com.ardais.bigr.es.beans;

/**
 * EJSCMPArdaisorderHomeBean
 */
public class EJSCMPArdaisorderHomeBean extends com.ardais.bigr.es.beans.EJSCMPArdaisorderHomeBean_3e397772 {
	/**
	 * EJSCMPArdaisorderHomeBean
	 */
	public EJSCMPArdaisorderHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Ardaisorder postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Ardaisorder) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
