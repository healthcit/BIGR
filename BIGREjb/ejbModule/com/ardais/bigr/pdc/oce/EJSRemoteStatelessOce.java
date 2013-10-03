package com.ardais.bigr.pdc.oce;

/**
 * EJSRemoteStatelessOce
 */
public class EJSRemoteStatelessOce extends com.ardais.bigr.pdc.oce.EJSRemoteStatelessOce_1c4c8256 implements Oce {
	/**
	 * EJSRemoteStatelessOce
	 */
	public EJSRemoteStatelessOce() throws java.rmi.RemoteException {
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
