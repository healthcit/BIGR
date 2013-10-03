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
public class PathRawEdit extends StandardOperation {
/**
 * Creates a new <code>PathRawEdit</code>.
 *
 * @param  req  the HTTP request
 * @param  res	the HTTP response
 * @param  ctx  the servlet context
 */
public PathRawEdit(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {
	PathReportHelper helper = new PathReportHelper(request);

	// Make sure that all required parameters are present.
	String ardaisId = helper.getArdaisId();	
	if (JspHelper.isEmpty(ardaisId))
		throw new ApiException("Required parameter 'ardaisId' is not present.");

	String consentId = helper.getConsentId();
	if (JspHelper.isEmpty(consentId))
		throw new ApiException("Required parameter 'consentId' is not present.");
	
	// If this is a new path report, make sure that some text was entered.
	// If no text was entered, then consider this a validation error and
	// go back to the edit page.
	if (helper.isNew() && ApiFunctions.isEmpty(helper.getRawPathReport())) {
		helper.getMessageHelper().addMessage("Please enter a pathology report.");
		helper.getMessageHelper().setError(true);
		request.setAttribute("helper", helper);
		(new PathRawPrepare(request, response, servletCtx)).invoke();
	}
	
	// Save the raw path report.
	else {
		try {
			String user = (String)request.getSession(false).getAttribute("user");
			PathReportData pathData = helper.getDataBean();
			pathData.setLastUpdateUser(user);
      //get the alias values for the donor and case and set them on the
      //path report so they can be logged in the history record.
      DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = ddcDonorHome.create();
      ConsentData cd = new ConsentData();
      cd.setConsentId(pathData.getConsentId());
      String caseAlias = donorOperation.getConsentDetail(cd).getCustomerId();
      pathData.setConsentCustomerId(caseAlias);
      DonorData dd = new DonorData();
      dd.setArdaisId(pathData.getArdaisId());
      String donorAlias = donorOperation.getDonorProfile(dd).getCustomerId();
      pathData.setDonorCustomerId(donorAlias);
			if (helper.isNew()) {
				pathData.setCreateUser(user);
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathOperation = pathHome.create();
				pathData = pathOperation.createRawPathReport(pathData, secInfo);
			}
			else {
        SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathOperation = pathHome.create();
				pathData = pathOperation.updateRawPathReport(pathData, secInfo);
			}
		}
		catch (Exception e) {
			throw new ApiException(e);
		}

		// Forward to the case list page.
		SelectDonorHelper context = new SelectDonorHelper();
		context.setArdaisId(ardaisId);
		context.setOp("CaseListPrepare");
		context.setPathOp("PathRawPrepare");
    if ("Y".equalsIgnoreCase(helper.getDonorImportedYN())) {
      context.setImportedYN("Y");
    }
		request.setAttribute("context", context);
		(new CaseListPrepare(request, response, servletCtx)).invoke();
	}
}

public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
