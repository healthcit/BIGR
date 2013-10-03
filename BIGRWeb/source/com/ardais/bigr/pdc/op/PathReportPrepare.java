package com.ardais.bigr.pdc.op;

import com.ardais.bigr.api.*;
import com.ardais.bigr.iltds.helpers.*;
import com.ardais.bigr.pdc.beans.*;
import com.ardais.bigr.pdc.helpers.*;
import com.ardais.bigr.pdc.javabeans.*;
import com.ardais.bigr.util.EjbHomes;

import java.io.*;
import java.util.Collection;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 */
public class PathReportPrepare extends StandardOperation {
/**
 * Creates a <code>PathReportPrepare</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public PathReportPrepare(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {
	PathReportHelper helper = new PathReportHelper(request);
	
	// Get the path report id, and throw an exception if we don't have one.
	String id = helper.getPathReportId();
	if (JspHelper.isEmpty(id)) {
		throw new ApiException("Required parameter 'id' is not present.");
	}

	// If we do have a path report id, then look up the path report.
	else {
		try {
      PathReportData data = helper.getDataBean();
      DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
      DDCPathology pathOperation = pathHome.create();
			PathReportData pathData = pathOperation.getPathReport(data);
			helper = new PathReportHelper(pathData);
		}
		catch (Exception e) {
			throw new ApiException(e);
		}
	}

	// Forward to the path report edit page.
	request.setAttribute("helper", helper);
	forward("/hiddenJsps/ddc/path/abstract/PathCase.jsp");
}
public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
