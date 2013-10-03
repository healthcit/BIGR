package com.ardais.bigr.core;

/**
 * EJSRemoteStatelessSequenceGeneratorHome
 */
public class EJSRemoteStatelessSequenceGeneratorHome extends com.ardais.bigr.core.EJSRemoteStatelessSequenceGeneratorHome_390aae5b implements com.ardais.bigr.core.SequenceGeneratorHome {
	/**
	 * EJSRemoteStatelessSequenceGeneratorHome
	 */
	public EJSRemoteStatelessSequenceGeneratorHome() throws java.rmi.RemoteException {
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
