package com.ardais.bigr.es.beans;

/**
 * Remote interface for Enterprise Bean: Ardaisaccount
 */
public interface Ardaisaccount extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {




















/**
 * Getter method for ardais_acct_active_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getArdais_acct_active_date() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_acct_close_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getArdais_acct_close_date() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_acct_company_desc
 * @return java.lang.String
 */
java.lang.String getArdais_acct_company_desc() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_acct_deactivate_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getArdais_acct_deactivate_date() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_acct_deactivate_reason
 * @return java.lang.String
 */
java.lang.String getArdais_acct_deactivate_reason() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_acct_open_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getArdais_acct_open_date() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_acct_reactivate_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getArdais_acct_reactivate_date() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_acct_status
 * @return java.lang.String
 */
java.lang.String getArdais_acct_status() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_acct_type
 * @return java.lang.String
 */
java.lang.String getArdais_acct_type() throws java.rmi.RemoteException;
/**
 * Getter method for ardais_parent_acct_comp_desc
 * @return java.lang.String
 */
java.lang.String getArdais_parent_acct_comp_desc() throws java.rmi.RemoteException;
/**
 * Setter method for ardais_acct_active_date
 * @param newValue java.sql.Timestamp
 */
void setArdais_acct_active_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_acct_close_date
 * @param newValue java.sql.Timestamp
 */
void setArdais_acct_close_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_acct_company_desc
 * @param newValue java.lang.String
 */
void setArdais_acct_company_desc(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_acct_deactivate_date
 * @param newValue java.sql.Timestamp
 */
void setArdais_acct_deactivate_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_acct_deactivate_reason
 * @param newValue java.lang.String
 */
void setArdais_acct_deactivate_reason(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_acct_open_date
 * @param newValue java.sql.Timestamp
 */
void setArdais_acct_open_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_acct_reactivate_date
 * @param newValue java.sql.Timestamp
 */
void setArdais_acct_reactivate_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_acct_status
 * @param newValue java.lang.String
 */
void setArdais_acct_status(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_acct_type
 * @param newValue java.lang.String
 */
void setArdais_acct_type(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ardais_parent_acct_comp_desc
 * @param newValue java.lang.String
 */
void setArdais_parent_acct_comp_desc(java.lang.String newValue) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisuser.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddArdaisuser(
		com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisuser.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveArdaisuser(
		com.ardais.bigr.es.beans.Ardaisuser anArdaisuser)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisuser.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getArdaisuser()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
  /**
   * Get accessor for persistent attribute: request_mgr_email_address
   */
  public java.lang.String getRequest_mgr_email_address() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: request_mgr_email_address
   */
  public void setRequest_mgr_email_address(java.lang.String newRequest_mgr_email_address)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: linked_cases_only_yn
   */
  public java.lang.String getLinked_cases_only_yn() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: linked_cases_only_yn
   */
  public void setLinked_cases_only_yn(java.lang.String newLinked_cases_only_yn)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: passwordPolicyCid
   */
  public java.lang.String getPasswordPolicyCid() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: passwordPolicyCid
   */
  public void setPasswordPolicyCid(java.lang.String newPasswordPolicyCid)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: passwordLifeSpan
   */
  public int getPasswordLifeSpan() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: passwordLifeSpan
   */
  public void setPasswordLifeSpan(int newPasswordLifeSpan) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: defaultBoxLayoutId
   */
  public java.lang.String getDefaultBoxLayoutId() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: defaultBoxLayoutId
   */
  public void setDefaultBoxLayoutId(java.lang.String newDefaultBoxLayoutId)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: primaryLocation
   */
  public java.lang.String getPrimaryLocation() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: primaryLocation
   */
  public void setPrimaryLocation(java.lang.String newPrimaryLocation)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: donorRegistrationForm
   */
  public java.lang.String getDonorRegistrationForm() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: donorRegistrationForm
   */
  public void setDonorRegistrationForm(java.lang.String newDonorRegistrationForm)
    throws java.rmi.RemoteException;
}
