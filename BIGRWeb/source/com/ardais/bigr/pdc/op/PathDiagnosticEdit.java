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
 * Creates a new pathology report diagnostic or updates an existing pathology report diagnostic
 * based upon the request parameters.
 *
 * <p><b>Inputs:</b></p>
 * <ul>
 * <li>diagnostic (request parameter, required) - the diagnostic concept id</li>
 * <li>pathReportId (request parameter, required) - the id of the pathology report
 *     with which the diagnostic is associated</li>
 * <li>new (request parameter, optional) - if this parameter is present and equal to
 *     <code>true</code>, then a new diagnostic is created; otherwise an existing
 *     diagnostic is updated</li>
 * <li>addnext (request parameter, optional) - if this parameter is present and equal to
 *     <code>addnext</code>, then the op to allow a new diagnostic to be created is
 *     forwarded to; otherwise the pathology abstract summary op is forwarded to</li>
 * <li>all other diagnostic properties (request parameter, optional)</li>
 * </ul>
 *
 * <b>Outputs:</b>
 * <ul>
 * <li>helper (request attribute)</li>
 *   <ul>
 *   <li>a <code>PathReportDiagnosticHelper</code> that contains the diagnostic
 *       information entered if there was a validation error</li>
 *   <li>a <code>PathReportDiagnosticHelper</code> that contains just the 
 *       path report id if the <code>addnext</code> request parameter was specified</li>
 *   <li>a <code>PathReportHelper</code> that contains just the path report id if the 
 *       <code>addnext</code> request parameter was not specified</li>
 *   </ul>
 * </ul>
 *
 * <b>Forwards:</b>
 * <ul>
 * <li>PathDiagnosticPrepare op - if there was a validation error or <code>addnext</code> 
 *     was specified</li>
 * <li>PathAbstractSummaryPrepare op - if <code>addnext</code> was not specified</li>
 * </ul>
 */
public class PathDiagnosticEdit extends StandardOperation {
/**
 * Creates a <code>PathDiagnosticEdit</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public PathDiagnosticEdit(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Creates or updates the diagnostic specified by the request parameters,
 * after validation, and forwards to the appropriate view.
 */
public void invoke() {
	PathReportDiagnosticHelper helper = new PathReportDiagnosticHelper(request);

	// If the submitted data is valid, save the diagnostic.
	if (helper.isValid()) {
		try {
			PathReportDiagnosticData diagnosticData = helper.getDataBean();
      boolean isNew = helper.isNew();
      SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
      DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
      DDCPathology pathOperation = pathHome.create();
			if (isNew) {
				diagnosticData = pathOperation.createPathReportDiagnostic(diagnosticData, secInfo);
			}
			else {
				diagnosticData = pathOperation.updatePathReportDiagnostic(diagnosticData, secInfo);
			}
		}
		catch (Exception e) {
			throw new ApiException(e);
		}

		String addNext = request.getParameter("addnext");
		if ((addNext != null) && (addNext.equals("addnext"))) {
			PathReportDiagnosticData diagnosticData = new PathReportDiagnosticData();
			diagnosticData.setPathReportId(helper.getPathReportId());
      String donorImportedYN = helper.getDonorImportedYN();
      String consentId = helper.getConsentId();
			helper = new PathReportDiagnosticHelper(diagnosticData);
      //carry forward the information about whether the donor was imported, as well as
      //the consent id
      helper.setDonorImportedYN(donorImportedYN);
      helper.setConsentId(consentId);
			request.setAttribute("helper", helper);
			(new PathDiagnosticPrepare(request, response, servletCtx)).invoke();			
		}
		else {
			PathReportData pathData = new PathReportData();
			pathData.setPathReportId(helper.getPathReportId());
			PathReportHelper pathHelper = new PathReportHelper(pathData);
      //carry forward the information about whether the donor was imported
      pathHelper.setDonorImportedYN(helper.getDonorImportedYN());
			request.setAttribute("helper", pathHelper);
			(new PathAbstractSummaryPrepare(request, response, servletCtx)).invoke();
		}
	}
	
	// If there was a validation error, forward back to the diagnostic page.
	else {
		request.setAttribute("helper", helper);
		(new PathDiagnosticPrepare(request, response, servletCtx)).invoke();
	}
}

public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
