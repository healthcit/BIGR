package com.ardais.bigr.iltds.icp.ejb.session;

/**
 * EJSRemoteStatelessIcpOperation
 */
public class EJSRemoteStatelessIcpOperation extends com.ardais.bigr.iltds.icp.ejb.session.EJSRemoteStatelessIcpOperation_ad5b9602 implements IcpOperation {
	/**
	 * EJSRemoteStatelessIcpOperation
	 */
	public EJSRemoteStatelessIcpOperation() throws java.rmi.RemoteException {
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
