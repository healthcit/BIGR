package com.ardais.bigr.util;

import javax.xml.parsers.SAXParser;

import org.apache.commons.digester.Digester;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.util.digester.GsbDigester;

/**
 * This is a base class intended to make it easier to build classes that use the Jakarta Digester
 * for XML parsing. For example, it configures digester instances with a set of default properties
 * appropriate for BIGR, and implements parser error handling in a way that is appropriate for BIGR.
 */
public class BigrXMLParserBase implements ErrorHandler {

  /**
   * Constructor for BigrXMLParserBase.
   */
  public BigrXMLParserBase() {
    super();
  }

  public GsbDigester makeDigester() {
    GsbDigester digester = new GsbDigester();
    initializeDigester(digester, null);
    return digester;
  }

  public GsbDigester makeDigester(ErrorHandler errorHandler) {
    GsbDigester digester = new GsbDigester();
    initializeDigester(digester, errorHandler);
    return digester;
  }

  public GsbDigester makeDigester(SAXParser parser) {
    GsbDigester digester = new GsbDigester(parser);
    initializeDigester(digester, null);
    return digester;
  }

  public GsbDigester makeDigester(SAXParser parser, ErrorHandler errorHandler) {
    GsbDigester digester = new GsbDigester(parser);
    initializeDigester(digester, errorHandler);
    return digester;
  }
  
  /**
   * Initialize a new digester instance with default properties appropriate for BIGR. The following
   * configuration changes are made from the defaults that ship with the Digester distribution:
   * <ul>
   * <li>Set {@link Digester#setUseContextClassLoader(boolean) useContextClassLoader}to true to
   * use the current thread's context class loader, for example for loading classes referenced in
   * {@link org.apache.commons.digester.FactoryCreateRule}and
   * {@link org.apache.commons.digester.ObjectCreateRule}.</li>
   * <li>Set {@link Digester#setErrorHandler(ErrorHandler) errorHandler}to this instance. This
   * base class handles parse errors as follows: errors and fatal errors result in throwing runtime
   * exceptions, and warnings result in logging the warnings.</li>
   * <li>Set {@link Digester#setValidating(boolean) validation}to true so that the XML document
   * being parsed will be validate against its DTD, if it has one.</li>
   * </ul>
   */
  private void initializeDigester(GsbDigester digester, ErrorHandler errorHandler) {
    digester.setUseContextClassLoader(true);

    // Have this object handle any parsing errors that the digester encounters. If we
    // don't specify an ErrorHandler the digester just logs the parsing error but continues
    // processing, something we don't want to happen because who know what consequences
    // continuing might lead to?
    if (errorHandler == null) {
      digester.setErrorHandler(this);
    }
    else {
      digester.setErrorHandler(errorHandler);
    }

    digester.setValidating(true);
  }

  /**
   * @see org.xml.sax.ErrorHandler#warning(SAXParseException)
   */
  public void warning(SAXParseException exception) throws SAXException {
    ApiLogger.warn("XML parser warning", exception);
  }

  /**
   * @see org.xml.sax.ErrorHandler#error(SAXParseException)
   */
  public void error(SAXParseException exception) throws SAXException {
    ApiFunctions.throwAsRuntimeException(exception);
  }

  /**
   * @see org.xml.sax.ErrorHandler#fatalError(SAXParseException)
   */
  public void fatalError(SAXParseException exception) throws SAXException {
    ApiFunctions.throwAsRuntimeException(exception);
  }

}
