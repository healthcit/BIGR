package com.ardais.bigr.orm.datachecker.framework;

import java.util.*;
import java.io.*;
import junit.framework.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import com.ardais.bigr.api.*;

/**
 * A data test suite containing the tests described in a Data Checker
 * XML test description document.
 */
public class XMLDataTestSuite extends TestSuite {
/**
 * Build a data test suite containing the tests described in a Data Checker
 * XML test description document.
 *
 * @param xmlDocumentURI the URI of the XML document that defines the test suite.
 *   This does not need to be an http URI.  For example, a filesystem location
 *   can be specified using a URI such as <code>file:///c:/DataChecker/datachecker.xml</code>.
 * @param nodesToProcess A set of ids of nodes within the specified XML document
 *   indicating which subset of the document to process.  If this parameter is <code>null</code>,
 *   all nodes will be processed.  Otherwise, a node will be ignored unless its id or
 *   the id of one of its parents is in the set.
 */
public XMLDataTestSuite(String xmlDocumentURI, Set nodesToProcess) throws Exception {
	super("XML Data Test Suite from " + xmlDocumentURI);

	// Parse the XML document and get its root element.
	//
	Document doc = parseXMLDocument(xmlDocumentURI);
	Node root = doc.getDocumentElement();

	// Validate that every item in nodesToProcess is indeed the id of some
	// node in the XML document.  This is done to catch typos in the node
	// names in nodesToProcess.
	//
	if (nodesToProcess != null) {
		Iterator iter = nodesToProcess.iterator();
		while (iter.hasNext()) {
			String id = (String) iter.next();
			if (doc.getElementById(id) == null) {
				throw new IllegalArgumentException(
					"Error in list of ids to process:  the XML document doesn't contain an element with id = " + id);
			}
		}
	}
	
	// Modify the parsed XML DOM tree by removing nodes that should
	// be ignored based on the list of node ids in nodesToProcess.
	// If nodesToProcess is null we don't ignore any nodes (if it is
	// not null but is an empty list, we ignore all nodes).
	// The original tree isn't destructively modified -- the result tree
	// is a copy.
	//
	root = filterTests(root, nodesToProcess);
	
	// Create a data test builder factory and use it to get a builder
	// for the XML tag for the root element.
	//
	DataTestBuilderFactory factory = DataTestBuilderFactoryImpl.getFactoryInstance();
	DataTestBuilder builder = factory.getInstance(root.getNodeName());
	
	// Use the builder we just obtained to build the data tests specified
	// in the filtered XML DOM tree (which could be empty or null after
	// filtering).  Pass a newly initialized builder context to the builder.
	//
	DataTestBuilderContext context = new DataTestBuilderContext();
	Test rootTest = builder.buildTest(root, context);
	
	// Add the test(s) returned by the builder to this test suite.
	//
	addTest(rootTest);
}
/**
 * Given a DOM tree, return a filtered DOM tree that contains element nodes
 * from the source tree only if the id of the node or one of its parents is
 * in the supplied set of node ids.  The original tree is not modified.
 *
 * @param domNode the source DOM tree
 * @param nodesToProcess A set of ids of nodes within the specified DOM tree
 *   indicating which subset of the document to include in the result.  If this
 *   parameter is <code>null</code>, <code>domNode</code> is returned as-is.
 *   Otherwise, a node will be excluded from the result unless its id or the id
 *   of one of its parents is in the set.
 *
 * @return the filtered XML DOM tree
 */
protected Node filterTests(Node domNode, Set nodesToProcess) {
	// nodesToProcess == null means process all nodes.  If, on the other
	// hand, nodesToProcess is non-null but has no elements, no nodes
	// are processed.
	//
	if (nodesToProcess == null) return domNode;

	if (domNode == null) {
		return null;
	}
	else if (domNode.getNodeType() != Node.ELEMENT_NODE) {
		// All test nodes are element nodes, so we can exclude all
		// non-element nodes we reach.
		
		return null;
	}
	else {
		Element elem = (Element) domNode;
		String id = elem.getAttribute("id");

		// Include this node and all of its children if its id is
		// in nodesToProcess.  Otherwise include the node only if it has
		// included children, and include only the children that should be
		// included.
		//
		if (nodesToProcess.contains(id)) {
			return domNode.cloneNode(true);
		}
		else {
			NodeList children = domNode.getChildNodes();
			int numChildren = children.getLength();
			List resultChildren = new ArrayList(numChildren);

			for (int i = 0; i < numChildren; i++) {
				Node childResult = filterTests(children.item(i), nodesToProcess);
				if (childResult != null) {
					resultChildren.add(childResult);
				}
			}

			if (resultChildren.size() == 0) {
				return null;
			}
			else {
				Node resultNode = domNode.cloneNode(false);

				Iterator iter = resultChildren.iterator();
				while (iter.hasNext()) {
					resultNode.appendChild((Node) iter.next());
				}
				
				return resultNode;
			}
		}
	}
}
/**
 * Parse the specified XML document, returning its DOM representation.
 * A validating parse is performed, ignorable whitespace it ignored,
 * and entity references are expanded.
 *
 * @param xmlDocumentURI the document location
 *
 * @return the document's XML DOM representation
 *
 * @throws ApiException when there is a parsing error
 */
protected Document parseXMLDocument(String xmlDocumentURI) throws ApiException {
	// Step 1: create a DocumentBuilderFactory and configure it
	//
	DocumentBuilderFactory dbf =
		DocumentBuilderFactory.newInstance();

	// Set namespaceAware to true to get a DOM Level 2 tree with nodes
	// containing namesapce information.  This is necessary because the
	// default value from JAXP 1.0 was defined to be false.
	//
	dbf.setNamespaceAware(true);

	// Set various configuration options
	//
	dbf.setValidating(true);
	dbf.setIgnoringElementContentWhitespace(true);
	dbf.setExpandEntityReferences(true);
	// Xerces 1.4.4 ignores the setIgnoreComments and setCoalescing calls,
	// which we'd like to have take effect.  We set them here anyways,
	// but have written the rest of the code to work correctly even if
	// comments aren't ignored and text/cdata nodes aren't coalesced.
	//
	dbf.setIgnoringComments(true);
	dbf.setCoalescing(true);

	// Step 2: create a DocumentBuilder that satisfies the constraints
	// specified by the DocumentBuilderFactory
	//
	DocumentBuilder db = null;
	try {
	    db = dbf.newDocumentBuilder();
	} catch (ParserConfigurationException pce) {
	    throw new ApiException(pce);
	}

	// Set an ErrorHandler before parsing
	//
	OutputStreamWriter errorWriter = new OutputStreamWriter(System.err);
	db.setErrorHandler(
	    new XMLErrorHandler(new PrintWriter(errorWriter, true)));

	// Step 3: parse the input
	//
	Document doc = null;
	try {
	    doc = db.parse(xmlDocumentURI);
	} catch (SAXException se) {
	    throw new ApiException(se);
	} catch (IOException ioe) {
	    throw new ApiException(ioe);
	}

	return doc;
}
}
