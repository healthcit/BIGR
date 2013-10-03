package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Insert the type's description here.
 * Creation date: (1/17/2001 5:51:15 PM)
 * @author: Jake Thompson
 */
public class CaseReleaseStart
	extends com.ardais.bigr.iltds.op.StandardOperation {
	/**
	 * CaseReleaseStart constructor comment.
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param ctx javax.servlet.ServletContext
	 */
	public CaseReleaseStart(
		HttpServletRequest req,
		HttpServletResponse res,
		ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (1/17/2001 5:51:15 PM)
	 */
	public void invoke() throws IOException, Exception {

		try {
			servletCtx
				.getRequestDispatcher("/hiddenJsps/iltds/caseRelease/caseRelease.jsp")
				.forward(request, response);
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
