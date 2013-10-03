package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPSampleboxBean_0d3574c3
 */
public class EJSJDBCPersisterCMPSampleboxBean_0d3574c3 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderSampleboxBean {
	private static final String _createString = "INSERT INTO ILTDS_SAMPLE_BOX (BOX_BARCODE_ID, BOX_CHECK_IN_DATE, BOX_CHECK_OUT_DATE, BOX_CHECK_OUT_REASON, BOX_CHECKOUT_REQUEST_STAFF_ID, BOX_STATUS, BOX_NOTE, MANIFEST_ORDER, BOX_LAYOUT_ID, STORAGE_TYPE_CID, MANIFEST_NUMBER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_SAMPLE_BOX  WHERE BOX_BARCODE_ID = ?";
	private static final String _storeString = "UPDATE ILTDS_SAMPLE_BOX  SET BOX_CHECK_IN_DATE = ?, BOX_CHECK_OUT_DATE = ?, BOX_CHECK_OUT_REASON = ?, BOX_CHECKOUT_REQUEST_STAFF_ID = ?, BOX_STATUS = ?, BOX_NOTE = ?, MANIFEST_ORDER = ?, BOX_LAYOUT_ID = ?, STORAGE_TYPE_CID = ?, MANIFEST_NUMBER = ? WHERE BOX_BARCODE_ID = ?";
	private static final String _loadString = " SELECT T1.BOX_BARCODE_ID, T1.BOX_CHECK_IN_DATE, T1.BOX_CHECK_OUT_DATE, T1.BOX_CHECK_OUT_REASON, T1.BOX_CHECKOUT_REQUEST_STAFF_ID, T1.BOX_STATUS, T1.BOX_NOTE, T1.MANIFEST_ORDER, T1.BOX_LAYOUT_ID, T1.STORAGE_TYPE_CID, T1.MANIFEST_NUMBER FROM ILTDS_SAMPLE_BOX  T1 WHERE T1.BOX_BARCODE_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPSampleboxBean_0d3574c3
	 */
	public EJSJDBCPersisterCMPSampleboxBean_0d3574c3() throws java.rmi.RemoteException {
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
		SampleboxBean b = (SampleboxBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.box_barcode_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.box_barcode_id);
			}
			if (b.box_check_in_date == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.box_check_in_date);
			}
			if (b.box_check_out_date == null) {
				pstmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(3, b.box_check_out_date);
			}
			if (b.box_check_out_reason == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.box_check_out_reason);
			}
			if (b.box_checkout_request_staff_id == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.box_checkout_request_staff_id);
			}
			if (b.box_status == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.box_status);
			}
			if (b.box_note == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.box_note);
			}
			if (b.manifest_order == null) {
				pstmt.setNull(8, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(8, b.manifest_order);
			}
			if (b.boxLayoutId == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.boxLayoutId);
			}
			if (b.storageTypeCid == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.storageTypeCid);
			}
			if (b.manifest_manifest_number == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.manifest_manifest_number);
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
		SampleboxBean b = (SampleboxBean) eb;
		com.ardais.bigr.iltds.beans.SampleboxKey _primaryKey = (com.ardais.bigr.iltds.beans.SampleboxKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.box_barcode_id = _primaryKey.box_barcode_id;
		b.box_check_in_date = resultSet.getTimestamp(2);
		b.box_check_out_date = resultSet.getTimestamp(3);
		b.box_check_out_reason = resultSet.getString(4);
		b.box_checkout_request_staff_id = resultSet.getString(5);
		b.box_status = resultSet.getString(6);
		b.box_note = resultSet.getString(7);
		b.manifest_order = resultSet.getBigDecimal(8);
		b.boxLayoutId = resultSet.getString(9);
		b.storageTypeCid = resultSet.getString(10);
		b.manifest_manifest_number = resultSet.getString(11);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		SampleboxBean b = (SampleboxBean) eb;
		com.ardais.bigr.iltds.beans.SampleboxKey _primaryKey = (com.ardais.bigr.iltds.beans.SampleboxKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.box_barcode_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.box_barcode_id);
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
		SampleboxBean b = (SampleboxBean) eb;
		com.ardais.bigr.iltds.beans.SampleboxKey _primaryKey = new com.ardais.bigr.iltds.beans.SampleboxKey();
		_primaryKey.box_barcode_id = b.box_barcode_id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		SampleboxBean b = (SampleboxBean) eb;
		com.ardais.bigr.iltds.beans.SampleboxKey _primaryKey = new com.ardais.bigr.iltds.beans.SampleboxKey();
		_primaryKey.box_barcode_id = b.box_barcode_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.box_barcode_id == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, _primaryKey.box_barcode_id);
			}
			if (b.box_check_in_date == null) {
				pstmt.setNull(1, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(1, b.box_check_in_date);
			}
			if (b.box_check_out_date == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.box_check_out_date);
			}
			if (b.box_check_out_reason == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.box_check_out_reason);
			}
			if (b.box_checkout_request_staff_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.box_checkout_request_staff_id);
			}
			if (b.box_status == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.box_status);
			}
			if (b.box_note == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.box_note);
			}
			if (b.manifest_order == null) {
				pstmt.setNull(7, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(7, b.manifest_order);
			}
			if (b.boxLayoutId == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.boxLayoutId);
			}
			if (b.storageTypeCid == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.storageTypeCid);
			}
			if (b.manifest_manifest_number == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.manifest_manifest_number);
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
		SampleboxBean b = (SampleboxBean) eb;
		com.ardais.bigr.iltds.beans.SampleboxKey _primaryKey = new com.ardais.bigr.iltds.beans.SampleboxKey();
		_primaryKey.box_barcode_id = b.box_barcode_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.box_barcode_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.box_barcode_id);
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
		com.ardais.bigr.iltds.beans.SampleboxKey key = new com.ardais.bigr.iltds.beans.SampleboxKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.box_barcode_id = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Samplebox findByPrimaryKey(com.ardais.bigr.iltds.beans.SampleboxKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Samplebox) home.activateBean(key);
	}
	/**
	 * findSampleboxByManifest
	 */
	public EJSFinder findSampleboxByManifest(com.ardais.bigr.iltds.beans.ManifestKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.BOX_BARCODE_ID, T1.BOX_CHECK_IN_DATE, T1.BOX_CHECK_OUT_DATE, T1.BOX_CHECK_OUT_REASON, T1.BOX_CHECKOUT_REQUEST_STAFF_ID, T1.BOX_STATUS, T1.BOX_NOTE, T1.MANIFEST_ORDER, T1.BOX_LAYOUT_ID, T1.STORAGE_TYPE_CID, T1.MANIFEST_NUMBER FROM ILTDS_SAMPLE_BOX  T1 WHERE T1.MANIFEST_NUMBER = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.manifest_number == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.manifest_number);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
