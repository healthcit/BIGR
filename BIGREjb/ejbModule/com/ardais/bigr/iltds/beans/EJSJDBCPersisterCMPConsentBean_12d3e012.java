package com.ardais.bigr.iltds.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPConsentBean_12d3e012
 */
public class EJSJDBCPersisterCMPConsentBean_12d3e012 extends EJSJDBCPersister implements com.ardais.bigr.iltds.beans.EJSFinderConsentBean {
	private static final String _createString = "INSERT INTO ILTDS_INFORMED_CONSENT (CONSENT_ID, ARDAIS_STAFF_ID, DISEASE_CONCEPT_ID, LINKED, FORM_ENTRY_STAFF_ID, FORM_ENTRY_DATETIME, FORM_SIGNED_IND, CONSENT_RELEASE_STAFF_ID, CONSENT_RELEASE_DATETIME, CONSENT_PULL_STAFF_ID, CONSENT_PULL_REQUEST_BY, CONSENT_PULL_DATETIME, CONSENT_PULL_REASON_CD, CONSENT_PULL_COMMENT, FORM_VERIFIED_BY_STAFF_ID, FORM_VERIFIED_DATETIME, FORM_VERIFIED_SIGN_IND, CONSENT_VERSION_NUM, CONSENT_DATETIME, BANKABLE_IND, DISEASE_CONCEPT_ID_OTHER, ARDAIS_ID, POLICY_ID, CONSENT_VERSION_ID, COMMENTS, BLOOD_SAMPLE_YN, ADDITIONAL_NEEDLE_STICK_YN, FUTURE_CONTACT_YN, IMPORTED_YN, CUSTOMER_ID, ARDAIS_ACCT_KEY, PSA, DRE_CID, CLINICAL_FINDING_NOTES, CASE_REGISTRATION_FORM, CONSENT_LOCATION_ADDRESS_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ILTDS_INFORMED_CONSENT  WHERE CONSENT_ID = ?";
	private static final String _storeString = "UPDATE ILTDS_INFORMED_CONSENT  SET ARDAIS_STAFF_ID = ?, DISEASE_CONCEPT_ID = ?, LINKED = ?, FORM_ENTRY_STAFF_ID = ?, FORM_ENTRY_DATETIME = ?, FORM_SIGNED_IND = ?, CONSENT_RELEASE_STAFF_ID = ?, CONSENT_RELEASE_DATETIME = ?, CONSENT_PULL_STAFF_ID = ?, CONSENT_PULL_REQUEST_BY = ?, CONSENT_PULL_DATETIME = ?, CONSENT_PULL_REASON_CD = ?, CONSENT_PULL_COMMENT = ?, FORM_VERIFIED_BY_STAFF_ID = ?, FORM_VERIFIED_DATETIME = ?, FORM_VERIFIED_SIGN_IND = ?, CONSENT_VERSION_NUM = ?, CONSENT_DATETIME = ?, BANKABLE_IND = ?, DISEASE_CONCEPT_ID_OTHER = ?, ARDAIS_ID = ?, POLICY_ID = ?, CONSENT_VERSION_ID = ?, COMMENTS = ?, BLOOD_SAMPLE_YN = ?, ADDITIONAL_NEEDLE_STICK_YN = ?, FUTURE_CONTACT_YN = ?, IMPORTED_YN = ?, CUSTOMER_ID = ?, ARDAIS_ACCT_KEY = ?, PSA = ?, DRE_CID = ?, CLINICAL_FINDING_NOTES = ?, CASE_REGISTRATION_FORM = ?, CONSENT_LOCATION_ADDRESS_ID = ? WHERE CONSENT_ID = ?";
	private static final String _loadString = " SELECT T1.CONSENT_ID, T1.ARDAIS_STAFF_ID, T1.DISEASE_CONCEPT_ID, T1.LINKED, T1.FORM_ENTRY_STAFF_ID, T1.FORM_ENTRY_DATETIME, T1.FORM_SIGNED_IND, T1.CONSENT_RELEASE_STAFF_ID, T1.CONSENT_RELEASE_DATETIME, T1.CONSENT_PULL_STAFF_ID, T1.CONSENT_PULL_REQUEST_BY, T1.CONSENT_PULL_DATETIME, T1.CONSENT_PULL_REASON_CD, T1.CONSENT_PULL_COMMENT, T1.FORM_VERIFIED_BY_STAFF_ID, T1.FORM_VERIFIED_DATETIME, T1.FORM_VERIFIED_SIGN_IND, T1.CONSENT_VERSION_NUM, T1.CONSENT_DATETIME, T1.BANKABLE_IND, T1.DISEASE_CONCEPT_ID_OTHER, T1.ARDAIS_ID, T1.POLICY_ID, T1.CONSENT_VERSION_ID, T1.COMMENTS, T1.BLOOD_SAMPLE_YN, T1.ADDITIONAL_NEEDLE_STICK_YN, T1.FUTURE_CONTACT_YN, T1.IMPORTED_YN, T1.CUSTOMER_ID, T1.ARDAIS_ACCT_KEY, T1.PSA, T1.DRE_CID, T1.CLINICAL_FINDING_NOTES, T1.CASE_REGISTRATION_FORM, T1.CONSENT_LOCATION_ADDRESS_ID FROM ILTDS_INFORMED_CONSENT  T1 WHERE T1.CONSENT_ID = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPConsentBean_12d3e012
	 */
	public EJSJDBCPersisterCMPConsentBean_12d3e012() throws java.rmi.RemoteException {
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
		ConsentBean b = (ConsentBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.consent_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.consent_id);
			}
			if (b.ardais_staff_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardais_staff_id);
			}
			if (b.disease_concept_id == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.disease_concept_id);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.linked);
			if (objectTemp == null) {
				pstmt.setNull(4, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(4, (java.lang.String)objectTemp);
			}
			if (b.form_entry_staff_id == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.form_entry_staff_id);
			}
			if (b.form_entry_datetime == null) {
				pstmt.setNull(6, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(6, b.form_entry_datetime);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.form_signed_ind);
			if (objectTemp == null) {
				pstmt.setNull(7, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(7, (java.lang.String)objectTemp);
			}
			if (b.consent_release_staff_id == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.consent_release_staff_id);
			}
			if (b.consent_release_datetime == null) {
				pstmt.setNull(9, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(9, b.consent_release_datetime);
			}
			if (b.consent_pull_staff_id == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.consent_pull_staff_id);
			}
			if (b.consent_pull_request_by == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.consent_pull_request_by);
			}
			if (b.consent_pull_datetime == null) {
				pstmt.setNull(12, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(12, b.consent_pull_datetime);
			}
			if (b.consent_pull_reason_cd == null) {
				pstmt.setNull(13, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(13, b.consent_pull_reason_cd);
			}
			if (b.consent_pull_comment == null) {
				pstmt.setNull(14, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(14, b.consent_pull_comment);
			}
			if (b.form_verified_by_staff_id == null) {
				pstmt.setNull(15, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(15, b.form_verified_by_staff_id);
			}
			if (b.form_verified_datetime == null) {
				pstmt.setNull(16, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(16, b.form_verified_datetime);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.form_verified_sign_ind);
			if (objectTemp == null) {
				pstmt.setNull(17, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(17, (java.lang.String)objectTemp);
			}
			if (b.consent_version_num == null) {
				pstmt.setNull(18, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(18, b.consent_version_num);
			}
			if (b.consent_datetime == null) {
				pstmt.setNull(19, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(19, b.consent_datetime);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.bankable_ind);
			if (objectTemp == null) {
				pstmt.setNull(20, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(20, (java.lang.String)objectTemp);
			}
			if (b.disease_concept_id_other == null) {
				pstmt.setNull(21, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(21, b.disease_concept_id_other);
			}
			if (b.ardais_id == null) {
				pstmt.setNull(22, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(22, b.ardais_id);
			}
			if (b.policy_id == null) {
				pstmt.setNull(23, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(23, b.policy_id);
			}
			if (b.consent_version_id == null) {
				pstmt.setNull(24, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(24, b.consent_version_id);
			}
			if (b.comments == null) {
				pstmt.setNull(25, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(25, b.comments);
			}
			if (b.blood_sample_yn == null) {
				pstmt.setNull(26, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(26, b.blood_sample_yn);
			}
			if (b.additional_needle_stick_yn == null) {
				pstmt.setNull(27, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(27, b.additional_needle_stick_yn);
			}
			if (b.future_contact_yn == null) {
				pstmt.setNull(28, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(28, b.future_contact_yn);
			}
			if (b.imported_yn == null) {
				pstmt.setNull(29, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(29, b.imported_yn);
			}
			if (b.customer_id == null) {
				pstmt.setNull(30, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(30, b.customer_id);
			}
			if (b.ardais_acct_key == null) {
				pstmt.setNull(31, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(31, b.ardais_acct_key);
			}
			if (b.psa == null) {
				pstmt.setNull(32, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(32, b.psa);
			}
			if (b.dre_cid == null) {
				pstmt.setNull(33, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(33, b.dre_cid);
			}
			if (b.clinical_finding_notes == null) {
				pstmt.setNull(34, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(34, b.clinical_finding_notes);
			}
			if (b.case_registration_form == null) {
				pstmt.setNull(35, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(35, b.case_registration_form);
			}
			if (b.geolocation_location_address_id == null) {
				pstmt.setNull(36, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(36, b.geolocation_location_address_id);
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
		ConsentBean b = (ConsentBean) eb;
		com.ardais.bigr.iltds.beans.ConsentKey _primaryKey = (com.ardais.bigr.iltds.beans.ConsentKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.consent_id = _primaryKey.consent_id;
		b.ardais_staff_id = resultSet.getString(2);
		b.disease_concept_id = resultSet.getString(3);
		b.linked = (java.lang.String)com.ibm.vap.converters.VapTrimStringConverter.singleton().objectFrom(resultSet.getString(4));
		b.form_entry_staff_id = resultSet.getString(5);
		b.form_entry_datetime = resultSet.getTimestamp(6);
		b.form_signed_ind = (java.lang.String)com.ibm.vap.converters.VapTrimStringConverter.singleton().objectFrom(resultSet.getString(7));
		b.consent_release_staff_id = resultSet.getString(8);
		b.consent_release_datetime = resultSet.getTimestamp(9);
		b.consent_pull_staff_id = resultSet.getString(10);
		b.consent_pull_request_by = resultSet.getString(11);
		b.consent_pull_datetime = resultSet.getTimestamp(12);
		b.consent_pull_reason_cd = resultSet.getString(13);
		b.consent_pull_comment = resultSet.getString(14);
		b.form_verified_by_staff_id = resultSet.getString(15);
		b.form_verified_datetime = resultSet.getTimestamp(16);
		b.form_verified_sign_ind = (java.lang.String)com.ibm.vap.converters.VapTrimStringConverter.singleton().objectFrom(resultSet.getString(17));
		b.consent_version_num = resultSet.getString(18);
		b.consent_datetime = resultSet.getTimestamp(19);
		b.bankable_ind = (java.lang.String)com.ibm.vap.converters.VapTrimStringConverter.singleton().objectFrom(resultSet.getString(20));
		b.disease_concept_id_other = resultSet.getString(21);
		b.ardais_id = resultSet.getString(22);
		b.policy_id = resultSet.getBigDecimal(23);
		b.consent_version_id = resultSet.getBigDecimal(24);
		b.comments = resultSet.getString(25);
		b.blood_sample_yn = resultSet.getString(26);
		b.additional_needle_stick_yn = resultSet.getString(27);
		b.future_contact_yn = resultSet.getString(28);
		b.imported_yn = resultSet.getString(29);
		b.customer_id = resultSet.getString(30);
		b.ardais_acct_key = resultSet.getString(31);
		b.psa = resultSet.getBigDecimal(32);
		b.dre_cid = resultSet.getString(33);
		b.clinical_finding_notes = resultSet.getString(34);
		b.case_registration_form = resultSet.getString(35);
		b.geolocation_location_address_id = resultSet.getString(36);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ConsentBean b = (ConsentBean) eb;
		com.ardais.bigr.iltds.beans.ConsentKey _primaryKey = (com.ardais.bigr.iltds.beans.ConsentKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.consent_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.consent_id);
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
		ConsentBean b = (ConsentBean) eb;
		com.ardais.bigr.iltds.beans.ConsentKey _primaryKey = new com.ardais.bigr.iltds.beans.ConsentKey();
		_primaryKey.consent_id = b.consent_id;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ConsentBean b = (ConsentBean) eb;
		com.ardais.bigr.iltds.beans.ConsentKey _primaryKey = new com.ardais.bigr.iltds.beans.ConsentKey();
		_primaryKey.consent_id = b.consent_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.consent_id == null) {
				pstmt.setNull(36, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(36, _primaryKey.consent_id);
			}
			if (b.ardais_staff_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.ardais_staff_id);
			}
			if (b.disease_concept_id == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.disease_concept_id);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.linked);
			if (objectTemp == null) {
				pstmt.setNull(3, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(3, (java.lang.String)objectTemp);
			}
			if (b.form_entry_staff_id == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.form_entry_staff_id);
			}
			if (b.form_entry_datetime == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.form_entry_datetime);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.form_signed_ind);
			if (objectTemp == null) {
				pstmt.setNull(6, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(6, (java.lang.String)objectTemp);
			}
			if (b.consent_release_staff_id == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.consent_release_staff_id);
			}
			if (b.consent_release_datetime == null) {
				pstmt.setNull(8, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(8, b.consent_release_datetime);
			}
			if (b.consent_pull_staff_id == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.consent_pull_staff_id);
			}
			if (b.consent_pull_request_by == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.consent_pull_request_by);
			}
			if (b.consent_pull_datetime == null) {
				pstmt.setNull(11, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(11, b.consent_pull_datetime);
			}
			if (b.consent_pull_reason_cd == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.consent_pull_reason_cd);
			}
			if (b.consent_pull_comment == null) {
				pstmt.setNull(13, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(13, b.consent_pull_comment);
			}
			if (b.form_verified_by_staff_id == null) {
				pstmt.setNull(14, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(14, b.form_verified_by_staff_id);
			}
			if (b.form_verified_datetime == null) {
				pstmt.setNull(15, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(15, b.form_verified_datetime);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.form_verified_sign_ind);
			if (objectTemp == null) {
				pstmt.setNull(16, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(16, (java.lang.String)objectTemp);
			}
			if (b.consent_version_num == null) {
				pstmt.setNull(17, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(17, b.consent_version_num);
			}
			if (b.consent_datetime == null) {
				pstmt.setNull(18, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(18, b.consent_datetime);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.bankable_ind);
			if (objectTemp == null) {
				pstmt.setNull(19, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(19, (java.lang.String)objectTemp);
			}
			if (b.disease_concept_id_other == null) {
				pstmt.setNull(20, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(20, b.disease_concept_id_other);
			}
			if (b.ardais_id == null) {
				pstmt.setNull(21, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(21, b.ardais_id);
			}
			if (b.policy_id == null) {
				pstmt.setNull(22, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(22, b.policy_id);
			}
			if (b.consent_version_id == null) {
				pstmt.setNull(23, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(23, b.consent_version_id);
			}
			if (b.comments == null) {
				pstmt.setNull(24, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(24, b.comments);
			}
			if (b.blood_sample_yn == null) {
				pstmt.setNull(25, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(25, b.blood_sample_yn);
			}
			if (b.additional_needle_stick_yn == null) {
				pstmt.setNull(26, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(26, b.additional_needle_stick_yn);
			}
			if (b.future_contact_yn == null) {
				pstmt.setNull(27, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(27, b.future_contact_yn);
			}
			if (b.imported_yn == null) {
				pstmt.setNull(28, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(28, b.imported_yn);
			}
			if (b.customer_id == null) {
				pstmt.setNull(29, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(29, b.customer_id);
			}
			if (b.ardais_acct_key == null) {
				pstmt.setNull(30, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(30, b.ardais_acct_key);
			}
			if (b.psa == null) {
				pstmt.setNull(31, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(31, b.psa);
			}
			if (b.dre_cid == null) {
				pstmt.setNull(32, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(32, b.dre_cid);
			}
			if (b.clinical_finding_notes == null) {
				pstmt.setNull(33, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(33, b.clinical_finding_notes);
			}
			if (b.case_registration_form == null) {
				pstmt.setNull(34, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(34, b.case_registration_form);
			}
			if (b.geolocation_location_address_id == null) {
				pstmt.setNull(35, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(35, b.geolocation_location_address_id);
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
		ConsentBean b = (ConsentBean) eb;
		com.ardais.bigr.iltds.beans.ConsentKey _primaryKey = new com.ardais.bigr.iltds.beans.ConsentKey();
		_primaryKey.consent_id = b.consent_id;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.consent_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.consent_id);
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
		com.ardais.bigr.iltds.beans.ConsentKey key = new com.ardais.bigr.iltds.beans.ConsentKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.consent_id = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.iltds.beans.Consent findByPrimaryKey(com.ardais.bigr.iltds.beans.ConsentKey primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.iltds.beans.Consent) home.activateBean(primaryKey);
	}
	/**
	 * findConsentByGeolocation
	 */
	public EJSFinder findConsentByGeolocation(com.ardais.bigr.iltds.beans.GeolocationKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.CONSENT_ID, T1.ARDAIS_STAFF_ID, T1.DISEASE_CONCEPT_ID, T1.LINKED, T1.FORM_ENTRY_STAFF_ID, T1.FORM_ENTRY_DATETIME, T1.FORM_SIGNED_IND, T1.CONSENT_RELEASE_STAFF_ID, T1.CONSENT_RELEASE_DATETIME, T1.CONSENT_PULL_STAFF_ID, T1.CONSENT_PULL_REQUEST_BY, T1.CONSENT_PULL_DATETIME, T1.CONSENT_PULL_REASON_CD, T1.CONSENT_PULL_COMMENT, T1.FORM_VERIFIED_BY_STAFF_ID, T1.FORM_VERIFIED_DATETIME, T1.FORM_VERIFIED_SIGN_IND, T1.CONSENT_VERSION_NUM, T1.CONSENT_DATETIME, T1.BANKABLE_IND, T1.DISEASE_CONCEPT_ID_OTHER, T1.ARDAIS_ID, T1.POLICY_ID, T1.CONSENT_VERSION_ID, T1.COMMENTS, T1.BLOOD_SAMPLE_YN, T1.ADDITIONAL_NEEDLE_STICK_YN, T1.FUTURE_CONTACT_YN, T1.IMPORTED_YN, T1.CUSTOMER_ID, T1.ARDAIS_ACCT_KEY, T1.PSA, T1.DRE_CID, T1.CLINICAL_FINDING_NOTES, T1.CASE_REGISTRATION_FORM, T1.CONSENT_LOCATION_ADDRESS_ID FROM ILTDS_INFORMED_CONSENT  T1 WHERE T1.CONSENT_LOCATION_ADDRESS_ID = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.location_address_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.location_address_id);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findConsentByArdaisID
	 */
	public EJSFinder findConsentByArdaisID(java.lang.String consentID) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.CONSENT_ID, T1.ARDAIS_STAFF_ID, T1.DISEASE_CONCEPT_ID, T1.LINKED, T1.FORM_ENTRY_STAFF_ID, T1.FORM_ENTRY_DATETIME, T1.FORM_SIGNED_IND, T1.CONSENT_RELEASE_STAFF_ID, T1.CONSENT_RELEASE_DATETIME, T1.CONSENT_PULL_STAFF_ID, T1.CONSENT_PULL_REQUEST_BY, T1.CONSENT_PULL_DATETIME, T1.CONSENT_PULL_REASON_CD, T1.CONSENT_PULL_COMMENT, T1.FORM_VERIFIED_BY_STAFF_ID, T1.FORM_VERIFIED_DATETIME, T1.FORM_VERIFIED_SIGN_IND, T1.CONSENT_VERSION_NUM, T1.CONSENT_DATETIME, T1.BANKABLE_IND, T1.DISEASE_CONCEPT_ID_OTHER, T1.ARDAIS_ID, T1.POLICY_ID, T1.CONSENT_VERSION_ID, T1.COMMENTS, T1.BLOOD_SAMPLE_YN, T1.ADDITIONAL_NEEDLE_STICK_YN, T1.FUTURE_CONTACT_YN, T1.IMPORTED_YN, T1.CUSTOMER_ID, T1.ARDAIS_ACCT_KEY, T1.PSA, T1.DRE_CID, T1.CLINICAL_FINDING_NOTES, T1.CASE_REGISTRATION_FORM, T1.CONSENT_LOCATION_ADDRESS_ID FROM ILTDS_INFORMED_CONSENT  T1 WHERE ardais_id = ?");
			if (consentID == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, consentID);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
	/**
	 * findByConsentID
	 */
	public EJSFinder findByConsentID(java.lang.String consentID) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.CONSENT_ID, T1.ARDAIS_STAFF_ID, T1.DISEASE_CONCEPT_ID, T1.LINKED, T1.FORM_ENTRY_STAFF_ID, T1.FORM_ENTRY_DATETIME, T1.FORM_SIGNED_IND, T1.CONSENT_RELEASE_STAFF_ID, T1.CONSENT_RELEASE_DATETIME, T1.CONSENT_PULL_STAFF_ID, T1.CONSENT_PULL_REQUEST_BY, T1.CONSENT_PULL_DATETIME, T1.CONSENT_PULL_REASON_CD, T1.CONSENT_PULL_COMMENT, T1.FORM_VERIFIED_BY_STAFF_ID, T1.FORM_VERIFIED_DATETIME, T1.FORM_VERIFIED_SIGN_IND, T1.CONSENT_VERSION_NUM, T1.CONSENT_DATETIME, T1.BANKABLE_IND, T1.DISEASE_CONCEPT_ID_OTHER, T1.ARDAIS_ID, T1.POLICY_ID, T1.CONSENT_VERSION_ID, T1.COMMENTS, T1.BLOOD_SAMPLE_YN, T1.ADDITIONAL_NEEDLE_STICK_YN, T1.FUTURE_CONTACT_YN, T1.IMPORTED_YN, T1.CUSTOMER_ID, T1.ARDAIS_ACCT_KEY, T1.PSA, T1.DRE_CID, T1.CLINICAL_FINDING_NOTES, T1.CASE_REGISTRATION_FORM, T1.CONSENT_LOCATION_ADDRESS_ID FROM ILTDS_INFORMED_CONSENT  T1 WHERE consent_id = ?");
			if (consentID == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, consentID);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
