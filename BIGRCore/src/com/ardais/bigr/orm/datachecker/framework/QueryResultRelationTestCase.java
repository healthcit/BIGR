package com.ardais.bigr.orm.datachecker.framework;

import java.util.*;
import junit.framework.*;
import java.sql.*;
import com.ardais.bigr.api.*;

/**
 * This test type corresponds to the <code>&lt;QueryResultRelation&gt;</code>
 * XML tag in an XML data tests description.
 */
public class QueryResultRelationTestCase extends DataRulesTestCase {
	private String _relationSQLCondition = null;
	private String _resultColumnName = null;
	private String _queryString = null;
/**
 * Create a QueryResultRelationTestCase.
 *
 * @param name the test case name
 * @param relationSQLCondition the SQL condition that the specified result column
 *   must satisfy
 * @param resultColumnName the column name in the query result that must satisfy
 *   the specified condition
 * @param queryString the database query
 */
public QueryResultRelationTestCase(String name, String relationSQLCondition,
								   String resultColumnName, String queryString) {
	super(name);
	
	_relationSQLCondition = relationSQLCondition;
	_resultColumnName = resultColumnName;
	_queryString = queryString;

	if (_relationSQLCondition == null || _relationSQLCondition.length() == 0) {
		throw new IllegalStateException(
			"A relation must be supplied.");
	}

	if (_resultColumnName == null || _resultColumnName.length() == 0) {
		throw new IllegalStateException(
			"A query result column name must be supplied.");
	}

	if (_queryString == null || _queryString.length() == 0) {
		throw new IllegalStateException(
			"An SQL query must be supplied.");
	}
}
/**
 * Run a QueryResultRelation test case.  This tests that the specified
 * result column of the specified query satisfies the specified relation.
 */
public void runTest() throws Exception {
	// Build the query.  The query we construct both executes the query supplied
	// in the XML and evaluates the relation condition based on the query result,
	// all in a single Oracle query.  The query we construct returns a single row
	// with two columns.  The first column is the result value from the user-specified
	// result column of the user-specified query.  The second column has the value 1
	// if the result value passes the user-specified relation test, otherwise it
	// has the value 0.
	//
	// The general structure of the query we build is:
	//
	// select dtest_temp_.<resultColumnName> as result_value,
	//        (case when (<relationSQLCondition>) then 1 else 0 end) as passed_test
	// from
	//  (<queryString>) dtest_temp_
	//
	// A specific example of such a query is::
	//
	// select dtest_temp_.cnt as result_value,
	//        (case when (dtest_temp_.cnt = 0) then 1 else 0 end) as passed_test
	// from
	//  (select count(1) as cnt from ILTDS_INFORMED_CONSENT
	//   where LINKED = 'N'
	//     and (BANKABLE_IND <> 'Y' or BANKABLE_IND is null)) dtest_temp_

	StringBuffer queryBuffer = new StringBuffer(1024);

	queryBuffer.append("select dtest_temp_.");
	queryBuffer.append(_resultColumnName);
	queryBuffer.append(" as result_value,\n       (case when (");
	queryBuffer.append(
		DataTestBuilderBase.replaceColumnNamePlaceholder(
			_relationSQLCondition,
			"dtest_temp_." + _resultColumnName));
	queryBuffer.append(") then 1 else 0 end) as passed_test\nfrom\n(");
	queryBuffer.append(_queryString);
	queryBuffer.append(") dtest_temp_");

	_queryInfo = queryBuffer.toString();

	// Run the query and fetch its results
	//
	String resultValue = null;
	boolean passedTest = false;
	{
		PreparedStatement stmt = null;
		ResultSet res = null;

		try {
			stmt = _con.prepareStatement(_queryInfo);

			res = ApiFunctions.queryDb(stmt, _con);

			if (! res.next()) {
				throw new ApiException(
					"Expected exactly one query result row, but there were no result rows.");
			}
		
			resultValue = res.getString(1);
			passedTest = (res.getInt(2) == 1);

			if (res.next()) {
				throw new ApiException(
					"Expected exactly one query result row, but there was more than one result row.");
			}
		}
		finally	{
			if (res != null) res.close();
			if (stmt != null) stmt.close();
		}
	}
	
	assertTrue("Result value did not pass test relation: <" + resultValue + ">", passedTest);
}
}
