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
import com.ardais.bigr.iltds.helpers.MessageHelper;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.beans.DDCPathology;
import com.ardais.bigr.pdc.beans.DDCPathologyHome;
import com.ardais.bigr.pdc.helpers.PathReportHelper;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.util.EjbHomes;

/**
 */
public class PathAbstractSummaryPrepare extends StandardOperation {
/**
 * Creates a <code>PathAbstractSummaryPrepare</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public PathAbstractSummaryPrepare(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {

  String forwardString = null;
  
	// Get the helper.
	PathReportHelper helper = (PathReportHelper)request.getAttribute("helper");
	if (helper == null) helper = new PathReportHelper(request);
	
	// If we don't have a path report id, then the user will be editing a
	// new path report, so check that we have all required path report
	// attributes.
	String id = helper.getPathReportId();
	if (ApiFunctions.isEmpty(id)) {
		if (ApiFunctions.isEmpty(helper.getArdaisId()))
			throw new ApiException("Required parameter 'ardaisId' is not present.");
		if (ApiFunctions.isEmpty(helper.getConsentId()))
			throw new ApiException("Required parameter 'consentId' is not present.");
    
		// Forward to the select disease JSP since the path report
		// has not been created yet.
		request.setAttribute("helper", helper);
    forwardString = "/hiddenJsps/ddc/path/abstract/SelectDisease.jsp";
		//forward("/hiddenJsps/ddc/path/abstract/SelectDisease.jsp");
	}

	// If we do have a path report id, then look up the path report, and get summary
	// information for all of its sections and diagnostic tests.
	else {
		try {
      PathReportData data = helper.getDataBean();
      DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
      DDCPathology pathOperation = pathHome.create();
			PathReportData pathData = pathOperation.getPathReportSummary(data);
      MessageHelper msgHelper = helper.getMessageHelper();
			helper = new PathReportHelper(pathData);
      helper.getMessageHelper().addMessages(msgHelper);
      //carry forward the information about whether the donor was imported
      helper.setDonorImportedYN(JspHelper.safeTrim(request.getParameter("donorImportedYN")));
			request.setAttribute("helper", helper);

			// If the disease has not been specified yet, forward to 
			// the select disease JSP.
			if (ApiFunctions.isEmpty(helper.getDisease())) {
        forwardString = "/hiddenJsps/ddc/path/abstract/SelectDisease.jsp";
        //forward("/hiddenJsps/ddc/path/abstract/SelectDisease.jsp");
			}

			// Otherwise forward to the path report summary page.
			else {
        forwardString = "/hiddenJsps/ddc/path/abstract/PathAbstractSummary.jsp";
        //forward("/hiddenJsps/ddc/path/abstract/PathAbstractSummary.jsp");
			}
		}
		catch (Exception e) {
			throw new ApiException(e);
		}
	}
  
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
	storeLastUsedInfo(helper.getArdaisId(), helper.getConsentId(), null, true);
  
  forward(forwardString);
}

public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
