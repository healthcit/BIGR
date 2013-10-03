package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteCMPManifestHome
 */
public class EJSRemoteCMPManifestHome extends com.ardais.bigr.iltds.beans.EJSRemoteCMPManifestHome_921a3765 implements com.ardais.bigr.iltds.beans.ManifestHome {
	/**
	 * EJSRemoteCMPManifestHome
	 */
	public EJSRemoteCMPManifestHome() throws java.rmi.RemoteException {
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
