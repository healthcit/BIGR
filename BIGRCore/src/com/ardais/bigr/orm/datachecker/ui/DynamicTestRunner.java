package com.ardais.bigr.orm.datachecker.ui;

import junit.framework.*;

/**
 * A Junit test runner that implements this interface supports running
 * tests that are created dynamically at run time rather than being statically
 * compiled.
 */
public interface DynamicTestRunner {
/**
 * Get the factory instance to use to create a dynamic test instance.
 *
 * @return the test factory
 */
DynamicTestFactory getDynamicTestFactory();
/**
 * Set the factory instance to use to create a dynamic test instance.
 *
 * @param newValue the test factory
 */
void setDynamicTestFactory(DynamicTestFactory newValue);
}
