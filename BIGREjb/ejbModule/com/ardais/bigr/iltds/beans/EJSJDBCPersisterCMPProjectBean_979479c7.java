package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPProjectBean_979479c7
 */
public class EJSJDBCPersisterCMPProjectBean_979479c7 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderProjectBean {
	private static final String _createString = "INSERT INTO PTS_PROJECT (PROJECTID, PROJECTNAME, DATEREQUESTED, DATEAPPROVED, DATESHIPPED, NOTES, STATUS, PERCENTCOMPLETE, ARDAIS_USER_ID, ARDAIS_ACCT_KEY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM PTS_PROJECT  WHERE PROJECTID = ?";
	private static final String _storeString = "UPDATE PTS_PROJECT  SET PROJECTNAME = ?, DATEREQUESTED = ?, DATEAPPROVED = ?, DATESHIPPED = ?, NOTES = ?, STATUS = ?, PERCENTCOMPLETE = ?, ARDAIS_USER_ID = ?, ARDAIS_ACCT_KEY = ? WHERE PROJECTID = ?";
	private static final String _loadString = " SELECT T1.PROJECTID, T1.PROJECTNAME, T1.DATEREQUESTED, T1.DATEAPPROVED, T1.DATESHIPPED, T1.NOTES, T1.STATUS, T1.PERCENTCOMPLETE, T1.ARDAIS_USER_ID, T1.ARDAIS_ACCT_KEY FROM PTS_PROJECT  T1 WHERE T1.PROJECTID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPProjectBean_979479c7
	 */
	public EJSJDBCPersisterCMPProjectBean_979479c7() throws java.rmi.RemoteException {
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
		ProjectBean b = (ProjectBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.projectid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.projectid);
			}
			if (b.projectname == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.projectname);
			}
			if (b.daterequested == null) {
				pstmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(3, b.daterequested);
			}
			if (b.dateapproved == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, b.dateapproved);
			}
			if (b.dateshipped == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.dateshipped);
			}
			if (b.notes == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.notes);
			}
			if (b.status == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.status);
			}
			if (b.percentcomplete == null) {
				pstmt.setNull(8, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(8, b.percentcomplete);
			}
			if (b.ardaisuserid == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.ardaisuserid);
			}
			if (b.ardaisaccountkey == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.ardaisaccountkey);
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
		ProjectBean b = (ProjectBean) eb;
		com.ardais.bigr.iltds.beans.ProjectKey _primaryKey = (com.ardais.bigr.iltds.beans.ProjectKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.projectid = _primaryKey.projectid;
		b.projectname = resultSet.getString(2);
		b.daterequested = resultSet.getTimestamp(3);
		b.dateapproved = resultSet.getTimestamp(4);
		b.dateshipped = resultSet.getTimestamp(5);
		b.notes = resultSet.getString(6);
		b.status = resultSet.getString(7);
		b.percentcomplete = resultSet.getBigDecimal(8);
		b.ardaisuserid = resultSet.getString(9);
		b.ardaisaccountkey = resultSet.getString(10);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ProjectBean b = (ProjectBean) eb;
		com.ardais.bigr.iltds.beans.ProjectKey _primaryKey = (com.ardais.bigr.iltds.beans.ProjectKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.projectid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.projectid);
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
		ProjectBean b = (ProjectBean) eb;
		com.ardais.bigr.iltds.beans.ProjectKey _primaryKey = new com.ardais.bigr.iltds.beans.ProjectKey();
		_primaryKey.projectid = b.projectid;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ProjectBean b = (ProjectBean) eb;
		com.ardais.bigr.iltds.beans.ProjectKey _primaryKey = new com.ardais.bigr.iltds.beans.ProjectKey();
		_primaryKey.projectid = b.projectid;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.projectid == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, _primaryKey.projectid);
			}
			if (b.projectname == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.projectname);
			}
			if (b.daterequested == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.daterequested);
			}
			if (b.dateapproved == null) {
				pstmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(3, b.dateapproved);
			}
			if (b.dateshipped == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, b.dateshipped);
			}
			if (b.notes == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.notes);
			}
			if (b.status == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.status);
			}
			if (b.percentcomplete == null) {
				pstmt.setNull(7, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(7, b.percentcomplete);
			}
			if (b.ardaisuserid == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.ardaisuserid);
			}
			if (b.ardaisaccountkey == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.ardaisaccountkey);
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
		ProjectBean b = (ProjectBean) eb;
		com.ardais.bigr.iltds.beans.ProjectKey _primaryKey = new com.ardais.bigr.iltds.beans.ProjectKey();
		_primaryKey.projectid = b.projectid;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.projectid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.projectid);
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
		com.ardais.bigr.iltds.beans.ProjectKey key = new com.ardais.bigr.iltds.beans.ProjectKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.projectid = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Project findByPrimaryKey(com.ardais.bigr.iltds.beans.ProjectKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Project) home.activateBean(key);
	}
}
