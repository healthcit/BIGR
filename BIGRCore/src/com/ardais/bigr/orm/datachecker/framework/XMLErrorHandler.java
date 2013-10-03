package com.ardais.bigr.orm.datachecker.framework;

import java.io.*;
import org.xml.sax.*;

/**
 * An error handler that can be registered with an XML parser to
 * report parsing errors and warnings.  This class handles errors
 * by throwing a {@link SAXException} containing the error message,
 * and it handles warnings by printing them to the <code>PrintWriter</code>
 * that was supplied to the constructor.
 */
public class XMLErrorHandler implements ErrorHandler {
	/**
	 * Error handler output goes here (specifically, only warnings
	 * are written here, not errors).
	 */
	private PrintWriter _out = null;
/**
 * Create an error handler that will write its output to the supplied writer.
 * Only warnings will be written to the writer, not errors.  Errors are thrown
 * as exceptions.
 */
public XMLErrorHandler(PrintWriter out) {
	_out = out;
}
/**
 * Receive notification of a recoverable error.
 * A <code>SAXException</code> containing the error descritpion will
 * be thrown.
 *
 * <p>This corresponds to the definition of "error" in section 1.2
 * of the W3C XML 1.0 Recommendation.  For example, a validating
 * parser would use this callback to report the violation of a
 * validity constraint.  The default behaviour is to take no
 * action.</p>
 *
 * <p>The SAX parser must continue to provide normal parsing events
 * after invoking this method: it should still be possible for the
 * application to process the document through to the end.  If the
 * application cannot do so, then the parser should report a fatal
 * error even if the XML 1.0 recommendation does not require it to
 * do so.</p>
 *
 * <p>Filters may use this method to report other, non-XML errors
 * as well.</p>
 *
 * @param exception The error information encapsulated in a
 *                  SAX parse exception.
 * @exception org.xml.sax.SAXException Any SAX exception, possibly
 *            wrapping another exception.
 * @see org.xml.sax.SAXParseException 
 */
public void error(SAXParseException exception) throws SAXException {
    String message = "Error: " + getParseExceptionInfo(exception);
    throw new SAXException(message);
}
/**
 * Receive notification of a non-recoverable error.
 * A <code>SAXException</code> containing the error descritpion will
 * be thrown.
 *
 * <p>This corresponds to the definition of "fatal error" in
 * section 1.2 of the W3C XML 1.0 Recommendation.  For example, a
 * parser would use this callback to report the violation of a
 * well-formedness constraint.</p>
 *
 * <p>The application must assume that the document is unusable
 * after the parser has invoked this method, and should continue
 * (if at all) only for the sake of collecting addition error
 * messages: in fact, SAX parsers are free to stop reporting any
 * other events once this method has been invoked.</p>
 *
 * @param exception The error information encapsulated in a
 *                  SAX parse exception.  
 * @exception org.xml.sax.SAXException Any SAX exception, possibly
 *            wrapping another exception.
 * @see org.xml.sax.SAXParseException
 */
public void fatalError(SAXParseException exception) throws SAXException {
    String message = "Fatal Error: " + getParseExceptionInfo(exception);
    throw new SAXException(message);
}
/**
 * Returns a string describing parse exception details
 */
private String getParseExceptionInfo(SAXParseException spe) {
    String systemId = spe.getSystemId();
    if (systemId == null) {
        systemId = "null";
    }
    String info = "URI=" + systemId +
        " Line=" + spe.getLineNumber() + ": " + spe.getMessage();
    return info;
}
/**
 * Receive notification of a warning.  Warnings are written to the
 * <code>PrintWriter</code> that was supplied to the constructor.
 *
 * <p>SAX parsers will use this method to report conditions that
 * are not errors or fatal errors as defined by the XML 1.0
 * recommendation.  The default behaviour is to take no action.</p>
 *
 * <p>The SAX parser must continue to provide normal parsing events
 * after invoking this method: it should still be possible for the
 * application to process the document through to the end.</p>
 *
 * <p>Filters may use this method to report other, non-XML warnings
 * as well.</p>
 *
 * @param exception The warning information encapsulated in a
 *                  SAX parse exception.
 * @exception org.xml.sax.SAXException Any SAX exception, possibly
 *            wrapping another exception.
 * @see org.xml.sax.SAXParseException 
 */
public void warning(SAXParseException exception) throws SAXException {
    _out.println("Warning: " + getParseExceptionInfo(exception));
}
}
