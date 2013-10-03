package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPManifestBean_921a3765
 */
public class EJSJDBCPersisterCMPManifestBean_921a3765 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderManifestBean {
	private static final String _createString = "INSERT INTO ILTDS_MANIFEST (MANIFEST_NUMBER, AIRBILL_TRACKING_NUMBER, SHIP_DATETIME, SHIP_STAFF_ID, SHIPMENT_STATUS, MNFT_CREATE_STAFF_ID, MNFT_CREATE_DATETIME, SHIP_VERIFY_STAFF_ID, SHIP_VERIFY_DATETIME, RECEIPT_BY_STAFF_ID, RECEIPT_DATETIME, RECEIPT_VERIFY_STAFF_ID, RECEIPT_VERIFY_DATETIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_MANIFEST  WHERE MANIFEST_NUMBER = ?";
	private static final String _storeString = "UPDATE ILTDS_MANIFEST  SET AIRBILL_TRACKING_NUMBER = ?, SHIP_DATETIME = ?, SHIP_STAFF_ID = ?, SHIPMENT_STATUS = ?, MNFT_CREATE_STAFF_ID = ?, MNFT_CREATE_DATETIME = ?, SHIP_VERIFY_STAFF_ID = ?, SHIP_VERIFY_DATETIME = ?, RECEIPT_BY_STAFF_ID = ?, RECEIPT_DATETIME = ?, RECEIPT_VERIFY_STAFF_ID = ?, RECEIPT_VERIFY_DATETIME = ? WHERE MANIFEST_NUMBER = ?";
	private static final String _loadString = " SELECT T1.MANIFEST_NUMBER, T1.AIRBILL_TRACKING_NUMBER, T1.SHIP_DATETIME, T1.SHIP_STAFF_ID, T1.SHIPMENT_STATUS, T1.MNFT_CREATE_STAFF_ID, T1.MNFT_CREATE_DATETIME, T1.SHIP_VERIFY_STAFF_ID, T1.SHIP_VERIFY_DATETIME, T1.RECEIPT_BY_STAFF_ID, T1.RECEIPT_DATETIME, T1.RECEIPT_VERIFY_STAFF_ID, T1.RECEIPT_VERIFY_DATETIME FROM ILTDS_MANIFEST  T1 WHERE T1.MANIFEST_NUMBER = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPManifestBean_921a3765
	 */
	public EJSJDBCPersisterCMPManifestBean_921a3765() throws java.rmi.RemoteException {
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
		ManifestBean b = (ManifestBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.manifest_number == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.manifest_number);
			}
			if (b.airbill_tracking_number == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.airbill_tracking_number);
			}
			if (b.ship_datetime == null) {
				pstmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(3, b.ship_datetime);
			}
			if (b.ship_staff_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.ship_staff_id);
			}
			if (b.shipment_status == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.shipment_status);
			}
			if (b.mnft_create_staff_id == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.mnft_create_staff_id);
			}
			if (b.mnft_create_datetime == null) {
				pstmt.setNull(7, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(7, b.mnft_create_datetime);
			}
			if (b.ship_verify_staff_id == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.ship_verify_staff_id);
			}
			if (b.ship_verify_datetime == null) {
				pstmt.setNull(9, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(9, b.ship_verify_datetime);
			}
			if (b.receipt_by_staff_id == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.receipt_by_staff_id);
			}
			if (b.receipt_datetime == null) {
				pstmt.setNull(11, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(11, b.receipt_datetime);
			}
			if (b.receipt_verify_staff_id == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.receipt_verify_staff_id);
			}
			if (b.receipt_verify_datetime == null) {
				pstmt.setNull(13, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(13, b.receipt_verify_datetime);
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
		ManifestBean b = (ManifestBean) eb;
		com.ardais.bigr.iltds.beans.ManifestKey _primaryKey = (com.ardais.bigr.iltds.beans.ManifestKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.manifest_number = _primaryKey.manifest_number;
		b.airbill_tracking_number = resultSet.getString(2);
		b.ship_datetime = resultSet.getTimestamp(3);
		b.ship_staff_id = resultSet.getString(4);
		b.shipment_status = resultSet.getString(5);
		b.mnft_create_staff_id = resultSet.getString(6);
		b.mnft_create_datetime = resultSet.getTimestamp(7);
		b.ship_verify_staff_id = resultSet.getString(8);
		b.ship_verify_datetime = resultSet.getTimestamp(9);
		b.receipt_by_staff_id = resultSet.getString(10);
		b.receipt_datetime = resultSet.getTimestamp(11);
		b.receipt_verify_staff_id = resultSet.getString(12);
		b.receipt_verify_datetime = resultSet.getTimestamp(13);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ManifestBean b = (ManifestBean) eb;
		com.ardais.bigr.iltds.beans.ManifestKey _primaryKey = (com.ardais.bigr.iltds.beans.ManifestKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.manifest_number == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.manifest_number);
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
		ManifestBean b = (ManifestBean) eb;
		com.ardais.bigr.iltds.beans.ManifestKey _primaryKey = new com.ardais.bigr.iltds.beans.ManifestKey();
		_primaryKey.manifest_number = b.manifest_number;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ManifestBean b = (ManifestBean) eb;
		com.ardais.bigr.iltds.beans.ManifestKey _primaryKey = new com.ardais.bigr.iltds.beans.ManifestKey();
		_primaryKey.manifest_number = b.manifest_number;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.manifest_number == null) {
				pstmt.setNull(13, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(13, _primaryKey.manifest_number);
			}
			if (b.airbill_tracking_number == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.airbill_tracking_number);
			}
			if (b.ship_datetime == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.ship_datetime);
			}
			if (b.ship_staff_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.ship_staff_id);
			}
			if (b.shipment_status == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.shipment_status);
			}
			if (b.mnft_create_staff_id == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.mnft_create_staff_id);
			}
			if (b.mnft_create_datetime == null) {
				pstmt.setNull(6, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(6, b.mnft_create_datetime);
			}
			if (b.ship_verify_staff_id == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.ship_verify_staff_id);
			}
			if (b.ship_verify_datetime == null) {
				pstmt.setNull(8, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(8, b.ship_verify_datetime);
			}
			if (b.receipt_by_staff_id == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.receipt_by_staff_id);
			}
			if (b.receipt_datetime == null) {
				pstmt.setNull(10, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(10, b.receipt_datetime);
			}
			if (b.receipt_verify_staff_id == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.receipt_verify_staff_id);
			}
			if (b.receipt_verify_datetime == null) {
				pstmt.setNull(12, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(12, b.receipt_verify_datetime);
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
		ManifestBean b = (ManifestBean) eb;
		com.ardais.bigr.iltds.beans.ManifestKey _primaryKey = new com.ardais.bigr.iltds.beans.ManifestKey();
		_primaryKey.manifest_number = b.manifest_number;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.manifest_number == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.manifest_number);
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
		com.ardais.bigr.iltds.beans.ManifestKey key = new com.ardais.bigr.iltds.beans.ManifestKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.manifest_number = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findByWaybill
	 */
	public EJSFinder findByWaybill(java.lang.String waybill) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.MANIFEST_NUMBER, T1.AIRBILL_TRACKING_NUMBER, T1.SHIP_DATETIME, T1.SHIP_STAFF_ID, T1.SHIPMENT_STATUS, T1.MNFT_CREATE_STAFF_ID, T1.MNFT_CREATE_DATETIME, T1.SHIP_VERIFY_STAFF_ID, T1.SHIP_VERIFY_DATETIME, T1.RECEIPT_BY_STAFF_ID, T1.RECEIPT_DATETIME, T1.RECEIPT_VERIFY_STAFF_ID, T1.RECEIPT_VERIFY_DATETIME FROM ILTDS_MANIFEST  T1 WHERE AIRBILL_TRACKING_NUMBER = ?");
			if (waybill == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, waybill);
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
	public com.ardais.bigr.iltds.beans.Manifest findByPrimaryKey(com.ardais.bigr.iltds.beans.ManifestKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Manifest) home.activateBean(key);
	}
	/**
	 * findByBoxID
	 */
	public EJSFinder findByBoxID(java.lang.String boxID) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.MANIFEST_NUMBER, T1.AIRBILL_TRACKING_NUMBER, T1.SHIP_DATETIME, T1.SHIP_STAFF_ID, T1.SHIPMENT_STATUS, T1.MNFT_CREATE_STAFF_ID, T1.MNFT_CREATE_DATETIME, T1.SHIP_VERIFY_STAFF_ID, T1.SHIP_VERIFY_DATETIME, T1.RECEIPT_BY_STAFF_ID, T1.RECEIPT_DATETIME, T1.RECEIPT_VERIFY_STAFF_ID, T1.RECEIPT_VERIFY_DATETIME FROM ILTDS_MANIFEST  T1 WHERE box_barcode_id = ?");
			if (boxID == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, boxID);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
