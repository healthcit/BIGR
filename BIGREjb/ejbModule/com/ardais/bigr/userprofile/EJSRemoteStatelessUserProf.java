package com.ardais.bigr.userprofile;

/**
 * EJSRemoteStatelessUserProf
 */
public class EJSRemoteStatelessUserProf extends com.ardais.bigr.userprofile.EJSRemoteStatelessUserProf_4714acdf implements UserProf {
	/**
	 * EJSRemoteStatelessUserProf
	 */
	public EJSRemoteStatelessUserProf() throws java.rmi.RemoteException {
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
