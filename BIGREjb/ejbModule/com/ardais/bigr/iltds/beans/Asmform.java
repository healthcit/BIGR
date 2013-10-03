package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Asmform
 */
public interface Asmform extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {




















/**
 * 
 * @return java.lang.String
 */
java.lang.String getArdais_staff_id() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Asmform REFILTDS_ARDAIS_STAFF23 Ardaisstaff.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.iltds.beans.ArdaisstaffKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
com.ardais.bigr.iltds.beans.ArdaisstaffKey getArdaisstaffKey() throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named consent.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.iltds.beans.Consent getConsent()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the relationship role named consent.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.iltds.beans.ConsentKey getConsentKey()
		throws java.rmi.RemoteException;
/**
 * Getter method for creation_time
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getCreation_time() throws java.rmi.RemoteException;
/**
 * Getter method for grossing_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getGrossing_date() throws java.rmi.RemoteException;
/**
 * Getter method for link_staff_id
 * @return java.lang.String
 */
java.lang.String getLink_staff_id() throws java.rmi.RemoteException;
/**
 * Getter method for removal_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getRemoval_date() throws java.rmi.RemoteException;
/**
 * Getter method for surgical_specimen_id
 * @return java.lang.String
 */
java.lang.String getSurgical_specimen_id() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getSurgical_specimen_id_other() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Asmform REFILTDS_ARDAIS_STAFF23 Ardaisstaff.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param inKey com.ardais.bigr.iltds.beans.ArdaisstaffKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void privateSetArdaisstaffKey(com.ardais.bigr.iltds.beans.ArdaisstaffKey inKey) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setArdais_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for creation_time
 * @param newValue java.sql.Timestamp
 */
void setCreation_time(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for grossing_date
 * @param newValue java.sql.Timestamp
 */
void setGrossing_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for link_staff_id
 * @param newValue java.lang.String
 */
void setLink_staff_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for removal_date
 * @param newValue java.sql.Timestamp
 */
void setRemoval_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for surgical_specimen_id
 * @param newValue java.lang.String
 */
void setSurgical_specimen_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newSurgical_specimen_id_other java.lang.String
 */
void setSurgical_specimen_id_other(java.lang.String newSurgical_specimen_id_other) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named consent.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondarySetConsent(
		com.ardais.bigr.iltds.beans.Consent aConsent)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named consent.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void setConsent(com.ardais.bigr.iltds.beans.Consent aConsent)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named consent.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void privateSetConsentKey(
		com.ardais.bigr.iltds.beans.ConsentKey inKey)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: ardais_id
	 */
	public java.lang.String getArdais_id() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: ardais_id
	 */
	public void setArdais_id(java.lang.String newArdais_id)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: acc_surgical_removal_time
	 */
	public java.lang.String getAcc_surgical_removal_time()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: acc_surgical_removal_time
	 */
	public void setAcc_surgical_removal_time(
		java.lang.String newAcc_surgical_removal_time)
		throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: procedure_date
   */
  public java.sql.Timestamp getProcedure_date() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: procedure_date
   */
  public void setProcedure_date(java.sql.Timestamp newProcedure_date)
    throws java.rmi.RemoteException;
}
