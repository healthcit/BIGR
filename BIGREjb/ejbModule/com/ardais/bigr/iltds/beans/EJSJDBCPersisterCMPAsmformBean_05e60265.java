package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPAsmformBean_05e60265
 */
public class EJSJDBCPersisterCMPAsmformBean_05e60265 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderAsmformBean {
	private static final String _createString = "INSERT INTO ILTDS_ASM_FORM (ASM_FORM_ID, ARDAIS_STAFF_ID, SURGICAL_SPECIMEN_ID, LINK_STAFF_ID, GROSSING_DATE, REMOVAL_DATE, CREATION_TIME, SURGICAL_SPECIMEN_ID_OTHER, ARDAIS_ID, SURGICALTIME_ACCURACY_CID, PROCEDURE_DATE, CONSENT_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_ASM_FORM  WHERE ASM_FORM_ID = ?";
	private static final String _storeString = "UPDATE ILTDS_ASM_FORM  SET ARDAIS_STAFF_ID = ?, SURGICAL_SPECIMEN_ID = ?, LINK_STAFF_ID = ?, GROSSING_DATE = ?, REMOVAL_DATE = ?, CREATION_TIME = ?, SURGICAL_SPECIMEN_ID_OTHER = ?, ARDAIS_ID = ?, SURGICALTIME_ACCURACY_CID = ?, PROCEDURE_DATE = ?, CONSENT_ID = ? WHERE ASM_FORM_ID = ?";
	private static final String _loadString = " SELECT T1.ASM_FORM_ID, T1.ARDAIS_STAFF_ID, T1.SURGICAL_SPECIMEN_ID, T1.LINK_STAFF_ID, T1.GROSSING_DATE, T1.REMOVAL_DATE, T1.CREATION_TIME, T1.SURGICAL_SPECIMEN_ID_OTHER, T1.ARDAIS_ID, T1.SURGICALTIME_ACCURACY_CID, T1.PROCEDURE_DATE, T1.CONSENT_ID FROM ILTDS_ASM_FORM  T1 WHERE T1.ASM_FORM_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPAsmformBean_05e60265
	 */
	public EJSJDBCPersisterCMPAsmformBean_05e60265() throws java.rmi.RemoteException {
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
		AsmformBean b = (AsmformBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.asm_form_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.asm_form_id);
			}
			if (b.ardais_staff_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardais_staff_id);
			}
			if (b.surgical_specimen_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.surgical_specimen_id);
			}
			if (b.link_staff_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.link_staff_id);
			}
			if (b.grossing_date == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.grossing_date);
			}
			if (b.removal_date == null) {
				pstmt.setNull(6, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(6, b.removal_date);
			}
			if (b.creation_time == null) {
				pstmt.setNull(7, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(7, b.creation_time);
			}
			if (b.surgical_specimen_id_other == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.surgical_specimen_id_other);
			}
			if (b.ardais_id == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.ardais_id);
			}
			if (b.acc_surgical_removal_time == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.acc_surgical_removal_time);
			}
			if (b.procedure_date == null) {
				pstmt.setNull(11, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(11, b.procedure_date);
			}
			if (b.consent_consent_id == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.consent_consent_id);
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
		AsmformBean b = (AsmformBean) eb;
		com.ardais.bigr.iltds.beans.AsmformKey _primaryKey = (com.ardais.bigr.iltds.beans.AsmformKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.asm_form_id = _primaryKey.asm_form_id;
		b.ardais_staff_id = resultSet.getString(2);
		b.surgical_specimen_id = resultSet.getString(3);
		b.link_staff_id = resultSet.getString(4);
		b.grossing_date = resultSet.getTimestamp(5);
		b.removal_date = resultSet.getTimestamp(6);
		b.creation_time = resultSet.getTimestamp(7);
		b.surgical_specimen_id_other = resultSet.getString(8);
		b.ardais_id = resultSet.getString(9);
		b.acc_surgical_removal_time = resultSet.getString(10);
		b.procedure_date = resultSet.getTimestamp(11);
		b.consent_consent_id = resultSet.getString(12);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		AsmformBean b = (AsmformBean) eb;
		com.ardais.bigr.iltds.beans.AsmformKey _primaryKey = (com.ardais.bigr.iltds.beans.AsmformKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.asm_form_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.asm_form_id);
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
		AsmformBean b = (AsmformBean) eb;
		com.ardais.bigr.iltds.beans.AsmformKey _primaryKey = new com.ardais.bigr.iltds.beans.AsmformKey();
		_primaryKey.asm_form_id = b.asm_form_id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		AsmformBean b = (AsmformBean) eb;
		com.ardais.bigr.iltds.beans.AsmformKey _primaryKey = new com.ardais.bigr.iltds.beans.AsmformKey();
		_primaryKey.asm_form_id = b.asm_form_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.asm_form_id == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, _primaryKey.asm_form_id);
			}
			if (b.ardais_staff_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.ardais_staff_id);
			}
			if (b.surgical_specimen_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.surgical_specimen_id);
			}
			if (b.link_staff_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.link_staff_id);
			}
			if (b.grossing_date == null) {
				pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(4, b.grossing_date);
			}
			if (b.removal_date == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.removal_date);
			}
			if (b.creation_time == null) {
				pstmt.setNull(6, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(6, b.creation_time);
			}
			if (b.surgical_specimen_id_other == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.surgical_specimen_id_other);
			}
			if (b.ardais_id == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.ardais_id);
			}
			if (b.acc_surgical_removal_time == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.acc_surgical_removal_time);
			}
			if (b.procedure_date == null) {
				pstmt.setNull(10, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(10, b.procedure_date);
			}
			if (b.consent_consent_id == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.consent_consent_id);
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
		AsmformBean b = (AsmformBean) eb;
		com.ardais.bigr.iltds.beans.AsmformKey _primaryKey = new com.ardais.bigr.iltds.beans.AsmformKey();
		_primaryKey.asm_form_id = b.asm_form_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.asm_form_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.asm_form_id);
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
		com.ardais.bigr.iltds.beans.AsmformKey key = new com.ardais.bigr.iltds.beans.AsmformKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.asm_form_id = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Asmform findByPrimaryKey(com.ardais.bigr.iltds.beans.AsmformKey primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Asmform) home.activateBean(primaryKey);
	}
	/**
	 * findAsmformByConsent
	 */
	public EJSFinder findAsmformByConsent(com.ardais.bigr.iltds.beans.ConsentKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ASM_FORM_ID, T1.ARDAIS_STAFF_ID, T1.SURGICAL_SPECIMEN_ID, T1.LINK_STAFF_ID, T1.GROSSING_DATE, T1.REMOVAL_DATE, T1.CREATION_TIME, T1.SURGICAL_SPECIMEN_ID_OTHER, T1.ARDAIS_ID, T1.SURGICALTIME_ACCURACY_CID, T1.PROCEDURE_DATE, T1.CONSENT_ID FROM ILTDS_ASM_FORM  T1 WHERE T1.CONSENT_ID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.consent_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.consent_id);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findByASMFormID
	 */
	public EJSFinder findByASMFormID(java.lang.String asmFormID) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ASM_FORM_ID, T1.ARDAIS_STAFF_ID, T1.SURGICAL_SPECIMEN_ID, T1.LINK_STAFF_ID, T1.GROSSING_DATE, T1.REMOVAL_DATE, T1.CREATION_TIME, T1.SURGICAL_SPECIMEN_ID_OTHER, T1.ARDAIS_ID, T1.SURGICALTIME_ACCURACY_CID, T1.PROCEDURE_DATE, T1.CONSENT_ID FROM ILTDS_ASM_FORM  T1 WHERE asm_form_id = ?");
			if (asmFormID == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, asmFormID);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
