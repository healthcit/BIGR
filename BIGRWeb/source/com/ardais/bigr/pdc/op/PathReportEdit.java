package com.ardais.bigr.pdc.op;

import com.ardais.bigr.api.*;
import com.ardais.bigr.iltds.helpers.*;
import com.ardais.bigr.pdc.beans.*;
import com.ardais.bigr.pdc.helpers.*;
import com.ardais.bigr.pdc.javabeans.*;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

import java.io.*;
import java.util.Collection;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 */
public class PathReportEdit extends StandardOperation {
/**
 * PathReportEdit constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public PathReportEdit(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {
	PathReportHelper helper = new PathReportHelper(request);
	
	// Check for required parameters, which are based on whether
	// this is a new path report.
	if (helper.isNew()) {
		if (JspHelper.isEmpty(helper.getArdaisId()))
			throw new ApiException("Required parameter 'ardaisId' is not present.");

		if (JspHelper.isEmpty(helper.getConsentId()))
			throw new ApiException("Required parameter 'consentId' is not present.");
	}

	// Validate the request parameters, and save the path report if
	// validation succeeds.
	if (helper.isValid()) {
		try {
			String user = (String)request.getSession(false).getAttribute("user");
			PathReportData pathData = helper.getDataBean();
			pathData.setLastUpdateUser(user);
			if (helper.isNew()) {
				pathData.setCreateUser(user);
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathOperation = pathHome.create();
				pathData = pathOperation.createPathReport(pathData, secInfo);
			}
			else {
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathOperation = pathHome.create();
				pathData = pathOperation.updatePathReport(pathData, secInfo);
			}
      String donorImportedYN = helper.getDonorImportedYN();
			helper = new PathReportHelper(pathData);
      //carry forward the knowledge of whether the donor was imported
      helper.setDonorImportedYN(donorImportedYN);
		}
		catch (Exception e) {
			throw new ApiException(e);
		}

		// Forward to the path report summary page.
		request.setAttribute("helper", helper);
		(new PathAbstractSummaryPrepare(request, response, servletCtx)).invoke();
	}

	// Validation failed, so forward back to the edit page to
	// allow the user to fix their mistakes.
	else  {		
		request.setAttribute("helper", helper);
		String context = helper.getContext();
		if (context.equals("disease"))
			(new PathAbstractSummaryPrepare(request, response, servletCtx)).invoke();	
		else if (context.equals("case"))
			(new PathCasePrepare(request, response, servletCtx)).invoke();	
	}
}

public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
