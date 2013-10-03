package com.ardais.bigr.lims.beans;

/**
 * EJSRemoteStatelessLimsOperationHome
 */
public class EJSRemoteStatelessLimsOperationHome extends com.ardais.bigr.lims.beans.EJSRemoteStatelessLimsOperationHome_01af3214 implements com.ardais.bigr.lims.beans.LimsOperationHome {
	/**
	 * EJSRemoteStatelessLimsOperationHome
	 */
	public EJSRemoteStatelessLimsOperationHome() throws java.rmi.RemoteException {
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
