package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPWorkorderBean_514544f7
 */
public class EJSJDBCPersisterCMPWorkorderBean_514544f7 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderWorkorderBean {
	private static final String _createString = "INSERT INTO PTS_WORKORDER (WORKORDERID, WORKORDERTYPE, WORKORDERNAME, LISTORDER, STARTDATE, ENDDATE, STATUS, PERCENTCOMPLETE, NUMBEROFSAMPLES, NOTES, PROJECTID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM PTS_WORKORDER  WHERE WORKORDERID = ?";
	private static final String _storeString = "UPDATE PTS_WORKORDER  SET WORKORDERTYPE = ?, WORKORDERNAME = ?, LISTORDER = ?, STARTDATE = ?, ENDDATE = ?, STATUS = ?, PERCENTCOMPLETE = ?, NUMBEROFSAMPLES = ?, NOTES = ?, PROJECTID = ? WHERE WORKORDERID = ?";
	private static final String _loadString = " SELECT T1.WORKORDERID, T1.WORKORDERTYPE, T1.WORKORDERNAME, T1.LISTORDER, T1.STARTDATE, T1.ENDDATE, T1.STATUS, T1.PERCENTCOMPLETE, T1.NUMBEROFSAMPLES, T1.NOTES, T1.PROJECTID FROM PTS_WORKORDER  T1 WHERE T1.WORKORDERID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPWorkorderBean_514544f7
	 */
	public EJSJDBCPersisterCMPWorkorderBean_514544f7() throws java.rmi.RemoteException {
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
		WorkorderBean b = (WorkorderBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.workorderid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.workorderid);
			}
			if (b.workordertype == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.workordertype);
			}
			if (b.workordername == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.workordername);
			}
			if (b.listorder == null) {
				pstmt.setNull(4, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(4, b.listorder);
			}
			if (b.startdate == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.startdate);
			}
			if (b.enddate == null) {
				pstmt.setNull(6, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(6, b.enddate);
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
			if (b.numberofsamples == null) {
				pstmt.setNull(9, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(9, b.numberofsamples);
			}
			if (b.notes == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.notes);
			}
			if (b.project_projectid == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.project_projectid);
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
		WorkorderBean b = (WorkorderBean) eb;
		com.ardais.bigr.iltds.beans.WorkorderKey _primaryKey = (com.ardais.bigr.iltds.beans.WorkorderKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.workorderid = _primaryKey.workorderid;
		b.workordertype = resultSet.getString(2);
		b.workordername = resultSet.getString(3);
		b.listorder = resultSet.getBigDecimal(4);
		b.startdate = resultSet.getTimestamp(5);
		b.enddate = resultSet.getTimestamp(6);
		b.status = resultSet.getString(7);
		b.percentcomplete = resultSet.getBigDecimal(8);
		b.numberofsamples = resultSet.getBigDecimal(9);
		b.notes = resultSet.getString(10);
		b.project_projectid = resultSet.getString(11);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		WorkorderBean b = (WorkorderBean) eb;
		com.ardais.bigr.iltds.beans.WorkorderKey _primaryKey = (com.ardais.bigr.iltds.beans.WorkorderKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.workorderid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.workorderid);
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
		WorkorderBean b = (WorkorderBean) eb;
		com.ardais.bigr.iltds.beans.WorkorderKey _primaryKey = new com.ardais.bigr.iltds.beans.WorkorderKey();
		_primaryKey.workorderid = b.workorderid;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		WorkorderBean b = (WorkorderBean) eb;
		com.ardais.bigr.iltds.beans.WorkorderKey _primaryKey = new com.ardais.bigr.iltds.beans.WorkorderKey();
		_primaryKey.workorderid = b.workorderid;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.workorderid == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, _primaryKey.workorderid);
			}
			if (b.workordertype == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.workordertype);
			}
			if (b.workordername == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.workordername);
			}
			if (b.listorder == null) {
				pstmt.setNull(3, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(3, b.listorder);
			}
			if (b.startdate == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, b.startdate);
			}
			if (b.enddate == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.enddate);
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
			if (b.numberofsamples == null) {
				pstmt.setNull(8, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(8, b.numberofsamples);
			}
			if (b.notes == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.notes);
			}
			if (b.project_projectid == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.project_projectid);
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
		WorkorderBean b = (WorkorderBean) eb;
		com.ardais.bigr.iltds.beans.WorkorderKey _primaryKey = new com.ardais.bigr.iltds.beans.WorkorderKey();
		_primaryKey.workorderid = b.workorderid;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.workorderid == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.workorderid);
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
		com.ardais.bigr.iltds.beans.WorkorderKey key = new com.ardais.bigr.iltds.beans.WorkorderKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.workorderid = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findWorkorderByProject
	 */
	public EJSFinder findWorkorderByProject(com.ardais.bigr.iltds.beans.ProjectKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.WORKORDERID, T1.WORKORDERTYPE, T1.WORKORDERNAME, T1.LISTORDER, T1.STARTDATE, T1.ENDDATE, T1.STATUS, T1.PERCENTCOMPLETE, T1.NUMBEROFSAMPLES, T1.NOTES, T1.PROJECTID FROM PTS_WORKORDER  T1 WHERE T1.PROJECTID = ?");
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
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Workorder findByPrimaryKey(com.ardais.bigr.iltds.beans.WorkorderKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Workorder) home.activateBean(key);
	}
}
