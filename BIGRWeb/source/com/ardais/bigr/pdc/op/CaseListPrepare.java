package com.ardais.bigr.pdc.op;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.DonorHelper;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.helpers.SelectDonorHelper;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

/**
 */
public class CaseListPrepare extends StandardOperation {
/**
 * Create a new <code>CaseListPrepare</code>.
 *
 * @param  req  the HTTP servlet request
 * @param  res  the HTTP servlet response
 * @param  ctx  the servlet context
 */
public CaseListPrepare(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {
	String ardaisId = null;
	String pathOp = null;
  String customerId = JspHelper.safeTrim(request.getParameter("customerId"));
  String importedYN = JspHelper.safeTrim(request.getParameter("importedYN"));
  boolean isImported = "Y".equalsIgnoreCase(importedYN);

	// If there is a request attribute named "context", then another
	// op is forwarding to this op.  Get the Ardais Id and pathology
	// operation from the context.
	SelectDonorHelper context = (SelectDonorHelper)request.getAttribute("context");
	if (context != null) {
		ardaisId = context.getArdaisId();
		pathOp = context.getPathOp();
    isImported = "Y".equalsIgnoreCase(context.getImportedYN());
	}

	// If there is not a request attribute named "context", then get
	// the Ardais Id and pathology operation from request parameters.
	else {
		ardaisId = JspHelper.safeTrim(request.getParameter("ardaisId"));
		pathOp = JspHelper.safeTrim(request.getParameter("pathOp"));
		context = new SelectDonorHelper();
		context.setArdaisId(ardaisId);
		context.setPathOp(pathOp);
	}

	// Make sure that required information is present, and if not
	// throw an exception.
  if (isImported) {
    if ((JspHelper.isEmpty(ardaisId) && JspHelper.isEmpty(customerId)) ||
        (!JspHelper.isEmpty(ardaisId) && !JspHelper.isEmpty(customerId))) {
      SelectDonorHelper helper = new SelectDonorHelper(request);
      helper.getMessageHelper().addMessage("Please specify either an Donor Id or Donor Alias");
      request.setAttribute("helper", helper);
      if ("PathRawPrepare".equalsIgnoreCase(pathOp)) {
        helper.setPageTitle("Select Donor (Path Report Full Text)");
        forward("/dataImport/pathReportFullTextStart.do");
      }
      else if ("PathAbstractSummaryPrepare".equalsIgnoreCase(pathOp)) {
        helper.setPageTitle("Select Donor (Path Report Abstraction)");
        forward("/dataImport/pathReportAbstractionStart.do");
      }
      else if ("ClinicalDataSummaryPrepare".equalsIgnoreCase(pathOp)) {
        helper.setPageTitle("Select Donor (Clinical Data Extraction)");
        forward("/dataImport/clinicalDataExtractionStart.do");
      }
      else if ("CaseProfileNotesPrepare".equalsIgnoreCase(pathOp)) {
        helper.setPageTitle("Select Donor (Case Profile Entry)");
        forward("/dataImport/caseProfileEntryStart.do");
      }
      else {
        throw new ApiException("Unable to determine forward path");
      }
      return;
    }    
  }
  else {
  	if (JspHelper.isEmpty(ardaisId))
  		throw new ApiException("Required parameter 'ardaisId' is not present.");
  }
  
  //if a customer id was specified, try to get the corresponding Ardais Id.
  //If one cannot be found, return an error message
  if (isImported && !ApiFunctions.isEmpty(customerId)) {
    ardaisId = PdcUtils.findDonorIdFromCustomerId(customerId, (WebUtils.getSecurityInfo(request)).getAccount());
    if (JspHelper.isEmpty(ardaisId)) {
      SelectDonorHelper helper = new SelectDonorHelper(request);
      helper.getMessageHelper().addMessage("No donor exists with the specified alias.");
      request.setAttribute("helper", helper);
      if ("PathRawPrepare".equalsIgnoreCase(pathOp)) {
        helper.setPageTitle("Select Donor (Path Report Full Text)");
        forward("/dataImport/pathReportFullTextStart.do");
      }
      else if ("PathAbstractSummaryPrepare".equalsIgnoreCase(pathOp)) {
        helper.setPageTitle("Select Donor (Path Report Abstraction)");
        forward("/dataImport/pathReportAbstractionStart.do");
      }
      else if ("ClinicalDataSummaryPrepare".equalsIgnoreCase(pathOp)) {
        helper.setPageTitle("Select Donor (Clinical Data Extraction)");
        forward("/dataImport/clinicalDataExtractionStart.do");
      }
      else if ("CaseProfileNotesPrepare".equalsIgnoreCase(pathOp)) {
        helper.setPageTitle("Select Donor (Case Profile Entry)");
        forward("/dataImport/caseProfileEntryStart.do");
      }
      else {
        throw new ApiException("Unable to determine forward path");
      }
      return;
    }    
  }
    
  // expand id...note that this assumes the id was validated prior to this 
  //  call...which is currently true for the DDC operations invoked from here...to
  //  add validation logic here will require handling all of the pathOp cases below...
  String validated_id = ValidateIds.canonicalizeId(ardaisId);
  ardaisId = validated_id;

	if (JspHelper.isEmpty(pathOp))
		throw new ApiException("Required parameter 'pathOp' is not present.");

	// Set the header to be displayed above the list of cases.
	if (pathOp.equals("PathRawPrepare")) {
		context.setListHeader("List of Cases: Full Text Pathology Reports");
	}

	else if (pathOp.equals("PathAbstractSummaryPrepare")) {
		context.setListHeader("List of Cases: Pathology Report Abstracts");
	}

	// Clinical Data Extraction is only valid for linked cases, so
	// check that here and go back to the select donor page if an
	// unlinked Ardais ID was entered.
	else if (pathOp.equals("ClinicalDataSummaryPrepare")) {		
		context.setListHeader("List of Cases: Clinical Data Extraction");
		context.setOp("CaseListPrepare");
		context.setCtx(JspHelper.SEARCH_CONTEXT_CLINICAL_DATA_EXTRACTION);
		boolean isInformed = true;
		if (ApiFunctions.isEmpty(ardaisId))
			isInformed = false;
		else if (!(ardaisId.substring(0, 2).equals(ValidateIds.PREFIX_DONOR_LINKED)))
			isInformed = false;
    //because imported donors (linked or otherwise) all start with "AX", the only
    //way to determine if an imported donor is linked is to see if they have any
    //linked cases
    if (isImported) {
      try {
        DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
        DDCDonor donorOperation = ddcDonorHome.create();
        DonorData donor = donorOperation.getDonorCaseSummary(ardaisId,true);
        isInformed = !ApiFunctions.isEmpty(donor.getConsents());
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
		if (!isInformed) {
      if (isImported) {
        SelectDonorHelper helper = new SelectDonorHelper(request);
        helper.getMessageHelper().addMessage("Clinical Data Extraction is for the entry of information for donors with linked cases only.  The specified donor has no linked cases.");
        request.setAttribute("helper", helper);
        helper.setPageTitle("Select Donor (Clinical Data Extraction)");
        forward("/dataImport/clinicalDataExtractionStart.do");
      }
      else {
  			context.getMessageHelper().addMessage("Clinical Data Extraction is for entry of linked donor information only.  Please enter a linked Ardais ID.");
  			context.getMessageHelper().setError(true);
  			context.setPageTitle("Select Donor (Clinical Data Extraction)");
  			context.determineLinkInformation(request,context.getCtx(),context.getOp(),context.getPathOp());
  			request.setAttribute("helper", context);
  			(new SelectDonorPrepare(request, response, servletCtx)).invoke();
      }
			return;
		}
	}

	else if (pathOp.equals("CaseProfileNotesPrepare")) {
		context.setListHeader("List of Cases: Case Profile Notes");
		context.setOp("CaseListPrepare");
		context.setCtx(JspHelper.SEARCH_CONTEXT_CASE_PROFILE_ENTRY);
    boolean isInformed = true;
    if (ApiFunctions.isEmpty(ardaisId))
      isInformed = false;
    else if (!(ardaisId.substring(0, 2).equals(ValidateIds.PREFIX_DONOR_LINKED)))
      isInformed = false;
    //because imported donors (linked or otherwise) all start with "AX", the only
    //way to determine if an imported donor is linked is to see if they have any
    //linked cases
    if (isImported) {
      try {
        DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
        DDCDonor donorOperation = ddcDonorHome.create();
        DonorData donor = donorOperation.getDonorCaseSummary(ardaisId,true);
        isInformed = !ApiFunctions.isEmpty(donor.getConsents());
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
    }
		if (!isInformed) {
      if (isImported) {
        SelectDonorHelper helper = new SelectDonorHelper(request);
        helper.getMessageHelper().addMessage("Case Profile Entry is for the entry of information for donors with linked cases only.  The specified donor has no linked cases.");
        request.setAttribute("helper", helper);
        helper.setPageTitle("Select Donor (Case Profile Entry)");
        forward("/dataImport/caseProfileEntryStart.do");
      }
      else {
  			context.getMessageHelper().addMessage("Case Profile Entry is for entry of linked donor information only.  Please enter a linked Ardais ID.");
  			context.getMessageHelper().setError(true);
  			context.setPageTitle("Select Donor (Case Profile Entry)");
  			context.determineLinkInformation(request,context.getCtx(),context.getOp(),context.getPathOp());
  			request.setAttribute("helper", context);
  			(new SelectDonorPrepare(request, response, servletCtx)).invoke();
      }
			return;
		}
	}

	try {
    DonorData donor = null;
		// Get the summary of the donor's cases.
    // Note that if the donor is imported and the transaction is either
    // Case Profile Entry or Clinical Data Extraction, we need to 
    // restrict the cases to linked only
    DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
    DDCDonor donorOperation = ddcDonorHome.create();
    if (isImported && 
        (pathOp.equals("ClinicalDataSummaryPrepare") || pathOp.equals("CaseProfileNotesPrepare"))) {
      donor = donorOperation.getDonorCaseSummary(ardaisId,true);
    }
    else {
      donor = donorOperation.getDonorCaseSummary(ardaisId,false);
    }
		
		//if the requested donor exists, see if this user has access to them.  If not, 
		//display a message to the user, log the event, and return the user to the 
		//"Select Donor" page
		if (donorOperation.isPresent(donor)) {			
			//try to get the security information for the current user
	        SecurityInfo securityInfo = null;
	        HttpSession session = request.getSession(false);
	        if (session != null) {
	            securityInfo =
	                (SecurityInfo) request.getSession().getAttribute(
	                    SecurityInfo.SECURITY_KEY);
	        }
			//if we cannot get the security information for the current user throw
			//an exception
	        if (securityInfo == null) {
				throw new ApiException("Unable to obtain security information for the current user.");
	        }
			if (!PdcUtils.isDonorAccessibleByDdcUser(securityInfo,ardaisId)) {
          Log apiLog = ApiLogger.getLog();
    			StringBuffer logMessage = new StringBuffer(150);
    			logMessage.append("User ");
    			logMessage.append(securityInfo.getUsername());
    			logMessage.append(" (account ");
    			logMessage.append(securityInfo.getAccount());
    			logMessage.append(") attempted to access donor ");
    			logMessage.append(ardaisId);
    			logMessage.append(" for DDC functionality but that donor belongs to a different donor institution."); 
    			apiLog.warn(logMessage.toString());
	    		SelectDonorHelper helper = new SelectDonorHelper(request);
				helper.getMessageHelper().addMessage("Donor belongs to a different donor institution.");
				request.setAttribute("helper", helper);
    			StringBuffer forwardTo = new StringBuffer(50);
    			forwardTo.append("/ddc/Dispatch?op=SelectDonorPrepare");
	   			forward(forwardTo.toString());
    			return;
			}
      
      //for imported donors get the donor alias (if any) so we can 
      //display it on resulting pages
      if (isImported) {
        DonorData dd = new DonorData();
        dd.setArdaisId(ardaisId);
        dd = donorOperation.getDonorProfile(dd);
        donor.setCustomerId(dd.getCustomerId());
      }
	
      //determine if this op needs to store donor/case id information in the session
      //and if so store the ardais id in the session for use in subsequent DDC operations.
			String storeInfo = JspHelper.safeTrim(request.getParameter("storeInfoInSession"));
			if (!JspHelper.isEmpty(storeInfo)) {
				if ("true".equalsIgnoreCase(storeInfo)) {
          //we pass "true" for the last parameter because if the user is modifying the same
          //donor as the one currently saved in the session we don't want to erase any saved 
          //case or sample id.  If the user is modifying a different donor then any case and 
          //sample information will be erased regardless of the value of the last parameter.
					PdcUtils.storeLastUsedDonorCaseAndSample(request, ardaisId, donor.getCustomerId(),
              null, null, null, null, true);
				}
			}
		}

		// Forward to the case list page.
		DonorHelper donorHelper = new DonorHelper(donor);
    donorHelper.setImportedYN(isImported ? "Y" : "N");
		donorHelper.setContext(context);
		request.setAttribute("context", context);
		request.setAttribute("helper", donorHelper);
		forward("/hiddenJsps/ddc/path/CaseList.jsp");
	}
	catch (Exception e) {
		ApiFunctions.throwAsRuntimeException(e);
	}
}
  public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
 
}
