package com.ardais.bigr.es.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPShoppingcartBean_4bdce51d
 */
public class EJSJDBCPersisterCMPShoppingcartBean_4bdce51d extends EJSJDBCPersister implements com.ardais.bigr.es.beans.EJSFinderShoppingcartBean {
	private static final String _createString = "INSERT INTO ES_SHOPPING_CART (SHOPPING_CART_ID, ARDAIS_USER_ID, ARDAIS_ACCT_KEY, CART_CREATE_DATE, LAST_UPDATED_DT) VALUES (?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ES_SHOPPING_CART  WHERE SHOPPING_CART_ID = ? AND ARDAIS_USER_ID = ? AND ARDAIS_ACCT_KEY = ?";
	private static final String _storeString = "UPDATE ES_SHOPPING_CART  SET CART_CREATE_DATE = ?, LAST_UPDATED_DT = ? WHERE SHOPPING_CART_ID = ? AND ARDAIS_USER_ID = ? AND ARDAIS_ACCT_KEY = ?";
	private static final String _loadString = " SELECT T1.SHOPPING_CART_ID, T1.CART_CREATE_DATE, T1.LAST_UPDATED_DT, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_SHOPPING_CART  T1 WHERE T1.SHOPPING_CART_ID = ? AND T1.ARDAIS_USER_ID = ? AND T1.ARDAIS_ACCT_KEY = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPShoppingcartBean_4bdce51d
	 */
	public EJSJDBCPersisterCMPShoppingcartBean_4bdce51d() throws java.rmi.RemoteException {
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
		ShoppingcartBean b = (ShoppingcartBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.shopping_cart_id == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, b.shopping_cart_id);
			}
			if (b.cart_create_date == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, b.cart_create_date);
			}
			if (b.last_update_dt == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.last_update_dt);
			}
			if (b.ardaisuser_ardais_user_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardaisuser_ardais_user_id);
			}
			if (b.ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.ardaisuser_ardaisaccount_ardais_acct_key);
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
		ShoppingcartBean b = (ShoppingcartBean) eb;
		com.ardais.bigr.es.beans.ShoppingcartKey _primaryKey = (com.ardais.bigr.es.beans.ShoppingcartKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.shopping_cart_id = _primaryKey.shopping_cart_id;
		b.ardaisuser_ardais_user_id = _primaryKey.ardaisuser_ardais_user_id;
		b.ardaisuser_ardaisaccount_ardais_acct_key = _primaryKey.ardaisuser_ardaisaccount_ardais_acct_key;
		b.cart_create_date = resultSet.getTimestamp(2);
		b.last_update_dt = resultSet.getTimestamp(3);
		b.ardaisuser_ardais_user_id = resultSet.getString(4);
		b.ardaisuser_ardaisaccount_ardais_acct_key = resultSet.getString(5);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ShoppingcartBean b = (ShoppingcartBean) eb;
		com.ardais.bigr.es.beans.ShoppingcartKey _primaryKey = (com.ardais.bigr.es.beans.ShoppingcartKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.shopping_cart_id == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.shopping_cart_id);
			}
			if (_primaryKey.ardaisuser_ardais_user_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardaisuser_ardais_user_id);
			}
			if (_primaryKey.ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.ardaisuser_ardaisaccount_ardais_acct_key);
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
		ShoppingcartBean b = (ShoppingcartBean) eb;
		com.ardais.bigr.es.beans.ShoppingcartKey _primaryKey = new com.ardais.bigr.es.beans.ShoppingcartKey();
		_primaryKey.shopping_cart_id = b.shopping_cart_id;
		_primaryKey.ardaisuser_ardais_user_id = b.ardaisuser_ardais_user_id;
		_primaryKey.ardaisuser_ardaisaccount_ardais_acct_key = b.ardaisuser_ardaisaccount_ardais_acct_key;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ShoppingcartBean b = (ShoppingcartBean) eb;
		com.ardais.bigr.es.beans.ShoppingcartKey _primaryKey = new com.ardais.bigr.es.beans.ShoppingcartKey();
		_primaryKey.shopping_cart_id = b.shopping_cart_id;
		_primaryKey.ardaisuser_ardais_user_id = b.ardaisuser_ardais_user_id;
		_primaryKey.ardaisuser_ardaisaccount_ardais_acct_key = b.ardaisuser_ardaisaccount_ardais_acct_key;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.shopping_cart_id == null) {
				pstmt.setNull(3, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(3, _primaryKey.shopping_cart_id);
			}
			if (_primaryKey.ardaisuser_ardais_user_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, _primaryKey.ardaisuser_ardais_user_id);
			}
			if (_primaryKey.ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, _primaryKey.ardaisuser_ardaisaccount_ardais_acct_key);
			}
			if (b.cart_create_date == null) {
				pstmt.setNull(1, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(1, b.cart_create_date);
			}
			if (b.last_update_dt == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.last_update_dt);
			}
			if (b.ardaisuser_ardais_user_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.ardaisuser_ardais_user_id);
			}
			if (b.ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.ardaisuser_ardaisaccount_ardais_acct_key);
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
		ShoppingcartBean b = (ShoppingcartBean) eb;
		com.ardais.bigr.es.beans.ShoppingcartKey _primaryKey = new com.ardais.bigr.es.beans.ShoppingcartKey();
		_primaryKey.shopping_cart_id = b.shopping_cart_id;
		_primaryKey.ardaisuser_ardais_user_id = b.ardaisuser_ardais_user_id;
		_primaryKey.ardaisuser_ardaisaccount_ardais_acct_key = b.ardaisuser_ardaisaccount_ardais_acct_key;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.shopping_cart_id == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.shopping_cart_id);
			}
			if (_primaryKey.ardaisuser_ardais_user_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardaisuser_ardais_user_id);
			}
			if (_primaryKey.ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.ardaisuser_ardaisaccount_ardais_acct_key);
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
		com.ardais.bigr.es.beans.ShoppingcartKey key = new com.ardais.bigr.es.beans.ShoppingcartKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.shopping_cart_id = resultSet.getBigDecimal(1);
			key.ardaisuser_ardais_user_id = resultSet.getString(4);
			key.ardaisuser_ardaisaccount_ardais_acct_key = resultSet.getString(5);
			return key;
		}
return null;
	}
	/**
	 * findByUserAccount
	 */
	public EJSFinder findByUserAccount(java.lang.String user, java.lang.String account) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.SHOPPING_CART_ID, T1.CART_CREATE_DATE, T1.LAST_UPDATED_DT, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_SHOPPING_CART  T1 WHERE ardais_user_id = ? and ardais_acct_key = ?");
			if (user == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, user);
			}
			if (account == null) {
			   pstmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(2, account);
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
	public com.ardais.bigr.es.beans.Shoppingcart findByPrimaryKey(com.ardais.bigr.es.beans.ShoppingcartKey primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.es.beans.Shoppingcart) home.activateBean(primaryKey);
	}
	/**
	 * findShoppingcartByArdaisuser
	 */
	public EJSFinder findShoppingcartByArdaisuser(com.ardais.bigr.es.beans.ArdaisuserKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.SHOPPING_CART_ID, T1.CART_CREATE_DATE, T1.LAST_UPDATED_DT, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM ES_SHOPPING_CART  T1 WHERE T1.ARDAIS_USER_ID = ? AND T1.ARDAIS_ACCT_KEY = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.ardais_user_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.ardais_user_id);
			}
			if (inKey.ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, inKey.ardaisaccount_ardais_acct_key);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
