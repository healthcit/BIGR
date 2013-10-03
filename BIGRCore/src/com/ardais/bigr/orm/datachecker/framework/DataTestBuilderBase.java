package com.ardais.bigr.orm.datachecker.framework;

import junit.framework.*;
import org.w3c.dom.*;
import com.ardais.bigr.api.ApiFunctions;

/**
 * This is an abstract base class that makes it easier to create implementations
 * of the <code>DataTestBuilder</code> interface.  Derived classes must override the
 * <code>buildTest</code> method and provide a constructor that takes a
 * <code>DataTestBuilderFactory</code> as an argument.  Constructors in derived
 * classes should call the corresponding constructor on this class in addition
 * to anything else they do.
 */
public abstract class DataTestBuilderBase implements DataTestBuilder {
	/**
	 * {@link #buildSQLConditionFromRelationNode(Node) buildSQLConditionFromRelation} uses
	 * the value of this constant as a placeholder for the name of the column that the
	 * condition will apply to.
	 */
	public static final String COLUMN_NAME_PLACEHOLDER = "##dtestcol##";

	/**
	 * The <code>DataTestBuilderFactory</code> that the builder will use to construct
	 * appropriate builders for child nodes.
	 */
	private DataTestBuilderFactory _factory = null;
/**
 * Derived classes must call this constructor to properly initialize the base class.
 *
 * @param factory The builder factory that the builder will use to construct
 *     appropriate builders for child nodes.
 */
public DataTestBuilderBase(DataTestBuilderFactory factory) {
	_factory = factory;
}
/**
 * Build tests for each child of a specified XML node and add those tests to the
 * supplied test suite.  This is a utility method that may be used by derived classes.
 *
 * @param suite the test suite to add the child tests to
 * @param domNode the XML node
 * @param context the test builder context to use when creating the tests
 */
protected void addTestsFromChildren(TestSuite suite, Node domNode, DataTestBuilderContext context) throws Exception {
	NodeList children = domNode.getChildNodes();
	int childCount = children.getLength();

	DataTestBuilderFactory factory = getBuilderFactory();

	for (int i = 0; i < childCount; i++) {
		Node childNode = children.item(i);
		if (childNode.getNodeType() == Node.ELEMENT_NODE) {
			// Get a test builder for the XML tag for this child element and use
			// it to build the child tests.  Then add the child tests to the
			// test suite we're building.
			//
			DataTestBuilder builder = factory.getInstance(childNode.getNodeName());
			Test childTest = builder.buildTest(childNode, context);
	
			suite.addTest(childTest);
		}
	}
}
/**
 * Given an XML node representing a <code>&lt;relation&gt;</code> tag and its
 * contents, return an SQL condition that tests the relation.  The returned
 * string will contain {@link #COLUMN_NAME_PLACEHOLDER} in places where the
 * SQL condition requires the name of the column that the relation applies to.
 * The caller can replace this placeholder with the actual column name
 * using the
 * {@link #replaceColumnNamePlaceholder(String,String) replaceColumnNamePlaceholder} method.
 *
 * @param domNode the XML node
 *
 * @return the SQL condition
 */
protected static final String buildSQLConditionFromRelationNode(Node domNode) {
	// domNode is expected to be a 'relation' element.
	//
	// Loop through the child elements and take appropriate action based
	// on the specific child relation type.  There can be more than one child,
	// the interpretation in this case is that the individual relations are
	// ANDed together.

	if (domNode.getNodeType() != Node.ELEMENT_NODE
		|| ! domNode.getNodeName().equals("relation")) {
		throw new IllegalArgumentException("Unexpected tag '" + domNode.getNodeName() + "'");
	}

	NodeList children = domNode.getChildNodes();
	int childCount = children.getLength();

	boolean firstTime = true;
	StringBuffer result = new StringBuffer(256);

	result.append('(');

	for (int i = 0; i < childCount; i++) {
		Node childNode = children.item(i);

		if (childNode.getNodeType() == Node.ELEMENT_NODE) {
			String nodeName = childNode.getNodeName();
				
			if (nodeName.equals("equal")
				|| nodeName.equals("NotEqual")
				|| nodeName.equals("GreaterThan")
				|| nodeName.equals("GreaterThanOrEqual")
				|| nodeName.equals("LessThan")
				|| nodeName.equals("LessThanOrEqual")) {

				String sqlOperator = getSQLOperatorFromRelationConditionTagName(nodeName);
				String sqlOperand = getSQLOperandFromRelationConditionNode(childNode);

				if (firstTime) {
					firstTime = false;
				}
				else {
					result.append(" and ");
				}

				result.append('(');
				result.append(COLUMN_NAME_PLACEHOLDER);
				result.append(' ');
				result.append(sqlOperator);
				result.append(' ');
				result.append(sqlOperand);
				result.append(')');
			}
			else {
				throw new IllegalArgumentException(
					"Unexpected tag '" + nodeName + "'");
			}
		}
	}

	result.append(')');

	return result.toString();
}
public abstract Test buildTest(Node domNode, DataTestBuilderContext context) throws Exception;
public DataTestBuilderFactory getBuilderFactory() {
	return _factory;
}
/**
 * Given an XML node that has only text content, return the string represent
 * all of its text content, appending the text from multiple child nodes if
 * necessary.  The supplied XML node must either be <code>null</code> or
 * must have only text content as children.  In particular, every child node
 * must have one of the following node types:
 * {@link Node#TEXT_NODE}, {@link Node#CDATA_SECTION_NODE},
 * {@link Node#ENTITY_REFERENCE_NODE}, {@link Node#COMMENT_NODE}, or
 * {@link Node#PROCESSING_INSTRUCTION_NODE}.  In addition, each child of
 * any entity reference nodes must also satisfy these constraints.  Comments
 * and processing instructions are ignored.  A runtime exception occurs if
 * the supplied XML node doesn't satisfy these constraints.
 *
 * This is a utility method.
 *
 * @param domNode the XML node
 *
 * @return The coalesced text.  This may be an empty string but will
 *    never be <code>null</code>.
 */
public static final String getCoalescedText(Node domNode) {
	StringBuffer sb = new StringBuffer(512);

	if (domNode != null) {
		NodeList children = domNode.getChildNodes();
		int childCount = children.getLength();

		for (int i = 0; i < childCount; i++) {
			Node childNode = children.item(i);
			short nodeType = childNode.getNodeType();

			switch (nodeType) {
				case Node.TEXT_NODE:
				case Node.CDATA_SECTION_NODE:
					sb.append(childNode.getNodeValue());
					break;

				case Node.ENTITY_REFERENCE_NODE:
					sb.append(getCoalescedText(childNode));
					break;

				case Node.COMMENT_NODE:
				case Node.PROCESSING_INSTRUCTION_NODE:
					// Do nothing
					break;

				default:
					throw new IllegalArgumentException(
						"Encountered unexpected non-text node type.  Type code = " + nodeType);
			}
		}
	}

	return sb.toString();
}
/**
 * Given an XML node representing one of the children of a <code>&lt;relation&gt;</code>,
 * return the SQL representation of the relation's operand.  The node
 * is expected to be one of the relation subtype nodes: <code>equal</code>,
 * <code>NotEqual</code>, <code>GreaterThan</code>, <code>GreaterThanOrEqual</code>,
 * <code>LessThan</code>, or <code>LessThanOrEqual</code>.
 * This method doesn't check that that's true.
 *
 * @param domNode the relation subtype XML node
 *
 * @return the SQL representation of the relation's operand.
 */
private static final String getSQLOperandFromRelationConditionNode(Node domNode) {
	// Currently getting the operand is very simple.  For all of the current
	// operation types, the operand is simply the text content of the node.

	return getCoalescedText(domNode).trim();
}
/**
 * Given the XML tag name of one of the children of a <code>&lt;relation&gt;</code>,
 * return the SQL representation of the relation's operator.  The tag name
 * is expected to be one of the relation subtype tags: <code>equal</code>,
 * <code>NotEqual</code>, <code>GreaterThan</code>, <code>GreaterThanOrEqual</code>,
 * <code>LessThan</code>, or <code>LessThanOrEqual</code>.
 *
 * @param String the relation subtype's XML tag name.
 *
 * @return the SQL representation of the relation's operator.
 */
private static final String getSQLOperatorFromRelationConditionTagName(String tagName) {
	String operator = null;
	
	if (tagName.equals("equal")) {
		operator = "=";
	}
	else if (tagName.equals("NotEqual")) {
		operator = "<>";
	}
	else if (tagName.equals("GreaterThan")) {
		operator = ">";
	}
	else if (tagName.equals("GreaterThanOrEqual")) {
		operator = ">=";
	}
	else if (tagName.equals("LessThan")) {
		operator = "<";
	}
	else if (tagName.equals("LessThanOrEqual")) {
		operator = "<=";
	}
	else {
		throw new IllegalArgumentException("Unexpected tag '" + tagName + "'");
	}

	return operator;
}
/**
 * Replace the column name placeholder in a string that was returned by
 * {@link #buildSQLConditionFromRelationNode(Node) buildSQLConditionFromRelationNode}
 * with an actual column name.  This is a utility method.
 *
 * @param sqlString the SQL condition string
 * @param columnName the column name
 *
 * @return the modified SQL string
 */
public final static String replaceColumnNamePlaceholder(String sqlString, String columnName) {
	return ApiFunctions.replace(sqlString, DataTestBuilderBase.COLUMN_NAME_PLACEHOLDER, columnName);
}
}
