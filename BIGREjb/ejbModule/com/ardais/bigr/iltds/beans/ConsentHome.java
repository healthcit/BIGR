package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface ConsentHome extends javax.ejb.EJBHome {

  /**
   * Insert the method's description here.
   * Creation date: (2/5/2001 11:10:36 AM)
   * @return java.util.Enumeration
   * @param consentID java.lang.String
   * @exception javax.ejb.FinderException The exception description.
   */
  java.util.Enumeration findByConsentID(String consentID)
    throws java.rmi.RemoteException, javax.ejb.FinderException;

  /**
   * Finds an instance using a key for Entity Bean: Consent
   */
  public com.ardais.bigr.iltds.beans.Consent findByPrimaryKey(
    com.ardais.bigr.iltds.beans.ConsentKey primaryKey)
    throws javax.ejb.FinderException, java.rmi.RemoteException;

  /**
   * Insert the method's description here.
   * Creation date: (2/14/2001 1:49:17 PM)
   * @return java.util.Enumeration
   * @param consentID java.lang.String
   * @exception javax.ejb.FinderException The exception description.
   */
  java.util.Enumeration findConsentByArdaisID(String consentID)
    throws java.rmi.RemoteException, javax.ejb.FinderException;

  /**
   * This method was generated for supporting the association named Consent REFILTDS_GEOGRAPHY_LOCATION19 Geolocation.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return java.util.Enumeration
   * @param inKey com.ardais.bigr.iltds.beans.GeolocationKey
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  java.util.Enumeration findConsentByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey)
    throws java.rmi.RemoteException, javax.ejb.FinderException;
  /**
   * ejbCreate method for a CMP entity bean.
   */
  public com.ardais.bigr.iltds.beans.Consent create(
    String consent_id,
    java.math.BigDecimal policy_id,
    String ardais_id,
    String imported_yn,
    String ardais_acct_key,
    String case_registration_form) throws javax.ejb.CreateException, java.rmi.RemoteException;
}
