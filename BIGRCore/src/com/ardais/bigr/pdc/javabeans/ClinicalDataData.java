package com.ardais.bigr.pdc.javabeans;

import com.ardais.bigr.api.*;
import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * This class represents unstructured clinical data.
 */
public class ClinicalDataData implements Serializable {
	private String _ardaisId;
	private String _category;
	private String _clinicalData;
	private String _clinicalDataId;
	private String _consentId;
	private String _createUser;
	private String _lastUpdateUser;
/**
 * Creates a new <code>ClinicalDataData</code>.
 */
public ClinicalDataData() {
	super();
}
/**
 * Creates a new <code>ClinicalDataData</code>, initializing it from
 * the current row in the <code>ResultSet</code>.
 */
public ClinicalDataData(ResultSet rs) {
    this();
    try {
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        HashMap lookup = new HashMap();

        for (int i = 0; i < columnCount; i++) {
            lookup.put(meta.getColumnName(i + 1).toLowerCase(), null);
        }

        if (lookup.containsKey("ardais_id"))
            setArdaisId(rs.getString("ardais_id"));

        if (lookup.containsKey("category"))
            setCategory(rs.getString("category"));

        if (lookup.containsKey("clinical_data"))
            setClinicalData(ApiFunctions.getStringFromClob(rs.getClob("clinical_data")));

        if (lookup.containsKey("clinical_data_id"))
            setClinicalDataId(rs.getString("clinical_data_id"));

        if (lookup.containsKey("consent_id"))
            setConsentId(rs.getString("consent_id"));

        if (lookup.containsKey("create_user"))
            setCreateUser(rs.getString("create_user"));

        if (lookup.containsKey("last_update_user"))
            setLastUpdateUser(rs.getString("last_update_user"));

    } catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
    }
}
/**
 * Returns this clinical data's donor id.
 *
 * @return  The donor id.
 */
public String getArdaisId() {
	return _ardaisId;
}
/**
 * Returns this clinical data's category.
 *
 * @return  The category.
 */
public String getCategory() {
	return _category;
}
/**
 * Returns this clinical data's data.
 *
 * @return  The clinical data.
 */
public String getClinicalData() {
	return _clinicalData;
}
/**
 * Returns this clinical data's id.
 *
 * @return  The clinical data id.
 */
public String getClinicalDataId() {
	return _clinicalDataId;
}
/**
 * Returns this clinical data's consent id.
 *
 * @return  The consent id.
 */
public String getConsentId() {
	return _consentId;
}
/**
 * Returns this clinical data's created by user.
 *
 * @return  The created by user.
 */
public String getCreateUser() {
	return _createUser;
}
/**
 * Returns this clinical data's last updated by user.
 *
 * @return  The last updated by user.
 */
public String getLastUpdateUser() {
	return _lastUpdateUser;
}
/**
 * Sets this clinical data's donor id.
 *
 * @param  id  the donor id
 */
public void setArdaisId(String id) {
	_ardaisId = id;
}
/**
 * Sets this clinical data's category.
 *
 * @param  category  the category
 */
public void setCategory(String category) {
	_category = category;
}
/**
 * Sets this clinical data's data.
 *
 * @param  data  the clinical data
 */
public void setClinicalData(String data) {
	_clinicalData = data;
}
/**
 * Sets this clinical data's id.
 *
 * @param  id  the clinical data id
 */
public void setClinicalDataId(String id) {
	_clinicalDataId = id;
}
/**
 * Sets this clinical data's consent id.
 *
 * @param  id  the consent id
 */
public void setConsentId(String id) {
	_consentId = id;
}
/**
 * Sets this clinical data's created by user.
 *
 * @param  user  the created by user
 */
public void setCreateUser(String user) {
	_createUser = user;
}
/**
 * Sets this clinical data's last updated by user.
 *
 * @param  user  the last updated by user
 */
public void setLastUpdateUser(String user) {
	_lastUpdateUser = user;
}
}
