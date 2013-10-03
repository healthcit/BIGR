package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPRevokedconsentBean_9e86b52d
 */
public class EJSJDBCPersisterCMPRevokedconsentBean_9e86b52d extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderRevokedconsentBean {
	private static final String _createString = "INSERT INTO ILTDS_REVOKED_CONSENT_ARCHIVE (CONSENT_ID, ARDAIS_ID, REVOKED_BY_STAFF_ID, REVOKED_TIMESTAMP, REVOKED_REASON) VALUES (?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_REVOKED_CONSENT_ARCHIVE  WHERE CONSENT_ID = ? AND ARDAIS_ID = ?";
	private static final String _storeString = "UPDATE ILTDS_REVOKED_CONSENT_ARCHIVE  SET REVOKED_BY_STAFF_ID = ?, REVOKED_TIMESTAMP = ?, REVOKED_REASON = ? WHERE CONSENT_ID = ? AND ARDAIS_ID = ?";
	private static final String _loadString = " SELECT T1.CONSENT_ID, T1.ARDAIS_ID, T1.REVOKED_BY_STAFF_ID, T1.REVOKED_TIMESTAMP, T1.REVOKED_REASON FROM ILTDS_REVOKED_CONSENT_ARCHIVE  T1 WHERE T1.CONSENT_ID = ? AND T1.ARDAIS_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPRevokedconsentBean_9e86b52d
	 */
	public EJSJDBCPersisterCMPRevokedconsentBean_9e86b52d() throws java.rmi.RemoteException {
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
		RevokedconsentBean b = (RevokedconsentBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.consent_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.consent_id);
			}
			if (b.ardais_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardais_id);
			}
			if (b.revoked_by_staff_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.revoked_by_staff_id);
			}
			if (b.revoked_timestamp == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, b.revoked_timestamp);
			}
			if (b.revoked_reason == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.revoked_reason);
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
		RevokedconsentBean b = (RevokedconsentBean) eb;
		com.ardais.bigr.iltds.beans.RevokedconsentKey _primaryKey = (com.ardais.bigr.iltds.beans.RevokedconsentKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.consent_id = _primaryKey.consent_id;
		b.ardais_id = _primaryKey.ardais_id;
		b.revoked_by_staff_id = resultSet.getString(3);
		b.revoked_timestamp = resultSet.getTimestamp(4);
		b.revoked_reason = resultSet.getString(5);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		RevokedconsentBean b = (RevokedconsentBean) eb;
		com.ardais.bigr.iltds.beans.RevokedconsentKey _primaryKey = (com.ardais.bigr.iltds.beans.RevokedconsentKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.consent_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.consent_id);
			}
			if (_primaryKey.ardais_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardais_id);
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
		RevokedconsentBean b = (RevokedconsentBean) eb;
		com.ardais.bigr.iltds.beans.RevokedconsentKey _primaryKey = new com.ardais.bigr.iltds.beans.RevokedconsentKey();
		_primaryKey.consent_id = b.consent_id;
		_primaryKey.ardais_id = b.ardais_id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		RevokedconsentBean b = (RevokedconsentBean) eb;
		com.ardais.bigr.iltds.beans.RevokedconsentKey _primaryKey = new com.ardais.bigr.iltds.beans.RevokedconsentKey();
		_primaryKey.consent_id = b.consent_id;
		_primaryKey.ardais_id = b.ardais_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.consent_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, _primaryKey.consent_id);
			}
			if (_primaryKey.ardais_id == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, _primaryKey.ardais_id);
			}
			if (b.revoked_by_staff_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.revoked_by_staff_id);
			}
			if (b.revoked_timestamp == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.revoked_timestamp);
			}
			if (b.revoked_reason == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.revoked_reason);
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
		RevokedconsentBean b = (RevokedconsentBean) eb;
		com.ardais.bigr.iltds.beans.RevokedconsentKey _primaryKey = new com.ardais.bigr.iltds.beans.RevokedconsentKey();
		_primaryKey.consent_id = b.consent_id;
		_primaryKey.ardais_id = b.ardais_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.consent_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.consent_id);
			}
			if (_primaryKey.ardais_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardais_id);
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
		com.ardais.bigr.iltds.beans.RevokedconsentKey key = new com.ardais.bigr.iltds.beans.RevokedconsentKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.consent_id = resultSet.getString(1);
			key.ardais_id = resultSet.getString(2);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Revokedconsent findByPrimaryKey(com.ardais.bigr.iltds.beans.RevokedconsentKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Revokedconsent) home.activateBean(key);
	}
}
