package com.ardais.bigr.lims.beans;

/**
 * EJSFinderPathologyEvaluationBean
 */
public interface EJSFinderPathologyEvaluationBean {
	/**
	 * findAll
	 */
	public com.ibm.ejs.persistence.EJSFinder findAll() throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findAllNonMigrated
	 */
	public com.ibm.ejs.persistence.EJSFinder findAllNonMigrated() throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findBySampleId
	 */
	public com.ibm.ejs.persistence.EJSFinder findBySampleId(java.lang.String sampleId) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
