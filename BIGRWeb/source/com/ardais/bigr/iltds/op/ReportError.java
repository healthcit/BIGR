package com.ardais.bigr.iltds.op;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiLogger;

/**
 * Insert the type's description here.
 * Creation date: (4/11/01 2:06:44 PM)
 * @author: Jake Thompson
 */
public class ReportError extends com.ardais.bigr.iltds.op.StandardOperation {
	public java.lang.String fromOp;
	public java.lang.String errorMessage;

	/**
	 * ReportError constructor comment.
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param ctx javax.servlet.ServletContext
	 */
	public ReportError(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (4/11/2001 12:30:15 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (4/11/2001 12:29:54 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getFromOp() {
		return fromOp;
	}
	/**
	 * Returns an error page to the client.
	 */
	public void invoke() throws IOException {

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
				request.setAttribute("myError", errorMessage);
				request.setAttribute("fromOp", fromOp);
				servletCtx.getRequestDispatcher(
					"/hiddenJsps/reportError/errorReport.jsp").forward(
					request,
					response);
			} catch (Exception e) {
				ApiLogger.log(e);
				writeSimpleError();
			}
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (4/11/2001 12:30:15 PM)
	 * @param newErrorMessage java.lang.String
	 */
	public void setErrorMessage(java.lang.String newErrorMessage) {
		errorMessage = newErrorMessage;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (4/11/2001 12:29:54 PM)
	 * @param newFromOp java.lang.String
	 */
	public void setFromOp(java.lang.String newFromOp) {
		fromOp = newFromOp;
	}
	/**
	 * Writes an error report page to the response stream.  This 
	 * is used if we cannot forward to the error reporting
	 * JSP page.
	 */
	private void writeSimpleError() throws IOException {
		boolean isCommitted = response.isCommitted();
		PrintWriter writer = response.getWriter();
		if (!isCommitted) {
			response.reset();
			com.ardais.bigr.orm.helpers.FormLogic.noCache(response);
			writer.println("<html><head><title>Error Report</title>");
			writer.println(
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
			writer.println("</head><body>");
		}
		writer.println("<p>**********");
		writer.println("<br>Error Report");
		writer.println("<br>**********");
		writer.println("<br>Error From Operation: " + fromOp);
		writer.println("<br>Error Message: " + errorMessage);
		writer.println("<br>**********");
		if (!isCommitted) {
			writer.println("</body></html>");
		}
		response.flushBuffer();
	}
}
