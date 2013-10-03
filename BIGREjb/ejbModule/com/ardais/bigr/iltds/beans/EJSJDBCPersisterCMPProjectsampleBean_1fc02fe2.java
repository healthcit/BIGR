package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPProjectsampleBean_1fc02fe2
 */
public class EJSJDBCPersisterCMPProjectsampleBean_1fc02fe2 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderProjectsampleBean {
	private static final String _createString = "INSERT INTO PTS_SAMPLE (SAMPLE_BARCODE_ID, LINEITEMID, PROJECTID) VALUES (?, ?, ?)";
	private static final String _removeString = "DELETE FROM PTS_SAMPLE  WHERE SAMPLE_BARCODE_ID = ? AND LINEITEMID = ? AND PROJECTID = ?";
	private static final String _storeString = "UPDATE";
	private static final String _loadString = " SELECT T1.SAMPLE_BARCODE_ID, T1.LINEITEMID, T1.PROJECTID FROM PTS_SAMPLE  T1 WHERE T1.SAMPLE_BARCODE_ID = ? AND T1.LINEITEMID = ? AND T1.PROJECTID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPProjectsampleBean_1fc02fe2
	 */
	public EJSJDBCPersisterCMPProjectsampleBean_1fc02fe2() throws java.rmi.RemoteException {
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
		ProjectsampleBean b = (ProjectsampleBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.samplebarcodeid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.samplebarcodeid);
			}
			if (b.lineitem_lineitemid == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.lineitem_lineitemid);
			}
			if (b.project_projectid == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.project_projectid);
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
		ProjectsampleBean b = (ProjectsampleBean) eb;
		com.ardais.bigr.iltds.beans.ProjectsampleKey _primaryKey = (com.ardais.bigr.iltds.beans.ProjectsampleKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.samplebarcodeid = _primaryKey.samplebarcodeid;
		b.lineitem_lineitemid = _primaryKey.lineitem_lineitemid;
		b.project_projectid = _primaryKey.project_projectid;
		b.lineitem_lineitemid = resultSet.getString(2);
		b.project_projectid = resultSet.getString(3);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ProjectsampleBean b = (ProjectsampleBean) eb;
		com.ardais.bigr.iltds.beans.ProjectsampleKey _primaryKey = (com.ardais.bigr.iltds.beans.ProjectsampleKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.samplebarcodeid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.samplebarcodeid);
			}
			if (_primaryKey.lineitem_lineitemid == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.lineitem_lineitemid);
			}
			if (_primaryKey.project_projectid == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.project_projectid);
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
		ProjectsampleBean b = (ProjectsampleBean) eb;
		com.ardais.bigr.iltds.beans.ProjectsampleKey _primaryKey = new com.ardais.bigr.iltds.beans.ProjectsampleKey();
		_primaryKey.samplebarcodeid = b.samplebarcodeid;
		_primaryKey.lineitem_lineitemid = b.lineitem_lineitemid;
		_primaryKey.project_projectid = b.project_projectid;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		return;
	}
	/**
	 * remove
	 */
	public void remove(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ProjectsampleBean b = (ProjectsampleBean) eb;
		com.ardais.bigr.iltds.beans.ProjectsampleKey _primaryKey = new com.ardais.bigr.iltds.beans.ProjectsampleKey();
		_primaryKey.samplebarcodeid = b.samplebarcodeid;
		_primaryKey.lineitem_lineitemid = b.lineitem_lineitemid;
		_primaryKey.project_projectid = b.project_projectid;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.samplebarcodeid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.samplebarcodeid);
			}
			if (_primaryKey.lineitem_lineitemid == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.lineitem_lineitemid);
			}
			if (_primaryKey.project_projectid == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, _primaryKey.project_projectid);
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
		com.ardais.bigr.iltds.beans.ProjectsampleKey key = new com.ardais.bigr.iltds.beans.ProjectsampleKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.samplebarcodeid = resultSet.getString(1);
			key.lineitem_lineitemid = resultSet.getString(2);
			key.project_projectid = resultSet.getString(3);
			return key;
		}
return null;
	}
	/**
	 * findProjectsampleByLineitem
	 */
	public EJSFinder findProjectsampleByLineitem(com.ardais.bigr.iltds.beans.LineitemKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.SAMPLE_BARCODE_ID, T1.LINEITEMID, T1.PROJECTID FROM PTS_SAMPLE  T1 WHERE T1.LINEITEMID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.lineitemid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.lineitemid);
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
	public com.ardais.bigr.iltds.beans.Projectsample findByPrimaryKey(com.ardais.bigr.iltds.beans.ProjectsampleKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Projectsample) home.activateBean(key);
	}
	/**
	 * findProjectsampleByProject
	 */
	public EJSFinder findProjectsampleByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.SAMPLE_BARCODE_ID, T1.LINEITEMID, T1.PROJECTID FROM PTS_SAMPLE  T1 WHERE T1.PROJECTID = ?");
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
