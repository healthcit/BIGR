package com.ardais.bigr.pdc.oce;

/**
 * EJSRemoteStatelessOceHome
 */
public class EJSRemoteStatelessOceHome extends com.ardais.bigr.pdc.oce.EJSRemoteStatelessOceHome_1c4c8256 implements com.ardais.bigr.pdc.oce.OceHome {
	/**
	 * EJSRemoteStatelessOceHome
	 */
	public EJSRemoteStatelessOceHome() throws java.rmi.RemoteException {
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
