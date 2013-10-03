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
public class AdditionalFindingEdit extends StandardOperation {
/**
 * Creates an <code>AdditionalFindingEdit</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public AdditionalFindingEdit(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Saves the additional finding.
 */
public void invoke() {

	PathReportSectionFindingHelper helper = new PathReportSectionFindingHelper(request);

	// Check that required parameters are present.
	String pathReportId = helper.getPathReportId();
	if (ApiFunctions.isEmpty(pathReportId))
		throw new ApiException("Required parameter 'pathReportId' is not present.");
	
	String pathReportSectionId = helper.getPathReportSectionId();
	if (ApiFunctions.isEmpty(pathReportSectionId))
		throw new ApiException("Required parameter 'pathReportSectionId' is not present.");

	// If the parameters are valid, then save the additional finding.
	if (helper.isValid()) {
		PathReportSectionFindingData dataBean = helper.getDataBean();
		try {
			String user = (String)request.getSession(false).getAttribute("user");
			dataBean.setEditUser(user);		
			if (helper.isNew()) {
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathOperation = pathHome.create();
				pathOperation.createPathReportSectionFinding(dataBean, secInfo);
      } else {
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathOperation = pathHome.create();
				pathOperation.updatePathReportSectionFinding(dataBean, secInfo);
      }
		}
		catch (Exception e) {
			throw new ApiException(e);
		}
			
		String addNext = request.getParameter("addnext");
		if ((addNext != null) && (addNext.equals("addnext"))) {
			dataBean = new PathReportSectionFindingData();
			dataBean.setPathReportSectionId(pathReportSectionId);
      String consentId = helper.getConsentId();
      String donorImportedYN = helper.getDonorImportedYN();
			helper = new PathReportSectionFindingHelper(dataBean);
      //carry forward the information about whether the donor was imported, as well as
      //the consent id
      helper.setDonorImportedYN(donorImportedYN);
      helper.setConsentId(consentId);
			helper.setPathReportId(pathReportId);
			request.setAttribute("helper", helper);
			(new AdditionalFindingPrepare(request, response, servletCtx)).invoke();			
		}
		else {
//			PathReportSectionData sectionData = new PathReportSectionData();
//			sectionData.setPathReportId(pathReportId);
//			sectionData.setPathReportSectionId(pathReportSectionId);
//			PathReportSectionHelper sectionHelper = new PathReportSectionHelper(sectionData);
//			request.setAttribute("helper", sectionHelper);
			(new PathSectionSummaryPrepare(request, response, servletCtx)).invoke();
		}
	}

	// There was a validation error, so go back to the edit page to allow
	// the error to be fixed.
	else {
		request.setAttribute("helper", helper);
		(new AdditionalFindingPrepare(request, response, servletCtx)).invoke();
	}
}

   public void preInvoke(Collection fileItems) {
     //method for potential file attachment, do nothing for now ;
     
   }
}
