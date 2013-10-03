package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPConsentHomeBean
 */
public class EJSCMPConsentHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPConsentHomeBean_12d3e012 {
	/**
	 * EJSCMPConsentHomeBean
	 */
	public EJSCMPConsentHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Consent postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Consent) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
