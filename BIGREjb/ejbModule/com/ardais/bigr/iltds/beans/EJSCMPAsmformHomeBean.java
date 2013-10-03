package com.ardais.bigr.iltds.beans;

/**
 * EJSCMPAsmformHomeBean
 */
public class EJSCMPAsmformHomeBean extends com.ardais.bigr.iltds.beans.EJSCMPAsmformHomeBean_05e60265 {
	/**
	 * EJSCMPAsmformHomeBean
	 */
	public EJSCMPAsmformHomeBean() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postCreateWrapper
	 */
	public com.ardais.bigr.iltds.beans.Asmform postCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
		return  (com.ardais.bigr.iltds.beans.Asmform) super.postCreate(beanO, ejsKey);
	}
	/**
	 * afterPostCreateWrapper
	 */
	public void afterPostCreateWrapper(com.ibm.ejs.container.BeanO beanO, Object ejsKey) throws javax.ejb.CreateException, java.rmi.RemoteException {
	}
}
