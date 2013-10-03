package com.ardais.bigr.es.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPShoppingcartdetailBean_a81a2bea
 */
public class EJSJDBCPersisterCMPShoppingcartdetailBean_a81a2bea extends EJSJDBCPersister implements com.ardais.bigr.es.beans.EJSFinderShoppingcartdetailBean {
	private static final String _createString = "INSERT INTO ES_SHOPPING_CART_DETAIL (SHOPPING_CART_LINE_NUMBER, SHOPPING_CART_ID, ARDAIS_ACCT_KEY, ARDAIS_USER_ID, SHOPPING_CART_LINE_AMOUNT, BARCODE_ID, CREATION_DT, SEARCH_DESC, QUANTITY, PRODUCT_TYPE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ES_SHOPPING_CART_DETAIL  WHERE SHOPPING_CART_LINE_NUMBER = ? AND SHOPPING_CART_ID = ? AND ARDAIS_ACCT_KEY = ? AND ARDAIS_USER_ID = ?";
	private static final String _storeString = "UPDATE ES_SHOPPING_CART_DETAIL  SET SHOPPING_CART_LINE_AMOUNT = ?, BARCODE_ID = ?, CREATION_DT = ?, SEARCH_DESC = ?, QUANTITY = ?, PRODUCT_TYPE = ? WHERE SHOPPING_CART_LINE_NUMBER = ? AND SHOPPING_CART_ID = ? AND ARDAIS_ACCT_KEY = ? AND ARDAIS_USER_ID = ?";
	private static final String _loadString = " SELECT T1.SHOPPING_CART_LINE_NUMBER, T1.SHOPPING_CART_LINE_AMOUNT, T1.BARCODE_ID, T1.CREATION_DT, T1.SEARCH_DESC, T1.QUANTITY, T1.PRODUCT_TYPE, T1.SHOPPING_CART_ID, T1.ARDAIS_ACCT_KEY, T1.ARDAIS_USER_ID FROM ES_SHOPPING_CART_DETAIL  T1 WHERE T1.SHOPPING_CART_LINE_NUMBER = ? AND T1.SHOPPING_CART_ID = ? AND T1.ARDAIS_ACCT_KEY = ? AND T1.ARDAIS_USER_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPShoppingcartdetailBean_a81a2bea
	 */
	public EJSJDBCPersisterCMPShoppingcartdetailBean_a81a2bea() throws java.rmi.RemoteException {
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
		ShoppingcartdetailBean b = (ShoppingcartdetailBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.shopping_cart_line_number == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, b.shopping_cart_line_number);
			}
			if (b.shopping_cart_line_amount == null) {
				pstmt.setNull(5, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(5, b.shopping_cart_line_amount);
			}
			if (b.barcode_id == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.barcode_id);
			}
			if (b.creation_dt == null) {
				pstmt.setNull(7, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(7, b.creation_dt);
			}
			if (b.search_desc == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.search_desc);
			}
			if (b.quantity == null) {
				pstmt.setNull(9, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(9, b.quantity.intValue());
			}
			if (b.productType == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.productType);
			}
			if (b.shoppingcart_shopping_cart_id == null) {
				pstmt.setNull(2, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(2, b.shoppingcart_shopping_cart_id);
			}
			if (b.shoppingcart_ardaisuser_ardais_user_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.shoppingcart_ardaisuser_ardais_user_id);
			}
			if (b.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key);
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
		ShoppingcartdetailBean b = (ShoppingcartdetailBean) eb;
		com.ardais.bigr.es.beans.ShoppingcartdetailKey _primaryKey = (com.ardais.bigr.es.beans.ShoppingcartdetailKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		int tempint;

		b.shopping_cart_line_number = _primaryKey.shopping_cart_line_number;
		b.shoppingcart_shopping_cart_id = _primaryKey.shoppingcart_shopping_cart_id;
		b.shoppingcart_ardaisuser_ardais_user_id = _primaryKey.shoppingcart_ardaisuser_ardais_user_id;
		b.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key = _primaryKey.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key;
		b.shopping_cart_line_amount = resultSet.getBigDecimal(2);
		b.barcode_id = resultSet.getString(3);
		b.creation_dt = resultSet.getTimestamp(4);
		b.search_desc = resultSet.getString(5);
		tempint = resultSet.getInt(6);
		b.quantity = resultSet.wasNull() ? null : new Integer(tempint);
		b.productType = resultSet.getString(7);
		b.shoppingcart_shopping_cart_id = resultSet.getBigDecimal(8);
		b.shoppingcart_ardaisuser_ardais_user_id = resultSet.getString(10);
		b.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key = resultSet.getString(9);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ShoppingcartdetailBean b = (ShoppingcartdetailBean) eb;
		com.ardais.bigr.es.beans.ShoppingcartdetailKey _primaryKey = (com.ardais.bigr.es.beans.ShoppingcartdetailKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.shopping_cart_line_number == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.shopping_cart_line_number);
			}
			if (_primaryKey.shoppingcart_shopping_cart_id == null) {
				pstmt.setNull(2, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(2, _primaryKey.shoppingcart_shopping_cart_id);
			}
			if (_primaryKey.shoppingcart_ardaisuser_ardais_user_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, _primaryKey.shoppingcart_ardaisuser_ardais_user_id);
			}
			if (_primaryKey.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key);
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
		ShoppingcartdetailBean b = (ShoppingcartdetailBean) eb;
		com.ardais.bigr.es.beans.ShoppingcartdetailKey _primaryKey = new com.ardais.bigr.es.beans.ShoppingcartdetailKey();
		_primaryKey.shopping_cart_line_number = b.shopping_cart_line_number;
		_primaryKey.shoppingcart_shopping_cart_id = b.shoppingcart_shopping_cart_id;
		_primaryKey.shoppingcart_ardaisuser_ardais_user_id = b.shoppingcart_ardaisuser_ardais_user_id;
		_primaryKey.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key = b.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ShoppingcartdetailBean b = (ShoppingcartdetailBean) eb;
		com.ardais.bigr.es.beans.ShoppingcartdetailKey _primaryKey = new com.ardais.bigr.es.beans.ShoppingcartdetailKey();
		_primaryKey.shopping_cart_line_number = b.shopping_cart_line_number;
		_primaryKey.shoppingcart_shopping_cart_id = b.shoppingcart_shopping_cart_id;
		_primaryKey.shoppingcart_ardaisuser_ardais_user_id = b.shoppingcart_ardaisuser_ardais_user_id;
		_primaryKey.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key = b.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.shopping_cart_line_number == null) {
				pstmt.setNull(7, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(7, _primaryKey.shopping_cart_line_number);
			}
			if (_primaryKey.shoppingcart_shopping_cart_id == null) {
				pstmt.setNull(8, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(8, _primaryKey.shoppingcart_shopping_cart_id);
			}
			if (_primaryKey.shoppingcart_ardaisuser_ardais_user_id == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, _primaryKey.shoppingcart_ardaisuser_ardais_user_id);
			}
			if (_primaryKey.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, _primaryKey.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key);
			}
			if (b.shopping_cart_line_amount == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, b.shopping_cart_line_amount);
			}
			if (b.barcode_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.barcode_id);
			}
			if (b.creation_dt == null) {
				pstmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(3, b.creation_dt);
			}
			if (b.search_desc == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.search_desc);
			}
			if (b.quantity == null) {
				pstmt.setNull(5, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(5, b.quantity.intValue());
			}
			if (b.productType == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.productType);
			}
			if (b.shoppingcart_shopping_cart_id == null) {
				pstmt.setNull(8, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(8, b.shoppingcart_shopping_cart_id);
			}
			if (b.shoppingcart_ardaisuser_ardais_user_id == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.shoppingcart_ardaisuser_ardais_user_id);
			}
			if (b.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key);
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
		ShoppingcartdetailBean b = (ShoppingcartdetailBean) eb;
		com.ardais.bigr.es.beans.ShoppingcartdetailKey _primaryKey = new com.ardais.bigr.es.beans.ShoppingcartdetailKey();
		_primaryKey.shopping_cart_line_number = b.shopping_cart_line_number;
		_primaryKey.shoppingcart_shopping_cart_id = b.shoppingcart_shopping_cart_id;
		_primaryKey.shoppingcart_ardaisuser_ardais_user_id = b.shoppingcart_ardaisuser_ardais_user_id;
		_primaryKey.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key = b.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.shopping_cart_line_number == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, _primaryKey.shopping_cart_line_number);
			}
			if (_primaryKey.shoppingcart_shopping_cart_id == null) {
				pstmt.setNull(2, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(2, _primaryKey.shoppingcart_shopping_cart_id);
			}
			if (_primaryKey.shoppingcart_ardaisuser_ardais_user_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, _primaryKey.shoppingcart_ardaisuser_ardais_user_id);
			}
			if (_primaryKey.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key);
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
		com.ardais.bigr.es.beans.ShoppingcartdetailKey key = new com.ardais.bigr.es.beans.ShoppingcartdetailKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.shopping_cart_line_number = resultSet.getBigDecimal(1);
			key.shoppingcart_shopping_cart_id = resultSet.getBigDecimal(8);
			key.shoppingcart_ardaisuser_ardais_user_id = resultSet.getString(10);
			key.shoppingcart_ardaisuser_ardaisaccount_ardais_acct_key = resultSet.getString(9);
			return key;
		}
return null;
	}
	/**
	 * findShoppingcartdetailByShoppingcart
	 */
	public EJSFinder findShoppingcartdetailByShoppingcart(com.ardais.bigr.es.beans.ShoppingcartKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.SHOPPING_CART_LINE_NUMBER, T1.SHOPPING_CART_LINE_AMOUNT, T1.BARCODE_ID, T1.CREATION_DT, T1.SEARCH_DESC, T1.QUANTITY, T1.PRODUCT_TYPE, T1.SHOPPING_CART_ID, T1.ARDAIS_ACCT_KEY, T1.ARDAIS_USER_ID FROM ES_SHOPPING_CART_DETAIL  T1 WHERE T1.SHOPPING_CART_ID = ? AND T1.ARDAIS_ACCT_KEY = ? AND T1.ARDAIS_USER_ID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.shopping_cart_id == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, inKey.shopping_cart_id);
			}
			if (inKey.ardaisuser_ardais_user_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, inKey.ardaisuser_ardais_user_id);
			}
			if (inKey.ardaisuser_ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, inKey.ardaisuser_ardaisaccount_ardais_acct_key);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findByBarcodeUserAccount
	 */
	public EJSFinder findByBarcodeUserAccount(java.lang.String barcode_arg, java.lang.String user_arg, java.lang.String account_arg) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.SHOPPING_CART_LINE_NUMBER, T1.SHOPPING_CART_LINE_AMOUNT, T1.BARCODE_ID, T1.CREATION_DT, T1.SEARCH_DESC, T1.QUANTITY, T1.PRODUCT_TYPE, T1.SHOPPING_CART_ID, T1.ARDAIS_ACCT_KEY, T1.ARDAIS_USER_ID FROM ES_SHOPPING_CART_DETAIL  T1 WHERE barcode_id = ? AND ardais_user_id = ? AND ardais_acct_key = ?");
			if (barcode_arg == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, barcode_arg);
			}
			if (user_arg == null) {
			   pstmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(2, user_arg);
			}
			if (account_arg == null) {
			   pstmt.setNull(3, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(3, account_arg);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findByUserAccountOrderByLineNumber
	 */
	public EJSFinder findByUserAccountOrderByLineNumber(java.lang.String user, java.lang.String account) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.SHOPPING_CART_LINE_NUMBER, T1.SHOPPING_CART_LINE_AMOUNT, T1.BARCODE_ID, T1.CREATION_DT, T1.SEARCH_DESC, T1.QUANTITY, T1.PRODUCT_TYPE, T1.SHOPPING_CART_ID, T1.ARDAIS_ACCT_KEY, T1.ARDAIS_USER_ID FROM ES_SHOPPING_CART_DETAIL  T1 WHERE ardais_user_id = ? AND ardais_acct_key = ? ORDER BY SHOPPING_CART_LINE_NUMBER DESC");
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
	public com.ardais.bigr.es.beans.Shoppingcartdetail findByPrimaryKey(com.ardais.bigr.es.beans.ShoppingcartdetailKey primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.es.beans.Shoppingcartdetail) home.activateBean(primaryKey);
	}
}
