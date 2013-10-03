package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import java.sql.*;
import com.ardais.bigr.api.*;

/**
 * This test type corresponds to the <code>&lt;NeverNull&gt;</code>
 * XML tag in an XML data tests description.
 */
public class NeverNullTestCase extends DataRulesTestCase {
	private String _tableName = null;
	private String _columnName = null;
	private String _when = null;
/**
 * Create a NeverNullTestCase.
 *
 * @param name the test case name
 * @param tableName the database table involved in the test case
 * @param columnName the database column involved in the test case
 * @param when additional SQL conditions
 */
public NeverNullTestCase(String name, String tableName, String columnName, String when) {
	super(name);
	
	_tableName = tableName;
	_columnName = columnName;
	_when = when;

	if (_tableName == null || _tableName.length() == 0) {
		throw new IllegalStateException(
			"A table name must be supplied.");
	}
	
	if (_columnName == null || _columnName.length() == 0) {
		throw new IllegalStateException(
			"A column name must be supplied.");
	}

	// If _when is empty or all whitespace, set _when to null.
	//
	if (_when != null) {
		if (_when.trim().length() == 0) _when = null;
	}
}
/**
 * Run a NeverNull test case.  This verifies that the specified column in
 * the specified table is never null.
 */
public void runTest() throws Exception {
	// Build the query.  The general structure of the query we build is:
	//
	// select count(1) from <tableName> where <columnName> is null [and (<when>)]
	//
	// A specific example of such a query is::
	//
	// select count(1) from ILTDS_INFORMED_CONSENT where CONSENT_ID is null

	StringBuffer queryBuffer = new StringBuffer(1024);

	queryBuffer.append("select count(1) from " + _tableName + " where " + _columnName + " is null");

	if (_when != null) {
		queryBuffer.append(" and (");
		queryBuffer.append(_when);
		queryBuffer.append(')');
	}

	_queryInfo = queryBuffer.toString();
		
	int resCount = getQueryCountValue(_queryInfo);

	assertEquals("Number of null values:", 0, resCount);
}
}
