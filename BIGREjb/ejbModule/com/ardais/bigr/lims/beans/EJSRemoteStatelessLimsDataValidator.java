package com.ardais.bigr.lims.beans;

/**
 * EJSRemoteStatelessLimsDataValidator
 */
public class EJSRemoteStatelessLimsDataValidator extends com.ardais.bigr.lims.beans.EJSRemoteStatelessLimsDataValidator_586fcd57 implements LimsDataValidator {
	/**
	 * EJSRemoteStatelessLimsDataValidator
	 */
	public EJSRemoteStatelessLimsDataValidator() throws java.rmi.RemoteException {
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
