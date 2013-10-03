package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPArdaisstaffBean_9351b184
 */
public class EJSJDBCPersisterCMPArdaisstaffBean_9351b184 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderArdaisstaffBean {
	private static final String _createString = "INSERT INTO ILTDS_ARDAIS_STAFF (ARDAIS_STAFF_ID, ARDAIS_STAFF_LNAME, ARDAIS_STAFF_FNAME, ARDAIS_ACCT_KEY, ARDAIS_USER_ID, LOCATION_ADDRESS_ID) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_ARDAIS_STAFF  WHERE ARDAIS_STAFF_ID = ?";
	private static final String _storeString = "UPDATE ILTDS_ARDAIS_STAFF  SET ARDAIS_STAFF_LNAME = ?, ARDAIS_STAFF_FNAME = ?, ARDAIS_ACCT_KEY = ?, ARDAIS_USER_ID = ?, LOCATION_ADDRESS_ID = ? WHERE ARDAIS_STAFF_ID = ?";
	private static final String _loadString = " SELECT T1.ARDAIS_STAFF_ID, T1.ARDAIS_STAFF_LNAME, T1.ARDAIS_STAFF_FNAME, T1.ARDAIS_ACCT_KEY, T1.ARDAIS_USER_ID, T1.LOCATION_ADDRESS_ID FROM ILTDS_ARDAIS_STAFF  T1 WHERE T1.ARDAIS_STAFF_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPArdaisstaffBean_9351b184
	 */
	public EJSJDBCPersisterCMPArdaisstaffBean_9351b184() throws java.rmi.RemoteException {
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
		ArdaisstaffBean b = (ArdaisstaffBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.ardais_staff_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.ardais_staff_id);
			}
			if (b.ardais_staff_lname == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardais_staff_lname);
			}
			if (b.ardais_staff_fname == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.ardais_staff_fname);
			}
			if (b.ardais_acct_key == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.ardais_acct_key);
			}
			if (b.ardais_user_id == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.ardais_user_id);
			}
			if (b.geolocation_location_address_id == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.geolocation_location_address_id);
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
		ArdaisstaffBean b = (ArdaisstaffBean) eb;
		com.ardais.bigr.iltds.beans.ArdaisstaffKey _primaryKey = (com.ardais.bigr.iltds.beans.ArdaisstaffKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.ardais_staff_id = _primaryKey.ardais_staff_id;
		b.ardais_staff_lname = resultSet.getString(2);
		b.ardais_staff_fname = resultSet.getString(3);
		b.ardais_acct_key = resultSet.getString(4);
		b.ardais_user_id = resultSet.getString(5);
		b.geolocation_location_address_id = resultSet.getString(6);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ArdaisstaffBean b = (ArdaisstaffBean) eb;
		com.ardais.bigr.iltds.beans.ArdaisstaffKey _primaryKey = (com.ardais.bigr.iltds.beans.ArdaisstaffKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.ardais_staff_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.ardais_staff_id);
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
		ArdaisstaffBean b = (ArdaisstaffBean) eb;
		com.ardais.bigr.iltds.beans.ArdaisstaffKey _primaryKey = new com.ardais.bigr.iltds.beans.ArdaisstaffKey();
		_primaryKey.ardais_staff_id = b.ardais_staff_id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ArdaisstaffBean b = (ArdaisstaffBean) eb;
		com.ardais.bigr.iltds.beans.ArdaisstaffKey _primaryKey = new com.ardais.bigr.iltds.beans.ArdaisstaffKey();
		_primaryKey.ardais_staff_id = b.ardais_staff_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.ardais_staff_id == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, _primaryKey.ardais_staff_id);
			}
			if (b.ardais_staff_lname == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.ardais_staff_lname);
			}
			if (b.ardais_staff_fname == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardais_staff_fname);
			}
			if (b.ardais_acct_key == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.ardais_acct_key);
			}
			if (b.ardais_user_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.ardais_user_id);
			}
			if (b.geolocation_location_address_id == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.geolocation_location_address_id);
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
		ArdaisstaffBean b = (ArdaisstaffBean) eb;
		com.ardais.bigr.iltds.beans.ArdaisstaffKey _primaryKey = new com.ardais.bigr.iltds.beans.ArdaisstaffKey();
		_primaryKey.ardais_staff_id = b.ardais_staff_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.ardais_staff_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.ardais_staff_id);
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
		com.ardais.bigr.iltds.beans.ArdaisstaffKey key = new com.ardais.bigr.iltds.beans.ArdaisstaffKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.ardais_staff_id = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findAllStaffMembers
	 */
	public EJSFinder findAllStaffMembers() throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ARDAIS_STAFF_ID, T1.ARDAIS_STAFF_LNAME, T1.ARDAIS_STAFF_FNAME, T1.ARDAIS_ACCT_KEY, T1.ARDAIS_USER_ID, T1.LOCATION_ADDRESS_ID FROM ILTDS_ARDAIS_STAFF  T1 WHERE 1=1");
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
	public com.ardais.bigr.iltds.beans.Ardaisstaff findByPrimaryKey(com.ardais.bigr.iltds.beans.ArdaisstaffKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Ardaisstaff) home.activateBean(key);
	}
	/**
	 * findLocByUserProf
	 */
	public EJSFinder findLocByUserProf(java.lang.String ardais_user_id, java.lang.String ardais_acct_key) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ARDAIS_STAFF_ID, T1.ARDAIS_STAFF_LNAME, T1.ARDAIS_STAFF_FNAME, T1.ARDAIS_ACCT_KEY, T1.ARDAIS_USER_ID, T1.LOCATION_ADDRESS_ID FROM ILTDS_ARDAIS_STAFF  T1 WHERE ardais_user_id = ? and ardais_acct_key = ?");
			if (ardais_user_id == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, ardais_user_id);
			}
			if (ardais_acct_key == null) {
			   pstmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(2, ardais_acct_key);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findArdaisstaffByGeolocation
	 */
	public EJSFinder findArdaisstaffByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ARDAIS_STAFF_ID, T1.ARDAIS_STAFF_LNAME, T1.ARDAIS_STAFF_FNAME, T1.ARDAIS_ACCT_KEY, T1.ARDAIS_USER_ID, T1.LOCATION_ADDRESS_ID FROM ILTDS_ARDAIS_STAFF  T1 WHERE T1.LOCATION_ADDRESS_ID = ?");
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
}
