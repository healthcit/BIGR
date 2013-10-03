package com.ardais.bigr.pdc.op;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.beans.DDCPathology;
import com.ardais.bigr.pdc.beans.DDCPathologyHome;
import com.ardais.bigr.pdc.helpers.PathReportHelper;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.util.EjbHomes;

/**
 */
public class PathRawPrepare extends StandardOperation {
/**
 * Creates a <code>PathRawPrepare</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public PathRawPrepare(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {

	// Determine whether this is a popup window or not.
	String context = ("true".equals(request.getParameter("popup"))) ? "popup" : null;
	
	// Get the path report helper.
	PathReportHelper helper = (PathReportHelper)request.getAttribute("helper");
	boolean haveHelper = (helper != null);
	if (!haveHelper)
		helper = new PathReportHelper(request);

	// If we don't have a path report id, then the user will be editing a
	// new path report, so check that we have all required path report
	// attributes.
	String id = helper.getPathReportId();
	if (ApiFunctions.isEmpty(id)) {
		if (ApiFunctions.isEmpty(helper.getArdaisId()))
			throw new ApiException("Required parameter 'ardaisId' is not present.");

		if (ApiFunctions.isEmpty(helper.getConsentId()))
			throw new ApiException("Required parameter 'consentId' is not present.");

		if (context == null)
			context = "new";
	}

	// If we do have a path report id and don't already have the path report
	// helper, then look up the raw path report.
	else if (!haveHelper) {
		try {
      PathReportData data = helper.getDataBean();
      DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
      DDCPathology pathOperation = pathHome.create();
			PathReportData pathData = pathOperation.getRawPathReport(data);
			helper = new PathReportHelper(pathData);
      //carry forward the information about whether the donor was imported
      helper.setDonorImportedYN(JspHelper.safeTrim(request.getParameter("donorImportedYN")));
		}
		catch (Exception e) {
			throw new ApiException(e);
		}
	}

	// Determine whether this is a new or existing path report, if we have not
	// figured it out yet.
	if (context == null)
		context = (ApiFunctions.isEmpty(helper.getRawPathReport())) ? "new" : "existing";
    
  //get the alias values for both the donor and the case for display on the resulting page
  //we don't check if the donor/case is imported because someday we may provide aliases for
  //non-imported objects and this code will automatically handle it
  try {  
    DonorData dd = new DonorData();
    dd.setArdaisId(helper.getArdaisId());
    DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
    DDCDonor donorOperation = ddcDonorHome.create();
    dd = donorOperation.getDonorProfile(dd);
    helper.setDonorCustomerId(dd.getCustomerId());
    ConsentAccessBean consentBean = new ConsentAccessBean(new ConsentKey(helper.getConsentId()));
    ConsentData consent = new ConsentData(consentBean);
    helper.setConsentCustomerId(consent.getCustomerId());
  }
  catch (Exception e) {
  ApiFunctions.throwAsRuntimeException(e);
  }

		
  //store the ardais id and case ids in the session for use in subsequent DDC operations
  //we pass "true" for the last parameter because if the user is modifying the same
  //donor/case as the ones currently saved in the session we don't want to erase any saved 
  //sample id.  If the user is modifying a different donor/case then any sample information 
  //will be erased regardless of the value of the last parameter.
	PdcUtils.storeLastUsedDonorCaseAndSample(request, helper.getArdaisId(), 
      helper.getDonorCustomerId(), helper.getConsentId(), helper.getConsentCustomerId(),
      null, null, true);
	
	// Forward to the raw path report page.
	request.setAttribute("helper", helper);
	request.setAttribute("context", context);
	forward("/hiddenJsps/ddc/path/PathRaw.jsp");
}
 public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
