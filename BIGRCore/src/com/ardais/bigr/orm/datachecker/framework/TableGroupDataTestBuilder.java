package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test suite for the &lt;TableGroup&gt; XML tag.
 */
public class TableGroupDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public TableGroupDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) throws Exception {
	try {
		Element elem = (Element) domNode;

		String id = elem.getAttribute("id");
		context.setTableGroupId(id);

		TestSuite suite = new TestSuite("Table Group " + id);

		addTestsFromChildren(suite, domNode, context);

		return suite;
	}
	finally {
		// Undo context settings that were only valid when within
		// the current XML tag.
		//
		context.setTableGroupId(null);
	}
}
}
