package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderSamplestatusBean
 */
public interface EJSFinderSamplestatusBean {
	/**
	 * findSamplestatusBySample
	 */
	public com.ibm.ejs.persistence.EJSFinder findSamplestatusBySample(com.ardais.bigr.iltds.beans.SampleKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findBySampleIDStatus
	 */
	public com.ibm.ejs.persistence.EJSFinder findBySampleIDStatus(java.lang.String sampleID, java.lang.String statusID) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
