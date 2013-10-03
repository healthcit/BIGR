package com.ardais.bigr.pdc.op;

import com.ardais.bigr.api.*;
import com.ardais.bigr.configuration.LabelPrintingConfiguration;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.iltds.helpers.*;
import com.ardais.bigr.pdc.beans.*;
import com.ardais.bigr.pdc.helpers.*;
import com.ardais.bigr.pdc.javabeans.*;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.StrutsProperties;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * Creation date: (7/10/2002 3:33:39 PM)
 * @author: Sahibul Islam
 */

 /**
 * Prepares information for the JSP that allows creating and updating of
 * Donor Profile.
 *
 * <p><b>Inputs:</b></p>
 * <ul>
 * <li>pathReportId (request parameter, required) - the id of the pathology report
 *     with which the diagnostic is associated</li>
 * <li>diagnostic (request parameter, optional) - the diagnostic concept id of the 
 *     diagnostic being edited, if an existing diagnostic is being edited</li>
 * <li>new (request parameter, optional) - if this parameter is present and equal to
 *     <code>true</code>, then a new diagnostic is to be created; otherwise
 *     an existing diagnostic is to be updated
 * </ul>
 * OR
 * <ul>
 * <li>helper (request attribute) - a <code>PathReportDiagnosticHelper</code>
 *     for the diagnostic to be edited.  This must contain at least the
 *     pathReportId, and may also contain the new flag, and diagnostic concept id
 *     and all fields of the diagnostic</li>
 * </ul>
 *
 * <b>Outputs:</b>
 * <ul>
 * <li>helper (request attribute) - a <code>PathReportDiagnosticHelper</code>
 *     that contains information on the diagnostic to be created/edited</li>
 * </ul>
 *
 * <b>Forwards:</b>
 * <ul>
 * <li>/path/abstract/PathDiagnostic.jsp</li>
 * </ul>
 */
 
public class DonorProfilePrepare extends StandardOperation {
/**
 * DonorProfilePrepare constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public DonorProfilePrepare(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {

	DonorHelper donorHelper = (DonorHelper)request.getAttribute("helper");
	boolean haveHelper = (donorHelper != null);
  boolean blankArdaisIdAllowed = false;
	
	if (haveHelper)
		if ("true".equals(JspHelper.safeTrim(request.getParameter("new"))))
			donorHelper.setNew(true);	
		if ("false".equals(JspHelper.safeTrim(request.getParameter("new"))))
			donorHelper.setNew(false);	

  //if we are dealing with a new imported donor, it's ok if the ardais Id is blank.
  //these donors don't get an Ardais Id until they are actually created.
  if (haveHelper && donorHelper.isNew() && "Y".equalsIgnoreCase(donorHelper.getImportedYN())) {
    blankArdaisIdAllowed = true;
  }
	
	
	// Make sure that required information is present, and if not
	// throw an exception.
	String ardaisId = JspHelper.safeTrim(request.getParameter("ardaisId"));
	if (JspHelper.isEmpty(ardaisId) && !blankArdaisIdAllowed) {
		throw new ApiException("Required parameter 'ardaisId' is not present.");
	}
	
	try {

		if (!haveHelper) {
		
			// Get the donor profile information.
			DonorData donor = new DonorData();
			donor.setArdaisId(ardaisId);
			
      DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
      DDCDonor donorOperation = ddcDonorHome.create();
      DonorData donorData = donorOperation.getDonorProfile(donor);
			if (ApiFunctions.isEmpty(donorData.getArdaisId())) {
				donorHelper = new DonorHelper(donor);
				donorHelper.setNew(true);
			}
			else {
				donorHelper = new DonorHelper(donorData);		
			}
      
		}
    
    //populate the label printing information.  If it is invalid then return an error
    //letting the user know that label printing is unavailable.
    String accountId = WebUtils.getSecurityInfo(request).getAccount();
    try {
      Map labelPrintingData = LabelPrintingConfiguration.getLabelTemplateDefinitionsAndPrintersForObjectType(accountId, Constants.LABEL_PRINTING_OBJECT_TYPE_DONOR);
      donorHelper.setLabelPrintingData(labelPrintingData);
      //default the number of labels to print to 1 if there is no value
      if (ApiFunctions.isEmpty(donorHelper.getLabelCount())) {
        donorHelper.setLabelCount("1");
      }
    }
    catch (IllegalStateException ise) {
      //log the error so the problems are documented
      ApiLogger.log("Label Printing Configuration refresh failed.", ise);
      donorHelper.setLabelPrintingData(new HashMap());
      StrutsProperties props = StrutsProperties.getInstance();
      String msg = props.getProperty("orm.error.label.invalidPrintingConfiguration");
      if (msg.startsWith("<li>")) {
        msg = msg.substring(4);
      }
      if (msg.endsWith("</li>")) {
        msg = msg.substring(0, msg.length() - 5);
      }
      donorHelper.getMessageHelper().addMessage(msg);
    }
    
    request.setAttribute("helper", donorHelper);
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    DataFormDefinition regForm = donorHelper.findRegistrationForm(securityInfo);
    if (regForm != null) {
      donorHelper.setupKcFormContext(request, regForm, donorHelper.getDataBean().getFormInstance());        
      forward("/hiddenJsps/ddc/donor/DonorProfile.jsp");
    }
    else {
      StrutsProperties props = StrutsProperties.getInstance();
      String msg = props.getProperty("dataImport.error.noDonorRegistrationFormSpecifiedForAccount");
      if (msg.startsWith("<li>")) {
        msg = msg.substring(4);
      }
      if (msg.endsWith("</li>")) {
        msg = msg.substring(0, msg.length() - 5);
      }
      donorHelper.getMessageHelper().addMessage(msg);
      forward("/hiddenJsps/ddc/donor/DonorProfileSummary.jsp");
    }
	}
	catch (Exception e) {
		ApiFunctions.throwAsRuntimeException(e);
	}	
}
public void preInvoke(Collection fileItems) {
  //method for potential file attachment, do nothing for now ;
  
}
}
