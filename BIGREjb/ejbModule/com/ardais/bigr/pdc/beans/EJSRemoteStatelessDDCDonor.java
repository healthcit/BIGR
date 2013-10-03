package com.ardais.bigr.pdc.beans;

/**
 * EJSRemoteStatelessDDCDonor
 */
public class EJSRemoteStatelessDDCDonor extends com.ardais.bigr.pdc.beans.EJSRemoteStatelessDDCDonor_00f3a761 implements DDCDonor {
	/**
	 * EJSRemoteStatelessDDCDonor
	 */
	public EJSRemoteStatelessDDCDonor() throws java.rmi.RemoteException {
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
