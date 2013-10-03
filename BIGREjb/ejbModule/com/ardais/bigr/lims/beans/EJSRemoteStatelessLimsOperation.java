package com.ardais.bigr.lims.beans;

/**
 * EJSRemoteStatelessLimsOperation
 */
public class EJSRemoteStatelessLimsOperation extends com.ardais.bigr.lims.beans.EJSRemoteStatelessLimsOperation_01af3214 implements LimsOperation {
	/**
	 * EJSRemoteStatelessLimsOperation
	 */
	public EJSRemoteStatelessLimsOperation() throws java.rmi.RemoteException {
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
