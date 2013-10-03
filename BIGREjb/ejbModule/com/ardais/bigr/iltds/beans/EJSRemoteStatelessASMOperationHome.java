package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteStatelessASMOperationHome
 */
public class EJSRemoteStatelessASMOperationHome extends com.ardais.bigr.iltds.beans.EJSRemoteStatelessASMOperationHome_c73b1944 implements com.ardais.bigr.iltds.beans.ASMOperationHome {
	/**
	 * EJSRemoteStatelessASMOperationHome
	 */
	public EJSRemoteStatelessASMOperationHome() throws java.rmi.RemoteException {
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
