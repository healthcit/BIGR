package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPLineitemBean_7206cc70
 */
public class EJSJDBCPersisterCMPLineitemBean_7206cc70 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderLineitemBean {
	private static final String _createString = "INSERT INTO PTS_LINEITEM (LINEITEMID, LINEITEMNUMBER, FORMAT, QUANTITY, NOTES, COMMENTS, PROJECTID) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM PTS_LINEITEM  WHERE LINEITEMID = ?";
	private static final String _storeString = "UPDATE PTS_LINEITEM  SET LINEITEMNUMBER = ?, FORMAT = ?, QUANTITY = ?, NOTES = ?, COMMENTS = ?, PROJECTID = ? WHERE LINEITEMID = ?";
	private static final String _loadString = " SELECT T1.LINEITEMID, T1.LINEITEMNUMBER, T1.FORMAT, T1.QUANTITY, T1.NOTES, T1.COMMENTS, T1.PROJECTID FROM PTS_LINEITEM  T1 WHERE T1.LINEITEMID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPLineitemBean_7206cc70
	 */
	public EJSJDBCPersisterCMPLineitemBean_7206cc70() throws java.rmi.RemoteException {
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
		LineitemBean b = (LineitemBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.lineitemid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.lineitemid);
			}
			if (b.lineitemnumber == null) {
				pstmt.setNull(2, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(2, b.lineitemnumber);
			}
			if (b.format == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.format);
			}
			if (b.quantity == null) {
				pstmt.setNull(4, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(4, b.quantity);
			}
			if (b.notes == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.notes);
			}
			if (b.comments == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.comments);
			}
			if (b.project_projectid == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.project_projectid);
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
		LineitemBean b = (LineitemBean) eb;
		com.ardais.bigr.iltds.beans.LineitemKey _primaryKey = (com.ardais.bigr.iltds.beans.LineitemKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.lineitemid = _primaryKey.lineitemid;
		b.lineitemnumber = resultSet.getBigDecimal(2);
		b.format = resultSet.getString(3);
		b.quantity = resultSet.getBigDecimal(4);
		b.notes = resultSet.getString(5);
		b.comments = resultSet.getString(6);
		b.project_projectid = resultSet.getString(7);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		LineitemBean b = (LineitemBean) eb;
		com.ardais.bigr.iltds.beans.LineitemKey _primaryKey = (com.ardais.bigr.iltds.beans.LineitemKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.lineitemid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.lineitemid);
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
		LineitemBean b = (LineitemBean) eb;
		com.ardais.bigr.iltds.beans.LineitemKey _primaryKey = new com.ardais.bigr.iltds.beans.LineitemKey();
		_primaryKey.lineitemid = b.lineitemid;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		LineitemBean b = (LineitemBean) eb;
		com.ardais.bigr.iltds.beans.LineitemKey _primaryKey = new com.ardais.bigr.iltds.beans.LineitemKey();
		_primaryKey.lineitemid = b.lineitemid;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.lineitemid == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, _primaryKey.lineitemid);
			}
			if (b.lineitemnumber == null) {
				pstmt.setNull(1, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(1, b.lineitemnumber);
			}
			if (b.format == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.format);
			}
			if (b.quantity == null) {
				pstmt.setNull(3, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(3, b.quantity);
			}
			if (b.notes == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.notes);
			}
			if (b.comments == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.comments);
			}
			if (b.project_projectid == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.project_projectid);
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
		LineitemBean b = (LineitemBean) eb;
		com.ardais.bigr.iltds.beans.LineitemKey _primaryKey = new com.ardais.bigr.iltds.beans.LineitemKey();
		_primaryKey.lineitemid = b.lineitemid;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.lineitemid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.lineitemid);
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
		com.ardais.bigr.iltds.beans.LineitemKey key = new com.ardais.bigr.iltds.beans.LineitemKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.lineitemid = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Lineitem findByPrimaryKey(com.ardais.bigr.iltds.beans.LineitemKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Lineitem) home.activateBean(key);
	}
	/**
	 * findLineitemByProject
	 */
	public EJSFinder findLineitemByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.LINEITEMID, T1.LINEITEMNUMBER, T1.FORMAT, T1.QUANTITY, T1.NOTES, T1.COMMENTS, T1.PROJECTID FROM PTS_LINEITEM  T1 WHERE T1.PROJECTID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.projectid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.projectid);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
