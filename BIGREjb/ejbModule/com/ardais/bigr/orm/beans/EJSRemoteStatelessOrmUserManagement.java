package com.ardais.bigr.orm.beans;

/**
 * EJSRemoteStatelessOrmUserManagement
 */
public class EJSRemoteStatelessOrmUserManagement extends com.ardais.bigr.orm.beans.EJSRemoteStatelessOrmUserManagement_14cf9741 implements OrmUserManagement {
	/**
	 * EJSRemoteStatelessOrmUserManagement
	 */
	public EJSRemoteStatelessOrmUserManagement() throws java.rmi.RemoteException {
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
