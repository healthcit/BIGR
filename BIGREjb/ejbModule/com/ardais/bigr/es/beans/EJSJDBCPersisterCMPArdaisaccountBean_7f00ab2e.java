package com.ardais.bigr.es.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPArdaisaccountBean_7f00ab2e
 */
public class EJSJDBCPersisterCMPArdaisaccountBean_7f00ab2e extends EJSJDBCPersister implements com.ardais.bigr.es.beans.EJSFinderArdaisaccountBean {
	private static final String _createString = "INSERT INTO ES_ARDAIS_ACCOUNT (ARDAIS_ACCT_KEY, ARDAIS_ACCT_TYPE, ARDAIS_ACCT_STATUS, ARDAIS_ACCT_COMPANY_DESC, ARDAIS_PARENT_ACCT_COMP_DESC, ARDAIS_ACCT_OPEN_DATE, ARDAIS_ACCT_ACTIVE_DATE, ARDAIS_ACCT_CLOSE_DATE, ARDAIS_ACCT_DEACTIVATE_DATE, ARDAIS_ACCT_DEACTIVATE_REASON, ARDAIS_ACCT_REACTIVATE_DATE, REQUEST_MGR_EMAIL_ADDRESS, LINKED_CASES_ONLY_YN, PASSWORD_POLICY_CID, PASSWORD_LIFE_SPAN, DEFAULT_BOX_LAYOUT_ID, PRIMARY_LOCATION, DONOR_REGISTRATION_FORM) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ES_ARDAIS_ACCOUNT  WHERE ARDAIS_ACCT_KEY = ?";
	private static final String _storeString = "UPDATE ES_ARDAIS_ACCOUNT  SET ARDAIS_ACCT_TYPE = ?, ARDAIS_ACCT_STATUS = ?, ARDAIS_ACCT_COMPANY_DESC = ?, ARDAIS_PARENT_ACCT_COMP_DESC = ?, ARDAIS_ACCT_OPEN_DATE = ?, ARDAIS_ACCT_ACTIVE_DATE = ?, ARDAIS_ACCT_CLOSE_DATE = ?, ARDAIS_ACCT_DEACTIVATE_DATE = ?, ARDAIS_ACCT_DEACTIVATE_REASON = ?, ARDAIS_ACCT_REACTIVATE_DATE = ?, REQUEST_MGR_EMAIL_ADDRESS = ?, LINKED_CASES_ONLY_YN = ?, PASSWORD_POLICY_CID = ?, PASSWORD_LIFE_SPAN = ?, DEFAULT_BOX_LAYOUT_ID = ?, PRIMARY_LOCATION = ?, DONOR_REGISTRATION_FORM = ? WHERE ARDAIS_ACCT_KEY = ?";
	private static final String _loadString = " SELECT T1.ARDAIS_ACCT_KEY, T1.ARDAIS_ACCT_TYPE, T1.ARDAIS_ACCT_STATUS, T1.ARDAIS_ACCT_COMPANY_DESC, T1.ARDAIS_PARENT_ACCT_COMP_DESC, T1.ARDAIS_ACCT_OPEN_DATE, T1.ARDAIS_ACCT_ACTIVE_DATE, T1.ARDAIS_ACCT_CLOSE_DATE, T1.ARDAIS_ACCT_DEACTIVATE_DATE, T1.ARDAIS_ACCT_DEACTIVATE_REASON, T1.ARDAIS_ACCT_REACTIVATE_DATE, T1.REQUEST_MGR_EMAIL_ADDRESS, T1.LINKED_CASES_ONLY_YN, T1.PASSWORD_POLICY_CID, T1.PASSWORD_LIFE_SPAN, T1.DEFAULT_BOX_LAYOUT_ID, T1.PRIMARY_LOCATION, T1.DONOR_REGISTRATION_FORM FROM ES_ARDAIS_ACCOUNT  T1 WHERE T1.ARDAIS_ACCT_KEY = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPArdaisaccountBean_7f00ab2e
	 */
	public EJSJDBCPersisterCMPArdaisaccountBean_7f00ab2e() throws java.rmi.RemoteException {
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
		ArdaisaccountBean b = (ArdaisaccountBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.ardais_acct_key == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.ardais_acct_key);
			}
			if (b.ardais_acct_type == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardais_acct_type);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.ardais_acct_status);
			if (objectTemp == null) {
				pstmt.setNull(3, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(3, (java.lang.String)objectTemp);
			}
			if (b.ardais_acct_company_desc == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.ardais_acct_company_desc);
			}
			if (b.ardais_parent_acct_comp_desc == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.ardais_parent_acct_comp_desc);
			}
			if (b.ardais_acct_open_date == null) {
				pstmt.setNull(6, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(6, b.ardais_acct_open_date);
			}
			if (b.ardais_acct_active_date == null) {
				pstmt.setNull(7, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(7, b.ardais_acct_active_date);
			}
			if (b.ardais_acct_close_date == null) {
				pstmt.setNull(8, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(8, b.ardais_acct_close_date);
			}
			if (b.ardais_acct_deactivate_date == null) {
				pstmt.setNull(9, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(9, b.ardais_acct_deactivate_date);
			}
			if (b.ardais_acct_deactivate_reason == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.ardais_acct_deactivate_reason);
			}
			if (b.ardais_acct_reactivate_date == null) {
				pstmt.setNull(11, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(11, b.ardais_acct_reactivate_date);
			}
			if (b.request_mgr_email_address == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.request_mgr_email_address);
			}
			if (b.linked_cases_only_yn == null) {
				pstmt.setNull(13, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(13, b.linked_cases_only_yn);
			}
			if (b.passwordPolicyCid == null) {
				pstmt.setNull(14, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(14, b.passwordPolicyCid);
			}
			pstmt.setInt(15, b.passwordLifeSpan);
			if (b.defaultBoxLayoutId == null) {
				pstmt.setNull(16, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(16, b.defaultBoxLayoutId);
			}
			if (b.primaryLocation == null) {
				pstmt.setNull(17, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(17, b.primaryLocation);
			}
			if (b.donorRegistrationForm == null) {
				pstmt.setNull(18, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(18, b.donorRegistrationForm);
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
		ArdaisaccountBean b = (ArdaisaccountBean) eb;
		com.ardais.bigr.es.beans.ArdaisaccountKey _primaryKey = (com.ardais.bigr.es.beans.ArdaisaccountKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.ardais_acct_key = _primaryKey.ardais_acct_key;
		b.ardais_acct_type = resultSet.getString(2);
		b.ardais_acct_status = (java.lang.String)com.ibm.vap.converters.VapTrimStringConverter.singleton().objectFrom(resultSet.getString(3));
		b.ardais_acct_company_desc = resultSet.getString(4);
		b.ardais_parent_acct_comp_desc = resultSet.getString(5);
		b.ardais_acct_open_date = resultSet.getTimestamp(6);
		b.ardais_acct_active_date = resultSet.getTimestamp(7);
		b.ardais_acct_close_date = resultSet.getTimestamp(8);
		b.ardais_acct_deactivate_date = resultSet.getTimestamp(9);
		b.ardais_acct_deactivate_reason = resultSet.getString(10);
		b.ardais_acct_reactivate_date = resultSet.getTimestamp(11);
		b.request_mgr_email_address = resultSet.getString(12);
		b.linked_cases_only_yn = resultSet.getString(13);
		b.passwordPolicyCid = resultSet.getString(14);
		b.passwordLifeSpan = resultSet.getInt(15);
		b.defaultBoxLayoutId = resultSet.getString(16);
		b.primaryLocation = resultSet.getString(17);
		b.donorRegistrationForm = resultSet.getString(18);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ArdaisaccountBean b = (ArdaisaccountBean) eb;
		com.ardais.bigr.es.beans.ArdaisaccountKey _primaryKey = (com.ardais.bigr.es.beans.ArdaisaccountKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.ardais_acct_key);
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
		ArdaisaccountBean b = (ArdaisaccountBean) eb;
		com.ardais.bigr.es.beans.ArdaisaccountKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisaccountKey();
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ArdaisaccountBean b = (ArdaisaccountBean) eb;
		com.ardais.bigr.es.beans.ArdaisaccountKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisaccountKey();
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(18, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(18, _primaryKey.ardais_acct_key);
			}
			if (b.ardais_acct_type == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.ardais_acct_type);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.ardais_acct_status);
			if (objectTemp == null) {
				pstmt.setNull(2, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(2, (java.lang.String)objectTemp);
			}
			if (b.ardais_acct_company_desc == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.ardais_acct_company_desc);
			}
			if (b.ardais_parent_acct_comp_desc == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.ardais_parent_acct_comp_desc);
			}
			if (b.ardais_acct_open_date == null) {
				pstmt.setNull(5, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(5, b.ardais_acct_open_date);
			}
			if (b.ardais_acct_active_date == null) {
				pstmt.setNull(6, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(6, b.ardais_acct_active_date);
			}
			if (b.ardais_acct_close_date == null) {
				pstmt.setNull(7, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(7, b.ardais_acct_close_date);
			}
			if (b.ardais_acct_deactivate_date == null) {
				pstmt.setNull(8, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(8, b.ardais_acct_deactivate_date);
			}
			if (b.ardais_acct_deactivate_reason == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.ardais_acct_deactivate_reason);
			}
			if (b.ardais_acct_reactivate_date == null) {
				pstmt.setNull(10, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(10, b.ardais_acct_reactivate_date);
			}
			if (b.request_mgr_email_address == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.request_mgr_email_address);
			}
			if (b.linked_cases_only_yn == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.linked_cases_only_yn);
			}
			if (b.passwordPolicyCid == null) {
				pstmt.setNull(13, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(13, b.passwordPolicyCid);
			}
			pstmt.setInt(14, b.passwordLifeSpan);
			if (b.defaultBoxLayoutId == null) {
				pstmt.setNull(15, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(15, b.defaultBoxLayoutId);
			}
			if (b.primaryLocation == null) {
				pstmt.setNull(16, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(16, b.primaryLocation);
			}
			if (b.donorRegistrationForm == null) {
				pstmt.setNull(17, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(17, b.donorRegistrationForm);
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
		ArdaisaccountBean b = (ArdaisaccountBean) eb;
		com.ardais.bigr.es.beans.ArdaisaccountKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisaccountKey();
		_primaryKey.ardais_acct_key = b.ardais_acct_key;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.ardais_acct_key == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.ardais_acct_key);
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
		com.ardais.bigr.es.beans.ArdaisaccountKey key = new com.ardais.bigr.es.beans.ArdaisaccountKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.ardais_acct_key = resultSet.getString(1);
			return key;
		}
return null;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Ardaisaccount findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisaccountKey key) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.es.beans.Ardaisaccount) home.activateBean(key);
	}
}
