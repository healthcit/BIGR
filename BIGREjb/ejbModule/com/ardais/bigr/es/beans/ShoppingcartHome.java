package com.ardais.bigr.es.beans;
/**
 * Home interface for Enterprise Bean: Shoppingcart
 */
public interface ShoppingcartHome extends javax.ejb.EJBHome {
	/**
	 * Creates an instance from a key for Entity Bean: Shoppingcart
	 */
	public com.ardais.bigr.es.beans.Shoppingcart create(
		java.math.BigDecimal shopping_cart_id,
		com.ardais.bigr.es.beans.Ardaisuser argArdaisuser)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
    
  /**
   * Creates an instance using the user key info, rather than the user.
   * Added to avoid a 2-phase commit bug in CMP links.
   */
  public com.ardais.bigr.es.beans.Shoppingcart create(
    java.math.BigDecimal shopping_cart_id,
    String userId,
    String acctId)
    throws javax.ejb.CreateException, java.rmi.RemoteException;
    
	/**
	 * Finds an instance using a key for Entity Bean: Shoppingcart
	 */
	public com.ardais.bigr.es.beans.Shoppingcart findByPrimaryKey(
		com.ardais.bigr.es.beans.ShoppingcartKey primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisuser.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration findShoppingcartByArdaisuser(
		com.ardais.bigr.es.beans.ArdaisuserKey inKey)
		throws java.rmi.RemoteException, javax.ejb.FinderException;

	public java.util.Enumeration findByUserAccount(
		java.lang.String user,
		java.lang.String account)
		throws java.rmi.RemoteException, javax.ejb.FinderException;
}
