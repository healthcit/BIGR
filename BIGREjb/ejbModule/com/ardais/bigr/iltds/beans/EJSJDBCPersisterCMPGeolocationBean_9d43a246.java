package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPGeolocationBean_9d43a246
 */
public class EJSJDBCPersisterCMPGeolocationBean_9d43a246 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderGeolocationBean {
	private static final String _createString = "INSERT INTO ILTDS_GEOGRAPHY_LOCATION (LOCATION_ADDRESS_ID, LOCATION_NAME, LOCATION_ADDRESS_1, LOCATION_ADDRESS_2, LOCATION_CITY, LOCATION_STATE, LOCATION_ZIP, LOCATION_PHONE, LOCATION_COUNTRY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_GEOGRAPHY_LOCATION  WHERE LOCATION_ADDRESS_ID = ?";
	private static final String _storeString = "UPDATE ILTDS_GEOGRAPHY_LOCATION  SET LOCATION_NAME = ?, LOCATION_ADDRESS_1 = ?, LOCATION_ADDRESS_2 = ?, LOCATION_CITY = ?, LOCATION_STATE = ?, LOCATION_ZIP = ?, LOCATION_PHONE = ?, LOCATION_COUNTRY = ? WHERE LOCATION_ADDRESS_ID = ?";
	private static final String _loadString = " SELECT T1.LOCATION_ADDRESS_ID, T1.LOCATION_NAME, T1.LOCATION_ADDRESS_1, T1.LOCATION_ADDRESS_2, T1.LOCATION_CITY, T1.LOCATION_STATE, T1.LOCATION_ZIP, T1.LOCATION_PHONE, T1.LOCATION_COUNTRY FROM ILTDS_GEOGRAPHY_LOCATION  T1 WHERE T1.LOCATION_ADDRESS_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPGeolocationBean_9d43a246
	 */
	public EJSJDBCPersisterCMPGeolocationBean_9d43a246() throws java.rmi.RemoteException {
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
		GeolocationBean b = (GeolocationBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.location_address_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.location_address_id);
			}
			if (b.location_name == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.location_name);
			}
			if (b.location_address_1 == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.location_address_1);
			}
			if (b.location_address_2 == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.location_address_2);
			}
			if (b.location_city == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.location_city);
			}
			if (b.location_state == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.location_state);
			}
			if (b.location_zip == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.location_zip);
			}
			if (b.location_phone == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.location_phone);
			}
			if (b.location_country == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.location_country);
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
		GeolocationBean b = (GeolocationBean) eb;
		com.ardais.bigr.iltds.beans.GeolocationKey _primaryKey = (com.ardais.bigr.iltds.beans.GeolocationKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.location_address_id = _primaryKey.location_address_id;
		b.location_name = resultSet.getString(2);
		b.location_address_1 = resultSet.getString(3);
		b.location_address_2 = resultSet.getString(4);
		b.location_city = resultSet.getString(5);
		b.location_state = resultSet.getString(6);
		b.location_zip = resultSet.getString(7);
		b.location_phone = resultSet.getString(8);
		b.location_country = resultSet.getString(9);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		GeolocationBean b = (GeolocationBean) eb;
		com.ardais.bigr.iltds.beans.GeolocationKey _primaryKey = (com.ardais.bigr.iltds.beans.GeolocationKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.location_address_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.location_address_id);
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
		GeolocationBean b = (GeolocationBean) eb;
		com.ardais.bigr.iltds.beans.GeolocationKey _primaryKey = new com.ardais.bigr.iltds.beans.GeolocationKey();
		_primaryKey.location_address_id = b.location_address_id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		GeolocationBean b = (GeolocationBean) eb;
		com.ardais.bigr.iltds.beans.GeolocationKey _primaryKey = new com.ardais.bigr.iltds.beans.GeolocationKey();
		_primaryKey.location_address_id = b.location_address_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.location_address_id == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, _primaryKey.location_address_id);
			}
			if (b.location_name == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.location_name);
			}
			if (b.location_address_1 == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.location_address_1);
			}
			if (b.location_address_2 == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.location_address_2);
			}
			if (b.location_city == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.location_city);
			}
			if (b.location_state == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.location_state);
			}
			if (b.location_zip == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.location_zip);
			}
			if (b.location_phone == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.location_phone);
			}
			if (b.location_country == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.location_country);
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
		GeolocationBean b = (GeolocationBean) eb;
		com.ardais.bigr.iltds.beans.GeolocationKey _primaryKey = new com.ardais.bigr.iltds.beans.GeolocationKey();
		_primaryKey.location_address_id = b.location_address_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.location_address_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.location_address_id);
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
		com.ardais.bigr.iltds.beans.GeolocationKey key = new com.ardais.bigr.iltds.beans.GeolocationKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.location_address_id = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Geolocation findByPrimaryKey(com.ardais.bigr.iltds.beans.GeolocationKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Geolocation) home.activateBean(key);
	}
}
