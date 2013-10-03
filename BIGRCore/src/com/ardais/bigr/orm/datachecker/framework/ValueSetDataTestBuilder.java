package com.ardais.bigr.orm.datachecker.framework;

import java.util.*;
import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test case for the &lt;ValueSet&gt; XML tag.
 */
public class ValueSetDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public ValueSetDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) {
	Element elem = (Element) domNode;

	String id = elem.getAttribute("id");

	String tableName = context.getTableName();

	String columnName = null;
	Set valueSet = new HashSet();

	String when = null;

	// Loop through the child elements.  According to the DTD there
	// should be exactly one 'column' element followed by one or more
	// 'value' elements.
	// We do minimal checking on that here and assume that the validating
	// parser has done its job.
	//
	{
		NodeList children = domNode.getChildNodes();
		int childCount = children.getLength();

		for (int i = 0; i < childCount; i++) {
			Node childNode = children.item(i);

			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = childNode.getNodeName();
				
				if ((columnName == null) && nodeName.equals("column")) {
					columnName = getCoalescedText(childNode).trim();
				}
				else if (nodeName.equals("value")) {
					valueSet.add(getCoalescedText(childNode).trim());
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

	Test test = new ValueSetTestCase(id + " " + columnName + " ValueSet",
		tableName, columnName, valueSet, when);

	return test;
}
}
