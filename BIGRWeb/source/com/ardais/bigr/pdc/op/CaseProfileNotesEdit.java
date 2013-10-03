package com.ardais.bigr.pdc.op;

import java.util.Collection;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.ConsentHelper;
import com.ardais.bigr.pdc.helpers.SelectDonorHelper;
import com.ardais.bigr.pdc.javabeans.ConsentData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

/**
 * Insert the type's description here.
 * Creation date: (7/24/2002 3:47:37 PM)
 * @author: Jake Thompson
 */
public class CaseProfileNotesEdit extends StandardOperation {
/**
 * CaseProfileNotesEdit constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public CaseProfileNotesEdit(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * The <code>invoke</code> method is where the work is performed
 * in the op subclass.
 */
public void invoke() {
	ConsentHelper helper = new ConsentHelper(request);

	// Make sure that all required parameters are present.
	String consentId = helper.getConsentId();
	if (JspHelper.isEmpty(consentId))
		throw new ApiException("Required parameter 'consentId' is not present.");
	
	String ardaisId = helper.getArdaisId();	
	if (JspHelper.isEmpty(ardaisId))
		throw new ApiException("Required parameter 'ardaisId' is not present.");

	
	// Save the raw path report.
	try {
		ConsentData consentData = helper.getDataBean();
    SecurityInfo secInfo = WebUtils.getSecurityInfo(request);
    DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
    DDCDonor donorOperation = ddcDonorHome.create();
		consentData = donorOperation.updateCaseProfileNotes(consentData, secInfo);
	}
	catch (Exception e) {
		throw new ApiException(e);
	}

	// Forward to the case list page.
	SelectDonorHelper context = new SelectDonorHelper();
	context.setArdaisId(ardaisId);
	context.setOp("CaseListPrepare");
	context.setPathOp("CaseProfileNotesPrepare");
  if ("Y".equalsIgnoreCase(helper.getDonorImportedYN())) {
    context.setImportedYN("Y");
  }
	request.setAttribute("context", context);
	(new CaseListPrepare(request, response, servletCtx)).invoke();
}

 public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
