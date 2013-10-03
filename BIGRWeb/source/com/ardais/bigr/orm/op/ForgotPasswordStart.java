package com.ardais.bigr.orm.op;

import com.ardais.bigr.orm.beans.*;
/**
 * Insert the type's description here.
 * Creation date: (5/29/01 10:33:55 AM)
 * @author: Jake Thompson
 */
public class ForgotPasswordStart extends StandardOperation {


/**
 * ForgotPasswordStart constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public ForgotPasswordStart(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Insert the method's description here.
 * Creation date: (5/29/01 10:33:55 AM)
 */
public void invoke() throws Exception, java.io.IOException {

	servletCtx
		.getRequestDispatcher("/hiddenJsps/orm/ForgotPasswordUser.jsp")
		.forward(request, response);
}
}
