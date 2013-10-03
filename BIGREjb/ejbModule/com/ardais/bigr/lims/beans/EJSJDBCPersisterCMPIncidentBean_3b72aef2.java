package com.ardais.bigr.lims.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPIncidentBean_3b72aef2
 */
public class EJSJDBCPersisterCMPIncidentBean_3b72aef2 extends EJSJDBCPersister implements com.ardais.bigr.lims.beans.EJSFinderIncidentBean {
	private static final String _createString = "INSERT INTO LIMS_INCIDENTS (INCIDENT_ID, CREATE_USER, CREATE_DATE, SAMPLE_BARCODE_ID, CONSENT_ID, ACTION, REASON, SLIDE_ID, PATHOLOGIST, RE_PV_REQUESTOR_CODE, ACTION_OTHER, COMMENTS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM LIMS_INCIDENTS  WHERE INCIDENT_ID = ?";
	private static final String _storeString = "UPDATE LIMS_INCIDENTS  SET CREATE_USER = ?, CREATE_DATE = ?, SAMPLE_BARCODE_ID = ?, CONSENT_ID = ?, ACTION = ?, REASON = ?, SLIDE_ID = ?, PATHOLOGIST = ?, RE_PV_REQUESTOR_CODE = ?, ACTION_OTHER = ?, COMMENTS = ? WHERE INCIDENT_ID = ?";
	private static final String _loadString = " SELECT T1.INCIDENT_ID, T1.CREATE_USER, T1.CREATE_DATE, T1.SAMPLE_BARCODE_ID, T1.CONSENT_ID, T1.ACTION, T1.REASON, T1.SLIDE_ID, T1.PATHOLOGIST, T1.RE_PV_REQUESTOR_CODE, T1.ACTION_OTHER, T1.COMMENTS FROM LIMS_INCIDENTS  T1 WHERE T1.INCIDENT_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPIncidentBean_3b72aef2
	 */
	public EJSJDBCPersisterCMPIncidentBean_3b72aef2() throws java.rmi.RemoteException {
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
		IncidentBean b = (IncidentBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.incidentId == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.incidentId);
			}
			if (b.createUser == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.createUser);
			}
			if (b.createDate == null) {
				pstmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(3, b.createDate);
			}
			if (b.sampleId == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.sampleId);
			}
			if (b.consentId == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.consentId);
			}
			if (b.action == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.action);
			}
			if (b.reason == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.reason);
			}
			if (b.slideId == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.slideId);
			}
			if (b.pathologist == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.pathologist);
			}
			if (b.rePVRequestorCode == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.rePVRequestorCode);
			}
			if (b.actionOther == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.actionOther);
			}
			if (b.comments == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.comments);
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
		IncidentBean b = (IncidentBean) eb;
		java.lang.String incidentId = (java.lang.String)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.incidentId = incidentId;
		b.createUser = resultSet.getString(2);
		b.createDate = resultSet.getTimestamp(3);
		b.sampleId = resultSet.getString(4);
		b.consentId = resultSet.getString(5);
		b.action = resultSet.getString(6);
		b.reason = resultSet.getString(7);
		b.slideId = resultSet.getString(8);
		b.pathologist = resultSet.getString(9);
		b.rePVRequestorCode = resultSet.getString(10);
		b.actionOther = resultSet.getString(11);
		b.comments = resultSet.getString(12);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		IncidentBean b = (IncidentBean) eb;
		java.lang.String incidentId = (java.lang.String)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (incidentId == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, incidentId);
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
		IncidentBean b = (IncidentBean) eb;
		java.lang.String incidentId;
		incidentId = b.incidentId;
		java.lang.String _primaryKey = incidentId;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		IncidentBean b = (IncidentBean) eb;
		java.lang.String incidentId;
		incidentId = b.incidentId;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (incidentId == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, incidentId);
			}
			if (b.createUser == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.createUser);
			}
			if (b.createDate == null) {
				pstmt.setNull(2, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(2, b.createDate);
			}
			if (b.sampleId == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.sampleId);
			}
			if (b.consentId == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.consentId);
			}
			if (b.action == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.action);
			}
			if (b.reason == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.reason);
			}
			if (b.slideId == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.slideId);
			}
			if (b.pathologist == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.pathologist);
			}
			if (b.rePVRequestorCode == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.rePVRequestorCode);
			}
			if (b.actionOther == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.actionOther);
			}
			if (b.comments == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.comments);
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
		IncidentBean b = (IncidentBean) eb;
		java.lang.String incidentId;
		incidentId = b.incidentId;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (incidentId == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, incidentId);
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
		java.lang.String incidentId;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			incidentId = resultSet.getString(1);
			return incidentId;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.lims.beans.Incident findByPrimaryKey(java.lang.String primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.lims.beans.Incident) home.activateBean(primaryKey);
	}
}
