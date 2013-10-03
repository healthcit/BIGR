package com.ardais.bigr.es.beans;

import com.ibm.ejs.persistence.*;
import javax.ejb.EntityBean;
import java.sql.*;

/**
 * EJSJDBCPersisterCMPArdaisuserBean_3fdc52da
 */
public class EJSJDBCPersisterCMPArdaisuserBean_3fdc52da extends EJSJDBCPersister implements com.ardais.bigr.es.beans.EJSFinderArdaisuserBean {
	private static final String _createString = "INSERT INTO ES_ARDAIS_USER (ARDAIS_USER_ID, ARDAIS_ACCT_KEY, PASSWORD, USER_VERIF_QUESTION, USER_VERIF_ANSWER, USER_LASTNAME, USER_FIRSTNAME, USER_TITLE, USER_FUNCTIONAL_TITLE, USER_AFFILIATION, USER_PHONE_NUMBER, USER_PHONE_EXT, USER_FAX_NUMBER, USER_MOBILE_NUMBER, USER_EMAIL_ADDRESS, CREATED_BY, CREATE_DATE, UPDATE_DATE, UPDATED_BY, USER_ADDRESS_ID, USER_ACTIVE_IND, PASSWORD_POLICY_CID, PASSWORD_LAST_CHANGE_DATE, USER_DEPARTMENT, CONSECUTIVE_FAILED_LOGINS, CONSECUTIVE_FAILED_ANSWERS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String _removeString = "DELETE FROM ES_ARDAIS_USER  WHERE ARDAIS_USER_ID = ? AND ARDAIS_ACCT_KEY = ?";
	private static final String _storeString = "UPDATE ES_ARDAIS_USER  SET PASSWORD = ?, USER_VERIF_QUESTION = ?, USER_VERIF_ANSWER = ?, USER_LASTNAME = ?, USER_FIRSTNAME = ?, USER_TITLE = ?, USER_FUNCTIONAL_TITLE = ?, USER_AFFILIATION = ?, USER_PHONE_NUMBER = ?, USER_PHONE_EXT = ?, USER_FAX_NUMBER = ?, USER_MOBILE_NUMBER = ?, USER_EMAIL_ADDRESS = ?, CREATED_BY = ?, CREATE_DATE = ?, UPDATE_DATE = ?, UPDATED_BY = ?, USER_ADDRESS_ID = ?, USER_ACTIVE_IND = ?, PASSWORD_POLICY_CID = ?, PASSWORD_LAST_CHANGE_DATE = ?, USER_DEPARTMENT = ?, CONSECUTIVE_FAILED_LOGINS = ?, CONSECUTIVE_FAILED_ANSWERS = ? WHERE ARDAIS_USER_ID = ? AND ARDAIS_ACCT_KEY = ?";
	private static final String _loadString = " SELECT T1.ARDAIS_USER_ID, T1.PASSWORD, T1.USER_VERIF_QUESTION, T1.USER_VERIF_ANSWER, T1.USER_LASTNAME, T1.USER_FIRSTNAME, T1.USER_TITLE, T1.USER_FUNCTIONAL_TITLE, T1.USER_AFFILIATION, T1.USER_PHONE_NUMBER, T1.USER_PHONE_EXT, T1.USER_FAX_NUMBER, T1.USER_MOBILE_NUMBER, T1.USER_EMAIL_ADDRESS, T1.CREATED_BY, T1.CREATE_DATE, T1.UPDATE_DATE, T1.UPDATED_BY, T1.USER_ADDRESS_ID, T1.USER_ACTIVE_IND, T1.PASSWORD_POLICY_CID, T1.PASSWORD_LAST_CHANGE_DATE, T1.USER_DEPARTMENT, T1.CONSECUTIVE_FAILED_LOGINS, T1.CONSECUTIVE_FAILED_ANSWERS, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_USER  T1 WHERE T1.ARDAIS_USER_ID = ? AND T1.ARDAIS_ACCT_KEY = ?";
	private static final String _loadForUpdateString = _loadString + " FOR UPDATE";
	private byte[] serObj = null;
	/**
	 * EJSJDBCPersisterCMPArdaisuserBean_3fdc52da
	 */
	public EJSJDBCPersisterCMPArdaisuserBean_3fdc52da() throws java.rmi.RemoteException {
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
		ArdaisuserBean b = (ArdaisuserBean) eb;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_createString);
		try {
			if (b.ardais_user_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.ardais_user_id);
			}
			if (b.password == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.password);
			}
			if (b.user_verif_question == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.user_verif_question);
			}
			if (b.user_verif_answer == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.user_verif_answer);
			}
			if (b.user_lastname == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.user_lastname);
			}
			if (b.user_firstname == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.user_firstname);
			}
			if (b.user_title == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.user_title);
			}
			if (b.user_functional_title == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.user_functional_title);
			}
			if (b.user_affiliation == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.user_affiliation);
			}
			if (b.user_phone_number == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.user_phone_number);
			}
			if (b.user_phone_ext == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.user_phone_ext);
			}
			if (b.user_fax_number == null) {
				pstmt.setNull(13, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(13, b.user_fax_number);
			}
			if (b.user_mobile_number == null) {
				pstmt.setNull(14, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(14, b.user_mobile_number);
			}
			if (b.user_email_address == null) {
				pstmt.setNull(15, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(15, b.user_email_address);
			}
			if (b.created_by == null) {
				pstmt.setNull(16, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(16, b.created_by);
			}
			if (b.create_date == null) {
				pstmt.setNull(17, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(17, b.create_date);
			}
			if (b.update_date == null) {
				pstmt.setNull(18, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(18, b.update_date);
			}
			if (b.updated_by == null) {
				pstmt.setNull(19, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(19, b.updated_by);
			}
			if (b.user_address_id == null) {
				pstmt.setNull(20, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(20, b.user_address_id);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.user_active_ind);
			if (objectTemp == null) {
				pstmt.setNull(21, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(21, (java.lang.String)objectTemp);
			}
			if (b.passwordPolicyCid == null) {
				pstmt.setNull(22, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(22, b.passwordPolicyCid);
			}
			if (b.passwordLastChangeDate == null) {
				pstmt.setNull(23, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(23, b.passwordLastChangeDate);
			}
			if (b.user_department == null) {
				pstmt.setNull(24, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(24, b.user_department);
			}
			objectTemp = com.ibm.vap.converters.VapBigDecimalToIntegerConverter.singleton().dataFrom(b.consecutive_failed_logins);
			if (objectTemp == null) {
				pstmt.setNull(25, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(25, (java.math.BigDecimal)objectTemp);
			}
			objectTemp = com.ibm.vap.converters.VapBigDecimalToIntegerConverter.singleton().dataFrom(b.consecutive_failed_answers);
			if (objectTemp == null) {
				pstmt.setNull(26, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(26, (java.math.BigDecimal)objectTemp);
			}
			if (b.ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.ardaisaccount_ardais_acct_key);
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
		ArdaisuserBean b = (ArdaisuserBean) eb;
		com.ardais.bigr.es.beans.ArdaisuserKey _primaryKey = (com.ardais.bigr.es.beans.ArdaisuserKey)pKey;
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;
		b.ardais_user_id = _primaryKey.ardais_user_id;
		b.ardaisaccount_ardais_acct_key = _primaryKey.ardaisaccount_ardais_acct_key;
		b.password = resultSet.getString(2);
		b.user_verif_question = resultSet.getString(3);
		b.user_verif_answer = resultSet.getString(4);
		b.user_lastname = resultSet.getString(5);
		b.user_firstname = resultSet.getString(6);
		b.user_title = resultSet.getString(7);
		b.user_functional_title = resultSet.getString(8);
		b.user_affiliation = resultSet.getString(9);
		b.user_phone_number = resultSet.getString(10);
		b.user_phone_ext = resultSet.getString(11);
		b.user_fax_number = resultSet.getString(12);
		b.user_mobile_number = resultSet.getString(13);
		b.user_email_address = resultSet.getString(14);
		b.created_by = resultSet.getString(15);
		b.create_date = resultSet.getTimestamp(16);
		b.update_date = resultSet.getTimestamp(17);
		b.updated_by = resultSet.getString(18);
		b.user_address_id = resultSet.getBigDecimal(19);
		b.user_active_ind = (java.lang.String)com.ibm.vap.converters.VapTrimStringConverter.singleton().objectFrom(resultSet.getString(20));
		b.passwordPolicyCid = resultSet.getString(21);
		b.passwordLastChangeDate = resultSet.getTimestamp(22);
		b.user_department = resultSet.getString(23);
		b.consecutive_failed_logins = (java.lang.Integer)com.ibm.vap.converters.VapBigDecimalToIntegerConverter.singleton().objectFrom(resultSet.getBigDecimal(24));
		b.consecutive_failed_answers = (java.lang.Integer)com.ibm.vap.converters.VapBigDecimalToIntegerConverter.singleton().objectFrom(resultSet.getBigDecimal(25));
		b.ardaisaccount_ardais_acct_key = resultSet.getString(26);
	}
	/**
	 * load
	 */
	public void load(EntityBean eb, Object pKey, boolean forUpdate) throws Exception {
		Object objectTemp = null;
		ArdaisuserBean b = (ArdaisuserBean) eb;
		com.ardais.bigr.es.beans.ArdaisuserKey _primaryKey = (com.ardais.bigr.es.beans.ArdaisuserKey)pKey;
		PreparedStatement pstmt;
		ResultSet resultSet = null;
		pstmt = (forUpdate) ?
			getPreparedStatement(_loadForUpdateString):
			getPreparedStatement(_loadString);
		try {
			if (_primaryKey.ardais_user_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.ardais_user_id);
			}
			if (_primaryKey.ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardaisaccount_ardais_acct_key);
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
		ArdaisuserBean b = (ArdaisuserBean) eb;
		com.ardais.bigr.es.beans.ArdaisuserKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisuserKey();
		_primaryKey.ardais_user_id = b.ardais_user_id;
		_primaryKey.ardaisaccount_ardais_acct_key = b.ardaisaccount_ardais_acct_key;
		load(b, _primaryKey, forUpdate);
	}
	/**
	 * store
	 */
	public void store(EntityBean eb) throws Exception {
		Object objectTemp = null;
		ArdaisuserBean b = (ArdaisuserBean) eb;
		com.ardais.bigr.es.beans.ArdaisuserKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisuserKey();
		_primaryKey.ardais_user_id = b.ardais_user_id;
		_primaryKey.ardaisaccount_ardais_acct_key = b.ardaisaccount_ardais_acct_key;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_storeString);
		try {
			if (_primaryKey.ardais_user_id == null) {
				pstmt.setNull(25, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(25, _primaryKey.ardais_user_id);
			}
			if (_primaryKey.ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(26, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(26, _primaryKey.ardaisaccount_ardais_acct_key);
			}
			if (b.password == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, b.password);
			}
			if (b.user_verif_question == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, b.user_verif_question);
			}
			if (b.user_verif_answer == null) {
				pstmt.setNull(3, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(3, b.user_verif_answer);
			}
			if (b.user_lastname == null) {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, b.user_lastname);
			}
			if (b.user_firstname == null) {
				pstmt.setNull(5, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(5, b.user_firstname);
			}
			if (b.user_title == null) {
				pstmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(6, b.user_title);
			}
			if (b.user_functional_title == null) {
				pstmt.setNull(7, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(7, b.user_functional_title);
			}
			if (b.user_affiliation == null) {
				pstmt.setNull(8, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(8, b.user_affiliation);
			}
			if (b.user_phone_number == null) {
				pstmt.setNull(9, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(9, b.user_phone_number);
			}
			if (b.user_phone_ext == null) {
				pstmt.setNull(10, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(10, b.user_phone_ext);
			}
			if (b.user_fax_number == null) {
				pstmt.setNull(11, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(11, b.user_fax_number);
			}
			if (b.user_mobile_number == null) {
				pstmt.setNull(12, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(12, b.user_mobile_number);
			}
			if (b.user_email_address == null) {
				pstmt.setNull(13, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(13, b.user_email_address);
			}
			if (b.created_by == null) {
				pstmt.setNull(14, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(14, b.created_by);
			}
			if (b.create_date == null) {
				pstmt.setNull(15, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(15, b.create_date);
			}
			if (b.update_date == null) {
				pstmt.setNull(16, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(16, b.update_date);
			}
			if (b.updated_by == null) {
				pstmt.setNull(17, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(17, b.updated_by);
			}
			if (b.user_address_id == null) {
				pstmt.setNull(18, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(18, b.user_address_id);
			}
			objectTemp = com.ibm.vap.converters.VapTrimStringConverter.singleton().dataFrom(b.user_active_ind);
			if (objectTemp == null) {
				pstmt.setNull(19, java.sql.Types.CHAR);
			}
			else {
				pstmt.setString(19, (java.lang.String)objectTemp);
			}
			if (b.passwordPolicyCid == null) {
				pstmt.setNull(20, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(20, b.passwordPolicyCid);
			}
			if (b.passwordLastChangeDate == null) {
				pstmt.setNull(21, java.sql.Types.TIMESTAMP);
			}
			else {
				pstmt.setTimestamp(21, b.passwordLastChangeDate);
			}
			if (b.user_department == null) {
				pstmt.setNull(22, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(22, b.user_department);
			}
			objectTemp = com.ibm.vap.converters.VapBigDecimalToIntegerConverter.singleton().dataFrom(b.consecutive_failed_logins);
			if (objectTemp == null) {
				pstmt.setNull(23, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(23, (java.math.BigDecimal)objectTemp);
			}
			objectTemp = com.ibm.vap.converters.VapBigDecimalToIntegerConverter.singleton().dataFrom(b.consecutive_failed_answers);
			if (objectTemp == null) {
				pstmt.setNull(24, java.sql.Types.DECIMAL);
			}
			else {
				pstmt.setBigDecimal(24, (java.math.BigDecimal)objectTemp);
			}
			if (b.ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(26, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(26, b.ardaisaccount_ardais_acct_key);
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
		ArdaisuserBean b = (ArdaisuserBean) eb;
		com.ardais.bigr.es.beans.ArdaisuserKey _primaryKey = new com.ardais.bigr.es.beans.ArdaisuserKey();
		_primaryKey.ardais_user_id = b.ardais_user_id;
		_primaryKey.ardaisaccount_ardais_acct_key = b.ardaisaccount_ardais_acct_key;
		PreparedStatement pstmt;
		pstmt = getPreparedStatement(_removeString);
		try {
			if (_primaryKey.ardais_user_id == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, _primaryKey.ardais_user_id);
			}
			if (_primaryKey.ardaisaccount_ardais_acct_key == null) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(2, _primaryKey.ardaisaccount_ardais_acct_key);
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
		com.ardais.bigr.es.beans.ArdaisuserKey key = new com.ardais.bigr.es.beans.ArdaisuserKey();
		java.sql.ResultSet resultSet = (java.sql.ResultSet) data;

		if (resultSet != null) {
			Object objectTemp = null;
			key.ardais_user_id = resultSet.getString(1);
			key.ardaisaccount_ardais_acct_key = resultSet.getString(26);
			return key;
		}
return null;
	}
	/**
	 * findByUserId
	 */
	public com.ardais.bigr.es.beans.Ardaisuser findByUserId(java.lang.String userId) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		com.ardais.bigr.es.beans.Ardaisuser result = null;

		EJSJDBCFinder tmpFinder = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" select  q1.\"ARDAIS_USER_ID\",  q1.\"PASSWORD\",  q1.\"USER_VERIF_QUESTION\",  q1.\"USER_VERIF_ANSWER\",  q1.\"USER_LASTNAME\",  q1.\"USER_FIRSTNAME\",  q1.\"USER_TITLE\",  q1.\"USER_FUNCTIONAL_TITLE\",  q1.\"USER_AFFILIATION\",  q1.\"USER_PHONE_NUMBER\",  q1.\"USER_PHONE_EXT\",  q1.\"USER_FAX_NUMBER\",  q1.\"USER_MOBILE_NUMBER\",  q1.\"USER_EMAIL_ADDRESS\",  q1.\"CREATED_BY\",  q1.\"CREATE_DATE\",  q1.\"UPDATE_DATE\",  q1.\"UPDATED_BY\",  q1.\"USER_ADDRESS_ID\",  q1.\"USER_ACTIVE_IND\",  q1.\"PASSWORD_POLICY_CID\",  q1.\"PASSWORD_LAST_CHANGE_DATE\",  q1.\"USER_DEPARTMENT\",  q1.\"CONSECUTIVE_FAILED_LOGINS\",  q1.\"CONSECUTIVE_FAILED_ANSWERS\",  q1.\"ARDAIS_ACCT_KEY\" from ES_ARDAIS_USER q1 where  ( q1.\"ARDAIS_USER_ID\" = ?) ");
			Object convObj = null;
			if (userId == null) {
			   pstmt.setNull(1, java.sql.Types.VARCHAR);
			} else {
			   pstmt.setString(1, userId);
			}
			resultSet = pstmt.executeQuery();
			tmpFinder = new EJSJDBCFinder(resultSet, this, pstmt);
			if (tmpFinder.hasMoreElements()) {
				result = (com.ardais.bigr.es.beans.Ardaisuser)tmpFinder.nextElement();
			}
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
		finally {
			if ( tmpFinder != null ) tmpFinder.close();
		}
		if (result == null) {
			throw new javax.ejb.ObjectNotFoundException();
		}
		return result;
	}
	/**
	 * findByPrimaryKey
	 */
	public com.ardais.bigr.es.beans.Ardaisuser findByPrimaryKey(com.ardais.bigr.es.beans.ArdaisuserKey primaryKey) throws java.rmi.RemoteException, javax.ejb.FinderException {
		return (com.ardais.bigr.es.beans.Ardaisuser) home.activateBean(primaryKey);
	}
	/**
	 * findArdaisuserByArdaisaccount
	 */
	public EJSFinder findArdaisuserByArdaisaccount(com.ardais.bigr.es.beans.ArdaisaccountKey inKey) throws javax.ejb.FinderException, java.rmi.RemoteException {
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		try {
			preFind();
			pstmt = getPreparedStatement(" SELECT T1.ARDAIS_USER_ID, T1.PASSWORD, T1.USER_VERIF_QUESTION, T1.USER_VERIF_ANSWER, T1.USER_LASTNAME, T1.USER_FIRSTNAME, T1.USER_TITLE, T1.USER_FUNCTIONAL_TITLE, T1.USER_AFFILIATION, T1.USER_PHONE_NUMBER, T1.USER_PHONE_EXT, T1.USER_FAX_NUMBER, T1.USER_MOBILE_NUMBER, T1.USER_EMAIL_ADDRESS, T1.CREATED_BY, T1.CREATE_DATE, T1.UPDATE_DATE, T1.UPDATED_BY, T1.USER_ADDRESS_ID, T1.USER_ACTIVE_IND, T1.PASSWORD_POLICY_CID, T1.PASSWORD_LAST_CHANGE_DATE, T1.USER_DEPARTMENT, T1.CONSECUTIVE_FAILED_LOGINS, T1.CONSECUTIVE_FAILED_ANSWERS, T1.ARDAIS_ACCT_KEY FROM ES_ARDAIS_USER  T1 WHERE T1.ARDAIS_ACCT_KEY = ?");
			Object objectTemp = null;
boolean nullData;
			if (inKey.ardais_acct_key == null) {
				pstmt.setNull(1, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(1, inKey.ardais_acct_key);
			}
			resultSet = pstmt.executeQuery();
			return new EJSJDBCFinder(resultSet, this, pstmt);
		}
		catch (Exception ex) {
			throw new EJSPersistenceException("find failed:", ex);
		}
	}
}
