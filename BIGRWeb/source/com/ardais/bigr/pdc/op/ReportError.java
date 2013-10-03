package com.ardais.bigr.pdc.op;

import com.ardais.bigr.api.*;
import java.io.*;
import java.util.Collection;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * The <code>ReportError</code> op is used to report errors to
 * the user in a standard and consistent way.  It works in
 * conjunction with the error report JSP, setting request
 * attributes appropriately before forwarding to the JSP.
 */
public class ReportError extends StandardOperation {
	public String _fromOp;
	public String _errorMessage;
/**
 * Create a new <code>ReportError</code> op.
 * 
 * @param  req  the servlet request
 * @param  res  the servlet response
 * @param  ctx  the servlet context
 */
public ReportError(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Return the error message.
 *
 * @return  The error message.
 */
public String getErrorMessage() {
	return _errorMessage;
}
/**
 * Return the op that invoked this op, the one that encountered the error.
 *
 * @return  The invoking op.
 */
public String getFromOp() {
	return _fromOp;
}
/**
 * Returns an error page to the client, based on the message and
 * invoking op that were previously set.
 */
public void invoke() {

	// If the response is already committed, then write
	// error information into the existing response stream.
	if (response.isCommitted()) {
		writeSimpleError();
	}

	// If the response has not been committed, then reset
	// it and forward to the error page.
	else {
		try {
			response.reset();
			request.setAttribute("myError", getErrorMessage());
			request.setAttribute("fromOp", getFromOp());
			forward("/hiddenJsps/reportError/errorReport.jsp");
		}
		catch (Exception e) {
			ApiLogger.log(e);
			writeSimpleError();
		}
	}
}

public void preInvoke(Collection fileItems) {
  //do nothing here ;
  
}

/**
 * Sets the error message.
 *
 * @param  msg  the error message
 */
public void setErrorMessage(String msg) {
	_errorMessage = msg;
}
/**
 * Sets the op that invoked this op, the one that encountered the error.
 *
 * @param  op  the invoking op.
 */
public void setFromOp(String op) {
	_fromOp = op;
}
/**
 * Writes an error report page to the response stream.  This 
 * is used if we cannot forward to the error reporting
 * JSP page.
 */
private void writeSimpleError() {
	boolean isCommitted = response.isCommitted();
	try {
		PrintWriter writer = response.getWriter();
		if (!isCommitted) {
			response.reset();
			com.ardais.bigr.orm.helpers.FormLogic.noCache(response);
			writer.println("<html><head><title>Error Report</title>");
			writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
			writer.println("</head><body>");
		}
		writer.println("<p>**********");
		writer.println("<br>Error Report");
		writer.println("<br>**********");
		writer.println("<br>Error From Operation: " + getFromOp());
		writer.println("<br>Error Message: " + getErrorMessage());
		writer.println("<br>**********");
		if (!isCommitted) {
			writer.println("</body></html>");
		}
		response.flushBuffer();
	}
	catch (IOException e) {
		throw new ApiException(e);
	}
}
}
