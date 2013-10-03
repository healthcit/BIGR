package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPAsmBean_ca61be70
 */
public class EJSJDBCPersisterCMPAsmBean_ca61be70 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderAsmBean {
	private static final String _createString = "INSERT INTO ILTDS_ASM (ASM_ID, CONSENT_ID, ASM_FORM_ID, ORGAN_SITE_CONCEPT_ID, SPECIMEN_TYPE, ASM_ENTRY_DATE, ARDAIS_ID, ORGAN_SITE_CONCEPT_ID_OTHER, PFIN_MEETS_SPECS, MODULE_WEIGHT, MODULE_COMMENTS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_ASM  WHERE ASM_ID = ?";
	private static final String _storeString = "UPDATE ILTDS_ASM  SET CONSENT_ID = ?, ASM_FORM_ID = ?, ORGAN_SITE_CONCEPT_ID = ?, SPECIMEN_TYPE = ?, ASM_ENTRY_DATE = ?, ARDAIS_ID = ?, ORGAN_SITE_CONCEPT_ID_OTHER = ?, PFIN_MEETS_SPECS = ?, MODULE_WEIGHT = ?, MODULE_COMMENTS = ? WHERE ASM_ID = ?";
	private static final String _loadString = " SELECT T1.ASM_ID, T1.CONSENT_ID, T1.ASM_FORM_ID, T1.ORGAN_SITE_CONCEPT_ID, T1.SPECIMEN_TYPE, T1.ASM_ENTRY_DATE, T1.ARDAIS_ID, T1.ORGAN_SITE_CONCEPT_ID_OTHER, T1.PFIN_MEETS_SPECS, T1.MODULE_WEIGHT, T1.MODULE_COMMENTS FROM ILTDS_ASM  T1 WHERE T1.ASM_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPAsmBean_ca61be70
	 */
	public EJSJDBCPersisterCMPAsmBean_ca61be70() throws java.rmi.RemoteException {
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
		AsmBean b = (AsmBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.asm_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.asm_id);
			}
			if (b.consent_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.consent_id);
			}
			if (b.asm_form_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.asm_form_id);
			}
			if (b.organ_site_concept_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.organ_site_concept_id);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.specimen_type);
			if (objectTemp == null) {
				pstmt.setNull(5, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(5, (java.lang.String)objectTemp);
			}
			if (b.asm_entry_date == null) {
				pstmt.setNull(6, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(6, b.asm_entry_date);
			}
			if (b.ardais_id == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.ardais_id);
			}
			if (b.organ_site_concept_id_other == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.organ_site_concept_id_other);
			}
			if (b.pfin_meets_specs == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.pfin_meets_specs);
			}
			if (b.module_weight == null) {
				pstmt.setNull(10, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(10, b.module_weight.intValue());
			}
			if (b.module_comments == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.module_comments);
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
		AsmBean b = (AsmBean) eb;
		com.ardais.bigr.iltds.beans.AsmKey _primaryKey = (com.ardais.bigr.iltds.beans.AsmKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		int tempint;

		b.asm_id = _primaryKey.asm_id;
		b.consent_id = resultSet.getString(2);
		b.asm_form_id = resultSet.getString(3);
		b.organ_site_concept_id = resultSet.getString(4);
		b.specimen_type = (java.lang.String)com.ibm.vap.converters.VapTrimStringConverter.singleton().objectFrom(resultSet.getString(5));
		b.asm_entry_date = resultSet.getTimestamp(6);
		b.ardais_id = resultSet.getString(7);
		b.organ_site_concept_id_other = resultSet.getString(8);
		b.pfin_meets_specs = resultSet.getString(9);
		tempint = resultSet.getInt(10);
		b.module_weight = resultSet.wasNull() ? null : new Integer(tempint);
		b.module_comments = resultSet.getString(11);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		AsmBean b = (AsmBean) eb;
		com.ardais.bigr.iltds.beans.AsmKey _primaryKey = (com.ardais.bigr.iltds.beans.AsmKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.asm_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.asm_id);
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
		AsmBean b = (AsmBean) eb;
		com.ardais.bigr.iltds.beans.AsmKey _primaryKey = new com.ardais.bigr.iltds.beans.AsmKey();
		_primaryKey.asm_id = b.asm_id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		AsmBean b = (AsmBean) eb;
		com.ardais.bigr.iltds.beans.AsmKey _primaryKey = new com.ardais.bigr.iltds.beans.AsmKey();
		_primaryKey.asm_id = b.asm_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.asm_id == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, _primaryKey.asm_id);
			}
			if (b.consent_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.consent_id);
			}
			if (b.asm_form_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.asm_form_id);
			}
			if (b.organ_site_concept_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.organ_site_concept_id);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.specimen_type);
			if (objectTemp == null) {
				pstmt.setNull(4, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(4, (java.lang.String)objectTemp);
			}
			if (b.asm_entry_date == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.asm_entry_date);
			}
			if (b.ardais_id == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.ardais_id);
			}
			if (b.organ_site_concept_id_other == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.organ_site_concept_id_other);
			}
			if (b.pfin_meets_specs == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.pfin_meets_specs);
			}
			if (b.module_weight == null) {
				pstmt.setNull(9, java.sql.Types.INTEGER);
			}
			else {
				pstmt.setInt(9, b.module_weight.intValue());
			}
			if (b.module_comments == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.module_comments);
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
		AsmBean b = (AsmBean) eb;
		com.ardais.bigr.iltds.beans.AsmKey _primaryKey = new com.ardais.bigr.iltds.beans.AsmKey();
		_primaryKey.asm_id = b.asm_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.asm_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.asm_id);
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
		com.ardais.bigr.iltds.beans.AsmKey key = new com.ardais.bigr.iltds.beans.AsmKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.asm_id = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Asm findByPrimaryKey(com.ardais.bigr.iltds.beans.AsmKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Asm) home.activateBean(key);
	}
	/**
	 * findByConsentID
	 */
	public EJSFinder findByConsentID(java.lang.String argConsentID) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ASM_ID, T1.CONSENT_ID, T1.ASM_FORM_ID, T1.ORGAN_SITE_CONCEPT_ID, T1.SPECIMEN_TYPE, T1.ASM_ENTRY_DATE, T1.ARDAIS_ID, T1.ORGAN_SITE_CONCEPT_ID_OTHER, T1.PFIN_MEETS_SPECS, T1.MODULE_WEIGHT, T1.MODULE_COMMENTS FROM ILTDS_ASM  T1 WHERE consent_id = ?");
			if (argConsentID == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, argConsentID);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
