package com.ardais.bigr.iltds.beans;

/**
 * EJSRemoteCMPProject
 */
public class EJSRemoteCMPProject extends com.ardais.bigr.iltds.beans.EJSRemoteCMPProject_979479c7 implements Project {
	/**
	 * EJSRemoteCMPProject
	 */
	public EJSRemoteCMPProject() throws java.rmi.RemoteException {
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
