package com.ardais.bigr.btx.framework;

/**
 * EJSRemoteStatelessBtxTransaction
 */
public class EJSRemoteStatelessBtxTransaction extends com.ardais.bigr.btx.framework.EJSRemoteStatelessBtxTransaction_af322ae2 implements BtxTransaction {
	/**
	 * EJSRemoteStatelessBtxTransaction
	 */
	public EJSRemoteStatelessBtxTransaction() throws java.rmi.RemoteException {
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
