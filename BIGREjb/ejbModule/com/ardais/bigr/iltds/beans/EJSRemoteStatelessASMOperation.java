package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteStatelessASMOperation
 */
public class EJSRemoteStatelessASMOperation extends com.ardais.bigr.iltds.beans.EJSRemoteStatelessASMOperation_c73b1944 implements ASMOperation {
	/**
	 * EJSRemoteStatelessASMOperation
	 */
	public EJSRemoteStatelessASMOperation() throws java.rmi.RemoteException {
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
