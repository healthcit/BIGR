package com.ardais.bigr.es.beans;

/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface Orderline extends com.ibm.ivj.ejb.runtime.CopyHelper, javax.ejb.EJBObject {
	/**
	 * This method was generated for supporting the relationship role named ardaisorder.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.es.beans.Ardaisorder getArdaisorder()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the relationship role named ardaisorder.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.es.beans.ArdaisorderKey getArdaisorderKey()
		throws java.rmi.RemoteException;
/**
 * Getter method for barcode_id
 * @return java.lang.String
 */
java.lang.String getBarcode_id() throws java.rmi.RemoteException;
/**
 * 
 * @return java.lang.String
 */
java.lang.String getConsortium_ind() throws java.rmi.RemoteException;
/**
 * Getter method for order_line_amount
 * @return java.math.BigDecimal
 */
java.math.BigDecimal getOrder_line_amount() throws java.rmi.RemoteException;
/**
 * Setter method for barcode_id
 * @param newValue java.lang.String
 */
void setBarcode_id(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * 
 * @return void
 * @param newValue java.lang.String
 */
void setConsortium_ind(java.lang.String newValue) throws java.rmi.RemoteException;
/**
 * Setter method for order_line_amount
 * @param newValue java.math.BigDecimal
 */
void setOrder_line_amount(java.math.BigDecimal newValue) throws java.rmi.RemoteException;
}
