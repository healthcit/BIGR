package com.ardais.bigr.es.beans;
/**
 * Remote interface for Enterprise Bean: Shoppingcartdetail
 */
public interface Shoppingcartdetail extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {







	/**
	 * Get accessor for persistent attribute: shopping_cart_line_amount
	 */
	public java.math.BigDecimal getShopping_cart_line_amount()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: shopping_cart_line_amount
	 */
	public void setShopping_cart_line_amount(
		java.math.BigDecimal newShopping_cart_line_amount)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: barcode_id
	 */
	public java.lang.String getBarcode_id() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: barcode_id
	 */
	public void setBarcode_id(java.lang.String newBarcode_id)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: search_desc
	 */
	public java.lang.String getSearch_desc() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: search_desc
	 */
	public void setSearch_desc(java.lang.String newSearch_desc)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: creation_dt
	 */
	public java.sql.Timestamp getCreation_dt() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: creation_dt
	 */
	public void setCreation_dt(java.sql.Timestamp newCreation_dt)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named shoppingcart.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.es.beans.ShoppingcartKey getShoppingcartKey()
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named shoppingcart.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.es.beans.Shoppingcart getShoppingcart()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
  /**
   * Get accessor for persistent attribute: quantity
   */
  public java.lang.Integer getQuantity() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: quantity
   */
  public void setQuantity(java.lang.Integer newQuantity) throws java.rmi.RemoteException;
  /**
   * Get accessor for persistent attribute: productType
   */
  public java.lang.String getProductType() throws java.rmi.RemoteException;
  /**
   * Set accessor for persistent attribute: productType
   */
  public void setProductType(java.lang.String newProductType) throws java.rmi.RemoteException;
}
