package com.ardais.bigr.orm.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPUseraccessmoduleBean_abc4f862
 */
public class EJSJDBCPersisterCMPUseraccessmoduleBean_abc4f862 extends EJSJDBCPersister implements com.ardais.bigr.orm.beans.EJSFinderUseraccessmoduleBean {
	private static final String _createString = "INSERT INTO ARD_USER_ACCESS_MODULE (ARDAIS_ACCT_KEY, ARDAIS_USER_ID, OBJECT_ID, NEW_ORDER_CREATION, ORDER_MAINTAIN, ORDER_VIEW, DESCRIPTION, CREATE_DATE, CREATED_BY, UPDATE_DATE, UPDATED_BY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ARD_USER_ACCESS_MODULE  WHERE ARDAIS_ACCT_KEY = ? AND ARDAIS_USER_ID = ? AND OBJECT_ID = ?";
	private static final String _storeString = "UPDATE ARD_USER_ACCESS_MODULE  SET NEW_ORDER_CREATION = ?, ORDER_MAINTAIN = ?, ORDER_VIEW = ?, DESCRIPTION = ?, CREATE_DATE = ?, CREATED_BY = ?, UPDATE_DATE = ?, UPDATED_BY = ? WHERE ARDAIS_ACCT_KEY = ? AND ARDAIS_USER_ID = ? AND OBJECT_ID = ?";
	private static final String _loadString = " SELECT T1.ARDAIS_ACCT_KEY, T1.ARDAIS_USER_ID, T1.NEW_ORDER_CREATION, T1.ORDER_MAINTAIN, T1.ORDER_VIEW, T1.DESCRIPTION, T1.CREATE_DATE, T1.CREATED_BY, T1.UPDATE_DATE, T1.UPDATED_BY, T1.OBJECT_ID FROM ARD_USER_ACCESS_MODULE  T1 WHERE T1.ARDAIS_ACCT_KEY = ? AND T1.ARDAIS_USER_ID = ? AND T1.OBJECT_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPUseraccessmoduleBean_abc4f862
	 */
	public EJSJDBCPersisterCMPUseraccessmoduleBean_abc4f862() throws java.rmi.RemoteException {
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
		UseraccessmoduleBean b = (UseraccessmoduleBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.ardais_acct_key == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.ardais_acct_key);
			}
			if (b.ardais_user_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardais_user_id);
			}
			if (b.new_order_creation == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.new_order_creation);
			}
			if (b.order_maintain == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.order_maintain);
			}
			if (b.order_view == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.order_view);
			}
			if (b.description == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.description);
			}
			if (b.create_date == null) {
				pstmt.setNull(8, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(8, b.create_date);
			}
			if (b.created_by == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.created_by);
			}
			if (b.update_date == null) {
				pstmt.setNull(10, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(10, b.update_date);
			}
			if (b.updated_by == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.updated_by);
			}
			if (b.objects_object_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.objects_object_id);
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
		UseraccessmoduleBean b = (UseraccessmoduleBean) eb;
		com.ardais.bigr.orm.beans.UseraccessmoduleKey _primaryKey = (com.ardais.bigr.orm.beans.UseraccessmoduleKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.ardais_acct_key = _primaryKey.ardais_acct_key;
		b.ardais_user_id = _primaryKey.ardais_user_id;
		b.objects_object_id = _primaryKey.objects_object_id;
		b.new_order_creation = resultSet.getString(3);
		b.order_maintain = resultSet.getString(4);
		b.order_view = resultSet.getString(5);
		b.description = resultSet.getString(6);
		b.create_date = resultSet.getTimestamp(7);
		b.created_by = resultSet.getString(8);
		b.update_date = resultSet.getTimestamp(9);
		b.updated_by = resultSet.getString(10);
		b.objects_object_id = resultSet.getString(11);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		UseraccessmoduleBean b = (UseraccessmoduleBean) eb;
		com.ardais.bigr.orm.beans.UseraccessmoduleKey _primaryKey = (com.ardais.bigr.orm.beans.UseraccessmoduleKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.ardais_acct_key);
			}
			if (_primaryKey.ardais_user_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardais_user_id);
			}
			if (_primaryKey.objects_object_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.objects_object_id);
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
		UseraccessmoduleBean b = (UseraccessmoduleBean) eb;
		com.ardais.bigr.orm.beans.UseraccessmoduleKey _primaryKey = new com.ardais.bigr.orm.beans.UseraccessmoduleKey();
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		_primaryKey.ardais_user_id = b.ardais_user_id;
		_primaryKey.objects_object_id = b.objects_object_id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		UseraccessmoduleBean b = (UseraccessmoduleBean) eb;
		com.ardais.bigr.orm.beans.UseraccessmoduleKey _primaryKey = new com.ardais.bigr.orm.beans.UseraccessmoduleKey();
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		_primaryKey.ardais_user_id = b.ardais_user_id;
		_primaryKey.objects_object_id = b.objects_object_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, _primaryKey.ardais_acct_key);
			}
			if (_primaryKey.ardais_user_id == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, _primaryKey.ardais_user_id);
			}
			if (_primaryKey.objects_object_id == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, _primaryKey.objects_object_id);
			}
			if (b.new_order_creation == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.new_order_creation);
			}
			if (b.order_maintain == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.order_maintain);
			}
			if (b.order_view == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.order_view);
			}
			if (b.description == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.description);
			}
			if (b.create_date == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.create_date);
			}
			if (b.created_by == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.created_by);
			}
			if (b.update_date == null) {
				pstmt.setNull(7, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(7, b.update_date);
			}
			if (b.updated_by == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.updated_by);
			}
			if (b.objects_object_id == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.objects_object_id);
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
		UseraccessmoduleBean b = (UseraccessmoduleBean) eb;
		com.ardais.bigr.orm.beans.UseraccessmoduleKey _primaryKey = new com.ardais.bigr.orm.beans.UseraccessmoduleKey();
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		_primaryKey.ardais_user_id = b.ardais_user_id;
		_primaryKey.objects_object_id = b.objects_object_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.ardais_acct_key);
			}
			if (_primaryKey.ardais_user_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardais_user_id);
			}
			if (_primaryKey.objects_object_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.objects_object_id);
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
		com.ardais.bigr.orm.beans.UseraccessmoduleKey key = new com.ardais.bigr.orm.beans.UseraccessmoduleKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.ardais_acct_key = resultSet.getString(1);
			key.ardais_user_id = resultSet.getString(2);
			key.objects_object_id = resultSet.getString(11);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.orm.beans.Useraccessmodule findByPrimaryKey(com.ardais.bigr.orm.beans.UseraccessmoduleKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.orm.beans.Useraccessmodule) home.activateBean(key);
	}
	/**
	 * findUseraccessmoduleByObjects
	 */
	public EJSFinder findUseraccessmoduleByObjects(com.ardais.bigr.orm.beans.ObjectsKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ARDAIS_ACCT_KEY, T1.ARDAIS_USER_ID, T1.NEW_ORDER_CREATION, T1.ORDER_MAINTAIN, T1.ORDER_VIEW, T1.DESCRIPTION, T1.CREATE_DATE, T1.CREATED_BY, T1.UPDATE_DATE, T1.UPDATED_BY, T1.OBJECT_ID FROM ARD_USER_ACCESS_MODULE  T1 WHERE T1.OBJECT_ID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.object_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.object_id);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
