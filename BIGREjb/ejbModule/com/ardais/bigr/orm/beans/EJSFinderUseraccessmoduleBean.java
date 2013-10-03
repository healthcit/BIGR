package com.ardais.bigr.orm.beans;

/**
 * EJSFinderUseraccessmoduleBean
 */
public interface EJSFinderUseraccessmoduleBean {
	/**
	 * findUseraccessmoduleByObjects
	 */
	public com.ibm.ejs.persistence.EJSFinder findUseraccessmoduleByObjects(com.ardais.bigr.orm.beans.ObjectsKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
