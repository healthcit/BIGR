package com.ardais.bigr.orm.datachecker.framework;

import java.util.*;
import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test case for the &lt;AllNullOrNotNull&gt; XML tag.
 */
public class AllNullOrNotNullDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public AllNullOrNotNullDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) {
	Element elem = (Element) domNode;

	String id = elem.getAttribute("id");

	String tableName = context.getTableName();

	Set columnNames = new HashSet();

	String when = null;
		
	// Loop through the child elements, which should all be "column" tags
	// according to the DTD.  Build a list of the column names.
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

	Test test = new AllNullOrNotNullTestCase(id + " AllNullOrNotNull",
		tableName, columnNames, when);

	return test;
}
}
