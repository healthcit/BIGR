package com.ardais.bigr.es.beans;

/**
 * EJSCMPArdaisuserHomeBean
 */
public class EJSCMPArdaisuserHomeBean extends com.ardais.bigr.es.beans.EJSCMPArdaisuserHomeBean_3fdc52da {
	/**
	 * EJSCMPArdaisuserHomeBean
	 */
	public EJSCMPArdaisuserHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Ardaisuser postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Ardaisuser) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
