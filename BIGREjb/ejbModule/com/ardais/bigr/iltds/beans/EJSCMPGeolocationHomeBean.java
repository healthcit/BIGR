package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPGeolocationHomeBean
 */
public class EJSCMPGeolocationHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPGeolocationHomeBean_9d43a246 {
	/**
	 * EJSCMPGeolocationHomeBean
	 */
	public EJSCMPGeolocationHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Geolocation postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Geolocation) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
