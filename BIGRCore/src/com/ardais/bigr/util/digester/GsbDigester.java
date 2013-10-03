package com.ardais.bigr.util.digester;

import javax.xml.parsers.SAXParser;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 * Provide a Digester implementation that is better suited to the HealthCare IT Corporation
 * products.
 * 
 * @see org.apache.commons.digester.Digester
 */
public class GsbDigester extends Digester {

  /**
   * Construct a new Digester with default properties.
   */
  public GsbDigester() {
    super();
  }

  /**
   * Construct a new Digester, allowing a SAXParser to be passed in. This allows Digester to be used
   * in environments which are unfriendly to JAXP1.1 (such as WebLogic 6.0). Thanks for the request
   * to change go to James House (james@interobjective.com). This may help in places where you are
   * able to load JAXP 1.1 classes yourself.
   */
  public GsbDigester(SAXParser parser) {
    super(parser);
  }

  /**
   * Construct a new Digester, allowing an XMLReader to be passed in. This allows Digester to be
   * used in environments which are unfriendly to JAXP1.1 (such as WebLogic 6.0). Note that if you
   * use this option you have to configure namespace and validation support yourself, as these
   * properties only affect the SAXParser and emtpy constructor.
   */
  public GsbDigester(XMLReader reader) {
    super(reader);
  }

  /**
   * Add a "set next if" rule for the specified parameters.
   * 
   * @param pattern Element matching pattern
   * @param expectedParentType Expected type of the parent object.
   * @param methodName Method name to call on the parent element
   * @see SetNextIfRule
   */
  public void addSetNextIf(String pattern, String expectedParentType, String methodName) {
    addRule(pattern, new SetNextIfRule(expectedParentType, methodName));
  }

  /**
   * Add a "set next if" rule for the specified parameters.
   * 
   * @param pattern Element matching pattern
   * @param expectedParentType Expected type of the parent object.
   * @param methodName Method name to call on the parent element
   * @param paramType Java class name of the expected parameter type (if you wish to use a primitive
   *          type, specify the corresonding Java wrapper class instead, such as
   *          <code>java.lang.Boolean</code> for a <code>boolean</code> parameter)
   * @see SetNextIfRule
   */
  public void addSetNextIf(String pattern, String expectedParentType, String methodName,
                           String paramType) {
    addRule(pattern, new SetNextIfRule(expectedParentType, methodName, paramType));
  }

  // MR 8659: The Digester class always logs parsing errors even if the caller specified
  // a custom ErrorHandler. We override Digester's implementations of the methods in the
  // org.xml.sax.ErrorHandler interface so that if the caller specified a customer error
  // handler, it is always simply used with no extra code called. Otherwise we delegate to
  // the standard Digester implementation.

  /**
   * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
   */
  public void error(SAXParseException exception) throws SAXException {
    if (errorHandler != null) {
      errorHandler.error(exception);
    }
    else {
      super.error(exception);
    }
  }

  /**
   * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
   */
  public void fatalError(SAXParseException exception) throws SAXException {
    if (errorHandler != null) {
      errorHandler.fatalError(exception);
    }
    else {
      super.fatalError(exception);
    }
  }

  /**
   * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
   */
  public void warning(SAXParseException exception) throws SAXException {
    if (errorHandler != null) {
      errorHandler.warning(exception);
    }
    else {
      super.warning(exception);
    }
  }
}
