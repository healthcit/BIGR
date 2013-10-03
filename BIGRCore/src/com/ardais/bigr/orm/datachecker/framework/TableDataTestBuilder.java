package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test suite for the &lt;table&gt; XML tag.
 */
public class TableDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public TableDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) throws Exception {
	try {
		Element elem = (Element) domNode;

		String id = elem.getAttribute("id");
		context.setTableId(id);

		String tableName = elem.getAttribute("tableName").trim();
		context.setTableName(tableName);

		TestSuite suite = new TestSuite("Table " + id);

		addTestsFromChildren(suite, domNode, context);

		// TODO: Add implicit ExtraTests based on testPackage and table name.

		return suite;
	}
	finally {
		// Undo context settings that were only valid when within
		// the current XML tag.
		//
		context.setTableId(null);
		context.setTableName(null);
	}
}
}
