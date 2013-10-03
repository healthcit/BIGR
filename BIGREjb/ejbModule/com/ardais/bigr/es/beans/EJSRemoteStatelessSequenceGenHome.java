package com.ardais.bigr.es.beans;

/**
 * EJSRemoteStatelessSequenceGenHome
 */
public class EJSRemoteStatelessSequenceGenHome extends com.ardais.bigr.es.beans.EJSRemoteStatelessSequenceGenHome_71ef6ecd implements com.ardais.bigr.es.beans.SequenceGenHome {
	/**
	 * EJSRemoteStatelessSequenceGenHome
	 */
	public EJSRemoteStatelessSequenceGenHome() throws java.rmi.RemoteException {
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
