package com.ardais.bigr.orm.datachecker.framework;

import java.util.*;
import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test case for the &lt;NeverNull&gt; XML tag.
 */
public class NeverNullDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public NeverNullDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) {
	Element elem = (Element) domNode;

	String id = elem.getAttribute("id");

	String tableName = context.getTableName();

	List columnNames = new ArrayList();

	String when = null;

	// A NeverNull tag expands into one test case for each column name listed
	// within it.  So we need to create a suite to contain these tests.
	//
	TestSuite suite = new TestSuite(id + " NeverNull");

	// Loop through the child elements, which should all be "column" tags
	// according to the DTD.
	//
	{
		NodeList children = domNode.getChildNodes();
		int childCount = children.getLength();

		for (int i = 0; i < childCount; i++) {
			Node childNode = children.item(i);

			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = childNode.getNodeName();
				
				if (nodeName.equals("column")) {
					columnNames.add(getCoalescedText(childNode).trim());
				}
				else if (nodeName.equals("when")) {
					when = getCoalescedText(childNode).trim();
				}
				else {
					throw new IllegalArgumentException(
						"Unexpected tag '" + nodeName + "'");
				}
			}
		}
	}

	// Now loop through the column names and create the test cases.
	//
	{
		Iterator iter = columnNames.iterator();
		while (iter.hasNext()) {
			String columnName = (String) iter.next();
			
			Test test = new NeverNullTestCase(id + " " + columnName + " NeverNull",
				tableName, columnName, when);
		
			suite.addTest(test);
		}
	}

	return suite;
}
}
