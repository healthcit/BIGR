package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteStatelessBTXHistoryRecorder
 */
public class EJSRemoteStatelessBTXHistoryRecorder extends com.ardais.bigr.iltds.beans.EJSRemoteStatelessBTXHistoryRecorder_5eb38b2c implements BTXHistoryRecorder {
	/**
	 * EJSRemoteStatelessBTXHistoryRecorder
	 */
	public EJSRemoteStatelessBTXHistoryRecorder() throws java.rmi.RemoteException {
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
