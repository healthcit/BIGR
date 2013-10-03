package com.ardais.bigr.orm.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.orm.helpers.FormLogic;
/**
 * Insert the type's description here.
 * Creation date: (4/10/01 4:40:32 PM)
 * @author: Jake Thompson
 */
public class InvalidateSession extends com.ardais.bigr.orm.op.StandardOperation {
/**
 * InvalidateSession constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public InvalidateSession(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
private String getCookieDomain(HttpServletRequest req) {
	
  String host = req.getServerName();
  String result = null;
  int pos = host.indexOf('.');
  boolean errorDetected = false;

  if (pos >= 0) {
    result = host.substring(pos);
    // Make sure result has at least one "." in it besides the one
    // that it now begins with.
    if (result.indexOf('.', 1) < 0) errorDetected = true;
  }
  else {
    errorDetected = true;
  }

  if (errorDetected) {
    throw new RuntimeException(
      "The host name you use in the URL must be a complete name (in the form 'x.y.com').");
  }

  return result;
}
public void invoke() throws Exception, IOException {
   	FormLogic.invalidateSession(request, response);
   	//log session time out. MR 5348
    if (ApiFunctions.safeEquals(ApiProperties.getProperty("api.session.timeout.log"), "true")) {
        StringBuffer logBuff = new StringBuffer(256);
        logBuff.append("Session time out called from " + this.getClass().getName());
        logBuff.append(" for op name = " + request.getParameter("op"));
        ApiLogger.getLog().info(logBuff.toString());
    }
    request.setAttribute("TimeOutError","N");
    servletCtx.getRequestDispatcher("/nosession.jsp").forward(request, response);
}
}
