package com.ardais.bigr.btx.framework;

/**
 * EJSRemoteStatelessBtxTransactionHome
 */
public class EJSRemoteStatelessBtxTransactionHome extends com.ardais.bigr.btx.framework.EJSRemoteStatelessBtxTransactionHome_af322ae2 implements com.ardais.bigr.btx.framework.BtxTransactionHome {
	/**
	 * EJSRemoteStatelessBtxTransactionHome
	 */
	public EJSRemoteStatelessBtxTransactionHome() throws java.rmi.RemoteException {
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
