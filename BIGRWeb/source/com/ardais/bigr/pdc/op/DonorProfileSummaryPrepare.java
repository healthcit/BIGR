package com.ardais.bigr.pdc.op;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.pdc.beans.DDCDonor;
import com.ardais.bigr.pdc.beans.DDCDonorHome;
import com.ardais.bigr.pdc.helpers.AttachmentDataHelper;
import com.ardais.bigr.pdc.helpers.DonorHelper;
import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.helpers.SelectDonorHelper;
import com.ardais.bigr.pdc.javabeans.AttachmentData;
import com.ardais.bigr.pdc.javabeans.DonorData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Insert the type's description here.
 * Creation date: (7/11/2002 4:16:10 PM)
 * @author: Sahibul Islam
 */
public class DonorProfileSummaryPrepare extends StandardOperation {
  
   private String ardaisId = null;
  
   private String customerId = null;

   private String importedYN = null;
   
   private String deleteAttachId = null;
   
   private String attachFileFlag = null;
   
   private byte[] attachFileBody = null;
   
   private String attachFileName = null;//this is  the file name that come from the attached file
   
   private String attachFileType = null;
   
   private String fileName = null; //this is the file name that the user define
   
   private String fileComments = null;
   
   private String isPHIFlag_value = null;
   
   private String inventoryItemView =null;

   private String readOnlyString = null;
   
   private boolean isReadOnly = false;
   
   private boolean isImported = false;
   
/**
 * DonorProfileSummaryPrepare constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public DonorProfileSummaryPrepare(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {
  
  //attachFileFlag = JspHelper.safeTrim(request.getParameter("btnAttach"));

	final SecurityInfo securityInfo = getSecurityInfo();

	if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_DONOR_VIEW))
	{
		final ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.noPrivilege", "view donor information"));
		request.setAttribute(Globals.ERROR_KEY, errors);
		forward("/hiddenJsps/reportError/onlyError.jsp");
	}

  if(attachFileName == null && attachFileBody == null) {
  ardaisId = JspHelper.safeTrim(request.getParameter("ardaisId"));
 
  customerId = JspHelper.safeTrim(request.getParameter("customerId"));

  importedYN = JspHelper.safeTrim(request.getParameter("importedYN"));
  
  deleteAttachId = JspHelper.safeTrim(request.getParameter("deleteAttachId"));
    
  readOnlyString = JspHelper.safeTrim(request.getParameter("readOnly"));
  
  fileName = request.getParameter("fileName");
  
  fileComments = request.getParameter("fileComments");
  
  isPHIFlag_value = request.getParameter("IsPHIFlg_Value");
  }
  
  isReadOnly = "true".equalsIgnoreCase(readOnlyString);
  
  isImported = "Y".equalsIgnoreCase(importedYN);
  
  
  /*
   * Note - there will be situations where this code is working with an imported donor but
   * isImported will be false, due to the lack of an importedYN request parameter.  This
   * can happen, for example, when working with KC forms for donors.  This code has been
   * refactored to allow for this situation (MR8620).  At this point, the only time it is
   * essential for this code to know if it is working with an imported donor or not is when
   * it needs to display a page asking for a donor id.  For imported donors this is done from
   * a page that is different from non-imported donors, since users can enter an alias as an 
   * identifier.  If a valid donor id (not an alias) is provided, this code behaves identically for 
   * imported and non-imported donors.  
   */
  
  // Make sure that required information is present.
  if (isImported) {
    if (!validImportedInput(ardaisId, customerId)) {
      return;
    }
  }
  else {
  	if (JspHelper.isEmpty(ardaisId)) {
  		throw new ApiException("Required parameter 'ardaisId' is not present.");
  	}
  }
  
  // Get the corresponding ardais id from the customer id for an imported sample.
  if (isImported && !ApiFunctions.isEmpty(customerId)) {
    ardaisId = getArdaisIdFromCustomerId(customerId);
    if (ardaisId == null) {
      return;
    }
  }
  
  // Validate and expand the ardais id.
  String validatedId = getValidatedId(isImported, ardaisId);
  if (validatedId == null) {
    return;
  }
  else {
    ardaisId = validatedId;    
  }
	
	try {
		DonorData donor = new DonorData();
		donor.setArdaisId(ardaisId);
		DonorHelper donorHelper = new DonorHelper(donor);

    // First check to see if the donor exists.
    DDCDonorHome ddcDonorHome = (DDCDonorHome) EjbHomes.getHome(DDCDonorHome.class);
    DDCDonor donorOperation = ddcDonorHome.create();
    if (donorOperation.isPresent(donor)) {
      donorHelper.setDonorPresent(true);
      DonorData a_donor = donorOperation.getDonorProfile(donor);
      String donorAlias = a_donor.getCustomerId();
      donor.setCustomerId(donorAlias);
      donor.setArdaisAccountKey(a_donor.getArdaisAccountKey());
    
               
      // If the donor exists, check that the user has access to the donor.
      // DO NOT peform this check if calling from a read-only menu
      if (!isReadOnly) {
        if (!validateUserAccess(isImported, ardaisId)) {
          return;
        }
      }

      //SWP-1110
      //delete attachment if asked
      if(deleteAttachId != null && !"".equals(deleteAttachId)) {
        donorOperation.deleteAttachment(deleteAttachId);
      }
      
      //insert the attachment if asked
      if(attachFileName != null && attachFileBody != null) {
        AttachmentData attach = new AttachmentData();
        attach.setArdaisAccountKey(a_donor.getArdaisAccountKey());
        attach.setArdaisId(a_donor.getArdaisId());
        attach.setAttachment(attachFileBody);
        attach.setComments(fileComments);
        attach.setIsPHIYN(isPHIFlag_value);
        attach.setContentType(attachFileType);
        
        if( fileName == null || "".equals(fileName))
        attach.setName(attachFileName);
        else attach.setName(fileName);
    
        attach.setCreatedBy(securityInfo.getUsername());
        donorOperation.insertDonorAttachment(attach, securityInfo);
       
      } 
      
      //retrieve possible attachments 
      Collection attachments = donorOperation.getAttachments(donor);
      
      ArrayList attachHelpers = new ArrayList();
      AttachmentDataHelper aHelper = null;
      //convert the data bean to the helper
      for(Iterator itr =attachments.iterator(); itr.hasNext();){
          AttachmentData aData = (AttachmentData)itr.next();
          aHelper = new AttachmentDataHelper(aData);
          attachHelpers.add(aHelper);
      }
      donorHelper.setAttachments(attachHelpers);
      
      // Get the existing form instances for the donor.
      donorHelper.findFormInstances(securityInfo, ardaisId);      
      
      // Get the form definitions for donors in the user's account.
      donorHelper.findFormDefinitions(securityInfo);      
      
      // Determine if this op needs to store donor/case id information in the session
      // aand if so store the ardais id in the session for use in subsequent DDC operations.
      String storeInfo = JspHelper.safeTrim(request.getParameter("storeInfoInSession"));
      if (!JspHelper.isEmpty(storeInfo)) {
        if ("true".equalsIgnoreCase(storeInfo)) {
          //we pass "true" for the last parameter because if the user is modifying the same
          //donor as the one currently saved in the session we don't want to erase any saved 
          //case or sample id.  If the user is modifying a different donor then any case and 
          //sample information will be erased regardless of the value of the last parameter.
          PdcUtils.storeLastUsedDonorCaseAndSample(request, ardaisId, 
              donorAlias, null, null, null, null, true);
        }
      }
      //because we might be arriving here from the Create Donor functionality, if there
      //are messages in the request make sure they are carried forward
      Collection messages = (Collection)request.getAttribute("msg");
      if (!ApiFunctions.isEmpty(messages)) {
        Iterator iterator = messages.iterator();
        while (iterator.hasNext()) {
          donorHelper.getMessageHelper().addMessage((String)iterator.next());
        }
      }
    }
    
    
    else {
      // create the helper indicating that the donor does not exist.
      donorHelper.setDonorPresent(false);
    }

    request.setAttribute("readOnly", "true");
		request.setAttribute("helper", donorHelper);
    forward("/hiddenJsps/ddc/donor/DonorProfileSummary.jsp");
	}
	catch (Exception e) {
		ApiFunctions.throwAsRuntimeException(e);
	}	
}


  public void preInvoke(Collection fileItems) {
    
    for (Iterator itr = fileItems.iterator(); itr.hasNext();  ) {
      FileItem item = (FileItem) itr.next();
      if (item.isFormField()) {
       // System.out.println("I am in item name="+item.getFieldName() + " ="+item.getString());
          if(item.getFieldName().equals("ardaisId"))
            ardaisId = JspHelper.safeTrim(item.getString());
          
          if(item.getFieldName().equals("customerId"))
           customerId = JspHelper.safeTrim(item.getString());
           
          if(item.getFieldName().equals("importedYN"))
           importedYN = JspHelper.safeTrim(item.getString());
           
          if(item.getFieldName().equals("IsPHIFlg_Value"))
          isPHIFlag_value = JspHelper.safeTrim(item.getString());
           
          if(item.getFieldName().equals("fileName"))
           fileName = JspHelper.safeTrim(item.getString());
          
          if(item.getFieldName().equals("fileComments"))
            fileComments = JspHelper.safeTrim(item.getString());
           
          if(item.getFieldName().equals("readOnly"))
           readOnlyString = JspHelper.safeTrim(item.getString());
        }
      else {
          attachFileName = item.getName(); 
          attachFileBody = item.get();
          attachFileType = item.getContentType();
       // System.out.println("I am in File name="+item.getName() + " ="+item.getString());
    }
    
    }
     fileItems = null;
     invoke();
  }

  private boolean validImportedInput(String ardaisId, String customerId) {
    if ((JspHelper.isEmpty(ardaisId) && JspHelper.isEmpty(customerId)) ||
        (!JspHelper.isEmpty(ardaisId) && !JspHelper.isEmpty(customerId))) {
      SelectDonorHelper helper = new SelectDonorHelper(request);
      helper.getMessageHelper().addMessage("Please specify either a Donor Id or Donor Alias");
      request.setAttribute("helper", helper);
      forward("/dataImport/modifyDonorStart.do");
      return false;
    }
    return true;
  }
  
  private String getArdaisIdFromCustomerId(String customerId) {
    String ardaisId = PdcUtils.findDonorIdFromCustomerId(customerId, (WebUtils
        .getSecurityInfo(request)).getAccount());
    if (JspHelper.isEmpty(ardaisId)) {
      SelectDonorHelper helper = new SelectDonorHelper(request);
      helper.getMessageHelper().addMessage("No donor exists with the specified alias.");
      request.setAttribute("helper", helper);
      forward("/dataImport/modifyDonorStart.do");
      return null;
    }
    return ardaisId;
  }
  
  private String getValidatedId(boolean isImported, String ardaisId) {
    ValidateIds validId = new ValidateIds();
    //due to MR8620, check the id against all valid donor prefixes (i.e. no longer
    //check against different prefixes when working with imported donors versus
    //non-imported donors.
    String validatedId = validId.validate(ardaisId, ValidateIds.TYPESET_DONOR, true);
    if (validatedId == null) {
      SelectDonorHelper helper = new SelectDonorHelper(request);
      helper.getMessageHelper().addMessage(validId.getErrorMessage("a Donor id"));
      request.setAttribute("helper", helper);
      if (isImported) {
        forward("/dataImport/modifyDonorStart.do");
      }
      else {
        forward("/ddc/Dispatch?op=SelectDonorPrepare");
      }
      return null;
    }
    return validatedId;
  }
  
  private boolean validateUserAccess(boolean isImported, String ardaisId) {
    SecurityInfo securityInfo = getSecurityInfo();

    // Check if the user has access to the donor.  If not, log an error message and show an
    // error message to the user.
    if (!PdcUtils.isDonorAccessibleByDdcUser(securityInfo, ardaisId)) {
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
      if (isImported) {
        forward("/dataImport/modifyDonorStart.do");
      }
      else {
        forward("/ddc/Dispatch?op=SelectDonorPrepare");
      }
      return false;
    }
    return true;
  }
  
  private SecurityInfo getSecurityInfo() {
    // Try to get the security information for the current user.  If we can't throw an exception.
    SecurityInfo securityInfo = null;
    HttpSession session = request.getSession(false);
    if (session != null) {
      securityInfo = (SecurityInfo) request.getSession().getAttribute(SecurityInfo.SECURITY_KEY);
    }
    if (securityInfo == null) {
      throw new ApiException("Unable to obtain security information for the current user.");
    }
    return securityInfo;
  }
}
