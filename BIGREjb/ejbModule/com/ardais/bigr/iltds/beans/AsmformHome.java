package com.ardais.bigr.iltds.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface AsmformHome extends javax.ejb.EJBHome {

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Asmform
 * @param argAsm_form_id java.lang.String
 * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Asmform create(java.lang.String argAsm_form_id, com.ardais.bigr.iltds.beans.ConsentKey argConsent) throws javax.ejb.CreateException, java.rmi.RemoteException;


/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Asmform
 * @param argAsm_form_id java.lang.String
 * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
 * @param argArdais_id java.lang.String
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Asmform create(java.lang.String argAsm_form_id, com.ardais.bigr.iltds.beans.ConsentKey argConsent, java.lang.String argArdais_id) throws javax.ejb.CreateException, java.rmi.RemoteException;

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Asmform
 * @param argAsm_form_id java.lang.String
 * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Asmform create(java.lang.String argAsm_form_id, com.ardais.bigr.iltds.beans.ConsentKey argConsent, ArdaisstaffKey argArdaisstaff) throws javax.ejb.CreateException, java.rmi.RemoteException;
/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.iltds.beans.Asmform
 * @param argAsm_form_id java.lang.String
 * @param argConsent com.ardais.bigr.iltds.beans.ConsentKey
 * @param argArdaisstaff com.ardais.bigr.iltds.beans.ArdaisstaffKey
 * @param argArdais_id java.lang.String
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.iltds.beans.Asmform create(java.lang.String argAsm_form_id, com.ardais.bigr.iltds.beans.ConsentKey argConsent, ArdaisstaffKey argArdaisstaff, java.lang.String argArdais_id) throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named consent.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration findAsmformByConsent(
		com.ardais.bigr.iltds.beans.ConsentKey inKey)
		throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Insert the method's description here.
 * Creation date: (2/5/2001 11:08:42 AM)
 * @return java.util.Enumeration
 * @param asmFormID java.lang.String
 * @exception javax.ejb.FinderException The exception description.
 */
java.util.Enumeration findByASMFormID(String asmFormID) throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Finds an instance using a key for Entity Bean: Asmform
	 */
	public com.ardais.bigr.iltds.beans.Asmform findByPrimaryKey(
		com.ardais.bigr.iltds.beans.AsmformKey primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * Creates an instance from a key for Entity Bean: Asmform
	 */
	public com.ardais.bigr.iltds.beans.Asmform create(
		java.lang.String asm_form_id,
		com.ardais.bigr.iltds.beans.Consent argConsent)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
