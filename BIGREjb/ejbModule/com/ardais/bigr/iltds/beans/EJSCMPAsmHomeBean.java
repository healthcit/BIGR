package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPAsmHomeBean
 */
public class EJSCMPAsmHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPAsmHomeBean_ca61be70 {
	/**
	 * EJSCMPAsmHomeBean
	 */
	public EJSCMPAsmHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Asm postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Asm) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
