package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteCMPAsmHome
 */
public class EJSRemoteCMPAsmHome extends com.ardais.bigr.iltds.beans.EJSRemoteCMPAsmHome_ca61be70 implements com.ardais.bigr.iltds.beans.AsmHome {
	/**
	 * EJSRemoteCMPAsmHome
	 */
	public EJSRemoteCMPAsmHome() throws java.rmi.RemoteException {
		super();	}
	/**
	 * getDeployedSupport
	 */
	public com.ibm.ejs.container.EJSDeployedSupport getDeployedSupport() {
		return new com.ibm.ejs.container.EJSDeployedSupport();
	}
	/**
	 * putDeployedSupport
	 */
	public void putDeployedSupport(com.ibm.ejs.container.EJSDeployedSupport support) {
		return;
	}
}
