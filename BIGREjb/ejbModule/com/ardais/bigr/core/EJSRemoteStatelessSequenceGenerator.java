package com.ardais.bigr.core;

/**
 * EJSRemoteStatelessSequenceGenerator
 */
public class EJSRemoteStatelessSequenceGenerator extends com.ardais.bigr.core.EJSRemoteStatelessSequenceGenerator_390aae5b implements SequenceGenerator {
	/**
	 * EJSRemoteStatelessSequenceGenerator
	 */
	public EJSRemoteStatelessSequenceGenerator() throws java.rmi.RemoteException {
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
