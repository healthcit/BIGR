package com.ardais.bigr.es.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPArdaisaddressBean_7d45b735
 */
public class EJSJDBCPersisterCMPArdaisaddressBean_7d45b735 extends EJSJDBCPersister implements com.ardais.bigr.es.beans.EJSFinderArdaisaddressBean {
	private static final String _createString = "INSERT INTO ARDAIS_ADDRESS (ADDRESS_ID, ARDAIS_ACCT_KEY, ADDRESS_TYPE, ADDRESS_1, ADDRESS_2, ADDR_CITY, ADDR_STATE, ADDR_ZIP_CODE, ADDR_COUNTRY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ARDAIS_ADDRESS  WHERE ADDRESS_ID = ? AND ARDAIS_ACCT_KEY = ?";
	private static final String _storeString = "UPDATE ARDAIS_ADDRESS  SET ADDRESS_TYPE = ?, ADDRESS_1 = ?, ADDRESS_2 = ?, ADDR_CITY = ?, ADDR_STATE = ?, ADDR_ZIP_CODE = ?, ADDR_COUNTRY = ? WHERE ADDRESS_ID = ? AND ARDAIS_ACCT_KEY = ?";
	private static final String _loadString = " SELECT T1.ADDRESS_ID, T1.ARDAIS_ACCT_KEY, T1.ADDRESS_TYPE, T1.ADDRESS_1, T1.ADDRESS_2, T1.ADDR_CITY, T1.ADDR_STATE, T1.ADDR_ZIP_CODE, T1.ADDR_COUNTRY FROM ARDAIS_ADDRESS  T1 WHERE T1.ADDRESS_ID = ? AND T1.ARDAIS_ACCT_KEY = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPArdaisaddressBean_7d45b735
	 */
	public EJSJDBCPersisterCMPArdaisaddressBean_7d45b735() throws java.rmi.RemoteException {
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
		ArdaisaddressBean b = (ArdaisaddressBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.address_id == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, b.address_id);
			}
			if (b.ardais_acct_key == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardais_acct_key);
			}
			if (b.address_type == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.address_type);
			}
			if (b.address_1 == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.address_1);
			}
			if (b.address_2 == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.address_2);
			}
			if (b.addr_city == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.addr_city);
			}
			if (b.addr_state == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.addr_state);
			}
			if (b.addr_zip_code == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.addr_zip_code);
			}
			if (b.addr_country == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.addr_country);
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
		ArdaisaddressBean b = (ArdaisaddressBean) eb;
		com.ardais.bigr.es.beans.ArdaisaddressKey _primaryKey = (com.ardais.bigr.es.beans.ArdaisaddressKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.address_id = _primaryKey.address_id;
		b.ardais_acct_key = _primaryKey.ardais_acct_key;
		b.address_type = resultSet.getString(3);
		b.address_1 = resultSet.getString(4);
		b.address_2 = resultSet.getString(5);
		b.addr_city = resultSet.getString(6);
		b.addr_state = resultSet.getString(7);
		b.addr_zip_code = resultSet.getString(8);
		b.addr_country = resultSet.getString(9);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ArdaisaddressBean b = (ArdaisaddressBean) eb;
		com.ardais.bigr.es.beans.ArdaisaddressKey _primaryKey = (com.ardais.bigr.es.beans.ArdaisaddressKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.address_id == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.address_id);
			}
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardais_acct_key);
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
		ArdaisaddressBean b = (ArdaisaddressBean) eb;
		com.ardais.bigr.es.beans.ArdaisaddressKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisaddressKey();
		_primaryKey.address_id = b.address_id;
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ArdaisaddressBean b = (ArdaisaddressBean) eb;
		com.ardais.bigr.es.beans.ArdaisaddressKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisaddressKey();
		_primaryKey.address_id = b.address_id;
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.address_id == null) {
				pstmt.setNull(8, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(8, _primaryKey.address_id);
			}
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, _primaryKey.ardais_acct_key);
			}
			if (b.address_type == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.address_type);
			}
			if (b.address_1 == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.address_1);
			}
			if (b.address_2 == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.address_2);
			}
			if (b.addr_city == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.addr_city);
			}
			if (b.addr_state == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.addr_state);
			}
			if (b.addr_zip_code == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.addr_zip_code);
			}
			if (b.addr_country == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.addr_country);
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
		ArdaisaddressBean b = (ArdaisaddressBean) eb;
		com.ardais.bigr.es.beans.ArdaisaddressKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisaddressKey();
		_primaryKey.address_id = b.address_id;
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.address_id == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.address_id);
			}
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardais_acct_key);
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
		com.ardais.bigr.es.beans.ArdaisaddressKey key = new com.ardais.bigr.es.beans.ArdaisaddressKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.address_id = resultSet.getBigDecimal(1);
			key.ardais_acct_key = resultSet.getString(2);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Ardaisaddress findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisaddressKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.es.beans.Ardaisaddress) home.activateBean(key);
	}
	/**
	 * findByAccountandType
	 */
	public EJSFinder findByAccountandType(java.lang.String arg_Account, java.lang.String arg_Type) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ADDRESS_ID, T1.ARDAIS_ACCT_KEY, T1.ADDRESS_TYPE, T1.ADDRESS_1, T1.ADDRESS_2, T1.ADDR_CITY, T1.ADDR_STATE, T1.ADDR_ZIP_CODE, T1.ADDR_COUNTRY FROM ARDAIS_ADDRESS  T1 WHERE ARDAIS_ACCT_KEY = ? AND ADDRESS_TYPE = ?");
			if (arg_Account == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, arg_Account);
			}
			if (arg_Type == null) {
			   pstmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(2, arg_Type);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
