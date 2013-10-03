package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import java.sql.*;
import java.util.*;
import com.ardais.bigr.api.*;

/**
 * This test type corresponds to the <code>&lt;ForeignKey&gt;</code>
 * XML tag in an XML data tests description.
 */
public class ForeignKeyTestCase extends DataRulesTestCase {
	private String _tableName = null;
	private List _columnList = null;
	private String _foreignTableName = null;
	private List _foreignColumnList = null;
	private String _when = null;
	private String _foreignWhen = null;
/**
 * Create a ForeignKeyTestCase.
 *
 * The test may have optional additional constraints on what local rows
 * to test and what foreign rows must match.  These additional conditions
 * are specified in the <code>when</code> and <code>foreignWhen</code>
 * parameters supplied to the test constructor.  The values of these parameters
 * are SQL condition strings.  In these strings, the table aliases
 * <code>local</code> and <code>foreign</code> should be used to qualify
 * all column names from the local and foreign tables, respectively -- this
 * will avoid any possible ambiguities in the query that is constructed to
 * run the test.
 *
 * @param name the test case name
 * @param tableName the database table involved in the test case
 * @param columnList the list of database columns involved in the test case
 * @param tableName the foreign database table involved in the test case
 * @param foreignColumnList the list of foreign database columns involved in the test case
 * @param when optional additional SQL constraints on what local rows to test
 * @param when optional additional SQL constraints on what foreign rows must match
 */
public ForeignKeyTestCase(String name, String tableName, List columnList,
						  String foreignTableName, List foreignColumnList,
						  String when, String foreignWhen) {
	super(name);
	
	_tableName = tableName;
	_columnList = columnList;
	_foreignTableName = foreignTableName;
	_foreignColumnList = foreignColumnList;
	_when = when;
	_foreignWhen = foreignWhen;

	if (_tableName == null || _tableName.length() == 0) {
		throw new IllegalStateException(
			"A table name must be supplied.");
	}

	if (_foreignTableName == null || _foreignTableName.length() == 0) {
		throw new IllegalStateException(
			"A foreign table name must be supplied.");
	}

	if (_columnList == null || _columnList.size() == 0) {
		throw new IllegalStateException(
			"A non-empty list of column names must be supplied.");
	}

	if (_foreignColumnList == null || _foreignColumnList.size() == 0) {
		throw new IllegalStateException(
			"A non-empty list of foreign column names must be supplied.");
	}

	if (_columnList.size() != _foreignColumnList.size()) {
		throw new IllegalStateException(
			"The number of columns and foreign columns must be the same.");
	}

	// If _when is empty or all whitespace, set _when to null.
	//
	if (_when != null) {
		if (_when.trim().length() == 0) _when = null;
	}

	// If _foreignWhen is empty or all whitespace, set _foreignWhen to null.
	//
	if (_foreignWhen != null) {
		if (_foreignWhen.trim().length() == 0) _foreignWhen = null;
	}
}
/**
 * Run a ForeignKey test.  This verifies that every compound key formed
 * from the specified column list has a corresponding row in the
 * foreign table matching the compound key formed from the foreign column
 * list.  If a row has one or more null values in the compound key, there
 * does not have to be a matching foreign row.
 *
 * The test may have optional additional constraints on what local rows
 * to test and what foreign rows must match.  These additional conditions
 * are specified in the <code>when</code> and <code>foreignWhen</code>
 * parameters supplied to the test constructor.  The values of these parameters
 * are SQL condition strings.  In these strings, the table aliases
 * <code>local</code> and <code>foreign</code> should be used to qualify
 * all column names from the local and foreign tables, respectively -- this
 * will avoid any possible ambiguities in the query that is constructed to
 * run the test.
 */
public void runTest() throws Exception {
	// Build the query.  The general structure of the query we build is:
	//
	// select count(1) from (
	//     select distinct local.<column1>, ..., local.<columnN>
	//     from <tableName> local
	//     where local.<column1> is not null
	//       and local.<column2> is not null
	//       ...
	//       and local.<columnN> is not null
	//       [and (<when>)]
	//   MINUS
	//     select local.<column1>, ..., local.<columnN>
	//     from <tableName> local, <foreignTableName> foreign
	//     where local.<column1> is not null
	//       and local.<column2> is not null
	//       ...
	//       and local.<columnN> is not null
	//       [and (<when>)]
	//       and local.<column1> = foreign.<foreignColumn1>
	//       and local.<column2> = foreign.<foreignColumn2>
	//       ...
	//       and local.<columnN> = foreign.<foreignColumnN>
	//       [and (<foreignWhen>)])
	//
	// A specific example of such a query is::
	//
	// select count(1) from (
	//     select distinct local.DIAGNOSIS_CONCEPT_ID
	//     from PDC_PATHOLOGY_REPORT local
	//     where local.DIAGNOSIS_CONCEPT_ID is not null
	//   MINUS
	//     select local.DIAGNOSIS_CONCEPT_ID
	//     from PDC_PATHOLOGY_REPORT local, PDC_DX_TC_HIERARCHY foreign
	//     where local.DIAGNOSIS_CONCEPT_ID is not null
	//       and local.DIAGNOSIS_CONCEPT_ID = foreign.OWNER_CODE
	//       and (foreign.TYPE_CODE = 'D'))
   

	StringBuffer queryBuffer = new StringBuffer(1024);

	queryBuffer.append("select count(1) from (\n    select distinct ");

	// Make the select-list string.
	//
	String selectList = null;
	{
		StringBuffer buf = new StringBuffer(128);

		Iterator iter = _columnList.iterator();
		boolean firstTime = true;

		while (iter.hasNext()) {
			String columnName = (String) iter.next();

			if (firstTime) {
				firstTime = false;
			}
			else {
				buf.append(", ");
			}

			buf.append("local.");
			buf.append(columnName);
		}

		selectList = buf.toString();
	}

	queryBuffer.append(selectList);
	queryBuffer.append("\n    from ");
	queryBuffer.append(_tableName);
	queryBuffer.append(" local\n    where ");
	
	// Make the local-conditions string.
	//
	String localConditions = null;
	{
		StringBuffer buf = new StringBuffer(128);

		Iterator iter = _columnList.iterator();
		boolean firstTime = true;

		while (iter.hasNext()) {
			String columnName = (String) iter.next();

			if (firstTime) {
				firstTime = false;
			}
			else {
				buf.append("\n      and ");
			}

			buf.append("local.");
			buf.append(columnName);
			buf.append(" is not null");
		}

		if (_when != null) {
			buf.append("\n      and (");
			buf.append(_when);
			buf.append(')');
		}

		localConditions = buf.toString();
	}

	queryBuffer.append(localConditions);
	queryBuffer.append("\n  MINUS\n    select ");
	queryBuffer.append(selectList);
	queryBuffer.append("\n    from ");
	queryBuffer.append(_tableName);
	queryBuffer.append(" local, ");
	queryBuffer.append(_foreignTableName);
	queryBuffer.append(" foreign\n    where ");
	queryBuffer.append(localConditions);
	queryBuffer.append("\n      and ");

	// Append the "local.<columnN> = foreign.<foreignColumnN>" conditions.
	//
	{
		Iterator iter = _columnList.iterator();
		Iterator foreignIter = _foreignColumnList.iterator();
		boolean firstTime = true;

		while (iter.hasNext()) {
			String columnName = (String) iter.next();
			String foreignColumnName = (String) foreignIter.next();

			if (firstTime) {
				firstTime = false;
			}
			else {
				queryBuffer.append("\n      and ");
			}

			queryBuffer.append("local.");
			queryBuffer.append(columnName);
			queryBuffer.append(" = ");
			queryBuffer.append("foreign.");
			queryBuffer.append(foreignColumnName);
		}
	}

	if (_foreignWhen != null) {
		queryBuffer.append("\n      and (");
		queryBuffer.append(_foreignWhen);
		queryBuffer.append(')');
	}

	queryBuffer.append(')');

	_queryInfo = queryBuffer.toString();

	int resCount = getQueryCountValue(_queryInfo);

	assertEquals("Number of distinct local keys without matching foreign keys:", 0, resCount);
}
}
