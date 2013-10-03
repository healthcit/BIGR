package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteStatelessBTXHistoryReader
 */
public class EJSRemoteStatelessBTXHistoryReader extends com.ardais.bigr.iltds.beans.EJSRemoteStatelessBTXHistoryReader_8f44ac6a implements BTXHistoryReader {
	/**
	 * EJSRemoteStatelessBTXHistoryReader
	 */
	public EJSRemoteStatelessBTXHistoryReader() throws java.rmi.RemoteException {
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
