package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteCMPSample
 */
public class EJSRemoteCMPSample extends com.ardais.bigr.iltds.beans.EJSRemoteCMPSample_46ba81fe implements Sample {
	/**
	 * EJSRemoteCMPSample
	 */
	public EJSRemoteCMPSample() throws java.rmi.RemoteException {
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
