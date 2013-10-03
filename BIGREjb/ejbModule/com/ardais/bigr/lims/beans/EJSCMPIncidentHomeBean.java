package com.ardais.bigr.lims.beans;

/**
 * EJSCMPIncidentHomeBean
 */
public class EJSCMPIncidentHomeBean extends com.ardais.bigr.lims.beans.EJSCMPIncidentHomeBean_3b72aef2 {
	/**
	 * EJSCMPIncidentHomeBean
	 */
	public EJSCMPIncidentHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.lims.beans.Incident postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.lims.beans.Incident) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
