package com.ardais.bigr.pdc.op;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Enumeration;


import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.fileupload.FileItem;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.helpers.JspHelper;


import com.ardais.bigr.iltds.beans.ConsentHome;
import com.ardais.bigr.iltds.beans.ConsentOperation;
import com.ardais.bigr.iltds.beans.ConsentOperationHome;
import com.ardais.bigr.iltds.beans.ConsentKey;
import com.ardais.bigr.iltds.beans.Consent;

import com.ardais.bigr.pdc.helpers.PdcUtils;
import com.ardais.bigr.pdc.helpers.SelectDonorHelper;
import com.ardais.bigr.pdc.javabeans.AttachmentData;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;


/**
 * This class only care about attachment upload for case management not like DonorProfileSummaryPrepare
 * Creation date: (8/27/2010)
 * @author: Eric Zhang
 */
public class CaseFormSummaryPrepare extends StandardOperation {
  
   
   private String consentId = null;

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
 * CaseFormSummaryPrepare constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public CaseFormSummaryPrepare(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
/**
 */
public void invoke() {
  
  
  
	
	try {
	
    // First check to see if the donor exists.
    ConsentHome cnstHome = (ConsentHome) EjbHomes.getHome(ConsentHome.class);
    Consent consent  = cnstHome.findByPrimaryKey(new ConsentKey(consentId));
    ConsentOperationHome cntOpHome = (ConsentOperationHome) EjbHomes.getHome(ConsentOperationHome.class);
    ConsentOperation cntOperation = cntOpHome.create();
    
    if (consent != null) {
           
      // If the donor exists, check that the user has access to the donor.
      // DO NOT peform this check if calling from a read-only menu
     /* if (!isReadOnly) {
        if (!validateUserAccess(isImported, ardaisId)) {
          return;
        }
      } */

      SecurityInfo securityInfo = getSecurityInfo();
      
   
      //insert the attachment if asked
      if(attachFileName != null && attachFileBody != null) {
        AttachmentData attach = new AttachmentData();
        attach.setArdaisAccountKey(consent.getArdais_acct_key());
        attach.setConsentId(consentId);
        attach.setAttachment(attachFileBody);
        attach.setComments(fileComments);
        attach.setIsPHIYN(isPHIFlag_value);
        attach.setContentType(attachFileType);
        
        if( fileName == null || "".equals(fileName))
        attach.setName(attachFileName);
        else attach.setName(fileName);
    
        attach.setCreatedBy(securityInfo.getUsername());
        cntOperation.insertCNTAttachment(attach, securityInfo);
       
      } 
      
      //retrieve possible attachments 
     // Collection attachments = cntOperation.getAttachments(consent);
      
   /*   ArrayList attachHelpers = new ArrayList();
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
      }  */
    }
    
    
  

  //  request.setAttribute("readOnly", "true");
    //System.out.println("I am in URL="+request.getContextPath()+"/dataImport/getCaseFormSummary.do?consentId="+consentId);
    request.setAttribute("consent", "Yes");
    forward("/hiddenJsps/dataImport/attachmentSuccess.jsp?consentId="+consentId);
    
  
    
   
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
          if(item.getFieldName().equals("consentId"))
            consentId = JspHelper.safeTrim(item.getString());
          
       /*   if(item.getFieldName().equals("customerId"))
           customerId = JspHelper.safeTrim(item.getString()); */
           
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
