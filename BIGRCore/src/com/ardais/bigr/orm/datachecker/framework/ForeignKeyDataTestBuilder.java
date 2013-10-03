package com.ardais.bigr.orm.datachecker.framework;

import java.util.*;
import junit.framework.*;
import org.w3c.dom.*;

/**
 * Build a data test case for the &lt;ForeignKey&gt; XML tag.
 */
public class ForeignKeyDataTestBuilder extends DataTestBuilderBase {
/**
 * Create a data test builder that uses the specified
 * <code>DataTestBuilderFactory</code> to get builder objects for any child node.
 *
 * @param factory the builder factory
 */
public ForeignKeyDataTestBuilder(DataTestBuilderFactory factory) {
	super(factory);
}
public Test buildTest(Node domNode, DataTestBuilderContext context) {
	Element elem = (Element) domNode;

	String id = elem.getAttribute("id");

	String tableName = context.getTableName();

	String foreignTableName = null;
	List columnList = new ArrayList();
	List foreignColumnList = new ArrayList();

	String when = null;
	String foreignWhen = null;

	// Loop through the child elements.  According to the DTD there
	// should be one or more 'column' elements followed by exactly on
	// 'ForeignTable' element followed by one or more 'ForeignColumn' elements.
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
				
				if (nodeName.equals("column")) {
					columnList.add(getCoalescedText(childNode).trim());
				}
				else if ((foreignTableName == null) && nodeName.equals("ForeignTable")) {
					foreignTableName = getCoalescedText(childNode).trim();
				}
				else if (nodeName.equals("ForeignColumn")) {
					foreignColumnList.add(getCoalescedText(childNode).trim());
				}
				else if (nodeName.equals("when")) {
					when = getCoalescedText(childNode).trim();
				}
				else if (nodeName.equals("ForeignWhen")) {
					foreignWhen = getCoalescedText(childNode).trim();
				}
				else {
					throw new IllegalArgumentException(
						"Unexpected tag '" + nodeName + "'");
				}
			}
		}
	}

	Test test = new ForeignKeyTestCase(id + " ForeignKey",
		tableName, columnList, foreignTableName, foreignColumnList, when, foreignWhen);

	return test;
}
}
