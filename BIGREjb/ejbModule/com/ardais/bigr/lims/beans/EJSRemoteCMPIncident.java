package com.ardais.bigr.lims.beans;

/**
 * EJSRemoteCMPIncident
 */
public class EJSRemoteCMPIncident extends com.ardais.bigr.lims.beans.EJSRemoteCMPIncident_3b72aef2 implements Incident {
	/**
	 * EJSRemoteCMPIncident
	 */
	public EJSRemoteCMPIncident() throws java.rmi.RemoteException {
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
