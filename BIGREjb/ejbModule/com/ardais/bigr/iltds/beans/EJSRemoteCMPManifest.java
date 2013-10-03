package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteCMPManifest
 */
public class EJSRemoteCMPManifest extends com.ardais.bigr.iltds.beans.EJSRemoteCMPManifest_921a3765 implements Manifest {
	/**
	 * EJSRemoteCMPManifest
	 */
	public EJSRemoteCMPManifest() throws java.rmi.RemoteException {
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
