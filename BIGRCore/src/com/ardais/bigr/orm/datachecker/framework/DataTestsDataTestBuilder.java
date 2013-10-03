package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test suite for the &lt;DataTests&gt; XML tag.
 */
public class DataTestsDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public DataTestsDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) throws Exception {
	try {
		Element elem = (Element) domNode;

		String id = elem.getAttribute("id");
		context.setDataTestsId(id);

		TestSuite suite = new TestSuite("Data Tests " + id);

		addTestsFromChildren(suite, domNode, context);

		return suite;
	}
	finally {
		// Undo context settings that were only valid when within
		// the current XML tag.
		//
		context.setDataTestsId(null);
	}
}
}
