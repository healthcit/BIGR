package com.ardais.bigr.es.beans;
/**
 * Remote interface for Enterprise Bean: Shoppingcart
 */
public interface Shoppingcart extends javax.ejb.EJBObject, com.ibm.ivj.ejb.runtime.CopyHelper {

	/**
	 * Get accessor for persistent attribute: last_update_dt
	 */
	public java.sql.Timestamp getLast_update_dt()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: last_update_dt
	 */
	public void setLast_update_dt(java.sql.Timestamp newLast_update_dt)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: cart_create_date
	 */
	public java.sql.Timestamp getCart_create_date()
		throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: cart_create_date
	 */
	public void setCart_create_date(java.sql.Timestamp newCart_create_date)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisuser.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.es.beans.ArdaisuserKey getArdaisuserKey()
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisuser.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.es.beans.Ardaisuser getArdaisuser()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * This method was generated for supporting the relationship role named shoppingcartdetail.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryAddShoppingcartdetail(
		com.ardais.bigr.es.beans.Shoppingcartdetail aShoppingcartdetail)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named shoppingcartdetail.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void secondaryRemoveShoppingcartdetail(
		com.ardais.bigr.es.beans.Shoppingcartdetail aShoppingcartdetail)
		throws java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named shoppingcartdetail.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration getShoppingcartdetail()
		throws java.rmi.RemoteException, javax.ejb.FinderException;
}
