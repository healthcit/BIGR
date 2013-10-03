package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPBoxlocationBean_bc5f2c16
 */
public class EJSJDBCPersisterCMPBoxlocationBean_bc5f2c16 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderBoxlocationBean {
	private static final String _createString = "INSERT INTO ILTDS_BOX_LOCATION (ROOM_ID, DRAWER_ID, SLOT_ID, UNIT_NAME, LOCATION_ADDRESS_ID, AVAILABLE_IND, LOCATION_STATUS, STORAGE_TYPE_CID, BOX_BARCODE_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_BOX_LOCATION  WHERE ROOM_ID = ? AND DRAWER_ID = ? AND SLOT_ID = ? AND UNIT_NAME = ? AND LOCATION_ADDRESS_ID = ?";
	private static final String _storeString = "UPDATE ILTDS_BOX_LOCATION  SET AVAILABLE_IND = ?, LOCATION_STATUS = ?, STORAGE_TYPE_CID = ?, BOX_BARCODE_ID = ? WHERE ROOM_ID = ? AND DRAWER_ID = ? AND SLOT_ID = ? AND UNIT_NAME = ? AND LOCATION_ADDRESS_ID = ?";
	private static final String _loadString = " SELECT T1.ROOM_ID, T1.DRAWER_ID, T1.SLOT_ID, T1.AVAILABLE_IND, T1.LOCATION_STATUS, T1.UNIT_NAME, T1.STORAGE_TYPE_CID, T1.LOCATION_ADDRESS_ID, T1.BOX_BARCODE_ID FROM ILTDS_BOX_LOCATION  T1 WHERE T1.ROOM_ID = ? AND T1.DRAWER_ID = ? AND T1.SLOT_ID = ? AND T1.UNIT_NAME = ? AND T1.LOCATION_ADDRESS_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPBoxlocationBean_bc5f2c16
	 */
	public EJSJDBCPersisterCMPBoxlocationBean_bc5f2c16() throws java.rmi.RemoteException {
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
		BoxlocationBean b = (BoxlocationBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.room_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.room_id);
			}
			if (b.drawer_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.drawer_id);
			}
			if (b.slot_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.slot_id);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.available_ind);
			if (objectTemp == null) {
				pstmt.setNull(6, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(6, (java.lang.String)objectTemp);
			}
			if (b.location_status == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.location_status);
			}
			if (b.unitName == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.unitName);
			}
			if (b.storageTypeCid == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.storageTypeCid);
			}
			if (b.geolocation_location_address_id == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.geolocation_location_address_id);
			}
			if (b.samplebox_box_barcode_id == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.samplebox_box_barcode_id);
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
		BoxlocationBean b = (BoxlocationBean) eb;
		com.ardais.bigr.iltds.beans.BoxlocationKey _primaryKey = (com.ardais.bigr.iltds.beans.BoxlocationKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.room_id = _primaryKey.room_id;
		b.drawer_id = _primaryKey.drawer_id;
		b.slot_id = _primaryKey.slot_id;
		b.geolocation_location_address_id = _primaryKey.geolocation_location_address_id;
		b.unitName = _primaryKey.unitName;
		b.available_ind = (java.lang.String)com.ibm.vap.converters.VapTrimStringConverter.singleton().objectFrom(resultSet.getString(4));
		b.location_status = resultSet.getString(5);
		b.storageTypeCid = resultSet.getString(7);
		b.geolocation_location_address_id = resultSet.getString(8);
		b.samplebox_box_barcode_id = resultSet.getString(9);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		BoxlocationBean b = (BoxlocationBean) eb;
		com.ardais.bigr.iltds.beans.BoxlocationKey _primaryKey = (com.ardais.bigr.iltds.beans.BoxlocationKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.room_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.room_id);
			}
			if (_primaryKey.drawer_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.drawer_id);
			}
			if (_primaryKey.slot_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.slot_id);
			}
			if (_primaryKey.geolocation_location_address_id == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, _primaryKey.geolocation_location_address_id);
			}
			if (_primaryKey.unitName == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, _primaryKey.unitName);
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
		BoxlocationBean b = (BoxlocationBean) eb;
		com.ardais.bigr.iltds.beans.BoxlocationKey _primaryKey = new com.ardais.bigr.iltds.beans.BoxlocationKey();
		_primaryKey.room_id = b.room_id;
		_primaryKey.drawer_id = b.drawer_id;
		_primaryKey.slot_id = b.slot_id;
		_primaryKey.geolocation_location_address_id = b.geolocation_location_address_id;
		_primaryKey.unitName = b.unitName;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		BoxlocationBean b = (BoxlocationBean) eb;
		com.ardais.bigr.iltds.beans.BoxlocationKey _primaryKey = new com.ardais.bigr.iltds.beans.BoxlocationKey();
		_primaryKey.room_id = b.room_id;
		_primaryKey.drawer_id = b.drawer_id;
		_primaryKey.slot_id = b.slot_id;
		_primaryKey.geolocation_location_address_id = b.geolocation_location_address_id;
		_primaryKey.unitName = b.unitName;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.room_id == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, _primaryKey.room_id);
			}
			if (_primaryKey.drawer_id == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, _primaryKey.drawer_id);
			}
			if (_primaryKey.slot_id == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, _primaryKey.slot_id);
			}
			if (_primaryKey.geolocation_location_address_id == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, _primaryKey.geolocation_location_address_id);
			}
			if (_primaryKey.unitName == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, _primaryKey.unitName);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.available_ind);
			if (objectTemp == null) {
				pstmt.setNull(1, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(1, (java.lang.String)objectTemp);
			}
			if (b.location_status == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.location_status);
			}
			if (b.storageTypeCid == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.storageTypeCid);
			}
			if (b.geolocation_location_address_id == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.geolocation_location_address_id);
			}
			if (b.samplebox_box_barcode_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.samplebox_box_barcode_id);
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
		BoxlocationBean b = (BoxlocationBean) eb;
		com.ardais.bigr.iltds.beans.BoxlocationKey _primaryKey = new com.ardais.bigr.iltds.beans.BoxlocationKey();
		_primaryKey.room_id = b.room_id;
		_primaryKey.drawer_id = b.drawer_id;
		_primaryKey.slot_id = b.slot_id;
		_primaryKey.geolocation_location_address_id = b.geolocation_location_address_id;
		_primaryKey.unitName = b.unitName;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.room_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.room_id);
			}
			if (_primaryKey.drawer_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.drawer_id);
			}
			if (_primaryKey.slot_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.slot_id);
			}
			if (_primaryKey.geolocation_location_address_id == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, _primaryKey.geolocation_location_address_id);
			}
			if (_primaryKey.unitName == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, _primaryKey.unitName);
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
		com.ardais.bigr.iltds.beans.BoxlocationKey key = new com.ardais.bigr.iltds.beans.BoxlocationKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.room_id = resultSet.getString(1);
			key.drawer_id = resultSet.getString(2);
			key.slot_id = resultSet.getString(3);
			key.geolocation_location_address_id = resultSet.getString(8);
			key.unitName = resultSet.getString(6);
			return key;
		}
return null;
	}
	/**
	 * findBoxlocationBySamplebox
	 */
	public EJSFinder findBoxlocationBySamplebox(com.ardais.bigr.iltds.beans.SampleboxKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ROOM_ID, T1.DRAWER_ID, T1.SLOT_ID, T1.AVAILABLE_IND, T1.LOCATION_STATUS, T1.UNIT_NAME, T1.STORAGE_TYPE_CID, T1.LOCATION_ADDRESS_ID, T1.BOX_BARCODE_ID FROM ILTDS_BOX_LOCATION  T1 WHERE T1.BOX_BARCODE_ID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.box_barcode_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.box_barcode_id);
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
	public com.ardais.bigr.iltds.beans.Boxlocation findByPrimaryKey(com.ardais.bigr.iltds.beans.BoxlocationKey primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Boxlocation) home.activateBean(primaryKey);
	}
	/**
	 * findBoxlocationByGeolocation
	 */
	public EJSFinder findBoxlocationByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ROOM_ID, T1.DRAWER_ID, T1.SLOT_ID, T1.AVAILABLE_IND, T1.LOCATION_STATUS, T1.UNIT_NAME, T1.STORAGE_TYPE_CID, T1.LOCATION_ADDRESS_ID, T1.BOX_BARCODE_ID FROM ILTDS_BOX_LOCATION  T1 WHERE T1.LOCATION_ADDRESS_ID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.location_address_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.location_address_id);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findBoxLocationByBoxId
	 */
	public EJSFinder findBoxLocationByBoxId(java.lang.String box) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ROOM_ID, T1.DRAWER_ID, T1.SLOT_ID, T1.AVAILABLE_IND, T1.LOCATION_STATUS, T1.UNIT_NAME, T1.STORAGE_TYPE_CID, T1.LOCATION_ADDRESS_ID, T1.BOX_BARCODE_ID FROM ILTDS_BOX_LOCATION  T1 WHERE  box_barcode_id = ? ");
			if (box == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, box);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findBoxLocationByStorageTypeCid
	 */
	public EJSFinder findBoxLocationByStorageTypeCid(java.lang.String locationAddressId, java.lang.String storageTypeCid, java.lang.String availableInd) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ROOM_ID, T1.DRAWER_ID, T1.SLOT_ID, T1.AVAILABLE_IND, T1.LOCATION_STATUS, T1.UNIT_NAME, T1.STORAGE_TYPE_CID, T1.LOCATION_ADDRESS_ID, T1.BOX_BARCODE_ID FROM ILTDS_BOX_LOCATION  T1 WHERE location_address_id = ? and storage_type_cid = ? and box_barcode_id is null and available_ind = ? order by location_address_id, room_id, unit_name, to_number(drawer_id), slot_id");
			if (locationAddressId == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, locationAddressId);
			}
			if (storageTypeCid == null) {
			   pstmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(2, storageTypeCid);
			}
			if (availableInd == null) {
			   pstmt.setNull(3, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(3, availableInd);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
