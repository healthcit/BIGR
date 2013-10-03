package com.ardais.bigr.es.beans;

/**
 * This is a Home interface for the Entity Bean
 */
public interface OrderlineHome extends javax.ejb.EJBHome {

/**
 * create method for a CMP entity bean
 * @return com.ardais.bigr.es.beans.Orderline
 * @param argOrder_line_number java.math.BigDecimal
 * @param argArdaisorder com.ardais.bigr.es.beans.ArdaisorderKey
 * @exception javax.ejb.CreateException The exception description.
 */
com.ardais.bigr.es.beans.Orderline create(java.math.BigDecimal argOrder_line_number, com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder) throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * Finds an instance using a key for Entity Bean: Orderline
	 */
	public com.ardais.bigr.es.beans.Orderline findByPrimaryKey(
		com.ardais.bigr.es.beans.OrderlineKey primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
	/**
	 * This method was generated for supporting the relationship role named ardaisorder.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public java.util.Enumeration findOrderlineByArdaisorder(
		com.ardais.bigr.es.beans.ArdaisorderKey inKey)
		throws java.rmi.RemoteException, javax.ejb.FinderException;
	/**
	 * Creates an instance from a key for Entity Bean: Orderline
	 */
	public com.ardais.bigr.es.beans.Orderline create(
		java.math.BigDecimal order_line_number,
		com.ardais.bigr.es.beans.Ardaisorder argArdaisorder)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
