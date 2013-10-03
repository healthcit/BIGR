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
 */
public class AdditionalFindingPrepare extends StandardOperation {
/**
 * Creates an <code>AdditionalFindingPrepare</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public AdditionalFindingPrepare(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Looks up the path report and additional finding, if editing an existing
 * additional finding, sets appropriate request attributes, and forwards to the 
 * additional finding editing JSP.
 */
public void invoke() {

	// Get the helper, either from a request attribute or create it from
	// the request parameters.
	PathReportSectionFindingHelper helper = (PathReportSectionFindingHelper)request.getAttribute("helper");
	boolean haveHelper = (helper != null);
	if (!haveHelper)
		helper = new PathReportSectionFindingHelper(request);
	
	// Make sure that the required parameters are present, and throw an
	// exception if they're not.
	String pathReportId = helper.getPathReportId();
	if (ApiFunctions.isEmpty(pathReportId))
		throw new ApiException("Required parameter 'pathReportId' is not present.");

	if (helper.isNew() && ApiFunctions.isEmpty(helper.getPathReportSectionId()))
		throw new ApiException("Required parameter 'pathReportSectionId' is not present.");

	try {
    DDCPathologyHome pathHome = null;
    DDCPathology pathOperation = null;
		// Get the existing additional finding if the finding id was specified.
		if (!haveHelper && !helper.isNew()) {
      PathReportSectionFindingData data = helper.getDataBean();
      pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
      pathOperation = pathHome.create();
			PathReportSectionFindingData findingData = pathOperation.getPathReportSectionFinding(data);
      String donorImportedYN = helper.getDonorImportedYN();
      String consentId = helper.getConsentId();
			helper = new PathReportSectionFindingHelper(findingData);
      //carry forward the information about whether the donor was imported, as well as
      //the consent id
      helper.setDonorImportedYN(donorImportedYN);
      helper.setConsentId(consentId);
		}

		// Get the ancestor path report.
		PathReportData pathData = new PathReportData();
		pathData.setPathReportId(pathReportId);
    if (pathHome == null) pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
    if (pathOperation == null) pathOperation = pathHome.create();
		pathData = pathOperation.getPathReport(pathData);
		helper.setPathReport(new PathReportHelper(pathData));			
    //since the jsp we're forwarding to makes use of the PathReportHelper, populate it's
    //donorImportedYN field so that all links derived from it are correct.
    helper.getPathReport().setDonorImportedYN(helper.getDonorImportedYN());
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
	forward("/hiddenJsps/ddc/path/abstract/AdditionalFinding.jsp");	
}

public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}

}
