package com.ardais.bigr.pdc.op;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.beans.DDCPathology;
import com.ardais.bigr.pdc.beans.DDCPathologyHome;
import com.ardais.bigr.pdc.helpers.PathReportHelper;
import com.ardais.bigr.pdc.javabeans.ConsentData;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.util.EjbHomes;

/**
 */
public class PathCasePrepare extends StandardOperation {
/**
 * Creates a <code>PathReportPrepare</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public PathCasePrepare(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {
	PathReportHelper helper = (PathReportHelper)request.getAttribute("helper");
	boolean haveHelper = (helper != null);
	if (!haveHelper)
		helper = new PathReportHelper(request);
		
	// Get the path report id, and throw an exception if we don't have one
	// since we're always editing an existing path report that was created
	// with at least a disease type.
	if (ApiFunctions.isEmpty(helper.getPathReportId()))
		throw new ApiException("Required parameter 'pathReportId' is not present.");

	// If we don't already have the path report, then look it up.
	if (!haveHelper) {
		try {
      PathReportData data = helper.getDataBean();
      DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
      DDCPathology pathOperation = pathHome.create();
			PathReportData pathData = pathOperation.getPathReport(data);
      String donorImportedYN = helper.getDonorImportedYN();
      helper = new PathReportHelper(pathData);
      //carry forward the knowledge of whether the donor was imported
      helper.setDonorImportedYN(donorImportedYN);
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
    com.ardais.bigr.javabeans.ConsentData consent = new com.ardais.bigr.javabeans.ConsentData(consentBean);
    helper.setConsentCustomerId(consent.getCustomerId());
  }
  catch (Exception e) {
  ApiFunctions.throwAsRuntimeException(e);
  }

	// Forward to the path report edit page.
	request.setAttribute("helper", helper);
	forward("/hiddenJsps/ddc/path/abstract/PathCase.jsp");
}
public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
  
}
