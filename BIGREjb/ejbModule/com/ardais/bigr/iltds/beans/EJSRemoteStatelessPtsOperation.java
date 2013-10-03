package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteStatelessPtsOperation
 */
public class EJSRemoteStatelessPtsOperation extends com.ardais.bigr.iltds.beans.EJSRemoteStatelessPtsOperation_52dcd328 implements PtsOperation {
	/**
	 * EJSRemoteStatelessPtsOperation
	 */
	public EJSRemoteStatelessPtsOperation() throws java.rmi.RemoteException {
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
