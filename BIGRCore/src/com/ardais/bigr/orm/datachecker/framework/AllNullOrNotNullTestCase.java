package com.ardais.bigr.orm.datachecker.framework;

import java.util.*;
import junit.framework.*;
import java.sql.*;
import com.ardais.bigr.api.*;

/**
 * This test type corresponds to the <code>&lt;AllNullOrNotNull&gt;</code>
 * XML tag in an XML data tests description.
 */
public class AllNullOrNotNullTestCase extends DataRulesTestCase {
	private String _tableName = null;
	private Set _columnNames = null;
	private String _when = null;
/**
 * Create a AllNullOrNotNullTestCase.
 *
 * @param name the test case name
 * @param tableName the database table involved in the test case
 * @param columnNames the set of database column involved in the test case
 * @param when additional SQL conditions
 */
public AllNullOrNotNullTestCase(String name, String tableName, Set columnNames, String when) {
	super(name);
	
	_tableName = tableName;
	_columnNames = columnNames;
	_when = when;

	if (_tableName == null || _tableName.length() == 0) {
		throw new IllegalStateException(
			"A table name must be supplied.");
	}
	
	if (_columnNames == null || _columnNames.size() == 0) {
		throw new IllegalStateException(
			"A set of column names must be supplied.");
	}

	// If _when is empty or all whitespace, set _when to null.
	//
	if (_when != null) {
		if (_when.trim().length() == 0) _when = null;
	}
}
/**
 * Run an AllNullOrNotNull test case.  This verifies that for every
 * row in the specified table, the specified set of columns is either all
 * null at the same time or all non-null at the same time.
 */
public void runTest() throws Exception {
	// Build the query.  An example of the query we construct here is:
	//
	// select count(1) from ILTDS_INFORMED_CONSENT
	// where
	//     ((CONSENT_ID is not null) or
	//      (CONSENT_RELEASE_STAFF_ID is not null) or
	//      (CONSENT_RELEASE_DATETIME is not null))
	// AND ((CONSENT_ID is null) or
	//      (CONSENT_RELEASE_STAFF_ID is null) or
	//      (CONSENT_RELEASE_DATETIME is null)) 

	StringBuffer queryBuffer = new StringBuffer(1024);

	queryBuffer.append("select count(1) from ");
	queryBuffer.append(_tableName);
	queryBuffer.append("\nwhere (");

	// Append the "is not null" pieces
	{
		Iterator iter = _columnNames.iterator();
		boolean firstTime = true;

		while (iter.hasNext()) {
			String columnName = (String) iter.next();

			queryBuffer.append("\n    ");
			
			if (firstTime) {
				firstTime = false;
			}
			else {
				queryBuffer.append("or ");
			}

			queryBuffer.append('(');
			queryBuffer.append(columnName);
			queryBuffer.append(" is not null)");
		}
	}
	
	queryBuffer.append("\n  ) and (");

	// Append the "is null" pieces
	{
		Iterator iter = _columnNames.iterator();
		boolean firstTime = true;

		while (iter.hasNext()) {
			String columnName = (String) iter.next();

			queryBuffer.append("\n    ");

			if (firstTime) {
				firstTime = false;
			}
			else {
				queryBuffer.append("or ");
			}

			queryBuffer.append('(');
			queryBuffer.append(columnName);
			queryBuffer.append(" is null)");
		}
	}
	
	queryBuffer.append(")");

	if (_when != null) {
		queryBuffer.append("\n  and (");
		queryBuffer.append(_when);
		queryBuffer.append(')');
	}

	_queryInfo = queryBuffer.toString();
		
	int resCount = getQueryCountValue(_queryInfo);

	assertEquals("Number of violations:", 0, resCount);
}
}
