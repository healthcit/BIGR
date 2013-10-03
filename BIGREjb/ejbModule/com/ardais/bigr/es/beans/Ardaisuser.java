package com.ardais.bigr.es.beans;

/**
 * Remote interface for Enterprise Bean: Ardaisuser
 */
public interface Ardaisuser extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {






	/**
	 * This method was generated for supporting the association named ardaisorder.
	 * It will be deleted/edited when the association is deleted/edited.
	 */
	public void addArdaisorder(com.ardais.bigr.es.beans.Ardaisorder anArdaisorder) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the association named ardaisorder.
	 * It will be deleted/edited when the association is deleted/edited.
	 */
	public java.util.Enumeration getArdaisorder() throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Getter method for create_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getCreate_date() throws java.rmi.RemoteException;
/**
 * Getter method for created_by
 * @return java.lang.String
 */
java.lang.String getCreated_by() throws java.rmi.RemoteException;
/**
 * Getter method for password
 * @return java.lang.String
 */
java.lang.String getPassword() throws java.rmi.RemoteException;
/**
 * Getter method for update_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getUpdate_date() throws java.rmi.RemoteException;
/**
 * Getter method for updated_by
 * @return java.lang.String
 */
java.lang.String getUpdated_by() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getUser_active_ind() throws java.rmi.RemoteException;
/**
 * 
 * @return java.math.BigDecimal
 */
java.math.BigDecimal getUser_address_id() throws java.rmi.RemoteException;
/**
 * Getter method for user_affiliation
 * @return java.lang.String
 */
java.lang.String getUser_affiliation() throws java.rmi.RemoteException;
/**
 * Getter method for user_email_address
 * @return java.lang.String
 */
java.lang.String getUser_email_address() throws java.rmi.RemoteException;
/**
 * Getter method for user_fax_number
 * @return java.lang.String
 */
java.lang.String getUser_fax_number() throws java.rmi.RemoteException;
/**
 * Getter method for user_firstname
 * @return java.lang.String
 */
java.lang.String getUser_firstname() throws java.rmi.RemoteException;
/**
 * Getter method for user_functional_title
 * @return java.lang.String
 */
java.lang.String getUser_functional_title() throws java.rmi.RemoteException;
/**
 * Getter method for user_lastname
 * @return java.lang.String
 */
java.lang.String getUser_lastname() throws java.rmi.RemoteException;
/**
 * Getter method for user_mobile_number
 * @return java.lang.String
 */
java.lang.String getUser_mobile_number() throws java.rmi.RemoteException;
/**
 * Getter method for user_phone_ext
 * @return java.lang.String
 */
java.lang.String getUser_phone_ext() throws java.rmi.RemoteException;
/**
 * Getter method for user_phone_number
 * @return java.lang.String
 */
java.lang.String getUser_phone_number() throws java.rmi.RemoteException;
/**
 * Getter method for user_title
 * @return java.lang.String
 */
java.lang.String getUser_title() throws java.rmi.RemoteException;
/**
 * Getter method for user_verif_answer
 * @return java.lang.String
 */
java.lang.String getUser_verif_answer() throws java.rmi.RemoteException;
/**
 * Getter method for user_verif_question
 * @return java.lang.String
 */
java.lang.String getUser_verif_question() throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the association named ardaisorder.
	 * It will be deleted/edited when the association is deleted/edited.
	 */
	public void secondaryAddArdaisorder(com.ardais.bigr.es.beans.Ardaisorder anArdaisorder) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the association named ardaisorder.
	 * It will be deleted/edited when the association is deleted/edited.
	 */
	public void secondaryRemoveArdaisorder(com.ardais.bigr.es.beans.Ardaisorder anArdaisorder) throws java.rmi.RemoteException;
/**
 * Setter method for create_date
 * @param newValue java.sql.Timestamp
 */
void setCreate_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for created_by
 * @param newValue java.lang.String
 */
void setCreated_by(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for password
 * @param newValue java.lang.String
 */
void setPassword(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for update_date
 * @param newValue java.sql.Timestamp
 */
void setUpdate_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * Setter method for updated_by
 * @param newValue java.lang.String
 */
void setUpdated_by(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setUser_active_ind(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newUser_address_id java.math.BigDecimal
 */
void setUser_address_id(java.math.BigDecimal newUser_address_id) throws java.rmi.RemoteException;
/**
 * Setter method for user_affiliation
 * @param newValue java.lang.String
 */
void setUser_affiliation(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_email_address
 * @param newValue java.lang.String
 */
void setUser_email_address(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_fax_number
 * @param newValue java.lang.String
 */
void setUser_fax_number(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_firstname
 * @param newValue java.lang.String
 */
void setUser_firstname(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_functional_title
 * @param newValue java.lang.String
 */
void setUser_functional_title(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_lastname
 * @param newValue java.lang.String
 */
void setUser_lastname(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_mobile_number
 * @param newValue java.lang.String
 */
void setUser_mobile_number(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_phone_ext
 * @param newValue java.lang.String
 */
void setUser_phone_ext(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_phone_number
 * @param newValue java.lang.String
 */
void setUser_phone_number(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_title
 * @param newValue java.lang.String
 */
void setUser_title(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_verif_answer
 * @param newValue java.lang.String
 */
void setUser_verif_answer(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for user_verif_question
 * @param newValue java.lang.String
 */
void setUser_verif_question(java.lang.String newValue) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named shoppingcart.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddShoppingcart(
		com.ardais.bigr.es.beans.Shoppingcart aShoppingcart)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named shoppingcart.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveShoppingcart(
		com.ardais.bigr.es.beans.Shoppingcart aShoppingcart)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named shoppingcart.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getShoppingcart()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the relationship role named ardaisaccount.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.es.beans.ArdaisaccountKey getArdaisaccountKey()
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisaccount.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.es.beans.Ardaisaccount getArdaisaccount()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
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
   * Get accessor for persistent attribute: passwordLastChangeDate
   */
  public java.sql.Timestamp getPasswordLastChangeDate() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: passwordLastChangeDate
   */
  public void setPasswordLastChangeDate(java.sql.Timestamp newPasswordLastChangeDate)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: user_department
   */
  public java.lang.String getUser_department() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: user_department
   */
  public void setUser_department(java.lang.String newUser_department)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: consecutive_failed_logins
   */
  public java.lang.Integer getConsecutive_failed_logins() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: consecutive_failed_logins
   */
  public void setConsecutive_failed_logins(java.lang.Integer newConsecutive_failed_logins)
    throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: consecutive_failed_answers
   */
  public java.lang.Integer getConsecutive_failed_answers() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: consecutive_failed_answers
   */
  public void setConsecutive_failed_answers(java.lang.Integer newConsecutive_failed_answers)
    throws java.rmi.RemoteException;
}
