package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import java.sql.*;
import com.ardais.bigr.api.*;

/**
 * This test type corresponds to the <code>&lt;never&gt;</code>
 * XML tag in an XML data tests description.
 */
public class NeverTestCase extends DataRulesTestCase {
	private String _tableName = null;
	private String _condition = null;
/**
 * Create a NeverTestCase.
 *
 * @param name the test case name
 * @param tableName the database table involved in the test case
 * @param condition the SQL condition that must never be true in the specified table
 */
public NeverTestCase(String name, String tableName, String condition) {
	super(name);
	
	_tableName = tableName;
	_condition = condition;

	if (_tableName == null || _tableName.length() == 0) {
		throw new IllegalStateException(
			"A table name must be supplied.");
	}
	
	if (_condition == null || _condition.length() == 0) {
		throw new IllegalStateException(
			"A condition must be supplied.");
	}
}
/**
 * Run a 'never' test case.  This checks that the specified condition isn't
 * true of any rows in the specified table.
 */
public void runTest() throws Exception {
	// Build the query.  The general structure of the query we build is:
	//
	// select count(1) from <tableName> where <condition>
	//
	// An specific example of such a query is::
	//
	// select count(1) from ILTDS_INFORMED_CONSENT
	// where CONSENT_PULL_DATETIME <= FORM_ENTRY_DATETIME

	_queryInfo =
		"select count(1) from " + _tableName + "\nwhere " + _condition.trim();
		
	int resCount = getQueryCountValue(_queryInfo);

	assertEquals("Number of violations:", 0, resCount);
}
}
