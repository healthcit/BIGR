package com.ardais.bigr.orm.datachecker.framework;

import com.ardais.bigr.api.*;
import java.util.*;
import java.lang.reflect.*;
import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test case for the &lt;ExtraTests&gt; XML tag.
 */
public class ExtraTestsDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public ExtraTestsDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) throws ApiException {
	Element elem = (Element) domNode;

	String id = elem.getAttribute("id");

	String className = elem.getAttribute("className").trim();

	TestSuite suite = new TestSuite(id + " ExtraTests");

	suite.addTest(getTest(className));

	return suite;
}
/**
 * Returns the Test corresponding to the given class name. If the specified
 * class has a static <code>suite()</code> method, it is called to get the test suite.
 * Otherwise it attempts to construct a test suite automatically by getting
 * all of the "test*" methods from the specified class.
 *
 * @param suiteClassName the class name for which to get a test suite
 * @return the test suite
 */
private Test getTest(String suiteClassName) throws ApiException {
	if (suiteClassName == null || suiteClassName.length() <= 0) {
		throw new IllegalArgumentException(
			"class name must not be null or empty");
	}

	try {
		Class testClass = Class.forName(suiteClassName);
	
		Method suiteMethod = null;
		try {
			suiteMethod = testClass.getMethod(junit.runner.BaseTestRunner.SUITE_METHODNAME, new Class[0]);
	 	} catch(Exception e) {
	 		// try to extract a test suite automatically
			return new TestSuite(testClass);
		}
 	
		return (Test)suiteMethod.invoke(null, new Class[0]); // static method
	}
	catch (Exception e) {
		throw new ApiException(
			"Failed to get tests for class " + suiteClassName, e);
	}
}
}
