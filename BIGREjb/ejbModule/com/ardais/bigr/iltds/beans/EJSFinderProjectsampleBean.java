package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderProjectsampleBean
 */
public interface EJSFinderProjectsampleBean {
	/**
	 * findProjectsampleByLineitem
	 */
	public com.ibm.ejs.persistence.EJSFinder findProjectsampleByLineitem(com.ardais.bigr.iltds.beans.LineitemKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findProjectsampleByProject
	 */
	public com.ibm.ejs.persistence.EJSFinder findProjectsampleByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
