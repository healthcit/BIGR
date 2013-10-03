package com.ardais.bigr.es.beans;

/**
 * EJSCMPArdaisaccountHomeBean
 */
public class EJSCMPArdaisaccountHomeBean extends com.ardais.bigr.es.beans.EJSCMPArdaisaccountHomeBean_7f00ab2e {
	/**
	 * EJSCMPArdaisaccountHomeBean
	 */
	public EJSCMPArdaisaccountHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Ardaisaccount postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Ardaisaccount) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
