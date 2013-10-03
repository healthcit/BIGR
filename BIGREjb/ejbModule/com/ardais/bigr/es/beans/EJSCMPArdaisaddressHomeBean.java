package com.ardais.bigr.es.beans;

/**
 * EJSCMPArdaisaddressHomeBean
 */
public class EJSCMPArdaisaddressHomeBean extends com.ardais.bigr.es.beans.EJSCMPArdaisaddressHomeBean_7d45b735 {
	/**
	 * EJSCMPArdaisaddressHomeBean
	 */
	public EJSCMPArdaisaddressHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.es.beans.Ardaisaddress postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.es.beans.Ardaisaddress) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
