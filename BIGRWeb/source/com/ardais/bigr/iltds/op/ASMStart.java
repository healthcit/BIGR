package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Insert the type's description here.
 * Creation date: (1/16/2001 2:59:56 PM)
 * @author: Jake Thompson
 */
public class ASMStart extends com.ardais.bigr.iltds.op.StandardOperation {
	/**
	 * ASMStart constructor comment.
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param ctx javax.servlet.ServletContext
	 */
	public ASMStart(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (1/16/2001 2:59:56 PM) 
	 */
	public void invoke() throws IOException, Exception {

		try {

			if (request.getParameter("op").equals("ASMStartLink")) {
				servletCtx
					.getRequestDispatcher("/hiddenJsps/iltds/asm/asmFormConsentLink.jsp")
					.forward(request, response);
			} else if (request.getParameter("op").equals("ASMStartLookup")) {
				servletCtx
					.getRequestDispatcher("/hiddenJsps/iltds/asm/asmFormLookup.jsp")
					.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ReportError err = new ReportError(request, response, servletCtx);
			err.setFromOp(this.getClass().getName());
			err.setErrorMessage(e.toString());
			try {
				err.invoke();
			} catch (Exception _axxx) {
			}
			return;
		}
	}
}
