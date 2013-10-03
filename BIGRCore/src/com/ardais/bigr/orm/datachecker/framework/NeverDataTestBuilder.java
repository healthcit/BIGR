package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test case for the &lt;never&gt; XML tag.
 */
public class NeverDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public NeverDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) {
	Element elem = (Element) domNode;

	String id = elem.getAttribute("id");

	String tableName = context.getTableName();

	String condition = getCoalescedText(domNode).trim();

	Test test = new NeverTestCase(id + " Never",
		tableName, condition);

	return test;
}
}
