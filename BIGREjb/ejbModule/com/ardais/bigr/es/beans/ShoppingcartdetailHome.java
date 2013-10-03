package com.ardais.bigr.es.beans;

import java.math.BigDecimal;


/**
 * Home interface for Enterprise Bean: Shoppingcartdetail
 */
public interface ShoppingcartdetailHome extends javax.ejb.EJBHome {
	/**
	 * Creates an instance from a key for Entity Bean: Shoppingcartdetail
	 */
	public com.ardais.bigr.es.beans.Shoppingcartdetail create(
		java.math.BigDecimal shopping_cart_line_number,
		com.ardais.bigr.es.beans.Shoppingcart argShoppingcart)
		throws javax.ejb.CreateException, java.rmi.RemoteException;

  /**
   * Creates an instance using the parent cart key info, rather than the cart EJB.
   * Added to avoid a 2-phase commit bug in CMP links.
   */
  public com.ardais.bigr.es.beans.Shoppingcartdetail create(
    String barcode_id,
    String productType,
    java.math.BigDecimal shopping_cart_line_number,
    String userId,
    String acctId,
    BigDecimal cartNo)
    throws javax.ejb.CreateException, java.rmi.RemoteException;
    
	/**
	 * Finds an instance using a key for Entity Bean: Shoppingcartdetail
	 */
	public com.ardais.bigr.es.beans.Shoppingcartdetail findByPrimaryKey(
		com.ardais.bigr.es.beans.ShoppingcartdetailKey primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
		* Insert the method's description here.
		* Creation date: (3/9/2001 4:23:04 PM)
		* @return java.util.Enumeration
		* @param barcode_arg java.lang.String
		* @param user_arg java.lang.String
		* @param account_arg java.lang.String
		* @exception javax.ejb.FinderException The exception description.
		*/
	java.util.Enumeration findByBarcodeUserAccount(
		String barcode_arg,
		String user_arg,
		String account_arg)
		throws java.rmi.RemoteException, javax.ejb.FinderException;

	/**
	* Insert the method's description here.
	* Creation date: (3/13/2001 5:16:38 PM)
	* @param user java.lang.String
	* @param account java.lang.String
	* @exception javax.ejb.FinderException The exception description.
	*/
	java.util.Enumeration findByUserAccountOrderByLineNumber(
		String user,
		String account)
		throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the relationship role named shoppingcart.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration findShoppingcartdetailByShoppingcart(
		com.ardais.bigr.es.beans.ShoppingcartKey inKey)
		throws java.rmi.RemoteException, javax.ejb.FinderException;
}
