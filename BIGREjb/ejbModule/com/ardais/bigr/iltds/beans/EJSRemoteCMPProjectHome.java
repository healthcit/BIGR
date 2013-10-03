package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteCMPProjectHome
 */
public class EJSRemoteCMPProjectHome extends com.ardais.bigr.iltds.beans.EJSRemoteCMPProjectHome_979479c7 implements com.ardais.bigr.iltds.beans.ProjectHome {
	/**
	 * EJSRemoteCMPProjectHome
	 */
	public EJSRemoteCMPProjectHome() throws java.rmi.RemoteException {
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
