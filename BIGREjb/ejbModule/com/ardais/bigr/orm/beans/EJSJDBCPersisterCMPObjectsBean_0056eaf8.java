package com.ardais.bigr.orm.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPObjectsBean_0056eaf8
 */
public class EJSJDBCPersisterCMPObjectsBean_0056eaf8 extends EJSJDBCPersister implements com.ardais.bigr.orm.beans.EJSFinderObjectsBean {
	private static final String _createString = "INSERT INTO ARD_OBJECTS (OBJECT_ID, OBJECT_DESCRIPTION, CREATE_DATE, CREATED_BY, UPDATE_DATE, UPDATED_BY, LONG_DESCRIPTION, URL) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ARD_OBJECTS  WHERE OBJECT_ID = ?";
	private static final String _storeString = "UPDATE ARD_OBJECTS  SET OBJECT_DESCRIPTION = ?, CREATE_DATE = ?, CREATED_BY = ?, UPDATE_DATE = ?, UPDATED_BY = ?, LONG_DESCRIPTION = ?, URL = ? WHERE OBJECT_ID = ?";
	private static final String _loadString = " SELECT T1.OBJECT_ID, T1.OBJECT_DESCRIPTION, T1.CREATE_DATE, T1.CREATED_BY, T1.UPDATE_DATE, T1.UPDATED_BY, T1.LONG_DESCRIPTION, T1.URL FROM ARD_OBJECTS  T1 WHERE T1.OBJECT_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPObjectsBean_0056eaf8
	 */
	public EJSJDBCPersisterCMPObjectsBean_0056eaf8() throws java.rmi.RemoteException {
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
		ObjectsBean b = (ObjectsBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.object_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.object_id);
			}
			if (b.object_description == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.object_description);
			}
			if (b.create_date == null) {
				pstmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(3, b.create_date);
			}
			if (b.created_by == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.created_by);
			}
			if (b.update_date == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.update_date);
			}
			if (b.updated_by == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.updated_by);
			}
			if (b.long_description == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.long_description);
			}
			if (b.url == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.url);
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
		ObjectsBean b = (ObjectsBean) eb;
		com.ardais.bigr.orm.beans.ObjectsKey _primaryKey = (com.ardais.bigr.orm.beans.ObjectsKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.object_id = _primaryKey.object_id;
		b.object_description = resultSet.getString(2);
		b.create_date = resultSet.getTimestamp(3);
		b.created_by = resultSet.getString(4);
		b.update_date = resultSet.getTimestamp(5);
		b.updated_by = resultSet.getString(6);
		b.long_description = resultSet.getString(7);
		b.url = resultSet.getString(8);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ObjectsBean b = (ObjectsBean) eb;
		com.ardais.bigr.orm.beans.ObjectsKey _primaryKey = (com.ardais.bigr.orm.beans.ObjectsKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.object_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.object_id);
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
		ObjectsBean b = (ObjectsBean) eb;
		com.ardais.bigr.orm.beans.ObjectsKey _primaryKey = new com.ardais.bigr.orm.beans.ObjectsKey();
		_primaryKey.object_id = b.object_id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ObjectsBean b = (ObjectsBean) eb;
		com.ardais.bigr.orm.beans.ObjectsKey _primaryKey = new com.ardais.bigr.orm.beans.ObjectsKey();
		_primaryKey.object_id = b.object_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.object_id == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, _primaryKey.object_id);
			}
			if (b.object_description == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.object_description);
			}
			if (b.create_date == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.create_date);
			}
			if (b.created_by == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.created_by);
			}
			if (b.update_date == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, b.update_date);
			}
			if (b.updated_by == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.updated_by);
			}
			if (b.long_description == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.long_description);
			}
			if (b.url == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.url);
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
		ObjectsBean b = (ObjectsBean) eb;
		com.ardais.bigr.orm.beans.ObjectsKey _primaryKey = new com.ardais.bigr.orm.beans.ObjectsKey();
		_primaryKey.object_id = b.object_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.object_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.object_id);
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
		com.ardais.bigr.orm.beans.ObjectsKey key = new com.ardais.bigr.orm.beans.ObjectsKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.object_id = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.orm.beans.Objects findByPrimaryKey(com.ardais.bigr.orm.beans.ObjectsKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.orm.beans.Objects) home.activateBean(key);
	}
}
