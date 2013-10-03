package com.ardais.bigr.orm.datachecker.framework;

import java.util.*;
import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test case for the &lt;QueryResultRelation&gt; XML tag.
 */
public class QueryResultRelationDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public QueryResultRelationDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) {
	Element elem = (Element) domNode;

	String id = elem.getAttribute("id");

	String relationSQLCondition = null;
	String resultColumnName = null;
	String queryString = null;
		
	// Loop through the child elements.  According to the DTD there
	// should be exactly one 'relation' element followed by exactly
	// one 'column' element followed by exactly one 'query' element.
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
				
				if ((relationSQLCondition == null) && nodeName.equals("relation")) {
					relationSQLCondition = buildSQLConditionFromRelationNode(childNode);
				}
				else if ((resultColumnName == null) && nodeName.equals("column")) {
					resultColumnName = getCoalescedText(childNode).trim();
				}
				else if ((queryString == null) && nodeName.equals("query")) {
					queryString = getCoalescedText(childNode).trim();
				}
				else {
					throw new IllegalArgumentException(
						"Unexpected tag '" + nodeName + "'");
				}
			}
		}
	}

	Test test = new QueryResultRelationTestCase(id + " QueryResultRelation",
		relationSQLCondition, resultColumnName, queryString);

	return test;
}
}
