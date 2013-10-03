package com.ardais.bigr.es.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPArdaisorderstatusBean_b930be81
 */
public class EJSJDBCPersisterCMPArdaisorderstatusBean_b930be81 extends EJSJDBCPersister implements com.ardais.bigr.es.beans.EJSFinderArdaisorderstatusBean {
	private static final String _createString = "INSERT INTO ES_ARDAIS_ORDER_STATUS (ORDER_STATUS_ID, ARDAIS_USER_ID, ARDAIS_ACCT_KEY, ORDER_STATUS_DATE, ORDER_NUMBER, ORDER_STATUS_COMMENT) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ES_ARDAIS_ORDER_STATUS  WHERE ORDER_STATUS_ID = ? AND ARDAIS_USER_ID = ? AND ARDAIS_ACCT_KEY = ? AND ORDER_STATUS_DATE = ? AND ORDER_NUMBER = ?";
	private static final String _storeString = "UPDATE ES_ARDAIS_ORDER_STATUS  SET ORDER_STATUS_COMMENT = ? WHERE ORDER_STATUS_ID = ? AND ARDAIS_USER_ID = ? AND ARDAIS_ACCT_KEY = ? AND ORDER_STATUS_DATE = ? AND ORDER_NUMBER = ?";
	private static final String _loadString = " SELECT T1.ORDER_STATUS_ID, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY, T1.ORDER_STATUS_COMMENT, T1.ORDER_STATUS_DATE, T1.ORDER_NUMBER FROM ES_ARDAIS_ORDER_STATUS  T1 WHERE T1.ORDER_STATUS_ID = ? AND T1.ARDAIS_USER_ID = ? AND T1.ARDAIS_ACCT_KEY = ? AND T1.ORDER_STATUS_DATE = ? AND T1.ORDER_NUMBER = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPArdaisorderstatusBean_b930be81
	 */
	public EJSJDBCPersisterCMPArdaisorderstatusBean_b930be81() throws java.rmi.RemoteException {
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
		ArdaisorderstatusBean b = (ArdaisorderstatusBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.order_status_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.order_status_id);
			}
			if (b.ardais_user_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardais_user_id);
			}
			if (b.ardais_acct_key == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.ardais_acct_key);
			}
			if (b.order_status_comment == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.order_status_comment);
			}
			if (b.order_status_date == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, b.order_status_date);
			}
			if (b.ardaisorder_order_number == null) {
				pstmt.setNull(5, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(5, b.ardaisorder_order_number);
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
		ArdaisorderstatusBean b = (ArdaisorderstatusBean) eb;
		com.ardais.bigr.es.beans.ArdaisorderstatusKey _primaryKey = (com.ardais.bigr.es.beans.ArdaisorderstatusKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.order_status_id = _primaryKey.order_status_id;
		b.ardais_user_id = _primaryKey.ardais_user_id;
		b.ardais_acct_key = _primaryKey.ardais_acct_key;
		b.order_status_date = _primaryKey.order_status_date;
		b.ardaisorder_order_number = _primaryKey.ardaisorder_order_number;
		b.order_status_comment = resultSet.getString(4);
		b.ardaisorder_order_number = resultSet.getBigDecimal(6);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ArdaisorderstatusBean b = (ArdaisorderstatusBean) eb;
		com.ardais.bigr.es.beans.ArdaisorderstatusKey _primaryKey = (com.ardais.bigr.es.beans.ArdaisorderstatusKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.order_status_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.order_status_id);
			}
			if (_primaryKey.ardais_user_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardais_user_id);
			}
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.ardais_acct_key);
			}
			if (_primaryKey.order_status_date == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, _primaryKey.order_status_date);
			}
			if (_primaryKey.ardaisorder_order_number == null) {
				pstmt.setNull(5, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(5, _primaryKey.ardaisorder_order_number);
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
		ArdaisorderstatusBean b = (ArdaisorderstatusBean) eb;
		com.ardais.bigr.es.beans.ArdaisorderstatusKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisorderstatusKey();
		_primaryKey.order_status_id = b.order_status_id;
		_primaryKey.ardais_user_id = b.ardais_user_id;
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		_primaryKey.order_status_date = b.order_status_date;
		_primaryKey.ardaisorder_order_number = b.ardaisorder_order_number;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ArdaisorderstatusBean b = (ArdaisorderstatusBean) eb;
		com.ardais.bigr.es.beans.ArdaisorderstatusKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisorderstatusKey();
		_primaryKey.order_status_id = b.order_status_id;
		_primaryKey.ardais_user_id = b.ardais_user_id;
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		_primaryKey.order_status_date = b.order_status_date;
		_primaryKey.ardaisorder_order_number = b.ardaisorder_order_number;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.order_status_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.order_status_id);
			}
			if (_primaryKey.ardais_user_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.ardais_user_id);
			}
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, _primaryKey.ardais_acct_key);
			}
			if (_primaryKey.order_status_date == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, _primaryKey.order_status_date);
			}
			if (_primaryKey.ardaisorder_order_number == null) {
				pstmt.setNull(6, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(6, _primaryKey.ardaisorder_order_number);
			}
			if (b.order_status_comment == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.order_status_comment);
			}
			if (b.ardaisorder_order_number == null) {
				pstmt.setNull(6, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(6, b.ardaisorder_order_number);
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
		ArdaisorderstatusBean b = (ArdaisorderstatusBean) eb;
		com.ardais.bigr.es.beans.ArdaisorderstatusKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisorderstatusKey();
		_primaryKey.order_status_id = b.order_status_id;
		_primaryKey.ardais_user_id = b.ardais_user_id;
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		_primaryKey.order_status_date = b.order_status_date;
		_primaryKey.ardaisorder_order_number = b.ardaisorder_order_number;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.order_status_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.order_status_id);
			}
			if (_primaryKey.ardais_user_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardais_user_id);
			}
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.ardais_acct_key);
			}
			if (_primaryKey.order_status_date == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, _primaryKey.order_status_date);
			}
			if (_primaryKey.ardaisorder_order_number == null) {
				pstmt.setNull(5, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(5, _primaryKey.ardaisorder_order_number);
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
		com.ardais.bigr.es.beans.ArdaisorderstatusKey key = new com.ardais.bigr.es.beans.ArdaisorderstatusKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.order_status_id = resultSet.getString(1);
			key.ardais_user_id = resultSet.getString(2);
			key.ardais_acct_key = resultSet.getString(3);
			key.order_status_date = resultSet.getTimestamp(5);
			key.ardaisorder_order_number = resultSet.getBigDecimal(6);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Ardaisorderstatus findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisorderstatusKey primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.es.beans.Ardaisorderstatus) home.activateBean(primaryKey);
	}
	/**
	 * findArdaisorderstatusByArdaisorder
	 */
	public EJSFinder findArdaisorderstatusByArdaisorder(com.ardais.bigr.es.beans.ArdaisorderKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_STATUS_ID, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY, T1.ORDER_STATUS_COMMENT, T1.ORDER_STATUS_DATE, T1.ORDER_NUMBER FROM ES_ARDAIS_ORDER_STATUS  T1 WHERE T1.ORDER_NUMBER = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.order_number == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, inKey.order_number);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
