package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderWorkorderBean
 */
public interface EJSFinderWorkorderBean {
	/**
	 * findWorkorderByProject
	 */
	public com.ibm.ejs.persistence.EJSFinder findWorkorderByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
