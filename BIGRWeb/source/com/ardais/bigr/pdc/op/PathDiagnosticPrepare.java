package com.ardais.bigr.pdc.op;

import com.ardais.bigr.api.*;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
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
 * Prepares information for the JSP that allows creating and updating of
 * pathology report diagnostics.
 *
 * <p><b>Inputs:</b></p>
 * <ul>
 * <li>pathReportId (request parameter, required) - the id of the pathology report
 *     with which the diagnostic is associated</li>
 * <li>diagnostic (request parameter, optional) - the diagnostic concept id of the 
 *     diagnostic being edited, if an existing diagnostic is being edited</li>
 * <li>new (request parameter, optional) - if this parameter is present and equal to
 *     <code>true</code>, then a new diagnostic is to be created; otherwise
 *     an existing diagnostic is to be updated
 * </ul>
 * OR
 * <ul>
 * <li>helper (request attribute) - a <code>PathReportDiagnosticHelper</code>
 *     for the diagnostic to be edited.  This must contain at least the
 *     pathReportId, and may also contain the new flag, and diagnostic concept id
 *     and all fields of the diagnostic</li>
 * </ul>
 *
 * <b>Outputs:</b>
 * <ul>
 * <li>helper (request attribute) - a <code>PathReportDiagnosticHelper</code>
 *     that contains information on the diagnostic to be created/edited</li>
 * </ul>
 *
 * <b>Forwards:</b>
 * <ul>
 * <li>/path/abstract/PathDiagnostic.jsp</li>
 * </ul>
 */
public class PathDiagnosticPrepare extends StandardOperation {
/**
 * Creates a <code>PathDiagnosticPrepare</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public PathDiagnosticPrepare(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Looks up the diagnostic and pathology report specified by the inputs,
 * sets appropriate request attributes, and forwards to the diagnostic
 * editing JSP.
 */
public void invoke() {
	// Get the helper.
	PathReportDiagnosticHelper helper = (PathReportDiagnosticHelper)request.getAttribute("helper");
	boolean haveHelper = (helper != null);
	if (!haveHelper)
		helper = new PathReportDiagnosticHelper(request);
	
	// Make sure all required parameters are present.
	if (ApiFunctions.isEmpty(helper.getPathReportId()))
		throw new ApiException("Required parameter 'pathReportId' is not present.");
	
	try {
		// If we have the diagnostic id, then look up the diagnostic.
		if ((!haveHelper) && (!ApiFunctions.isEmpty(helper.getDiagnostic()))) {
      PathReportDiagnosticData data = helper.getDataBean();
      DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
      DDCPathology pathOperation = pathHome.create();
			PathReportDiagnosticData diagnosticData = pathOperation.getPathReportDiagnostic(data);
      String donorImportedYN = helper.getDonorImportedYN();
      String consentId = helper.getConsentId();
			helper = new PathReportDiagnosticHelper(diagnosticData);
      //carry forward the information about whether the donor was imported, as well as
      //the consent id
      helper.setDonorImportedYN(donorImportedYN);
      helper.setConsentId(consentId);
		}
	}
	catch (Exception e) {
		throw new ApiException(e);
	}
  
  //if the donor is imported, get the alias values for both the donor and the case
  //for display on the resulting page
  if ("Y".equalsIgnoreCase(helper.getDonorImportedYN())) {
    try {  
      DonorData dd = new DonorData();
      dd.setArdaisId(helper.getPathReport().getArdaisId());
      DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = ddcDonorHome.create();
      dd = donorOperation.getDonorProfile(dd);
      helper.getPathReport().setDonorCustomerId(dd.getCustomerId());
      ConsentAccessBean consentBean = new ConsentAccessBean(new ConsentKey(helper.getPathReport().getConsentId()));
      com.ardais.bigr.javabeans.ConsentData consent = new com.ardais.bigr.javabeans.ConsentData(consentBean);
      helper.getPathReport().setConsentCustomerId(consent.getCustomerId());
    }
    catch (Exception e) {
    ApiFunctions.throwAsRuntimeException(e);
    }
  }

	// Forward to the path report edit page.
	request.setAttribute("helper", helper);
	forward("/hiddenJsps/ddc/path/abstract/PathDiagnostic.jsp");
}

public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
