package com.ardais.bigr.userprofile;

/**
 * EJSRemoteStatelessUserProfHome
 */
public class EJSRemoteStatelessUserProfHome extends com.ardais.bigr.userprofile.EJSRemoteStatelessUserProfHome_4714acdf implements com.ardais.bigr.userprofile.UserProfHome {
	/**
	 * EJSRemoteStatelessUserProfHome
	 */
	public EJSRemoteStatelessUserProfHome() throws java.rmi.RemoteException {
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
