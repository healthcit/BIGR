package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import java.sql.*;
import com.ardais.bigr.api.*;

/**
 * This is an abstract base class that makes it more convenient to implement
 * test cases classes that check data rules.  It extends JUnit's
 * {@link TestCase} class and provides default implementations of several
 * methods that should be suitable for many data rules test cases.  Specifically, it
 * <ul>
 * <li>Defines {@link #setUp() setUp} and {@link #tearDown() tearDown} methods that initialize
 *     a database connection variable ({@link #_con}) and close it, respectively.</li>
 * <li>Implements the {@link TestFailureDetails} interface to provide extra useful details
 *     regarding data rules tests, for example the query involved in the test.  Derived
 *     classes that want to take advantage of this default implementation should set
 *     the {@link #_queryInfo} member variable to the SQL string for the query.</li>
 * <li>Provides utility methods such as {@link #getQueryCountValue(String) getQueryCountValue}
 *     that may be useful in implementing derived classes.</li>
 * </ul>
 */
public abstract class DataRulesTestCase extends TestCase implements TestFailureDetails {
	/**
	 * A database connection obtained in <code>setUp</code> and closed in
	 * <code>tearDown</code>, for the convenience of subclasses.
	 */
	protected Connection _con = null;

	/**
	 * Derived classes may store a query string in here.  It is used by
	 * the default <code>getFailureDetailString</code> method to give the user
	 * details about what query was involved in a failed test.
	 */
	protected String _queryInfo = null;
/**
 * Create a data rules test case with the specified name.
 *
 * @param name the test case name
 */
public DataRulesTestCase(String name) {
	super(name);
}
/**
 * Returns a string that describes the details of a test failure.
 *
 * @param failure the <code>TestFailure</code> object describing the failure.
 *   The method requires that the <code>Test</code>
 *   instance associated with the failure is the same as the test instance that
 *   this method is being called on.
 *
 * @return the failure detail string
 */
public String getFailureDetailString(TestFailure failure) {
	final String DATACHECKER_FRAMEWORK_PACKAGE_PREFIX = "com.ardais.bigr.orm.datachecker.framework.";
	
	if (this != failure.failedTest()) {
		throw new IllegalArgumentException(
			"failure.failedTest() != this");
	}

	StringBuffer sb = new StringBuffer(1024);

	// For test cases that aren't defined in the datachecker framework
	// package, we also print the filtered stack trace of the exception.
	// We expect framework class instances to always have names that
	// uniquely identify them without needing a stack trace.  Other kinds
	// of tests may require a stack trace to identify exactly which test failed.
	//
	String failedTestClassName = getClass().getName();
	if (failedTestClassName.startsWith(DATACHECKER_FRAMEWORK_PACKAGE_PREFIX)) {
		sb.append(failure.thrownException().getMessage());
	}
	else {
		sb.append(junit.runner.BaseTestRunner.getFilteredTrace(failure.thrownException()));
	}

	if (_queryInfo != null && _queryInfo.length() != 0) {
		sb.append("\n\nQuery information:\n\n");
		sb.append(_queryInfo);
	}

	return sb.toString();
}
/**
 * Given an SQL query string for a query that returns a single count value,
 * execute the query and return the count value.  The supplied query must
 * have the form "select count(...) from ..." and must return exactly one
 * row.  We don't check that the query has the expected form, but we do check
 * that it returns exactly one row and throw an exception if it does not.
 * 
 * @param queryString the SQL query string to execute
 *
 * @return the count value returned by the specified query
 */
protected int getQueryCountValue(String queryString) throws ApiException, SQLException {
	PreparedStatement stmt = null;
	ResultSet res = null;

	try {
		stmt = _con.prepareStatement(queryString);

		res = ApiFunctions.queryDb(stmt, _con);

		if (! res.next()) {
			throw new ApiException(
				"Expected exactly one query result row, but there were no result rows.");
		}
		
		int resCount = res.getInt(1);

		if (res.next()) {
			throw new ApiException(
				"Expected exactly one query result row, but there was more than one result row.");
		}

		return resCount;
	}
	finally	{
		if (res != null) try { res.close(); } catch (Exception e) {};
		if (stmt != null) try { stmt.close(); } catch (Exception e) {};
	}
}
/**
 * Sets up the fixture, for example, open a database connection.
 * This method is called before a test is executed.
 *
 * This opens a database connection that can be used by test cases in
 * subclasses.
 */
protected void setUp() throws Exception {
	_con = ApiFunctions.getDbConnection();
	_queryInfo = null;
}
/**
 * Tears down the fixture, for example, close a database connection.
 * This method is called after a test is executed.
 *
 * This closes the database connection that was opened in <code>setUp</code>.
 */
protected void tearDown() throws Exception {
	if (_con != null) {
		ApiFunctions.closeDbConnection(_con);
		_con = null;
	}

	// We deliberately don't set _queryInfo back to null here since it
	// needs to get used by failure-reporting methods that get called
	// after tearDown has been called.
}
/**
 * Returns a string representation of the test case.  The overrides
 * the JUnit default <code>toString</code> method, which includes both the
 * test name and the class name in the result.  For data rules test cases
 * the class name isn't very informative, since we instantiate test
 * case classes multiple times to represent different test cases.
 * So we just use the name -- please make sure to give each class
 * instance a meaningful name.  If the name is null, we do use the
 * class name as the result value.
 */
public String toString() {
	String name = getName();
	
	if (name != null) {
		return name;
	}
	else {
		return "("+getClass().getName()+")";
	}
}
}
