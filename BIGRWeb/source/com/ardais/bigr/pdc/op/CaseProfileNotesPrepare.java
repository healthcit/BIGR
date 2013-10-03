package com.ardais.bigr.pdc.op;

import java.util.Collection;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.beans.ConsentAccessBean;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.ConsentHelper;
import com.ardais.bigr.pdc.javabeans.ConsentData;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.util.EjbHomes;

/**
 * Insert the type's description here.
 * Creation date: (7/24/2002 1:00:38 PM)
 * @author: Jake Thompson
 */
public class CaseProfileNotesPrepare extends StandardOperation {
/**
 * CaseProfileNotesPrepare constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public CaseProfileNotesPrepare(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * The <code>invoke</code> method is where the work is performed
 * in the op subclass.
 */
public void invoke() {
	// Determine whether this is a popup window or not.
	String context = ("true".equals(request.getParameter("popup"))) ? "popup" : null;
	
	// Get the consent helper.
	ConsentHelper helper = (ConsentHelper)request.getAttribute("helper");
	boolean haveHelper = (helper != null);
	if (!haveHelper)
		helper = new ConsentHelper(request);

	// If we do have a path report id and don't already have the path report
	// helper, then look up the raw path report.
	if (!haveHelper) {
		try {
      ConsentData cd = helper.getDataBean();
      DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = ddcDonorHome.create();
			ConsentData consentData = donorOperation.getConsentDetail(cd);
			helper = new ConsentHelper(consentData);
      //carry forward the information about whether the donor was imported
      helper.setDonorImportedYN(JspHelper.safeTrim(request.getParameter("donorImportedYN")));
		}
		catch (Exception e) {
			throw new ApiException(e);
		}
	}

	// Determine whether this is a new or existing path report, if we have not
	// figured it out yet.
	//if (context == null)
		context = (ApiFunctions.isEmpty(helper.getDiCaseProfileNotes())) ? "new" : "existing";
    
  //get the alias values for both the donor and the case for display on the resulting page
  //we don't check if the donor/case is imported because someday we may provide aliases for
  //non-imported objects and this code will automatically handle it
  String ardaisId = null;
  String customerId = null;
  String consentId = null;
  String customerConsentId = null;
  try {
    DonorData dd = new DonorData();
    ardaisId = helper.getArdaisId();
    dd.setArdaisId(ardaisId);
    DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
    DDCDonor donorOperation = ddcDonorHome.create();
    dd = donorOperation.getDonorProfile(dd);
    customerId = dd.getCustomerId();
    helper.setDonorCustomerId(customerId);
    consentId = helper.getConsentId();
    ConsentAccessBean consentBean = new ConsentAccessBean(new ConsentKey(consentId));
    com.ardais.bigr.javabeans.ConsentData consent = new com.ardais.bigr.javabeans.ConsentData(consentBean);
    customerConsentId = consent.getCustomerId();
    helper.setCustomerId(customerConsentId);
  } catch (Exception e) {
    ApiFunctions.throwAsRuntimeException(e);
  }
		
	//store the ardais id and case ids in the session for use in subsequent DDC operations
  //we pass "true" for the last parameter because if the user is modifying the same
  //donor/case as the ones currently saved in the session we don't want to erase any saved 
  //sample id.  If the user is modifying a different donor/case then any sample information 
  //will be erased regardless of the value of the last parameter.
  PdcUtils.storeLastUsedDonorCaseAndSample(request, ardaisId, customerId, consentId, customerConsentId, null, null, true);
	
	// Forward to the raw path report page.
	request.setAttribute("helper", helper);
	request.setAttribute("context", context);
	forward("/hiddenJsps/ddc/donor/CaseProfileNotes.jsp");	
}

 public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
