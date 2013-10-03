package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderLineitemBean
 */
public interface EJSFinderLineitemBean {
	/**
	 * findLineitemByProject
	 */
	public com.ibm.ejs.persistence.EJSFinder findLineitemByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
