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
public class PathSectionEdit extends StandardOperation {
/**
 * Creates a <code>PathSectionEdit</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public PathSectionEdit(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {
	PathReportSectionHelper helper = new PathReportSectionHelper(request);

	// If the section information is valid, save the path report section.
	if (helper.isValid()) {
		try {
      DDCPathologyHome pathHome = null;
      DDCPathology pathOperation = null;
			String user = (String)request.getSession(false).getAttribute("user");
			PathReportSectionData sectionData = helper.getDataBean();
			sectionData.setLastUpdateUser(user);
			if (helper.isNew()) {
				sectionData.setCreateUser(user);
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
        pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        pathOperation = pathHome.create();
				sectionData = pathOperation.createPathReportSection(sectionData, secInfo);
			}
			else {
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
        pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        pathOperation = pathHome.create();
				sectionData = pathOperation.updatePathReportSection(sectionData, secInfo);
			}
      String donorImportedYN = helper.getDonorImportedYN();
			helper = new PathReportSectionHelper(pathOperation.getPathReportSection(sectionData));
      //carry forward the information about whether the donor was imported
      helper.setDonorImportedYN(donorImportedYN);
		}
		catch (Exception e) {
			throw new ApiException(e);
		}

		// Forward to the path report section summary page.
		request.setAttribute("helper", helper);
		(new PathSectionSummaryPrepare(request, response, servletCtx)).invoke();
	}

	// The section information is not valid, so go back to the edit page.
	else {
		request.setAttribute("helper", helper);
		(new PathSectionPrepare(request, response, servletCtx)).invoke();
	}
}
public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
