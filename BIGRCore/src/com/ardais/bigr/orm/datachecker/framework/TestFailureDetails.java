package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;

/**
 * An interface for classes that can return details of a test failure.
 */
public interface TestFailureDetails {
/**
 * Returns a string that describes the details of a test failure.
 *
 * @param failure the <code>TestFailure</code> object describing the failure.
 *   When the class implementing this method is an instance of {@link Test},
 *   it may assume that the <code>Test</code> instance associated with the failure
 *   (the one returned by {@link TestFailure#failedTest() failure.failedTest()}) is
 *   the same as the test instance that this method is being called on.
 *
 * @return the failure detail string
 */
String getFailureDetailString(TestFailure failure);
}
