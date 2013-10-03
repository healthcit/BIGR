package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import java.sql.*;
import java.util.*;
import com.ardais.bigr.api.*;

/**
 * This test type corresponds to the <code>&lt;ValueSet&gt;</code>
 * XML tag in an XML data tests description.
 */
public class ValueSetTestCase extends DataRulesTestCase {
	private String _tableName = null;
	private String _columnName = null;
	private Set _valueSet = null;
	private String _when = null;
/**
 * Create a ValueSetTestCase.
 *
 * @param name the test case name
 * @param tableName the database table involved in the test case
 * @param columnName the database column involved in the test case
 * @param valueSet the set of legal values for the column
 * @param when additional SQL conditions
 */
public ValueSetTestCase(String name, String tableName, String columnName, Set valueSet, String when) {
	super(name);

	_tableName = tableName;
	_columnName = columnName;
	_valueSet = valueSet;
	_when = when;

	if (_tableName == null || _tableName.length() == 0) {
		throw new IllegalStateException(
			"A table name must be supplied.");
	}

	if (_columnName == null || _columnName.length() == 0) {
		throw new IllegalStateException(
			"A column name must be supplied.");
	}

	if (_valueSet == null || _valueSet.size() == 0) {
		throw new IllegalStateException(
			"A non-empty set of values must be supplied.");
	}

	// If _when is empty or all whitespace, set _when to null.
	//
	if (_when != null) {
		if (_when.trim().length() == 0) _when = null;
	}
}
/*
 * Run a ValueSet test case.  This looks for rows where the specified column
 * has a value that is not in the specified set.  Null is considered an
 * illegal value unless it is explicitly listed in the set of legal values.
 */
public void runTest() throws Exception {
	// Build the query.  The general structure of the query we build is:
	//
	// select count(1) from <tableName>
	// where (<columnName> not in (<valueList>)
	//        [or <columnName> is null])
	//   [and (<when>)]
	//
	// A specific example of such a query is::
	//
	// select count(1) from iltds_informed_consent
	// where (bankable_ind not in ('Y','N'))

	StringBuffer queryBuffer = new StringBuffer(1024);

	queryBuffer.append("select count(1) from ");
	queryBuffer.append(_tableName);
	queryBuffer.append("\nwhere (");
	queryBuffer.append(_columnName);
	queryBuffer.append(" not in (");

	// Append the values in valueList.  We require that string values
	// already be appropriately quoted for Oracle, and don't attempt to
	// do any quoting here.  We do this because we don't know what the
	// data type of the column is -- it could be a non-string column.
	//
	// We handle null as a special case, since it has different semantics
	// in expression evaluation -- just including it in a SQL IN list
	// doesn't work.
	//
	boolean nullAllowed = false;
	{
		Iterator iter = _valueSet.iterator();
		boolean firstTime = true;

		while (iter.hasNext()) {
			String value = (String) iter.next();
			boolean valueIsNull = (value.equalsIgnoreCase("null"));

			if (valueIsNull) {
				nullAllowed = true;
			}
			else {
				if (firstTime) {
					firstTime = false;
				}
				else {
					queryBuffer.append(',');
				}

				queryBuffer.append(value);
			}
		}
	}
	
	queryBuffer.append(')');

	if (! nullAllowed) {
		queryBuffer.append("\n       or ");
		queryBuffer.append(_columnName);
		queryBuffer.append(" is null");
	}

	queryBuffer.append(')');

	if (_when != null) {
		queryBuffer.append("\n  and (");
		queryBuffer.append(_when);
		queryBuffer.append(')');
	}

	_queryInfo = queryBuffer.toString();

	int resCount = getQueryCountValue(_queryInfo);

	assertEquals("Number of values not in value set:", 0, resCount);
}
}
