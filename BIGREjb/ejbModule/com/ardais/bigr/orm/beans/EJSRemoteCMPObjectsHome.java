package com.ardais.bigr.orm.beans;

/**
 * EJSRemoteCMPObjectsHome
 */
public class EJSRemoteCMPObjectsHome extends com.ardais.bigr.orm.beans.EJSRemoteCMPObjectsHome_0056eaf8 implements com.ardais.bigr.orm.beans.ObjectsHome {
	/**
	 * EJSRemoteCMPObjectsHome
	 */
	public EJSRemoteCMPObjectsHome() throws java.rmi.RemoteException {
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
