package com.ardais.bigr.iltds.beans;

/**
 * EJSFinderArdaisstaffBean
 */
public interface EJSFinderArdaisstaffBean {
	/**
	 * findAllStaffMembers
	 */
	public com.ibm.ejs.persistence.EJSFinder findAllStaffMembers() throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findLocByUserProf
	 */
	public com.ibm.ejs.persistence.EJSFinder findLocByUserProf(java.lang.String ardais_user_id, java.lang.String ardais_acct_key) throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * findArdaisstaffByGeolocation
	 */
	public com.ibm.ejs.persistence.EJSFinder findArdaisstaffByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException;
}
