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
 * Prepares information for the JSP that allows editing of a pathology report
 * section.
 *
 * <p><b>Inputs:</b></p>
 * <ul>
 * <li>pathReportId [required] [request parameter] - the id of the pathology report
 *     with which this section is associated</li>
 * <li>pathReportSectionId [optional] [request parameter] - the id of the pathology 
 *     report section.  If this is not specified, it is assumed that a new pathology 
 *     report section is being created.</li>
 * </ul>
 *
 * <b>Outputs:</b>
 * <ul>
 * <li>helper [request attribute] - a {@link PathReportSectionHelper}
 *     that contains information on the section to be edited</li>
 * </ul>
 *
 * <b>Forwards:</b>
 * <ul>
 * <li>/path/abstract/PathSection.jsp</li>
 * </ul>
 */
public class PathSectionPrepare extends StandardOperation {
/**
 * Creates a <code>PathSectionPrepare</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public PathSectionPrepare(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Looks up the path report and path report section, if an existing
 * section, sets appropriate request attributes, and forwards to the path
 * report section editing JSP.
 */
public void invoke() {
	PathReportSectionHelper helper = (PathReportSectionHelper)request.getAttribute("helper");
	boolean haveHelper = (helper != null);
	if (!haveHelper)
		helper = new PathReportSectionHelper(request);
	
	// Make sure that the required parameters are present, and throw an
	// exception if they're not.
	String pathReportId = helper.getPathReportId();	
	if (ApiFunctions.isEmpty(pathReportId))
		throw new ApiException("Required parameter 'pathReportId' is not present.");

	try {
    DDCPathologyHome pathHome = null;
    DDCPathology pathOperation = null;

		if ((!haveHelper) && (helper.getPathReportSectionId() != null)) {
      PathReportSectionData data = helper.getDataBean();
      pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
      pathOperation = pathHome.create();
			PathReportSectionData sectionData = pathOperation.getPathReportSection(data);
      String donorImportedYN = helper.getDonorImportedYN();
			helper = new PathReportSectionHelper(sectionData);
      //carry forward the information about whether the donor was imported
      helper.setDonorImportedYN(donorImportedYN);
		}
			
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
	forward("/hiddenJsps/ddc/path/abstract/PathSection.jsp");	
}

public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
