package com.ardais.bigr.es.beans;
/**
 * ArdaisorderAccessBeanData
 * @generated
 */
public interface ArdaisorderAccessBeanData {
	/**
	 * getOrder_po_number
	 * @generated
	 */
	public java.lang.String getOrder_po_number()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * setOrder_po_number
	 * @generated
	 */
	public void setOrder_po_number(java.lang.String newValue);
	/**
	 * getArdaisuserKey
	 * @generated
	 */
	public com.ardais.bigr.es.beans.ArdaisuserKey getArdaisuserKey()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * getBill_to_addr_id
	 * @generated
	 */
	public java.math.BigDecimal getBill_to_addr_id()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * setBill_to_addr_id
	 * @generated
	 */
	public void setBill_to_addr_id(java.math.BigDecimal newValue);
	/**
	 * getOrder_date
	 * @generated
	 */
	public java.sql.Timestamp getOrder_date()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * setOrder_date
	 * @generated
	 */
	public void setOrder_date(java.sql.Timestamp newValue);
	/**
	 * getApproved_date
	 * @generated
	 */
	public java.sql.Timestamp getApproved_date()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * setApproved_date
	 * @generated
	 */
	public void setApproved_date(java.sql.Timestamp newValue);
	/**
	 * getOrder_status
	 * @generated
	 */
	public java.lang.String getOrder_status()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * setOrder_status
	 * @generated
	 */
	public void setOrder_status(java.lang.String newValue);
	/**
	 * getOrder_amount
	 * @generated
	 */
	public java.math.BigDecimal getOrder_amount()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * setOrder_amount
	 * @generated
	 */
	public void setOrder_amount(java.math.BigDecimal newValue);
	/**
	 * getShip_instruction
	 * @generated
	 */
	public java.lang.String getShip_instruction()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * setShip_instruction
	 * @generated
	 */
	public void setShip_instruction(java.lang.String newValue);
	/**
	 * getApproval_user_id
	 * @generated
	 */
	public java.lang.String getApproval_user_id()
		throws
			java.rmi.RemoteException,
			javax.ejb.CreateException,
			javax.ejb.FinderException,
			javax.naming.NamingException;
	/**
	 * setApproval_user_id
	 * @generated
	 */
	public void setApproval_user_id(java.lang.String newValue);
}
