package com.ardais.bigr.es.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPArdaisorderBean_3e397772
 */
public class EJSJDBCPersisterCMPArdaisorderBean_3e397772 extends EJSJDBCPersister implements com.ardais.bigr.es.beans.EJSFinderArdaisorderBean {
	private static final String _createString = "INSERT INTO ES_ARDAIS_ORDER (ORDER_NUMBER, ORDER_DATE, ORDER_STATUS, ORDER_AMOUNT, BILL_TO_ADDR_ID, SHIP_INSTRUCTION, APPROVAL_USER_ID, ORDER_PO_NUMBER, APPROVED_DATE, ARDAIS_USER_ID, ARDAIS_ACCT_KEY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ES_ARDAIS_ORDER  WHERE ORDER_NUMBER = ?";
	private static final String _storeString = "UPDATE ES_ARDAIS_ORDER  SET ORDER_DATE = ?, ORDER_STATUS = ?, ORDER_AMOUNT = ?, BILL_TO_ADDR_ID = ?, SHIP_INSTRUCTION = ?, APPROVAL_USER_ID = ?, ORDER_PO_NUMBER = ?, APPROVED_DATE = ?, ARDAIS_USER_ID = ?, ARDAIS_ACCT_KEY = ? WHERE ORDER_NUMBER = ?";
	private static final String _loadString = " SELECT T1.ORDER_NUMBER, T1.ORDER_DATE, T1.ORDER_STATUS, T1.ORDER_AMOUNT, T1.BILL_TO_ADDR_ID, T1.SHIP_INSTRUCTION, T1.APPROVAL_USER_ID, T1.ORDER_PO_NUMBER, T1.APPROVED_DATE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_ORDER  T1 WHERE T1.ORDER_NUMBER = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPArdaisorderBean_3e397772
	 */
	public EJSJDBCPersisterCMPArdaisorderBean_3e397772() throws java.rmi.RemoteException {
		super();	}
	/**
	 * postInit
	 */
	public void postInit() {
	}
	/**
	 * _create
	 */
	public void _create(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ArdaisorderBean b = (ArdaisorderBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.order_number == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, b.order_number);
			}
			if (b.order_date == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.order_date);
			}
			if (b.order_status == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.order_status);
			}
			if (b.order_amount == null) {
				pstmt.setNull(4, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(4, b.order_amount);
			}
			if (b.bill_to_addr_id == null) {
				pstmt.setNull(5, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(5, b.bill_to_addr_id);
			}
			if (b.ship_instruction == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.ship_instruction);
			}
			if (b.approval_user_id == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.approval_user_id);
			}
			if (b.order_po_number == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.order_po_number);
			}
			if (b.approved_date == null) {
				pstmt.setNull(9, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(9, b.approved_date);
			}
			if (b.ardaisuser_ardais_user_id == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.ardaisuser_ardais_user_id);
			}
			if (b.ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.ardaisuser_ardaisaccount_ardais_acct_key);
			}
			pstmt.executeUpdate();
		}
		finally {
			returnPreparedStatement(pstmt);
		}
	}
	/**
	 * hydrate
	 */
	public void hydrate(EntityBean eb, Object data, Object pKey) throws Exception {
		Object objectTemp = null;
		ArdaisorderBean b = (ArdaisorderBean) eb;
		com.ardais.bigr.es.beans.ArdaisorderKey _primaryKey = (com.ardais.bigr.es.beans.ArdaisorderKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.order_number = _primaryKey.order_number;
		b.order_date = resultSet.getTimestamp(2);
		b.order_status = resultSet.getString(3);
		b.order_amount = resultSet.getBigDecimal(4);
		b.bill_to_addr_id = resultSet.getBigDecimal(5);
		b.ship_instruction = resultSet.getString(6);
		b.approval_user_id = resultSet.getString(7);
		b.order_po_number = resultSet.getString(8);
		b.approved_date = resultSet.getTimestamp(9);
		b.ardaisuser_ardais_user_id = resultSet.getString(10);
		b.ardaisuser_ardaisaccount_ardais_acct_key = resultSet.getString(11);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ArdaisorderBean b = (ArdaisorderBean) eb;
		com.ardais.bigr.es.beans.ArdaisorderKey _primaryKey = (com.ardais.bigr.es.beans.ArdaisorderKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.order_number == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.order_number);
			}
			resultSet = pstmt.executeQuery();
			if (!(resultSet.next())) throw new javax.ejb.ObjectNotFoundException();
			hydrate(eb, resultSet, pKey);
		}
		finally {
			if (resultSet != null) resultSet.close();
			returnPreparedStatement(pstmt);
		}
	}
	/**
	 * refresh
	 */
	public void refresh(EntityBean eb, boolean forUpdate) throws Exception {
		ArdaisorderBean b = (ArdaisorderBean) eb;
		com.ardais.bigr.es.beans.ArdaisorderKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisorderKey();
		_primaryKey.order_number = b.order_number;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ArdaisorderBean b = (ArdaisorderBean) eb;
		com.ardais.bigr.es.beans.ArdaisorderKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisorderKey();
		_primaryKey.order_number = b.order_number;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.order_number == null) {
				pstmt.setNull(11, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(11, _primaryKey.order_number);
			}
			if (b.order_date == null) {
				pstmt.setNull(1, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(1, b.order_date);
			}
			if (b.order_status == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.order_status);
			}
			if (b.order_amount == null) {
				pstmt.setNull(3, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(3, b.order_amount);
			}
			if (b.bill_to_addr_id == null) {
				pstmt.setNull(4, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(4, b.bill_to_addr_id);
			}
			if (b.ship_instruction == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.ship_instruction);
			}
			if (b.approval_user_id == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.approval_user_id);
			}
			if (b.order_po_number == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.order_po_number);
			}
			if (b.approved_date == null) {
				pstmt.setNull(8, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(8, b.approved_date);
			}
			if (b.ardaisuser_ardais_user_id == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.ardaisuser_ardais_user_id);
			}
			if (b.ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.ardaisuser_ardaisaccount_ardais_acct_key);
			}
			pstmt.executeUpdate();
		}
		finally {
			returnPreparedStatement(pstmt);
		}
	}
	/**
	 * remove
	 */
	public void remove(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ArdaisorderBean b = (ArdaisorderBean) eb;
		com.ardais.bigr.es.beans.ArdaisorderKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisorderKey();
		_primaryKey.order_number = b.order_number;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.order_number == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.order_number);
			}
			pstmt.executeUpdate();
		}
		finally {
			returnPreparedStatement(pstmt);
		}
	}
	/**
	 * getPrimaryKey
	 */
	public Object getPrimaryKey(Object data) throws Exception {
		com.ardais.bigr.es.beans.ArdaisorderKey key = new com.ardais.bigr.es.beans.ArdaisorderKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.order_number = resultSet.getBigDecimal(1);
			return key;
		}
return null;
	}
	/**
	 * findByOrderStatus
	 */
	public EJSFinder findByOrderStatus(java.lang.String ardOrder_status) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_NUMBER, T1.ORDER_DATE, T1.ORDER_STATUS, T1.ORDER_AMOUNT, T1.BILL_TO_ADDR_ID, T1.SHIP_INSTRUCTION, T1.APPROVAL_USER_ID, T1.ORDER_PO_NUMBER, T1.APPROVED_DATE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_ORDER  T1 WHERE order_status = ?");
			if (ardOrder_status == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, ardOrder_status);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findUserOpenOrders
	 */
	public EJSFinder findUserOpenOrders(java.lang.String user, java.lang.String account) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_NUMBER, T1.ORDER_DATE, T1.ORDER_STATUS, T1.ORDER_AMOUNT, T1.BILL_TO_ADDR_ID, T1.SHIP_INSTRUCTION, T1.APPROVAL_USER_ID, T1.ORDER_PO_NUMBER, T1.APPROVED_DATE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_ORDER  T1 WHERE ardais_user_id = ? AND ardais_acct_key= ?");
			if (user == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, user);
			}
			if (account == null) {
			   pstmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(2, account);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Ardaisorder findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisorderKey primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.es.beans.Ardaisorder) home.activateBean(primaryKey);
	}
	/**
	 * findAllOrders
	 */
	public EJSFinder findAllOrders() throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_NUMBER, T1.ORDER_DATE, T1.ORDER_STATUS, T1.ORDER_AMOUNT, T1.BILL_TO_ADDR_ID, T1.SHIP_INSTRUCTION, T1.APPROVAL_USER_ID, T1.ORDER_PO_NUMBER, T1.APPROVED_DATE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_ORDER  T1 WHERE 1 = 1");
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findByOrderNumber
	 */
	public EJSFinder findByOrderNumber(java.lang.String orderID) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_NUMBER, T1.ORDER_DATE, T1.ORDER_STATUS, T1.ORDER_AMOUNT, T1.BILL_TO_ADDR_ID, T1.SHIP_INSTRUCTION, T1.APPROVAL_USER_ID, T1.ORDER_PO_NUMBER, T1.APPROVED_DATE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_ORDER  T1 WHERE order_number = ?");
			if (orderID == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, orderID);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findAccountOrderswithStatus
	 */
	public EJSFinder findAccountOrderswithStatus(java.lang.String arg_account, java.lang.String arg_status) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_NUMBER, T1.ORDER_DATE, T1.ORDER_STATUS, T1.ORDER_AMOUNT, T1.BILL_TO_ADDR_ID, T1.SHIP_INSTRUCTION, T1.APPROVAL_USER_ID, T1.ORDER_PO_NUMBER, T1.APPROVED_DATE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_ORDER  T1 WHERE ardais_acct_key = ? AND order_status = ?");
			if (arg_account == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, arg_account);
			}
			if (arg_status == null) {
			   pstmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(2, arg_status);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findByAccount
	 */
	public EJSFinder findByAccount(java.lang.String arg_accountID) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_NUMBER, T1.ORDER_DATE, T1.ORDER_STATUS, T1.ORDER_AMOUNT, T1.BILL_TO_ADDR_ID, T1.SHIP_INSTRUCTION, T1.APPROVAL_USER_ID, T1.ORDER_PO_NUMBER, T1.APPROVED_DATE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_ORDER  T1 WHERE ardais_acct_key = ?");
			if (arg_accountID == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, arg_accountID);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findUserOrderHistory
	 */
	public EJSFinder findUserOrderHistory(java.lang.String user, java.lang.String account) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_NUMBER, T1.ORDER_DATE, T1.ORDER_STATUS, T1.ORDER_AMOUNT, T1.BILL_TO_ADDR_ID, T1.SHIP_INSTRUCTION, T1.APPROVAL_USER_ID, T1.ORDER_PO_NUMBER, T1.APPROVED_DATE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_ORDER  T1 WHERE ardais_user_id = ? AND ardais_acct_key= ? AND order_status = \'CL\' OR order_status = \'CA\'");
			if (user == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, user);
			}
			if (account == null) {
			   pstmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(2, account);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findAccountOrderHistory
	 */
	public EJSFinder findAccountOrderHistory(java.lang.String accountID) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_NUMBER, T1.ORDER_DATE, T1.ORDER_STATUS, T1.ORDER_AMOUNT, T1.BILL_TO_ADDR_ID, T1.SHIP_INSTRUCTION, T1.APPROVAL_USER_ID, T1.ORDER_PO_NUMBER, T1.APPROVED_DATE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_ORDER  T1 WHERE ardais_acct_key = ?  AND order_status = \'CL\' OR order_status = \'CA\'");
			if (accountID == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, accountID);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findArdaisorderByArdaisuser
	 */
	public EJSFinder findArdaisorderByArdaisuser(com.ardais.bigr.es.beans.ArdaisuserKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_NUMBER, T1.ORDER_DATE, T1.ORDER_STATUS, T1.ORDER_AMOUNT, T1.BILL_TO_ADDR_ID, T1.SHIP_INSTRUCTION, T1.APPROVAL_USER_ID, T1.ORDER_PO_NUMBER, T1.APPROVED_DATE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_ORDER  T1 WHERE T1.ARDAIS_USER_ID = ? AND T1.ARDAIS_ACCT_KEY = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.ardais_user_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.ardais_user_id);
			}
			if (inKey.ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, inKey.ardaisaccount_ardais_acct_key);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
