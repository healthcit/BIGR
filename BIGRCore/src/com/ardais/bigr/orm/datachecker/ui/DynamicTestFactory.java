package com.ardais.bigr.orm.datachecker.ui;

import junit.framework.*;

/**
 * This interface allows a {@link DynamicTestRunner} to fetch a dynamically constructed
 * test suite.
 */
public interface DynamicTestFactory {
/**
 * Return a test instance to be run by a {@link DynamicTestRunner}.
 *
 * @return the test instance to run
 */
Test getInstance() throws Exception;
}
