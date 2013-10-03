package com.ardais.bigr.es.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ArdaisaccountHome extends javax.ejb.EJBHome {

	/**
	 * Creates an instance from a key for Entity Bean: Ardaisaccount
	 */
	public com.ardais.bigr.es.beans.Ardaisaccount create(
    java.lang.String ardais_acct_key,
    java.lang.String linked_cases_only_yn,
    java.lang.String argPasswordPolicyCid,
    int argPasswordLifeSpan) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * findByPrimaryKey method comment
 * @return com.ardais.bigr.es.beans.Ardaisaccount
 * @param key com.ardais.bigr.es.beans.ArdaisaccountKey
 * @exception javax.ejb.FinderException The exception description.
 */
com.ardais.bigr.es.beans.Ardaisaccount findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisaccountKey key) throws java.rmi.RemoteException, javax.ejb.FinderException;

  /**
   * Creates an instance from a key for Entity Bean: Ardaisaccount
   */
  public com.ardais.bigr.es.beans.Ardaisaccount create(java.lang.String ardais_acct_key)
    throws javax.ejb.CreateException,
    java.rmi.RemoteException;
}
