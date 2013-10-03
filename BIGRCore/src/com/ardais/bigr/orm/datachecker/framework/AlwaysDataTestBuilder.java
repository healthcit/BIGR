package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test case for the &lt;always&gt; XML tag.
 */
public class AlwaysDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public AlwaysDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) {
	Element elem = (Element) domNode;

	String id = elem.getAttribute("id");

	String tableName = context.getTableName();

	String condition = getCoalescedText(domNode).trim();

	Test test = new AlwaysTestCase(id + " Always",
		tableName, condition);

	return test;
}
}
