package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPSamplestatusBean_fcc4f8e2
 */
public class EJSJDBCPersisterCMPSamplestatusBean_fcc4f8e2 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderSamplestatusBean {
	private static final String _createString = "INSERT INTO ILTDS_SAMPLE_STATUS (ID, STATUS_TYPE_CODE, SAMPLE_STATUS_DATETIME, SAMPLE_BARCODE_ID) VALUES (?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_SAMPLE_STATUS  WHERE ID = ?";
	private static final String _storeString = "UPDATE ILTDS_SAMPLE_STATUS  SET STATUS_TYPE_CODE = ?, SAMPLE_STATUS_DATETIME = ?, SAMPLE_BARCODE_ID = ? WHERE ID = ?";
	private static final String _loadString = " SELECT T1.STATUS_TYPE_CODE, T1.SAMPLE_STATUS_DATETIME, T1.ID, T1.SAMPLE_BARCODE_ID FROM ILTDS_SAMPLE_STATUS  T1 WHERE T1.ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPSamplestatusBean_fcc4f8e2
	 */
	public EJSJDBCPersisterCMPSamplestatusBean_fcc4f8e2() throws java.rmi.RemoteException {
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
		SamplestatusBean b = (SamplestatusBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.status_type_code == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.status_type_code);
			}
			if (b.sample_status_datetime == null) {
				pstmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(3, b.sample_status_datetime);
			}
			if (b.id == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, b.id);
			}
			if (b.sample_sample_barcode_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.sample_sample_barcode_id);
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
		SamplestatusBean b = (SamplestatusBean) eb;
		com.ardais.bigr.iltds.beans.SamplestatusKey _primaryKey = (com.ardais.bigr.iltds.beans.SamplestatusKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.id = _primaryKey.id;
		b.status_type_code = resultSet.getString(1);
		b.sample_status_datetime = resultSet.getTimestamp(2);
		b.sample_sample_barcode_id = resultSet.getString(4);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		SamplestatusBean b = (SamplestatusBean) eb;
		com.ardais.bigr.iltds.beans.SamplestatusKey _primaryKey = (com.ardais.bigr.iltds.beans.SamplestatusKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.id == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.id);
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
		SamplestatusBean b = (SamplestatusBean) eb;
		com.ardais.bigr.iltds.beans.SamplestatusKey _primaryKey = new com.ardais.bigr.iltds.beans.SamplestatusKey();
		_primaryKey.id = b.id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		SamplestatusBean b = (SamplestatusBean) eb;
		com.ardais.bigr.iltds.beans.SamplestatusKey _primaryKey = new com.ardais.bigr.iltds.beans.SamplestatusKey();
		_primaryKey.id = b.id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.id == null) {
				pstmt.setNull(4, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(4, _primaryKey.id);
			}
			if (b.status_type_code == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.status_type_code);
			}
			if (b.sample_status_datetime == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.sample_status_datetime);
			}
			if (b.sample_sample_barcode_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.sample_sample_barcode_id);
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
		SamplestatusBean b = (SamplestatusBean) eb;
		com.ardais.bigr.iltds.beans.SamplestatusKey _primaryKey = new com.ardais.bigr.iltds.beans.SamplestatusKey();
		_primaryKey.id = b.id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.id == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.id);
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
		com.ardais.bigr.iltds.beans.SamplestatusKey key = new com.ardais.bigr.iltds.beans.SamplestatusKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.id = resultSet.getBigDecimal(3);
			return key;
		}
return null;
	}
	/**
	 * findSamplestatusBySample
	 */
	public EJSFinder findSamplestatusBySample(com.ardais.bigr.iltds.beans.SampleKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.STATUS_TYPE_CODE, T1.SAMPLE_STATUS_DATETIME, T1.ID, T1.SAMPLE_BARCODE_ID FROM ILTDS_SAMPLE_STATUS  T1 WHERE T1.SAMPLE_BARCODE_ID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.sample_barcode_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.sample_barcode_id);
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
	public com.ardais.bigr.iltds.beans.Samplestatus findByPrimaryKey(com.ardais.bigr.iltds.beans.SamplestatusKey primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Samplestatus) home.activateBean(primaryKey);
	}
	/**
	 * findBySampleIDStatus
	 */
	public EJSFinder findBySampleIDStatus(java.lang.String sampleID, java.lang.String statusID) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.STATUS_TYPE_CODE, T1.SAMPLE_STATUS_DATETIME, T1.ID, T1.SAMPLE_BARCODE_ID FROM ILTDS_SAMPLE_STATUS  T1 WHERE  sample_barcode_id = ? AND status_type_code = ? ");
			if (sampleID == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, sampleID);
			}
			if (statusID == null) {
			   pstmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(2, statusID);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
