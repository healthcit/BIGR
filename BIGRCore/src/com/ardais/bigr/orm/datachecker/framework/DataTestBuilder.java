package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import org.w3c.dom.*;

/**
 * A generic interface for classes that can build a data test case or test
 * suite from an XML document describing data tests.
 */
public interface DataTestBuilder {
/**
 * Build a test case or test suite corresponding to the test(s) described
 * by the supplied XML node.
 *
 * @param domNode the XML node to build tests from
 * @param context information about the node's context in the test description
 *    hierarchy
 *
 * @return the test case or test suite
 */
Test buildTest(Node domNode, DataTestBuilderContext context) throws Exception;
/**
 * Return the factory object that should be used to construct appropriate
 * builder instances for any child tests.  Each kind of XML tag that describes
 * a data test case has an associated builder class that knows how to construct
 * a test case or test suite for that tag.  The builder factory returns builder
 * instances appropriate for a give XML tag.
 *
 * @return the builder factory
 */
DataTestBuilderFactory getBuilderFactory();
}
