package com.ardais.bigr.es.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPOrderlineBean_3a6e7917
 */
public class EJSJDBCPersisterCMPOrderlineBean_3a6e7917 extends EJSJDBCPersister implements com.ardais.bigr.es.beans.EJSFinderOrderlineBean {
	private static final String _createString = "INSERT INTO ES_ORDER_LINE (ORDER_LINE_NUMBER, ORDER_NUMBER, ORDER_LINE_AMOUNT, BARCODE_ID, CONSORTIUM_IND) VALUES (?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ES_ORDER_LINE  WHERE ORDER_LINE_NUMBER = ? AND ORDER_NUMBER = ?";
	private static final String _storeString = "UPDATE ES_ORDER_LINE  SET ORDER_LINE_AMOUNT = ?, BARCODE_ID = ?, CONSORTIUM_IND = ? WHERE ORDER_LINE_NUMBER = ? AND ORDER_NUMBER = ?";
	private static final String _loadString = " SELECT T1.ORDER_LINE_NUMBER, T1.ORDER_LINE_AMOUNT, T1.BARCODE_ID, T1.CONSORTIUM_IND, T1.ORDER_NUMBER FROM ES_ORDER_LINE  T1 WHERE T1.ORDER_LINE_NUMBER = ? AND T1.ORDER_NUMBER = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPOrderlineBean_3a6e7917
	 */
	public EJSJDBCPersisterCMPOrderlineBean_3a6e7917() throws java.rmi.RemoteException {
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
		OrderlineBean b = (OrderlineBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.order_line_number == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, b.order_line_number);
			}
			if (b.order_line_amount == null) {
				pstmt.setNull(3, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(3, b.order_line_amount);
			}
			if (b.barcode_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.barcode_id);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.consortium_ind);
			if (objectTemp == null) {
				pstmt.setNull(5, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(5, (java.lang.String)objectTemp);
			}
			if (b.ardaisorder_order_number == null) {
				pstmt.setNull(2, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(2, b.ardaisorder_order_number);
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
		OrderlineBean b = (OrderlineBean) eb;
		com.ardais.bigr.es.beans.OrderlineKey _primaryKey = (com.ardais.bigr.es.beans.OrderlineKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.order_line_number = _primaryKey.order_line_number;
		b.ardaisorder_order_number = _primaryKey.ardaisorder_order_number;
		b.order_line_amount = resultSet.getBigDecimal(2);
		b.barcode_id = resultSet.getString(3);
		b.consortium_ind = (java.lang.String)com.ibm.vap.converters.VapTrimStringConverter.singleton().objectFrom(resultSet.getString(4));
		b.ardaisorder_order_number = resultSet.getBigDecimal(5);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		OrderlineBean b = (OrderlineBean) eb;
		com.ardais.bigr.es.beans.OrderlineKey _primaryKey = (com.ardais.bigr.es.beans.OrderlineKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.order_line_number == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.order_line_number);
			}
			if (_primaryKey.ardaisorder_order_number == null) {
				pstmt.setNull(2, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(2, _primaryKey.ardaisorder_order_number);
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
		OrderlineBean b = (OrderlineBean) eb;
		com.ardais.bigr.es.beans.OrderlineKey _primaryKey = new com.ardais.bigr.es.beans.OrderlineKey();
		_primaryKey.order_line_number = b.order_line_number;
		_primaryKey.ardaisorder_order_number = b.ardaisorder_order_number;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		OrderlineBean b = (OrderlineBean) eb;
		com.ardais.bigr.es.beans.OrderlineKey _primaryKey = new com.ardais.bigr.es.beans.OrderlineKey();
		_primaryKey.order_line_number = b.order_line_number;
		_primaryKey.ardaisorder_order_number = b.ardaisorder_order_number;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.order_line_number == null) {
				pstmt.setNull(4, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(4, _primaryKey.order_line_number);
			}
			if (_primaryKey.ardaisorder_order_number == null) {
				pstmt.setNull(5, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(5, _primaryKey.ardaisorder_order_number);
			}
			if (b.order_line_amount == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, b.order_line_amount);
			}
			if (b.barcode_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.barcode_id);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.consortium_ind);
			if (objectTemp == null) {
				pstmt.setNull(3, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(3, (java.lang.String)objectTemp);
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
	 * remove
	 */
	public void remove(EntityBean eb) throws Exception {
		Object objectTemp = null;
		OrderlineBean b = (OrderlineBean) eb;
		com.ardais.bigr.es.beans.OrderlineKey _primaryKey = new com.ardais.bigr.es.beans.OrderlineKey();
		_primaryKey.order_line_number = b.order_line_number;
		_primaryKey.ardaisorder_order_number = b.ardaisorder_order_number;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.order_line_number == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.order_line_number);
			}
			if (_primaryKey.ardaisorder_order_number == null) {
				pstmt.setNull(2, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(2, _primaryKey.ardaisorder_order_number);
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
		com.ardais.bigr.es.beans.OrderlineKey key = new com.ardais.bigr.es.beans.OrderlineKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.order_line_number = resultSet.getBigDecimal(1);
			key.ardaisorder_order_number = resultSet.getBigDecimal(5);
			return key;
		}
return null;
	}
	/**
	 * findOrderlineByArdaisorder
	 */
	public EJSFinder findOrderlineByArdaisorder(com.ardais.bigr.es.beans.ArdaisorderKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ORDER_LINE_NUMBER, T1.ORDER_LINE_AMOUNT, T1.BARCODE_ID, T1.CONSORTIUM_IND, T1.ORDER_NUMBER FROM ES_ORDER_LINE  T1 WHERE T1.ORDER_NUMBER = ?");
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
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Orderline findByPrimaryKey(com.ardais.bigr.es.beans.OrderlineKey primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.es.beans.Orderline) home.activateBean(primaryKey);
	}
}
