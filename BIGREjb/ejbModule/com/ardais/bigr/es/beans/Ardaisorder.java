package com.ardais.bigr.es.beans;

/**
 * Remote interface for Enterprise Bean: Ardaisorder
 */
public interface Ardaisorder extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {


/**
 * 
 * @return java.lang.String
 */
java.lang.String getApproval_user_id() throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Ardaisorderstatus-Ardaisorder.  
 * 	It will be deleted/edited when the association is deleted/edited.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public java.util.Enumeration getArdaisorderstatus() throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the association named ardaisuser.
	 * It will be deleted/edited when the association is deleted/edited.
	 */
	public com.ardais.bigr.es.beans.Ardaisuser getArdaisuser() throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the association named ardaisuser.
	 * It will be deleted/edited when the association is deleted/edited.
	 */
	public com.ardais.bigr.es.beans.ArdaisuserKey getArdaisuserKey() throws java.rmi.RemoteException;
/**
 * 
 * @return java.math.BigDecimal
 */
java.math.BigDecimal getBill_to_addr_id() throws java.rmi.RemoteException;
/**
 * Getter method for order_amount
 * @return java.math.BigDecimal
 */
java.math.BigDecimal getOrder_amount() throws java.rmi.RemoteException;
/**
 * Getter method for order_date
 * @return java.sql.Timestamp
 */
java.sql.Timestamp getOrder_date() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getOrder_po_number() throws java.rmi.RemoteException;
/**
 * Getter method for order_status
 * @return java.lang.String
 */
java.lang.String getOrder_status() throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named orderline.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getOrderline()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
/**
 * Getter method for ship_instruction
 * @return java.lang.String
 */
java.lang.String getShip_instruction() throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the association named ardaisuser.
	 * It will be deleted/edited when the association is deleted/edited.
	 */
	public void privateSetArdaisuserKey(com.ardais.bigr.es.beans.ArdaisuserKey inKey) throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Ardaisorderstatus-Ardaisorder.  
 * 	It will be deleted/edited when the association is deleted/edited.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondaryAddArdaisorderstatus(com.ardais.bigr.es.beans.Ardaisorderstatus anArdaisorderstatus) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named orderline.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddOrderline(
		com.ardais.bigr.es.beans.Orderline anOrderline)
		throws java.rmi.RemoteException;
/**
 * This method was generated for supporting the association named Ardaisorderstatus-Ardaisorder.  
 * 	It will be deleted/edited when the association is deleted/edited.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void secondaryRemoveArdaisorderstatus(com.ardais.bigr.es.beans.Ardaisorderstatus anArdaisorderstatus) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named orderline.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveOrderline(
		com.ardais.bigr.es.beans.Orderline anOrderline)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the association named ardaisuser.
	 * It will be deleted/edited when the association is deleted/edited.
	 */
	public void secondarySetArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setApproval_user_id(java.lang.String newValue) throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the association named ardaisuser.
	 * It will be deleted/edited when the association is deleted/edited.
	 */
	public void setArdaisuser(com.ardais.bigr.es.beans.Ardaisuser anArdaisuser) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.math.BigDecimal
 */
void setBill_to_addr_id(java.math.BigDecimal newValue) throws java.rmi.RemoteException;
/**
 * Setter method for order_amount
 * @param newValue java.math.BigDecimal
 */
void setOrder_amount(java.math.BigDecimal newValue) throws java.rmi.RemoteException;
/**
 * Setter method for order_date
 * @param newValue java.sql.Timestamp
 */
void setOrder_date(java.sql.Timestamp newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setOrder_po_number(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for order_status
 * @param newValue java.lang.String
 */
void setOrder_status(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for ship_instruction
 * @param newValue java.lang.String
 */
void setShip_instruction(java.lang.String newValue) throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: approved_date
	 */
	public java.sql.Timestamp getApproved_date()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: approved_date
	 */
	public void setApproved_date(java.sql.Timestamp newApproved_date)
		throws java.rmi.RemoteException;
}
