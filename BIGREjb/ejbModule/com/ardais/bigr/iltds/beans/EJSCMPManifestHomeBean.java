package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPManifestHomeBean
 */
public class EJSCMPManifestHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPManifestHomeBean_921a3765 {
	/**
	 * EJSCMPManifestHomeBean
	 */
	public EJSCMPManifestHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Manifest postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Manifest) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
