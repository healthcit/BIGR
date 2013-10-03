package com.ardais.bigr.iltds.icp.ejb.session;

/**
 * EJSRemoteStatelessIcpOperationHome
 */
public class EJSRemoteStatelessIcpOperationHome extends com.ardais.bigr.iltds.icp.ejb.session.EJSRemoteStatelessIcpOperationHome_ad5b9602 implements com.ardais.bigr.iltds.icp.ejb.session.IcpOperationHome {
	/**
	 * EJSRemoteStatelessIcpOperationHome
	 */
	public EJSRemoteStatelessIcpOperationHome() throws java.rmi.RemoteException {
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
