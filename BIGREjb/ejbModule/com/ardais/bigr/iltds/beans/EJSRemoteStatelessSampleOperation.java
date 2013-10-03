package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteStatelessSampleOperation
 */
public class EJSRemoteStatelessSampleOperation extends com.ardais.bigr.iltds.beans.EJSRemoteStatelessSampleOperation_7ad0408e implements SampleOperation {
	/**
	 * EJSRemoteStatelessSampleOperation
	 */
	public EJSRemoteStatelessSampleOperation() throws java.rmi.RemoteException {
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
