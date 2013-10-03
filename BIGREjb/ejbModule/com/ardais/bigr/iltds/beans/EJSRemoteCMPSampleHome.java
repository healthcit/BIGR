package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteCMPSampleHome
 */
public class EJSRemoteCMPSampleHome extends com.ardais.bigr.iltds.beans.EJSRemoteCMPSampleHome_46ba81fe implements com.ardais.bigr.iltds.beans.SampleHome {
	/**
	 * EJSRemoteCMPSampleHome
	 */
	public EJSRemoteCMPSampleHome() throws java.rmi.RemoteException {
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
