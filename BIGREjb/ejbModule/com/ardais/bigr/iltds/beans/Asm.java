package com.ardais.bigr.iltds.beans;

/**
 * Remote interface for Enterprise Bean: Asm
 */
public interface Asm extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {










/**
 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSample com.ardais.bigr.iltds.beans.Sample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void addSample(com.ardais.bigr.iltds.beans.Sample aSample) throws java.rmi.RemoteException;
/**
 * Getter method for asm_entry_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getAsm_entry_date() throws java.rmi.RemoteException;
/**
 * Getter method for organ_site_concept_id
 * @return java.lang.String
 */
java.lang.String getOrgan_site_concept_id() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getOrgan_site_concept_id_other() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return java.util.Enumeration
 * @exception javax.ejb.FinderException The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
java.util.Enumeration getSample() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Getter method for specimen_type
 * @return java.lang.String
 */
java.lang.String getSpecimen_type() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSample com.ardais.bigr.iltds.beans.Sample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void removeSample(com.ardais.bigr.iltds.beans.Sample aSample) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSample com.ardais.bigr.iltds.beans.Sample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryAddSample(com.ardais.bigr.iltds.beans.Sample aSample) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param aSample com.ardais.bigr.iltds.beans.Sample
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
void secondaryRemoveSample(com.ardais.bigr.iltds.beans.Sample aSample) throws java.rmi.RemoteException;
/**
 * Setter method for asm_entry_date
 * @param newValue java.sql.Timestamp
 */
void setAsm_entry_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for organ_site_concept_id
 * @param newValue java.lang.String
 */
void setOrgan_site_concept_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newOrgan_site_concept_id_other java.lang.String
 */
void setOrgan_site_concept_id_other(java.lang.String newOrgan_site_concept_id_other) throws java.rmi.RemoteException;
/**
 * Setter method for specimen_type
 * @param newValue java.lang.String
 */
void setSpecimen_type(java.lang.String newValue) throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: asm_form_id
	 */
	public java.lang.String getAsm_form_id() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: asm_form_id
	 */
	public void setAsm_form_id(java.lang.String newAsm_form_id)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: consent_id
	 */
	public java.lang.String getConsent_id() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: consent_id
	 */
	public void setConsent_id(java.lang.String newConsent_id)
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
	 * Get accessor for persistent attribute: pfin_meets_specs
	 */
	public java.lang.String getPfin_meets_specs()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: pfin_meets_specs
	 */
	public void setPfin_meets_specs(java.lang.String newPfin_meets_specs)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: module_weight
	 */
	public java.lang.Integer getModule_weight()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: module_weight
	 */
	public void setModule_weight(java.lang.Integer newModule_weight)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: module_comments
	 */
	public java.lang.String getModule_comments()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: module_comments
	 */
	public void setModule_comments(java.lang.String newModule_comments)
		throws java.rmi.RemoteException;
}
