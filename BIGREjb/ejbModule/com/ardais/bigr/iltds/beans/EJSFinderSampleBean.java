package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderSampleBean
 */
public interface EJSFinderSampleBean {
	/**
	 * findSampleByAsm
	 */
	public com.ibm.ejs.persistence.EJSFinder findSampleByAsm(com.ardais.bigr.iltds.beans.AsmKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findAll
	 */
	public com.ibm.ejs.persistence.EJSFinder findAll() throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findSampleBySamplebox
	 */
	public com.ibm.ejs.persistence.EJSFinder findSampleBySamplebox(com.ardais.bigr.iltds.beans.SampleboxKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
